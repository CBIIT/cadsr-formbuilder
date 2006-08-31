package gov.nih.nci.ncicb.cadsr.dao;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

public interface  EDCIDAOFactory {
   
    public GlobalDefinitionsDAO getGlobalDefinitionsDAO() throws DataAccessException;
    
    public InstrumentDAO getInstrumentDAO() throws DataAccessException;
    
    
}
