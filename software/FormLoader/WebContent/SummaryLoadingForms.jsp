<%--L
  Copyright Oracle Inc, ScenPro Inc, SAIC-F

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/cadsr-formbuilder/LICENSE.txt for details.
L--%>

<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<style type="text/css">
@import url(css/style.css);
</style>
	<title>Load Summary</title>
</head>
<div style="padding-left: 50px; padding-right: 50px;">
<body>
<h2>Summary of Collection "<s:property value="loadedFormCollection.getNameWithRepeatIndicator()" />"</h2>
<s:property value="loadedForms.size()" /> form(s) in the Collection were successfully loaded. <br>
<s:property value="otherForms.size()" /> form(s) in the Collection were not loaded.
<br><br>

<h3>Loaded</h3>
<div class="content">
		<table class="fileTable">
			<tr class="even">
				<th>Public Id</th>
				<th>Version</th>
				<th>Long Name</th>
				<th>Context</th>
				<th>Type</th>
				<th>Protocol Name(s)</th>
				<th># Module(s)</th>
				<th>Load Type</th>
				<th>Load Status</th>
			</tr>
			<s:iterator value="loadedForms" var = "bean" status="status">
			<tr	class="<s:if test="#status.odd == true ">odd</s:if> <s:else>even</s:else>">
				<td>
				<a href="<s:property value="getLinkToFormBuilder()" />" target="_blank"><s:property value="publicId" /></a>
				</td>
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
<br>
<s:if test="otherForms.size() > 0">	
<hr>
	<h3>Not Loaded</h3>

		<div class="content">
		<table class="fileTable">
			<tr class="even">
				<th>Public Id in XML</th>
				<th>Version in XML</th>
				<th>Long Name in XML</th>
				<th>Reason</th>
			</tr>
			<s:iterator value="otherForms" var = "bean" status="status">
			<tr	class="<s:if test="#status.odd == true ">odd</s:if> <s:else>even</s:else>">
				<td><s:property value="publicId" /></td>
				<td><s:property value="version" /></td>
				<td><s:property value="longName" /></td>
				<td><s:property value="getLoadStatusStringWithMessages()" /></td>
			</tr>
			</s:iterator>
		</table>
		</div>
	
	</s:if>
</body>
</div>
</html>