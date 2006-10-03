/* The contents of this file are subject to the Health Level-7 Public
* License Version 1.0 (the "License"); you may not use this file
* except in compliance with the License. You may obtain a copy of the
* License at http://www.hl7.org/HPL/
*
* Software distributed under the License is distributed on an "AS IS"
* basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See
* the License for the specific language governing rights and
* limitations under the License.
*
* The Original Code is all this file.
*
* The Initial Developer of the Original Code is .
* Portions created by Initial Developer are Copyright (C) 2002-2004
* Health Level Seven, Inc. All Rights Reserved.
*
* Contributor(s):
*/
package org.hl7.meta.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Stack;

import org.hl7.meta.Cardinality;
import org.hl7.meta.CodingStrength;
import org.hl7.meta.Conformance;
import org.hl7.meta.DatatypeMetadataFactory;
import org.hl7.meta.LoaderException;
import org.hl7.meta.MessageType;
import org.hl7.meta.MetSource;
import org.hl7.meta.UnknownDatatypeException;
import org.hl7.meta.util.StringUtils;

/**
 * An implementation of <code>MessageTypeLoader</code> that
 * loads message type metadata from HL7 v3 repository Access database.
 *
 * @author Skirmantas Kligys (skirmis@hotmail.com)
 */
public class AccessMessageTypeLoader extends MessageTypeLoaderImpl
{
  //-------------------------------------------------------------------------
  /**
   * ODBC source for message metadata.
   */
  private final String odbcSource_;

  /**
   * Connection for message metadata.
   */
  private Connection conn_;

  /**
   * ODBC source for CMET metadata.
   */
  private final String cmetOdbcSource_;

  /**
   * Connection for CMET metadata.
   */
  private Connection cmetConn_;

  /**
   * Factory for parsing datatype literals.
   */
  private DatatypeMetadataFactory dmFactory_ =
    DatatypeMetadataFactoryImpl.instance();

  /**
   * CMET cache for on-demand cached loading.
   */
  private CmetCacheImpl cmetCache_;

  //-------------------------------------------------------------------------
  /**
   * Constructor. It also loads the ODBC/JDBC bridge driver.
   *
   * @param odbcSource  datasource name for message metadata
   * @param cmetOdbcSource  datasource name for CMET metadata
   *
   * @throws DatabaseLoaderException  if loading ODBC bridge fails
   */
  public AccessMessageTypeLoader(String odbcSource, String cmetOdbcSource)
    throws DatabaseLoaderException
  {
    try
    {
      Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
    }
    catch (ClassNotFoundException ex)
    {
      throw new DatabaseLoaderException(ex);
    }

    odbcSource_ = odbcSource;
    cmetOdbcSource_ = cmetOdbcSource;
    cmetCache_ = new CmetCacheImpl(this);
  }

  //-------------------------------------------------------------------------
  /**
   * If the connection for message metadata is not yet open, opens it.
   */
  public void openConnection() throws SQLException
  {
    if (conn_ == null)
    {
      conn_ = DriverManager.getConnection("jdbc:odbc:" + odbcSource_, "admin",
        "");
    }
  }

  //-------------------------------------------------------------------------
  /**
   * If the connection for CMET metadata is not yet open, opens it.
   */
  public void openCmetConnection() throws SQLException
  {
    if (cmetConn_ == null)
    {
      cmetConn_ = DriverManager.getConnection("jdbc:odbc:" + cmetOdbcSource_,
        "admin", "");
    }
  }

  //-------------------------------------------------------------------------
  /**
   * Closes both message and CMET metadata connections if they are open.
   */
  public void close()
  {
    if (conn_ != null)
    {
      try { conn_.close(); } catch (SQLException ex) {}
      conn_ = null;
    }

    if (cmetConn_ != null)
    {
      try { cmetConn_.close(); } catch (SQLException ex) {}
      cmetConn_ = null;
    }
  }

  //-------------------------------------------------------------------------
  /**
   * A helper class that keeps track of the current row in the result set;
   * also holds the metadata object under construction.
   */
  private static class LoaderContext
  {
    //.......................................................................
    /**
     * The metadata object being constructed.
     */
    private final MessageTypeImpl mti_;

    /**
     * JDBC connection.
     */
    private final Connection conn_;

    /**
     * GUID of the first HMD row in the HMD result set.
     */
    private byte[] firstRowGuid_;

    /**
     * The current row in the result set of HMD rows.
     */
    private int row_ = 1;

    /**
     * A stack of clone classes currently under construction.
     */
    private Stack/*<CloneClassImpl>*/ cloneClasses_ =
      new Stack/*<CloneClassImpl>*/();

    //.......................................................................
    /**
     * Default constructor.
     *
     * @param mti  an empty message type metadata object to fill in
     * @param conn a JDBC connection
     */
    /*default*/ LoaderContext(MessageTypeImpl mti, Connection conn)
    {
      if (mti == null || conn == null) throw new NullPointerException();
      mti_ = mti;
      conn_ = conn;
    }

    //.......................................................................
    /**
     * Returns the metadata object under construction.
     *
     * @return  message type object
     */
    /*default*/ MessageTypeImpl getMessageType() { return mti_; }

    /**
     * Returns the JDBC connection used.
     *
     * @return JDBC connection
     */
    /*default*/ Connection getConnection() { return conn_; }

    /**
     * Returns the GUID of the first HMD row in the HMD result set.
     *
     * @return  the GUID
     */
    /*default*/ byte[] getFirstRowGuid()
    {
      return firstRowGuid_;
    }

    /**
     * Sets the GUID of the first HMD row in the HMD result set.
     *
     * @param firstRowGuid  the GUID
     */
    /*default*/ void setFirstRowGuid(byte[] firstRowGuid)
    {
      firstRowGuid_ = firstRowGuid;
    }

    /**
     * Returns the ID of message type being loaded.
     *
     * @return  message ID
     */
    /*default*/ String getMessageId() { return mti_.getId(); }

    //.......................................................................
    /**
     * Returns <code>true</code> if the current row is the first row
     * in the result set.
     *
     * @return  <code>true</code> if the current row is the first
     */
    /*default*/ boolean isFirstRow() { return row_ == 1; }

    /**
     * Notifies that the row was processed, increases the row counter.
     */
    /*default*/ void rowProcessed() { ++row_; }

    /**
     * Returns the current row counter.
     *
     * @return  the row counter
     */
    /*default*/ int getRow() { return row_; }

    //.......................................................................
    /**
     * Returns a reference to the clone class currently being processed.
     *
     * @return  the current clone class
     */
    /*default*/ CloneClassImpl getCurrentCloneClass()
    {
      return (CloneClassImpl)cloneClasses_.peek();
    }

    //.......................................................................
    /**
     * Pushes a clone class onto the stack of clone classes being processed.
     *
     * @param cloneClass  the clone class to add
     */
    /*default*/ void setCurrentCloneClass(CloneClassImpl cloneClass)
    {
      cloneClasses_.push(cloneClass);
    }

    //.......................................................................
    /**
     * Notifies that the specified number of clone classes are finished,
     * pops them out of the stack of clone classes under processing.
     *
     * @param count  number of clone classes finished
     */
    /*default*/ void finishedCloneClasses(int count)
    {
      if (count == 0) return;

      for (int i = 0; i < count; ++i)
      {
        cloneClasses_.pop();
      }
    }

    //.......................................................................
    /**
     * Returns number of clone classes being constructed at the moment.
     *
     * @return  number of clone classes
     */
    /*default*/ int getLevel() { return cloneClasses_.size(); }
  }

  //-------------------------------------------------------------------------
  /**
   * Loads the message type metadata and returns the reference to the
   * metadata object for the message type.  Note that referenced CMETs are
   * not loaded automatically, but only on demand when the corresponding
   * association is actually traversed.
   *
   * @param id  the message type ID
   * @return the message type metadata
   * @throws LoaderException  if loading fails
   */
  public MessageType loadMessageType(String id) throws LoaderException
  {
    if (id == null) throw new NullPointerException();

    MessageTypeImpl mti = new MessageTypeImpl(id);
    try
    {
      openConnection();

      LoaderContext context = new LoaderContext(mti, conn_);
      loadMessageTypeWithGuid(context);
      loadMessageRowsWithGuid(context);
    }
    catch (SQLException ex)
    {
      throw new DatabaseLoaderException(ex);
    }

    postProcess(mti);

    return mti;
  }

  //-------------------------------------------------------------------------
  /**
   * Loads the CMET metadata and returns the reference to the
   * metadata object for the message type.  Note that referenced CMETs are
   * not loaded automatically, but only on demand when the corresponding
   * association is actually traversed.
   *
   * @param id  the message type ID
   * @return the message type metadata
   * @throws LoaderException  if loading fails
   */
  public MessageType loadCmet(String id) throws LoaderException
  {
    if (id == null) throw new NullPointerException();

    MessageTypeImpl mti = new MessageTypeImpl(id);
    try
    {
      openCmetConnection();

      LoaderContext context = new LoaderContext(mti, cmetConn_);
      loadMessageTypeWithGuid(context);
      loadMessageRowsWithGuid(context);
    }
    catch (SQLException ex)
    {
      throw new DatabaseLoaderException(ex);
    }

    postProcess(mti);

    return mti;
  }

  //-------------------------------------------------------------------------
  /**
   * Loads message type long name, based on message type ID.
   * This is an equivalent of the method
   * {@link #loadMessageType(AccessMessageTypeLoader.LoaderContext)
   *   loadMessageType()}
   * for loading from broken repository databases where sequence numbers
   * are invalid and records must be ordered by GUIDs.
   * Uses a separate query for each row, teherfore is very slow.
   *
   * Use <code>loadMessageType()</code> once the databases are fixed, the
   * performance will be much better (10 times faster!).
   *
   * @param context  the loader context
   * @throws SQLException  if loading fails
   * @throws DatabaseLoaderException  if loading fails
   */
  private void loadMessageTypeWithGuid(LoaderContext context)
    throws SQLException, DatabaseLoaderException
  {
    final String sql = "SELECT mt.name, h.rootRowID " +
      "FROM vHMD AS h INNER JOIN vMessageType AS mt ON " +
      "(h.vhmdID = mt.vhmdID) AND (h.vrmimID = mt.vrmimID) " +
      "WHERE mt.msgeIdentifier = ?";

    MessageTypeImpl mti = context.getMessageType();

    PreparedStatement stmt = null;
    ResultSet rs = null;
    try
    {
      stmt = context.getConnection().prepareStatement(sql);
      stmt.setString(1, mti.getId());
      rs = stmt.executeQuery();

      if (!rs.next())
      {
        throw new DatabaseLoaderException("Message type not found: " +
          mti.getId());
      }

      mti.setName(rs.getString(1));
      context.setFirstRowGuid(rs.getBytes(2));
    }
    finally
    {
      if (rs != null)
      {
        try
        {
          rs.close();
        }
        finally
        {
          if (stmt != null) stmt.close();
        }
      }
    }
  }

//  //-------------------------------------------------------------------------
//  /**
//   * Loads HMD row details, based on message type ID.  An old fast but
//   * deprecated version with no GUID support.  Works only on
//   * non-screwed-up repository databases (where sequence column is really a
//   * sequence column).
//   *
//   * @param context  the loader context
//   * @throws SQLException  if loading fails
//   * @throws DatabaseLoaderException  if loading fails
//   *
//   * @deprecated  Use the
//   *   {@link #loadMessageRowsWithGuid(AccessMessageTypeLoader.LoaderContext
//   *     context) loadMessageRowsWithGuid} method.
//   */
//  private void loadMessageRows(LoaderContext context)
//    throws SQLException, DatabaseLoaderException
//  {
//    final String sql = "SELECT hr.sequence, hr.nestLevel, hr.rowType, " +
//      "hr.baseMET, hr.choiceSet, hr.metSource, " +
//      "hr.nodeShortName, hr.rimName, hr.rimClassName, mrc.rmimVocDomain, " +
//      "mrc.rmimVocStrength, mrc.mandatory, mrc.constraint, mrc.defaultVal, " +
//      "mrc.cardinality, mrc.conformance " +
//      "FROM vMessageType AS mt INNER JOIN " +
//      "(vHMDrow AS hr INNER JOIN vMessageRowControl AS mrc ON " +
//      "(hr.vhmdRowID = mrc.controlledHmdRowID) AND (hr.vhmdID = mrc.vhmdID) AND " +
//      "(hr.vrmimID = mrc.vrmimID)) ON " +
//      "(mt.msgTypeID = mrc.parentMsgTypeID) AND (mt.vhmdID = mrc.vhmdID) AND " +
//      "(mt.vrmimID = mrc.vrmimID) " +
//      "WHERE mt.msgeIdentifier = ? ORDER BY hr.sequence";
//
//    MessageTypeImpl mti = context.getMessageType();
//
//    PreparedStatement stmt = null;
//    ResultSet rs = null;
//    try
//    {
//      stmt = context.getConnection().prepareStatement(sql);
//      stmt.setString(1, mti.getId());
//
//      for (rs = stmt.executeQuery(); rs.next(); )
//      {
//        processRow(context, rs);
//      }
//    }
//    finally
//    {
//      if (rs != null)
//      {
//        try
//        {
//          rs.close();
//        }
//        finally
//        {
//          if (stmt != null) stmt.close();
//        }
//      }
//    }
//  }

  //-------------------------------------------------------------------------
  /**
   * Loads HMD row details, based on message type ID.
   * This is an equivalent of the method
   * {@link #loadMessageRows(AccessMessageTypeLoader.LoaderContext)
   *   loadMessageType()}
   * for loading from broken repository databases where sequence numbers
   * are invalid and records must be ordered by GUIDs.
   * Uses a separate query for each row, teherfore is very slow.
   *
   * Use <code>loadMessageRows()</code> once the databases are fixed, the
   * performance will be much better (10 times faster!).
   *
   * @param context  the loader context
   * @throws SQLException  if loading fails
   * @throws DatabaseLoaderException  if loading fails
   */
  private void loadMessageRowsWithGuid(LoaderContext context)
    throws SQLException, DatabaseLoaderException
  {
    final String sql = "SELECT hr.vhmdRowID, hr.sequence, hr.nestLevel, " +
      "hr.rowType, hr.baseMET, hr.choiceSet, hr.metSource, hr.nodeShortName, " +
      "hr.rimName, hr.rimClassName, mrc.rmimVocDomain, mrc.rmimVocStrength, " +
      "mrc.mandatory, mrc.constraint, mrc.defaultVal, mrc.cardinality, " +
      "mrc.conformance, hr.nextHmdID " +
      "FROM vMessageType AS mt INNER JOIN " +
      "(vHMDrow AS hr INNER JOIN vMessageRowControl AS mrc ON " +
      "(hr.vhmdRowID = mrc.controlledHmdRowID) AND " +
      "(hr.vhmdID = mrc.vhmdID) AND (hr.vrmimID = mrc.vrmimID)) ON " +
      "(mt.msgTypeID = mrc.parentMsgTypeID) AND (mt.vhmdID = mrc.vhmdID) AND " +
      "(mt.vrmimID = mrc.vrmimID) " +
      "WHERE mt.msgeIdentifier = ? AND hr.vhmdRowID = ?";

    MessageTypeImpl mti = context.getMessageType();

    PreparedStatement stmt = null;
    ResultSet rs = null;
    try
    {
      stmt = context.getConnection().prepareStatement(sql);
      stmt.setString(1, mti.getId());

      byte[] rowGuid = context.getFirstRowGuid();

      while (rowGuid != null)
      {
        stmt.setBytes(2, rowGuid);

        rs = stmt.executeQuery();
        if (!rs.next())
        {
          throw new DatabaseLoaderException(mti.getId(), context.getRow(),
            "Cannot find next row");
        }

        processRow(context, rs);
        rowGuid = rs.getBytes("nextHmdID");

        if (rs.next())
        {
          throw new DatabaseLoaderException(mti.getId(), context.getRow(),
            "Multiple next rows found");
        }
      }
    }
    finally
    {
      if (rs != null)
      {
        try
        {
          rs.close();
        }
        finally
        {
          if (stmt != null) stmt.close();
        }
      }
    }
  }

  //-------------------------------------------------------------------------
  /**
   * Processes a single row from the result set according to its type.  Calls
   * one of the following methods to do real work:
   * <ul>
   *   <li>{@link #processClassRow(AccessMessageTypeLoader.LoaderContext,
   *     ResultSet) processClassRow()}</li>
   *   <li>{@link #processAttributeRow(AccessMessageTypeLoader.LoaderContext,
   *     ResultSet) processAttributeRow()}</li>
   *   <li>{@link #processAssociationRow(AccessMessageTypeLoader.LoaderContext,
   *     ResultSet) processAssociationRow()}</li>
   * </ul>
   *
   * @param context  the loader context
   * @param rs  the result set
   * @throws SQLException  if loading fails
   * @throws DatabaseLoaderException  if loading fails
   */
  private void processRow(LoaderContext context, ResultSet rs)
    throws SQLException, DatabaseLoaderException
  {
    MessageTypeImpl mti = context.getMessageType();
    String rowType = rs.getString("rowType");

    try
    {
      if (rowType.equals("class"))
      {
        processClassRow(context, rs);
      }
      else if (rowType.equals("attr"))
      {
        processAttributeRow(context, rs);
      }
      else if (rowType.equals("assoc"))
      {
        processAssociationRow(context, rs);
      }
      else
      {
        throw new DatabaseLoaderException(mti.getId(), context.getRow(),
          "Unknown row type: " + rowType);
      }

      context.rowProcessed();
    }
    catch (IllegalArgumentException ex)
    {
      throw new DatabaseLoaderException(mti.getId(), context.getRow(),
        ex.getMessage());
    }
  }

  //-------------------------------------------------------------------------
  /**
   * Processes a single class row from the result set.
   *
   * @param context  the loader context
   * @param rs  the result set
   * @throws SQLException  if loading fails
   * @throws DatabaseLoaderException  if loading fails
   */
  private void processClassRow(LoaderContext context, ResultSet rs)
    throws SQLException, DatabaseLoaderException
  {
    if (!context.isFirstRow())
    {
      throw new DatabaseLoaderException(context.getMessageId(),
        context.getRow(), "Row type 'class' must be only on the first row");
    }

    int level = rs.getInt("nestLevel");
    if (rs.wasNull() || level != 0)
    {
      throw new DatabaseLoaderException(context.getMessageId(),
        context.getRow(), "First row must have level 0");
    }

    CloneClassImpl cci = new CloneClassImpl(context.getMessageType());
    cci.setName(rs.getString("baseMET"));

    String rimClass = rs.getString("rimClassName");
    rimClass = StringUtils.trimSuffix(rimClass, "_heir");
    rimClass = StringUtils.trimSuffix(rimClass, "Heir");
    cci.setRimClass(rimClass);
    cci.setChoices(StringUtils.split(rs.getString("choiceSet"), '|'));

    context.setCurrentCloneClass(cci);
    context.getMessageType().setRootClass(cci);
  }

  //-------------------------------------------------------------------------
  /**
   * Processes a single attribute row from the result set.
   *
   * @param context  the loader context
   * @param rs  the result set
   * @throws SQLException  if loading fails
   * @throws DatabaseLoaderException  if loading fails
   */
  private void processAttributeRow(LoaderContext context, ResultSet rs)
    throws SQLException, DatabaseLoaderException
  {
    if (context.isFirstRow())
    {
      throw new DatabaseLoaderException(context.getMessageId(),
        context.getRow(), "Row type 'attr' cannot occur on the first row");
    }

    int level = rs.getInt("nestLevel");
    if (rs.wasNull() || level > context.getLevel())
    {
      throw new DatabaseLoaderException(context.getMessageId(),
        context.getRow(), "Level mismatch: context level=" +
        context.getLevel() + ", DB row level=" + level);
    }
    context.finishedCloneClasses(context.getLevel() - level);

    String dt = rs.getString("baseMET");
    AttributeImpl ai = new AttributeImpl(context.getCurrentCloneClass());
    try
    {
      ai.setDatatype(dmFactory_.create(dt));
    }
    catch (UnknownDatatypeException ex)
    {
      throw new DatabaseLoaderException("Unknown datatype: " + dt);
    }

    String metSource = rs.getString("metSource");
    if (metSource == null || !metSource.equals("D"))
    {
      throw new DatabaseLoaderException(context.getMessageId(),
        context.getRow(), "An attribute row must have metSource = 'D'");
    }

    ai.setName(rs.getString("nodeShortName"));

    String rimClass = rs.getString("rimClassName");
    rimClass = StringUtils.trimSuffix(rimClass, "_heir");
    rimClass = StringUtils.trimSuffix(rimClass, "Heir");
    ai.setRimClass(rimClass);

    ai.setRimPropertyName(rs.getString("rimName"));

    FixedValuesDomains fvd = new FixedValuesDomains(
      rs.getString("rmimVocDomain"));
    ai.setFixedValues(fvd.getFixedValues());
    ai.setDomains(fvd.getDomains());

    ai.setCodingStrength(CodingStrength.create(rs.getString("rmimVocStrength")));

    String mand = rs.getString("mandatory");
    ai.setMandatory(mand != null && mand.equals("M"));

    ai.setConstraint(rs.getString("constraint"));
    ai.setDefaultValue(rs.getString("defaultVal"));
    ai.setCardinality(Cardinality.create(rs.getString("cardinality")));
    ai.setConformance(Conformance.create(rs.getString("conformance")));

    context.getCurrentCloneClass().addAttribute(ai);
  }

  //-------------------------------------------------------------------------
  /**
   * Processes a single association row from the result set.  Depending
   * on <code>metSource</code>, calls one of the following:
   * <ul>
   *   <li>{@link #processCloneAssociationRow(
   *     AccessMessageTypeLoader.LoaderContext, ResultSet, MetSource, String)
   *     processCloneAssociationRow()}</li>
   *   <li>{@link #processCmetAssociationRow(
   *     AccessMessageTypeLoader.LoaderContext, ResultSet, MetSource, String)
   *     processCmetAssociationRow()}</li>
   * </ul>
   *
   * @param context  the loader context
   * @param rs  the result set
   * @throws SQLException  if loading fails
   * @throws DatabaseLoaderException  if loading fails
   */
  private void processAssociationRow(LoaderContext context, ResultSet rs)
    throws SQLException, DatabaseLoaderException
  {
    if (context.isFirstRow())
    {
      throw new DatabaseLoaderException(context.getMessageId(),
        context.getRow(), "Row type 'assoc' cannot occur on the first row");
    }

    int level = rs.getInt("nestLevel");
    if (rs.wasNull() || level > context.getLevel())
    {
      throw new DatabaseLoaderException(context.getMessageId(),
        context.getRow(), "Level mismatch: context level=" +
        context.getLevel() + ", DB row level=" + level);
    }
    context.finishedCloneClasses(context.getLevel() - level);

    MetSource metSource = MetSource.create(rs.getString("metSource"));
    if (metSource == null)
    {
      throw new DatabaseLoaderException(context.getMessageId(),
        context.getRow(), "Missing MET source");
    }

    String name = StringUtils.removeSet(rs.getString("baseMET"));

    if (metSource == MetSource.NEW)
    {
      processCloneAssociationRow(context, rs, metSource, name);
    }
    else if (metSource == MetSource.CMET)
    {
      processCmetAssociationRow(context, rs, metSource, name);
    }
    else if (metSource == MetSource.REUSED || metSource == MetSource.RECURSIVE)
    {
      if (StringUtils.matchesCmetMessagetypePattern(name) ||
        StringUtils.matchesCmetHmdPattern(name))
      {
        processCmetAssociationRow(context, rs, metSource, name);
      }
      else
      {
        processCloneAssociationRow(context, rs, metSource, name);
      }
    }
  }

  //-------------------------------------------------------------------------
  /**
   * Processes a single clone association row from the result set.
   *
   * @param context  the loader context
   * @param rs  the result set
   * @throws SQLException  if loading fails
   * @throws DatabaseLoaderException  if loading fails
   */
  private void processCloneAssociationRow(LoaderContext context,
    ResultSet rs, MetSource metSource, String metName)
    throws SQLException, DatabaseLoaderException
  {
    CloneClassAssociationImpl ccai = new CloneClassAssociationImpl(
      context.getCurrentCloneClass());

    ccai.setName(rs.getString("nodeShortName"));
    ccai.setCardinality(Cardinality.create(rs.getString("cardinality")));

    String rimClass = rs.getString("rimClassName");
    rimClass = StringUtils.trimSuffix(rimClass, "_heir");
    rimClass = StringUtils.trimSuffix(rimClass, "Heir");
    ccai.setRimClass(rimClass);

    ccai.setRimPropertyName(rs.getString("rimName"));
    ccai.setMetSource(metSource);

    String mand = rs.getString("mandatory");
    ccai.setMandatory(mand != null && mand.equals("M"));
    ccai.setConformance(Conformance.create(rs.getString("conformance")));

    context.getCurrentCloneClass().addAssociation(ccai);

    MessageTypeImpl mti = context.getMessageType();
    CloneClassImpl cci = (CloneClassImpl)mti.lookupCloneClass(metName);
    if (cci == null)
    {
      if (metSource != MetSource.NEW)
      {
        throw new DatabaseLoaderException(context.getMessageId(), context.getRow(),
          "Cannot find clone class: " + metName);
      }

      cci = new CloneClassImpl(mti);
      cci.setName(metName);
      cci.setRimClass(ccai.getRimClass());
      cci.setChoices(StringUtils.split(rs.getString("choiceSet"), '|'));

      mti.addCloneClass(cci);
    }
    ccai.setTarget(cci);

    context.setCurrentCloneClass(cci);
  }

  //-------------------------------------------------------------------------
  /**
   * Processes a single CMET association row from the result set.
   *
   * @param context  the loader context
   * @param rs  the result set
   * @throws SQLException  if loading fails
   * @throws DatabaseLoaderException  if loading fails
   */
  private void processCmetAssociationRow(LoaderContext context, ResultSet rs,
    MetSource metSource, String metName)
    throws SQLException, DatabaseLoaderException
  {
    CmetAssociationImpl cai = new CmetAssociationImpl(
      context.getCurrentCloneClass(), cmetCache_);

    cai.setName(rs.getString("nodeShortName"));
    cai.setCardinality(Cardinality.create(rs.getString("cardinality")));

    String rimClass = rs.getString("rimClassName");
    rimClass = StringUtils.trimSuffix(rimClass, "_heir");
    rimClass = StringUtils.trimSuffix(rimClass, "Heir");
    cai.setRimClass(rimClass);

    cai.setRimPropertyName(rs.getString("rimName"));
    cai.setMetSource(metSource);

    String mand = rs.getString("mandatory");
    cai.setMandatory(mand != null && mand.equals("M"));
    cai.setConformance(Conformance.create(rs.getString("conformance")));

    String cmetId = StringUtils.mapCmetHmdToMessageType(metName);
    if (cmetId == null)
    {
      throw new DatabaseLoaderException(context.getMessageId(),
        context.getRow(), "Cannot map CMET ID: " + metName);
    }
    cai.setCmetId(cmetId);

    context.getCurrentCloneClass().addAssociation(cai);
  }

  //-------------------------------------------------------------------------
  /**
   * Retrieves all message type IDs present in the repository database.
   *
   * @throws DatabaseLoaderException  if loading fails
   */
  /*default*/ String[] getAllMessageTypes() throws DatabaseLoaderException
  {
    final String sql = "SELECT msgeIdentifier FROM vMessageType " +
      "WHERE isCommon = False ORDER BY msgeIdentifier";

    Statement stmt = null;
    ResultSet rs = null;
    ArrayList/*<String>*/ result = new ArrayList();
    try
    {
      openConnection();

      stmt = conn_.createStatement();

      for (rs = stmt.executeQuery(sql); rs.next(); )
      {
        String mt = rs.getString(1);
        if (mt != null) result.add(mt);
      }

      return (String[])result.toArray(new String[result.size()]);
    }
    catch (SQLException ex)
    {
      throw new DatabaseLoaderException(ex);
    }
    finally
    {
      if (rs != null)
      {
        try
        {
          try
          {
            rs.close();
          }
          finally
          {
            if (stmt != null) stmt.close();
          }
        }
        catch (SQLException ex)
        {
          throw new DatabaseLoaderException(ex);
        }
      }
    }
  }

  //-------------------------------------------------------------------------
  /**
   * Runs a test: load a specified message type from a specified ODBC
   * datasource and print it to <code>System.out</code>.  See also
   * {@link AccessMessageTypeLoaderTest}
   * for more extensive and formalized JUnit tests.
   *
   * @param args  command line arguments
   */
  public static void main(String[] args)
  {
    System.out.println("Access Message Type Loader, v.0.1.5, 2003-11-07");

    if (args.length < 3)
    {
      System.out.println("Usage: java org.hl7.meta.impl.AccessMessageTypeLoader " +
        "<odbc datasource> <CMET odbc datasource> <message type id>");
      System.exit(1);
    }

    AccessMessageTypeLoader mtl = null;
    try
    {
      mtl = new AccessMessageTypeLoader(args[0], args[1]);
      MessageType mt = mtl.loadMessageType(args[2]);

      System.out.println();
      System.out.println(mt);
    }
    catch (Throwable t)
    {
      t.printStackTrace();
    }
    finally
    {
      if (mtl != null) mtl.close();
    }
  }
}
