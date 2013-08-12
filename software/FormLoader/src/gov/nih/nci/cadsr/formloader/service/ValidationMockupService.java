package gov.nih.nci.cadsr.formloader.service;

import gov.nih.nci.cadsr.formloader.object.FormObj;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class ValidationMockupService implements ValidationServiceI {
	private static ValidationMockupService instance = null;
	
	private ValidationMockupService() {
		
	}
	
	public static ValidationMockupService getInstance() {
		 if(instance == null) {
	         instance = new ValidationMockupService();
	      }
	      return instance;
	}
	
	public  FormObj validateForm(FormObj form) throws Exception {
		String errMessage = "";
		
		if (form.getPublicId() <=0) 
				form.setAction("New Form");
		else if (form.getVersion().equals(""))
				form.setAction("New Version");
		else form.setAction("Update Form");
		
		if  (form.getXmlFilename().endsWith("validation_error.xml") && form.getLongName().startsWith("validation error")) {
			errMessage += "caDSR Form validation error(s) found in Form - public id:"+form.getPublicId()
					+" version:"+form.getVersion()+" name:"+form.getLongName()+"\n";
			errMessage += "Error #1: validation error 1 \n";
			errMessage += "Error #2: validation error 2 \n";
		}
		
		if (!errMessage.equals("")) 
			throw new Exception(errMessage);
		
		return form;
	}
	
	
	
	public  List<FormObj> parseXMLFile(File xmlFile, String filename) throws Exception  {
		String errMessage = "";
		System.out.println("ValidationMockupService - Validating XML file:"+ filename);
		if (filename.endsWith("format_error.xml")) {
			errMessage += "XML format error(s) found in file:"+filename+"\n";
			errMessage += "Error #1: format error 1 \n";
			errMessage += "Error #2: format error 2 \n";
		}
		
			
		List<FormObj> parsedFormsList = new ArrayList<FormObj>();
		int numForms = 5;
		
		int publicId = 0;
		String version = "";
		String name = "";
		int numModules = 2;
		
		for (int i=0; i< numForms; i++) {
			 FormObj one = new FormObj();
			 if (i==0) { // new id
				 publicId = 0;
				 version = "";
				 name = "New Form "+i;
				 numModules = 1;
			 }
			 else if (i==2) { // new version
				 publicId =3479001;
				 version = "";
				 name = "New Version of Existing Form "+1;
			 }
			 else if (filename.endsWith("validation_error.xml") && i==3) { // form with valiation error
				 publicId = 3479000+i;
				 version = "1.0"+i;  
				 name = "validation error in Form "+1;
				 numModules =0;
			 }
			 else {	// existing id and version
				 publicId = 3479000+i;
				 version = "1.0"+i; 
				 name = "Existing Form "+i;
			 }
			 one.setPublicId(publicId);
			 one.setVersion(version); 
			 one.setContext("NCI");
			 one.setLongName(name);
			 one.setType("CRF"); 
			 one.setProtocolName("Test Protocol");
			 one.setWorkflowStatus("DRAFT NEW");
			 one.setXmlFilename(filename);
			 one.setNumModules(numModules);
			 parsedFormsList.add(one);
			 
		 }
		if (!errMessage.equals(""))
			throw new Exception(errMessage);
		
		return parsedFormsList;
	}
	
}
