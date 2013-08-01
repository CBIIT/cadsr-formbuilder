package gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import gov.nih.nci.ncicb.cadsr.common.dto.DataElementTransferObject;
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
	JDBCAdminComponentDAOV2 adminComponentV2Dao;

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
	
	@Test
	public void testGetAllReferenceDocumentsByPublicIds() {
		List<String> publicIds = new ArrayList<String>();
		
		publicIds.add("2002460");
		publicIds.add("782"); //ver: 4.1
		publicIds.add("2001039");
		publicIds.add("2002713");
		publicIds.add("470"); //fake
		
		HashMap<String, List<ReferenceDocumentTransferObject>> des = 
				adminComponentV2Dao.getReferenceDocumentsByCdePublicIds(publicIds);
				
		assertNotNull(des);
		assertTrue(des.size() >= 4);
		assertTrue(des.get("782-4.1") != null);
		
	}

}
