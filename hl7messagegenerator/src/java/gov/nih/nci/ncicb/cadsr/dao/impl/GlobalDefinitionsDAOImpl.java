package gov.nih.nci.ncicb.cadsr.dao.impl;

import gov.nih.nci.cadsr.domain.AdministeredComponent;
import gov.nih.nci.cadsr.domain.DataElement;
import gov.nih.nci.cadsr.domain.Designation;
import gov.nih.nci.cadsr.domain.EnumeratedValueDomain;
import gov.nih.nci.cadsr.domain.Form;
import gov.nih.nci.cadsr.domain.Module;
import gov.nih.nci.cadsr.domain.NonenumeratedValueDomain;
import gov.nih.nci.cadsr.domain.Question;
import gov.nih.nci.cadsr.domain.ReferenceDocument;
import gov.nih.nci.cadsr.domain.ValidValue;
import gov.nih.nci.cadsr.domain.ValueDomainPermissibleValue;
import gov.nih.nci.ncicb.cadsr.constants.EDCIConfiguration;
import gov.nih.nci.ncicb.cadsr.dao.DataAccessException;
import gov.nih.nci.ncicb.cadsr.dao.GlobalDefinitionsDAO;
import gov.nih.nci.ncicb.cadsr.dao.impl.CaDSRApiDAOImpl;
import gov.nih.nci.ncicb.cadsr.dto.ReferenceDocumentAttachment;
import gov.nih.nci.ncicb.cadsr.edci.domain.*;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
     * implements GlobalDefinitionsDAO using the caDSR API
     */
public class GlobalDefinitionsDAOImpl  extends CaDSRApiDAOImpl implements GlobalDefinitionsDAO
{
   
    private static Logger logger = LogManager.getLogger(GlobalDefinitionsDAO.class);
    private ReferenceDocumentCreator refDocCreator;
    private RefDocAttachmentCreator refDocAttachmentCreator;
    private QueryRefDocAttachment queryRefDocAttachment;
    private StoreBlob storeBlob;
    private SimpleDateFormat formatter = new SimpleDateFormat(":yyyyMMdd:HH:mm:ss");
    
    public GlobalDefinitionsDAOImpl() 
    {
    }
    //@Transactional(readOnly=true)
    public GlobalDefinitions getGlobalDefinitions(String formIdSeq) throws DataAccessException {
        Form form = new Form();
        form.setId(formIdSeq);
        EDCIConfiguration config = EDCIConfiguration.getInstance();
        //DomainObjectFactory domainObjectFactory = getDomainObjectFactory();
        GlobalDefinitions globalDefinitions = domainObjectFactory.getGlobalDefinitions();
        try {
            //HM activityTime is a mandatory field
          globalDefinitions.setActivityTime(new Date());
          List forms = appService.search(Form.class.getName(), form);
            Form qForm;
            if (forms.size()== 0) throw new Exception("Could not find the form for "+formIdSeq);
            qForm = (Form)forms.get(0);
            if (qForm != null)
          {
              Collection<Module> modules = qForm.getModuleCollection();
              ArrayList<gov.nih.nci.ncicb.cadsr.edci.domain.DataElement> dataElements = new ArrayList<gov.nih.nci.ncicb.cadsr.edci.domain.DataElement>();
              ArrayList<DataElementConcept> dataElementConcepts = new ArrayList<DataElementConcept>();
              ArrayList<ValueDomain> valueDomains = new ArrayList<ValueDomain>();
              
              for (Module module:modules)   {
                  DataElementGroup dataElementGroup = domainObjectFactory.getDataElementGroup();
                  dataElementGroup.setDescription(module.getPreferredDefinition());
                  dataElementGroup.setName(module.getLongName());
                  dataElementGroup.setNamespace(config.getProperty("default.namespace"));
                  Collection<Question> questions = module.getQuestionCollection();
                  for (Question question:questions){
                      DataElement dE = question.getDataElement();
                      //HM add the dataElement to the dataElementGroup
                      gov.nih.nci.ncicb.cadsr.edci.domain.DataElement eDCIDE = (gov.nih.nci.ncicb.cadsr.edci.domain.DataElement)getDataElement(dE);
                      dataElements.add(eDCIDE);
                      dataElementGroup.addDataElement(eDCIDE);
                      dataElementConcepts.add(getDataElementConcept(dE));
                      valueDomains.add(getValueDomain(dE,question));
                  }
                  //HM add dataElementGroup to GlobalDefinitions
                  globalDefinitions.addDataElementGroup(dataElementGroup);
              }
             globalDefinitions.setDataElementCollection(dataElements);
             globalDefinitions.setDataElementConceptCollection(dataElementConcepts);
             globalDefinitions.setValueDomainCollection(valueDomains);
          }
            
        }catch(Exception e) {
            logger.error("Error querying DataElements.", e);
            throw new DataAccessException("Error querying DataElements.", e);
        }
        
       
        return globalDefinitions;
        
    }

    /**
     * Returns the DataElement
     * @param dE
     * @return DataElement
     */
    protected gov.nih.nci.ncicb.cadsr.edci.domain.DataElement getDataElement(DataElement dE) throws DataAccessException {
        gov.nih.nci.ncicb.cadsr.edci.domain.DataElement eDCIDE = domainObjectFactory.getDataElement();
        eDCIDE.setDefinition(dE.getPreferredDefinition());
        EDCIConfiguration config = EDCIConfiguration.getInstance();
        try {
            eDCIDE.setDescription(getDocText(dE,"Description"));
        } catch (DataAccessException e) {
            logger.error("Error setting DataElement Description.", e);
            throw new DataAccessException("Error setting DataElement Description.", e);
        }
        try {
            eDCIDE.setDataElementTextCollection(getDataElementTextCollection(dE));
        } catch (DataAccessException e) {
            logger.error("Error setting DataElement Text Collection.", e);
            throw new DataAccessException("Error setting DataElement Text Collection.", e);
        
        }
        try {
            eDCIDE.setAlternateDesignationCollection(getAlternateDesignationCollection(dE));
        } catch (DataAccessException e) {
            logger.error("Error setting DataElement Alternate Designation.", e);
            throw new DataAccessException("Error setting Alternate Designations.", e);
        
        }
        eDCIDE.setGUID(dE.getId());
        eDCIDE.setName(dE.getLongName());
        eDCIDE.setNamespace(config.getProperty("default.namespace"));
        eDCIDE.setDataElementConceptGUID(dE.getDataElementConcept().getId());
        eDCIDE.setValueDomainGUID(dE.getValueDomain().getId());
        return eDCIDE;
    }
    /**
     * Returns the DataElementConcept associated with a DataElement
     * @param dE
     * @return DataElementConcept
     * @throws DataAccessException
     */   
  protected DataElementConcept getDataElementConcept(gov.nih.nci.cadsr.domain.DataElement dE) throws DataAccessException{
      DataElementConcept eDCIDEC = domainObjectFactory.getDataElementConcept();
      gov.nih.nci.cadsr.domain.DataElementConcept dEC = dE.getDataElementConcept();
      eDCIDEC.setGUID(dEC.getId());
      eDCIDEC.setDefinition(dEC.getPreferredDefinition());
      eDCIDEC.setName(dEC.getLongName());
      eDCIDEC.setDescription(dEC.getConceptualDomain().getPreferredDefinition());
      return eDCIDEC;
  }
    /**
     * Returns the ValueDomain associated with a DataElement
     * @param dE
     * @param question
     * @return ValueDomain
     * @throws DataAccessException
     */   
  protected ValueDomain getValueDomain(gov.nih.nci.cadsr.domain.DataElement dE, Question question) throws DataAccessException{
      ValueDomain eDCIVD = domainObjectFactory.getValueDomain();
      gov.nih.nci.cadsr.domain.ValueDomain vD = dE.getValueDomain();
      EDCIConfiguration config = EDCIConfiguration.getInstance();
      eDCIVD.setDatatype(vD.getDatatypeName());
      if (vD.getDecimalPlace()!=null){
      eDCIVD.setDecimalPlaces(vD.getDecimalPlace());
      }
      eDCIVD.setDescription(vD.getPreferredDefinition());
      eDCIVD.setGUID(vD.getId());
      eDCIVD.setMaximumLength(vD.getMaximumLengthNumber());
      eDCIVD.setName(vD.getLongName());
      eDCIVD.setNamespace(config.getProperty("default.namespace"));
      try{
      eDCIVD.setSourceCodingSystem(getDocText(vD,"VD Reference"));     
      } catch (DataAccessException e) {
            logger.error("Error setting DataElement Description.", e);
            throw new DataAccessException("Error setting DataElement Description.", e);
        }
      if (vD instanceof EnumeratedValueDomain) {
             eDCIVD.setIsEnumeratedFlag(true);
             EnumeratedValueDomain eVD = (EnumeratedValueDomain) vD;
             
             // base subset based on then value domain
             EVDSubset baseEVDS = domainObjectFactory.getEVDSubset();
             baseEVDS.setBaseSubsetFlag(true);
             baseEVDS.setSubsetId(geteDCIGUID(getGUID()));
             
             // subset based on use in question
             EVDSubset eVDSS = domainObjectFactory.getEVDSubset();
             eVDSS.setBaseSubsetFlag(false);
             eVDSS.setSubsetId(geteDCIGUID(getGUID()));
             
             // get all permissible values for the valid value
             Collection<ValueDomainPermissibleValue> eVDS = eVD.getValueDomainPermissibleValueCollection();  
                    Integer i = 0;
                     for (ValueDomainPermissibleValue vDPVS:eVDS){
                          i++;
                          
                          // element for a permissible Value
                          EVDElement eVDE = domainObjectFactory.getEVDElement();
                          eVDE.setValue(vDPVS.getPermissibleValue().getValue());
                          
                          ArrayList <EVDElementText> eVDETC = new ArrayList <EVDElementText> ();
                          
                          // value meaning for the permissible value
                          EVDElementText eVDET = domainObjectFactory.getEVDElementText();
                          
                          eVDET.setValueMeaning(vDPVS.getPermissibleValue().getValueMeaning().getShortMeaning());
                          eVDET.setValueMeaningDescription(vDPVS.getPermissibleValue().getValueMeaning().getDescription());
                          eVDET.setLanguage(config.getProperty("default.language"));
                          eVDETC.add(eVDET);
                          
                          // Add the Element Text Collection to the Element
                          eVDE.setEVDElementTextCollection(eVDETC);
                          
                          // Create a base subset  element
                          ElementInSubset baseESS = domainObjectFactory.getElementInSubset();
                         // get its value from permissible values of value domain
                          baseESS.setValue(eVDE.getValue());
                          baseESS.setSequenceNumber(i);
                          
                          // Create a subset element
                          ElementInSubset eSS = domainObjectFactory.getElementInSubset();
                          eSS.setValue(eVDE.getValue());
                          
                          // set its value based on it's usage by the question
                          Collection <ValidValue> vVC = new ArrayList <ValidValue>();
                          vVC = question.getValidValueCollection();
                          for (ValidValue validValue:vVC){
                              if (validValue.getLongName().equals(eSS.getValue())){
                              //add to the subset only if it is found in the question
                                  eVDSS.addElementInSubset(eSS);
                                  eSS.setSequenceNumber(validValue.getDisplayOrder());
                              }
                          }
                         
                          baseEVDS.addElementInSubset(baseESS);
                          eDCIVD.addEVDElement(eVDE);
                          
                      } 
                      
                      //eDCIDE.setEVDSubsetId(baseEVDS.getSubsetId());
                      eDCIVD.addEVDSubset(baseEVDS);
                      if ( !baseEVDS.equals(eVDSS)){
                        eDCIVD.addEVDSubset(eVDSS);
                      }
                  }
      else if (vD instanceof NonenumeratedValueDomain) {
                      eDCIVD.setIsEnumeratedFlag(false);
                  }
                  
        return eDCIVD;
  }
  
    /**
     * Returns the documentText associated with an AdministeredComponent
     * @param ac
     * @param documentType
     * @return documentText
     * @throws DataAccessException
     */   
  protected String getDocText(AdministeredComponent ac, String documentType) throws DataAccessException{
        String docText;
        docText = null;
        Collection <ReferenceDocument> referenceDocuments;
        referenceDocuments = ac.getReferenceDocumentCollection();

        for (ReferenceDocument referenceDocument:referenceDocuments){
      
          if (referenceDocument.getType().equals(documentType)) {
              docText =referenceDocument.getDoctext();
              break;
          }
         
      
      }
      
      return docText;
  }
  
 
  
    /**
     * Returns Collection of DataElementText associated with a DataElement
     * @param dE
     * @return Collection of DataElementText
     * @throws DataAccessException
     */   
     
  protected Collection <DataElementText> getDataElementTextCollection(gov.nih.nci.cadsr.domain.DataElement dE) throws DataAccessException{
      Collection<ReferenceDocument> referenceDocuments = dE.getReferenceDocumentCollection();
      
       EDCIConfiguration config = EDCIConfiguration.getInstance();
       Collection<DataElementText> dETC = new ArrayList <DataElementText> ();
      
       for (ReferenceDocument referenceDocument:referenceDocuments){
       
           if (referenceDocument.getType().equals("Preferred Question Text") ){
               DataElementText dET = domainObjectFactory.getDataElementText();
               dET.setPrompt(referenceDocument.getDoctext());
               dET.setLanguage(referenceDocument.getLanguageName());
               dETC.add(dET);
           }
       
       }       
       if (dETC.isEmpty()){
           DataElementText dET = domainObjectFactory.getDataElementText();
           dET.setPrompt(dE.getLongName());
           dET.setLanguage(config.getProperty("default.language"));
           dETC.add(dET);
       }
       
       return dETC;
  }
  
    /**
     * Returns a Collection of AlternateDesignations associated with a DataElement
     * @param dE
     * @return Collection of AlternateDesignations
     * @throws DataAccessException
     */   
    protected Collection <AlternateDesignation> getAlternateDesignationCollection(gov.nih.nci.cadsr.domain.DataElement dE) throws DataAccessException{
        Collection<AlternateDesignation> aDC = new ArrayList <AlternateDesignation> ();
        Collection<Designation> designations = dE.getDesignationCollection();             
        for (Designation designation:designations){
           AlternateDesignation aD = domainObjectFactory.getAlternateDesignation();
           aD.setLanguage(designation.getLanguageName());
           aD.setName(designation.getName());
           aD.setType(designation.getType());
           aDC.add(aD);
        }
         return aDC;
    }
    public Form queryCaDSRForm(String idSeq) throws DataAccessException {
        Form form = new Form();
        form.setId(idSeq);
        try {
            List<Form> forms = appService.search(Form.class,form);
            if (forms.size() == 0)throw new DataAccessException("Form not found for "+idSeq);
            Form qForm = forms.get(0);
            return qForm;
        }
        catch (Exception e) {
            logger.error("Error querying form.",e);
            throw new DataAccessException("Error querying form.",e);
        }
    }

    public Form queryCaDSRForm(String publicId, String version) throws DataAccessException {
        Form form = new Form();
        try {
           form.setPublicID(new Long(publicId));
           form.setVersion(new Float(version));
        }
        catch(Exception e) {
            throw new DataAccessException("PublicId should be a valid number and version should be a valid floating point number.");
        }
        try {
            List<Form> forms = appService.search(Form.class,form);
            if (forms.size() == 0) throw new DataAccessException("Form not found for "+publicId+" v"+version);
            Form qForm = forms.get(0);
            return qForm;
        }
        catch (Exception e) {
            logger.error("Error querying form.",e);
            throw new DataAccessException("Error querying form.",e);
        }
    }   
    /**
     * Get the name of the ReferenceDocument used to store the instrument Hl7 
     * message based on current date.
     * @param form
     * @return name of the ReferenceDocument
     * @throws DataAccessException
     */
    protected String getRefDocName(Form form) throws DataAccessException
    {      
       return getRefDocName(form,new Date());
    }
    /**
     * Get the name of the ReferenceDocument used to store the instrument Hl7 
     * message
     * @param form
     * @param createDate
     * @return name of the ReferenceDocument.
     * @throws DataAccessException
     */
    protected String getRefDocName(Form form, Date createDate) throws DataAccessException
    {
       StringBuffer name = new StringBuffer(30);
       name.append(form.getPublicID().toString());
       name.append(":v");
       name.append(form.getVersion());
       name.append(formatter.format(createDate));
       
       return name.toString();
    }    
    
    /**
     * Gets the attachment name for ReferenceDocument for GlobalDefinitions MIF message
     * @param refDocName
     * @return
     */
    protected String getAttachmentName(ReferenceDocument refDoc) {
        return refDoc.getName()+"G";
    }    

    //@Transactional(readOnly=false,
      //             propagation=Propagation.REQUIRED,
         //          rollbackFor=DataAccessException.class)
     public String storeGlobalDefinitionsMIFMessage(String formIdSeq, String message, Date createDate, String user) throws DataAccessException {
         try {
              if (refDocCreator == null) {
                  refDocCreator = new ReferenceDocumentCreator(dataSource);
              }
              if (refDocAttachmentCreator == null) {
                  refDocAttachmentCreator = new RefDocAttachmentCreator(dataSource);
              }
              if (storeBlob == null) {
                  storeBlob = new StoreBlob(dataSource);
              }
             //HM 
              if (message == null) {
                   throw new DataAccessException("Global Definitions MIF message is null.");
              }
              Form form = queryCaDSRForm(formIdSeq);
              ReferenceDocument referenceDocument = new ReferenceDocument();
              referenceDocument.setCreatedBy(user);
              referenceDocument.setId(getGUID());
              referenceDocument.setType(GLOBALDEFINITIONS_REF_DOC_TYPE);
              referenceDocument.setName(getRefDocName(form, createDate));
              referenceDocument.setContext(form.getContext());
              referenceDocument = refDocCreator.createReferenceDocument(referenceDocument, formIdSeq);
              
              ReferenceDocumentAttachment refDocAttachment = new ReferenceDocumentAttachment();
              refDocAttachment.setReferenceDocument(referenceDocument);
              refDocAttachment.setMimeType("text/xml");
              refDocAttachment.setCreatedBy(user);
              refDocAttachment.setContentType("BLOB");
              refDocAttachment.setName(getAttachmentName(referenceDocument));
              refDocAttachment.setDocSize(new Long(message.length()));
              refDocAttachment.setAttachment(message);
              refDocAttachmentCreator.createRefDocAttachment(refDocAttachment);
              return referenceDocument.getId();
         }
         catch (Exception e) {
             //HM changed error message 
             logger.error("Error storing Global Definitions MIF message.", e);
             throw new DataAccessException("Error storing Global Definitions MIF message.", e);
         }
     }

     public ReferenceDocumentAttachment queryGlobalDefinitionsMIFMessage(String formIdSeq, Date createDate) throws DataAccessException {
           try {
               if (queryRefDocAttachment == null){
                   queryRefDocAttachment = new QueryRefDocAttachment(dataSource);
               }
               ReferenceDocument rd = new ReferenceDocument();
               rd.setType(GLOBALDEFINITIONS_REF_DOC_TYPE);
               Form form = queryCaDSRForm(formIdSeq);
               rd.setName(getRefDocName(form,createDate));
               List refDocs = appService.search(ReferenceDocument.class,rd);
               if (refDocs.size() == 0) {
                   throw new DataAccessException("Global Definitions MIF Message Reference Document not found for form "+formIdSeq+" date "+formatter.format(createDate));
               }
               ReferenceDocument referenceDocument = (ReferenceDocument)refDocs.get(0);
               ReferenceDocumentAttachment rda = queryRefDocAttachment.query(getAttachmentName(referenceDocument));
               rda.setReferenceDocument(referenceDocument);
               
               return rda;
           }
           catch(Exception e) {
               logger.error("Error querying ReferenceDocumentAttachment.",e);
               throw new DataAccessException("Error querying ReferenceDocumentAttachment.",e);
           }
     }
     
  
    public ReferenceDocumentAttachment queryGlobalDefinitionsMIFMessage(String rdIdSeq) throws DataAccessException {
        try {
            if (queryRefDocAttachment == null){
                queryRefDocAttachment = new QueryRefDocAttachment(dataSource);
            }
            ReferenceDocument rd = new ReferenceDocument();
            rd.setId(rdIdSeq);
            List refDocs = appService.search(ReferenceDocument.class,rd);
            if (refDocs.size() == 0) {
                throw new DataAccessException("Instrument Message Reference Document not found for "+rdIdSeq);
            }
            ReferenceDocument referenceDocument = (ReferenceDocument)refDocs.get(0);
            ReferenceDocumentAttachment rda = queryRefDocAttachment.query(getAttachmentName(referenceDocument));
            rda.setReferenceDocument(referenceDocument);
            
            return rda;
        }
        catch(Exception e) {
            logger.error("Error querying ReferenceDocumentAttachment.",e);
            throw new DataAccessException("Error querying ReferenceDocumentAttachment.",e);
        }        
    }
    
   
   
}
