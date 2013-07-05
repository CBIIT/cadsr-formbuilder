package gov.nih.nci.cadsr.formloader.service.impl;

import java.util.List;

import gov.nih.nci.cadsr.formloader.domain.FormCollection;
import gov.nih.nci.cadsr.formloader.domain.FormHeader;
import gov.nih.nci.cadsr.formloader.service.XmlValidationService;
import gov.nih.nci.cadsr.formloader.service.common.FormLoaderServiceError;
import gov.nih.nci.cadsr.formloader.service.common.FormLoaderServiceException;
import gov.nih.nci.cadsr.formloader.service.common.XmlValidationError;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext-service-test.xml"})
public class XmlValidationServiceImplTest {
	
	private static Logger logger = Logger.getLogger(XmlValidationServiceImplTest.class.getName());
	
	@Autowired
    private XmlValidationService xmlValService;

	@Before
	public void setUp() throws Exception {
	
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testValidateXmlMalformed() {
		try {
			List<FormHeader> forms = this.xmlValService.validateXml(".\\test\\data\\forms-malformed.xml");
			fail("Exception not thrown as expected");
		} catch (FormLoaderServiceException e) {
			System.out.println(e.toString());
			assertTrue(e.getErrorCode() == FormLoaderServiceError.ERROR_MALFORMED_XML);
			assertTrue(e.getError() instanceof XmlValidationError);
			assertTrue(((XmlValidationError)e.getError()).getType().equals(XmlValidationError.XML_FATAL_ERROR));
		}
	}
	
	@Test
	public void testValidateXmlHappyPath() {
		XmlValidationServiceImpl.XSD_PATH_NAME = ".\\test\\data\\FormLoaderv1-testonly.xsd";
		try {
			List<FormHeader> forms = this.xmlValService.validateXml(".\\test\\data\\forms.xml");
			assertNotNull(forms);
			assertTrue(forms.size() == 3);
			assertTrue(forms.get(0).getErrors().size() > 0);
		
		} catch (FormLoaderServiceException e) {
			fail("Got exception: " + e.toString());
		}
	}

}
