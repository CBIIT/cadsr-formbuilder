package gov.nih.nci.cadsr.formloader.service.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import gov.nih.nci.cadsr.formloader.domain.FormCollection;
import gov.nih.nci.cadsr.formloader.domain.FormDescriptor;
import gov.nih.nci.cadsr.formloader.domain.QuestionDescriptor;
import gov.nih.nci.cadsr.formloader.service.common.FormLoaderServiceError;
import gov.nih.nci.cadsr.formloader.service.common.FormLoaderServiceException;
import gov.nih.nci.cadsr.formloader.service.common.StaXParser;
import gov.nih.nci.cadsr.formloader.service.common.StatusFormatter;
import gov.nih.nci.ncicb.cadsr.common.dto.ContactCommunicationV2TransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.DataElementTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.DefinitionTransferObjectExt;
import gov.nih.nci.ncicb.cadsr.common.dto.DesignationTransferObjectExt;
import gov.nih.nci.ncicb.cadsr.common.dto.ProtocolTransferObjectExt;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext.xml"})
public class ContentValidationServiceImplTest {
	
	private static Logger logger = Logger.getLogger(ContentValidationServiceImplTest.class.getName());

	@Autowired
	ContentValidationServiceImpl contentValidationService;
	@Autowired
	XmlValidationServiceImpl xmlValidator;
	
	FormCollection aColl;
	List<FormDescriptor> forms;
	
	
	@Before
	public void setUp() throws Exception {
		aColl = new FormCollection();
	}

	//https://tracker.nci.nih.gov/browse/FORMBUILD-18
	@Test
	public void testValidateXmlJR18() {
		
		try {
			aColl.setXmlPathOnServer(".\\test\\data\\xmlvalidation");
			aColl.setXmlFileName("JR18.xml");
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
			
		} catch (FormLoaderServiceException fle) {
			logger.debug(fle);
			fail("Got exception: " + fle.getMessage());
		}
	}
	
	@Test
	public void testValidateXmlContentNullCollection() {
		try {
			contentValidationService.validateXmlContent(null);
			fail("This should throw an excemption but didn't.");
		} catch (FormLoaderServiceException fe) {
			assertTrue(fe.getErrorCode() == FormLoaderServiceException.ERROR_COLLECTION_NULL);
		}
	}
	
	@Test
	public void testValidateXmlContentWith0Form() {
		try {
			aColl.setForms(new ArrayList<FormDescriptor>());
			contentValidationService.validateXmlContent(aColl);
			fail("This should throw an excemption but didn't.");
		} catch (FormLoaderServiceException fe) {
			assertTrue(fe.getErrorCode() == FormLoaderServiceException.ERROR_EMPTY_FORM_LIST);
		}
	}
	
	@Test
	public void testValidateXmlContentWithNullFormList() {
		try {
			contentValidationService.validateXmlContent(aColl);
			fail("This should throw an excemption but didn't.");
		} catch (FormLoaderServiceException fe) {
			assertTrue(fe.getErrorCode() == FormLoaderServiceException.ERROR_EMPTY_FORM_LIST);
		}
	}
	
	@Test
	public void testValidateXmlContentWithNullLoggedinUser() {
		try {
			FormCollection aColl = this.generateContentValidationData();
			aColl.setCreatedBy(null);
			contentValidationService.validateXmlContent(aColl);
			fail("This should throw an excemption but didn't.");
		} catch (FormLoaderServiceException fe) {
			assertTrue(fe.getErrorCode() == FormLoaderServiceException.ERROR_USER_INVALID);
		}
	}
	
	@Test
	public void testValidateXmlContentWithEmptyLoggedinUser() {
		try {
			FormCollection aColl = this.generateContentValidationData();
			aColl.setCreatedBy("");
			contentValidationService.validateXmlContent(aColl);
			fail("This should throw an excemption but didn't.");
		} catch (FormLoaderServiceException fe) {
			assertTrue(fe.getErrorCode() == FormLoaderServiceException.ERROR_USER_INVALID);
		}
	}
	
	@Test
	public void testValidateXmlContentent() {
		
		try {
			aColl.setXmlPathOnServer(".\\test\\data\\contentvalidation");
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
			assertTrue(forms.get(0).getLoadStatus() == FormDescriptor.STATUS_CONTENT_VALIDATED);
			
			FormDescriptor form = forms.get(0);
			assertTrue(form.getLoadType().equals(FormDescriptor.LOAD_TYPE_UPDATE_FORM));
			assertTrue(form.getFormSeqId() != null && form.getFormSeqId().length() > 0);
			
			//Question text in xml does not match. Should take from cde ref docs' docText of DCTL_Name "Preferred Question Text" 
			//Question public id: 3193451
			QuestionDescriptor question = form.getModules().get(0).getQuestions().get(0);
			logger.debug("Question [" + question.getPublicId() + "|" + question.getVersion() + 
					"] question text: " + question.getQuestionText());
			
			
			//1 line commented out for v4.1
			//assertTrue(!question.getQuestionText().startsWith("SY"));
			
			//Questiong's cde public id changes. Should get a message in instruction
			question = form.getModules().get(0).getQuestions().get(1);
			logger.debug("Question [" + question.getPublicId() + "|" + question.getVersion() + 
					"] has this instruction: " + question.getInstruction());
			
			//3193457 | 1.0 has 6 permissible values
			//valid valid #3 and #4 have fake values
			question = form.getModules().get(0).getQuestions().get(5);
			logger.debug("Question [" + question.getPublicId() + "|" + question.getVersion() + 
					"] has this instruction: " + question.getInstruction());
			
			//2 line commented out for v4.1
			//assertTrue(question.getValidValues().get(2).isSkip());
			//assertTrue(question.getValidValues().get(3).isSkip());
			
			String status = StatusFormatter.getStatusInXml(form);
			StatusFormatter.writeStatusToXml(status, ".\\test\\data\\contentvalidation\\3193449_has_valid_values_valstatus.xml");
			
		} catch (FormLoaderServiceException fle) {
			logger.debug(fle);
			fail("Got exception: " + fle.getMessage());
		}
	}
	
	@Test
	public void testValidateXmlContententNewVersion() {
		
		
		try {
			aColl.setXmlPathOnServer(".\\test\\data\\contentvalidation");
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
			
			//Data changed due to db data refresh
			assertTrue(forms.get(0).getLoadStatus() == FormDescriptor.STATUS_CONTENT_VALIDATION_FAILED);
			//assertTrue(forms.get(0).getLoadStatus() == FormDescriptor.STATUS_CONTENT_VALIDATED);
			
			FormDescriptor form = forms.get(0);
			assertTrue(form.getLoadType().equals(FormDescriptor.LOAD_TYPE_UNKNOWN));
			//assertTrue(form.getLoadType().equals(FormDescriptor.LOAD_TYPE_NEW_VERSION));
			//assertTrue(form.getFormSeqId() != null && form.getFormSeqId().length() > 0);
			
		
			
			String status = StatusFormatter.getStatusInXml(form);
			StatusFormatter.writeStatusToXml(status, ".\\test\\data\\contentvalidation\\new-version-3643954.status.xml");
			
		} catch (FormLoaderServiceException fle) {
			logger.debug(fle);
			fail("Got exception: " + fle.getMessage());
		}
	}
	
	@Test
	public void testValidateXmlContententRave() {
		
		
		try {
			aColl.setXmlPathOnServer(".\\test\\data\\contentvalidation");
			aColl.setXmlFileName("FourTheradexMedidataRaveFormsMinimumFields_01-11-2014.xml");
			aColl.setCreatedBy("yangs");
			aColl = xmlValidator.validateXml(aColl);
			
			forms = aColl.getForms();
			assertNotNull(forms);
			//assertTrue(forms.size() == 1);
			assertTrue(forms.get(0).getLoadStatus() == FormDescriptor.STATUS_XML_VALIDATED);
			
			for (FormDescriptor form : forms) {
				form.setSelected(true);
			}
			
			assertNotNull(contentValidationService);
			aColl = contentValidationService.validateXmlContent(aColl);
			
			assertNotNull(aColl);
			forms = aColl.getForms();
			//assertTrue(forms.size() == 1);
			assertTrue(forms.get(0).getLoadStatus() == FormDescriptor.STATUS_CONTENT_VALIDATED);
			
			FormDescriptor form = forms.get(0);
			//assertTrue(form.getLoadType().equals(FormDescriptor.LOAD_TYPE_NEW_VERSION));
			//assertTrue(form.getFormSeqId() != null && form.getFormSeqId().length() > 0);
			
			String status = StatusFormatter.getStatusInXml(form);
			StatusFormatter.writeStatusToXml(status, ".\\test\\data\\contentvalidation\\FourTheradexMedidataRaveForms.status.xml");
			
		} catch (FormLoaderServiceException fle) {
			logger.debug(fle);
			fail("Got exception: " + fle.getMessage());
		}
	}
	
	@Test
	public void testCheckQuestionValidValueFiedsWithTheredexFile() {
		
		
		try {
			aColl.setXmlPathOnServer(".\\test\\data\\contentvalidation");
			aColl.setXmlFileName("FourTheradexMedidataRaveFormsMinimumFields_01-11-2014.xml");
			aColl.setCreatedBy("yangs");
			aColl = xmlValidator.validateXml(aColl);
			
			forms = aColl.getForms();
			assertNotNull(forms);
			//assertTrue(forms.size() == 1);
			assertTrue(forms.get(0).getLoadStatus() == FormDescriptor.STATUS_XML_VALIDATED);
			
			aColl.resetAllSelectFlag(true);
			
			assertNotNull(contentValidationService);
			
			aColl = contentValidationService.validateXmlContent(aColl);
			
			assertNotNull(aColl);
			forms = aColl.getForms();
			assertTrue(forms.size() == 4);
			
			//Only 1st is validatated. The other 3 are not because of context "THEREDEX"
			//1st form: module 1 question 3 has valid values.
			
			assertTrue(FormDescriptor.STATUS_CONTENT_VALIDATION_FAILED == forms.get(1).getLoadStatus());
			
			FormDescriptor form = forms.get(0);
			assertTrue(form.getLoadStatus() == FormDescriptor.STATUS_CONTENT_VALIDATED);
			
			List<QuestionDescriptor> questions = form.getModules().get(0).getQuestions();
			QuestionDescriptor question = questions.get(2);
			List<QuestionDescriptor.ValidValue> vvs = question.getValidValues();
			
			assertTrue(vvs.get(0).getDescription() != null && vvs.get(0).getDescription().length() > 0);
			assertTrue(vvs.get(0).getDescription().startsWith("A person who belongs to the sex"));
			
			// ==========Setup fields to run the real target test ==============
			contentValidationService.checkQuestionValidValueFieds(null); //should not break
			
			vvs.get(0).setDescription(null);
			vvs.get(1).setDescription("");
			
			contentValidationService.checkQuestionValidValueFieds(question);
			assertTrue(question.getValidValues().get(0).getDescription().equals("Female"));
			assertTrue(question.getValidValues().get(1).getDescription().equals("Male"));
			
			vvs.get(0).setMeaningText(null);
			vvs.get(1).setMeaningText("");
			vvs.get(0).setSkip(false);
			vvs.get(1).setSkip(false);
			contentValidationService.checkQuestionValidValueFieds(question);
			assertTrue(question.getValidValues().get(0).isSkip());
			assertTrue(question.getValidValues().get(1).isSkip());
			
			vvs.get(0).setValue(null);
			vvs.get(1).setValue("");
			vvs.get(0).setSkip(false);
			vvs.get(1).setSkip(false);
			contentValidationService.checkQuestionValidValueFieds(question);
			assertTrue(question.getValidValues().get(0).isSkip());
			assertTrue(question.getValidValues().get(1).isSkip());
			
			question.setValidValues(new ArrayList<QuestionDescriptor.ValidValue>());
			contentValidationService.checkQuestionValidValueFieds(question); //should not break
			question.setValidValues(null);
			contentValidationService.checkQuestionValidValueFieds(question); //should not break
			
			//String status = StatusFormatter.getStatusInXml(form);
			//StatusFormatter.writeStatusToXml(status, ".\\test\\data\\contentvalidation\\FourTheradexMedidataRaveForms.status.xml");
			
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
		
		forms = aColl.getForms();
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
	}
	
	@Test
	public void testDetermineLoadTypeForFormNoVersion() {
		
		FormCollection coll = this.generateContentValidationData();
		List<FormDescriptor> forms = coll.getForms();
		
		FormDescriptor form = forms.get(2);
		form.setVersion(null);
		
		List<Float> existingVersions = new ArrayList<Float>();
		existingVersions.add(new Float(1.0));
		existingVersions.add(new Float(4.3));
		existingVersions.add(new Float(5.0));
		
		contentValidationService.determineLoadTypeForForm(form, existingVersions);
		assertTrue(FormDescriptor.LOAD_TYPE_UNKNOWN.equals(form.getLoadType()));
		
		form.setVersion("");
		contentValidationService.determineLoadTypeForForm(form, existingVersions);
		assertTrue(FormDescriptor.LOAD_TYPE_UNKNOWN.equals(form.getLoadType()));
	}
	
	@Test
	public void testDetermineLoadTypeForFormVersionMatched() {
		
		FormCollection coll = this.generateContentValidationData();
		List<FormDescriptor> forms = coll.getForms();
		
		FormDescriptor form = forms.get(2);
		form.setVersion("5.0");
		
		List<Float> existingVersions = new ArrayList<Float>();
		existingVersions.add(new Float(1.0));
		existingVersions.add(new Float(4.3));
		existingVersions.add(new Float(5.0));
		
		contentValidationService.determineLoadTypeForForm(form, existingVersions);
		assertTrue(FormDescriptor.LOAD_TYPE_UPDATE_FORM.equals(form.getLoadType()));
	}
	
	@Test
	public void testDetermineLoadTypeForFormVersionNotMatched() {
		
		FormCollection coll = this.generateContentValidationData();
		List<FormDescriptor> forms = coll.getForms();
		
		FormDescriptor form = forms.get(2);
		form.setVersion("2.0");
		
		List<Float> existingVersions = new ArrayList<Float>();
		existingVersions.add(new Float(1.0));
		existingVersions.add(new Float(4.3));
		existingVersions.add(new Float(5.0));
		
		contentValidationService.determineLoadTypeForForm(form, existingVersions);
		assertTrue(FormDescriptor.LOAD_TYPE_NEW_VERSION.equals(form.getLoadType()));
		
		form.setVersion("7.0");
		form.setLoadType(FormDescriptor.LOAD_TYPE_UNKNOWN);
		contentValidationService.determineLoadTypeForForm(form, existingVersions);
		assertTrue(FormDescriptor.LOAD_TYPE_NEW_VERSION.equals(form.getLoadType()));
	}
	
	@Test
	public void testVerifyFormTypeNullForm() {
		contentValidationService.verifyFormType(null);
	}
	
	@Test
	public void testVerifyFormTypeNullFormType() {
		
		aColl = this.generateContentValidationData();
		FormDescriptor form = aColl.getForms().get(0);
		form.setType(null);
		contentValidationService.verifyFormType(form);
		assertTrue("CRF".equals(form.getType()));
	}
	
	@Test
	public void testVerifyFormTypeEmptyFormType() {
		
		aColl = this.generateContentValidationData();
		FormDescriptor form = aColl.getForms().get(0);
		form.setType("");
		contentValidationService.verifyFormType(form);
		assertTrue("CRF".equals(form.getType()));
	}
	
	@Test
	public void testVerifyFormTypeInvalidFormType() {
		
		aColl = this.generateContentValidationData();
		FormDescriptor form = aColl.getForms().get(0);
		form.setType("1234");
		contentValidationService.verifyFormType(form);
		assertTrue("CRF".equals(form.getType()));
	}
	
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
	
	@Test
	public void testVerifyCredential() {
		FormCollection coll = this.generateContentValidationData();
		List<FormDescriptor> forms = coll.getForms();
		
		FormDescriptor form = forms.get(0);
		form.setCreatedBy("ShanYang");
		form.setContext("TEST");
		
		this.contentValidationService.verifyCredential(form, "FORMLOADER");
		
		assertTrue("FORMLOADER".equals(form.getCreatedBy()));
		assertTrue("FORMLOADER".equals(form.getModifiedBy()));
		assertTrue(form.getChangeNote().contains("ShanYang"));
	}
	
	@Test
	public void testAbleToValidateByAlternatives() {
		// VM seqid: A4499CFB-7A3A-DD4D-E040-BB89AD4307BF     doesn't have definition  nor designation  
				//     68A1E96E-6F15-619E-E040-BB89AD437847     has definiation but no designation
		
		String meaningLongName = "Not public; kept secret or restricted.: A mechanism for guarding against financial aspects of risk by making payments in the form of premiums to an insurance company, which pays an agreed-upon sum to the insured in the event of loss.";
		boolean validated = this.contentValidationService.ableToValidateByAlternatives(meaningLongName, "A4499CFB-7A3A-DD4D-E040-BB89AD4307BF");
		assertFalse(validated);
		
		
		validated = this.contentValidationService.ableToValidateByAlternatives(meaningLongName, "68A1E96E-6F15-619E-E040-BB89AD437847");
		assertTrue(validated);
	}
	
	@Test
	public void testVerifyPublicIdAndVersion() {
		String mes = contentValidationService.verifyPublicIdAndVersion(null, "");
		assertTrue(mes.contains("public"));
		
		mes = contentValidationService.verifyPublicIdAndVersion("", "");
		assertTrue(mes.contains("public"));
		
		mes = contentValidationService.verifyPublicIdAndVersion("232424", null);
		assertTrue(mes.contains("version"));
		
		mes = contentValidationService.verifyPublicIdAndVersion("232424", "");
		assertTrue(mes.contains("version"));
		
		mes = contentValidationService.verifyPublicIdAndVersion("", null);
		assertTrue(mes.contains("public") && mes.contains("version"));
		
		mes = contentValidationService.verifyPublicIdAndVersion("--232424", "");
		assertTrue(mes.contains("public"));
		
		mes = contentValidationService.verifyPublicIdAndVersion("--232424", "2.4fafa");
		assertTrue(mes.contains("public") && mes.contains("version"));
	}
	
	@Test
	public void testQuickCheckOnCollectionNull() {
		try {
			contentValidationService.quickCheckOnCollection(null);
			fail("Null collection objc should have triggered an exception");
		} catch (FormLoaderServiceException fse) {
			assertTrue(FormLoaderServiceException.ERROR_COLLECTION_NULL == fse.getErrorCode());
		}
	}
	
	@Test
	public void testQuickCheckOnCollectionNullForms() {
		FormCollection aColl = this.generateContentValidationData();
		try {
			aColl.setForms(null);
			contentValidationService.quickCheckOnCollection(aColl);
			fail("Null form list should have triggered an exception");
		} catch (FormLoaderServiceException fse) {
			assertTrue(FormLoaderServiceException.ERROR_EMPTY_FORM_LIST == fse.getErrorCode());
		}
	}
	
	@Test
	public void testQuickCheckOnCollectionEmptyForms() {
		FormCollection aColl = this.generateContentValidationData();
		try {
			aColl.setForms(new ArrayList<FormDescriptor>());
			contentValidationService.quickCheckOnCollection(aColl);
			fail("Empty form list should have triggered an exception");
		} catch (FormLoaderServiceException fse) {
			assertTrue(FormLoaderServiceException.ERROR_EMPTY_FORM_LIST == fse.getErrorCode());
		}
	}
	
	@Test
	public void testQuickCheckOnCollectionNullCreatedBy() {
		FormCollection aColl = this.generateContentValidationData();
		aColl.setCreatedBy(null);
		try {
			contentValidationService.quickCheckOnCollection(aColl);
			fail("Null createdBy should have triggered an exception");
		} catch (FormLoaderServiceException fse) {
			assertTrue(FormLoaderServiceException.ERROR_USER_INVALID == fse.getErrorCode());
		}
	}
	
	@Test
	public void testQuickCheckOnCollectionEmptyCreatedBy() {
		FormCollection aColl = this.generateContentValidationData();
		aColl.setCreatedBy("");
		try {
			contentValidationService.quickCheckOnCollection(aColl);
			fail("Null createdBy should have triggered an exception");
		} catch (FormLoaderServiceException fse) {
			assertTrue(FormLoaderServiceException.ERROR_USER_INVALID == fse.getErrorCode());
		}
	}
	
	@Test
	public void testIsAssociatedVdValid() {
		try {
			aColl.setXmlPathOnServer(".\\test\\data\\contentvalidation");
			aColl.setXmlFileName("3193449_has_valid_values.xml");
			aColl.setCreatedBy("YANGS");
			aColl = xmlValidator.validateXml(aColl);

			aColl = contentValidationService.validateXmlContent(aColl);
			forms = aColl.getForms();
			assertNotNull(forms);

			QuestionDescriptor question = forms.get(0).getModules().get(0).getQuestions().get(0);
			assertNotNull(question);
			
			DataElementTransferObject cde = new DataElementTransferObject();
			assertFalse(contentValidationService.isAssociatedVdValid(question, cde));
			
			cde.setVdIdseq("B3A8191F-BCD8-233F-E034-0003BA12F5E7");
			assertTrue(contentValidationService.isAssociatedVdValid(question, cde));
			
			question.setDatatypeName("CAHR");
			assertFalse(contentValidationService.isAssociatedVdValid(question, cde));
			
			//restore match
			question.setDatatypeName("CHARACTER");
			
			question.setUOMName("Meter");
			assertFalse(contentValidationService.isAssociatedVdValid(question, cde));
			//restore match
			question.setUOMName(null);
			
			question.setCdeVdVersion("2.0");
			assertFalse(contentValidationService.isAssociatedVdValid(question, cde));
			
			question.setCdeVdVersion(null);
			assertFalse(contentValidationService.isAssociatedVdValid(question, cde));
		} catch (FormLoaderServiceException e) {

		}
	}
	
	@Test
	public void testValidateXmlContententWithInvalidVd() {
		
		try {
			aColl.setXmlPathOnServer(".\\test\\data\\contentvalidation");
			aColl.setXmlFileName("RaveFormsMinimumFields_with_invalidVD.xml");
			aColl.setCreatedBy("YANGS");
			aColl = xmlValidator.validateXml(aColl);
			
			forms = aColl.getForms();
			assertNotNull(forms);
			assertTrue(forms.size() == 4);
			assertTrue(forms.get(0).getLoadStatus() == FormDescriptor.STATUS_XML_VALIDATED);
			
			for (FormDescriptor form : forms) {
				form.setSelected(true);
			}
			
			assertNotNull(contentValidationService);
			aColl = contentValidationService.validateXmlContent(aColl);
			
			assertNotNull(aColl);
			forms = aColl.getForms();
			assertTrue(forms.size() == 4);
			assertTrue(forms.get(0).getLoadStatus() == FormDescriptor.STATUS_CONTENT_VALIDATED);
			
			FormDescriptor form = forms.get(3);			
			//VD for this question has: <valueDomain>
            // <datatypeName>CHARACTE</datatypeName>    ===Missing a letter at the end
			//</valueDomain>
			QuestionDescriptor question = form.getModules().get(0).getQuestions().get(1);
			assertNotNull(question);
			assertTrue(question.getCdeSeqId().length() == 0);
			
		} catch (FormLoaderServiceException fle) {
			logger.debug(fle);
			fail("Got exception: " + fle.getMessage());
		}
	}
	
	
	public void testVerifyProtocols() {
		try {
			aColl.setXmlPathOnServer(".\\test\\data\\contentvalidation");
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
			assertTrue(forms.get(0).getLoadStatus() == FormDescriptor.STATUS_CONTENT_VALIDATED);
			
			FormDescriptor form = forms.get(0);			
			List<ProtocolTransferObjectExt> protos = form.getProtocols();
			
			assertTrue(protos != null && protos.size() == 1);
			assertTrue(protos.get(0).getProtoIdseq() != null && protos.get(0).getProtoIdseq().length() > 0);
			
		} catch (FormLoaderServiceException fle) {
			logger.debug(fle);
			fail("Got exception: " + fle.getMessage());
		}
	}
	
	@Test
	public void testVerifyDesignations() {
		try {
			aColl.setXmlPathOnServer(".\\test\\data\\contentvalidation");
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
			assertTrue(forms.get(0).getLoadStatus() == FormDescriptor.STATUS_CONTENT_VALIDATED);
			
			FormDescriptor form = forms.get(0);				
			List<DesignationTransferObjectExt> designs = form.getDesignations();
			
			assertTrue(designs != null && designs.size() == 1);
			
		} catch (FormLoaderServiceException fle) {
			logger.debug(fle);
			fail("Got exception: " + fle.getMessage());
		}
	}
	
	@Test
	public void testVerifyDefinition() {
		try {
			aColl.setXmlPathOnServer(".\\test\\data\\contentvalidation");
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
			assertTrue(forms.get(0).getLoadStatus() == FormDescriptor.STATUS_CONTENT_VALIDATED);
			
			FormDescriptor form = forms.get(0);				
			List<DefinitionTransferObjectExt> defs = form.getDefinitions();
			
			assertTrue(defs == null || defs.size() == 0);
			
			List<String> messages = form.getMessages();
			boolean hasTheMsg = false;
			for (String msg : messages) {
				if (msg.contains("invalid ClassificationScheme or ClassificationSchemeItem")) {
					hasTheMsg = true;
					break;
				}
			}
			
			assertTrue(hasTheMsg);
			
		} catch (FormLoaderServiceException fle) {
			logger.debug(fle);
			fail("Got exception: " + fle.getMessage());
		}
	}
	
	@Test
	public void testVerifyContactCommnunication() {
		try {
			aColl.setXmlPathOnServer(".\\test\\data\\contentvalidation");
			aColl.setXmlFileName("3256357_v1_0_newform-contactCommunication.xml");
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
			assertTrue(forms.get(0).getLoadStatus() == FormDescriptor.STATUS_CONTENT_VALIDATED);
			
			FormDescriptor form = forms.get(0);				
			List<ContactCommunicationV2TransferObject> contacts = form.getContactCommnunications();
			
			assertTrue(contacts.size() == 1);
			assertTrue(contacts.get(0).getType().equals("EMAIL"));
			assertTrue(contacts.get(0).getValue().contains("joh.doe"));
			
		} catch (FormLoaderServiceException fle) {
			logger.debug(fle);
			fail("Got exception: " + fle.getMessage());
		}
	}
}
