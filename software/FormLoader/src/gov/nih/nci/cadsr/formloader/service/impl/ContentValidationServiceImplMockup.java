/*L
 * Copyright Oracle Inc, ScenPro Inc, SAIC-F
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-formbuilder/LICENSE.txt for details.
 */

package gov.nih.nci.cadsr.formloader.service.impl;

import gov.nih.nci.cadsr.formloader.domain.FormCollection;
import gov.nih.nci.cadsr.formloader.service.ContentValidationService;
import gov.nih.nci.cadsr.formloader.service.common.FormLoaderServiceException;
import gov.nih.nci.cadsr.formloader.service.common.JAXBMockGen;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class ContentValidationServiceImplMockup implements ContentValidationService {
	
	private static Logger logger = Logger.getLogger(ContentValidationServiceImpl.class.getName());
	
	public ContentValidationServiceImplMockup() {}
	
	@Override
	public FormCollection validateXmlContent(FormCollection aCollection, String xmlPathName) 
			throws FormLoaderServiceException {
		
		aCollection = JAXBMockGen.xmlToObjects(".\\test\\data\\mockdata-forms.xml");
		
		
		return aCollection;
	}
	
	
}
