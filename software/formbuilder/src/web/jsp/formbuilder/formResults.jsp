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
<%@page import="gov.nih.nci.ncicb.cadsr.common.formbuilder.common.FormBuilderConstants" %>
<%@page import="gov.nih.nci.ncicb.cadsr.formbuilder.struts.common.FormJspUtil" %>
<%@ page session="true" %>
<HTML>
<HEAD>
<TITLE>Welcome to Form Builder..</TITLE>
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<LINK rel="stylesheet" TYPE="text/css" HREF="<html:rewrite page='/css/blaf.css' />">

<LINK REL=STYLESHEET TYPE="text/css" HREF="<%=request.getContextPath()%>/css/ui-lightness/jquery-ui-1.10.3.custom.min.css">
<script src="./js/jquery-1.9.1.js"></SCRIPT>
<script src="./js/jquery-ui-1.10.3.custom.min.js"></SCRIPT>
<script src="./js/jquery.cookie.js"></SCRIPT>

<script type="text/javascript">
var un = $.cookie('FormbuilderUsername');
var pw;
var nun = $.cookie('newFormbuilderUsername');

////alert("formResultPage");



$(document).ready(function()
{
	
	setupUser();
	
	setupLink();

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
			
			$("input.viewerDisable").removeAttr("disabled");
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

function setupLink()
{
	var cdeBrowserlink = $("#idCDEBrowser").attr('href');
	
	var strAdm="cadsradmin";
	var n = cdeBrowserlink.search("-");
	
	if( n == 18 )
	{
		var cadsrAdminLink = "http://" + strAdm + cdeBrowserlink.substring(n);
		////alert(cadsrAdminLink);
		$("#idCaDSRAdmin").attr('href', cadsrAdminLink);		
	}
}
</script>	

<%
  String urlPrefix = "";
  String jumpto = (String)request.getSession().getAttribute(CaDSRConstants.ANCHOR);
  String jumptoStr ="";
  
  if(jumpto!=null)
    jumptoStr = "onload=\"location.hash='#"+jumpto+"'\"";

%>



</HEAD>
<BODY topmargin=0 bgcolor="#ffffff" <%=jumptoStr%> ">

<logic:notPresent name="<%=FormConstants.IN_PROCESS%>"> 
        <%@ include  file="/jsp/common/common_header_inc.jsp" %>
        
        <jsp:include page="/jsp/common/tab_inc.jsp" flush="true">
                <jsp:param name="label" value="Form&nbsp;Search" />
                <jsp:param name="urlPrefix" value="" />
        </jsp:include>
</logic:notPresent>        

<logic:present name="<%=FormConstants.IN_PROCESS%>"> 
    <logic:present name="<%=FormConstants.SKIP_PATTERN%>"> 
            <%@ include  file="/jsp/common/in_process_common_header_no_strip_inc.jsp" %>
            
            <jsp:include page="/jsp/common/tab_inc.jsp" flush="true">
                    <jsp:param name="label" value="Skip&nbsp;to&nbsp;Form&nbsp;Search" />
                    <jsp:param name="urlPrefix" value="../" />
            </jsp:include>
    </logic:present>  
    
    <logic:present name="<%=FormConstants.MODULE_DISPLAY_ORDER_TO_COPY%>"> 
            <%@ include  file="/jsp/common/in_process_common_header_no_strip_inc.jsp" %>
            
            <jsp:include page="/jsp/common/tab_inc.jsp" flush="true">
                    <jsp:param name="label" value="Copy&nbsp;module&nbsp;Form&nbsp;Search" />
                    <jsp:param name="urlPrefix" value="../" />
            </jsp:include>
    </logic:present>  
    <logic:notPresent name="<%=FormConstants.MODULE_DISPLAY_ORDER_TO_COPY%>"> 
    test
    </logic:notPresent>  
</logic:present>  


<table>
    <tr>    
      <td align="left" class="AbbreviatedText">
        <bean:message key="cadsr.formbuilder.helpText.search.page"/>
      </td>
    </tr>  
</table> 
<html:form action="/formSearchAction.do">
 <%@ include  file="/jsp/formbuilder/formSearch_inc.jsp" %> 
<logic:present name="<%=FormConstants.FORM_SEARCH_RESULTS%>">  
    <A NAME="results"></A>
       <table cellpadding="0" cellspacing="0" width="100%" align="center">  
      <tr colspan="2" >
           <td  nowrap>&nbsp;</td>
      </tr>
      <tr>
         <td  valign="bottom" class="OraHeaderSubSub" width="50%" align="left" nowrap>Search Results</td>
          <td  valign="bottom" class="OraFieldText" width="50%" align="right" nowrap>
            <html:link action='<%="/formbuilder/viewModuleList?"+NavigationConstants.METHOD_PARAM+"="+NavigationConstants.VIEW_MODULE_LIST%>' 
              target="_parent">          
                    <html:img src='<%=urlPrefix+"i/moduleCart.gif"%>' border="0" alt="Done"/>        
            </html:link>          
          </td>
     </tr>       
        <tr>
          <td colspan="2" ><img height=1 src="i/beigedot.gif" width="99%" align=top border=0> </td>
        </tr>
      </table>   
  <%@ include  file="/jsp/formbuilder/formResults_inc.jsp" %>
</logic:present> 
   
</html:form>
<%
  request.getSession().removeAttribute(CaDSRConstants.ANCHOR);
%>
<%@ include file="/jsp/common/common_bottom_border.jsp"%>

</BODY>
</HTML>
