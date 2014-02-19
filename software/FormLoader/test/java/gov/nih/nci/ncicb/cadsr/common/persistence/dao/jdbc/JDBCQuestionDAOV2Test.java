package gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.ncicb.cadsr.common.dto.DataElementTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.QuestionTransferObject;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext-service-test-db.xml"})
public class JDBCQuestionDAOV2Test {
	
	@Autowired
	JDBCQuestionDAOV2 questionV2Dao;

	@Test
	public void testGetQuestionsByPublicId() {
		List<QuestionTransferObject> qDtos = questionV2Dao.getQuestionsByPublicId(3193457);
		assertNotNull(qDtos);
	}

	@Test
	public void testGetQuestionsByPublicIdAndVersion() {
		List<QuestionTransferObject> qDtos = questionV2Dao.getQuestionByPublicIdAndVersion(3193457, (float)1.0);
		assertNotNull(qDtos);
		assertTrue(qDtos.size() == 1);
	}
	
	@Test
	public void testGetCdesByPublicId() {
		List<DataElementTransferObject> des = questionV2Dao.getCdesByPublicId("64788");
		assertNotNull(des);
		assertTrue(des.size() == 2);
		
	}
	
	@Test
	public void testGetCdesByPublicIds() {
		List<String> publicIds = new ArrayList<String>();
		
		publicIds.add("2002460");
		publicIds.add("782");
		publicIds.add("2001039");
		publicIds.add("2002713");
		publicIds.add("470"); //fake
		
		List<DataElementTransferObject> des = questionV2Dao.getCdesByPublicIds(publicIds);
		assertNotNull(des);
		assertTrue(des.size() >= 4);
		
	}
	
	@Test 
	public void testGetQuestionsByPublicIds() {
		List<String> publicIds = new ArrayList<String>();
		
		publicIds.add("3193451");
		publicIds.add("3193452");
		publicIds.add("3193453");
		publicIds.add("3193455");
		publicIds.add("470"); //fake
		publicIds.add("3193456");
		publicIds.add("3193457");
		publicIds.add("3193470");
		//QuestionTransferObject
		List<QuestionTransferObject> qDtos = questionV2Dao.getQuestionsByPublicIds(publicIds);
		assertNotNull(qDtos);
		
		assertTrue(qDtos.size() == 7);
	}
	
	@Test
	public void testGetQuestionsPublicIdVersionBySeqid() {
		String questSeqid = "9D1F6BBF-4349-0B69-E040-BB89AD436323";
		QuestionTransferObject questdto = questionV2Dao.getQuestionsPublicIdVersionBySeqid(questSeqid);
		
		assertNotNull(questdto);
		
	}
 }
