package gov.nih.nci.ncicb.cadsr.dao.impl;

import gov.nih.nci.cadsr.domain.Form;
import gov.nih.nci.cadsr.domain.Module;
import gov.nih.nci.cadsr.domain.Question;
import gov.nih.nci.cadsr.domain.ReferenceDocument;
import gov.nih.nci.ncicb.cadsr.dao.DataAccessException;
import gov.nih.nci.ncicb.cadsr.dao.GlobalDefinitionsDAO;
import gov.nih.nci.ncicb.cadsr.edci.domain.DataElement;
import gov.nih.nci.ncicb.cadsr.edci.domain.DataElementGroup;
import gov.nih.nci.ncicb.cadsr.edci.domain.GlobalDefinitions;
import gov.nih.nci.system.applicationservice.ApplicationService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


public class GlobalDefinitionsDAOImpl  extends EDCIDAOImpl implements GlobalDefinitionsDAO
{
   
    private static Logger logger = LogManager.getLogger(GlobalDefinitionsDAO.class);
    
    public GlobalDefinitionsDAOImpl() 
    {
    }
    
    public GlobalDefinitions getGlobalDefinitions(String formIdSeq) throws DataAccessException {
        Form form = new Form();
        form.setId(formIdSeq);
        GlobalDefinitions globalDefinitions = new GlobalDefinitions();
        try {
          ApplicationService applicationService = serviceLocator.getCaDSRPublicApiService();
          List forms = applicationService.search(Form.class.getName(), form);
          Form qForm = (Form)forms.get(0);
          if (qForm != null)
          {
              Collection<Module> modules = qForm.getModuleCollection();
              ArrayList<DataElement> dataElements = new ArrayList<DataElement>();
              for (Module module:modules)   {
                  DataElementGroup dataElementGroup = new DataElementGroup();
                  dataElementGroup.setDescription(module.getPreferredDefinition());
                  dataElementGroup.setName(module.getLongName());
                  dataElementGroup.setNamespace("NCI");
                  Collection<Question> questions = module.getQuestionCollection();
                  for (Question question:questions){
                      DataElement eDCIDE = new DataElement();
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
                      eDCIDE.setNamespace("NCI");
                      eDCIDE.setDataElementConceptGUID(dE.getDataElementConcept().getId());
                      eDCIDE.setValueDomainGUID(dE.getValueDomain().getId());
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
