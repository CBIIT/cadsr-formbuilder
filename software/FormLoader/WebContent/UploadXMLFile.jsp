<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>
<html>
<head>
<title>Upload XML File</title>
<sx:head />
</head>
 
<body>
<h2>Upload a XML File defining Forms</h2>
<s:actionerror />
<s:form action="xmlFileUpload" method="post" enctype="multipart/form-data">
    <s:file name="xmlFile" label="XML File" />
    <s:submit value="Upload" align="center" />
</s:form>
</body>
</html>