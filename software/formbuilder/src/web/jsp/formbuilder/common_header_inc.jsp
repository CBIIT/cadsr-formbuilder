

<%@ page import="gov.nih.nci.ncicb.cadsr.common.*"%>
<%@page import="gov.nih.nci.ncicb.cadsr.common.util.* " %>
<%
	String dest = pageContext.getRequest().getParameter("loginDestination");
  CDEBrowserParams params = CDEBrowserParams.getInstance();
%>

<SCRIPT LANGUAGE="JavaScript1.1" SRC="js/newWinJS.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript1.1" SRC="js/helpWinJS.js"></SCRIPT>

<TABLE width=100% Cellpadding=0 Cellspacing=0 border=0>
  <tr>

    <td align="left" nowrap>

    <img src=i/graphic6.gif alt="Form Builder Logo" border=0>
    </td>

    <td align=right valign=top colspan=2 nowrap>
      <TABLE Cellpadding=0 Cellspacing=0 border=0 >
        <TR>
          <TD valign="TOP" align="CENTER" width="1%" colspan=1><A HREF="<%= "formCDECartAction.do?method=displayCDECart"%>" TARGET="_top"><IMG SRC="i/cde_cart.gif" alt="CDE Cart" border=0 ></A><br><font color=brown face=verdana size=1>&nbsp;CDE &nbsp;Cart</font></TD>
          <TD valign="TOP" align="CENTER" width="1%" colspan=1><A target="_blank" HREF="<%=params.getCdeBrowserUrl()%>" TARGET="_top"><IMG SRC="i/icon_home.gif" alt="Home" border=0  width=32 height=32></A><br><font color=brown face=verdana size=1>&nbsp;Home&nbsp;</font></TD>
          <TD valign="TOP" align="CENTER" width="1%" colspan=1><A HREF="<%= "formSearchAction.do"%>" TARGET="_top"><IMG SRC="i/formicon.gif" alt="FormBuilder" border=0  width=32 height=32></A><br><font color=brown face=verdana size=1>&nbsp;FormBuilder&nbsp;</font></TD>
          <TD valign="TOP" align="CENTER" width="1%" colspan=1><A HREF="j<%=params.getFormBuilderHelpUrl()%>" target="_blank"><IMG SRC="i/icon_help.gif" alt="Task Help" border=0  width=32 height=32></A><br><font color=brown face=verdana size=1>&nbsp;Help&nbsp;</font></TD>
         <logic:present name="nciUser">
            <TD valign="TOP" align="CENTER" width="1%" colspan=1><A HREF="<%="logout?"+CaDSRConstants.LOGOUT_URL+"=cdeBrowse.jsp?FirstTimer=0"%>" TARGET="_top"><IMG SRC="i/logout.gif" alt="Logout" border=0  width=32 height=32></A><br><font color=brown face=verdana size=1>&nbsp;Logout&nbsp;</font></TD>
          </logic:present>
          <logic:notPresent name="nciUser">
            <TD valign="TOP" align="CENTER" width="1%" colspan=1><A HREF="<%= dest %>" TARGET="_top"><IMG SRC="i/icon_login.gif" alt="Login" border=0  width=32 height=32></A><br><font color=brown face=verdana size=1>&nbsp;Login&nbsp;</font></TD>
          </logic:notPresent>
        </TR>
      </TABLE>
    </td>
  </tr>
  <tr>
    <td id="noneViewer" align="left" class="OraInlineInfoText" nowrap>
       <logic:present name="nciUser">
        <bean:message key="user.greet" />
    	<bean:write name="nciUser" property="username"  scope="session"/>
       </logic:present>
    </td>
    <td id="urViewer" style="display:none" width=98% align="left" class="OraInlineInfoText" nowrap>
    VIEWER - please login.
    </td>    
  </tr>
</TABLE>

