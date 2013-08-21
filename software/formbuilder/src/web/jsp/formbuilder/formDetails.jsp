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
<%@page import="gov.nih.nci.ncicb.cadsr.common.formbuilder.common.FormBuilderConstants" %>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.resource.Form"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.resource.*"%>
<%@ page import="java.util.*"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.formbuilder.struts.common.*"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.util.CDEBrowserParams"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.jsp.util.CDEDetailsUtils"%>

<% CDEBrowserParams params = CDEBrowserParams.getInstance();%> 

<%
   // TO DO : Replace this with appropriate struts tags
   boolean isPublished = false;
   Object o = session.getAttribute(FormConstants.CRF);
   if (o != null) {
      Form myCRF = (Form)o;
      isPublished = myCRF.getIsPublished();
   }

%>


<HTML>
  <HEAD>
    <TITLE>Formbuilder: Form Details</TITLE>
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache"/>
    <LINK rel="stylesheet" TYPE="text/css" HREF="<html:rewrite page='/css/blaf.css' />">

<script type="text/javascript" src='<html:rewrite page="/js/collapsible.js"/>' ></script>

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

  </HEAD>
  <BODY topmargin=0 bgcolor="#ffffff" onLoad="makeCollapsible(document.getElementsByTagName('table'), 'collapsible');getToggleDisplay(document.getElementById('toggle_display'));">
    <% String urlPrefix = "";
     int dummyInstructionDisplayCount = 3;

%>
    <%@ include file="../common/in_process_common_header_inc.jsp"%>
    <jsp:include page="../common/tab_inc.jsp" flush="true">
      <jsp:param name="label" value="View&nbsp;Form"/>
      <jsp:param name="urlPrefix" value=""/>
    </jsp:include>

<table>
    <tr>    
      <td align="left" class="AbbreviatedText">
        <bean:message key="cadsr.formbuilder.helpText.form.view"/>
      </td>
    </tr>  
</table> 
    <%@ include file="viewButton_inc.jsp"%>
    <%@ include file="showMessages.jsp" %>
    <logic:present name="<%=FormConstants.CRF%>">
      <table width="80%" align="center" cellpadding="1" cellspacing="1" border="0" class="OraBGAccentVeryDark">
        <tr class="OraTabledata">
          <td class="TableRowPromptTextLeft" width="20%">
            <bean:message key="cadsr.formbuilder.form.longName" />
          </td>                
          <td width="80%" class="OraFieldText">
            <bean:write name="<%=FormConstants.CRF%>" property="longName"/>
          </td>
        </tr>
        <tr class="OraTabledata">
          <td  class="TableRowPromptTextLeft" width="20%">
            <bean:message key="cadsr.formbuilder.form.definition"/>
          </td>                
          <td width="80%" class="OraFieldText">
            <bean:write name="<%=FormConstants.CRF%>" property="preferredDefinition"/>
          </td>
        </tr>
        <tr class="OraTabledata">
          <td  class="TableRowPromptTextLeft" width="20%">
            <bean:message key="cadsr.formbuilder.form.context"/>
          </td>                
          <td  class="OraFieldText">
            <bean:write name="<%=FormConstants.CRF%>" property="context.name"/>
          </td>
        </tr>
        <tr class="OraTabledata">
          <td class="TableRowPromptTextLeft"  width="20%">
            <bean:message key="cadsr.formbuilder.form.protocols.longName"/>
          </td>                
          <td  class="OraFieldText">
           <bean:define name="<%=FormConstants.CRF%>" property="protocols" id="protocols"/>
            <%=FormJspUtil.getDelimitedProtocolLongNames((List)protocols,  "<br/>")%>                
          </td>
        </tr>   
        <tr class="OraTabledata">
          <td class="TableRowPromptTextLeft"  width="20%">
            <bean:message key="cadsr.formbuilder.form.workflow"/>
          </td>                
          <td  class="OraFieldText">
            <bean:write name="<%=FormConstants.CRF%>" property="aslName"/>
          </td>
        </tr>  
        <tr class="OraTabledata">
          <td class="TableRowPromptTextLeft"  width="20%">
            <bean:message key="cadsr.formbuilder.form.category"/>
          </td>                
          <td  class="OraFieldText">
            <bean:write name="<%=FormConstants.CRF%>" property="formCategory"/>
          </td>
        </tr>  
        <tr class="OraTabledata">
          <td class="TableRowPromptTextLeft"  width="20%">
            <bean:message key="cadsr.formbuilder.form.type"/>
          </td>                
          <td  class="OraFieldText">
            <bean:write name="<%=FormConstants.CRF%>" property="formType"/>
          </td>
        </tr> 
        <tr class="OraTabledata">
          <td class="TableRowPromptTextLeft"  width="20%">
            <bean:message key="cadsr.formbuilder.form.publicID"/>
          </td>                
          <td  class="OraFieldText">
            <bean:write  name="<%=FormConstants.CRF%>" property="publicId"/> 
          </td>
        </tr>           
        <tr class="OraTabledata">
          <td class="TableRowPromptTextLeft"  width="20%">
            <bean:message key="cadsr.formbuilder.question.version"/>
          </td>                
          <td  class="OraFieldText">
            <bean:write  name="<%=FormConstants.CRF%>" property="version"/> 
          </td>
        </tr>    
        <logic:present name="<%=FormConstants.CRF%>" property="instruction">
          <tr class="OraTabledata">
            <td class="TableRowPromptTextLeft"  width="20%">
              <bean:message key="cadsr.formbuilder.form.header.instruction"/>
            </td>                
            <td  class="OraFieldTextInstruction">
              <bean:write  name="<%=FormConstants.CRF%>" property="instruction.preferredDefinition"/>
            </td>
          </tr>
        </logic:present>
        <logic:present name="<%=FormConstants.CRF%>" property="footerInstruction">     
          <tr class="OraTabledata">
            <td class="TableRowPromptTextLeft"  width="20%">
              <bean:message key="cadsr.formbuilder.form.footer.instruction"/>
            </td>                
            <td  class="OraFieldTextInstruction">
              <bean:write  name="<%=FormConstants.CRF%>" property="footerInstruction.preferredDefinition"/>
            </td>
          </tr> 
        </logic:present>          
      </table>
      <table width="80%" align="center" cellpadding="0" cellspacing="0" border="0" >
        <tr >
          <td >
             &nbsp;
          </td>
        </tr>
      </table> 
      <table cellpadding="0" cellspacing="0" width="80%" align="center">
        <tr >
          <td colspan=2>
            &nbsp;
          </td>
        </tr>         
        <tr>
          <td class="OraHeaderSubSub" width="100%">Form Details</td>
		<td align="right"><a href="javascript:getToggleDisplay(document.getElementById('toggle_display'));" id="toggle_display"></a></td>
          <td align="right">
          <bean:define id="formObj" name="<%=FormConstants.CRF%>" />
          <% Form aForm = (Form)formObj;
            if(FormJspUtil.hasModuleRepetition(aForm)){ %>
             <logic:present name="<%=FormConstants.SHOW_MODULE_REPEATS%>">
               <html:link action='<%="/displayViewFormModuleRepeationAction.do?"+NavigationConstants.METHOD_PARAM+"="+NavigationConstants.HIDE_REPETITIONS%>'
                 >
               <html:img src='i/hideModuleRepetitions.gif' border="0" alt="Hide Module Repetitions"/>
              </html:link>                
             </logic:present>
             <logic:notPresent name="<%=FormConstants.SHOW_MODULE_REPEATS%>">
               <html:link action='<%="/displayViewFormModuleRepeationAction.do?"+NavigationConstants.METHOD_PARAM+"="+NavigationConstants.SHOW_REPETITIONS%>'
                 >
               <html:img src='i/showModuleRepetitions.gif' border="0" alt="Show Module Repetitions"/>
              </html:link>  
             </logic:notPresent>  
         <% }else{%>
            &nbsp;
         <%}%>  
          </td>          
        </tr>
        <tr>
          <td colspan=3><img height=1 src="i/beigedot.gif" width="99%" align=top border=0> </td>
        </tr>
      </table>       
      
     <%@ include file="moduleDetails_inc.jsp"%>
    
    </logic:present>
    <logic:notPresent name="<%=FormConstants.CRF%>">Selected form has been deleted by a diffrent user </logic:notPresent>
    <%@ include file="viewButton_inc.jsp"%>
    <%@ include file="../common/common_bottom_border.jsp"%>
  </BODY>
</HTML>
