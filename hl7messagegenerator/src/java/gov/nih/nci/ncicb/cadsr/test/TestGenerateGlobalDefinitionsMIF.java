package gov.nih.nci.ncicb.cadsr.test;

import junit.framework.Test;
import junit.framework.TestSuite;
/**
 *  Test generating GlobalDefinitions MIF message.
 */
public class TestGenerateGlobalDefinitionsMIF extends TestServices{
    public TestGenerateGlobalDefinitionsMIF() {
    }
    
    public static Test suite() {
      TestSuite suite = new TestSuite();
      suite.addTest(new TestServices("testGenerateDCIDefMessage"));
      return suite;
    }   
    
    public static void main(String args[]) {
      if (args.length>0){
          formIdSeq = args[0];
      }
      junit.textui.TestRunner.run(suite());
    }     
}
