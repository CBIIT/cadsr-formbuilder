package gov.nih.nci.cadsr.formloader.struts2;

import gov.nih.nci.cadsr.formloader.domain.FormCollection;
import gov.nih.nci.cadsr.formloader.domain.FormDescriptor;
import gov.nih.nci.cadsr.formloader.service.common.FormLoaderServiceException;
import gov.nih.nci.cadsr.formloader.service.impl.LoadingServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.opensymphony.xwork2.ActionSupport;

public class LoadFormsAction extends ActionSupport implements SessionAware{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<Integer, String> checkboxes;
	private HttpServletRequest servletRequest;
    private List<FormDescriptor> selectedFormsList = new ArrayList<FormDescriptor>();
    private FormCollection loadedFormCollection;
    private List<FormDescriptor> loadedForms = null;
	ApplicationContext applicationContext = null;
    
    public String execute() {
    	System.out.println("in LoadFormsAction.execute()");
    	servletRequest = ServletActionContext.getRequest();
    	
        try {
    	List<FormDescriptor> validatedFormsList = (List<FormDescriptor>) servletRequest.getSession().getAttribute("validatedForms");
    	
    	if (checkboxes != null && checkboxes.size()>0){
			
			for (int i=0; i<checkboxes.size();i++) {
				Integer key = new Integer(i);
				if (checkboxes.containsKey(key)) {
					String checkboxValue = checkboxes.get(key).toString();
					if (checkboxValue.equals("true")) {
						FormDescriptor selectedForm = validatedFormsList.get(i);
						selectedForm.setSelected(true);
						selectedFormsList.add(selectedForm);
					}
				}
			}
			
		}
    	
    	if (selectedFormsList.size() > 0)
    	{
    		loadFormCollection();
    	}
        }catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
            addActionError(e.getMessage());
 
            return INPUT;
        }
        
    	
    	return SUCCESS;
    }
    
    private void loadFormCollection()
    {
		applicationContext =
				WebApplicationContextUtils.getRequiredWebApplicationContext(
	                                    ServletActionContext.getServletContext()
	                        );
		LoadingServiceImpl loadFormsService = (LoadingServiceImpl)this.applicationContext.getBean("loadService");
		try {
				FormCollection aColl = new FormCollection(selectedFormsList);
				aColl.setCreatedBy((String)servletRequest.getSession().getAttribute("username"));
				aColl.setXmlFileName((String)servletRequest.getSession().getAttribute("filename"));
				aColl.setXmlPathOnServer((String)servletRequest.getSession().getAttribute("upload.file.path"));
				loadedFormCollection = loadFormsService.loadForms(aColl);
				loadedForms = loadedFormCollection.getForms();
	        	servletRequest.getSession().setAttribute("loadedForms", loadedForms);
	        	System.out.println(loadedForms.size()+" Forms loaded");
		} catch (FormLoaderServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
	@Override
	public void setSession(Map<String, Object> arg0) {
		// TODO Auto-generated method stub
		
	}

}
