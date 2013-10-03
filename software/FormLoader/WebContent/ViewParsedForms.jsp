<%--L
  Copyright Oracle Inc, ScenPro Inc, SAIC-F

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/cadsr-formbuilder/LICENSE.txt for details.
L--%>

<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
	<title>View Parsed Forms</title>
<s:head />
<style type="text/css">
@import url(css/style.css);
</style>
</head>

<div id="4b" style="padding-left: 50px; padding-right: 50px;">

<body>
<h2>Forms Passed XML Validation</h2>
<br>
Select forms to validate the questions and data elements against caDSR database

<br>
<s:actionerror />	
<s:if test="parsedFormsNoErrorList.size() >= 1">
	<s:form action="validateForms" theme = "simple" method="post"> 
		<div class="content">
		<table class="fileTable" cellpadding="5px">
			<tr class="even">
				<th>Select</th>
				<th>Public Id</th>
				<th>Version</th>
				<th>Long Name</th>
				<th>Context</th>
				<th>Type</th>
				<th>Protocol Name</th>
				<th>Workflow Status</th>
				<th># Module(s)</th>
			</tr>
			<s:iterator value="parsedFormsNoErrorList" id = "bean" status="status">
			<tr	class="<s:if test="#status.odd == true ">odd</s:if> <s:else>even</s:else>">
				<td>
					<!--  s:checkbox name="checkboxes[%{#status.index}]" theme = "simple" /> -->
					<s:checkbox name="selectedFormIndices" fieldValue="%{index}" theme = "simple" />
				</td>
				<td><s:property value="publicId" /></td>
				<td><s:property value="version" /></td>
				<td><s:property value="longName" /></td>
				<td><s:property value="context" /></td>
				<td><s:property value="type" /></td>
				<td><div style="width: 250px;"><s:property value="protocolName" /></div></td>
				<td><s:property value="workflowStatusName" /></td>
				<td><s:property value="modules.size()" /></td>
			</tr>
			</s:iterator>
<tr>
		  	<td colspan="1" align="left" nowrap>
<s:submit type="image" src="/FormLoader/i/validate.gif" method="execute" align="left" theme="simple" /></td>
	  	<td colspan="1" align="left" nowrap>
<s:submit type="image" src="/FormLoader/i/cancel.gif" method="cancel" align="left" theme="simple"/></td>
	  	<td colspan="1" align="left" nowrap>
<s:submit type="image" src="/FormLoader/i/reset.gif" method="reset" align="left" theme="simple"/></td>
</tr>
		</table>
		</div> 
	</s:form>
	</s:if>
	
	<h2>Forms Failed XML Validation</h2>
    <br>

	<s:if test="parsedFormsWithErrorList.size() >= 1">
		<div class="content">
		<table class="fileTable" cellpadding="5px">
			<tr class="even">
				<th>Public Id</th>
				<th>Version</th>
				<th>Long Name</th>
				<th>Errors</th>
			</tr>
			<s:iterator value="parsedFormsWithErrorList" id = "bean" status="status">
			<tr	class="<s:if test="#status.odd == true ">odd</s:if> <s:else>even</s:else>">
				<td><s:property value="publicId" /></td>
				<td><s:property value="version" /></td>
				<td><s:property value="longName" /></td>
				<td><s:property value="getXmlValidationErrorString()" /></td>
			</tr>
			</s:iterator>
		</table>
		</div>
	</s:if>
</body>
</div>
</html>