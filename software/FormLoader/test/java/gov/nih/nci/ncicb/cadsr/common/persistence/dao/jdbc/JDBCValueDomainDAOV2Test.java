package gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.ncicb.cadsr.common.dto.PermissibleValueV2TransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.ValueMeaningV2TransferObject;
import gov.nih.nci.ncicb.cadsr.common.resource.Definition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext.xml"})
public class JDBCValueDomainDAOV2Test {

	@Autowired
	JDBCValueDomainDAOV2 valueDomainV2Dao;
	
	@Test
	public void testGetPermissibleValuesByVdSeqIds() {
		List<String> seqIds = new ArrayList<String>();
		
		seqIds.add("68A1E96E-6EFF-619E-E040-BB89AD437847"); // has 12
		seqIds.add("99BA9DC8-20D4-4E69-E034-080020C9C0E0"); //has 3
		seqIds.add("A8AAA336-44F7-5AFE-E034-0003BA12F5E7"); //doesn't return any
		seqIds.add("062E1D30-4602-17A2-E044-0003BA3F9857"); //has 50+ perm values
		seqIds.add("B3EB74E4-CC3A-521D-E034-0003BA12F5E7");
		//seqIds.add("3193455");
		
		HashMap<String, List<PermissibleValueV2TransferObject>> pvMap = 
		// List<PermissibleValueV2TransferObject> pvMap =
				valueDomainV2Dao.getPermissibleValuesByVdIds(seqIds);
		
		assertNotNull(pvMap);
		
		assertTrue(pvMap.containsKey("99BA9DC8-20D4-4E69-E034-080020C9C0E0"));
		assertTrue(pvMap.containsKey("062E1D30-4602-17A2-E044-0003BA3F9857"));
		assertTrue(!pvMap.containsKey("A8AAA336-44F7-5AFE-E034-0003BA12F5E7"));
		
		//from cde: 2865130
		assertTrue(pvMap.containsKey("68A1E96E-6EFF-619E-E040-BB89AD437847"));
		List<PermissibleValueV2TransferObject> pvs = pvMap.get("68A1E96E-6EFF-619E-E040-BB89AD437847");
		assertTrue(pvs.size() >= 3); // there should be 12
		
		PermissibleValueV2TransferObject pv = pvs.get(0);
		ValueMeaningV2TransferObject vm = (ValueMeaningV2TransferObject)pv.getValueMeaningV2();
		
		assertTrue(pv.getIdseq() != null && pv.getIdseq().length() > 0);
		assertTrue(vm.getIdseq() != null && vm.getIdseq().length() > 0);
		assertNotNull(vm);
		
	}

	@Test
	public void testGetDefinitionTextByVMId() {
		// VM seqid: A4499CFB-7A3A-DD4D-E040-BB89AD4307BF     doesn't have definition  nor designation  
		//     68A1E96E-6F15-619E-E040-BB89AD437847     has definiation but no designation
		
		List<String> defs = valueDomainV2Dao.getDefinitionTextByVMId("68A1E96E-6F15-619E-E040-BB89AD437847");
		assertNotNull(defs);
		assertTrue(defs.size() > 0);
		
		defs = valueDomainV2Dao.getDefinitionTextByVMId("A4499CFB-7A3A-DD4D-E040-BB89AD4307BF");
		assertNotNull(defs);
		assertTrue(defs.size() == 0);
	}
	
	
	@Test
	public void testGetDesignationNamesByVMId() {
		// VM seqid: A4499CFB-7A3A-DD4D-E040-BB89AD4307BF     doesn't have definition  nor designation  
		//     68A1E96E-6F15-619E-E040-BB89AD437847     has definiation but no designation
		//VM: 2572232 v1.0 2509CE87-E735-5C23-E044-0003BA3F9857 has def and 2 designations
		List<String> defs = valueDomainV2Dao.getDesignationNamesByVMId("68A1E96E-6F15-619E-E040-BB89AD437847");
		assertNotNull(defs);
		assertTrue(defs.size() == 0);
		
		defs = valueDomainV2Dao.getDesignationNamesByVMId("2509CE87-E735-5C23-E044-0003BA3F9857");
		assertNotNull(defs);
		assertTrue(defs.size() == 2);
	}
}
