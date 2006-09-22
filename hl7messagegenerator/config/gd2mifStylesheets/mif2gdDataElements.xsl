<?xml version="1.0"?>
<xsl:stylesheet version="1.0"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:template name="mif2gdDataElements">
    <xsl:param name="codeSystem"/>
    <xsl:for-each select="$codeSystem/conceptCode">
      <xsl:element name="DataElement">
        <xsl:attribute name="Name">
          <xsl:value-of select="@printName"/>
        </xsl:attribute>
        <xsl:attribute name="GUID">
          <xsl:value-of select="@code"/>
        </xsl:attribute>
        <xsl:if test="conceptProperty[@propertyCode='Description']">
          <xsl:attribute name="Description">
            <xsl:value-of select="conceptProperty[@propertyCode='Description']/@propertyValue"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:if test="conceptProperty[@propertyCode='Definition']">
          <xsl:attribute name="Definition">
            <xsl:value-of select="conceptProperty[@propertyCode='Definition']/@propertyValue"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:if test="conceptProperty[@propertyCode='ValueDomainGUID']">
          <xsl:attribute name="ValueDomainGUID">
            <xsl:value-of select="conceptProperty[@propertyCode='ValueDomainGUID']/@propertyValue"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:if test="conceptProperty[@propertyCode='EVDSubsetId']">
          <xsl:attribute name="EVDSubsetId">
            <xsl:value-of select="conceptProperty[@propertyCode='EVDSubsetId']/@propertyValue"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:if test="conceptProperty[@propertyCode='Namespace']">
          <xsl:attribute name="Namespace">
            <xsl:value-of select="conceptProperty[@propertyCode='Namespace']/@propertyValue"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:if test="conceptProperty[@propertyCode='DataElementConceptGUID']">
          <xsl:attribute name="DataElementConceptGUID">
            <xsl:value-of select="conceptProperty[@propertyCode='DataElementConceptGUID']/@propertyValue"/>
          </xsl:attribute>
        </xsl:if>
        <!-- Retrieve DataElementTexts (should be at least one) -->
        <!--        <xsl:for-each select="annotations/documentation/description[starts-with(text,'DataElementText')]"> -->
        <xsl:for-each select="annotations/documentation/description/text[starts-with(.,'DataElementText')]">
          <xsl:element name="DataElementText">
            <xsl:attribute name="Language">
              <xsl:value-of select="substring-before(substring-after(.,'Language::'), '::Language')"/>
            </xsl:attribute>
            <xsl:attribute name="Prompt">
              <xsl:value-of select="substring-before(substring-after(.,'Prompt::'), '::Prompt')"/>
            </xsl:attribute>
          </xsl:element>
        </xsl:for-each>
        <!-- Retrieve AlternateDesignations -->
        <xsl:for-each select="annotations/documentation/description/text[starts-with(.,'AlternateDesignation')]">
          <xsl:element name="AlternateDesignation">
            <xsl:attribute name="Language">
              <xsl:value-of select="substring-before(substring-after(.,'Language::'), '::Language')"/>
            </xsl:attribute>
            <xsl:attribute name="Type">
              <xsl:value-of select="substring-before(substring-after(.,'Type::'), '::Type')"/>
            </xsl:attribute>
            <xsl:attribute name="Name">
              <xsl:value-of select="substring-before(substring-after(.,'Name::'), '::Name')"/>
            </xsl:attribute>
          </xsl:element>
        </xsl:for-each>
      </xsl:element>
    </xsl:for-each>
  </xsl:template>
</xsl:stylesheet>