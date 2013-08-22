package gov.nih.nci.cadsr.formloader.repository;

import gov.nih.nci.cadsr.formloader.domain.FormCollection;
import gov.nih.nci.cadsr.formloader.domain.FormDescriptor;
import gov.nih.nci.cadsr.formloader.domain.ModuleDescriptor;
import gov.nih.nci.cadsr.formloader.domain.QuestionDescriptor;
import gov.nih.nci.cadsr.formloader.service.common.StaXParser;
import gov.nih.nci.ncicb.cadsr.common.dto.ContextTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.DataElementTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.DefinitionTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.DesignationTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.FormTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.FormV2TransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.FormValidValueChangesTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.FormValidValueTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.ModuleTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.PermissibleValueV2TransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.QuestionChangeTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.QuestionTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.ReferenceDocumentTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.InstructionTransferObject;
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
import gov.nih.nci.ncicb.cadsr.common.resource.PermissibleValueV2;
import gov.nih.nci.ncicb.cadsr.common.resource.ValueDomainV2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class FormLoaderRepositoryImpl implements FormLoaderRepository {
	private static Logger logger = Logger.getLogger(FormLoaderRepositoryImpl.class.getName());
	
	static final int MAX_LONG_NAME_LENGTH = 255;
	
	public static final String COMPONENT_TYPE_FORM = "QUEST_CONTENT";
	
	JDBCFormDAOV2 formV2Dao;
	JDBCModuleDAOV2 moduleV2Dao;
	JDBCQuestionDAOV2 questionV2Dao;
	JDBCValueDomainDAOV2 valueDomainV2Dao;
	JDBCFormInstructionDAOV2 formInstructionV2Dao;
	JDBCQuestionInstructionDAOV2 questInstructionV2Dao;
	JDBCFormValidValueDAOV2 formValidValueV2Dao;
	JDBCFormValidValueInstructionDAOV2 formValidValueInstructionV2Dao;
	JDBCCollectionDAO collectionDao;
	
	HashMap<String, String> conteNameSeqIdMap;
	List<String> designationTypes;

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
	public void setPublicIdVersionBySeqids(List<FormDescriptor> forms) {
		List<String> seqids = new ArrayList<String>();
		for (FormDescriptor form : forms) {
			seqids.add(form.getFormSeqId());
		}
		
		HashMap<String, FormV2TransferObject> seqidMap = formV2Dao.getFormsBySeqids(seqids);
		for (FormDescriptor form : forms) {
			FormV2TransferObject formdto = seqidMap.get(form.getFormSeqId());
			form.setPublicId(String.valueOf(formdto.getPublicId()));
			form.setVersion(String.valueOf(formdto.getVersion()));
		}
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
	
	@Transactional
	public String updateForm(FormDescriptor form, String loggedinUser, String xmlPathName, int formIdx) {
		
		FormTransferObject formdto = translateIntoFormDTO(form);	
		if (formdto == null) 
			return null;

		//when loading, first check user from xml form. If that's invalid,
		//check with the form loader user name. If neither works, inform user
		// - confirmed with Denise on 8/7/2013
		int res;
		for (int tryCnt = 0; tryCnt < 2; tryCnt++) {
			try {
				res = formV2Dao.updateFormComponent(formdto);
			} catch (DMLException dmle) {
				if (dmle.getErrorCode() == ErrorCodeConstants.INSUFFICIENT_PRIVILEGES &&
						tryCnt == 0) {
					logger.warn("This user \"" + formdto.getModifiedBy() + 
							"\" doesn't have enough privileges to modify form in context \"" + 
							form.getContext() + "\". Trying again with the loggedin user name");
					formdto.setModifiedBy(loggedinUser);
				} else {
					logger.error("Unable to update [" + form.getFormIdString() + "] with reason: " + dmle.getMessage());
					form.addMessage("Failed to load form with reason: " + dmle.getMessage());
					form.setLoadStatus(FormDescriptor.STATUS_LOAD_FAILED);
					return null;
				}
					
			}
		}
		
		form.setFormSeqId(formdto.getFormIdseq());
		
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
			String seqid = form.getFormSeqId();
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
		
		InstructionTransferObject formInstruction = createInstruction(form, formdto, instructString);
		if (formInstruction != null)
			formInstructionV2Dao.createInstruction(formInstruction, formdto.getFormIdseq());
		
		instructString = form.getFooterInstruction();
		formInstruction = createInstruction(form, formdto, instructString);
		if (formInstruction != null)
			formInstructionV2Dao.createFooterInstruction(formInstruction, formdto.getFormIdseq());
		
		logger.debug("Done creating instructions for form");
	}
	
	protected InstructionTransferObject createInstruction(FormDescriptor form, 
			FormTransferObject formdto, String instructionString) {
			
		InstructionTransferObject formInstruction = null;
		if (instructionString != null && instructionString.length() > 0) {
			formInstruction = new InstructionTransferObject();
			formInstruction.setLongName(form.getLongName());
			formInstruction.setPreferredDefinition(instructionString);
			formInstruction.setContext(formdto.getContext());
			formInstruction.setAslName("DRAFT NEW");
			formInstruction.setVersion(new Float(1.0));
			formInstruction.setCreatedBy(form.getCreatedBy());
			formInstruction.setDisplayOrder(1);
		}
		
		return formInstruction;
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
		
		List<DesignationTransferObject> designations = parser.getDesignations();
		processDesignations(form, designations);
		
		List<DefinitionTransferObject> definitions = parser.getDefinitions();
		
		//TODO
		//processRefdocs();
		//processContactCommunications()
		
		
		logger.debug("Done processing protocols, designations, refdocs and definitions for form");
	}

	/**
	 * For update form, if designation doesn't already exist, designate it to the context. For new version or new form,
	 * designate the form to the context.
	 * @param form
	 * @param designations
	 */
	protected void processDesignations(FormDescriptor form, List<DesignationTransferObject> designations) {
		String formSeqid = form.getFormSeqId();
		String contextSeqId = this.getContextSeqIdByName(form.getContext());
		
		for (DesignationTransferObject desig : designations) {
			if (form.getLoadType().equals(FormDescriptor.LOAD_TYPE_UPDATE_FORM)) {
				List<DesignationTransferObject> existing = formV2Dao.getDesignationsForForm(
						formSeqid, desig.getName(), desig.getType(), desig.getLanguage());
				if (existing == null || existing.size() == 0) {
					designateForm(formSeqid, contextSeqId, form.getCreatedBy(), desig);
				}
			} else {
				designateForm(formSeqid, contextSeqId, form.getCreatedBy(), desig);
			}
		}
	}
	
	protected int designateForm(String formSeqid, String contextSeqid, String createdby,
			DesignationTransferObject desig) {
		List<String> ac_seqids = new ArrayList<String>();
		ac_seqids.add(formSeqid);
		return formV2Dao.designate(contextSeqid, ac_seqids, createdby);
	}
	
	/**
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
				if (FormDescriptor.LOAD_TYPE_UPDATE_FORM.equals(form.getLoadType())) {
					if (!formV2Dao.formProtocolExists(formSeqid, protoSeqid));
						formV2Dao.addFormProtocol(formSeqid, protoSeqid, form.getModifiedBy());

				} else {
					formV2Dao.addFormProtocol(formSeqid, protoSeqid, form.getCreatedBy());
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
			logger.debug("Created a question: " + newQuestdto.getQuesIdseq());
			
			
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
			FormValidValueTransferObject fvv = new FormValidValueTransferObject();
			
			fvv.setCreatedBy(moduledto.getCreatedBy());
			fvv.setQuestion(newQuestdto);
			fvv.setVpIdseq(vValue.getVdPermissibleValueSeqid());
			fvv.setVersion(Float.valueOf("1.0"));
			
			//These are for calling the stored procedure "sbrext_form_builder_pkg.ins_value"		
			
			
			fvv.setLongName(vValue.getValue());
			fvv.setPreferredName(vValue.getMeaningText());
			fvv.setPreferredDefinition(vValue.getDescription());
			
			fvv.setContext(moduledto.getContext());
			fvv.setAslName("DRAFT NEW");
			fvv.setCreatedBy(moduledto.getCreatedBy());
			fvv.setDisplayOrder(idx++);
			
			//in.put("p_proto_idseq", protocolIdSeq);
		     
			
			//createFormValidValueComponent() will 
			//create valid value and record in the valid_value_att_ext 
			
			//TODO: failed to create. complaining about null version. Even hardcoded one still trigger complain.
			// Investigate later
			
			
			//String newFVVIdseq =
			//		formValidValueV2Dao.createFormValidValueComponent(
			//				fvv,newQuestdto.getQuesIdseq(),moduledto.getCreatedBy());
		}
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
			
		if (!FormDescriptor.LOAD_TYPE_UPDATE_FORM.equals(form.getLoadType())) 
			questdto.setVersion(Float.valueOf("1.0")); 
		else {
			questdto.setVersion(Float.valueOf(question.getVersion())); 
			//questdto.setModifiedBy(form.getModifiedBy());
		}
		
		questdto.setEditable(question.isEditable());
		questdto.setMandatory(question.isMandatory());
		questdto.setDefaultValue(question.getDefaultValue());
		
		questdto.setAslName("DRAFT NEW");
		//xsd doesn't have long name for question
		//use question text - confirmed with Denise
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
		
		for (ModuleDescriptor module : modules) {
			ModuleTransferObject moduledto = translateIntoModuleDTO(module, form, formdto);
			
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
	}
	
	protected boolean isExistingModule(ModuleTransferObject currModule, List<ModuleTransferObject> existingModuledtos) {
		
		for (ModuleTransferObject moduledto : existingModuledtos) {
			if (moduledto.getPublicId() == currModule.getPublicId() &&
					moduledto.getVersion() == currModule.getVersion()) {
				currModule.setIdseq(moduledto.getIdseq());
				currModule.setModuleIdseq(moduledto.getModuleIdseq()); //don't know how these are used
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Reset all question versions to 1.0 in a module
	 * @param module
	 */
	protected void resetQeustionVersionInModule(ModuleDescriptor module) {
		List<QuestionDescriptor> questions = module.getQuestions();
		for (QuestionDescriptor question : questions) {
			question.setVersion("1.0");
		}
	}
	
	
	protected void updateQuestionsInModule(ModuleDescriptor module, ModuleTransferObject moduledto, 
			FormDescriptor form, FormTransferObject formdto) {
		
		List<QuestionTransferObject> existingQuestiondtos = 
				moduleV2Dao.getQuestionsInAModuleV2(moduledto.getModuleIdseq());
		
		List<QuestionDescriptor> questions = module.getQuestions();
		
		/*
		 * Need to think about the display order
		 * 
		 * 
		 */
		
		int idx = 0;
		for (QuestionDescriptor question : questions) {
			if (question.isSkip()) continue;
			
			QuestionTransferObject questdto = translateIntoQuestionDTO(question, form);
	
			questdto.setContext(formdto.getContext());
			questdto.setModule(moduledto);
			questdto.setModifiedBy(module.getModifiedBy());
			
			//TODO: display order needs to come from xml
			questdto.setDisplayOrder(idx++);
			
			if (isExistingQuestion(questdto, existingQuestiondtos)) {
				
				
			} else {
				//better to call createQuestionComponents, which is not implemented.
				QuestionTransferObject newQuestdto = (QuestionTransferObject)this.questionV2Dao.createQuestionComponent(questdto);
				createQuestionInstruction(newQuestdto, moduledto, question.getInstruction());
				createQuestionValidValues(question, form, newQuestdto, moduledto);
			}
		}
	}
	
	protected boolean isExistingQuestion(QuestionTransferObject currQuestiondto, List<QuestionTransferObject> existingQuestiondtos) {
		for (QuestionTransferObject quest : existingQuestiondtos) {
			if (currQuestiondto.getPublicId() == quest.getPublicId() && 
				 currQuestiondto.getVersion() == quest.getVersion()) {
				currQuestiondto.setQuesIdseq(quest.getQuesIdseq());
				currQuestiondto.setIdseq(quest.getIdseq());
				return true;
			}
		}
		
		return false;
	}
	
	protected void updateQuestion(QuestionDescriptor question, QuestionTransferObject questdto,
			ModuleTransferObject moduledto) {
		
		int re = questionV2Dao.updateQuestionLongNameDispOrderDeIdseq(questdto);
		
		QuestionChangeTransferObject qChangedto = new QuestionChangeTransferObject();
		
		//fill data
        
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
			int res = collectionDao.createCollectionFormMappingRecord(collSeqid, form.getFormSeqId(),
					Integer.parseInt(form.getPublicId()), Float.parseFloat(form.getVersion()));
			
			//TODO: check response value.
			int loatStatus = (res == 1) ? FormDescriptor.STATUS_LOADED : FormDescriptor.STATUS_LOAD_FAILED;
			
			form.setLoadStatus(loatStatus);
			
		}
		
		return collSeqid;
	}
	
	public boolean hasLoadFormRight(String userName, String contextName) {
		String contextseqid = this.getContextSeqIdByName(contextName);
		return this.formV2Dao.hasCreate(userName, COMPONENT_TYPE_FORM, contextseqid);
	}
	
	/*
	public boolean definitionTypeValid(String defType) {
		if (designationTypes == null) {
			
		}
	}
	*/
}
