package gov.nih.nci.ncicb.cadsr.formbuilder.struts.common;

import gov.nih.nci.ncicb.cadsr.formbuilder.struts.actions.FormBuilderActionUtils;

public class FormBuilderUtil
{
	public static boolean validateIdSeqRequestParameter(String idSeq)
	{
		if (idSeq != null && idSeq.matches("[^a-zA-Z0-9-]"))  
			return false;
		//we need to validate exact format of ID SEQ string to prevent SQL injections for non-empty parameters
		if ((idSeq != null) && (idSeq.trim().length() > 0)) //we do not invalidate empty strings for format
			return FormBuilderActionUtils.validateIdSeqFormat(idSeq);
		
		return true;
	}
	
	public static boolean validateNumber(String numStr)
	{
		boolean valid = true;
		if (numStr != null && numStr.matches("[^\\d]"))  /*    "^.*(%|<|>|\\$).*$"  */
			valid = false;
				
		return valid;
	}

}
