<?xml version="1.0"?>
<xsl:stylesheet version="1.0" 
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:template name="gd2mifDataElementGroup">
    <!-- Create a value set for the group -->
    <xsl:element name="valueSet">
      <!-- supply values for MIF required attributes, providing dummies if needed. -->
      <xsl:attribute name="packageKind">artifact</xsl:attribute>
      <xsl:attribute name="title">DataElementGroup</xsl:attribute>
      <xsl:attribute name="name">
        <xsl:value-of select="@Name"/>
      </xsl:attribute>
      <xsl:attribute name="valueSetId">
        <xsl:value-of select="$DataElementGroupCodeSystemId"/>
      </xsl:attribute>
      <xsl:attribute name="basedOnCodeSystem">
        <xsl:value-of select="$DataElementsCodeSystemId"/>
      </xsl:attribute>
      <!-- Create description texts containing <name>:<value> pairs for remaining GD domain attributes -->
      <xsl:element name="annotations">
        <xsl:element name="documentation">
          <xsl:element name="description">
            <xsl:call-template name="writeDescriptionText">
              <xsl:with-param name="name" select="'GUID'"/>
              <xsl:with-param name="value" select="@GUID"/>
            </xsl:call-template>
            <xsl:call-template name="writeDescriptionText">
              <xsl:with-param name="name" select="'Description'"/>
              <xsl:with-param name="value" select="@Description"/>
            </xsl:call-template>
            <xsl:call-template name="writeDescriptionText">
              <xsl:with-param name="name" select="'Namespace'"/>
              <xsl:with-param name="value" select="@Namespace"/>
            </xsl:call-template>
          </xsl:element>
        </xsl:element>
      </xsl:element>
      <!-- Create a referencedCode element for each member of the Group -->
      <xsl:for-each select="Member">
        <xsl:sort select="@DataElementGUID"/>
        <xsl:call-template name="DataElementGroupMember"/>
      </xsl:for-each>
    </xsl:element>
  </xsl:template>
  <!--

 -->
  <xsl:template name="DataElementGroupMember">
    <xsl:comment>eDCI Data Element Group Member</xsl:comment>
    <xsl:element name="referencedCode">
      <!-- supply values for MIF required attributes, providing dummies if needed. -->
      <xsl:attribute name="conceptCode">
        <xsl:value-of select="@DataElementGUID"/>
      </xsl:attribute>
    </xsl:element>
  </xsl:template>
</xsl:stylesheet>