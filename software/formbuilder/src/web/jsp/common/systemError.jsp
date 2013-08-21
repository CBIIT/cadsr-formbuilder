<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<HTML>
  <HEAD>
    <TITLE>Form Builder: System Error</TITLE>
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
	<LINK REL=STYLESHEET TYPE="text/css" HREF="<%=request.getContextPath()%>/css/blaf.css">

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
  <BODY topmargin=0 bgcolor="#ffffff">
    <% 
    	String urlPrefix = "";
    %>
    <%@ include file="syserror_common_header_inc.jsp" %>

    <jsp:include page="tab_inc.jsp" flush="true">
      <jsp:param name="label" value="System&nbsp;Error"/>
      <jsp:param name="urlPrefix" value=""/>
    </jsp:include>

    <table>
      <tr>
        <td class="OraErrorHeader">
        	Unexpected System Error has Occured <br><br>
      <logic:messagesPresent >
       <table width="80%" align="center">
        <html:messages id="error" >
          <logic:present name="error">
            <tr align="center" >
               <td  align="left" class="OraErrorText" nowrap>
                <b><bean:write  name="error"/></b>
              </td>
            </tr>
          </logic:present>          
        </html:messages> 
           <tr align="center" >
             <td>
                &nbsp;
            </td>
           </tr>        
       </table>
      </logic:messagesPresent>  
        </td>
      </tr>
    </table>
<%@ include file="common_bottom_border.jsp"%>
  </BODY>
</HTML>
