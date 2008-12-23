<%@page import="gov.nih.nci.ncicb.cadsr.common.util.*"%>
<%
	CDEBrowserParams params = CDEBrowserParams.getInstance();
%>


<SCRIPT LANGUAGE="JavaScript1.1"
	SRC='<html:rewrite page="/js/helpWinJS.js"/>'></SCRIPT>
<SCRIPT LANGUAGE="JavaScript1.1"
	SRC='<html:rewrite page="/js/newWinJS.js"/>'></SCRIPT>
<TABLE width=100% valign=top Cellpadding=0 Cellspacing=0 border=0>
	<tr>
		<td align="left" valign=top nowrap>
			<img src=i/cde_form_builder_banner.gif border=0>
		</td>
		<td align=right valign=top colspan=2 nowrap>
			<TABLE Cellpadding=0 Cellspacing=0 border=0>
				<TR>
					<TD valign="TOP" align="CENTER" width="1%" colspan=1>
						<A HREF="<%="formCDECartAction.do?method=displayCDECart"%>"
							TARGET="_top"><IMG SRC="i/cde_cart.gif" alt="CDE Cart"
								border=0> </A>
						<br>
						<font color=brown face=verdana size=1>&nbsp;CDE &nbsp;Cart</font>
					</TD>
					<TD valign="TOP" align="CENTER" width="1%" colspan=1>
						<A
							HREF="<%=params.getFormBuilderHelpUrl()%>" target="_blank"><html:img
								page="/i/icon_help.gif" alt="Task Help" border="0" width="32"
								height="32" /> </A>
						<br>
						<font color=brown face=verdana size=1>&nbsp;Help&nbsp;</font>
					</TD>
					<TD valign="TOP" align="CENTER" width="1%" colspan=1>
						<html:link page="/logout?FirstTimer=0" target="_top">
							<html:img page="/i/logout.gif" alt="Logout" border="0" width="32"
								height="32" />
						</html:link>
						<br>
						<font color=brown face=verdana size=1>&nbsp;Logout&nbsp;</font>
					</TD>
				</TR>
			</TABLE>
		</td>
	</tr>
</table>
<br>
<TABLE align="left" width="100%" Cellpadding=0 Cellspacing=0 border=0>
	<tr>
		<td align="center" width="12%" height="10">
			<span style="font-size: 9pt; font-family: Arial"> <a
				href="<%=params.getCdeBrowserUrl()%>" target="_blank">&nbsp;CDE	Browser&nbsp;</a> </span>
		</td>
		<td>|</td>
		<td align="center" width="12%" height="10">
			<span style="font-size: 9pt; font-family: Arial"> <a
				href="<%=request.getContextPath()%>/html/adminRedirection.html" target="_blank">&nbsp;Admin Tool&nbsp;</a> </span>
		</td>
		<td>|</td>
		<td align="center" width="12%" height="10">
			<span style="font-size: 9pt; font-family: Arial"> <a
				href="<%=params.getCurationToolUrl()%>" target="_blank">&nbsp;Curation Tool&nbsp;</a> </span>
		</td>
		<td>|</td>
		<td align="center" width="12%" height="10">
			<span style="font-size: 9pt; font-family: Arial"> <a
				href="<%=params.getSentinelToolUrl()%>" target="_blank">&nbsp;Sentinel Tool&nbsp;</a> </span>
		</td>
		<td>|</td>
		<td align="center" width="12%" height="10">
			<span style="font-size: 9pt; font-family: Arial"> <a
				href="<%=params.getUmlBrowserUrl()%>" target="_blank">&nbsp;UML Model Browser&nbsp;</a> </span>
		</td>
		<td>|</td>
		<td align="center" width="14%" height="10">
			<span style="font-size: 9pt; font-family: Arial"> <a
				href="<%=params.getNciMetathesaurusUrl()%>" target="_blank">&nbsp;NCI Metathesaurus&nbsp;</a> </span>
		</td>
		<td>|</td>
		<td align="center" width="14%" height="10">
			<span style="font-size: 9pt; font-family: Arial"> <a
				href="<%=params.getNciTerminologyServerUrl()%>" target="_blank">&nbsp;NCI Terminology Server&nbsp;</a> </span>
		</td>
		<td>|</td>
		<td align="center" width="12%" height="10">
			<span style="font-size: 10.0pt; font-family: Arial"> <A
				HREF="<%=params.getFormBuilderHelpUrl()%>" target="_blank">
					What's new</a>&nbsp;&nbsp;</span>
		</td>
	</tr>
</table>
<br>
<br>