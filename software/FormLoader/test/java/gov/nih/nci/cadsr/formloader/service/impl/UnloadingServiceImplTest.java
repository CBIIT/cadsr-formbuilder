package gov.nih.nci.cadsr.formloader.service.impl;

import static org.junit.Assert.*;
import gov.nih.nci.cadsr.formloader.domain.FormCollection;
import gov.nih.nci.cadsr.formloader.domain.FormDescriptor;
import gov.nih.nci.cadsr.formloader.service.common.FormLoaderServiceException;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext-service-test-db.xml"})
public class UnloadingServiceImplTest {
	
	@Autowired
	CollectionRetrievalServiceImpl collectionRetrievalService;
	
	@Autowired
	UnloadingServiceImpl unloadService;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testUnloadForms() {
		try {
			List<FormCollection> colls = collectionRetrievalService.getAllCollectionsByUser("FORMBUILDER");
			assertNotNull(colls);
			assertTrue(colls.size() > 10);
			
			for (FormCollection coll : colls) {
				if (coll.getId().equals("E49101B2-1BAA-BA26-E040-BB8921B61DC6")) {
					List<FormDescriptor> forms = coll.getForms();
					for (FormDescriptor form : forms) {
						form.setSelected(true);
					}
					unloadService.unloadForms(forms, "YANGS");
				}
			}
		
		} catch (FormLoaderServiceException e) {
			fail(e.getMessage());
		}
	}

}
