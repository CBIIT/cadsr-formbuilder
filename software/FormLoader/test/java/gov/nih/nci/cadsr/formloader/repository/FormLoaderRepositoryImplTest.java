package gov.nih.nci.cadsr.formloader.repository;

import static org.junit.Assert.*;
import gov.nih.nci.cadsr.formloader.domain.FormCollection;
import gov.nih.nci.cadsr.formloader.domain.FormDescriptor;
import gov.nih.nci.cadsr.formloader.domain.QuestionDescriptor;
import gov.nih.nci.cadsr.formloader.service.ContentValidationService;
import gov.nih.nci.cadsr.formloader.service.XmlValidationService;
import gov.nih.nci.cadsr.formloader.service.common.FormLoaderServiceException;
import gov.nih.nci.cadsr.formloader.service.common.StatusFormatter;
import gov.nih.nci.cadsr.formloader.service.impl.LoadingServiceImpl;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext-service-test-db.xml"})
public class FormLoaderRepositoryImplTest {
	
	@Autowired
	FormLoaderRepository repository;
	@Autowired
	ContentValidationService contentValidationService;
	@Autowired
	XmlValidationService xmlValidator;
	@Autowired
	LoadingServiceImpl loadService;
	
	
	FormCollection aColl;
	
	String testfile = "3256357_v1_0_newform-partial.xml";
	//String testfile = "3256357_v1_0_multi-protocols-newform.xml";
	String filepath = ".\\test\\data";


	@Before
	public void setUp() throws Exception {
		prepareCollectionToLoad();
	}

	@Test
	public void testCreateForm() {
		
		assertNotNull(aColl);
		assertNotNull(repository);
		
		FormDescriptor form = aColl.getForms().get(0);
		repository.createForm(form, "FORMBUILDER", filepath + "\\" + testfile, 0);
		
	}
	
	protected void prepareCollectionToLoad() {
		assertNotNull(loadService);
		
		//String testfile = "3256357_v1_0_newform.xml";
		//String testfile = "3256357_v1_0_newform-partial.xml";
		//String testfile = "3256357_v1_0_multi-protocols-newform.xml";
		//String filepath = ".\\test\\data";
		
		
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
			aColl.setCreatedBy("jjuser");
			aColl.setXmlFileName(testfile);
			aColl.setXmlPathOnServer(filepath);
			
			assertNotNull(contentValidationService);
			aColl = contentValidationService.validateXmlContent(aColl);
			
			assertNotNull(aColl);
			forms = aColl.getForms();
			assertTrue(forms.size() == 1);
			assertTrue(forms.get(0).getLoadStatus() == FormDescriptor.STATUS_DB_VALIDATED);
			
			form = forms.get(0);
			
			List<QuestionDescriptor> questions = forms.get(0).getModules().get(0).getQuestions();
			assertTrue(questions.size() == 2);
			assertTrue(questions.get(1).getPublicId() != null);
			assertTrue(questions.get(1).getCdePublicId().equals("2341940"));

			String status = StatusFormatter.getStatusInXml(form);
			StatusFormatter.writeStatusToXml(status, filepath + "\\LoadService-before.xml");
		} catch (FormLoaderServiceException fle) {
			fail("Got exception: " + fle.getMessage());
		}
	}

}
