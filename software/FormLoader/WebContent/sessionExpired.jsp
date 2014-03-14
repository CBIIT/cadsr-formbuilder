<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>


<html>
<head>
<title>Login</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<LINK REL=STYLESHEET TYPE="text/css" HREF="<%=request.getContextPath()%>/css/blaf.css">

 <s:head />
</head>
<div id="4b" style="padding-left: 50px; padding-right: 50px;">
<body  height ="100%" width = "100%" text="#000000" topmargin="0">

   <%@ include file="basicHeaderLogin_inc.jsp"%>
   
<br>
<br>
  
  <TABLE width=100% Cellpadding=0 Cellspacing=0 border=0>
  <TR>
  <td align=left valign=top width="1%" bgcolor="#336699"><img src="<%=request.getContextPath()%>/i/top_left.gif" width=4 height="25"></td>
  <td nowrap align=left valign=top width="5%" bgcolor="#336699"><b><font size="3" face="Arial" color="#FFFFFF">&nbsp; &nbsp;Please Login</font></b></td>

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
      <td align="left" class="OraTipText">
        Guest users can login using username "guest" and password "Nci_gue5t".
   <br> If you require an account with curator privileges to a specific context other than Test, please contact CBIIT Application Support Email: <a href='mailto:ncicbiit@mail.nih.gov'>ncicbiit@mail.nih.gov</a>
      </td>
    </tr>  
    </table>
  
    <table width=100% Cellpadding=0 Cellspacing=0 border=0>
      <tr><td>&nbsp;</td></tr>   
      <tr><td>&nbsp;</td></tr>
      <tr><td class="OraErrorText"><b>Your Session has Expired! Please login again.</b></td></tr>
      <tr><td>&nbsp;</td></tr>
      </table>

   <table>
<s:form  method="post" action="login">
  <tr>
	<s:textfield name="username" key="label.username" size="20" />  </tr>
	  <tr>
	<s:password name="password" key="label.password" size="20" />  </tr>
	  <tr>
	  	<td colspan="1" align="left" nowrap>
<s:submit type="image" src="/FormLoader/i/logon.gif" value="execute" align="left" theme="simple" /></td>
	  	<td colspan="1" align="left" nowrap>
<s:submit type="image" src="/FormLoader/i/clear.gif" value="clear" action="clearlogin" onclick="this.form.reset();"  align="left" theme="simple"/></td>
  </tr>
</s:form>
 </table> 

<%@ include file="common/common_bottom_border.jsp"%>

</body>
</div>
</html>