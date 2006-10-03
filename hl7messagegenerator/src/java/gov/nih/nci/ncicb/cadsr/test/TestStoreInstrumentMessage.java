package gov.nih.nci.ncicb.cadsr.test;

import gov.nih.nci.ncicb.cadsr.dao.EDCIDAOFactory;
import gov.nih.nci.ncicb.cadsr.dao.GlobalDefinitionsDAO;
import gov.nih.nci.ncicb.cadsr.dao.InstrumentDAO;

import java.util.Date;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
/**
 * Test storing Instrument message.
 */
public class TestStoreInstrumentMessage extends TestServices {


    public TestStoreInstrumentMessage() {
    }
    
    public TestStoreInstrumentMessage(String method) {
       super(method);
    }
    public void testStoreInstrumentMessage() {
        try {
            EDCIDAOFactory daoFactory = (EDCIDAOFactory)beanFactory.getBean("daoFactory");
            InstrumentDAO instrumentDAO = daoFactory.getInstrumentDAO();
            String rdIdSeq = instrumentDAO.storeInstrumentHL7Message("1B4FBBDD-9FD4-5F94-E044-0003BA0B1A09",testInstrumentMessage,new Date(),"user");
            System.out.println("Reference Document idseq ->"+rdIdSeq);
        }
        catch(Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }    
    

    
    public static Test suite() {
      TestSuite suite = new TestSuite();
      suite.addTest(new TestStoreInstrumentMessage("testStoreInstrumentMessage"));
      return suite;
    } 
    
    public static void main(String args[]) {
      junit.textui.TestRunner.run(suite());
    }     
}
