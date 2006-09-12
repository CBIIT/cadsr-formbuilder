package gov.nih.nci.ncicb.cadsr.service;

import gov.nih.nci.cadsr.domain.Form;
import gov.nih.nci.ncicb.cadsr.dao.EDCIDAOFactory;
import gov.nih.nci.ncicb.cadsr.edci.domain.GlobalDefinitions;
import gov.nih.nci.ncicb.cadsr.edci.domain.Instrument;

import java.util.Date;

/**
 * This Service defines the interface required to query  and generate FormMetaData
 * from caDSR.
 */
public interface QueryMetadataService 
{
   /**
     * Query Instrument metadata for a given formIdSeq
     * @param idSeq
     * @return Instrument
     * @throws ServiceException
     */
   public Instrument getInstrumentMetaData(String idSeq ) throws ServiceException;
   /**
     * Query GlobalDefinitions for a given formIdSeq
     * @param idSeq
     * @return GlobalDefinitions
     * @throws ServiceException
     */
   public GlobalDefinitions getGlobalDefinitions(String idSeq) throws ServiceException;
    /**
     * Query a caDSR Form for a give publicId and version.
     * @param publicId
     * @param version
     * @return caDSR Form
     * @throws ServiceException
     */
    public Form getForm(String publicId, String version ) throws ServiceException;
    /**
     * Gets the Instrument message from the database.
     * @param idSeq
     * @return eDCI HL7 message without attachment.
     * @throws ServiceException
     */
    public String getInstrumentMessage(String idSeq, Date generateDate ) throws ServiceException;
    /**
     * Gets the GlobalDefinitions message from the database.
     * @param idSeq
     * @return GlobalDefinitions MIF message.
     * @throws ServiceException
     */
    public String getGlobalDefinitionsMessage(String idSeq, Date generateDate) throws ServiceException;

    public void setDaoFactory(EDCIDAOFactory eDCIDAOFactory);

    public EDCIDAOFactory getDaoFactory();   
   
}
