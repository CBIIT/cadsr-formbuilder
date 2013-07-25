package gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc;

import gov.nih.nci.ncicb.cadsr.common.dto.DataElementTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.QuestionTransferObject;

import java.util.List;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext-jdbcdao-test.xml"})
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
}
