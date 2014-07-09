/*L
 * Copyright Oracle Inc, ScenPro Inc, SAIC-F
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-formbuilder/LICENSE.txt for details.
 */

package gov.nih.nci.cadsr.formloader.struts2;

import gov.nih.nci.cadsr.formloader.domain.FormCollection;
import gov.nih.nci.cadsr.formloader.domain.FormDescriptor;
import gov.nih.nci.cadsr.formloader.service.common.FormLoaderHelper;
import gov.nih.nci.cadsr.formloader.service.common.FormLoaderServiceException;
import gov.nih.nci.cadsr.formloader.service.impl.ContentValidationServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.opensymphony.xwork2.ActionSupport;


public class ValidateFormsAction extends ActionSupport implements SessionAware {
	
	private static Logger logger = Logger.getLogger(ValidateFormsAction.class.getName());
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<Integer, String> checkboxes;
    //private List<FormDescriptor> selectedFormsList = new ArrayList<FormDescriptor>();
    private List<FormDescriptor> validatedForms = null;
    private List<FormDescriptor> invalidForms = null;
    private FormCollection validatedFormCollection;
    
    private HttpServletRequest servletRequest;
	ApplicationContext applicationContext = null;
	
	private int[] selectedFormIndices;
	
	private String versioningRulesUrl;
    
    public String execute() {
    	System.out.println("in ValidateFormsAction.execute()");
    	servletRequest = ServletActionContext.getRequest();
        try {
        	//List<FormDescriptor> parsedFormsList = (List<FormDescriptor>) servletRequest.getSession().getAttribute("parsedFormsList");
        	
        	FormCollection aColl = (FormCollection)servletRequest.getSession().getAttribute("formCollection");
        	
        	if (selectedFormIndices != null && selectedFormIndices.length > 0) {
        		setSelectForFormsInCollections(aColl, selectedFormIndices);
        		validateFormCollection(aColl);
        	} else {
        		addActionError("No form has been selected. Unable to procedd.");
        		return INPUT;
        	}
      
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
            addActionError(e.getMessage());
 
            return INPUT;
        }
        return SUCCESS;
    }
    
    private void validateFormCollection(FormCollection aColl)
	    {
			applicationContext =
					WebApplicationContextUtils.getRequiredWebApplicationContext(
		                                    ServletActionContext.getServletContext()
		                        );
			ContentValidationServiceImpl xmlContentValidator = (ContentValidationServiceImpl)this.applicationContext.getBean("contentValidationService");
			try {
				validatedFormCollection = xmlContentValidator.validateXmlContent(aColl);
				validatedForms = extractValidedForms(validatedFormCollection);
				invalidForms = extractInvalidForms(validatedFormCollection);
				
				if (validatedForms.size() == 0) {
					addActionError("All your forms failed DB validation");
					for (FormDescriptor form : invalidForms) {
						addActionError(form.getMessagesInString());
					}
				}
				
				servletRequest.getSession().setAttribute("formCollection", aColl);
				logger.debug(validatedForms.size()+" Forms selected for validation");
			} catch (FormLoaderServiceException e) {
				addActionError("Got FormLoaderServiceException: " + e.getMessage());
			}
	    }
    
    protected List<FormDescriptor> extractValidedForms(FormCollection validatedColl) {
    	List<FormDescriptor> validated = new ArrayList<FormDescriptor>();
    	List<FormDescriptor> forms = validatedColl.getForms();
    	
    	for (FormDescriptor form : forms) {
    		if (form.getLoadStatus() == FormDescriptor.STATUS_CONTENT_VALIDATED)
    			validated.add(form);
    	}
    	
    	return validated;
    }
    
    protected List<FormDescriptor> extractInvalidForms(FormCollection validatedColl) {
    	List<FormDescriptor> invalid = new ArrayList<FormDescriptor>();
    	List<FormDescriptor> forms = validatedColl.getForms();
    	
    	for (FormDescriptor form : forms) {
    		if (form.getLoadStatus() == FormDescriptor.STATUS_CONTENT_VALIDATION_FAILED)
    			invalid.add(form);
    	}
    	
    	return invalid;
    }
    
    protected void setSelectForFormsInCollections(FormCollection aColl, int[] selectedFormIndices) {
    	
		if (aColl == null) {
			logger.error("Collection is null. Unable to verify selection for forms.");
			return; //TODO
		}
		
		List<FormDescriptor> forms = aColl.getForms();
		
		if (forms == null) {
			logger.error("Collection has null form list. Unable to verify selection for forms.");
			return; //TODO
		}
	
		for (FormDescriptor form : forms) {
			boolean selected = isFormSelected(form.getIndex(), selectedFormIndices);
			form.setSelected(selected);
			if (!selected && form.getLoadStatus() == FormDescriptor.STATUS_XML_VALIDATED)
				form.setLoadStatus(FormDescriptor.STATUS_SKIPPED_CONTENT_VALIDATION);
		}
	}
    
    /**
     * Determine whether form is selected by comparing form's index field against the list of selected indices
     * 
     * @param idx
     * @param selectedFormIdx
     * @return
     */
    protected boolean isFormSelected(int formIdx, int[] selectedFormIndices) {
		for (int idx : selectedFormIndices) {
			if (idx == formIdx)
				return true;
		}
		
		return false;
	}
    
    public Map<Integer, String> getCheckboxes() {
		return checkboxes;
	}
	public void setCheckboxes(Map<Integer, String> checkboxes) {
		this.checkboxes = checkboxes;
	}

	public int[] getSelectedFormIndices() {
		return selectedFormIndices;
	}

	public void setSelectedFormIndices(int[] selectedFormIndices) {
		this.selectedFormIndices = selectedFormIndices;
	}

	@Override
	public void setSession(Map<String, Object> arg0) {
		// TODO Auto-generated method stub
		
	}

	public List<FormDescriptor> getValidatedForms() {
		return validatedForms;
	}

	public FormCollection getValidatedFormCollection() {
		return validatedFormCollection;
	}
	
	/**
	 * Cancel action should go back to xml validation page
	 * @return
	 */
	public String cancel() {
		return SUCCESS;
	}

	public String getVersioningRulesUrl() {
		if (versioningRulesUrl == null)
			this.versioningRulesUrl = FormLoaderHelper.getProperty("formbuilder.versioning.url");
		return versioningRulesUrl;
	}

	public void setVersioningRulesUrl(String versioningRulesUrl) {
		this.versioningRulesUrl = versioningRulesUrl;
	}

	public List<FormDescriptor> getInvalidForms() {
		return invalidForms;
	}

	public void setInvalidForms(List<FormDescriptor> invalidForms) {
		this.invalidForms = invalidForms;
	}
	
	
}
