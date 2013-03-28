<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/cdebrowser.tld" prefix="cde"%>
<%--@ page import="oracle.clex.process.jsp.GetInfoBean "--%>
<%@page import="gov.nih.nci.ncicb.cadsr.common.html.* " %>
<%@page import="gov.nih.nci.ncicb.cadsr.common.util.* " %>
<%@page import="gov.nih.nci.ncicb.cadsr.common.CaDSRConstants" %>
<%@page import="gov.nih.nci.ncicb.cadsr.common.formbuilder.struts.common.FormConstants" %>
<%@page import="gov.nih.nci.ncicb.cadsr.common.formbuilder.struts.common.NavigationConstants" %>
<%@page import="gov.nih.nci.ncicb.cadsr.formbuilder.struts.actions.FormCreateAction" %>
<%@page import="gov.nih.nci.ncicb.cadsr.common.CaDSRConstants" %>

<HTML>
<%
  String urlPrefix = "";

%>
<HEAD>
<TITLE>Welcome to Form Builder..</TITLE>
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<LINK rel="stylesheet" TYPE="text/css" HREF="<html:rewrite page='/css/blaf.css' />">
    <SCRIPT LANGUAGE="JavaScript">
<!--


function submitFormToSave(methodName) {
  document.forms[0].<%=NavigationConstants.METHOD_PARAM%>.value=methodName;
  document.forms[0].submit();
}

-->
</SCRIPT>
</HEAD>
<BODY topmargin=0 bgcolor="#ffffff">

<%@ include file="../common/in_process_common_header_inc.jsp"%>

<jsp:include page="../common/tab_inc.jsp" flush="true">
	<jsp:param name="label" value="Save&nbsp;Form" />
	<jsp:param name="urlPrefix" value="" />
</jsp:include>
<%@ include file="showMessages.jsp" %>
<html:form action="/saveFormModuleEditAction.do">
   <html:hidden value="" property="<%=NavigationConstants.METHOD_PARAM%>"/>
   <html:hidden property="moduleIndex"/>
      <table width="80%" align="center" cellpadding="1" cellspacing="1" border="0" >      
        <tr >
          <td align="center" class="OraTipLabel">
             <bean:message key="cadsr.formbuilder.form.edit.module.prompt.save"/> 
          </td>          
        </tr>     
        <tr >
          <td class ="OraPromptText">
             &nbsp;
          </td>          
        </tr>          
      </table>  
      <table width="20%" align="center" cellpadding="1" cellspacing="1" border="0" >      
        <tr >
         <td align="center">
            <a href="javascript:submitFormToSave('<%=NavigationConstants.SAVE_FORM_CHANGES%>')">
                <html:img src='<%=urlPrefix+"i/save.gif"%>' border="0" alt="Save"/>
             </a> 
          </td>   
         <td align="center">
            <a href="javascript:submitFormToSave('<%=NavigationConstants.CANCEL_FORM_CHANGES_MODULE_EDIT%>')">
                <html:img src='<%=urlPrefix+"i/cancel.gif"%>' border="0" alt="Cancel"/>
             </a> 
          </td>            
      </tr>      
      </table>  
</html:form>
<%@ include file="../common/common_bottom_border.jsp"%>

</BODY>
</HTML>
