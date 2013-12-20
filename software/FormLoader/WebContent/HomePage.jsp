<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<title>Form Loader</title>
<LINK REL=STYLESHEET TYPE="text/css" HREF="<%=request.getContextPath()%>/css/blaf.css">
</head>

<div style="padding-left: 50px; padding-right: 50px;">
<body>
<br>
<br>
    <b>Form Loader</b>
    <br>
	Form Loader allows users to load form collections, search for previously loaded form collections, and unload previously loaded form collections.
    
    <p>


<p>
<table>
<tr>
<td>
<s:form action="uploadForm" theme = 'css_xhtml' > 
<s:submit type="image" src="/FormLoader/i/load.jpg" method="execute" alt="Load Forms" align="left" />
</s:form>
</td>
<td>
<a href="<s:url action="uploadForm"/>"><s:text name="<b><u>Load Form Collection</u></b><br>Load a form collection in XML format based on the Form<br>Collection XSD."></s:text></a>



</td></tr>
<tr></tr>
<tr></tr>
<tr></tr>
<tr></tr>
<tr>
<td>
<s:form action="searchFormCollections" theme = 'css_xhtml'>   
<s:submit type="image" src="/FormLoader/i/unload.jpg" method="execute" alt="Unload Forms" align="left" />
</s:form>
</td>
<td><a href="<s:url action="searchFormCollections"/>">
<s:text name="<b><u>View/Unload Form Collections</u></b><br>View previously loaded Form Collections and unload forms<br>within a Form Collection."></s:text></a>
</td></tr>
</table>
</body>
</div>
</html>
