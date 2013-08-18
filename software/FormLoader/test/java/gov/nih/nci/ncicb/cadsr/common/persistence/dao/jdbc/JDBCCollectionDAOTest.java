package gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc;

import gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc.JDBCCollectionDAO;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext-jdbcdao-test.xml"})
//@ContextConfiguration(locations = {"classpath:/applicationContext-service-test-db.xml"})
public class JDBCCollectionDAOTest {
	
	@Autowired
	JDBCCollectionDAO collectionDao;

	@Test
	public void testCreateCollectionRecord() {
		String idseq = collectionDao.createCollectionRecord("collection dao unit test", 
				"UnitTest", "SomeFile", "/opt/content/formloader", "shanyang");
		assertNotNull(idseq);
	}

}
