<%--L
  Copyright Oracle Inc, ScenPro Inc, SAIC-F

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/cadsr-formbuilder/LICENSE.txt for details.
L--%>

            <% 
             List repeats = FormActionUtil.getRepetitions(module);
             pageContext.setAttribute("repeats",repeats);
             %>
            <logic:present name="repeats" >
            <logic:notEmpty name="repeats" >
              <logic:iterate id="module" name="repeats" type="gov.nih.nci.ncicb.cadsr.common.resource.Module" indexId="modIndex" >                                          
                 <%@ include file="repeatDetailsModuleDetails_inc.jsp"%> 
      		<table width="80%" align="center" cellpadding="0" cellspacing="0" border="0" >
        	   <tr class>
          	      <td >
			             &nbsp;
          	      </td>
        	   </tr> 
        	</table>
              </logic:iterate><!-- Module-->
            </logic:notEmpty>
            </logic:present>