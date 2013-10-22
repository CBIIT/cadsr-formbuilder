package gov.nih.nci.cadsr.formloader.service.impl;

import java.util.List;

import gov.nih.nci.cadsr.formloader.domain.FormCollection;
import gov.nih.nci.cadsr.formloader.domain.FormDescriptor;
import gov.nih.nci.cadsr.formloader.service.XmlValidationService;
import gov.nih.nci.cadsr.formloader.service.common.FormLoaderServiceError;
import gov.nih.nci.cadsr.formloader.service.common.FormLoaderServiceException;
import gov.nih.nci.cadsr.formloader.service.common.StatusFormatter;
import gov.nih.nci.cadsr.formloader.service.common.XmlValidationError;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;

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
		FormCollection aColl = new FormCollection();
		try {			
			aColl.setXmlPathOnServer(".\\test\\data");
			aColl.setXmlFileName("forms-malformed.xml");
			aColl = this.xmlValService.validateXml(aColl);
			fail("Exception not thrown as expected");
		} catch (FormLoaderServiceException e) {
			
			String status = StatusFormatter.getStatusInXml(aColl);
			StatusFormatter.writeStatusToXml(status, ".\\test\\data\\xmlValService-malformed.xml");
			
			System.out.println(e.toString());
			assertTrue(e.getErrorCode() == FormLoaderServiceError.ERROR_MALFORMED_XML);
		}
	}
	
	@Test
	public void testValidateXmlHappyPath() {
		
		try {
			FormCollection aColl = new FormCollection();
			aColl.setXmlPathOnServer(".\\test\\data\\xmlvalidation");
			aColl.setXmlFileName("forms.xml"); //2 of the 3 forms have an empty module (doens't have question).
			aColl = this.xmlValService.validateXml(aColl);
			List<FormDescriptor> forms = aColl.getForms();
			assertNotNull(forms);
			assertTrue(forms.size() == 3);
			
			String xmlerror = forms.get(1).getXmlValidationErrorString();
			logger.debug("Xml validation error: " + xmlerror);
			//assertTrue(xmlerror.length() == 0);
			
			String status = StatusFormatter.getStatusInXml(aColl);
			StatusFormatter.writeStatusToXml(status, ".\\test\\data\\xmlvalidation\\forms.HappyPath.status.xml");
			
			assertTrue(forms.get(1).getErrors().size() > 0);
		
		} catch (FormLoaderServiceException e) {
			fail("Got exception: " + e.toString());
		}
	}
	
	@Test
	public void testValidateXmlCollectionNameMissing() {
		FormCollection aColl = null;
		try {
			aColl = new FormCollection();
			aColl.setXmlPathOnServer(".\\test\\data\\xmlvalidation");
			aColl.setXmlFileName("forms-no-collectionname.xml"); 
			aColl = this.xmlValService.validateXml(aColl);

			fail("Should have thrown exception because collection name is missing in input file");
		
		} catch (FormLoaderServiceException e) {
			
			String status = StatusFormatter.getStatusInXml(aColl);
			StatusFormatter.writeStatusToXml(status, ".\\test\\data\\xmlvalidation\\forms-no-collectionname.status.xml");
			assertTrue(e.getErrorCode() == FormLoaderServiceException.ERROR_COLLECTION_NAME_MISSING);
		}
	}

	@Test
	public void testValidateXmlWithEmptyPublicId() {
		
		//This is to test the xsd. It should accept xmls that don't have form public id or version
		try {
			FormCollection aColl = new FormCollection();
			aColl.setXmlPathOnServer(".\\test\\data\\xmlvalidation");
			aColl.setXmlFileName("forms-2.xml");
			aColl = this.xmlValService.validateXml(aColl);
			List<FormDescriptor> forms = aColl.getForms();
			assertNotNull(forms);
			FormDescriptor form = forms.get(0);
			assertTrue(form.getLoadStatus() == FormDescriptor.STATUS_XML_VALIDATED);
			assertTrue(form.getPublicId() == null || form.getPublicId().length() == 0);
			
			String xmlerror = form.getXmlValidationErrorString();
			logger.debug("Xml validation error: " + xmlerror);
			assertTrue(xmlerror.length() == 0);
		
		} catch (FormLoaderServiceException e) {
			fail("Got exception: " + e.toString());
		}
	}
	
	//@Test No longer needed
	//Context will be validation at content validation.
	public void testValidateXmlWithInvalidContext() {
		//This works because we're using a non-finalized xsd, which has context type enumeration. 
		//When we move to the xsd without those list, this test needs to be removed or althered. 
		// == 09/20/2013
		
		//FormLoaderv7.xsd will validate this xml == 09/26/2013
		try {
			FormCollection aColl = new FormCollection();
			aColl.setXmlPathOnServer(".\\test\\data\\xmlvalidation");
			aColl.setXmlFileName("invalid-context.xml");
			aColl = this.xmlValService.validateXml(aColl);
			List<FormDescriptor> forms = aColl.getForms();
			assertNotNull(forms);
			FormDescriptor form = forms.get(0);
			
			String status = StatusFormatter.getStatusInXml(aColl);
			StatusFormatter.writeStatusToXml(status, ".\\test\\data\\xmlvalidation\\invalid-context.status.xml");
			
			//assertTrue(form.getLoadStatus() == FormDescriptor.STATUS_XML_VALIDATION_FAILED);
			assertTrue(form.getLoadStatus() == FormDescriptor.STATUS_XML_VALIDATED);
			String xmlerror = form.getXmlValidationErrorString();
			//assertTrue(xmlerror.length() > 0);
			logger.debug("Concatinated xml validation error: " + xmlerror);
			
		
		} catch (FormLoaderServiceException e) {
			fail("Got exception: " + e.toString());
		}
	}
	
	@Test
	public void testValidatexmlWithInvalidStatusXml() {
		//entirely wrong xml but with forms and form element	
		FormCollection aColl = new FormCollection();
		try {
			
			aColl.setXmlPathOnServer(".\\test\\data\\xmlvalidation");
			aColl.setXmlFileName("invalid-xml.xml");
			aColl = this.xmlValService.validateXml(aColl);
			List<FormDescriptor> forms = aColl.getForms();
			assertNotNull(forms);
			FormDescriptor form = forms.get(0);
			assertTrue(form.getLoadStatus() == FormDescriptor.STATUS_XML_VALIDATION_FAILED);
			String status = StatusFormatter.getStatusInXml(aColl);
			StatusFormatter.writeStatusToXml(status, ".\\test\\data\\xmlvalidation\\invalid-xml.status.xml");
			
		
		} catch (FormLoaderServiceException e) {
			String status = StatusFormatter.getStatusInXml(aColl);
			StatusFormatter.writeStatusToXml(status, ".\\test\\data\\xmlvalidation\\invalid-xml.status.xml");
		}
	}
	
	@Test
	public void testValidatexmlWithWrongXml() {
		//entirely wrong xml but with forms and form element	
		FormCollection aColl = new FormCollection();
		try {
			
			aColl.setXmlPathOnServer(".\\test\\data\\xmlvalidation");
			aColl.setXmlFileName("build.xml");
			aColl = this.xmlValService.validateXml(aColl);
			List<FormDescriptor> forms = aColl.getForms();
			assertNotNull(forms);
			assertTrue(forms.size() == 0);
			
			String status = StatusFormatter.getStatusInXml(aColl);
			StatusFormatter.writeStatusToXml(status, ".\\test\\data\\xmlvalidation\\build.xml.status.xml");
			
		
		} catch (FormLoaderServiceException e) {
			String status = StatusFormatter.getStatusInXml(aColl);
			StatusFormatter.writeStatusToXml(status, ".\\test\\data\\xmlvalidation\\invalid-xml.status.xml");
		}
	}
	
	@Test
	public void testValidatexmlWith5Forms() {
		//entirely wrong xml but with forms and form element	
		FormCollection aColl = new FormCollection();
		try {
			
			aColl.setXmlPathOnServer(".\\test\\data\\xmlvalidation");
			aColl.setXmlFileName("load_forms-5.xml");
			aColl = this.xmlValService.validateXml(aColl);
			List<FormDescriptor> forms = aColl.getForms();
			assertNotNull(forms);
			assertTrue(forms.size() == 5);
			
			String status = StatusFormatter.getStatusInXml(aColl);
			StatusFormatter.writeStatusToXml(status, ".\\test\\data\\xmlvalidation\\load_forms-5.xmlvalidationstatus.xml");
			
		
		} catch (FormLoaderServiceException e) {
			String status = StatusFormatter.getStatusInXml(aColl);
			StatusFormatter.writeStatusToXml(status, ".\\test\\data\\xmlvalidation\\invalid-xml.status.xml");
		}
	}
}
