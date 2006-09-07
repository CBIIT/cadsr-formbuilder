<?xml version="1.0"?>
<xsl:stylesheet version="1.0" 
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:template name="gd2mifDataElementConcept">
    <!--
    Create a concept code for this Data Element Concept.
   -->
    <xsl:element name="conceptCode">
      <!-- supply values for MIF required attributes, providing dummies if needed. -->
      <xsl:attribute name="internalId">
        <!--
        <xsl:value-of select="$EDCI_Dummy"/>
        -->
        <xsl:call-template name="uuid-from-id">
          <xsl:with-param name="id">
            <xsl:value-of select="generate-id()"/>
          </xsl:with-param>
        </xsl:call-template>
      </xsl:attribute>
      <xsl:attribute name="code">
        <xsl:value-of select="@GUID"/>
      </xsl:attribute>
      <xsl:attribute name="printName">
        <xsl:value-of select="@Name"/>
      </xsl:attribute>
      <!-- Create property name-value pairs for remaining GD domain attributes. -->
      <xsl:call-template name="writeConceptProperty">
        <xsl:with-param name="code" select="'Definition'"/>
        <xsl:with-param name="value" select="@Definition"/>
      </xsl:call-template>
      <xsl:call-template name="writeConceptProperty">
        <xsl:with-param name="code" select="'Description'"/>
        <xsl:with-param name="value" select="@Description"/>
      </xsl:call-template>

    </xsl:element>
  </xsl:template>
</xsl:stylesheet>