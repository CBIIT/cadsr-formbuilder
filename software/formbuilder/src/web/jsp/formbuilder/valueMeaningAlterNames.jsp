
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@page import="java.util.*" %>
<%@page import="gov.nih.nci.ncicb.cadsr.common.util.StringUtils"%>
<%@page import="gov.nih.nci.ncicb.cadsr.common.resource.* " %>
<%@page import="gov.nih.nci.ncicb.cadsr.common.formbuilder.struts.common.*"%>


<html>
  <head>
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache"/>
    <LINK rel="stylesheet" TYPE="text/css" HREF="/formbuilder/css/blaf.css">
    <title>Value Meaning Details</title>
    
    <SCRIPT LANGUAGE="JavaScript1.1">
    function passBack(alter, columnName) {
	    var objForm0 = opener.document.forms[0];	    
            var objAlter = objForm0[columnName + '[' + <%=request.getParameter("vvColumnIndex")%> + ']'];	    
	    objAlter.value = alter;
	    close();
    }
    </SCRIPT>
</head>
  <body>

    <% 
    
    ValueMeaning vm = (ValueMeaning)request.getAttribute(FormConstants.VALUE_MEANING_OBJ);

    List altNames = vm.getDesignations();
    List altDefs = vm.getDefinitions();
    
    HashMap idToCscsi = new HashMap();
    HashMap csToAltName = new HashMap();
    HashMap csToAltDef = new HashMap();
    List altNamesNoCS = new ArrayList();
    List altDefsNoCS = new ArrayList();
    if (altNames !=null)
    for (int i=0; i<altNames.size(); i++) {
    	Designation des = (Designation) altNames.get(i);
    	if (des.getCsCsis() == null || des.getCsCsis().size() == 0) 
    	   altNamesNoCS.add(des);
    	else {
		Iterator iter = des.getCsCsis().iterator();

		while (iter.hasNext()) {
		   ClassSchemeItem cscsi= (ClassSchemeItem) iter.next();
		   idToCscsi.put(cscsi.getCsCsiIdseq(), cscsi);
		   if (csToAltName.get(cscsi.getCsCsiIdseq()) == null)
			csToAltName.put(cscsi.getCsCsiIdseq(), new ArrayList());
		   ((ArrayList) csToAltName.get(cscsi.getCsCsiIdseq())).add(des);
		}
    	}
    }
  
  if (altDefs != null)
    for (int i=0; i<altDefs.size(); i++) {
      Definition definition = (Definition) altDefs.get(i);
	if (definition.getCsCsis() == null || definition.getCsCsis().size() == 0) 
	   altDefsNoCS.add(definition);
	else {
	  Iterator iter = definition.getCsCsis().iterator();
	  while (iter.hasNext()) {
	    ClassSchemeItem cscsi= (ClassSchemeItem) iter.next();
	    idToCscsi.put(cscsi.getCsCsiIdseq(), cscsi);
	    if (csToAltDef.get(cscsi.getCsCsiIdseq()) == null)
		csToAltDef.put(cscsi.getCsCsiIdseq(), new ArrayList());
	    ((ArrayList) csToAltDef.get(cscsi.getCsCsiIdseq())).add(definition);
	}
    	}
    }
    
    if (altNamesNoCS.size() >0) {
    	idToCscsi.put("null", null);
    	csToAltName.put("null", altNamesNoCS);
    }
    	
    if (altDefsNoCS.size() >0) {
    	idToCscsi.put("null", null);
    	csToAltDef.put("null", altDefsNoCS);
    }
  %>

<br>
<table cellpadding="0" cellspacing="0" width="80%" align="center" >
  <tr>
    <td class="OraHeaderSubSub" width="100%" colspan="2">Value Meaning Alternate Names and Definitions</td>
  </tr>
  <tr>
    <td width="100%" colspan="2"><img height=1 src="i/beigedot.gif" width="99%" align=top border=0> </td>
  </tr>
</table>
<p>
<table width="80%" align="center" cellpadding="1" cellspacing="1" border="0" class="OraBGAccentVeryDark">
      <tr class="OraTabledata">
        <td class="OraTableColumnHeader" width="20%">Value Meaning Text</td>
        <td class="OraFieldText"><%=vm.getLongName()%>
	<br>
        <% String longName = vm.getLongName();
           if (longName!=null){
        	   longName = StringUtils.getValidJSString(longName);
		//longName = StringUtils.strReplace(longName, "\"","&quot;");
	      	//longName = StringUtils.strReplace(longName, "\'",  "&acute;");
	   }   	
	%>      		
        <logic:notPresent name="formbuilder">
       	<a href="javascript:passBack('<%=longName%>', 'formsValueMeaningTexts')">Select as Value Meaning Text</a>
        </logic:notPresent>
       	</td>

      </tr>
      <tr class="OraTabledata">
        <td class="OraTableColumnHeader">Value Meaning Public ID Version</td>
        <td class="OraFieldText"><%=vm.getPublicId()%>v<%=vm.getVersion()%></td>
      </tr>
      <tr class="OraTabledata">
        <td class="OraTableColumnHeader">Value Meaning Definition</td>
        <td class="OraFieldText"><%=vm.getPreferredDefinition()%>
	<br>
        <% String desc = vm.getPreferredDefinition();
           if (desc!=null){
        	   desc = StringUtils.getValidJSString(desc);
		//desc = StringUtils.strReplace(desc, "\"","&quot;");
	 //     	desc = StringUtils.strReplace(desc, "\'",  "&acute;");
	   }   	
	%>      		
        <logic:notPresent name="formbuilder">
       	<a href="javascript:passBack('<%=desc%>', 'formsValueMeaningDescs')">Select as Value Meaning Desc.</a>
        </logic:notPresent>
       	</td>

      </tr>
</table>
<p>
<table width="80%" align="center" cellpadding="1" cellspacing="1" border="0" class="OraBGAccentVeryDark">

<% if (idToCscsi.isEmpty()) {%>
    <tr class="OraTabledata"> There are no alternate names and definitions</tr>
    </table>
<%}else{
Iterator csiIter = idToCscsi.keySet().iterator();
while (csiIter.hasNext()) {
    String csiId = (String) csiIter.next();
    ClassSchemeItem currCSI = (ClassSchemeItem) idToCscsi.get(csiId);
    List currAltNames = (List) csToAltName.get(csiId);
    List currAltDefs = (List) csToAltDef.get(csiId);
 %>   
<table width="80%" align="center" cellpadding="1" cellspacing="1" border="1" class="OraBGAccentVeryDark">
<% if (currCSI != null) { %>
	<TR class="OraTableColumnHeader">
	 <th class="OraTableColumnHeader">CS* Long Name</th>
	 <th class="OraTableColumnHeader">CS* Public ID Version</th>
	 <th class="OraTableColumnHeader">CS* Definition</th>
	 <th class="OraTableColumnHeader">CSI* Name</th>
	 <th class="OraTableColumnHeader">CSI* Public ID Version</th>
	 <th class="OraTableColumnHeader">CSI* Type</th>
	</TR>
      <tr class="OraTabledata">
        <td class="OraFieldText"><%=currCSI.getClassSchemeLongName()%> </td>
        <td class="OraFieldText"><%=currCSI.getCsID()%>v<%=currCSI.getCsVersion()%></td>
        <td class="OraFieldText"><%=currCSI.getClassSchemeDefinition()%> </td>
        <td class="OraFieldText"><%=currCSI.getClassSchemeItemName()%> </td>
        <td class="OraFieldText"><%=currCSI.getCsiId()%>v<%=currCSI.getCsiVersion()%></td>
        <td class="OraFieldText"><%=currCSI.getClassSchemeItemType()%> </td>
      </tr>
<%}else {%>
      <tr class="OraTabledata"  align="center">
        <td class="OraFieldText" colspan="4">No Classification</td>
</tr>
<%}%>
<tr class="OraTabledata">
<td class="OraHeaderSubSubSub" width="100%" colspan=4><b>Alternate Names </b></td>
</tr>
<tr class="OraTabledata"><td width="100%" colspan=4>
<table width="100%" align="center" cellpadding="1" cellspacing="1" border="1" class="OraBGAccentVeryDark">
<%
  Designation des = null;
  if (currAltNames!=null && currAltNames.size() > 0) {
%>
  <tr class="OraTableColumnHeader">
    <th class="OraTableColumnHeader" width="50%">
        <bean:message key="cadsr.formbuilder.valueMeaning.alternate.name"/> </th>
    <th class="OraTableColumnHeader" width="16%">
        <bean:message key="cadsr.formbuilder.valueMeaning.alternate.type"/></th>
    <th class="OraTableColumnHeader" width="16%">
        <bean:message key="cadsr.formbuilder.valueMeaning.alternate.context"/></th>
    <th class="OraTableColumnHeader" >
        <bean:message key="cadsr.formbuilder.valueMeaning.alternate.language"/></th>
  </tr>
<%
    for (int i=0;i<currAltNames.size(); i++) {
      des = (Designation)currAltNames.get(i);
%>
      <tr class="OraTabledata">
        <td class="OraFieldText"><%=des.getName()%>
        <% String altName = des.getName();
           if (altName!=null){
        	   altName = StringUtils.getValidJSString(altName);
		//altName = StringUtils.strReplace(altName, "\"","&quot;");
	     // 	altName = StringUtils.strReplace(altName, "\'",  "&acute;");
	   }   	
	%>      		        
        <logic:notPresent name="formbuilder">        
	<br>
       	<a href="javascript:passBack('<%=altName%>', 'formsValueMeaningTexts')">Select as Value Meaning Text</a>
        </logic:notPresent>
        </td>
        <td class="OraFieldText"><%=des.getType()==null?"":des.getType()%> </td>
        <td class="OraFieldText"><%=des.getContext().getName()%> </td>
        <td class="OraFieldText"><%=des.getLanguage()%></td>
      </tr>
<%
    }
  }
  else {
%>
       <tr class="OraTabledata" align="center">
         <td colspan="4">There are no alternate names for the selected Value Meaning.</td>
       </tr>
<%
  }
%>

</table> 
</td></tr>
<tr class="OraTabledata">
<td class="OraHeaderSubSubSub" width="100%" colspan=4><b>Alternate Definitions </b></td>
</tr>
<tr class="OraTabledata"><td width="100%" colspan=4>
<table width="100%" align="center" cellpadding="1" cellspacing="1" border="1" class="OraBGAccentVeryDark">
<%
  if (currAltDefs != null && currAltDefs.size() > 0) {
%>
  <tr class="OraTableColumnHeader">
    <th class="OraTableColumnHeader" width="50%">
        <bean:message key="cadsr.formbuilder.valueMeaning.alternate.definition"/> </th>
    <th class="OraTableColumnHeader" width="16%">
        <bean:message key="cadsr.formbuilder.valueMeaning.alternate.type"/></th>
    <th class="OraTableColumnHeader" width="16%">
        <bean:message key="cadsr.formbuilder.valueMeaning.alternate.context"/></th>
    <th class="OraTableColumnHeader" >
        <bean:message key="cadsr.formbuilder.valueMeaning.alternate.language"/></th>
  </tr>
<%
    for (int i=0;i<currAltDefs.size(); i++) {
      Definition def = (Definition)currAltDefs.get(i);
%>
      <tr class="OraTabledata">
        <td class="OraFieldText"><%=def.getDefinition()%> 
        <% String altDef = def.getDefinition();
           if (altDef!=null){
        	   altDef = StringUtils.getValidJSString(altDef);
		//altDef = StringUtils.strReplace(altDef, "\"","&quot;");
	     // 	altDef = StringUtils.strReplace(altDef, "\'",  "&acute;");
	   }   	
	%>  
       <logic:notPresent name="formbuilder">
	<br>        
       	<a href="javascript:passBack('<%=altDef%>', 'formsValueMeaningDescs')">Select as Value Meaning Description</a>
        </logic:notPresent>
        </td>
        <td class="OraFieldText"><%=def.getType()==null?"":def.getType()%> </td>
        <td class="OraFieldText"><%=def.getContext().getName()%> </td>
        <td class="OraFieldText"><%=def.getLanguage()%> </td>
      </tr>
<%
    }
  }
  else {
%>
       <tr class="OraTabledata" align="center">
         <td colspan="4">There are no alternate definitions for the selected Value Meaning.</td>
       </tr>
<%
  }
%>

</table> 
</td></tr>
</table>
<p>
<%} //end of while
}//end of else%>

</body>
</html>