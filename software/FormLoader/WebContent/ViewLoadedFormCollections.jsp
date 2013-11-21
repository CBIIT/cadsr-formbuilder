<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css">

<title>View Loaded Form Collections</title>

<!-- Contact Form CSS files -->
<link type='text/css' href='css/modalview.css' rel='stylesheet' media='screen' />

<style type="text/css">
@import url(css/style.css);
</style>

 <script>
  $(function() {
    $( document ).tooltip();
  });
  </script>
  
</head>
<div style="padding-left: 50px; padding-right: 50px;">

<body>
<h2>View Loaded Form Collections</h2>

<s:if test="collectionList.size() > 0">
<p>You have previously loaded <s:property value="collectionList.size()"/> Collections. Click on any collection field to view forms in a Collection.<br>
To Unload,  select forms in <b>Collection View</b> or <b>Loaded Form View</b> tab, and click the <b>Unload Forms</b> button.
<br><br>
Only forms eligible for unloading have checkboxes available for selection. <a href="#" title="A form is eligible for unload if
1. it was successfully loaded previously into caDSR database
2. its current modified date is the same as its loaded date

Unload means the form will have a new workflow status RETIRED UNLOADED but the form itself will not be deleted from caDSR database." >More...</a>
</p>
</s:if>

<s:elseif test="collectionList.size() == 0">
   <h4>You haven't loaded any Collection previously. </h4>
</s:elseif>
<s:actionerror />

<table><tr>
<td>Filter:</td>
<td><input type="text" id="filter" placeholder="Type to search"></td> 
<td>Filter the Collection or Form list by typing into the filter input field</td>
</tr></table>

<div id="tabs">
<ul>
<li><a href="#Collection_View">Collection View</a></li>
<li><a href="#Form_View">Loaded Form View</a></li>
</ul>

 <div id="Collection_View">
  	<s:form action="unloadForms" theme="simple" method="post">
  	<div id="content">
		<table id="collectionsTab">
		
			<tr id="collTitle" class="even">
				<th>Collection Name</th>
				<th>Description</th>
				<th># of Forms</th>
				<th>Loaded By</th>
				<th>Loaded Date</th>
			</tr>
			
            <s:iterator value="collectionList" id = "bean" status="status">
            
			<tr	class="<s:if test="#status.odd == true ">odd</s:if> <s:else>even</s:else>" id="parent-<s:property value="id" />"> 			    
				<td id="expandable"><div style="width: 250px;"><s:property value= "getNameWithRepeatIndicator()"/></div></td>
				<td id="expandable"><div style="width: 250px;"><s:property value="description" /></div></td>
				<td id="expandable" align="center"><div style="width: 100px;"><s:property value="forms.size()" /></div></td>
				<td id="expandable" ><div style="width: 150px;"><s:property value="createdBy" /></div></td>
				<td id="expandable"><div style="width: 250px;"><s:date name="dateCreated" format="dd/MM/yyyy h:mm:ss a"/></div></td>
			</tr>
			<!-- row for form table -->
			<tr class="child-<s:property value="id" />" id="child-<s:property value="id" />" style="display:none;">
			<td colspan=5 bgcolor="#FFE6CE" >
			<table>
			<tr>
				<th>&nbsp;</th>
				<th>Public ID</th>
				<th>Version</th>
				<th>Long Name</th>
				<th>Context</th>
				<th>Protocol Name(s)</th>
				<th>Modified By</th>
				<th>Modified Date</th>
				<th>Load/Unload Date</th>
				<th>Load Status</th>
				
				</tr>
				<s:iterator value="forms" var="form" status="status"> 
				<tr>
				<s:if test="isUnloadable() == true ">
				<td><div style="width: 50px;"><s:checkbox name="selectedFormIds" fieldValue="%{formSeqId}" theme = "simple" /></div></td>
				</s:if>
				<s:else><td><div style="width: 50px;">&nbsp;</div></td></s:else>
				
				<td><s:property value="publicId" /></td>
				<td><s:property value="version" /></td>
				
				
				<td><div style="width: 150px;"><s:property value="longName" /></div></td>
				<td><s:property value="context" /></td>
				<td><div style="width: 250px;"><s:property value="protocolName(s)" /></div></td>
				<td><s:property value="modifiedBy" /></td>
				
				<td><s:property value="modifiedDate" /></td>
				<td><s:property value="loadUnloadDate" /></td>
				
				<td><s:property value="getLoadTypeLoadStatusString()" /></td>
				
				</tr>
				</s:iterator>
			</table>
			</td>
			</tr>			
			
			</s:iterator>
			<tr id="buttonRow">
		  	<td colspan="1" align="left" nowrap>
<s:submit type="image" src="/FormLoader/i/Unload-Forms.gif" method="execute" align="left" theme="simple" /></td>
<td colspan="1" align="left" nowrap>
<input type="image" src="/FormLoader/i/reset.gif"  onClick="return resetAllCheckboxesByName('selectedFormIds', 0);" /></input>
</td>
</tr>
		</table>
		</div>
	</s:form>
		
		</div> <!--  End Collection_View -->
		
<div id="Form_View">
<s:form action="unloadForms" theme="simple">
  	<div id="content">
<table id="loadedFormsTab">
		<tr class="even">
				<th>Select</th>
				<th>Public ID</th>
				<th>Version</th>
				<th>Long Name</th>
				<th>Context</th>
				<th>Protocol Name(s)</th>
				<th>Workflow Status</th>
				<th>Created By</th>
				<th>Modified By</th>
				<th>Load Type</th>
				<th>Collections</th>
		</tr>

			<s:iterator value="unloadableForms" var="form" status="status"> 
			<tr	class="<s:if test="#status.odd == true ">odd</s:if> <s:else>even</s:else>">
				
				
				<td><div style="width: 50px;"><s:checkbox name="selectedFormIds" fieldValue="%{formSeqId}" theme = "simple" /></div></td>				
				<td><s:property value="publicId" /></td>
				<td><s:property value="version" /></td>
				<td><div style="width: 150px;"><s:property value="longName" /></div></td>
				<td><s:property value="context" /></td>
				<td><div style="width: 250px;"><s:property value="protocolName" /></div></td>
				<td><s:property value="workflowStatusName" /></td>
				<td><s:property value="createdBy" /></td>
				<td><s:property value="modifiedBy" /></td>
				<td><s:property value="loadType" /></td>
				<td id="collectionView"><a href="#">View Collection(s)</a>
				<input type="hidden" id="<s:property value="getFormIdString()" />" value='<s:property value="getCollectionsInHtmlRows()" />'>
				</td>
				
				</tr>
				
				</s:iterator>
<tr id="buttonRow">
	<td colspan="1" align="left" nowrap>
	<s:submit type="image" src="/FormLoader/i/Unload-Forms.gif" method="execute" align="left" theme="simple" /></td>
	<td colspan="1" align="left" nowrap>
	<input type="image" src="/FormLoader/i/reset.gif"  onClick="return resetAllCheckboxesByName('selectedFormIds', 0);" /></input></td>
</tr>
		</table>
		</div>
</s:form>

</div> <!-- end Form_View -->

</div>  <!--  End Tabs -->

<script src="http://ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.min.js"></script>
<script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.2/jquery-ui.min.js"></script>
  
<script type='text/javascript' src='js/jquery.simplemodal.js'></script>
<script type='text/javascript' src='js/filter.js'></script>
  
  <script type="text/javascript"> 
$(document).ready(function() {
	
	$('td[id="collectionView"]').click(function(){
		
		var formId = $(this).find('input').attr('id');
		//alert(formId);
		var d = '<html><head><title>Collections for Form</title></head>';
		d += '<body><h4>The Form [';
		d += formId;
		d += '] was loaded with the following collection(s):<br><br>';
		d += 'The most recent Collection the Form was loaded in will be used to unload the Form</h4><br><br>';
		d += '<table><tr class="even"><th>Collection Name</th><th>Description</th><th># of Forms</th><th>Loaded By</th><th>Loaded Date</th><th>Modified Date</th><th>Eligible for Unload</th><tr>';
		var data = $(this).find('input').val();
		//alert(data);
		d += data;
		d += '</table></body></html>';
		$.modal(d);
      }); 
	
	
});
</script>
    
<script type="text/javascript">  
        $(document).ready(function(){
        	$('tr[id^="parent"]').addClass("odd");
            $('tr[id="formHeader"]').show();
            $('tr[id^="child"]').hide();

            $('td[id="expandable"]').click(function(){
            	//alert($(this).parents('tr').attr('id'));
                $(this).parents('tr').next("tr").toggle();
                //$('tr[id="child-EA0EFF9D-55ED-73E0-E040-BB8921B66692"]').show();
              });            
        });
    </script>  
   
  <script type="text/javascript">  
  $(document).ready(function() {
    $( "#tabs" ).tabs();
  });
  </script>
<script type='text/javascript' src='js/formloader-common.js'></script>
</body>
</div>
</html>