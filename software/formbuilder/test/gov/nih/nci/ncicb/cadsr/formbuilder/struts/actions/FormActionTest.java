package gov.nih.nci.ncicb.cadsr.formbuilder.struts.actions;

import static org.junit.Assert.*;

import org.junit.Test;

public class FormActionTest {

	@Test
	public void testIsValidParmeter() {
    	//validate
    	String idSeq = "E593F10C-3186-508C-E040-BB89AD4324A0";
    	boolean result = true;
    	if ((idSeq != null) && (! idSeq.isEmpty())) {
    		if (! FormBuilderActionUtils.validateIdSeqFormat(idSeq)) {
    			
    			result = false;
    		}
    	}
    	assertTrue(result);
	}
	@Test
	public void testInvalidParmeter() {
    	//validate
    	String idSeq = "E593F10C-3186-508C-E040-BB89AD432|ls";
    	boolean result = true;
    	if ((idSeq != null) && (! idSeq.isEmpty())) {
    		if (! FormBuilderActionUtils.validateIdSeqFormat(idSeq)) {
    			
    			result = false;
    		}
    	}
    	assertFalse(result);
	}
	@Test
	public void testInvalidParmeterLen() {
    	//validate
    	String idSeq = "E593F10C-3186-508C-E040-BB89AD4324A0|ls";
    	boolean result = true;
    	if ((idSeq != null) && (! idSeq.isEmpty())) {
    		if (! FormBuilderActionUtils.validateIdSeqFormat(idSeq)) {
    			
    			result = false;
    		}
    	}
    	assertFalse(result);
	}

}
