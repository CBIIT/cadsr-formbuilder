package gov.nih.nci.ncicb.cadsr.test;

import junit.framework.Test;
import junit.framework.TestSuite;

public class TestQueryInstrumentMetadata extends TestServices{
    public TestQueryInstrumentMetadata() {
    }
    
    public static Test suite() {
      TestSuite suite = new TestSuite();
      suite.addTest(new TestServices("testQueryInstrumentMetadata"));
      return suite;
    }    
    
}
