<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>
<html>
<head>
	<title>Search Uploaded XML Files</title>
<s:head />
<style type="text/css">
@import url(css/style.css);
</style>
<sx:head />
</head>
<body>
	<h2>Search Uploaded XML Files</h2>

<s:form action="searchUploadedXMLFiles" > 
    	<sx:autocompleter size="1" list="uploadedFilenames" name="filename" label = "File Name:">
    	</sx:autocompleter>  	
   		<sx:autocompleter size="1" list="descriptionList" name="description" label ="Description:">
    	</sx:autocompleter>
   		<s:textfield name="fromDate" size="20" label="Uploaded Date: from" /> 
   	    <s:textfield name="toDate" size="20"  label = "to" /> 
<s:submit value="Search"/>  
</s:form>	 

<br>
 	
	<s:if test="xmlFileList.size() >= 1">
	
	<s:form action="selectUploadedXMLFiles" theme = "simple" method="post"> 
		<div class="content">
		<table class="fileTable" cellpadding="5px">
			<tr class="even">
				<th>File Name</th>
				<th>Description</th>
				<th>Version</th>
				<th># of Forms</th>
				<th>Uploaded By</th>
				<th>Uploaded Date</th>
				<th>Status</th>
				<th>Select</th>
			</tr>
			<s:iterator value="xmlFileList" id = "bean" status="fileStatus">
			<tr	class="<s:if test="#fileStatus.odd == true ">odd</s:if> <s:else>even</s:else>">
				<td><s:property value="filename" /></td>
				<td><s:property value="description" /></td>
				<td><s:property value="version" /></td>
				<td><s:property value="numForms" /></td>
				<td><s:property value="uploadedBy" /></td>
				<td><s:property value="uploadedDate" /></td>
				<td><s:property value="status" /></td>
				<td>
					<s:checkbox name="checkboxes[%{#fileStatus.index}]" theme = "simple" />
					<s:hidden name="fileids[%{#fileStatus.index}]" value = "%{#bean.fileid}" />
					
				</td>
			</tr>
			</s:iterator>
		</table>
		</div>
	<s:submit value="View Uploaded Forms"/>  
	</s:form>
	</s:if>
</body>
</html>