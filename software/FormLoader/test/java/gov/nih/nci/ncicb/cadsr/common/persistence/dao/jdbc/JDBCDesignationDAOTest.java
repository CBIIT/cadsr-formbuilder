package gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc;

import static org.junit.Assert.*;
import gov.nih.nci.ncicb.cadsr.common.dto.DesignationTransferObjectExt;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext.xml"})
public class JDBCDesignationDAOTest {
	
	@Autowired
	JDBCDesignationDAO designationV2Dao;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testCreateDesignationForComponent() {
		String formSeqid = "F46DF5A5-B830-FC46-E040-BB8921B6210C";
		String createdBy = "FORMLOADER";
		String contextSeqid = "29A8FB18-0AB1-11D6-A42F-0010A4C1E842"; //"test" context
		DesignationTransferObjectExt desig = new DesignationTransferObjectExt();
		desig.setLanguage("ENGLISH");
		desig.setName("SY Testing2"); //this has to be unique for a form
		desig.setType("ABBREVIATION");
		
		int res = designationV2Dao.createDesignationForComponent(formSeqid, contextSeqid, createdBy, desig);
		assertTrue(res == 1);
	}

}
