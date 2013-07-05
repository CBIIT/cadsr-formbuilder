package gov.nih.nci.cadsr.formloader.service.common;


import java.io.File;
import java.util.ArrayList;
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
import org.xml.sax.SAXException;

public class FormLoaderHelper {
	
	private static Logger logger = Logger.getLogger(FormLoaderHelper.class.getName());

	public static XmlValidationError filePahtNameContainsError(String filePathName) {
		if (filePathName == null)
			return new XmlValidationError(XmlValidationError.XML_FILE_INVALID, "File path name is null", 0);
		else {
			File xml = new File(filePathName);
			if (!xml.exists() || !xml.canRead()) {
				return new XmlValidationError(XmlValidationError.XML_FILE_NOT_FOUND, 
						"File doesn't exist or can't be read at " + filePathName, 0);
			}
		}
		
		//if we're here, we're good
		return null;
	}
	
	public static String checkFilePathName(String filePathName) {
		if (filePathName == null)
			return "File path name is null";
		else {
			File xml = new File(filePathName);
			if (!xml.exists() || !xml.canRead()) {
				return "File doesn't exist or can't be read at " + filePathName;
			}
		}
		
		//if we're here, we're good
		return "";
	}
	
	public static XmlValidationError inputFilePathNamesContainsError(String xmlPathName, String xsdPathName) {
		
		XmlValidationError error = filePahtNameContainsError(xmlPathName);
		if (error == null) {
			error = filePahtNameContainsError(xsdPathName);
		}
		
		return error;
	}
	
	/**
	 * Check WellFormedness of an xml file
	 * @param xmlPathName
	 * @return If wellformed, XmlValidationError with XML_NO_ERROR. Otherwise, XmlValidationError with one of the error types
	 */
	public static XmlValidationError checkXmlWellFormedness(String xmlPathName) {
		
		XmlValidationError error;
		if ((error = filePahtNameContainsError(xmlPathName)) != null)
			return error;
			
		XmlValidationErrorHandler errorHandler = new XmlValidationErrorHandler();
		try {
            SAXReader reader = new SAXReader();
            reader.setValidation(false);
            reader.setErrorHandler(errorHandler);
            reader.read(xmlPathName);
        } catch (DocumentException e) {
        	//TODO: what should we do here? Create an XmlValidateError, maybe
        	logger.error(e);
        }
		
		return (errorHandler.getXmlErrors().size() > 0) ? 
				errorHandler.getXmlErrors().get(0) : new XmlValidationError(XmlValidationError.XML_NO_ERROR, "Xml well formed", 0);
	}

	
	public static List<XmlValidationError> validateXml(String xmlPathName, String xsdPathName) {

		XmlValidationError error;
		if ((error = inputFilePathNamesContainsError(xmlPathName, xsdPathName)) != null) {
			List<XmlValidationError> errors = new ArrayList<XmlValidationError>();
			errors.add(error);
			return errors;
		}
		
		logger.debug("Start validating xml file: " + xmlPathName);
		XmlValidationErrorHandler handler = new XmlValidationErrorHandler();
		try {
			
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            factory.setSchema(schemaFactory.newSchema(new Source[] {new StreamSource(xsdPathName)}));

            SAXParser parser = factory.newSAXParser();

            SAXReader reader = new SAXReader(parser.getXMLReader());
            reader.setValidation(true);  //TODO: check on this
            reader.setErrorHandler(handler);
            reader.read(xmlPathName);
            
            System.out.println("done");
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
		
		logger.debug("Done validating xml file: " + xmlPathName);
		return handler.getXmlErrors();
	}

}
