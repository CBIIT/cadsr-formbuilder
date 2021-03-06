package gov.nih.nci.cadsr.formloader.service.impl;

import gov.nih.nci.cadsr.formloader.domain.FormCollection;
import gov.nih.nci.cadsr.formloader.domain.FormDescriptor;
import gov.nih.nci.cadsr.formloader.repository.FormLoaderRepository;
import gov.nih.nci.cadsr.formloader.service.XmlValidationService;
import gov.nih.nci.cadsr.formloader.service.common.FormLoaderHelper;
import gov.nih.nci.cadsr.formloader.service.common.FormLoaderServiceException;
import gov.nih.nci.cadsr.formloader.service.common.StaXParser;
import gov.nih.nci.cadsr.formloader.service.common.XmlValidationError;
import gov.nih.nci.cadsr.formloader.service.common.XmlValidationErrorHandler;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import org.apache.xerces.impl.Constants;

@Service
public class XmlValidationServiceImpl implements XmlValidationService, ResourceLoaderAware {
	
	private static Logger logger = Logger.getLogger(XmlValidationServiceImpl.class.getName());
	
	protected ResourceLoader resourceLoader;
	FormLoaderRepository repository;
	
	protected String XSD_PATH_NAME = "FormLoaderv17.xsd";
	

	public XmlValidationServiceImpl() {}
	
	@Override
	public FormCollection validateXml(FormCollection collection) throws FormLoaderServiceException {
		
		if (collection == null) {
			throw new FormLoaderServiceException(FormLoaderServiceException.ERROR_COLLECTION_NULL,
					"Input collection object is null");
		}
		
		String xmlPathName = FormLoaderHelper.checkInputFile(collection.getXmlPathOnServer(), collection.getXmlFileName());

		XmlValidationError error = checkXmlWellFormedness(xmlPathName);
		if (error != null && error.getType() != XmlValidationError.XML_NO_ERROR) {
			collection.addMessage(error.getMessage());
			throw new FormLoaderServiceException(FormLoaderServiceException.ERROR_MALFORMED_XML,
					"Xml Malform Error", error);
		}

		logger.debug("XSD_PATH_NAME: " + XSD_PATH_NAME);
		Resource resource = this.resourceLoader.getResource(
				"classpath:gov/nih/nci/cadsr/formloader/service/impl/" + XSD_PATH_NAME);

		List<XmlValidationError> errors = validateXml(xmlPathName, resource);

		StaXParser parser = new StaXParser();
		collection = parser.parseCollectionAndForms(collection, xmlPathName);		

		if (checkOnCollectionOK(collection, errors)) {
			assignErrors(collection, collection.getForms(), errors);
			assignNameRepeatNumber(collection);
		}
		
		return collection;
	}
	
	/**
	 * A quick sanity check on the collection 
	 * @param coll
	 * @return
	 */
	protected boolean checkOnCollectionOK(FormCollection coll, List<XmlValidationError> errors) 
			throws FormLoaderServiceException{
		if (coll == null)
			throw new FormLoaderServiceException(FormLoaderServiceException.ERROR_COLLECTION_NULL, 
					"Unable to construct a valid form collection from xml. Please check your input file.");
		
		String collName = coll.getName();
		if (collName == null || collName.length() == 0)
			throw new FormLoaderServiceException(FormLoaderServiceException.ERROR_COLLECTION_NAME_MISSING, 
					"Collection name is missing in the xml. Please check your input file.");
		
		List<FormDescriptor> forms = coll.getForms();
		if (forms == null || forms.size() == 0)
			throw new FormLoaderServiceException(FormLoaderServiceException.ERROR_EMPTY_FORM_LIST, 
					"There is 0 form in the xml. Please check your input file.");
		
		return true;
	}
	
	/**
	 * Collection name from xml doesn't need to be unique. However, if an existing collection has the same
	 * name, we'll suffix the name with "_n" where n is the highest unoccupied number for the name.
	 * 
	 * @param collection
	 */
	protected void assignNameRepeatNumber(FormCollection collection) {
		int existingMax = repository.getMaxNameRepeatForCollection(collection.getName());
		collection.setNameRepeatNum(++existingMax);
	}
	
	
	/**
	 * Assign errors to forms based on line number where the error was detected
	 * @param forms
	 * @param errors
	 */
	protected void assignErrors(FormCollection collection, List<FormDescriptor> forms, List<XmlValidationError> errors) 
	throws FormLoaderServiceException {
		
		int formSize = forms.size();
		if (errors != null && errors.size() > 0) {
			int currFormIdx = 0;
			for (XmlValidationError xmlError : errors) {
				if (formSize == 0)
					collection.addMessage(xmlError.toString());
				else {
					currFormIdx = assignErrorToForm(xmlError, forms);
					//Unable to assign error to a form. Assign it to collection
					if (currFormIdx < 0)
						evaluateCollectionLevelXmlErrors(collection, xmlError);
				}
			}
		}
		
		//Update xml validation status to all forms
		for (FormDescriptor form : forms) {
			if (form.getLoadStatus() != FormDescriptor.STATUS_XML_VALIDATION_FAILED)
				form.setLoadStatus(FormDescriptor.STATUS_XML_VALIDATED);	
		}
	}
	
	protected void evaluateCollectionLevelXmlErrors(FormCollection collection, XmlValidationError error) 
			throws FormLoaderServiceException {
		String errorString = error.toString();
		
		if (errorString.contains("no grammar found") ||
				errorString.contains("must match DOCTYPE root \"null\""))
			return;  //ignore -- Watch this!
		
		collection.addMessage(errorString);
		
		if (errorString.contains("collectionName"))
			throw new FormLoaderServiceException(FormLoaderServiceException.ERROR_COLLECTION_NAME_MISSING, 
					"Collection name is required in xml");
		
		if (errorString.contains("Cannot find the declaration of element 'form'"))
			throw new FormLoaderServiceException(FormLoaderServiceException.ERROR_FORMS_ELEMENT_MISSING, 
					"Xml doesn't have a \"forms\" element. Suspected to be a wrong file");
	}
	
	/**
	 * 
	 * @param xmlError
	 * @param forms
	 * @param startIdx 
	 * @return
	 */
	protected int assignErrorToForm(XmlValidationError xmlError, List<FormDescriptor> forms) {
		int total = forms.size();
		int errorLineNum = xmlError.getLineNumber();
		
		for (int i = 0; i < total; i++) {
			FormDescriptor form = forms.get(i);
			if (errorLineNum > form.getXml_line_begin() && errorLineNum <= form.getXml_line_end()) {
				form.getErrors().add(xmlError);
				form.setLoadStatus(FormDescriptor.STATUS_XML_VALIDATION_FAILED);
				return i;
			}
		}
		
		//If we get here, no match is found. This should be errors at "forms" (root) element 
		return -1;
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
	
	/**
	 * 
	 * @param xmlPathName
	 * @param resource
	 * @return
	 * @throws FormLoaderServiceException
	 */
	public static List<XmlValidationError> validateXml(String xmlPathName, Resource resource) 
	throws FormLoaderServiceException  {
	    
	    try {
	    	
	    	//String sch = Constants.W3C_XML_SCHEMA11_NS_URI;
	    	// 1. Lookup a factory for the W3C XML Schema language
	    	SchemaFactory factory = SchemaFactory.newInstance(Constants.W3C_XML_SCHEMA11_NS_URI);
		     
		    // 2. Compile the schema.
		    File schemaLocation = resource.getFile();
		    Schema schema = factory.newSchema(schemaLocation);

		    // 3. Get a validator from the schema.
		    Validator validator = schema.newValidator();
		    XmlValidationErrorHandler handler = new XmlValidationErrorHandler();
		    validator.setErrorHandler(handler);
		    
		    // 4. Parse the document you want to check.
		    Source source = new StreamSource(new File(xmlPathName));
		    
		 // 5. Check the document
	        validator.validate(source);
	        logger.debug(xmlPathName + " is valid.");
	        
	        return handler.getXmlErrors();
	    }catch (IOException e) { 
            logger.error("IOException while validing xml: " + e.getMessage());
            throw new FormLoaderServiceException(FormLoaderServiceException.ERROR_XML_EXCEPTION,
					"Xml validation Error: " + e.getMessage());
        }  catch (SAXException e) {
        	logger.error("SAXException while validing xml: " + e.getMessage());
        	throw new FormLoaderServiceException(FormLoaderServiceException.ERROR_XML_EXCEPTION,
					"Xml validation Error: " + e.getMessage());
        } 
	    
	    
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

	public FormLoaderRepository getRepository() {
		return repository;
	}

	public void setRepository(FormLoaderRepository repository) {
		this.repository = repository;
	}
	
	
}
