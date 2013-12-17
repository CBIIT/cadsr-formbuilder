package gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.ncicb.cadsr.common.dto.DesignationTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.ReferenceDocumentTransferObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath:/applicationContext-service-test-db.xml"})
@ContextConfiguration(locations = {"classpath:/applicationContext-jdbcdao-test.xml"})
public class JDBCAdminComponentDAOV2Test {
	
	@Autowired
	JDBCFormDAOV2 adminComponentV2Dao;

	@Test
	public void testGetAllReferenceDocumentsIntFloat() {
		List<ReferenceDocumentTransferObject> refdocs =
				adminComponentV2Dao.getAllReferenceDocumentsForDE(64788, (float)2.31);
		
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
	
	@Test
	public void testGetAllDesignationTypes() {
		List<String> alltypes = adminComponentV2Dao.getAllDesignationTypes();
		assertTrue(alltypes.size() > 10 && alltypes.size() < 200);
		
	}
	
	@Test
	public void testGetAllRefdocTypes() {
		List<String> alltypes = adminComponentV2Dao.getAllRefdocTypes();
		assertTrue(alltypes.size() > 10 && alltypes.size() < 100);
		
	}
	
	@Test
	public void testGetAllDefinitionTypes() {
		List<String> alltypes = adminComponentV2Dao.getAllDefinitionTypes();
		assertTrue(alltypes.size() > 10 && alltypes.size() < 100);
		
	}
	
	@Test
	public void testGetAllWorkflowStatusName() {
		List<String> alltypes = adminComponentV2Dao.getAllWorkflowNames();
		assertTrue(alltypes.size() > 10 && alltypes.size() < 100);
		
	}
	
	@Test
	public void testUpdateWorkflowStatus() {
		String formseqid = "E9FA3574-4D30-3253-E040-BB8921B6498C";
		String wfName = "RETIRED DELETED";
		
		int res = adminComponentV2Dao.updateWorkflowStatus(formseqid, wfName, "FORMLOADER", "Unit testing update workflow");
		
		assertTrue(res >= 1);
	}
}
