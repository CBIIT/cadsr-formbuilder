/*L
 * Copyright Oracle Inc, ScenPro Inc, SAIC-F
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-formbuilder/LICENSE.txt for details.
 */

package gov.nih.nci.cadsr.formloader.struts2;

import gov.nih.nci.cadsr.formloader.domain.FormCollection;
import gov.nih.nci.cadsr.formloader.domain.FormDescriptor;
import gov.nih.nci.cadsr.formloader.service.common.FormLoaderServiceException;
import gov.nih.nci.cadsr.formloader.service.impl.ContentValidationServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.opensymphony.xwork2.ActionSupport;



public class ValidateFormsAction extends ActionSupport implements SessionAware{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<Integer, String> checkboxes;
    //private List<FormObj> selectedFormsList = new ArrayList<FormObj>();
    private List<FormDescriptor> selectedFormsList = new ArrayList<FormDescriptor>();
    private HttpServletRequest servletRequest;
	ApplicationContext applicationContext = null;
    
    public String execute() {
    	System.out.println("in ValidateFormsAction.execute()");
    	servletRequest = ServletActionContext.getRequest();
        try {
        	List<FormDescriptor> parsedFormsList = (List<FormDescriptor>) servletRequest.getSession().getAttribute("parsedFormsList");
        	
        	if (checkboxes != null && checkboxes.size()>0){
				
				for (int i=0; i<checkboxes.size();i++) {
					Integer key = new Integer(i);
					if (checkboxes.containsKey(key)) {
						String checkboxValue = checkboxes.get(key).toString();
						if (checkboxValue.equals("true")) {
							FormDescriptor selectedForm = parsedFormsList.get(i);
							selectedForm.setSelected(true);
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
    
    private void validateFormCollection()
    {
		applicationContext =
				WebApplicationContextUtils.getRequiredWebApplicationContext(
	                                    ServletActionContext.getServletContext()
	                        );
		ContentValidationServiceImpl xmlContentValidator = (ContentValidationServiceImpl)this.applicationContext.getBean("contentValidationService");
		try {
				FormCollection aColl = new FormCollection(selectedFormsList);
				xmlContentValidator.validateXmlContent(aColl);
		} catch (FormLoaderServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public List<FormDescriptor> getSelectedFormsList() {
		return selectedFormsList;
	}

	public void setSelectedFormsList(List<FormDescriptor> parsedFormsList) {
		this.selectedFormsList = parsedFormsList;
	}

    public Map<Integer, String> getCheckboxes() {
		return checkboxes;
	}
	public void setCheckboxes(Map<Integer, String> checkboxes) {
		this.checkboxes = checkboxes;
	}


	@Override
	public void setSession(Map<String, Object> arg0) {
		// TODO Auto-generated method stub
		
	}
}

