
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<TABLE width=100% cellspacing=0 cellpadding=0 border=0>
<TR>
<TD valign=bottom width=99%><html:img page="i/bottom_shade.gif" height="6" width="100%" /></TD>
<TD valign=bottom width="1%" align=right><html:img page="i/bottomblueright.gif" /></TD>
</TR>
</TABLE>
<TABLE width=100% cellspacing=0 cellpadding=0 bgcolor="#336699" border=0>
<TR>
<TD width="20%" align="LEFT">
&nbsp;
<FONT face="Arial" color="WHITE" size="-2">User: </FONT>
<FONT face="Arial" size="-1" color="#CCCC99">
<%
   String name = request.getParameter( "username" );
%>
<s:property value="%{#session.username}" />  
</FONT>
</td>
<td width="10%" align="right">
 <FONT color="white" size=-2 face=arial><a href="https://wiki.nci.nih.gov/x/qxEhAQ" target="_blank">Privacy Notice</a></FONT>
</TD>

<td width="30%" align="right">
 <FONT color="white" size=-2 face=arial>4.1 Build 1 Iteration 1
 </FONT>
</TD>

<td td width="70%" align="right">
  <FONT color="white" size=-3 face=arial>
     Please send comments and suggestions to <A href="mailto:ncicbiit@mail.nih.gov
">
     	ncicbiit@mail.nih.gov
</A>      
  </FONT>
   &nbsp; &nbsp;
</td>
</TR>
<TR>
<TD colspan=4><html:img page="i/bottom_middle.gif" height="6" width="100%" /></TD>
</TR>
</TABLE>