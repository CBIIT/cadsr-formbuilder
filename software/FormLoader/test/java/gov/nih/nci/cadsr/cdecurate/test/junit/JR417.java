package gov.nih.nci.cadsr.cdecurate.test.junit;

import static org.junit.Assert.assertTrue;
import gov.nih.nci.cadsr.formloader.service.common.FormLoaderHelper;
import gov.nih.nci.ncicb.cadsr.common.dto.PermissibleValueV2TransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.ValueMeaningV2TransferObject;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc.JDBCValueDomainDAOV2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext.xml"})
public class JR417 {
	@Autowired
	JDBCValueDomainDAOV2 valueDomainV2Dao;

	@BeforeClass
	public static void init() {
	}

	@After
	public void cleanup() {
	}

	@Test
	public void testEmpty() {
		List<String> vdSeqIds = new ArrayList();
		//[99BA9DC8-4CF3-4E69-E034-080020C9C0E0, B54EF112-5457-674A-E034-0003BA12F5E7, B4358773-2F37-159A-E034-0003BA12F5E7, B54EF112-5457-674A-E034-0003BA12F5E7, B76D9182-A557-5B3C-E034-0003BA12F5E7, B76D9182-A557-5B3C-E034-0003BA12F5E7, 232AEB9D-12CE-72DA-E044-0003BA3F9857, D40D9C18-2A51-4D87-E034-0003BA12F5E7, B2290399-D744-4A70-E034-0003BA12F5E7, D40DA439-A5C7-4AF1-E034-0003BA12F5E7, B2290399-D744-4A70-E034-0003BA12F5E7, C6FBE132-3509-3E23-E034-0003BA12F5E7, E0C66055-4C2A-4F38-E034-0003BA12F5E7, F169098A-E8D2-306E-E034-0003BA3F9857, F54516A5-2717-25D2-E034-0003BA3F9857, 85FA5C84-F008-BF1A-E040-BB89AD43366C, 0781A8FE-53BA-0C0C-E044-0003BA3F9857, 47A0CEBB-5D55-61FA-E044-0003BA3F9857, 47A0CEBB-5D55-61FA-E044-0003BA3F9857]
		//=== Please keep the above samples (DEV tier)
		vdSeqIds.add("99BA9DC8-4CF3-4E69-E034-080020C9C0E0");	//no pv
		vdSeqIds.add("B54EF112-5457-674A-E034-0003BA12F5E7");	//no pv
		vdSeqIds.add("B4358773-2F37-159A-E034-0003BA12F5E7");	//no pv
		HashMap<String, List<PermissibleValueV2TransferObject>> pValues = valueDomainV2Dao.getPermissibleValuesByVdIds(vdSeqIds);
		//printVMPublicIdVersion(pValues);
		try {
			ValueMeaningV2TransferObject vm = FormLoaderHelper.getVVPVVM(0, 0, pValues);
			assertTrue("Test empty results", vm == null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testVMPublicIdVersion() {
		List<String> vdSeqIds = new ArrayList();
		//[99BA9DC8-4CF3-4E69-E034-080020C9C0E0, B54EF112-5457-674A-E034-0003BA12F5E7, B4358773-2F37-159A-E034-0003BA12F5E7, B54EF112-5457-674A-E034-0003BA12F5E7, B76D9182-A557-5B3C-E034-0003BA12F5E7, B76D9182-A557-5B3C-E034-0003BA12F5E7, 232AEB9D-12CE-72DA-E044-0003BA3F9857, D40D9C18-2A51-4D87-E034-0003BA12F5E7, B2290399-D744-4A70-E034-0003BA12F5E7, D40DA439-A5C7-4AF1-E034-0003BA12F5E7, B2290399-D744-4A70-E034-0003BA12F5E7, C6FBE132-3509-3E23-E034-0003BA12F5E7, E0C66055-4C2A-4F38-E034-0003BA12F5E7, F169098A-E8D2-306E-E034-0003BA3F9857, F54516A5-2717-25D2-E034-0003BA3F9857, 85FA5C84-F008-BF1A-E040-BB89AD43366C, 0781A8FE-53BA-0C0C-E044-0003BA3F9857, 47A0CEBB-5D55-61FA-E044-0003BA3F9857, 47A0CEBB-5D55-61FA-E044-0003BA3F9857]
		//=== Please keep the above samples (DEV tier)
		vdSeqIds.add("47A0CEBB-5D55-61FA-E044-0003BA3F9857");	//1 pv
		HashMap<String, List<PermissibleValueV2TransferObject>> pValues = valueDomainV2Dao.getPermissibleValuesByVdIds(vdSeqIds);
		//printVMPublicIdVersion(pValues);
		try {
			ValueMeaningV2TransferObject vm = FormLoaderHelper.getVVPVVM(0, 0, pValues);
			assertTrue("Test 1 pv 1 st vm", (vm.getPreferredName() != null && vm.getPublicId() > 0 && vm.getVersion() > 0));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void printVMPublicIdVersion(HashMap<String, List<PermissibleValueV2TransferObject>> pValues) {
		try {
			ValueMeaningV2TransferObject vm = FormLoaderHelper.getVVPVVM(0, 0, pValues);
			System.out.println("VM Preferred name [" + vm.getPreferredName() + "] public id [" + vm.getPublicId() + "] version [" + vm.getVersion() + "]");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
