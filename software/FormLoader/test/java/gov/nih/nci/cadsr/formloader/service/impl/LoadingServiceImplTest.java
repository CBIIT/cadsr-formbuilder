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
@ContextConfiguration(locations = {"classpath:/applicationContext.xml"})
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
		this.prepareCollectionToLoad(".\\.\\test\\data\\loading", "3256357_v1_0_newform.xml");
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
public void testUserHasRight() {
		this.prepareCollectionToLoad(".\\.\\test\\data\\loading", "3256357_v1_0_newform.xml");
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
		this.prepareCollectionToLoad(".\\.\\test\\data\\loading", "load_newform.xml");
		
		try {
			FormDescriptor form = aColl.getForms().get(0);
			form.setSelected(true);
			aColl.setCreatedBy("RAJAS");
			aColl = this.loadService.loadForms(aColl);
			form = aColl.getForms().get(0);
			String status = StatusFormatter.getStatusInXml(form);
			StatusFormatter.writeStatusToXml(status, ".\\test\\data\\loading\\load_newform-jim.status.xml");
		} catch (FormLoaderServiceException fle) {
			fail("Got exception: " + fle.getMessage());
		}
	}
	
	@Test
	public void testLoadNewFormRave() {
		
		this.prepareCollectionToLoad(".\\.\\test\\data\\loading", "FourTheradexMedidataRaveFormsMinimumFields_01-11-2014.xml");
		
		try {
			FormDescriptor form = aColl.getForms().get(0);
			form.setSelected(true);
			aColl.setCreatedBy("YANGS");
			aColl = this.loadService.loadForms(aColl);
			form = aColl.getForms().get(0);
			String status = StatusFormatter.getStatusInXml(form);
			StatusFormatter.writeStatusToXml(status, ".\\test\\data\\loading\\FourTheradexMedidataRaveFormsMinimumFields_01-11-2014.status.xml");
		} catch (FormLoaderServiceException fle) {
			fail("Got exception: " + fle.getMessage());
		}
	}
	
	@Test
	public void testLoadNewFormWithQuestDefaultValue() {
		this.prepareCollectionToLoad(".\\.\\test\\data\\loading", "load-newform-with-defaultVal.xml");
		try {
			FormDescriptor form = aColl.getForms().get(0);
			form.setSelected(true);
			aColl = this.loadService.loadForms(aColl);
			form = aColl.getForms().get(0);
			String status = StatusFormatter.getStatusInXml(form);
			StatusFormatter.writeStatusToXml(status, ".\\test\\data\\loading\\load-newform-with-defaultVal.status.xml");
		} catch (FormLoaderServiceException fle) {
			fail("Got exception: " + fle.getMessage());
		}
	}
	
	@Test
	public void testLoadBiggerNewForm() {
		this.prepareCollectionToLoad(".\\.\\test\\data\\loading", "3256357_v1_0_newform.xml");
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
	
	@Test
	public void testLoadNewVersion() {
		this.prepareCollectionToLoad(".\\.\\test\\data", "3256357_v1_0_newform-partial-newversion.xml");
		try {
			FormDescriptor form = aColl.getForms().get(0);
			form.setSelected(true);
			aColl.setCreatedBy("RAJAS");
			aColl = this.loadService.loadForms(aColl);
			form = aColl.getForms().get(0);
			String status = StatusFormatter.getStatusInXml(aColl);
			StatusFormatter.writeStatusToXml(status, ".\\test\\data\\LoadService-creatednew-version.xml");
		} catch (FormLoaderServiceException fle) {
			fail("Got exception: " + fle.getMessage());
		}
	}
	
	//@Test
	public void testLoadUpdateForm() {

		//this.prepareCollectionToLoad(".\\test\\data", "update-form.xml");
		this.prepareCollectionToLoad(".\\test\\data", "update-form-with-xmlval-errors.xml");
		try {
			FormDescriptor form = aColl.getForms().get(0);
			assertTrue(form.getLoadType() == FormDescriptor.LOAD_TYPE_UPDATE_FORM);
			form.setSelected(true);
			aColl = this.loadService.loadForms(aColl);
			form = aColl.getForms().get(0);
			assertTrue(form.getLoadStatus() == FormDescriptor.STATUS_LOADED);
			String status = StatusFormatter.getStatusInXml(aColl);
			StatusFormatter.writeStatusToXml(status, ".\\test\\data\\update-form.status.xml");
		} catch (FormLoaderServiceException fle) {
			fail("Got exception: " + fle.getMessage());
		}
	}
	
	//@Test
	public void testLoadUpdateFormWithRefdocs() {
		
		this.prepareCollectionToLoad(".\\.\\test\\data", "update-with-refdocs.xml");
		try {
			FormDescriptor form = aColl.getForms().get(0);
			assertTrue(form.getLoadType() == FormDescriptor.LOAD_TYPE_UPDATE_FORM);
			form.setSelected(true);
			aColl = this.loadService.loadForms(aColl);
			form = aColl.getForms().get(0);
			assertTrue(form.getLoadStatus() == FormDescriptor.STATUS_LOADED);
			String status = StatusFormatter.getStatusInXml(aColl);
			StatusFormatter.writeStatusToXml(status, ".\\test\\data\\update-with-refdocs.status.xml");
		} catch (FormLoaderServiceException fle) {
			fail("Got exception: " + fle.getMessage());
		}
	}
	
	//@Test
	public void testLoadNewVersionFormWithRefdocs() {
		//new-version-3643954: has a forever non-existing version, thus making it 
		//a new version load always.
		this.prepareCollectionToLoad(".\\.\\test\\data\\loading", "new-version-3643954.xml");
		try {
			FormDescriptor form = aColl.getForms().get(0);
			assertTrue(form.getLoadType() == FormDescriptor.LOAD_TYPE_NEW_VERSION);
			form.setSelected(true);
			aColl = this.loadService.loadForms(aColl);
			form = aColl.getForms().get(0);
			assertTrue(form.getLoadStatus() == FormDescriptor.STATUS_LOADED);
			String status = StatusFormatter.getStatusInXml(aColl);
			StatusFormatter.writeStatusToXml(status, ".\\test\\data\\loading\\new-version-3643954.status.xml");
		} catch (FormLoaderServiceException fle) {
			fail("Got exception: " + fle.getMessage());
		}
	}

	protected void prepareCollectionToLoad(String filepath, String testfile) {
		assertNotNull(loadService);
		
		try {
			aColl = new FormCollection();
			aColl.setXmlPathOnServer(filepath);
			aColl.setXmlFileName(testfile);
			aColl = xmlValidator.validateXml(aColl);
			List<FormDescriptor> forms = aColl.getForms();
			assertNotNull(forms);
			assertTrue(forms.size() >= 1);
			
			FormDescriptor form = forms.get(0);
			String status = StatusFormatter.getStatusInXml(form);
			StatusFormatter.writeStatusToXml(status, filepath + "\\load-preparation-xml.xml");
			
			assertTrue(forms.get(0).getLoadStatus() == FormDescriptor.STATUS_XML_VALIDATED);
	
			//aColl.setForms(forms);
			aColl.setName("Testing Load 5 forms with FormLoaderv7.xsd");
			aColl.setCreatedBy("YANGS");
			aColl.setXmlFileName(testfile);
			aColl.setXmlPathOnServer(filepath);
			
			for (FormDescriptor f : forms) 
				f.setSelected(true);
			
			assertNotNull(contentValidationService);
			
			aColl = contentValidationService.validateXmlContent(aColl);
			
			assertNotNull(aColl);
			forms = aColl.getForms();
			//assertTrue(forms.size() == 1);
			status = StatusFormatter.getStatusInXml(aColl);
			StatusFormatter.writeStatusToXml(status, filepath + "\\load-preparation-content.xml");
			assertTrue(forms.get(0).getLoadStatus() == FormDescriptor.STATUS_CONTENT_VALIDATED);
			
			form = forms.get(0);

			//status = StatusFormatter.getStatusInXml(aColl);
			//StatusFormatter.writeStatusToXml(status, filepath + "\\LoadService-collection.xml");
		} catch (FormLoaderServiceException fle) {
			fail("Got exception: " + fle.getMessage());
		}
	}
	
	@Test
	public void testLoad5Forms() {
		this.prepareCollectionToLoad(".\\test\\data\\loading", "load_forms-5.xml");
		try {
			List<FormDescriptor> forms = aColl.getForms();
			assertTrue(forms.get(0).getLoadType().equals(FormDescriptor.LOAD_TYPE_NEW));
			assertTrue(forms.get(1).getLoadType().equals(FormDescriptor.LOAD_TYPE_UNKNOWN));
			assertTrue(forms.get(2).getLoadType().equals(FormDescriptor.LOAD_TYPE_UNKNOWN));
			//assertTrue(forms.get(3).getLoadStatus() == FormDescriptor.STATUS_XML_VALIDATION_FAILED);
			assertTrue(forms.get(4).getLoadType().equals(FormDescriptor.LOAD_TYPE_UNKNOWN));
			
			forms.get(0).setSelected(true);
//			forms.get(1).setSelected(true);
//			forms.get(2).setSelected(true);
//			forms.get(3).setSelected(false);
//			forms.get(4).setSelected(true);
			
//			
			aColl = this.loadService.loadForms(aColl);
//			
			String status = StatusFormatter.getStatusInXml(aColl);
			StatusFormatter.writeStatusToXml(status, ".\\test\\data\\loading\\load_forms-5.status.xml");
			
			FormDescriptor form = aColl.getForms().get(0);
			assertTrue(form.getLoadStatus() == FormDescriptor.STATUS_LOADED);
			
			//status = StatusFormatter.getStatusMessagesInXml(aColl.getForms().get(1));
			//StatusFormatter.writeStatusToXml(status, ".\\test\\data\\load_forms-5-1.status.xml");
//			
		} catch (FormLoaderServiceException fle) {
			fail("Got exception: " + fle.getMessage());
		}
	}
}
