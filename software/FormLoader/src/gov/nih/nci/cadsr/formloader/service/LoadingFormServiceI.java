package gov.nih.nci.cadsr.formloader.service;

import gov.nih.nci.cadsr.formloader.object.FormObj;

public interface LoadingFormServiceI {

	public String unloadForm(String publicId, String version);
	
	public String loadForm(FormObj form) ;
	
}
