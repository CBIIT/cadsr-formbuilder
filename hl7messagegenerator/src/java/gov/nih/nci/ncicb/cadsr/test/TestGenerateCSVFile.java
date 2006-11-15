package gov.nih.nci.ncicb.cadsr.test;

import junit.framework.Test;
import junit.framework.TestSuite;

public class TestGenerateCSVFile extends TestServices {
    public TestGenerateCSVFile() {
    }
    
    public static Test suite() {
      TestSuite suite = new TestSuite();
      suite.addTest(new TestServices("testGenerateCSVFile"));
      return suite;
    }       

    public static void main(String[] args) {
        if (args.length >0) {
            formIdSeq = args[0];
        }
        junit.textui.TestRunner.run(suite());    }
}
