package gov.nih.nci.cadsr.formloader.service.impl;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.cadsr.formloader.domain.FormCollection;
import gov.nih.nci.cadsr.formloader.domain.FormHeader;
import gov.nih.nci.cadsr.formloader.service.ContentValidationService;
import gov.nih.nci.cadsr.formloader.service.common.FormLoaderServiceException;

import java.util.ArrayList;
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
@ContextConfiguration(locations = {"classpath:/applicationContext-service-test.xml"})
public class ContentValidationServiceImplTest {
	
	private static Logger logger = Logger.getLogger(ContentValidationServiceImplTest.class.getName());

	@Autowired
	ContentValidationService contentValidationService;
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testValidateXmlContent() {
		try {
			FormCollection aColl = contentValidationService.validateXmlContent(null, "afafa");
			assertNotNull(aColl);
		} catch (FormLoaderServiceException e) {
			logger.debug(e);
		}
		
		//fail("Not yet implemented");
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
}
