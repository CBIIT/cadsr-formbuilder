package gov.nih.nci.ncicb.cadsr.formbuilder.struts.actions;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormBuilderActionUtils {
	public static final int EXPECTED_ID_SEQ_LENGTH = 36;

	//expected format example: D6CA1C24-3726-672B-E034-0003BA12F5E7
	public static final String ID_SEQ_PATTERN_STRING = "^[A-Z0-9]{8}\\-[A-Z0-9]{4}\\-[A-Z0-9]{4}\\-[A-Z0-9]{4}\\-[A-Z0-9]{12}$";
	public static boolean validateIdSeqFormat(String idSeq) {
		if ((idSeq != null) && (idSeq.length() == EXPECTED_ID_SEQ_LENGTH)) {
			Pattern p = Pattern.compile(ID_SEQ_PATTERN_STRING);
			Matcher m = p.matcher(idSeq);
			return m.matches();
		}
		else return false;
	}
}
