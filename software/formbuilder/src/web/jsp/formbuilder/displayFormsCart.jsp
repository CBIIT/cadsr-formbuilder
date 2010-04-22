
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/cdebrowser.tld" prefix="cde"%>
<%@ page import="oracle.clex.process.jsp.GetInfoBean "%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.html.* "%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.util.* "%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.CaDSRConstants"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.formbuilder.struts.common.FormConstants"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.formbuilder.struts.common.NavigationConstants"%>
<%
	String urlPrefix = "";
%>
<HTML>
<HEAD>
<TITLE>Display Form Cart</TITLE>
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache"/>
<LINK rel="stylesheet" TYPE="text/css" HREF="<html:rewrite page='/css/blaf.css' />">
<SCRIPT LANGUAGE="JavaScript1.1" SRC="js/checkbox.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
<!--

function submitForm() {
  document.forms[0].submit();
}

function saveItems() {
  if (validateSelection('selectedSaveItems','Please select at least one data element to save to your Form Cart.')) {
   document.forms[0].method.value = 'addItems'
   submitForm();
   return true;
  }
}

function deleteItems() {
  if (validateSelection('selectedDeleteItems','Please select at least one data element to delete from your Form Cart.')) {
    document.forms[0].method.value = 'removeItems'
    submitForm();
  }
}

function ToggleSaveAll(e){
	if (e.checked) {
	    setChecked(1,'selectedSaveItems');
	}
	else {
	    setChecked(0,'selectedSaveItems');
	}
}

function ToggleDeleteAll(e){
	if (e.checked) {
	    setChecked(1,'selectedDeleteItems');
	}
	else {
	    setChecked(0,'selectedDeleteItems');
	}
}

function details(formIdSeq ){
  var urlString="/FormBuilder/formDetailsAction.do?method=getFormDetails&formIdSeq="+formIdSeq;
  newBrowserWin(urlString,'formDetails',800,600)  
}

function retrieveSavedItems() {
  document.location.href = "formsCartAction.do?method=displayFormsCart";
}

-->

</SCRIPT>
</HEAD>
<BODY topmargin=0 bgcolor="#ffffff" topmargin="0">

<% 
  String downloadExcelURL = "formExcelDownload.do?&formIdSeq=";
  String doneURL = "";
  
  String src = request.getParameter("src");
  String modIndex = "";
  String quesIndex = "";
  String urlParams = "";
    
  if ((src != null) && (!"".equals(src))) {
    modIndex = request.getParameter("moduleIndex");
    quesIndex = request.getParameter("questionIndex");
    doneURL= src+".do?method=displayFormCart";
    urlParams = "&src="+src+"&method=displayCDECart&moduleIndex="+modIndex+"&questionIndex="+quesIndex;
  }
  else {
    doneURL="formSearchAction.do";
  }
%>

<%@ include file="../common/in_process_common_header_inc.jsp"%>
<jsp:include page="../common/tab_inc.jsp" flush="true">
  <jsp:param name="label" value="Form&nbsp;Cart"/>
  <jsp:param name="urlPrefix" value=""/>
</jsp:include>
<table>
    <tr>    
      <td align="left" class="AbbreviatedText">
        <bean:message key="cadsr.formbuilder.helpText.cart.secure"/>
      </td>
    </tr>  
</table> 
<%@ include file="showMessages.jsp" %>
<html:form action="/formsCartAction.do">
<html:hidden value="" property="<%=NavigationConstants.METHOD_PARAM%>"/>
<html:hidden property="<%= FormConstants.QUESTION_INDEX %>"/>
<html:hidden property="<%= FormConstants.MODULE_INDEX %>"/>
<html:hidden property="<%= FormConstants.DE_SEARCH_SRC %>"/>
<logic:present name="<%=CaDSRConstants.FORMS_CART%>">
  <table width="80%" align="center" cellpadding="1" cellspacing="1" border="0" class="OraBGAccentVeryDark">
    <tr class="OraTableColumnHeader">
    <logic:notEmpty name="<%=CaDSRConstants.FORMS_CART%>" property = "forms">
      <th>Delete<input type="checkbox" name="deleteAllChk" value="yes" onClick="ToggleDeleteAll(this)"/></th>
    </logic:notEmpty>
      <th>Long Name</th>
      <th>Context</th>
		<th>Type</th>
      <th>Protocol Long Name(s)</th>
      <th>Workflow Status</th>
      <th>Public Id</th>
      <th>Version</th>
    </tr>
  <logic:empty name="<%=CaDSRConstants.FORMS_CART%>" property = "forms">
    <tr class="OraTabledata">
        <td class="OraFieldText" colspan="7">
          Forms Cart is empty. 
        </td>
    </tr>
  </logic:empty>
  <logic:notEmpty name="<%=CaDSRConstants.FORMS_CART%>" property = "forms">
    <logic:iterate id="form" name="<%=CaDSRConstants.FORMS_CART%>" type="gov.nih.nci.ncicb.cadsr.common.dto.FormTransferObject" property="forms">
<%
      String formId = form.getIdseq();
      String detailsURL = "javascript:details(formId)";
%>
      <tr class="OraTabledata">
        <td>
          <input type="checkbox" name="selectedDeleteItems" value="<%= formId %>"/>
        </td>
        <td class="OraFieldText">
          <a href="<%=detailsURL%>">
            <bean:write name="form" property="longName"/>
          </a>
        </td>
        <td class="OraFieldText">
          <bean:write name="form" property="context.name"/>
        </td>
        <td class="OraFieldText">
          <bean:write name="form" property="formType"/>
        </td>
        <td class="OraFieldText">
		<logic:iterate id="proto" name="form" type="gov.nih.nci.ncicb.cadsr.common.resource.Protocol" property="protocols">
			<bean:write name="proto" property="longName"/><br/>
		</logic:iterate>
	  
        </td>
        <td class="OraFieldText">
          <bean:write name="form" property="aslName"/>
        </td>
        <td class="OraFieldText">
	  <bean:write name="form" property="publicId"/>
        </td>
        <td class="OraFieldText">
	  <bean:write name="form" property="version"/>
        </td>
      </tr>
    </logic:iterate>
  </logic:notEmpty>
   </table>
    <br>
    <table width="30%" align="center" cellpadding="1" cellspacing="1" border="0">  
      <TR>
        <td>&nbsp;</td>
      </TR>
      <tr>
        <td>
          <a href="javascript:retrieveSavedItems()">
            <html:img src='<%=urlPrefix+"i/refresh.gif"%>' border="0" alt="Refresh Saved Forms"/> 
          </a>
        </td>
		<logic:notEmpty name="<%=CaDSRConstants.FORMS_CART%>" property = "forms">
        <td>
          <a href="javascript:deleteItems()">
            <html:img src='<%="i/deleteButton.gif"%>' border="0" alt="Delete"/> 
          </a>
        </td> 
		</logic:notEmpty>        
        <td>
          <html:link href="<%=doneURL%>">				
            <html:img src='<%="i/backButton.gif"%>' border="0" alt="Back"/>
          </html:link>             
        </td> 
      </tr>
 </table>
</logic:present>

<table width="10%" align="center" cellpadding="1" cellspacing="1" border="0" >
  <tr>
    <td>
      <a href='#' target="_blank"><html:img src="i/add_more_data_elements.gif" border="0" alt="Add more forms"/></a>
    </td>
	<logic:notPresent name="<%=CaDSRConstants.FORMS_CART%>">
        <td>
          <html:link href="<%=doneURL%>">				
            <html:img src='<%="i/backButton.gif"%>' border="0" alt="Back"/>
          </html:link>             
        </td> 
	</logic:notPresent>  
  </tr>
</table>    

</html:form>
<%@ include file="../common/common_bottom_border.jsp"%>
</body>
</html>