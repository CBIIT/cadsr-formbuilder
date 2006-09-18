package gov.nih.nci.ncicb.cadsr.dao;

import gov.nih.nci.cadsr.domain.Form;
import gov.nih.nci.cadsr.domain.ReferenceDocument;
import gov.nih.nci.ncicb.cadsr.dto.ReferenceDocumentAttachment;
import gov.nih.nci.ncicb.cadsr.edci.domain.GlobalDefinitions;
import gov.nih.nci.ncicb.cadsr.edci.domain.Instrument;

import java.util.Collection;
import java.util.Date;

/**
 *   DAO for querying Instrument metadata.
 */
public interface InstrumentDAO {
    public static final String INSTRUMENT_REF_DOC_TYPE="HL7_EDCI_INSTRUMENT_MIF";
   /**
     *  Query Instrument metadata.
     * @param formIdSeq
     * @param globalDefinitions
     * @return Instrument
     * @throws DataAccessException
     */
  public Instrument getInstrument(String formIdSeq, GlobalDefinitions globalDefinitions) throws DataAccessException;
  /**
     * Stores the Instrument HL7 message in the caDSR database and returns the
     * id of the ReferenceDocument.
     * @param formIdSeq
     * @param message
     * @param createDate date the message was created.
     * @return id of the ReferenceDocument
     * @throws DataAccessException
     */
  public String storeInstrumentHL7Message(String formIdSeq, String message, Date createDate, String user) throws DataAccessException;
   /**
     * Queries an existing instument HL7 message 
     * @param formIdSeq
     * @param createDate
     * @return ReferenceDocumentAttachment for the HL7 message
     * @throws DataAccessException
     */
  public ReferenceDocumentAttachment queryInstrumentHL7Message(String formIdSeq, Date createDate) throws DataAccessException;
  /**
     * 
     * @param rdIdSeq
     * @return ReferenceDocumentAttachment for the HL7 message
     * @throws DataAccessException
     */
  public ReferenceDocumentAttachment queryInstrumentHL7Message(String rdIdSeq) throws DataAccessException;
  /**
     * Queries a caDSR Form object 
     * @param publicId
     * @param version
     * @return caDSR Form object.
     * @throws DataAccessException
     */
  public Form queryCaDSRForm(String publicId, String version) throws DataAccessException;
  /**
     * Queries a ReferenceDocument for Instrument HL7 message and GlobalDefinition MIF
     * message associated with a Form. The actual message attachments should be 
     * queried separately.
     * @param publicId
     * @param version
     * @return Collection of instrument HL7 message and GlobalDefinition MIF ReferenceDocument
     * @throws DataAccessException
     */
  public Collection<ReferenceDocument> queryFormMessageReferenceDocuments(String publicId,String version) throws DataAccessException;
  /**
     * Queries a ReferenceDocument for Instrument HL7 message and GlobalDefinition MIF
     * message associated with a Form. The actual message attachments should be 
     * queried separately.
     * @param formIdSeq
     * @return Collection of instrument HL7 message and GlobalDefinition MIF ReferenceDocument
     * @throws DataAccessException
     */
  public Collection<ReferenceDocument> queryFormMessageReferenceDocuments(String formIdSeq) throws DataAccessException;
  
}
