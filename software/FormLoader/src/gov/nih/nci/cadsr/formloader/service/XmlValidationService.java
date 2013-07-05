package gov.nih.nci.cadsr.formloader.service;

import gov.nih.nci.cadsr.formloader.domain.FormHeader;
import gov.nih.nci.cadsr.formloader.service.common.FormLoaderServiceException;

import java.util.List;

public interface XmlValidationService {
	
	public List<FormHeader> validateXml(String xmlPathName) 
			throws FormLoaderServiceException;

}
