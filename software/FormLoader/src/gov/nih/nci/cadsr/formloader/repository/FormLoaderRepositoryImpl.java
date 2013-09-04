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
import gov.nih.nci.ncicb.cadsr.common.dto.FormTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.FormV2TransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.FormValidValueTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.InstructionTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.ModuleTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.PermissibleValueV2TransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.QuestionChangeTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.QuestionTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.ReferenceDocumentTransferObject;
import gov.nih.nci.ncicb.cadsr.common.exception.DMLException;
import gov.nih.nci.ncicb.cadsr.common.persistence.ErrorCodeConstants;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc.JDBCCollectionDAO;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc.JDBCFormDAOV2;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc.JDBCFormInstructionDAOV2;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc.JDBCFormValidValueDAOV2;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc.JDBCFormValidValueInstructionDAOV2;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc.JDBCModuleDAOV2;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc.JDBCQuestionDAOV2;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc.JDBCQuestionInstructionDAOV2;
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
	public static final	String DEFAULT_REFDOC_TYPE	= "To find out";
	
	protected static final int MARK_TO_KEEP = 99;
	
	JDBCFormDAOV2 formV2Dao;
	JDBCModuleDAOV2 moduleV2Dao;
	JDBCQuestionDAOV2 questionV2Dao;
	JDBCValueDomainDAOV2 valueDomainV2Dao;
	JDBCFormInstructionDAOV2 formInstructionV2Dao;
	JDBCQuestionInstructionDAOV2 questInstructionV2Dao;
	JDBCFormValidValueDAOV2 formValidValueV2Dao;
	JDBCFormValidValueInstructionDAOV2 formValidValueInstructionV2Dao;
	JDBCCollectionDAO collectionDao;
	
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
				questionV2Dao.getAllReferenceDocuments(
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
	public List<FormCollection> getAllLoadedCollections() {
		
		//first get collection headers
		List<FormCollection> colls = collectionDao.getAllLoadedCollections();
		
		for (FormCollection coll : colls) {
			if (coll.getId().equals("E49101B2-1B48-BA26-E040-BB8921B61DC6")) {
				logger.debug("debug");
				
			}
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
	
	protected void retrievePublicIdForQuestion(String questSeqid, QuestionDescriptor quest) {
		QuestionTransferObject dto = this.questionV2Dao.getQuestionsPublicIdVersionBySeqid(questSeqid);
		quest.setPublicId(String.valueOf(dto.getPublicId()));
		quest.setVersion(String.valueOf(dto.getVersion()));
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
	
	/**
	 * Create new form or new version of an existing form
	 */
	@Transactional
	public String createForm(FormDescriptor form, String loggedinUser, String xmlPathName, int formIdx) {
		
		String formSeqid = "";
		
		try {
			FormTransferObject formdto = translateIntoFormDTO(form);	
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
		}
		
		return formSeqid;
	}
	
	/**
	 * Create new form or new version of an existing form
	 */
	@Transactional
	public String createFormNewVersion(FormDescriptor form, String loggedinUser, String xmlPathName, int formIdx) {
		
		String formSeqid = "";
		
		try {
			FormTransferObject formdto = translateIntoFormDTO(form);	
			if (formdto == null) 
				return null;

			formSeqid = formV2Dao.createNewFormVersion(formdto.getFormIdseq(), formdto.getVersion(), 
					"New version form by Form Loader", formdto.getCreatedBy());		
			
			logger.debug("Created new version for form. Seqid: " + formSeqid);
			
			formdto.setFormIdseq(formSeqid);
			form.setFormSeqId(formSeqid);

			updateForm(form, loggedinUser, xmlPathName, formIdx);
			
		} catch (DMLException dbe) {
			logger.error(dbe.getMessage());
		}
		
		return formSeqid;
	}
	
	@Transactional
	public String updateForm(FormDescriptor form, String loggedinUser, String xmlPathName, int formIdx) {
		
		FormTransferObject formdto = translateIntoFormDTO(form);	
		if (formdto == null) {
			logger.error("Error!! Failed to translate xml form into FormTransferObject");
			return null;
		}

		int res = formV2Dao.updateFormComponent(formdto);
		if (res != 1) {
			logger.error("Error!! Failed to update form");
			return null;
		}
		
		//form.setFormSeqId(formdto.getFormIdseq());
		
		//TODO: check with Denise if this is right
		//On Form Builder, you could only modify what's already in the header or footer
		//And there'll only be one record associated with the form
		createFormInstructions(form, formdto);
		
		//designations, refdocs, definitions and contact communications
		processFormdetails(form, xmlPathName, formIdx);
		
		//Onto modules and questions
		updateModulesInForm(form, formdto);
		
		return form.getFormSeqId();
	}
	
	protected FormTransferObject translateIntoFormDTO(FormDescriptor form) {
			
		FormTransferObject formdto = new FormTransferObject();
		String loadType = form.getLoadType(); 
		if (FormDescriptor.LOAD_TYPE_NEW_VERSION.equals(loadType)) {
			float latest = this.formV2Dao.getMaxFormVersion(Integer.parseInt(form.getPublicId()));
			formdto.setVersion(new Float(latest + 1.0)); //TODO: is this the way to assign new version?
			formdto.setPublicId(Integer.parseInt(form.getPublicId()));
			formdto.setFormIdseq(form.getFormSeqId());
			formdto.setAslName("DRAFT NEW");
			formdto.setCreatedBy(form.getCreatedBy()); 
		} else if (FormDescriptor.LOAD_TYPE_NEW.equals(loadType)) {
			formdto.setVersion(Float.valueOf("1.0")); 
			formdto.setAslName("DRAFT NEW");
			formdto.setCreatedBy(form.getCreatedBy()); 
		} else if (FormDescriptor.LOAD_TYPE_UPDATE_FORM.equals(loadType)) {
			formdto.setVersion(Float.valueOf(form.getVersion()));
			formdto.setAslName(form.getWorkflowStatusName()); 
			formdto.setModifiedBy(form.getModifiedBy());
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
		
		String contextName = form.getContext();
		
		if (contextName != null && contextName.length() > 0) {
			Context context = new ContextTransferObject();
		    context.setConteIdseq(this.getContextSeqIdByName(contextName));
		    formdto.setContext(context);
			formdto.setConteIdseq(this.getContextSeqIdByName(contextName));
		} else {
			String msg = "Form doesn't have a valid context name. Unable to load";
			form.addMessage(msg);
			form.setLoadStatus(FormDescriptor.STATUS_LOAD_FAILED);
			logger.error(form.getFormIdString() + ": " + msg);
			return null; 
		}
		
		
		return formdto;
	}
	
	
	
	protected void createFormInstructions(FormDescriptor form, FormTransferObject formdto) {
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
	
	protected List<String> getCdeSeqIdsFromForm(FormDescriptor form) {
		return null;
	}
	
	protected void processFormdetails(FormDescriptor form, String xmlPathName, int currFormIdx) {
		logger.debug("Processing protocols, designations, refdocs and definitions for form");
		StaXParser parser = new StaXParser();
		parser.parseFormDetails(xmlPathName, form, currFormIdx);
		
		List<String> protoIds = parser.getProtocolIds();
		processProtocols(form, protoIds);
		
		List<DesignationTransferObjectExt> designations = parser.getDesignations();
		processDesignations(form, designations);
		
		List<DefinitionTransferObject> definitions = parser.getDefinitions();
		
		//TODO
		//processRefdocs();
		//processContactCommunications()
		
		
		logger.debug("Done processing protocols, designations, refdocs and definitions for form");
	}

	/**
	 * For new version and update form, if designation doesn't already exist, designate it to the context. For new form,
	 * designate the form to the context.
	 * @param form
	 * @param designations
	 */
	protected void processDesignations(FormDescriptor form, List<DesignationTransferObjectExt> designations) {
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
					form.addMessage("Designation type \"" + desigType + "\" is invalid. Use default type Form Loader");
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
	protected void createModulesInForm(FormDescriptor form, FormTransferObject formdto) {
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
			
			//Now, onto questions
			createQuestionsInModule(module, moduledto, form, formdto);
		}
		
		logger.debug("Done creating modules for form");
	}
	
	@Transactional
	protected void createQuestionsInModule(ModuleDescriptor module, ModuleTransferObject moduledto, 
			FormDescriptor form, FormTransferObject formdto) {
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
			retrievePublicIdForQuestion(seqid, question);
			
			createQuestionInstruction(newQuestdto, moduledto, question.getInstruction());
			
			createQuestionValidValues(question, form, newQuestdto, moduledto);
		}
		
		
		
		logger.debug("Done creating questions for module");
	}
	
	@Transactional
	protected void createQuestionValidValues(QuestionDescriptor question, FormDescriptor form, QuestionTransferObject newQuestdto, 
			ModuleTransferObject moduledto) {
		
		List<QuestionDescriptor.ValidValue>  validValues = question.getValidValues();
		
		int idx = 0;
		for (QuestionDescriptor.ValidValue vValue : validValues) {
			if (vValue.isSkip()) continue;
			
			idx++;
			FormValidValueTransferObject fvv = new FormValidValueTransferObject();
			
			fvv.setCreatedBy(moduledto.getCreatedBy());
			fvv.setQuestion(newQuestdto);
			fvv.setVpIdseq(vValue.getVdPermissibleValueSeqid());
			fvv.setVersion(Float.valueOf("1.0"));		
			
			//PreferredName format: value meaning public id_quetionpublicid_form_public_id_version_<x> x = 1, 2, 3
			String preferredName = composeVVPreferredName(form, question, vValue, idx);
			
			fvv.setLongName(vValue.getValue());
			fvv.setPreferredName(preferredName);
			fvv.setPreferredDefinition(vValue.getDescription());
			
			fvv.setContext(moduledto.getContext());
			fvv.setAslName("DRAFT NEW");
			fvv.setCreatedBy(moduledto.getCreatedBy());
			fvv.setDisplayOrder(idx);
			
			//in.put("p_proto_idseq", protocolIdSeq);
			
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
	
	//PreferredName format: value meaning public id_quetionpublicid_form_public_id_version_<x> x = 1, 2, 3
	protected String composeVVPreferredName(FormDescriptor form, QuestionDescriptor question, 
			QuestionDescriptor.ValidValue vValue, int displayorder) {
		
		return vValue.getPreferredName() + "_" + question.getPublicId() + "_" + displayorder;
		
	}
	
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
	
	
	protected QuestionTransferObject translateIntoQuestionDTO(QuestionDescriptor question, FormDescriptor form) {
		QuestionTransferObject questdto = new QuestionTransferObject();
		
		String deSeqid = question.getCdeSeqId();
		if (deSeqid != null && deSeqid.length() > 0) {
			DataElementTransferObject de = new DataElementTransferObject();
			de.setDeIdseq(deSeqid);
			questdto.setDataElement(de);
		}
			
		if (!FormDescriptor.LOAD_TYPE_UPDATE_FORM.equals(form.getLoadType())) {
			questdto.setVersion(Float.valueOf("1.0")); 
			questdto.setCreatedBy(form.getCreatedBy());
		}
		else {
			questdto.setVersion(Float.valueOf(question.getVersion())); 
			questdto.setModifiedBy(form.getModifiedBy());
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
	
	protected ModuleTransferObject translateIntoModuleDTO(ModuleDescriptor module, FormDescriptor form, FormTransferObject formdto) {
		ModuleTransferObject moduleDto = new ModuleTransferObject();
		
		moduleDto.setForm(formdto);
		moduleDto.setAslName("DRAFT NEW");
		moduleDto.setLongName(module.getLongName());
		
		if (!FormDescriptor.LOAD_TYPE_UPDATE_FORM.equals(form.getLoadType())) {
				moduleDto.setVersion(Float.valueOf("1.0")); 
				moduleDto.setCreatedBy(formdto.getCreatedBy());
		}
		else {
			moduleDto.setModifiedBy(form.getModifiedBy());
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
	protected void updateModulesInForm(FormDescriptor form, FormTransferObject formdto) {
		
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
				int res = moduleV2Dao.updateModuleComponent(moduledto);
				//TODO: better understand spring sqlupdate return code
				updateQuestionsInModule(module, moduledto, form, formdto);
			}
			else {
				moduledto.setContext(formdto.getContext());
				moduleSeqid = moduleV2Dao.createModuleComponent(moduledto);
				module.setModuleSeqId(moduleSeqid);
				moduledto.setIdseq(moduleSeqid);
				//Now, onto questions. Ignore version from xml. Start from 1.0
				
				resetQeustionVersionInModule(module); //
				createQuestionsInModule(module, moduledto, form, formdto);
			}
			
		}
		
		//Find the existing modules to be deleted
		removeExtraModulesIfAny(existingModuledtos);
	}
	
	protected boolean isExistingModule(ModuleTransferObject currModule, List<ModuleTransferObject> existingModuledtos) {
		
		for (ModuleTransferObject moduledto : existingModuledtos) {
			if ((moduledto.getPublicId() == currModule.getPublicId()) &&
					(moduledto.getVersion().floatValue() == currModule.getVersion().floatValue())) {
				currModule.setIdseq(moduledto.getIdseq());
				currModule.setModuleIdseq(moduledto.getModuleIdseq()); //don't know how these are used
				moduledto.setDisplayOrder(MARK_TO_KEEP); //use this to indicate module's taken
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Check the list to see if any is marked with MARK_FOR_DELETE. Delete the marked ones.
	 * @param existingmoduledtos
	 */
	protected void removeExtraModulesIfAny(List<ModuleTransferObject> existingmoduledtos) {
		for (ModuleTransferObject moduledto : existingmoduledtos) {
			if (moduledto.getDisplayOrder() != MARK_TO_KEEP) {
				logger.debug("Found a module to delete: [" + moduledto.getPublicId() + "|" + moduledto.getVersion() + "]");
				this.moduleV2Dao.deleteModule(moduledto.getModuleIdseq());
			}
		}
	}
	
	@Transactional
	protected void removeExtraQuestionsIfAny(List<QuestionTransferObject> questiondtos) {
		//Method copied from FormBuilderEJB
		
		for (QuestionTransferObject qdto : questiondtos) {
			if (qdto.getDisplayOrder() == MARK_TO_KEEP)
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
	protected void updateQuestionsInModule(ModuleDescriptor module, ModuleTransferObject moduledto, 
			FormDescriptor form, FormTransferObject formdto) {
		
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
				updateQuestion(question, questdto, existingquestdto, moduledto);
				
			} else {
				//better to call createQuestionComponents, which is not implemented.
				QuestionTransferObject newQuestdto = (QuestionTransferObject)this.questionV2Dao.createQuestionComponent(questdto);
				createQuestionInstruction(newQuestdto, moduledto, question.getInstruction());
				createQuestionValidValues(question, form, newQuestdto, moduledto);
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
				quest.setDisplayOrder(MARK_TO_KEEP);
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
			ModuleTransferObject moduledto) {
		
		//What we could update in sbrext.Quest_contents_view_ext
		//display order, long name and asl name
		if (!questdto.getLongName().equals(existing.getLongName()) ||
				questdto.getDisplayOrder() != existing.getDisplayOrder()) {
			int re = questionV2Dao.updateQuestionLongNameDispOrderDeIdseq(questdto);
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
		
		this.createQuestionInstruction(questdto, moduledto, question.getInstruction());
			
		//For valid values, need to first compare what's in db
		updateQuestionValidValues(question, questdto, moduledto);
		
	}

	protected void updateQuestionValidValues(QuestionDescriptor question, QuestionTransferObject questdto, 
			ModuleTransferObject moduledto) {

		List<QuestionDescriptor.ValidValue>  validValues = question.getValidValues();
		List<FormValidValueTransferObject> existingVVs = questionV2Dao.getValidValues(questdto.getQuesIdseq());

		/*
		 * compare vv's long name, preferred definition and display order
		 * 
		 * 
		 */

		int idx = 0;
		for (QuestionDescriptor.ValidValue vValue : validValues) {
			
			FormValidValueTransferObject fvvdto = new FormValidValueTransferObject();

			fvvdto.setCreatedBy(moduledto.getCreatedBy());
			fvvdto.setQuestion(questdto);
			fvvdto.setVpIdseq(vValue.getVdPermissibleValueSeqid());
			fvvdto.setVersion(Float.valueOf("1.0"));

			//These are for calling the stored procedure "sbrext_form_builder_pkg.ins_value"

			//These values are from the permissible value query
			fvvdto.setLongName(vValue.getValue());
			fvvdto.setPreferredDefinition(vValue.getDescription());
			fvvdto.setPreferredName(vValue.getMeaningText());
			fvvdto.setContext(moduledto.getContext());
			fvvdto.setAslName("DRAFT NEW");
			fvvdto.setCreatedBy(moduledto.getCreatedBy());
			
			fvvdto.setDisplayOrder(idx++);

			//in.put("p_proto_idseq", protocolIdSeq);
			
			if (isExistingValidValue(fvvdto, existingVVs)) {
				fvvdto.setModifiedBy(moduledto.getModifiedBy());
				this.updateQuestionValidValue(fvvdto, vValue, questdto);
			} else {
				String newFVVIdseq =
						formValidValueV2Dao.createFormValidValueComponent(
								fvvdto,questdto.getQuesIdseq(),questdto.getCreatedBy());
				
				//TODO: handle instructions
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
		String collSeqid = collectionDao.createCollectionRecord(coll.getName(), coll.getDescription(), 
				coll.getXmlFileName(), coll.getXmlPathOnServer(), coll.getCreatedBy());
		
		if (collSeqid == null || collSeqid.length() == 0) {
			logger.error("Error!!! while creating record for collection: " + coll.getName() +
					" Uable to create form and collection mappings.");
			return null;
			
		}
		
		List<FormDescriptor> forms = coll.getForms();
		for (FormDescriptor form : forms) {
			if (form.getLoadStatus() != FormDescriptor.STATUS_LOADED)
				continue;
			
			int res = collectionDao.createCollectionFormMappingRecord(collSeqid, form.getFormSeqId(),
					Integer.parseInt(form.getPublicId()), Float.parseFloat(form.getVersion()), form.getLoadType());
			
			//TODO: check response value.
			int loatStatus = (res > 0) ? FormDescriptor.STATUS_LOADED : FormDescriptor.STATUS_LOAD_FAILED;
			
		}
		
		return collSeqid;
	}
	
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
	public boolean designationTypeValid(FormDescriptor form) {
		if (designationTypes == null) {
			designationTypes = this.formV2Dao.getAllDesignationTypes();
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
