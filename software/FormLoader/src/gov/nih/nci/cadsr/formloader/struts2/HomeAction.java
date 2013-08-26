package gov.nih.nci.cadsr.formloader.struts2;

import java.util.Map;

import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class HomeAction extends ActionSupport {
	private String username;
	private static final long serialVersionUID = 1L;

	@Override
	public String execute() throws Exception {
		Map session = ActionContext.getContext().getSession();
		username = (String) session.get("userName");
		return SUCCESS;
	}
	
	public String getUsername() {
		return username;
	}
}
