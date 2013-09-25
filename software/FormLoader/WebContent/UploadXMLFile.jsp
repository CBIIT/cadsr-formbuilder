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
<title>Upload XML File</title>
<LINK REL=STYLESHEET TYPE="text/css" HREF="<%=request.getContextPath()%>/css/blaf.css">
<sx:head />
</head>
 
<body>
<h2>Load Form Collection</h2>
<s:actionerror />
<table>
<s:form action="xmlFileUpload" method="post" enctype="multipart/form-data">
<tr><td align="left">
   <!-- <s:textfield key="Form Collection XML File" name="form_collection" /> -->
   <s:file name="upload" label="Form Collection XML File" cols="20" rows="1"  /></td></tr>
   <tr><td align="left">
   <s:textfield key="Form Collection Name" name="collectionName"  /></td></tr>
   <tr><td align="left">
   <s:textarea key="Form Collection Description" name="description" cols="20" rows="5" /></td></tr>
    	  <tr>
	  	<td colspan="1" align="left" nowrap>
<s:submit type="image" src="/FormLoader/i/validate-XML.gif" action="xmlFileUpload" align="left" theme="simple" /></td>
	  	<td colspan="1" align="left" nowrap>
<s:submit type="image" src="/FormLoader/i/cancel.gif" action="cancelFileUpload" onclick="this.form.reset();" align="left" theme="simple"/></td>
	  	<td colspan="1" align="left" nowrap>
<s:submit type="image" src="/FormLoader/i/reset.gif" onclick="this.form.reset();" align="left" theme="simple"/></td>
  </tr>
</s:form>
</table>
</body>
</html>