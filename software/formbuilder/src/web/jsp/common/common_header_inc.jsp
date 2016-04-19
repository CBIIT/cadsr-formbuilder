

<%@ page import="java.io.IOException" %>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.*"%>
<%@page import="gov.nih.nci.ncicb.cadsr.common.util.* " %>

<%
String destLogin = pageContext.getRequest().getParameter("loginDestination");

String username=(String) session.getAttribute("myUsername");
System.out.println("common_header_inc.jsp:" + username);

CDEBrowserParams params = CDEBrowserParams.getInstance();
	
String formloaderUrl = "";

try {
	formloaderUrl = CaDSRUtil.getFormLoaderUrlNoCache();
} catch (IOException ioe) {
	formloaderUrl = "https://formbuilder.nih.nci.gov/FormBuilder";
}

%>

<SCRIPT LANGUAGE="JavaScript1.1"
	SRC='<html:rewrite page="/js/helpWinJS.js"/>'></SCRIPT>
<SCRIPT LANGUAGE="JavaScript1.1"
	SRC='<html:rewrite page="/js/newWinJS.js"/>'></SCRIPT>
	
<TABLE width=100% valign=top Cellpadding=0 Cellspacing=0 border=0>
	<tr>
		<td align="left" valign=top nowrap>
			<img src=i/cde_form_builder_banner.gif alt="Form Builder Banner" border=0>
		</td>
		<td align=right valign=top colspan=2 nowrap>
			<TABLE Cellpadding=0 Cellspacing=0 border=0>
				<TR>
					<TD valign="TOP" align="CENTER" width="1%" colspan=1>
						<A HREF="<%="formsCartAction.do?method=displayFormsCartV1"%>"
							TARGET="_top"><IMG SRC="i/cde_cart.gif" alt="Form Cart 1"
								border=0> </A>
						<br>
						<font color=brown face=verdana size=1>&nbsp;Form &nbsp;Cart 1</font>
					</TD>
					<TD valign="TOP" align="CENTER" width="1%" colspan=1>
						<A HREF="<%="formsCartAction.do?method=displayFormsCartV2"%>"
							TARGET="_top"><IMG SRC="i/cde_cart.gif" alt="Form Cart 2"
								border=0> </A>
						<br>
						<font color=brown face=verdana size=1>&nbsp;Form &nbsp;Cart2<sup><font color="red"><b>New!</b></font></sup></font>
					</TD>
					<TD valign="TOP" align="CENTER" width="1%" colspan=1>
						<A HREF="<%="formCDECartAction.do?method=displayCDECart"%>"
							TARGET="_top"><IMG SRC="i/cde_cart.gif" alt="CDE Cart"
								border=0> </A>
						<br>
						<font color=brown face=verdana size=1>&nbsp;CDE &nbsp;Cart</font>
					</TD>
					<TD valign="TOP" align="CENTER" width="1%" colspan=1>
						<A HREF="<%= request.getContextPath() %>"
							TARGET="_top"><IMG SRC="i/icon_home.gif" alt="Home"
								border=0> </A>
						<br>
						<font color=brown face=verdana size=1>&nbsp;Home</font>
					</TD>
					
					<TD valign="TOP" align="CENTER" width="1%" colspan=1>
						<A
							HREF="<%=params.getFormBuilderHelpUrl()%>" target="_blank"><html:img
								page="/i/icon_help.gif" alt="Task Help" border="0" width="32"
								height="32" /> </A>
						<br>
						<font color=brown face=verdana size=1>&nbsp;Help&nbsp;</font>
					</TD>

<%--GF29128 Begin, See implementation in <head>   D.An,20130729. --%>
					<TD id="idLogout" style="display:none" valign="TOP" align="CENTER" width="1%" colspan=1>
						<html:link page="/logout?FirstTimer=0" target="_top">
							<html:img page="/i/logout.gif" alt="Logout" border="0" width="32"
								height="32" />
						</html:link>
						<br>
						<font color=brown face=verdana size=1>&nbsp;Logout&nbsp;</font>
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
</table>

<TABLE align="left" width="90%" Cellpadding=0 Cellspacing=0 border=0>
	<tr>
<%--GF26236 Begin, See implementation in <head>  D.An,20130808. --%>
		<td align="center" width="10%" height="10">
			<span style="font-size: 9pt; font-family: Arial"> <a id="idCDEBrowser" 
				href="<%=params.getCdeBrowserUrl()%>" target="_blank">CDE<br>Browser</a> </span>
		</td>
		
		<td align="center" width="10%" height="10">
			<span style="font-size: 9pt; font-family: Arial"> <a id="idCaDSRAdmin" 
				href="http://cadsradmin.nci.nih.gov" target="_blank">Admin<br>Tool</a> </span>
		</td>
 <%--GF26236 end --%>
 		
		<td align="center" width="10%" height="10">
			<span style="font-size: 9pt; font-family: Arial"> <a
				href="<%=params.getCurationToolUrl()%>" target="_blank">Curation<br>Tool</a> </span>
		</td>
		
		<td align="center" width="10%" height="10">
			<span style="font-size: 9pt; font-family: Arial"> <a
				href="<%=params.getSentinelToolUrl()%>" target="_blank">Sentinel<br>Tool</a> </span>
		</td>
		
		<%-- <td align="center" width="15%" height="10">
			<span style="font-size: 9pt; font-family: Arial"> <a
				href="<%=params.getUmlBrowserUrl()%>" target="_blank">UML Model<br>Browser</a> </span>
		</td> --%>
		
		<td align="center" width="15%" height="10">
			<span style="font-size: 9pt; font-family: Arial"> <a
				href="<%=params.getNciMetathesaurusUrl()%>" target="_blank">NCI<br>Metathesaurus</a> </span>
		</td>
		
		<td align="center" width="20%" height="10">
			<span style="font-size: 9pt; font-family: Arial"> <a
				href="<%=params.getNciTerminologyServerUrl()%>" target="_blank">NCI Terminology<br>Server</a> </span>
		</td>
		<%-- <td align="center" width="10%" height="10">
			<span style="font-size: 9pt; font-family: Arial"> <a id="idCDEBrowser" 
				href="<%=formloaderUrl%>" target="_blank">Form<br>Loader</a> </span>
		</td> --%>
		<td align="center" width="10%" height="10">
			<span style="font-size: 10.0pt; font-family: Arial"> <A
				HREF="<%=params.getFormBuilderWhatsNewUrl()%>" target="_blank">
					What's new</a>&nbsp;&nbsp;</span>
		</td>
		
		
	</tr>
</table>
<br>
<br>