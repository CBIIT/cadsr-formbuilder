package gov.nih.nci.cadsr.formloader.service.impl;

import static org.junit.Assert.*;
import gov.nih.nci.cadsr.formloader.domain.FormCollection;
import gov.nih.nci.cadsr.formloader.domain.FormDescriptor;
import gov.nih.nci.cadsr.formloader.service.CollectionRetrievalService;
import gov.nih.nci.cadsr.formloader.service.UnloadingService;
import gov.nih.nci.cadsr.formloader.service.common.FormLoaderHelper;
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
	CollectionRetrievalService collectionRetrievalService;
	
	@Autowired
	UnloadingService unloadService;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testUnloadForms() {
		try {
			List<FormCollection> colls = collectionRetrievalService.getAllCollectionsByUser("YANGS");
			assertNotNull(colls);
			assertTrue(colls.size() > 10);
			
			for (FormCollection coll : colls) {
				if (coll.getId().equals("F07CAD98-127E-3BD3-E040-BB89A7B4176E")) {
					List<FormDescriptor> forms = coll.getForms();
					for (FormDescriptor form : forms) {
						form.setSelected(true);
					}
					//unloadService.unloadForms(forms, "YANGS");
				}
			}
		
		} catch (FormLoaderServiceException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testAdjustFormSelections() {
		List<FormCollection> colls = FormLoaderHelper.readCollectionListFromFile();
		
		for (FormCollection coll : colls) {
			List<FormDescriptor> forms = coll.getForms();
			for (FormDescriptor form : forms) {
				
			}
		}
	}

}
