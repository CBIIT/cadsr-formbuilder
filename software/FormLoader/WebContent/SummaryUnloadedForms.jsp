<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>
<html>
<head>
	<title>Summary of Unloaded Forms</title>
	<sx:head />
</head>
<body>
<h2>Summary of Unloaded Forms
</h2>
<div class="content">
	<table class="summaryTable" cellpadding="5px">	
		<s:iterator value="unloadingSummary" status="status">
			<tr	class="<s:if test="#status.odd == true ">odd</s:if> <s:else>even</s:else>">
				<td><s:property/></td>
			</tr>
		</s:iterator>
	</table>
</div>
</body>
</html>