package gov.nih.nci.ncicb.cadsr.formbuilder.struts.actions;

import static org.junit.Assert.*;

import java.util.regex.Pattern;

import org.junit.Test;

public class FormBuilderLOVActionTest {
	private static final Pattern ID_PATTERN = Pattern.compile("[a-zA-Z0-9-]*");
	@Test
	public void testgetProtocolsLOV() {
		String contextIdSeq = "E593F10C-3186-508C-E040-BB89AD4324A0";
		boolean valid = true;
		  if (contextIdSeq != null && valid) {
			  valid = ID_PATTERN.matcher(contextIdSeq).matches();
			  assertTrue(valid);
			  valid &= FormBuilderActionUtils.validateIdSeqFormat(contextIdSeq);
			  assertTrue(valid);
		  }
		assertTrue(valid);
	}
	@Test
	public void testgetProtocolsLOVInvalid() {
		String contextIdSeq = "E593F10C-3186-508C-E040-BB89AD432lss";
		boolean valid = true;
		  if (contextIdSeq != null && valid) {
			  valid = ID_PATTERN.matcher(contextIdSeq).matches();
			  assertTrue(valid);
			  valid &= FormBuilderActionUtils.validateIdSeqFormat(contextIdSeq);
			  assertFalse(valid);
		  }
		assertFalse(valid);
	}
	@Test
	public void testgetProtocolsLOVEmpty() {
		String contextIdSeq = "";
		boolean valid = true;
		  if (contextIdSeq != null && valid) {
			  valid = ID_PATTERN.matcher(contextIdSeq).matches();
			  assertTrue(valid);
			  valid &= FormBuilderActionUtils.validateIdSeqFormat(contextIdSeq);
			  assertFalse(valid);
		  }
		assertFalse(valid);
	}
	@Test
	public void testgetProtocolsLOVNullStr() {
		String contextIdSeq = "null";
		boolean valid = true;
		  if (contextIdSeq != null && valid) {
			  valid = ID_PATTERN.matcher(contextIdSeq).matches();
			  assertTrue(valid);
			  valid &= valid &= FormBuilderActionUtils.validateIdSeqFormat(contextIdSeq);
			  assertFalse(valid);
		  }
		assertFalse(valid);
	}
}
