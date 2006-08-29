package gov.nih.nci.ncicb.cadsr.dao.impl;

import gov.nih.nci.ncicb.cadsr.dao.DataAccessException;
import gov.nih.nci.ncicb.cadsr.dao.DomainObjectFactory;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocator;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.object.StoredProcedure;

public abstract class CaDSRApiDAOImpl {

    protected ServiceLocator serviceLocator;
    protected DomainObjectFactory domainObjectFactory;
    protected GUIDGenerator guidGenerator =null;
    private static final String GUID_GENERATOR_FUNC="admincomponent_crud.cmr_guid";
    protected static Logger logger = LogManager.getLogger(CaDSRApiDAOImpl.class);

    public void setServiceLocator(ServiceLocator serviceLocator) {
        this.serviceLocator = serviceLocator;
    }

    public ServiceLocator getServiceLocator() {
        return serviceLocator;
    }
    
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
    
}
