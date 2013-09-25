<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>


<html>
<head>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<meta name="viewport" content="width=device-width">

<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
  <script src="http://code.jquery.com/jquery-1.9.1.js"></script>
  <script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
  <link rel="stylesheet" href="/resources/demos/style.css" />
  <script>
  $(function() {
    $( "#accordion" ).accordion();
  });
  </script>
    

<title>Search XML Files</title>

<script type="text/javascript">
jQuery(document).ready(function() {
	$('tr.parent')
	.css("cursor","pointer")
	.attr("title","Click to expand/collapse")
	.click(function(){
		$(this).siblings('.child-'+this.id).toggle();
	});
	
	$('tr[@class^=child-]').hide().children('td');});

</script>

<style type="text/css">
@import url(css/levelledTableStyle.css)
</style>

</head>
<body>
<h5 class="OraTipText">You are logged in as: <s:property value="userName" /></h5>
<s:if test="collectionList.size() > 0">
<h4>You have previously loaded <s:property value="collectionList.size()"/> form collections. You may use the input fields to narrow down the list.<br>
Click on a collection to view its forms. To unload, check individual forms and click the unload form button.
</h4>
</s:if>

<s:elseif test="collectionList.size() == 0">
   <h4>You don't have previously loaded form collections. </h4>
</s:elseif>
<s:actionerror />

<s:form action="unloadForms" method="post">

  <table>
    <tr>
        <td><b>Form Collection Name:</b></td> 
        <td><input type="Text" id="collection_name" maxlength="25" size="20">
	     <a href="javascript:SearchCollections()">            
                <img id="searchLight"  src="i/search_light.gif" width="16" height="16" border="0">
             <script type="text/javascript">
             $(function(){
          	   $(collection_name).change(function(){
          		   $("#collectiontable td.colname:contain('" + $(this).val() + "')").parent().show();
          		   $("#collectiontable td.colname:not(:contains('" + $(this).val() + "'))").parent().hide();
          	   });
          	   
             });
               </script>   
         </a>
    	</td>
    	<td>&nbsp; </td>
        
    </tr>
    <tr>
        <td><b>Form Collection Description:</b></td>
        <td>
          <input type="text" id="description" name="description" value="" size ="20"> 
        </td>
        <td>&nbsp; </td>
    </tr>       
   <tr>
      <td colspan="1" align="left" nowrap><b>Creation Date:</b></td>
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
	  <td colspan="1" align="center" nowrap><a href="javascript:submitForm()">
	  <img src=<%=request.getContextPath()%>/i/refresh.gif border=0 alt="Logon"></a></td>
      <td colspan="1" align="center" nowrap><a href="javascript:clearForm()">
      <img src=<%=request.getContextPath()%>/i/reset.gif border=0 alt="Clear"></a></td> 
   </tr>
 </table> 

		<table class="level1Table" id="collectiontable">
		
			<tr>
				<th>Select</th>
				<th>Collection Name</th>
				<th>Description</th>
				<th># of Forms</th>
				<th>Loaded By</th>
				<th>Loaded Date</th>
			</tr>
			
            <s:iterator value="collectionList" id = "bean" status="status">
            <tbody>
			<!--  tr	class="<s:if test="#status.odd == true ">odd</s:if> <s:else>even</s:else>" id="<s:property value="id" />"> -->
			<tr class="parent" id="<s:property value="id" />" >
			    <td align="center">
			      <s:checkbox name="" theme = "simple" />
			    </td>
			    
				<td align="center"><s:property value= "name"/></td>
				<td align="center"><s:property value="description" /></td>
				<td align="center"><s:property value="forms.size()" /></td>
				<td align="center"><s:property value="createdBy" /></td>
				<td align="center"><s:property value="dateCreated" /></td>
			</tr>			
			
			<tr class="child-<s:property value="id" />" style="display:none;">
			<td>&nbsp;</td>
			
			<td colspan=6>
			<table class="level2Table">
			<tr class="even">
				<th>&nbsp;</th>
				<th>Public ID</th>
				<th>Version</th>
				<th>Long Name</th>
				<th>Context</th>
				<th>Protocol Name</th>
				<th>Workflow Status</th>
				<th>Created By</th>
				<th>Modified By</th>
				</tr>
				
				<s:iterator value="forms" var="form" status="status"> 
				<tr>
				<td><s:checkbox name="selectedFormIds" fieldValue="%{formSeqId}" theme ="simple" /></td>
				
				<td><s:property value="publicId" /></td>
				<td><s:property value="version" /></td>
				<td><div style="width: 150px;"><s:property value="longName" /></div></td>
				<td><s:property value="context" /></td>
				<td><div style="width: 250px;"><s:property value="protocolName" /></div></td>
				<td><s:property value="workflowStatusName" /></td>
				<td><s:property value="createdBy" /></td>
				<td><s:property value="modifiedBy" /></td>
				</tr>
				</s:iterator>
			</table>
			</td>
			</tr>
			</tbody>
			</s:iterator>
			
			<tr>
		  	<td colspan="1" align="left" nowrap>
<s:submit type="image" src="/FormLoader/i/Unload-Forms.gif" method="execute" align="left" theme="simple" /></td>
</tr>
		</table>
		</div>
 
</s:form>

</body>
</html>