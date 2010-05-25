<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/cdebrowser.tld" prefix="cde"%>
<%@ page import="java.util.*" %>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.resource.ValidValue" %>

<HTML>
  <HEAD>
    <TITLE>Formbuilder: Valid Values</TITLE>
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache"/>
    <LINK rel="stylesheet" TYPE="text/css" HREF="<html:rewrite page='/css/blaf.css' />">
  </HEAD>
  <BODY topmargin=0 bgcolor="#ffffff" onLoad="makeCollapsible(document.getElementsByTagName('table'), 'collapsible');">

    <%@ include file="../common/in_process_common_header_inc.jsp"%>
    <logic:present name="nciUser">
	    <jsp:include page="../common/tab_inc.jsp" flush="true">
	      <jsp:param name="label" value="View&nbsp;Valid&nbsp;Values"/>
	      <jsp:param name="urlPrefix" value=""/>
	    </jsp:include>
    </logic:present>

<table>
    <tr>    
      <td align="left" class="AbbreviatedText">
        <bean:message key="cadsr.formbuilder.helpText.form.view"/>
      </td>
    </tr>  
</table> 
	
    <logic:present name="pvMap" scope="request">
		<table width="80%" align="center" cellpadding="1" cellspacing="1" border="0" class="OraBGAccentVeryDark">
		<tr class="OraTabledata">
		<td class="OraFieldText">
		<logic:iterate name="pvMap" id="pvs" >
			<bean:define id="acType" name="pvs" property="key" />
			<table width="100%" align="center" cellpadding="1" cellspacing="1" border="0" class="OraBGAccentVeryDark">
				<tr class="OraTabledata">
					<td class="TableRowPromptTextLeft" width="20%">
		            Component Type
		          </td>
					<td class="OraFieldText" >
		            	<bean:write name="acType" property="componentType" />
		          	</td>  
				</tr>
				<tr class="OraTabledata">
					<td class="TableRowPromptTextLeft" width="20%">
		            Long Name
		          </td>
					<td class="OraFieldText" >
		            	<bean:write name="acType" property="longName" />
		          	</td>  
				</tr>
				<tr class="OraTabledata">
					<td class="TableRowPromptTextLeft" width="20%">
		            Public Id
		          </td>
					<td class="OraFieldText" >
		            	<bean:write name="acType" property="publicId" />
		          	</td>  
				</tr>
				<tr class="OraTabledata">
					<td class="TableRowPromptTextLeft" width="20%">
		            Version
		          </td>
					<td class="OraFieldText" >
		            	<bean:write name="acType" property="version" />
		          	</td>  
				</tr>
			</table>
			</logic:iterate>
		</td></tr>
		<tr class="OraTabledata">
			<td class="OraFieldText">
				<table width="100%" align="center" cellpadding="0" cellspacing="0" border="0" class="OraTabledata">
				<tr class="OraTabledata" height="10"/>
					<tr class="OraTabledata">
						<td class="OraFieldText" width="50">&nbsp;</td>
						<td class="OraFieldText">
							<logic:present name="pvs" property="value">
							<table width="100%" align="center" cellpadding="1" cellspacing="1" border="0" class="OraBGAccentVeryDark">
								<tr class="OraTabledata">
									<td class="OraTableColumnHeader" width="20%">
										Value Value
									</td>
									<td class="OraTableColumnHeader" width="40%">
										Value Meaning Long Name
									</td>
									<td class="OraTableColumnHeader" width="40%">
										Value Meaning Definition
									</td>
								</tr>
								<logic:iterate id="vv" name="pvs" property="value">
									<bean:define id="vm" name="vv" property="valueMeaning" />
							
									<tr class="OraTabledata">
										<td class="OraFieldText">
							            	<bean:write name="vv" property="shortMeaning" />
							          	</td>                
										
										<td class="OraFieldText" >
							            	<bean:write name="vm" property="longName" />
							          	</td> 
																
										<td class="OraFieldText" >
							            	<bean:write name="vm" property="preferredDefinition" />
							          	</td>  
									</tr>
											
									
								</logic:iterate>
							</table>
							</logic:present>
							<logic:notPresent name="pvs" property="value">
								No PVs/VVs are present for this administered component
							</logic:notPresent>
							</td>
							<td class="OraFieldText" width="50">&nbsp;</td>
					</tr>
					<tr class="OraTabledata" height="10"/>
				</table>
			</td>
		</tr>
		</table>
	</logic:present>
	<logic:notPresent name="pvMap" scope="request">
		No Permissible Values or Valid Values are present for the requested Public ID and Version.
	</logic:notPresent>
</BODY>
</HTML>