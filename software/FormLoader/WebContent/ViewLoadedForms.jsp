<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>


<html>
<head>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>

<title>View Loaded Forms</title>

<style type="text/css">
        body { font-family:Arial, Helvetica, Sans-Serif; font-size:0.8em;}
        #report { border-collapse:collapse;}
        #report h4 { margin:0px; padding:0px;}
        #report img { float:right;}
        #report ul { margin:10px 0 10px 40px; padding:0px;}
        #report th { background:#fff url(i/header_bkg4.png) repeat-x scroll center left; color:#fff; padding:7px 15px; text-align:left;}
        #report td { background:#fff none repeat-x scroll center left; color:#000; padding:7px 15px; }
        #report tr.odd td { background:#fff url(i/row_bkg.png) repeat-x scroll center left; cursor:pointer; }
        #report div.arrow { background:transparent url(i/arrows.png) no-repeat scroll 0px -16px; width:16px; height:16px; display:block;}
        #report div.up { background-position:0px 0px;}
    </style>

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

</head>
<body>
<h5 class="OraTipText">You are logged in as: <s:property value="userName" /></h5>
<s:if test="forms.size() > 0">
<p>You have previously loaded <s:property value="forms.size()"/> forms. You may use the input fields to narrow down the list.
Click on a form to view the collection(s) it was loaded with. <br> To unload, check individual forms and click the Unload Forms button.
</p>
</s:if>

<s:elseif test="forms.size() == 0">
   <h4>You don't have previously loaded form. </h4>
</s:elseif>
<s:actionerror />

<s:form action="unloadForms" method="post">

<table id="report">
		<tr id="formHeader">
				<th>Select</th>
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
				<tr class="parent" id="parent-<s:property value="formSeqId" />">
				<td><s:checkbox name="selectedFormIds" fieldValue="%{formSeqId}" theme = "simple" /></td>
				<td id="expandable"><s:property value="publicId" /></td>
				<td id="expandable"><s:property value="version" /></td>
				<td id="expandable"><div style="width: 150px;"><s:property value="longName" /></div></td>
				<td id="expandable"><s:property value="context" /></td>
				<td id="expandable"><div style="width: 250px;"><s:property value="protocolName" /></div></td>
				<td id="expandable"><s:property value="workflowStatusName" /></td>
				<td id="expandable"><s:property value="createdBy" /></td>
				<td id="expandable"><s:property value="modifiedBy" /></td>
				</tr>
				
				<tr class="child-<s:property value="formSeqId" />" style="display:none;">
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td colspan=7>
				<table>
		
				<tr>
					<th>Collection Name</th>
					<th>Description</th>
					<th># of Forms</th>
					<th>Loaded By</th>
					<th>Loaded Date</th>
				</tr>
				<s:iterator value="belongToCollections">
				<tr>			    
				<td align="center"><s:property value= "name"/></td>
				<td align="center"><s:property value="description" /></td>
				<td align="center"><s:property value="forms.size()" /></td>
				<td align="center"><s:property value="createdBy" /></td>
				<td align="center"><s:property value="dateCreated" /></td>
			</tr>	
				</s:iterator>
				
				</table>
				</td>
				</tr>
				</s:iterator>

			
			<tr>
		  	<td colspan="1" align="left" nowrap>
<s:submit type="image" src="/FormLoader/i/Unload-Forms.gif" method="execute" align="left" theme="simple" /></td>
</tr>
		</table>
		
 
</s:form>

</body>
</html>