<%--L
  Copyright Oracle Inc, ScenPro Inc, SAIC-F

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/cadsr-formbuilder/LICENSE.txt for details.
L--%>

      <table width="20%" align="center" cellpadding="1" cellspacing="1" border="0" >
        <tr >
          <td >
            <a href="javascript:submitForm()">
              <img src='<%=urlPrefix+"i/copyButton.gif"%>' border="0" alt="Copy"/>
            </a>
          </td>           
          <td >
            <a href="javascript:resetForm()">
              <html:img src='<%=urlPrefix+"i/reset.gif"%>' border="0" alt="Reset"/>
            </a>
          </td>                
          <td >
 	    <html:link action='<%="/formSearchAction"%>'>				
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