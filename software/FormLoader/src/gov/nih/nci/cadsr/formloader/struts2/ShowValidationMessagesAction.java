package gov.nih.nci.cadsr.formloader.struts2;

import gov.nih.nci.cadsr.formloader.domain.FormCollection;
import gov.nih.nci.cadsr.formloader.domain.FormDescriptor;
import gov.nih.nci.cadsr.formloader.domain.FormStatus;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

public class ShowValidationMessagesAction extends ActionSupport implements SessionAware {
	private static Logger logger = Logger.getLogger(ShowValidationMessagesAction.class.getName());
	
	List<String> formMessages;
	List<String> moduleMessage;
	List<String> questionMessages;
	
	int formIndexInCollection = -1;
	
	String publicId;
	String version;
	
	private HttpServletRequest servletRequest;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String execute() {
		servletRequest = ServletActionContext.getRequest();
		
		FormCollection coll = (FormCollection) servletRequest.getSession().getAttribute("formCollection");
		if (coll == null) {
			this.addActionError("No collection in session. Unable to retrieve form database validation messages.");
			return ERROR;
		}
		
		FormDescriptor theForm = findSelectedForm(coll, formIndexInCollection);
		if (theForm == null) {
			addActionError("Unable to retrieve a form with index: " + formIndexInCollection);
			return ERROR;
		}
		
		this.publicId = theForm.getPublicId();
		this.version = theForm.getVersion();
		if ((publicId == null || publicId.length() == 0) && 
				theForm.getLoadType() == FormDescriptor.LOAD_TYPE_NEW) {
			publicId = "(New Form - public id will be generated at load.)";
			version = "(New Form - version will be generated at load.)";
		}
		
		
		FormStatus status = theForm.getStructuredStatus(false);
		formMessages = theForm.getFormLevelMessages(status);
		questionMessages = theForm.getQuestionMessages(status);
		 
		 return SUCCESS;
	 }
	
	private FormDescriptor findSelectedForm(FormCollection coll, int formIndexInCollection) {
		if (coll == null) {
			return null;
		}
		
		if (formIndexInCollection < 0) {
			logger.error("In findSelectedForm(), formIndexInCollection is not valid. Check your code");
			return null;
		}
		
		List<FormDescriptor> forms = coll.getForms();
		for (FormDescriptor form : forms) {
			if (form.getIndex() == formIndexInCollection)
				return form;
		}
		
		return null;
	}
	
	
	
	public String getPublicId() {
		return publicId;
	}

	public void setPublicId(String publicId) {
		this.publicId = publicId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public HttpServletRequest getServletRequest() {
		return servletRequest;
	}



	public void setServletRequest(HttpServletRequest servletRequest) {
		this.servletRequest = servletRequest;
	}



	public int getFormIndexInCollection() {
		return formIndexInCollection;
	}



	public void setFormIndexInCollection(int formIndexInCollection) {
		this.formIndexInCollection = formIndexInCollection;
	}



	public List<String> getFormMessages() {
		return formMessages;
	}



	public void setFormMessages(List<String> formMessages) {
		this.formMessages = formMessages;
	}



	public List<String> getModuleMessage() {
		return moduleMessage;
	}



	public void setModuleMessage(List<String> moduleMessage) {
		this.moduleMessage = moduleMessage;
	}



	public List<String> getQuestionMessages() {
		return questionMessages;
	}



	public void setQuestionMessages(List<String> questionMessages) {
		this.questionMessages = questionMessages;
	}



	@Override
	public void setSession(Map<String, Object> arg0) {
		// TODO Auto-generated method stub
		
	}
	
	 

}
