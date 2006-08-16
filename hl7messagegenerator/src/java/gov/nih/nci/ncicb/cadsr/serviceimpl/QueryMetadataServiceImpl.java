package gov.nih.nci.ncicb.cadsr.serviceimpl;

import gov.nih.nci.ncicb.cadsr.dao.EDCIDAOFactory;
import gov.nih.nci.ncicb.cadsr.dao.GlobalDefinitionsDAO;
import gov.nih.nci.ncicb.cadsr.dto.FormMetaData;
import gov.nih.nci.ncicb.cadsr.edci.domain.GlobalDefinitions;
import gov.nih.nci.ncicb.cadsr.service.QueryMetadataService;
import gov.nih.nci.ncicb.cadsr.service.ServiceException;

public class QueryMetadataServiceImpl implements QueryMetadataService
{
    private EDCIDAOFactory daoFactory;
    
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
