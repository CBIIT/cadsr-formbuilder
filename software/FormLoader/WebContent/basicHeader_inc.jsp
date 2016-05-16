

<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include  file="common/topHeader.jsp" %>
<%@ page import="java.io.IOException" %>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.*"%>
<% 

String formbuilderUrl = "";
try {
	formbuilderUrl = CaDSRUtilV2.getFormBuilderUrlNoCache();
} catch (IOException ioe) {
	System.out.println(ioe.getMessage());
	formbuilderUrl = "https://formbuilder.nci.nih.gov/FormBuilder/";
}

String cdeBrowserUrl = "";
try {
  cdeBrowserUrl = CaDSRUtilV2.getCdeBrowserUrlNoCache();
} catch (IOException ioe) {
  System.out.println(ioe.getMessage());
  cdeBrowserUrl = "https://cdebrowser.nci.nih.gov";
}

%>

<TABLE width=100% Cellpadding=0 Cellspacing=0 border=0>
  <tr>
    <td align="left" valign="TOP">
       <a href="http://cbiit.nci.nih.gov/ncip/biomedical-informatics-resources/interoperability-and-semantics/metadata-and-models" target="_blank"><img src="<%=request.getContextPath()%>/i/caDSR_logo.gif" border=0></a>
    </td>
    <%--td align="left" nowrap>
    <img src="<%=request.getContextPath()%>/i/form_loader_banner.jpg" border=0>
    </td--%>

    <td align=right valign=top nowrap>
      <TABLE Cellpadding=0 Cellspacing=0 border=0>

          <TD valign="TOP" align="left" width="1%" colspan=1>
          <A HREF="<%=formbuilderUrl%>" target="_blank">
          <IMG SRC="<%=request.getContextPath()%>/i/form_builder_icon.jpg" alt="Form Builder" border=0  width=32 height=32></A>
          </TD>
          <TD valign="TOP" align="left" width="1%" colspan=1><A HREF="<%=cdeBrowserUrl%>" target="_blank"><IMG SRC="<%=request.getContextPath()%>/i/icon_cdebrowser.gif" alt="CDE Browser" border=0  width=32 height=32></A></TD>
          <TD valign="TOP" align="left" width="1%" colspan=1><A HREF=" https://wiki.nci.nih.gov/x/MYC2DQ" target="_blank"><IMG SRC="<%=request.getContextPath()%>/i/icon_help.gif" alt="Task Help" border=0  width=32 height=32></A></TD>
		  <TD valign="TOP" align="CENTER" width="1%" colspan=1>
		  	  <s:form action="home" method="post">
				<s:submit type="image" src="/FormLoader/i/icon_home.gif" method="execute" alt="form loader home" align="left" />
			  </s:form>
		  </TD>
		  <TD valign="TOP" align="left" width="1%" colspan=1>
			  <s:form action="logout" method="post">
				<s:submit type="image" src="/FormLoader/i/logout.gif" method="execute" alt="Logout" align="left" />
			  </s:form>
		  </TD>
        </TR>
        <TR>
          <TD valign="TOP" align="left" colspan=1><font color=brown face=verdana size=1>&nbsp;Form Builder&nbsp;</font></TD>
          <TD valign="TOP" align="left" colspan=1><font color=brown face=verdana size=1>&nbsp;CDE Browser&nbsp;</font></TD>
          <TD valign="TOP" align="left" colspan=1><font color=brown face=verdana size=1>&nbsp;Help&nbsp;</font></TD>
          <TD valign="TOP" align="left" colspan=1><font color=brown face=verdana size=1>&nbsp;Home&nbsp;</font></TD>
          <TD valign="TOP" align="left" colspan=1><font color=brown face=verdana size=1>&nbsp;Logout&nbsp;</font></TD>
        </TR>
      </TABLE>
    </td>
  </tr>
</TABLE>