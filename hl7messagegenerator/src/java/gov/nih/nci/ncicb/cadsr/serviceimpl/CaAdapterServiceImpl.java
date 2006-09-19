package gov.nih.nci.ncicb.cadsr.serviceimpl;

import gov.nih.nci.hl7.map.TransformationResult;
import gov.nih.nci.hl7.map.TransformationService;
import gov.nih.nci.hl7.validation.ValidatorResult;
import gov.nih.nci.hl7.validation.ValidatorResults;
import gov.nih.nci.ncicb.cadsr.constants.EDCIConfiguration;
import gov.nih.nci.ncicb.cadsr.service.CaAdapterService;

import gov.nih.nci.ncicb.cadsr.service.ServiceException;

import java.io.File;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


/**
 * Implements the CaAdapterService interface.
 */
public class CaAdapterServiceImpl implements CaAdapterService {

    private String csvEDCIGL7MappingFile;
    private static final Logger logger = LogManager.getLogger(CaAdapterServiceImpl.class);
    private File mapFile = null;
    
    public CaAdapterServiceImpl() {
          init();
    }
    
    protected void init()  {
        EDCIConfiguration config = EDCIConfiguration.getInstance();
        String mapFileLocation = config.getProperty("InstrumentMAPFile");
        mapFile = new File(mapFileLocation);
    }

    public String generateeDCIHL7Message(File csvFile) throws ServiceException {

      try {
           TransformationService ts = new TransformationService(mapFile, csvFile);
           List<TransformationResult> results = ts.process();
           TransformationResult tr = results.get(0);
           ValidatorResults validatorResults = tr.getValidatorResults();
           List fatalMessages = validatorResults.getMessages(ValidatorResult.Level.FATAL);
           if (fatalMessages.size() > 0 ) {
               // A fatal error has occurred using caAdapter transformation service.
               logger.error("Fatal error using caAdapter transformation service.");
               for (Iterator i=fatalMessages.iterator();i.hasNext();) {
                   logger.error(" CaAdapter Fatal Message ->"+i.next());
               }
               throw new ServiceException("Error occured using caAdapter transformation service. "+fatalMessages.size()+" fatal errors. "+fatalMessages.get(0));
           }
           List errorMessages = validatorResults.getMessages(ValidatorResult.Level.ERROR);
           if (errorMessages.size()>0) {
               // An error has occurred using caAdapter transformation service.
               logger.error("Error using caAdapter transformation service.");
               for (Iterator i=errorMessages.iterator();i.hasNext();) {
                   logger.error(" CaAdapter Error Message ->"+i.next());
               }
               throw new ServiceException("Error occured using caAdapter transformation service. "+errorMessages.size()+" errors. "+errorMessages.get(0));
           }
           //Process warning messages
           //Process Info messages
           String message = tr.getHl7V3MessageText();
           return message;
       }
       catch(Exception e) {
           logger.error("Error using caAdapter transformation service.",e);
           throw new ServiceException("Error occured using caAdapter transformation service. ",e);
       }
        
    }

    public void setCsvEDCIGL7MappingFile(String csvEDCIGL7MappingFile) {
        this.csvEDCIGL7MappingFile = csvEDCIGL7MappingFile;
    }

    public String getCsvEDCIGL7MappingFile() {
        return csvEDCIGL7MappingFile;
    }
}
