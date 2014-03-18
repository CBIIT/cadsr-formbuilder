package gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext.xml"})
public class JDBCProtocolDAOV2Test {
	
	@Autowired
	JDBCProtocolDAOV2 protocolV2Dao;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testgetProtocolSeqidByPreferredName() {
		String seqid = protocolV2Dao.getProtocolSeqidByPreferredName("02_C_0241E", "A5599257-A08F-41D1-E034-080020C9C0E0");
		
		assertNotNull(seqid);
		assertTrue(seqid.length() > 0);
		
		seqid = protocolV2Dao.getProtocolSeqidByPreferredName("02_C_0241E", null);
		assertTrue(seqid == null || seqid.length() == 0);
		
		seqid = protocolV2Dao.getProtocolSeqidByPreferredName("", null);
		assertTrue(seqid == null || seqid.length() == 0);
	}

}
