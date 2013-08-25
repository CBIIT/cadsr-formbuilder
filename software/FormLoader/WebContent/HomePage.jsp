<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<title>Form Loader</title>
<LINK REL=STYLESHEET TYPE="text/css" HREF="<%=request.getContextPath()%>/css/blaf.css">
</head>

<body>
    <h5 class="OraTipText">You are logged in as: <s:property value="username" /></h5>
    <h3 class="OraTipText"><b>Form Loader</b></h3>
	<h5 class="OraTipText">Form Loader allows users to load form collections, search for previously loaded form collections, and unload previously loaded form collections.</h5>
    
    <p>


<p>
<table>
<tr>
<td>
<s:form action="upload-form" theme = 'css_xhtml' > 
<s:submit type="image" src="/FormLoader/i/load_forms.jpg" method="execute" alt="Load Forms" align="left" />
</s:form>
</td>
<td>
<s:text name="<b><u>Load Form Collection</u></b><br>Load a form collection in XML format based on the Form<br>Collection XSD."></s:text>
</td></tr>
<tr></tr>
<tr></tr>
<tr></tr>
<tr></tr>
<tr>
<td>
<s:form action="search-form" theme = 'css_xhtml'>   
<s:submit type="image" src="/FormLoader/i/unload_forms.jpg" method="execute" alt="Unload Forms" align="left" />
</s:form>
</td>
<td>
<s:text name="<b><u>Unload Forms and Form Collection</u></b><br>Search previously loaded form collections and unload a<br>previously loaded form collection or a form within a form<br>collection."></s:text>
</td></tr>
</table>
</body>
</html>
