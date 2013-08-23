package gov.nih.nci.cadsr.formloader.service;

import gov.nih.nci.cadsr.formloader.domain.FormCollection;
import gov.nih.nci.cadsr.formloader.service.common.FormLoaderServiceException;

public interface ContentValidationService {

	/**
	 * Validate the content of forms in xml, after a successful xml wellness/ xsd validation.
	 * 
	 * @param aCollection 
	 * @param xmlPathName 
	 * @return
	 * @throws FormLoaderServiceException
	 */
	public FormCollection validateXmlContent(FormCollection aCollection) 
			throws FormLoaderServiceException;
}
