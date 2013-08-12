<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<!-- code below is never executed -->
<html>
<head>
<title>Login Error</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script language=javascript> 
//if (parent.frames[1]) 
//  parent.location.href = self.location.href; 
</script> 
</head>

<body bgcolor="#FFFFFF" text="#000000">
You are not authenticated to use this web application 

<br>
<br>

<a href="<%= "formSearchAction.do"%>"> Retry Login again</a>

</body>
</html>
