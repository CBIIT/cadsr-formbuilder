package gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc;

import gov.nih.nci.ncicb.cadsr.common.dto.ModuleTransferObject;
import gov.nih.nci.ncicb.cadsr.common.resource.FormV2;
import gov.nih.nci.ncicb.cadsr.common.resource.Module;

import java.util.ArrayList;
import java.util.List;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

/**
 * @author yangs8
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext-jdbcdao-test.xml"})
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
		ids.add("3636260");
		ids.add("3636081");
		ids.add("3636123");
		ids.add("3460365");
		ids.add("1234567"); //bogus
		
		List<FormV2> forms = formV2Dao.getExistingVersionsForPublicIds(ids);
		assertNotNull(forms);
		assertTrue(forms.size() > 3);
	
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

}
