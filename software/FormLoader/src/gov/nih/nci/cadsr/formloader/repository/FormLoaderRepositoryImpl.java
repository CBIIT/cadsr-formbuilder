package gov.nih.nci.cadsr.formloader.repository;

import gov.nih.nci.cadsr.formloader.domain.FormCollection;
import gov.nih.nci.cadsr.formloader.domain.FormDescriptor;
import gov.nih.nci.cadsr.formloader.domain.QuestionDescriptor;
import gov.nih.nci.ncicb.cadsr.common.dto.DataElementTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.FormV2TransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.FormValidValueTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.ModuleTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.PermissibleValueV2TransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.QuestionTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.ReferenceDocumentTransferObject;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc.JDBCCollectionDAO;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc.JDBCFormDAOV2;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc.JDBCFormInstructionDAOV2;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc.JDBCFormValidValueDAOV2;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc.JDBCFormValidValueInstructionDAOV2;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc.JDBCModuleDAOV2;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc.JDBCModuleInstructionDAOV2;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc.JDBCQuestionDAOV2;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc.JDBCQuestionInstructionDAOV2;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc.JDBCReferenceDocumentDAOV2;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc.JDBCValueDomainDAOV2;
import gov.nih.nci.ncicb.cadsr.common.resource.FormV2;
import gov.nih.nci.ncicb.cadsr.common.resource.ValueDomainV2;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class FormLoaderRepositoryImpl implements FormLoaderRepository {
	private static Logger logger = Logger.getLogger(FormLoaderRepositoryImpl.class.getName());
	
	static final int MAX_LONG_NAME_LENGTH = 255;
	
	public static final String COMPONENT_TYPE_FORM = "QUEST_CONTENT";
	
	public static final String DEFAULT_DEFINITION_TYPE = "Form Loader";
	public static final String DEFAULT_DESIGNATION_TYPE = "Form Loader";
	public static final String DEFAULT_CONTEXT_NAME = "NCIP";
	public static final String DEFAULT_FORM_TYPE = "CRF";
	public static final	String DEFAULT_REFDOC_TYPE	= "REFERENCE";
	
	public static final	String FORM_LOADER_DB_USER = "FORMLOADER";
	public static final	String WORKFLOW_STATUS_UNLOADED = "RETIRED UNLOADED"; 
	
	protected static final int MARK_TO_KEEP_IN_UPDATE = 99;
	
	JDBCFormDAOV2 formV2Dao;
	JDBCModuleDAOV2 moduleV2Dao;
	JDBCQuestionDAOV2 questionV2Dao;
	JDBCValueDomainDAOV2 valueDomainV2Dao;
	JDBCFormInstructionDAOV2 formInstructionV2Dao;
	JDBCQuestionInstructionDAOV2 questInstructionV2Dao;
	JDBCFormValidValueDAOV2 formValidValueV2Dao;
	JDBCFormValidValueInstructionDAOV2 formValidValueInstructionV2Dao;
	JDBCCollectionDAO collectionDao;
	JDBCReferenceDocumentDAOV2 referenceDocV2Dao;
	JDBCModuleInstructionDAOV2 moduleInstructionV2Dao;
	
	//These are loaded from database for validation purposes
	HashMap<String, String> conteNameSeqIdMap;
	List<String> definitionTypes;
	List<String> designationTypes;
	List<String> refdocTypes;
	List<String> workflowNames;

	/**
	 * Gets Seq id, public id and version for forms with the given public ids
	 * @param pubicIDList
	 * @return
	 */
	@Transactional(readOnly=true)
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
	
	@Transactional(readOnly=true)
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
	
	@Transactional(readOnly=true)
	public List<QuestionTransferObject> getQuestionsByPublicIds(List<String> publicIds) {
		if (publicIds == null || publicIds.size() == 0) {
			logger.debug("getQuestionsByPublicIds(): Question public id list is null or empty. Unable to querry db.");
			return null;
		}
			
		List<QuestionTransferObject> questions = questionV2Dao.getQuestionsByPublicIds(publicIds);
		logger.debug("getQuestionsByPublicId(): Dao returns " + questions.size() + " questions.");
		return questions;
	}
	
	@Transactional(readOnly=true)
	public List<DataElementTransferObject> getCDEByPublicId(String cdePublicId) {
		if (cdePublicId == null || cdePublicId.length() == 0) {
			logger.debug("getCDEByPublicId(): Question's CDE public id is null or empty. Unable to querry db.");
			return null;
		}
		
		List<DataElementTransferObject> des = questionV2Dao.getCdesByPublicId(cdePublicId);
		logger.debug("getCDEByPublicId(): Dao returns " + des.size() + " CDEs");
		
		return des;
	}
	
	@Transactional(readOnly=true)
	public List<DataElementTransferObject> getCDEsByPublicIds(List<String> cdePublicIds) {
		if (cdePublicIds == null || cdePublicIds.size() == 0) {
			logger.debug("getCDEsByPublicIds(): cde public id list is null or empty. Unable to querry db.");
			return null;
		}
		List<DataElementTransferObject> des = questionV2Dao.getCdesByPublicIds(cdePublicIds);
		logger.debug("getCDEsByPublicIds(): Dao returns " + des.size() + " CDEs");
		
		return des;
	}
	
	@Transactional(readOnly=true)
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
				questionV2Dao.getAllReferenceDocumentsForDE(
						Integer.parseInt(cdePublicId), Float.parseFloat(cdeVersion));
		logger.debug("getReferenceDocsForQuestionCde(): Dao returns " + deRefDocs.size() + " CDE reference docs.");
		
		return deRefDocs;
	}
	
	@Transactional(readOnly=true)
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
	
//	@Transactional(readOnly=true)
//	public List<PermissibleValueV2TransferObject> getValueDomainPermissibleValuesByVdId(String vdSeqId) {
//		if (vdSeqId == null || vdSeqId.length() == 0) {
//			logger.debug("getValueDomainBySeqId(): Value domain seq id is null or empty. Unable to querry db.");
//			return null;
//		}
//		
//		List<PermissibleValueV2TransferObject> pValues = valueDomainV2Dao.getPermissibleValuesByVdId(vdSeqId);
//		String msg = "getValueDomainPermissibleValuesByVdId(): Dao returns ";
//		if (pValues == null || pValues.size() == 0)
//			msg += "a value domain obj with 0 permissible value.";
//		else
//			msg += "a value domain obj with " + pValues.size() + " permissible values";
//		
//		logger.debug("msg");
//		return pValues;
//	}
	
	@Transactional(readOnly=true)
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
		
		logger.debug(msg);
		return pValues;
	}
	
	/**
	 * To get the public id and version for the loaded forms
	 * @param forms
	 */
	@Transactional(readOnly=true)
	public void setPublicIdVersionBySeqids(List<FormDescriptor> forms) {
		List<String> seqids = new ArrayList<String>();
		for (FormDescriptor form : forms) {
			if (form.getLoadStatus() == FormDescriptor.STATUS_LOADED)
				seqids.add(form.getFormSeqId());
		}
		
		if (seqids.size() == 0)
			return; 
		
		HashMap<String, FormV2TransferObject> seqidMap = formV2Dao.getFormsBySeqids(seqids);
		for (FormDescriptor form : forms) {
			FormV2TransferObject formdto = seqidMap.get(form.getFormSeqId());
			if (formdto != null) {
				form.setPublicId(String.valueOf(formdto.getPublicId()));
				form.setVersion(String.valueOf(formdto.getVersion()));
			}
		}
	}
	
	@Transactional(readOnly=true)
	public List<FormCollection> getAllLoadedCollectionsByUser(String userName) {
		
		//first get collection headers
		List<FormCollection> colls = collectionDao.getAllLoadedCollectionsByUser(userName);
		if (colls != null)
			logger.debug("User [" + userName + "] has loaded " + colls.size() + " collections");
		return colls;
	}	
	
	@Transactional(readOnly=true)
	public List<FormDescriptor> getAllFormsWithCollectionId(String collectionSeqid) {
		if (collectionSeqid == null || collectionSeqid.length() == 0)
			return null;
		
		List<FormDescriptor> forms = collectionDao.getAllFormInfoForCollection(collectionSeqid);
		if (forms != null)
			logger.debug("Collection " + collectionSeqid + " returns " + forms.size() + " forms");
		
		return forms;
	}
	
	@Transactional(readOnly=true)
	public List<FormV2TransferObject> getFormsInCadsrBySeqids(List<String> formSeqids) {
		if (formSeqids == null || formSeqids.size() == 0)
			return null;
		
		return this.formV2Dao.getFormHeadersBySeqids(formSeqids);
	}
	
	protected FormValidValueTransferObject translateIntoValidValueDto(QuestionDescriptor.ValidValue vValue,
			 QuestionTransferObject newQuestdto, ModuleTransferObject moduledto, FormV2TransferObject formdto,  int displayOrder) {
		FormValidValueTransferObject fvv = new FormValidValueTransferObject();
		
		fvv.setCreatedBy(moduledto.getCreatedBy());
		fvv.setQuestion(newQuestdto);
		fvv.setVpIdseq(vValue.getVdPermissibleValueSeqid());
		
		String preferredName = composeVVPreferredName(vValue, newQuestdto.getPublicId(), formdto.getPublicId(), formdto.getVersion(), displayOrder);
		
		fvv.setLongName(vValue.getValue());
		fvv.setPreferredName(preferredName);
		fvv.setPreferredDefinition(vValue.getDescription());
		
		fvv.setContext(moduledto.getContext());
		
		fvv.setVersion(Float.valueOf("1.0"));
		fvv.setAslName("DRAFT NEW");
		
		return fvv;
	}
	
	//PreferredName format: value meaning public id_quetionpublicid_form_public_id_version_<x> x = 1, 2, 3
		protected String composeVVPreferredName(QuestionDescriptor.ValidValue vValue, int questPublicId, int formPublicId, float formversion,  int displayorder) {
			
			return vValue.getPreferredName() + "_" + questPublicId + "_" + formPublicId + "v" + formversion + "_"  + displayorder;
			
		}

	@Transactional(readOnly=true)
	public HashMap<String, Date> getModifiedDateForForms(List<String> formSeqids) {
		if (formSeqids == null || formSeqids.size() == 0)
			return new HashMap<String, Date>();
		
		return this.formV2Dao.getFormModifiedDateByIds(formSeqids);
	}
	
	@Transactional(readOnly=true)
	public List<String> getDefinitionTextsByVmIds(String vmSeqid) {
		if (vmSeqid == null || vmSeqid.length() == 0) {
			logger.debug("Input vmSeqid is not valid in getDefinitionTextsByVmIds()");
			return null;
		}
		
		return this.valueDomainV2Dao.getDefinitionTextByVMId(vmSeqid);
		
		
	}
	
	@Transactional(readOnly=true)
	public List<String> getDesignationNamesByVmIds(String vmSeqid) {
		if (vmSeqid == null || vmSeqid.length() == 0) {
			logger.debug("Input vmSeqid is not valid in getDesignationNamessByVmIds()");
			return null;
		}
		
		return this.valueDomainV2Dao.getDesignationNamesByVMId(vmSeqid);
		
		
	}
	
	/**
	 * Public id is created when new component is created. Need to go back to db for it
	 * @param formSeqid
	 * @param form
	 */
	@Transactional(readOnly=true)
	protected void retrievePublicIdForForm(String formSeqid, FormDescriptor form) {
		FormV2TransferObject formdto = this.formV2Dao.getFormPublicIdVersion(formSeqid);
		form.setPublicId(String.valueOf(formdto.getPublicId()));
		form.setVersion(String.valueOf(formdto.getVersion()));
	}
	
	@Transactional(readOnly=true)
	protected void retrievePublicIdForQuestion(String questSeqid, QuestionDescriptor quest,QuestionTransferObject questdto) {
		QuestionTransferObject dto = this.questionV2Dao.getQuestionsPublicIdVersionBySeqid(questSeqid);
		
		quest.setPublicId(String.valueOf(dto.getPublicId()));
		quest.setVersion(String.valueOf(dto.getVersion()));
		
		questdto.setPublicId(dto.getPublicId());
		questdto.setVersion(dto.getVersion());
	}
	
	public float getLatestVersionForForm(String publicId) {
		if (publicId == null || publicId.length() == 0) {
			logger.error("Input public id is null or empty. Unable to query for latest version for form.");
			return 0;
		}
			
		return this.formV2Dao.getLatestVersionForForm(Integer.parseInt(publicId));
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

	public JDBCFormInstructionDAOV2 getFormInstructionV2Dao() {
		return formInstructionV2Dao;
	}

	public void setFormInstructionV2Dao(
			JDBCFormInstructionDAOV2 formInstructionV2Dao) {
		this.formInstructionV2Dao = formInstructionV2Dao;
	}

	public JDBCQuestionInstructionDAOV2 getQuestInstructionV2Dao() {
		return questInstructionV2Dao;
	}

	public void setQuestInstructionV2Dao(
			JDBCQuestionInstructionDAOV2 questInstructionV2Dao) {
		this.questInstructionV2Dao = questInstructionV2Dao;
	}

	public JDBCFormValidValueDAOV2 getFormValidValueV2Dao() {
		return formValidValueV2Dao;
	}

	public void setFormValidValueV2Dao(JDBCFormValidValueDAOV2 formValidValueV2Dao) {
		this.formValidValueV2Dao = formValidValueV2Dao;
	}
	

	public JDBCFormValidValueInstructionDAOV2 getFormValidValueInstructionV2Dao() {
		return formValidValueInstructionV2Dao;
	}

	public void setFormValidValueInstructionV2Dao(
			JDBCFormValidValueInstructionDAOV2 formValidValueInstructionV2Dao) {
		this.formValidValueInstructionV2Dao = formValidValueInstructionV2Dao;
	}

	public JDBCCollectionDAO getCollectionDao() {
		return collectionDao;
	}

	public void setCollectionDao(JDBCCollectionDAO collectionDao) {
		this.collectionDao = collectionDao;
	}

	public JDBCReferenceDocumentDAOV2 getReferenceDocV2Dao() {
		return referenceDocV2Dao;
	}

	public void setReferenceDocV2Dao(JDBCReferenceDocumentDAOV2 referenceDocV2Dao) {
		this.referenceDocV2Dao = referenceDocV2Dao;
	}

	public JDBCModuleInstructionDAOV2 getModuleInstructionV2Dao() {
		return moduleInstructionV2Dao;
	}

	public void setModuleInstructionV2Dao(
			JDBCModuleInstructionDAOV2 moduleInstructionV2Dao) {
		this.moduleInstructionV2Dao = moduleInstructionV2Dao;
	}

	@Transactional(readOnly=true)
	public String getContextSeqIdByName(String contextName) {
		if (conteNameSeqIdMap == null)
			conteNameSeqIdMap = this.formV2Dao.getAllContextSeqIds();
		
		return conteNameSeqIdMap.get(contextName);
	}
	
	
	@Transactional(readOnly=true)
	public boolean designationTypeExists(String designName) {
		if (designationTypes == null)
			designationTypes = this.formV2Dao.getAllDesignationTypes();
		
		return designationTypes.contains(designName);
	}
	
	@Transactional(readOnly=true)
	public boolean refdocTypeExists(String refdocType) {
		if (this.refdocTypes == null)
			refdocTypes = this.formV2Dao.getAllRefdocTypes();
		
		
		
		return (refdocTypes == null) ? false : refdocTypes.contains(refdocType);
	}
	
	@Transactional(readOnly=true)
	public int getMaxNameRepeatForCollection(String collName) {
		if (collName == null || collName.length() == 0)
			return 0;
		
		return this.collectionDao.getMaxNameRepeatNum(collName);
	}
	
	
	
	/**
	 * Compare a refdoc against a list from database, based on name and type.
	 * @param currRefdoc
	 * @param refdocs
	 * @return
	 */
	protected boolean isExistingRefdoc(ReferenceDocumentTransferObject currRefdoc, List<ReferenceDocumentTransferObject> refdocs) {
		if (refdocs == null) {
			return false;
		}
		
		for (ReferenceDocumentTransferObject refdoc : refdocs) {
			if (refdoc.getDocName().equals(currRefdoc.getDocName()) && 
					refdoc.getDocType().equals(currRefdoc.getDocType()) ) {
				refdoc.setDisplayOrder(MARK_TO_KEEP_IN_UPDATE);
				currRefdoc.setDocIDSeq(refdoc.getDocIdSeq());
				return true;
			}
		}
		
		return false;
	}
	
	
	
	@Transactional(readOnly=true)
	public boolean hasLoadFormRight(FormDescriptor form, String userName, String contextName) {
		String contextseqid = this.getContextSeqIdByName(contextName);
		
		if (userName == null || userName.length() == 0) {
			form.addMessage("User name is emppty. Unable to verify user load right");
			return false;
		}
		
		if (contextseqid == null || contextseqid.length() == 0) {
			form.addMessage("Context name [" + contextName + "]is empty. Unable to verify user load right");
			return false;
		}
			
		return this.formV2Dao.hasCreate(userName, COMPONENT_TYPE_FORM, contextseqid);
	}
	
	@Transactional(readOnly=true)
	public boolean definitionTypeValid(FormDescriptor form) {
		if (definitionTypes == null) {
			definitionTypes = this.formV2Dao.getAllDefinitionTypes();
		}
		
		return false;
	}
	
	@Transactional(readOnly=true)
	public boolean refDocTypeValid(FormDescriptor form) {
		if (refdocTypes == null) {
			refdocTypes = this.formV2Dao.getAllRefdocTypes();
		}
		
		return false;
	}
	
	@Transactional(readOnly=true)
	public void checkWorkflowStatusName(FormDescriptor form) {
		if (this.workflowNames == null) {
			workflowNames = this.formV2Dao.getAllWorkflowNames();
		}
		
		String formWfName = form.getWorkflowStatusName();
		if (!workflowNames.contains(formWfName)) {
			form.addMessage("Form's workflow status name in xml is invalid. Use default value DRAFT NEW or DRAFT MOD");
			form.setDefaultWorkflowName();
		}
	}
	
	public ValueDomainV2 getValueDomainBySeqid(String vdseqid) {
		if (vdseqid == null || vdseqid.length() == 0) {
			logger.error("Invalid vd seqid passed to getValueDomainBySeqid()");
			return null;
		}
			
		return this.valueDomainV2Dao.getValueDomainV2ById(vdseqid);
	}

	@Override
	public String createForm(FormDescriptor form, String xmlPathName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String updateForm(FormDescriptor form, String userName,
			String xmlPathName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String createFormCollectionRecords(FormCollection coll) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void unloadForm(FormDescriptor form) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String createFormNewVersion(FormDescriptor form,
			String loggedinUser, String xmlPathName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateFormInCollectionRecord(FormCollection coll,
			FormDescriptor form) {
		// TODO Auto-generated method stub
		
	}
	
}
