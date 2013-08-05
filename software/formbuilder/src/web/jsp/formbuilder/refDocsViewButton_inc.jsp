<%--L
  Copyright Oracle Inc, ScenPro Inc, SAIC-F

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/cadsr-formbuilder/LICENSE.txt for details.
L--%>

      <table width="20%" align="center" cellpadding="1" cellspacing="1" border="0" >
        <tr >
                    
          <td > 
       <html:link action='<%="/formDetailsAction?"+NavigationConstants.METHOD_PARAM+"="+NavigationConstants.GET_FORM_DETAILS%>' paramId = "<%=FormConstants.FORM_ID_SEQ%>"
 				paramName="crf" paramProperty="formIdseq"
 				target="_parent" >
              <html:img src='<%=urlPrefix+"i/backButton.gif"%>' border="0" alt="Done"/>
            </html:link>             
          </td>                                 
        </tr> 
                
      </table>