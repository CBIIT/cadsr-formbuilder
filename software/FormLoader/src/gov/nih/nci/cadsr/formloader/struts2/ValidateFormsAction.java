/*L
 * Copyright Oracle Inc, ScenPro Inc, SAIC-F
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-formbuilder/LICENSE.txt for details.
 */

package gov.nih.nci.cadsr.formloader.struts2;


import gov.nih.nci.cadsr.formloader.object.FormObj;
import gov.nih.nci.cadsr.formloader.service.LoadingFormMockupService;
import gov.nih.nci.cadsr.formloader.service.MiscMockupService;
import gov.nih.nci.cadsr.formloader.service.ValidationMockupService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;

public class ValidateFormsAction extends ActionSupport implements ServletRequestAware{
	private Map<Integer, String> checkboxes;
    private List<FormObj> selectedFormsList = new ArrayList<FormObj>();
    private HttpServletRequest servletRequest;
    
    
    public String execute() {
    	System.out.println("in ValidateFormsAction.execute()");
        try {
        	List<FormObj> parsedFormsList = (List<FormObj>) servletRequest.getSession().getValue("parsedFormsList");
        	
        	if (checkboxes != null && checkboxes.size()>0){
				
				for (int i=0; i<checkboxes.size();i++) {
					Integer key = new Integer(i);
					if (checkboxes.containsKey(key)) {
						String checkboxValue = checkboxes.get(key).toString();
						if (checkboxValue.equals("true")) {
							FormObj selectedForm = parsedFormsList.get(i);
							selectedForm = ValidationMockupService.getInstance().validateForm(selectedForm);
							
							selectedFormsList.add(selectedForm);
						}
					}
				}
				
			}
        	
        	servletRequest.getSession().putValue("selectedFormsList", selectedFormsList);
        	System.out.println(selectedFormsList.size()+" Forms selected for validation");
        	
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
            addActionError(e.getMessage());
 
            return INPUT;
        }
        return SUCCESS;
    }
 
    
    public List<FormObj> getSelectedFormsList() {
		return selectedFormsList;
	}

	public void setSelectedFormsList(List<FormObj> parsedFormsList) {
		this.selectedFormsList = parsedFormsList;
	}

    public Map<Integer, String> getCheckboxes() {
		return checkboxes;
	}
	public void setCheckboxes(Map<Integer, String> checkboxes) {
		this.checkboxes = checkboxes;
	}
	

    public void setServletRequest(HttpServletRequest servletRequest) {
        this.servletRequest = servletRequest;
 
    }
}