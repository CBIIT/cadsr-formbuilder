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
@ContextConfiguration(locations = {"classpath:/applicationContext.xml"})
public class FormLoaderRepositoryImplTest {
	
	@Autowired
	FormLoaderRepository repository;
	
	
	
	FormCollection aColl;
	
	String testfile = "3256357_v1_0_newform-partial.xml";
	//String testfile = "3256357_v1_0_multi-protocols-newform.xml";
	String filepath = ".\\test\\data\\xmlvalidation";


	@Before
	public void setUp() throws Exception {
	}
	
	@Test
	public void testCheckWorkflowStatusName() {
		FormDescriptor form = new FormDescriptor("seqid", "345678", "1.0");
		form.setWorkflowStatusName("Fake Name");
		repository.checkWorkflowStatusName(form);
		
		assertTrue(form.getWorkflowStatusName().equals("DRAFT NEW"));
		
		form.setWorkflowStatusName("RELEASED");
		repository.checkWorkflowStatusName(form);
		assertTrue(form.getWorkflowStatusName().equals("RELEASED"));
	}
	
	@Test
	public void testGetAllLoadedCollections() {
		List<FormCollection> colls = repository.getAllLoadedCollectionsByUser("yangs");
		
		assertNotNull(colls);
		assertTrue(colls.size() > 0);

		for (FormCollection coll : colls) {
			if (coll.getId().equals("E49101B2-1B48-BA26-E040-BB8921B61DC6")) {
				List<FormDescriptor> forms = coll.getForms();

				assertNotNull(forms.get(0).getPublicId());
				assertTrue(forms.get(0).getPublicId().length() > 0);

				assertNotNull(forms.get(0).getVersion());
				assertTrue(forms.get(0).getVersion().length() > 0);
			}
		}
	}
	
	
}
