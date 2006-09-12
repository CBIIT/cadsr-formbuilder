package gov.nih.nci.ncicb.cadsr.dao;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
/**
 *  Factory for DAO objects.
 */
public interface  EDCIDAOFactory {
   /**
     * 
     * @return  GlobalDefinitions data access object
     * @throws DataAccessException
     */
    public GlobalDefinitionsDAO getGlobalDefinitionsDAO() throws DataAccessException;
    /**
     * 
     * @return Instrument data access object.
     * @throws DataAccessException
     */
    public InstrumentDAO getInstrumentDAO() throws DataAccessException;
    
    
}
