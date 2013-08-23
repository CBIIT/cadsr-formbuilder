package gov.nih.nci.cadsr.formloader.service.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import gov.nih.nci.cadsr.formloader.domain.FormCollection;
import gov.nih.nci.cadsr.formloader.domain.FormDescriptor;
import gov.nih.nci.cadsr.formloader.domain.QuestionDescriptor;
import gov.nih.nci.cadsr.formloader.service.ContentValidationService;
import gov.nih.nci.cadsr.formloader.service.XmlValidationService;
import gov.nih.nci.cadsr.formloader.service.common.FormLoaderServiceException;
import gov.nih.nci.cadsr.formloader.service.common.StatusFormatter;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext-service-test-db.xml"})
public class LoadingServiceImplTest {
	
	@Autowired
	ContentValidationService contentValidationService;
	@Autowired
	XmlValidationService xmlValidator;
	@Autowired
	LoadingServiceImpl loadService;
	
	FormCollection aColl;
	
	@Before
	public void setUp() {
		//prepareCollectionToLoad();
	}

	@Test
	public void testLoadBadParams() {
		this.prepareCollectionToLoad(".\\.\\test\\data", "3256357_v1_0_newform.xml");
		try {		
			aColl.setCreatedBy("");
			aColl = this.loadService.loadForms(aColl);
			fail("Should throw exception because longedin user is not valid");
			
			aColl.setCreatedBy("jjuser");
			aColl.setXmlFileName("");
			aColl = this.loadService.loadForms(aColl);
			fail("Should throw exception because xml file name is not valid");
			
			aColl.setXmlFileName("dfaf"); //only checking valid string
			aColl.setForms(null);
			aColl = this.loadService.loadForms(aColl);
			fail("Should throw exception because form list is null");
		} catch (FormLoaderServiceException fle) {
			//this is good
		}
	}
	
	@Test
	public void testValidContextName() {
		this.prepareCollectionToLoad(".\\.\\test\\data", "3256357_v1_0_newform.xml");
		FormDescriptor form = aColl.getForms().get(0);
		boolean valid = this.loadService.validContextName(form);
		assertTrue(valid);
		
		form.setContext("");
		valid = this.loadService.validContextName(form);
		assertTrue(valid);
		assertTrue(form.getContext().equals("NCIP"));
		
		form.setContext("BOGUS");
		valid = this.loadService.validContextName(form);
		assertFalse(valid);
		
	}
	
	@Test
public void testUserHasRight() {
		this.prepareCollectionToLoad(".\\.\\test\\data", "3256357_v1_0_newform.xml");
		FormDescriptor form = aColl.getForms().get(0);
		boolean hasRight = this.loadService.userHasRight(form, "");
		assertTrue(hasRight); //form's createdBy is DWARZEL context is TEST
		
		form.setCreatedBy("dafa");
		hasRight = this.loadService.userHasRight(form, "");
		assertFalse(hasRight);
		
		hasRight = this.loadService.userHasRight(form, "FORMBUILDER");
		assertTrue(hasRight);
	}
	
	@Test
	public void testLoadNewForm() {
		this.prepareCollectionToLoad(".\\.\\test\\data", "3256357_v1_0_newform-partial.xml");
		try {
			FormDescriptor form = aColl.getForms().get(0);
			form.setSelected(true);
			aColl = this.loadService.loadForms(aColl);
			form = aColl.getForms().get(0);
			String status = StatusFormatter.getStatusInXml(form);
			StatusFormatter.writeStatusToXml(status, ".\\test\\data\\LoadService-creatednew.xml");
		} catch (FormLoaderServiceException fle) {
			fail("Got exception: " + fle.getMessage());
		}
	}
	
	@Test
	public void testLoadBiggerNewForm() {
		this.prepareCollectionToLoad(".\\.\\test\\data", "3256357_v1_0_newform.xml");
		try {
			FormDescriptor form = aColl.getForms().get(0);
			form.setSelected(true);
			aColl = this.loadService.loadForms(aColl);
			form = aColl.getForms().get(0);
			String status = StatusFormatter.getStatusInXml(aColl);
			StatusFormatter.writeStatusToXml(status, ".\\test\\data\\LoadService-creatednew-bigger.xml");
		} catch (FormLoaderServiceException fle) {
			fail("Got exception: " + fle.getMessage());
		}
	}

	protected void prepareCollectionToLoad(String filepath, String testfile) {
		assertNotNull(loadService);
		
		try {
			FormCollection aColl = new FormCollection();
			aColl.setXmlPathOnServer(filepath);
			aColl.setXmlFileName(testfile);
			aColl = xmlValidator.validateXml(aColl);
			List<FormDescriptor> forms = aColl.getForms();
			assertNotNull(forms);
			assertTrue(forms.size() == 1);
			
			FormDescriptor form = forms.get(0);
			//String status = StatusFormatter.getStatusInXml(form);
			//StatusFormatter.writeStatusToXml(status, filepath + "\\LoadServiceTest-xmlVal.xml");
			
			assertTrue(forms.get(0).getLoadStatus() == FormDescriptor.STATUS_XML_VALIDATED);
			
			aColl = new FormCollection();
			aColl.setForms(forms);
			aColl.setName("Testing Create New Form");
			aColl.setCreatedBy("FORMBUILDER");
			aColl.setXmlFileName(testfile);
			aColl.setXmlPathOnServer(filepath);
			
			assertNotNull(contentValidationService);
			aColl = contentValidationService.validateXmlContent(aColl);
			
			assertNotNull(aColl);
			forms = aColl.getForms();
			assertTrue(forms.size() == 1);
			assertTrue(forms.get(0).getLoadStatus() == FormDescriptor.STATUS_DB_VALIDATED);
			
			form = forms.get(0);
			
			//List<QuestionDescriptor> questions = forms.get(0).getModules().get(0).getQuestions();
			//assertTrue(questions.size() == 2);
			//assertTrue(questions.get(1).getPublicId() != null);
			//assertTrue(questions.get(1).getCdePublicId().equals("2341940"));

			String status = StatusFormatter.getStatusInXml(aColl);
			StatusFormatter.writeStatusToXml(status, filepath + "\\LoadService-collection.xml");
		} catch (FormLoaderServiceException fle) {
			fail("Got exception: " + fle.getMessage());
		}
	}
}
