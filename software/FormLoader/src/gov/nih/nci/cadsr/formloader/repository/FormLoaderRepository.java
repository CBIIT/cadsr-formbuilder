package gov.nih.nci.cadsr.formloader.repository;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import gov.nih.nci.cadsr.formloader.domain.FormDescriptor;
import gov.nih.nci.ncicb.cadsr.common.dto.ModuleTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.QuestionTransferObject;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc.JDBCFormDAOV2;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc.JDBCModuleDAOV2;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc.JDBCQuestionDAOV2;
import gov.nih.nci.ncicb.cadsr.common.resource.FormV2;
import gov.nih.nci.ncicb.cadsr.common.resource.Module;
import gov.nih.nci.ncicb.cadsr.common.resource.ReferenceDocument;

@Repository
public class FormLoaderRepository {
	private static Logger logger = Logger.getLogger(FormLoaderRepository.class.getName());
	
	@Autowired
	JDBCFormDAOV2 formV2Dao;
	
	@Autowired
	JDBCModuleDAOV2 moduleV2Dao;
	
	@Autowired
	JDBCQuestionDAOV2 questionV2Dao;
	
	

	/**
	 * Gets Seq id, public id and version for forms with the given public ids
	 * @param pubicIDList
	 * @return
	 */
	public List<FormV2> getFormsForPublicIDs(List<String> pubicIDList) {
		if (pubicIDList == null || pubicIDList.size() ==0) {
			logger.debug("public id list is null or empty. Do nothing.");
			return null;
		}
		
		//Seq id, public id and version are set in the returned forms
		List<FormV2> formDtos = formV2Dao.getVersionsByPublicIds(pubicIDList);
		logger.debug("Dao returns " + formDtos.size() + " forms with " + pubicIDList.size() + "public ids");
		
		return formDtos;
	}
	
	public List<ModuleTransferObject> getModulesInForm(String formSeqId) {
		if (formSeqId == null || formSeqId.length() == 0) {
			logger.debug("Form seq id is null or empty. Do nothing");
			return null;
		}
		
		List<ModuleTransferObject> modules = formV2Dao.getModulesInAForm(formSeqId);
		logger.debug("Dao returns " + modules.size() + " modules for form [" + formSeqId + "]");
		
		return modules;	
	}
	
	public List<QuestionTransferObject> getQuestionsInModule(String moduleSeqId) {
		if (moduleSeqId == null || moduleSeqId.length() == 0) {
			logger.debug("Module seq id is null or empty. Do nothing");
			return null;
		}
		
		List<QuestionTransferObject> questions = moduleV2Dao.getQuestionsInAModuleV2(moduleSeqId);
		logger.debug("Dao returns " + questions.size() + " questions for module [" + moduleSeqId + "]");
		return questions;
	}
	
	public List<ReferenceDocument> getReferenceDocsForQuestionCde(String cdePublicId, String cdeVersion) {
		
		List<ReferenceDocument> deRefDocs = questionV2Dao.getAllReferenceDocuments(cdePublicId, cdeVersion);
			   
		
		//  getReferenceDocuments(term.getDataElement().getDeIdseq());
			    // term.getDataElement().setReferenceDocs(deRefDocs);
		
	/*	
		private List<ReferenceDocument> getReferenceDocuments(String acIdseq)
	    {
	        ReferenceDocumentDAO myDAO = (ReferenceDocumentDAO)daoFactory.getReferenceDocumentDAO();
	        List refDocs = myDAO.getAllReferenceDocuments(acIdseq, 
	                        null);
	        return refDocs;        
	    }
		*/	     
		return deRefDocs;
	}
	
	 
}
