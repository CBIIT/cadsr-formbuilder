package gov.nih.nci.cadsr.formloader.service.impl;

import static org.junit.Assert.*;

import java.util.List;

import gov.nih.nci.cadsr.formloader.domain.FormCollection;
import gov.nih.nci.cadsr.formloader.service.common.FormLoaderServiceException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext.xml"})
public class CollectionRetrievalServiceImplTest {
	
	@Autowired
	CollectionRetrievalServiceImpl collectionRetrievalService;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGetAllCollections() {
		try {
			List<FormCollection> colls = collectionRetrievalService.getAllCollectionsByUser("yangs");
			assertNotNull(colls);
			assertTrue(colls.size() > 5);
		
		} catch (FormLoaderServiceException e) {
			fail(e.getMessage());
		}
		
	}

}
