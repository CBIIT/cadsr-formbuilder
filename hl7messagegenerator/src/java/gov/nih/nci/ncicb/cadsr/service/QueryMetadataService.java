package gov.nih.nci.ncicb.cadsr.service;
/**
 * This Service defines the interface required to query  and generate FormMetaData
 * from caDSR.
 */
import gov.nih.nci.ncicb.cadsr.dao.EDCIDAOFactory;
import gov.nih.nci.ncicb.cadsr.dto.FormMetaData;
import gov.nih.nci.ncicb.cadsr.edci.domain.GlobalDefinitions;

/**
 * This Service defines the interface required to query  and generate FormMetaData
 * from caDSR.
 */
public interface

QueryMetadataService 
{
    public void setDaoFactory(EDCIDAOFactory eDCIDAOFactory);

    public EDCIDAOFactory getDaoFactory();
    
   public FormMetaData getFormMetaData(String idSeq ) throws ServiceException;
   
   public GlobalDefinitions getGlobalDefinitions(String idSeq) throws ServiceException;
   
   public FormMetaData generateeDCIDefs(FormMetaData formMetaData) throws ServiceException;
}
