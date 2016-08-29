package gov.nih.nci.ncicb.cadsr.formbuilder.struts.common;

import static org.junit.Assert.*;

import org.junit.Test;

public class FormBuilderUtilTest {

	@Test
	public void test() {
		String idSeq = "E593F10C-3186-508C-E040-BB89AD4324A0";
		assertFalse((idSeq != null && idSeq.matches("[^a-zA-Z0-9-]")));
		boolean received = FormBuilderUtil.validateIdSeqRequestParameter(idSeq);
		assertTrue(received);
	}
	@Test
	public void testInvalidString() {
		String idSeq = "E593F10C-3186-508C-E040-BB89AD432pwd";
		assertFalse((idSeq != null && idSeq.matches("[^a-zA-Z0-9-]")));
		boolean received = FormBuilderUtil.validateIdSeqRequestParameter(idSeq);
		assertFalse(received);
	}
}
