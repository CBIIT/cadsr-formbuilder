<table width="20%" align="center" cellpadding="1" cellspacing="1" border="0" >
  <tr >
    <td>
      <a href="javascript:retrieveCDEs('retrieveQuestionCDEs')">
        <html:img src='<%=urlPrefix+"i/retrieve.gif"%>' border="0" alt="Retrieve Saved Data Elements"/> 
      </a>
    </td>
	<logic:notEmpty name="<%=CaDSRConstants.CDE_CART%>" property = "dataElements">
    <td >
      <a href="javascript:submitForm('<%=NavigationConstants.ADD_QUESTION%>')">
        <html:img src='<%=urlPrefix+"i/add_button.gif"%>' border="0" alt="Add"/>
      </a>
    </td>     
	</logic:notEmpty>
    <!--td >
      <a href="javascript:submitForm('<%=NavigationConstants.SUBSET_QUESTION_VALIDVALUES%>')">
        <html:img src='<%=urlPrefix+"i/subset_validvalues.gif"%>' border="0" alt="Subset ValidValues and Add"/>
      </a>
    </td -->            
          <td >
            <a href="javascript:submitCancelForm()"><html:img src='<%=urlPrefix+"i/cancel.gif"%>' border="0" alt="cancel"/></a>
          </td>                 
  </tr> 
  <tr >
    <td >
      &nbsp;
    </td>                          
  </tr>         
  
</table>