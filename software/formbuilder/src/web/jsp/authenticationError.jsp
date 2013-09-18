<%@ page import="gov.nih.nci.ncicb.cadsr.common.CaDSRConstants"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@page import="gov.nih.nci.ncicb.cadsr.common.util.CDEBrowserParams" %>
<%
  CDEBrowserParams params = CDEBrowserParams.getInstance();
%>
<html>
<head>
<title>Login Error</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<LINK REL=STYLESHEET TYPE="text/css" HREF="<%=request.getContextPath()%>/css/blaf.css">

<LINK REL=STYLESHEET TYPE="text/css" HREF="<%=request.getContextPath()%>/css/ui-lightness/jquery-ui-1.10.3.custom.min.css">
<script src="./js/jquery-1.9.1.js"></SCRIPT>
<script src="./js/jquery-ui-1.10.3.custom.min.js"></SCRIPT>
<script src="./js/jquery.cookie.js"></SCRIPT>

<SCRIPT LANGUAGE="JavaScript">
////<!--
////if (parent.frames[1]) 
////  parent.location.href = self.location.href; 
////-->

var un = $.cookie('FormbuilderUsername');
var nun = $.cookie('newFormbuilderUsername');

var expDate = new Date(); 
expDate.setTime(expDate.getTime() + (1 * 60 * 1000)); 

$(document).ready(function()
{
  //// Go to login page
		un = "LoginAsGuest";
  
		$.cookie( 'FormbuilderUsername', un, { expires: expDate } );	
		$.cookie( 'newFormbuilderUsername', un, { expires: expDate } );	

		setupPassChangeLink();
});

function setupPassChangeLink()
{
	var locUrl = window.location.href;
	var linkPassChange = "cadsrpasswordchange";
	
	var n = locUrl.search("-");
	var passChangeLink = "";
	if( n == 18 )
	{
		passChangeLink = "https://" + linkPassChange + locUrl.substring(n);
		////alert(passChangeLink);
		$("#myPassChangeLink").attr('href', cadsrAdminLink);
		$("#myLink").html(cadsrAdminLink);
	}
}

</SCRIPT>
</head>

<body text="#000000" topmargin="0">
  <%@ include file="basicHeader_inc.jsp"%>
<br>
<br>
  
  <TABLE width=100% Cellpadding=0 Cellspacing=0 border=0>
  <TR>
  <td align=left valign=top width="1%" bgcolor="#336699"><img src="i/top_left.gif" width=4 height="25"></td>
  <td nowrap align=left valign=top width="5%" bgcolor="#336699"><b><font size="3" face="Arial" color="#FFFFFF"></font></b></td>

  <td align=left valign=top width="5%" bgcolor="#336699">&nbsp;</td>
  
  <TD align=left valign=center bgcolor="#336699" height=25 width="94%">&nbsp;</TD>
  </tr>
  </table>
  
  <table  width=100% Cellpadding=0 Cellspacing=0 border=0>
  <tr>
  <td align=right valign=top width=49 height=21 bgcolor="#336699"><img src="i/left_end_bottom.gif" height=21 width=49></td>
  <TD align=right valign=top bgcolor="#FFFFFF" height=21 width="100%"><img src="i/bottom_middle.gif" height=6 width=100%></TD>
  <td align="LEFT" valign=top height=21 width=5  bgcolor="#FFFFFF"><img src="i/right_end_bottom.gif" height=7 width=5></td>
  </TR>
  </table>
  
      <table>
            <tr>
              <td class="OraErrorHeader">
              	Login Error <br><br>
                <table width="80%" align="center">
                  <tr align="center" >
                     <td  align="left" class="OraErrorText" nowrap>
                      <b>Could not validate the User Name and Password, please <a href="formSearchAction.do"> Retry Login </a></b>
                    </td>
                  </tr>
                  <tr align="center" >
                   <td>
                      &nbsp;
                  </td>
                 </tr>        
                </table>
              </td>
            </tr>
 <tr>
 <td>
&nbsp;
 </td>
 </tr>
 <tr>
 <td>
&nbsp;
 </td>
 </tr>
 <TR>
<td align="left" class="OraErrorText" nowrap>After 6 attempts you will be locked out for 1 hour. </td>
 </tr>
 <tr>
<td align="left" class="OraErrorText" nowrap>Visit&nbsp;
<a id="myPassChangeLink 1" href="https://cadsrpasswordchange-dev.nci.nih.gov/">Password Change Station</a>
&nbsp;at&nbsp;<a id="myPassChangeLink 2" href="https://cadsrpasswordchange-dev.nci.nih.gov/"><b id="myLink">https://cadsrpasswordchange-dev.nci.nih.gov/</b></a>&nbsp;to reset your password.
</td>
</tr>
 </table>
<TABLE width=100% cellspacing=0 cellpadding=0 border=0>
<TR>
<TD valign=bottom width=99%><img src="i/bottom_shade.gif" height=6 width="100%"></TD>
<TD valign=bottom width="1%" align=right><IMG src="i/bottomblueright.gif"></TD>
</TR>
<TABLE width=100% cellspacing=0 cellpadding=0 bgcolor="#336699" border=0>
<TR>
<TD width="60%" align="LEFT">
<FONT face="Arial" color="WHITE" size="-2"></FONT>
<FONT face="Arial" size="-1" color="#CCCC99"></FONT>
&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
<FONT color="white" size=-2 face=arial></FONT>
</TD>
</TR>
<TR>
<TD colspan=2><IMG src="i/bottom_middle.gif" height=6 width="100%"></TD>
</TR>
</TABLE>
</body>
</html>     