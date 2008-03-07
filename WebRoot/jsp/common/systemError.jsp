<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<HTML>
  <HEAD>
    <TITLE>Form Builder: System Error</TITLE>
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
	<LINK REL=STYLESHEET TYPE="text/css" HREF="<%=request.getContextPath()%>/css/blaf.css">
  </HEAD>
  <BODY topmargin=0 bgcolor="#ffffff">
    <% 
    	String urlPrefix = "";
    %>
    <%@ include file="syserror_common_header_inc.jsp" %>

    <jsp:include page="tab_inc.jsp" flush="true">
      <jsp:param name="label" value="System&nbsp;Error"/>
      <jsp:param name="urlPrefix" value=""/>
    </jsp:include>

    <table>
      <tr>
        <td class="OraErrorHeader">
        	Unexpected System Error has Occured <br><br>
      <logic:messagesPresent >
       <table width="80%" align="center">
        <html:messages id="error" >
          <logic:present name="error">
            <tr align="center" >
               <td  align="left" class="OraErrorText" nowrap>
                <b><bean:write  name="error"/></b>
              </td>
            </tr>
          </logic:present>          
        </html:messages> 
           <tr align="center" >
             <td>
                &nbsp;
            </td>
           </tr>        
       </table>
      </logic:messagesPresent>  
  <!--
   <%  
    java.lang.Throwable exception = (java.lang.Throwable) 
    request.getAttribute("org.apache.struts.action.EXCEPTION");
    
    if (exception !=null)
	exception.printStackTrace(new java.io.PrintWriter(out));
    %>
  -->
        </td>
      </tr>
    </table>
<%@ include file="common_bottom_border.jsp"%>
  </BODY>
</HTML>
