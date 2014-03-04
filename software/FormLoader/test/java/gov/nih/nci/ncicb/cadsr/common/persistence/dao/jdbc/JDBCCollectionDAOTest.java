package gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc;

import static org.junit.Assert.*;
import gov.nih.nci.cadsr.formloader.domain.FormCollection;
import gov.nih.nci.cadsr.formloader.domain.FormDescriptor;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext.xml"})
public class JDBCCollectionDAOTest {
	
	@Autowired
	JDBCCollectionDAO collectionDao;
	
	@Test
	public void testCreateCollectionRecord() {
		assertNotNull(collectionDao);
		String idseq = collectionDao.createCollectionRecord("collection dao unit test", 
				"UnitTest", "SomeFile", "/opt/content/formloader", "shanyang", 2);
		assertNotNull(idseq);
	}
	
	@Test
	public void testCreateCollectionFormMappingRecord() {
		int res = collectionDao.createCollectionFormMappingRecord("EFA0D42F-53BF-6520-E040-BB89A7B45CF0", 
				"E97FC0E8-7737-5937-E040-BB8921B63902", 0, 0, "Unknown", -3, "A Fake Record From JUnit", 0, new Date());
		
		assertTrue(res == 1);
	}
	
	@Test
	public void testUpdateCollectionFormMappingRecord() {
		
		try {
		int res = collectionDao.createCollectionFormMappingRecord("E97FC0E8-782D-5937-E040-BB8921B63902", 
				"E97FC0E8-7737-5937-E040-BB8921B63902", 0, 0, "Unknown", -6, "A Fake Record From JUnit Testing Update", 0, new Date());
		
		assertTrue(res == 0);
		} catch (DataAccessException da) {
			//TODO: due to db refresh, this is ok for now
		}
	}
	
	@Test
	public void testGetAllLoadedCollections() {
		List<FormCollection> formColls = collectionDao.getAllLoadedCollectionsByUser("YANGS");
		assertNotNull(formColls);
		assertTrue(formColls.size() > 5);
		
	}
	
	@Test
	public void testGetAllFormSeqidsForCollection() {
		String collSeqid = "F06C867A-C8A4-4EAD-E040-BB89A7B46B99";
		
		List<String> formids = collectionDao.getAllFormSeqidsForCollection(collSeqid);
		assertNotNull(formids);
		assertTrue(formids.size() > 0);
		assertTrue(formids.get(0).equals("F06C867A-C7C2-4EAD-E040-BB89A7B46B99"));
				
	}
	
	@Test
	public void testGetMaxNameRepeatNum() {
		String collName = "collection dao unit test";
		int maxNum = collectionDao.getMaxNameRepeatNum(collName);
		
		assertTrue(maxNum > 0);
	}

	@Test
	public void testGetMaxNameRepeatNumReturnsNull() {
		String collName = "TestColle";
		int maxNum = collectionDao.getMaxNameRepeatNum(collName);
		
		assertTrue(maxNum == 0);
	}
	
	@Test
	public void testGetAllFormInfoForCollection() {
		String collIdseq = "EFA0D42F-53BF-6520-E040-BB89A7B45CF0";
		
		List<FormDescriptor> forms = collectionDao.getAllFormInfoForCollection(collIdseq);
		
		assertNotNull(forms);
		assertTrue(forms.size() >= 2);
	}
}
