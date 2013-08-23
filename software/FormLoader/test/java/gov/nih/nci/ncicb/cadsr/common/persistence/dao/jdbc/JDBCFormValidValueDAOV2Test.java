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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext-jdbcdao-test.xml"})

public class JDBCFormValidValueDAOV2Test {
	
	@Autowired
	JDBCFormValidValueDAOV2 validValueV2Dao;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testCreateFormValidValueComponent() {
		
		try {	

			String parentQuestId = "E479549F-5D87-A01B-E040-BB8921B6711E";
			FormValidValueTransferObject vValue = new FormValidValueTransferObject();
			vValue.setVersion(Float.valueOf("3.0"));
			vValue.setPreferredName("Testshan4");
			vValue.setLongName("New VV LongName");
			vValue.setPreferredDefinition("Description of the VV");

			ContextTransferObject context = new ContextTransferObject();
			context.setConteIdseq("29A8FB18-0AB1-11D6-A42F-0010A4C1E842");
			vValue.setContext(context);

			vValue.setVpIdseq("EAA80DF9-32D1-4CDE-E034-0003BA3F9857");
			vValue.setCreatedBy("FORMBUILDER");

			vValue.setDisplayOrder(1);

			validValueV2Dao.createFormValidValueComponent(vValue,  parentQuestId, "FORMBUILDER");
		} catch (DMLException dme) {
			System.out.println(dme.getMessage());
		}
		
	}

	

}
