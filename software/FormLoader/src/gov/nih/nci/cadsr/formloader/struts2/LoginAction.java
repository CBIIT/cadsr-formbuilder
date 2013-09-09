<<<<<<< HEAD
package gov.nih.nci.cadsr.formloader.struts2;


import gov.nih.nci.ncicb.cadsr.common.persistence.dao.UserManagerDAO;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class LoginAction extends ActionSupport implements SessionAware, ServletRequestAware{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private WebApplicationContext applicationContext;
    private HttpServletRequest servletRequest;
    
	private String username;
	private String password;
	private String appname; 
	private String [] apps = {"FormBuilder", "FormLoader" };
	SessionMap<String, String> sessionmap;
	
	public String execute() {
		applicationContext =
				WebApplicationContextUtils.getRequiredWebApplicationContext(
	                                    ServletActionContext.getServletContext()
	                        );
		UserManagerDAO userManagerDAOV2 = (UserManagerDAO)applicationContext.getBean("userManagerDAO");

		if (userManagerDAOV2.validUser(username, password)==true) {
			Map session = ActionContext.getContext().getSession();
			setSession(session);
			return SUCCESS;
		} else
		{
			return ERROR;
		}

	}
	
	public String cancel() {
		return SUCCESS;
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

	@Override
	public void setSession(Map<String, Object> map) {
		sessionmap = (SessionMap)map;
		sessionmap.put("login","true");
		sessionmap.put("username",getUsername());
	}
	
	public String clear()
	{
		sessionmap.invalidate();
		return "success";
	}

	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		servletRequest = arg0;
		
	}
=======
/*L
 * Copyright Oracle Inc, ScenPro Inc, SAIC-F
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-formbuilder/LICENSE.txt for details.
 */

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

>>>>>>> refs/remotes/origin/Formbuilder-4.0.4
}
