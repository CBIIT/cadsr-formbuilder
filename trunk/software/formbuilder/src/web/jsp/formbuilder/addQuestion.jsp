<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/cdebrowser.tld" prefix="cde"%>
<%@ page import="oracle.clex.process.jsp.GetInfoBean "%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.html.* "%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.util.* "%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.CaDSRConstants"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.formbuilder.struts.common.FormConstants"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.formbuilder.struts.common.NavigationConstants"%>
<% 
  CDEBrowserParams params = CDEBrowserParams.getInstance();
  String browseURL = params.getCdeBrowserUrl();
%>
<html:html>
<HEAD>
<TITLE>Formbuilder: Add Question</TITLE>
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache"/>
<LINK rel="stylesheet" TYPE="text/css" HREF="<html:rewrite page='/css/blaf.css' />">
<bean:define id="qIndex" name="addQuestionForm" property="<%= FormConstants.QUESTION_INDEX %>"/> 
<bean:define id="moduleIndex" name="addQuestionForm" property="<%=FormConstants.MODULE_INDEX%>"/>
<SCRIPT LANGUAGE="JavaScript">
  function switchAll(e)
  {
	if (e.checked) {
	    CheckAll();
	}
	else {
	    ClearAll();
	}
  }
  function Check(e)
    {
	e.checked = true;
    }

  function Clear(e)
    {
	e.checked = false;
    }  

  function CheckAll()
    {
	var fo = document.forms[0];
	var len = fo.elements.length;
	for (var i = 0; i < len; i++) {
	    var e = fo.elements[i];
	    if (e.name == "<%= FormConstants.SELECTED_ITEMS %>") {
		Check(e);
	    }
	}
	fo.toggleAll.checked = true;
    }
  
  function ClearAll()
    {
	var fo = document.forms[0];
	var len = fo.elements.length;
	for (var i = 0; i < len; i++) {
	    var e = fo.elements[i];
            if (e.name == "<%= FormConstants.SELECTED_ITEMS %>") {
		Clear(e);
	    }
	}
	fo.toggleAll.checked = false;
    }

  function validate(fo) {
	var len = fo.elements.length;
        var oneChecked = false;
	for (var i = 0; i < len; i++) {
	    var e = fo.elements[i];
            if (e.name == "<%= FormConstants.SELECTED_ITEMS %>") {
              if(e.checked == true) {
                 oneChecked=true;
                 i = len;
              }
            }            
        }    
        if(oneChecked == true) {
          return true;
        } else {
          alert("Please Select at least one Data Element");
          return false;
        }
  }

  function submitForm(methodName) {
     var f = document.forms[0];
     if(validate(f) == true)
     {
       document.forms[0].<%=NavigationConstants.METHOD_PARAM%>.value=methodName;
       f.submit();
       }
  }
  
  function retrieveCDEs(methodName) {
       document.forms[0].<%=NavigationConstants.METHOD_PARAM%>.value=methodName;
       document.forms[0].submit();
  }
  
  function submitCancelForm() {
          document.location.href= '<%=request.getContextPath()+"/cancelAction.do?" + NavigationConstants.METHOD_PARAM + "=" + NavigationConstants.GET_MODULE_TO_EDIT
        		  																	+"&"+FormConstants.QUESTION_INDEX +"="+qIndex 
        		  																	+"&"+FormConstants.MODULE_INDEX +"="+moduleIndex
        		  																	%>';
      }     
  
function details(linkParms ){
  var urlString="<%=browseURL%>" + "/CDEBrowser/search?dataElementDetails=9" + linkParms + "&PageId=DataElementsGroup"+"&queryDE=yes";
  newBrowserWin(urlString,'deDetails',800,600)
  
}
  
</SCRIPT>
</HEAD>
<BODY topmargin=0 bgcolor="#ffffff" topmargin="0">

<% 
  String urlPrefix = "";
%>
<%@ include file="../common/in_process_common_header_inc.jsp"%>
<jsp:include page="../common/tab_inc.jsp" flush="true">
  <jsp:param name="label" value="Add&nbsp;Question"/>
  <jsp:param name="urlPrefix" value=""/>
</jsp:include>
<table>
    <tr>    
      <td align="left" class="AbbreviatedText">
        <bean:message key="cadsr.formbuilder.helpText.form.module.addquestion"/>
      </td>
    </tr>  
</table> 
<html:form action='<%= "/addQuestion" %>' 
  onsubmit="validate(this)">
<html:hidden property="<%= FormConstants.QUESTION_INDEX %>"/>
<html:hidden value="" property="<%=NavigationConstants.METHOD_PARAM%>"/>
<html:hidden property="<%=FormConstants.MODULE_INDEX%>"/>


<%@ include file="addQuestion_inc.jsp" %>

<%@ include file="showMessages.jsp" %>


<logic:present name="<%=CaDSRConstants.CDE_CART%>">
  <table width="90%" align="center" cellpadding="1" cellspacing="1" border="0" class="OraBGAccentVeryDark">
    <tr class="OraTableColumnHeader">
      <th scope="col"><input type="checkbox" name="toggleAll" title="<bean:message key="cadsr.formbuilder.selectAll"/>" onclick="switchAll(this)"/></th>
      <th scope="col"><bean:message key="cadsr.formbuilder.question.longName"/></th>
      <th scope="col"><bean:message key="cadsr.formbuilder.question.comments"/></th>
      <th scope="col"><bean:message key="cadsr.formbuilder.question.context"/></th>
      <th scope="col"><bean:message key="cadsr.formbuilder.question.registration"/></th>
      <th scope="col"><bean:message key="cadsr.formbuilder.question.workflow"/></th>
      <th scope="col"><bean:message key="cadsr.formbuilder.question.publicID"/></th>
      <th scope="col"><bean:message key="cadsr.formbuilder.question.version"/></th>
    </tr>
  <logic:empty name="<%=CaDSRConstants.CDE_CART%>" property = "dataElements">
    <tr class="OraTabledata">
        <td class="OraFieldText" colspan="9">
          CDE Cart is empty. 
        </td>
    </tr>
  </logic:empty>
   <logic:notEmpty name="<%=CaDSRConstants.CDE_CART%>" property = "dataElements">
    <logic:iterate id="de" name="<%=CaDSRConstants.CDE_CART%>" type="gov.nih.nci.ncicb.cadsr.objectCart.CDECartItem" property="dataElements" indexId="itemId">
<%
      String deId = de.getId();
      String detailsURL = "javascript:details('&p_de_idseq="+deId +"')";
%>
      <tr class="OraTabledata">

          <td class="OraFieldText">
           <html:checkbox property="<%= FormConstants.SELECTED_ITEMS %>" value="<%= itemId.toString() %>"/>
          </td>
         <td class="OraFieldText">
          <a href="<%= detailsURL %>">
            <bean:write name="de" property="item.longName"/>
          </a>
         </td>
      
        <td class="OraFieldText">
          <bean:write name="de" property="item.longCDEName"/>
        </td>
        <td class="OraFieldText">
          <bean:write name="de" property="item.contextName"/>
        </td>
        <td class="OraFieldText">
	  <bean:write name="de" property="item.registrationStatus"/>
        </td>
        <td class="OraFieldText">
          <bean:write name="de" property="item.aslName"/>
        </td>
        <td class="OraFieldText">
          <bean:write name="de" property="item.publicId"/>
        </td>
        <td class="OraFieldText">
          <bean:write name="de" property="item.version"/>
        </td>
      </tr>
    </logic:iterate>
    <tr class="OraTabledata">
      <td colspan=9 class="OraFieldText">
        <a href="javascript:CheckAll()">Check All</a>&nbsp;-&nbsp;<a href="javascript:ClearAll()">Clear All</a>
      </td>
    </tr>
  </logic:notEmpty>
    </table>
    <br>

	<%@ include file="addQuestion_inc.jsp" %>
  
  <table width="10%" align="center" cellpadding="1" cellspacing="1" border="0" >
    
    <tr >
      <td>
        <a target="_blank" href='<%=params.getCdeBrowserUrl()%>'><html:img src='<%=urlPrefix+"i/add_more_data_elements.gif"%>' border="0" alt="Add more data elements"/></a>
      </td>
    </tr>
  </table>    
</logic:present>
</html:form>
<%@ include file="../common/common_bottom_border.jsp"%>
</body>
</html:html>


