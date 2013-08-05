<%--L
  Copyright Oracle Inc, ScenPro Inc, SAIC-F

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/cadsr-formbuilder/LICENSE.txt for details.
L--%>

<table width="20%" align="center" cellpadding="1" cellspacing="1" border="0" >
  <tr >
    <td >
      &nbsp;
    </td>                          
  </tr>         
  <tr >
    <td >
      <html:link action='<%= "/gotoAddClassifications?" + NavigationConstants.METHOD_PARAM + "=" + NavigationConstants.GO_TO_ADD_CLASSIFICATIONS %>'>
        <html:img src='<%=urlPrefix+"i/add_button.gif"%>' border="0" alt="Add"/>
      </html:link>
    </td>           

    <td >
      <html:link action='<%= "/cancelAction?" + NavigationConstants.METHOD_PARAM + "=" + NavigationConstants.GET_FORM_TO_EDIT %>'>
        <html:img src='<%=urlPrefix+"i/backButton.gif"%>' border="0" alt="Done"/>
      </html:link>             
    </td>                
  </tr> 
  <tr >
    <td >
      &nbsp;
    </td>                          
  </tr>         
  
</table>