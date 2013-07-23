package gov.nih.nci.cadsr.formloader.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import gov.nih.nci.cadsr.formloader.domain.FormCollection;
import gov.nih.nci.cadsr.formloader.domain.FormDescriptor;
import gov.nih.nci.cadsr.formloader.domain.FormStatus;
import gov.nih.nci.cadsr.formloader.domain.ModuleDescriptor;
import gov.nih.nci.cadsr.formloader.domain.QuestionDescriptor;
import gov.nih.nci.cadsr.formloader.repository.FormLoaderRepository;
import gov.nih.nci.cadsr.formloader.service.ContentValidationService;
import gov.nih.nci.cadsr.formloader.service.common.FormLoaderServiceException;
import gov.nih.nci.cadsr.formloader.service.common.StaXParser;
import gov.nih.nci.ncicb.cadsr.common.dto.ModuleTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.QuestionTransferObject;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.FormDAO;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc.JDBCFormDAO;
import gov.nih.nci.ncicb.cadsr.common.resource.FormV2;
import gov.nih.nci.ncicb.cadsr.common.resource.Module;
import gov.nih.nci.ncicb.cadsr.common.resource.ReferenceDocument;
import gov.nih.nci.ncicb.cadsr.common.servicelocator.ServiceLocator;

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
	public FormCollection validateXmlContent(FormCollection aCollection, String xmlPathName) 
			throws FormLoaderServiceException {
		
		List<FormDescriptor> formHeaders = aCollection.getForms();
		if (formHeaders == null || formHeaders.size() == 0) {
			logger.error("Input form list is null or empty. Nothing to do");
			return null;
		}
		
		determineLoadType(formHeaders);
		validateQuestions(xmlPathName, formHeaders);
		
		return aCollection;
	}
	
	/**
	 * 1st part of the content validation
	 * @param formDescriptors
	 */
	protected void determineLoadType(List<FormDescriptor> formDescriptors) {
		
		List<String> pidList = new ArrayList<String>();
		
		for (FormDescriptor form : formDescriptors) {
			if (!FormStatus.STATUS_XML_VALIDATED.equals(form.getStatus().getType()))
				continue;
			
			String publicid = form.getPublicId();
			if (publicid == null || publicid.length() == 0)
				form.setLoadType(FormDescriptor.LOAD_TYPE_NEW);
			else {
				pidList.add(publicid); //TODO: Could be duplicated ids
										//need handle: 2 forms with same public id but different versions
										//what if: 2 forms with same public id and empty version
				form.setLoadType(FormDescriptor.LOAD_TYPE_UNKNOWN);
			}
		}
		
		List<FormV2> formDtos = repository.getFormsForPublicIDs(pidList);
		HashMap<String, List<Float>> publicIdVersions = createFormExistingVersionMap(formDtos);
		determineLoadTypeByVersion(formDescriptors, publicIdVersions);
		
		//extra info we want from the dtos, eg. seq id for existing forms
		transferFormDtos(formDtos, formDescriptors);
		
	}
	
	/**
	 * Assumption is that the list of dtos is ordered by public id and version
	 * @param formDtos
	 * @return
	 */
	protected HashMap<String, List<Float>> createFormExistingVersionMap(List<FormV2> formDtos) {
		HashMap<String, List<Float>> map = new HashMap<String, List<Float>>();
		for (FormV2 form : formDtos) {
			List<Float> vers = (!map.containsKey(form.getPublicId())) ? new ArrayList<Float>() :
				map.get(form.getPublicId());
			
			vers.add(Float.valueOf(form.getVersion()));		
		}
		
		return map;
	}
	
	
	
	/**
	 * 
	 * @param formHeaders
	 * @param existingVersions comma delimited versions
	 */
	protected void determineLoadTypeByVersion(List<FormDescriptor> formHeaders,
			HashMap<String, List<Float>> existingVersions) {

		// for duplicate check
		HashMap<String, String> processed = new HashMap<String, String>();

		for (FormDescriptor form : formHeaders) {
			String publicid = form.getPublicId();
			String version = form.getVersion();
			
			if (publicid == null || publicid.length() == 0)
				form.setLoadType(FormDescriptor.LOAD_TYPE_NEW);
			else if (!existingVersions.containsKey(publicid)) {// invalid public id from xml
				form.setLoadType(FormDescriptor.LOAD_TYPE_NEW);
				form.getMessages().add(
						"Form public id from xml doesn't have a match in database. Will load as new form");
			}
			else 
				determineLoadTypeForForm(form, existingVersions.get(publicid));
	
			//TODO: requirement not clear yet. candidate for duplicate check
			processed.put(publicid, version);
		}
		
	}
	
	/**
	 * 
	 * @param form
	 * @param existingVersions assumption is that existing versions are ordered from least to greatest
	 */
	protected void determineLoadTypeForForm(FormDescriptor form, List<Float> existingVersions) {
		String version = form.getVersion();
		if (version == null || version.length() == 0)
			form.setLoadType(FormDescriptor.LOAD_TYPE_NEW_VERSION);
		else {
			float versNum = Float.valueOf(version).floatValue();
			if (versNum > existingVersions.get(existingVersions.size()-1)) {
				form.setLoadType(FormDescriptor.LOAD_TYPE_NEW_VERSION);
			} else {
				boolean matched = false;
				for (Float existing : existingVersions) {
					if (versNum == existing.floatValue()) {
						form.setLoadType(FormDescriptor.LOAD_TYPE_UPDATE_FORM);
						matched = true;
						break;
					}
				}
				
				if (!matched) {
					form.setLoadType(FormDescriptor.LOAD_TYPE_NEW_VERSION);
					form.getMessages().add("Form version in xml doesn't match an existing version in database and is less than the latest version in database. Will load as new version");
				}
			}
		}
	}
	
	/**
	 * Transfer extra info from form dtos to the form descriptor list, for later use
	 * @param formDtos
	 * @param formDescriptors
	 */
	protected void transferFormDtos(List<FormV2>formDtos, List<FormDescriptor> formDescriptors) {
		for (FormDescriptor formDesc : formDescriptors) {
			if (formDesc.getLoadType() != FormDescriptor.LOAD_TYPE_UPDATE_FORM) //for now we only care for this case
				continue;
			
			FormV2 match = findMatchingFormDto(formDtos, formDesc);
			if (match != null)
				formDesc.setFormSeqId(match.getIdseq());
		}
	}
	
	protected FormV2 findMatchingFormDto(List<FormV2> formDtos, FormDescriptor form) {
		
		for (FormV2 formdto : formDtos) {
			int publicId = Integer.parseInt(form.getPublicId());
			float version = Float.parseFloat(form.getVersion());
			if (formdto.getPublicId() == publicId && formdto.getVersion().floatValue() == version) 
				return formdto;
		}
		
		return null;
	}
	
	protected boolean beenProcessed(HashMap<String, String> processed, String currPublicid, String currVersion) {
		if (processed == null || processed.size() == 0)
			return false;
		
		return processed.containsKey(currPublicid);
	
		//TODO: need clarification for duplicate
		/*  
		if (!processed.containsKey(currPublicid))
			return false;
		else {
			
			String processedVers = processed.get(currPublicid);
			if ((processedVers == null || processedVers.length() == 0) && 
					(currVersion == null || currVersion.length() == 0))
				return true;
			else
		
			
		}
		*/
		
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
			
		for (FormDescriptor form : formDescriptors) {
			logger.debug("Start validating questions for form [" + form.getPublicId() + "|" + form.getVersion() + "|" + form.getFormSeqId() + "]");
			List<ModuleDescriptor> modules = form.getModules();
			for (ModuleDescriptor module : modules) {
				logger.debug("Start validating questions for module [" + module.getPublicId() + "|" + module.getVersion() + "|" + module.getModuleSeqId()  + "]");
				List<QuestionDescriptor> questions = module.getQuestions();
				for (QuestionDescriptor question : questions) {
					logger.debug("Start validating question [" + question.getPublicId() + "|" + question.getVersion() + "|" + question.getQuestionSeqId()  + "]");
					
					validateQuestion(question, form);
					
				}
				logger.debug("Done validating questions for module [" + module.getPublicId() + "|" + module.getVersion() + "|" + module.getModuleSeqId()  + "]");
			}
			
			logger.debug("Done validating questions for form [" + form.getPublicId() + "|" + form.getVersion()  + "]");
		}
		
	}
	
	protected List<FormDescriptor> getFormQuestionsFromXml(String xmlPathName, List<FormDescriptor> formHeaders) {
		StaXParser parser = new StaXParser();
		List<FormDescriptor> forms = parser.parseFormQuestions(xmlPathName, formHeaders);
		
		return forms;
	}
	
	protected void validateQuestion(QuestionDescriptor question, FormDescriptor form) {
		String formLoadType = form.getLoadType();
		String publicId = question.getPublicId();
		String version = question.getVersion();
		
		if (!formLoadType.equals(FormDescriptor.LOAD_TYPE_NEW_VERSION)
				|| formLoadType.equals(FormDescriptor.LOAD_TYPE_NEW) 
				|| formLoadType.equals(FormDescriptor.LOAD_TYPE_UPDATE_FORM)) {
			String msg = "Error: the form containing the question has undetermined load type";
			question.addInstruction(msg);
			logger.error("Error for Question [" + publicId + "|" + version + "] " + msg);
			return;
		}
		
		//TODO: clean up
		if (formLoadType.equals(FormDescriptor.LOAD_TYPE_NEW)) {
			//Generate question's public id and version\
			question.setPublicId("");
			question.setVersion("1.0");
		
		} else if (formLoadType.equals(FormDescriptor.LOAD_TYPE_NEW_VERSION)) {
			//if no match in db, mark for loading and add message (which field has a prob.)
			
			//if null, generate them.
			
			if (publicId == null || publicId.length() == 0)
				question.setVersion("1.0");
			else if (version == null || version.length() == 0)
				question.setVersion(""); //what does this do? Should treat as new version?
			else 
				verifyQuestionKeys(question, form);
			
		} else if (formLoadType.equals(FormDescriptor.LOAD_TYPE_UPDATE_FORM)) {
			//Verify the question's public id and version.
			//if null, generate them
			if (publicId == null || publicId.length() == 0)
				question.setVersion("1.0");
			else if (version == null || version.length() == 0)
				question.setVersion(""); //what does this do? Should treat as new version?
			else
				verifyQuestionKeys(question, form);
			//if no match in db, skip loading
			//add msg to inform user
			
		} 
		
		validateQuestionAgainstRefDocs(question, form);
	}
	
	protected void verifyQuestionKeys(QuestionDescriptor question, FormDescriptor form) {
	
		List<ModuleTransferObject> modules = repository.getModulesInForm(form.getFormSeqId());
		if (modules == null || modules.size() == 0) {
			logger.error("Repository returned null module list");
			form.getMessages().add("Form has no module defined. Unable to verify questions from xml");
			return;
		}
		
		for (ModuleTransferObject module : modules) {
			if (questionKeysExistInModule(question, module.getModuleIdseq())) {
				return;
			} else {
				if (form.getLoadType().equals(FormDescriptor.LOAD_TYPE_UPDATE_FORM))
					question.setSkip(true);
			}
		}
		
	}
	
	protected boolean questionKeysExistInModule(QuestionDescriptor question, String moduleSeqId) {
		int publicId = Integer.parseInt(question.getPublicId());
		float version = Float.parseFloat(question.getVersion());
		
		List<QuestionTransferObject> questiondtos = repository.getQuestionsInModule(moduleSeqId);
		
		boolean pidfound = false;
		for (QuestionTransferObject qDto : questiondtos) {
			if (qDto.getPublicId() != publicId)
				continue;
			
			if (qDto.getVersion() == version) {
				question.setQuestionSeqId(qDto.getQuesIdseq()); //don't know if it's useful now
				return true;
			}
			else
				pidfound = true;
			
		}
		if (!pidfound)
			question.addInstruction("Question's public id and version [" + publicId + "|" + version 
					+ "] from xml have no match in database");
		else
			question.addInstruction("Question's version have no match in database with this public id: " 
					+ publicId);
			
		return false;
	}
	
	protected void validateQuestionAgainstRefDocs(QuestionDescriptor question, FormDescriptor form) {
		String cdePublicId = question.getCdePublicId();
		String cdeVersion = question.getCdeVersion();
		if (cdePublicId == null || cdePublicId.length() == 0 
				|| cdeVersion == null || cdeVersion.length() == 0) {
			String msg = "Question [" + question.getPublicId() + "|" + question.getVersion() 
					+ "] doesn't have valid cde public id or version";
			logger.debug(msg);
			question.addInstruction(msg);
			return;
		}
		
		
		List<ReferenceDocument> refDocs = 
				repository.getReferenceDocsForQuestionCde(cdePublicId, cdeVersion);
		
		
	}
}
