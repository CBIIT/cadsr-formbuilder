/*L
 * Copyright Oracle Inc, ScenPro Inc, SAIC-F
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-formbuilder/LICENSE.txt for details.
 */

package gov.nih.nci.cadsr.formloader.service;

import gov.nih.nci.cadsr.formloader.object.FormObj;
import gov.nih.nci.cadsr.formloader.object.XMLFileObj;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

public class MiscMockupService {
	
	public static boolean loginUser(String username, String password) {
		if (( username.equals("admin") && password.equals("admin123") )
			|| (username.equals("guest") && password.equals("Nci_gue5t"))
			|| (username.equals("liur")&& password.equals("liur")) )
			return true;
		else return false;
			
	}
	
	private static String data = "filename1, filename2, filename3, filename4, filename5, " +
			"filename6, filename7, filename8, filename9, filename10, filename11, filename12," +
			"testname1, testname2, testname3, testname4, testname5, testname6, testname7";
	private static String data2 = "description1, description2, description3, description4, description5, " +
			"description6, description7, description8, description9, description10, description11, description12," +
			"testDesc1, testDesc2, testDesc3, testDesc4, testDesc5, testDesc6, testDesc7";
	
	public static List<String> getUploadedXMLFilenames() {
		List<String> fileNames = new ArrayList<String>();
		StringTokenizer st = new StringTokenizer(data, ",");

		while (st.hasMoreTokens()) {
			fileNames.add(st.nextToken().trim());
		}
		return fileNames;
	}
	
	public static List<String> getUploadedXMLFiledescriptions() {
		List<String> descriptions = new ArrayList<String>();
		StringTokenizer st = new StringTokenizer(data2, ",");

		while (st.hasMoreTokens()) {
			descriptions.add(st.nextToken().trim());
		}
		return descriptions;
	}
	

	
	public static List<XMLFileObj> getSearchXMLFiles(String filename, String description, String fromDate, String toDate) {
		List<XMLFileObj> xmlFileList = new ArrayList<XMLFileObj>();
		//
		for (int i=0; i<10; i++) {
			int j = i+1;
			XMLFileObj fileObj = new XMLFileObj();
			fileObj.setFileid(j);
			if (filename!= null && !filename.equals(""))
				fileObj.setFilename(filename);
			else fileObj.setFilename("filename"+j);
			
			if (description!= null && !description.equals(""))
				fileObj.setDescription(description);
			else fileObj.setDescription("description"+j);
			
			fileObj.setVersion("v"+i);
			if (i%2==0)
				fileObj.setNumForms(1);
			else fileObj.setNumForms(2);
			fileObj.setUploadedBy("LIUR3");
			fileObj.setUploadedDate(new Date());
			fileObj.setStatus("Loaded");
			xmlFileList.add(fileObj);
		}
		return xmlFileList;
		
	}

	public static List<FormObj> getSelectedForms(Vector<String> selectedFileIds) {
		List<FormObj> uploadedFormsList = new ArrayList<FormObj>();
		for (int i=0; i<selectedFileIds.size(); i++) {
			 FormObj one = new FormObj();
			 one.setPublicId(3479000+i);
			 one.setVersion("1.0");
			 one.setContext("NCI");
			 one.setLongName("Test Form");
			 one.setType("CRF");
			 one.setProtocolName("Test Protocol");
			 one.setWorkflowStatus("DRAFT NEW");
			 one.setXmlFilename("Filename"+selectedFileIds.elementAt(i).toString());
			 uploadedFormsList.add(one);
			 
			 FormObj two = new FormObj();
			 two.setPublicId(3479110+i);
			 two.setVersion("2.0");
			 two.setContext("NCI");
			 two.setLongName("Test Form 2");
			 two.setType("CRF");
			 two.setProtocolName("Test Protocol 2");
			 two.setWorkflowStatus("DRAFT NEW");
			 two.setXmlFilename("Filename"+selectedFileIds.elementAt(i).toString());
			 uploadedFormsList.add(two);
			 
		 }
		return uploadedFormsList;
	}
	
	
	
}
