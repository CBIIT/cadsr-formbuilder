<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@page import="gov.nih.nci.ncicb.cadsr.common.util.CDEBrowserParams" %>
<%
  CDEBrowserParams params = CDEBrowserParams.getInstance();
  System.out.println(params.getCdeBrowserUrl() + " tool " + params.getFormBuilderUrl());
%>
<html>
<head>
<!-- @tag fb master for 4.1 r0006 -->
<title>Login</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<LINK REL=STYLESHEET TYPE="text/css" HREF="<%=request.getContextPath()%>/css/blaf.css">

<LINK REL=STYLESHEET TYPE="text/css" HREF="<%=request.getContextPath()%>/css/ui-lightness/jquery-ui-1.10.3.custom.min.css">
<script src="./js/jquery-1.9.1.js"></SCRIPT>
<script src="./js/jquery-ui-1.10.3.custom.min.js"></SCRIPT>
<script src="./js/jquery.cookie.js"></SCRIPT>

<script type="text/javascript">

if (parent.frames[1]) 
  parent.location.href = self.location.href; 
	//request.getRequestDispatcher("formSearchAction.do").forward(request, response);
	
var un = $.cookie('FormbuilderUsername');
var pw;
var nun = $.cookie('newFormbuilderUsername');

////alert("loginPage");

$(document).ready(function()
{
	if( nun == 'LoginAsGuest' )
    {
		un = "viewer";
		$.cookie( 'newFormbuilderUsername', un );
		
		$("#bd").show();
		
		un = "guest";

		$("#j_username").val("");
		$("#j_username").focus().select();
    }
	else 
    {
		un = "viewer";
		pw = "viewer";
		$("#j_username").val(un);
		$("#j_password").val(pw);
		
		submitForm();   
    }
});
	
function submitForm() {
	
	un = $('#j_username').val();
	
	$.cookie( 'FormbuilderUsername', un );		
	$.cookie( 'newFormbuilderUsername', un );	
	
	////alert('ck-un:' + $.cookie('FormbuilderUsername') + ';   ck-pw:' + $.cookie('FormbuilderPassword') + '!' );
		
  document.forms[0].submit();
}

function clearForm()
{
  document.form[0].reset();
}

</SCRIPT>
</head>

<body text="#000000" topmargin="0" id="bd" style="display:none">

   <%@ include file="basicHeader_inc.jsp"%>
   
<br>
<br>
<div>  
  <TABLE width=100% Cellpadding=0 Cellspacing=0 border=0>
  <TR>
  <td align=left valign=top width="1%" bgcolor="#336699"><img src="<%=request.getContextPath()%>/i/top_left.gif" alt="bar" width=4 height="25"></td>
  <td nowrap align=left valign=top width="5%" bgcolor="#336699"><b><font size="3" face="Arial" color="#FFFFFF">&nbsp; &nbsp;Please Login</font></b></td>

  <td align=left valign=top width="5%" bgcolor="#336699">&nbsp;</td>
  
  <TD align=left valign=center bgcolor="#336699" height=25 width="94%">&nbsp;</TD>
  </tr>
  </table>
  
  <table  width=100% Cellpadding=0 Cellspacing=0 border=0>
  <tr>
  <td align=right valign=top width=49 height=21 bgcolor="#336699"><img src="<%=request.getContextPath()%>/i/left_end_bottom.gif" alt="bar" height=21 width=49></td>
  <TD align=right valign=top bgcolor="#FFFFFF" height=21 width="100%"><img src="<%=request.getContextPath()%>/i/bottom_middle.gif" alt="image" height=6 width=100%></TD>
  <td align="LEFT" valign=top height=21 width=5  bgcolor="#FFFFFF"><img src="<%=request.getContextPath()%>/i/right_end_bottom.gif" alt="bar" height=7 width=5></td>
  </TR>
  </table>
  
  <table>
    <tr>    
      <td align="left" class="OraTipText">
        Guest users can login using username "guest" and password "Nci_gue5t".
   <br> If you require an account with curator privileges to a specific context other than Test, please contact NCICB Application Support Email: <a href='mailto:ncicbiit@mail.nih.gov'>ncicbiit@mail.nih.gov</a>
      </td>
    </tr>  
  </table>  
  
  <%
  
  %>
  
  <form method="POST" action="j_security_check">

  <table id="loginTb" display="inline" align=center cellspacing="2" cellpadding="3" border="0" onkeypress="if(event.keyCode==13){submitForm()};">
    <% if(request.getAttribute("msg") != null) { 
    	System.out.println("msg " + request.getAttribute("msg"));
    %>
    <tr>
      <td colspan=2 class="OraErrorText"><b><%=request.getAttribute("msg")%></b></td>
    </tr>
    <% } %>
    <tr>
        <td class="OraFieldtitlebold" nowrap>
        	<label for="j_username">Username:</label> 
		</td>
        <td class="OraFieldText" nowrap>
          <input type="text" id="j_username" name="j_username" value="" size ="20"> 
        </td>
    </tr>
    <tr>
        <td class="OraFieldtitlebold" nowrap>
        	<label for="j_password">Password:</label>
        </td>
        <td class="OraFieldText" nowrap>
          <input type="password" id="j_password" name="j_password" value="" size ="20"> 
        </td>
    </tr>  

  </table>
  <table width=100% cellspacing="2" cellpadding="3" border="0">
     <TR>
      	<td width=45%>&nbsp;</td>
      	<td width=55%>
      	  <table cellspacing="2" cellpadding="3" border="0">
		     <TR>
		        <td colspan="1" align="left" nowrap><a href="javascript:submitForm()"><img src=<%=request.getContextPath()%>/i/logon.gif border=0 alt="Logon"></a></td>
		        <td colspan="1" align="left" nowrap><a href="javascript:clearForm()"><img src=<%=request.getContextPath()%>/i/clear.gif border=0 alt="Clear"></a></td>
		        <td colspan="1" align="left" nowrap><a target="_blank" href="<%=params.getCdeBrowserUrl()%>"><img src=<%=request.getContextPath()%>/i/return_cdebrowse.gif border=0 alt="Cancel"></a></td>
		    </TR>  
		  </table>    
      	</td>
    </TR>  
    <tr>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
  </table>    
  </form>
  <SCRIPT>
    document.forms[0].elements[0].focus();
  </SCRIPT>

<%@ include file="common/common_bottom_border.jsp"%>

</div>

</body>
</html>