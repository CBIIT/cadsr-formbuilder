<xsl:stylesheet version="1.0" 
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:template name="gd2mifValueDomain">
    <!-- Create a code system for this value domain -->
    <xsl:comment>eDCI Value Domain</xsl:comment>
    <xsl:element name="codeSystem">
      <!-- supply values for MIF required attributes, providing dummies if needed. -->
      <xsl:attribute name="packageKind">artifact</xsl:attribute>
      <xsl:attribute name="name">
        <xsl:value-of select="@Name"/>
      </xsl:attribute>
      <xsl:attribute name="codeSystemType">I</xsl:attribute>
      <xsl:attribute name="title">ValueDomain</xsl:attribute>
      <xsl:attribute name="codeSystemId">
        <xsl:value-of select="$ValueDomainCodeSystemId"/>
      </xsl:attribute>
      <xsl:attribute name="releaseId">
        <xsl:value-of select="$EDCI_Release"/>
      </xsl:attribute>
      <!-- Create descriptions for remaining GD domain attributes
        *  Note: cannot use conceptProperty code/value pairs here.
        *        Will have to fall back on <name>:<value>. Would put this in
        *        otherAnnotation, but cannot create a documentation without
        *        creating a description (20060605 edition of MIF).
         -->
      <xsl:element name="annotations">
        <xsl:element name="documentation">
          <xsl:element name="description">
            <xsl:call-template name="writeDescriptionText">
              <xsl:with-param name="name" select="'GUID'"/>
              <xsl:with-param name="value" select="@GUID"/>
            </xsl:call-template>
            <xsl:call-template name="writeDescriptionText">
              <xsl:with-param name="name" select="'SourceCodingSystemVersion'"/>
              <xsl:with-param name="value" select="@SourceCodingSystemVersion"/>
            </xsl:call-template>
            <xsl:call-template name="writeDescriptionText">
              <xsl:with-param name="name" select="'MaximumLength'"/>
              <xsl:with-param name="value" select="@MaximumLength"/>
            </xsl:call-template>
            <xsl:call-template name="writeDescriptionText">
              <xsl:with-param name="name" select="'IsEnumeratedFlag'"/>
              <xsl:with-param name="value" select="@IsEnumeratedFlag"/>
            </xsl:call-template>
            <xsl:call-template name="writeDescriptionText">
              <xsl:with-param name="name" select="'SourceCodingSystem'"/>
              <xsl:with-param name="value" select="@SourceCodingSystem"/>
            </xsl:call-template>
            <xsl:call-template name="writeDescriptionText">
              <xsl:with-param name="name" select="'DecimalPlaces'"/>
              <xsl:with-param name="value" select="@DecimalPlaces"/>
            </xsl:call-template>
            <xsl:call-template name="writeDescriptionText">
              <xsl:with-param name="name" select="'SourceCodingSystemGUID'"/>
              <xsl:with-param name="value" select="@SourceCodingSystemGUID"/>
            </xsl:call-template>
            <xsl:call-template name="writeDescriptionText">
              <xsl:with-param name="name" select="'Description'"/>
              <xsl:with-param name="value" select="@Description"/>
            </xsl:call-template>
            <xsl:call-template name="writeDescriptionText">
              <xsl:with-param name="name" select="'SasFormatName'"/>
              <xsl:with-param name="value" select="@SasFormatName"/>
            </xsl:call-template>
            <xsl:call-template name="writeDescriptionText">
              <xsl:with-param name="name" select="'Namespace'"/>
              <xsl:with-param name="value" select="@Namespace"/>
            </xsl:call-template>
            <xsl:call-template name="writeDescriptionText">
              <xsl:with-param name="name" select="'Datatype'"/>
              <xsl:with-param name="value" select="@Datatype"/>
            </xsl:call-template>
            <xsl:call-template name="writeDescriptionText">
              <xsl:with-param name="name" select="'SourceDomainId'"/>
              <xsl:with-param name="value" select="@SourceDomainId"/>
            </xsl:call-template>
          </xsl:element>
        </xsl:element>
      </xsl:element>
      <!-- Does this value domain have enumerated values? -->
      <xsl:if test="EVDElement">
        <!-- Create a conceptCode for any enumerated values in the domain -->
        <xsl:for-each select="EVDElement">
          <xsl:call-template name="EVDElement"/>
        </xsl:for-each>
      </xsl:if>
    </xsl:element>
  </xsl:template>
  <!--

  -->
  <xsl:template name="EVDElement">
    <!--
    Create a concept code for an enumerated value in a Value Domain.
   -->
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
        <xsl:value-of select="@value"/>
      </xsl:attribute>
      <xsl:attribute name="printName">
        <xsl:value-of select="$EDCI_Dummy"/>
      </xsl:attribute>
      <!-- The EVDElement will have 1 or more EVDElementText children. Process each -->
      <xsl:for-each select="EVDElementText">
        <xsl:call-template name="EVDElementText"/>
      </xsl:for-each>
    </xsl:element>
  </xsl:template>
  <!--

-->
  <xsl:template name="EVDElementText">
    <!-- Describe the language-specific texts associated with an enumerated value.
   * It would be great if we could use conceptCode/conceptDesignation here, 
   * but conceptDesignation doesn't have sufficient attributes to plug in to.
   * So, we create property code/value pair elements, organized by language.
  -->
    <xsl:element name="conceptProperty">
      <xsl:attribute name="language">
        <xsl:value-of select="@Language"/>
      </xsl:attribute>
      <xsl:attribute name="propertyCode">HelpText</xsl:attribute>
      <xsl:attribute name="propertyValue">
        <xsl:value-of select="@HelpText"/>
      </xsl:attribute>
    </xsl:element>
    <xsl:element name="conceptProperty">
      <xsl:attribute name="language">
        <xsl:value-of select="@Language"/>
      </xsl:attribute>
      <xsl:attribute name="propertyCode">ValueMeaning</xsl:attribute>
      <xsl:attribute name="propertyValue">
        <xsl:value-of select="@ValueMeaning"/>
      </xsl:attribute>
    </xsl:element>
    <xsl:element name="conceptProperty">
      <xsl:attribute name="language">
        <xsl:value-of select="@Language"/>
      </xsl:attribute>
      <xsl:attribute name="propertyCode">ValueMeaningDescription</xsl:attribute>
      <xsl:attribute name="propertyValue">
        <xsl:value-of select="@ValueMeaningDescription"/>
      </xsl:attribute>
    </xsl:element>
  </xsl:template>
  <!--


 -->
  <xsl:template name="EVDSubset">
    <!-- Create a codeSystem for an EVD Subset -->
    <xsl:comment>eDCI EVDSubset</xsl:comment>
    <xsl:element name="codeSystem">
      <!-- supply values for MIF required attributes, providing dummies if needed. -->
      <xsl:attribute name="packageKind">artifact</xsl:attribute>
      <xsl:attribute name="name">
        <xsl:value-of select="@Name"/>
      </xsl:attribute>
      <xsl:attribute name="codeSystemType">I</xsl:attribute>
      <xsl:attribute name="title">EVDSubset</xsl:attribute>
      <!-- Subset ID is not an OID, so provide a dummy here. -->
      <xsl:attribute name="codeSystemId">
        <xsl:value-of select="'99.99'"/>
      </xsl:attribute>
      <xsl:attribute name="releaseId">
        <xsl:value-of select="$EDCI_Release"/>
      </xsl:attribute>
      <!-- Create description texts containing <name>:<value> pairs for remaining GD domain attributes -->
      <xsl:element name="annotations">
        <xsl:element name="documentation">
          <xsl:element name="description">
            <xsl:call-template name="writeDescriptionText">
              <xsl:with-param name="name" select="'ValueDomainGUID'"/>
              <xsl:with-param name="value" select="../@GUID"/>
            </xsl:call-template>
            <xsl:call-template name="writeDescriptionText">
              <xsl:with-param name="name" select="'SubsetID'"/>
              <xsl:with-param name="value" select="@SubsetId"/>
            </xsl:call-template>
            <xsl:call-template name="writeDescriptionText">
              <xsl:with-param name="name" select="'RadioButtonLabel'"/>
              <xsl:with-param name="value" select="@RadioButtonLabel"/>
            </xsl:call-template>
          </xsl:element>
        </xsl:element>
      </xsl:element>
      <!-- Create a member for each element in the EVD Subset. -->
      <xsl:for-each select="ElementInSubset">
        <xsl:sort select="@SequenceNumber"/>
        <xsl:call-template name="ElementInSubset"/>
      </xsl:for-each>
    </xsl:element>
  </xsl:template>
  <!--

 -->
  <xsl:template name="ElementInSubset">
    <xsl:comment>eDCI enumerated Value Domain Element in Subset</xsl:comment>
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
        <xsl:value-of select="@value"/>
      </xsl:attribute>
      <xsl:attribute name="printName">
        <xsl:value-of select="$EDCI_Dummy"/>
      </xsl:attribute>
      <!-- Create description texts containing <name>:<value> pairs for remaining  attributes -->
      <xsl:element name="annotations">
        <xsl:element name="documentation">
          <xsl:element name="description">
            <xsl:call-template name="writeDescriptionText">
              <xsl:with-param name="name" select="'SequenceNumber'"/>
              <xsl:with-param name="value" select="@SequenceNumber"/>
            </xsl:call-template>
          </xsl:element>
        </xsl:element>
      </xsl:element>
    </xsl:element>
  </xsl:template>

</xsl:stylesheet>