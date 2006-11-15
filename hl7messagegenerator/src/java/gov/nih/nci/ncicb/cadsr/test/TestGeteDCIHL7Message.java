package gov.nih.nci.ncicb.cadsr.test;

import junit.framework.Test;
import junit.framework.TestSuite;

public class TestGeteDCIHL7Message extends TestServices{
    public TestGeteDCIHL7Message() {
    }
    
    public TestGeteDCIHL7Message(String method) {
       super(method);
    }
    
    public static Test suite() {
      TestSuite suite = new TestSuite();
      suite.addTest(new TestGeteDCIHL7Message("testGeteDCIHL7Message"));
      return suite;
    } 
    
    public static void main(String args[]) {
      junit.textui.TestRunner.run(suite());
    }       
}
