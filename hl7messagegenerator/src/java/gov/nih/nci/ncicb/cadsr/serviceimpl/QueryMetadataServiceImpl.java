package gov.nih.nci.ncicb.cadsr.serviceimpl;

import gov.nih.nci.ncicb.cadsr.dao.EDCIDAOFactory;
import gov.nih.nci.ncicb.cadsr.dao.GlobalDefinitionsDAO;
import gov.nih.nci.ncicb.cadsr.dto.FormMetaData;
import gov.nih.nci.ncicb.cadsr.edci.domain.GlobalDefinitions;
import gov.nih.nci.ncicb.cadsr.service.QueryMetadataService;
import gov.nih.nci.ncicb.cadsr.service.ServiceException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class QueryMetadataServiceImpl implements QueryMetadataService
{
    private EDCIDAOFactory daoFactory;
    private static Logger logger = LogManager.getLogger(QueryMetadataService.class);
    
    public QueryMetadataServiceImpl() {
    }
    
    public FormMetaData getFormMetaData(String idSeq ) throws ServiceException 
    {
        return new FormMetaData();
    }
    
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

    
    public FormMetaData generateeDCIDefs(FormMetaData formMetaData) throws ServiceException 
    {
        return formMetaData;
    }


    public void setDaoFactory(EDCIDAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public EDCIDAOFactory getDaoFactory() {
        return daoFactory;
    }
}
