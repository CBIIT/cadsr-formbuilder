package gov.nih.nci.ncicb.cadsr.dao.impl;

import gov.nih.nci.cadsr.domain.EnumeratedValueDomain;
import gov.nih.nci.cadsr.domain.Form;
import gov.nih.nci.cadsr.domain.Module;
import gov.nih.nci.cadsr.domain.Question;
import gov.nih.nci.cadsr.domain.ReferenceDocument;
import gov.nih.nci.cadsr.domain.ValidValue;
import gov.nih.nci.cadsr.domain.ValueDomainPermissibleValue;
import gov.nih.nci.cadsr.domain.ValueMeaning;
import gov.nih.nci.ncicb.cadsr.dao.DataAccessException;
import gov.nih.nci.ncicb.cadsr.dao.GlobalDefinitionsDAO;
import gov.nih.nci.ncicb.cadsr.edci.domain.*;
import gov.nih.nci.system.applicationservice.ApplicationService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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
        GlobalDefinitions globalDefinitions = domainObjectFactory.getGlobalDefinitions();
        globalDefinitions.setActivityTime(new Date());
        try {
          ApplicationService applicationService = serviceLocator.getCaDSRPublicApiService();
          List forms = applicationService.search(Form.class.getName(), form);
          Form qForm = (Form)forms.get(0);
          if (qForm != null)
          {
              Collection<Module> modules = qForm.getModuleCollection();
              ArrayList<DataElement> dataElements = new ArrayList<DataElement>();
              for (Module module:modules)   {
                  //Create the DataElementGroup
                  DataElementGroup dataElementGroup = domainObjectFactory.getDataElementGroup();
                  dataElementGroup.setDescription(module.getPreferredDefinition());
                  dataElementGroup.setName(module.getLongName());
                  dataElementGroup.setNamespace("NCI");
                  dataElementGroup.setGUID(geteDCIGUID(getGUID()));
                  globalDefinitions.addDataElementGroup(dataElementGroup);
                  Collection<Question> questions = module.getQuestionCollection();
                  for (Question question:questions){
                      //Create the DataElement
                      DataElement eDCIDE = domainObjectFactory.getDataElement();
                      gov.nih.nci.cadsr.domain.DataElement dE = question.getDataElement();
                      eDCIDE.setDefinition(dE.getPreferredDefinition());
                      Collection<ReferenceDocument> referenceDocuments = dE.getReferenceDocumentCollection();
                      for (ReferenceDocument referenceDocument:referenceDocuments){
                          if (referenceDocument.getType().equals("Description")) {
                              eDCIDE.setDescription(referenceDocument.getDoctext());
                          }
                          if (referenceDocument.getType().equals("Preferred Question Text")) {
                              DataElementText dataElementText = domainObjectFactory.getDataElementText();
                              dataElementText.setLanguage(referenceDocument.getLanguageName());
                              dataElementText.setPrompt(referenceDocument.getDoctext());
                              eDCIDE.addDataElementText(dataElementText);
                          }
                      }
                      eDCIDE.setGUID(geteDCIGUID(dE.getId()));
                      eDCIDE.setName(dE.getLongName());
                      eDCIDE.setNamespace("NCI");
                      eDCIDE.setDataElementConceptGUID(dE.getDataElementConcept().getId());
                      eDCIDE.setValueDomainGUID(geteDCIGUID(dE.getValueDomain().getId()));
                      dataElementGroup.addDataElement(eDCIDE);
                      globalDefinitions.addDataElement(eDCIDE);
                      //Create the DataElementConcept
                      DataElementConcept eDCIDEC = domainObjectFactory.getDataElementConcept();
                      gov.nih.nci.cadsr.domain.DataElementConcept dec = dE.getDataElementConcept();
                      eDCIDEC.setDefinition(dec.getPreferredDefinition());
                      eDCIDEC.setName(dec.getLongName());
                      eDCIDEC.setGUID(geteDCIGUID(dec.getId()));
                      Collection<ReferenceDocument> decReferenceDocuments = dec.getReferenceDocumentCollection();
                      for (ReferenceDocument referenceDocument:decReferenceDocuments){
                          if (referenceDocument.getType().equals("Description")) {
                              eDCIDEC.setDescription(referenceDocument.getDoctext());break;
                          }
                      }
                      globalDefinitions.addDataElementConcept(eDCIDEC);
                      //Get the ValueDomain
                      ValueDomain eDCIVD = domainObjectFactory.getValueDomain();
                      gov.nih.nci.cadsr.domain.ValueDomain vd = dE.getValueDomain();
                      eDCIVD.setDatatype(vd.getDatatypeName());
                      eDCIVD.setDecimalPlaces(vd.getDecimalPlace()==null?0:vd.getDecimalPlace().intValue());
                      eDCIVD.setDescription(vd.getPreferredDefinition());
                      eDCIVD.setGUID(geteDCIGUID(vd.getId()));
                      eDCIVD.setMaximumLength(vd.getMaximumLengthNumber());
                      eDCIVD.setName(vd.getLongName());
                      eDCIVD.setNamespace("NCI");
                      if (vd instanceof EnumeratedValueDomain) {
                          eDCIVD.setIsEnumeratedFlag(true);
                      }else {
                          eDCIVD.setIsEnumeratedFlag(false);
                      }
                      if (eDCIVD.getIsEnumeratedFlag()) {
                          EVDSubset defaultSubset = domainObjectFactory.getEVDSubset();
                          defaultSubset.setSubsetId(geteDCIGUID(getGUID()));
                          Collection<ValueDomainPermissibleValue>  vdpvs = ((EnumeratedValueDomain)vd).getValueDomainPermissibleValueCollection();
                          Collection<ValidValue> qValidValues = question.getValidValueCollection();
                          
                          for(ValueDomainPermissibleValue vdpv: vdpvs)
                          {
                              EVDElement evdElement = domainObjectFactory.getEVDElement();
                              evdElement.setValue(vdpv.getPermissibleValue().getValue());
                              ValueMeaning valueMeaning = vdpv.getPermissibleValue().getValueMeaning();
                              EVDElementText evdElementText = domainObjectFactory.getEVDElementText();
                              evdElementText.setValueMeaning(valueMeaning.getShortMeaning());
                              evdElementText.setValueMeaningDescription(valueMeaning.getShortMeaning());
                              evdElementText.setLanguage("en");
                              evdElement.addEVDElementText(evdElementText);
                              ElementInSubset eis = domainObjectFactory.getElementInSubset();
                              eis.setValue(vdpv.getPermissibleValue().getValue());
                              for (ValidValue validValue: qValidValues)
                              {
                                 if (validValue.getValueDomainPermissibleValue().getId().equals(vdpv.getId()))
                                 {
                                   eis.setSequenceNumber(validValue.getDisplayOrder());
                                   break;
                                 }
                              }
                              defaultSubset.addElementInSubset(eis);
                              eDCIVD.addEVDElement(evdElement);                            
                          }
                          eDCIVD.addEVDSubset(defaultSubset);
                      }
                      globalDefinitions.addValueDomain(eDCIVD);
                  }
              }

          }
        }catch(Exception e) {
            logger.error("Error querying DataElements.", e);
            throw new DataAccessException("Error querying DataElements.", e);
        }
        return globalDefinitions;
    }


}
