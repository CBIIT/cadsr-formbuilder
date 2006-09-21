package gov.nih.nci.ncicb.cadsr.dao.impl;

import gov.nih.nci.ncicb.cadsr.dao.DataAccessException;
import gov.nih.nci.ncicb.cadsr.dao.EDCIDAOFactory;
import gov.nih.nci.ncicb.cadsr.dao.GlobalDefinitionsDAO;

import gov.nih.nci.ncicb.cadsr.dao.InstrumentDAO;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
/**
 * Implements the EDCIDAOFactory using the spring framework BeanFactory.
 */
public class EDCIDAOFactoryImpl implements EDCIDAOFactory, BeanFactoryAware {
    BeanFactory beanFactory = null;
    public EDCIDAOFactoryImpl() {
        //ClassPathResource resource = new ClassPathResource("beans.xml");
        //beanFactory = new XmlBeanFactory(resource);
    }
    
   public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
       this.beanFactory = beanFactory;
   }
    
    public GlobalDefinitionsDAO getGlobalDefinitionsDAO() throws DataAccessException {
        try {
            
            GlobalDefinitionsDAO globalDefinitionsDAO = (GlobalDefinitionsDAO)beanFactory.getBean("globalDefinitionsDAO");
            return globalDefinitionsDAO;
        }
        catch(Exception e){
            throw new DataAccessException("Error creating GlobalDefinitionsDAO",e);
        }
    }
    
    public InstrumentDAO getInstrumentDAO() throws DataAccessException {
        try {
            
            InstrumentDAO instrumentDAO = (InstrumentDAO)beanFactory.getBean("instrumentDAO");
            return instrumentDAO;
        }
        catch(Exception e){
            throw new DataAccessException("Error creating InstrumentDAO",e);
        }
    }    
    
   
}
