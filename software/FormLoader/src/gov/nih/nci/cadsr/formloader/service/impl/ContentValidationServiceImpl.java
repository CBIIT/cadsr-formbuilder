package gov.nih.nci.cadsr.formloader.service.impl;

import gov.nih.nci.cadsr.formloader.domain.FormCollection;
import gov.nih.nci.cadsr.formloader.domain.FormDescriptor;
import gov.nih.nci.cadsr.formloader.domain.ModuleDescriptor;
import gov.nih.nci.cadsr.formloader.domain.QuestionDescriptor;
import gov.nih.nci.cadsr.formloader.repository.FormLoaderRepository;
import gov.nih.nci.cadsr.formloader.repository.FormLoaderRepositoryImpl;
import gov.nih.nci.cadsr.formloader.service.ContentValidationService;
import gov.nih.nci.cadsr.formloader.service.common.FormLoaderHelper;
import gov.nih.nci.cadsr.formloader.service.common.FormLoaderServiceException;
import gov.nih.nci.cadsr.formloader.service.common.StaXParser;
import gov.nih.nci.ncicb.cadsr.common.dto.DataElementTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.PermissibleValueV2TransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.QuestionTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.ReferenceDocumentTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.ValueMeaningV2TransferObject;
import gov.nih.nci.ncicb.cadsr.common.resource.FormV2;
import gov.nih.nci.ncicb.cadsr.common.resource.PermissibleValueV2;
import gov.nih.nci.ncicb.cadsr.common.resource.ReferenceDocument;
import gov.nih.nci.ncicb.cadsr.common.resource.ValueMeaningV2;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
//import gov.nih.nci.cadsr.formloader.repository.FormLoaderRepositoryImpl;

@Service
public class ContentValidationServiceImpl implements ContentValidationService {
	
	private static Logger logger = Logger.getLogger(ContentValidationServiceImpl.class.getName());
	
	FormLoaderRepository repository;
	
	public ContentValidationServiceImpl() {}
	
	public ContentValidationServiceImpl(FormLoaderRepository repository) {
		this.repository = repository;
	}

	public FormLoaderRepository getRepository() {
		return repository;
	}

	public void setRepository(FormLoaderRepository repository) {
		this.repository = repository;
	}

	@Override
	public FormCollection validateXmlContent(FormCollection aCollection) 
			throws FormLoaderServiceException {
		
		if (aCollection == null)
			throw new FormLoaderServiceException(FormLoaderServiceException.ERROR_COLLECTION_NULL,
					"Input collection is null");
		
		List<FormDescriptor> formHeaders = aCollection.getForms();
		if (formHeaders == null || formHeaders.size() == 0) {
			logger.error("Input form list is null or empty. Nothing to do");
			throw new FormLoaderServiceException(FormLoaderServiceException.ERROR_EMPTY_FORM_LIST,
					"Input form list is null or empty. Unable to validate form content.");
		}
		
		String loggedinUser = aCollection.getCreatedBy();
		if (loggedinUser == null || loggedinUser.length() == 0) {
			logger.error("Collection's createdBy (Form Loader's logged in user) is not set. Unable to proceed");
			throw new FormLoaderServiceException(FormLoaderServiceException.ERROR_USER_INVALID,
					"Collection's createdBy (Form Loader's logged in user) is not set. Unable to proceed.");
		}
		
		String xmlPathName = FormLoaderHelper.checkInputFile(aCollection.getXmlPathOnServer(), aCollection.getXmlFileName());
			
		if (aCollection.isSelectAllForms()) 
			setSelectedToForms(formHeaders);
		
		determineLoadType(formHeaders);
		 
		validateFormInfo(formHeaders, loggedinUser);
		
		validateQuestions(xmlPathName, formHeaders);
		
		aCollection.resetAllSelectFlag(false);
		return aCollection;
	}
	
	protected void setSelectedToForms(List<FormDescriptor> formHeaders) {
		if (formHeaders == null)
			return;
		
		for (FormDescriptor form : formHeaders)
			form.setSelected(true);
	}
	
	/**
	 * 1st part of the content validation
	 * @param formDescriptors
	 */
	protected void determineLoadType(List<FormDescriptor> formDescriptors) {
		
		List<String> pidList = new ArrayList<String>();
		
		for (FormDescriptor form : formDescriptors) {
			if (!form.isSelected()) 
				continue;
			
			if (form.getLoadStatus() < FormDescriptor.STATUS_XML_VALIDATED) {
				form.setSelected(false); //turn this off so we won't do other val check.
				continue;
			}
			
			String publicid = form.getPublicId();
			if (publicid != null && publicid.length() > 0)
				pidList.add(publicid); 	
			else {
				form.setLoadType(FormDescriptor.LOAD_TYPE_NEW);
			}
		}
		
		if (pidList.size() > 0) {
			List<FormV2> formDtos = repository.getFormsForPublicIDs(pidList);
			HashMap<String, List<Float>> publicIdVersions = createFormExistingVersionMap(formDtos);
			determineLoadTypeByVersion(formDescriptors, publicIdVersions);

			//extra info we want from the dtos, eg. seq id for update form and new version
			transferFormSeqids(formDtos, formDescriptors);
		}
		
	}
	
	/**
	 * Validate form's context name, workflow status, form type and Form Loader loggedin user's load right in 
	 * the form's specified context. Any discrepancy will be entered as a message in the form message list
	 *  
	 * @param formDescriptors
	 */
	protected void validateFormInfo(List<FormDescriptor> formDescriptors, String loggedinUser) {
		
		int idx = 0;
		for (FormDescriptor form : formDescriptors) {		
			if (!form.isSelected()) 
				continue;
			
			if (!validContextName(form)) {
				form.setLoadStatus(FormDescriptor.STATUS_CONTENT_VALIDATION_FAILED);
				form.setSelected(false);
				return;
			}
				
			this.repository.checkWorkflowStatusName(form);
			
			//This should be done with list from database
			//But we couldn't find the table that contains the list
			String formType = form.getType();
			if (formType == null || formType.length() == 0) {
				form.addMessage("Form type in xml is empty. Use default type CRF");
				form.setType("CRF");
			} else {
				if (!formType.equals("CRF") && !formType.equals("TEMPLATE")) {
					form.addMessage("Form type \"" + formType + "\" is invalid. Use default type CRF");
					form.setType("CRF");
				}
			}
			
			if (!this.repository.hasLoadFormRight(form, loggedinUser, form.getContext())) {
				form.addMessage("Form Loader logged in user [" + loggedinUser + "] doesn't have load form right in context [" + 
						form.getContext() + "]. Validation failed");
				form.setLoadStatus(FormDescriptor.STATUS_CONTENT_VALIDATION_FAILED);
				form.setSelected(false);
			} else {
				String changeNote = "Created/Updated using Form Loader by [" + loggedinUser + 
						"], XML document contained createdBy [" + form.getCreatedBy() + "] and modifiedBy [" + form.getModifiedBy() + "]";
				form.setCreatedBy(loggedinUser);
				form.setChangeNote(changeNote);
			}
		}
	}
	
	/**
	 * If context name is null or empty, use default "NCIP". If context name is present but not matching any valid value
	 * from database, add a message to the form and reject loading the form.
	 * 
	 * @param form from xml
	 * @return true if context name in the form is valid or empty, thus, reset to default "NCIP"; false if context name invalid
	 */
	protected boolean validContextName(FormDescriptor form) {
		
		String contextName = form.getContext();
		if (contextName == null || contextName.length() == 0) {
			form.addMessage("Context name in form is null or empty. Use \"NCIP\" context to load form");
			form.setContext("NCIP");
		}
		
		String contextSeqid = this.repository.getContextSeqIdByName(form.getContext());
		if (contextSeqid == null || contextSeqid.length() == 0) {
			form.addMessage("Context name in form [" + form.getContext() + "] is not valid. Unable to load form");
			return false;
		} 
		
		form.setContextSeqid(contextSeqid);
		return true;
	}
	
	/**
	 * Assumption is that the list of dtos is ordered by public id and version
	 * @param formDtos
	 * @return
	 */
	protected HashMap<String, List<Float>> createFormExistingVersionMap(List<FormV2> formDtos) {
		HashMap<String, List<Float>> map = new HashMap<String, List<Float>>();
		for (FormV2 formdto : formDtos) {
			String pubId = String.valueOf(formdto.getPublicId());
			List<Float> vers = (!map.containsKey(pubId)) ? new ArrayList<Float>() : map.get(pubId);
			
			vers.add(Float.valueOf(formdto.getVersion()));		
			map.put(pubId, vers);
		}
		
		return map;
	}
	
	/**
	 * 
	 * @param formHeaders forms parsed from xml
	 * @param existingVersions hashmap with form public id as key and a list of existing versions from db as value
	 */
	protected void determineLoadTypeByVersion(List<FormDescriptor> formHeaders,
			HashMap<String, List<Float>> existingVersions) {

		//For duplicate check
		//Seems the only real duplicates are those with the same public id + version and has a match in db
		HashMap<String, String> processed = new HashMap<String, String>();
		
		for (FormDescriptor form : formHeaders) {
			if (!form.isSelected())
				continue;
			
			String publicid = form.getPublicId();
			String version = form.getVersion();
			
			if (publicid == null || publicid.length() == 0) {
				form.setLoadType(FormDescriptor.LOAD_TYPE_NEW);
			} else if (!existingVersions.containsKey(publicid)) {// invalid public id from xml
				form.setLoadType(FormDescriptor.LOAD_TYPE_UNKNOWN);
				form.setSelected(false);
				form.addMessage(
					"Form public id from xml doesn't have a match in database. Please correct or it'll be skipped loading");
				form.setLoadStatus(FormDescriptor.STATUS_CONTENT_VALIDATION_FAILED);
			} else 
				determineLoadTypeForForm(form, existingVersions.get(publicid));
	
			checkDuplicate(processed, publicid, version, form);
		}
		
	}
	
	/**
	 * Determine load type. We get here only if public id in xml has a match in db
	 * @param form
	 * @param existingVersions assumption is that existing versions are ordered from least to greatest
	 */
	protected void determineLoadTypeForForm(FormDescriptor form, List<Float> existingVersions) {
		
		String version = form.getVersion();
		if (version == null || version.length() == 0) {
			form.setLoadType(FormDescriptor.LOAD_TYPE_UNKNOWN);
			form.setSelected(false);
			form.addMessage("Form public id from xml has a match in database but version string is null. Please correct or it'll be skipped loading");
		}
		else {
			float versNum = Float.valueOf(version).floatValue();
			float highestVers = existingVersions.get(existingVersions.size()-1);
			if (versNum > highestVers) {
				form.setLoadType(FormDescriptor.LOAD_TYPE_NEW_VERSION);
			} else {
				boolean matched = false;
				for (Float existing : existingVersions) {
					if (versNum == existing.floatValue()) {
						form.setLoadType(FormDescriptor.LOAD_TYPE_UPDATE_FORM);
						if (versNum < highestVers) 
							form.addMessage("A newer version of this form exists in dabatase. Updating an older version of the form.");
						matched = true;
						break;
					}
				}
				
				if (!matched) {
					form.setLoadType(FormDescriptor.LOAD_TYPE_NEW_VERSION);
					form.getMessages().add("Form version in xml doesn't match any existing version in database and is less than the highest version in database. Will load as new version");
				}
			}
		}
	}
	
	/**
	 * Transfer extra info from form dtos to the form descriptor list for "update" forms or new version forms, for later use
	 * @param formDtos list of dtos from a query by public id query
	 * @param forms forms parsed from xml
	 */
	protected void transferFormSeqids(List<FormV2>formDtos, List<FormDescriptor> forms) {
		for (FormDescriptor formDesc : forms) {
			if (formDesc.getLoadType().equals(FormDescriptor.LOAD_TYPE_NEW)) 
				continue;
			
			FormV2 match = findMatchingFormDto(formDtos, formDesc);
			if (match != null)
				formDesc.setFormSeqId(match.getIdseq());
		}
	}
	
	/**
	 * Find a matching form dto based on a xml form (form parsed from xml)'s public id or/and version,
	 * depending on whether it's a new version form or an update form
	 * 
	 * @param formDtos form dtos from a query by public ids
	 * @param form a form from xml
	 * @return a matching dto or null
	 */
	protected FormV2 findMatchingFormDto(List<FormV2> formDtos, FormDescriptor form) {
		
		for (FormV2 formdto : formDtos) {
			try {
			int publicId = Integer.parseInt(form.getPublicId());
			float version = Float.parseFloat(form.getVersion());
			
			if (formdto.getPublicId() == publicId) {
				if (form.getLoadType().equals(FormDescriptor.LOAD_TYPE_NEW_VERSION))
					return formdto;
				else if (form.getLoadType().equals(FormDescriptor.LOAD_TYPE_UPDATE_FORM)
						&& formdto.getVersion().floatValue() == version) 
					return formdto;
			} 
			
		} catch (NumberFormatException ne) {
				logger.debug("Error while converting string to number: >" + form.getPublicId() + "< and >" + form.getVersion() + "<");
				continue;
		}
			
		}
		
		return null;
	}
	
	/**
	 * Check if a form having the same valid (has match in db) public id and version has been 
	 * processed. If so, add a message to inform user
	 * @param processed
	 * @param currPublicid
	 * @param currVersion
	 * @param currForm
	 */
	protected void checkDuplicate(HashMap<String, String> processed, 
			String currPublicid, String currVersion, FormDescriptor currForm) {
		if (processed == null || processed.size() == 0)
			return;
		
		if (currForm.getLoadType().equals(FormDescriptor.LOAD_TYPE_UPDATE_FORM)) {
			if (processed.containsKey(currPublicid) ) {
				if (processed.get(currPublicid).contains(currVersion)) 
					currForm.addMessage("Duplicate form found - there is already a form in xml that has the same " +
							"public id and version. This form will overwrite the earlier one.");
				else
					processed.put(currPublicid, processed.get(currPublicid) + "," + currVersion);
			} else 
				processed.put(currPublicid, currVersion);
		}
		
	}

	/**
	 * 2nd part of the content validation
	 * @param xmlPathName
	 * @param formHeaders
	 */
	protected void validateQuestions(String xmlPathName, List<FormDescriptor> formDescriptors) {
		
		List<FormDescriptor> forms = getFormQuestionsFromXml(xmlPathName, formDescriptors);
		
		if (forms == null) {
			logger.error("form list is null. This should not happen");
			return;
		}
			
		//First pass, collect question public ids and their cde ids so we could get necessary data from 
		//database as a list (= less queries)
		for (FormDescriptor form : formDescriptors) {
			if (!form.isSelected()) 
				continue;
			
			String formLoadType = form.getLoadType();
			if (!formLoadType.equals(FormDescriptor.LOAD_TYPE_NEW_VERSION)
					&& !formLoadType.equals(FormDescriptor.LOAD_TYPE_NEW) 
					&& !formLoadType.equals(FormDescriptor.LOAD_TYPE_UPDATE_FORM)) {
				//TODO: We should not get here. Check code if we see this message.
				form.addMessage("The form has undetermined load type. Unable to validate its questions.");
				form.setSelected(false);
				continue;
			}
			
			logger.debug("Start validating questions for form [" + form.getPublicId() + "|" + form.getVersion() + "|" + form.getFormSeqId() + "]");
			
			List<String> questPublicIds = new ArrayList<String>();
			List<String> questCdePublicIds = new ArrayList<String>();
			List<ModuleDescriptor> modules = form.getModules();
			collectPublicIdsForModules(modules, questPublicIds, questCdePublicIds, formLoadType);
			
			List<QuestionTransferObject> questDtos = repository.getQuestionsByPublicIds(questPublicIds);
			List<DataElementTransferObject> cdeDtos = repository.getCDEsByPublicIds(questCdePublicIds);
			
			HashMap<String, List<ReferenceDocumentTransferObject>> refdocDtos = 
					repository.getReferenceDocsByCdePublicIds(questCdePublicIds);
			List<String> vdSeqIds = new ArrayList<String>();
			for (DataElementTransferObject de: cdeDtos) {
				String vdseqId = de.getVdIdseq();
				if (vdseqId != null && vdseqId.length() > 0)
					vdSeqIds.add(vdseqId);
			}
			
			HashMap<String, List<PermissibleValueV2TransferObject>> pvDtos = 
					repository.getPermissibleValuesByVdIds(vdSeqIds);
			
			validateQuestionsInModules(modules, form, questDtos, cdeDtos, refdocDtos, pvDtos);			
			
			form.setLoadStatus(FormDescriptor.STATUS_CONTENT_VALIDATED);
			
			logger.debug("Done validating questions for form [" + form.getPublicId() + "|" + form.getVersion()  + "]");
		}
		
	}
	
	protected void validateQuestionsInModules(List<ModuleDescriptor> modules, FormDescriptor form, 
			List<QuestionTransferObject> questDtos, List<DataElementTransferObject> cdeDtos, 
			HashMap<String, List<ReferenceDocumentTransferObject>> refdocDtos, HashMap<String, List<PermissibleValueV2TransferObject>> pvDtos) {
		if (modules == null) {
			logger.debug("Module list is null. Unable to validate questions.");
			return;
		}
		
		for (ModuleDescriptor module : modules) {
			logger.debug("Start validating questions for module [" + module.getPublicId() + "|" + module.getVersion() + "|" + module.getModuleSeqId()  + "]");
			
			List<QuestionDescriptor> questions = module.getQuestions();		
			for (QuestionDescriptor question : questions) {
				
				logger.debug("Start validating question [" + question.getPublicId() + "|" + question.getVersion() + "|" + question.getQuestionSeqId()  + "]");
				validateQuestion(question, form, questDtos, cdeDtos, refdocDtos, pvDtos);
			}
			
			logger.debug("Done validating questions for module [" + module.getPublicId() + "|" + module.getVersion() + "|" + module.getModuleSeqId()  + "]");
		}
	}
	
	/**
	 * Collect public ids for questions and their cde from all modules of a form, so that
	 * we could query database with a list.
	 * @param modules
	 * @param questPublicIds
	 * @param questCdePublicIds
	 * @param formLoadType
	 */
	protected void collectPublicIdsForModules(List<ModuleDescriptor> modules, 
			List<String> questPublicIds, List<String> questCdePublicIds, String formLoadType) {
		
		if (modules == null) {
			logger.debug("Module list is null. Unable to collect public ids.");
			return;
		}
			
		for (ModuleDescriptor module : modules) {
			List<QuestionDescriptor> questions = module.getQuestions();
			
			for (QuestionDescriptor question : questions) {
				String questPubId = question.getPublicId();
				//Only need to validate question public id + version if it's an update form
				if (formLoadType.equals(FormDescriptor.LOAD_TYPE_UPDATE_FORM)
						&& questPubId != null && questPubId.length() > 0)
					questPublicIds.add(questPubId);
				
				String cdePublicId = question.getCdePublicId();
				if (cdePublicId != null && cdePublicId.length() > 0)
					questCdePublicIds.add(cdePublicId); 
				else
					question.addMessage("Question has not associated data element public id. Unable to validate");				
			}
			
			logger.debug("Collected " + questPublicIds.size() + " question public ids and " + questCdePublicIds.size() +
					" cde public ids in module [" + module.getPublicId() + "|" + module.getVersion() + "]");
		}
		
	}
	
	protected List<FormDescriptor> getFormQuestionsFromXml(String xmlPathName, List<FormDescriptor> formHeaders) {
		StaXParser parser = new StaXParser();
		List<FormDescriptor> forms = parser.parseFormQuestions(xmlPathName, formHeaders);
		
		return forms;
	}
	
	/**
	 * @deprecated
	 * @param question
	 * @param form
	 */
	protected void validateQuestion(QuestionDescriptor question, FormDescriptor form) {
		String formLoadType = form.getLoadType();
		String publicId = question.getPublicId();
		String version = question.getVersion();
		
		if (!formLoadType.equals(FormDescriptor.LOAD_TYPE_NEW_VERSION)
				&& !formLoadType.equals(FormDescriptor.LOAD_TYPE_NEW) 
				&& !formLoadType.equals(FormDescriptor.LOAD_TYPE_UPDATE_FORM)) {
			String msg = "Error: the form containing the question has undetermined load type";
			question.addMessage(msg);
			logger.error("Error for Question [" + publicId + "|" + version + "] " + msg);
			return;
		}
		
		verifyQuestionPublicIdVersion(question, form);
		
		//For all cases: verify cde if present in xml
		verifyCDEInQuestion(question, form);
	}
	
	protected void validateQuestion(QuestionDescriptor question, FormDescriptor form, 
			List<QuestionTransferObject> questDtos, List<DataElementTransferObject> cdeDtos,
			HashMap<String, List<ReferenceDocumentTransferObject>> refdocDtos, 
			HashMap<String, List<PermissibleValueV2TransferObject>> pvDtos) {
		
		//Verify question public id and version for Update form only
		verifyQuestionPublicIdVersion(question, form, questDtos);
		
		//For all cases: verify cde if present in xml
		verifyQuestionAgainstCde(question, form, cdeDtos, refdocDtos, pvDtos);
	}
	
	
	
	/**
	 * Concerns only questions in an "update" form.
	 * 1) use same public id + version if they have a match in db
	 * 2) If public id + version are present in xml but have no match in db, skip loading and add error message 
	 * 3. If public id + version are null, generate new ones at load time;
	 * 
	 * For new form or new version of a form, question's public id + version will be generated at load time
	 * by default
	 * 	
	 * @param question
	 * @param form 
	 */
	protected void verifyQuestionPublicIdVersion(QuestionDescriptor question, FormDescriptor form,
			List<QuestionTransferObject> questDtos) {
		
		String publicId = question.getPublicId();
		String version = question.getVersion();
		String loadType = form.getLoadType();
		
		if (loadType.equals(FormDescriptor.LOAD_TYPE_UPDATE_FORM)) {
			if (questDtos != null || publicId != null && publicId.length() > 0 && 
					version != null && version.length() > 0) {
				
				int match = 0;
				for (QuestionTransferObject qDto : questDtos) {
					if (qDto.getPublicId() == Integer.parseInt(publicId)) {
						match = 1;
						if (qDto.getVersion().floatValue() == Float.parseFloat(version)) {
							match = 2;
						}
					}
				}
				//check db
				
				if (match < 2) {
					question.setSkip(true);
					if (match == 0)
						form.addMessage("Question [" + publicId + "|" + version + "] will not be loaded because public id has no match in db");
					else if (match == 1)
						form.addMessage("Question [" + publicId + "|" + version + "] will not be loaded because version has no match in db");

				}
			} else {
				//question's public id and version will be generated at load time
				//question.setPublicId("");
				//question.setVersion("");
			}
		}
		
	}
	
	/**
	 * @deprecated
	 * @param question
	 * @param form
	 */
	protected void verifyQuestionPublicIdVersion(QuestionDescriptor question, FormDescriptor form) {
		
		String publicId = question.getPublicId();
		String version = question.getVersion();
		String loadType = form.getLoadType();
		
		if (loadType.equals(FormDescriptor.LOAD_TYPE_UPDATE_FORM)) {
			if (publicId != null && publicId.length() > 0 && 
					version != null && version.length() > 0) {
				//check db
				int match = checkMatchInDatabase(question);
				if (match > 0) {
					question.setSkip(true);
					if (match == 1)
						form.addMessage("Question [" + publicId + "|" + version + "] will not be loaded because public id has no match in db");
					else if (match == 2)
						form.addMessage("Question [" + publicId + "|" + version + "] will not be loaded because version has no match in db");

				}
			} else {
				//question's public id and version will be generated at load time
				question.setPublicId("");
				question.setVersion("");
			}
		}
		
	}
	
	protected int checkMatchInDatabase(QuestionDescriptor question) {
		String publicId = question.getPublicId();
		String version = question.getVersion();
	
		List<QuestionTransferObject> qDtos = repository.getQuestionsByPublicId(publicId);
		
		//TODO: need contants for returns
		if (qDtos == null || qDtos.size() == 0) 
			return 1; //public id no match
			
		for (QuestionTransferObject qDto : qDtos) {
			if (qDto.getVersion().floatValue() == Float.parseFloat(version))
				return 0; //match found
		}
		
		//if we get here, no match found in version
		return 2;
	}
	
	/**
	 * Get data element obj from database, with cde public and version associated with a question in xml. Verify the question's
	 * data field against the data element obj.
	 * 
	 * @deprecated
	 * @param question
	 * @param form
	 */
	protected void verifyCDEInQuestion(QuestionDescriptor question, FormDescriptor form) {
		String cdePublicId = question.getCdePublicId();
		String cdeVersion = question.getCdeVersion();
		
		String errField = verifyPublicIdAndVersion(cdePublicId, cdeVersion); 
		if (errField.length() > 0) {
			question.addInstruction("Question in xml doesn't contain valid CDE " + errField + ". Unable to validate question in xml");
			return;
		}
		
		DataElementTransferObject matchingCde = getDataElementFromDB(cdePublicId, cdeVersion, question);
		if (matchingCde == null) {
			logger.debug("Unable to load a Date Element with [" + cdePublicId + "|" + cdeVersion + "] from database. Unable to validate question content.");
			return; 
		}
		
		//DEBUG
				if (question.getPublicId().equals("3193457")) {
					int i = 10;
					i++;
				}
		List<PermissibleValueV2TransferObject> pValues = getPermissibleValueListFromDB(matchingCde, question);	
		List<ReferenceDocumentTransferObject> refDocs = getReferenceDocsForCdeFromDB(cdePublicId, cdeVersion, question);
		matchingCde.setReferenceDocs(refDocs); //we probably don't need this
		
		verifyQuestionDefaultValue(question, pValues, matchingCde);
		verifyQuestionValidValues(question, pValues, matchingCde);
		verifyQuestionText(question, refDocs, matchingCde);		
			
		logger.debug("Done validating question against CDE");
	}
	
	/**
	 * 
	 * @param question
	 * @param form
	 * @param cdeDtos
	 * @param refdocDtos
	 * @param pvDtos
	 */
	protected void verifyQuestionAgainstCde(QuestionDescriptor question, FormDescriptor form,
			List<DataElementTransferObject> cdeDtos,
			HashMap<String, List<ReferenceDocumentTransferObject>> refdocDtos, 
			HashMap<String, List<PermissibleValueV2TransferObject>> pvDtos) {
		
		String cdePublicId = question.getCdePublicId();
		String cdeVersion = question.getCdeVersion();
		
		String errField = verifyPublicIdAndVersion(cdePublicId, cdeVersion); 
		if (errField.length() > 0) {
			//TODO: Denise to check with Diana to see where we should add messages.
			question.addInstruction("Question in xml doesn't contain valid CDE " + errField + ". Unable to validate question in xml");
			question.addMessage("Question in xml doesn't contain valid CDE " + errField + ". Unable to validate question in xml. Skip loading");
			
			return;
		}
		
		DataElementTransferObject matchingCde = getMatchingDataElement(cdePublicId, cdeVersion, question, cdeDtos);
		if (matchingCde == null) {
			String msg = "Unable to load a Date Element with [" + cdePublicId + "|" + cdeVersion + "] from database. Unable to validate question content.";
			logger.debug(msg);
			question.addMessage(msg);
			question.addInstruction(msg);
			return; 
		}
		
		List<PermissibleValueV2TransferObject> pValDtos = pvDtos.get(matchingCde.getVdIdseq());	
		List<ReferenceDocumentTransferObject> rdDtos = refdocDtos.get(
				"" + matchingCde.getPublicId() + "-" + matchingCde.getVersion());
		
		verifyQuestionDefaultValue(question, pValDtos, matchingCde);
		verifyQuestionValidValues(question, pValDtos, matchingCde);
		verifyQuestionText(question, rdDtos, matchingCde);		
			
		logger.debug("Done validating question against CDE");
	}
	
	protected String verifyPublicIdAndVersion(String publicid, String version) {
		String errField = "";
		if (publicid == null || publicid.length() == 0 ) {
			errField = "public id";
		}
		
		if (version == null || version.length() == 0) {
			errField += (errField.length() > 0) ? " and version" : "version";
		}
		
		return errField;
	}
	
	/**
	 * Get the data element obj from database, that have the same public id and version as those associated with a question in xml
	 * @param cdePublicId
	 * @param cdeVersion
	 * @param question
	 * 
	 * @deprecated
	 * @return
	 */
	protected DataElementTransferObject getDataElementFromDB(String cdePublicId, String cdeVersion, QuestionDescriptor question) {
		
		List<DataElementTransferObject> cdes = repository.getCDEByPublicId(cdePublicId);
		if (cdes == null || cdes.size() ==0) {
			question.addInstruction(
					"Question in xml contains CDE public id but it has no match in database. Unable to validate question.");
			return null;
		} else {
			for (DataElementTransferObject cde : cdes) {
				if (Float.parseFloat(cdeVersion) == cde.getVersion().floatValue()) {
					return cde;
				}
			}

			question.addInstruction(
					"Question in xml contains CDE public id and verion but the version has no match in database. Unable to validate question.");
			return null;
			
		}
	}
	
	protected DataElementTransferObject getMatchingDataElement(String cdePublicId, String cdeVersion, 
		QuestionDescriptor question, List<DataElementTransferObject> cdeDtos) {
		
		if (cdeDtos == null || cdeDtos.size() ==0) {
			question.addInstruction(
					"Question in xml contains CDE public id but it has no match in database. Unable to validate question.");
			return null;
		} 
		
		int cdePubIdNum = Integer.parseInt(cdePublicId);
		float cdeVerNum = Float.parseFloat(cdeVersion);
		boolean pidMatched = false;
		for (DataElementTransferObject cde : cdeDtos) {
			if (cde.getPublicId() == cdePubIdNum)
				pidMatched = true;
			else
				continue;

			if (cdeVerNum == cde.getVersion().floatValue()) {
				question.setCdeSeqId(cde.getDeIdseq());
				return cde;
			}
		}

		if (pidMatched)
			question.addInstruction(
				"Question in xml contains CDE public id and verion but the version has no match in database. Unable to validate question.");
		else
			question.addInstruction(
				"Question in xml contains CDE public id and verion but the public id has no match in database. Unable to validate question.");
		return null;
			
		
	}
	
	protected List<PermissibleValueV2TransferObject> getPermissibleValueListFromDB(
			DataElementTransferObject cde, QuestionDescriptor question) {
		
		List<PermissibleValueV2TransferObject> pValues = null;
		String vdSeqId = cde.getVdIdseq();
        
		if (vdSeqId != null && vdSeqId.length() > 0) {
			pValues = repository.getValueDomainPermissibleValuesByVdId(vdSeqId);

			if (pValues == null || pValues.size() == 0) {
				logger.debug("The value domain associated with data Element [" + 
						cde.getLongName() + 
						"] does not have permissible values. Will not be able to verify question's default value and valid values");
			}
		}
		 
		return pValues;
	}
	
	protected List<ReferenceDocumentTransferObject> getReferenceDocsForCdeFromDB(String cdePublicId, String cdeVersion,
			QuestionDescriptor question) {
		
		List<ReferenceDocumentTransferObject> refDocs = repository.getReferenceDocsForQuestionCde(cdePublicId, cdeVersion);
		 
		if (refDocs == null || refDocs.size() == 0) {
			logger.debug("Unable to load any reference document from db with CDE public id and version [" + cdePublicId +
					"|" + cdeVersion + "] in xml");
		}
		
		return refDocs;
		
	}
	
	/**
	 * Verify the question's default value against CDE's permissible values / value
	 * 
	 * If the default value doesn't match any of the CDE's permissible values , mark the question for loading, 
	 * set its default value to null and add a warning message in the question's instruction field 
	 * ("Default value <value> is invalid").
	 * 
	 * @param question
	 * @param pValues
	 */
	protected void verifyQuestionDefaultValue(QuestionDescriptor question, List<PermissibleValueV2TransferObject> pValues,
			DataElementTransferObject matchingCde) {
		String msg;
		if (pValues == null || pValues.size() == 0) {
			msg = "The value domain associated with data Element [" + 
					matchingCde.getLongName() + 
					"] does not have permissible values. Unable to verify question's default value";
			question.addInstruction(msg);
			question.addMessage(msg);
			return;
		} 	 
		
		String defaultValue = question.getDefaultValue();
		boolean validated = false;
		if (defaultValue != null && defaultValue.length() > 0) {
			logger.debug("Verifying quesiton's default value [" + defaultValue + "]");
			for (PermissibleValueV2TransferObject pVal : pValues) {
				if (defaultValue.equalsIgnoreCase(pVal.getValue()))
					validated = true;
			}

			if (!validated) {
				question.addInstruction("Question's default value [" + defaultValue + "] doesn't match any of the associated CDE's permissible values");
				question.setDefaultValue("");
			}
		} else {
			question.addInstruction("Question's default value is null");
		}
	}
	
	/**
	 * Verify each of the question's valid value and its value meaning against CDE's permissible values / value
	 * a. If one or more valid values in xml don't match the CDE's permissible values, list the non-matching 
	 * values in the question's instruction field.
	 * b. Create valid value and its value meaning for the question that matches CDE permissible values.
	 * 
	 * @param question
	 * @param pValues
	 * @param matchingCde
	 */
	protected void verifyQuestionValidValues(QuestionDescriptor question, List<PermissibleValueV2TransferObject> pValues,
			DataElementTransferObject matchingCde) {
		
		String msg;
		
		List<QuestionDescriptor.ValidValue> validValues = question.getValidValues();
		if (validValues == null || validValues.size() == 0)
			 return; 
		 
		if (pValues == null || pValues.size() == 0) {
			msg = "The value domain associated with data Element [" + 
					matchingCde.getLongName() + 
					"] does not have permissible values. Unable to verify question's valid values";
			question.addInstruction(msg);
			question.addMessage(msg);
			return;
		} 	
		
		for (QuestionDescriptor.ValidValue vVal : validValues) {
			 boolean valValidated = false;
			 boolean meaningValidated = false;
			 boolean meaningDescValidated = false;
			 String val = vVal.getValue();
			 
			 String valMeaning = FormLoaderHelper.normalizeSpace(vVal.getMeaningText());
			 String valDesc = FormLoaderHelper.normalizeSpace(vVal.getDescription());
			 
			 for (PermissibleValueV2TransferObject pVal : pValues) {
				 String pValStr = pVal.getValue().trim();
				 if (val.equals(pValStr)) {
					 valValidated = true;
					 ValueMeaningV2TransferObject valMeaningDto = (ValueMeaningV2TransferObject)pVal.getValueMeaningV2();
					 
					 String valMeaningLongName = FormLoaderHelper.normalizeSpace(valMeaningDto.getLongName());
					 String valMeaningDescription = FormLoaderHelper.normalizeSpace(valMeaningDto.getDescription());
					 
					 if (valMeaning.equals(valMeaningLongName)) {
						 meaningValidated = true; 
						 if (valMeaningDescription.equalsIgnoreCase(valDesc)) {
							 meaningDescValidated = true;
							 vVal.setVdPermissibleValueSeqid(pVal.getIdseq());
						 
							 //This will be used as the first part of the valid value preferred name
							 vVal.setPreferredName(String.valueOf(valMeaningDto.getPublicId()));
						 }
					 }
				 }
			 }
			 
			 msg = "";
			 if (!valValidated)
				 msg = "Valid value [" + val + "] doesn't match any of the associated CDE's permissible values. Skip loading";
			 else if (!meaningValidated)
				 msg = "Valid value meaning text [" + valMeaning + "] doesn't match any of the associated CDE's permissible value meaning. Skip loading";
			 else if (!meaningDescValidated)
				 msg = "Valid value description [" + vVal.getDescription() + 
				 "] doesn't match any of the associated CDE's permissible value's value meaning description. Skip loading";
			 
			 if (msg.length() > 0) {
				 vVal.setSkip(true);
				 question.addInstruction(msg);
				 question.addMessage(msg);
			 }
		 }
		 
	}
	
	/**
	 * Verify the question's question text against CDE reference document.
	 * a. If the question's question text in the xml is not null but does not match 
	 * the doctext field value of preferred or alternate type of the CDE's reference document, 
	 * mark it for loading and set as default text "Question text invalid".
	 * b. If the question's question text in xml is null while the CDE is valid, use CDE 
	 * reference document preferred text as its question text.
	 * c. If a question's question text in xml is null and the CDE is valid but doesn't have 
	 * a reference document preferred text, set "Data Element <long name> does not have Preferred Question Text" 
	 * as default question text for the question.
	 * 
	 * @param question
	 * @param refDocs
	 * @param matchingCde
	 */
	protected void verifyQuestionText(QuestionDescriptor question, List<ReferenceDocumentTransferObject> refDocs, 
			DataElementTransferObject matchingCde) {
		
		if (refDocs == null || refDocs.size() == 0) {
			question.addInstruction("Unable to load any reference document with CDE public id and version [" + 
				matchingCde.getPublicId() +
				"|" + matchingCde.getVersion() + "] in xml. Unable to verify question text.");
			return;
		}
		String questionText = question.getQuestionText();
		String cdePublicId = question.getCdePublicId();
		String cdeVersion = question.getCdeVersion();
		
		//refdoc list is ordered by type, preferred first
		if (questionText == null || questionText.length() == 0) {
			
			for (ReferenceDocument refdoc : refDocs) {				
				if ("Preferred Question Text".equalsIgnoreCase( refdoc.getDocType())) {
					questionText = refdoc.getDocText();
					if (questionText == null || questionText.length() == 0) {
						questionText = "Data Element [" + matchingCde.getLongName() + "] does not have Preferred Question Text";
					}
					break;
				}
			}
		} else {
			boolean matched = false;
			String preferredText = null; 
			for (ReferenceDocument refdoc : refDocs) {
				String docText = refdoc.getDocText();
				if ("Preferred Question Text".equalsIgnoreCase( refdoc.getDocType()))
					preferredText = docText;
				
				if (questionText.equalsIgnoreCase(docText)) {
					matched = true;
					break;
				}
			}
			
			if (!matched) {
				if (preferredText != null && preferredText.length() > 0)
					questionText = preferredText;
				else {
					question.addInstruction("Question text in xml [" + questionText + "] is invalid");
					questionText = "Data Element [" + matchingCde.getLongName() + "] does not have Preferred Question Text";
					
				}
			}
		}
		question.setQuestionText(questionText);
	}
	
	//default: CRF
	protected void validateFormType() {
		
	}
	
	//default: Form Loader (create new in db)
	protected void validateDesignationTypes() {
		
	}
	
	//default: ??
	protected void validateRefdocTypes() {
		
	}
	
	//default: Form Loader (create new in db)
	protected void validateDefinitionTypes() {
		
	}
	

}
