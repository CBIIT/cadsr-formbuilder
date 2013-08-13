package gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.ncicb.cadsr.common.dto.PermissibleValueV2TransferObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext-service-test-db.xml"})
public class JDBCValueDomainDAOV2Test {

	@Autowired
	JDBCValueDomainDAOV2 valueDomainV2Dao;
	
	@Test
	public void testGetPermissibleValuesByVdSeqIds() {
		List<String> seqIds = new ArrayList<String>();
		
		seqIds.add("99BA9DC8-20D4-4E69-E034-080020C9C0E0"); //has 3
		seqIds.add("A8AAA336-44F7-5AFE-E034-0003BA12F5E7"); //doesn't return any
		seqIds.add("062E1D30-4602-17A2-E044-0003BA3F9857"); //has 50+ perm values
		//seqIds.add("3193455");
		
		HashMap<String, List<PermissibleValueV2TransferObject>> pvMap = 
		// List<PermissibleValueV2TransferObject> pvMap =
				valueDomainV2Dao.getPermissibleValuesByVdIds(seqIds);
		
		assertNotNull(pvMap);
		
		assertTrue(pvMap.containsKey("99BA9DC8-20D4-4E69-E034-080020C9C0E0"));
		assertTrue(pvMap.containsKey("062E1D30-4602-17A2-E044-0003BA3F9857"));
		assertTrue(!pvMap.containsKey("A8AAA336-44F7-5AFE-E034-0003BA12F5E7"));
		
		List<PermissibleValueV2TransferObject> pvs = pvMap.get("99BA9DC8-20D4-4E69-E034-080020C9C0E0");
		assertTrue(pvs.size() >= 3);
		
		
	}

}