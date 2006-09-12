package gov.nih.nci.ncicb.cadsr.dao.impl;

import gov.nih.nci.cadsr.domain.AdministeredComponent;
import gov.nih.nci.cadsr.domain.Designation;
import gov.nih.nci.cadsr.domain.EnumeratedValueDomain;
import gov.nih.nci.cadsr.domain.Form;
import gov.nih.nci.cadsr.domain.Module;
import gov.nih.nci.cadsr.domain.NonenumeratedValueDomain;
import gov.nih.nci.cadsr.domain.Question;
import gov.nih.nci.cadsr.domain.ReferenceDocument;
import gov.nih.nci.cadsr.domain.ValidValue;
import gov.nih.nci.cadsr.domain.ValueDomainPermissibleValue;
import gov.nih.nci.ncicb.cadsr.constants.DefaultEDCIValues;
import gov.nih.nci.ncicb.cadsr.dao.DataAccessException;
import gov.nih.nci.ncicb.cadsr.dao.DomainObjectFactory;
import gov.nih.nci.ncicb.cadsr.dao.GlobalDefinitionsDAO;
import gov.nih.nci.ncicb.cadsr.dao.impl.CaDSRApiDAOImpl;
import gov.nih.nci.ncicb.cadsr.edci.domain.*;
import gov.nih.nci.system.applicationservice.ApplicationService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


public class GlobalDefinitionsDAOImpl  extends CaDSRApiDAOImpl implements GlobalDefinitionsDAO
{
   
    private static Logger logger = LogManager.getLogger(GlobalDefinitionsDAO.class);
    
    public GlobalDefinitionsDAOImpl() 
    {
    }
    
    public GlobalDefinitions getGlobalDefinitions(String formIdSeq) throws DataAccessException {
        Form form = new Form();
        form.setId(formIdSeq);
        //DomainObjectFactory domainObjectFactory = getDomainObjectFactory();
        GlobalDefinitions globalDefinitions = domainObjectFactory.getGlobalDefinitions();
        try {
          ApplicationService applicationService = serviceLocator.getCaDSRPublicApiService();
          List forms = applicationService.search(Form.class.getName(), form);
          Form qForm = (Form)forms.get(0);
          if (qForm != null)
          {
              Collection<Module> modules = qForm.getModuleCollection();
              ArrayList<DataElement> dataElements = new ArrayList<DataElement>();
              ArrayList<DataElementConcept> dataElementConcepts = new ArrayList<DataElementConcept>();
              ArrayList<ValueDomain> valueDomains = new ArrayList<ValueDomain>();
              
              for (Module module:modules)   {
                  DataElementGroup dataElementGroup = domainObjectFactory.getDataElementGroup();
                  dataElementGroup.setDescription(module.getPreferredDefinition());
                  dataElementGroup.setName(module.getLongName());
                  dataElementGroup.setNamespace("NCI");
                  Collection<Question> questions = module.getQuestionCollection();
                  for (Question question:questions){
                      DataElement eDCIDE = domainObjectFactory.getDataElement();
                      gov.nih.nci.cadsr.domain.DataElement dE = question.getDataElement();
                      eDCIDE.setDefinition(dE.getPreferredDefinition());
                      eDCIDE.setDescription(getDocText(dE,"Description"));
                      eDCIDE.setDataElementTextCollection(getDataElementTextCollection(dE));
                      eDCIDE.setAlternateDesignationCollection(getAlternateDesignationCollection(dE));
                      eDCIDE.setGUID(dE.getId());
                      eDCIDE.setName(dE.getLongName());
                      eDCIDE.setNamespace(DefaultEDCIValues.NAMESPACE);
                      eDCIDE.setDataElementConceptGUID(dE.getDataElementConcept().getId());
                      eDCIDE.setValueDomainGUID(dE.getValueDomain().getId());
                      //if the value domain is enumerated then
                      dataElements.add(eDCIDE);
                      dataElementConcepts.add(getDataElementConcept(dE));
                      valueDomains.add(getValueDomain(dE,question));
                  }
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
    
  protected DataElementConcept getDataElementConcept(gov.nih.nci.cadsr.domain.DataElement dE){
      DataElementConcept eDCIDEC = domainObjectFactory.getDataElementConcept();
      gov.nih.nci.cadsr.domain.DataElementConcept dEC = dE.getDataElementConcept();
      eDCIDEC.setGUID(dEC.getId());
      eDCIDEC.setDefinition(dEC.getPreferredDefinition());
      eDCIDEC.setName(dEC.getLongName());
      eDCIDEC.setDescription(dEC.getConceptualDomain().getPreferredDefinition());
      return eDCIDEC;
  }

  protected ValueDomain getValueDomain(gov.nih.nci.cadsr.domain.DataElement dE, Question question) throws DataAccessException{
      ValueDomain eDCIVD = domainObjectFactory.getValueDomain();
      gov.nih.nci.cadsr.domain.ValueDomain vD = dE.getValueDomain();
      eDCIVD.setDatatype(vD.getDatatypeName());
      if (vD.getDecimalPlace()!=null){
      eDCIVD.setDecimalPlaces(vD.getDecimalPlace());
      }
      eDCIVD.setDescription(vD.getPreferredDefinition());
      eDCIVD.setGUID(vD.getId());
      eDCIVD.setMaximumLength(vD.getMaximumLengthNumber());
      eDCIVD.setName(vD.getLongName());
      eDCIVD.setNamespace(DefaultEDCIValues.NAMESPACE);
      eDCIVD.setSourceCodingSystem(getDocText(vD,"VD Reference"));
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
                          eVDET.setLanguage(DefaultEDCIValues.LANGUAGE);
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
  
  protected String getDocText(AdministeredComponent ac, String documentType){
        String docText;
        docText = null;
        Collection<ReferenceDocument> referenceDocuments = ac.getReferenceDocumentCollection();
      
      for (ReferenceDocument referenceDocument:referenceDocuments){
      
          if (referenceDocument.getType().equals(documentType)) {
              docText =referenceDocument.getDoctext();
              break;
          }
         
      
      }
      
      return docText;
  }
  
  protected Collection <DataElementText> getDataElementTextCollection(gov.nih.nci.cadsr.domain.DataElement dE){
      Collection<ReferenceDocument> referenceDocuments = dE.getReferenceDocumentCollection();
      
      
       Collection<DataElementText> dETC = new ArrayList <DataElementText> ();
      
       for (ReferenceDocument referenceDocument:referenceDocuments){
       
           if (referenceDocument.getType().equals("Preferred Question Text") || referenceDocument.getType().equals("Alternate Question Text")){
               DataElementText dET = domainObjectFactory.getDataElementText();
               dET.setPrompt(referenceDocument.getDoctext());
               dET.setLanguage(referenceDocument.getLanguageName());
               dETC.add(dET);
           }
       
       }       
       if (dETC.isEmpty()){
           DataElementText dET = domainObjectFactory.getDataElementText();
           dET.setPrompt(dE.getLongName());
           dET.setLanguage(DefaultEDCIValues.LANGUAGE);
           dETC.add(dET);
       }
       
       return dETC;
  }
  
  
    protected Collection <AlternateDesignation> getAlternateDesignationCollection(gov.nih.nci.cadsr.domain.DataElement dE){
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
}
