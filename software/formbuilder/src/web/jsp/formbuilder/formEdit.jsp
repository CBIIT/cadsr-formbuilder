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
<%@ page import="gov.nih.nci.ncicb.cadsr.common.resource.*"%>
<%@ page import="java.util.*"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.formbuilder.struts.common.*"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.util.CDEBrowserParams"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.jsp.util.CDEDetailsUtils"%>


<%@page import="java.util.regex.Pattern"%>
<%! 
	boolean insertHiddenFld = false;
%>
<HTML>
  <HEAD>
    <TITLE>Formbuilder: Edit Form </TITLE>
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache"/>
    <LINK rel="stylesheet" TYPE="text/css" HREF="<html:rewrite page='/css/blaf.css' />">
	<script type="text/javascript" src='<html:rewrite page="/js/collapsible.js"/>' ></script>
<LINK REL=STYLESHEET TYPE="text/css" HREF="<%=request.getContextPath()%>/css/ui-lightness/jquery-ui-1.10.3.custom.min.css">
<script src="./js/jquery-1.9.1.js"></SCRIPT>
<script src="./js/jquery-ui-1.10.3.custom.min.js"></SCRIPT>
<script src="./js/jquery.cookie.js"></SCRIPT>
<script src="./js/jquery.marquee.js"></script>
<script src="./js/formbuilderJQ.js"></script>

<script type="text/javascript">

$(document).ready(function()
{
	setupUser();	
});

</script>	

	
    <SCRIPT LANGUAGE="JavaScript">
<!--

function submitForm() {
     document.forms[0].submit();
}
function submitChanges2(methodName,forward, displayOrder) {
  if(validateFormEditForm(formEditForm)) {
  document.forms[0].<%=NavigationConstants.METHOD_PARAM%>.value=methodName;
  document.forms[0].<%=FormConstants.DISPLAY_ORDER%>.value=displayOrder;
  document.forms[0].<%=FormConstants.FORM_FORWARD%>.value=forward;
  document.forms[0].submit();
  }
}

function submitChanges(methodName,forward) {
  if(validateFormEditForm(formEditForm)) {
  document.forms[0].<%=NavigationConstants.METHOD_PARAM%>.value=methodName;
  document.forms[0].<%=FormConstants.FORM_FORWARD%>.value=forward;
  document.forms[0].submit();
  }
}

function submitFormEdit(methodName,moduleIndexValue) {
  if(validateFormEditForm(formEditForm)) {
  document.forms[0].<%=NavigationConstants.METHOD_PARAM%>.value=methodName;
  document.forms[0].<%=FormConstants.MODULE_INDEX%>.value=moduleIndexValue;
  document.forms[0].submit();
  }
}
function submitFormToSave(methodName) {
  if(validateFormEditForm(formEditForm)) {
     document.forms[0].<%=NavigationConstants.METHOD_PARAM%>.value=methodName;
     document.forms[0].submit();
  }
}

function manageProtocols() {
  document.forms[0].action= '<%=request.getContextPath()+"/gotoManageProtocolsFormEdit.do?" + NavigationConstants.METHOD_PARAM + "=" + NavigationConstants.GOTO_MANAGE_PROTOCOLS_FORM_EDIT%>'
  document.forms[0].submit();
}

function showRepetition(method) {
  document.forms[0].action= '<%=request.getContextPath()+"/displayModuleRepeationAction.do?" + NavigationConstants.METHOD_PARAM + "=" + NavigationConstants.SHOW_REPETITIONS%>'
  document.forms[0].submit();
}
function hideRepetition(method) {
  document.forms[0].action= '<%=request.getContextPath()+"/displayModuleRepeationAction.do?" + NavigationConstants.METHOD_PARAM + "=" + NavigationConstants.HIDE_REPETITIONS%>'
  document.forms[0].submit();
}

function submitModuleRepition(methodName,moduleIndexValue) {
  if(validateFormEditForm(formEditForm)) {
  document.forms[0].<%=NavigationConstants.METHOD_PARAM%>.value=methodName;
  document.forms[0].<%=FormConstants.MODULE_INDEX%>.value=moduleIndexValue;
  document.forms[0].<%=FormConstants.FORM_FORWARD%>.value="<%=NavigationConstants.SUCCESS%>";
  document.forms[0].action='<%=request.getContextPath()%>/saveFormModuleRepeatAction.do'; 
  document.forms[0].submit();
  }
}

function repeatDisplay(methodName) {
  document.forms[0].<%=NavigationConstants.METHOD_PARAM%>.value=methodName;
  document.forms[0].action='<%=request.getContextPath()%>/displayModuleRepeationAction.do'; 
    document.forms[0].submit();
}
      
-->
<% 

  CDEBrowserParams params = CDEBrowserParams.getInstance();


 String urlPrefix = "";
    String startIndex="0";
    pageContext.setAttribute("startIndex", startIndex); 
  String contextPath = request.getContextPath();
  urlPrefix = contextPath+"/";
  
  String pageUrl = "&PageId=DataElementsGroup";
  // HSK
  // To jum to the correct location on the screen
  String jumpto = (String)request.getAttribute(CaDSRConstants.ANCHOR);
  String jumptoStr ="";
  
  if(jumpto!=null)
    jumptoStr = "onload=\"location.hash='#"+jumpto+"'\"";  
    
  String protoLOVUrl= 
    "javascript:newWin('"+contextPath+"/formLOVAction.do?method=getProtocolsLOV&idVar=protocolIdSeq&chkContext=true&nameVar=protocolLongName"+pageUrl+"','protoLOV',1200,900)";

  gov.nih.nci.ncicb.cadsr.common.struts.formbeans.GenericDynaFormBean formBean = (gov.nih.nci.ncicb.cadsr.common.struts.formbeans.GenericDynaFormBean)session.getAttribute("formEditForm");
  String longName = (String)formBean.get(FormConstants.FORM_LONG_NAME);
  String def = (String)formBean.get(FormConstants.PREFERRED_DEFINITION);
  String header = (String)formBean.get(FormConstants.FORM_HEADER_INSTRUCTION);
  String footer = (String)formBean.get(FormConstants.FORM_FOOTER_INSTRUCTION);
  Pattern validTextPattern = Pattern.compile("[a-zA-Z0-9*-_ ]*");
  
  if (longName != null) {
	  insertHiddenFld = !validTextPattern.matcher(longName).matches();  
  }
  if (def != null && !insertHiddenFld) {
	  insertHiddenFld = !validTextPattern.matcher(def).matches();  
  }
  if (header != null && !insertHiddenFld) {
	  insertHiddenFld = !validTextPattern.matcher(header).matches();  
  }
  if (footer != null && !insertHiddenFld) {
	  insertHiddenFld = !validTextPattern.matcher(footer).matches();  
  }
%>
</SCRIPT>
  </HEAD>
  <BODY topmargin=0 bgcolor="#ffffff" <%=jumptoStr%> onLoad="makeCollapsible(document.getElementsByTagName('table'), 'collapsible');getToggleDisplay(document.getElementById('toggle_display'));">


      
    <html:form action="/formSaveAction.do">
		<%
			if (insertHiddenFld) {
		%>
		<input type="hidden" name="xyz" value="one of the fields has an invalid character" />
		<% 
			}
		%>
     <html:hidden value="" property="<%=NavigationConstants.METHOD_PARAM%>"/>
     <html:hidden value="" property="<%=FormConstants.MODULE_INDEX%>"/>
     <html:hidden value="moduleEdit" property="forward"/>
     <html:hidden value="" property="<%=FormConstants.DISPLAY_ORDER%>"/>
      <%@ include file="../common/in_process_common_header_inc.jsp"%>
      <jsp:include page="../common/tab_inc.jsp" flush="true">
        <jsp:param name="label" value="Edit&nbsp;Form"/>
        <jsp:param name="urlPrefix" value=""/>
      </jsp:include>
    <table>
        <tr>    
          <td align="left" class="AbbreviatedText">
            <bean:message key="cadsr.formbuilder.helpText.form.edit"/>
          </td>
        </tr>  
        <tr>    
          <td align="left" class="AbbreviatedText">
            <bean:message key="cadsr.formbuilder.form.will.be.locked.by.you"/>
          </td>
        </tr>  
    </table> 

      <%@ include file="editButton_inc.jsp"%>
    <%@ include file="showMessages.jsp" %>

<!--GF32932 D.An, 20130828  -->
<input type="hidden" id="myInputCurrentPage" name="myInputCurrentPage" value="Edit" />
<input type="hidden" id="myInputFirstTime" name="myInputFirstTime" value="Y" />
<script type="text/javascript">
var currentPageIs = $.cookie( 'currentPageIs' );
if( currentPageIs == $("#myInputCurrentPage").val() )
	$("#myInputFirstTime").val("n");
$.cookie( 'currentPageIs', $("#myInputCurrentPage").val() );
</script> 
       <table width="80%" height="25" align="center" cellpadding="1" cellspacing="1" border="0">
        <tr>
          <td>
<%@ include file="additionalMessages.jsp" %>
          </td>                
        </tr>
	</table>

      <logic:present name="<%=FormConstants.CRF%>">

       <html:hidden property="<%=FormConstants.FORM_ID_SEQ%>"/>

      <table cellpadding="0" cellspacing="0" width="80%" align="center">
        <tr >
          <td >
            &nbsp;
          </td>
        </tr>         
        <tr>
          <td class="OraHeaderSubSub" width="100%">Form Header</td>
        </tr>
        <tr>
          <td><img height=1 src="<%=urlPrefix%>i/beigedot.gif" width="99%" align=top border=0> </td>
        </tr>
      </table>       
       
        <table width="80%" align="center" cellpadding="1" cellspacing="1" border="0" class="OraBGAccentVeryDark">
          <tr class="OraTabledata">
            <td class="OraTableColumnHeader" width="20%" nowrap>
              <bean:message key="cadsr.formbuilder.form.longName"/>
            </td>
            <td  class="OraFieldText" width="80%" nowrap>
              <html:text size="100" property="<%=FormConstants.FORM_LONG_NAME%>"
                 maxlength="<%= Integer.toString(FormConstants.LONG_NAME_MAX_LENGTH)%>">
             </html:text>
            </td>
          </tr>
          <tr class="OraTabledata">
            <td class="OraTableColumnHeader" width="20%">
              <bean:message key="cadsr.formbuilder.form.definition"/>
            </td>
            <td  class="OraFieldText" width="80%" >
              <html:textarea  styleClass="OraFieldText" rows="3" cols="102" property="<%=FormConstants.PREFERRED_DEFINITION%>"></html:textarea>
            </td>
          </tr>
          <tr class="OraTabledata">
            <td class="OraTableColumnHeader" width="20%" nowrap>
              <bean:message key="cadsr.formbuilder.form.context"/>
            </td>
            <td class="OraFieldText" nowrap>
              <html:select styleClass="Dropdown" property="<%=FormConstants.CONTEXT_ID_SEQ%>" >               
                <html:options collection="<%=CaDSRConstants.USER_CONTEXTS%>" property="conteIdseq" labelProperty="name"/>
              </html:select>
            </td>
          </tr>
          
          <tr class="OraTabledata">
            <td class="OraTableColumnHeader" width="20%" nowrap>
              <bean:message key="cadsr.formbuilder.form.protocols.longName"/>
            </td>
            <td class="OraFieldText">
           <bean:define name="<%=FormConstants.CRF%>" property="protocols" id="protocols"/>
            <%=FormJspUtil.getDelimitedProtocolLongNames((List)protocols,  "<br/><br/>")%>                
            <br/>
            <a href="javascript:manageProtocols()"><i>Manage Protocols</i></a> 
            <html:hidden property="<%=FormConstants.PROTOCOL_ID_SEQ%>"/>
            </td>
          </tr>

          <tr class="OraTabledata">
            <td class="OraTableColumnHeader" width="20%" nowrap>
              <bean:message key="cadsr.formbuilder.form.workflow" />
            </td>
            <td class="OraFieldText" nowrap>
            <html:select styleClass = "FreeDropdown" property="<%=FormConstants.WORKFLOW%>">        	
               <html:options name="<%=FormConstants.ALL_WORKFLOWS%>"/>
              </html:select>        
            </td>   
          </tr>
          <tr class="OraTabledata">
            <td class="OraTableColumnHeader" width="20%" nowrap>
              <bean:message key="cadsr.formbuilder.form.category" />
            </td>
            <td class="OraFieldText" nowrap>
              <html:select styleClass = "Dropdown" property="<%=FormConstants.CATEGORY_NAME%>">              
                <html:option value=""/>
                <html:options name="<%=FormConstants.ALL_FORM_CATEGORIES%>" /> 
              </html:select> 
            </td>
          </tr>         
          <tr class="OraTabledata" >
              <td class="OraTableColumnHeader" nowrap><bean:message key="cadsr.formbuilder.form.type" /></td>  
              <td class="OraFieldText" nowrap>
               <html:select styleClass = "Dropdown" property="<%=FormConstants.FORM_TYPE%>"> 
                  <html:options name="<%=FormConstants.ALL_FORM_TYPES%>" /> 
               </html:select> 
              </td>        
          </tr> 
          <tr class="OraTabledata" >
              <td class="OraTableColumnHeader" nowrap><bean:message key="cadsr.formbuilder.form.publicID" /></td>  
              <td class="OraFieldText" nowrap>
                <bean:write  name="<%=FormConstants.CRF%>" property="publicId"/> 
              </td>        
          </tr>         
          <tr class="OraTabledata" >
              <td class="OraTableColumnHeader" nowrap><bean:message key="cadsr.formbuilder.question.version" /></td>  
              <td class="OraFieldText" nowrap>
                <bean:write  name="<%=FormConstants.CRF%>" property="version"/> 
              </td>        
          </tr> 
                  
            <tr class="OraTabledata">
              <td class="OraTableColumnHeader" width="20%" nowrap>
                <bean:message key="cadsr.formbuilder.form.header.instruction"/>
              </td>        
              <td  class="OraFieldText" width="80%" >
                <html:textarea  styleClass="OraFieldTextInstruction" rows="2" cols="102" 
                   property="<%=FormConstants.FORM_HEADER_INSTRUCTION%>">
                </html:textarea>
              </td>            
            </tr>   
            
            <tr class="OraTabledata">
              <td class="OraTableColumnHeader" width="20%" nowrap>
                <bean:message key="cadsr.formbuilder.form.footer.instruction"/>
              </td>
              <td  class="OraFieldText" width="80%" >
                <html:textarea  styleClass="OraFieldTextInstruction" rows="2" cols="102" 
                   property="<%=FormConstants.FORM_FOOTER_INSTRUCTION%>">
                </html:textarea>
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
			<td align="right"><a href="javascript:getToggleDisplay(document.getElementById('toggle_display'));" id="toggle_display"></a></td>
          <td align="right">
          <bean:define id="formObj" name="<%=FormConstants.CRF%>" />
          <% Form aForm = (Form)formObj;
            if(FormJspUtil.hasModuleRepetition(aForm)){ %>
             <logic:present name="<%=FormConstants.SHOW_MODULE_REPEATS%>">
                <a href="javascript:hideRepetition()">
                   <img src="<%=urlPrefix%>i/hideModuleRepetitions.gif" border="0" alt="Hide Module Repetitions"/>
                </a>                          
             </logic:present>
             <logic:notPresent name="<%=FormConstants.SHOW_MODULE_REPEATS%>">
                <a href="javascript:showRepetition()">
                   <img src="<%=urlPrefix%>i/showModuleRepetitions.gif" border="0" alt="Show Module Repetitions"/>
                </a>                          
              </logic:notPresent>  
         <% }else{%>
            &nbsp;
         <%}%>           
          </td>          
        </tr>
        <tr>
          <td colspan=3><img height=1 src="<%=urlPrefix%>i/beigedot.gif" width="99%" align=top border=0> </td>
        </tr>
      </table>        
        <!-- If the Modules Collection is empty and deleted modules Exists -->
            <logic:empty name="<%=FormConstants.CRF%>" property="modules">

             <table width="79%" align="center" cellpadding="0" cellspacing="0" border="0">        
              <tr align="right">                
                <logic:notEmpty name="<%=FormConstants.DELETED_MODULES%>">
                  <td align="right"   class="OraFieldText" nowrap>    
                      <html:select styleClass="Dropdown" property="<%=FormConstants.ADD_DELETED_MODULE_IDSEQ%>">
                        <html:options collection="<%=FormConstants.DELETED_MODULES%>" 
                            property="moduleIdseq" labelProperty="longName" />
                      </html:select >
                  </td>
                  <td align="left" width="25">
                      <a href="javascript:submitFormEdit('<%=NavigationConstants.ADD_FROM_DELETED_LIST%>','0')">
                         <img src=<%=urlPrefix%>i/add.gif border=0 alt="Add">
                      </a>                          
                  </td>   
                  </logic:notEmpty>
                  <logic:empty name="<%=FormConstants.DELETED_MODULES%>">
                  <td width="0">
                    &nbsp;
                  </td>  
                </logic:empty>                        
                <td align="right" width="205"> 
                      <a href="javascript:submitChanges2('<%=NavigationConstants.CHECK_CHANGES_MODULE_EDIT%>', 'gotoModuleList', '<%=startIndex%>')">
                         Copy Module from module cart
                      </a>                          
                </td>   
                <td align="right" width="160">
                      <a href="javascript:submitChanges2('<%=NavigationConstants.CHECK_CHANGES_MODULE_EDIT%>', 'gotoModuleSearch', '<%=startIndex%>')">
                         Copy Module from a Form
                      </a>                          
                </td>  
                <td align="right" width="80">
                      <a href="javascript:submitChanges2('<%=NavigationConstants.CHECK_CHANGES_MODULE_EDIT%>', 'gotoModuleCreate', '<%=startIndex%>')">
                         Create New
                      </a>                          
                </td>                                        
              </tr>               
              </table> 
              </logic:empty>
            <!-- Add for delete and new Module end -->            
        
        
        <!-- If the Modules Collection is empty and deleted modules Exists end -->

        <logic:notEmpty name="<%=FormConstants.CRF%>" property="modules">

          <logic:iterate id="module" indexId="moduleIndex" name="<%=FormConstants.CRF%>" type="gov.nih.nci.ncicb.cadsr.common.resource.Module" property="modules">

            <bean:size id="moduleSize" name="<%=FormConstants.CRF%>" property="modules"/>  

            <!-- and anchor -->
            <A NAME="<%="M"+moduleIndex%>"></A>
            <!-- Add for delete and new Module -->
             <table width="79%" align="center" cellpadding="0" cellspacing="0" border="0">        
              <tr align="right">
              
                <logic:notEmpty name="<%=FormConstants.DELETED_MODULES%>">
                  <td align="right"   class="OraFieldText" nowrap>    
                      <html:select styleClass="Dropdown" property="<%=FormConstants.ADD_DELETED_MODULE_IDSEQ%>">
                        <html:options collection="<%=FormConstants.DELETED_MODULES%>" 
                            property="moduleIdseq" labelProperty="longName" />
                      </html:select >
                  </td>
                  <td align="left" width="25">
                      <a href="javascript:submitFormEdit('<%=NavigationConstants.ADD_FROM_DELETED_LIST%>','<%=moduleIndex%>')">
                         <img src=<%=urlPrefix%>i/add.gif border=0 alt="Add">
                      </a>                          
                  </td>   
                  </logic:notEmpty>
                  <logic:empty name="<%=FormConstants.DELETED_MODULES%>">
                  <td >
                    &nbsp;
                  </td>  
                </logic:empty> 
                
                <td align="right" width="205"> 
                      <a href="javascript:submitChanges2('<%=NavigationConstants.CHECK_CHANGES_MODULE_EDIT%>', 'gotoModuleList', '<%=moduleIndex%>')">
                         Copy Module from module cart
                      </a>                          
                </td>   
                <td align="right" width="160">
                      <a href="javascript:submitChanges2('<%=NavigationConstants.CHECK_CHANGES_MODULE_EDIT%>', 'gotoModuleSearch', '<%=moduleIndex%>')">
                         Copy Module from a Form
                      </a>                          
                </td>  
                <td align="right" width="80">
                      <a href="javascript:submitChanges2('<%=NavigationConstants.CHECK_CHANGES_MODULE_EDIT%>', 'gotoModuleCreate', '<%=moduleIndex%>')">
                         Create New
                      </a>                          
                </td>                                        
              </tr>
             
              </table> 
            <!-- Add for delete and new Module end -->             

            <table width="80%" align="center" cellpadding="0" cellspacing="1" border="0" class="OraBGAccentVeryDark">
              <tr class="OraTableColumnHeader">
                <td class="OraTableColumnHeader">
                  <table width="100%" align="right" cellpadding="0" cellspacing="0" border="0" class="OraBGAccentVeryDark">
                    <tr class="OraHeaderBlack">
                      <td width="65%">
                        <bean:write name="module" property="longName"/>
                      </td>
                      <td align="right">
                        <table width="100%" align="right" cellpadding="0" cellspacing="0" border="0" class="OraBGAccentVeryDark">
                            <tr class="OraTableColumnHeader">
                              <td align="center">
                                  <a href="javascript:submitModuleRepition('<%=NavigationConstants.CHECK_CHANGES_MODULE_EDIT%>','<%=moduleIndex%>')">
                                     Manage Repetition
                                  </a>                                    
                                </td>                              
                             <td align="center">
                                <logic:notEqual value="<%= String.valueOf(moduleSize.intValue()-1) %>" name="moduleIndex">
                                  <a href="javascript:submitFormEdit('<%=NavigationConstants.MOVE_MODULE_DOWN%>','<%=moduleIndex%>')">
                                     <img src=<%=urlPrefix%>i/down.gif border=0 alt="Down">
                                  </a>                                  
                                </logic:notEqual>
                             </td>
                             <td align="center">
                                <logic:notEqual value="<%= String.valueOf(0) %>" name="moduleIndex">
                                  <a href="javascript:submitFormEdit('<%=NavigationConstants.MOVE_MODULE_UP%>','<%=moduleIndex%>')">
                                     <img src=<%=urlPrefix%>i/up.gif border=0 alt="Up">
                                  </a>                           
                                </logic:notEqual> 
                              </td>
                              <td align="center">
                                  <a href="javascript:submitFormEdit('<%=NavigationConstants.CHECK_CHANGES_MODULE_EDIT%>','<%=moduleIndex%>')">
                                     <img src=<%=urlPrefix%>i/edit.gif border=0 alt="Edit">
                                  </a>                                    
                                </td>
                                <td align="center">
                                    <a href="javascript:submitFormEdit('<%=NavigationConstants.DELETE_MODULE%>','<%=moduleIndex%>')">
                                       <img src=<%=urlPrefix%>i/delete.gif border=0 alt="Delete">
                                    </a>
                                </td>
                              </tr>
                           </table>
                      </td>
                    </tr>

                   <logic:present name="module" property="instruction">                   
                      <tr>  
                       <td colspan="2">
                           <table width="100%" align="center" cellpadding="0" cellspacing="1" border="0" class="OraBGAccentVeryDark" >
                             <tr class="OraTabledata">
                              <td class="OraTableColumnHeader" width="10%" nowrap>
                                <bean:message key="cadsr.formbuilder.form.instruction"/> 
                             </td>
                             <td class="OraFieldTextInstruction">
                               <bean:write  name="module" property="instruction.preferredDefinition"/>
                             </td>
                            </tr>
                             <tr class="OraTabledata">
                              <td class="OraTableColumnHeader" width="10%" nowrap>
                                Number of Repetitions 
                             </td>
                             <td class="OraFieldText">
                               <bean:write name="module" property="numberOfRepeats"/>
                             </td>
                            </tr>                             
                           </table>
                       </td>
                      </tr>
                   </logic:present> 
                   <logic:notPresent name="module" property="instruction">                   
                      <tr>  
                       <td colspan="2">
                           <table width="100%" align="center" cellpadding="0" cellspacing="1" border="0" class="OraBGAccentVeryDark" >
                             <tr class="OraTabledata">
                              <td class="OraTableColumnHeader" width="10%" nowrap>
                                Number of Repetitions 
                             </td>
                             <td class="OraFieldTextInstruction">
                               <bean:write name="module" property="numberOfRepeats"/>
                             </td>
                            </tr>
                           </table>
                       </td>
                      </tr>
                   </logic:notPresent>                      
                 </table>
               </td>
              </tr>
              <logic:present name="module">

                <logic:notEmpty name="module" property="questions">

                  <tr class="OraTabledata">
                    <td>                
                        <table width="100%" align="center" cellpadding="0" cellspacing="0" border="0" class="OraTabledata">      
                          <logic:iterate id="question" name="module" type="gov.nih.nci.ncicb.cadsr.common.resource.Question" property="questions">                           
                            <tr class="OraTabledata">
                              <td class="OraFieldText" width="50">&nbsp;</td>
                              <td height="1"  class="OraFieldText">                               
                              </td>                              
                            </tr>                             
                            <tr class="OraTabledata">
                              <td class="OraFieldText" width="7%">&nbsp;</td>
                              <td class="UnderlineOraFieldText" >
                                <bean:write name="question" property="longName"/>
                              </td>
                              <td class="OraTabledata" width="15%" align="right" >
                               <table width="100%" align="right" cellpadding="0" cellspacing="0" border="0" class="OraTabledata">
                                 <tr>
                                   <logic:present name="question" property = "dataElement">
                                     <td align="right" width="70" class="UnderlineOraFieldText" >                        
                                            <html:link href='<%=params.getCdeBrowserUrl() + "/CDEBrowser/search?dataElementDetails=9&PageId=DataElementsGroup&queryDE=yes"%>'
                                               paramId = "p_de_idseq"
                                                paramName="question"
                                                paramProperty="dataElement.deIdseq"
                                                target="_blank">
                                            <bean:write name="question" property="dataElement.CDEId"/>
                                            </html:link>
                                     </td>
                                    <td align="right" width="70" class="UnderlineOraFieldText">
                                       <bean:write name="question" property="dataElement.version"/>
                                    </td>                                  
                                   </logic:present>
                                   <logic:notPresent name="question" property="dataElement">
                                     <td align="center" width="70" class="UnderlineOraFieldText">
                                       &nbsp;
                                     </td>
                                     <td align="center" width="70" class="UnderlineOraFieldText">
                                       &nbsp;
                                      </td>                              
                                   </logic:notPresent>  
                                 </tr>  
                               </table>
                              </td> 
                            </tr>
                            <logic:present name="question" property="instruction">
                              <tr class="OraTabledata">
                                 <td class="OraFieldText" width="50">&nbsp;</td>
                                  <td class="OraFieldText" colspan="2">                              
                                   <table align="center" width="100%" cellpadding="0" cellspacing="1" border="0" class="OraBGAccentVeryDark" >
                                     <tr class="OraTabledata">
                                      <td class="OraTableColumnHeader" width="10%" nowrap>
                                        <bean:message key="cadsr.formbuilder.form.instruction"/>
                                     </td>
                                     <td class="OraFieldTextInstruction">
                                       <bean:write  name="question" property="instruction.preferredDefinition"/>
                                     </td>
                                    </tr>
                                   </table>                                                            
                                 </td>
                               </tr> 
                            </logic:present>
                            <%--mandatory--%>                            
                              <tr class="OraTabledata">
                                 <td class="OraFieldText" width="50">&nbsp;</td>
                                  <td class="OraFieldText" colspan="2">                              
                                   <table align="center" width="100%" cellpadding="0" cellspacing="1" border="0" class="OraBGAccentVeryDark" >
                                     <tr class="OraTabledata">
                                      <td class="OraTableColumnHeader" width="10%" nowrap>
	                                  <bean:message key="cadsr.formbuilder.form.question.mandatory"/> 
                                     </td>
                                     <td class="OraFieldText">
						<html:checkbox name="question" property="mandatory" disabled="true"/>
					</td>
                                    </tr>
									<tr class="OraTabledata">
                                      <td class="OraTableColumnHeader" width="10%" nowrap>
	                                  <bean:message key="cadsr.formbuilder.form.question.editable"/> 
                                     </td>
                                     <td class="OraFieldText">
						<html:checkbox name="question" property="editable" disabled="true"/>
						<logic:equal name="question" property="deDerived" value="true">
							&nbsp;&nbsp;<font color="gray" size="2"><i>Cannot be changed because Data Element is derived</i></font>
						</logic:equal>
					</td>
                                    </tr>
                                   </table>                                                            
                                 </td>
                               </tr> 
                            <logic:notEmpty name="question" property="defaultValidValue">
                              <logic:notEmpty name="question" property="defaultValidValue.longName">
                              <tr class="OraTabledata">
                                 <td class="OraFieldText" width="50">&nbsp;</td>
                                  <td class="OraFieldText" colspan="2">                              
                                   <table align="center" width="100%" cellpadding="0" cellspacing="1" border="0" class="OraBGAccentVeryDark" >
                                     <tr class="OraTabledata">
                                      <td class="OraTableColumnHeader" width="10%" nowrap>
                                        <bean:message key="cadsr.formbuilder.form.questionDefaultValue"/>
                                     </td>
                                     <td class="OraFieldTextInstruction">
                                       <bean:write  name="question" property="defaultValidValue.longName"/>
                                     </td>
                                    </tr>
                                   </table>                                                            
                                 </td>
                               </tr> 
                              </logic:notEmpty>
                            </logic:notEmpty>

                            <logic:notEmpty name="question" property="defaultValue">
                              <tr class="OraTabledata">
                                 <td class="OraFieldText" width="50">&nbsp;</td>
                                  <td class="OraFieldText" colspan="2">                              
                                   <table align="center" width="100%" cellpadding="0" cellspacing="1" border="0" class="OraBGAccentVeryDark" >
                                     <tr class="OraTabledata">
                                      <td class="OraTableColumnHeader" width="10%" nowrap>
                                        <bean:message key="cadsr.formbuilder.form.questionDefaultValue"/>
                                     </td>
                                     <td class="OraFieldTextInstruction">
                                       <bean:write  name="question" property="defaultValue"/>
                                     </td>
                                    </tr>
                                   </table>                                                            
                                 </td>
                               </tr> 
                            </logic:notEmpty>
                            
                           <logic:present name="question" property="dataElement">
                            <logic:present name="question" property="dataElement.valueDomain">
                              <tr class="OraTabledata">
                                 <td class="OraFieldText" width="50">&nbsp;</td>
                                  <td class="OraFieldText" colspan="2">                              
                                   <table align="center" width="100%" cellpadding="0" cellspacing="1" border="0" 
class="OraBGAccentVeryDark" >
                                     <tr class="OraTabledata">
                                      <td class="OraTableColumnHeader"  nowrap colspan="2">
                                        <bean:message key="cadsr.formbuilder.form.valueDomain.valueDomainDetails"/>        
                                     </td>                                     
                                    </tr>
                                    
                                    <tr class="OraTabledata">
                                     <td class="OraTableColumnHeader" width="20%">
                                        <bean:message key="cadsr.formbuilder.form.valueDomain.longName"/>
                                     </td>
                                     <td class="OraFieldText">
                                       <bean:write  name="question" property="dataElement.valueDomain.longName"/>          
                            
                                     </td>
                                    </tr>
                                    
                                    <tr class="OraTabledata">
                                     <td class="OraTableColumnHeader">
                                        <bean:message key="cadsr.formbuilder.form.valueDomain.datatype"/>
                                     </td>
                                     <td class="OraFieldText">
                                       <bean:write  name="question" property="dataElement.valueDomain.datatype"/>          
                            
                                     </td>
                                    </tr>

                                    <tr class="OraTabledata">
                                     <td class="OraTableColumnHeader">
                                        <bean:message key="cadsr.formbuilder.form.valueDomain.unitofmeasure"/>
                                     </td>
                                     <td class="OraFieldText">
                                       <bean:write  name="question" property="dataElement.valueDomain.unitOfMeasure"/>     
                                     </td>
                                    </tr>

                                    <tr class="OraTabledata">
                                     <td class="OraTableColumnHeader">
                                        <bean:message key="cadsr.formbuilder.form.valueDomain.displayFormat"/>
                                     </td>
                                     <td class="OraFieldText">
                                       <bean:write  name="question" property="dataElement.valueDomain.displayFormat"/>     
                                     </td>
                                    </tr>

                                    <tr class="OraTabledata">
                                     <td class="OraTableColumnHeader">
                                        <bean:message key="cadsr.formbuilder.form.valueDomain.concepts"/>
                                     </td>
                                     <td class="OraFieldText">
                                       
<%=CDEDetailsUtils.getConceptCodesUrl(question.getDataElement().getValueDomain().getConceptDerivationRule(),CDEBrowserParams.getInstance(),"link",",")%>
                                     </td>
                                    </tr>

                                   </table>                                                            
                                 </td>
                               </tr> 
                            </logic:present>
                           </logic:present>

                            <logic:present name="question">
                            <logic:notEmpty name="question" property = "validValues">
                              <tr class="OraTabledata">
                                <td class="OraFieldText" width="50">&nbsp;</td>
                                <td colspan="2">
                                  <table width="100%" align="center" cellpadding="0" cellspacing="0" border="0" class="OraBGAccentVeryDark" id="collapsible"><logic:iterate id="validValue" name="question" type="gov.nih.nci.ncicb.cadsr.common.resource.FormValidValue" property="validValues"><tr   class="OraTabledata">
                                        <td COLSPAN="2" class="OraFieldText" >&nbsp;</td>
                                      </tr>
                                      <tr   class="OraTabledata">
                                        <td class="OraFieldText" width="50">&nbsp;</td>
                                        <td class="OraFieldText">
                                          <bean:write name="validValue" property="longName"/>
                                        </td>
                                      </tr>                                       
                                      <tr   class="OraTabledata">
                                        <td class="OraFieldText" width="50">&nbsp;</td>
                                        <td >
                                          <table align="center" cellpadding="1" cellspacing="1" border="0" class="OraBGAccentVeryDark" >
                                             <tr class="OraTabledata">
                                             <td  class="OraTableColumnHeader" width="10%" nowrap >
                                                 <bean:message key="cadsr.formbuilder.valueMeaning.text" /></td>
                                             <td class="OraFieldText" >
                                                <bean:write name="validValue" property="formValueMeaningText"/></td>                                          
                                            </tr>
                                            <tr class="OraTabledata">
                                                 <td  class="OraTableColumnHeader" width="10%" nowrap >
                                                   <bean:message key="cadsr.formbuilder.valueMeaning.idversion" /></td>
                                                 <td class="OraFieldText" >
                                                  <bean:write name="validValue" property="formValueMeaningIdVersion"/></td>                                          
                                             </tr>  
                                             <tr class="OraTabledata">
                                             <td  class="OraTableColumnHeader" width="10%" nowrap >
                                                 <bean:message key="cadsr.formbuilder.valueMeaning.description" /></td>
                                             <td class="OraFieldText" >
                                                <bean:write name="validValue" property="formValueMeaningDesc"/></td>                                          
                                            </tr>
                                             <logic:present name="validValue" property="instruction">                
                                                 <tr class="OraTabledata">
                                                  <td class="OraTableColumnHeader" width="10%" nowrap>
                                                    <bean:message key="cadsr.formbuilder.form.instruction"/> 
                                                 </td>
                                                 <td class="OraFieldTextInstruction">
                                                   <bean:write  name="validValue" property="instruction.preferredDefinition"/>
                                                 </td>
                                                </tr>   
                                              </logic:present>                                                    
                                          </table>                                       
                                        </td>
                                      </tr>
                                      <!-- vv skip Pattern -->
                                      <logic:present name="validValue" property = "triggerActions" >
			              <logic:notEmpty name="validValue" property = "triggerActions">
                                      
                                      <tr   class="OraTabledata">
                                        <td class="OraFieldText" width="50">&nbsp;</td>
                                        <td >	
				          <table width="100%" align="center" cellpadding="0" cellspacing="1" border="0" class="OraBGAccentVeryDark">
					    <logic:iterate id="currTriggerAction" name="validValue" type="gov.nih.nci.ncicb.cadsr.common.resource.TriggerAction" property="triggerActions" indexId="triggerIndex" >
						<%@ include file="skipPatternDetailsView_inc.jsp"%>
					    </logic:iterate>
					  </table>

                                         </td>
                                        </tr>
				       </logic:notEmpty>
				       </logic:present>                                         
                                       <!-- vv Skip pattern end -->                                        
                                    </logic:iterate><!-- valid Value-->
                                  </table>
                                </td>
                              </tr>
                            </logic:notEmpty>
                            <logic:empty name="question" property = "validValues">
                              <tr class="OraTabledata">
                                <td class="OraFieldText" width="50">&nbsp;</td>
                                <td>
                                  <table width="100%" align="center" cellpadding="0" cellspacing="0" border="0" class="OraBGAccentVeryDark">
                                      <tr  COLSPAN="3" class="OraTabledata">
                                        <td class="OraFieldText" width="50">&nbsp;</td>
                                        <td class="OraFieldText">
                                          &nbsp;
                                        </td>
                                      </tr>   
                                  </table>
                                </td>
                              </tr>                            
                            </logic:empty>
                            </logic:present>
                          </logic:iterate><!-- Question-->
                        </table>                      
                                           
                    </td>
                  </tr>
                </logic:notEmpty>
                <!-- Module skip Pattern -->
                 <logic:present name="module" property = "triggerActions" >
                   <logic:notEmpty name="module" property = "triggerActions">
                            <table width="80%" align="center" cellpadding="0" cellspacing="1" border="0" class="OraBGAccentVeryDark">
                              <logic:iterate id="currTriggerAction" name="module" type="gov.nih.nci.ncicb.cadsr.common.resource.TriggerAction" property="triggerActions" indexId="triggerIndex" >
                             	<%@ include file="skipPatternDetailsView_inc.jsp"%>
                              </logic:iterate>
                            </table>
                    </logic:notEmpty>
                 </logic:present>
                 <!-- Module Skip pattern end --> 
              </logic:present>
            </table>   
           
            <logic:notPresent name="<%=FormConstants.SHOW_MODULE_REPEATS%>">
                <logic:equal value="<%= String.valueOf(moduleSize.intValue()-1) %>" name="moduleIndex">
                <!-- Add for delete and new Module -->
                 <table width="79%" align="center" cellpadding="0" cellspacing="0" border="0">        
                  <tr align="right">
                    <logic:notEmpty name="<%=FormConstants.DELETED_MODULES%>">
    
                      <td align="right"   class="OraFieldText" nowrap>    
                          <html:select styleClass="Dropdown" property="<%=FormConstants.ADD_DELETED_MODULE_IDSEQ%>">
                            <html:options collection="<%=FormConstants.DELETED_MODULES%>" 
                                property="moduleIdseq" labelProperty="longName" />
                          </html:select >
                      </td>
                      <td align="left" width="25">
                          <a href="javascript:submitFormEdit('<%=NavigationConstants.ADD_FROM_DELETED_LIST%>','<%=moduleSize%>')">
                             <img src="<%=urlPrefix%>i/add.gif" border=0 alt="Add">
                          </a>                          
                      </td>   
                    </logic:notEmpty>
    
                    <logic:empty name="<%=FormConstants.DELETED_MODULES%>">
    
                    <td >
                      &nbsp;
                    </td>  
                    </logic:empty>  
                    
                    <td align="right" width="205"> 
                      <a href="javascript:submitChanges2('<%=NavigationConstants.CHECK_CHANGES_MODULE_EDIT%>', 'gotoModuleList', '<%=moduleSize%>')">
                         Copy Module from module cart
                      </a>                          
                    </td>   
	                <td align="right" width="160">
	                      <a href="javascript:submitChanges2('<%=NavigationConstants.CHECK_CHANGES_MODULE_EDIT%>', 'gotoModuleSearch', '<%=moduleSize%>')">
	                         Copy Module from a Form
	                      </a>                          
	                </td>  
	                <td align="right" width="80">
	                      <a href="javascript:submitChanges2('<%=NavigationConstants.CHECK_CHANGES_MODULE_EDIT%>', 'gotoModuleCreate', '<%=moduleSize%>')">
	                         Create New
	                      </a>                          
	                </td>                                        
                  </tr>
                  </table> 
                <!-- Add for delete and new Module end -->  
                </logic:equal>
            </logic:notPresent>
            <logic:present name="<%=FormConstants.SHOW_MODULE_REPEATS%>">
      		<table width="80%" align="center" cellpadding="0" cellspacing="0" border="0" >
        	   <tr>
          	      <td >
			             &nbsp;
          	      </td>
        	   </tr> 
        	</table>            
                     <%@ include file="repititionDetails_inc.jsp"%> 
                    <logic:equal value="<%= String.valueOf(moduleSize.intValue()-1) %>" name="moduleIndex">
                    <!-- Add for delete and new Module -->
                     <table width="79%" align="center" cellpadding="0" cellspacing="0" border="0">        
                      <tr align="right">
                        <logic:notEmpty name="<%=FormConstants.DELETED_MODULES%>">
        
                          <td align="right"   class="OraFieldText" nowrap>    
                              <html:select styleClass="Dropdown" property="<%=FormConstants.ADD_DELETED_MODULE_IDSEQ%>">
                                <html:options collection="<%=FormConstants.DELETED_MODULES%>" 
                                    property="moduleIdseq" labelProperty="longName" />
                              </html:select >
                          </td>
                          <td align="left" width="25">
                              <a href="javascript:submitFormEdit('<%=NavigationConstants.ADD_FROM_DELETED_LIST%>','<%=moduleSize%>')">
                                 <img src="<%=urlPrefix%>i/add.gif" border="0" alt="Add">
                              </a>                          
                          </td>   
                        </logic:notEmpty>
        
                        <logic:empty name="<%=FormConstants.DELETED_MODULES%>">      
                        <td >
                          &nbsp;
                        </td>  
                        </logic:empty>  
	                    <td align="right" width="205"> 
	                      <a href="javascript:submitChanges2('<%=NavigationConstants.CHECK_CHANGES_MODULE_EDIT%>', 'gotoModuleList', '<%=moduleSize%>')">
	                         Copy Module from module cart
	                      </a>                          
	                    </td>   
		                <td align="right" width="160">
		                      <a href="javascript:submitChanges2('<%=NavigationConstants.CHECK_CHANGES_MODULE_EDIT%>', 'gotoModuleSearch', '<%=moduleSize%>')">
		                         Copy Module from a Form
		                      </a>                          
		                </td>  
		                <td align="right" width="80">
		                      <a href="javascript:submitChanges2('<%=NavigationConstants.CHECK_CHANGES_MODULE_EDIT%>', 'gotoModuleCreate', '<%=moduleSize%>')">
		                         Create New
		                      </a>                          
		                </td>                                        
                      </tr>
                      </table> 
                    <!-- Add for delete and new Module end -->  
                    </logic:equal>
            </logic:present>
            
          </logic:iterate>

        </logic:notEmpty>   
 
           <br>
    <!-- skip pattern end -->        
      </logic:present>
     <%@ include file="editButton_inc.jsp"%>
    </html:form>

    <%@ include file="../common/common_bottom_border.jsp"%>

<html:javascript formName="formEditForm"/>
   
  </BODY>
</HTML>
