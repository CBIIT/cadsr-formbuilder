package gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc;

import gov.nih.nci.ncicb.cadsr.common.resource.ValueDomainV2;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext-jdbcdao-test.xml"})
public class JDBCValueDomainDAOV2Test {

	@Autowired
	JDBCValueDomainDAOV2 valueDomainV2Dao;
	
	@Test
	public void test() {
		ValueDomainV2 vd = valueDomainV2Dao.getValueDomainV2ById("");
		
	}

}
