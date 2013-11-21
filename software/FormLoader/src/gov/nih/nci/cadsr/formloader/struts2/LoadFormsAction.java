package gov.nih.nci.cadsr.formloader.struts2;

import gov.nih.nci.cadsr.formloader.domain.FormCollection;
import gov.nih.nci.cadsr.formloader.domain.FormDescriptor;
import gov.nih.nci.cadsr.formloader.service.common.FormLoaderServiceException;
import gov.nih.nci.cadsr.formloader.service.impl.LoadingServiceImpl;

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

public class LoadFormsAction extends ActionSupport implements SessionAware{
	
	private static Logger logger = Logger.getLogger(LoadFormsAction.class.getName());

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<Integer, String> loadformcheckboxes;
	private HttpServletRequest servletRequest;
    private List<FormDescriptor> selectedFormsList = new ArrayList<FormDescriptor>();
    private FormCollection loadedFormCollection;
    private List<FormDescriptor> loadedForms = null;
    private List<FormDescriptor> otherForms = null;
	ApplicationContext applicationContext = null;
	
	private int[] selectedFormIndices;
    
    public String execute() {
    	System.out.println("in LoadFormsAction.execute()");
    	servletRequest = ServletActionContext.getRequest();
    	
        try {
        	FormCollection aColl = (FormCollection)servletRequest.getSession().getAttribute("formCollection");
        	
        	if (selectedFormIndices != null && selectedFormIndices.length > 0) {
        		setSelectForFormsInCollections(aColl, selectedFormIndices);
        		loadFormCollection(aColl);
        	} else {
        		addActionError("No form has been selected. Unable to procedd.");
        	}
        	
        }catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
            addActionError(e.getMessage());
 
            return INPUT;
        }
        
    	
    	return SUCCESS;
    }
    
    private void loadFormCollection(FormCollection aColl)
    {
		applicationContext =
				WebApplicationContextUtils.getRequiredWebApplicationContext(
	                                    ServletActionContext.getServletContext()
	                        );
		LoadingServiceImpl loadFormsService = (LoadingServiceImpl)this.applicationContext.getBean("loadService");
		try {
				//FormCollection aColl = new FormCollection(selectedFormsList);
				//aColl.setCreatedBy((String)servletRequest.getSession().getAttribute("username"));
				//aColl.setXmlFileName((String)servletRequest.getSession().getAttribute("filename"));
				//aColl.setXmlPathOnServer((String)servletRequest.getSession().getAttribute("upload.file.path"));
				//aColl.setName("Sulas Collection");
				loadedFormCollection = loadFormsService.loadForms(aColl);
				loadedForms = extractLoadedForm(loadedFormCollection);
				otherForms = extractOtherForm(loadedFormCollection);
	        	//servletRequest.getSession().setAttribute("loadedForms", loadedForms);
	        	System.out.println(loadedForms.size()+" Forms loaded");
		} catch (FormLoaderServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    protected List<FormDescriptor> extractLoadedForm(FormCollection aColl) {
    	List<FormDescriptor> loaded = new ArrayList<FormDescriptor>();
    	if (aColl == null)
    		return loaded;
    	
    	List<FormDescriptor> forms = aColl.getForms();
    	for (FormDescriptor form : forms) {
    		if (form.getLoadStatus() == FormDescriptor.STATUS_LOADED)
    			loaded.add(form);
    	}
    	
    	return loaded;
    }
    
    protected List<FormDescriptor> extractOtherForm(FormCollection aColl) {
    	List<FormDescriptor> others = new ArrayList<FormDescriptor>();
    	if (aColl == null)
    		return others;
    	
    	List<FormDescriptor> forms = aColl.getForms();
    	for (FormDescriptor form : forms) {
    		if (form.getLoadStatus() != FormDescriptor.STATUS_LOADED)
    			others.add(form);
    	}
    	
    	return others;
    }
    
	public Map<Integer, String> getLoadformcheckboxes() {
		return loadformcheckboxes;
	}

	public void setLoadformcheckboxes(Map<Integer, String> loadformcheckboxes) {
		this.loadformcheckboxes = loadformcheckboxes;
	}

	public List<FormDescriptor> getSelectedFormsList() {
		return selectedFormsList;
	}

	public FormCollection getLoadedFormCollection() {
		return loadedFormCollection;
	}

	public List<FormDescriptor> getLoadedForms() {
		return loadedForms;
	}

	public int[] getSelectedFormIndices() {
		return selectedFormIndices;
	}

	public void setSelectedFormIndices(int[] selectedFormIndices) {
		this.selectedFormIndices = selectedFormIndices;
	}
	

	public List<FormDescriptor> getOtherForms() {
		return otherForms;
	}

	public void setOtherForms(List<FormDescriptor> otherForms) {
		this.otherForms = otherForms;
	}

	public void setLoadedForms(List<FormDescriptor> loadedForms) {
		this.loadedForms = loadedForms;
	}

	@Override
	public void setSession(Map<String, Object> arg0) {
		// TODO Auto-generated method stub
		
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
			if (!selected && form.getLoadStatus() == FormDescriptor.STATUS_CONTENT_VALIDATED)
				form.setLoadStatus(FormDescriptor.STATUS_SKIPPED_LOADING);
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
    
    public String cancel() {
    	return SUCCESS;
    }

}
