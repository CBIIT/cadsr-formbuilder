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
<title>Upload File</title>
<sx:head />
</head>
 
<body>
<h2>Load Form Collection</h2>
<s:actionerror />

<table>
<s:form action="xmlFileResultAction" method="post" namespace="/" enctype="multipart/form-data">
<tr>
 </tr>
<tr><td colspan="2" align="left" nowrap>
   <s:file name="formFile" label="Form Collection XML File"  size="40" />
   </td></tr>
    <tr><td colspan="2" align="left" nowrap>
   <s:textfield key="Form Collection Name" name="collection_name" /></td></tr>
   <tr><td colspan="2" align="left" nowrap>
   <s:textarea key="Form Collection Description" name="description"/></td></tr>
    	  <tr>
	  	<td colspan="1" align="left" nowrap>
<s:submit type="image" src="/FormLoader/i/validate-XML.gif" method="execute" align="left" theme="simple" /></td>
	  	<td colspan="1" align="left" nowrap>
<s:submit type="image" src="/FormLoader/i/cancel.gif" method="cancel" align="left" theme="simple"/></td>
	  	<td colspan="1" align="left" nowrap>
<s:submit type="image" src="/FormLoader/i/reset.gif" method="reset" align="left" theme="simple"/></td>
  </tr>
</s:form>
</table>
</body>
</html>