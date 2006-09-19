package gov.nih.nci.ncicb.cadsr.service;

import java.io.File;
/**
 * Interface abstracting all interaction with caAdapter API.
 */
public interface CaAdapterService {
  /**
     * Generate the Instrument HL7 message from the csv file.
     * Assumes only one Instrument is detailed in the csv File if more than one
     * Instrument is defined in the csv file HL7 message will be returned only
     * for the first one.
     * @param csvFile 
     * @return eDCI HL7 message.
     * @throws ServiceException
     */
  public String generateeDCIHL7Message(File csvFile) throws ServiceException;
  
}
