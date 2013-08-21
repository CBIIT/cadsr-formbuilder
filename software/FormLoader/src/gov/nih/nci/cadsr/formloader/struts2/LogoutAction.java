package gov.nih.nci.cadsr.formloader.struts2;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import java.util.Map;

public class LogoutAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String execute() throws Exception {
	Map session = ActionContext.getContext().getSession();
	session.remove("userName");
	session.remove("password");
	return SUCCESS;
	}
	}