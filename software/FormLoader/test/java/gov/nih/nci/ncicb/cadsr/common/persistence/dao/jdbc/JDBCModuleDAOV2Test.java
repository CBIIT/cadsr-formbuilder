package gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.ncicb.cadsr.common.dto.ModuleTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.QuestionTransferObject;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.ModuleDAOV2;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext.xml"})
public class JDBCModuleDAOV2Test {

	@Autowired
	ModuleDAOV2 moduleV2Dao;
	
	@Test
	public void testGetQuestionsInAModuleV2() {
		String moduleSeqId = "D7BA1894-9852-2123-E040-BB89AD431FD9";
		List<QuestionTransferObject> questions = moduleV2Dao.getQuestionsInAModuleV2(moduleSeqId);
		
		assertNotNull(questions);
		assertTrue(questions.size() == 4);
		
		QuestionTransferObject aQuestion = questions.get(3);
		assertNotNull(aQuestion.getDataElement());
	}
	@Test
	public void testGetQuestionsInAModuleV2WithInvalidInput() {
		String moduleSeqId = "9D1F6BBF-433F-0B69-E040-BB89AD4";
		List<QuestionTransferObject> questions = moduleV2Dao.getQuestionsInAModuleV2(moduleSeqId);
		
		assertNotNull(questions);
		assertTrue(questions.size() == 0);
	}
	
	@Test
	public void testGetModulePublicIdVersionBySeqid() {
		String seqid = "DE3366B5-706B-3BBE-E034-0003BA12F5E7";
		
		ModuleTransferObject module = moduleV2Dao.getModulePublicIdVersionBySeqid(seqid);
		assertNotNull(module);
		assertTrue(module.getPublicId() == 2265272);
		assertTrue(module.getVersion() == 3.0);
		
	}
}
