
<%@ page import="gov.nih.nci.ncicb.cadsr.common.CaDSRConstants"%>


<SCRIPT LANGUAGE="JavaScript1.1" SRC='<html:rewrite page="/js/newWinJS.js"/>'></SCRIPT>
<SCRIPT LANGUAGE="JavaScript1.1" SRC='<html:rewrite page="/js/helpWinJS.js"/>'></SCRIPT>

<%@ include  file="../common/topHeader.jsp" %>

<TABLE valign="top" width=100% Cellpadding=0 Cellspacing=0 border=0>
  <tr>

    <td valign="top" align="left" nowrap>

    <html:img page="/i/cde_form_builder_banner_full.gif" border="0" />
    </td>

    <td align=right valign="top" colspan=2 nowrap>
      <TABLE valign="top" Cellpadding=0 Cellspacing=0 border=0 >
        <TR>
          <TD valign="TOP" align="CENTER" width="1%" colspan=1><A HREF="javascript:newBrowserWin('<%=request.getContextPath()%>/html/common/help/cdeBrowserHelp.html','helpWin',1200,900)"><html:img page="/i/icon_help.gif" alt="Task Help" border="0"  width="32" height="32" /></A><br><font color=brown face=verdana size=1>&nbsp;Help&nbsp;</font></TD>
         <logic:present name="nciUser">
            <TD valign="TOP" align="CENTER" width="1%" colspan=1><A HREF="<%=request.getContextPath()%>/logout?FirstTimer=0" TARGET="_top"><html:img page="/i/logout.gif" alt="Logout" border="0"  width="32" height="32" /></A><br><font color=brown face=verdana size=1>&nbsp;Logout&nbsp;</font></TD>
          </logic:present>
        </TR>
      </TABLE>
    </td>
  </tr>
</TABLE>
<br>

