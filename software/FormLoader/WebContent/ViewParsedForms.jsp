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

<div style="padding-left: 50px; padding-right: 50px;">

<body>
<br>
<table>
<tr>
<td align="left"><div style="height:10px; "><h3>Collection Name:</h3></div></td>
<td>&nbsp;</td>
<td valign="bottom"><div style="height:10px; "><b><s:property value="collectionName" /></b></div></td>
</tr>
<tr>
<td align="left"><div style="height:10px; "><h3>Collection Description:</h3></div></td>
<td>&nbsp;</td>
<td valign="bottom"><div style="height:10px; "><b><s:property value="description" /></b></div></td>
</tr>
</table>
<br><hr>

<h3>Forms that passed XML Validation</h3>
Select forms to validate the questions against caDSR database
<br><br><br>

<s:actionerror />
	
<s:if test="parsedFormsNoErrorList.size() >= 1">
	<s:form action="validateForms" theme = "simple" method="post">
		<div class="content">
		<table class="fileTable">
			<tr class="even">
				<th>Select for <br>DB Validation</th>
				<th>Public Id in XML</th>
				<th>Version in XML</th>
				<th>Long Name in XML</th>
				<th>Context in XML</th>
				<th>Type in XML</th>
				<th>Protocol Name(s) in XML</th>
				<th>Workflow Status in XML</th>
				<th># Module(s) in XML</th>
			</tr>
			<s:iterator value="parsedFormsNoErrorList" id = "bean" status="status">
			<tr	class="<s:if test="#status.odd == true ">odd</s:if> <s:else>even</s:else>">
				<td>
					<s:checkbox id="mycheck" name="selectedFormIndices" fieldValue="%{index}" value="false" 
					onClick="toggleActionButton('selectedFormIndices', 'action:validateForms')" theme = "simple" />
				</td>
				<td><s:property value="publicId" /></td>
				<td><s:property value="version" /></td>
				<td><s:property value="longName" /></td>
				<td><s:property value="context" /></td>
				<td><s:property value="type" /></td>
				<td><div style="width: 250px;"><s:property value="protocolName" /></div></td>
				<td><s:property value="workflowStatusName" /></td>
				<td><s:property value="modules.size()" /></td>
				
			</tr>
			</s:iterator>

<!-- Button row -->
<tr>
	<td colspan="1" align="left" nowrap>
<s:submit type="image" src="/FormLoader/i/dbvalidate.gif" title="Select at least one form and you may proceed..." 
	action="validateForms" disabled="true" align="left" theme="simple" /></td>
	<td colspan="1" align="left" nowrap>
<s:submit type="image" src="/FormLoader/i/cancel.gif" action="cancelDBValidate" align="left" theme="simple"/></td>
	<td colspan="1" align="left" nowrap>
<input type="image" src="/FormLoader/i/reset.gif"  onClick="return resetAllCheckboxesByName('selectedFormIndices', 0);" /></input></td>
</tr>
		</table>
		</div> 
	</s:form>
	</s:if>

<br>
<div style="padding-left: 150px; padding-right: 150px;">
<hr>
</div>
<s:if test="parsedFormsWithErrorList.size() > 0">	
	<h3>Forms that failed XML Validation</h3>

	<s:if test="parsedFormsWithErrorList.size() >= 1">
		<div class="content">
		<table class="fileTable" 
		>
			<tr class="even">
				<th>Public Id in XML</th>
				<th>Version in XML</th>
				<th>Long Name in XML</th>
				<th>Errors</th>
			</tr>
			<s:iterator value="parsedFormsWithErrorList" id = "bean" status="status">
			<tr	class="<s:if test="#status.odd == true ">odd</s:if> <s:else>even</s:else>">
				<td><s:property value="publicId" /></td>
				<td><s:property value="version" /></td>
				<td><s:property value="longName" /></td>
				<td><s:property value="getXmlValidationErrorString()" /></td>
			</tr>
			</s:iterator>
		</table>
		</div>
	</s:if>
	</s:if>
	
	
	
<script type='text/javascript' src='js/formloader-common.js'></script>

 <script>
  $(function() {
    $( document ).tooltip();
  });
  </script>
</body>
</div>
</html>