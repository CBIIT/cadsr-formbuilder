package gov.nih.nci.cadsr.formloader.service;

import gov.nih.nci.cadsr.formloader.domain.FormCollection;
import gov.nih.nci.cadsr.formloader.service.common.FormLoaderServiceException;

public interface ContentValidationService {

	public FormCollection validateXmlContent(FormCollection aCollection, String xmlPathName) 
			throws FormLoaderServiceException;
}
