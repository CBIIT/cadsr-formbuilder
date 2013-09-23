package gov.nih.nci.cadsr.formloader.struts2;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

public class UnloadFormsAction extends ActionSupport implements
SessionAware {
	
	public String execute() {
		return SUCCESS;
	}
	
	@Override
	public void setSession(Map<String, Object> arg0) {
		// TODO Auto-generated method stub
		
	}

}
