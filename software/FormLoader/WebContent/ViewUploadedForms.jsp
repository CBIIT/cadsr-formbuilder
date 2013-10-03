<%--L
  Copyright Oracle Inc, ScenPro Inc, SAIC-F

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/cadsr-formbuilder/LICENSE.txt for details.
L--%>

<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
	<title>View Uploaded Forms</title>

<s:head />
<style type="text/css">
@import url(css/style.css);
</style>
</head>
<div id="4b" style="padding-left: 50px; padding-right: 50px;">
<body>
<h2>View Uploaded Forms of Selected XML Files</h2>
<br>

<s:if test="uploadedFormsList.size() >= 1">
	
	<s:form action="unloadForms" theme = "simple" method="post"> 
		<div class="content">
		<table class="fileTable" cellpadding="5px">
			<tr class="even">
				<th>Public Id</th>
				<th>Version</th>
				<th>Long Name</th>
				<th>Context</th>
				<th>Type</th>
				<th>Protocol Name</th>
				<th>Workflow Status</th>
				<th>XML Filename</th>
				<th>Select</th>
			</tr>
			<s:iterator value="uploadedFormsList" id = "bean" status="status">
			<tr	class="<s:if test="#status.odd == true ">odd</s:if> <s:else>even</s:else>">
				<td><s:property value="publicId" /></td>
				<td><s:property value="version" /></td>
				<td><s:property value="longName" /></td>
				<td><s:property value="context" /></td>
				<td><s:property value="type" /></td>
				<td><s:property value="protocolName" /></td>
				<td><s:property value="workflowStatus" /></td>
				<td><s:property value="xmlFilename" /></td>
				<td>
					<s:checkbox name="checkboxes[%{#status.index}]" theme = "simple" />
					<s:hidden name="publicIds[%{#status.index}]" value = "%{#bean.publicId}" />
					<s:hidden name="versions[%{#status.index}]" value = "%{#bean.version}" />
				</td>
			</tr>
			</s:iterator>
		</table>
		</div>
	<s:submit value="Unload Selected Forms"/>  
	</s:form>
	</s:if>
</body>
</div>
</html>