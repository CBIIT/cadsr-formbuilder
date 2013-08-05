<%--L
  Copyright Oracle Inc, ScenPro Inc, SAIC-F

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/cadsr-formbuilder/LICENSE.txt for details.
L--%>

<%@ page contentType="text/html;charset=windows-1252"%>
<%@page import="gov.nih.nci.ncicb.cadsr.common.util.* " %>
<%@page import="gov.nih.nci.ncicb.cadsr.common.CaDSRConstants"%>
<%@page import="gov.nih.nci.ncicb.cadsr.common.resource.Context"%>
<%@page import="java.util.HashMap"%>
<%@page import="gov.nih.nci.ncicb.cadsr.contexttree.TreeConstants"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/cdebrowser.tld" prefix="cde"%>

<%
  String ctepUser = (String)pageContext.getAttribute("accessValue");
  String treeURL;
  String formbuilderURL;
    treeURL = 
      "/jsp/treeLoader.jsp?"+
      "&treeParams="+TreeConstants.TREE_TYPE_URL_PARAM +":" + 
      TreeConstants.FORM_SEARCH_TREE + ";" +
      TreeConstants.FUNCTION_NAME_URL_PARAM + ":" +
      TreeConstants.FORM_SEARCH_FUNCTION + ";" +
      "&treeName=formTree" +
      "&skin=CDEBrowser1";
    formbuilderURL = "/jsp/formbuilder/formResults.jsp";

    System.out.println(formbuilderURL + " tree url " + treeURL);
    
%>
<HTML>
<HEAD>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=windows-1252">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<TITLE>
FormBuilder
</TITLE>
</HEAD>
  <jsp:useBean id="requestMap" scope="request" class="java.util.HashMap"/>
  
<frameset rows="30,*">
  <html:frame page="/jsp/common/topHeader.jsp" frameborder="0" scrolling = "no" frameName="tree_header"/>
  <frameset cols="25%,*">
    <frameset rows="7%,*">
       <html:frame page="/jsp/common/tree_hdr.jsp"
              name="requestMap"
              frameborder="0"
              frameName="tree_header"
              scrolling = "no"/>
       <html:frame page='<%=treeURL%>'
              name="requestMap"
              frameborder="0"
              frameName="tree"/>              
    </frameset>
       <html:frame action="/setMessagesForFrameAction?method=setMessagesFormKeys"
              name="requestMap"
              frameborder="0"
              frameName="body"/>      
  </frameset>
</frameset>   
</HTML>
