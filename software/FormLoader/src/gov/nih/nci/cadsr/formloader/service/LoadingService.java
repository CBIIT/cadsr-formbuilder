package gov.nih.nci.cadsr.formloader.service;

import gov.nih.nci.cadsr.formloader.domain.FormCollection;
import gov.nih.nci.cadsr.formloader.service.common.FormLoaderServiceException;

public interface LoadingService {
	
	public FormCollection loadForms(FormCollection aCollection) 
			throws FormLoaderServiceException;

}
