
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
	<title>Search Result Loaded Forms</title>
<s:head />
<style type="text/css">
@import url(css/style.css);
</style>
</head>
<body>
<h2>Search Result Loaded Forms</h2>
<h3>Select forms below will be marked as RETIRED UNLOADED.<br>If it's a new version, upon unloaded, an older version needs be selected as latest.</h3>

<br>
<s:actionerror />	
<body>
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
				<th>Current Workflow Status</th>
				<th>Modified Date</th>
				<th>Modified By</th>
				<th>Form Collection Name</th>
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
				<td><s:property value="modifiedBy" /></td>
				<td><s:property value="modifiedBy" /></td>
				<td><s:property value="formSeqId" /></td>
			</tr>
			</s:iterator>
<tr>
  <td colspan="1" align="left" nowrap>
      <s:submit type="image" src="/FormLoader/i/Unload-Forms.gif" method="execute" align="left" theme="simple" /></td>

</tr>
</table>
</div>
</s:form>
</body>
</html>