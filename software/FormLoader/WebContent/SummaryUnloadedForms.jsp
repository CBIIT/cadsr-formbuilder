<%--L
  Copyright Oracle Inc, ScenPro Inc, SAIC-F

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/cadsr-formbuilder/LICENSE.txt for details.
L--%>

<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
	<title>Summary of Unloaded Forms</title>
<script src="./js/jquery-1.9.1.js"></script>
  <script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
  
  <script>
  $(function() {
    $( document ).tooltip();
  });
  </script>
  
<style type="text/css">
@import url(css/style.css)
</style>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>

    <meta name="viewport" content="width=device-width">
</head>
<body>
<h2>Summary of Unloaded Forms
</h2>

	<!--  div class="content"> -->
		<table id="collectiontable">
		
			<tr>
				<th>Unload Status</th>
				<th>Public ID</th>
				<th>Version</th>
				<th>Long Name</th>
				<th>Context</th>
				<th>Current Workflow Status</th>
				<th>Modified By</th>
				<th>Modified Date</th>
				<th>Collection Name</th>
				<th>Comment</th>
			</tr>
			
            <s:iterator value="unloadedForms" var = "bean" status="status">
            <tbody>
			<tr	class="<s:if test="#status.odd == true ">odd</s:if> <s:else>even</s:else>" id="<s:property value="id" />">
				<td align="center">
				<s:if test="loadStatus < 0">
					<a href="#" title="<s:property value="getMessagesInString()" />">
				<s:property value="getLoadStatusString()" /></a></s:if>
				<s:elseif test="loadStatus > 0">
				<s:property value="getLoadStatusString()" />
				
				</s:elseif>
				
				</td>
				<td><s:property value="publicId" /></td>
				<td><s:property value="version" /></td>
				<td align="center"><div style="width: 150px;"><s:property value="longName" /></div></td>
				<td><s:property value="context" /></td>
				<td><s:property value="workflowStatusName" /></td>
				<td><s:property value="modifiedBy" /></td>
				<td><s:property value="modifiedDate" /></td>
				<td><s:property value="getCollectionName()" /></td>
				<td><s:property value="getMessagesInString()" /></td>
			</tr>			
			</tbody>
			</s:iterator>
			
		</table>
		

</body>
</html>