package gov.nih.nci.cadsr.formloader.struts2;

import gov.nih.nci.cadsr.formloader.service.MiscMockupService;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;

public class LoginAction extends ActionSupport implements ServletRequestAware	{
	private String username;
	private String password;
	private String appname; 
	private String [] apps = {"FormBuilder", "FormLoader" };

	private HttpServletRequest servletRequest;
	
	public String execute() {
		/*
		System.out.println("excecuting login"+ this.appname);
		if (!this.appname.equals("FormLoader")) {
			addActionError(getText("error.unimplemented"));
			return ERROR;
		}
		*/
		if (MiscMockupService.loginUser(username, password)) {
			//servletRequest.getSession().putValue("nciUser", this.username);
			servletRequest.getSession().setAttribute("nciUser", this.username);
			return SUCCESS;
		}
		else {
			addActionError(getText("error.login"));
			return ERROR;
		}
		
	}
	

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public List<String> getAppList() {
		
		return Arrays.asList(apps);
		
	}
	
	public String [] getApps() {
		return this.apps;
	}


	public String getAppname() {
		return appname;
	}


	public void setAppname(String appname) {
		this.appname = appname;
	}


    public void setServletRequest(HttpServletRequest servletRequest) {
        this.servletRequest = servletRequest;
 
    }

}
