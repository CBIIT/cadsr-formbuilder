package gov.nih.nci.ncicb.cadsr.formbuilder.struts.actions;

import gov.nih.nci.ncicb.cadsr.common.CaDSRConstants;
import gov.nih.nci.ncicb.cadsr.common.downloads.GetExcelDownload;
import gov.nih.nci.ncicb.cadsr.common.downloads.GetXMLDownload;
import gov.nih.nci.ncicb.cadsr.common.downloads.impl.GetExcelDownloadImpl;
import gov.nih.nci.ncicb.cadsr.common.downloads.impl.GetXMLDownloadImpl;
import gov.nih.nci.ncicb.cadsr.common.dto.QuestionTransferObject;
import gov.nih.nci.ncicb.cadsr.common.resource.DataElement;
import gov.nih.nci.ncicb.cadsr.common.resource.Form;
import gov.nih.nci.ncicb.cadsr.common.resource.Module;
import gov.nih.nci.ncicb.cadsr.common.resource.NCIUser;
import gov.nih.nci.ncicb.cadsr.common.resource.Question;
import gov.nih.nci.ncicb.cadsr.common.struts.formbeans.CDECartFormBean;
import gov.nih.nci.ncicb.cadsr.common.util.CDEBrowserParams;
import gov.nih.nci.ncicb.cadsr.common.util.ContentTypeHelper;
import gov.nih.nci.ncicb.cadsr.common.util.DTOTransformer;
import gov.nih.nci.ncicb.cadsr.formbuilder.common.FormBuilderException;
import gov.nih.nci.ncicb.cadsr.formbuilder.service.FormBuilderServiceDelegate;
import gov.nih.nci.ncicb.cadsr.formbuilder.struts.common.FormActionUtil;
import gov.nih.nci.ncicb.cadsr.objectCart.CDECart;
import gov.nih.nci.ncicb.cadsr.objectCart.CDECartItem;
import gov.nih.nci.ncicb.cadsr.objectCart.impl.CDECartOCImpl;
import gov.nih.nci.objectCart.client.ObjectCartClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

/**
 * Action class for downloading data from the CDE cart in either xml or excel format
 * 
 * @author Ashwin Mathur
 * @version 1.0
 * @see org.apache.struts.actions.DownloadAction
 *
 */
public class SecureCDECartDownloadAction extends org.apache.struts.actions.DownloadAction {

	@Override
	protected StreamInfo getStreamInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		String fileName = null;
		String type = request.getParameter("type");
		
		if (type.equalsIgnoreCase("xml")) {
			CDECart sessionCart = (CDECart) this.getSessionObject(request, CaDSRConstants.CDE_CART);
		    GetXMLDownload xmlDown = new GetXMLDownloadImpl();
		    xmlDown.generateXMLForCDECart(sessionCart, "cdeCart", null);
		    fileName = xmlDown.getFileName("");
		}
		else if (type.equalsIgnoreCase("excel")) {
			CDECart sessionCart = (CDECart) this.getSessionObject(request, CaDSRConstants.CDE_CART);
		      GetExcelDownload excelDown = new GetExcelDownloadImpl();
		      excelDown.generateExcelForCDECart(sessionCart, "cdeCart", null);
		      fileName = excelDown.getFileName();
		}
		else if (type.equalsIgnoreCase("form"))
			fileName = (String)request.getAttribute("fileName");
		else return null;
		
		final String downFileName = fileName;
	    response.setHeader("Content-Disposition", "attachment; filename="+fileName);
	    
		StreamInfo info = new StreamInfo() {
			public String getContentType() {
				return ContentTypeHelper.getContentType(downFileName);
			}
			public InputStream getInputStream() throws IOException {
				FileInputStream fis = new FileInputStream(downFileName);
				return fis;
			}
		};
		return info;
	}

	/**
	   * Retrieve a session object based on the request and the attribute name.
	   */
	  protected Object getSessionObject(
	    HttpServletRequest req,
	    String attrName) {
	    Object sessionObj = null;
	    HttpSession session = req.getSession(false);

	    if (session != null) {
	      sessionObj = session.getAttribute(attrName);
	    }

	    return sessionObj;
	  }
}
