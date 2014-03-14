package gov.nih.nci.cadsr.formloader.struts2.interceptor;

import java.util.Map;

import org.apache.struts2.dispatcher.SessionMap;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class MyLoggingInterceptor implements Interceptor{

	private static final long serialVersionUID = 1L;
	
	SessionMap<String, String> sessionmap;

	public String intercept(ActionInvocation invocation) throws Exception {

		String className = invocation.getAction().getClass().getName();
		long startTime = System.currentTimeMillis();
		System.out.println("Before calling action: " + className);

		//String result = invocation.invoke();
		String result = null;
		if (isLoginAction(invocation)) {
			result = invocation.invoke();
		} else if (isLoginSessionActive()) {
			result = invocation.invoke();
		} else {
			return "backToLogin";
		}

		long endTime = System.currentTimeMillis();
		System.out.println("After calling action: " + className
				+ " Time taken: " + (endTime - startTime) + " ms");

		return result;
	}

	public void destroy() {
		System.out.println("Destroying MyLoggingInterceptor...");
	}

	public void init() {
		System.out.println("Initializing MyLoggingInterceptor...");
	}
	
	private boolean isLoginAction(ActionInvocation arg0) {
		String name = arg0.getInvocationContext().getName();
		
		return "login".equals(arg0.getInvocationContext().getName())
				|| "actionLogin".equals(arg0.getInvocationContext().getName())
				|| "loginPage".equals(arg0.getInvocationContext().getName())
				|| "reLogin".equals(arg0.getInvocationContext().getName());
	}
	
	private boolean isLoginSessionActive()
	{
		Map map = ActionContext.getContext().getSession();
		//session.remove("userName");
		//session.remove("password");
		sessionmap = (SessionMap)map;
		if ((sessionmap.containsKey("login")) &&
				((sessionmap.get("login") == "true")))
		{
			return true;
		}
		return false;
	}

}
