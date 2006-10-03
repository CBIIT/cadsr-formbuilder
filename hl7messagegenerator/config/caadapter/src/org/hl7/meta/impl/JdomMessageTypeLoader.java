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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import org.hl7.meta.Cardinality;
import org.hl7.meta.CodingStrength;
import org.hl7.meta.Conformance;
import org.hl7.meta.DatatypeMetadataFactory;
import org.hl7.meta.LoaderException;
import org.hl7.meta.MessageType;
import org.hl7.meta.MetSource;
import org.hl7.meta.UnknownDatatypeException;
import org.hl7.meta.util.ElementNotFoundException;
import org.hl7.meta.util.JdomUtils;
import org.hl7.meta.util.MultipleElementsFoundException;
import org.hl7.meta.util.StringUtils;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

/**
 * An implementation of <code>MessageTypeLoader</code> that
 * loads message type metadata from an XML extract from HL7 v3 repository
 * database generated via the RoseTree tool.
 * XML parser used is JDOM on top of SAX.
 *
 * @author Skirmantas Kligys (skirmis@hotmail.com), Eric Chen
 */
public class JdomMessageTypeLoader extends MessageTypeLoaderImpl
{
  //-------------------------------------------------------------------------
  /** Input file with XML extract. */
  private final File inputFile_;

  /** RIM association lookup table. */
  private final RimAssociationLookups lookups_;

  /**
   * Factory for parsing datatype literals.
   */
  private DatatypeMetadataFactory dmFactory_ =
    DatatypeMetadataFactoryImpl.instance();

  /** CMET cache for on-demand cached loading. */
  private CmetCacheImpl cmetCache_;

  public static final String GENERALIZES = "generalizes";

    //-------------------------------------------------------------------------
  /**
   * Constructor.  Also checks that file referenced is readable.
   *
   * @param inputFile  file name for the XML extract file
   * @param rimFile  file name for rim.xml
   *
   * @throws XmlLoaderException  if file does not exist or is not readable
   */
  public JdomMessageTypeLoader(String inputFile, String rimFile)
    throws XmlLoaderException
  {
    inputFile_ = new File(inputFile);

    if (!inputFile_.exists())
    {
      throw new XmlLoaderException(new FileNotFoundException(inputFile));
    }
    if (!inputFile_.canRead())
    {
      throw new XmlLoaderException(new IOException("Cannot read: " +
        inputFile));
    }

    try
    {
      RimAssociationLookupsParser ralp = new RimAssociationLookupsParser();
      lookups_ = ralp.parse(rimFile);
    }
    catch (IOException ex)
    {
      throw new XmlLoaderException(ex);
    }
    catch (ParserConfigurationException ex)
    {
      throw new XmlLoaderException(ex);
    }
    catch (SAXException ex)
    {
      throw new XmlLoaderException(ex);
    }

    cmetCache_ = new CmetCacheImpl(this);
  }

  //-------------------------------------------------------------------------
  /**
   * An internal class that keeps track of the current position in the
   * document (current element, path) as well as some universally used
   * information (XML ID for IDREFs to a message type).
   */
  private static class LoaderContext
  {
    //.......................................................................
    /**
     * A simple read only data structure to hold a tag and a JDOM Element;
     * to be stacked inside LoaderContext.
     */
    private static class TaggedElement
    {
      //.....................................................................
      private final String tag_;
      private final Element element_;

      //.....................................................................
      /*default*/ TaggedElement(String tag, Element element)
      {
        tag_ = tag;
        element_ = element;
      }

      //.....................................................................
      /*default*/ String getTag() { return tag_; }
      /*default*/ Element getElement() { return element_; }
    }

    //.......................................................................
    private final MessageTypeImpl mti_;
    private String messageXmlId_;
    private Stack/*<TaggedElement>*/ elements_ =
      new Stack/*<TaggedElement>*/();

    //.......................................................................
    /*default*/ LoaderContext(MessageTypeImpl mti)
    {
      if (mti == null) throw new NullPointerException();
      mti_ = mti;
    }

    //.......................................................................
    /*default*/ MessageTypeImpl getMessageType() { return mti_; }
    /*default*/ String getMessageId() { return mti_.getId(); }

    //.......................................................................
    /*default*/ String getMessageXmlId() { return messageXmlId_; }
    /*default*/ void setMessageXmlId(String messageXmlId)
    { messageXmlId_ = messageXmlId; }

    //.......................................................................
    /*default*/ void pushElement(String tag, Element element)
    {
      elements_.push(new TaggedElement(tag, element));
    }

    //.......................................................................
    /*default*/ void popElement() { elements_.pop(); }

    //.......................................................................
    /*default*/ Element getCurrentElement()
    {
      TaggedElement te = (TaggedElement)elements_.peek();
      return te.getElement();
    }

    //.......................................................................
    /*default*/ String getCurrentPath()
    {
      if (elements_.isEmpty()) return "/";

      StringBuffer sb = new StringBuffer();
      for (Iterator/*<TaggedElement>*/ it = elements_.iterator();
        it.hasNext(); )
      {
        TaggedElement te = (TaggedElement)it.next();

        sb.append('/');
        sb.append(te.getTag());
      }
      return sb.toString();
    }
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
    MessageTypeImpl mti = new MessageTypeImpl(id);

    try
    {
      SAXBuilder builder = new SAXBuilder();
      Document doc = builder.build(inputFile_);

      LoaderContext context = new LoaderContext(mti);
      Element elRoot = getRootElement(context, doc);
      context.pushElement("HMD", elRoot);
      processRoot(context, mti);
      context.popElement();
    }
    catch (JDOMException ex)
    {
      throw new XmlLoaderException(ex);
    } catch (IOException e) {
      throw new XmlLoaderException(e);
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
    // Find the corresponding CMET file.
    File cmetFile = findCmetFile(inputFile_, id);

    MessageTypeImpl mti = new MessageTypeImpl(id);

    try
    {
      SAXBuilder builder = new SAXBuilder();
      Document doc = builder.build(cmetFile);

      LoaderContext context = new LoaderContext(mti);
      Element elRoot = getRootElement(context, doc);
      context.pushElement("HMD", elRoot);
      processRoot(context, mti);
      context.popElement();
    }
    catch (JDOMException ex)
    {
      throw new XmlLoaderException(ex);
    } catch (IOException e) {
      throw new XmlLoaderException(e);
    }

    postProcess(mti);

    return mti;
  }

  //-------------------------------------------------------------------------
  /**
   * Returns a file where CMET metadata is held, given a file
   * for the message type and CMET ID.  Assumes that it is located in the
   * same directory.
   *
   * @param mtFile  File where message type metadata is located
   * @param id      CMET ID
   * @return  reference to an existing readable file where CMET
   *            metadata is loacated
   * @throws XmlLoaderException  if CMET file name mapping fails, or
   *     file is unreadable
   */
  private File findCmetFile(File mtFile, String id)
    throws XmlLoaderException
  {
    String cmetFilename = StringUtils.mapCmetMessagetypeToHmd(id);
    if (cmetFilename == null)
    {
      throw new XmlLoaderException("Cannot map CMET message type " + id +
        " to its HMD");
    }

    File f = new File(mtFile.getParent(), cmetFilename + ".hmd");

    if (!f.exists())
    {
      throw new XmlLoaderException(new FileNotFoundException(f.getPath()));
    }
    if (!f.canRead())
    {
      throw new XmlLoaderException(new IOException("Cannot read: " +
        f.getPath()));
    }

    return f;
  }

  //-------------------------------------------------------------------------
  /**
   * A wrapper for
   * {@link org.hl7.meta.util.JdomUtils#getUniqueChild(org.jdom.Element,
   *   String, String) JDomUtils.getUniqueChild()}.
   * Converts from
   * {@link org.hl7.meta.util.ElementNotFoundException ElementNotFoundException},
   * {@link org.hl7.meta.util.MultipleElementsFoundException MultipleElementsFoundException}
   * into
   * <code>XmlLoaderException</code> and employs <code>LoaderContext</code>.
   *
   * @return  child element found
   * @throws XmlLoaderException  if a unique child element cannot be found
   */
  private Element getUniqueChildElement(LoaderContext context, String tag)
    throws XmlLoaderException
  {
    try
    {
      return JdomUtils.getUniqueChild(context.getCurrentElement(),
        tag, context.getCurrentPath());
    }
    catch (ElementNotFoundException ex)
    {
      throw new XmlLoaderException(context.getMessageId(),
        context.getCurrentPath(), "Element not found: " + ex.getMessage());
    }
    catch (MultipleElementsFoundException ex)
    {
      throw new XmlLoaderException(context.getMessageId(),
        context.getCurrentPath(), "Multiple elements found: " + ex.getMessage());
    }
  }

  //-------------------------------------------------------------------------
  /**
   * A wrapper for
   * {@link org.hl7.meta.util.JdomUtils#getOptionalChild(org.jdom.Element,
   *   String, String) JDomUtils.getOptionalChild()}.
   * Converts from
   * {@link org.hl7.meta.util.MultipleElementsFoundException MultipleElementsFoundException}
   * into
   * <code>XmlLoaderException</code> and employs <code>LoaderContext</code>.
   *
   * @return  child element found
   * @throws XmlLoaderException  if multiple child elements were found
   */
  private Element getOptionalChildElement(LoaderContext context, String tag)
    throws XmlLoaderException
  {
    try
    {
      return JdomUtils.getOptionalChild(context.getCurrentElement(),
        tag, context.getCurrentPath());
    }
    catch (MultipleElementsFoundException ex)
    {
      throw new XmlLoaderException(context.getMessageId(),
        context.getCurrentPath(), "Multiple elements found: " + ex.getMessage());
    }
  }

  //-------------------------------------------------------------------------
  /**
   * A wrapper for
   * {@link org.hl7.meta.util.JdomUtils#getUniqueChildByAttribute(org.jdom.Element,
   *   String, String, String, String) JDomUtils.getUniqueChildByAttribute()}.
   * Converts from
   * {@link org.hl7.meta.util.ElementNotFoundException ElementNotFoundException},
   * {@link org.hl7.meta.util.MultipleElementsFoundException MultipleElementsFoundException}
   * into
   * <code>XmlLoaderException</code> and employs <code>LoaderContext</code>.
   *
   * @return  child element found
   * @throws XmlLoaderException  if a unique child element cannot be found
   */
  private Element getUniqueChildElementByAttribute(LoaderContext context,
    String tag, String attributeName, String attributeValue)
    throws XmlLoaderException
  {
    try
    {
      return JdomUtils.getUniqueChildByAttribute(context.getCurrentElement(),
        tag, attributeName, attributeValue, context.getCurrentPath());
    }
    catch (ElementNotFoundException ex)
    {
      throw new XmlLoaderException(context.getMessageId(),
        context.getCurrentPath(), "Element not found: " + ex.getMessage());
    }
    catch (MultipleElementsFoundException ex)
    {
      throw new XmlLoaderException(context.getMessageId(),
        context.getCurrentPath(), "Multiple elements found: " + ex.getMessage());
    }
  }

  //-------------------------------------------------------------------------
  /**
   * A wrapper for <code>org.jdom.Document.getRootElement()</code>.
   *
   * @return  the root element
   * @throws XmlLoaderException  if the root element QN is not "HMD"
   */
  private Element getRootElement(LoaderContext context, Document doc)
    throws XmlLoaderException
  {
    Element elRoot = doc.getRootElement();
    if (!elRoot.getQualifiedName().equals("HMD"))
    {
      throw new XmlLoaderException(context.getMessageId(),
        context.getCurrentPath(), "Root element must be HMD");
    }
    return elRoot;
  }

  //-------------------------------------------------------------------------
  /**
   * Processes the JDOM tree starting at the root.  Stores temporary
   * information in <code>LoaderContext</code>.
   *
   * @param context  context for temporary information
   * @param mti  message type metadata object under construction
   * @throws XmlLoaderException  if needed data cannot be found in JDOM tree
   */
  private void processRoot(LoaderContext context, MessageTypeImpl mti)
    throws XmlLoaderException
  {
    // Problem: message type names are not present in *.hmd, only HMD names.
    mti.setName(context.getCurrentElement().getAttributeValue("Name"));

    Element elMessage = getUniqueChildElementByAttribute(context, "Message",
      "Identifier", context.getMessageId());

		context.pushElement("Message[@Identifier='" + context.getMessageId() +
      "']", elMessage);
    context.setMessageXmlId(elMessage.getAttributeValue("ID"));
    context.popElement();

    Element elClass = getUniqueChildElement(context, "Class");
    context.pushElement("Class", elClass);
    processClass(context, mti);
    context.popElement();
  }

  //-------------------------------------------------------------------------
  /**
   * A silly little helper class that allows a method to return both
   * a name and a choice set at the same time.
   */
  private static class NameAndChoiceSet
  {
    //.......................................................................
    private final String name_;
    private final String[] choiceSet_;

    //.......................................................................
    /*default*/ NameAndChoiceSet(String name, String[] choiceSet)
    {
      if (name == null) throw new NullPointerException();
      name_ = name;
      choiceSet_ = choiceSet;
    }

    //.......................................................................
    /*default*/ String getName() { return name_; }
    /*default*/ String[] getChoiceSet() { return choiceSet_; }
  }

  //-------------------------------------------------------------------------
  /**
   * Processes the JDOM subtree starting at the &lt;Class&gt; element
   * (corresponding to the root class).  Stores temporary information in
   * <code>LoaderContext</code>.
   *
   * @param context  context for temporary information
   * @param mti  message type metadata object under construction
   * @throws XmlLoaderException  if needed data cannot be found in JDOM tree
   */
  private void processClass(LoaderContext context, MessageTypeImpl mti)
    throws XmlLoaderException
  {
    Element elClass = context.getCurrentElement();
    CloneClassImpl cci = new CloneClassImpl(mti);

    cci.setRimClass(elClass.getAttributeValue("RimSource"));
    NameAndChoiceSet nac = getNameAndChoiceSet(context);
//    cci.setName(nac.getName());
    String elementName = elClass.getAttributeValue("ElementName");
    cci.setName(elementName);
    cci.setFullName(elementName);
    cci.setChoices(nac.getChoiceSet());

    mti.setRootClass(cci);

    processAttributesAndAssociations(context, cci);
  }

  //-------------------------------------------------------------------------
  /**
   * Based on current element in the context, extracts and returns name
   * and choice set of the current clone class.
   *
   * @param context  context for temporary information
   * @return  extracted information
   * @throws XmlLoaderException  if needed data cannot be found in JDOM tree
   */
  private NameAndChoiceSet getNameAndChoiceSet(LoaderContext context)
    throws XmlLoaderException
  {
    ArrayList/*<String>*/ choices = new ArrayList/*<String>*/();
    int i = 1;
    for (Iterator/*<Element>*/ it =
      context.getCurrentElement().getChildren("ofMEType").iterator();
      it.hasNext(); ++i)
    {
      Element el = (Element)it.next();

      context.pushElement("ofMEType[" + i + ']', el);
      Element elMet = getUniqueChildElement(context, "MET");
      context.pushElement("MET", elMet);
      choices.add(elMet.getAttributeValue("name"));
      context.popElement();
      context.popElement();
    }

    switch (choices.size())
    {
      case 0:
        throw new XmlLoaderException(context.getMessageId(),
          context.getCurrentPath() + "/ofMEType",
          "Element not found");

      case 1:
        return new NameAndChoiceSet((String)choices.get(0), null);

      default:
        String[] choiceSet =
          (String[])choices.toArray(new String[choices.size()]);
        return new NameAndChoiceSet((String)choices.get(0), choiceSet);
    }
  }

  //-------------------------------------------------------------------------
  /**
   * Starting with current element in context, processes its attributes
   * and associations.
   *
   * @param context  context for temporary information
   * @param cci  clone class under construction
   * @throws XmlLoaderException  if needed data cannot be found in JDOM tree
   */
  private void processAttributesAndAssociations(LoaderContext context,
    CloneClassImpl cci)
    throws XmlLoaderException
  {
    Element elParent = context.getCurrentElement();

    int i = 1;
    for (Iterator/*<Element>*/ it = elParent.getChildren("Attr").iterator();
      it.hasNext(); ++i)
    {
      Element el = (Element)it.next();

      context.pushElement("Attr[" + i + ']', el);
      processAttribute(context, cci);
      context.popElement();
    }

    i = 1;
    for (Iterator/*<Element>*/ it = elParent.getChildren("Assoc").iterator();
      it.hasNext(); ++i)
    {
      Element el = (Element)it.next();

      context.pushElement("Assoc[" + i + ']', el);
      processAssociation(context, cci);
      context.popElement();
    }
  }

  //-------------------------------------------------------------------------
  /**
   * Processes JDOM subtree corresponding to a clone class attribute.
   *
   * @param context  context for temporary information
   * @param cci  clone class under construction
   * @throws XmlLoaderException  if needed data cannot be found in JDOM tree
   */
  private void processAttribute(LoaderContext context, CloneClassImpl cci)
    throws XmlLoaderException
  {
    Element elAttribute = context.getCurrentElement();
    AttributeImpl ai = new AttributeImpl(cci);

    ai.setName(elAttribute.getAttributeValue("ElementName"));
      try
      {
          ai.setSortKey(Integer.parseInt(elAttribute.getAttributeValue("SortKey")));
      }
      catch (NumberFormatException e)
      {
          ai.setSortKey(0);
      }

      String dt = elAttribute.getAttributeValue("ofMetString");
    try
    {
      ai.setDatatype(dmFactory_.create(dt));
    }
    catch (UnknownDatatypeException ex)
    {
      throw new XmlLoaderException("Unknown datatype: " + dt, ex);
    }

    ai.setRimClass(elAttribute.getAttributeValue("RimSource"));
    ai.setRimPropertyName(elAttribute.getAttributeValue("ClassOrProperty"));

    Element elConstraints = getUniqueChildElementByAttribute(context,
      "MsgConstraints", "MessageID", context.getMessageXmlId());
    context.pushElement("MsgConstraints", elConstraints);
    processAttributeConstraints(context, ai);
    context.popElement();

    cci.addAttribute(ai);
  }

  //-------------------------------------------------------------------------
  /**
   * Processes JDOM subtree corresponding to a attribute constraints.
   *
   * @param context  context for temporary information
   * @param ai  attribute under construction
   * @throws XmlLoaderException  if needed data cannot be found in JDOM tree
   */
  private void processAttributeConstraints(LoaderContext context,
    AttributeImpl ai)
    throws XmlLoaderException
  {
    Element elConstraints = context.getCurrentElement();

    String cardinalitySpec = elConstraints.getAttributeValue("minOccurs") +
      ".." + elConstraints.getAttributeValue("maxOccurs");
    ai.setCardinality(Cardinality.create(cardinalitySpec));

    ai.setCodingStrength(CodingStrength.create(
      elConstraints.getAttributeValue("CodingStrength")));

    String mandatorySpec = elConstraints.getAttributeValue("Mandatory");
    ai.setMandatory(mandatorySpec != null && mandatorySpec.equals("true"));

    ai.setConformance(Conformance.create(elConstraints.getAttributeValue(
      "Conformance")));

    Element elDomainSpec = getOptionalChildElement(context, "DomainSpec");
    if (elDomainSpec != null)
    {
      context.pushElement("DomainSpec", elDomainSpec);
      processDomainSpec(context, ai);
      context.popElement();
    }

    Element elAnnotation = getOptionalChildElement(context, "Annotation");
    if (elAnnotation != null)
    {
      context.pushElement("Annotation", elAnnotation);
      processAnnotation(context, ai);
      context.popElement();
    }
  }

  //-------------------------------------------------------------------------
  /**
   * Processes attribute domain specification (includes fixed values and
   * vocabulary domains).
   *
   * @param context  context for temporary information
   * @param ai  attribute under construction
   */
  private void processDomainSpec(LoaderContext context, AttributeImpl ai)
  {
    String text = context.getCurrentElement().getAttributeValue("text");
    String rimTable = context.getCurrentElement().getAttributeValue("rimTable");
    FixedValuesDomains fvd = new FixedValuesDomains(text + "+" + rimTable);
    ai.setFixedValues(fvd.getFixedValues());
    ai.setDomains(fvd.getDomains());
  }

  //-------------------------------------------------------------------------
  /**
   * Processes attribute free text annotation.
   *
   * @param context  context for temporary information
   * @param ai  attribute under construction
   * @throws XmlLoaderException  if needed data cannot be found in JDOM tree
   */
  private void processAnnotation(LoaderContext context, AttributeImpl ai)
    throws XmlLoaderException
  {
    Element elP = getOptionalChildElement(context, "p");

    if (elP != null)
    {
      context.pushElement("p", elP);
      ai.setConstraint(elP.getText());
      context.popElement();
    }
  }

  //-------------------------------------------------------------------------
  /**
   * A silly little class to hold both an association's distal end name and
   * the MET source code.
   */
  private static class MetNameAndSource
  {
    //.......................................................................
    private final String metName_;
    private final MetSource metSource_;

    //.......................................................................
    /*default*/ MetNameAndSource(String name, MetSource metSource)
    {
      if (name == null || metSource == null) throw new NullPointerException();
      metName_ = name;
      metSource_ = metSource;
    }

    //.......................................................................
    /*default*/ String getMetName() { return metName_; }
    /*default*/ MetSource getMetSource() { return metSource_; }
  }

  //-------------------------------------------------------------------------
  /**
   * Processes all types of associations. Depending on <code>MetSource</code>,
   * calls
   * {@link #processCloneAssociation(JdomMessageTypeLoader.LoaderContext,
   *   CloneClassImpl, JdomMessageTypeLoader.MetNameAndSource)
   *   processCloneAssociation()}
   * or
   * {@link #processCmetAssociation(JdomMessageTypeLoader.LoaderContext,
   *   CloneClassImpl, JdomMessageTypeLoader.MetNameAndSource)
   *   processCmetAssociation()}.
   *
   * @param context  context for temporary information
   * @param cci  clone class under construction
   * @throws XmlLoaderException  if needed data cannot be found in JDOM tree
   */
  private void processAssociation(LoaderContext context, CloneClassImpl cci)
    throws XmlLoaderException
  {
    // Determine the type of association.
    MetNameAndSource mnas = getMetNameAndSource(context);
    MetSource metSource = mnas.getMetSource();

    if (metSource == MetSource.NEW)
    {
      processCloneAssociation(context, cci, mnas);
    }
    else if (metSource == MetSource.CMET)
    {
      processCmetAssociation(context, cci, mnas);
    }
    else if (metSource == MetSource.REUSED || metSource == MetSource.RECURSIVE)
    {
      if (StringUtils.matchesCmetMessagetypePattern(mnas.getMetName()) ||
        StringUtils.matchesCmetHmdPattern(mnas.getMetName()))
      {
        processCmetAssociation(context, cci, mnas);
      }
      else
      {
        processCloneAssociation(context, cci, mnas);
      }
    }
  }

  //-------------------------------------------------------------------------
  /**
   * Based on current element in the context, extracts and returns name
   * and MET source of the current clone class.
   *
   * @param context  context for temporary information
   * @return  extracted information
   * @throws XmlLoaderException  if needed data cannot be found in JDOM tree
   */
  private MetNameAndSource getMetNameAndSource(LoaderContext context)
    throws XmlLoaderException
  {
    Element elOfMetype = getUniqueChildElementByAttribute(context, "ofMEType",
      "isPrimaryRowType", "true");
    context.pushElement("ofMEType", elOfMetype);
    Element elMet = getUniqueChildElement(context, "MET");
    context.pushElement("MET", elMet);

    String name = StringUtils.removeSet(elMet.getAttributeValue("name"));

    MetSource metSource;
    try
    {
      metSource = MetSource.create(elMet.getAttributeValue("source"));
    }
    catch (IllegalArgumentException ex)
    {
      throw new XmlLoaderException(context.getMessageId(),
        context.getCurrentPath(), ex.getMessage());
    }

    context.popElement();
    context.popElement();

    return new MetNameAndSource(name, metSource);
  }

  //-------------------------------------------------------------------------
  /**
   * Processes a clone association.
   *
   * @param context  context for temporary information
   * @param cci  clone class under construction
   * @param mnas  MET name and source information
   * @throws XmlLoaderException  if needed data cannot be found in JDOM tree
   */
  private void processCloneAssociation(LoaderContext context,
    CloneClassImpl cci, MetNameAndSource mnas)
    throws XmlLoaderException
  {
    MessageTypeImpl mti = context.getMessageType();
    Element elAssoc = context.getCurrentElement();

    CloneClassAssociationImpl ccai = new CloneClassAssociationImpl(cci);

    String name = elAssoc.getAttributeValue("ElementName");
    ccai.setName(name);

    String rimDestination = elAssoc.getAttributeValue("RimDestination");
    rimDestination = StringUtils.trimSuffix(rimDestination, "_heir");
    rimDestination = StringUtils.trimSuffix(rimDestination, "Heir");
    ccai.setRimClass(rimDestination);

    // A workaround for a RoseTree bug: ClassOrProperty contains the
    // association name after renaming, unusable.
//    ccai.setRimPropertyName(elAssoc.getAttributeValue("ClassOrProperty"));
    String classOrProperty = elAssoc.getAttributeValue("ClassOrProperty");
    if (classOrProperty.equals(GENERALIZES))
    {
      ccai.setRimPropertyName(GENERALIZES);
    }
    else
    {
      String sourceClass = elAssoc.getAttributeValue("RimSource");
      String associationId = elAssoc.getAttributeValue("RimSourceID");
      ccai.setRimPropertyName(lookups_.lookupAssociation(sourceClass, associationId));
    }

    ccai.setMetSource(mnas.getMetSource());

    Element elConstraints = getUniqueChildElementByAttribute(context,
      "MsgConstraints", "MessageID", context.getMessageXmlId());
    context.pushElement("MsgConstraints", elConstraints);
    processAssociationConstraints(context, ccai);
    context.popElement();

    cci.addAssociation(ccai);

    CloneClassImpl cci2 = (CloneClassImpl)mti.lookupCloneClass(
      mnas.getMetName());
    if (cci2 == null)
    {
      if (mnas.getMetSource() != MetSource.NEW)
      {
        throw new XmlLoaderException(context.getMessageId(),
          context.getCurrentPath(), "Cannot find clone class: " + mnas.getMetName());
      }

      cci2 = new CloneClassImpl(mti);
      cci2.setName(mnas.getMetName()); //TODO: should use 'name' instead ? Cause the confusion of CloneClass's name and CloneClassAssociaton's name
      cci2.setFullName( cci.getFullName() != null ?  cci.getFullName() + "."+ name  : name);
      cci2.setRimClass(ccai.getRimClass());
      cci2.setChoices(getNameAndChoiceSet(context).getChoiceSet());
      cci2.setParent(cci);

      mti.addCloneClass(cci2);

      processAttributesAndAssociations(context, cci2);
    }
    ccai.setTarget(cci2);

    if (cci2.isAbstract()) {
        int index = name.indexOf(mnas.getMetName());
        if (index !=-1) {
            String prefix_ = name.substring(0, index);
//            System.out.println("prefix_ = " + prefix_);
            ccai.setPrefix_(prefix_);
        }
//        if (index == 0) {
//            ccai.setName(ccai.getName());
//        }
    }
  }

  //-------------------------------------------------------------------------
  /**
   * Processes a CMET association.
   *
   * @param context  context for temporary information
   * @param cci  clone class under construction
   * @param mnas  MET name and source information
   * @throws XmlLoaderException  if needed data cannot be found in JDOM tree
   */
  private void processCmetAssociation(LoaderContext context,
    CloneClassImpl cci, MetNameAndSource mnas)
    throws XmlLoaderException
  {
    Element elAssoc = context.getCurrentElement();

    CmetAssociationImpl cai = new CmetAssociationImpl(cci, cmetCache_);

    String name = elAssoc.getAttributeValue("ElementName");
    cai.setName(name);
//      cai.setFullName( cci.getFullName() != null ?
//              cci.getFullName() + "."+ name  : name);


    String rimDestination = elAssoc.getAttributeValue("RimDestination");
    rimDestination = StringUtils.trimSuffix(rimDestination, "_heir");
    rimDestination = StringUtils.trimSuffix(rimDestination, "Heir");
    cai.setRimClass(rimDestination);

    // A workaround for a RoseTree bug: ClassOrProperty contains the
    // association name after renaming, unusable.
//    ccai.setRimPropertyName(elAssoc.getAttributeValue("ClassOrProperty"));
    String classOrProperty = elAssoc.getAttributeValue("ClassOrProperty");
    if (classOrProperty.equals(GENERALIZES))
    {
      cai.setRimPropertyName(GENERALIZES);
    }
    else
    {
      String sourceClass = elAssoc.getAttributeValue("RimSource");
      String associationId = elAssoc.getAttributeValue("RimSourceID");
      cai.setRimPropertyName(lookups_.lookupAssociation(sourceClass, associationId));
    }

    cai.setMetSource(mnas.getMetSource());

    String cmetId = StringUtils.mapCmetHmdToMessageType(mnas.getMetName());
    if (cmetId == null)
    {
      throw new XmlLoaderException(context.getMessageId(),
        context.getCurrentPath(), "Cannot map CMET ID: " + mnas.getMetName());
    }
    cai.setCmetId(cmetId);

    Element elConstraints = getUniqueChildElementByAttribute(context,
      "MsgConstraints", "MessageID", context.getMessageXmlId());
    context.pushElement("MsgConstraints", elConstraints);
    processAssociationConstraints(context, cai);
    context.popElement();

    cci.addAssociation(cai);
  }

  //-------------------------------------------------------------------------
  /**
   * Processes association constraints.  These include cardinality, mandatory
   * and conformance flags.
   *
   * @param context  context for temporary information
   * @param ai  association under construction
   */
  private void processAssociationConstraints(LoaderContext context,
    AssociationImpl ai)
  {
    Element elConstraints = context.getCurrentElement();

    String cardinalitySpec = elConstraints.getAttributeValue("minOccurs") +
      ".." + elConstraints.getAttributeValue("maxOccurs");
    ai.setCardinality(Cardinality.create(cardinalitySpec));

    String mandatorySpec = elConstraints.getAttributeValue("Mandatory");
    ai.setMandatory(mandatorySpec != null && mandatorySpec.equals("true"));

    ai.setConformance(Conformance.create(elConstraints.getAttributeValue(
      "Conformance")));
  }

  //-------------------------------------------------------------------------
  /**
   * Runs a test: load a specified message type from a specified XML extract
   * file and print it to <code>System.out</code>.  See also
   * {@link JdomMessageTypeLoaderTest}
   * for more extensive and formalized JUnit tests.
   *
   * @param args  command line arguments
   */
  public static void main(String[] args)
  {
    System.out.println("JDOM XML Message Type Loader, v.0.1.5, 2002-11-19");

    if (args.length < 3)
    {
      System.out.println("Usage: java org.hl7.meta.impl.JdomMessageTypeLoader " +
        "<xml file> <message type id> <rim.xml file>");
      System.exit(1);
    }

    JdomMessageTypeLoader mtl = null;
    try
    {
      mtl = new JdomMessageTypeLoader(args[0], args[2]);
      MessageType mt = mtl.loadMessageType(args[1]);

      System.out.println();
      System.out.println(mt);
    }
    catch (Throwable t)
    {
      t.printStackTrace();
    }
  }
}
