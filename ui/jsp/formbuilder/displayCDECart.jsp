<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/cdebrowser.tld" prefix="cde"%>
<%@ page import="oracle.clex.process.jsp.GetInfoBean "%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.html.* "%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.util.* "%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.CaDSRConstants"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.formbuilder.struts.common.FormConstants"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.formbuilder.struts.common.NavigationConstants"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.CaDSRConstants"%>
<%
String urlPrefix = "";
%>
<HTML>
<HEAD>
<TITLE>Display CDE Cart</TITLE>
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache"/>
<LINK rel="stylesheet" TYPE="text/css" HREF="<html:rewrite page='/css/blaf.css' />">
<SCRIPT LANGUAGE="JavaScript1.1" SRC="jsLib/checkbox.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
<!--

function submitForm() {
  document.forms[0].submit();
}

function saveItems() {
  if (validateSelection('selectedSaveItems','Please select at least one data element to save to your CDE Cart.')) {
   document.forms[0].method.value = 'addItems'
   submitForm();
   return true;
  }
}

function deleteItems() {
  if (validateSelection('selectedDeleteItems','Please select at least one data element to delete from your CDE Cart.')) {
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

function details(linkParms ){
  var urlString="search?dataElementDetails=9" + linkParms + "&PageId=DataElementsGroup"+"&queryDE=yes";
  newBrowserWin(urlString,'deDetails',800,600)
  
}

function retrieveSavedItems() {
  document.location.href = "formCDECartAction.do?method=displayCDECart";
}

-->

</SCRIPT>
</HEAD>
<BODY topmargin=0 bgcolor="#ffffff" topmargin="0">

<% 
System.out.println("formbuilder contextpath: " + request.getContextPath());

  String downloadXMLURL = "javascript:fileDownloadWin('cdebrowser/downloadXMLPage.jsp?src=cdeCart','xmlWin',500,200)";
  String downloadExcelURL = "javascript:fileDownloadWin('cdebrowser/downloadExcelPage.jsp?src=cdeCart','excelWin',500,200)";
  
  String doneURL = "";
  
  String src = request.getParameter("src");
  String modIndex = "";
  String quesIndex = "";
  String urlParams = "";
    
  if ((src != null) && (!"".equals(src))) {
    modIndex = request.getParameter("moduleIndex");
    quesIndex = request.getParameter("questionIndex");
    doneURL= src+".do?method=displayCDECart&moduleIndex="+modIndex+"&questionIndex="+quesIndex;
    urlParams = "&src="+src+"&method=displayCDECart&moduleIndex="+modIndex+"&questionIndex="+quesIndex;
  }
  else {
    doneURL="formSearchAction.do";
  }
%>
<%  
  if ((src != null) && (!"".equals(src))) {
%>
  <%@ include file="../common/in_process_common_header_inc.jsp"%>
<%
  }
  else {
%>
<jsp:include page="../common/common_cdebrowser_header_jsp_inc.jsp" flush="true">
  <jsp:param name="loginDestination" value="formCDECartAction.do?method=displayCDECart"/>
</jsp:include>
<%
  }
%>
<jsp:include page="../common/tab_inc.jsp" flush="true">
  <jsp:param name="label" value="CDE&nbsp;Cart"/>
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
<html:form action="/formCDECartAction.do">
<html:hidden value="" property="<%=NavigationConstants.METHOD_PARAM%>"/>
<html:hidden property="<%= FormConstants.QUESTION_INDEX %>"/>
<html:hidden property="<%= FormConstants.MODULE_INDEX %>"/>
<html:hidden property="<%= FormConstants.DE_SEARCH_SRC %>"/>
<logic:present name="<%=CaDSRConstants.CDE_CART%>">
<logic:notEmpty name="<%=CaDSRConstants.CDE_CART%>" property = "dataElements">
    <table cellpadding="0" cellspacing="0" width="80%" align="center">
      <tr>
        <td nowrap>
          <b><a href="<%=downloadExcelURL%>" >[Download Data Elements to Excel]</a></b> &nbsp;&nbsp;
          <b><a href="<%=downloadXMLURL%>" >[Download Data Elements as XML]</a></b> &nbsp;&nbsp;
        </td>
      </tr>
      <tr>
        <td width="100%" nowrap><img height=2 src="i/beigedot.gif" width="99%" align=top border=0> </td>
      </tr>
    </table>
</logic:notEmpty>
  <table width="80%" align="center" cellpadding="1" cellspacing="1" border="0" class="OraBGAccentVeryDark">
    <tr class="OraTableColumnHeader">
    <logic:notEmpty name="<%=CaDSRConstants.CDE_CART%>" property = "dataElements">
      <th>Save<input type="checkbox" name="saveAllChk" value="yes" onClick="ToggleSaveAll(this)"/> </th>
      <th>Delete<input type="checkbox" name="deleteAllChk" value="yes" onClick="ToggleDeleteAll(this)"/></th>
    </logic:notEmpty>
      <th>Long Name</th>
      <th>Doc Text</th>
      <th>Context</th>
      <th>Registration Status</th>
      <th>Workflow Status</th>
      <th>Public Id</th>
      <th>Version</th>
    </tr>
  <logic:empty name="<%=CaDSRConstants.CDE_CART%>" property = "dataElements">
    <tr class="OraTabledata">
        <td class="OraFieldText" colspan="7">
          CDE Cart is empty. 
        </td>
    </tr>
  </logic:empty>
  <logic:notEmpty name="<%=CaDSRConstants.CDE_CART%>" property = "dataElements">
    <logic:iterate id="de" name="<%=CaDSRConstants.CDE_CART%>" type="gov.nih.nci.ncicb.cadsr.common.resource.CDECartItem" property="dataElements">
<%
      String deId = de.getId();
      String detailsURL = "javascript:details('&p_de_idseq="+deId +"')";
%>
      <tr class="OraTabledata">
      	<td>
            <logic:equal name="de" property="persistedInd" value="true">
              &nbsp;       
            </logic:equal>
            <logic:notEqual name="de" property="persistedInd" value="true">
              <input type="checkbox" name="selectedSaveItems" value="<%=de.getId()%>"/>
            </logic:notEqual>
        </td>
        <td>
          <input type="checkbox" name="selectedDeleteItems" value="<%=de.getId()%>"/>
        </td>
        <td class="OraFieldText">
          <a href="<%=detailsURL%>">
            <bean:write name="de" property="item.longName"/>
          </a>
        </td>
        <td class="OraFieldText">
          <bean:write name="de" property="item.longCDEName"/>
        </td>
        <td class="OraFieldText">
          <bean:write name="de" property="item.contextName"/>
        </td>
        <td class="OraFieldText">
	  <bean:write name="de" property="item.registrationStatus"/>
        </td>
        <td class="OraFieldText">
          <bean:write name="de" property="item.aslName"/>
        </td>
        <td class="OraFieldText">
	  <bean:write name="de" property="item.publicId"/>
        </td>
        <td class="OraFieldText">
	  <bean:write name="de" property="item.version"/>
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
          <a href="javascript:retrieveSavedItems()()">
            <html:img src='<%=urlPrefix+"i/retrieve.gif"%>' border="0" alt="Retrieve Saved Data Elements"/> 
          </a>
        </td>
		<logic:notEmpty name="<%=CaDSRConstants.CDE_CART%>" property = "dataElements">
        <td>
          <a href="javascript:saveItems()">
            <html:img src='<%="i/save.gif"%>' border="0" alt="Save"/> 
          </a>
        </td>   
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

<logic:notPresent name="<%=CaDSRConstants.CDE_CART%>">
</logic:notPresent>

<table width="10%" align="center" cellpadding="1" cellspacing="1" border="0" >
  <tr>
    <td>
      <a href='http://localhost:8080/CDEBrowser/cdeBrowse.jsp?PageId=DataElementsGroup' target="_blank"><html:img src="i/add_more_data_elements.gif" border="0" alt="Add more data elements"/></a>
    </td>
  </tr>
</table>    

</html:form>
<%@ include file="../common/common_bottom_border.jsp"%>
</body>
</html>