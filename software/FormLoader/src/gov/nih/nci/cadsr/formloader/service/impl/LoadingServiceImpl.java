package gov.nih.nci.cadsr.formloader.service.impl;

import gov.nih.nci.cadsr.formloader.domain.FormCollection;
import gov.nih.nci.cadsr.formloader.domain.FormDescriptor;
import gov.nih.nci.cadsr.formloader.repository.FormLoaderRepository;
import gov.nih.nci.cadsr.formloader.service.LoadingService;
import gov.nih.nci.cadsr.formloader.service.common.FormLoaderServiceException;
import gov.nih.nci.cadsr.formloader.service.common.StaXParser;
import gov.nih.nci.ncicb.cadsr.common.dto.FormTransferObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

@Service
public class LoadingServiceImpl implements LoadingService {
	
	private static Logger logger = Logger.getLogger(LoadingServiceImpl.class.getName());
	
	FormLoaderRepository repository;
	
	public LoadingServiceImpl() {}
	
	public LoadingServiceImpl(FormLoaderRepository repository) {
		this.repository = repository;
	}

	public FormLoaderRepository getRepository() {
		return repository;
	}

	public void setRepository(FormLoaderRepository repository) {
		this.repository = repository;
	}

	@Override
	public FormCollection loadForms(FormCollection aCollection) 
		throws FormLoaderServiceException{

		if (aCollection == null)
			throw new FormLoaderServiceException(FormLoaderServiceException.ERROR_COLLECTION_NULL,
					"Input collection is null");
		
		String loggedinuser = aCollection.getCreatedBy();
		if (loggedinuser == null || loggedinuser.length() == 0) 
			throw new FormLoaderServiceException(FormLoaderServiceException.ERROR_USER_INVALID,
					"User name for the collection is either null or empty. Unable to load form collection");
		
		String xmlPath = aCollection.getXmlPathOnServer();
		String xmlName = aCollection.getXmlFileName();
		
		if (xmlPath == null || xmlPath.length() == 0 ||
				xmlName == null || xmlName.length() == 0)
			throw new FormLoaderServiceException(FormLoaderServiceException.ERROR_FILE_INVALID,
					"Xml file path or name is invalid");
		
		String xmlPathName = xmlPath.endsWith("\\") ? xmlPath + xmlName : xmlPath + "\\" + xmlName;
		
		List<FormDescriptor> forms = aCollection.getForms();
		if (forms == null || forms.size() == 0) {
			logger.error("Input form list is null or empty. Nothing to do");
			throw new FormLoaderServiceException(FormLoaderServiceException.ERROR_EMPTY_FORM_LIST,
					"Input form list is null or empty. Unable to validate form content.");
		}
			
		loadForms(xmlPathName, forms, loggedinuser);
		
		//TODO: update form loader related tables.
		createRecordsForCollection(aCollection, loggedinuser);
		
		
		return aCollection;
	}
	
	protected void createRecordsForCollection(FormCollection coll, String user) {
		List<FormDescriptor> forms = coll.getForms();
		
		//get generated public id for the loaded forms
		this.repository.setPublicIdVersionBySeqids(forms);
		
		String collSeqid = this.repository.createFormCollectionRecords(coll);
		coll.setId(collSeqid);
		coll.setDateCreated(new Date()); //TODO: This probably should come from db after create
		
		logger.info("Collection \"" + coll.getName() + "\" loaded successfully, with seqid: " + collSeqid);
		
	}
	
	protected void loadForms(String xmlPathName, List<FormDescriptor> forms, String loggedinUser) 
	throws FormLoaderServiceException {
		
		int form_idx = 0;
		for (FormDescriptor form : forms) {
			if (form.getLoadStatus() < FormDescriptor.STATUS_DB_VALIDATED) {
				form.addMessage("Form didn't pass db validation. Unable to load form");
				logger.debug("Form didn't pass db validation. Unable to load form");
				continue;
			}
			
			if (!form.isSelected()) {
				form.addMessage("Form is not selected by user. Skip loading");
				logger.debug("Form is not selected by user. Skip loading");
				continue;
			}
			
			if (!form.getLoadType().equals(FormDescriptor.LOAD_TYPE_NEW ) &&
					!form.getLoadType().equals(FormDescriptor.LOAD_TYPE_NEW_VERSION ) &&
					!form.getLoadType().equals(FormDescriptor.LOAD_TYPE_UPDATE_FORM)) {
				form.addMessage("Form has undetermined loat type. Unable to load form");
				logger.debug("Form has undetermined loat type. Unable to load form");
				continue;
			}
			
			//TODO: check context, designation, definition and reference doc type here
			//What about UOM name
			
			if (!validContextName(form)) {
				form.setLoadStatus(FormDescriptor.STATUS_LOAD_FAILED);
				logger.debug("Context name invalid");
				continue;
			}
			
			if (!userHasRight(form, loggedinUser)) {
				form.setLoadStatus(FormDescriptor.STATUS_LOAD_FAILED);
				logger.debug("User right issue");
				continue;
			}
				
			if (FormDescriptor.LOAD_TYPE_NEW.equals(form.getLoadType())
					|| FormDescriptor.LOAD_TYPE_NEW_VERSION.equals(form.getLoadType())) {
				
				String seqid = this.repository.createForm(form, loggedinUser, xmlPathName, form_idx);
				if (seqid == null || seqid.length() == 0)
					form.setLoadStatus(FormDescriptor.STATUS_LOAD_FAILED);
				else
					form.setLoadStatus(FormDescriptor.STATUS_LOADED);
			} else if (FormDescriptor.LOAD_TYPE_UPDATE_FORM.equals(form.getLoadType())) {
				this.repository.updateForm(form, loggedinUser, xmlPathName, form_idx);
				form.setLoadStatus(FormDescriptor.STATUS_LOADED);
			} 
			
			form_idx++;
		}	
	}
	
	protected boolean validContextName(FormDescriptor form) {
		
		String contextName = form.getContext();
		if (contextName == null || contextName.length() == 0) {
			form.addMessage("Context name in form is null or empty. Use \"NCIP\" context to load form");
			form.setContext("NCIP");
		}
		
		String contextSeqid = this.repository.getContextSeqIdByName(form.getContext());
		if (contextSeqid == null || contextSeqid.length() == 0) {
			form.addMessage("Context name in form \"" + form.getContext() + "\" is not valid. Unable to load form");
			return false;
		} 
		
		form.setContextSeqid(contextSeqid);
		return true;
	}
	
	/**
	 * when loading, first check user from xml form. If that's invalid, 
	 * check with the form loader user name. If neither works, inform user
	 *  - confirmed with Denise on 8/7/2013
	 * @param form
	 * @param loggeduser
	 * @return
	 */
	protected boolean userHasRight(FormDescriptor form, String loggeduser) {
		if (form.getLoadType().equals(FormDescriptor.LOAD_TYPE_UPDATE_FORM)) {
			if (!this.repository.hasLoadFormRight(form.getModifiedBy(), form.getContext())) {
				form.addMessage("Form's modifiedBy user has no right to update form into context \"" + form.getContext() + 
						". Will try with Form Loader's logged-in user");
				
				if (this.repository.hasLoadFormRight(loggeduser, form.getContext())) {
					form.setModifiedBy(loggeduser);
					return true;
				}
				
			} else
				return true;
		} else {
			if (!this.repository.hasLoadFormRight(form.getCreatedBy(), form.getContext())) {
				form.addMessage("Form's createdBy user has no right to create form into context \"" + form.getContext() + 
						". Will try with Form Loader's logged-in user");
				if (this.repository.hasLoadFormRight(loggeduser, form.getContext())) {
					form.setCreatedBy(loggeduser);
					return true;
				}
				form.setCreatedBy(loggeduser);	
			} else 
				return true;
		}
		
		form.addMessage("Neither the form's createdBy/modifiedBy nor the Form Loader logged in user has right to load form. Form load failed");
		return false;
	}
	

}
