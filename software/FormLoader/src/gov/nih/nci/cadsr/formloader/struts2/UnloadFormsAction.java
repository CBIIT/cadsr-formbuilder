package gov.nih.nci.cadsr.formloader.struts2;

import gov.nih.nci.cadsr.formloader.domain.FormCollection;
import gov.nih.nci.cadsr.formloader.domain.FormDescriptor;
import gov.nih.nci.cadsr.formloader.service.impl.UnloadingServiceImpl;

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

public class UnloadFormsAction extends ActionSupport implements SessionAware {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(UnloadFormsAction.class.getName());
	
	private String[] selectedFormIds;
	
	private List<FormCollection> collectionList = null;
	private List<FormDescriptor> unloadedForms = null;
	ApplicationContext applicationContext = null;
	private Map<String, Object> sessionMap;
	
	public String execute() {
		logger.debug("We are in UnloadFormsAction.execute()");
		
		try {

			applicationContext = WebApplicationContextUtils
					.getRequiredWebApplicationContext(ServletActionContext
							.getServletContext());
			UnloadingServiceImpl unloadService =
					(UnloadingServiceImpl)this.applicationContext.getBean("unloadService");
			String userName = (String)sessionMap.get("username");
			
			collectionList = (List<FormCollection>)sessionMap.get("collectionList");
			
			String error = setSelectForFormsInCollections(collectionList, selectedFormIds);
			if (error.length() > 0) {
				addActionError(error);
				return ERROR;
			}
			
			collectionList = unloadService.unloadCollections(collectionList, userName);
			
			unloadedForms = extractUnloadedForms(collectionList);
			//servletRequest.getSession().setAttribute("unloadedForms", unloadedForms);
			
			return SUCCESS;

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			addActionError(e.getMessage());
		}
		
		return ERROR;
	}
	
	@Override
	public void setSession(Map<String, Object> arg0) {
		sessionMap = arg0;
		
	}
	
	public String[] getSelectedFormIds() {
		return selectedFormIds;
	}

	public void setSelectedFormIds(String[] selectedFormIds) {
		this.selectedFormIds = selectedFormIds;
	}

	public List<FormCollection> getCollectionList() {
		return collectionList;
	}

	public void setCollectionList(List<FormCollection> collectionList) {
		this.collectionList = collectionList;
	}
	
	public List<FormDescriptor> getUnloadedForms() {
		return unloadedForms;
	}

	public void setUnloadedForms(List<FormDescriptor> unloadedForms) {
		this.unloadedForms = unloadedForms;
	}

	protected String setSelectForFormsInCollections(List<FormCollection>collectionList, String[] selectedFormIds) {
	
		if (collectionList == null) 
			return "Collection list is null"; 
		
		if (selectedFormIds == null)
			return "No form has been selected";
	
		for (FormCollection aColl : collectionList) {
			List<FormDescriptor> forms = aColl.getForms();
			if (forms == null) continue;
			
			for (FormDescriptor form : forms) {
				String seqid = form.getFormSeqId();
				
				form.setSelected(setFormSelected(form, selectedFormIds));
				
				logger.debug("=== Form [" + form.getFormIdString() + "|" + seqid + "] selected to be unloaded? " + form.isSelected());
			}
		}
		
		return "";
	}
	
	protected boolean setFormSelected(FormDescriptor form, String[] selectedFormIds) {
		for (String id : selectedFormIds) {
			if (id.equals(form.getFormSeqId()))
				return true;
		}
		
		return false;
	}
	
	protected List<FormDescriptor> extractUnloadedForms(List<FormCollection> collectionList) {
		List<FormDescriptor> unloaded = new ArrayList<FormDescriptor>();
		if (collectionList == null)
			return unloaded;
		
		for (FormCollection aColl : collectionList) {
			List<FormDescriptor> forms = aColl.getForms();
			if (forms == null) continue;
			
			for (FormDescriptor form : forms) {
				if (!form.isSelected()) continue;
				
				form.setCollectionName(aColl.getNameWithRepeatIndicator());
				unloaded.add(form);
			}
		}
		
		return unloaded;
	}

}
