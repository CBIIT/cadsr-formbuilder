package gov.nih.nci.ncicb.cadsr.dao.impl;

import gov.nih.nci.cadsr.domain.Form;
import gov.nih.nci.cadsr.domain.ReferenceDocument;
import gov.nih.nci.ncicb.cadsr.dao.DataAccessException;
import gov.nih.nci.ncicb.cadsr.dao.DomainObjectFactory;
import gov.nih.nci.ncicb.cadsr.dto.ReferenceDocumentAttachment;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocator;

import gov.nih.nci.system.applicationservice.ApplicationService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.io.StringReader;

import java.io.StringWriter;

import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import oracle.jdbc.OracleConnection;

import oracle.sql.BLOB;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultReader;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlQuery;
import org.springframework.jdbc.object.SqlUpdate;
import org.springframework.jdbc.object.StoredProcedure;
/**
 *   Abstract DAO using the caDSR API for querying the caDSR database.
 *   It also defines common methods used be the DAOs.
 */
public abstract class CaDSRApiDAOImpl {

    protected DataSource dataSource;
    protected ApplicationService appService;
    protected DomainObjectFactory domainObjectFactory;
    protected GUIDGenerator guidGenerator =null;
    protected String oid = null;
    private static final String GUID_GENERATOR_FUNC="admincomponent_crud.cmr_guid";
    protected static Logger logger = LogManager.getLogger(CaDSRApiDAOImpl.class);

    /**
     * Generate a new caDSR GUID.
     * @return GUID
     * @throws DataAccessException
     */
    public String getGUID() throws DataAccessException 
    {
      try 
      {
        if (guidGenerator == null){
            guidGenerator = new GUIDGenerator(dataSource);
        }
        return guidGenerator.getGUID();
      }
      catch(Exception e) {
          logger.error("Error getting GUID.",e);
          throw new DataAccessException("Error getting GUID.",e);
      }
    }
    /**
     * Return an eDCI HL7 GUID which is of the form
     * ^root^extension where root is the caDSR oid
     * @param extension
     * @return
     * @throws DataAccessException
     */
    public String geteDCIGUID(String extension) throws DataAccessException
    {
         return "^"+getCaDSROid()+"^"+extension;
    }
    /**
     * Gets the caDSR OID from the database. 
     * @return caDSR oid
     * @throws DataAccessException
     */
    public String getCaDSROid() throws DataAccessException{
      try{
         if (oid == null)
         {
            CaDSROidLoader caDSROidLoader = new CaDSROidLoader(dataSource);
            oid = (String)caDSROidLoader.getOid();
         }
        return oid;
      }
      catch(Exception e){
          logger.error("Error quering oid.",e);
          throw new DataAccessException("Error quering oid.",e);
      }
    }    
    




    /**
     * Inner class to get global unique identifier
     */
    protected class GUIDGenerator extends StoredProcedure {
      public GUIDGenerator(DataSource ds) {
        super(ds, GUID_GENERATOR_FUNC );
        setFunction(true);
        declareParameter(new SqlOutParameter("returnValue", Types.VARCHAR));
        compile();
      }
      
      public String getGUID() {
          Map in = new HashMap();
          Map out = execute(in);
          String retValue = (String) out.get("returnValue");

          return retValue;
      }
    }
    
       
    /**
     * Inner class to get caDSR OID
     */
    protected class CaDSROidLoader extends  MappingSqlQuery
    {
        private String oid = ""; 
        protected CaDSROidLoader(DataSource ds) {
             String stmt = "select name,ra_ind,rai\n" + 
                          "from organizations\n" + 
                          "where ra_ind = 'Yes'\n"+
                          " and name = 'NCI Cancer Data Standard Repository'";
             setDataSource(ds);
             setSql(stmt);
             compile();
        }
        
        protected Object mapRow(ResultSet rs, int rowNumber) throws SQLException 
        {
           oid = rs.getString("rai");
           return oid;
        }       
        
        protected String getOid(){
            execute();
            return oid.trim();
        }
    }    
    
    /**
     * This inner class is used to create a ReferenceDocument. If a GUID for
     * the ReferenceDocument is not supplied it creates one.
     */
    protected class ReferenceDocumentCreator extends SqlUpdate {
        protected ReferenceDocumentCreator(DataSource ds) {
            String refDocInsertSql =
              " INSERT INTO reference_documents " +
              " (rd_idseq, name, dctl_name, ac_idseq, doc_text, url, " +
              " display_order, conte_idseq, created_by ) " +
              " VALUES " + " (?, ?, ?, ?, ?,?, ?, ?,?) ";
              setDataSource(ds);
              setSql(refDocInsertSql);
              declareParameter(new SqlParameter("rd_idseq", Types.VARCHAR));
              declareParameter(new SqlParameter("name", Types.VARCHAR));
              declareParameter(new SqlParameter("dctl_name", Types.VARCHAR));
              declareParameter(new SqlParameter("ac_idseq", Types.VARCHAR));
              declareParameter(new SqlParameter("doc_text", Types.VARCHAR));
              declareParameter(new SqlParameter("url", Types.VARCHAR));
              declareParameter(new SqlParameter("display_order", Types.INTEGER));
              declareParameter(new SqlParameter("conte_idseq", Types.VARCHAR));
              declareParameter(new SqlParameter("created_by", Types.VARCHAR));
              compile();
        }
        
        protected ReferenceDocument createReferenceDocument(ReferenceDocument refDoc, String acIdSeq) throws DataAccessException
        {
          if (refDoc.getId() == null) 
          {//Create a new GUID
              refDoc.setId(getGUID());
          }
          Object[] parameters =  new Object[] {refDoc.getId()
                                       ,refDoc.getName()
                                       ,refDoc.getType()
                                       ,acIdSeq
                                       ,refDoc.getDoctext()
                                       ,refDoc.getURL()
                                       ,refDoc.getDisplayOrder()
                                       ,refDoc.getContext().getId()
                                       ,refDoc.getCreatedBy()};

          int res = update(parameters);
          ReferenceDocument refDocInserted = null;
          if (res == 1) {
               refDocInserted = refDoc;
          }
          return refDocInserted;
        }
    }
    
    /**
     * This inner class queries and writes to the Blob. 
     */
    protected class StoreBlob extends MappingSqlQuery {
        protected StoreBlob(DataSource ds) {
             String storeBlobSql =
                    " select blob_content" +
                    "   from reference_blobs "+
                    "  where name = ?";
              setDataSource(ds);
              setSql(storeBlobSql);
              declareParameter(new SqlParameter("name", Types.VARCHAR));
              compile();
        }
        
        protected void storeBlob(ReferenceDocumentAttachment rda) throws DataAccessException {
            try {
                Object[] parameters = new Object[]{rda.getName()};
                List blobs = execute(parameters);
                BLOB blob = (BLOB)blobs.get(0);
                blob.open(rda.getAttachment().length());
                OutputStream os = blob.getBinaryOutputStream();
                os.write(rda.getAttachment().getBytes());
                os.close();
                blob.close();
            }
            catch(Exception e) {
                logger.error("Error querying blob attachment.",e);
                throw new DataAccessException("Error querying blob attachment.",e);
            }
        }

        protected Object mapRow(ResultSet resultSet, int i) throws SQLException {
            return resultSet.getBlob("blob_content");
        }
    }
    /**
     * Inner class to query a ReferenceDocumentAttachment, using JDBC from
     * caDSR. This object is not available for query from caDSR API.
     */
    protected class QueryRefDocAttachment extends MappingSqlQuery {
        protected QueryRefDocAttachment(DataSource ds) {
            String query = "select name,mime_type,doc_size," +
                          " dad_charset,content_type,blob_content, created_by,date_created " +
                           " from reference_blobs" +
                           " where name=?";
            setDataSource(ds);
            setSql(query);
            declareParameter(new SqlParameter("name",Types.VARCHAR));
            compile();
        }
        
        protected ReferenceDocumentAttachment query(String name) throws DataAccessException {
            Object[] parameters = new Object[]{name};
            List list = execute(parameters);
            return (ReferenceDocumentAttachment)list.get(0);
        }
        
        protected Object mapRow(ResultSet resultSet, int i) throws SQLException 
        {
            ReferenceDocumentAttachment rda = new ReferenceDocumentAttachment();
            rda.setName(resultSet.getString("name"));
            rda.setMimeType(resultSet.getString("mime_type"));
            rda.setDocSize(new Long(resultSet.getLong("doc_size")));
            rda.setDadCharset(resultSet.getString("dad_charset"));
            rda.setContentType(resultSet.getString("content_type"));
            rda.setCreatedBy(resultSet.getString("created_by"));
            rda.setCreatedDate(resultSet.getDate("date_created"));
            try {
              Blob blob = resultSet.getBlob("blob_content");
              byte[] b = new byte[4096];
              InputStream is = blob.getBinaryStream();
              StringBuffer sb = new StringBuffer(200);
              for (int n; (n=is.read(b))!= -1;) 
              {
                 byte[] b1= new byte[n];
                 for (int j=0;j<n;j++) b1[j] =b[j];
                 sb.append(new String(b1));
              }
              rda.setAttachment(sb.toString());
              is.close();
            }
            catch(IOException ie)
            { 
               logger.error("Error reading blob.",ie);
               throw new SQLException("IOException reading blob.");
            }
            return rda;
        }
    }

    /**
     * Inner class to create a ReferenceDocument attachment.
     * This creates a database record in the REFERENCE_BLOBS
     * table with an empty blob.
     */
    protected class RefDocAttachmentCreator extends SqlUpdate {
        protected StoreBlob storeBlob;
        protected RefDocAttachmentCreator (DataSource ds) 
        {
            String refDocAttachInsertSql =
                 " INSERT INTO reference_blobs " +
                 " (rd_idseq, name, mime_type, doc_size,dad_charset,last_updated," +
                 " content_type, created_by, blob_content ) " +
                 " VALUES " + " (?, ?, ?, ?, ?,?, ?, ?, ?) ";
            setDataSource(ds);
            setSql(refDocAttachInsertSql);
            storeBlob = new StoreBlob(ds);
            declareParameter(new SqlParameter("rd_idseq", Types.VARCHAR));
            declareParameter(new SqlParameter("name", Types.VARCHAR));
            declareParameter(new SqlParameter("mime_type", Types.VARCHAR));
            declareParameter(new SqlParameter("doc_size", Types.INTEGER));
            declareParameter(new SqlParameter("dad_charset", Types.VARCHAR));
            declareParameter(new SqlParameter("last_updated", Types.DATE));
            declareParameter(new SqlParameter("content_type", Types.VARCHAR));
            declareParameter(new SqlParameter("created_by", Types.VARCHAR));
            declareParameter(new SqlParameter("blob_content", Types.BLOB));
            compile();
        }
        
        protected int createRefDocAttachment(ReferenceDocumentAttachment refDocAttachment) throws DataAccessException 
        {
           try
           {
             Object[] parameters = new Object[]{refDocAttachment.getReferenceDocument().getId()
                                               ,refDocAttachment.getName()
                                               ,refDocAttachment.getMimeType()
                                               ,refDocAttachment.getDocSize()
                                               ,refDocAttachment.getDadCharset()
                                               ,refDocAttachment.getLastUpdated()
                                               ,refDocAttachment.getContentType()
                                               ,refDocAttachment.getCreatedBy()
                                               ,BLOB.empty_lob()};
               int res = update(parameters);
               storeBlob.storeBlob(refDocAttachment);
               return res;
           }
           catch (Exception e) {
               logger.error("Error creating reference document attachement.",e);
               throw new DataAccessException("Error creating reference document attachement.",e);
           }
       }
    }
    
    public void setDomainObjectFactory(DomainObjectFactory domainObjectFactory) {
        this.domainObjectFactory = domainObjectFactory;
    }

    public DomainObjectFactory getDomainObjectFactory() {
        return domainObjectFactory;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setAppService(ApplicationService appService) {
        this.appService = appService;
    }

    public ApplicationService getAppService() {
        return appService;
    }
    
    
}
