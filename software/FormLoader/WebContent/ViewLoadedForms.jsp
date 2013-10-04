<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>


<html>
<head>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script type='text/javascript' src='js/jquery.simplemodal.js'></script>

<title>View Loaded Forms</title>

<!-- Contact Form CSS files -->
<link type='text/css' href='css/modalview.css' rel='stylesheet' media='screen' />

<style type="text/css">
@import url(css/style.css);
</style>

<script type="text/javascript"> 
$(document).ready(function() {
	
	$('td[id="collectionView"]').click(function(){
		
		var formId = $(this).find('input').attr('id');
		//alert(formId);
		//alert($(this).find('input').attr('name'));
		var d = '<html><head><title>Collections for Form</title></head>';
		d += '<body><h4>The form [';
		d += formId;
		d += '] was loaded with the following collection(s):</h4><br>';
		d += '<table><tr class="even"><th>Collection Name</th><th>Description</th><th># of Forms</th><th>Loaded By</th><th>Loaded Date</th><tr>';
		var data = $(this).find('input').val();
		d += data;
		d += '</table></body></html>';
		$.modal(d);
      }); 
	
	$('input[type="button"]').click(function(){
		var data = this.value;
		$.modal(data);
		//$.modal('<p><b>HTML</b> elements</p>');
      });
});
</script>
    
<script type="text/javascript">  
        $(document).ready(function(){
        	$('tr[id^="parent"]').addClass("odd");
            $('tr[id="formHeader"]').show();
            $('tr[id^="child"]').hide();

            $('td[id="expandable"]').click(function(){
                $(this).parents('tr').next("tr").toggle();
              });            
        });
    </script>  
   
   
   <script type="text/javascript">  
   $(document).ready(function(){
    $("#searchInput").keyup(function () {
    //split the current value of searchInput
    var data = this.value.split(" ");
    //create a jquery object of the rows
    var jo = $("#fbody").find("tr");
    if (this.value == "") {
        jo.show();
        return;
    }
    //hide all the rows
    jo.hide();

    //Recusively filter the jquery object to get results.
    jo.filter(function (i, v) {
        var $t = $(this);
        for (var d = 0; d < data.length; ++d) {
            if ($t.is(":contains('" + data[d] + "')")) {
                return true;
            }
        }
        return false;
    })
    //show the rows that match.
    .show();
}).focus(function () {
    this.value = "";
    $(this).css({
        "color": "black"
    });
    $(this).unbind('focus');
}).css({
    "color": "#C0C0C0"
});
   });
    </script>
</head>
<div id="4b" style="padding-left: 50px; padding-right: 50px;">
<body>
<h5 class="OraTipText">You are logged in as: <s:property value="userName" /></h5>
<s:if test="forms.size() > 0">
<p>You have previously loaded <s:property value="forms.size()"/> forms. click on any form field to view collection(s) it was loaded in. <br>
To unload, check individual forms and click the Unload Forms button.
<br><br>
You may also filter the form list by typing into the filter input field.
</p>
</s:if>

<s:elseif test="forms.size() == 0">
   <h4>You don't have previously loaded form. </h4>
</s:elseif>
<s:actionerror />

<table>
<tr>
<td>Filter:
</td>
<td><input id="searchInput" value="Type To Filter">
</td>
</tr>
</table>
<div id='content'>
<s:form action="unloadForms" method="post">
<table id="fileTable">
		<tr id="formHeader" class="even">
				<th>Select</th>
				<th>Public ID</th>
				<th>Version</th>
				<th>Long Name</th>
				<th>Context</th>
				<th>Protocol Name</th>
				<th>Workflow Status</th>
				<th>Created By</th>
				<th>Modified By</th>
				<th>Collections</th>
		</tr>
<tbody id="fbody">

			<s:iterator value="forms" var="form" status="status"> 
			<tr	class="<s:if test="#status.odd == true ">odd</s:if> <s:else>even</s:else>">
				<!--  tr class="parent" id="parent-<s:property value="formSeqId" />"> -->
				<td><div style="width: 50px;"><s:checkbox name="selectedFormIds" fieldValue="%{formSeqId}" theme = "simple" /></div></td>
				<td id="expandable"><s:property value="publicId" /></td>
				<td id="expandable"><s:property value="version" /></td>
				<td id="expandable"><div style="width: 150px;"><s:property value="longName" /></div></td>
				<td id="expandable"><s:property value="context" /></td>
				<td id="expandable"><div style="width: 250px;"><s:property value="protocolName" /></div></td>
				<td id="expandable"><s:property value="workflowStatusName" /></td>
				<td id="expandable"><s:property value="createdBy" /></td>
				<td id="expandable"><s:property value="modifiedBy" /></td>
				
				<td id="collectionView">View Collection Info
				<input type="hidden" id="<s:property value="getFormIdString()" />" value='<s:property value="getCollectionsInHtmlRows()" />'>
				</td>
				
				</tr>
				
				</s:iterator>
</tbody>
			
			<tr>
		  	<td colspan="1" align="left" nowrap>
<s:submit type="image" src="/FormLoader/i/Unload-Forms.gif" method="execute" align="left" theme="simple" /></td>
</tr>
		</table>
</s:form>
</div>

</body>
</div>
</html>