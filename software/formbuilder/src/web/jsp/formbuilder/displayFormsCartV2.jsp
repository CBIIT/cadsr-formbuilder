
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/cdebrowser.tld" prefix="cde"%>
<%--@ page import="oracle.clex.process.jsp.GetInfoBean "--%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.html.* "%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.util.* "%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.CaDSRConstants"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.formbuilder.struts.common.FormConstants"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.formbuilder.struts.common.NavigationConstants"%>
<%
	String urlPrefix = "";
	CDEBrowserParams params = CDEBrowserParams.getInstance();
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
   document.forms[0].method.value = 'addItemsV2'
   submitForm();
   return true;
  }
}

function deleteItems() {
  if (validateSelection('selectedDeleteItems','Please select at least one form to delete from your Form Cart.')) {
    document.forms[0].method.value = 'removeItemsV2'
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
  document.location.href = "formsCartAction.do?method=displayFormsCartV2";
}

-->

</SCRIPT>

<LINK REL=STYLESHEET TYPE="text/css" HREF="<%=request.getContextPath()%>/css/ui-lightness/jquery-ui-1.10.3.custom.min.css">
<script src="./js/jquery-1.9.1.js"></SCRIPT>
<script src="./js/jquery-ui-1.10.3.custom.min.js"></SCRIPT>
<script src="./js/jquery.cookie.js"></SCRIPT>
<script src="./js/jquery.marquee.js"></script>
<script src="./js/formbuilderJQ.js"></script>

<script type="text/javascript">

$(document).ready(function()
{
	setupUser();
});

</script>	


</HEAD>
<BODY topmargin=0 bgcolor="#ffffff" topmargin="0">

<% 
  String downloadExcelURL = "formExcelDownload.do?&formIdSeq=";
  String doneURL = "";
  
  String src = request.getParameter("src");
  String modIndex = "";
  String quesIndex = "";
  String urlParams = "";
    
 // Security scan fix - Checking 'src' for hazardous characters to prevent Link Injection, Cross-site scripting & Content spoofing 
  if ((src != null) && (!"".equals(src))) {
  	if (src.matches("[^a-zA-Z0-9-]")) {
    modIndex = request.getParameter("moduleIndex");
    quesIndex = request.getParameter("questionIndex");
    doneURL= src+".do?method=displayFormCart";
    urlParams = "&src="+src+"&method=displayCDECart&moduleIndex="+modIndex+"&questionIndex="+quesIndex; 
    } else {
    	src = "";
    	doneURL="formSearchAction.do";
    }
  }
  else {
    doneURL="formSearchAction.do";
  }
%>

<%@ include file="../common/in_process_common_header_inc.jsp"%>
<jsp:include page="../common/tab_inc.jsp" flush="true">
  <jsp:param name="label" value="Form Cart V2"/>
  <jsp:param name="urlPrefix" value=""/>
</jsp:include>
<table>
    <tr>    
      <td align="left" class="AbbreviatedText">
        <bean:message key="cadsr.formbuilder.helpText.forms.cart.secure"/>
      </td>
    </tr>  
</table> 
<%@ include file="showMessages.jsp" %>
<html:form action="/formsCartAction.do">
<html:hidden value="" property="<%=NavigationConstants.METHOD_PARAM%>"/>
<html:hidden property="<%= FormConstants.QUESTION_INDEX %>"/>
<html:hidden property="<%= FormConstants.MODULE_INDEX %>"/>
<html:hidden property="<%= FormConstants.DE_SEARCH_SRC %>"/>
<logic:present name="<%=CaDSRConstants.FORMS_DISPLAY_CART2%>">
  <table width="80%" align="center" cellpadding="1" cellspacing="1" border="0" class="OraBGAccentVeryDark">
    <tr class="OraTableColumnHeader">
    <logic:notEmpty name="<%=CaDSRConstants.FORMS_DISPLAY_CART2%>" property = "formDisplayObjects">
      <th><center>Save<br/><input type="checkbox" name="saveAllChk" value="yes" onClick="ToggleSaveAll(this)"/></center></th>
      <th><center>Delete<br/><input type="checkbox" name="deleteAllChk" value="yes" onClick="ToggleDeleteAll(this)"/></center></th>
	<th><center>Action</center></th>
    </logic:notEmpty>
      <th>Long Name</th>
      <th>Context</th>
		<th>Type</th>
      <th>Protocol Long Name(s)</th>
      <th>Workflow Status</th>
      <th>Public Id</th>
      <th>Version</th>
    </tr>
  <logic:empty name="<%=CaDSRConstants.FORMS_DISPLAY_CART2%>" property = "formDisplayObjects">
    <tr class="OraTabledata">
        <td class="OraFieldText" colspan="7">
          Form Cart is empty. 
        </td>
    </tr>
  </logic:empty>
  <logic:notEmpty name="<%=CaDSRConstants.FORMS_DISPLAY_CART2%>" property = "formDisplayObjects">
	<bean:size id="noOfItems" name="<%=CaDSRConstants.FORMS_DISPLAY_CART2%>" property="formDisplayObjects" />
    <logic:iterate id="form" name="<%=CaDSRConstants.FORMS_DISPLAY_CART2%>" type="gov.nih.nci.ncicb.cadsr.formbuilder.struts.common.FormCartDisplayObjectPersisted" property="formDisplayObjects">
<%
      String formId = form.getIdseq();
      String detailsURL = "javascript:details('"+formId+"')";
      boolean ISPERSISTED = form.getIsPersisted();
%>
      <tr class="OraTabledata">
      	<td class="OraFieldText"><center>
			<logic:equal name="form" property="isPersisted" value="true">
              	&nbsp;       
            </logic:equal>
			<logic:notEqual name="form" property="isPersisted" value="true">
				<input type="checkbox" name="selectedSaveItems" value="<%= formId %>" />
			</logic:notEqual></center>
		</td>
<!--GF32932. -D.An, 20130830  -->		
        <td><center>
<logic:equal name="form" property="isPersisted" value="true">
    <input type="checkbox" name="selectedDeleteItems" value="<%= formId %>"/></center>
</logic:equal>
        </td>
		<td>
			<table>
                    <tr>               
                    	<td width="20" class="OraTabledata" align=center>                  
					<html:link action='<%="/formExcelDownload.do?"%>' 
      			            paramId = "<%=FormConstants.FORM_ID_SEQ%>"
            	      		paramName="form" paramProperty="idseq"
		      	            target="_blank" >
	             			<html:img src='<%=urlPrefix+"i/excel-icon.jpg"%>' border="0" alt="Excel Download"/>
	      		      </html:link>
				</td>
                    	<td width="20" class="OraTabledata" align=center>                  
					<html:link action='<%="/formXMLDownload.do?"%>' 
            			      paramId = "<%=FormConstants.FORM_ID_SEQ%>"
			                  paramName="form" paramProperty="idseq"
			                  target="_blank" >
               				<html:img src='<%=urlPrefix+"i/xml-icon.gif"%>' border="0" alt="XML Download"/>
			            </html:link>
	                    </td> 
                    </tr>
                    </table>
		</td>
        <td class="OraFieldText">
          <a href="<%=detailsURL%>">
            <bean:write name="form" property="longName"/>
          </a>
        </td>
        <td class="OraFieldText">
          <bean:write name="form" property="contextName"/>
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
    </logic:iterate>
		<tr class="OraTabledata">
			<td class="OraFieldText" colspan="9">
	  			<br/>Total items in cart: <b><bean:write name="noOfItems" /></b>
        	</td>
		</tr>
  </logic:notEmpty>
   </table>
    <br>
    <table width="30%" align="center" cellpadding="1" cellspacing="1" border="0">  
      <TR>
        <td>&nbsp;</td>
      </TR>
      <tr>
		<logic:notEmpty name="<%=CaDSRConstants.FORMS_DISPLAY_CART2%>" property = "formDisplayObjects">
        <td>
          <center><a href="javascript:deleteItems()">
            <html:img src='<%="i/deleteButton.gif"%>' border="0" alt="Delete"/> 
          </a></center>
        </td> 
		</logic:notEmpty>  
		<td>
          <center><a href="javascript:saveItems()">
            <html:img src='<%="i/save.gif"%>' border="0" alt="Save"/> 
          </a></center>
        </td>       
        <td>
          <CENTER><html:link href="<%=doneURL%>">				
            <html:img src='<%="i/backButton.gif"%>' border="0" alt="Back"/>
          </html:link></CENTER>             
        </td> 
      </tr>
 </table>
</logic:present>

<table width="10%" align="center" cellpadding="1" cellspacing="1" border="0" >
  <tr>
    <td>
      <%--<a href='<%=params.getFormBuilderUrl() %>' target="_blank"><html:img src="i/add_more_forms.gif" border="0" alt="Add more forms"/></a>--%>
          <CENTER><html:link href="<%=doneURL%>">				
            <html:img src='<%="i/add_more_forms.gif"%>' border="0" alt="add more forms"/>
          </html:link></CENTER>  
    </td>
  </tr>
</table> 

</html:form>
<%@ include file="../common/common_bottom_border.jsp"%>
</body>
</html>