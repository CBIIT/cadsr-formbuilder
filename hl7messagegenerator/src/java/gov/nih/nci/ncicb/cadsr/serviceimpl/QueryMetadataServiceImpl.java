package gov.nih.nci.ncicb.cadsr.serviceimpl;

import gov.nih.nci.cadsr.domain.Form;
import gov.nih.nci.ncicb.cadsr.dao.EDCIDAOFactory;
import gov.nih.nci.ncicb.cadsr.dao.GlobalDefinitionsDAO;
import gov.nih.nci.ncicb.cadsr.dao.InstrumentDAO;
import gov.nih.nci.ncicb.cadsr.edci.domain.GlobalDefinitions;
import gov.nih.nci.ncicb.cadsr.edci.domain.Instrument;
import gov.nih.nci.ncicb.cadsr.service.QueryMetadataService;
import gov.nih.nci.ncicb.cadsr.service.ServiceException;

import java.util.Date;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


/**
 * An implementation of QueryMetadataService.
 */
public class QueryMetadataServiceImpl implements QueryMetadataService
{
    private EDCIDAOFactory daoFactory;
    private static Logger logger = LogManager.getLogger(QueryMetadataService.class);
    
    public QueryMetadataServiceImpl() {
    }
    /**
     * Queries Instrument metadata.
     * @param formIdSeq
     * @return
     * @throws ServiceException
     */
    public Instrument getInstrumentMetaData(String formIdSeq ) throws ServiceException 
    {
        try {
         //Query GlobalDefinitions metadata.
         GlobalDefinitions globalDefinitions = getGlobalDefinitions(formIdSeq);
         //Query Instrument metadata.
         InstrumentDAO instrumentDAO = daoFactory.getInstrumentDAO();
         Instrument instrument = instrumentDAO.getInstrument(formIdSeq, globalDefinitions);
         return instrument;
        }
        catch (Exception e) {
            logger.error("Error getting GlobalDefinitions.", e);
            throw new ServiceException("Error getting GlobalDefinitions.", e);
        }        
    }
    /**
     * Query GlobalDefinitions metadata.
     * @param idSeq
     * @return
     * @throws ServiceException
     */
    public GlobalDefinitions getGlobalDefinitions(String idSeq) throws ServiceException 
    {
      try {
       GlobalDefinitionsDAO globalDefinitionsDAO = daoFactory.getGlobalDefinitionsDAO();
       GlobalDefinitions globalDefinitions = globalDefinitionsDAO.getGlobalDefinitions(idSeq);
       return globalDefinitions;
      }
      catch (Exception e) {
          logger.error("Error getting GlobalDefinitions.", e);
          throw new ServiceException("Error getting GlobalDefinitions.", e);
      }
    }
    /**
     * Get the caDSR Form
     * @param publicId
     * @param version
     * @return
     */
    public Form getForm(String publicId, String version) {
        return null;
    }
    /**
     * Get the Instrument message from the database.
     * @param idSeq
     * @param generateDate
     * @return
     */
    public String getInstrumentMessage(String idSeq, Date generateDate) {
        return null;
    }
    /**
     * Get the GlobalDefinitions MIF message from the database.
     * @param idSeq
     * @param generateDate
     * @return
     */
    public String getGlobalDefinitionsMessage(String idSeq, 
                                              Date generateDate) {
        return null;
    }

    public void setDaoFactory(EDCIDAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public EDCIDAOFactory getDaoFactory() {
        return daoFactory;
    }


}
