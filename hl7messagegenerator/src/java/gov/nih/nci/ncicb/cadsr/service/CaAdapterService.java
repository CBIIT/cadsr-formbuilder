package gov.nih.nci.ncicb.cadsr.service;

import java.io.File;
/**
 * Interface abstracting all interaction with caAdapter API.
 */
public interface CaAdapterService {
  /**
     * Generate the Instrument HL7 message from the csv file.
     * @param csvFile
     * @return eDCI HL7 message.
     * @throws ServiceException
     */
  public String generateeDCIHL7Message(File csvFile) throws ServiceException;
  
}
