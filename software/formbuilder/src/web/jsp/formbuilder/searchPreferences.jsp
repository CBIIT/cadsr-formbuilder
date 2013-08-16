

<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/cdebrowser.tld" prefix="cde"%>


<%@ page import="gov.nih.nci.ncicb.cadsr.common.formbuilder.struts.common.FormConstants"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.formbuilder.struts.common.NavigationConstants"%>
<HTML>
<HEAD>
<TITLE>Form Builder Search Preferences</TITLE>
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache"/>
<LINK REL=STYLESHEET TYPE="text/css" HREF="<%=request.getContextPath()%>/css/blaf.css">
<SCRIPT LANGUAGE="JavaScript1.1" SRC="<%=request.getContextPath()%>/jsLib/checkbox.js"></SCRIPT>

<LINK REL=STYLESHEET TYPE="text/css" HREF="<%=request.getContextPath()%>/css/ui-lightness/jquery-ui-1.10.3.custom.min.css">
<script src="./js/jquery-1.9.1.js"></SCRIPT>
<script src="./js/jquery-ui-1.10.3.custom.min.js"></SCRIPT>
<script src="./js/jquery.cookie.js"></SCRIPT>
<script type="text/javascript">
var un = $.cookie('FormbuilderUsername');
var pw;
var nun = $.cookie('newFormbuilderUsername');

$(document).ready(function()
{
	setupUser();
});

function setupUser()
{
	var myInputun = $("#myInputUserName").val();
	////alert(myInputun);
		
		if( myInputun != "viewer/" )  //logout
	    {
			$(".viewer").hide("fast");
			$(".noneViewer").show("fast");

			$("#urViewer").hide("fast");
			$("#noneViewer").show("fast");

			$("#idLogout").show("fast");
	    }
		else  //login
	    {
			$(".noneViewer").hide("fast");
			$(".viewer").show("fast");

			$("#noneViewer").hide("fast");
			$("#urViewer").show("fast");
			
			$("#idLogin").show("fast");
			
		    $("input.viewerDisable").attr("disabled", true);
	    }
}
</script>	


<SCRIPT LANGUAGE="JavaScript">
<!--
function save() {
  document.forms[0].<%=NavigationConstants.METHOD_PARAM%>.value="saveFormSearchPref";
  document.forms[0].submit();
}
function cancel() {
  document.forms[0].<%=NavigationConstants.METHOD_PARAM%>.value="cancelFormSearchPref";
  document.forms[0].submit();
}

function setDefaults() {
  document.forms[0].<%=NavigationConstants.METHOD_PARAM%>.value="setDefaultFormSearchPref";
  document.forms[0].submit();
}
-->

</SCRIPT>



</HEAD>
<BODY bgcolor="#ffffff" topmargin="0">
  	<%@ include file="../common/in_process_common_header_inc.jsp"%>
      <jsp:include page="../common/tab_inc.jsp" flush="true">
        <jsp:param name="label" value="Search&nbsp;Preferences"/>
        <jsp:param name="urlPrefix" value=""/>
      </jsp:include>
     
    <html:form action="/formSearchPrefAction">
      <html:hidden value="" property="<%=NavigationConstants.METHOD_PARAM%>"/>
      <html:hidden property="src"/>
      <html:hidden property="<%=FormConstants.MODULE_INDEX%>"/>
      <html:hidden property="<%=FormConstants.QUESTION_INDEX%>"/>
      
 <table width="100%">
  <tr align="left">
     <td class="OraHeaderSubSub" width="50%" align="left" nowrap>Form search preferences for this session</td>
    <logic:equal value="false" name="searchPrefForm" property="isPreferencesDefault">
      <td width="50%" align="right" nowrap>
        <a href="javascript:setDefaults()">
               Reset to default search preferences
        </a>    
      </td>
    </logic:equal>
  </tr>  
  <tr>
    <td align="center" colspan="2" ><html:img page="/i/beigedot.gif" border="0"  height="1" width="99%" align="top" /> </td>
   </tr> 
 </table> 
 
      <%@ include file="showMessages.jsp" %>     
  <table width="80%" align="center">
    <tr>
         <td valign="top" width="100%" >
          <table width="100%" cellpadding="1" cellspacing="1" class="OraBGAccentVeryDark" >      
            <tr>
              <td colspan="2" class="OraTableColumnHeaderNoBG" >
                <html:checkbox property="excludeTestContext" value="true"  >
                   <bean:message key="cadsr.cdebrowser.exclude.test.context"/>
                </html:checkbox>
              </td>
            </tr>   
            <tr>
              <td colspan="2" class="OraTableColumnHeaderNoBG" >
                <html:checkbox property="excludeTrainingContext" value="true"  >
                   <bean:message key="cadsr.cdebrowser.exclude.training.context"/>
                </html:checkbox>
              </td>
            </tr>             
          </table>
         </td>
     </tr>
  </table> 
   
 <table width="100%">
  <tr>
    <td align="center" ><html:img page="/i/beigedot.gif" border="0"  height="1" width="99%" align="top" /> </td>
   </tr> 
 </table>             
    <%@ include file="searchPreferences_inc.jsp"%>
       <%@ include file="../common/common_bottom_border.jsp"%>  
    </html:form>

</body>
</html>