package gov.nih.nci.ncicb.cadsr.test;

import junit.framework.Test;
import junit.framework.TestSuite;
/**
 * Test querying GlobalDefinitions metadata.
 */
public class TestQueryGlobalDefinitionsMetadata extends TestServices{
    public TestQueryGlobalDefinitionsMetadata() {
    }
    
    
    public static Test suite() {
      TestSuite suite = new TestSuite();
      suite.addTest(new TestServices("testQueryGlobalDefinitionMetadata"));
      return suite;
    }
    
    public static void main(String args[]) {
      junit.textui.TestRunner.run(suite());
    }      
}
