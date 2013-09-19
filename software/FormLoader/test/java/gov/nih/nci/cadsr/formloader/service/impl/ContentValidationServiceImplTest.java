package gov.nih.nci.cadsr.formloader.service.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import gov.nih.nci.cadsr.formloader.domain.FormCollection;
import gov.nih.nci.cadsr.formloader.domain.FormDescriptor;
import gov.nih.nci.cadsr.formloader.domain.FormStatus;
import gov.nih.nci.cadsr.formloader.domain.QuestionDescriptor;
import gov.nih.nci.cadsr.formloader.service.ContentValidationService;
import gov.nih.nci.cadsr.formloader.service.common.FormLoaderServiceError;
import gov.nih.nci.cadsr.formloader.service.common.FormLoaderServiceException;
import gov.nih.nci.cadsr.formloader.service.common.StatusFormatter;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext-service-test-db.xml"})
public class ContentValidationServiceImplTest {
	
	private static Logger logger = Logger.getLogger(ContentValidationServiceImplTest.class.getName());

	@Autowired
	ContentValidationServiceImpl contentValidationService;
	@Autowired
	XmlValidationServiceImpl xmlValidator;
	
	FormCollection aColl = new FormCollection();
	List<FormDescriptor> forms;
	
	@Before
	public void setUp() throws Exception {
		aColl.setName("TestCollection");
	}
	
	@Test
	public void testValidateXmlContentent() {
		String xmlPathName = ".\\test\\data\\contentvalidation\\3193449_has_valid_values.xml";
		
		try {
			FormCollection aColl = new FormCollection();
			aColl.setXmlPathOnServer(".\\test\\data");
			aColl.setXmlFileName("3193449_has_valid_values.xml");
			aColl.setCreatedBy("YANGS");
			aColl = xmlValidator.validateXml(aColl);
			forms = aColl.getForms();
			assertNotNull(forms);
			assertTrue(forms.size() == 1);
			assertTrue(forms.get(0).getLoadStatus() == FormDescriptor.STATUS_XML_VALIDATED);
			
			for (FormDescriptor form : forms) {
				form.setSelected(true);
			}
			
			assertNotNull(contentValidationService);
			aColl = contentValidationService.validateXmlContent(aColl);
			
			assertNotNull(aColl);
			forms = aColl.getForms();
			assertTrue(forms.size() == 1);
			assertTrue(forms.get(0).getLoadStatus() == FormDescriptor.STATUS_DB_VALIDATED);
			
			FormDescriptor form = forms.get(0);
			assertTrue(form.getLoadType().equals(FormDescriptor.LOAD_TYPE_UPDATE_FORM));
			assertTrue(form.getFormSeqId() != null && form.getFormSeqId().length() > 0);
			
			//Question text in xml does not match. Should take from cde ref docs' docText of DCTL_Name "Preferred Question Text" 
			//Question public id: 3193451
			QuestionDescriptor question = form.getModules().get(0).getQuestions().get(0);
			logger.debug("Question [" + question.getPublicId() + "|" + question.getVersion() + 
					"] question text: " + question.getQuestionText());
			assertTrue(!question.getQuestionText().startsWith("SY"));
			
			//Questiong's cde public id changes. Should get a message in instruction
			question = form.getModules().get(0).getQuestions().get(1);
			logger.debug("Question [" + question.getPublicId() + "|" + question.getVersion() + 
					"] has this instruction: " + question.getInstruction());
			
			//3193457 | 1.0 has 6 permissible values
			//valid valid #3 and #4 have fake values
			question = form.getModules().get(0).getQuestions().get(5);
			logger.debug("Question [" + question.getPublicId() + "|" + question.getVersion() + 
					"] has this instruction: " + question.getInstruction());
			assertTrue(question.getValidValues().get(2).isSkip());
			assertTrue(question.getValidValues().get(3).isSkip());
			
			String status = StatusFormatter.getStatusInXml(form);
			StatusFormatter.writeStatusToXml(status, ".\\test\\data\\contentvalidation\\3193449_has_valid_values_valstatus.xml");
			
		} catch (FormLoaderServiceException fle) {
			logger.debug(fle);
			fail("Got exception: " + fle.getMessage());
		}
	}
	
	@Test
	public void testValidateXmlContententNewVersion() {
		//String xmlPathName = ".\\test\\data\\3193449_has_valid_values.xml";
		
		try {
			FormCollection aColl = new FormCollection();
			aColl.setXmlPathOnServer(".\\test\\data\\contentvalidation");
			//aColl.setXmlFileName("3256357_v1_0_newform-partial-newversion.xml");
			aColl.setXmlFileName("new-version-3643954.xml");
			aColl.setCreatedBy("yangs");
			aColl = xmlValidator.validateXml(aColl);
			
			forms = aColl.getForms();
			assertNotNull(forms);
			assertTrue(forms.size() == 1);
			assertTrue(forms.get(0).getLoadStatus() == FormDescriptor.STATUS_XML_VALIDATED);
			
			for (FormDescriptor form : forms) {
				form.setSelected(true);
			}
			
			assertNotNull(contentValidationService);
			aColl = contentValidationService.validateXmlContent(aColl);
			
			assertNotNull(aColl);
			forms = aColl.getForms();
			assertTrue(forms.size() == 1);
			assertTrue(forms.get(0).getLoadStatus() == FormDescriptor.STATUS_DB_VALIDATED);
			
			FormDescriptor form = forms.get(0);
			assertTrue(form.getLoadType().equals(FormDescriptor.LOAD_TYPE_NEW_VERSION));
			assertTrue(form.getFormSeqId() != null && form.getFormSeqId().length() > 0);
			
		
			
			String status = StatusFormatter.getStatusInXml(form);
			StatusFormatter.writeStatusToXml(status, ".\\test\\data\\contentvalidation\\new-version-3643954.status.xml");
			
		} catch (FormLoaderServiceException fle) {
			logger.debug(fle);
			fail("Got exception: " + fle.getMessage());
		}
	}
	
	@Test
	public void testValidContextName() {
		this.prepareCollectionForValidation(".\\.\\test\\data\\contentvalidation", "3256357_v1_0_newform.xml");
		FormDescriptor form = aColl.getForms().get(0);
		boolean valid = this.contentValidationService.validContextName(form);
		assertTrue(valid);
		
		form.setContext("");
		valid = this.contentValidationService.validContextName(form);
		assertTrue(valid);
		assertTrue(form.getContext().equals("NCIP"));
		
		form.setContext("BOGUS");
		valid = this.contentValidationService.validContextName(form);
		assertFalse(valid);
		
	}
	
	@Test
	public void testValidateContentInvalidPublicid() {
		try {
		this.prepareCollectionForValidation(".\\.\\test\\data\\contentvalidation", "invalid-publicid.xml");
		FormDescriptor form = aColl.getForms().get(0);
		form.setSelected(true);
		aColl = this.contentValidationService.validateXmlContent(aColl);
		assertTrue(aColl.getForms().get(0).getLoadStatus() == FormDescriptor.STATUS_CONTENT_VALIDATION_FAILED);
		
		String status = StatusFormatter.getStatusInXml(form);
		StatusFormatter.writeStatusToXml(status, ".\\test\\data\\contentvalidation\\invalid-publicid.status.xml");
		} catch (FormLoaderServiceException e) {
			fail("Got FormLoaderServiceException: " + e.getMessage());
		}
	}

	
	@Test
	public void testDetermineLoadTypeByVersionWithMockData() {
		//1234345, (float)1.0
		//1234346, (float)3.0
		//1234347, (float)4.0
		//1234348, (float)2.0
		//0, (float)1.0
		//1234349, (float)1.0
		FormCollection aColl = generateContentValidationData();
		
		List<FormDescriptor> forms = aColl.getForms();
		HashMap<String, List<Float>> versions = new HashMap<String, List<Float>>();
		List<Float> vers = new ArrayList<Float>();
		vers.add(Float.parseFloat("4.0"));
		versions.put("1234345", vers); 

		vers = new ArrayList<Float>();
		vers.add(Float.parseFloat("1.0"));
		vers.add(Float.parseFloat("2.0"));
		vers.add(Float.parseFloat("3.0"));
		versions.put("1234346", vers);
		
		vers = new ArrayList<Float>();
		vers.add(Float.parseFloat("1.0"));
		vers.add(Float.parseFloat("2.0"));
		vers.add(Float.parseFloat("3.0"));
		versions.put("1234347", vers);
		
		((ContentValidationServiceImpl)contentValidationService).determineLoadTypeByVersion(forms, versions);
		
		
		assertTrue(FormDescriptor.LOAD_TYPE_NEW_VERSION.equals(forms.get(0).getLoadType()));
		assertTrue(FormDescriptor.LOAD_TYPE_UPDATE_FORM.equals(forms.get(1).getLoadType()));
		assertTrue(FormDescriptor.LOAD_TYPE_NEW_VERSION.equals(forms.get(2).getLoadType()));
		assertTrue(FormDescriptor.LOAD_TYPE_UNKNOWN.equals(forms.get(3).getLoadType()));
		assertTrue(FormDescriptor.LOAD_TYPE_UNKNOWN.equals(forms.get(4).getLoadType()));
		
		/*
		Denise:
			if public ID exists but no version element in the form, skip loading.
			If public ID is null, and no version, default to version 1.0
			If public ID does not exist, skip loading.
			If public id exist and version does not exist, create new version
			If public id exists and version exists, update existing version
	*/
	}
	
	//@Test
	/*
	public void testDetermineLoadTypeOnVersions() {
		
		//Setup test
		List<FormHeader> formHeaders = new ArrayList<FormHeader>();
		HashMap<String, String> existingVersions = new HashMap<String, String>();
		
		int id = 2321849;
		for (int i = 0; i < 5; i++) {
			id++;
			String vers = (i % 2 == 0) ? "2.0" : "3.0";
			FormHeader form = new FormHeader("", "" + id, vers);
			formHeaders.add(form);
		}
		
		formHeaders.add(new FormHeader("", "", null));
		
		existingVersions.put("2321850", "1.0,2.0");
		existingVersions.put("2321851", "1.0,2.0");
		existingVersions.put("2321853", null);
		existingVersions.put("2321854", "2.0");
		
		contentValidationService.determineLoadTypeOnVersions(formHeaders, existingVersions);
		
		assertTrue(formHeaders.get(0).getLoadType().equals(FormHeader.LOAD_TYPE_UPDATE_FORM));
		
		assertTrue(formHeaders.get(1).getLoadType().equals(FormHeader.LOAD_TYPE_NEW_VERSION));
		assertTrue(formHeaders.get(2).getLoadType().equals(FormHeader.LOAD_TYPE_NEW));
		assertTrue(formHeaders.get(3).getLoadType().equals(FormHeader.LOAD_TYPE_NEW_VERSION));
		assertTrue(formHeaders.get(4).getLoadType().equals(FormHeader.LOAD_TYPE_UPDATE_FORM));
		assertTrue(formHeaders.get(5).getLoadType().equals(FormHeader.LOAD_TYPE_NEW));
	
	}
	*/
	
	protected void prepareCollectionForValidation (String filepath, String testfile) {
		assertNotNull(contentValidationService);
		
		try {
			aColl = new FormCollection();
			aColl.setXmlPathOnServer(filepath);
			aColl.setXmlFileName(testfile);
			aColl.setCreatedBy("YANGS");
			aColl = xmlValidator.validateXml(aColl);
			
			
			List<FormDescriptor> forms = aColl.getForms();
			assertNotNull(forms);
			assertTrue(forms.size() == 1);
			
			assertTrue(forms.get(0).getLoadStatus() == FormDescriptor.STATUS_XML_VALIDATED);
	
		} catch (FormLoaderServiceException fle) {
			fail("Got exception: " + fle.getMessage());
		}
	}
	
	public FormCollection generateContentValidationData() {
		FormCollection aColl = new FormCollection();
		List<FormLoaderServiceError> errors = new ArrayList<FormLoaderServiceError>();
		  
		  aColl.setCreatedBy("Denise");
		  aColl.setDateCreated(new Date());
		  aColl.setDescription("This is the collection from nci");
		  aColl.setId("1234567");
		  aColl.setName("Denise's Collection");
		  aColl.setXmlFileName("denise-coll.xml");
		  aColl.setXmlPathOnServer("/local/content/formloader/20130703");
		  
		  //1
		  List<FormDescriptor> forms = new ArrayList<FormDescriptor>();
		  FormDescriptor form = new FormDescriptor("443355", "1234345", "1.0"); //p.id invalid
		  form.setContext("CTRP");
		  //FormStatus status = new FormStatus(FormStatus.STATUS_DB_VALIDATED);
		 // List<String> msgs = new ArrayList<String>();
		  form.addMessage("Question 1 has no default text");
		  form.addMessage("Question 2 need work");
		  form.setLoadStatus(FormDescriptor.STATUS_XML_VALIDATED);
		  form.setSelected(true);
		  forms.add(form);
		  
		  //2
		  form = new FormDescriptor("553355", "1234346", "3.0"); //p.id invalid
		  form.setContext("NCIP");
		 form.addMessage("No error / success");
		 form.setLoadStatus(FormDescriptor.STATUS_XML_VALIDATED);
		 form.setSelected(true);
		  forms.add(form);
		  
		  //3
		  form = new FormDescriptor("663355", "1234347", "4.0");
		  form.setContext("NCIP");
		  form.setLoadType(FormDescriptor.LOAD_TYPE_UPDATE_FORM);
		  List<String> msgs3 = new ArrayList<String>();
		  form.addMessage("Question 1 has no default text");
		  form.addMessage("Question 2 need work");
		  form.setLoadStatus(FormDescriptor.STATUS_XML_VALIDATED);
		  form.setSelected(true);
		  forms.add(form);
		  
		  //4
		  form = new FormDescriptor("773355", "1234348", "2.0");
		  form.setWorkflowStatusName("RELEASED");
		  form.setContext("NCIP");
		  form.setLoadType(FormDescriptor.LOAD_TYPE_UPDATE_FORM);
		  form.addMessage("Question 3 has no default text");
		  form.addMessage("Question 4 need work");
		  form.setLoadStatus(FormDescriptor.STATUS_XML_VALIDATED);
		  form.setSelected(true);
		  forms.add(form);
		  
		  //5
		 //public id = 0
		  form = new FormDescriptor("883355", "0", "1.0");
		  form.setLoadStatus(FormDescriptor.STATUS_XML_VALIDATED);
		  form.setSelected(true);
		  forms.add(form);
		  
		  //6
		  forms.add(new FormDescriptor("883355", "1234349", "1.0"));
		  
		  aColl.setForms(forms);
		  
		  return aColl;
	}
}
