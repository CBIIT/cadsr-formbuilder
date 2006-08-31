package gov.nih.nci.ncicb.cadsr.dao.impl;

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
                      DataElementConcept eDCIDEC = domainObjectFactory.getDataElementConcept();
                      ValueDomain eDCIVD = domainObjectFactory.getValueDomain();
                      gov.nih.nci.cadsr.domain.DataElement dE = question.getDataElement();
                      eDCIDE.setDefinition(dE.getPreferredDefinition());
                      Collection<ReferenceDocument> referenceDocuments = dE.getReferenceDocumentCollection();
                      for (ReferenceDocument referenceDocument:referenceDocuments){
                          if (referenceDocument.getType().equals("Description")) {
                              eDCIDE.setDescription(referenceDocument.getDoctext());
                              break;
                          }
                      }
                      eDCIDE.setGUID(dE.getId());
                      eDCIDE.setName(dE.getLongName());
                      eDCIDE.setNamespace(DefaultEDCIValues.NAMESPACE);
                      eDCIDE.setDataElementConceptGUID(dE.getDataElementConcept().getId());
                      
                      // Set Data Element concept for the Data Element
                      gov.nih.nci.cadsr.domain.DataElementConcept dEC = question.getDataElement().getDataElementConcept();
                      eDCIDEC.setGUID(dEC.getId());
                      eDCIDEC.setDefinition(dEC.getPreferredDefinition());
                      eDCIDEC.setName(dEC.getLongName());
                      eDCIDEC.setDescription(dEC.getConceptualDomain().getPreferredDefinition());
                      
                      // Set Value Domain for the Data Element
                     
                      eDCIDE.setValueDomainGUID(dE.getValueDomain().getId());
                      gov.nih.nci.cadsr.domain.ValueDomain vD = question.getDataElement().getValueDomain();
                      eDCIVD.setDatatype(vD.getDatatypeName());
                      eDCIVD.setDecimalPlaces(vD.getDecimalPlace());
                      eDCIVD.setDescription(vD.getPreferredDefinition());
                      eDCIVD.setGUID(vD.getId());
                      eDCIVD.setMaximumLength(vD.getMaximumLengthNumber());
                      eDCIVD.setName(vD.getLongName());
                      eDCIVD.setNamespace(DefaultEDCIValues.NAMESPACE);
                      
                      //if the value domain is enumerated then
                      if (vD instanceof EnumeratedValueDomain) {
                             eDCIVD.setIsEnumeratedFlag(true);
                             EnumeratedValueDomain eVD = (EnumeratedValueDomain) vD;
                             
                             // base subset based on then value domain
                             EVDSubset baseEVDS = domainObjectFactory.getEVDSubset();
                             baseEVDS.setBaseSubsetFlag(true);
                             
                             // subset based on use in question
                             EVDSubset eVDSS = domainObjectFactory.getEVDSubset();
                             eVDSS.setBaseSubsetFlag(false);
                             
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
                                  }
                     else if (vD instanceof NonenumeratedValueDomain) {
                                      eDCIVD.setIsEnumeratedFlag(false);
                                  }
                      
                   
                      dataElements.add(eDCIDE);
                      dataElementConcepts.add(eDCIDEC);
                      valueDomains.add(eDCIVD);
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


}
