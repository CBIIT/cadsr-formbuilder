package gov.nih.nci.ncicb.cadsr.dao;

import gov.nih.nci.ncicb.cadsr.dto.ReferenceDocumentAttachment;
import gov.nih.nci.ncicb.cadsr.edci.domain.GlobalDefinitions;

import java.util.Date;

/**
 *  DAO for querying GlobalDefinitions metadata.
 */
public interface GlobalDefinitionsDAO  {
    public static final String GLOBALDEFINITIONS_REF_DOC_TYPE="HL7_GLOBAL_DEFINITION_MIF";
   /**
     * Query GlobalDefinitions metadata.
     * @param formIdSeq
     * @return GlobalDefinitions 
     * @throws DataAccessException
     */
   public GlobalDefinitions getGlobalDefinitions(String formIdSeq) throws DataAccessException;
    /**
     * Stores the GlobalDefinitions MIF message.
     * @param formIdSeq
     * @param message
     * @param user
     * @return IdSeq of the ReferenecDocument.
     * @throws DataAccessException
     */
    public String storeGlobalDefinitionsMIFMessage(String formIdSeq, String message, String user) throws DataAccessException;
     /**
       * Queries an existing GlobalDefinitionsMIF message.
       * @param formIdSeq
       * @param createDate
       * @return ReferenceDocumentAttachment for the HL7 message
       * @throws DataAccessException
       */
    public ReferenceDocumentAttachment queryGlobalDefinitionsMIFMessage(String formIdSeq, Date createDate) throws DataAccessException;
    /**
       * Queries an existing GlobalDefinitionsMIF message.
       * @param rdIdSeq
       * @return ReferenceDocumentAttachment for the HL7 message
       * @throws DataAccessException
       */
    public ReferenceDocumentAttachment queryGlobalDefinitionsMIFMessage(String rdIdSeq) throws DataAccessException;
       
}
