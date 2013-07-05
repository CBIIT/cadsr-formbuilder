package gov.nih.nci.cadsr.formloader.service.impl;

import gov.nih.nci.cadsr.formloader.domain.FormHeader;
import gov.nih.nci.cadsr.formloader.service.XmlValidationService;
import gov.nih.nci.cadsr.formloader.service.common.FormLoaderHelper;
import gov.nih.nci.cadsr.formloader.service.common.FormLoaderServiceError;
import gov.nih.nci.cadsr.formloader.service.common.FormLoaderServiceException;
import gov.nih.nci.cadsr.formloader.service.common.StaXParser;
import gov.nih.nci.cadsr.formloader.service.common.XmlValidationError;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class XmlValidationServiceImpl implements XmlValidationService {
	
	private static Logger logger = Logger.getLogger(XmlValidationServiceImpl.class.getName());
	
	protected static String XSD_PATH_NAME = "forms.xsd";

	public XmlValidationServiceImpl() {}
	
	/** 
	 * @param xmlPathName
	 * @return
	 * @throws FormLoaderServiceException when xml is not valid or malformed
	 */
	@Override
	public List<FormHeader> validateXml(String xmlPathName) throws FormLoaderServiceException{
		
		XmlValidationError error = FormLoaderHelper.checkXmlWellFormedness(xmlPathName);
		if (error != null && error.getType() != XmlValidationError.XML_NO_ERROR)
			throw new FormLoaderServiceException(FormLoaderServiceError.ERROR_MALFORMED_XML,
					"Xml Malform Error", error);
		
		List<XmlValidationError> errors = FormLoaderHelper.validateXml(xmlPathName, XSD_PATH_NAME);
		
		//match errors with a form via line number in an error
		StaXParser parser = new StaXParser();
		List<FormHeader> forms = parser.parseFormHeaders(xmlPathName);		
		assignErrorsToForms(forms, errors);
		
		return forms;
	}
	
	protected void assignErrorsToForms(List<FormHeader> forms, List<XmlValidationError> errors) {
		if (forms == null || forms.size() == 0 ||
				errors == null || errors.size() == 0) {
			logger.debug("form list or error list is null or empty. Nothing to do");
			return;
		}
		
		int currFormIdx = 0;
		for (XmlValidationError xmlError : errors) {
			int formSize = forms.size();
			for (int i = 0; i < formSize; i++) {
				currFormIdx = assignError(xmlError, forms, currFormIdx);
			}
		}
	}
	
	/**
	 * 
	 * @param xmlError
	 * @param forms
	 * @param startIdx 
	 * @return
	 */
	protected int assignError(XmlValidationError xmlError, List<FormHeader> forms, int startIdx) {
		int total = forms.size();
		int errorLineNum = xmlError.getLineNumber();
		for (int i = startIdx; i < total; i++) {
			FormHeader form = forms.get(i);
			if (errorLineNum >= form.getXml_line_begin() && errorLineNum <= form.getXml_line_end()) {
				form.getErrors().add(xmlError);
				return i;
			}
		}
		
		//If we get here, no match is found. This could be errors outside of "form" elements
		logger.warn("Unable to find owner form for this error: " + xmlError.toString());
		return startIdx;
	}
	

}
