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
import org.springframework.transaction.annotation.Transactional;

@Service
public class CollectionRetrievalServiceImpl implements CollectionRetrievalService {

	private static Logger logger = Logger.getLogger(CollectionRetrievalServiceImpl.class.getName());
	
	FormLoaderRepository repository;
	
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
	
	@Transactional(readOnly=true)
	@Override
	public List<FormCollection> getAllCollectionsByUser(String userName) throws FormLoaderServiceException {
		//Not check user credential. Check on unload
		
		if (userName == null || userName.length() == 0) 
			throw new FormLoaderServiceException(FormLoaderServiceException.ERROR_USER_INVALID, 
					"User name is null or empty. Unable to get collections previously loader by user");
		
		List<FormCollection >colls = repository.getAllLoadedCollectionsByUser(userName);
		if (colls == null || colls.size() == 0) {
			logger.info("User [" + userName + "] doesn't seem have loaded any colleciton previously.");
			return new ArrayList<FormCollection>();
		}
		
		return colls;
	}
	
}
