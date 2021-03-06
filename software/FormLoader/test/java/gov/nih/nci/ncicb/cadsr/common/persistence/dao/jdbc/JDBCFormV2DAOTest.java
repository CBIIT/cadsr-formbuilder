package gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import gov.nih.nci.ncicb.cadsr.common.dto.FormTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.FormV2TransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.ModuleTransferObject;
import gov.nih.nci.ncicb.cadsr.common.resource.FormV2;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author yangs8
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext.xml"})
public class JDBCFormV2DAOTest {

	@Autowired
	JDBCFormDAOV2 formV2Dao;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		
	}

	/**
	 * Test method for {@link gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc.JDBCFormDAOV2#getVersionsByPublicIds(java.util.List)}.
	 */
	@Test
	public void testGetVersionsByPublicIds() {
		List<String> ids = new ArrayList<String>();
		//ids.add("3641027");
		
		ids.add("3636260");
		ids.add("3636081");
		ids.add("3636123");
		ids.add("3460365");
		ids.add("1234567"); //bogus
		
		
		List<FormV2> forms = formV2Dao.getExistingVersionsForPublicIds(ids);
		assertNotNull(forms);
		assertTrue(forms.size() > 0);
	
		for (FormV2 form : forms) {
			if ("1234567".equals(form.getPublicId()))
				fail("Bogus public id returns a record. That's fishy.");
		}
		
	}
	
	@Test
	public void testGetModulesInAForm() {
		//public id:3193449
		String formSeqId = "9D1F6BBF-433E-0B69-E040-BB89AD436323";
		
		List<ModuleTransferObject> module = formV2Dao.getModulesInAForm(formSeqId);
		assertNotNull(module);
		assertTrue(module.size() == 3);
	}
	
	@Test
	public void testCreateFormComponent() {
		FormV2TransferObject newform = new FormV2TransferObject();
		
		HashMap<String, String> conteMap = formV2Dao.getAllContextSeqIds();
		
		newform.setVersion((float)1.0);
		newform.setAslName("DRAFT NEW");
		newform.setLongName("SY Test");
		newform.setConteIdseq(conteMap.get("CTEP"));
		newform.setCreatedBy("FORMBUILDER");
		newform.setFormType("CRF");
		newform.setPreferredDefinition("SY- CALGB: 40101 HER-2/neu TESTING FORM");
		newform.setFormCategory("Treatment");
		newform.setChangeNote("Adding a change note");
		
		String seq = formV2Dao.createFormComponent(newform);
		
		assertNotNull(seq);
		assertTrue(seq.length() > 0);
		System.out.println("Inserted a new form: " + seq);
	}
	
	@Test
	public void testGetProtocolSeqidsByIds() {
		
		List<String> protoIds = new ArrayList<String>();
		protoIds.add("ACOSOG-Z4099");
		protoIds.add("DCLS001");
		
		HashMap<String, String> protoMap = formV2Dao.getProtocolSeqidsByIds(protoIds);
		assertNotNull(protoMap);
		String key = protoMap.get("DCLS001");
		assertTrue(key.equals("C118C152-222D-6D05-E034-0003BA12F5E7"));
	}

	@Test
	public void testFormProtocolExists() {
		String formSeqId = "9D1F6BBF-433E-0B69-E040-BB89AD436323";
		String proto_idseq = "97782209-606A-AFBF-E040-BB89AD43078F";
		boolean proto = formV2Dao.formProtocolExists(formSeqId, proto_idseq);
		assertTrue(proto);
		
		proto_idseq = "afagasgaeageagae";
		proto = formV2Dao.formProtocolExists(formSeqId, proto_idseq);
		assertFalse(proto);
		
	}
	
	@Test
	public void testGetFormHeadersBySeqids() {
		List<String> seqids = new ArrayList<String>();
		seqids.add("BFC76B3A-AC92-45C7-E040-BB89AD430B2C");
		seqids.add("E49101B2-1B33-BA26-E040-BB8921B61DC6");
		String formSeqId = "9D1F6BBF-433E-0B69-E040-BB89AD436323";
		List<FormV2TransferObject> forms = formV2Dao.getFormHeadersBySeqids(seqids);
		assertNotNull(forms);
		assertTrue(forms.size() >0);
	}
	
	@Test
	public void testGetLatestVersionForForm() {
		float latest = formV2Dao.getLatestVersionForForm(2403952);
		
		assertTrue(latest >= 3.0);
	}
	
	@Test
	public void testUpdateLatestVersionIndicator() {
		int res = formV2Dao.updateLatestVersionIndicator("E9FA3574-4D30-3253-E040-BB8921B6498C", "Yes", "FORMLOADER");
		
		//Due to db refresh, the particular form record no longer exists in db.
		assertTrue(res == 0);
	}
	
	@Test
	public void testUpdateLatestVersionIndicatorByPublicIdAndVersion() {
		int res = formV2Dao.updateLatestVersionIndicatorByPublicIdAndVersion(3643954, (float)10.0, "No", "FORMLOADER");
		//Due to db refresh, the particular form record no longer exists in db.
		assertTrue(res == 0);
	}
	
	@Test
	public void testGetLatestVersionIndicatorForForm() {

		boolean yesno = formV2Dao.isLatestVersionForForm("0059EF25-C95F-0C32-E044-0003BA3F9857");
		assertTrue(yesno);
		
		yesno = formV2Dao.isLatestVersionForForm("01C305F7-D454-2719-E044-0003BA3F9857");
		assertFalse(yesno);
		
	}
	
	@Test
	public void testGetFormModifiedDateByIds() {
		List<String> ids = new ArrayList<String>();
		ids.add("EBA1ACB9-78B5-726C-E040-BB8921B66A4F");
		ids.add("001AF010-44C8-52AC-E044-0003BA3F9857");
		HashMap<String, Date> dateMap = formV2Dao.getFormModifiedDateByIds(ids);
		
		assertNotNull(dateMap.get("001AF010-44C8-52AC-E044-0003BA3F9857"));
	}
	
	@Test
	public void testGetFormModifiedDateByIdsReturnsNull() {
		List<String> ids = new ArrayList<String>();
		ids.add("D7D63DD6-67D3-59E5-E040-BB89A7B45DE7");
		HashMap<String, Date> dateMap = formV2Dao.getFormModifiedDateByIds(ids);
		
		assertNull(dateMap.get("D7D63DD6-67D3-59E5-E040-BB89A7B45DE7"));
		Date d = dateMap.get("EBA1ACB9-78B5-726C-E040-BB8921B66A4F");
	}
	
}
