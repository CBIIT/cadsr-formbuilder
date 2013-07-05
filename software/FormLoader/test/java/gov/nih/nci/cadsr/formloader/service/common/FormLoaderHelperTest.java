package gov.nih.nci.cadsr.formloader.service.common;

import static org.junit.Assert.*;

import gov.nih.nci.cadsr.formloader.service.common.FormLoaderHelper;
import gov.nih.nci.cadsr.formloader.service.common.XmlValidationError;

import java.io.File;
import java.util.List;

import org.junit.Test;

public class FormLoaderHelperTest {

	@Test
	public void testHasWellFormednessErrorNoError() {
		
		XmlValidationError error = FormLoaderHelper.checkXmlWellFormedness(".\\test\\data\\forms.xml");
		assertNotNull(error);
		assertTrue(error.getType().equals(XmlValidationError.XML_NO_ERROR));
	}
	
	@Test
	public void testHasWellFormednessErrorMalFormedXml() {
		
		XmlValidationError error = FormLoaderHelper.checkXmlWellFormedness(".\\test\\data\\forms-malformed.xml");
		assertNotNull(error);
		assertTrue(error.getType().equals(XmlValidationError.XML_FATAL_ERROR));
	}
	
	@Test
	public void testHasWellFormednessErrorFileNotFound() {
		XmlValidationError error = FormLoaderHelper.checkXmlWellFormedness(".\\test\\data\\forms-malf.xml");
		assertNotNull(error);
		assertTrue(error.getType().equals(XmlValidationError.XML_FILE_NOT_FOUND));
	}

	@Test
	public void testHasWellFormednessErrorWithNullFile() {
		XmlValidationError error = FormLoaderHelper.checkXmlWellFormedness(null);
		assertNotNull(error);
		assertTrue(error.getType().equals(XmlValidationError.XML_FILE_INVALID));
	}
	
	@Test
	public void testValidateXml() {
		List<XmlValidationError> errors = FormLoaderHelper.validateXml(".\\test\\data\\forms-2.xml", ".\\test\\data\\FormLoaderv2.xsd");
		assertNotNull(errors);
		assertTrue(errors.size() > 0);
	}

}
