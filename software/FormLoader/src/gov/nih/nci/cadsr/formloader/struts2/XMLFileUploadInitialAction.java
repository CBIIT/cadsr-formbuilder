package gov.nih.nci.cadsr.formloader.struts2;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.context.ApplicationContext;

import com.opensymphony.xwork2.ActionSupport;

/**
 * This class is the starting point of the Load form feature
 * @author yangs8
 *
 */
public class XMLFileUploadInitialAction extends ActionSupport implements
ServletRequestAware {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ApplicationContext applicationContext = null;

	private HttpServletRequest servletRequest;


	public String execute() {

		return SUCCESS;
	}


	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		this.servletRequest = arg0;

	}



}
