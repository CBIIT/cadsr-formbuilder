package gov.nih.nci.ncicb.cadsr.serviceimpl;

import gov.nih.nci.ncicb.cadsr.dto.FormMetaData;
import gov.nih.nci.ncicb.cadsr.service.QueryMetadataService;
import gov.nih.nci.ncicb.cadsr.service.ServiceException;

public class QueryMetadataServiceImpl implements QueryMetadataService
{
    public QueryMetadataServiceImpl() {
    }
    
    public FormMetaData getFormMetaData(String idSeq ) throws ServiceException 
    {
        return new FormMetaData();
    }
    
    public FormMetaData generateeDCIDefs(FormMetaData formMetaData) throws ServiceException 
    {
        return formMetaData;
    }
}
