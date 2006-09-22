<?xml version="1.0"?>
<xsl:stylesheet version="1.0" 
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:template name="gd2mifDataElement">
    <xsl:element name="conceptCode">
      <!-- supply values for MIF required attributes, providing dummies if needed. -->
      <xsl:attribute name="internalId">
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
      <!-- 
      * Store DataElementTexts and Alternate Designations in description/text elements.
      * Note: these have to precede conceptProperties because of the sequence
      * of content elements in conceptCode
       -->
      <xsl:if test="DataElementText or AlternateDesignation">
        <xsl:element name="annotations">
          <xsl:element name="documentation">
            <xsl:element name="description">
              <xsl:for-each select="DataElementText">
                <xsl:element name="text">
                  <!--
                  <xsl:value-of select="concat('DataElementText:', @Language, ':', @Prompt)"/>
                  -->
                  <xsl:value-of select="concat(
                    'DataElementText:'
                  , ' Language::',@Language, '::Language'
                  , ' Prompt::', @Prompt, '::Prompt')"/>
                </xsl:element>
              </xsl:for-each>
              <xsl:for-each select="AlternateDesignation">
                <xsl:element name="text">
                  <!--
                  <xsl:value-of select="concat('AlternateDesignation:', @Language, ':', @Type, ':', @Name)"/>
                -->
                  <xsl:value-of select="concat(
                  'AlternateDesignation:'
                , ' Language::', @Language, '::Language'
                , ' Type::', @Type, '::Type'
                , ' Name::', @Name, '::Name')"/>
                </xsl:element>
              </xsl:for-each>
            </xsl:element>
          </xsl:element>
        </xsl:element>
      </xsl:if>
      <!-- Create property name-value pairs for remaining GD domain attributes of DataElement. -->
      <xsl:call-template name="writeConceptProperty">
        <xsl:with-param name="code" select="'Definition'"/>
        <xsl:with-param name="value" select="@Definition"/>
      </xsl:call-template>
      <xsl:call-template name="writeConceptProperty">
        <xsl:with-param name="code" select="'ValueDomainGUID'"/>
        <xsl:with-param name="value" select="@ValueDomainGUID"/>
      </xsl:call-template>
      <xsl:call-template name="writeConceptProperty">
        <xsl:with-param name="code" select="'EVDSubsetId'"/>
        <xsl:with-param name="value" select="@EVDSubsetId"/>
      </xsl:call-template>
      <xsl:call-template name="writeConceptProperty">
        <xsl:with-param name="code" select="'Description'"/>
        <xsl:with-param name="value" select="@Description"/>
      </xsl:call-template>
      <xsl:call-template name="writeConceptProperty">
        <xsl:with-param name="code" select="'Namespace'"/>
        <xsl:with-param name="value" select="@Namespace"/>
      </xsl:call-template>
      <xsl:call-template name="writeConceptProperty">
        <xsl:with-param name="code" select="'DataElementConceptGUID'"/>
        <xsl:with-param name="value" select="@DataElementConceptGUID"/>
      </xsl:call-template>
    </xsl:element>
  </xsl:template>
  <!--
  
  
  -->
  <xsl:template name="writeConceptProperty">
    <!-- Writes a conceptProperty code-value pair, but only if the value exists. This avoids 
         writing a conceptProperty for an optional Global Definition attribute 
         that has been omitted. 
     -->
    <xsl:param name="code"/>
    <xsl:param name="value"/>
    <xsl:if test="$value">
      <xsl:element name="conceptProperty">
        <xsl:attribute name="propertyCode" >
          <xsl:value-of select="$code"/>
        </xsl:attribute>
        <xsl:attribute name="propertyValue">
          <xsl:value-of select="$value"/>
        </xsl:attribute>
      </xsl:element>
    </xsl:if>
  </xsl:template>
</xsl:stylesheet>