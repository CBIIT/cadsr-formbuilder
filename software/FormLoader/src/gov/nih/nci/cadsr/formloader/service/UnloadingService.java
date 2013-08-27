package gov.nih.nci.cadsr.formloader.service;

import java.util.List;

import gov.nih.nci.cadsr.formloader.domain.FormCollection;
import gov.nih.nci.cadsr.formloader.domain.FormDescriptor;
import gov.nih.nci.cadsr.formloader.service.common.FormLoaderServiceException;

public interface UnloadingService {
	
	public List<FormCollection> unloadCollections(List<FormCollection> collections, String userName) 
			throws FormLoaderServiceException;
	
	public List<FormDescriptor> unloadForms(List<FormDescriptor> forms, String userName) 
			throws FormLoaderServiceException;

}
