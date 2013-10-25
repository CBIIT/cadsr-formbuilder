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
				"UnitTest", "SomeFile", "/opt/content/formloader", "shanyang", 2);
		assertNotNull(idseq);
	}
	
	@Test
	public void testCreateCollectionFormMappingRecord() {
		int res = collectionDao.createCollectionFormMappingRecord("E97FC0E8-782D-5937-E040-BB8921B63902", 
				"E97FC0E8-7737-5937-E040-BB8921B63902", 0, 0, "Unknown", -3, "A Fake Record From JUnit");
		
		assertTrue(res == 1);
	}
	
	@Test
	public void testUpdateCollectionFormMappingRecord() {
		int res = collectionDao.createCollectionFormMappingRecord("E97FC0E8-782D-5937-E040-BB8921B63902", 
				"E97FC0E8-7737-5937-E040-BB8921B63902", 0, 0, "Unknown", -6, "A Fake Record From JUnit Testing Update");
		
		assertTrue(res == 1);
	}
	
	@Test
	public void testGetAllLoadedCollections() {
		List<FormCollection> formColls = collectionDao.getAllLoadedCollectionsByUser("FORMBUILDER");
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
	
	@Test
	public void testGetMaxNameRepeatNum() {
		String collName = "Collection with 5 Forms";
		int maxNum = collectionDao.getMaxNameRepeatNum(collName);
		
		assertTrue(maxNum > 0);
	}

	@Test
	public void testGetMaxNameRepeatNumReturnsNull() {
		String collName = "TestColle";
		int maxNum = collectionDao.getMaxNameRepeatNum(collName);
		
		assertTrue(maxNum == 0);
	}
}
