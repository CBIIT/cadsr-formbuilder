package gov.nih.nci.cadsr.formloader.repository;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import gov.nih.nci.cadsr.formloader.domain.FormDescriptor;
import gov.nih.nci.ncicb.cadsr.common.dto.DataElementTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.ModuleTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.QuestionTransferObject;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc.JDBCFormDAOV2;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc.JDBCModuleDAOV2;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc.JDBCQuestionDAOV2;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc.JDBCValueDomainDAOV2;
import gov.nih.nci.ncicb.cadsr.common.resource.FormV2;
import gov.nih.nci.ncicb.cadsr.common.resource.Module;
import gov.nih.nci.ncicb.cadsr.common.resource.PermissibleValueV2;
import gov.nih.nci.ncicb.cadsr.common.resource.ReferenceDocument;
import gov.nih.nci.ncicb.cadsr.common.resource.ValueDomainV2;

@Repository
public class FormLoaderRepository {
	private static Logger logger = Logger.getLogger(FormLoaderRepository.class.getName());
	
	@Autowired
	JDBCFormDAOV2 formV2Dao;
	
	@Autowired
	JDBCModuleDAOV2 moduleV2Dao;
	
	@Autowired
	JDBCQuestionDAOV2 questionV2Dao;
	
	@Autowired
	JDBCValueDomainDAOV2 valueDomainV2Dao;
	

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
	
	public List<QuestionTransferObject> getQuestionsByPublicId(String publicId) {
		if (publicId == null || publicId.length() == 0) {
			logger.debug("Question public id is null or empty. Unable to querry db.");
			return null;
		}
		
		int pubId = Integer.parseInt(publicId);
		
		List<QuestionTransferObject> questions = questionV2Dao.getQuestionsByPublicId(pubId);
		logger.debug("Dao returns " + questions.size() + " questions.");
		return questions;
	}
	
	public List<QuestionTransferObject> getQuestionsByPublicIdAndVersion(String publicId, String version) {
		if (publicId == null || publicId.length() == 0) {
			logger.debug("Question public id is null or empty. Unable to querry db.");
			return null;
		}
		
		if (version == null || version.length() == 0) {
			logger.debug("Question version is null or empty. Unable to querry db.");
			return null;
		}
		
		int pubId = Integer.parseInt(publicId);
		float vers = Float.parseFloat(version);
		List<QuestionTransferObject> questions = questionV2Dao.getQuestionByPublicIdAndVersion(pubId, vers);
		logger.debug("Dao returns " + questions.size() + " questions.");
		return questions;
	}
	
	public List<DataElementTransferObject> getCDEByPublicId(String cdePublicId) {
		if (cdePublicId == null || cdePublicId.length() == 0) {
			logger.debug("Question's CDE public id is null or empty. Unable to querry db.");
			return null;
		}
		
		List<DataElementTransferObject> des = questionV2Dao.getCdesByPublicId(cdePublicId);
		logger.debug("Dao returns " + des.size() + " CDEs");
		
		return des;
	}
	
	public List<ReferenceDocument> getReferenceDocsForQuestionCde(String cdePublicId, String cdeVersion) {
		if (cdePublicId == null || cdePublicId.length() == 0) {
			logger.debug("Question's CDE public id is null or empty. Unable to querry db.");
			return null;
		}
		
		if (cdeVersion == null || cdeVersion.length() == 0) {
			logger.debug("Question CDE version is null or empty. Unable to querry db.");
			return null;
		}
		
		List<ReferenceDocument> deRefDocs = questionV2Dao.getAllReferenceDocuments(cdePublicId, cdeVersion);
		logger.debug("Dao returns " + deRefDocs.size() + " CDE reference docs.");
		
		return deRefDocs;
	}
	
	public ValueDomainV2 getValueDomainBySeqId(String vdSeqId) {
		if (vdSeqId == null || vdSeqId.length() == 0) {
			logger.debug("Value domain seq id is null or empty. Unable to querry db.");
			return null;
		}
		
		ValueDomainV2 vd = valueDomainV2Dao.getValueDomainV2ById(vdSeqId);
		String msg = "Dao returns ";
		if (vd == null)
			msg += "null ValueDomainV2 obj";
		else {
			List<PermissibleValueV2> pValues = vd.getPermissibleValueV2();
			if (pValues == null || pValues.size() == 0)
				msg += "a value domain obj with 0 permissible value.";
			else
				msg += "a value domain obj with " + pValues.size() + " permissible values";
		}
		logger.debug(msg);
		return vd;
	}
	
	 
}
