<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<!-- code below is never executed -->
<html>
<head>
<title>Login Error</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<LINK REL=STYLESHEET TYPE="text/css" HREF="<%=request.getContextPath()%>/css/ui-lightness/jquery-ui-1.10.3.custom.min.css">
<script src="./js/jquery-1.9.1.js"></SCRIPT>
<script src="./js/jquery-ui-1.10.3.custom.min.js"></SCRIPT>
<script src="./js/jquery.cookie.js"></SCRIPT>

<script language=javascript> 
//if (parent.frames[1]) 
//  parent.location.href = self.location.href; 

var un = $.cookie('FormbuilderUsername');
var nun = $.cookie('newFormbuilderUsername');

var expDate = new Date(); 
expDate.setTime(expDate.getTime() + (1 * 60 * 1000));

$(document).ready(function()
{
  //// Go to login page
		un = "LoginAsGuest";
  
		$.cookie( 'FormbuilderUsername', un, { expires: expDate } );	
		$.cookie( 'newFormbuilderUsername', un, { expires: expDate } );	
});
</script>
 
</head>

<body bgcolor="#FFFFFF" text="#000000">
You are not authenticated to use this web application 

<br>
<br>

<a href="<%= "formSearchAction.do"%>"> Retry Login again</a>

</body>
</html>
