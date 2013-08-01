
<%@page import="gov.nih.nci.ncicb.cadsr.common.util.CDEBrowserParams"%>

<%
String destLogin = pageContext.getRequest().getParameter("loginDestination");

String username=(String) session.getAttribute("myUsername");
System.out.println("in_process_common_header_inc.jsp:" + username);

	CDEBrowserParams fbparams = CDEBrowserParams.getInstance();
%>

<SCRIPT LANGUAGE="JavaScript1.1" SRC='<html:rewrite page="/js/newWinJS.js"/>'></SCRIPT>
<SCRIPT LANGUAGE="JavaScript1.1" SRC='<html:rewrite page="/js/helpWinJS.js"/>'></SCRIPT>

<%@ include  file="../common/topHeader.jsp" %>


<TABLE width=100% Cellpadding=0 Cellspacing=0 border=0>
  <tr>

    <td valign="top" align="left" nowrap="nowrap">

    <html:img page="/i/cde_form_builder_banner_full.gif" border="0" />
    </td>

    <td align=right valign="top" colspan=2 nowrap>
      <TABLE Cellpadding=0 Cellspacing=0 border=0 >
        <TR>
          <TD valign="TOP" align="CENTER" width="1%" colspan=1><A HREF="<%=fbparams.getFormBuilderHelpUrl()%>" target="_blank"><html:img page="/i/icon_help.gif" alt="Task Help" border="0"  width="32" height="32" /></A><br><font color=brown face=verdana size=1>&nbsp;Help&nbsp;</font></TD>

<%--GF29128
          <TD valign="TOP" align="CENTER" width="1%" colspan=1><A HREF="<%=request.getContextPath()%>/logout?FirstTimer=0" TARGET="_top"><html:img page="/i/logout.gif" alt="Logout" border="0"  width="32" height="32" /></A><br><font color=brown face=verdana size=1>&nbsp;in-pro Logout&nbsp;</font></TD>
--%>
<%--GF29128 Begin, D.An,20130729. --%>
					<TD id="idLogout" style="display:none" valign="TOP" align="CENTER" width="1%" colspan=1>
						<html:link page="/logout?FirstTimer=0" target="_top">
							<html:img page="/i/logout.gif" alt="Logout" border="0" width="32"
								height="32" />
						</html:link>
						<br>
						<font color=brown face=verdana size=1>&nbsp; Logout&nbsp;</font>
					</TD>
					<TD id="idLogin" style="display:none" valign="TOP" align="CENTER" width="1%" colspan=1>
						<html:link page="/logout?FirstTimer=0" target="_top">
							<html:img page="/i/icon_login.gif" alt="Logout" border="0" width="32"
								height="32" />
						</html:link>
						<br>
						<font color=brown face=verdana size=1>&nbsp;Login&nbsp;</font>
					</TD>
 <input type="hidden" id="myInputUserName" name="myInputUserName" value=<%=username %>/>
 <%--GF29128 end --%>	

        </TR>
      </TABLE>
    </td>
  </tr>
</TABLE>
<br>

