package gov.nih.nci.cadsr.formloader.service;

import gov.nih.nci.cadsr.formloader.object.FormObj;

public class LoadingFormMockupService implements LoadingFormServiceI {
	private static LoadingFormMockupService instance = null;
	
	private LoadingFormMockupService() {
		
	}
	
	public static LoadingFormMockupService getInstance() {
		 if(instance == null) {
	         instance = new LoadingFormMockupService();
	      }
	      return instance;
	}

	public String unloadForm(String publicId, String version) {
		String summary = "Form pubic id:"+publicId+" version:"+version+" unloaded successfully";
		return summary;
	}
	
	public String loadForm(FormObj form) {
		String summary = "Form pubic id:"+form.getPublicId()+" version:"+form.getVersion()+" long name:"+form.getLongName()+" loaded successfully";
		return summary;
	}
	
}
