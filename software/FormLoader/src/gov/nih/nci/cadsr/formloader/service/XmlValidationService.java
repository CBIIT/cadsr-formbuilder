package gov.nih.nci.cadsr.formloader.service;

import gov.nih.nci.cadsr.formloader.domain.FormDescriptor;
import gov.nih.nci.cadsr.formloader.service.common.FormLoaderServiceException;

import java.util.List;

public interface XmlValidationService {
	
	public List<FormDescriptor> validateXml(String xmlPathName) 
			throws FormLoaderServiceException;

}
