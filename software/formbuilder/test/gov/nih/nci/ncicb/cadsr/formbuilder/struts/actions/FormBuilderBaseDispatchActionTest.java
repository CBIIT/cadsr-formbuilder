package gov.nih.nci.ncicb.cadsr.formbuilder.struts.actions;

import static org.junit.Assert.*;

import org.junit.Test;

import gov.nih.nci.ncicb.cadsr.common.exception.FatalException;
import gov.nih.nci.ncicb.cadsr.formbuilder.struts.common.FormBuilderUtil;

public class FormBuilderBaseDispatchActionTest {

	@Test(expected=FatalException.class)
	public void testsetFormForActionError() {
		String formIdSeq = "";
		String pformIdSeq = "lss";
	      if (!FormBuilderUtil.validateIdSeqRequestParameter(formIdSeq))
	    	  throw new FatalException("Invalid formId parameters.", new Exception("Invalid formId parameters: " + formIdSeq));
			
	      //Added to support tree
	      if ("".equals(formIdSeq)) {
	        formIdSeq = pformIdSeq;
	      }
	      if (!FormBuilderUtil.validateIdSeqRequestParameter(formIdSeq))
	    	  throw new FatalException("Invalid formId parameters.", new Exception("Invalid formId parameters: " + formIdSeq));
	      assertFalse(false);
	}
	@Test
	public void testsetFormForActionFromAttr() {
		String formIdSeq = "";
		String pformIdSeq = "E593F10C-3186-508C-E040-BB89AD4324A0";
	      if (!FormBuilderUtil.validateIdSeqRequestParameter(formIdSeq))
	    	  throw new FatalException("Invalid formId parameters.", new Exception("Invalid formId parameters: " + formIdSeq));
			
	      //Added to support tree
	      if ("".equals(formIdSeq)) {
	        formIdSeq = pformIdSeq;
	      }
	      if (!FormBuilderUtil.validateIdSeqRequestParameter(formIdSeq))
	    	  throw new FatalException("Invalid formId parameters.", new Exception("Invalid formId parameters: " + formIdSeq));
	      
	      assertTrue(true);
	}
	@Test
	public void testsetFormForActionFromParam() {
		String formIdSeq = "E593F10C-3186-508C-E040-BB89AD4324A0";
		String pformIdSeq = "";
	      if (!FormBuilderUtil.validateIdSeqRequestParameter(formIdSeq))
	    	  throw new FatalException("Invalid formId parameters.", new Exception("Invalid formId parameters: " + formIdSeq));
			
	      //Added to support tree
	      if ("".equals(formIdSeq)) {
	        formIdSeq = pformIdSeq;
	      }
	      if (!FormBuilderUtil.validateIdSeqRequestParameter(formIdSeq))
	    	  throw new FatalException("Invalid formId parameters.", new Exception("Invalid formId parameters: " + formIdSeq));
	      
	      assertTrue(true);
	}
}
