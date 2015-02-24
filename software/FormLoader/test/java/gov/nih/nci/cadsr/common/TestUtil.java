/*L
 * Copyright ScenPro Inc, SAIC-F
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-cdecurate/LICENSE.txt for details.
 */

package gov.nih.nci.cadsr.common;


import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

public class TestUtil {

    private static Logger logger = Logger.getLogger(TestUtil.class.getName());
	
	public static void dumpAllHttpRequests(String marker, HttpServletRequest request) {
		System.out.println("To out-put All the request-attributes received from request - ");
	
		Enumeration enAttr = request.getAttributeNames(); 
		while(enAttr.hasMoreElements()){
		 String attributeName = (String)enAttr.nextElement();
		 System.out.println(marker + " Attribute Name - "+attributeName+", Value - "+(request.getAttribute(attributeName)).toString());
		}
	
		System.out.println("To out-put All the request parameters received from request - ");
	
		Enumeration enParams = request.getParameterNames(); 
		while(enParams.hasMoreElements()){
		 String paramName = (String)enParams.nextElement();
		 System.out.println(marker + " Parameter Name - "+paramName+", Value - "+request.getParameter(paramName));
		}
	}
}
