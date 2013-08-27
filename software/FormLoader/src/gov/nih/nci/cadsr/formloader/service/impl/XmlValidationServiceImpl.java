package gov.nih.nci.cadsr.formloader.service.impl;

import gov.nih.nci.cadsr.formloader.domain.FormCollection;
import gov.nih.nci.cadsr.formloader.domain.FormDescriptor;
import gov.nih.nci.cadsr.formloader.service.XmlValidationService;
import gov.nih.nci.cadsr.formloader.service.common.FormLoaderHelper;
import gov.nih.nci.cadsr.formloader.service.common.FormLoaderServiceException;
import gov.nih.nci.cadsr.formloader.service.common.StaXParser;
import gov.nih.nci.cadsr.formloader.service.common.XmlValidationError;
import gov.nih.nci.cadsr.formloader.service.common.XmlValidationErrorHandler;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;

import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

@Service
public class XmlValidationServiceImpl implements XmlValidationService, ResourceLoaderAware {
	
	private static Logger logger = Logger.getLogger(XmlValidationServiceImpl.class.getName());
	
	protected ResourceLoader resourceLoader;
	
	protected String XSD_PATH_NAME = "FormCartv22.xsd";
	//protected String XSD_PATH_NAME = "FormLoaderv2.xsd";
	

	public XmlValidationServiceImpl() {}
	
	/** 
	 * Validate the xml file. 
	 * 
	 * Will FormLoaderServiceException when xml is not valid or malformed. <br>
	 * Call FormDescriptor.getLoadStatus() to check status after <br>
	 * If load status is not FormDescriptor.STATUS_XML_VALIDATED, FormDescriptor.getXmlValidationErrors() <br>
	 * is available to get an error list.
	 * 
	 * @param xmlPathName
	 * @return
	 * @throws FormLoaderServiceException when xml is not valid or malformed. 
	 * 			Call FormLoaderServiceException.getMessage() for explanation of the error
	 * 
	 */
	@Override
	public FormCollection validateXml(FormCollection collection) throws FormLoaderServiceException{
		
		if (collection == null) {
			throw new FormLoaderServiceException(FormLoaderServiceException.ERROR_COLLECTION_NULL,
					"Input collection object is null");
		}
		
		String xmlPathName = FormLoaderHelper.checkInputFile(collection.getXmlPathOnServer(), collection.getXmlFileName());
		
		XmlValidationError error = checkXmlWellFormedness(xmlPathName);
		if (error != null && error.getType() != XmlValidationError.XML_NO_ERROR)
			throw new FormLoaderServiceException(FormLoaderServiceException.ERROR_MALFORMED_XML,
					"Xml Malform Error", error);
		
		logger.debug("XSD_PATH_NAME: " + XSD_PATH_NAME);
		Resource resource = this.resourceLoader.getResource(
				"classpath:gov/nih/nci/cadsr/formloader/service/impl/" + XSD_PATH_NAME);

		List<FormDescriptor> forms = null;

		List<XmlValidationError> errors = validateXml(xmlPathName, resource);

		//match errors with a form via line number in an error
		StaXParser parser = new StaXParser();
		forms = parser.parseFormHeaders(xmlPathName);		
		assignErrorsToForms(forms, errors);

		collection.setForms(forms);
		return collection;
	}
	
	/**
	 * Assign errors to forms based on line number where the error was detected
	 * @param forms
	 * @param errors
	 */
	protected void assignErrorsToForms(List<FormDescriptor> forms, List<XmlValidationError> errors) {
		if (forms == null || forms.size() == 0) {
			logger.debug("form list null or empty. Nothing to do");
			return;
		}
		
		if (errors != null && errors.size() > 0) {
			int currFormIdx = 0;
			for (XmlValidationError xmlError : errors) {
				int formSize = forms.size();
				for (int i = 0; i < formSize; i++) {
					currFormIdx = assignError(xmlError, forms, currFormIdx);
				}
			}
		}
		
		//Update xml validation status to all forms
		for (FormDescriptor form : forms) {
			if (form.getLoadStatus() != FormDescriptor.STATUS_XML_VALIDATION_FAILED)
				form.setLoadStatus(FormDescriptor.STATUS_XML_VALIDATED);	
		}
	}
	
	/**
	 * 
	 * @param xmlError
	 * @param forms
	 * @param startIdx 
	 * @return
	 */
	protected int assignError(XmlValidationError xmlError, List<FormDescriptor> forms, int startIdx) {
		int total = forms.size();
		int errorLineNum = xmlError.getLineNumber();
		for (int i = startIdx; i < total; i++) {
			FormDescriptor form = forms.get(i);
			if (errorLineNum >= form.getXml_line_begin() && errorLineNum <= form.getXml_line_end()) {
				form.getErrors().add(xmlError);
				form.setLoadStatus(FormDescriptor.STATUS_XML_VALIDATION_FAILED);
				return i;
			}
		}
		
		//If we get here, no match is found. This could be errors outside of "form" elements
		logger.warn("Unable to find owner form for this error: " + xmlError.toString());
		return startIdx;
	}
	
	/**
	 * Check WellFormedness of an xml file
	 * @param xmlPathName
	 * @return If wellformed, XmlValidationError with XML_NO_ERROR. Otherwise, XmlValidationError with one of the error types
	 */
	public XmlValidationError checkXmlWellFormedness(String xmlPathName) 
		throws FormLoaderServiceException {
		
		XmlValidationError error;
	
		XmlValidationErrorHandler errorHandler = new XmlValidationErrorHandler();
		try {
            SAXReader reader = new SAXReader();
            reader.setValidation(false);
            reader.setErrorHandler(errorHandler);
            reader.read(xmlPathName);
        } catch (DocumentException e) {
        	logger.error(e);
        	throw new FormLoaderServiceException(FormLoaderServiceException.ERROR_MALFORMED_XML,
					"Xml Malform Error: " + e.getMessage());
        }
		
		return (errorHandler.getXmlErrors().size() > 0) ? 
				errorHandler.getXmlErrors().get(0) : new XmlValidationError(XmlValidationError.XML_NO_ERROR, "Xml well formed", 0);
	}
	
	public List<XmlValidationError> validateXml(String xmlPathName, Resource resource) 
	throws FormLoaderServiceException {
		
		logger.debug("Start validating xml file: " + xmlPathName);
		XmlValidationErrorHandler handler = new XmlValidationErrorHandler();
		InputStream is = null;
		BufferedReader bufferredReader = null;
		try {
			is = resource.getInputStream();
			bufferredReader = new BufferedReader(new InputStreamReader(is));
			
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            factory.setSchema(schemaFactory.newSchema(new Source[] {new StreamSource(bufferredReader)}));

            SAXParser parser = factory.newSAXParser();

            SAXReader reader = new SAXReader(parser.getXMLReader());
            reader.setValidation(true);  //TODO: check on this
            reader.setErrorHandler(handler);
            reader.read(xmlPathName);
            
        } catch (IOException e) { 
            logger.error("IOException while validing xml: " + e.getMessage());
            throw new FormLoaderServiceException(FormLoaderServiceException.ERROR_XML_EXCEPTION,
					"Xml validation Error: " + e.getMessage());
        } catch (ParserConfigurationException e) {
        	logger.error("ParserConfigurationException while validing xml:" + e.getMessage());
        	throw new FormLoaderServiceException(FormLoaderServiceException.ERROR_XML_EXCEPTION,
					"Xml validation Error: " + e.getMessage());
        } catch (SAXException e) {
        	logger.error("SAXException while validing xml: " + e.getMessage());
        	throw new FormLoaderServiceException(FormLoaderServiceException.ERROR_XML_EXCEPTION,
					"Xml validation Error: " + e.getMessage());
        } catch (DocumentException e) {
        	logger.error("DocumentException while validing xml: " + e.getMessage());
        	throw new FormLoaderServiceException(FormLoaderServiceException.ERROR_XML_EXCEPTION,
					"Xml validation Error: " + e.getMessage());
        } finally {
        	try {
        		if (is != null)
        			is.close();
        	
        		if (bufferredReader != null)
        			bufferredReader.close();
        	} catch (IOException ioe) {
        		logger.error("Error while closing xsd fileresource");
        	}
        		
        }
		
		logger.debug("Done validating xml file: " + xmlPathName);
		return handler.getXmlErrors();
	}



	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
		
	}
	
	public Resource getResource(String location){
		return resourceLoader.getResource(location);
	}

	public String getXSD_PATH_NAME() {
		return XSD_PATH_NAME;
	}

	public void setXSD_PATH_NAME(String xSD_PATH_NAME) {
		XSD_PATH_NAME = xSD_PATH_NAME;
	}
	
	
}
