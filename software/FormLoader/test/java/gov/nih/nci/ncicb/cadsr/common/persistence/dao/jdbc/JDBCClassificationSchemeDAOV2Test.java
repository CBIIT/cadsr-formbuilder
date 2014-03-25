package gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext.xml"})
public class JDBCClassificationSchemeDAOV2Test {

	@Autowired
	JDBCClassificationSchemeDAOV2 classificationSchemeDao;
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGetClassificationSchemaCountByPublicIdVersion() {
		int count = classificationSchemeDao.getClassificationSchemeCountByPublicIdVersion(4011991, (float)1.0);
		assertTrue(count == 1);
		
		count = classificationSchemeDao.getClassificationSchemeCountByPublicIdVersion(2812522, (float)1.0);
		assertTrue(count == 0);
	}

	@Test
	public void testGetClassificationSchemaItemCountByPublicIdVersion() {
		int count = classificationSchemeDao.getClassificationSchemeItemCountByPublicIdVersion(4011991, (float)1.0);
		assertTrue(count == 0);
		
		count = classificationSchemeDao.getClassificationSchemeItemCountByPublicIdVersion(2812522, (float)1.0);
		assertTrue(count == 1);
	}
}
