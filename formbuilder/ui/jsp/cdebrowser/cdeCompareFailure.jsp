<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/cdebrowser.tld" prefix="cde"%>
<%@page import="oracle.clex.process.jsp.GetInfoBean " %>
<%@page import="gov.nih.nci.ncicb.cadsr.common.html.* " %>
<%@page import="gov.nih.nci.ncicb.cadsr.common.util.* " %>
<%@page import="gov.nih.nci.ncicb.cadsr.common.CaDSRConstants" %>
<%@page import="gov.nih.nci.ncicb.cadsr.common.formbuilder.struts.common.FormConstants" %>
<%@page import="gov.nih.nci.ncicb.cadsr.common.formbuilder.struts.common.NavigationConstants" %>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.struts.common.BrowserFormConstants"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.struts.common.BrowserNavigationConstants"%>
<%@page import="gov.nih.nci.ncicb.cadsr.formbuilder.struts.actions.FormCreateAction" %>
<%@page import="gov.nih.nci.ncicb.cadsr.common.CaDSRConstants" %>

<HTML>
<%
  String urlPrefix = "";

%>
<HEAD>
<TITLE>Form Builder- Compare CDEs</TITLE>
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<LINK rel="stylesheet" TYPE="text/css" HREF="<html:rewrite page='/css/blaf.css' />">
    <SCRIPT LANGUAGE="JavaScript">
<!--


function submitForm(methodName) {
  document.forms[0].<%=NavigationConstants.METHOD_PARAM%>.value=methodName;
  document.forms[0].submit();
}

-->
</SCRIPT>
</HEAD>
<BODY topmargin=0 bgcolor="#ffffff">

<%@ include file="../common/in_process_common_header_inc.jsp"%>

<jsp:include page="../common/tab_inc.jsp" flush="true">
	<jsp:param name="label" value="CDE&nbsp;Compare" />
	<jsp:param name="urlPrefix" value="" />
</jsp:include>

<html:form action="/cdebrowser/doneCompareListAction">
   <html:hidden value="" property="<%=NavigationConstants.METHOD_PARAM%>"/>
   
         
<logic:messagesPresent >
  <table width="100%" align="center">
    <html:messages id="error" >
      <logic:present name="error">
      <tr align="center" >
        <td  class="OraErrorText" >         
          <b><bean:write  name="error"/></b><br>
        </td>
      </tr>
      </logic:present>      
    </html:messages>           
  </table>
</logic:messagesPresent>  
<logic:messagesPresent message="true">
  <table width="100%" align="center">
    <html:messages id="message" 
      message="true">
      <logic:present name="message">
      <tr align="center" >
        <td   class="OraTipLabel" >        
          <b><bean:write  name="message"/></b><br>
        </td>
      </tr>
     </logic:present>
    </html:messages>      
  </table>
</logic:messagesPresent>  
 <br>
      <table width="15%" align="center" cellpadding="1" cellspacing="1" border="0" >      
        <tr >
         <td align="center">
            <a href="javascript:submitForm('<%=BrowserNavigationConstants.DONE_CDE_COMPARE%>')">
                <html:img src='<%=urlPrefix+"../i/backButton.gif"%>' border="0" alt="No"/>
             </a> 
          </td>            
      </tr>      
      </table>  
</html:form>
<%@ include file="/common/common_bottom_border.jsp"%>

</BODY>
</HTML>
