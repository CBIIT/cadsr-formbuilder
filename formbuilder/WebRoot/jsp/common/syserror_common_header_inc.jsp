
<%@ page import="gov.nih.nci.ncicb.cadsr.common.CaDSRConstants"%>
<%
	String dest = pageContext.getRequest().getParameter("loginDestination");
%>

<SCRIPT LANGUAGE="JavaScript1.1" SRC='<html:rewrite page="/js/newWinJS.js"/>'></SCRIPT>

<%@ include  file="topHeader.jsp" %>

<TABLE width=100% Cellpadding=0 Cellspacing=0 border=0>
  <tr>

    <td align="left" nowrap>
    <html:img page="/i/graphic6.gif" border="0" />
    </td>

    <td align=right valign=top colspan=2 nowrap>
      <TABLE Cellpadding=0 Cellspacing=0 border=0 >
        <TR>
          <TD valign="TOP" align="CENTER" width="1%" colspan=1>
             <A HREF='<%=request.getContextPath()%>/formSearchAction.do' TARGET="_top">
               <html:img page="/i/formicon.gif" alt="FormBuilder" border="0"  width="32" height="32" />
             </A><br><font color=brown face=verdana size=1>&nbsp;Form&nbsp;Builder&nbsp;</font>
           </TD>
          <TD valign="TOP" align="CENTER" width="1%" colspan=1>
          <A HREF="javascript:newBrowserWin('/help','helpWin',1200,900)">
            <html:img page="/i/icon_help.gif" alt="Help" border="0"  width="32" height="32" />
          </A><br><font color=brown face=verdana size=1>&nbsp;Help&nbsp;</font></TD>
        </TR>
      </TABLE>
    </td>
  </tr>
</TABLE>
<br>
