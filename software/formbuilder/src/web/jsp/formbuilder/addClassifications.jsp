<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/cdebrowser.tld" prefix="cde"%>
<%--@ page import="oracle.clex.process.jsp.GetInfoBean "--%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.html.* "%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.util.* "%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.CaDSRConstants"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.formbuilder.struts.common.FormConstants"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.formbuilder.struts.common.NavigationConstants"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.CaDSRConstants"%>
<HTML>
  <HEAD>
    <TITLE>Add Classifications</TITLE>
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache"/>
    <LINK rel="stylesheet" TYPE="text/css" HREF="<html:rewrite page='/css/blaf.css' />">
  </HEAD>
  <BODY topmargin=0 bgcolor="#ffffff">
    <%   
      String urlPrefix = "";
    %>
    <%@ include file="../common/in_process_common_header_inc.jsp"%>
    <jsp:include page="../common/tab_inc.jsp" flush="true">
      <jsp:param name="label" value="Add&nbsp;Classifications"/>
      <jsp:param name="urlPrefix" value=""/>
    </jsp:include>


    <SCRIPT>
      <!--
           function clearClassSchemeItem(i) {
           var name = '<%= FormConstants.CSI_NAME %>' + i;
           document.forms[0][name].value = "";
           name = '<%= FormConstants.CS_CSI_ID %>[' + i + ']';
           document.forms[0][name].value = "";
           }
           -->
    </SCRIPT>

    <logic:present name="<%=FormConstants.CRF%>">

    <%
      int nbOfClassifications = 3;
      String pageUrl = "&PageId=DataElementsGroup";
      String contextPath = request.getContextPath();
      String[] csLOVUrl = new String[3];

      String contextId = ((gov.nih.nci.ncicb.cadsr.common.resource.Form)session.getAttribute(FormConstants.CRF)).getContext().getConteIdseq();

      for(int i=0; i<nbOfClassifications; i++) 
      	csLOVUrl[i] = "javascript:newWin('"+contextPath+"/classificationLOVAction.do?method=getClassificationsLOV&chkContext=always&classificationsLOV=1&P_CONTE_IDSEQ=" + contextId +"&idVar=jspClassification[" + i + "]&nameVar=txtClassSchemeItem" + i + pageUrl + "','csLOV',1200,900)";
      //csLOVUrl[i] = "javascript:newWin('"+contextPath+"/search?chkContext=always&classificationsLOV=1&P_CONTE_IDSEQ=" + contextId +"&idVar=jspClassification[" + i + "]&nameVar=txtClassSchemeItem" + i + pageUrl + "','csLOV',1200,900)";

      %>

      <html:form action='<%="/addClassifications?"+NavigationConstants.METHOD_PARAM+"="+NavigationConstants.ADD_CLASSIFICATIONS%>'>

  <%@ include file="addClassifications_inc.jsp"%>    
  <%@ include file="showMessages.jsp" %>
  <table cellspacing="2" cellpadding="3" border="0" width="80%" >

    <% for(int i=0; i<nbOfClassifications; i++) { 
    	String jspName = FormConstants.CS_CSI_ID + "["+ i + "]";
    %>		
    <tr>
      <td class="OraFieldtitlebold" nowrap>CS Long Name:</td>
      <td class="OraFieldText" nowrap>
      <input type=text name="<%= FormConstants.CSI_NAME + i%>" 
        readonly="true" 
        size="19"
        styleClass="LOVField"
        />
        &nbsp;
        <a href="<%=csLOVUrl[i]%>"><img src="<%=urlPrefix%>i/search_light.gif" border="0" alt="Search for Classification Scheme Items"></a>&nbsp;
        <a href="javascript:clearClassSchemeItem(<%= i %>)"><i>Clear</i></a>  
         <input type=hidden name="<%= jspName%>" />      
      </td>
    </tr>
    <% } %>
    <tr>
      <td class="OraFieldtitlebold" nowrap><bean:message key="cadsr.formbuilder.form.classifyCDE"/>:
      </td>
     <td>
        <input type=checkbox class="OraFieldText" name="<%= FormConstants.CLASSIFY_CDE_ON_FORM%>" value ="true"
      />
      </td>
      </tr>
  </table>

  <%@ include file="addClassifications_inc.jsp"%>    
    
  </html:form>
</logic:present>

    <logic:notPresent name="<%=FormConstants.CRF%>">Selected form has been deleted by a different user </logic:notPresent>

    <%@ include file="../common/common_bottom_border.jsp"%>

  </BODY>
</HTML>
