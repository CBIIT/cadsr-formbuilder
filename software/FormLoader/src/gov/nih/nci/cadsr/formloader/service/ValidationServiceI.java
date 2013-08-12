package gov.nih.nci.cadsr.formloader.service;

import gov.nih.nci.cadsr.formloader.object.FormObj;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public interface ValidationServiceI {
	
	public FormObj validateForm(FormObj form) throws Exception ;
	
	
	public List<FormObj> parseXMLFile(File xmlFile, String filename) throws Exception  ;
}
