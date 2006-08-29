package gov.nih.nci.ncicb.cadsr.dao;

import gov.nih.nci.ncicb.cadsr.edci.domain.GlobalDefinitions;
import gov.nih.nci.ncicb.cadsr.edci.domain.Instrument;

public interface InstrumentDAO {

  public Instrument getInstrument(String formIdSeq, GlobalDefinitions globalDefinitions) throws DataAccessException;

}
