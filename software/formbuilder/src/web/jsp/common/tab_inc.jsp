<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>

<%
	String urlPrefix = request.getContextPath();
	String label = pageContext.getRequest().getParameter("label");
%>

<TABLE width=100% Cellpadding=0 Cellspacing=0 border=0>
	<tr>
		<td id="noneViewer" width=90% align="left" class="OraInlineInfoText" nowrap>
			&nbsp;
			<logic:present name="nciUser">
				<bean:message key="user.greet" />
				<bean:write name="nciUser" property="username" scope="session" />
			</logic:present>
			<logic:notPresent name="nciUser">
				&nbsp;
			</logic:notPresent>
		</td>
    <td id="urViewer" style="display:none" width=90% align="left" class="OraInlineInfoText" nowrap>
    VIEWER - please login.
	</td>
		<td valign=bottom align=right>
			<table border=0 cellpadding=0 cellspacing=0>
				<tr>
<TD bgcolor="#336699" width="1%" align=LEFT valign=TOP><IMG SRC="<%=urlPrefix%>/i/ctab_open.gif" alt="image" height=21 width=18 border=0></TD>
<TD width=2% bgcolor="#336699"><b><font size="-1" face="Arial" color="#FFFFFF"><%=label%></font></b></TD>
<TD bgcolor="#336699" width="1%" align=RIGHT valign=TOP><IMG SRC="<%=urlPrefix%>/i/ctab_close.gif" alt="image" height=21 width=12 border=0></TD>
				</tr>
			</table>
		</td>
	</TR>
</TABLE>

<TABLE width=100% Cellpadding=0 Cellspacing=0 border=0>
<TR>
<td align=left valign=top width="1%" bgcolor="#336699"><img src="<%=urlPrefix%>/i/top_left.gif" alt="image" width=4 height="25"></td>
<td align=left valign=top width="5%" bgcolor="#336699">&nbsp;</td>


<td align=left valign=top width="5%" bgcolor="#336699">&nbsp;</td>

<!-- add here --->

<TD align=left valign=center bgcolor="#336699" height=25 width="94%">&nbsp;</TD>
</tr>
</table>

<table  width=100% Cellpadding=0 Cellspacing=0 border=0>
<tr>
<td align=right valign=top width=49 height=21 bgcolor="#336699"><img src="<%=urlPrefix%>/i/left_end_bottom.gif" alt="image" height=21 width=49></td>
<TD align=right valign=top bgcolor="#FFFFFF" height=21 width="100%"><img src="<%=urlPrefix%>/i/bottom_middle.gif" alt="image" height=6 width=100%></TD>
<td align="LEFT" valign=top height=21 width=5  bgcolor="#FFFFFF"><img src="<%=urlPrefix%>/i/right_end_bottom.gif" alt="image" height=7 width=5></td>
</TR>
</TABLE>

