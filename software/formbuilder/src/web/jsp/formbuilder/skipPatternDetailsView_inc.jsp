<%--L
  Copyright Oracle Inc, ScenPro Inc, SAIC-F

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/cadsr-formbuilder/LICENSE.txt for details.
L--%>

                                      

                            
                             
                             <%
                               String currSkipTargetType = FormJspUtil.getFormElementType(currTriggerAction.getActionTarget());
                               pageContext.setAttribute("currSkipTargetType",currSkipTargetType);
                                             
                            %> 
                             
                                  <tr class="OraTabledata">
                                   <td>
                                     <table width="100%" align="center" cellpadding="0" cellspacing="0" border="0" >
                                       <tr>
                                         <td class="OraTableColumnHeader" width="100%" nowrap>
                                           Skip to 
                                         </td>
                                        </tr>
                                      </table>
                                     </td>
                                  </tr>
                                  <%@ include file="skipPatternDetailsViewInclude_inc.jsp"%>
                                                       
