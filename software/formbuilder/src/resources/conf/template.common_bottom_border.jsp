<%@ page import="gov.nih.nci.ncicb.cadsr.common.util.CDEBrowserParams"%>
<TABLE width=100% cellspacing=0 cellpadding=0 border=0>
<TR>
<TD valign=bottom width=99%><html:img page="/i/bottom_shade.gif" alt="shade" height="6" width="100%" /></TD>
<TD valign=bottom width="1%" align=right><html:img page="/i/bottomblueright.gif" alt="image"/></TD>
</TR>
</TABLE>
<TABLE width=100% cellspacing=0 cellpadding=0 bgcolor="#336699" border=0>
<TR>
<TD width="20%" align="LEFT">
&nbsp;
<FONT face="Arial" color="WHITE" size="-2">User: </FONT>
<FONT face="Arial" size="-1" color="#CCCC99">
  <logic:present name="nciUser">
    <bean:write name="nciUser" property="username"  scope="session"/>
  </logic:present>
  <logic:notPresent name="nciUser">
    Public User    
  </logic:notPresent>
</FONT>
</td>

<td width="10%" align="right">
 <FONT color="white" size=-2 face=arial><a href="https://wiki.nci.nih.gov/x/qxEhAQ" target="_blank">Privacy Notice</a></FONT>
</TD>

<td width="30%" align="right">
 <FONT color="white" size=-2 face=arial>Version @Tool.Version@&nbsp;&nbsp;
 <%=CDEBrowserParams.mode%></FONT>
</TD>

<td td width="70%" align="right">
  <FONT color="white" size=-3 face=arial>
     Please send comments and suggestions to <A href="mailto:@Tool.Support@">
     	@Tool.Support@</A>      
  </FONT>
   &nbsp; &nbsp;
</td>
</TR>
<TR>
<TD colspan=4><html:img page="/i/bottom_middle.gif" height="6" width="100%" alt="image"/></TD>
</TR>
</TABLE>