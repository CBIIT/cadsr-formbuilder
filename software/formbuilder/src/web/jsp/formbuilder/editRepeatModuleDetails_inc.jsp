 		<table width="80%" align="center" cellpadding="0" cellspacing="1" border="0" class="OraBGGrayVeryDark">               
                 <tr>                 
                    <td class="OraBGGrayLight">
                      <bean:write name="module" property="longName"/>-<%= ((Integer)modIndex).intValue() + 1 %>
                    </td>
                  </tr>        
                   <logic:present name="module" property="instruction">                   
                      <tr>  
                       <td colspan="2">
                           <table width="100%" align="center" cellpadding="0" cellspacing="1" border="0" class="OraBGGrayVeryDark" >
                             <tr class="OraTabledata">
                              <td class="OraTableColumnHeaderGrayBG" width="10%" nowrap>
                                <bean:message key="cadsr.formbuilder.form.instruction"/> 
                             </td>
                             <td class="OraFieldTextInstruction">
                               <bean:write  name="module" property="instruction.preferredDefinition"/>
                             </td>
                            </tr>
                           </table>
                       </td>
                      </tr>
                   </logic:present>                                      

                  <logic:notEmpty name="module" property = "questions">
                    <tr class="OraTabledata">
                      <td>
                        <table width="100%" align="center" cellpadding="0" cellspacing="0" border="0" class="OraTabledata">      
                          <logic:iterate id="question" name="module" type="gov.nih.nci.ncicb.cadsr.common.resource.Question" property="questions" indexId="questionIndex" >                           
                            <bean:size id="questionSize" name="module" property="questions" />
							
                            <tr class="OraTabledata">
                              <td class="OraFieldText" width="50">&nbsp;</td>
                              <td height="1"  class="OraFieldText">                               
                              </td>                              
                            </tr>    
                              
                            <tr class="OraTabledata">
                              <td class="OraFieldText" width="7%"> 
                                &nbsp;
                              </td>
                              <td class="UnderlineOraFieldText" >
                                <bean:write name="question" property="longName" filter="false"/>
                              </td>
                              <td class="OraTabledata" width="15%" align="right" >
                               <table width="100%" align="right" cellpadding="0" cellspacing="0" border="0" class="OraTabledata">
                                 <tr>
                                   <logic:present name="question" property = "dataElement">
                                     <td align="right" width="70" class="UnderlineOraFieldText" >                        
                                            <html:link href='<%=params.getCdeBrowserUrl() + "/CDEBrowser/search?dataElementDetails=9&PageId=DataElementsGroup&queryDE=yes"%>'
                                               paramId = "p_de_idseq"
                                                paramName="question"
                                                paramProperty="dataElement.deIdseq"
                                                target="_blank">
                                            <bean:write name="question" property="dataElement.CDEId"/>
                                            </html:link>
                                     </td>
                                    <td align="right" width="70" class="UnderlineOraFieldText">
                                       <bean:write name="question" property="dataElement.version"/>
                                    </td>                                  
                                   </logic:present>
                                   <logic:notPresent name="question" property="dataElement">
                                     <td align="center" width="70" class="UnderlineOraFieldText">
                                       &nbsp;
                                     </td>
                                     <td align="center" width="70" class="UnderlineOraFieldText">
                                       &nbsp;
                                      </td>                              
                                   </logic:notPresent>  
                                 </tr>  
                               </table>
                              </td> 
                            </tr>
                            <logic:present name="question" property="instruction">
                              <tr class="OraTabledata">
                                 <td class="OraFieldText" width="50">&nbsp;</td>
                                  <td class="OraFieldText" colspan="2">                              
                                   <table align="center" width="100%" cellpadding="0" cellspacing="1" border="0" class="OraBGGrayVeryDark" >
                                     <tr class="OraTabledata">
                                      <td class="OraTableColumnHeaderGrayBG" width="10%" nowrap>
                                        <bean:message key="cadsr.formbuilder.form.instruction"/>
                                     </td>
                                     <td class="OraFieldTextInstruction">
                                       <bean:write  name="question" property="instruction.preferredDefinition"/>
                                     </td>
                                    </tr>
                                     <tr class="OraTabledata">
                                      <td class="OraTableColumnHeaderGrayBG" width="10%" nowrap>
                                          <bean:message key="cadsr.formbuilder.form.question.mandatory"/> 
                                     </td>
                                     <td class="OraFieldText">
                                	<html:checkbox name="question" property="mandatory" disabled="true"/>
                                     </td>
                                    </tr>
									<tr class="OraTabledata">
                                      <td class="OraTableColumnHeaderGrayBG" width="10%" nowrap>
                                          <bean:message key="cadsr.formbuilder.form.question.editable"/> 
                                     </td>
                                     <td class="OraFieldText">
										<logic:equal name="question" property="deDerived" value="true">
											<html:checkbox property="<%=FormConstants.QUESTION_EDITABLES+"["+defaultIndex+"]"%>" disabled="true"/>
											<font color="gray" size="2"><i>Cannot be changed because Data Element is derived</i></font>
										</logic:equal>

										<logic:notEqual name="question" property="deDerived" value="true">
											<logic:empty name="moduleRepeatForm" property='<%=FormConstants.QUESTION_DEFAULTS+"["+defaultIndex+"]"%>'>
												<html:checkbox property="<%=FormConstants.QUESTION_EDITABLES+"["+defaultIndex+"]"%>" disabled="true" onclick='<%= "checkHidden('"+ FormConstants.QUESTION_EDITABLES+"["+defaultIndex +"]')" %>'/>
											</logic:empty>
											<logic:notEmpty name="moduleRepeatForm" property='<%=FormConstants.QUESTION_DEFAULTS+"["+defaultIndex+"]"%>'>
												<html:checkbox property="<%=FormConstants.QUESTION_EDITABLES+"["+defaultIndex+"]"%>" onclick='<%= "checkHidden('"+ FormConstants.QUESTION_EDITABLES+"["+defaultIndex +"]')" %>'/>
											</logic:notEmpty>
										</logic:notEqual>

										<html:hidden property="<%=FormConstants.QUESTION_EDITABLES+"["+defaultIndex+"]"%>" value="<%= String.valueOf(((Boolean[])((org.apache.struts.action.DynaActionForm)session.getAttribute("moduleRepeatForm")).get(FormConstants.QUESTION_EDITABLES))[defaultIndex]) %>" />
                                     </td>
                                    </tr>

                                     <logic:notEmpty name="question" property = "validValues">
                                     <tr class="OraTabledata">
                                      <td class="OraTableColumnHeaderGrayBG" width="10%" nowrap>
                                        Default value
                                     </td>
                                     <td class="OraFieldText">
                                        <html:hidden property="<%=FormConstants.QUESTION_DEFAULT_VV_IDS+"["+defaultIndex+"]"%>"  />
                                         <html:text  styleClass="OraFieldText"  property='<%=FormConstants.QUESTION_DEFAULTS+"["+defaultIndex+"]"%>' readonly="true" size="70" />                                   
                                     </td>  
                                     </tr>
                                      </logic:notEmpty>
                                      <logic:empty name="question" property = "validValues">
                                      <tr class="OraTabledata">
                                         <td class="OraTableColumnHeaderGrayBG" width="10%" nowrap>
                                           Default value
                                        </td>
                                        <td class="OraFieldText">
                                         <html:hidden property="<%=FormConstants.QUESTION_DEFAULT_VV_IDS+"["+defaultIndex+"]"%>" value="" />
                                         <html:text  styleClass="OraFieldText"  property='<%=FormConstants.QUESTION_DEFAULTS+"["+defaultIndex+"]"%>' size="70" onkeyup='<%= "setEditable(this, '"+ FormConstants.QUESTION_EDITABLES+"["+defaultIndex +"]')" %>' />         
                                        </td> 
                                      </tr>
                                       </logic:empty>
                                   </table>                                                            
                                 </td>
                               </tr> 
                            </logic:present>
                            
                            <logic:notPresent name="question" property="instruction">                            
                              <tr class="OraTabledata">
                                 <td class="OraFieldText" width="50">&nbsp;</td>
                                  <td class="OraFieldText" colspan="2">                              
                                   <table align="center" width="100%" cellpadding="0" cellspacing="1" border="0" class="OraBGGrayVeryDark" >
                                     <tr class="OraTabledata">
                                      <td class="OraTableColumnHeaderGrayBG" width="10%" nowrap>
                                          <bean:message key="cadsr.formbuilder.form.question.mandatory"/> 
                                     </td>
                                     <td class="OraFieldText">
                                	<html:checkbox name="question" property="mandatory" disabled="true"/>
                                     </td>
                                    </tr>
									<tr class="OraTabledata">
                                      <td class="OraTableColumnHeaderGrayBG" width="10%" nowrap>
                                          <bean:message key="cadsr.formbuilder.form.question.editable"/> 
                                     </td>
                                     <td class="OraFieldText">
									<logic:equal name="question" property="deDerived" value="true">
										<html:checkbox property="<%=FormConstants.QUESTION_EDITABLES+"["+defaultIndex+"]"%>" disabled="true" />
										<font color="gray" size="2"><i>Cannot be changed because Data Element is derived</i></font>
									</logic:equal>

									<logic:notEqual name="question" property="deDerived" value="true">
										<logic:empty name="moduleRepeatForm" property="<%=FormConstants.QUESTION_DEFAULTS+"["+defaultIndex+"]"%>">
											<html:checkbox property="<%=FormConstants.QUESTION_EDITABLES+"["+defaultIndex+"]"%>" disabled="true" onclick='<%= "checkHidden('"+ FormConstants.QUESTION_EDITABLES+"["+defaultIndex +"]')" %>'/>
										</logic:empty>
										<logic:notEmpty name="moduleRepeatForm" property="<%=FormConstants.QUESTION_DEFAULTS+"["+defaultIndex+"]"%>">
											<html:checkbox property="<%=FormConstants.QUESTION_EDITABLES+"["+defaultIndex+"]"%>" onclick='<%= "checkHidden('"+ FormConstants.QUESTION_EDITABLES+"["+defaultIndex +"]')" %>'/>
										</logic:notEmpty>
									</logic:notEqual>

									<html:hidden property="<%=FormConstants.QUESTION_EDITABLES+"["+defaultIndex+"]"%>" value="<%= String.valueOf(((Boolean[])((org.apache.struts.action.DynaActionForm)session.getAttribute("moduleRepeatForm")).get(FormConstants.QUESTION_EDITABLES))[defaultIndex]) %>" />
									
                                     </td>
                                    </tr>
                                     <tr class="OraTabledata">
                                     <logic:notEmpty name="question" property = "validValues">
                                      <td class="OraTableColumnHeaderGrayBG" width="10%" nowrap>
                                        Default value
                                     </td>
                                     <td class="OraFieldText">
                                        <html:hidden property="<%=FormConstants.QUESTION_DEFAULT_VV_IDS+"["+defaultIndex+"]"%>"  />
                                         <html:text  styleClass="OraFieldText"  property='<%=FormConstants.QUESTION_DEFAULTS+"["+defaultIndex+"]"%>' readonly="true" size="70" />                                   
                                     </td>                                    
                                      </logic:notEmpty>
                                      <logic:empty name="question" property = "validValues">
                                         <td class="OraTableColumnHeaderGrayBG" width="10%" nowrap>
                                           Default value
                                        </td>
                                        <td class="OraFieldText">
											<logic:equal name="question" property="deDerived" value="true">
												<html:hidden property="<%=FormConstants.QUESTION_DEFAULT_VV_IDS+"["+defaultIndex+"]"%>" value="" />
                                         		<html:text  styleClass="OraFieldText"  property='<%=FormConstants.QUESTION_DEFAULTS+"["+defaultIndex+"]"%>' size="70" readonly="true" />
											</logic:equal>
		
                                         <logic:notEqual name="question" property="deDerived" value="true">
											<html:hidden property="<%=FormConstants.QUESTION_DEFAULT_VV_IDS+"["+defaultIndex+"]"%>" value="" />
                                        	<html:text  styleClass="OraFieldText"  property='<%=FormConstants.QUESTION_DEFAULTS+"["+defaultIndex+"]"%>' size="70" onkeyup='<%= "setEditable(this, '"+ FormConstants.QUESTION_EDITABLES+"["+defaultIndex +"]')" %>'/>
										</logic:notEqual>         
                                        </td>                                   
                                       </logic:empty>
                                    </tr>                                    
                                   </table>                                                            
                                 </td>
                               </tr> 
                            
                            </logic:notPresent>                            
                            <logic:present name="question">
                            <logic:notEmpty name="question" property = "validValues">
                              <tr class="OraTabledata">
                                <td class="OraFieldText" width="50">&nbsp;</td>
                                <td colspan="2">
                                  <table width="100%" align="center" cellpadding="0" cellspacing="0" border="0" class="OraBGAccentVeryDark" id="collapsible"><logic:iterate id="validValue" name="question" type="gov.nih.nci.ncicb.cadsr.common.resource.FormValidValue" property="validValues" indexId="vvIndex"><tr   class="OraTabledata">
                                        <td COLSPAN="2" class="OraFieldText" >&nbsp;</td>
                                      </tr>
                                      <tr   class="OraTabledata">
                                        <td class="OraFieldText" width="50">&nbsp;</td>
                                        <td class="OraFieldText">
                                          <bean:write name="validValue" property="longName" filter="false"/>
                                          <% String formattedValidValue = validValue.getLongName();
                                             formattedValidValue = StringUtils.getValidJSString(formattedValidValue);
                                           %>  
											<logic:notEqual name="question" property="deDerived" value="true">
                                          <a href="javascript:populateDefaultValue('<%=formattedValidValue%>','<%=validValue.getValueIdseq()%>','<%=defaultIndex%>')">
                                             Click here to set as default
                                          </a>                                           
											</logic:notEqual>
                                        </td>
                                      </tr>
                                      <tr   class="OraTabledata">
                                        <td class="OraFieldText" width="50">&nbsp;</td>
                                        <td >
                                        <%-- if(question.getDataElement()!=null|| validValue.getInstruction()!=null){--%>
                                          <table align="center" cellpadding="1" cellspacing="1" border="0" class="OraBGGrayVeryDark" >                          
                                               <tr class="OraTabledata">
                                                 <td  class="OraTableColumnHeaderGrayBG" width="10%" nowrap >
                                                   <bean:message key="cadsr.formbuilder.valueMeaning.text" /></td>
                                                 <td class="OraFieldText" >
                                                  <bean:write name="validValue" property="formValueMeaningText"/></td>                                          
                                               </tr>  
                                            	<tr class="OraTabledata">
                                                 <td  class="OraTableColumnHeaderGrayBG" width="10%" nowrap >
                                                   <bean:message key="cadsr.formbuilder.valueMeaning.idversion" /></td>
                                                 <td class="OraFieldText" >
                                                  <bean:write name="validValue" property="formValueMeaningIdVersion"/></td>                                          
                                               </tr>  
                                               <tr class="OraTabledata">
                                                 <td  class="OraTableColumnHeaderGrayBG" width="10%" nowrap >
                                                   <bean:message key="cadsr.formbuilder.valueMeaning.description" /></td>
                                                 <td class="OraFieldText" >
                                                  <bean:write name="validValue" property="formValueMeaningDesc"/></td>                                          
                                               </tr>  
                                              <logic:present name="validValue" property="instruction">                
                                                 <tr class="OraTabledata">
                                                  <td class="OraTableColumnHeaderGrayBG" width="10%" nowrap>
                                                    <bean:message key="cadsr.formbuilder.form.instruction"/> 
                                                 </td>
                                                 <td class="OraFieldTextInstruction">
                                                   <bean:write  name="validValue" property="instruction.preferredDefinition"/>
                                                 </td>
                                                </tr>  
                                                
                                              </logic:present>                                                
                                           </table>   
                                          <%--}--%>
                                        </td>                                        
                                      </tr>   
                                                                                                                                                
                                    </logic:iterate><!-- valid Value-->
                                  </table>
                                </td>
                              </tr>
                            </logic:notEmpty>
                            <logic:empty name="question" property = "validValues">
                              <tr class="OraTabledata">
                                <td class="OraFieldText" width="50">&nbsp;</td>
                                <td>
                                  <table width="100%" align="center" cellpadding="0" cellspacing="0" border="0" class="OraBGAccentVeryDark">
                                      <tr  COLSPAN="3" class="OraTabledata">
                                        <td class="OraFieldText" width="50">&nbsp;</td>
                                        <td class="OraFieldText">
                                          &nbsp;
                                        </td>
                                      </tr>   
                                  </table>
                                </td>
                              </tr>                            
                            </logic:empty>
                            </logic:present>
                            
                           <logic:equal value="<%= String.valueOf(questionSize.intValue()-1) %>" name="questionIndex">          
                            <tr class="OraTabledata">
                              <td class="OraFieldText" width="50">&nbsp;</td>
                              <td height="1"  class="OraFieldText">                               
                              </td>                              
                            </tr>         
                          </logic:equal>    
                               <%
                                    defaultIndex++;
                                 %>
                          </logic:iterate><!-- Question-->
                        </table>
                      </td>
                    </tr>
                  </logic:notEmpty>
            </table>