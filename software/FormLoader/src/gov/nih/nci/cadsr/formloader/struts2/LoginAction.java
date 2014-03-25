package gov.nih.nci.cadsr.formloader.struts2;


import gov.nih.nci.cadsr.formloader.service.common.FormLoaderHelper;
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
	boolean clear = false;
	
	public String execute() {
		applicationContext =
				WebApplicationContextUtils.getRequiredWebApplicationContext(
	                                    ServletActionContext.getServletContext()
	                        );
		UserManagerDAO userManagerDAOV2 = (UserManagerDAO)applicationContext.getBean("userManagerDAO");

		if (userManagerDAOV2.validUser(username, password)==true) {
			//Map session = ActionContext.getContext().getSession();
			//setSession(session);
			this.sessionmap.put(FormLoaderActionConstants.KEY_LOGIN, "true");
			this.sessionmap.put(FormLoaderActionConstants.KEY_USER_NAME, username);
			
			FormLoaderHelper.OutputJaxpImplementationInfo();
			
			return SUCCESS;
		} else {
			addActionError("Invalid login. Please try again.");
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
	}
	
	
	
	public String clear()
	{
		sessionmap.invalidate();
		return SUCCESS;
	}

	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		servletRequest = arg0;
		
	}
	
	public void validateClear(){
		clear = true;
	}
	
}
