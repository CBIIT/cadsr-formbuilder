package gov.nih.nci.ncicb.cadsr.servicelocator;

import gov.nih.nci.ncicb.cadsr.dao.EDCIDAOFactory;
import gov.nih.nci.ncicb.cadsr.service.GenerateMessageService;
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

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

/**
 * ServiceLocator used by a client of the software to get instances of
 *  - generateMessageService
 */
public class ServiceLocator {

    private static Logger logger = LogManager.getLogger(ServiceLocator.class);
    private static final String springBeanFile = "beans.xml";
    private static BeanFactory beanFactory = null;
    static {
        ClassPathResource resource = new ClassPathResource("beans.xml");
        beanFactory = new XmlBeanFactory(resource);
    }
    
    public  ServiceLocator() 
    {
    }
    
    
    public GenerateMessageService getGenerateMessageService() throws ServiceLocatorException {
         return (GenerateMessageService)beanFactory.getBean("generateMessageService");
    }

}
