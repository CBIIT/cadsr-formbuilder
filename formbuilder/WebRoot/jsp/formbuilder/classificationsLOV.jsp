<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/cdebrowser.tld" prefix="cde"%>
<%@page import="gov.nih.nci.ncicb.cadsr.common.util.* " %>
<%@page import="oracle.clex.process.PageConstants " %>
<%@page import="gov.nih.nci.ncicb.cadsr.common.ProcessConstants " %>
<%@page import="gov.nih.nci.ncicb.cadsr.common.lov.ClassificationsLOVBean " %>
<%@page import="org.owasp.esapi.ESAPI" %>

<%
  ClassificationsLOVBean cslb = (ClassificationsLOVBean)session.getAttribute(ProcessConstants.CS_LOV);
  CommonLOVBean clb = cslb.getCommonLOVBean();
    
  String pageName = "PageId";
  String pageId = "DataElementsGroup";
  String pageUrl = ESAPI.encoder().encodeForHTML("&"+pageName+"="+pageId);
  
%>

<HTML>
<HEAD>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=WINDOWS-1252">
<LINK REL=STYLESHEET TYPE="text/css" HREF="<%=request.getContextPath()%>/css/blaf.css">
<TITLE>
List of Values - Classifications
</TITLE>
</HEAD>
<BODY topmargin="0">



<SCRIPT LANGUAGE="JavaScript">
//<!--
function passback(P_ID, P_NAME) {
   opener.document.forms[0].<%=ESAPI.encoder().encodeForHTML(clb.getJsName())%>.value = P_NAME;
   opener.document.forms[0]['<%=ESAPI.encoder().encodeForHTML(clb.getJsId())%>'].value = P_ID;
   opener.document.forms[0].<%=ESAPI.encoder().encodeForHTML(clb.getJsName())%>.focus();
   window.close();
}

function closeOnClick() {
    close();
}

var reFloat = /^((\d+(\.\d*)?)|((\d*\.)?\d+))$/
function validate() {
  var csVersion = document.forms[0].SEARCH[1].value;
  if ((csVersion != '')&&(!(reFloat.test(csVersion)))) {
     alert('Enter a valid CS Version.');
     document.forms[0].SEARCH[1].focus();
     return false;
  }
  else {
   return true;
  }
}

function goPage(pageInfo) {
  document.location.href = "classificationLOVAction.do?method=getClassificationsLOV&classificationsLOV=9&"+pageInfo + "<%=pageUrl%>";
}


  
//-->
</SCRIPT>
<%@ include file="../common/in_process_common_header_inc.jsp"%>
<jsp:include page="../common/tab_inc.jsp" flush="true">
  <jsp:param name="label" value="List&nbsp;of&nbsp;Values"/>
  <jsp:param name="urlPrefix" value=""/>
</jsp:include>
<center>
<p class="OraHeaderSubSub">Classifications </p>
</center>
<form method="POST" onSubmit="return validate()" ENCTYPE="application/x-www-form-urlencoded" action="classificationLOVAction.do?method=getClassificationsLOV">

<INPUT TYPE="HIDDEN" NAME="NOT_FIRST_DISPLAY" VALUE="<%=ESAPI.encoder().encodeForHTML("1")%>"/>
<INPUT TYPE="HIDDEN" NAME="idVar" VALUE="<%= ESAPI.encoder().encodeForHTML(clb.getJsId()) %>"/>
<INPUT TYPE="HIDDEN" NAME="nameVar" VALUE="<%= ESAPI.encoder().encodeForHTML(clb.getJsName()) %>"/>
<INPUT TYPE="HIDDEN" NAME="classificationsLOV" VALUE="<%=ESAPI.encoder().encodeForHTML("9") %>"/>
<INPUT TYPE="HIDDEN" NAME="contextIdSeq" value="<%=ESAPI.encoder().encodeForHTML((String)request.getAttribute("contextIdSeq")) %>">
<p align="left">
<font face="Arial, Helvetica, sans-serif" size="-1" color="#336699">
  Please enter the search criteria. Wildcard character is *.
</font>
</p>
<center>
<table>
<%= clb.getSearchFields() %>
<tr>
  <% 
    String chkContext = (String)request.getAttribute("chkContext");    
    if((chkContext == null) || (!chkContext.equals("always"))) {
  %>
  <td class="fieldtitlebold">Restrict Search to Current Context</td>
<%
  if (clb.isFirstDisplay()) {
%>
  <td class="OraFieldText"><input type="checkbox" name="chkContext" value="yes" CHECKED /></td>
<%
  }
  else {
    if (cslb.getIsContextSpecific()) {
%>
  <td class="OraFieldText"><input type="checkbox" name="chkContext" value="yes" CHECKED /></td>
<%
    }
    else if (!cslb.getIsContextSpecific()) {
%>
  <td class="OraFieldText"><input type="checkbox" name="chkContext" value="yes" /></td>
<%
    }
  }
} else {
%>
<INPUT type="HIDDEN" NAME="chkContext" value="always"/>
<% } %>
</tr>

<TR>
  <TD></TD>
  <TD><input type="submit" name="submit"  value="Find" />&nbsp;
  <INPUT type="button" value="Close" onclick="javascript:closeOnClick()"/></TD>
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
  <table width="100%" align="center" cellpadding="1" cellspacing="1" border="0" class="OraBGAccentVeryDark">
  <tr class="OraTableColumnHeader">
    <th class="OraTableColumnHeader">Class Scheme Item</th>
    <th class="OraTableColumnHeader">CSI Public ID Version</th>
    <th class="OraTableColumnHeader">CS Long Name</th>
    <th class="OraTableColumnHeader">CS Public ID Version</th>
    <th class="OraTableColumnHeader">CS Context</th>
    <th class="OraTableColumnHeader">CS Workflow Status</th>
    <th class="OraTableColumnHeader">CS Preferred Definition</th>
    
  </tr>
  <tr class="OraTabledata">
         <td colspan="7">No classification scheme items match the search criteria</td>
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

