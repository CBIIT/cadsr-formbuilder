<script LANGUAGE="Javascript">
<!---
function actionConfirm(message, url){
if(confirm(message)) location.href = url;
}
// --->
</SCRIPT>
      <table width="80%" align="center" cellpadding="1" cellspacing="1" border="0" >
        <tr >
		<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
         <td>
            <a href="javascript:submitFormToSave('<%=NavigationConstants.SAVE_FORM%>')">
                <html:img src='<%=urlPrefix+"i/save.gif"%>' border="0" alt="Save"/>
             </a> 
          </td>          
         <td>
            <a href="javascript:submitChanges('<%=NavigationConstants.CHECK_CHANGES_MODULE_EDIT%>','manageCS')">
               <img src="<%=urlPrefix%>i/classifications.gif" border=0 alt="Manage Classifications">
            </a>              
           </td>
         <td>
            <a href="javascript:submitChanges('<%=NavigationConstants.CHECK_CHANGES_MODULE_EDIT%>','manageRefDoc')">
               <img src="<%=urlPrefix%>i/refdocs.gif" border=0 alt="Manage Reference Documents">
            </a>              
          </td>                                  
          <td > 
            <a href="javascript:submitFormToSave('<%=NavigationConstants.CHECK_CHANGES_DONE%>')">
               <img src=<%=urlPrefix%>i/backButton.gif border=0 alt="Done">
            </a>              
          </td> 
                         
<!--GF32971 Removed Delete Button, -D,An, 20130919 -->

          <td > 
            <html:link action='<%="/cancelFormEditAction?"+NavigationConstants.METHOD_PARAM+"="+NavigationConstants.CANCEL_FORM_EDIT%>'>
              <html:img src='<%=urlPrefix+"i/cancel.gif"%>' border="0" alt="Cancel"/>
	          </html:link>               
          </td>               
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
<%--GF29128 remove add form cart for viewer. D.An, 20130815 --%>          
<td class="noneViewerAddFormCart" style="display:none" Title="Add this form to Form Cart queue.">          
				<html:link action='<%= "/addFormToCartFromEdit?method=addFormToCart" %>'
 	       		paramId = '<%= "checkedFormIds" %>'
 				paramName="<%=FormConstants.CRF%>" paramProperty="formIdseq"
 				>
			Add Form to Cart
		      </html:link>
</td>
        </tr> 
                
      </table>