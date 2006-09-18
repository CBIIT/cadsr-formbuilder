package gov.nih.nci.ncicb.cadsr.test;

import gov.nih.nci.ncicb.cadsr.dao.EDCIDAOFactory;
import gov.nih.nci.ncicb.cadsr.dao.InstrumentDAO;
import gov.nih.nci.ncicb.cadsr.dto.ReferenceDocumentAttachment;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class TestQueryInstrumentMessage extends TestServices{
    public TestQueryInstrumentMessage() {
    }
    
    public TestQueryInstrumentMessage(String method) {
       super(method);
    }
    
   
    
    public static Test suite() {
      TestSuite suite = new TestSuite();
      suite.addTest(new TestQueryInstrumentMessage("testQueryInstrumentMessage"));
      return suite;
    } 
    
    public static void main(String args[]) {
      junit.textui.TestRunner.run(suite());
    }        
}
