/*L
 * Copyright Oracle Inc, ScenPro Inc, SAIC-F
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-formbuilder/LICENSE.txt for details.
 */

package gov.nih.nci.cadsr.formloader.struts2.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class MyLoggingInterceptor implements Interceptor{

	private static final long serialVersionUID = 1L;

	public String intercept(ActionInvocation invocation) throws Exception {

		String className = invocation.getAction().getClass().getName();
		long startTime = System.currentTimeMillis();
		System.out.println("Before calling action: " + className);

		String result = invocation.invoke();

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

}
