<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>


<html>
<head>
<LINK REL=STYLESHEET TYPE="text/css" HREF="<%=request.getContextPath()%>/css/blaf.css">

<LINK REL=STYLESHEET TYPE="text/css" HREF="<%=request.getContextPath()%>/css/ui-lightness/jquery-ui-1.10.3.custom.min.css">
<script src="./js/jquery-1.9.1.js"></SCRIPT>
<script src="./js/jquery-ui-1.10.3.custom.min.js"></SCRIPT>
<script src="./js/jquery.cookie.js"></SCRIPT>

<title>Search XML Files</title>
<LINK REL=STYLESHEET TYPE="text/css" HREF="<%=request.getContextPath()%>/css/blaf.css">
<style type="text/css">
@import url(css/style.css);
</style>
<sx:head />
</head>
<body>
<h5 class="OraTipText">You are logged in as: <s:property value="username" /></h5>
<h2>Search Form Collection</h2>
<s:actionerror />

<s:form action="search-form" method="post" enctype="multipart/form-data">

  <table>
    <tr>
        <td><b>Form Collection Name:</b></td>
        <td>
          <input type="text" id="collection_name" name="collection_name" value="" size ="20"> 
        </td>
        <td>
           <s:submit type="image" src="i/search_light.gif" method="search" theme="simple" />
        </td>
    </tr>
    <tr>
        <td><b>Form Collection Description:</b></td>
        <td>
          <input type="text" id="description" name="description" value="" size ="20"> 
        </td>
    </tr>       
   <tr>
      <td colspan="1" align="left" nowrap><b>Creation Date:</b></td>
   </tr>
 </table> 
 
 <table>  
   <tr>   
	<td colspan="2" align="center" nowrap><b>From: </b><input type="Text" id="from" maxlength="25" size="30">
	     <a href="javascript:NewCal('from','ddmmmyyyy')">
            <script src="js/datetimepicker.js" type="text/javascript"></script>
                <img src="i/cal.gif" width="16" height="16" border="0">
         </a>
    </td>
    
    <td colspan="2" align="center" nowrap><b>To: </b><input type="Text" id="to" maxlength="25" size="30">
	     <a href="javascript:NewCal('to','ddmmmyyyy')">
            <script src="js/datetimepicker.js" type="text/javascript"></script>
                <img src="i/cal.gif" width="16" height="16" border="0">
         </a>
    </td> 
   </tr>
 </table>
 
 <table>
   <tr> 
	  <td colspan="1" align="center" nowrap><a href="javascript:submitForm()"><img src=<%=request.getContextPath()%>/i/search.gif border=0 alt="Logon"></a></td>
      <td colspan="1" align="center" nowrap><a href="javascript:clearForm()"><img src=<%=request.getContextPath()%>/i/reset.gif border=0 alt="Clear"></a></td>
     
   </tr>
 </table> 

<%
  String colname = (String) request.getAttribute("name");
  String des = (String) request.getAttribute("description");
  String crtedby = (String) request.getAttribute("createdBy");
  String dtcrt = (String) request.getAttribute("dateCreated");
%>
 
 <s:if test="collectionList.size() >= 1">
   		<div class="content">
		<table class="fileTable" cellpadding="5px">
			<tr class="even">
				<th>Select</th>
				<th>Collection Name</th>
				<th>Description</th>
				<th># of Forms</th>
				<th>Loaded By</th>
				<th>Loaded Date</th>
			</tr>
            <s:iterator value="collectionList" id = "bean" status="status">
            
			<tr	class="<s:if test="#status.odd == true ">odd</s:if> <s:else>even</s:else>">
			    <td>
			      <s:checkbox name="checkboxes[%{#status.index}]" theme = "simple" />
			    </td>
				<td><s:property value= "colname"/></td>
				<td><s:property value="des" /></td>
				<td><s:property value="???" /></td>
				<td><s:property value="crtedby" /></td>
				<td><s:property value="dtcrt" /></td>
			</tr>
			</s:iterator>
		</table>
		</div>
	<s:submit value="View Forms in Collections"/>  	
 </s:if> 
 
 
</s:form>

</body>
</html>