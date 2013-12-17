<%--L
  Copyright Oracle Inc, ScenPro Inc, SAIC-F

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/cadsr-formbuilder/LICENSE.txt for details.
L--%>

<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
	<title>DB Validation Summary</title>
<s:head />
<style type="text/css">
@import url(css/style.css);
</style>

</head>
<div style="padding-left: 50px; padding-right: 50px;">
<body>
<h2>Result of DB Validation</h2>
<br>
<s:actionerror />
<s:if test="validatedForms.size() >= 1">
	<s:form action="loadForms" theme = "simple" method="post"> 
		<div class="content">
		<table class="fileTable">
			<tr class="even">
				<th>Select to Load</th>
				<th>Public Id in XML</th>
				<th>Version in XML</th>
				<th>Existing Versions in DB</th>
				<th>Long Name in XML</th>
				<th>Context in XML</th>
				<th>Type in XML</th>
				<th>Protocol Name(s) in XML</th>
				<th>Workflow Status in XML</th>
				<th>Workflow Status in DB</th>
				<th># Module(s) in XML</th>
				<th>Load Type</th>
				<th>DB Validation Status</th>
			</tr>
			<s:iterator value="validatedForms" var="form" status="status">
			<tr	class="<s:if test="#status.odd == true ">odd</s:if> <s:else>even</s:else>">
				<td>					
					<s:if test="loadStatus == 4">
					<s:checkbox id="myCheck" name="selectedFormIndices" fieldValue="%{index}" value="false" 
					onClick="toggleActionButton('selectedFormIndices', 'action:loadForms')" theme = "simple" />
					</s:if>
					<s:else>
    				<s:checkbox name="greyout" disabled="true" theme = "simple" />
					</s:else>
				</td>
				<td><s:property value="publicId" /></td>
				<td><s:property value="version" /></td>
				<td style="word-wrap: break-word"><div style="width: 250px;"><s:property value="versionCadsr" /></div></td>
				<td><s:property value="longName" /></td>
				<td><s:property value="context" /></td>
				<td><s:property value="type" /></td>
				<td><div style="width: 250px;"><s:property value="protocolName" /></div></td>
				<td><s:property value="workflowStatusName" /></td>
				<td><s:property value="workflowStatusCadsr" /></td>
				
				<td><s:property value="modules.size()" /></td>
				<td>
				<s:if test="loadType.equals('Update Form')">
					<b><s:property value="loadType" /> </b>
				</s:if>
				  <s:if test="loadType.equals('New Version')">
					<b><a href="<s:property value="versioningRulesUrl" />" target="_blank"><s:property value="loadType" /></a></b>
				</s:if>  
				<s:else>
					<s:property value="loadType" />
				</s:else>
				</td>
				<td>
				<s:if test="loadStatus != 4">
				
					<a href="#" title="<s:property value="getMessagesInString()" />">
				<s:property value="getLoadStatusString()" /></a></s:if>
				
				<s:elseif test="loadStatus == 4">
				
				<a href="#" onClick=
				"return popupvalidationMessages('showValidationMessages.action?formIndexInCollection=<s:property value="index" />')"><s:property value="getLoadStatusString()" /> </a>
				
				
				
				<!--  a href="#"><s:property value="getLoadStatusString()" /></a>
				<input type="hidden" id="<s:property value="getFormIdString()" />" value='<s:property value="getStructuredStatusInHtml(false)" />'> -->
				</s:elseif>
				
				
				
				</td>
			</tr>
			</s:iterator>
				<tr>
		 <td colspan="1" align="left" nowrap>
<s:submit type="image" src="/FormLoader/i/load_forms.gif" title="Select at least one form and you may proceed..." 
	action="loadForms" disabled="true" align="left" theme="simple" /></td>
	  	<td colspan="1" align="left" nowrap>
<s:submit type="image" src="/FormLoader/i/cancel.gif" action="cancelLoad" align="left" theme="simple"/></td>
		<td colspan="1" align="left" nowrap>
<input type="image" src="/FormLoader/i/reset.gif"  onClick="return resetAllCheckboxesByName('selectedFormIndices', 0);" /></input></td>
</tr> 
		</table>
		</div>
	</s:form>
	</s:if>
	
<script src="http://ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.min.js"></script>
<script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.2/jquery-ui.min.js"></script>
  
<script type='text/javascript' src='js/jquery.simplemodal.js'></script>
	
	<script type='text/javascript' src='js/formloader-common.js'></script>
	
<script type="text/javascript"> 
$(document).ready(function() {
	
	$('td[id="valStatus"]').click(function(){
		
		var data = $(this).find('input').val();
		//alert(data);
		
		$.modal(data);
      }); 
	
	
});
</script>
	
	
	<script>
  $(function() {
    $( document ).tooltip();
  });
  </script>

</body>
</div>
</html>