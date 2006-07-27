package gov.nih.nci.ncicb.cadsr.service;

import java.io.File;

public interface CaAdapterService {

  public String generateeDCIHL7Message(File csvFile) throws ServiceException;
  
}
