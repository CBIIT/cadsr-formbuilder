package gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc;

import static org.junit.Assert.*;
import gov.nih.nci.cadsr.formloader.domain.FormCollection;

import java.util.List;

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
	
	@Test
	public void testGetAllLoadedCollections() {
		List<FormCollection> formColls = collectionDao.getAllLoadedCollections();
		assertNotNull(formColls);
		assertTrue(formColls.size() > 10);
		
	}
	
	@Test
	public void testGetAllFormSeqidsForCollection() {
		String collSeqid = "E49101B2-1B48-BA26-E040-BB8921B61DC6";
		
		List<String> formids = collectionDao.getAllFormSeqidsForCollection(collSeqid);
		assertNotNull(formids);
		assertTrue(formids.size() > 0);
		assertTrue(formids.get(0).equals("E49101B2-1B33-BA26-E040-BB8921B61DC6"));
				
	}

}
