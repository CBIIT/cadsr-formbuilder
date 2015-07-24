package gov.nih.nci.cadsr.cdecurate.test.junit;

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
import gov.nih.nci.cadsr.formloader.service.impl.LoadingServiceImpl;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Setup:
 * 
 * 1. Make sure all FL libraries i.e. software/FormLoader/WebContent/WEB-INF/*.jar are on top of your classpath except FormLoader's src/java and test/java
 * 2. Add software/FormLoader/WebContent/WEB-INF into the classpath of JUnit runner
 * 3. Add software/FormLoader/resources into the classpath of JUnit runner
 * 
 * @author tanj
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext.xml"})
public class JR423 {
	
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
		System.out.println("...");
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
			StatusFormatter.writeStatusToXml(status, filepath + "load-preparation-xml.xml");
			
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
			StatusFormatter.writeStatusToXml(status, filepath + "load-preparation-content.xml");
			assertTrue(forms.get(0).getLoadStatus() == FormDescriptor.STATUS_CONTENT_VALIDATED);
			
			form = forms.get(0);

			//status = StatusFormatter.getStatusInXml(aColl);
			//StatusFormatter.writeStatusToXml(status, filepath + "\\LoadService-collection.xml");
		} catch (FormLoaderServiceException fle) {
			fle.printStackTrace();
			fail("Got exception: " + fle.getMessage());
		}
	}

	@Test
	public void testFormLoad() throws Exception {
		this.prepareCollectionToLoad("/Users/tanj3/cadsr-formbuilder/software/FormLoader/test/data/loading/", "QA4188231_v1_8.xml");
		try {
			List<FormDescriptor> forms = aColl.getForms();
			assertTrue(forms.get(0).getLoadType().equals(FormDescriptor.LOAD_TYPE_NEW));
			
			forms.get(0).setSelected(true);

			try {
				aColl = this.loadService.loadForms(aColl);
			} catch(Exception e) {
				e.printStackTrace();
				//org.springframework.dao.DataIntegrityViolationException: PreparedStatementCallback; SQL [ INSERT INTO sbrext.quest_contents_view_ext  (qc_idseq, version, preferred_name, long_name, preferred_definition,   conte_idseq, asl_name, created_by, qtl_name, de_idseq)  VALUES  (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ]; ORA-01400: cannot insert NULL into ("SBREXT"."QUEST_CONTENTS_EXT"."PREFERRED_DEFINITION")
				//; nested exception is java.sql.SQLException: ORA-01400: cannot insert NULL into ("SBREXT"."QUEST_CONTENTS_EXT"."PREFERRED_DEFINITION")
				throw e;
			}
			
			String status = StatusFormatter.getStatusInXml(aColl);
			StatusFormatter.writeStatusToXml(status, "/Users/tanj3/cadsr-formbuilder/software/FormLoader/test/data/loading/QA4188231_v1_8.status.xml");
			
			FormDescriptor form = aColl.getForms().get(0);
			assertTrue(form.getLoadStatus() == FormDescriptor.STATUS_LOADED);
			
			//status = StatusFormatter.getStatusMessagesInXml(aColl.getForms().get(1));
			//StatusFormatter.writeStatusToXml(status, ".\\test\\data\\load_forms-5-1.status.xml");
		} catch (FormLoaderServiceException fle) {
			fail("Got exception: " + fle.getMessage());
		}
	}
}