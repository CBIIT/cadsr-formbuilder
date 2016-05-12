<%@ include  file="common/topHeader.jsp" %>
<%@ page import="java.io.IOException" %>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.*"%>
<% 

String formbuilderUrl = "";
try {
	formbuilderUrl = CaDSRUtil.getFormBuilderUrlNoCache();
} catch (IOException ioe) {
  System.out.println(ioe.getMessage());
	formbuilderUrl = "https://formbuilder.nih.nci.gov/FormBuilder/";
}

String cdeBrowserUrl = "";
try {
  cdeBrowserUrl = CaDSRUtil.getCdeBrowserUrlNoCache();
} catch (IOException ioe) {
  System.out.println(ioe.getMessage());
  cdeBrowserUrl = "https://cdebrowser.nci.nih.gov";
}
%>
<TABLE width=100% Cellpadding=0 Cellspacing=0 border=0>
  <tr>
    <td align="left" valign="TOP">
       <a href="http://cbiit.nci.nih.gov/" target="_blank"><img src="<%=request.getContextPath()%>/i/caDSR_logo.gif" border=0></a>
    </td>
    <%-- <td align="left" nowrap>
    <img src="<%=request.getContextPath()%>/i/form_loader_banner.jpg" border=0>
    </td> --%>

    <td align=right valign=top nowrap>
      <TABLE Cellpadding=0 Cellspacing=0 border=0>
        <TR>
          <TD valign="TOP" align="center" width="1%" colspan=1><A HREF="<%=formbuilderUrl%>" ><IMG SRC="<%=request.getContextPath()%>/i/form_builder_icon.jpg" alt="Form Builder" border=0  width=32 height=32></A></TD>
          <TD valign="TOP" align="center" width="1%" colspan=1><A HREF="<%=cdeBrowserUrl%>" target="_blank"><IMG SRC="<%=request.getContextPath()%>/i/icon_cdebrowser.gif" alt="CDE Browser" border=0  width=32 height=32></A></TD>
          <TD valign="TOP" align="left" width="1%" colspan=1><A HREF="<s:url value="https://wiki.nci.nih.gov/x/MYC2DQ"/>"  target="_blank"><IMG SRC="<%=request.getContextPath()%>/i/icon_help.gif" alt="Task Help" border=0  width=32 height=32></A></TD>
        </TR>
        <TR>
          <TD valign="TOP" align="center" colspan=1><font color=brown face=verdana size=1>&nbsp;Form Builder&nbsp;</font></TD>
          <TD valign="TOP" align="center" colspan=1><font color=brown face=verdana size=1>&nbsp;CDE Browser&nbsp;</font></TD>
          <TD valign="TOP" align="left" colspan=1><font color=brown face=verdana size=1>&nbsp;Help&nbsp;</font></TD>
        </TR> 
      </TABLE>
    </td>
  </tr>
</TABLE>