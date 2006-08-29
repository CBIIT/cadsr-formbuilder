package gov.nih.nci.ncicb.cadsr.serviceimpl;

import gov.nih.nci.ncicb.cadsr.dto.FormMetaData;
import gov.nih.nci.ncicb.cadsr.edci.domain.GlobalDefinitions;
import gov.nih.nci.ncicb.cadsr.edci.domain.Instrument;
import gov.nih.nci.ncicb.cadsr.edci.domain.impl.GlobalDefinitionsImpl;
import gov.nih.nci.ncicb.cadsr.service.CaAdapterService;
import gov.nih.nci.ncicb.cadsr.service.GenerateMessageService;

import gov.nih.nci.ncicb.cadsr.service.QueryMetadataService;
import gov.nih.nci.ncicb.cadsr.service.ServiceException;

import java.io.File;
import java.io.StringWriter;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class GenerateMessageServiceImpl implements GenerateMessageService
{
    private QueryMetadataService queryMetadataService = null;
    private CaAdapterService caAdapterService = null;
    private static Logger logger = LogManager.getLogger(GenerateMessageServiceImpl.class);

    
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
        Instrument instrument = queryMetadataService.getInstrumentMetaData(formIdSeq);
        File csvFile = generateCSVFile(instrument);
        String eDCIHL7Message = caAdapterService.generateeDCIHL7Message(csvFile);
        
        return eDCIHL7Message;
    }
    
    public String getDCIDefsMessage(String formIdSeq ) throws ServiceException {
         try {
             GlobalDefinitions globalDefinitions = queryMetadataService.getGlobalDefinitions(formIdSeq);
             return getDCIDefsMessage(globalDefinitions);
         }
         catch(Exception e) {
             logger.error("Error generating DCI def message.",e);
             throw new ServiceException("Error generating DCI def message.",e);
         }
    }

    
    protected File generateCSVFile(Instrument instrument) throws ServiceException
    {
       return null;    
    }
    
    protected String getDCIDefsMessage(GlobalDefinitions globalDefinitions) throws ServiceException {
        try {
            StringWriter sw = new StringWriter();
            ((GlobalDefinitionsImpl)globalDefinitions).marshal(sw); 
            sw.close();
            return sw.toString();
        }
        catch (Exception e){
            logger.error("Error marshalling GlobalDefinitions.",e);
            throw new ServiceException("Error marshalling GlobalDefinitions.",e);
        }
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
