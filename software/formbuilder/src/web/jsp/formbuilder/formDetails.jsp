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
<%@page import="gov.nih.nci.ncicb.cadsr.common.formbuilder.common.FormBuilderConstants" %>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.resource.Form"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.resource.*"%>
<%@ page import="java.util.*"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.formbuilder.struts.common.*"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.util.CDEBrowserParams"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.jsp.util.CDEDetailsUtils"%>

<% CDEBrowserParams params = CDEBrowserParams.getInstance();%> 

<%
   // TO DO : Replace this with appropriate struts tags
   boolean isPublished = false;
   Object o = session.getAttribute(FormConstants.CRF);
   if (o != null) {
      Form myCRF = (Form)o;
      isPublished = myCRF.getIsPublished();
   }

%>


<HTML>
  <HEAD>
    <TITLE>Formbuilder: Form Details</TITLE>
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache"/>
    <LINK rel="stylesheet" TYPE="text/css" HREF="<html:rewrite page='/css/blaf.css' />">

<script type="text/javascript">

/* CLOSED_IMAGE - the image to be displayed when the sublists are closed
 * OPEN_IMAGE   - the image to be displayed when the sublists are opened
 */
CLOSED_IMAGE='i/plus.png';
OPEN_IMAGE='i/minus.png';

/* makeCollapsible - makes a list have collapsible sublists
 * 
 * listElement - the element representing the list to make collapsible
 */
function makeCollapsible(listElements, idVal){
var i=0;
while (i<listElements.length) {

	var listElement = listElements.item(i);
	var attrs = listElement.attributes;
	if (attrs.length > 0) {
		if (attrs.getNamedItem('id') != null && attrs.getNamedItem('id').nodeValue==idVal) {

		  // loop over all child elements of the list
		  var fstChild=listElement.firstChild.firstChild;
		  if (fstChild!=null){

			// only process li elements (and not text elements)
			if (fstChild.nodeType==1){

				var sblng = fstChild.nextSibling;
			  // build a list of child ol and ul elements and hide them
			  var list=new Array();
			  while (sblng!=null){
				if (sblng.tagName=='TR'){
				  sblng.style.display='none';
				  list.push(sblng);
				}
				sblng=sblng.nextSibling;
			  }

			  // add toggle buttons
			  var node=document.createElement('img');
			  var row = document.createElement('tr');
			  row.setAttribute('class','OraTabledata');
				var col = document.createElement('td');
				col.setAttribute('class','OraFieldText'); 
				col.setAttribute('colspan','2');
				
			  node.setAttribute('src',CLOSED_IMAGE);
			  node.setAttribute('class','collapsibleClosed');
			  node.onclick=createToggleFunction(node,list);

			  col.appendChild(node);
			  row.appendChild(col);
			 listElement.firstChild.insertBefore(row, listElement.firstChild.firstChild);
			}
			
		  }
		}
	}
	i++;
}
  

}

/* createToggleFunction - returns a function that toggles the sublist display
 * 
 * toggleElement  - the element representing the toggle gadget
 * sublistElement - an array of elements representing the sublists that should
 *                  be opened or closed when the toggle gadget is clicked
 */
function createToggleFunction(toggleElement,sublistElements){

  return function(){

    // toggle status of toggle gadget
    if (toggleElement.getAttribute('class')=='collapsibleClosed'){
      toggleElement.setAttribute('class','collapsibleOpen');
      toggleElement.setAttribute('src',OPEN_IMAGE);
    }else{
      toggleElement.setAttribute('class','collapsibleClosed');
      toggleElement.setAttribute('src',CLOSED_IMAGE);
    }

    // toggle display of sublists
    for (var i=0;i<sublistElements.length;i++){
      sublistElements[i].style.display=
          (sublistElements[i].style.display=='block')?'none':'block';
    }

  }

}

</script>

  </HEAD>
  <BODY topmargin=0 bgcolor="#ffffff" onLoad="makeCollapsible(document.getElementsByTagName('table'), 'collapsible');">
    <% String urlPrefix = "";
     int dummyInstructionDisplayCount = 3;

%>
    <%@ include file="../common/in_process_common_header_inc.jsp"%>
    <jsp:include page="../common/tab_inc.jsp" flush="true">
      <jsp:param name="label" value="View&nbsp;Form"/>
      <jsp:param name="urlPrefix" value=""/>
    </jsp:include>

<table>
    <tr>    
      <td align="left" class="AbbreviatedText">
        <bean:message key="cadsr.formbuilder.helpText.form.view"/>
      </td>
    </tr>  
</table> 
    <%@ include file="viewButton_inc.jsp"%>
    <%@ include file="showMessages.jsp" %>
    <logic:present name="<%=FormConstants.CRF%>">
      <table width="80%" align="center" cellpadding="1" cellspacing="1" border="0" class="OraBGAccentVeryDark">
        <tr class="OraTabledata">
          <td class="TableRowPromptTextLeft" width="20%">
            <bean:message key="cadsr.formbuilder.form.longName" />
          </td>                
          <td width="80%" class="OraFieldText">
            <bean:write name="<%=FormConstants.CRF%>" property="longName"/>
          </td>
        </tr>
        <tr class="OraTabledata">
          <td  class="TableRowPromptTextLeft" width="20%">
            <bean:message key="cadsr.formbuilder.form.definition"/>
          </td>                
          <td width="80%" class="OraFieldText">
            <bean:write name="<%=FormConstants.CRF%>" property="preferredDefinition"/>
          </td>
        </tr>
        <tr class="OraTabledata">
          <td  class="TableRowPromptTextLeft" width="20%">
            <bean:message key="cadsr.formbuilder.form.context"/>
          </td>                
          <td  class="OraFieldText">
            <bean:write name="<%=FormConstants.CRF%>" property="context.name"/>
          </td>
        </tr>
        <tr class="OraTabledata">
          <td class="TableRowPromptTextLeft"  width="20%">
            <bean:message key="cadsr.formbuilder.form.protocols.longName"/>
          </td>                
          <td  class="OraFieldText">
           <bean:define name="<%=FormConstants.CRF%>" property="protocols" id="protocols"/>
            <%=FormJspUtil.getDelimitedProtocolLongNames((List)protocols,  "<br/>")%>                
          </td>
        </tr>   
        <tr class="OraTabledata">
          <td class="TableRowPromptTextLeft"  width="20%">
            <bean:message key="cadsr.formbuilder.form.workflow"/>
          </td>                
          <td  class="OraFieldText">
            <bean:write name="<%=FormConstants.CRF%>" property="aslName"/>
          </td>
        </tr>  
        <tr class="OraTabledata">
          <td class="TableRowPromptTextLeft"  width="20%">
            <bean:message key="cadsr.formbuilder.form.category"/>
          </td>                
          <td  class="OraFieldText">
            <bean:write name="<%=FormConstants.CRF%>" property="formCategory"/>
          </td>
        </tr>  
        <tr class="OraTabledata">
          <td class="TableRowPromptTextLeft"  width="20%">
            <bean:message key="cadsr.formbuilder.form.type"/>
          </td>                
          <td  class="OraFieldText">
            <bean:write name="<%=FormConstants.CRF%>" property="formType"/>
          </td>
        </tr> 
        <tr class="OraTabledata">
          <td class="TableRowPromptTextLeft"  width="20%">
            <bean:message key="cadsr.formbuilder.form.publicID"/>
          </td>                
          <td  class="OraFieldText">
            <bean:write  name="<%=FormConstants.CRF%>" property="publicId"/> 
          </td>
        </tr>           
        <tr class="OraTabledata">
          <td class="TableRowPromptTextLeft"  width="20%">
            <bean:message key="cadsr.formbuilder.question.version"/>
          </td>                
          <td  class="OraFieldText">
            <bean:write  name="<%=FormConstants.CRF%>" property="version"/> 
          </td>
        </tr>    
        <logic:present name="<%=FormConstants.CRF%>" property="instruction">
          <tr class="OraTabledata">
            <td class="TableRowPromptTextLeft"  width="20%">
              <bean:message key="cadsr.formbuilder.form.header.instruction"/>
            </td>                
            <td  class="OraFieldTextInstruction">
              <bean:write  name="<%=FormConstants.CRF%>" property="instruction.preferredDefinition"/>
            </td>
          </tr>
        </logic:present>
        <logic:present name="<%=FormConstants.CRF%>" property="footerInstruction">     
          <tr class="OraTabledata">
            <td class="TableRowPromptTextLeft"  width="20%">
              <bean:message key="cadsr.formbuilder.form.footer.instruction"/>
            </td>                
            <td  class="OraFieldTextInstruction">
              <bean:write  name="<%=FormConstants.CRF%>" property="footerInstruction.preferredDefinition"/>
            </td>
          </tr> 
        </logic:present>          
      </table>
      <table width="80%" align="center" cellpadding="0" cellspacing="0" border="0" >
        <tr >
          <td >
             &nbsp;
          </td>
        </tr>
      </table> 
      <table cellpadding="0" cellspacing="0" width="80%" align="center">
        <tr >
          <td colspan=2>
            &nbsp;
          </td>
        </tr>         
        <tr>
          <td class="OraHeaderSubSub" width="100%">Form Details</td>
          <td align="right">
          <bean:define id="formObj" name="<%=FormConstants.CRF%>" />
          <% Form aForm = (Form)formObj;
            if(FormJspUtil.hasModuleRepetition(aForm)){ %>
             <logic:present name="<%=FormConstants.SHOW_MODULE_REPEATS%>">
               <html:link action='<%="/displayViewFormModuleRepeationAction.do?"+NavigationConstants.METHOD_PARAM+"="+NavigationConstants.HIDE_REPETITIONS%>'
                 >
               <html:img src='i/hideModuleRepetitions.gif' border="0" alt="Hide Module Repetitions"/>
              </html:link>                
             </logic:present>
             <logic:notPresent name="<%=FormConstants.SHOW_MODULE_REPEATS%>">
               <html:link action='<%="/displayViewFormModuleRepeationAction.do?"+NavigationConstants.METHOD_PARAM+"="+NavigationConstants.SHOW_REPETITIONS%>'
                 >
               <html:img src='i/showModuleRepetitions.gif' border="0" alt="Show Module Repetitions"/>
              </html:link>  
             </logic:notPresent>  
         <% }else{%>
            &nbsp;
         <%}%>  
          </td>          
        </tr>
        <tr>
          <td colspan=2><img height=1 src="i/beigedot.gif" width="99%" align=top border=0> </td>
        </tr>
      </table>       
      
     <%@ include file="moduleDetails_inc.jsp"%>
    
    </logic:present>
    <logic:notPresent name="<%=FormConstants.CRF%>">Selected form has been deleted by a diffrent user </logic:notPresent>
    <%@ include file="viewButton_inc.jsp"%>
    <%@ include file="../common/common_bottom_border.jsp"%>
  </BODY>
</HTML>
