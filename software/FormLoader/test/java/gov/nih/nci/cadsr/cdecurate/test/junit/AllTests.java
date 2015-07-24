package gov.nih.nci.cadsr.cdecurate.test.junit;

import gov.nih.nci.cadsr.formloader.domain.QuestionDescriptorTest;
import gov.nih.nci.cadsr.formloader.repository.FormLoaderRepositoryImplTest;
import gov.nih.nci.cadsr.formloader.repository.LoadServiceRepositoryImplTest;
import gov.nih.nci.cadsr.formloader.service.common.FormLoaderHelperTest;
import gov.nih.nci.cadsr.formloader.service.common.StaXParserTest;
import gov.nih.nci.cadsr.formloader.service.impl.CollectionRetrievalServiceImplTest;
import gov.nih.nci.cadsr.formloader.service.impl.ContentValidationServiceImplTest;
import gov.nih.nci.cadsr.formloader.service.impl.LoadingServiceImplTest;
import gov.nih.nci.cadsr.formloader.service.impl.UnloadingServiceImplTest;
import gov.nih.nci.cadsr.formloader.service.impl.XmlValidationServiceImplTest;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc.JDBCAdminComponentDAOV2Test;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc.JDBCClassificationSchemeDAOV2Test;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc.JDBCCollectionDAOTest;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc.JDBCContactCommunicationDAOV2Test;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc.JDBCDefinitionDAOTest;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc.JDBCDesignationDAOTest;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc.JDBCFormV2DAOTest;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc.JDBCFormValidValueDAOV2Test;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc.JDBCModuleDAOV2Test;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc.JDBCProtocolDAOV2Test;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc.JDBCQuestionDAOV2Test;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc.JDBCValueDomainDAOV2Test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Setup:
 * 
 * 1. Make sure all FL libraries i.e. software/FormLoader/WebContent/WEB-INF/*.jar are on top of your classpath except FormLoader's src/java and test/java
 * 2. Add software/FormLoader/WebContent/WEB-INF into the classpath of JUnit runner
 * 3. Add software/FormLoader/resources into the classpath of JUnit runner
 * 
 * @author tanj
 *
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ 
	JR366.class,
	JR417.class,
	JR423.class,
	JDBCAdminComponentDAOV2Test.class,
	JDBCClassificationSchemeDAOV2Test.class,
	JDBCCollectionDAOTest.class,
	JDBCContactCommunicationDAOV2Test.class,
	JDBCDefinitionDAOTest.class,
	JDBCDesignationDAOTest.class,
	JDBCFormV2DAOTest.class,
	JDBCFormValidValueDAOV2Test.class,
	JDBCModuleDAOV2Test.class,
	JDBCProtocolDAOV2Test.class,
	JDBCQuestionDAOV2Test.class,
	JDBCValueDomainDAOV2Test.class,
	CollectionRetrievalServiceImplTest.class,
	ContentValidationServiceImplTest.class,
	LoadingServiceImplTest.class,
	UnloadingServiceImplTest.class,
	XmlValidationServiceImplTest.class,
	FormLoaderHelperTest.class,
	StaXParserTest.class,
	FormLoaderRepositoryImplTest.class,
	LoadServiceRepositoryImplTest.class,
	QuestionDescriptorTest.class
})

public class AllTests {
}