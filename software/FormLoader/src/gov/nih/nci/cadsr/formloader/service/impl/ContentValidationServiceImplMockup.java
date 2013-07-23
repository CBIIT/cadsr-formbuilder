package gov.nih.nci.cadsr.formloader.service.impl;

import gov.nih.nci.cadsr.formloader.domain.FormCollection;
import gov.nih.nci.cadsr.formloader.service.ContentValidationService;
import gov.nih.nci.cadsr.formloader.service.common.FormLoaderServiceException;
import gov.nih.nci.cadsr.formloader.service.common.MockDataGenerator;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class ContentValidationServiceImplMockup implements ContentValidationService {
	
	private static Logger logger = Logger.getLogger(ContentValidationServiceImpl.class.getName());
	
	public ContentValidationServiceImplMockup() {}
	
	@Override
	public FormCollection validateXmlContent(FormCollection aCollection, String xmlPathName) 
			throws FormLoaderServiceException {
		
		aCollection = MockDataGenerator.generateContentValidationData();
		
		
		return aCollection;
	}
	
	
}
