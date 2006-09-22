<?xml version="1.0"?>
<xsl:stylesheet version="1.0"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:template name="mif2gdDataElementConcepts">
  <xsl:param name="codeSystem"/>
    <xsl:for-each select="$codeSystem/conceptCode">
      <xsl:element name="DataElementConcept">
        <xsl:attribute name="Name">
          <xsl:value-of select="@printName"/>
        </xsl:attribute>
        <xsl:attribute name="GUID">
          <xsl:value-of select="@code"/>
        </xsl:attribute>
        <xsl:attribute name="Definition">
          <xsl:value-of select="conceptProperty[@propertyCode='Definition']/@propertyValue"/>
        </xsl:attribute>
        <xsl:attribute name="Description">
          <xsl:value-of select="conceptProperty[@propertyCode='Description']/@propertyValue"/>
        </xsl:attribute>
      </xsl:element>
    </xsl:for-each>
  </xsl:template>
</xsl:stylesheet>