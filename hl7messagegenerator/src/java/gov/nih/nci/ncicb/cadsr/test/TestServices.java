package gov.nih.nci.ncicb.cadsr.test;

import gov.nih.nci.ncicb.cadsr.service.*;


import junit.framework.Test;
import junit.framework.TestCase;

import junit.framework.TestSuite;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

/**
 * Unit tests for the service layer.
 */
public class TestServices extends TestCase {
    BeanFactory beanFactory;
    
    public TestServices() {
    }
    
    public TestServices(String method) {
      super(method);
    }    
    
    public void tearDown() {
      beanFactory = null;    
    }
    
    public void setUp() {
       ClassPathResource resource = new ClassPathResource("beans.xml");
       beanFactory = new XmlBeanFactory(resource);
    }
    
    
    public void testQueryMetadataService() {
      try {
        QueryMetadataService queryMetadataService = (QueryMetadataService)beanFactory.getBean("queryMetadataService");
        queryMetadataService.getInstrumentMetaData("1B4FBBDD-9FD4-5F94-E044-0003BA0B1A09");
      }
      catch(Exception e){
          e.printStackTrace();
          fail(e.getMessage());
      }
    }
    
    public void testQueryInstrumentMetadata() {
      try {
        QueryMetadataService queryMetadataService = (QueryMetadataService)beanFactory.getBean("queryMetadataService");
        queryMetadataService.getInstrumentMetaData("1B4FBBDD-9FD4-5F94-E044-0003BA0B1A09");
      }
      catch(Exception e){
          e.printStackTrace();
          fail(e.getMessage());
      }
    }
    
    public void testQueryGlobalDefinitionMetadata() {
      try {
        QueryMetadataService queryMetadataService = (QueryMetadataService)beanFactory.getBean("queryMetadataService");
        queryMetadataService.getGlobalDefinitions("1B4FBBDD-9FD4-5F94-E044-0003BA0B1A09");
      }
      catch(Exception e){
          e.printStackTrace();
          fail(e.getMessage());
      }
    }
    
    public void testGenerateDCIDefMessage() {
      try {
        GenerateMessageService generateMessageService = (GenerateMessageService)beanFactory.getBean("generateMessageService");
        String message = generateMessageService.generateMessage("1B4FBBDD-9FD4-5F94-E044-0003BA0B1A09","user", generateMessageService.GLOBAL_DEFINITIONS_MIF);
        System.out.println("Message :\n"+message);
      }
      catch(Exception e){
          e.printStackTrace();
          fail(e.getMessage());
      }
    }
    
    public static Test suite() {
      TestSuite suite = new TestSuite();
     // suite.addTest(new TestServices("testGenerateDCIDefMessage"));
      suite.addTest(new TestServices("testQueryGlobalDefinitionMetadata"));
      //suite.addTest(new TestServices("testQueryInstrumentMetadata"));
      return suite;
    }

    public static void main(String args[]) {
      junit.textui.TestRunner.run(suite());
    }    
}
