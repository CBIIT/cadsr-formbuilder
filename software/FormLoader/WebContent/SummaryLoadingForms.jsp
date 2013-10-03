<%--L
  Copyright Oracle Inc, ScenPro Inc, SAIC-F

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/cadsr-formbuilder/LICENSE.txt for details.
L--%>

<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>
<html>
<head>
<style type="text/css">
@import url(css/style.css);
</style>
	<title>Summary of Loading Forms</title>
	<sx:head />
</head>
<div id="4b" style="padding-left: 50px; padding-right: 50px;">
<body>
<h2>Summary of Loaded Forms
</h2>
<div class="content">
		<table class="fileTable" cellpadding="5px">
			<tr class="even">
				<th>Public Id</th>
				<th>Version</th>
				<th>Long Name</th>
				<th>Context</th>
				<th>Type</th>
				<th>Protocol Name</th>
				<th># Modules</th>
				<th>Load Type</th>
				<th>Load Status</th>
			</tr>
			<s:iterator value="loadedForms" id = "bean" status="status">
			<tr	class="<s:if test="#status.odd == true ">odd</s:if> <s:else>even</s:else>">
				<td><s:property value="publicId" /></td>
				<td><s:property value="version" /></td>
				<td><s:property value="longName" /></td>
				<td><s:property value="context" /></td>
				<td><s:property value="type" /></td>
				<td><s:property value="protocolName" /></td>
				<td><s:property value="modules.size()" /></td>
				<td><s:property value="loadType" /></td>
				<td><s:property value="getLoadStatusString()" /></td>
			</tr>
			</s:iterator>
		</table>
</div>

</body>
</div>
</html>