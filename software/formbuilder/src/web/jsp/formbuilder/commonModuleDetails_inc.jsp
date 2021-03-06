<table width="80%" align="center" cellpadding="0" cellspacing="1" border="0" class="OraBGAccentVeryDark">  
             
                 <tr>                 
                    <td class="OraHeaderBlack">
                      <bean:write name="module" property="longName"/>
                    </td>
                  </tr>        
                   <logic:present name="module" property="instruction">                   
                      <tr>  
                       <td colspan="2">
                           <table width="100%" align="center" cellpadding="0" cellspacing="1" border="0" class="OraBGAccentVeryDark" >
                             <tr class="OraTabledata">
                              <td class="OraTableColumnHeader" width="10%" nowrap>
                                <bean:message key="cadsr.formbuilder.form.instruction"/> 
                             </td>
                             <td class="OraFieldTextInstruction">
                               <bean:write  name="module" property="instruction.preferredDefinition"/>
                             </td>
                            </tr>
                             <tr class="OraTabledata">
                              <td class="OraTableColumnHeader" width="10%" nowrap>
                                Number of Repetitions 
                             </td>
                             <td class="OraFieldText">
                               <bean:write name="module" property="numberOfRepeats"/>
                             </td>
                            </tr>                            
                           </table>
                       </td>
                      </tr>
                   </logic:present>     
                   <logic:notPresent name="module" property="instruction">                   
                      <tr>  
                       <td colspan="2">
                           <table width="100%" align="center" cellpadding="0" cellspacing="1" border="0" class="OraBGAccentVeryDark" >
                             <tr class="OraTabledata">
                              <td class="OraTableColumnHeader" width="10%" nowrap>
                                Number of Repetitions 
                             </td>
                             <td class="OraFieldTextInstruction">
                               <bean:write name="module" property="numberOfRepeats"/>
                             </td>
                            </tr>
                           </table>
                       </td>
                      </tr>
                   </logic:notPresent>                     

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
                                       <html:link href='<%=params.getCdeBrowserUrl() + "/CDEBrowser/search?dataElementDetails=9&PageId=DataElementsGroup&queryDE=yes&FirstTimer=0"%>'
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
                                   <table align="center" width="100%" cellpadding="0" cellspacing="1" border="0" class="OraBGAccentVeryDark" >
                                     <tr class="OraTabledata">
                                      <td class="OraTableColumnHeader" width="10%" nowrap>
                                        <bean:message key="cadsr.formbuilder.form.instruction"/> 
                                     </td>
                                     <td class="OraFieldTextInstruction">
                                       <bean:write  name="question" property="instruction.preferredDefinition"/>
                                     </td>
                                    </tr>
                                   </table>                                                            
                                 </td>
                               </tr> 
                            </logic:present>
                            <%--mandatory--%>
                              <tr class="OraTabledata">
                                 <td class="OraFieldText" width="50">&nbsp;</td>
                                  <td class="OraFieldText" colspan="2">                              
                                   <table align="center" width="100%" cellpadding="0" cellspacing="1" border="0" class="OraBGAccentVeryDark" >
                                     <tr class="OraTabledata">
                                      <td class="OraTableColumnHeader" width="10%" nowrap>
                                      <bean:message key="cadsr.formbuilder.form.question.mandatory"/>
                                     </td>
                                     <td class="OraFieldTextInstruction"><html:checkbox name="question" property="mandatory" disabled="true"/> </td>
                                    </tr>
                                   </table>                                                            
                                 </td>
                               </tr> 

								<tr class="OraTabledata">
                                 <td class="OraFieldText" width="50">&nbsp;</td>
                                  <td class="OraFieldText" colspan="2">                              
                                   <table align="center" width="100%" cellpadding="0" cellspacing="1" border="0" class="OraBGAccentVeryDark" >
                                     <tr class="OraTabledata">
                                      <td class="OraTableColumnHeader" width="10%" nowrap>
                                      <bean:message key="cadsr.formbuilder.form.question.editable"/>
                                     </td>
                                     <td class="OraFieldTextInstruction">
										<html:checkbox name="question" property="editable" disabled="true"/>
										<logic:equal name="question" property="deDerived" value="true">
											&nbsp;&nbsp;<font color="gray" size="2"><i>Cannot be changed because Data Element is derived</i></font>
										</logic:equal> 
									</td>
                                    </tr>
                                   </table>                                                            
                                 </td>
                               </tr> 
                            <logic:notEmpty name="question" property="defaultValue">
                              <tr class="OraTabledata">
                                 <td class="OraFieldText" width="50">&nbsp;</td>
                                  <td class="OraFieldText" colspan="2">                              
                                   <table align="center" width="100%" cellpadding="0" cellspacing="1" border="0" class="OraBGAccentVeryDark" >
                                     <tr class="OraTabledata">
                                      <td class="OraTableColumnHeader" width="10%" nowrap>
                                        <bean:message key="cadsr.formbuilder.form.questionDefaultValue"/>                                        
                                     </td>
                                     <td class="OraFieldTextInstruction">
                                       <bean:write  name="question" property="defaultValue" filter="false"/>
                                     </td>
                                    </tr>
                                   </table>                                                            
                                 </td>
                               </tr> 
                            </logic:notEmpty>

                            <logic:notEmpty name="question" property="defaultValidValue">
	                      <logic:notEmpty name="question" property="defaultValidValue.longName">                            
                              <tr class="OraTabledata">
                                 <td class="OraFieldText" width="50">&nbsp;</td>
                                  <td class="OraFieldText" colspan="2">                              
                                   <table align="center" width="100%" cellpadding="0" cellspacing="1" border="0" class="OraBGAccentVeryDark" >
                                     <tr class="OraTabledata">
                                      <td class="OraTableColumnHeader" width="10%" nowrap>
                                        <bean:message key="cadsr.formbuilder.form.questionDefaultValue"/>
                                     </td>
                                     <td class="OraFieldTextInstruction">
                                       <bean:write  name="question" property="defaultValidValue.longName" filter="false"/>
                                     </td>
                                    </tr>
                                   </table>                                                            
                                 </td>
                               </tr> 
                              </logic:notEmpty>
                            </logic:notEmpty>
			   <logic:present name="question" property="dataElement">
                            <logic:present name="question" property="dataElement.valueDomain">
                              <tr class="OraTabledata">
                                 <td class="OraFieldText" width="50">&nbsp;</td>
                                  <td class="OraFieldText" colspan="2">                              
                                   <table align="center" width="100%" cellpadding="0" cellspacing="1" border="0" class="OraBGAccentVeryDark" >
                                     <tr class="OraTabledata">
                                      <td class="OraTableColumnHeader"  nowrap colspan="2">
                                        <bean:message key="cadsr.formbuilder.form.valueDomain.valueDomainDetails"/>        
                                     </td>                                     
                                    </tr>
                                    
                                    <tr class="OraTabledata">
                                     <td class="OraTableColumnHeader" width="20%">
                                        <bean:message key="cadsr.formbuilder.form.valueDomain.longName"/>
                                     </td>
                                     <td class="OraFieldText">
                                       <bean:write  name="question" property="dataElement.valueDomain.longName"/>          
                            
                                     </td>
                                    </tr>
                                    
                                    <tr class="OraTabledata">
                                     <td class="OraTableColumnHeader">
                                        <bean:message key="cadsr.formbuilder.form.valueDomain.datatype"/>
                                     </td>
                                     <td class="OraFieldText">
                                       <bean:write  name="question" property="dataElement.valueDomain.datatype"/>          
                            
                                     </td>
                                    </tr>

                                    <tr class="OraTabledata">
                                     <td class="OraTableColumnHeader">
                                        <bean:message key="cadsr.formbuilder.form.valueDomain.unitofmeasure"/>
                                     </td>
                                     <td class="OraFieldText">
                                       <bean:write  name="question" property="dataElement.valueDomain.unitOfMeasure"/>     
                                     </td>
                                    </tr>

                                    <tr class="OraTabledata">
                                     <td class="OraTableColumnHeader">
                                        <bean:message key="cadsr.formbuilder.form.valueDomain.displayFormat"/>
                                     </td>
                                     <td class="OraFieldText">
                                       <bean:write  name="question" property="dataElement.valueDomain.displayFormat"/>     
                                     </td>
                                    </tr>

                                    <tr class="OraTabledata">
                                     <td class="OraTableColumnHeader">
                                        <bean:message key="cadsr.formbuilder.form.valueDomain.concepts"/>
                                     </td>
                                     <td class="OraFieldText">
                                       
<%=CDEDetailsUtils.getConceptCodesUrl(question.getDataElement().getValueDomain().getConceptDerivationRule(),params,"link",",")%>
                                     </td>
                                    </tr>
                                   </table>                                                            
                                 </td>
                               </tr> 
                            </logic:present>
                           </logic:present>

                            <logic:present name="question">
                            <logic:notEmpty name="question" property = "validValues">
                              <tr class="OraTabledata">
                                <td class="OraFieldText" width="50">&nbsp;</td><td colspan="2"><table width="100%" align="center" cellpadding="0" cellspacing="0" border="0" class="OraBGAccentVeryDark" id="collapsible"><logic:iterate id="validValue" name="question" 
type="gov.nih.nci.ncicb.cadsr.common.resource.FormValidValue" property="validValues" indexId="vvIndex"><tr class="OraTabledata"><td class="OraFieldText"><table width="100%" cellpadding="1" cellspacing="1" border="0" class="OraTabledata">
										<tr class="OraTabledata">
                                        <td COLSPAN="2" class="OraFieldText" >&nbsp;</td>
                                      </tr>
                                      <tr   class="OraTabledata">
                                        <td class="OraFieldText" width="50">&nbsp;</td>
                                        <td class="OraFieldText">
                                          <bean:write name="validValue" property="longName" filter="false"/>
                                        </td>
                                      </tr>
                                      <tr   class="OraTabledata">
                                        <td class="OraFieldText" width="50">&nbsp;</td>
                                        <td><table width="100%" align="center" cellpadding="1" cellspacing="1" border="0" class="OraBGAccentVeryDark" >
                                               <tr class="OraTabledata">
                                                 <td  class="OraTableColumnHeader" width="10%" nowrap >
                                                   <bean:message key="cadsr.formbuilder.valueMeaning.text" /></td>
                                                 <td class="OraFieldText" width="90%" >
                                                  <bean:write name="validValue" property="formValueMeaningText"/></td>                                          
                                               </tr><tr class="OraTabledata">
                                                 <td  class="OraTableColumnHeader" width="10%" nowrap >
                                                   <bean:message key="cadsr.formbuilder.valueMeaning.idversion" /></td>
                                                 <td class="OraFieldText" width="90%">
                                                  <bean:write name="validValue" property="formValueMeaningIdVersion"/></td>                                          
                                               </tr><tr class="OraTabledata">
                                                 <td  class="OraTableColumnHeader" width="10%" nowrap >
                                                   <bean:message key="cadsr.formbuilder.valueMeaning.description" /></td>
                                                 <td class="OraFieldText" width="90%">
                                                  <bean:write name="validValue" property="formValueMeaningDesc"/></td>                                          
                                               </tr><logic:present name="validValue" property="instruction"><tr class="OraTabledata">
                                                  <td class="OraTableColumnHeader" width="10%" nowrap>
                                                    <bean:message key="cadsr.formbuilder.form.instruction"/> 
                                                 </td>
                                                 <td class="OraFieldTextInstruction" width="90%">
                                                   <bean:write  name="validValue" property="instruction.preferredDefinition"/>
                                                 </td>
                                                </tr>                           
                                              </logic:present> 
                                              
                                      <!-- vv skip Pattern -->
                                      <logic:present name="validValue" property = "triggerActions" >
			              <logic:notEmpty name="validValue" property = "triggerActions">
                                      
                                      <tr   class="OraTabledata">

                                        <td colspan="2">	
				          <table width="100%" align="center" cellpadding="0" cellspacing="1" border="0" class="OraBGAccentVeryDark">
					    <logic:iterate id="currTriggerAction" name="validValue" 
type="gov.nih.nci.ncicb.cadsr.common.resource.TriggerAction" property="triggerActions" indexId="triggerIndex" >
						<%@ include file="skipPatternDetailsView_inc.jsp"%>
					    </logic:iterate>
					  </table>

                                         </td>
                                        </tr>
				       </logic:notEmpty>
				       </logic:present>                                         
                                       <!-- vv Skip pattern end -->   
                                       
                                           </table>   
                                          <%--}--%>
                                        </td>                                        
                                      </tr>   
                                                </table></td></tr></logic:iterate><!-- valid Value--></table></td></tr>
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
                          </logic:iterate><!-- Question-->
                        </table>
                      </td>
                    </tr>
                  </logic:notEmpty>
            </table>
