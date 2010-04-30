
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/cdebrowser.tld" prefix="cde"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ page import="oracle.clex.process.jsp.GetInfoBean "%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.html.* "%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.util.* "%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.CaDSRConstants"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.formbuilder.struts.common.FormConstants"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.formbuilder.struts.common.NavigationConstants"%>
<%
	String urlPrefix = "";
  CDEBrowserParams params = CDEBrowserParams.getInstance();
  String browseURL = params.getCdeBrowserUrl();
%>
<HTML>
<HEAD>
<TITLE>Display CDE Cart</TITLE>
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache"/>
<LINK rel="stylesheet" TYPE="text/css" HREF="<html:rewrite page='/css/blaf.css' />">
<SCRIPT LANGUAGE="JavaScript1.1" SRC="js/checkbox.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
<!--

function submitForm() {
  document.forms[0].submit();
}


function deleteCart(cartName) {
var r=confirm('You are about to delete cart '+cartName+'.')
if (r == true) {
	document.forms[0].method.value = 'deleteCart';
	document.forms[0].deleteCartName.value = cartName;
   submitForm();
} else { }

}

function saveItems(sItems) {  
  if (validateSelection(sItems,'Please select at least one data element to save to your Cart.')) {
   document.forms[0].method.value = 'addItems'
   submitForm();
   return true;
  }
}

function saveItemsNewCart(sItems) {  
  if (validateSelection(sItems,'Please select at least one data element to save to your  Cart.') && 
  	validateNewCartName()) {
   document.forms[0].method.value = 'addNewCart'
   submitForm();
   return true;
  }
}
function setModChecked(val,chkName,chkValue) {
  dml=document.forms[0];
  len = dml.elements.length; 
  var i=0;
  for( i=0 ; i<len ; i++) {
   if (dml.elements[i].name==chkName) {
    chkVal = dml.elements[i].value;
    if (chkVal.match('^'+chkValue+':') == chkValue+':'){
    	dml.elements[i].checked=val;
    }
   }
  }
}
function validateNewCartName ( )
{
    valid = true;

    if ( document.forms[0].newCartName.value == "" )
    {
        alert ( "Please fill in the 'New Cart Name' to create new cart." );
        valid = false;
    }

    return valid;
}

function deleteItems(dItems) {
  if (validateSelection(dItems,'Please select at least one data element to delete from your CDE Cart.')) {
    document.forms[0].method.value = 'removeItems'
    submitForm();
  }
}

function ToggleSaveAll(e,cartId){
	if (e.checked) {
	    setModChecked(1,'selectedSaveItems',cartId);
	}
	else {
	    setModChecked(0,'selectedSaveItems',cartId);
	}
}

function ToggleDeleteAll(e,cartId){
	if (e.checked) {
	    setModChecked(1,'selectedDeleteItems',cartId);
	}
	else {
	    setModChecked(0,'selectedDeleteItems',cartId);
	}
}
function ToggleAll(e, name){
	if (e.checked) {
	    setChecked(1,name);
	}
	else {
	    setChecked(0,name);
	}
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
  var urlString="<%=browseURL%>" + "/CDEBrowser/search?dataElementDetails=9" + linkParms + "&PageId=DataElementsGroup"+"&queryDE=yes";
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
  //String downloadXMLURL = "javascript:fileDownloadWin('" + browseURL + "/CDEBrowser/cdebrowser/downloadXMLPage.jsp?src=formCdeCart&cartName=" + cartName + "','xmlWin',500,200)";
  //String downloadExcelURL = "javascript:fileDownloadWin('" + browseURL + "/CDEBrowser/cdebrowser/downloadExcelPage.jsp?src=formCdeCart&cartName=" + cartName + "','excelWin',500,200)";
  String downloadXMLURL = "formCDEDownload.do?type=xml";
  String downloadExcelURL = "formCDEDownload.do?type=excel";
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

<%@ include file="../common/in_process_common_header_inc.jsp"%>
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
<html:form action="/formsCartAction.do">
<html:hidden value="" property="<%=NavigationConstants.METHOD_PARAM%>"/>
<html:hidden property="<%= FormConstants.QUESTION_INDEX %>"/>
<html:hidden property="<%= FormConstants.MODULE_INDEX %>"/>
<html:hidden property="<%= FormConstants.DE_SEARCH_SRC %>"/>
<input type="hidden" value="" name="deleteCartName"/>
			
<c:forEach items="${cdeCart}" var="cart">			
<c:if test="${ not empty cart }">
	<% String sCartName = ((gov.nih.nci.ncicb.cadsr.objectCart.CDECart)pageContext.getAttribute("cart")).getCartName();
	   String sCartId = ((gov.nih.nci.ncicb.cadsr.objectCart.CDECart)pageContext.getAttribute("cart")).getCartId();
	
	%>
	<c:if test="${ not empty cart.dataElements }">
    <table cellpadding="0" cellspacing="0" width="80%" align="center">
      <tr>
        <td nowrap>
          <b><a href="<%=downloadExcelURL%>" target="_blank">[Download Data Elements to Excel]</a></b> &nbsp;&nbsp;
          <b><a href="<%=downloadXMLURL%>" target="_blank">[Download Data Elements as XML]</a></b> &nbsp;&nbsp;
        </td>
      </tr>
      <tr>
        <td width="100%" nowrap><img height=2 src="i/beigedot.gif" width="99%" align=top border=0> </td>
      </tr>
    </table>
</c:if>
  <table width="80%" align="center" cellpadding="1" cellspacing="1" border="0" class="OraBGAccentVeryDark">
    <tr class="OraTableColumnHeader">
		<th colspan="8"><%=sCartName%></th>
		<th><span size="small"> <a class="btn red" href="javascript:deleteCart('<%=sCartName%>')">Remove Cart</a></span></th>
	</tr>
	
	<c:if test="${ empty cart.dataElements }">
    <tr class="OraTableColumnHeader"> <th colspan="9"></th> </tr>
    <tr class="OraTabledata">
        <td class="OraFieldText" colspan="9">
          <%=sCartName%> has no Data Elements. 
        </td>
    </tr>
    </c:if>
	<c:if test="${ not empty cart.dataElements }">
    <tr class="OraTableColumnHeader">
   	  <th>Save<input type="checkbox" name="saveAllChk:<%=sCartId%>" value="yes" onClick="ToggleSaveAll(this, '<%=sCartId%>')" /></th>
      <th>Delete<input type="checkbox" name="deleteAllChk:<%=sCartId%>" value="yes" onClick="ToggleDeleteAll(this, '<%=sCartId%>')"/></th>
      <th>Long Name</th>
      <th>Doc Text</th>
      <th>Context</th>
      <th>Registration Status</th>
      <th>Workflow Status</th>
      <th>Public Id</th>
      <th>Version</th>   
    </tr>
 
	<c:forEach items="${cart.dataElements}" var="de">
<%
      String deId = ((gov.nih.nci.ncicb.cadsr.objectCart.CDECartItem)pageContext.getAttribute("de")).getId();
	  String detailsURL = "javascript:details('&p_de_idseq="+deId +"')";
%>
      <tr class="OraTabledata">
      <script>
		sItems = "selectedSaveItems";
		dItems = "selectedDeleteItems";
	  </script>
	  	<td>
	        <c:if test="${de.persistedInd == true}">
						&nbsp;       
			</c:if>
			<c:if test="${de.persistedInd != true}">
				<input type="checkbox" name="selectedSaveItems" value="<%=sCartId%>:<%=deId%>" />
			</c:if>
		</td>
		<td>
			<input type="checkbox" name="selectedDeleteItems" value="<%=sCartId%>:<%=deId%>" />
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
    </c:forEach>
  </c:if>
  
    <c:if test="${ empty cart.forms }">
	  <tr class="OraTableColumnHeader"><th colspan="9"></th></tr>
	    <tr class="OraTabledata">
	        <td class="OraFieldText" colspan="9">
	          <%=sCartName%> has no Forms. 
	        </td>
	    </tr>
	  </c:if>
	  
  	<c:if test="${ not empty cart.forms }">
  	<tr class="OraTableColumnHeader">
      <th>Save<input type="checkbox" name="saveAllChk:<%=sCartId%>" value="yes" onClick="ToggleSaveAll(this, '<%=sCartId%>')"/></th>
      <th>Delete<input type="checkbox" name="deleteAllChk:<%=sCartId%>" value="yes" onClick="ToggleDeleteAll(this, '<%=sCartId%>')"/></th>
      <th>Long Name</th>
      <th>Context</th>
	  <th>Type</th>
      <th>Protocol Long Name(s)</th>
      <th>Workflow Status</th>
      <th>Public Id</th>
      <th>Version</th>
    </tr>

	<c:forEach items="${cart.forms}" var="form">
  <%
      String formId = ((gov.nih.nci.ncicb.cadsr.common.dto.FormTransferObject)pageContext.getAttribute("form")).getIdseq();
      String detailsURL = "javascript:details('"+formId+"')";
%>
      <tr class="OraTabledata">
        <td>
          <input type="checkbox" name="selectedSaveItems" value="<%=sCartId%>:<%= formId %>"/>
        </td>
        <td>
          <input type="checkbox" name="selectedDeleteItems" value="<%=sCartId%>:<%= formId %>"/>
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
		<logic:notEmpty name="form" property="protocols">
		<logic:iterate id="proto" name="form" type="gov.nih.nci.ncicb.cadsr.common.resource.Protocol" property="protocols">
			<bean:write name="proto" property="longName"/><br/>
		</logic:iterate>
	  </logic:notEmpty>
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
  	</c:forEach>
  </c:if>
  
  
   </table>
  </c:if>
  <br/>
</c:forEach>

    <br>
    <table width="40%" align="center" cellpadding="1" cellspacing="1"
							border="1">
							<tr>
								
									<td colspan="2">
								
									<a href="javascript:saveItems(sItems)" class="btn blue">Save</a>
								</td>
								<td>
									<a href="javascript:deleteItems(dItems)" class="btn blue">Delete</a>
								</td>				
							</tr>
							<tr>
								
									<td>
										<input type="text" name="newCartName"/>
									</td>
								
								<td>
									<a href="javascript:saveItemsNewCart(sItems)" class="btn blue">Save by Name</a>
								</td>
								<td>
									<html:link page="/jsp/cdeBrowse.jsp?PageId=DataElementsGroup" >
										<html:img src='<%=urlPrefix + "i/backButton.gif"%>' border="0"
											alt="Back to Data Element Search" />
									</html:link>
								</td>
							</tr>
						</table>
							
					
<table width="10%" align="center" cellpadding="1" cellspacing="1" border="0" >
  <tr>
    <td>
      <a href='<%=params.getCdeBrowserUrl() %>' target="_blank"><html:img src="i/add_more_data_elements.gif" border="0" alt="Add more data elements"/></a>
    </td>  
  </tr>
</table>    

</html:form>
<%@ include file="../common/common_bottom_border.jsp"%>
</body>
</html>