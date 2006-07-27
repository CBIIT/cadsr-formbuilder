package gov.nih.nci.ncicb.cadsr.service;
/**
 * This Service defines the interface required to query  and generate FormMetaData 
 * from caDSR.
 */
import gov.nih.nci.ncicb.cadsr.dto.FormMetaData;

public interface QueryMetadataService 
{
   public FormMetaData getFormMetaData(String idSeq ) throws ServiceException;
   
   public FormMetaData generateeDCIDefs(FormMetaData formMetaData) throws ServiceException;
}
