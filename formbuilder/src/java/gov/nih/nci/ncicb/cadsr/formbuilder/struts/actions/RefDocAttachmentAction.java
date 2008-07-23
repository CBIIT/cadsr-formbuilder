package gov.nih.nci.ncicb.cadsr.formbuilder.struts.actions;

import gov.nih.nci.ncicb.cadsr.common.dto.AttachmentTransferObject;
import gov.nih.nci.ncicb.cadsr.common.formbuilder.struts.common.FormConstants;
import gov.nih.nci.ncicb.cadsr.common.resource.Attachment;
import gov.nih.nci.ncicb.cadsr.common.util.DBUtil;
import gov.nih.nci.ncicb.cadsr.common.util.logging.Log;
import gov.nih.nci.ncicb.cadsr.common.util.logging.LogFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

public class RefDocAttachmentAction extends Action{

	private static Log log = LogFactory.getLog(RefDocAttachmentAction.class.getName());
	 /**
	  *
	  *
	  * @param mapping The ActionMapping used to select this instance.
	  * @param form The optional ActionForm bean for this request.
	  * @param request The HTTP Request we are processing.
	  * @param response The HTTP Response we are processing.
	  *
	  * @return
	  *
	  * @throws IOException
	  * @throws ServletException
	  */
	 public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
	                                                HttpServletResponse response) throws IOException, ServletException {
	  OutputStream out = null;
	  out = response.getOutputStream();

	  InputStream is = null;
	  String attachmentName = request.getParameter(FormConstants.REFERENCE_DOC_ATTACHMENT_NAME);
	  response.addHeader("Content-Disposition", "inline;filename=\"" + attachmentName + "\"");
//	  response.addHeader("Pragma", "No-cache");
//	  response.addHeader("Cache-Control", "public");
//	  response.addHeader("Expires", "0");

	  // first find out if the attachment is new and saved in the session
	  HttpSession session = request.getSession();
	  Map attMap = (Map)session.getAttribute(FormConstants.REFDOC_ATTACHMENT_MAP);
	  Attachment attachment = getAttachmentFromSession(attMap, attachmentName);

	  if (attachment != null) {
	   FormFile attFile = (FormFile)attMap.get(attachment);

	   is = attFile.getInputStream();
	   response.setContentType(attachment.getMimeType());
	  } else {
	   Blob theBlob = null;

	   Connection conn = null;
	   ResultSet rs = null;
	   PreparedStatement ps = null;
	   try {
	    DBUtil dbUtil = new DBUtil();
	    dbUtil.getConnectionFromContainer();  

	    String sqlStmt = "SELECT blob_content, mime_type, doc_size from sbr.reference_blobs_view where name = ?";
	    log.info(sqlStmt);
	    conn = dbUtil.getConnection();
	    ps = conn.prepareStatement(sqlStmt);
	    ps.setString(1, attachmentName);
	    rs = ps.executeQuery();
	    boolean exists = false;

	    if (rs.next()) {
	     exists = true;

	     String mimeType = rs.getString(2);
	 //    (mimeType);
	     
	     response.setContentType(mimeType);
	     //theBlob = ((OracleResultSet)rs).getBLOB(1);
	     theBlob = rs.getBlob(1);
	     is = theBlob.getBinaryStream();
	     response.setContentLength(rs.getInt(3));
	     response.setBufferSize(4*1024);

	      //Writing to the OutputStream
	      if (is != null) {
	       byte [] buf = new byte[4 * 1024]; // 4K buffer
	    
	       int bytesRead;
	    
	       while ((bytesRead = is.read(buf)) != -1) {
	        out.write(buf, 0, bytesRead);
	       }
	      }
	     response.setStatus(HttpServletResponse.SC_OK);
	    }
	   } catch (Exception ex) {
	    log.error("Exception Caught:", ex);
	   } finally {
	    try {
	      if (is != null)
	       is.close();
	       
	      if (out != null) 
	      {
	       out.flush();
	       out.close();
	      }
	    
	     try
	     {
	       if(ps!=null)
	        ps.close();
	     }
	     catch (Exception e)
	     {       
	     }
	     try
	     {
	       if(rs!=null)
	        rs.close();
	     }
	     catch (Exception e)
	     {       
	     }     
	     if (conn != null)
	      conn.close();

	    //if (db != null) db.closeDB();
	    } catch (Exception ex) {
	     log.error("Exception Caught cleaning up:", ex);
	    } finally { }
	   }
	  }

	  return null;
	 }

	 private Attachment getAttachmentFromSession(Map attMap, String fileName) {
		  if (attMap == null)
		   return null;

		  Iterator iter = attMap.keySet().iterator();

		  while (iter.hasNext()) {
		   Attachment attachment = (AttachmentTransferObject)iter.next();

		   if (attachment.getName().equals(fileName))
		    return attachment;
		  }

		  return null;
		 }

}
