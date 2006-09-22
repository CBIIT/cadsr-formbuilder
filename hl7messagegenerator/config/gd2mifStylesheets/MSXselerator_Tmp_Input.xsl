<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <!--
   *
   * File: mif2gd.xsl
   * Accepts a MIF-compliant document as input, writes 
   * an eDCI Global Definitions document as output.
   *
  -->
  <xsl:output method="xml" encoding="utf-8"/>
  <!--
  Establish constants for dummy values for required attributes
  -->
  <xsl:variable name="EDCI_Release">1</xsl:variable>
  <xsl:variable name="EDCI_GlobalDefs">eDciGlobalDefinitions</xsl:variable>
  <xsl:variable name="EDCI_RepresentationKind">short</xsl:variable>
  <xsl:variable name="EDCI_Dummy">0</xsl:variable>
  <xsl:variable name="DataElementConceptCodeSystemId">998.98.1</xsl:variable>
  <xsl:variable name="DataElementsCodeSystemId">998.98.2</xsl:variable>
  <xsl:variable name="ValueDomainCodeSystemId">998.98.4</xsl:variable>
  <xsl:variable name="DataElementGroupCodeSystemId">998.98.5</xsl:variable>
  <!-- Includes -->
  <xsl:include href="uuid-from-id.xsl"/>
  <xsl:include href="mif2gdValueDomain.xsl"/>
  <xsl:include href="mif2gdDataElementConcepts.xsl"/>
  <xsl:include href="mif2gdDataElements.xsl"/>
  <xsl:include href="mif2gdDataElementGroups.xsl"/>
  <!--

 -->
  <xsl:template match="/package[@name='eDciGlobalDefinitions']/content/vocabularyModel">
    <xsl:element name="GlobalDefinitions">
      <xsl:attribute name="activityTime">
        <xsl:value-of select="header/renderingInformation/@renderingTime"/>
      </xsl:attribute>
      <xsl:comment>
        To make this xml document describe how to find the eDciGlobalDefinition schema 
        needed to validate itself, include attributes like these in the start tag of 
        the /GlobalDefinitions element:
           xmlns="urn:hl7-org:v3/mif" 
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
           xsi:schemaLocation="urn:hl7-org:v3/mif
           &lt;eDciGlobalDefPath>eDciGlobalDefs.xsd"
        where &lt;eDciGlobalDefPath> stands for the path to the directory containing the 
        eDciGlobalDefs schema.
      </xsl:comment>
      <xsl:if test="codeSystem[@title='ValueDomain']">
        <xsl:comment>Value Domains</xsl:comment>
        <xsl:for-each select="codeSystem[@title='ValueDomain']">
          <xsl:call-template name="mif2gdValueDomain"/>
        </xsl:for-each>
      </xsl:if>
      <xsl:if test="codeSystem[@title='DataElementConcepts']">
        <xsl:comment>Data Element Concepts</xsl:comment>
        <xsl:call-template name="mif2gdDataElementConcepts">
          <xsl:with-param 
          name="codeSystem" 
          select="codeSystem[@title='DataElementConcepts']"/>
        </xsl:call-template>
      </xsl:if>
      <xsl:if test="codeSystem[@name='DataElements']">
        <xsl:comment>Data Elements</xsl:comment>
        <xsl:call-template name="mif2gdDataElements">
          <xsl:with-param 
          name="codeSystem" 
          select="codeSystem[@name='DataElements']"/>
        </xsl:call-template>
      </xsl:if>
      <xsl:if test="valueSet[@title='DataElementGroup']">
        <xsl:comment>Data Element Groups</xsl:comment>
        <xsl:for-each select="valueSet[@title='DataElementGroup']">
          <xsl:call-template name="mif2gdDataElementGroups">
            <xsl:with-param 
          name="valueSet" 
          select="valueSet[@title='DataElementGroups']"/>
          </xsl:call-template>
        </xsl:for-each>
      </xsl:if>
    </xsl:element>
  </xsl:template>
  <!--
  
  -->
  <xsl:template name="retrieveValueFromPair">
    <!-- Creates an attribute, populates it with value from attName:value text -->
    <xsl:param name="attName"/>
    <xsl:if test="annotations/documentation/description/text[starts-with(.,concat($attName,':'))]">
      <xsl:attribute name="{$attName}">
        <xsl:value-of select="substring-after(
       annotations/documentation/description/text[starts-with(.,concat($attName,':'))]
       , ':')"/>
      </xsl:attribute>
    </xsl:if>
  </xsl:template>
</xsl:stylesheet>