package gov.nih.nci.cadsr.formloader.repository;

import gov.nih.nci.ncicb.cadsr.common.dto.DataElementTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.FormV2TransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.ModuleTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.PermissibleValueV2TransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.QuestionTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.ReferenceDocumentTransferObject;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc.JDBCFormDAOV2;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc.JDBCModuleDAOV2;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc.JDBCQuestionDAOV2;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc.JDBCValueDomainDAOV2;
import gov.nih.nci.ncicb.cadsr.common.resource.FormV2;
import gov.nih.nci.ncicb.cadsr.common.resource.PermissibleValueV2;
import gov.nih.nci.ncicb.cadsr.common.resource.ReferenceDocument;
import gov.nih.nci.ncicb.cadsr.common.resource.ValueDomainV2;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

@Repository
public class FormLoaderRepositoryImpl implements FormLoaderRepository {
	private static Logger logger = Logger.getLogger(FormLoaderRepositoryImpl.class.getName());
	
	JDBCFormDAOV2 formV2Dao;
	JDBCModuleDAOV2 moduleV2Dao;
	JDBCQuestionDAOV2 questionV2Dao;
	JDBCValueDomainDAOV2 valueDomainV2Dao;
	

	/**
	 * Gets Seq id, public id and version for forms with the given public ids
	 * @param pubicIDList
	 * @return
	 */
	public List<FormV2> getFormsForPublicIDs(List<String> pubicIDList) {
		if (pubicIDList == null || pubicIDList.size() ==0) {
			logger.debug("getFormsForPublicIDs(): public id list is null or empty. Do nothing.");
			return null;
		}
		
		//Seq id, public id and version are set in the returned forms
		List<FormV2> formDtos = formV2Dao.getExistingVersionsForPublicIds(pubicIDList);
		logger.debug("getFormsForPublicIDs() returns " + formDtos.size() + " forms with " + pubicIDList.size() 
				+ " public ids");
		
		return formDtos;
	}
	
	public List<QuestionTransferObject> getQuestionsByPublicId(String publicId) {
		if (publicId == null || publicId.length() == 0) {
			logger.debug("getQuestionsByPublicId(): Question public id is null or empty. Unable to querry db.");
			return null;
		}
		
		int pubId = Integer.parseInt(publicId);
		
		List<QuestionTransferObject> questions = questionV2Dao.getQuestionsByPublicId(pubId);
		logger.debug("getQuestionsByPublicId(): Dao returns " + questions.size() + " questions.");
		return questions;
	}
	
	public List<QuestionTransferObject> getQuestionsByPublicIds(List<String> publicIds) {
		if (publicIds == null || publicIds.size() == 0) {
			logger.debug("getQuestionsByPublicIds(): Question public id list is null or empty. Unable to querry db.");
			return null;
		}
			
		List<QuestionTransferObject> questions = questionV2Dao.getQuestionsByPublicIds(publicIds);
		logger.debug("getQuestionsByPublicId(): Dao returns " + questions.size() + " questions.");
		return questions;
	}
	
	public List<DataElementTransferObject> getCDEByPublicId(String cdePublicId) {
		if (cdePublicId == null || cdePublicId.length() == 0) {
			logger.debug("getCDEByPublicId(): Question's CDE public id is null or empty. Unable to querry db.");
			return null;
		}
		
		List<DataElementTransferObject> des = questionV2Dao.getCdesByPublicId(cdePublicId);
		logger.debug("getCDEByPublicId(): Dao returns " + des.size() + " CDEs");
		
		return des;
	}
	
	public List<DataElementTransferObject> getCDEsByPublicIds(List<String> cdePublicIds) {
		if (cdePublicIds == null || cdePublicIds.size() == 0) {
			logger.debug("getCDEsByPublicIds(): cde public id list is null or empty. Unable to querry db.");
			return null;
		}
		List<DataElementTransferObject> des = questionV2Dao.getCdesByPublicIds(cdePublicIds);
		logger.debug("getCDEsByPublicIds(): Dao returns " + des.size() + " CDEs");
		
		return des;
	}
	
	public List<ReferenceDocumentTransferObject> getReferenceDocsForQuestionCde(String cdePublicId, String cdeVersion) {
		if (cdePublicId == null || cdePublicId.length() == 0) {
			logger.debug("getReferenceDocsForQuestionCde(): Question's CDE public id is null or empty. Unable to querry db.");
			return null;
		}
		
		if (cdeVersion == null || cdeVersion.length() == 0) {
			logger.debug("getReferenceDocsForQuestionCde(): Question CDE version is null or empty. Unable to querry db.");
			return null;
		}
		
		List<ReferenceDocumentTransferObject> deRefDocs = 
				questionV2Dao.getAllReferenceDocuments(
						Integer.parseInt(cdePublicId), Float.parseFloat(cdeVersion));
		logger.debug("getReferenceDocsForQuestionCde(): Dao returns " + deRefDocs.size() + " CDE reference docs.");
		
		return deRefDocs;
	}
	
	
	public HashMap<String, List<ReferenceDocumentTransferObject>> getReferenceDocsByCdePublicIds(List<String> cdePublicIds) {
		if (cdePublicIds == null || cdePublicIds.size() == 0) {
			logger.debug("getReferenceDocsByCdePublicIds(): cde public id list is null or empty. Unable to querry db.");
			return null;
		}
		
		HashMap<String, List<ReferenceDocumentTransferObject>> deRefDocs = 
				questionV2Dao.getReferenceDocumentsByCdePublicIds(cdePublicIds);
				
				
		logger.debug("getReferenceDocsByCdePublicIds(): Dao returns " + deRefDocs.size() + " CDE reference docs.");
		
		return deRefDocs;
	}
	
	public List<PermissibleValueV2TransferObject> getValueDomainPermissibleValuesByVdId(String vdSeqId) {
		if (vdSeqId == null || vdSeqId.length() == 0) {
			logger.debug("getValueDomainBySeqId(): Value domain seq id is null or empty. Unable to querry db.");
			return null;
		}
		
		List<PermissibleValueV2TransferObject> pValues = valueDomainV2Dao.getPermissibleValuesByVdId(vdSeqId);
		String msg = "getValueDomainPermissibleValuesByVdId(): Dao returns ";
		if (pValues == null || pValues.size() == 0)
			msg += "a value domain obj with 0 permissible value.";
		else
			msg += "a value domain obj with " + pValues.size() + " permissible values";
		
		logger.debug("msg");
		return pValues;
	}
	
	public HashMap<String, List<PermissibleValueV2TransferObject>> getPermissibleValuesByVdIds(List<String> vdSeqIds) {
		if (vdSeqIds == null || vdSeqIds.size() == 0) {
			logger.debug("getPermissibleValuesByVdIds(): vdSeqIds list is null or empty. Unable to querry db.");
			return null;
		}
		
		HashMap<String, List<PermissibleValueV2TransferObject>> pValues = 
				valueDomainV2Dao.getPermissibleValuesByVdIds(vdSeqIds);
		
		String msg = "getPermissibleValuesByVdIds(): Dao returns ";
		if (pValues == null || pValues.size() == 0)
			msg += "a value domain obj with 0 permissible value.";
		else
			msg += "a value domain obj with " + pValues.size() + " permissible values";
		
		logger.debug("msg");
		return pValues;
	}
	
	//Not used
	public ValueDomainV2 getValueDomainBySeqId(String vdSeqId) {
		if (vdSeqId == null || vdSeqId.length() == 0) {
			logger.debug("getValueDomainBySeqId(): Value domain seq id is null or empty. Unable to querry db.");
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
		
		logger.debug("getValueDomainBySeqId(): " + msg);
		return vd;
	}
	
	//not used
	public List<ModuleTransferObject> getModulesInForm(String formSeqId) {
		if (formSeqId == null || formSeqId.length() == 0) {
			logger.debug("getModulesInForm():Form seq id is null or empty. Do nothing");
			return null;
		}

		List<ModuleTransferObject> modules = formV2Dao.getModulesInAForm(formSeqId);
		logger.debug("getModulesInForm(): Dao returns " + modules.size() + " modules for form [" + formSeqId + "]");

		return modules;	
	}

	//not used
	public List<QuestionTransferObject> getQuestionsInModule(String moduleSeqId) {
		if (moduleSeqId == null || moduleSeqId.length() == 0) {
			logger.debug("getQuestionsInModule(): Module seq id is null or empty. Do nothing");
			return null;
		}

		List<QuestionTransferObject> questions = moduleV2Dao.getQuestionsInAModuleV2(moduleSeqId);
		logger.debug("getQuestionsInModule(): Dao returns " + questions.size() + " questions for module [" + moduleSeqId + "]");
		return questions;
	}
	
	//Not used
	public List<QuestionTransferObject> getQuestionsByPublicIdAndVersion(String publicId, String version) {
		if (publicId == null || publicId.length() == 0) {
			logger.debug("getQuestionsByPublicIdAndVersion(): Question public id is null or empty. Unable to querry db.");
			return null;
		}

		if (version == null || version.length() == 0) {
			logger.debug("getQuestionsByPublicIdAndVersion(): Question version is null or empty. Unable to querry db.");
			return null;
		}

		int pubId = Integer.parseInt(publicId);
		float vers = Float.parseFloat(version);
		List<QuestionTransferObject> questions = questionV2Dao.getQuestionByPublicIdAndVersion(pubId, vers);
		logger.debug("getQuestionsByPublicIdAndVersion(): Dao returns " + questions.size() + " questions.");
		return questions;
	}

	public JDBCFormDAOV2 getFormV2Dao() {
		return formV2Dao;
	}

	public void setFormV2Dao(JDBCFormDAOV2 formV2Dao) {
		this.formV2Dao = formV2Dao;
	}

	public JDBCModuleDAOV2 getModuleV2Dao() {
		return moduleV2Dao;
	}

	public void setModuleV2Dao(JDBCModuleDAOV2 moduleV2Dao) {
		this.moduleV2Dao = moduleV2Dao;
	}

	public JDBCQuestionDAOV2 getQuestionV2Dao() {
		return questionV2Dao;
	}

	public void setQuestionV2Dao(JDBCQuestionDAOV2 questionV2Dao) {
		this.questionV2Dao = questionV2Dao;
	}

	public JDBCValueDomainDAOV2 getValueDomainV2Dao() {
		return valueDomainV2Dao;
	}

	public void setValueDomainV2Dao(JDBCValueDomainDAOV2 valueDomainV2Dao) {
		this.valueDomainV2Dao = valueDomainV2Dao;
	}
	
	
}
