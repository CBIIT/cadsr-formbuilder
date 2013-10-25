package gov.nih.nci.cadsr.formloader.service.impl;

import gov.nih.nci.cadsr.formloader.domain.FormCollection;
import gov.nih.nci.cadsr.formloader.domain.FormDescriptor;
import gov.nih.nci.cadsr.formloader.repository.FormLoaderRepository;
import gov.nih.nci.cadsr.formloader.service.CollectionRetrievalService;
import gov.nih.nci.cadsr.formloader.service.common.FormLoaderServiceException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class CollectionRetrievalServiceImpl implements CollectionRetrievalService {

	private static Logger logger = Logger.getLogger(CollectionRetrievalServiceImpl.class.getName());
	
	FormLoaderRepository repository;
	
	//temp code
	List<FormCollection> colls;
	
	public CollectionRetrievalServiceImpl() {}
	
	public CollectionRetrievalServiceImpl(FormLoaderRepository repository) {
		this.repository = repository;
	}

	public FormLoaderRepository getRepository() {
		return repository;
	}

	public void setRepository(FormLoaderRepository repository) {
		this.repository = repository;
	}
	
	@Override
	public List<FormCollection> getAllCollectionsByUser(String userName) throws FormLoaderServiceException {
		//Not check user credential. Check on unload
		
		if (userName == null || userName.length() == 0) 
			throw new FormLoaderServiceException(FormLoaderServiceException.ERROR_USER_INVALID, 
					"User name is null or empty. Unable to get collections previously loader by user");
		
		colls = repository.getAllLoadedCollectionsByUser(userName);
		if (colls == null || colls.size() == 0) {
			logger.info("User [" + userName + "] doesn't seem have loaded any colleciton previously.");
			return new ArrayList<FormCollection>();
		}
		
		return colls;
	}

	@Override
	public List<FormDescriptor> getAllFormsByUser(String userName)
			throws FormLoaderServiceException {

		if (colls == null)
			colls = getAllCollectionsByUser(userName);
		
		List<FormDescriptor> forms = convertCollectionsToFormList(colls);
		
		return forms;
	}
	
	/**
	 * This is to convert a collection based list to form based, with collections as form's attribute
	 * 
	 * @param colls
	 * @return
	 */
	protected List<FormDescriptor> convertCollectionsToFormList(List<FormCollection> colls) {
		
		if (colls == null || colls.size() == 0) 
			return new ArrayList<FormDescriptor>();
		
		Map<String, FormDescriptor> formTracker = new HashMap<String, FormDescriptor>();
		
		for (FormCollection coll : colls) {
			List<FormDescriptor> forms = coll.getForms();
			for (FormDescriptor form : forms) {
				if (!formTracker.containsKey(form.getFormSeqId())) {
					form.getBelongToCollections().add(coll);
					formTracker.put(form.getFormSeqId(), form);
				} else {
					formTracker.get(form.getFormSeqId()).getBelongToCollections().add(coll);
				}
			}
		}
		
		return new ArrayList<FormDescriptor>(formTracker.values());
	}

	
}
