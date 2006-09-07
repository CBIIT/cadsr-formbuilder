<?xml version="1.0"?>
<xsl:stylesheet version="1.0"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:template name="mif2gdDataElementGroups">
    <xsl:param name="valueSet"/>
    <xsl:element name="DataElementGroup">
      <xsl:attribute name="Name">
        <xsl:value-of select="@name"/>
      </xsl:attribute>

      <xsl:attribute name="Namespace">
        <xsl:value-of select="substring-after(annotations/documentation/description/text[starts-with(.,'Namespace')], ':')"/>
      </xsl:attribute>
      <xsl:attribute name="Description">
        <xsl:value-of select="substring-after(annotations/documentation/description/text[starts-with(.,'Description')], ':')"/>
      </xsl:attribute>
      
      
      <xsl:call-template name="retrieveValueFromPair">
        <xsl:with-param name="attName">GUID</xsl:with-param>
      </xsl:call-template>
      <xsl:call-template name="retrieveValueFromPair">
        <xsl:with-param name="attName">Namespace</xsl:with-param>
      </xsl:call-template>
      <xsl:call-template name="retrieveValueFromPair">
        <xsl:with-param name="attName">Description</xsl:with-param>
      </xsl:call-template>
      
      <!-- There should be at least one member of the group -->
      <xsl:for-each select="referencedCode">
        <xsl:element name="Member">
          <xsl:attribute name="DataElementGUID">
            <xsl:value-of select="@conceptCode"/>
          </xsl:attribute>
        </xsl:element>
      </xsl:for-each>
    </xsl:element>
  </xsl:template>
</xsl:stylesheet>