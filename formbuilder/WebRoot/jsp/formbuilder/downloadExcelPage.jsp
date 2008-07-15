<%@page import="gov.nih.nci.ncicb.cadsr.common.formbuilder.struts.common.FormConstants" %>
<%@page import="gov.nih.nci.ncicb.cadsr.common.formbuilder.struts.common.NavigationConstants" %>
<%
  String action = request.getParameter("action");
  String method = request.getParameter(NavigationConstants.METHOD_PARAM);
  String idseq = request.getParameter(FormConstants.FORM_ID_SEQ);
  
  String url= request.getContextPath() + "/" + action + ".do?" + NavigationConstants.METHOD_PARAM + "=" + method + "&" + FormConstants.FORM_ID_SEQ + "=" + idseq;
 %>
<HTML>
<HEAD>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=windows-1252">
<LINK REL=STYLESHEET TYPE="text/css" HREF="<%=request.getContextPath()%>/css/blaf.css">
<TITLE>Excel Download</TITLE>
</HEAD>
<BODY onLoad= "goPage()">
<SCRIPT LANGUAGE="JavaScript">
<!--
function goPage() {

  document.location.href ="<%=url%>";
  //setTimeOut("window.close();", 10);
  //closeWindow();
}
function closeWindow() {
  close();
}
//-->
</SCRIPT>
<form name="downloadForm">
<br>
<p class="OraHeaderSubSub">Downloading form elements to Excel...</p>

<p><font face="Arial, Helvetica, sans-serif" size="-1" color="#336699">This 
operation may take a few minutes, so please do not close this status window while 
it is in progress. You may close this status window after the download is 
complete.</font></p>


</form>
</BODY>
</HTML>
