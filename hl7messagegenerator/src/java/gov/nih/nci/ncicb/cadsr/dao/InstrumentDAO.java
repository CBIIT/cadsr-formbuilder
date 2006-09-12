package gov.nih.nci.ncicb.cadsr.dao;

import gov.nih.nci.ncicb.cadsr.edci.domain.GlobalDefinitions;
import gov.nih.nci.ncicb.cadsr.edci.domain.Instrument;
/**
 *   DAO for querying Instrument metadata.
 */
public interface InstrumentDAO {
  /**
     *  Query Instrument metadata.
     * @param formIdSeq
     * @param globalDefinitions
     * @return Instrument
     * @throws DataAccessException
     */
  public Instrument getInstrument(String formIdSeq, GlobalDefinitions globalDefinitions) throws DataAccessException;

}
