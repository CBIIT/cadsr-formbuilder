package gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc;

import static org.junit.Assert.*;

import java.util.List;

import gov.nih.nci.ncicb.cadsr.common.dto.ReferenceDocumentTransferObject;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.AdminComponentDAOV2;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.ModuleDAOV2;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext-jdbcdao-test.xml"})
public class JDBCAdminComponentDAOV2Test {
	
	@Autowired
	AdminComponentDAOV2 adminComponentV2Dao;

	@Test
	public void testGetAllReferenceDocumentsIntFloat() {
		List<ReferenceDocumentTransferObject> refdocs =
				adminComponentV2Dao.getAllReferenceDocuments(64788, (float)2.31);
		
		assertNotNull(refdocs);
		assertTrue(refdocs.size() == 2);
		assertTrue(refdocs.get(0).getDocName().equalsIgnoreCase("CRF Text"));
		assertTrue(refdocs.get(0).getDocType().equalsIgnoreCase("Alternate Question Text") 
				|| refdocs.get(0).getDocType().equalsIgnoreCase("Preferred Question Text"));
		
	}

}
