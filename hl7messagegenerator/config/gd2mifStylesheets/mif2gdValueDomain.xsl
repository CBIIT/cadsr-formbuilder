<?xml version="1.0"?>
<xsl:stylesheet version="1.0"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform" >
  <xsl:template name="mif2gdValueDomain">
    <!-- Retrieve attribute values from codeSystem attributes -->
    <xsl:element name="ValueDomain">
      <xsl:attribute name="Name">
        <xsl:value-of select="@name"/>
      </xsl:attribute>
      <!-- 
        Retrieve the GUID for this Value Domain. 
        Hold on to GUID of this ValueDomain for identification of EVDSubsets below.
        Assign the GUID value to the output GUID attribute.
       -->
      <xsl:variable name="thisValueDomainGUID">
    <xsl:if test="annotations/documentation/description/text[starts-with(.,'GUID:')]">
      <xsl:value-of select="substring-after(
       annotations/documentation/description/text[starts-with(.,'GUID:')]
       , ':')"/>
    </xsl:if>
</xsl:variable>
      <xsl:attribute name="GUID"><xsl:value-of select="$thisValueDomainGUID"/></xsl:attribute>

      <!-- retrieve attributes transmitted as name:value pairs -->
      <xsl:call-template name="retrieveValueFromPair">
        <xsl:with-param name="attName">SourceCodingSystemVersion</xsl:with-param>
      </xsl:call-template>
      <xsl:call-template name="retrieveValueFromPair">
        <xsl:with-param name="attName">MaximumLength</xsl:with-param>
      </xsl:call-template>
      <xsl:call-template name="retrieveValueFromPair">
        <xsl:with-param name="attName">IsEnumeratedFlag</xsl:with-param>
      </xsl:call-template>
      <xsl:call-template name="retrieveValueFromPair">
        <xsl:with-param name="attName">SourceCodingSystem</xsl:with-param>
      </xsl:call-template>
      <xsl:call-template name="retrieveValueFromPair">
        <xsl:with-param name="attName">DecimalPlaces</xsl:with-param>
      </xsl:call-template>
      <xsl:call-template name="retrieveValueFromPair">
        <xsl:with-param name="attName">SourceCodingSystemGUID</xsl:with-param>
      </xsl:call-template>
      <xsl:call-template name="retrieveValueFromPair">
        <xsl:with-param name="attName">Description</xsl:with-param>
      </xsl:call-template>
      <xsl:call-template name="retrieveValueFromPair">
        <xsl:with-param name="attName">SasFormatName</xsl:with-param>
      </xsl:call-template>
      <xsl:call-template name="retrieveValueFromPair">
        <xsl:with-param name="attName">Namespace</xsl:with-param>
      </xsl:call-template>
      <xsl:call-template name="retrieveValueFromPair">
        <xsl:with-param name="attName">Datatype</xsl:with-param>
      </xsl:call-template>
      <xsl:call-template name="retrieveValueFromPair">
        <xsl:with-param name="attName">SourceDomainId</xsl:with-param>
      </xsl:call-template>
      <!-- process enumerated values, if any -->
      <xsl:if test="conceptCode">
        <xsl:for-each select="conceptCode">
          <xsl:element name="EVDElement">
            <xsl:attribute name="value">
              <xsl:value-of select="@code"/>
            </xsl:attribute>
            <!-- Create Text elements for this Element -->
            <xsl:call-template name="retrieveEVDElementTexts"/>
          </xsl:element>
        </xsl:for-each>
      </xsl:if>
      <!-- Process EVD Subsets, if any -->
      <xsl:for-each select="../codeSystem[@title='EVDSubset']">
        <xsl:variable name="ParentGUID" 
        select="substring-after(
          annotations/documentation/description/text[starts-with(.,'ValueDomainGUID')]
             , ':')"></xsl:variable>
        <xsl:if test="$thisValueDomainGUID = $ParentGUID">
          <xsl:call-template name="retrieveEVDSubset">
            <xsl:with-param name="subset" select="."/>
          </xsl:call-template>
        </xsl:if>
      </xsl:for-each>
    </xsl:element>
  </xsl:template>

  <!--
  
  -->
  <xsl:template name="retrieveEVDElementTexts">
    <!-- 
       * Each EVD Element has, at a minimum, one Text, describing its
       * valueMeaning, in one language. In a MIF representation, this is stored in a conceptProperty
       * named ValueMeaning, with attribute Language holding the language.
       * Additionally, for a given language, there may be additional conceptProperty
       * elements holding HelpText and ValueMeaningDescription.
       * This template constructs a Text element for each conceptProperty
       * holding ValueMeaning (one element for each language in which 
       * ValueMeaning is expressed). Then, it populates the rest of the
       * ouput Text element's attributes with the attributes from HelpText and ValueMeaningDescription 
       * conceptProperty elements that match on language, if present.
    -->
    <xsl:for-each select="conceptProperty[@propertyCode='ValueMeaning']">
      <xsl:variable name="propertyLanguage" select="@language"/>
      <xsl:element name="EVDElementText">
        <xsl:attribute name="Language">
          <xsl:value-of select="$propertyLanguage"/>
        </xsl:attribute>
        <xsl:attribute name="ValueMeaning">
          <xsl:value-of select="@propertyValue"/>
        </xsl:attribute>
        <xsl:if test="../conceptProperty[@language=$propertyLanguage and @propertyCode='HelpText']">
          <xsl:attribute name="HelpText">
            <xsl:value-of select="../conceptProperty[@language=$propertyLanguage and @propertyCode='HelpText']/@propertyValue"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:if test="../conceptProperty[@language=$propertyLanguage and @propertyCode='ValueMeaningDescription']">
          <xsl:attribute name="ValueMeaningDescription">
            <xsl:value-of select="../conceptProperty[@language=$propertyLanguage and @propertyCode='ValueMeaningDescription']/@propertyValue"/>
          </xsl:attribute>
        </xsl:if>
      </xsl:element>
    </xsl:for-each>
  </xsl:template>
  <!--
  
  -->
  <xsl:template name="retrieveEVDSubset">
    <!-- Creates elements for an EVD Subset and its members -->
    <xsl:param name="subset"/>
    <xsl:element name="EVDSubset">
      <xsl:attribute name="Name">
        <xsl:value-of select="@name"/>
      </xsl:attribute>
      <xsl:attribute name="SubsetId">
        <xsl:value-of 
          select="substring-after(
            annotations/documentation/description/text[starts-with(.,'SubsetID')]
            ,':')"/>
      </xsl:attribute>
      <xsl:attribute name="RadioButtonLabel">
        <xsl:value-of 
          select="substring-after(
            annotations/documentation/description/text[starts-with(.,'RadioButtonLabel')]
            ,':')"/>
      </xsl:attribute>
      <!-- Create elements for each EVD Subset member -->
      <xsl:for-each select="conceptCode">
        <xsl:element name="ElementInSubset">
          <xsl:attribute name="value">
            <xsl:value-of select="@code"/>
          </xsl:attribute>
          <xsl:attribute name="SequenceNumber">
            <xsl:value-of 
          select="substring-after(
            annotations/documentation/description/text[starts-with(.,'SequenceNumber')]
            ,':')"/>
          </xsl:attribute>
        </xsl:element>
      </xsl:for-each>
    </xsl:element>
  </xsl:template>
</xsl:stylesheet>