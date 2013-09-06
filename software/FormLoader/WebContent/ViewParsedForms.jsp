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
<body>
<h2>Successfully parsed forms from XML File: <s:property value="xmlFileFileName"/></h2>
<h3>Select forms to validate the questions and <br>data elements against caDSR database</h3>

<br>
<s:actionerror />	
<s:if test="parsedFormsList.size() >= 1">
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
			<s:iterator value="parsedFormsList" id = "bean" status="status">
			<tr	class="<s:if test="#status.odd == true ">odd</s:if> <s:else>even</s:else>">
				<td>
					<s:checkbox name="checkboxes[%{#status.index}]" theme = "simple" />
					
				</td>
				<td><s:property value="publicId" /></td>
				<td><s:property value="version" /></td>
				<td><s:property value="longName" /></td>
				<td><s:property value="context" /></td>
				<td><s:property value="type" /></td>
				<td><s:property value="protocolName" /></td>
				<td><s:property value="workflowStatusName" /></td>
				<td><s:property value="modules.size()" /></td>
			</tr>
			</s:iterator>
		</table>
		</div>
	<s:submit value="Select Forms"/>  
	</s:form>
	</s:if>
</body>
</html>