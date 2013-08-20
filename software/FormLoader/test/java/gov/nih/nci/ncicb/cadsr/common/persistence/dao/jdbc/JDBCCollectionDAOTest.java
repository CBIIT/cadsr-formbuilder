package gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext-jdbcdao-test.xml"})
//@ContextConfiguration(locations = {"classpath:/applicationContext-service-test-db.xml"})
public class JDBCCollectionDAOTest {
	
	//@Autowired
	//@Resource
	//@Qualifier("collectionDao")
	@Autowired
	JDBCCollectionDAO collectionDao;
	//@Autowired
	//JDBCFormDAOV2 collectionDao;
	
	
	//@Autowired
	//JDBCCollectionDAO anotherDao;
	
	

	@Test
	public void testCreateCollectionRecord() {
		assertNotNull(collectionDao);
		String idseq = collectionDao.createCollectionRecord("collection dao unit test", 
				"UnitTest", "SomeFile", "/opt/content/formloader", "shanyang");
		assertNotNull(idseq);
	}

}
