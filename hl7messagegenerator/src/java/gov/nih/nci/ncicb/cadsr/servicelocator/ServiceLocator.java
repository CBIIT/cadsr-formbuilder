package gov.nih.nci.ncicb.cadsr.servicelocator;

import gov.nih.nci.ncicb.cadsr.dao.EDCIDAOFactory;
import gov.nih.nci.system.applicationservice.ApplicationService;
import gov.nih.nci.system.applicationservice.ApplicationServiceProvider;

import java.util.HashMap;
import java.util.Map;

import javax.naming.InitialContext;

import javax.naming.NamingException;

import javax.sql.DataSource;

import oracle.jdbc.pool.OracleDataSource;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
/**
 * ServiceLocator used to get external resources:
 * - caDSR API ApplicationService.
 * - caDSR DataSource for direct JDBC access.
 * - generateMessageService
 */
public class ServiceLocator {
    private  static final String CADSR_DATASOURCE = "cadsr_datasource";
    private  static final String CADSR_PUBLIC_API = "cadsr_public_api";
    private  String caCOREUrl;
    private  String caDSRDataSourceName;
    private  String caDSRPassword;
    private  String caDSRUserName;
    private  String caDSRUrl;
    private  Map cache = new HashMap();
    private static Logger logger = LogManager.getLogger(ServiceLocator.class);
    
    public  ServiceLocator() 
    {
    }
    
    /**
     * Get the caDSR API applicationService.
     * @return caDSR Public API ApplicationService
     * @throws ServiceLocatorException
     */
    public ApplicationService getCaDSRPublicApiService() throws ServiceLocatorException {
       ApplicationService appService = (ApplicationService)cache.get(CADSR_PUBLIC_API);
       try
       {
          if (appService == null)
          {
             appService = ApplicationServiceProvider.getRemoteInstance(getCaCOREUrl());
             cache.put(CADSR_PUBLIC_API, appService);
          }
       }
       catch (Exception e){
           throw new ServiceLocatorException("Error getting application service", e);
       }
        return appService;
    }
    /**
     * Returns the caDSR dataSource.
     * @return
     * @throws ServiceLocatorException
     */
    public DataSource getCaDSRDataSource() throws ServiceLocatorException {
       DataSource dataSource = (DataSource)cache.get(CADSR_DATASOURCE);
       try{
          if (dataSource == null)
          {     //Try to get the dataSource from a JNDI lookup.
                try {
                  InitialContext initialContext = new InitialContext();
                  dataSource = (DataSource)initialContext.lookup(caDSRDataSourceName);
                }
                catch(NamingException ne){
                    logger.warn("Error getting InitialContext.Trying to create the DataSource directly ..", ne);
                    //Create a new DataSource from the properties file.
                    dataSource = getOracleDataSource();
                }
                cache.put("CADSR_DATASOURCE", dataSource);
          }
       }
       catch(Exception e){
           logger.error("Error creating the DataSource",e);
           throw new ServiceLocatorException("Error creating the DataSource",e);
       }
       return dataSource;
    }
    /**
     * Creates a dataSource from properties file.
     * @return
     * @throws ServiceLocatorException
     */
    private DataSource getOracleDataSource() throws ServiceLocatorException{
      try {
        OracleDataSource ods = new OracleDataSource();
        if (((caDSRUrl == null)||(caDSRUrl.equals("")))&&
            ((caDSRPassword == null)||(caDSRPassword.equals("")))&&
            ((caDSRUserName == null)||(caDSRUserName.equals("")))) {
                throw new Exception("Not enough information to create datasource.");
            }
        ods.setURL(caDSRUrl);
        ods.setPassword(caDSRPassword);
        ods.setUser(caDSRUserName);
        return (DataSource)ods;
      }
      catch (Exception e){
          logger.error("Error creating oracle datasource.", e);
          throw new ServiceLocatorException("Error creating oracle datasource.", e);
      }
    }

    public void setCaCOREUrl(String caCOREUrl) {
        this.caCOREUrl = caCOREUrl;
    }

    public String getCaCOREUrl() {
        return caCOREUrl;
    }

    public void setCaDSRDataSourceName(String caDSRDataSourceName) {
        this.caDSRDataSourceName = caDSRDataSourceName;
    }

    public String getCaDSRDataSourceName() {
        return caDSRDataSourceName;
    }

    public void setCaDSRPassword(String caDSRPassword) {
        this.caDSRPassword = caDSRPassword;
    }

    public String getCaDSRPassword() {
        return caDSRPassword;
    }

    public void setCaDSRUserName(String caDSRUserName) {
        this.caDSRUserName = caDSRUserName;
    }

    public String getCaDSRUserName() {
        return caDSRUserName;
    }

    public void setCaDSRUrl(String caDSRUrl) {
        this.caDSRUrl = caDSRUrl;
    }

    public String getCaDSRUrl() {
        return caDSRUrl;
    }
}
