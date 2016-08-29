package gov.nih.nci.ncicb.cadsr.formbuilder.struts.actions;

import static org.junit.Assert.*;

import org.junit.Test;

public class FormBuilderSecureBaseDispatchActionTest {

	@Test
	public void testIdSeqGood() {
		String idSeq = "E593F10C-3186-508C-E040-BB89AD4324A0";
		boolean received = true;
		  if ((idSeq != null) && (! idSeq.isEmpty())) {//check format if not empty
				if (! FormBuilderActionUtils.validateIdSeqFormat(idSeq)) {
					received = false;
				}
			}
		  assertTrue(received);
	}
	@Test
	public void testIdSeqBad() {
		String idSeq = "E593F10C-3186-508C-E040-BB89AD4324A0|llss";
		boolean received = true;
		  if ((idSeq != null) && (! idSeq.isEmpty())) {//check format if not empty
				if (! FormBuilderActionUtils.validateIdSeqFormat(idSeq)) {
					received = false;
				}
			}
		  assertFalse(received);
	}

}
