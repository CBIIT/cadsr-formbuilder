package gov.nih.nci.ncicb.cadsr.dao;

import gov.nih.nci.ncicb.cadsr.edci.domain.GlobalDefinitions;

public interface GlobalDefinitionsDAO  {

   public GlobalDefinitions getGlobalDefinitions(String formIdSeq) throws DataAccessException;
   
}
