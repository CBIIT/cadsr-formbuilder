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
 * ServiceLocator used by a client of the software to get instances of 
 *  - generateMessageService
 */
public class ServiceLocator {

    private static Logger logger = LogManager.getLogger(ServiceLocator.class);
    
    public  ServiceLocator() 
    {
    }

}
