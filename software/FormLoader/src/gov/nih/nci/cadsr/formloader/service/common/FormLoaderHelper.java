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
import org.springframework.core.io.Resource;
import org.xml.sax.SAXException;

public class FormLoaderHelper {
	
	private static Logger logger = Logger.getLogger(FormLoaderHelper.class.getName());
	
	public static String checkInputFile(String xmlPath, String xmlName) 
			throws FormLoaderServiceException
	{
		if (xmlPath == null || xmlPath.length() == 0)
			throw new FormLoaderServiceException(FormLoaderServiceException.ERROR_FILE_INVALID,
					"Input file path on server is null or empty. Unable to validate form content.");

		if (xmlName == null || xmlName.length() == 0)
			throw new FormLoaderServiceException(FormLoaderServiceException.ERROR_FILE_INVALID,
					"Input file name is null or empty. Unable to validate form content.");

		String xmlPathName = xmlPath.endsWith("\\") ? xmlPath + xmlName : xmlPath + "\\" + xmlName;

		File input = new File(xmlPathName);
		if (input == null || !input.exists() || !input.canRead())
			throw new FormLoaderServiceException(FormLoaderServiceException.ERROR_FILE_INVALID,
					"Input file [" + xmlPathName + "] is invalid. Does it exist? Unable to proceed to load form collection.");


		return xmlPathName;
	}

	

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
	 * This mimicks the xpath function normalize-space(): whitespace normalized by stripping leading and 
	 * trailing whitespace and replacing sequences of whitespace characters by a single space.
	 * 
	 * @param input
	 * @return
	 */
	public static String normalizeSpace(String input) {
		if (input == null || input.length() == 0)
			return input;
		
		String in = input.trim();
		int len = in.length();
		
		char[] outArr = new char[len+1];
		int outIdx = 0;
		int spaceCnt = 0;
		for (int i = 0; i < len; i++) {
			char c = in.charAt(i);
			
			if (c != 32) {
				outArr[outIdx++] = c;
				spaceCnt = 0; 
				continue;
			}
			
			spaceCnt++;
			if (spaceCnt == 1)
				outArr[outIdx++] = c;
		}
		
		return new String(outArr).trim();
		
	}

}
