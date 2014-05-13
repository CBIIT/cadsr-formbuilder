package gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import gov.nih.nci.ncicb.cadsr.common.dto.ContactCommunicationV2TransferObject;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext.xml"})
public class JDBCContactCommunicationDAOV2Test {
	
	@Autowired
	JDBCContactCommunicationDAOV2 contactCommV2Dao;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testCreateContactCommnunication() {
		ContactCommunicationV2TransferObject contact = new ContactCommunicationV2TransferObject();
		
		contact.setCreatedBy("Yangs8");
		contact.setRankOrder(1);
		contact.setType("EMAIL");
		contact.setValue("junit.test@shady.com");
		int ret = contactCommV2Dao.createContactCommnunication("CA1469BC-A416-4680-E040-BB89AD4325CD", "9A5DC042-0DE2-4F94-E034-080020C9C0E0", contact);
		
		assertTrue(ret == 1);
	}

	@Test
	public void testGetOrganizationIdseqByName(){
		String orgSeqid = contactCommV2Dao.getOrganizationIdseqByName("NCICB");
		assertNotNull(orgSeqid);
		assertTrue(orgSeqid.length() > 0);
				
	}
}
