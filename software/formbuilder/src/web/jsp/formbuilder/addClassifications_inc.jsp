<%--L
  Copyright Oracle Inc, ScenPro Inc, SAIC-F

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/cadsr-formbuilder/LICENSE.txt for details.
L--%>

<table width="20%" align="center" cellpadding="1" cellspacing="1" border="0" >
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr >
    <td >
      <html:image src='<%=urlPrefix+"i/save.gif"%>' border="0" alt="Save"/>
    </td>           
    <td >
      <html:link action='<%= "/cancelAction?" + NavigationConstants.METHOD_PARAM + "=" + NavigationConstants.GO_TO_MANAGE_CLASSIFICATIONS %>'>
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