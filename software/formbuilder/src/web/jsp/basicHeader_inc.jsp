
<SCRIPT SRC='<html:rewrite page="/js/helpWinJS.js"/>'></SCRIPT>
<SCRIPT SRC='<html:rewrite page="/js/newWinJS.js"/>'></SCRIPT>
<%@ include  file="common/topHeader.jsp" %>
<TABLE width=100% Cellpadding=0 Cellspacing=0 border=0>
  <tr>
    <td align="left" nowrap>

    <img src="<%=request.getContextPath()%>/i/cde_form_builder_banner_full.gif" alt="CDE Form Builder" border=0>
    </td>

    <td align=right valign=top nowrap>
      <TABLE Cellpadding=0 Cellspacing=0 border=0>
        <TR>
          <TD valign="TOP" align="center" width="1%" colspan=1><A target="_blank" HREF="<%=params.getCdeBrowserUrl()%>" TARGET="_top"><IMG SRC="<%=request.getContextPath()%>/i/icon_cdebrowser.gif" alt="CDE Browser" border=0  width=32 height=32></A></TD>
          <TD valign="TOP" align="left" width="1%" colspan=1><A HREF="<%=params.getFormBuilderHelpUrl()%>" target="_blank"><IMG SRC="<%=request.getContextPath()%>/i/icon_help.gif" alt="Task Help" border=0  width=32 height=32></A></TD>
        </TR>
        <TR>
          <TD valign="TOP" align="center" colspan=1><font color=brown face=verdana size=1>&nbsp;CDE Browser&nbsp;</font></TD>
          <TD valign="TOP" align="left" colspan=1><font color=brown face=verdana size=1>&nbsp;Help&nbsp;</font></TD>
        </TR>
      </TABLE>
    </td>
  </tr>
</TABLE>