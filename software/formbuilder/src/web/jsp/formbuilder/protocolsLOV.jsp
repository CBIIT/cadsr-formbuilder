<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/cdebrowser.tld" prefix="cde"%>
<%@page import="gov.nih.nci.ncicb.cadsr.common.util.* " %>
<%@page import="gov.nih.nci.ncicb.cadsr.common.lov.ProtocolsLOVBean " %>
<%@page import="gov.nih.nci.ncicb.cadsr.common.formbuilder.struts.common.FormConstants" %>
<%@page import="org.owasp.esapi.ESAPI" %>

<%
  TabInfoBean tib = 
    (TabInfoBean)session.getAttribute(FormConstants.PROTOCOLS_LOV_TAB_BEAN);
  ProtocolsLOVBean protolb = 
    (ProtocolsLOVBean)session.getAttribute(FormConstants.PROTOCOLS_LOV_BEAN);
  CommonLOVBean clb = protolb.getCommonLOVBean();
    
  String pageName = "PageId";
  String pageId = "DataElementsGroup";
  String pageUrl = ESAPI.encoder().encodeForHTML("&"+pageName+"="+pageId);

%>

<HTML>
<HEAD>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=WINDOWS-1252">
<LINK REL=STYLESHEET TYPE="text/css" HREF="<%=request.getContextPath()%>/css/blaf.css">
<TITLE>
List of Values - Protocols
</TITLE>
</HEAD>
<BODY>



<SCRIPT LANGUAGE="JavaScript">
<!--
function passback(P_ID, P_NAME) {
   opener.document.forms[0].<%= ESAPI.encoder().encodeForJavaScript(clb.getJsName()) %>.value = P_NAME;
   opener.document.forms[0].<%= ESAPI.encoder().encodeForJavaScript(clb.getJsId()) %>.value = P_ID;
   opener.document.forms[0].<%= ESAPI.encoder().encodeForJavaScript(clb.getJsName()) %>.focus();
   close();
}

function closeOnClick() {
    close();
}

function goPage(pageInfo) {
  document.location.href = "formLOVAction.do?method=getProtocolsLOV&"+pageInfo + "<%= pageUrl%>";
    
}
  
//-->
</SCRIPT>
<%@ include file="../common/in_process_common_header_inc.jsp"%>
<jsp:include page="../common/tab_inc.jsp" flush="true">
  <jsp:param name="label" value="List&nbsp;of&nbsp;Values"/>
  <jsp:param name="urlPrefix" value=""/>
</jsp:include>

<center>
<p class="OraHeaderSubSub">Protocols </p>
</center>

<form method="POST" action="formLOVAction.do">
<INPUT TYPE="HIDDEN" NAME="NOT_FIRST_DISPLAY" VALUE="1">
<INPUT TYPE="HIDDEN" NAME="idVar" VALUE="<%=ESAPI.encoder().encodeForHTML(clb.getJsId())%>">
<INPUT TYPE="HIDDEN" NAME="nameVar" VALUE="<%=ESAPI.encoder().encodeForHTML(clb.getJsName())%>">
<INPUT TYPE="HIDDEN" NAME="method" VALUE="getProtocolsLOV">
<INPUT TYPE="HIDDEN" NAME="contextIdSeq" value="<%= ESAPI.encoder().encodeForHTML((String)request.getAttribute("contextIdSeq")) %>">

<p align="left">
<font face="Arial, Helvetica, sans-serif" size="-1" color="#336699">
  Please enter a keyword. This search will display all protocols which have
  the search criteria in their long name or short name. Wildcard character is *.
</font>
</p>
<center>
<table>
<%= clb.getSearchFields() %>
<tr>

  <% 
    String chkContext = (String)request.getAttribute("chkContext");
    if((chkContext == null) || (!chkContext.equals("true"))) {
  %>
  <td class="fieldtitlebold">Restrict Search to Current Context</td>

<%
  if (clb.isFirstDisplay()) {
%>
  <td class="OraFieldText"><input type="checkbox" name="chkContext" value="yes" CHECKED /></td>
<%
  }
  else {
    if (protolb.getIsContextSpecific()) {
%>
  <td class="OraFieldText"><input type="checkbox" name="chkContext" value="yes" CHECKED /></td>
<%
    }
    else if (!protolb.getIsContextSpecific()) {
%>
  <td class="OraFieldText"><input type="checkbox" name="chkContext" value="yes" /></td>
<%
    }
  }
} else {
%>
<INPUT type="HIDDEN" NAME="chkContext" value="true"/>
<% } %>
</tr>

<TR>
  <TD></TD>
  <TD><input type=submit name="submit" value="Find">&nbsp;
  <INPUT type="button" value="Close" onclick="javascript:closeOnClick()"></TD>
</TR>
</table>

<% 
  if (clb.getTotalRecordCount() != 0) {
%>
<%= clb.getHitList() %>

<p class="OraFieldText">Total Record Count:<B> <%= clb.getTotalRecordCount() %></B></p>
<P>
<%= clb.getPageInfo() %>
<%
  }
  else {
    if (!clb.isFirstDisplay()) {
%>
  <table width="80%" align="center" cellpadding="1" cellspacing="1" border="0" class="OraBGAccentVeryDark">
  <tr class="OraTableColumnHeader">
    <th class="OraTableColumnHeader">Long Name</th>
    <th class="OraTableColumnHeader">Short Name</th>
    <th class="OraTableColumnHeader">Context</th>
    <th class="OraTableColumnHeader">Version</th>
    <th class="OraTableColumnHeader">Workflow Status</th>
    <th class="OraTableColumnHeader">Preferred Definition</th>
  </tr>
  <tr class="OraTabledata">
         <td colspan="6">No protocol matches the search criteria</td>
  </tr>
  </table>
<%
    }
  }
%>
</center>
</form>

<%@ include file="../common/common_bottom_border.jsp"%>
</BODY>
</HTML>