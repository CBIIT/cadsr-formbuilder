<%--L
  Copyright Oracle Inc, ScenPro Inc, SAIC-F

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/cadsr-formbuilder/LICENSE.txt for details.
L--%>

<table width="20%" align="center" cellpadding="1" cellspacing="1" border="0" >
  <tr >
    <td>
      <a href="javascript:retrieveCDEs('retrieveQuestionCDEs')">
        <html:img src='<%=urlPrefix+"i/refresh.gif"%>' border="0" alt="Refresh Saved Data Elements"/> 
      </a>
    </td>
	<logic:notEmpty name="<%=CaDSRConstants.CDE_CART%>" property = "dataElements">
    <td >
      <a href="javascript:submitForm('<%=NavigationConstants.ADD_QUESTION%>')">
        <html:img src='<%=urlPrefix+"i/add_button.gif"%>' border="0" alt="Add"/>
      </a>
    </td>     
	</logic:notEmpty>
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