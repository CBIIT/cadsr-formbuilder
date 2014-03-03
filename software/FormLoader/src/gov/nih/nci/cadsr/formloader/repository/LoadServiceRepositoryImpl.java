package gov.nih.nci.cadsr.formloader.repository;

import gov.nih.nci.cadsr.formloader.domain.FormCollection;
import gov.nih.nci.cadsr.formloader.domain.FormDescriptor;
import gov.nih.nci.cadsr.formloader.domain.ModuleDescriptor;
import gov.nih.nci.cadsr.formloader.domain.QuestionDescriptor;
import gov.nih.nci.cadsr.formloader.service.common.FormLoaderHelper;
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
import gov.nih.nci.ncicb.cadsr.common.dto.QuestionChangeTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.QuestionTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.RefdocTransferObjectExt;
import gov.nih.nci.ncicb.cadsr.common.dto.ReferenceDocumentTransferObject;
import gov.nih.nci.ncicb.cadsr.common.exception.DMLException;
import gov.nih.nci.ncicb.cadsr.common.resource.Context;
import gov.nih.nci.ncicb.cadsr.common.resource.Instruction;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

public class LoadServiceRepositoryImpl extends FormLoaderRepositoryImpl {
	
	private static Logger logger = Logger.getLogger(LoadServiceRepositoryImpl.class.getName());
	
	/**
	 * Create new form
	 */
	@Transactional
	public String createForm(FormDescriptor form, String xmlPathName) {
		
		String formSeqid = "";
		
		try {
			FormV2TransferObject formdto = translateIntoFormDTO(form);	
			if (formdto == null) 
				return null;

			formSeqid = formV2Dao.createFormComponent(formdto);		
			logger.debug("Created form. Seqid: " + formSeqid);
			
			retrievePublicIdForForm(formSeqid, form);

			formdto.setFormIdseq(formSeqid);
			form.setFormSeqId(formSeqid);

			createFormInstructions(form, formdto);

			processFormdetails(form, xmlPathName, form.getIndex());

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
	public String createFormNewVersion(FormDescriptor form, String loggedinUser, String xmlPathName) {
		
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
			processFormdetails(form, xmlPathName, form.getIndex());
			updateModulesInForm(form, formdto);
			
			return form.getFormSeqId();
			
		} catch (DMLException dbe) {
			logger.error(dbe.getMessage());
			form.addMessage(dbe.getMessage());
			formSeqid = null;
			form.setVersion("0.0");
			form.setLoadStatus(FormDescriptor.STATUS_LOAD_FAILED);
		}
		
		return formSeqid;
	}
	
	@Transactional
	public String updateForm(FormDescriptor form, String loggedinUser, String xmlPathName) {
		
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
			processFormdetails(form, xmlPathName, form.getIndex());

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
			formdto.setVersion(Float.parseFloat(form.getVersion()));
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
		formdto.setConteIdseq(form.getContextSeqid());
		
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
	
	/**
	 * Process designations, refdocs, definitions and contact communications for a form
	 * @param form
	 * @param xmlPathName
	 * @param currFormIdx
	 */
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
	 * First, check if a protoId is valid (has a match in db)
	 * 
	 * Second, for those that are valid, do the following:
	 * 
	 * 1. New form and new version form: add all listed protocols from xml to form.
	 * 2. update form: replace what's in db for the form with the set from xml.
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
				if (FormDescriptor.LOAD_TYPE_NEW.equals(form.getLoadType())
						|| FormDescriptor.LOAD_TYPE_NEW_VERSION.equals(form.getLoadType())) {
					formV2Dao.addFormProtocol(formSeqid, protoSeqid, form.getCreatedBy());
				} else  {
					//TODO: requirement changed. Need work here 1/17/2014
					//TODO: requirement changed. Need work here 1/17/2014
					if (!formV2Dao.formProtocolExists(formSeqid, protoSeqid))
						formV2Dao.addFormProtocol(formSeqid, protoSeqid, form.getModifiedBy());
					//TODO: requirement changed. Need work here 1/17/2014
					//TODO: Need to remove protocols that exist in db but not in xml
				} 
			}
			
		}
	}
	
	protected List<String> markNoMatchProtoIds(FormDescriptor form, List<String> protoIds, HashMap<String, String> protoIdMap) {
		List<String> protoSeqIds = new ArrayList<String>();
		String nomatchids = "";
		
		int cnt = 0;
		for (String protoId : protoIds) {
			String seqid = protoIdMap.get(protoId);
			if (seqid == null) {
				nomatchids = (nomatchids.length() > 0) ? "," + protoId : protoId;
				cnt++;
			}
			else
				protoSeqIds.add(seqid);
			
		}
		
		if (cnt == 1)
			form.addMessage("Protocol " + nomatchids + " has not registered in caDSR. Not loaded");
		else if (cnt > 1)
			form.addMessage("Protocols " + nomatchids + " have not registered in caDSR. Not loaded");
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
			
			//do we need to go back to db to get module's public id?
			
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
			
			if (fvv.getPreferredDefinition() == null || fvv.getPreferredDefinition().length() == 0) {
				String stop = "debug";
				stop = "stop";
			}
			
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
				//DEBUGG
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
			
			if (vValue.isSkip()) continue;
			
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
		
		String collSeqid = collectionDao.createCollectionRecord(coll.getName(), coll.getDescription(), 
				coll.getXmlFileName(), coll.getXmlPathOnServer(), coll.getCreatedBy(), coll.getNameRepeatNum());
		
		List<FormDescriptor> forms = coll.getForms();		
		for (FormDescriptor form : forms) {
			
			int publicId = (form.getPublicId() == null || form.getPublicId().length() == 0) ? 0 : Integer.parseInt(form.getPublicId());
			float version = (form.getVersion() == null || form.getVersion().length() == 0) ? 0 : Float.parseFloat(form.getVersion());
			Date loadDate = form.getModifiedDate();
			if (loadDate == null)
				loadDate = new Date();
			int res = collectionDao.createCollectionFormMappingRecord(collSeqid, form.getFormSeqId(),
					publicId, version, form.getLoadType(),
					form.getLoadStatus(), form.getLongName(), form.getPreviousLatestVersion(), loadDate);
			
			int loatStatus = (res > 0) ? FormDescriptor.STATUS_LOADED : FormDescriptor.STATUS_LOAD_FAILED;
		}
		
		return collSeqid;
	}
	
	@Transactional
	public void unloadForm(FormDescriptor form) {
		
		if (form == null) {
			logger.warn("Form object is null. Unable to proceed with unload");
			return;
		}
			 
		String seqid = form.getFormSeqId();
		if (seqid == null || seqid.length() == 0) {
			logger.debug("Form's seqid is invalid");
			form.addMessage("Form's seqid is invalid. Unable to unload form");
			form.setLoadStatus(FormDescriptor.STATUS_UNLOAD_FAILED);
			return;
		}
		
		if (WORKFLOW_STATUS_UNLOADED.endsWith(form.getWorkflowStatusName())) {
			form.addMessage("Form [" + form.getFormIdString() + "] had been unloaded previously.");
			form.setLoadStatus(FormDescriptor.STATUS_UNLOAD_FAILED);
			return;
		}
		
		logger.debug("Start unloading form: " + seqid + "|" + form.getFormSeqId());
		int res = formV2Dao.updateWorkflowStatus(seqid, WORKFLOW_STATUS_UNLOADED, FORM_LOADER_DB_USER, form.getChangeNote());
		if (res <= 0) {
			form.addMessage("Failed to update form's workflow status in database. Reason unknown");
			form.setLoadStatus(FormDescriptor.STATUS_UNLOAD_FAILED);
		} else {
			form.setLoadStatus(FormDescriptor.STATUS_UNLOADED);
			manageFormVersionPostUnload(form);
		}
		
		FormV2TransferObject formdto = formV2Dao.getFormHeadersBySeqid(seqid);
		form.setModifiedBy(formdto.getModifiedBy());
		form.setModifiedDate(formdto.getDateModified());
		form.setWorkflowStatusName(formdto.getAslName());
		form.setLoadStatus(FormDescriptor.STATUS_UNLOADED);
		
		logger.debug("Done unloading form: " + seqid + "|" + form.getFormSeqId());
	}
	
	public void updateFormInCollectionRecord(FormCollection coll, FormDescriptor form) {
		if (coll == null || form == null) {
			logger.error("Invalid input on FormCollection obj or FormDescriptor obj");
			return;
		}
		
		this.collectionDao.updateCollectionFormMappingRecord(coll.getId(), form.getFormSeqId(), 
			 form.getLoadType(), form.getLoadStatus());
	}
	
	/**
	 * Post unload tasks:
	 * 
	 * If the form had been loaded as "New Version" AND if it was marked as the latest version prior to unload, we
	 *    need to 1) set latest version indicator to "no" and 2) restore the previous latest version 
	 *    (latest version form before the form was loaded).
	 *    
	 * @param form
	 */
	protected void manageFormVersionPostUnload(FormDescriptor form) {
		String seqid = form.getFormSeqId();

		logger.debug("Form: " + seqid + "|" + form.getFormSeqId() + " unloaded");

		if (!formV2Dao.isLatestVersionForForm(seqid) ||
				!FormDescriptor.LOAD_TYPE_NEW_VERSION.equals(form.getLoadType().trim()))
			return; 

		formV2Dao.updateLatestVersionIndicator(seqid, "No", FORM_LOADER_DB_USER);

		float prevVersion = form.getPreviousLatestVersion();
		logger.debug("Form: " + seqid + "|" + form.getFormSeqId() + " unloaded. Previous Lastest version was " + prevVersion);
		if (prevVersion > 0) {
			int r = formV2Dao.updateLatestVersionIndicatorByPublicIdAndVersion(Integer.parseInt(form.getPublicId()), 
					prevVersion, "Yes", FORM_LOADER_DB_USER);
			form.addMessage("After unload, the latest version for the form with pulbic id [" + form.getPublicId() +
					"] is reset to " + prevVersion);
			logger.debug("Previous Lastest version was restored: " + prevVersion + ". Response: " + r);
		} else {
			form.addMessage("No valid previous latest version with form. Unable to restore previous latest " +
					"version after unloading a new version form");
			logger.debug("No valid previous latest version with form");
		}

	}
	
	
}
