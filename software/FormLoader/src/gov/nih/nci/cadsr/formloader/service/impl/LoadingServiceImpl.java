package gov.nih.nci.cadsr.formloader.service.impl;

import gov.nih.nci.cadsr.formloader.domain.FormCollection;
import gov.nih.nci.cadsr.formloader.domain.FormDescriptor;
import gov.nih.nci.cadsr.formloader.repository.FormLoaderRepository;
import gov.nih.nci.cadsr.formloader.service.LoadingService;
import gov.nih.nci.cadsr.formloader.service.common.FormLoaderServiceException;
import gov.nih.nci.cadsr.formloader.service.common.StaXParser;
import gov.nih.nci.ncicb.cadsr.common.dto.FormTransferObject;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class LoadingServiceImpl implements LoadingService {
	
	private static Logger logger = Logger.getLogger(LoadingServiceImpl.class.getName());
	
	FormLoaderRepository repository;

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
		
		String user = aCollection.getCreatedBy();
		if (user == null || user.length() == 0) 
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
			
		//loadForms(xmlPathName, forms, user);
		
		//TODO: update form loader related tables.
		//createRecordsForCollection(aCollection, user);
		
		
		return aCollection;
	}
	
	protected void createRecordsForCollection(FormCollection coll, String user) {
		List<FormDescriptor> forms = coll.getForms();
		
		//get generated public id for the loaded forms
		this.repository.setPublicIdVersionBySeqids(forms);;
		
		String collSeqid = this.repository.createFormCollectionRecords(coll);
		
		logger.info("Collection \"" + coll.getName() + "\" loaded successfully, with seqid: " + collSeqid);
		
	}
	
	protected void loadForms(String xmlPathName, List<FormDescriptor> forms, String loggedinUser) {
		
		int form_idx = 0;
		for (FormDescriptor form : forms) {
			if (form.getLoadStatus() < FormDescriptor.STATUS_DB_VALIDATED)
				continue;
			
			if (!form.isSelected())
				continue;
			
			if (FormDescriptor.LOAD_TYPE_NEW.equals(form.getLoadType())
					|| FormDescriptor.LOAD_TYPE_NEW_VERSION.equals(form.getLoadType())) {
				
				this.repository.createForm(form, loggedinUser, xmlPathName, form_idx);
				form.setLoadStatus(FormDescriptor.STATUS_LOADED);
			} else if (FormDescriptor.LOAD_TYPE_UPDATE_FORM.equals(form.getLoadType())) {
				this.repository.updateForm(form, loggedinUser, xmlPathName, form_idx);
				form.setLoadStatus(FormDescriptor.STATUS_LOADED);
			} else {
				form.addMessage("Form will not be loaded due to unknown load type");
				form.setLoadStatus(FormDescriptor.STATUS_LOAD_FAILED);
			}
			
			form_idx++;
		}	
	}

}
