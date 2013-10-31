<%--L
  Copyright Oracle Inc, ScenPro Inc, SAIC-F

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/cadsr-formbuilder/LICENSE.txt for details.
L--%>

<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<p>
<s:form action="upload-form" theme = "simple"> 
<s:submit value="Load Forms"/>  
</s:form>	

<p>
<s:form action="search-form" theme = "simple"> 
<s:submit value="Unload Forms"/>  
</s:form>	 

<p>
<a href="file/FormLoaderv8.xsd" target="_blank">Download XSD</a>