package gov.nih.nci.ncicb.cadsr.serviceimpl;

import gov.nih.nci.ncicb.cadsr.dto.FormMetaData;
import gov.nih.nci.ncicb.cadsr.service.CaAdapterService;
import gov.nih.nci.ncicb.cadsr.service.GenerateMessageService;

import gov.nih.nci.ncicb.cadsr.service.QueryMetadataService;
import gov.nih.nci.ncicb.cadsr.service.ServiceException;

import java.io.File;

public class GenerateMessageServiceImpl implements GenerateMessageService
{
    QueryMetadataService queryMetadataService = null;
    CaAdapterService caAdapterService = null;
    
    public GenerateMessageServiceImpl() {
    }
    
    /**
     * Generates the eDCI HL7 message for the give formIdSeq.
     * @param formIdSeq
     * @return
     * @throws ServiceException
     */
    public String geteDCIHL7Message(String formIdSeq) throws ServiceException
    {
        FormMetaData formMetaData = queryMetadataService.getFormMetaData(formIdSeq);
        formMetaData = queryMetadataService.generateeDCIDefs(formMetaData);
        File csvFile = generateCSVFile(formMetaData);
        String eDCIHL7Message = caAdapterService.generateeDCIHL7Message(csvFile);
        
        return eDCIHL7Message;
    }
    
    protected File generateCSVFile(FormMetaData formMetaData) throws ServiceException
    {
       return null;    
    }
    
    public void setQueryMetadataService(QueryMetadataService queryMetadataService) {
        this.queryMetadataService = queryMetadataService;
    }

    public QueryMetadataService getQueryMetadataService() {
        return queryMetadataService;
    }

    public void setCaAdapterService(CaAdapterService caAdapterService) {
        this.caAdapterService = caAdapterService;
    }

    public CaAdapterService getCaAdapterService() {
        return caAdapterService;
    }
}
