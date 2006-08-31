package gov.nih.nci.ncicb.cadsr.dao.impl;

import gov.nih.nci.ncicb.cadsr.dao.DataAccessException;
import gov.nih.nci.ncicb.cadsr.dao.DomainObjectFactory;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocator;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.StoredProcedure;

public abstract class CaDSRApiDAOImpl {

    protected ServiceLocator serviceLocator;
    protected DomainObjectFactory domainObjectFactory;
    protected GUIDGenerator guidGenerator =null;
    protected String oid = null;
    private static final String GUID_GENERATOR_FUNC="admincomponent_crud.cmr_guid";
    protected static Logger logger = LogManager.getLogger(CaDSRApiDAOImpl.class);

    public void setServiceLocator(ServiceLocator serviceLocator) {
        this.serviceLocator = serviceLocator;
    }

    public ServiceLocator getServiceLocator() {
        return serviceLocator;
    }
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
            DataSource dataSource = serviceLocator.getCaDSRDataSource();
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
            CaDSROidLoader caDSROidLoader = new CaDSROidLoader(serviceLocator.getCaDSRDataSource());
            oid = (String)caDSROidLoader.getOid();
         }
        return oid;
      }
      catch(Exception e){
          logger.error("Error quering oid.",e);
          throw new DataAccessException("Error quering oid.",e);
      }
    }    

    public void setDomainObjectFactory(DomainObjectFactory domainObjectFactory) {
        this.domainObjectFactory = domainObjectFactory;
    }

    public DomainObjectFactory getDomainObjectFactory() {
        return domainObjectFactory;
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
    
}
