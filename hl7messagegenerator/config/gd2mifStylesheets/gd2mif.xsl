<?xml version="1.0"?>
<xsl:stylesheet version="1.0" 
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <!--
  *
  * File: gd2mif.xsl
  * Accepts an eDCI Global Definitions document as input, writes
  * a MIF-compliant document as output.
  -->
  <xsl:output method="xml" encoding="utf-8"/>
  <!--
    Establish constants for dummy values for required attributes 
  -->
  <xsl:variable name="EDCI_Release">1</xsl:variable>
  <xsl:variable name="EDCI_GlobalDefs">eDciGlobalDefinitions</xsl:variable>
  <xsl:variable name="EDCI_RepresentationKind">short</xsl:variable>
  <xsl:variable name="EDCI_Dummy">O</xsl:variable>
  <xsl:variable name="DataElementConceptId">998.98.1</xsl:variable>
  <xsl:variable name="DataElementsCodeSystemId">998.98.2</xsl:variable>
  <xsl:variable name="DataElementConceptCodeSystemId">998.98.3</xsl:variable>
  <xsl:variable name="ValueDomainCodeSystemId">998.98.4</xsl:variable>
  <xsl:variable name="DataElementGroupCodeSystemId">998.98.5</xsl:variable>
  <!-- Includes -->
  <xsl:include href="uuid-from-id.xsl"/>
  <xsl:include href="gd2mifValueDomain.xsl"/>
  <xsl:include href="gd2mifDataElementConcept.xsl"/>
  <xsl:include href="gd2mifDataElement.xsl"/>
  <xsl:include href="gd2mifDataElementGroup.xsl"/>
  <!--
    Driver template. This starts at the root, identifies the immediate children of /package/content/vocabularyModel that need to be created, and calls the appropriate template for each type of child.
  -->
  <xsl:template match="/GlobalDefinitions">
    <!--
       Create the outer elements common to all eDCI Global Definition xml
       document instances.
    -->
    <xsl:element name="package">
      <xsl:attribute name="packageKind">artifact</xsl:attribute>
      <xsl:attribute name="title">eDCI Global Definitions</xsl:attribute>
      <xsl:attribute name="name">
        <xsl:value-of select="$EDCI_GlobalDefs"/>
      </xsl:attribute>
      <xsl:attribute name="isComplete">
        <xsl:value-of select="'false'"/>
      </xsl:attribute>
      <xsl:comment>
        To make this xml document describe how to find the MIF schema needed to validate 
        itself, include attributes like these in the start tag of the package 
        element:
           xmlns="urn:hl7-org:v3/mif" 
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
           xsi:schemaLocation="urn:hl7-org:v3/mif
           &lt;MifPath>mifPackage.xsd"
        where &lt;MifPath> stands for the path to the directory containing the MIF schema.
      </xsl:comment>
      <xsl:element name="content">
        <xsl:element name="vocabularyModel">
          <xsl:attribute name="packageKind">artifact</xsl:attribute>
          <xsl:attribute name="title">vocabularyModel</xsl:attribute>
          <xsl:attribute name="name">
            <xsl:value-of select="$EDCI_GlobalDefs"/>
          </xsl:attribute>
          <xsl:attribute name="representationKind">
            <xsl:value-of select="$EDCI_RepresentationKind"/>
          </xsl:attribute>
          <!--
            The activityTime of the globalDefinition element represents the time at which the 
            globalDefinition was composed. 
          -->
          <xsl:element name="header">
            <xsl:element name="renderingInformation">
              <xsl:attribute name="renderingTime">
                <xsl:value-of select="@activityTime"/>
              </xsl:attribute>
            </xsl:element>
          </xsl:element>
          <!--
             Now we are ready to construct elements for each type of
             information in a global definition. 
          -->
          <!--
                  ********* value Domains ****************
                If there are any valueDomains:
                1. Create a codesystem for each value Domain
          -->
          <xsl:comment>eDCI value Domains</xsl:comment>
          <xsl:if test="ValueDomain">
            <xsl:for-each select="ValueDomain">
              <xsl:call-template name="gd2mifValueDomain"/>
            </xsl:for-each>
            <!-- Write out Subsets of the Value Domains, if any exist -->
            <xsl:for-each select="ValueDomain/EVDSubset">
              <xsl:call-template name="EVDSubset"/>
            </xsl:for-each>
          </xsl:if>
          <!--
               ********** Data Element concepts ************
          If there are any DataElementconcepts:
          1. create a single code system to hold a list of concepts.
           -->
          <xsl:comment>eDCI DataElementConcepts</xsl:comment>
          <xsl:if test="DataElementConcept">
            <xsl:element name="codeSystem">
              <xsl:attribute name="packageKind">artifact</xsl:attribute>
              <xsl:attribute name="title">DataElementConcepts</xsl:attribute>
              <xsl:attribute name="name">
                <xsl:value-of select="'Data Element concepts'"/>
              </xsl:attribute>
              <xsl:attribute name="codeSystemType">I</xsl:attribute>
              <xsl:attribute name="codeSystemId">
                <xsl:value-of select="$DataElementConceptCodeSystemId"/>
              </xsl:attribute>
              <xsl:attribute name="releaseId">
                <xsl:value-of select="$EDCI_Release"/>
              </xsl:attribute>
              <!--
              2. process each DataElementconcept as a concept in the code system.
              -->
              <xsl:for-each select="DataElementConcept">
                <xsl:call-template name="gd2mifDataElementConcept"/>
              </xsl:for-each>
            </xsl:element>
          </xsl:if>
          <!--
                ****** Data Elements ********************
            If there are any DataElements:
            1. create a code system to hold a list of concepts.
           -->
          <xsl:comment>eDCI DataElements</xsl:comment>
          <xsl:if test="DataElement">
            <xsl:element name="codeSystem">
              <xsl:attribute name="packageKind">artifact</xsl:attribute>
              <xsl:attribute name="name">
                <xsl:value-of select="'DataElements'"/>
              </xsl:attribute>
              <xsl:attribute name="codeSystemType">I</xsl:attribute>
              <xsl:attribute name="codeSystemId">
                <xsl:value-of select="$DataElementsCodeSystemId"/>
              </xsl:attribute>
              <xsl:attribute name="releaseId">
                <xsl:value-of select="$EDCI_Release"/>
              </xsl:attribute>
              <!--
               2. Process each DataElement as a concept in the code system.
               -->
              <xsl:for-each select="DataElement">
                <xsl:call-template name="gd2mifDataElement"/>
              </xsl:for-each>
            </xsl:element>
          </xsl:if>
          <!--
              ********* Data Element Groups ***********
          If there are any DataElementGroups:
          1. create a value set for each group
           -->
          <xsl:comment>eDCI DataElementGroups</xsl:comment>
          <xsl:if test="DataElementGroup">
            <xsl:for-each select="DataElementGroup">
              <xsl:call-template name="gd2mifDataElementGroup"/>
            </xsl:for-each>
          </xsl:if>
          <!--
            * close all the containing elements, back up to the root.
          -->
        </xsl:element>
      </xsl:element>
    </xsl:element>
  </xsl:template>
  <!--
  
  
  -->
  <xsl:template name="writeDescriptionText">
    <!-- Writes a name-value pair, but only if the value exists. This avoids 
         writing a text for an optional Global Definition attribute 
         that has been omitted. 
     -->
    <xsl:param name="name"/>
    <xsl:param name="value"/>
    <xsl:if test="$value">
      <xsl:element name="text">
        <xsl:value-of select="concat($name, ':', $value)"/>
      </xsl:element>
    </xsl:if>
  </xsl:template>
</xsl:stylesheet>