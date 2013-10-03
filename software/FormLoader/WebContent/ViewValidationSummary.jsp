<%--L
  Copyright Oracle Inc, ScenPro Inc, SAIC-F

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/cadsr-formbuilder/LICENSE.txt for details.
L--%>

<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
	<title>View Validation Summary of Selected Forms</title>
<s:head />
<style type="text/css">
@import url(css/style.css);
</style>
</head>
<div id="4b" style="padding-left: 50px; padding-right: 50px;">
<body>
<h2>View Validation Summary of Selected Forms</h2>
<br>
<s:actionerror />
<s:if test="validatedForms.size() >= 1">
	<s:form action="loadForms" theme = "simple" method="post"> 
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
				<th># Modules</th>
				<th>Load Type</th>
				<th>Load Status</th>
			</tr>
			<s:iterator value="validatedForms" id = "bean" status="status">
			<tr	class="<s:if test="#status.odd == true ">odd</s:if> <s:else>even</s:else>">
				<td>
					<!--  s:checkbox name="loadformcheckboxes[%{#status.index}]" theme = "simple" /> -->
					<s:if test="loadStatus >=3">
					<s:checkbox name="selectedFormIndices" fieldValue="%{index}" theme = "simple" />
					</s:if>
					<s:else>
    				<s:checkbox name="greyout" disabled="true" theme = "simple" />
					</s:else>
					
				</td>
				<td><s:property value="publicId" /></td>
				<td><s:property value="version" /></td>
				<td><s:property value="longName" /></td>
				<td><s:property value="context" /></td>
				<td><s:property value="type" /></td>
				<td><div style="width: 250px;"><s:property value="protocolName" /></div></td>
				<td><s:property value="workflowStatusName" /></td>
				<td><s:property value="modules.size()" /></td>
				<td><s:property value="loadType" /></td>
				<td><s:property value="getLoadStatusString()" /></td>
			</tr>
			</s:iterator>
				<tr>
		 <td colspan="1" align="left" nowrap>
<s:submit type="image" src="/FormLoader/i/load_forms.gif" method="execute" align="left" theme="simple" /></td>
	  	<td colspan="1" align="left" nowrap>
<s:submit type="image" src="/FormLoader/i/cancel.gif" method="cancel" align="left" theme="simple"/></td>
</tr> 
		</table>
		</div>
	</s:form>
	</s:if>
</body>
</div>
</html>