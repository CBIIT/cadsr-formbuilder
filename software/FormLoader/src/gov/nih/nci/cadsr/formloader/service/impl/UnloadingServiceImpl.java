package gov.nih.nci.cadsr.formloader.service.impl;

import org.jboss.xnio.log.Logger;
import org.springframework.stereotype.Service;
import java.util.List;

import gov.nih.nci.cadsr.formloader.domain.FormCollection;
import gov.nih.nci.cadsr.formloader.domain.FormDescriptor;
import gov.nih.nci.cadsr.formloader.repository.FormLoaderRepository;
import gov.nih.nci.cadsr.formloader.service.UnloadingService;
import gov.nih.nci.cadsr.formloader.service.common.FormLoaderServiceException;

@Service
public class UnloadingServiceImpl implements UnloadingService {
	
	private static Logger logger = Logger.getLogger(UnloadingServiceImpl.class.getName());
	
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
			try {
				unloadForms(coll.getForms(), userName);
			} catch (FormLoaderServiceException fle) {
				if (fle.getErrorCode() == FormLoaderServiceException.ERROR_EMPTY_FORM_LIST)
					logger.debug("Collection [" + coll.getName() + "] has 0 form. Nothing to unload.");
				else if (fle.getErrorCode() == FormLoaderServiceException.ERROR_USER_INVALID)
					logger.error("Login user name is empty or null. Unable to unload form.");
			}
		}

		return collections;
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
			if (!repository.hasLoadFormRight(form, userName, context)) {
				form.addMessage("Loggedin user doesn't have right to unload form with context \"" + context + "\"");
				form.setLoadStatus(FormDescriptor.STATUS_UNLOAD_FAILED);
				continue;
			}
			
			repository.unloadForm(form);
		}
		
		
		return forms;
	}

	

}
