<%--L
  Copyright Oracle Inc, ScenPro Inc, SAIC-F

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/cadsr-formbuilder/LICENSE.txt for details.
L--%>


      <table width="30%" align="center" cellpadding="0" cellspacing="0" border="0" >
        <tr >
          <td align="center">
            <html:link action='<%="/formbuilder/viewModuleList?"+NavigationConstants.METHOD_PARAM+"="+NavigationConstants.DONE_VIEW_MODULE_LIST%>' 
              target="_parent">          
                    <html:img src='<%=urlPrefix+"/i/backButton.gif"%>' border="0" alt="Go back"/>         
            </html:link>
          </td>
         <logic:notEmpty name="<%=FormConstants.MODULE_LIST%>" >
          <td align="center">
             <a href="javascript:submitModuleListEdit('<%=NavigationConstants.DELETE_ELEMENTS_FROM_MODULE_LIST%>')" >                                              
                 <img src="<%=urlPrefix%>/i/deleteButton.gif" border="0" alt="Delete"/>
             </a>            
          </td>  
          
         </logic:notEmpty>
        </tr>
      </table>      
