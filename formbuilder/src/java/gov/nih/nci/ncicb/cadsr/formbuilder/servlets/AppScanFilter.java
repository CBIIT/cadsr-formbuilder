package gov.nih.nci.ncicb.cadsr.formbuilder.servlets;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AppScanFilter implements Filter {

	private static Log log = LogFactory.getLog(AppScanFilter.class);
	
	private static char[] escapeChars = {'|','&',';','$','%','@','\'','"','\'','\\','+',0x0d,0x0a,',', '<', '>', '(', ')'};
		
	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain filterChain) throws IOException, ServletException {
		Map paramsMap = req.getParameterMap();
		Iterator valuesIter = paramsMap.values().iterator();
		boolean invalid = false;
		while (valuesIter.hasNext()) {
			String[] values = (String[])valuesIter.next();
			for (String value: values) {
				if (isInvalid(value)) {
					invalid = true;
					if (log.isInfoEnabled()) {
						log.info("Request param value ["+value+"] supplied which contains an un-permissible character");
					}
					
					break;
				}
			}
		}
		
		if (invalid) {
			ServletOutputStream out = res.getOutputStream();
			out.println("One of the values supplied in the request contains un-permissible characters.");
			out.println();
			out.println("Please use the browser back button to go back to the requesting page");
		}
		else {
			filterChain.doFilter(req, res);
		}
	}
	
	private boolean isInvalid(String paramValue) {
		boolean invalid = false;
		
		if (paramValue != null && paramValue.length() > 0) {
			for (char escapeChar: escapeChars) {
				if (paramValue.indexOf(escapeChar) != -1) {
					invalid = true;
					break;
				}
			}
		}
		
		return invalid;
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}
