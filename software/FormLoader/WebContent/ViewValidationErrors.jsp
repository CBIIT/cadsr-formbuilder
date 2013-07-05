<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
	<title>View Validation Errors</title>
<s:head />
<style type="text/css">
@import url(css/style.css);
</style>
</head>
<body>
<h2>View Validation Errors in XML File: <s:property value="xmlFileFileName"/> </h2>

<br>
	
<s:if test="parsedFormsList.size() >= 1">
	
		<div class="content">
		<table class="fileTable" cellpadding="5px">
			<tr class="even">
				<th>Public Id</th>
				<th>Version</th>
				<th>Form Long Name</th>
				<th>Validation Errors</th>
			</tr>
			<s:iterator value="parsedFormsList" id = "bean" status="status">
			<tr	class="<s:if test="#status.odd == true ">odd</s:if> <s:else>even</s:else>">
				<td><s:property value="publicId" /></td>
				<td><s:property value="version" /></td>
				<td><s:property value="longName" /></td>
				<td><font color = red><s:property value="errorMessage" /></font></td>
			</tr>
			</s:iterator>
		</table>
		</div>
	
	</s:if>
</body>
</html>