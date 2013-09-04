package gov.nih.nci.cadsr.formloader.service.impl;

import org.springframework.stereotype.Service;
import java.util.List;

import gov.nih.nci.cadsr.formloader.domain.FormCollection;
import gov.nih.nci.cadsr.formloader.domain.FormDescriptor;
import gov.nih.nci.cadsr.formloader.repository.FormLoaderRepository;
import gov.nih.nci.cadsr.formloader.service.UnloadingService;
import gov.nih.nci.cadsr.formloader.service.common.FormLoaderServiceException;

@Service
public class UnloadingServiceImpl implements UnloadingService {
	
	FormLoaderRepository repository;
	
	public UnloadingServiceImpl() {}
	
	public UnloadingServiceImpl(FormLoaderRepository repository) {
		this.repository = repository;
	}

	public FormLoaderRepository getRepository() {
		return repository;
	}

	public void setRepository(FormLoaderRepository repository) {
		this.repository = repository;
	}

	@Override
	public List<FormCollection> unloadCollections(
			List<FormCollection> collections, String userName) throws FormLoaderServiceException {

		if (collections == null)
			throw new FormLoaderServiceException(FormLoaderServiceException.ERROR_COLLECTION_NULL, 
					"Form collections is null. Nothing to unload");
		
		for (FormCollection coll : collections) {
			unloadForms(coll.getForms(), userName);
		}

		return null;
	}

	@Override
	public List<FormDescriptor> unloadForms(List<FormDescriptor> forms, String userName)
			throws FormLoaderServiceException {
		
		if (userName == null || userName.length() == 0)
			throw new FormLoaderServiceException(FormLoaderServiceException.ERROR_USER_INVALID, 
					"Login user name is null or empty");
		
		if (forms == null)
			throw new FormLoaderServiceException(FormLoaderServiceException.ERROR_EMPTY_FORM_LIST, 
					"Form list is null");
		
		for (FormDescriptor form : forms) {
			if (!form.isSelected())
				continue;
			
			String context = form.getContext();
			if (repository.hasLoadFormRight(form, userName, context)) {
				form.addMessage("Loggedin user doesn't have right to unload form with context \"" + context + "\"");
				form.setLoadStatus(FormDescriptor.STATUS_UNLOAD_FAILED);
				continue;
			}
			
			repository.unloadForm(form);
		}
		
		
		return null;
	}

	

}
