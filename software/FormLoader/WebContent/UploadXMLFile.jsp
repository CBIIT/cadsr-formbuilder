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
<div style="padding-left: 50px; padding-right: 50px;">
<h2>Load Form Collection</h2>
<s:if test="hasActionErrors()">
   <div class="errors">
      <s:actionerror/>
   </div>
</s:if>

<s:form action="xmlFileUpload" id="xmlFileUpload" method="post" enctype="multipart/form-data">
<div id="content">
<table>
<tr><td align="left">
   <s:file name="upload" label="Form Collection XML File" required="true" cols="20" rows="1"  /></td>
   </tr>
   <tr/>
   <tr><td/><td colspan=2>You may download and review the <a href="file/FormLoaderv17.xsd" target="_blank">XSD</a> <br>against which Form Loader will validate your collection XML.</td></tr>
  <tr></tr>
  <tr></tr>
  <tr></tr>
    	  <tr>
	  	<td colspan="1" align="left" nowrap >
<s:submit type="image" src="/FormLoader/i/validate-XML.gif" align="left" theme="simple" /></td>
	  	<td colspan="1" align="left" nowrap >
<s:submit type="image" src="/FormLoader/i/cancel.gif" action="cancelFileUpload" align="left" theme="simple"/>
<!--  input type="image" id="cancel" src="/FormLoader/i/cancel.gif" value="Cancel" onclick="javascript:cancelButton(); return true;"/>-->
</td>
	  <td></td>
  </tr>
  </table>
  </div>
</s:form>

</div>



</body>

</html>