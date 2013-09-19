package gov.nih.nci.cadsr.formloader.service.impl;

import gov.nih.nci.cadsr.formloader.domain.FormCollection;
import gov.nih.nci.cadsr.formloader.repository.FormLoaderRepository;
import gov.nih.nci.cadsr.formloader.service.CollectionRetrievalService;
import gov.nih.nci.cadsr.formloader.service.common.FormLoaderServiceException;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

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
	
	@Override
	public List<FormCollection> getAllCollectionsByUser(String userName) throws FormLoaderServiceException {
		
		//Not check user credential. Check on unload
		
		List<FormCollection> colls = repository.getAllLoadedCollectionsByUser(userName);
		if (colls != null)
			logger.debug("Retrieved " + colls.size() + " form collections from database created by user [" + userName + "]");
		
		return colls;
	}

	
}
