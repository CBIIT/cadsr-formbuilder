package gov.nih.nci.cadsr.formloader.struts2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;

import gov.nih.nci.cadsr.formloader.object.FormObj;
import gov.nih.nci.cadsr.formloader.object.XMLFileObj;
import gov.nih.nci.cadsr.formloader.service.MiscMockupService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class ViewUploadedFormsAction extends ActionSupport {
	private Map<Integer, String> checkboxes;
	private Map<Integer, String> fileids;
	private Vector<String> selectedFileIds = new Vector<String>();
	
	private List<FormObj> uploadedFormsList = new ArrayList<FormObj>();
	
	public String execute() {
		System.out.println("in ViewUploadedFormsAction.execute() ... ");
		
		if (checkboxes != null){
			selectedFileIds = new Vector<String>();		
			for (int i=0; i<checkboxes.size();i++) {
				Integer key = new Integer(i);
				if (checkboxes.containsKey(key)) {
					String checkboxValue = checkboxes.get(key).toString();
					if (checkboxValue.equals("true")) {
						selectedFileIds.add(fileids.get(key).toString());
						
					}
				}
			}
			uploadedFormsList = MiscMockupService.getSelectedForms(selectedFileIds);
		}
		
		
		
		return SUCCESS;
	}
	
	
	  public Map<Integer, String> getCheckboxes() {
	    return checkboxes;
	  }

	  public void setCheckboxes(Map<Integer, String> checkboxes) {
	    this.checkboxes = checkboxes;
	  }


	public Map<Integer, String> getFileids() {
		return fileids;
	}


	public void setFileids(Map<Integer, String> fileids) {
		this.fileids = fileids;
	}


	public List<FormObj> getUploadedFormsList() {
		return uploadedFormsList;
	}


	public void setUploadedFormsList(List<FormObj> uploadedFormsList) {
		this.uploadedFormsList = uploadedFormsList;
	}


	
}
