package gov.nih.nci.ncicb.cadsr.test;

import junit.framework.Test;
import junit.framework.TestSuite;
/**
 * Test generating an Instrument HL7 message.
 */
public class TestGenerateInstrumentHL7 extends TestServices {
    public TestGenerateInstrumentHL7() {
    }
    
    public TestGenerateInstrumentHL7(String method) {
       super(method);
    }    
    
    public static Test suite() {
      TestSuite suite = new TestSuite();
      suite.addTest(new TestGenerateInstrumentHL7("testGenerateDCIHL7Message"));
      return suite;
    } 
    
    public static void main(String args[]) {
      if (args.length >0) {
          formIdSeq = args[0];
      }
      junit.textui.TestRunner.run(suite());
    }       
    
}
