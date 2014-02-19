package gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import gov.nih.nci.ncicb.cadsr.common.dto.ContextTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.FormValidValueTransferObject;
import gov.nih.nci.ncicb.cadsr.common.exception.DMLException;
import gov.nih.nci.ncicb.cadsr.common.resource.FormValidValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext-service-test-db.xml"})
public class JDBCFormValidValueDAOV2Test {
	
	@Autowired
	JDBCFormValidValueDAOV2 validValueV2Dao;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testCreateFormValidValueComponent() {
		
		try {	

			String parentQuestId = "F07EC184-FF20-F9EE-E040-BB89A7B433A4"; //a question id
			FormValidValueTransferObject vValue = new FormValidValueTransferObject();
			vValue.setVersion(Float.valueOf("3.0"));
			vValue.setPreferredName("Testshan4");
			vValue.setLongName("New VV LongName");
			vValue.setPreferredDefinition("Description of the VV");
			vValue.setAslName("DRAFT NEW");

			ContextTransferObject context = new ContextTransferObject();
			context.setConteIdseq("29A8FB18-0AB1-11D6-A42F-0010A4C1E842");
			vValue.setContext(context);

			vValue.setVpIdseq("EAA80DF9-32D1-4CDE-E034-0003BA3F9857");
			vValue.setCreatedBy("FORMBUILDER");

			vValue.setDisplayOrder(1);

			//TODO: Due to db data refresh, commented out for now
			validValueV2Dao.createFormValidValueComponent(vValue,  parentQuestId, "FORMLOADER");
		} catch (DMLException dme) {
			System.out.println(dme.getMessage());
		}
		
	}

	@Test
	public void testCreateValidValue() {
		
		try {	

			String parentQuestId = "F07EC184-FF20-F9EE-E040-BB89A7B433A4";
			FormValidValueTransferObject vValue = new FormValidValueTransferObject();
			vValue.setVersion(Float.valueOf("3.0"));
			vValue.setPreferredName("Testshan8800ASFAF");
			vValue.setLongName("New VV LongName");
			vValue.setPreferredDefinition("Description of the VV");
			vValue.setAslName("DRAFT NEW");

			ContextTransferObject context = new ContextTransferObject();
			context.setConteIdseq("29A8FB18-0AB1-11D6-A42F-0010A4C1E842");
			vValue.setContext(context);

			vValue.setVpIdseq("EAA80DF9-32D1-4CDE-E034-0003BA3F9857");
			vValue.setCreatedBy("FORMLOADER");

			vValue.setDisplayOrder(2);

			//TODO: Due to db data refresh, commented out for now
			validValueV2Dao.createValidValue(vValue,  parentQuestId, "FORMLOADER");
		} catch (DataAccessException dme) {
			
			//For now, this fine. db doesn't allow duplicates.
			System.out.println(dme.getMessage());
		} 
		
	}

	@Test
	public void testGetValidValueSeqidsByQuestionSeqid() {
		String questionSeqid = "F07EC184-FF20-F9EE-E040-BB89A7B433A4";
		
		List<String> vvIds = validValueV2Dao.getValidValueSeqidsByQuestionSeqid(questionSeqid);
		
		assertNotNull(vvIds);
		assertTrue(vvIds.size() > 0);
	}
}
