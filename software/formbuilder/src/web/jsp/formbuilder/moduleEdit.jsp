<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/cdebrowser.tld" prefix="cde"%>
<%--@ page import="oracle.clex.process.jsp.GetInfoBean "--%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.html.* "%>
<%@ page import="java.util.* "%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.util.* "%> 
<%@ page import="gov.nih.nci.ncicb.cadsr.common.resource.*"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.formbuilder.struts.common.*"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.formbuilder.struts.common.*"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.CaDSRConstants"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.jsp.tag.handler.AvailableValidValue"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.resource.ReferenceDocument"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.util.CDEBrowserParams"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.jsp.util.CDEDetailsUtils"%>

<HTML>
  <HEAD>
    <TITLE>Formbuilder: Edit Module</TITLE>
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache"/>
    <LINK rel="stylesheet" TYPE="text/css" HREF="<html:rewrite page='/css/blaf.css' />">
    <SCRIPT LANGUAGE="JavaScript1.1" SRC="<%=request.getContextPath()%>/js/checkbox.js"></SCRIPT>
    <SCRIPT LANGUAGE="JavaScript1.1" SRC='<%=request.getContextPath()%>/js/newWinJS.js'></SCRIPT>

    <SCRIPT LANGUAGE="JavaScript">
<!--

function populateDefaultValue(defaultValidValue,defaultValidValueId, index){
    var objForm0 = document.forms[0];
    var objQuestionDefaultValue = objForm0['questionDefaultValues[' + index + ']'];
 	objQuestionDefaultValue.readonly = "false";
    var objQuestionDefaultValidValueId = objForm0['questionDefaultValidValueIds[' + index + ']'];
	objQuestionDefaultValidValueId.readonly = "false";
	
    if (defaultValidValueId == '<%=FormConstants.UNKNOWN_VV_ID%>' || defaultValidValueId=='null'){
        alert("Please save the module before setting the question default value.");
        return;
    }
    objQuestionDefaultValue.value = defaultValidValue;
    objQuestionDefaultValidValueId.value = defaultValidValueId;

    setEditable(objQuestionDefaultValue, '<%= FormConstants.QUESTION_EDITABLES+"['+index+']"%>');
    
    var nv = replaceAll(defaultValidValue,'<','&lt'); // < does not seem to display, so encode
    document.getElementById("questionDefaultValuesSpan[" + index + "]").innerHTML=nv;  
}


function submitForm() {
  document.forms[0].submit();
}

function submitValidValueEdit(methodName,questionIndexValue,validValueIndexValue) {
  if (methodName=='<%=NavigationConstants.DELETE_VALID_VALUE%>' || methodName=='<%=NavigationConstants.DELETE_VALID_VALUES%>'){
	  if (isValidValueQuestionDefault(questionIndexValue,validValueIndexValue)==true){
	    alert("One of the Valid Value is used as the question default value. Please clear the default value of this quesiton first.");
	    return;
	  }
  }	  
  document.forms[0].<%=NavigationConstants.METHOD_PARAM%>.value=methodName;
  document.forms[0].<%=FormConstants.QUESTION_INDEX%>.value=questionIndexValue;
  document.forms[0].<%=FormConstants.VALID_VALUE_INDEX%>.value=validValueIndexValue;
  document.forms[0].submit();
}

function isValidValueQuestionDefault(questionIndexValue,validValueIndexValue){
    var objForm0 = document.forms[0];
    var objQuestionDefaultValidValueId = objForm0['questionDefaultValidValueIds[' + questionIndexValue+ ']'];
    
    var objDeletedValidValueIds = objForm0['<%=FormConstants.SELECTED_ITEMS+"H"%>' + questionIndexValue];    
    var objDeletedValidValueId = objDeletedValidValueIds[validValueIndexValue];
    if (objDeletedValidValueId.value==objQuestionDefaultValidValueId.value){
        return true;
    }else{
        return false;    
    }    
}



function getSelectedNumber(selectedElements){
  var len = selectedElements.length; 
  var i=0;
  var total = 0;
  for( i=0; i<len ; i++) {
    if (selectedElements[i].checked==1) {
    	total = total + 1;
    }	
  }
  return total;
}


function submitValidValuesEdit(methodName,questionIndexValue) {
   var objForm0 = document.forms[0];
   var selectedElements = objForm0['<%=FormConstants.SELECTED_ITEMS%>' + questionIndexValue];
   var selectedSize = getSelectedNumber(selectedElements);
   
   if (selectedSize==0 && methodName=='<%=NavigationConstants.DELETE_VALID_VALUES%>'){
   	alert('Please select at least one valid value to delete');
   	return;
   }

   if(selectedSize>1)
      {
      	  //verify each valid value is used as default
	  var len = selectedElements.length; 
	  var i=0;
	  for( i=0; i<len ; i++) {
	   if (selectedElements[i].checked==1) {
	      if (methodName=='<%=NavigationConstants.DELETE_VALID_VALUE%>' || methodName=='<%=NavigationConstants.DELETE_VALID_VALUES%>' ){
		if (isValidValueQuestionDefault(questionIndexValue, i) ){  
	          alert("One of the Valid Value is used as the question default value. Please clear the default value of this quesiton first.");
	    	  return;
	    	}  
	      }
	   } 
	  }
      
        document.forms[0].<%=NavigationConstants.METHOD_PARAM%>.value=methodName;
        document.forms[0].<%=FormConstants.QUESTION_INDEX%>.value=questionIndexValue;
        document.forms[0].submit();
        return true;
      }
      else
      {
	  var selectedIndexValue;
	  var len = selectedElements.length; 
	  var i=0;
	  for( i=0; i<len ; i++) {
	   if (selectedElements[i].checked==1) {
	     selectedIndexValue = i;
	   } 
	  }
        submitValidValueEdit(methodName,questionIndexValue,selectedIndexValue);
        return;
      }
 }
 
function submitModuleEdit(methodName,questionIndexValue) {
  document.forms[0].<%=NavigationConstants.METHOD_PARAM%>.value=methodName;
  document.forms[0].<%=FormConstants.QUESTION_INDEX%>.value=questionIndexValue;
  document.forms[0].submit();
}

function submitToSubsets(methodName,questionIndexValue) {
  document.forms[0].<%=FormConstants.QUESTION_INDEX%>.value=questionIndexValue;
  document.forms[0].<%=NavigationConstants.METHOD_PARAM%>.value=methodName;
  document.forms[0].submit();  
}
function submitModuleToSave(methodName) {
  if(validateModuleEditForm(moduleEditForm)) {
  document.forms[0].<%=NavigationConstants.METHOD_PARAM%>.value=methodName;
  document.forms[0].submit();
  }
}

function submitModuleForModuleSkipCreate(methodName) {
  if(validateModuleEditForm(moduleEditForm)) {
  document.forms[0].action='<%=request.getContextPath()%>/createModuleSkipAction.do'; 
  document.forms[0].<%=NavigationConstants.METHOD_PARAM%>.value=methodName;
  document.forms[0].submit();
  }  
}

function submitModuleForValidValueSkipCreate(methodName,questionIndex,validValueIndex) {
  if(validateModuleEditForm(moduleEditForm)) {
  document.forms[0].action='<%=request.getContextPath()%>/createValidValueSkipAction.do'; 
  document.forms[0].<%=NavigationConstants.METHOD_PARAM%>.value=methodName;
  document.forms[0].<%=FormConstants.SK_QUESTION_INDEX%>.value=questionIndex;
  document.forms[0].<%=FormConstants.SK_VALID_VALUE_INDEX%>.value=validValueIndex;
  document.forms[0].submit();
  }  
}
function submitModuleForModuleSkipEdit(methodName,triggerIndex) {
  if(validateModuleEditForm(moduleEditForm)) {
  document.forms[0].action='<%=request.getContextPath()%>/editModuleSkipAction.do'; 
  document.forms[0].<%=NavigationConstants.METHOD_PARAM%>.value=methodName;
  document.forms[0].<%=FormConstants.TRIGGER_ACTION_INDEX%>.value=triggerIndex;
  document.forms[0].submit();
  }  
}

function submitModuleForValidValueSkipEdit(methodName,questionIndex,validValueIndex,triggerIndex) {
  if(validateModuleEditForm(moduleEditForm)) {
  document.forms[0].action='<%=request.getContextPath()%>/editValidValueSkipAction.do'; 
  document.forms[0].<%=NavigationConstants.METHOD_PARAM%>.value=methodName;
  document.forms[0].<%=FormConstants.SK_QUESTION_INDEX%>.value=questionIndex;
  document.forms[0].<%=FormConstants.SK_VALID_VALUE_INDEX%>.value=validValueIndex;
  document.forms[0].<%=FormConstants.TRIGGER_ACTION_INDEX%>.value=triggerIndex;
  document.forms[0].submit();
  }  
}

function submitModuleToQuestion(methodName,questionIndex) {
  if(validateModuleEditForm(moduleEditForm)) {
    document.forms[0].action='<%=request.getContextPath()%>/editModuleQuestionAction.do';
  	document.forms[0].<%=NavigationConstants.METHOD_PARAM%>.value=methodName;
  	document.forms[0].<%=FormConstants.QUESTION_INDEX%>.value=questionIndex;
  	document.forms[0].submit();
  }
}

function submitForDeleteSkipModule(methodName,triggerIndex) {

  document.forms[0].action='<%=request.getContextPath()%>/formbuilder/skipAction.do'; 
  document.forms[0].<%=NavigationConstants.METHOD_PARAM%>.value=methodName;
  document.forms[0].<%=FormConstants.TRIGGER_ACTION_INDEX%>.value=triggerIndex;
  document.forms[0].submit();
}
function submitForDeleteSkipValidValue(methodName,questionIndex,validValueIndex,triggerIndex) {

  document.forms[0].action='<%=request.getContextPath()%>/formbuilder/skipAction.do'; 
  document.forms[0].<%=NavigationConstants.METHOD_PARAM%>.value=methodName;
  document.forms[0].<%=FormConstants.SK_QUESTION_INDEX%>.value=questionIndex;
  document.forms[0].<%=FormConstants.SK_VALID_VALUE_INDEX%>.value=validValueIndex;  
  document.forms[0].<%=FormConstants.TRIGGER_ACTION_INDEX%>.value=triggerIndex;
  document.forms[0].submit();
}
function clearProtocol() {
  document.forms[0].protocolIdSeq.value = "";
  document.forms[0].protocolLongName.value = "";
}


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

  function CheckAll(checkSetName)
    {
	var fo = document.forms[0];
	var len = fo.elements.length;
	for (var i = 0; i < len; i++) {
	    var e = fo.elements[i];
	    if (e.name == checkSetName) {
		Check(e);
	    }
	}

    }
  
  function ClearAll(checkSetName)
    {
	var fo = document.forms[0];
	var len = fo.elements.length;
	for (var i = 0; i < len; i++) {
	    var e = fo.elements[i];
            if (e.name == checkSetName) {
		Clear(e);
	    }
	}
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
  
  function refDocSelected(srcCompId,targetCompId)
    {
        var targetObj = document.getElementById(targetCompId);
         
         var srcObj = document.getElementById(srcCompId);
         var i;
         var count = 0;
         var newValue = '';
         for (i=0; i<srcObj.options.length; i++) {
           if (srcObj.options[i].selected) {
              targetObj.value = srcObj.options[i].value;
              newValue = srcObj.options[i].value;
             }
           }
           
        var index = targetCompId.substring(15,targetCompId.length);
        var nv = replaceAll(newValue,'<','&lt'); // < does not seem to display, so encode

        document.getElementById("moduleQuestionsSpan" + index).innerHTML=nv;  
     }
  
  function refDocHyperlink(targetCompId,newValue)
    {
        var targetObj = document.getElementById(targetCompId);
        targetObj.value=newValue;
        
        var index = targetCompId.substring(15,targetCompId.length);
        var nv = replaceAll(newValue,'<','&lt'); // < does not seem to display, so encode

        document.getElementById("moduleQuestionsSpan" + index).innerHTML=nv;  
     }  
     
   function replaceAll(string, token, newtoken) {
    if(token!=newtoken)
    while(string.indexOf(token) > -1) {
        string = string.replace(token, newtoken);
    }
    return string;
   }  
     
  function submitChangeAsso(methodName, moduleIndex, questionIndex){
    var objForm0 = document.forms[0];
    var objQuestionDefaultValidValueId = objForm0['questionDefaultValidValueIds[' + questionIndex+ ']'];
    if (objQuestionDefaultValidValueId.value !=''){
    	alert('Please clear the default value of this question and save the module before change CDE association');
    	return;
    } 	
    if(validateModuleEditForm(moduleEditForm)) {
    	document.forms[0].action='<%=request.getContextPath()%>/editModuleAssociateAction.do';
  		document.forms[0].<%=NavigationConstants.METHOD_PARAM%>.value=methodName;
  		document.forms[0].<%=FormConstants.MODULE_INDEX%>.value=moduleIndex;
  		document.forms[0].<%=FormConstants.QUESTION_INDEX%>.value=questionIndex;
  		document.forms[0].submit();
    }
  }

  function setEditable(defValElem, editableFld) {
	  var editFld = document.forms[0][editableFld][0];
	  var hiddenFld = document.forms[0][editableFld][1];
	  if (defValElem != null && editFld != null) {
		  if (defValElem.value == null || defValElem.value == '') {
				editFld.checked = true;
				editFld.disabled = true;
				hiddenFld.value=true;
			}
			else {
				editFld.checked = false;
				editFld.disabled = false;
				hiddenFld.value=false;
			}
		}
	}

	function checkHidden(fld) {
		var cbxFld = document.forms[0][fld][0];
		var hdnFld = document.forms[0][fld][1];
		
		if (cbxFld.checked == true) {
			hdnFld.value = 'true';
		}
		else {
			hdnFld.value = 'false';
		}
	}

-->
<%
  //int vvCount = 0; //JR391

  CDEBrowserParams params = CDEBrowserParams.getInstance();

  // To jum to the correct location on the screen
  String jumpto = (String)request.getAttribute(CaDSRConstants.ANCHOR);
  String jumptoStr ="";
  
  if(jumpto!=null)
    jumptoStr = "onload=\"location.hash='#"+jumpto+"'\"";  
 %>
</SCRIPT>
  </HEAD>
  <BODY topmargin=0 bgcolor="#ffffff" <%=jumptoStr%> >
  <bean:define id="moduleIndex" name="moduleEditForm" property="<%=FormConstants.MODULE_INDEX%>"/>

    <% 
      String contextPath = request.getContextPath();
      String urlPrefix = contextPath+"/";

      // Prepare parameter map for add and edit linx
      java.util.Map paramMap = new java.util.HashMap();
      paramMap.put(FormConstants.MODULE_INDEX, moduleIndex);
      // paramMap.put(FormConstants.QUESTION_INDEX, request.getParameter(FormConstants.QUESTION_INDEX));

      pageContext.setAttribute("params", paramMap); 

      %>
    <html:form action="/moduleSaveAction.do">
      <html:hidden value="" property="<%=NavigationConstants.METHOD_PARAM%>"/>
      <html:hidden property="<%=FormConstants.FORM_ID_SEQ%>"/>
      <html:hidden property="<%=FormConstants.QUESTION_ID_SEQ%>"/>
      <html:hidden property="<%=FormConstants.QUESTION_INDEX%>"/> 
      <html:hidden property="<%=FormConstants.VALID_VALUE_INDEX%>"/>
      <html:hidden property="<%=FormConstants.SK_QUESTION_INDEX%>"/>
      <html:hidden property="<%=FormConstants.SK_VALID_VALUE_INDEX%>"/>
      <html:hidden property="<%=FormConstants.TRIGGER_ACTION_INDEX%>"/>
      <html:hidden property="<%=FormConstants.MODULE_INDEX%>"/>
      
      
      <%@ include file="../common/in_process_common_header_inc.jsp"%>
      <jsp:include page="../common/tab_inc.jsp" flush="true">
        <jsp:param name="label" value="Edit&nbsp;Module"/>
        <jsp:param name="urlPrefix" value=""/>
      </jsp:include>
	  <table>
	    <tr>    
	      <td align="left" class="AbbreviatedText">
	        <bean:message key="cadsr.formbuilder.helpText.form.module.edit"/>
	      </td>
	    </tr>  
	  </table>       

		<logic:present parameter="updated" scope="request">
			<%@ include file="moduleEditBackButton_inc.jsp"%>
		</logic:present> 

		<logic:notPresent parameter="updated" scope="request">
			<%@ include file="moduleEditButton_inc.jsp"%>
		</logic:notPresent>

      
        <%@ include file="showMessages.jsp" %>
    
      <logic:present name="<%=FormConstants.MODULE%>">
        <table width="80%" align="center" cellpadding="1" cellspacing="1" border="0" class="OraBGAccentVeryDark">
          <tr class="OraTabledata">
            <td class="OraTableColumnHeader" width="20%" nowrap>
              <bean:message key="cadsr.formbuilder.form.name"/>
            </td>
            <td class="OraFieldText" >
              <bean:write  name="<%=FormConstants.CRF%>" property="longName"/> 
            </td>
          </tr>
          <tr class="OraTabledata">
            <td class="OraTableColumnHeader" width="20%" nowrap>
              <bean:message key="cadsr.formbuilder.form.publicID"/>
            </td>
            <td class="OraFieldText" >
              <bean:write  name="<%=FormConstants.CRF%>" property="publicId"/> 
            </td>
          </tr>
          <tr class="OraTabledata">
            <td class="OraTableColumnHeader" width="20%" nowrap>
              <bean:message key="cadsr.formbuilder.question.version"/>
            </td>
            <td class="OraFieldText" >
              <bean:write  name="<%=FormConstants.CRF%>" property="version"/> 
            </td>
          </tr>          
        </table>
      
        <bean:define id="module" name="<%=FormConstants.MODULE%>"></bean:define>
        
      <table cellpadding="0" cellspacing="0" width="80%" align="center">
        <tr >
          <td >
            &nbsp;
          </td>
        </tr>         
        <tr>
          <td class="OraHeaderSubSub" width="100%">Module Header</td>
        </tr>
        <tr>
          <td><img height=1 src="i/beigedot.gif" width="99%" align=top border=0> </td>
        </tr>
      </table>       
      
        <table width="80%" align="center" cellpadding="1" cellspacing="1" border="0" class="OraBGAccentVeryDark">
          <tr class="OraTabledata">
            <td class="OraTableColumnHeader" width="20%" nowrap>
              <bean:message key="cadsr.formbuilder.module.name"/>
            </td>
            <td class="OraFieldText" nowrap>
              <html:text size="100" property="<%=FormConstants.MODULE_LONG_NAME%>"
                  maxlength="<%= Integer.toString(FormConstants.LONG_NAME_MAX_LENGTH) %>">
              </html:text>
            </td>
          </tr>
          <tr class="OraTabledata">
            <td class="OraTableColumnHeader" width="20%" nowrap>
              <bean:message key="cadsr.formbuilder.form.instruction"/>
            </td>        
            <td  class="OraFieldText" width="80%" >
              <html:textarea  styleClass="OraFieldTextInstruction" rows="2" cols="102" 
                 property="<%=FormConstants.MODULE_INSTRUCTION%>">                   
              </html:textarea>
            </td>            
          </tr>   
                                  
        </table>
        
      <table cellpadding="0" cellspacing="0" width="80%" align="center">
        <tr >
          <td >
            &nbsp;
          </td>
        </tr>         
        <tr>
          <td class="OraHeaderSubSub" width="100%">Questions</td>
        </tr>
        <tr>
          <td><img height=1 src="i/beigedot.gif" width="99%" align=top border=0> </td>
        </tr>
      </table>          
        <!-- If the Question Collection is empty and deleted Question Exists -->
            <logic:empty name="module" property="questions">
             <table width="79%" align="center" cellpadding="0" cellspacing="0" border="0">     
              <tr align="right">                      
                <td align="right" width="3%">
                      <a href="javascript:submitModuleToQuestion('<%=NavigationConstants.CHECK_MODULE_CHANGES%>','<%=new Integer(0)%>')">
                         <img src='<%=urlPrefix+"i/new.gif"%>' border=0 alt="Add Question">
                      </a>                          
                </td>
              </tr>
              </table> 
              </logic:empty>
            <!-- Add for delete and new Question end -->         
        <logic:notEmpty name="module" property="questions">        
             <%
               int vvInstrIndex = 0; //used for instruction Index
             %>
            <logic:iterate id="question" name="module" indexId="questionIndex" type="gov.nih.nci.ncicb.cadsr.common.resource.Question" property="questions">
             <bean:size id="questionSize" name="module" property="questions"/>
            <!-- and anchor -->
            <A NAME="<%="Q"+questionIndex%>"></A>            
        <!-- If the Question Collection is empty and deleted Questions Exists -->
             <table width="79%" align="center" cellpadding="0" cellspacing="0" border="0">                   
              <tr align="right">
                <logic:notEmpty name="<%=FormConstants.DELETED_QUESTIONS%>">
                  <td align="right"   class="OraFieldText" nowrap width="90%">    
                      <html:select styleClass="FreeDropdown" property="<%=FormConstants.ADD_DELETED_QUESTION_IDSEQ_ARR%>">
                        <html:options collection="<%=FormConstants.DELETED_QUESTIONS%>" 
                            property="quesIdseq" labelProperty="longName" />
                      </html:select >
                  </td>
                  <td align="left" width="3%">
                      <a href="javascript:submitModuleEdit('<%=NavigationConstants.ADD_FROM_DELETED_QUESTION_LIST%>','<%=questionIndex%>')">
                         <img src=<%=urlPrefix%>i/add.gif border=0 alt="Add">
                      </a>                          
                  </td>   
                  </logic:notEmpty>
                  <logic:empty name="<%=FormConstants.DELETED_QUESTIONS%>">
                  <td width="93%">
                    &nbsp;
                  </td>  
                </logic:empty>                        
                <td align="right" width="3%">
      				<a href="javascript:submitModuleToQuestion('<%=NavigationConstants.CHECK_MODULE_CHANGES%>','<%=questionIndex%>')">
                         <img src='<%=urlPrefix+"i/new.gif"%>' border=0 alt="Add Question">
                    </a>                          
                </td>
              </tr>              
              
              </table> 
            <!-- Add for delete and new Question end -->                 
             
             <table width="80%" align="center" cellpadding="0" cellspacing="1" border="0" class="OraBGAccentVeryDark">              
              <tr class="OraTabledata">
                <td>
                  <table width="100%" align="center" cellpadding="0" cellspacing="0" border="0" class="OraBGAccentVeryDark">
                    <tr class="OraTableColumnHeader">
                      <td class="OraTableColumnHeaderModule">
                        <table width="100%" align="right" cellpadding="0" cellspacing="0" border="0" class="OraBGAccentVeryDark">
                          <tr class="OraTableColumnHeaderModule">
                            <td width="86%">&nbsp;</td>
                            <td align="right">
                              <table width="100%" align="right" cellpadding="0" cellspacing="0" border="0" class="OraBGAccentVeryDark">
                                <tr class="OraTableColumnHeaderModule">
                                  <td align="center">
                                    <logic:notEqual value="<%= String.valueOf(questionSize.intValue()-1) %>" name="questionIndex">
                                     <a href="javascript:submitModuleEdit('<%=NavigationConstants.MOVE_QUESTION_DOWN%>','<%=questionIndex%>')">                                     
                                        <img src="<%=urlPrefix%>i/down.gif" border="0" alt="Down"/>
                                      </a>
                                    </logic:notEqual>                                  
                                  </td>
                                  <td align="center">
                                    <logic:notEqual value="<%= String.valueOf(0) %>" name="questionIndex">                                    
                                     <a href="javascript:submitModuleEdit('<%=NavigationConstants.MOVE_QUESTION_UP%>','<%=questionIndex%>')">
                                        <img src="<%=urlPrefix%>i/up.gif" border="0" alt="Up"/>
                                      </a>
                                    </logic:notEqual>
                                  </td>
                                  <td align="center">
                                     <a href="javascript:submitChangeAsso('<%=NavigationConstants.CHECK_MODULE_CHANGES%>', '<%=moduleIndex%>', '<%=questionIndex%>')">
                                      <img src="<%=urlPrefix%>i/association.gif" border="0" alt="Change CDE Association"/>
                                      </a>
                                  </td>                                  
                                  <td align="center">
                                    <a href="javascript:submitModuleEdit('<%=NavigationConstants.DELETE_QUESTION%>','<%=questionIndex%>')">
                                      <img src="<%=urlPrefix%>i/delete.gif" border="0" alt="Delete"/>
                                    </a>
                                  </td>
                                  
                                </tr>
                              </table>
                            </td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    
                    <tr class="OraTableColumnHeader">
                      <td class="OraTableColumnHeaderModule">
                        <table width="100%" align="right" cellpadding="0" cellspacing="0" border="0" class="OraBGAccentVeryDark">
                          <tr class="OraHeaderBlack">
                            
                            <td align="right">
                              <table width="100%" align="right" cellpadding="0" cellspacing="0" border="0" class="OraBGAccentVeryDark">
                                <tr class="OraHeaderBlack">                                
                                <logic:notPresent name="question" property="dataElement">
                                 <td >
                                  <html:textarea  styleClass="OraFieldText" rows="2" cols="102" property='<%=FormConstants.MODULE_QUESTIONS+"["+questionIndex+"]"%>' styleId="<%=FormConstants.MODULE_QUESTIONS+questionIndex %>">
                                 </html:textarea>
                                 </td>        
                                </logic:notPresent>
                                <logic:present name="question" property="dataElement">
                                 <td >
                                 <span id='<%=FormConstants.MODULE_QUESTIONS+"Span"+questionIndex%>'><bean:write name="moduleEditForm" property='<%=FormConstants.MODULE_QUESTIONS+"["+questionIndex+"]"%>' filter="false" /></span>
                                 <html:hidden  property='<%=FormConstants.MODULE_QUESTIONS+"["+questionIndex+"]"%>' styleId="<%=FormConstants.MODULE_QUESTIONS+questionIndex %>"></html:hidden>
                                 </td>        
                                  <td class="OraHeaderBlack" align="center" width="70" >
                                   <html:link href='<%=params.getCdeBrowserUrl() +"/CDEBrowser/search?dataElementDetails=9&PageId=DataElementsGroup&queryDE=yes&FirstTimer=0"%>' 
                                      paramId="p_de_idseq" paramName="question" paramProperty="dataElement.deIdseq" target="_blank">
                                    <bean:write name="question" property="dataElement.CDEId"/>
                                   </html:link>
                                  </td>                        
                                  <td class="OraHeaderBlack" align="center" width="70" >
                                    <bean:write name="question" property="dataElement.version"/>
                                  </td>
                                 </logic:present>                               
                                </tr>
                              </table>
                            </td>
                          </tr>
                        </table>
                      </td>
                    </tr>                  
                    
                  </table>
                </td>
              </tr>           
              <tr class="OraTabledata">
                <td>
                  <table width="100%" align="center" cellpadding="0" cellspacing="0" border="0" class="OraBGAccentVeryDark">                                                                                 
                      <tr><td colspan="2">
                      <table width="100%" align="center" cellpadding="0" cellspacing="1" border="0" class="OraBGAccentVeryDark">
                
                        <tr class="OraTabledata">
                            <td width="26%" class="OraTableColumnHeader" nowrap align="left">
                              <bean:message key="cadsr.formbuilder.form.instruction"/> 
                            </td>                      
                            <td class="OraFieldTextInstruction">
                               <html:textarea   rows="2" cols="67" 
                                    property='<%=FormConstants.QUESTION_INSTRUCTIONS+"["+questionIndex+"]"%>'>
                                </html:textarea>                               
                            </td>
                        </tr> 
                        <%--mandatory--%>
                        <tr class="OraTabledata">
                            <td width="26%" class="OraTableColumnHeader" nowrap align="left">
                              <bean:message key="cadsr.formbuilder.form.question.mandatory"/> 
                            </td>                      
                            <td class="OraFieldText">
        			    <html:radio property='<%=FormConstants.QUESTION_MANDATORIES+"["+questionIndex+"]"%>' value="No" >No </html:radio>
				    <html:radio property='<%=FormConstants.QUESTION_MANDATORIES+"["+questionIndex+"]"%>' value="Yes" >Yes</html:radio>
                            </td>
                        </tr> 
						
						<tr class="OraTabledata">
                            <td width="26%" class="OraTableColumnHeader" nowrap align="left">
                              <bean:message key="cadsr.formbuilder.form.question.editable"/> 
                            </td>                      
                            <td class="OraFieldText">
        			    
							<logic:equal name="question" property="deDerived" value="true">
								<html:checkbox property='<%=FormConstants.QUESTION_EDITABLES+"["+questionIndex+"]"%>' disabled="true" />
								&nbsp;&nbsp;<font color="gray" size="2"><i>Cannot be changed because Data Element is derived</i></font>
							</logic:equal>

							<logic:notEqual name="question" property="deDerived" value="true">
								<logic:empty name="moduleEditForm" property='<%=FormConstants.QUESTION_DEFAULTVALUES+"["+questionIndex+"]"%>'>
									<html:checkbox property='<%=FormConstants.QUESTION_EDITABLES+"["+questionIndex+"]"%>' disabled="true" onclick='<%= "checkHidden('"+ FormConstants.QUESTION_EDITABLES+"["+questionIndex +"]')" %>'/>
								</logic:empty>
								<logic:notEmpty name="moduleEditForm" property='<%=FormConstants.QUESTION_DEFAULTVALUES+"["+questionIndex+"]"%>'>
									<html:checkbox property='<%=FormConstants.QUESTION_EDITABLES+"["+questionIndex+"]"%>' onclick='<%= "checkHidden('"+ FormConstants.QUESTION_EDITABLES+"["+questionIndex +"]')" %>'/>
								</logic:notEmpty>
							</logic:notEqual>
								<html:hidden property='<%=FormConstants.QUESTION_EDITABLES+"["+questionIndex+"]"%>' value="<%= String.valueOf(((Boolean[])((org.apache.struts.action.DynaActionForm)session.getAttribute("moduleEditForm")).get(FormConstants.QUESTION_EDITABLES))[questionIndex.intValue()]) %>" />
                            </td>
                        </tr> 
                      <logic:present name="question" property="dataElement">                       
                       <tr class="OraTabledata">
                          <td width="26%" class="OraTableColumnHeader" nowrap align="left">
                            <bean:message key="cadsr.formbuilder.cde.workflow" />
                          </td>                      
                          <td class="OraFieldText">
                              <bean:write name="question" property="dataElement.aslName"/>
                          </td>
                      </tr> 
                      <tr class="OraTabledata">
                          <td width="26%" class="OraTableColumnHeader" nowrap align="left">
                             <bean:message key="cadsr.formbuilder.helpText.moduleEdit.altText.preferred" />
                          </td>                      
                          <td class="OraFieldText">
                              <cde:RefDocAltQuestionTextDisplay questionBeanId= "question" 
                                                  htmlObjectRef='<%=FormConstants.MODULE_QUESTIONS+questionIndex %>'
                                                  selectBoxClassName="AltQuestionField"
                                                  selectBoxSize="4"
                                                  refDocType="<%=ReferenceDocument.REF_DOC_TYPE_PREFERRED_QUESTION_TEXT%>"
                                                  questionIndex="<%=questionIndex.toString()%>" 
                                                  selectBoxJSFunctionName="refDocSelected"
                                                  hyperLinkJSFunctionName="refDocHyperlink"
                                                  /> 
                          </td>                          
                      </tr>    
                      <tr class="OraTabledata">
                          <td width="26%" class="OraTableColumnHeader" nowrap align="left">
                             <bean:message key="cadsr.formbuilder.helpText.moduleEdit.altText.other" />
                          </td>                      
                          <td class="OraFieldText">
                              <cde:RefDocAltQuestionTextDisplay questionBeanId= "question" 
                                                  htmlObjectRef='<%=FormConstants.MODULE_QUESTIONS+questionIndex %>'
                                                  selectBoxClassName="AltQuestionField"
                                                  selectBoxSize="4"
                                                  refDocType="<%=ReferenceDocument.REF_DOC_TYPE_ALT_QUESTION_TEXT%>"
                                                  questionIndex="<%=questionIndex.toString()%>" 
                                                  selectBoxJSFunctionName="refDocSelected"
                                                  hyperLinkJSFunctionName="refDocHyperlink"
                                                  /> 
                          </td>                          
                      </tr>                                                
                      </logic:present>                        
                       <tr class="OraTabledata">
                          <td width="28%" class="OraTableColumnHeader" nowrap align="left">
                            <bean:message key="cadsr.formbuilder.helpText.moduleEdit.altText.orgQuestion" />
                          </td>                      
                          <td class="OraFieldText">
                              <cde:questionAltText questionBeanId= "question" 
                                                  htmlObjectRef='<%=FormConstants.MODULE_QUESTIONS+questionIndex %>'
                                                  questionProperty = "longName"
                                                  deProperty = "longCDEName"
                                                  orgModuleBeanId= '<%=FormConstants.CLONED_MODULE%>'
                                                  formIndex="0"
                                                  questionIndex="<%=questionIndex.toString()%>" /> 
                          </td>
                      </tr>  
                        <tr class="OraTabledata">
                            <td width="26%" class="OraTableColumnHeader" nowrap align="left">
                              Default value 
                            </td>                      
                            <td class="OraFieldText">
                            <logic:notEmpty name="question" property="validValues">
                            <html:hidden property='<%=FormConstants.QUESTION_DEFAULTVALUES+"["+questionIndex+"]"%>'/>
                            <span id='<%=FormConstants.QUESTION_DEFAULTVALUES+"Span["+questionIndex+"]"%>'><bean:write name="moduleEditForm" property='<%=FormConstants.QUESTION_DEFAULTVALUES+"["+questionIndex+"]"%>' filter="false" /></span>
                            &nbsp;&nbsp;&nbsp;<a href="javascript:populateDefaultValue('','', '<%=questionIndex%>')">Clear</a>                          

                            <html:hidden property='<%=FormConstants.QUESTION_DEFAULT_VALIDVALUE_IDS+"["+questionIndex+"]"%>'/>
                            </logic:notEmpty>    
                            <logic:empty name="question" property="validValues">                                    
								<logic:equal name="question" property="deDerived" value="true">
		                            <html:hidden property='<%=FormConstants.QUESTION_DEFAULTVALUES+"["+questionIndex+"]"%>' />
		                            <span id="questionDefaultValues"><bean:write name="moduleEditForm" property='<%=FormConstants.QUESTION_DEFAULTVALUES+"["+questionIndex+"]"%>' filter="false" /></span>
		                            &nbsp;&nbsp;&nbsp;<a href="javascript:populateDefaultValue('','', '<%=questionIndex%>')">Clear</a>                          
								</logic:equal>
								<logic:notEqual name="question" property="deDerived" value="true">
									<html:hidden property='<%=FormConstants.QUESTION_DEFAULT_VALIDVALUE_IDS+"["+questionIndex+"]"%>' />
                              		<html:text property='<%=FormConstants.QUESTION_DEFAULTVALUES+"["+questionIndex+"]"%>' size="70" onkeyup='<%= "setEditable(this, '"+ FormConstants.QUESTION_EDITABLES+"["+questionIndex +"]')" %>' />
								</logic:notEqual>
                            </logic:empty>    
                            </td>
                        </tr>                       



<%--  value domain details--%>
				
			   <logic:present name="question" property="dataElement">
                            <logic:present name="question" property="dataElement.valueDomain">
                         	<tr class="OraTabledata">
                                  <td class="OraTableColumnHeader" colspan="2">                              
                                        <bean:message key="cadsr.formbuilder.form.valueDomain.valueDomainDetails"/>        
                                     </td>                                     
                                    </tr>
                                    
                                    <tr class="OraTabledata">
                                     <td class="OraTableColumnHeader" width="26%">
                                        <bean:message key="cadsr.formbuilder.form.valueDomain.longName"/>
                                     </td>
                                     <td class="OraFieldText">
                                       <bean:write  name="question" property="dataElement.valueDomain.longName"/>          
                            
                                     </td>
                                    </tr>
                                    
                                    <tr class="OraTabledata">
                                     <td class="OraTableColumnHeader" width="26%">
                                        <bean:message key="cadsr.formbuilder.form.valueDomain.datatype"/>
                                     </td>
                                     <td class="OraFieldText">
                                       <bean:write  name="question" property="dataElement.valueDomain.datatype"/>          
                            
                                     </td>
                                    </tr>

                                    <tr class="OraTabledata">
                                     <td class="OraTableColumnHeader" width="26%">
                                        <bean:message key="cadsr.formbuilder.form.valueDomain.unitofmeasure"/>
                                     </td>
                                     <td class="OraFieldText">
                                       <bean:write  name="question" property="dataElement.valueDomain.unitOfMeasure"/>     
                                     </td>
                                    </tr>

                                    <tr class="OraTabledata">
                                     <td class="OraTableColumnHeader" width="26%">
                                        <bean:message key="cadsr.formbuilder.form.valueDomain.displayFormat"/>
                                     </td>
                                     <td class="OraFieldText">
                                       <bean:write  name="question" property="dataElement.valueDomain.displayFormat"/>     
                                     </td>
                                    </tr>

                                    <tr class="OraTabledata" width="26%">
                                     <td class="OraTableColumnHeader">
                                        <bean:message key="cadsr.formbuilder.form.valueDomain.concepts"/>
                                     </td>
                                     <td class="OraFieldText">
                                       
<%=CDEDetailsUtils.getConceptCodesUrl(question.getDataElement().getValueDomain().getConceptDerivationRule(),params,"link",",")%>
                                     </td>
                                    </tr>
                            </logic:present>
                           </logic:present>
						
<%-- end of value domain details--%>


                      </table>
                      </td></tr>
                      
                      <logic:present name="question">
                      <!-- Empty ValidValues -->
                      <logic:empty name="question" property="validValues">
						                         
                          <tr class="OraTabledata">
                            <td class="OraTabledata" width="50">&nbsp;</td>
                            <td class="OraTabledata" align="right" width="90%">                                                                        
                                <table width="79%" align="right" cellpadding="0" cellspacing="0" border="0" class="OraBGAccentVeryDark">
                                  <tr class="OraTabledata" >
                                   <td >&nbsp;</td>
                                       <!-- Adding from available vv list -->
                                          <td align="right"   class="OraFieldText" nowrap width="90%">  
                                          <% String vvZeroSelectName = FormConstants.ADD_AVAILABLE_VALID_VALUE_INDEX+"Q"+questionIndex+"V"+0; %>  
                                            <cde:availableValidValues
                                              questionBeanId="question"
                                              availableValidValusMapId="<%=FormConstants.AVAILABLE_VALID_VALUES_MAP%>"
                                              selectClassName="FreeDropdown"
                                              selectName="<%=vvZeroSelectName%>"/>
                                          </td>
                                          <logic:present name="<%=AvailableValidValue.AVAILABLE_VALID_VALUE_PRESENT%>">
                                           <td align="left" width="4%">
                                              <a href="javascript:submitValidValueEdit('<%=NavigationConstants.ADD_FROM_AVAILABLE_VALID_VALUE_LIST%>','<%=questionIndex%>','0')">
                                                 <img src=<%=urlPrefix%>i/add.gif border=0 alt="Add">
                                              </a>                          
                                            </td>
                                          </logic:present>
                                          <logic:notPresent name="<%=AvailableValidValue.AVAILABLE_VALID_VALUE_PRESENT%>">
                                              <td class="OraTabledata" >&nbsp;</td>                                                                                                                                                                           
                                           </logic:notPresent>                                            
                                          <td class="OraTabledata" width="10">&nbsp;</td>
                                          <!-- Adding from available vv list end -->                                         
                                  </tr>

                                </table>                                                                                                            
                            </td>
                          </tr> 
							
                        </logic:empty>
                      <!-- ValidValues not Empty -->
                      <logic:notEmpty name="question" property="validValues">                           
						
                          <tr class="OraTabledata">
                            
                            <td  colspan="2" width="90%">
                              <table width="100%" align="center" cellpadding="1" cellspacing="0" border="0" class="OraBGAccentVeryDark">
                                  <tr class="OraTabledata">
                                    <td class="OraTabledata" >&nbsp;</td>
                                    <td class="OraTabledata" align="left" width="90%"> 
                                       <table width="60%" align="left" cellpadding="0" cellspacing="0" border="0" >
                                         <tr >
                                            <td width="16%" align="left" >
                                             <a href="javascript:CheckAll('<%= FormConstants.SELECTED_ITEMS+questionIndex %>')">Check All
                                             </a>
                                           </td>
                                           <td width="16%" align="left">
                                             <a href="javascript:ClearAll('<%= FormConstants.SELECTED_ITEMS+questionIndex %>')">Clear All
                                             </a>     
                                           </td>
                                           <td align="left" width="5%">
                                              <a href="javascript:submitValidValuesEdit('<%=NavigationConstants.DELETE_VALID_VALUES%>','<%=questionIndex%>')">                                              
                                               <img src="<%=urlPrefix%>i/delete.gif" border="0" alt="Delete"/>
                                              </a>
                                            </td>
                                            <!-- Subset Uncomment when working on subsets for CDEs
                                            <td align="left">
                                             <a href="javascript:submitToSubsets('<%=NavigationConstants.VIEW_SUBSETTEDVDS_LIST%>','<%=questionIndex %>')">
                                                 Select from existing subsets
                                             </a>
                                            </td>
                                            Subset end -->
                                        </tr>
                                      </table>
                                    </td>                               
                                <logic:iterate id="validValue" name="question" indexId="validValueIndex" type="gov.nih.nci.ncicb.cadsr.common.resource.FormValidValue" property="validValues">
                                <bean:size id="validValueSize" name="question" property="validValues"/>  
                                	<% String vvIndSelectName = FormConstants.ADD_AVAILABLE_VALID_VALUE_INDEX+"Q"+questionIndex+"V"+validValueIndex; %>                                
                                  <tr class="OraTabledata">
                                    <td class="OraTabledata" >&nbsp;</td>
                                    <td class="OraTabledata" align="right" width="90%">                                                                        
                                        <table width="100%" align="right" cellpadding="0" cellspacing="0" border="0" class="OraBGAccentVeryDark">
                                          <tr class="OraTabledata" >
                                           <td align="left" >
                                              <INPUT TYPE=CHECKBOX NAME="<%= FormConstants.SELECTED_ITEMS+questionIndex%>" value="<%= validValueIndex %>">
                                              <INPUT TYPE=hidden NAME='<%= FormConstants.SELECTED_ITEMS+"H"+questionIndex%>' value='<%= validValue.getValueIdseq()%>'>
                                              </td>
                                               <!-- Adding from available vv list -->
                                                  
                                                  <td align="right"   class="OraFieldText" nowrap width="90%">    
                                                    <cde:availableValidValues
                                                      questionBeanId="question"
                                                      availableValidValusMapId="<%=FormConstants.AVAILABLE_VALID_VALUES_MAP%>"
                                                      selectClassName="FreeDropdown"
                                                      selectName="<%=vvIndSelectName%>"/>
                                                  </td>
                                                  <logic:present name="<%=AvailableValidValue.AVAILABLE_VALID_VALUE_PRESENT%>">
                                                    <td align="left" width="4%">
                                                      <a href="javascript:submitValidValueEdit('<%=NavigationConstants.ADD_FROM_AVAILABLE_VALID_VALUE_LIST%>','<%=questionIndex%>','<%=validValueIndex%>')">
                                                         <img src=<%=urlPrefix%>i/add.gif border=0 alt="Add">
                                                      </a>                          
                                                    </td>   
                                                  </logic:present>
                                                  <logic:notPresent name="<%=AvailableValidValue.AVAILABLE_VALID_VALUE_PRESENT%>">
                                                    <td class="OraTabledata" >&nbsp;</td>                                                                                                                                                                           
                                                  </logic:notPresent>                                                    
                                                  <!-- Adding from available vv list end -->
                                          </tr>                                         
                                        </table>                                                                                                            
                                    </td>
                                  </tr>                                   
                                  <tr class="OraFieldText">
                                    <td class="OraTabledata" width="10%">&nbsp;</td>
                                    <td class="OraFieldText" align="right" width="90%">                                                                        
                                        <table width="100%" align="right" cellpadding="0" cellspacing="0" border="0" class="OraBGAccentVeryDark">
                                          <tr class="OraHeaderBlack" >
                                           <td class="OraFieldText" width="86%">
                                          <bean:write name="validValue" property="longName" filter="false"/>
                                          <% String formattedValidValue = validValue.getLongName();
                                             if (formattedValidValue!=null){
                                             	formattedValidValue = StringUtils.getValidJSString(formattedValidValue);
                                             }	
                                           %>  
										<logic:notEqual name="question" property="deDerived" value="true">
                                          <a href="javascript:populateDefaultValue('<%=formattedValidValue%>', '<%=validValue.getValueIdseq()%>', '<%=questionIndex%>')">
                                             Set as Question Default Value
                                          </a>                          
										</logic:notEqual>
                                           </td>
                                            <td align="center">
                                              <logic:notEqual value="<%= String.valueOf(validValueSize.intValue()-1) %>" name="validValueIndex">
                                                <a href="javascript:submitValidValueEdit('<%=NavigationConstants.MOVE_VALID_VALUE_DOWN%>','<%=questionIndex%>','<%=validValueIndex%>')">
                                                  <img src="<%=urlPrefix%>i/down.gif" border="0" alt="Down"/>
                                                </a>
                                              </logic:notEqual>   
                                              <logic:equal value="<%= String.valueOf(validValueSize.intValue()-1) %>" name="validValueIndex">
						                                    &nbsp;&nbsp;&nbsp;
                                              </logic:equal>                                                
                                            </td>
                                            <td align="center">
                                              <logic:notEqual value="<%= String.valueOf(0) %>" name="validValueIndex">
                                               <a href="javascript:submitValidValueEdit('<%=NavigationConstants.MOVE_VALID_VALUE_UP%>','<%=questionIndex%>','<%=validValueIndex%>')">                                               
                                                 <img src="<%=urlPrefix%>i/up.gif" border="0" alt="Up"/>
                                                </a>
                                              </logic:notEqual>
                                              <logic:equal value="<%= String.valueOf(0) %>" name="validValueIndex">
						                                     &nbsp;&nbsp;&nbsp;
                                              </logic:equal>                                              
                                            </td>
                                            <td align="center">
                                              <a href="javascript:submitValidValueEdit('<%=NavigationConstants.DELETE_VALID_VALUE%>','<%=questionIndex%>','<%=validValueIndex%>')">                                              
                                               <img src="<%=urlPrefix%>i/delete.gif" border="0" alt="Delete"/>
                                              </a>
                                            </td>
                                          </tr>
                                            <tr class="OraFieldText" >
                                              <td colspan="4"> 
                                                 <table width="100%" align="right" cellpadding="0" cellspacing="1" border="0" class="OraBGAccentVeryDark">
<logic:present name="question" property="dataElement">
                                                  <%
                                                     String dest =request.getContextPath() + "/showValueMeaningAlterNames.do?"+NavigationConstants.METHOD_PARAM+"=showValueMeaningAlterNames&validValueIndex=" + validValueIndex + "&vvColumnIndex=" + vvInstrIndex +
                                                            	"&questionIndex=" + questionIndex + "&moduleIndex="+moduleIndex;
                                                   %>
                                                  <logic:notEmpty name="validValue" property="valueMeaning"> 
                                                      <tr class="OraTabledata" >
                                                       <td  class="OraTableColumnHeader" width="30%">
                                                            <bean:message key="cadsr.formbuilder.valueMeaning.text" />
                                                       </td>
                                                       <td class="OraFieldText" >      
                                                          <html:textarea  styleClass="OraFieldText" rows="2" cols="80" property='<%=FormConstants.FORM_VALUE_MEANING_TEXT + "[" + vvInstrIndex + "]"%>' readonly="true">
                                                          </html:textarea>
                                                          &nbsp;
    <!-- JR391 -->
    <%
        //*** Valid Value = VM ***
        //This block would not be entered if there is a Question does not have any valid value/vm!!!
        //System.out.println("The type of the question is: " + question.getClass().getName());
    /*
        vvCount++;
        if(question.getValidValues() != null) {
            List<ValidValue> validValues = question.getValidValues();
            System.out.println(vvCount + "] validValues.size() = [" + validValues.size() + "]");
        }
    */
    %>
    <!-- JR391 -->
                                                          <logic:notEmpty name="validValue" property="valueMeaning.designations">
                                                            <a href="javaScript:newWin('<%=dest%>', 'Alternates', 1200, 900);">
                                                            Modify
                                                            </a>
                                                          </logic:notEmpty>
                                                       </td>                                          
                                                      </tr>
		                                              <tr class="OraTabledata">
		                                                 <td  class="OraTableColumnHeader" width="30%">
		                                                   <bean:message key="cadsr.formbuilder.valueMeaning.idversion" /></td>
		                                                 <td class="OraFieldText" valign="middle">
                                                          <html:textarea  styleClass="OraFieldText" rows="1" cols="80" property='<%=FormConstants.FORM_VALUE_MEANING_IDVERSION + "[" + vvInstrIndex + "]"%>' readonly="true">
                                                          </html:textarea>
                                                         </td> 
		                                              </tr>  
                                                      <%--value meaning description--%>
                                                      <tr class="OraTabledata" >
                                                       <td  class="OraTableColumnHeader" width="30%">
                                                            <bean:message key="cadsr.formbuilder.valueMeaning.description" />
                                                       </td>
                                                       <td class="OraFieldText" >                                                       
                                                          <html:textarea  styleClass="OraFieldText" rows="2" cols="80" property='<%=FormConstants.FORM_VALUE_MEANING_DESC + "[" + vvInstrIndex + "]"%>' readonly="true">
                                                          </html:textarea>
    <!-- JR391 -->
                                                        <logic:notEmpty name="validValue" property="valueMeaning.definitions">
                                                          &nbsp;
                                                            <a href="javaScript:newWin('<%=dest%>', 'Alternates', 1200, 900);">
                                                           Modify
                                                            </a>
                                                        </logic:notEmpty> <%--definitions--%>
                                                       </td>                                          
                                                      </tr>
                                                      </logic:notEmpty>
                                                      <logic:empty name="validValue" property="valueMeaning">
						         <html:hidden  value="" property="<%=FormConstants.FORM_VALUE_MEANING_TEXT%>"/>
						         <html:hidden  value="" property="<%=FormConstants.FORM_VALUE_MEANING_IDVERSION%>"/>
						         <html:hidden  value="" property="<%=FormConstants.FORM_VALUE_MEANING_DESC%>"/>
                                                      </logic:empty>
</logic:present>
                                                      
<logic:notPresent name="question" property="dataElement">   
<%--						         <html:hidden property='<%=FormConstants.FORM_VALUE_MEANING_TEXT + "[" + vvInstrIndex + "]"%>'/>
						         <html:hidden property='<%=FormConstants.FORM_VALUE_MEANING_DESC + "[" + vvInstrIndex + "]"%>'/>
--%>
                                                      <tr class="OraTabledata" >
                                                       <td  class="OraTableColumnHeader" width="30%">
                                                            <bean:message key="cadsr.formbuilder.valueMeaning.text" />
                                                       </td>
                                                       <td class="OraFieldText" >      
                                                          <html:textarea  styleClass="OraFieldText" rows="2" cols="80" property='<%=FormConstants.FORM_VALUE_MEANING_TEXT + "[" + vvInstrIndex + "]"%>'>
                                                          </html:textarea>
                                                       </td>                                          
                                                      </tr>
                                                      <tr class="OraTabledata" >
                                                       <td  class="OraTableColumnHeader" width="30%">
                                                            <bean:message key="cadsr.formbuilder.valueMeaning.idversion" />
                                                       </td>
                                                       <td class="OraFieldText"  valign="middle">      
                                                          <html:textarea  styleClass="OraFieldText" rows="1" cols="80" property='<%=FormConstants.FORM_VALUE_MEANING_IDVERSION + "[" + vvInstrIndex + "]"%>'>
                                                          </html:textarea>
                                                       </td>                                          
                                                      </tr>
                                                      <%--value meaning description--%>
                                                      <tr class="OraTabledata" >
                                                       <td  class="OraTableColumnHeader" width="30%">
                                                            <bean:message key="cadsr.formbuilder.valueMeaning.description" />
                                                       </td>
                                                       <td class="OraFieldText" >                                                       
                                                          <html:textarea  styleClass="OraFieldText" rows="2" cols="80" property='<%=FormConstants.FORM_VALUE_MEANING_DESC + "[" + vvInstrIndex + "]"%>' >
                                                          </html:textarea>
                                                       </td>                                          
                                                      </tr>
</logic:notPresent>

                                                    <tr class="OraTabledata" >
                                                     <td  class="OraTableColumnHeader" width="30%">
                                                        <bean:message key="cadsr.formbuilder.form.instruction"/>
                                                     </td>
                                                     <td class="OraFieldTextInstruction" >
                                                         <html:textarea   rows="2" cols="70" 
                                                              property='<%=FormConstants.FORM_VALID_VALUE_INSTRUCTIONS+"["+vvInstrIndex+"]"%>'>
                                                          </html:textarea>                                                              
                                                     </td>                                          
                                                    </tr>                
  
                                                  </table>
                                               </td>
                                           </tr>
                                                <!-- vv skip Pattern -->
                                                <tr class="OraTabledata" >
                                                    
                                                            <td colspan="4" align="right">
                                                            <table width="100%" align="center" cellpadding="0" cellspacing="0" border="0" class="OraBGAccentVeryDark">
								
								 <logic:present name="validValue" property = "triggerActions" >
								   <logic:notEmpty name="validValue" property = "triggerActions">
									      <logic:iterate id="currTriggerAction" name="validValue" type="gov.nih.nci.ncicb.cadsr.common.resource.TriggerAction" property="triggerActions" indexId="triggerIndex" >
											<%@ include file="skipPatternDetailsEditVV_inc.jsp"%>
									      </logic:iterate>
								    </logic:notEmpty>
								 </logic:present>
								                                                             
                                                            </table>
                                                           </td>
                                                    </tr>
                                                  <!-- vv Skip pattern end -->    
                                                  <tr class="OraTabledata" >                                                         
                                                   <td colspan="4" align=right>

                                                         <a href="javascript:submitModuleForValidValueSkipCreate('<%=NavigationConstants.CHECK_MODULE_CHANGES%>','<%=questionIndex%>','<%=validValueIndex%>')"> 
                                                            Add Skip pattern                                                            
                                                         </a>                                                        
                                                      </td>
                                                    </tr>                                                    
                                                    
                                        </table>                                                                                                            
                                    </td>
                                  </tr> 
                                       
                                                                            
                                  
                                <logic:equal value="<%= String.valueOf(validValueSize.intValue()-1) %>" name="validValueIndex">
                                  <tr class="OraTabledata">
                                    <td class="OraTabledata" >&nbsp;</td>
                                    <td class="OraTabledata" align="right" >                                                                        
                                        <table width="79%" align="right" cellpadding="0" cellspacing="0" border="0" class="OraBGAccentVeryDark">
                                          <tr class="OraTabledata" >
                                           <td >&nbsp;</td>
                                               <!-- Adding from available vv list -->                            
                                				<% String vvSizeSelectName = FormConstants.ADD_AVAILABLE_VALID_VALUE_INDEX+"Q"+questionIndex+"V"+validValueSize; %>                                
                                                  <td align="right"   class="OraFieldText" nowrap width="90%">    
                                                    <cde:availableValidValues
                                                      questionBeanId="question"
                                                      availableValidValusMapId="<%=FormConstants.AVAILABLE_VALID_VALUES_MAP%>"
                                                      selectClassName="FreeDropdown"
                                                      selectName="<%=vvSizeSelectName%>"/>
                                                  </td>
                                                  <logic:present name="<%=AvailableValidValue.AVAILABLE_VALID_VALUE_PRESENT%>">
                                                    <td align="left" width="4%">
                                                      <a href="javascript:submitValidValueEdit('<%=NavigationConstants.ADD_FROM_AVAILABLE_VALID_VALUE_LIST%>','<%=questionIndex%>','<%=validValueSize%>')">
                                                         <img src=<%=urlPrefix%>i/add.gif border=0 alt="Add">
                                                      </a>                          
                                                    </td>   
                                                  </logic:present>
                                                  <logic:notPresent name="<%=AvailableValidValue.AVAILABLE_VALID_VALUE_PRESENT%>">
                                                    <td class="OraTabledata" >&nbsp;</td>                                                                                                                                                                           
                                                  </logic:notPresent>                                                    
                                                  <!-- Adding from available vv list end -->
                                          </tr>

                                         </table>                                                                                                            
                                    </td>
                                  </tr>  
                                  <logic:present name="<%=AvailableValidValue.AVAILABLE_VALID_VALUE_PRESENT%>">
                                  <tr class="OraTabledata" >
                                       <td class="OraTabledata" >&nbsp;</td>
                                       <td colspan="2" align="left" class="AbbreviatedText">
                                       <logic:present name="question" property="dataElement">
                                     <bean:message key="cadsr.formbuilder.helpText.moduleEdit.vd.validvalue.lov"/>
                                       </logic:present>
                                       <logic:notPresent name="question" property="dataElement">
                                     <bean:message key="cadsr.formbuilder.helpText.moduleEdit.validvalue.deleted.lov"/>
                                       </logic:notPresent>					       
                                       </td>
                                   </tr>
                                 </logic:present>
                                  
                                 </logic:equal>  
                                 <%
                                    ++vvInstrIndex;
                                 %>
                                </logic:iterate> 



                              </table>
                            </td>
                          </tr>
							
                        </logic:notEmpty>                                                                   
                      </logic:present>                  
                  </table>
                </td>
              </tr>
             </table>             
            <logic:equal value="<%= String.valueOf(questionSize.intValue()-1) %>" name="questionIndex">
            <!-- Add for delete and new Question -->
             <table width="79%" align="center" cellpadding="0" cellspacing="0" border="0">        
              <tr align="right">
                <logic:notEmpty name="<%=FormConstants.DELETED_QUESTIONS%>">
                  <td align="right"   class="OraFieldText" nowrap="nowrap" width="23%">    
                      <html:select styleClass="FreeDropdown" property="<%=FormConstants.ADD_DELETED_QUESTION_IDSEQ_ARR%>">
                        <html:options collection="<%=FormConstants.DELETED_QUESTIONS%>" 
                            property="quesIdseq" labelProperty="longName" />
                      </html:select >
                  </td>
                  <td align="left" width="1%">
                      <a href="javascript:submitModuleEdit('<%=NavigationConstants.ADD_FROM_DELETED_QUESTION_LIST%>','<%=questionIndex.intValue()+1%>')">
                         <img src=<%=urlPrefix%>i/add.gif border=0 alt="Add">
                      </a>                          
                  </td>   
                  </logic:notEmpty>
                  <logic:empty name="<%=FormConstants.DELETED_QUESTIONS%>">
                   <td width="20%">
                    &nbsp;
                   </td>  
                  </logic:empty>               
                 <td align="right" width="1%">
                      <a href="javascript:submitModuleToQuestion('<%=NavigationConstants.CHECK_MODULE_CHANGES%>','<%=questionSize%>')">
                         <img src='<%=urlPrefix+"i/new.gif"%>' border=0 alt="Add Question">
                      </a>                          
						&nbsp;
                </td>           
              </tr>
              </table> 
              
            <!-- Add for delete and new Question end -->  
            </logic:equal>             
            </logic:iterate>          
        </logic:notEmpty>
        
                <!-- Module skip Pattern -->
                 <logic:present name="module" property = "triggerActions" >
                   <logic:notEmpty name="module" property = "triggerActions">
                            <table width="84%" align="center" cellpadding="0" cellspacing="1" border="0" class="OraBGAccentVeryDark">
                              <logic:iterate id="currTriggerAction" name="module" type="gov.nih.nci.ncicb.cadsr.common.resource.TriggerAction" property="triggerActions" indexId="triggerIndex" >
								<%@ include file="skipPatternDetailsEditModule_inc.jsp"%>                                             
                              </logic:iterate>
                            </table>
                    </logic:notEmpty>
                 </logic:present>
                 <!-- Module Skip pattern end --> 
                 
           
               <table width="79%" align="center" cellpadding="0" cellspacing="0" border="0">     
                <tr>
                <td align="right">
                 <a href="javascript:submitModuleForModuleSkipCreate('<%=NavigationConstants.CHECK_MODULE_CHANGES%>')"> 
                    Add Skip pattern                                                            
                 </a>    &nbsp;
                </td>
               </tr>
              </table>
           <br>        
      </logic:present>

      <logic:present parameter="updated" scope="request">
		<%@ include file="moduleEditBackButton_inc.jsp"%>
	</logic:present> 

	<logic:notPresent parameter="updated" scope="request">
		<%@ include file="moduleEditButton_inc.jsp"%>
	</logic:notPresent>

    </html:form>
    <%@ include file="../common/common_bottom_border.jsp"%>
  	<html:javascript formName="moduleEditForm"/>
  </BODY>
</HTML>
