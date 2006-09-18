package gov.nih.nci.ncicb.cadsr.test;

import junit.framework.Test;
import junit.framework.TestSuite;

public class TestQueryFormReferenceDocuments extends TestServices{
    public TestQueryFormReferenceDocuments() {
    }
    
    public TestQueryFormReferenceDocuments(String method) {
       super(method);
    }    
    
    public static Test suite() {
      TestSuite suite = new TestSuite();
      suite.addTest(new TestQueryFormReferenceDocuments("testQueryReferenceDocuments"));
      return suite;
    }     
}
