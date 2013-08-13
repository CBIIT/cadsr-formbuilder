<%--L
  Copyright Oracle Inc, ScenPro Inc, SAIC-F

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/cadsr-formbuilder/LICENSE.txt for details.
L--%>

<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>


<html>
<head>
<title>Login</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <s:head />
</head>

<body  height ="100%" width = "100%" text="#000000" topmargin="0">

   <%@ include file="basicHeader_inc.jsp"%>
   
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
   <br> If you require an account with curator privileges to a specific context other than Test, please contact NCICB Application Support Email: <a href='mailto:ncicb@pop.nci.nih.gov'>ncicb@pop.nci.nih.gov</a>
      </td>
    </tr>  
  </table>  
  

  <p>

  <s:actionerror />
<s:form action="login" method="post">
	<s:textfield name="username" key="label.username" size="20" />
	<s:password name="password" key="label.password" size="20" />
	<s:submit method="execute" key="label.login" align="center" />
</s:form>
 
  <p>
<%@ include file="common/common_bottom_border.jsp"%>

</body>
</html>