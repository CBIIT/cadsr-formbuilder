package gov.nih.nci.ncicb.cadsr.dao;

import gov.nih.nci.ncicb.cadsr.edci.domain.GlobalDefinitions;
/**
 *  DAO for querying GlobalDefinitions metadata.
 */
public interface GlobalDefinitionsDAO  {
   /**
     * Query GlobalDefinitions metadata.
     * @param formIdSeq
     * @return GlobalDefinitions 
     * @throws DataAccessException
     */
   public GlobalDefinitions getGlobalDefinitions(String formIdSeq) throws DataAccessException;
   
}
