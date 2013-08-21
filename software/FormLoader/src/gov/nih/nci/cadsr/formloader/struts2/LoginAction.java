package gov.nih.nci.cadsr.formloader.struts2;


import gov.nih.nci.ncicb.cadsr.common.persistence.dao.UserManagerDAO;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.opensymphony.xwork2.ActionSupport;

public class LoginAction extends ActionSupport implements SessionAware{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	private String appname; 
	private String [] apps = {"FormBuilder", "FormLoader" };
	SessionMap<String, String> sessionmap;
	
	public String execute() {
		//WebApplicationContext context =
		//		WebApplicationContextUtils.getRequiredWebApplicationContext(
	    //                                ServletActionContext.getServletContext()
	    //                    );
		@SuppressWarnings("resource")
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"/applicationContext-service-test-db.xml");
		//UserManagerDAO userManagerDAOV2 = (UserManagerDAO)context.getBean("userManagerDAO");
		UserManagerDAO userManagerDAOV2 = (UserManagerDAO)applicationContext.getBean("userManagerDAO");

		if (userManagerDAOV2.validUser(username, password)==true) {
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
	}
	
	public String clear()
	{
		sessionmap.invalidate();
		return "success";
	}
}
