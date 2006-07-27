package gov.nih.nci.ncicb.cadsr.service;

import gov.nih.nci.ncicb.cadsr.dto.FormMetaData;

import java.io.File;


/**
 * Defines the methods required to generate the HL7 message
 */
public interface GenerateMessageService {

   public String geteDCIHL7Message(String formIdSeq ) throws ServiceException;
   
}
