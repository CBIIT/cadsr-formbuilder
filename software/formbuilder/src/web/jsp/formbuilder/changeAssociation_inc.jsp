<%--L
  Copyright Oracle Inc, ScenPro Inc, SAIC-F

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/cadsr-formbuilder/LICENSE.txt for details.
L--%>

<table width="20%" align="center" cellpadding="1" cellspacing="1" border="0" >
  <tr >
    <td>
      <a href="javascript:retrieveCDEs('retrieveAssociateCDEs')">
        <html:img src='<%=urlPrefix+"i/refresh.gif"%>' border="0" alt="Refresh Saved Data Elements"/> 
      </a>
    </td>
	<logic:notEmpty name="<%=CaDSRConstants.CDE_CART%>" property = "dataElements">
    <td >
      <a href="javascript:submitUpdateForm('<%= NavigationConstants.CHANGE_DE_ASSOCIATION %>')">
        <html:img src='<%=urlPrefix+"i/update_button.gif"%>' border="0" alt="Update"/>
      </a>
    </td>           
	</logic:notEmpty>
    <td >
      <html:link action='<%= "/cancelAction?" + NavigationConstants.METHOD_PARAM + "=" + NavigationConstants.GET_MODULE_TO_EDIT %>'>
        <html:img src='<%=urlPrefix+"i/cancel.gif"%>' border="0" alt="Cancel"/>
      </html:link>             
    </td>                
  </tr> 
  <tr >
    <td >
      &nbsp;
    </td>                          
  </tr>         
  
</table>