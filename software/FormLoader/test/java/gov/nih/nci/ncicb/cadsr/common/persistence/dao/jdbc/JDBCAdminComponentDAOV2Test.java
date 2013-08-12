package gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import gov.nih.nci.ncicb.cadsr.common.dto.DataElementTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.DesignationTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.ReferenceDocumentTransferObject;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.AdminComponentDAOV2;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.ModuleDAOV2;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext-service-test-db.xml"})
//@ContextConfiguration(locations = {"classpath:/applicationContext-jdbcdao-test.xml"})
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
	
	@Test
	public void testGetAllContextSeqIds() {
		
		HashMap<String, String> map = adminComponentV2Dao.getAllContextSeqIds();
		assertNotNull(map);
		
		Iterator<String> ite = map.keySet().iterator();
		while (ite.hasNext()) {
			String key = (String)ite.next();
			System.out.println("Context name: " + key + " seqid: " + map.get(key));
		}
	}

	@Test 
	public void testGetDesignationsForForm() {
		//select * from sbr.designations_view
		//where detl_name='C3D Name' and lae_name='ENGLISH' and ac_idseq='C8B96C2F-397E-607A-E034-0003BA12F5E7' 
		//		and name='VITALS_POSITION' 
		
		List<DesignationTransferObject> desigs = adminComponentV2Dao.getDesignationsForForm(
				"C8B96C2F-397E-607A-E034-0003BA12F5E7",  //this is cde ac_seqid not form's
				"VITALS_POSITION", "C3D Name", "ENGLISH");
		  
		assertNotNull(desigs);
		assertTrue(desigs.size() > 0);
	}
}
