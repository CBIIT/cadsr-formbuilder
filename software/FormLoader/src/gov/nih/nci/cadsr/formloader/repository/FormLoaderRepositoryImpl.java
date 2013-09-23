package gov.nih.nci.cadsr.formloader.repository;

import gov.nih.nci.cadsr.formloader.domain.FormCollection;
import gov.nih.nci.cadsr.formloader.domain.FormDescriptor;
import gov.nih.nci.cadsr.formloader.domain.ModuleDescriptor;
import gov.nih.nci.cadsr.formloader.domain.QuestionDescriptor;
import gov.nih.nci.cadsr.formloader.service.common.StaXParser;
import gov.nih.nci.ncicb.cadsr.common.dto.AdminComponentTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.ContextTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.DataElementTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.DefinitionTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.DesignationTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.DesignationTransferObjectExt;
import gov.nih.nci.ncicb.cadsr.common.dto.FormV2TransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.FormValidValueTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.InstructionTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.ModuleTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.PermissibleValueV2TransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.QuestionChangeTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.QuestionTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.RefdocTransferObjectExt;
import gov.nih.nci.ncicb.cadsr.common.dto.ReferenceDocumentTransferObject;
import gov.nih.nci.ncicb.cadsr.common.exception.DMLException;
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
import gov.nih.nci.ncicb.cadsr.common.resource.Context;
import gov.nih.nci.ncicb.cadsr.common.resource.FormV2;
import gov.nih.nci.ncicb.cadsr.common.resource.Instruction;

import java.util.ArrayList;
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
	
	public static final String DEFAULT_WORKFLOW_STATUS = "DRAFT NEW";
	public static final String DEFAULT_DEFINITION_TYPE = "Form Loader";
	public static final String DEFAULT_DESIGNATION_TYPE = "Form Loader";
	public static final String DEFAULT_CONTEXT_NAME = "NCIP";
	public static final String DEFAULT_FORM_TYPE = "CRF";
	public static final	String DEFAULT_REFDOC_TYPE	= "REFERENCE";
	
	public static final	String FORM_LOADER_DB_USER = "FORMLOADER";
	
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
	
	@Transactional(readOnly=true)
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
		
		for (FormCollection coll : colls) {
			
			List<String> formseqids = collectionDao.getAllFormSeqidsForCollection(coll.getId());
			if (formseqids == null || formseqids.size() == 0) 
				logger.warn("Collection " + coll.getId() + " doesn't have form seqids associated with it in database");
			else {
				List<FormV2TransferObject> formdtos = this.formV2Dao.getFormHeadersBySeqids(formseqids);
				List<FormDescriptor> forms = translateIntoFormDescriptors(formdtos);
				coll.setForms(forms);
			}
				
		}
		
		return colls;
	}
	
	protected void retrievePublicIdForForm(String formSeqid, FormDescriptor form) {
		FormV2TransferObject formdto = this.formV2Dao.getFormPublicIdVersion(formSeqid);
		form.setPublicId(String.valueOf(formdto.getPublicId()));
		form.setVersion(String.valueOf(formdto.getVersion()));
	}
	
	protected void retrievePublicIdForQuestion(String questSeqid, QuestionDescriptor quest,QuestionTransferObject questdto) {
		QuestionTransferObject dto = this.questionV2Dao.getQuestionsPublicIdVersionBySeqid(questSeqid);
		
		quest.setPublicId(String.valueOf(dto.getPublicId()));
		quest.setVersion(String.valueOf(dto.getVersion()));
		
		questdto.setPublicId(dto.getPublicId());
		questdto.setVersion(dto.getVersion());
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
	
	/**
	 * Create new form
	 */
	@Transactional
	public String createForm(FormDescriptor form, String xmlPathName, int formIdx) {
		
		String formSeqid = "";
		
		try {
			FormV2TransferObject formdto = translateIntoFormDTO(form);	
			if (formdto == null) 
				return null;

			formSeqid = formV2Dao.createFormComponent(formdto);		
			logger.debug("Created form. Seqid: " + formSeqid);
			
			//public id is created when new component is created
			//Need to go back to db for it
			retrievePublicIdForForm(formSeqid, form);

			formdto.setFormIdseq(formSeqid);
			form.setFormSeqId(formSeqid);

			//instructions
			createFormInstructions(form, formdto);

			//designations, refdocs, definitions and contact communications
			processFormdetails(form, xmlPathName, formIdx);

			//Onto modules and questions
			createModulesInForm(form, formdto);
		} catch (DMLException dbe) {
			logger.error(dbe.getMessage());
			form.addMessage(dbe.getMessage());
			form.setLoadStatus(FormDescriptor.STATUS_LOAD_FAILED);
		}
		
		return formSeqid;
	}
	
	/**
	 * Create new form or new version of an existing form
	 * <br><br>
	 * This will first call the store procedure Sbrext_Form_Builder_Pkg.CRF_VERSION which will make a full copy
	 * of the form with the same public id and an upgraded version number. Then it'll apply an update on the 
	 * copy with content from the xml. This is very inefficient. 
	 * <br><br>
	 * Need to explore better way. Key quetions: how public id
	 * is generated? Is it possible to insert new form row withough triggering a new public id generation.
	 */
	@Transactional
	public String createFormNewVersion(FormDescriptor form, String loggedinUser, String xmlPathName, int formIdx) {
		
		String formSeqid = "";
		
		try {
			FormV2TransferObject formdto = translateIntoFormDTO(form);	
			if (formdto == null) 
				return null;

			formSeqid = formV2Dao.createNewFormVersionShellOnly(formdto.getFormIdseq(), formdto.getVersion(), 
					"New version form by Form Loader", formdto.getCreatedBy());		
			
			logger.debug("Created new version for form. Seqid: " + formSeqid);
			
			formdto.setFormIdseq(formSeqid);
			form.setFormSeqId(formSeqid);

			int res = formV2Dao.updateFormComponent(formdto);
			if (res != 1) {
				logger.error("Error!! Failed to update form");
				return null;
			}
			
			createFormInstructions(form, formdto);
			
			//designations, refdocs, definitions and contact communications
			processFormdetails(form, xmlPathName, formIdx);
			
			//Onto modules and questions
			updateModulesInForm(form, formdto);
			
			return form.getFormSeqId();
			
		} catch (DMLException dbe) {
			logger.error(dbe.getMessage());
			form.addMessage(dbe.getMessage());
			formSeqid = null;
			form.setLoadStatus(FormDescriptor.STATUS_LOAD_FAILED);
		}
		
		return formSeqid;
	}
	
	@Transactional
	public String updateForm(FormDescriptor form, String loggedinUser, String xmlPathName, int formIdx) {
		
		try {
			FormV2TransferObject formdto = translateIntoFormDTO(form);	
			if (formdto == null) {
				logger.error("Error!! Failed to translate xml form into FormTransferObject");
				return null;
			}

			int res = formV2Dao.updateFormComponent(formdto);
			if (res != 1) {
				logger.error("Error!! Failed to update form");
				return null;
			}

			createFormInstructions(form, formdto);

			//designations, refdocs, definitions and contact communications
			processFormdetails(form, xmlPathName, formIdx);

			//Onto modules and questions
			updateModulesInForm(form, formdto);
		} catch (DMLException dbe) {
			logger.error(dbe.getMessage());
			form.addMessage(dbe.getMessage());
			form.setFormSeqId("");
			form.setLoadStatus(FormDescriptor.STATUS_LOAD_FAILED);
		}
		
		return form.getFormSeqId();
	}
	
	protected FormV2TransferObject translateIntoFormDTO(FormDescriptor form) {
			
		FormV2TransferObject formdto = new FormV2TransferObject();
		
		//User FORMLOADER for forms, modules, questions, etc.
		formdto.setModifiedBy(FORM_LOADER_DB_USER);
		form.setModifiedBy(FORM_LOADER_DB_USER);
		
		formdto.setCreatedBy(form.getCreatedBy());
		formdto.setChangeNote(form.getChangeNote());
		
		String loadType = form.getLoadType(); 
		if (FormDescriptor.LOAD_TYPE_NEW_VERSION.equals(loadType)) {
			float latest = this.formV2Dao.getMaxFormVersion(Integer.parseInt(form.getPublicId()));
			formdto.setVersion(new Float(latest + 1.0)); //TODO: is this the way to assign new version?
			form.setVersion(String.valueOf(formdto.getVersion()));
			formdto.setPublicId(Integer.parseInt(form.getPublicId()));
			formdto.setFormIdseq(form.getFormSeqId());
			formdto.setAslName("DRAFT NEW"); 
		} else if (FormDescriptor.LOAD_TYPE_NEW.equals(loadType)) {
			formdto.setVersion(Float.valueOf("1.0")); 
			formdto.setAslName("DRAFT NEW");
		} else if (FormDescriptor.LOAD_TYPE_UPDATE_FORM.equals(loadType)) {
			formdto.setVersion(Float.valueOf(form.getVersion()));
			formdto.setAslName(form.getWorkflowStatusName()); 
			String seqid = form.getFormSeqId(); //we got this when we queried on form public id
			//This should not happen
			if (seqid == null || seqid.length() == 0) {
				String msg = "Update form doesn't have a valid seqid. Unable to load.";
				form.addMessage(msg);
				form.setLoadStatus(FormDescriptor.STATUS_LOAD_FAILED);
				logger.error("Error with [" + form.getFormIdString() + "]: " + msg);
				return null;
			}
			
			formdto.setFormIdseq(seqid);
		}
		
		formdto.setLongName(form.getLongName());
		formdto.setPreferredName(form.getLongName());
		formdto.setFormType(form.getType());
		formdto.setFormCategory(form.getCategoryName());
		formdto.setPreferredDefinition(form.getPreferredDefinition());
		
		Context context = new ContextTransferObject();
	    context.setConteIdseq(form.getContextSeqid());
	    formdto.setContext(context);
		formdto.setConteIdseq(form.getContextSeqid()); //maynot needed
		
		return formdto;
	}
	
	
	@Transactional
	protected void createFormInstructions(FormDescriptor form, FormV2TransferObject formdto) {
		logger.debug("Creating instructions for form");
		String instructString = form.getHeaderInstruction();
		if (instructString == null || instructString.length() == 0)
			return;
		
		InstructionTransferObject formInstruction = createInstructionDto(formdto, instructString);
		if (formInstruction != null)
			formInstructionV2Dao.createInstruction(formInstruction, formdto.getFormIdseq());
		
		instructString = form.getFooterInstruction();
		formInstruction = createInstructionDto(formdto, instructString);
		if (formInstruction != null)
			formInstructionV2Dao.createFooterInstruction(formInstruction, formdto.getFormIdseq());
		
		logger.debug("Done creating instructions for form");
	}
	
	/**
	 * Create the instruction dto for the parent admin component dto.
	 * 
	 * @param compdto parent admin component that the instruction belongs to
	 * @param instructionString instruction string
	 * 
	 * @return InstructionTransferObject
	 */
	protected InstructionTransferObject createInstructionDto(AdminComponentTransferObject compdto, String instructionString) {
			
		InstructionTransferObject instruction = null;
		if (instructionString != null && instructionString.length() > 0) {
			instruction = new InstructionTransferObject();
			instruction.setLongName(compdto.getLongName());
			instruction.setPreferredDefinition(instructionString);
			instruction.setContext(compdto.getContext());
			instruction.setAslName("DRAFT NEW");
			instruction.setVersion(new Float(1.0));
			instruction.setCreatedBy(compdto.getCreatedBy());
			instruction.setDisplayOrder(1);
		}
		
		return instruction;
	}
	
	@Transactional
	protected void processFormdetails(FormDescriptor form, String xmlPathName, int currFormIdx) {
		logger.debug("Processing protocols, designations, refdocs and definitions for form");
		StaXParser parser = new StaXParser();
		parser.parseFormDetails(xmlPathName, form, currFormIdx);
		
		List<String> protoIds = parser.getProtocolIds();
		processProtocols(form, protoIds);
		
		List<DesignationTransferObjectExt> designations = parser.getDesignations();
		processDesignations(form, designations);
		
		List<RefdocTransferObjectExt> refdocs = parser.getRefdocs();
		processRefdocs(form, refdocs);
		
		//TODO
		List<DefinitionTransferObject> definitions = parser.getDefinitions();
		
		
		//TODO
		
		//processContactCommunications()
		
		
		logger.debug("Done processing protocols, designations, refdocs and definitions for form");
	}
	
	/**
	 * 
	 * @param form
	 * @param refdocs
	 */
	@Transactional
	protected void processRefdocs(FormDescriptor form, List<RefdocTransferObjectExt> refdocs) {
		if (refdocs == null) {
			logger.error("Null refdocs list passed in to processRefdocs(). Do nothing");
			return;
		}
		
		String formSeqid = form.getFormSeqId();
		String contextSeqId = this.getContextSeqIdByName(form.getContext());
		List existings = null;
		
		if (!form.getLoadType().equals(FormDescriptor.LOAD_TYPE_NEW)) {
			//2nd arg is not used in the actual query.
			existings = this.formV2Dao.getAllReferenceDocuments(form.getFormSeqId(), null);
		}
		
		//create ref docs
		int idx = 0;
		for (RefdocTransferObjectExt refdoc : refdocs) {
			String contextName = refdoc.getContextName();
			String refdocContextSeqid = this.getContextSeqIdByName(contextName);
			if (refdocContextSeqid == null || refdocContextSeqid.length() == 0) {
				refdocContextSeqid = contextSeqId;
				contextName = form.getContext();
			}

			ContextTransferObject con = new ContextTransferObject(contextName);
			con.setConteIdseq(refdocContextSeqid);
			refdoc.setContext(con);
			refdoc.setDisplayOrder(idx++);
			if (!this.refdocTypeExists(refdoc.getDocType())) {
				form.addMessage("Refdoc type [" + refdoc.getDocType() + "] is invalid. Use default type [REFERENCE]");
				refdoc.setDocType(DEFAULT_REFDOC_TYPE);
			}
			if (existings != null && isExistingRefdoc(refdoc, existings)) {
				referenceDocV2Dao.updateReferenceDocument(refdoc);
			} else {
				this.referenceDocV2Dao.createReferenceDoc(refdoc, formSeqid);
			}
		}
		
		removeExtraRefdocsIfAny(existings);
		
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
	
	/**
	 * Check a list of ref doc object that are previously marked and delete from database those are not marked as "KEEP"
	 * 
	 * @param refdocs
	 */
	@Transactional
	protected void removeExtraRefdocsIfAny(List<ReferenceDocumentTransferObject> refdocs) {
		if (refdocs == null) {
			return;
		}
		
		for (ReferenceDocumentTransferObject refdoc : refdocs) {
			if (refdoc.getDisplayOrder() == MARK_TO_KEEP_IN_UPDATE)
				continue;
			
			logger.debug("Deleting refdoc [" + refdoc.getDocName() + "|" + refdoc.getDocType());
			referenceDocV2Dao.deleteReferenceDocument(refdoc.getDocIdSeq());
		}
	}

	/**
	 * For new version and update form, if designation doesn't already exist, designate it to the context. For new form,
	 * designate the form to the context.
	 * @param form
	 * @param designations
	 */
	@Transactional
	protected void processDesignations(FormDescriptor form, List<DesignationTransferObjectExt> designations) {
		
		if (designations == null) {
			logger.error("Null designation list passed in to processDesignations(). Do nothing");
			return;
		}
		
		String formSeqid = form.getFormSeqId();
		String contextSeqId = this.getContextSeqIdByName(form.getContext());
		
		for (DesignationTransferObjectExt desig : designations) {
			if (form.getLoadType().equals(FormDescriptor.LOAD_TYPE_NEW)) {
				designateForm(formSeqid, contextSeqId, form.getCreatedBy(), desig);
			} else {
				//Check context name in designation elem from xml. If that's not valid, use form's
				String desigContext = desig.getContextName();
				String desgContextSeqid = this.getContextSeqIdByName(desigContext);
				if (desgContextSeqid == null || desgContextSeqid.length() == 0)
					desgContextSeqid = contextSeqId;
				
				//validate the type, use default if neccessary
				String desigType = desig.getType();
				if (!this.designationTypeExists(desigType)) {
					form.addMessage("Designation type [" + desigType + "] is invalid. Use default type [Form Loader]");
					desig.setType(DEFAULT_DESIGNATION_TYPE);
				}
				
				List<DesignationTransferObject> existing = formV2Dao.getDesignationsForForm(
						formSeqid, desig.getName(), desig.getType(), desig.getLanguage());
				if (existing == null || existing.size() == 0) {
					designateForm(formSeqid, contextSeqId, form.getCreatedBy(), desig);
				}
			} 
		}
	}
	
	@Transactional
	protected int designateForm(String formSeqid, String contextSeqid, String createdby,
			DesignationTransferObjectExt desig) {
		List<String> ac_seqids = new ArrayList<String>();
		ac_seqids.add(formSeqid);
		return formV2Dao.designate(contextSeqid, ac_seqids, createdby);
	}
	
	/**
	 * 1. New form : add all listed protocols from xml to new form.
	 * 2. New version and update form: add only if a protocol in xml doesn't already exist with the form.
	 * 
	 * @param form
	 * @param protoIds
	 */
	@Transactional
	protected void processProtocols(FormDescriptor form, List<String> protoIds) {
		String formSeqid = form.getFormSeqId();
		if (protoIds != null && protoIds.size() > 0) {
			HashMap<String, String> protoIdMap = formV2Dao.getProtocolSeqidsByIds(protoIds);
			List<String> protoSeqIds = markNoMatchProtoIds(form, protoIds, protoIdMap);

			for (String protoSeqid : protoSeqIds) {
				if (FormDescriptor.LOAD_TYPE_NEW.equals(form.getLoadType())) {
					formV2Dao.addFormProtocol(formSeqid, protoSeqid, form.getCreatedBy());
				} else  {
					if (!formV2Dao.formProtocolExists(formSeqid, protoSeqid))
						formV2Dao.addFormProtocol(formSeqid, protoSeqid, form.getModifiedBy());

				} 
			}
			
		}
	}
	
	protected List<String> markNoMatchProtoIds(FormDescriptor form, List<String> protoIds, HashMap<String, String> protoIdMap) {
		List<String> protoSeqIds = new ArrayList<String>();
		String nomatchids = "";
		for (String protoId : protoIds) {
			String seqid = protoIdMap.get(protoId);
			if (seqid == null)
				nomatchids = (nomatchids.length() > 0) ? "," + protoId : protoId;
			else
				protoSeqIds.add(seqid);
			
		}
		
		if (nomatchids.length() > 0)
			form.addMessage("Protocol ids [" + nomatchids + "] with the form has no match in database.");
		
		return protoSeqIds;
	}
	
	@Transactional
	protected void createModulesInForm(FormDescriptor form, FormV2TransferObject formdto) {
		logger.debug("Start creating modules for form");
		List<ModuleDescriptor> modules = form.getModules();
		
		/*
		 * Denise:
		 * For a module and its questions in a form
			1) If it's a new form or new version, use the form's createdBy
			2) If it's an update form, check what's with module element in xml. 
				a. If the module's createBy is valid, use it and apply that to all questions.
				b. If the module's createdBy is not valid, use form's createdBy and apply to all questions.
		 */
		
		for (ModuleDescriptor module : modules) {
			ModuleTransferObject moduledto = translateIntoModuleDTO(module, form, formdto);
			
			String moduleSeqid = moduleV2Dao.createModuleComponent(moduledto);
			logger.debug("Created a module with seqid: " + moduleSeqid);
			
			module.setModuleSeqId(moduleSeqid);
			moduledto.setIdseq(moduleSeqid);
			moduledto.setContext(formdto.getContext());
			
			//TODO: do we need to go back to db to get module's public id?
			
			//Now, onto questions
			createQuestionsInModule(module, moduledto, form, formdto);
		}
		
		logger.debug("Done creating modules for form");
	}
	
	@Transactional
	protected void createQuestionsInModule(ModuleDescriptor module, ModuleTransferObject moduledto, 
			FormDescriptor form, FormV2TransferObject formdto) {
		List<QuestionDescriptor> questions = module.getQuestions();
		
		logger.debug("Creating questions for module");
		int idx = 0;
		for (QuestionDescriptor question : questions) {
			if (question.isSkip()) continue;
			
			QuestionTransferObject questdto = translateIntoQuestionDTO(question, form);
			
			questdto.setDisplayOrder(idx++);
			questdto.setContext(formdto.getContext());
			questdto.setModule(moduledto);
			
			//better to call createQuestionComponents, which is not implement.
			QuestionTransferObject newQuestdto = (QuestionTransferObject)this.questionV2Dao.createQuestionComponent(questdto);
			String seqid = newQuestdto.getQuesIdseq();
			
			logger.debug("Created a question: " + seqid);
			question.setQuestionSeqId(seqid);
			retrievePublicIdForQuestion(seqid, question, questdto);
			
			createQuestionInstruction(newQuestdto, moduledto, question.getInstruction());
			
			createQuestionValidValues(question, form, newQuestdto, moduledto, formdto);
		}
		
		
		
		logger.debug("Done creating questions for module");
	}
	
	/**
	 * Create valid value for a newly created question.
	 * 
	 * @param question question generated from xml
	 * @param form	form generated from xml
	 * @param newQuestdto new question dto that just got created in db, with new seqid and public id
	 * @param moduledto
	 */
	@Transactional
	protected void createQuestionValidValues(QuestionDescriptor question, FormDescriptor form, QuestionTransferObject newQuestdto, 
			ModuleTransferObject moduledto, FormV2TransferObject formdto) {
		
		List<QuestionDescriptor.ValidValue>  validValues = question.getValidValues();
		
		int idx = 0;
		for (QuestionDescriptor.ValidValue vValue : validValues) {
			if (vValue.isSkip()) continue;
			
			idx++;
			FormValidValueTransferObject fvv = translateIntoValidValueDto(vValue, newQuestdto, moduledto, formdto, idx);
			
			fvv.setDisplayOrder(idx);
			
			String vvSeqid  = 
					formValidValueV2Dao.createValidValue(fvv,newQuestdto.getQuesIdseq(),moduledto.getCreatedBy());
			
			
			if (vvSeqid != null && vvSeqid.length() > 0) {
				formValidValueV2Dao.createValidValueAttributes(vvSeqid, vValue.getMeaningText(), vValue.getDescription(), moduledto.getCreatedBy());
				
				String instr = vValue.getInstruction();
				if (instr != null && instr.length() > 0) {
					InstructionTransferObject instrdto = createInstructionDto(fvv, instr);
					formValidValueInstructionV2Dao.createInstruction(instrdto, vvSeqid);
				}
			}
			
			logger.debug("Created new valid valid");
		}
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
	
	/**
	 * Create new instruction for a question.
	 * 
	 * Do nothing is instruction string is null or empty. 
	 * 
	 * @param newQuestdto
	 * @param moduledto
	 * @param instString
	 */
	@Transactional
	protected void createQuestionInstruction(QuestionTransferObject newQuestdto, 
			ModuleTransferObject moduledto, String instString) {
		
		if (instString == null || instString.length() == 0)
			return; //Nothing to do
		
		String sizedUpInstr = instString;

		/* (Snippet copied from FormModuleEditAction (FB)
		 * 
		 * Truncate instruction string to fit in LONG_NAME field (255 characters)
		 * Refer to GF# 12379 for guidance on this
		 */

		if (sizedUpInstr.length() > MAX_LONG_NAME_LENGTH) { 
			sizedUpInstr = sizedUpInstr.substring(0, MAX_LONG_NAME_LENGTH);
		}

		Instruction instr = new InstructionTransferObject();
		instr.setLongName(sizedUpInstr);
		instr.setDisplayOrder(0);
		instr.setVersion(new Float(1));
		instr.setAslName("DRAFT NEW");
		instr.setContext(moduledto.getContext());
		instr.setPreferredDefinition(instString);
		instr.setCreatedBy(moduledto.getCreatedBy());
		newQuestdto.setInstruction(instr);
		
		questInstructionV2Dao.createInstruction(instr, newQuestdto.getQuesIdseq());
         
	}
	
	/**
	 * Check if question has existing instruction. If instruction found in db, update it. Otherwise, create new.
	 * 
	 * Do nothing if instruction string is null or empty.
	 * 
	 * @param questdto
	 * @param moduledto
	 * @param instString
	 */
	@Transactional
	protected void updateQuestionInstruction(QuestionTransferObject questdto, 
			ModuleTransferObject moduledto, String instString) {
		if (instString == null || instString.length() == 0)
			return; //Nothing to do
		
		List existings = questInstructionV2Dao.getInstructions(questdto.getQuesIdseq());
		if (existings != null && existings.size() > 0) {
			String sizedUpInstr = instString;
			InstructionTransferObject existing = (InstructionTransferObject)existings.get(0);

			if (sizedUpInstr.length() > MAX_LONG_NAME_LENGTH) { 
				sizedUpInstr = sizedUpInstr.substring(0, MAX_LONG_NAME_LENGTH);
			}

			Instruction instr = new InstructionTransferObject();
			instr.setLongName(sizedUpInstr);
			instr.setPreferredDefinition(instString);
			instr.setModifiedBy(moduledto.getCreatedBy());
			instr.setIdseq(existing.getIdseq());
			questInstructionV2Dao.updateInstruction(instr);
		} else {
			createQuestionInstruction(questdto, moduledto, instString);
		}
	}
	
	
	protected QuestionTransferObject translateIntoQuestionDTO(QuestionDescriptor question, FormDescriptor form) {
		QuestionTransferObject questdto = new QuestionTransferObject();
		
		questdto.setCreatedBy(form.getCreatedBy());
		questdto.setModifiedBy(FORM_LOADER_DB_USER);
		
		String deSeqid = question.getCdeSeqId();
		if (deSeqid != null && deSeqid.length() > 0) {
			DataElementTransferObject de = new DataElementTransferObject();
			de.setDeIdseq(deSeqid);
			questdto.setDataElement(de);
		}
			
		if (FormDescriptor.LOAD_TYPE_NEW.equals(form.getLoadType())) {
			questdto.setVersion(Float.valueOf("1.0")); 
		} else if (FormDescriptor.LOAD_TYPE_NEW_VERSION.equals(form.getLoadType())) {
			questdto.setVersion(Float.valueOf(form.getVersion()));
		} else {
			questdto.setVersion(Float.valueOf(question.getVersion())); 
		}
		
		String pid = question.getPublicId();
		if (pid != null && pid.length() > 0)
			questdto.setPublicId(Integer.parseInt(pid));
		
		questdto.setEditable(question.isEditable());
		questdto.setMandatory(question.isMandatory());
		questdto.setDefaultValue(question.getDefaultValue());
		questdto.setAslName("DRAFT NEW");
		
		questdto.setLongName(question.getQuestionText());  
		
		//TODO: xsd doesn't have preferred def use question text now
		//Denise: if preferred def of data element if it's not null. Otherwise, use long name of data element. if No CDE, use question text
		questdto.setPreferredDefinition(question.getQuestionText());
		return questdto;
	}
	
	protected ModuleTransferObject translateIntoModuleDTO(ModuleDescriptor module, FormDescriptor form, FormV2TransferObject formdto) {
		ModuleTransferObject moduleDto = new ModuleTransferObject();
		
		moduleDto.setForm(formdto);
		moduleDto.setAslName("DRAFT NEW");
		moduleDto.setLongName(module.getLongName());
		
		moduleDto.setCreatedBy(formdto.getCreatedBy());
		moduleDto.setModifiedBy(FORM_LOADER_DB_USER);
		
		if (FormDescriptor.LOAD_TYPE_NEW.equals(form.getLoadType())) {
				moduleDto.setVersion(Float.valueOf("1.0")); 
		} else if (FormDescriptor.LOAD_TYPE_NEW_VERSION.equals(form.getLoadType())) {
			moduleDto.setVersion(formdto.getVersion());
		} else {
			moduleDto.setPublicId(Integer.parseInt((module.getPublicId())));
			moduleDto.setVersion(Float.parseFloat(module.getVersion()));
		}
		
		moduleDto.setContext(formdto.getContext());
		moduleDto.setPreferredDefinition(module.getPreferredDefinition());
		
		return moduleDto;
	}
	
	protected List<FormDescriptor> translateIntoFormDescriptors(List<FormV2TransferObject> formdtos) {
		List<FormDescriptor> forms = new ArrayList<FormDescriptor>();
		
		if (formdtos == null) {
			logger.debug("Form dtos is null. Can't translater list into FormDescriptors");
			return forms;
		}
		
		for (FormV2TransferObject dto : formdtos) {
			FormDescriptor form  = new FormDescriptor();
			form.setFormSeqId(dto.getFormIdseq());
			form.setLongName(dto.getLongName());
			form.setContext(dto.getContextName());
			form.setModifiedBy(dto.getModifiedBy());
			form.setCreatedBy(dto.getCreatedBy());
			form.setProtocolName(dto.getProtocolLongName());
			form.setPublicId(String.valueOf(dto.getPublicId()));
			form.setVersion(String.valueOf(dto.getVersion()));
			form.setType(dto.getFormType());
			form.setWorkflowStatusName(dto.getAslName());
			forms.add(form);
		}
		
		return forms;
	}
	
	/**
	 * Update inlcudes adding new modules, updating existing ones and delete extra ones that are not in xml
	 * 
	 * @param form
	 * @param formdto
	 */
	@Transactional
	protected void updateModulesInForm(FormDescriptor form, FormV2TransferObject formdto) {
		
		List<ModuleDescriptor> modules = form.getModules();
		
		List<ModuleTransferObject> existingModuledtos = this.formV2Dao.getModulesInAForm(formdto.getFormIdseq());
		
		/*
		 * Denise:
		 * For a module and its questions in a form
			1) If it's a new form or new version, use the form's createdBy
			2) If it's an update form, check what's with module element in xml. 
				a. If the module's createBy is valid, use it and apply that to all questions.
				b. If the module's createdBy is not valid, use form's createdBy and apply to all questions.
		 */
		
		int displayOrder = 0;
		for (ModuleDescriptor module : modules) {
			ModuleTransferObject moduledto = translateIntoModuleDTO(module, form, formdto);
			moduledto.setDisplayOrder(++displayOrder);
			String moduleSeqid = "";
	
			if (isExistingModule(moduledto, existingModuledtos)) {
				//this means sampe public id and version
				int res = moduleV2Dao.updateModuleComponent(moduledto);
				//TODO: better understand spring sqlupdate return code
				updateQuestionsInModule(module, moduledto, form, formdto);
			}
			else {
				moduledto.setContext(formdto.getContext());
				moduleSeqid = moduleV2Dao.createModuleComponent(moduledto);
				module.setModuleSeqId(moduleSeqid);
				moduledto.setIdseq(moduleSeqid);
				
				addModuleInstructionIfNecessary(moduledto, existingModuledtos);
				
				//Now, onto questions. Ignore version from xml. Start from 1.0
				resetQeustionVersionInModule(module); //
				createQuestionsInModule(module, moduledto, form, formdto);
			}
			
		}
		
		//Find the existing modules to be deleted
		removeExtraModulesIfAny(existingModuledtos);
	}
	
	/**
	 * Check to see if a new module has a "similar" existing module: no match with public id and version but has the same long name. If found,
	 * add an instruction to the module: 
	 * 		"New Module with public id 2963886 and version 1.0 was created by Form Loader because the XML module public id value of 
	 * 		2962822 and version 1.0 did not match an existing module. Please review modules and delete any unnecessary module, 
	 * 		this module may have been intended to replace an existing module." 
	 * 
	 * This applies to update form ONLY
	 * 
	 * @param currModule
	 * @param existingModuledtos
	 */
	@Transactional(readOnly=true)
	protected void addModuleInstructionIfNecessary(ModuleTransferObject currModule, 
			List<ModuleTransferObject> existingModuledtos) {
		
		for (ModuleTransferObject moduledto : existingModuledtos) {
			//These are the previously marked as "matched" in isExistingModule
			if (MARK_TO_KEEP_IN_UPDATE == moduledto.getDisplayOrder())
				continue;
			
			if (currModule.getLongName().equalsIgnoreCase(moduledto.getLongName())) {
				//get the newly added module's public id and version
				ModuleTransferObject mod = moduleV2Dao.getModulePublicIdVersionBySeqid(currModule.getIdseq());
				
				String instr = "New Module with public id " + mod.getPublicId() + " and version " + mod.getVersion() + 
						" was created by Form Loader because the XML module public id value of " +
						currModule.getPublicId() + " and version " + currModule.getVersion() + " did not match an existing module. " +
						"Please review modules and delete any unnecessary module, this module may have been intended to " +
						"replace an existing module.";
				
				InstructionTransferObject instdto = createInstructionDto(currModule, instr);
				moduleInstructionV2Dao.createInstruction(instdto, currModule.getIdseq());
				
				break;
			}
			
		
		}
	}
	
	protected boolean isExistingModule(ModuleTransferObject currModule, List<ModuleTransferObject> existingModuledtos) {
		
		for (ModuleTransferObject moduledto : existingModuledtos) {
			if ((moduledto.getPublicId() == currModule.getPublicId()) &&
					(moduledto.getVersion().floatValue() == currModule.getVersion().floatValue())) {
				currModule.setIdseq(moduledto.getIdseq());
				currModule.setModuleIdseq(moduledto.getModuleIdseq()); //don't know how these are used
				moduledto.setDisplayOrder(MARK_TO_KEEP_IN_UPDATE); //use this to indicate module's taken
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Check the list to see if any is marked with MARK_FOR_DELETE. Delete the marked ones.
	 * @param existingmoduledtos
	 */
	@Transactional
	protected void removeExtraModulesIfAny(List<ModuleTransferObject> existingmoduledtos) {
		for (ModuleTransferObject moduledto : existingmoduledtos) {
			if (moduledto.getDisplayOrder() != MARK_TO_KEEP_IN_UPDATE) {
				logger.debug("Found a module to delete: [" + moduledto.getPublicId() + "|" + moduledto.getVersion() + "]");
				this.moduleV2Dao.deleteModule(moduledto.getModuleIdseq());
			}
		}
	}
	
	/**
	 * Check a list of pre-processed existing questions from db. Delete those NOT marked as MARK_TO_KEEP_IN_UPDATE
	 * @param questiondtos
	 */
	@Transactional
	protected void removeExtraQuestionsIfAny(List<QuestionTransferObject> questiondtos) {
		//Method copied from FormBuilderEJB
		
		for (QuestionTransferObject qdto : questiondtos) {
			if (qdto.getDisplayOrder() == MARK_TO_KEEP_IN_UPDATE)
				continue;
			
			String qSeqid = qdto.getQuesIdseq();
			logger.debug("Found a question to delete: [" + qdto.getPublicId() + "|" + qdto.getVersion() + "|" + qSeqid);
			
			//delete question
			questionV2Dao.deleteQuestion(qdto.getQuesIdseq());
			logger.debug("Done deleting question");
			
			//delete instructions
			List instructions = this.questInstructionV2Dao.getInstructions(qSeqid);
			if (instructions != null && instructions.size() > 0) {
				logger.debug("Deleting instructions for question...");
				for (int i = 0; i < instructions.size(); i++) {
					InstructionTransferObject instr = (InstructionTransferObject)instructions.get(i);
					this.questInstructionV2Dao.deleteInstruction(instr.getIdseq());
					logger.debug("Deleted an instruction for question");
				}
			}
			
			//delete valid values
			List<String> vvIds = this.formValidValueV2Dao.getValidValueSeqidsByQuestionSeqid(qSeqid);
			if (vvIds != null) {
				for (String vvid : vvIds) {
					//This calls remove_value sp which takes care of vv and instruction
					formValidValueV2Dao.deleteFormValidValue(vvid);
				}
			}
			
		}
	}
	
	/**
	 * Reset all question versions to 1.0 in a module, mostly because we'll insert these questions
	 * as new.
	 * @param module
	 */
	protected void resetQeustionVersionInModule(ModuleDescriptor module) {
		List<QuestionDescriptor> questions = module.getQuestions();
		for (QuestionDescriptor question : questions) {
			question.setVersion("1.0");
		}
	}
	
	/**
	 * 
	 * @param module
	 * @param moduledto
	 * @param form
	 * @param formdto
	 */
	@Transactional
	protected void updateQuestionsInModule(ModuleDescriptor module, ModuleTransferObject moduledto, 
			FormDescriptor form, FormV2TransferObject formdto) {
		
		List<QuestionTransferObject> existingQuestiondtos = 
				moduleV2Dao.getQuestionsInAModuleV2(moduledto.getModuleIdseq());
		
		List<QuestionDescriptor> questions = module.getQuestions();
		
		int idx = 0;
		for (QuestionDescriptor question : questions) {
			if (question.isSkip()) continue;
			
			QuestionTransferObject questdto = translateIntoQuestionDTO(question, form);
	
			questdto.setContext(formdto.getContext());
			questdto.setModule(moduledto);
			//questdto.setModifiedBy(module.getModifiedBy());
			
			//TODO: display order should come from xml?
			questdto.setDisplayOrder(idx++);
			
			QuestionTransferObject existingquestdto = getExistingQuestionDto(questdto, existingQuestiondtos);
			if (existingquestdto != null) {
				//existing question
				updateQuestion(question, questdto, existingquestdto, moduledto, formdto);
				
			} else {
				//better to call createQuestionComponents, which is not implemented.
				QuestionTransferObject newQuestdto = (QuestionTransferObject)this.questionV2Dao.createQuestionComponent(questdto);
				createQuestionInstruction(newQuestdto, moduledto, question.getInstruction());
				createQuestionValidValues(question, form, newQuestdto, moduledto, formdto);
			}
		}
		
		removeExtraQuestionsIfAny(existingQuestiondtos);
	}
	
	/**
	 * Determine if it's an existing question based on public id and version
	 * @param currQuestiondto
	 * @param existingQuestiondtos
	 * @return
	 */
	protected QuestionTransferObject getExistingQuestionDto(QuestionTransferObject currQuestdto, List<QuestionTransferObject> existingQuestiondtos) {
		for (QuestionTransferObject quest : existingQuestiondtos) {
			if (currQuestdto.getPublicId() == quest.getPublicId() && 
					currQuestdto.getVersion().floatValue() == quest.getVersion().floatValue()) {
				currQuestdto.setQuesIdseq(quest.getQuesIdseq());
				currQuestdto.setIdseq(quest.getIdseq());
				//Mark this as being update so we don't collect it to delete later.
				quest.setDisplayOrder(MARK_TO_KEEP_IN_UPDATE);
				return quest;
			}
		}
		
		return null;
	}
	
	/**
	 * Update an existing question's various attributes.
	 * @param question
	 * @param questdto
	 * @param moduledto
	 */
	@Transactional
	protected void updateQuestion(QuestionDescriptor question, QuestionTransferObject questdto, QuestionTransferObject existing,
			ModuleTransferObject moduledto, FormV2TransferObject formdto) {
		
		//What we could update in sbrext.Quest_contents_view_ext
		//display order, long name and asl name
		if (!questdto.getLongName().equals(existing.getLongName()) ||
				questdto.getDisplayOrder() != existing.getDisplayOrder()) {
			int res = questionV2Dao.updateQuestionLongNameDispOrderDeIdseq(questdto);
		}
		
		//What we could update in sbrext.quest_attributes_ext
		//manditory, editable, default value
		QuestionChangeTransferObject qChangedto = new QuestionChangeTransferObject();
		qChangedto.setDefaultValue(question.getDefaultValue());
		qChangedto.setEditable(question.isEditable());
		qChangedto.setMandatory(question.isMandatory());
		qChangedto.setQuestionId(questdto.getQuesIdseq());
		
		qChangedto.setQuestAttrChange(true);
        
		//update isEditable, isMandatory and default value
		questionV2Dao.updateQuestAttr(qChangedto, questdto.getModifiedBy().toUpperCase());
      
		//what to do with isderived?
		
		updateQuestionInstruction(questdto, moduledto, question.getInstruction());
			
		//For valid values, need to first compare what's in db
		updateQuestionValidValues(question, questdto, moduledto, formdto);
		
	}

	@Transactional
	protected void updateQuestionValidValues(QuestionDescriptor question, QuestionTransferObject questdto, 
			ModuleTransferObject moduledto, FormV2TransferObject formdto) {

		List<QuestionDescriptor.ValidValue>  validValues = question.getValidValues();
		List<FormValidValueTransferObject> existingVVs = questionV2Dao.getValidValues(questdto.getQuesIdseq());

		/*
		 * compare vv's long name, preferred definition and display order
		 * 
		 * 
		 */

		int idx = 0;
		for (QuestionDescriptor.ValidValue vValue : validValues) {
			
			FormValidValueTransferObject fvvdto = translateIntoValidValueDto(vValue, questdto, moduledto,
					formdto, idx);
			
			fvvdto.setDisplayOrder(idx++);
			
			if (isExistingValidValue(fvvdto, existingVVs)) {
				fvvdto.setModifiedBy(moduledto.getModifiedBy());
				this.updateQuestionValidValue(fvvdto, vValue, questdto);
			} else {
				String vvSeqid  = 
						formValidValueV2Dao.createValidValue(fvvdto,questdto.getQuesIdseq(),moduledto.getCreatedBy());
				
				if (vvSeqid != null && vvSeqid.length() > 0) {
					formValidValueV2Dao.createValidValueAttributes(vvSeqid, vValue.getMeaningText(), vValue.getDescription(), moduledto.getCreatedBy());
					
					String instr = vValue.getInstruction();
					if (instr != null && instr.length() > 0) {
						InstructionTransferObject instrdto = createInstructionDto(fvvdto, instr);
						formValidValueInstructionV2Dao.createInstruction(instrdto, vvSeqid);
					}
				}
				
			}
		}
	}
	
	protected boolean isExistingValidValue(FormValidValueTransferObject currVV, List<FormValidValueTransferObject> existingVVs) {
		for (FormValidValueTransferObject vv : existingVVs) {
			if (currVV.getLongName().equalsIgnoreCase((vv.getLongName())) 
					&& currVV.getPreferredDefinition().equalsIgnoreCase(vv.getPreferredDefinition())) {
				currVV.setIdseq(vv.getIdseq());
				currVV.setValueIdseq(vv.getValueIdseq());
				return true;
			}
		}
		
		return false;
	}
	
	@Transactional
	protected void updateQuestionValidValue(FormValidValueTransferObject currVV, 
			QuestionDescriptor.ValidValue vvXml, QuestionTransferObject questdto) {
		
		formValidValueV2Dao.updateDisplayOrder(currVV.getValueIdseq(), 
				currVV.getDisplayOrder(), currVV.getModifiedBy());
		
		String meaningText = vvXml.getMeaningText();
		String description = vvXml.getDescription();
		if (meaningText != null && meaningText.length() > 0
				|| description != null && description.length() > 0)
			formValidValueV2Dao.updateValueMeaning(currVV.getValueIdseq(), meaningText, description, currVV.getModifiedBy());
			
		/*
		 * 
		 */
		String instruct = vvXml.getInstruction();
		if (instruct != null && instruct.length() > 0) {
			Instruction instr = new InstructionTransferObject();
			instr.setLongName(instruct);
			instr.setDisplayOrder(0);
			instr.setVersion(new Float(1));
			instr.setAslName("DRAFT NEW");
			instr.setContext(questdto.getContext());
			instr.setPreferredDefinition(instruct);
			instr.setCreatedBy(questdto.getCreatedBy());
			formValidValueInstructionV2Dao.createInstruction(instr, currVV.getValueIdseq());
		}
	}
	
	@Transactional
	public String createFormCollectionRecords(FormCollection coll) {
		
		String collSeqid = null;
		
		//update collection record only if at least one form in it has been loaded successfully
		List<FormDescriptor> forms = coll.getForms();
		for (FormDescriptor form : forms) {
			if (form.getLoadStatus() != FormDescriptor.STATUS_LOADED)
				continue;
			
			if (collSeqid == null)
				collSeqid = collectionDao.createCollectionRecord(coll.getName(), coll.getDescription(), 
						coll.getXmlFileName(), coll.getXmlPathOnServer(), coll.getCreatedBy());
			
			int res = collectionDao.createCollectionFormMappingRecord(collSeqid, form.getFormSeqId(),
					Integer.parseInt(form.getPublicId()), Float.parseFloat(form.getVersion()), form.getLoadType());
			
			//TODO: check response value.
			int loatStatus = (res > 0) ? FormDescriptor.STATUS_LOADED : FormDescriptor.STATUS_LOAD_FAILED;
		}
		
		return collSeqid;
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
	
	//@Transactional(readOnly=true)
	/*
	public void validateDesignationType(FormDescriptor form) {
		if (designationTypes == null) {
			designationTypes = this.formV2Dao.getAllDesignationTypes();
		}
		
		if (designationTypeExists(form)
		
		return false;		//return (designationTypes == null) ? false : designationTypes.contains(form.getd);
	}
	*/
	
	@Transactional(readOnly=true)
	public boolean refDocTypeValid(FormDescriptor form) {
		if (refdocTypes == null) {
			refdocTypes = this.formV2Dao.getAllRefdocTypes();
		}
		
		return false;
	}
	
	@Transactional(readOnly=true)
	public void checkWorkflowStatusName(FormDescriptor form) {
		if (workflowNames == null) {
			workflowNames = this.formV2Dao.getAllWorkflowNames();
		}
		
		String formWfName = form.getWorkflowStatusName();
		if (!workflowNames.contains(formWfName)) {
			form.addMessage("Form's workflow status name invalid. Use default value Draft New");
			form.setWorkflowStatusName(DEFAULT_WORKFLOW_STATUS);
		}
	}
	
	@Transactional
	public void unloadForm(FormDescriptor form) {
		String seqid = form.getFormSeqId();
		if (seqid == null || seqid.length() == 0) {
			logger.debug("Form's seqid is invalid");
			form.addMessage("Form's seqid is invalid. Unable to unload form");
			form.setLoadStatus(FormDescriptor.STATUS_UNLOAD_FAILED);
			return;
		}
		
		int res = formV2Dao.updateWorkflowStatus(seqid, "RETIRED WITHDRAWN");
		if (res <= 0) {
			form.addMessage("Failed to update form's workflow status. Unload failed");
			form.setLoadStatus(FormDescriptor.STATUS_UNLOAD_FAILED);
		} else
			form.setLoadStatus(FormDescriptor.STATUS_UNLOADED);
	}
	
}
