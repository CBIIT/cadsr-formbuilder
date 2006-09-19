package gov.nih.nci.ncicb.cadsr.serviceimpl;

import gov.nih.nci.cadsr.domain.Form;
import gov.nih.nci.cadsr.domain.ReferenceDocument;
import gov.nih.nci.ncicb.cadsr.constants.EDCIConfiguration;
import gov.nih.nci.ncicb.cadsr.dao.EDCIDAOFactory;
import gov.nih.nci.ncicb.cadsr.dao.GlobalDefinitionsDAO;
import gov.nih.nci.ncicb.cadsr.dao.InstrumentDAO;
import gov.nih.nci.ncicb.cadsr.edci.domain.GlobalDefinitions;
import gov.nih.nci.ncicb.cadsr.edci.domain.Instrument;
import gov.nih.nci.ncicb.cadsr.service.CaAdapterService;
import gov.nih.nci.ncicb.cadsr.service.GenerateMessageService;
import gov.nih.nci.ncicb.cadsr.service.QueryMetadataService;
import gov.nih.nci.ncicb.cadsr.service.ServiceException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import java.net.URL;

import java.util.Collection;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import org.exolab.castor.xml.Marshaller;

import org.w3c.dom.Document;


/**
 * Implements the GenerateMessage interface.
 */


public class GenerateMessageServiceImpl implements GenerateMessageService
{
    private QueryMetadataService queryMetadataService = null;
    private CaAdapterService caAdapterService = null;
    private Transformer gdToMifTransformer = null;
    private EDCIDAOFactory daoFactory;
    private static Logger logger = LogManager.getLogger(GenerateMessageServiceImpl.class);

    
    public GenerateMessageServiceImpl() {
    }
    
    /**
     * Generates the  message for the give formIdSeq.
     * @param formIdSeq
     * @param user
     * @param messageType
     * @return
     * @throws ServiceException
     */
     public String generateMessage(String formIdSeq, String user, String messageType ) throws ServiceException
    {
        try
        {
          if (!(messageType.equals(EDCI))) {
              throw new ServiceException("Only Instrument HL7 message can be generated.");
          }
          String message = null;
          if (!validateUserForGeneratingMessage(formIdSeq, user, messageType)) {
               throw new ServiceException("User does not have sufficient privileges to generate this message.");
          }
          GlobalDefinitions globalDefinitions = null;
          String gdMessage = null;
          Date createDate = new Date();
          if ((messageType.equals(EDCI))||(messageType.equals(GLOBAL_DEFINITIONS_MIF))) {
               globalDefinitions = queryMetadataService.getGlobalDefinitions(formIdSeq);
               gdMessage = getGlobalDefinitionMIFMessage(globalDefinitions);
               GlobalDefinitionsDAO globalDefinitionsDAO = daoFactory.getGlobalDefinitionsDAO();
               globalDefinitionsDAO.storeGlobalDefinitionsMIFMessage(formIdSeq, gdMessage,createDate, user);
          }
          String instrumentMessage = null;
          if ((messageType.equals(EDCI))||(messageType.equals(EDCI_WITHOUT_ATTACHMENT)))
          {
                Instrument instrument = queryMetadataService.getInstrumentMetaData(formIdSeq);
                File csvFile = generateCSVFile(instrument);
                instrumentMessage = caAdapterService.generateeDCIHL7Message(csvFile);
                InstrumentDAO instrumentDAO = daoFactory.getInstrumentDAO();
                instrumentDAO.storeInstrumentHL7Message(formIdSeq, instrumentMessage,createDate, user);
          }
          if (messageType.equals(EDCI)) {
              message = attachGlobalDefinitionsMIF(instrumentMessage, gdMessage);  
          }
          if (messageType.equals(EDCI_WITHOUT_ATTACHMENT)) {
              message = instrumentMessage;
          }
          if (messageType.equals(GLOBAL_DEFINITIONS_MIF)) {
                message = gdMessage;
          }
          return message;
        }
        catch (ServiceException se) {
            logger.error(se);throw se;
        }
        catch (Exception e) {
            logger.error("Error generating message. "+formIdSeq+" "+user+" "+messageType, e);
            throw new ServiceException("Error generating message. "+formIdSeq+" "+user+" "+messageType, e);
        }

    }
   /**
     * Generate message for given form publicId, and version.
     * @param publicId
     * @param version
     * @param user
     * @param messageType
     * @return
     * @throws ServiceException
     */
    public String generateMessage(String publicId, String version, String user, String messageType ) throws ServiceException{
       try {
           Form form = queryMetadataService.getForm(publicId, version);
           return generateMessage(form.getId(), user, messageType);
       }
        catch (ServiceException se) {
            logger.error(se);throw se;
        }
        catch (Exception e) {
            logger.error("Error generating message. "+publicId+" "+version+" "+user+" "+messageType, e);
            throw new ServiceException("Error generating message. "+publicId+" "+version+" "+user+" "+messageType, e);
        }       
    }
    /**
     * Gets the message from the database.
     * @param formIdSeq
     * @param messageType
     * @return
     * @throws ServiceException
     */
    public String getMessage(String formIdSeq,Date generateDate ,String messageType) throws ServiceException {
        try {
            String instrumentMessage = queryMetadataService.getInstrumentMessage(formIdSeq, generateDate);
            String gdMessage = queryMetadataService.getGlobalDefinitionsMessage(formIdSeq, generateDate);
            String message = null;
            if (messageType.equals(EDCI)) {
                message = attachGlobalDefinitionsMIF(instrumentMessage, gdMessage);  
            }
            if (messageType.equals(EDCI_WITHOUT_ATTACHMENT)) {
                message = instrumentMessage;
            }
            if (messageType.equals(GLOBAL_DEFINITIONS_MIF)) {
                  message = gdMessage;
            }
            return message;            
        }
        catch (ServiceException se) {
            logger.error(se);throw se;
        }
        catch (Exception e) {
            logger.error("Error generating message. "+formIdSeq+" "+" "+messageType, e);
            throw new ServiceException("Error generating message. "+formIdSeq+" "+messageType, e);
        }
    }
    /**
     * Gets the message from the database.
     * @param publicId
     * @param version
     * @param generateDate
     * @param messageType
     * @return
     * @throws ServiceException
     */
    public String getMessage(String publicId,String version,Date generateDate, String messageType) throws ServiceException {
        try {
            Form form = queryMetadataService.getForm(publicId, version);
            return getMessage(form.getId(), generateDate,messageType);
        }
         catch (ServiceException se) {
             logger.error(se);throw se;
         }
         catch (Exception e) {
             logger.error("Error generating message. "+publicId+" "+version+" "+" "+messageType, e);
             throw new ServiceException("Error generating message. "+publicId+" "+version+" "+" "+messageType, e);
         }       
    }
    /**
     * Validates the messageType
     * @param messageType
     * @throws ServiceException
     */
    protected void validateMessageType(String messageType) throws ServiceException {
        if ((messageType == null)&&
           (!(messageType.equals(EDCI)))&&
            (!(messageType.equals(EDCI_WITHOUT_ATTACHMENT)))&&
            (!(messageType.equals(GLOBAL_DEFINITIONS_MIF)))) {
                throw new ServiceException("Invalid messageType. messageType can be :"+EDCI+", "+EDCI_WITHOUT_ATTACHMENT+", "+GLOBAL_DEFINITIONS_MIF);
            }
    }
    /**
     * Returns true if a user has privileges to generate message for a given form.
     * otherwise returns false.
     * @param formIdSeq
     * @param user
     * @param messageType
     * @return
     * @throws ServiceException
     */
    protected boolean validateUserForGeneratingMessage(String formIdSeq,String user, String messageType) throws ServiceException {
        return true;
    }
    
    /**
     * Generate the csv file ftom the Instrument metadata.
     * @param instrument
     * @return
     * @throws ServiceException
     */
    protected File generateCSVFile(Instrument instrument) throws ServiceException
    {
      EDCIConfiguration config = EDCIConfiguration.getInstance();
      String csvFileLocation = config.getProperty("csvFileLocation");
      try {
      //Create a csv File from the instrument based on a CSV file specification
       String instrumentSCSFile = config.getProperty("InstrumentSCSFile");
      //Is there a way to validate the generated csvFile against csv File specification.
       
       //Return a test csv File
       String testFileName ="040002.csv"; 
       File csvFile = new File(csvFileLocation+testFileName);
       return csvFile;    
      }
      catch(Exception e){
          logger.error("Error creating the csvFile.",e);
          throw new ServiceException("Error creating the csvFile.",e);
      }
    }
    /**
     * Transformer for GlobalDefinitionsToMif stylesheet.
     * @return
     * @throws ServiceException
     */
    protected Transformer getGlobalDefinitionsToMifStyleSheetTransformer() throws ServiceException
    {
       try {
          if (gdToMifTransformer == null)
          {
             TransformerFactory transformerFactory = TransformerFactory.newInstance();
             EDCIConfiguration config = EDCIConfiguration.getInstance();
             //Read in the Stylesheet.
             String globalDefinitionsToMIFStylesheet = config.getProperty("GlobalDefinitionsToMIFStyleSheet");
             URL styleSheetUrl = getClass().getResource(globalDefinitionsToMIFStylesheet);
             InputStream styleSheetStream = styleSheetUrl.openStream();
             Source styleSheet = new StreamSource(styleSheetStream);
             logger.debug("System id "+styleSheet.getSystemId());
             logger.debug("URL path "+styleSheetUrl.getPath().substring(0,styleSheetUrl.getPath().lastIndexOf("/")));
             logger.debug("URI "+styleSheetUrl.toURI().toString());
             //Set the Stylesheet systemId so that it can load included/imported files.
             styleSheet.setSystemId(styleSheetUrl.toURI().toString());
             Templates templates = transformerFactory.newTemplates(styleSheet);
             //Create a Transformer.
             gdToMifTransformer = templates.newTransformer();
          }
          return gdToMifTransformer;
       }
       catch(Exception e) {
           logger.error("Error getting the GlobalDefinitionsToMIF stylesheet.", e);
           throw new ServiceException("Error getting the GlobalDefinitionsToMIF stylesheet.", e);
       }
    }
    /**
     * Generate the GlobalDefinitions MIF message.
     * @param globalDefinitions
     * @return
     * @throws ServiceException
     */
    protected String getGlobalDefinitionMIFMessage(GlobalDefinitions globalDefinitions) throws ServiceException {
        try {
            //Create the GlobalDefinitions document
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.newDocument();
            Marshaller.marshal(globalDefinitions, document);
            //Apply the stylesheet to transform the document to MIF format.
            Transformer transformer = getGlobalDefinitionsToMifStyleSheetTransformer();
            Source source = new DOMSource(document);
            StringWriter sWriter = new StringWriter();
            transformer.transform (source, new StreamResult(sWriter));
            String gdMIFMessage = sWriter.getBuffer().toString();
            logger.debug(gdMIFMessage);
            return gdMIFMessage;
        }
        catch (Exception e){
            logger.error("Error marshalling GlobalDefinitions.",e);
            throw new ServiceException("Error marshalling GlobalDefinitions.",e);
        }
    }
    
    public Collection<ReferenceDocument> queryFormMessageReferenceDocuments(String publicId, 
                                                                            String version) {
        return null;
    }

    public Collection<ReferenceDocument> queryFormMessageReferenceDocuments(String formIdSeq) {
        return null;
    }
    
    /**
     * Attache the GlobalDefinitions MIF to the instrumentMessage.
     * @param instrumentMessage
     * @param attachment
     * @return
     * @throws ServiceException
     */
    protected String attachGlobalDefinitionsMIF(String instrumentMessage, String attachment) throws ServiceException 
    {
        return instrumentMessage+attachment;
    }
    
    public void setQueryMetadataService(QueryMetadataService queryMetadataService) {
        this.queryMetadataService = queryMetadataService;
    }

    public QueryMetadataService getQueryMetadataService() {
        return queryMetadataService;
    }

    public void setCaAdapterService(CaAdapterService caAdapterService) {
        this.caAdapterService = caAdapterService;
    }

    public CaAdapterService getCaAdapterService() {
        return caAdapterService;
    }
    
    public void setDaoFactory(EDCIDAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public EDCIDAOFactory getDaoFactory() {
        return daoFactory;
    }


}
