<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" version="2.0">
    <!-- Version 4 Added 
        Trigger Action CDE ID version -->
    <!-- Version 7 added 
        adminstrative information to all objects that require it in caDSR -->
    <!-- Version 8 added 
        generate a question/checkAllThatApply (deprecated) element based on text in question/instruction/preferred-definition-->
    <!-- Version 9 added  
        generate a question/usageCategory element based on the text in the modules/long-name field
        generate a dataElement/shortName EMPTY tag
        generate a form/disease EMPTY tag 
        generate a valueDomain/shortName EMPTY tag
        changed mapping of module.longName
        fixes spelling of current cart questionRepititions -> questionRepetitions in transformed cart -->
    <!-- Version 10 add remaining fields that are planned for 4.1  
        generate a question/dataElement/dataElementDerivation (see dataElement template)
        generate a dataElement/designation 
        add module/preferredDefinition-->
    <!-- Version 11 change tag from question/checkAllThatApply to question/multiValue 
        add "choose all" to the list of text that could inidcate "yes" for this attribute 
        add default value for question/usageCategory/usageType = "None" -->
    <!-- Version 12 add extraction of isEditable for Question repetitions -->
    <!-- Version 13 
         add creation of valueDomain/type, values are Enumerated or NonEnumerated -->
    <!-- Version 14
        add dataElement/cdeBrowserLink, generate from hueristic -->
    <!-- Version 15 
         fix to valueDomain/formatName, changed from value-domain/format-name to value-domain/display-format 
         add valueDomain/valueDomainConcept for the Primary Concept of the Value domain
         add valueDomain/nciTermBrowserLink, for the primary conncept of the Value Domain, gnerated by hueristic (still working on best format)
         add valueMeaning/designation 
         add designation/classification -->
    <!-- Version 16
         make usageCategory a module attribute instead of a question attribute.  changed question/usgaeCategory to module/usageCategory based on walk through 
         added componentDataElement/displayOrder 
         added module/publicID and module/version-->
    <!-- Version 17
         transform cart dates into xs:dateTime datatype format -->
    <!-- Version 18 
         change test for instructions when multiValue from Uppercase "Report all" to "report all",and "Include all" to "include all" 
         fix usageCategory/usageType xPath expression
         add transformation to set usageCategory/rule based on Module instruction -->
    <!-- Version 19
         change order of protocol elements, moving leadOrganization to the top, to match xsdV16 -->
    <xsl:output indent="yes" exclude-result-prefixes="xsi"/>
    <xsl:template match="/">
        <xsl:apply-templates select="*"/>
    </xsl:template>

    <xsl:template match="form-transfer-object">
        <xsl:element name="form">
            <xsl:element name="context">
                <xsl:value-of select="./context-name"/>
            </xsl:element>
            <xsl:element name="createdBy">
                <xsl:value-of select="created-by"/>
            </xsl:element>
            <xsl:element name="dateModified"><!--  e.g. 2012-08-17 10:59:57.0 trabsform to xs:dateTime format 2001-10-26T21:32:52.12679-->
                <xsl:value-of select="concat(substring(date-modified, 1, 10), 'T', substring(date-modified, 12, 10))"/>  
            </xsl:element>
            <xsl:element name="longName">
                <xsl:value-of select="./long-name"/>
            </xsl:element>
            <xsl:element name="preferredDefinition">
                <xsl:value-of select="./preferred-definition"/>
            </xsl:element>
            <xsl:element name="publicID">
                <xsl:value-of select="./@public-id"/>
            </xsl:element>
            <xsl:element name="version">
                <xsl:value-of select="./version"/>
            </xsl:element>
            <xsl:apply-templates select="registrationStatus"/>
            <xsl:element name="workflowStatusName">
                <xsl:value-of select="./asl-name"/>
            </xsl:element>
            <xsl:element name="categoryName">
                <xsl:value-of select="./form-category"/>
            </xsl:element>
            <xsl:element name="disease"/>
            <xsl:element name="type">
                <xsl:value-of select="./form-type"/>
            </xsl:element>
            <xsl:element name="designation"/>
            <xsl:element name="headerInstruction">
                <xsl:element name="text">
                    <xsl:value-of select="normalize-space(instruction/preferred-definition)"/>
                </xsl:element>
            </xsl:element>
            <xsl:element name="footerInstruction">
                <xsl:element name="text">
                    <xsl:value-of
                        select="normalize-space(./footer-instructions/preferred-definition)"/>
                </xsl:element>
            </xsl:element>
            <xsl:apply-templates select="*"/>
        </xsl:element>
    </xsl:template>

    <xsl:template match="instruction[parent::form-transfer-object]"/>
    <xsl:template match="context[parent::form-transfer-object]"/>

    <xsl:template match="registrationStatus">
        <xsl:element name="registrationStatus">
            <xsl:value-of select="."/>
        </xsl:element>
    </xsl:template>

    <xsl:template match="@xsi:type"/>

    <xsl:template match="modules">
        <xsl:element name="module">
            <xsl:element name="displayOrder">
                <xsl:value-of select="./@display-order"/>
            </xsl:element>
            <xsl:element name="maximumModuleRepeat">
                <xsl:value-of select="./@number-of-repeats"/>
            </xsl:element>
            <xsl:element name="longName">
                <xsl:value-of select="long-name"/>
            </xsl:element>
            <xsl:element name="instruction">
                <xsl:element name="text">
                    <xsl:value-of select="normalize-space(instruction/preferred-definition)"/>
                </xsl:element>
            </xsl:element>
            <xsl:element name="preferredDefinition">
                <xsl:value-of select="preferred-definition"/>
            </xsl:element>
            <xsl:element name="publicID">
                <xsl:value-of select="./@public-id"/>
            </xsl:element>
            <xsl:element name="version">
                <xsl:value-of select="version"/>
            </xsl:element>
            <!-- move usageCategory to MODULE level based on walk-through 10/01/2012 -->
            <!-- set usageCategory.rule to module instruction -->
            <xsl:element name="usageCategory">
                <xsl:element name="usageType">
                    <xsl:choose>
                        <xsl:when test="contains(lower-case(long-name), 'mandatory')">
                            <xsl:text>Mandatory</xsl:text>
                        </xsl:when>
                        <xsl:when test="contains(lower-case(long-name), 'optional')">
                            <xsl:text>Optional</xsl:text>
                        </xsl:when>
                        <xsl:when test="contains(lower-case(long-name), 'conditional')">
                            <xsl:text>Conditional</xsl:text>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:text>None</xsl:text>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:element>
                <xsl:element name="rule">
                    <xsl:value-of select="/instruction/preferred-definition"/>
                </xsl:element>
            </xsl:element>
            <xsl:apply-templates select="questions"/>
            <xsl:apply-templates select="trigger-actions"/>
        </xsl:element>
    </xsl:template>

    <xsl:template match="questions">
        <xsl:element name="question">
            <xsl:element name="isDerived">
                <xsl:value-of select="@de-derived"/>
            </xsl:element>
            <xsl:element name="displayOrder">
                <xsl:value-of select="@display-order"/>
            </xsl:element>
            <xsl:element name="questionText">
                <xsl:value-of select="normalize-space(long-name)"/>
            </xsl:element>
            <xsl:element name="instruction">
                <xsl:element name="text">
                    <xsl:value-of select="normalize-space(instruction/preferred-definition)"/>
                </xsl:element>
            </xsl:element>
            <xsl:apply-templates select="default-valid-value"/>
            <xsl:element name="isEditable">
                <xsl:choose>
                    <xsl:when test="@editable = 'true'">
                        <xsl:text>Yes</xsl:text>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:text>No</xsl:text>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:element>
            <xsl:element name="isMandatory">
                <xsl:choose>
                    <xsl:when test="../questions/@mandatory = 'true'">
                        <xsl:text>Yes</xsl:text>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:text>No</xsl:text>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:element>
            <xsl:element name="multiValue">
                <xsl:choose>
                    <xsl:when
                        test="contains(lower-case(instruction/preferred-definition), 'check all')">
                        <xsl:text>Yes</xsl:text>
                    </xsl:when>
                    <xsl:when
                        test="contains(lower-case(instruction/preferred-definition), 'mark all')">
                        <xsl:text>Yes</xsl:text>
                    </xsl:when>
                    <xsl:when
                        test="contains(lower-case(instruction/preferred-definition), 'select all')">
                        <xsl:text>Yes</xsl:text>
                    </xsl:when>
                    <xsl:when
                        test="contains(lower-case(instruction/preferred-definition), 'choose all')">
                        <xsl:text>Yes</xsl:text>
                    </xsl:when>
                    <xsl:when
                        test="contains(lower-case(instruction/preferred-definition), 'all that')">
                        <xsl:text>Yes</xsl:text>
                    </xsl:when>
                    <xsl:when
                        test="contains(lower-case(instruction/preferred-definition), 'enter all')">
                        <xsl:text>Yes</xsl:text>
                    </xsl:when>
                    <xsl:when
                        test="contains(lower-case(instruction/preferred-definition), 'report all')">
                        <xsl:text>Yes</xsl:text>
                    </xsl:when>
                    <xsl:when
                        test="contains(lower-case(instruction/preferred-definition), 'include all')">
                        <xsl:text>Yes</xsl:text>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:text>No</xsl:text>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:element>
            <xsl:apply-templates select="data-element"/>
            <xsl:apply-templates select="valid-values"/>
            <xsl:apply-templates select="question-repititions"/>
        </xsl:element>
    </xsl:template>

    <xsl:template match="data-element">
        <xsl:element name="dataElement">
            <xsl:element name="longName">
                <xsl:value-of select="long-name"/>
            </xsl:element>
            <xsl:element name="shortName"/>
            <xsl:element name="publicID">
                <xsl:value-of select="CDEId"/>
            </xsl:element>
            <xsl:element name="version">
                <xsl:value-of select="version"/>
            </xsl:element>
            <xsl:element name="workflowStatusName">
                <xsl:value-of select="asl-name"/>
            </xsl:element>
            <xsl:element name="preferredDefinition">
                <xsl:value-of select="normalize-space(../preferred-definition)"/>
            </xsl:element>
            <xsl:element name="designation"/>
            <xsl:apply-templates select="value-domain"/>
            <xsl:choose>
                <xsl:when test="parent::questions/@de-derived = 'true'">
                    <xsl:element name="dataElementDerivation">
                        <xsl:element name="type"/>
                        <xsl:element name="methods"/>
                        <xsl:element name="componentDataElement">
                            <xsl:element name="usageCategory"/>
                            <xsl:element name="displayOrder">0</xsl:element>
                            <xsl:element name="dataElement">
                                <xsl:element name="publicID">0</xsl:element>
                                <xsl:element name="version">0</xsl:element>
                            </xsl:element>
                        </xsl:element>
                    </xsl:element>
                </xsl:when>
                <xsl:otherwise/>
            </xsl:choose>
            <xsl:apply-templates select="referece-docs"/>
            <!-- generate url to link to CDE Browser for the data element -->
            <xsl:element name="cdeBrowserLink">
                <xsl:variable name="baseURL">https://cdebrowser.nci.nih.gov/CDEBrowser/search?elementDetails=9%26FirstTimer=0%26PageId=ElementDetailsGroup&amp;publicId=</xsl:variable>
                <xsl:variable name="publicIdValue" select="CDEId"/>
                <xsl:variable name="attributeName">&amp;version=</xsl:variable>
                <xsl:variable name="value" select="version"/>
                
                <xsl:value-of select="concat($baseURL, $publicIdValue, $attributeName, $value)"/>
               
            </xsl:element>
        </xsl:element>
    </xsl:template>

    <xsl:template match="context">
        <xsl:element name="context">
            <xsl:value-of select="./name"/>
        </xsl:element>
    </xsl:template>

    <xsl:template match="value-domain">
        <xsl:variable name="VDid" select="./@public-id"/>
        <xsl:element name="valueDomain">
            <xsl:element name="longName">
                <xsl:value-of select="long-name"/>
            </xsl:element>
            <xsl:element name="shortName"/>
            <xsl:element name="publicID">
                <xsl:value-of select="@public-id"/>
            </xsl:element>
            <xsl:element name="version">
                <xsl:value-of select="version"/>
            </xsl:element>
            <xsl:element name="type">
                <xsl:choose>
                    <xsl:when test="ancestor::questions/valid-values">Enumerated</xsl:when>
                    <xsl:otherwise>NonEnumerated</xsl:otherwise>
                </xsl:choose>
            </xsl:element>
            <xsl:apply-templates select="context"/>
            <xsl:element name="workflowStatusName">
                <xsl:value-of select="asl-name"/>
            </xsl:element>
            <xsl:element name="datatypeName">
                <xsl:value-of select="datatype"/>
            </xsl:element>
            <xsl:element name="decimalPlace">
                <xsl:value-of select="decimal-place"/>
            </xsl:element>
            <xsl:element name="formatName">
                <xsl:value-of select="display-format"/>
            </xsl:element>
            <xsl:element name="highValueNumber">
                <xsl:value-of select="high-value"/>
            </xsl:element>
            <xsl:element name="lowValueNumber">
                <xsl:value-of select="low-value"/>
            </xsl:element>
            <xsl:element name="maximumLengthNumber">
                <xsl:value-of select="max-length"/>
            </xsl:element>
            <xsl:element name="minimumLengthNumber">
                <xsl:value-of select="min-length"/>
            </xsl:element>
            <xsl:element name="UOMName">
                <xsl:value-of select="unit-of-measure"/>
            </xsl:element>
            <xsl:apply-templates select="concept-derivation-rule/component-concepts"/>
        </xsl:element>
    </xsl:template>

    <xsl:template match="default-valid-value">
        <xsl:element name="defaultValue">
            <xsl:value-of select="long-name"/>
        </xsl:element>
    </xsl:template>


    <xsl:template match="valid-values">
        <xsl:element name="validValue">
            <xsl:element name="displayOrder">
                <xsl:value-of select="./@display-order"/>
            </xsl:element>
            <xsl:element name="value">
                <xsl:value-of select="./long-name"/>
            </xsl:element>
            <xsl:element name="meaningText">
                <xsl:value-of select="./form-value-meaning-text"/>
            </xsl:element>
            <xsl:element name="description">
                <xsl:value-of select="./form-value-meaning-desc"/>
            </xsl:element>
            <xsl:element name="instruction">
                <xsl:element name="text">
                    <xsl:value-of select="./instruction/preferred-definition"/>
                </xsl:element>
            </xsl:element>
            <xsl:apply-templates select="value-meaning"/>
            <xsl:apply-templates select="trigger-actions"/>
        </xsl:element>
    </xsl:template>

    <xsl:template match="concept-derivation-rule/component-concepts">
        <!-- generate link to open the concept in NCI Browser -->
        <xsl:variable name="baseURL">http://ncit.nci.nih.gov/ncitbrowser/ConceptReport.jsp?dictionary=</xsl:variable>
        <xsl:variable name="dictionary" select="replace(concept/origin, ' ', '%20')"/>
        <xsl:variable name="identifier">&amp;code=</xsl:variable>
        <xsl:variable name="code" select=".[./@is-primary = 'true']/concept/code"/>
        
        <xsl:element name="valueDomainConcept">
            <xsl:value-of select=".[./@is-primary = 'true']/concept/code"/>
        </xsl:element>
        <xsl:element name="nciTermBrowserLink">
            <xsl:value-of
                select="concat($baseURL,$dictionary, $identifier, $code)"/>
        </xsl:element>
    </xsl:template>

    <xsl:template match="value-meaning">
        <xsl:element name="valueMeaning">
            <xsl:element name="publicID">
                <xsl:value-of select="./@public-id"/>
            </xsl:element>
            <xsl:element name="version">
                <xsl:value-of select="./version"/>
            </xsl:element>
            <xsl:apply-templates select="designations"/>
        </xsl:element>
    </xsl:template>
    
    <xsl:template match="designations">
        <xsl:element name="designation">
            <xsl:element name="createdBy"/>
            <xsl:element name="dateCreated">2012-01-01T00:00:00.0</xsl:element>
            <xsl:element name="dateModified">2012-01-01T00:00:00.0</xsl:element>
            <xsl:element name="modifiedBy"/>
            <xsl:element name="languageName">
                <xsl:value-of select="language"/>
            </xsl:element>
            <xsl:element name="name">
                <xsl:value-of select="name"/>
            </xsl:element>
            <xsl:element name="type">
                <xsl:value-of select="type"/>
            </xsl:element> 
            <xsl:element name="context">
                <xsl:value-of select="context/name"/>
            </xsl:element>
            <xsl:apply-templates select="cs-csis"/>
        </xsl:element>
    </xsl:template>
    
    <xsl:template match="cs-csis">
        <xsl:element name="classification">
            <xsl:element name="name">
                <xsl:value-of select="class-scheme-long-name"/>
            </xsl:element>
            <xsl:element name="publicID">
                <xsl:value-of select="cs-iD"/>
            </xsl:element>
            <xsl:element name="version">
                <xsl:value-of select="cs-version"/>
            </xsl:element>
            <xsl:element name="preferredDefinition">
                <xsl:value-of select="class-scheme-definition"/>
            </xsl:element>
            <xsl:element name="classificationSchemeItem">
                <xsl:element name="name">
                    <xsl:value-of select="class-scheme-item-name"></xsl:value-of>
                </xsl:element>
            <xsl:element name="publicID">
                <xsl:value-of select="csi-id"/>
            </xsl:element>
                <xsl:element name="version">
                    <xsl:value-of select="csi-version"/>
                </xsl:element>
            <xsl:element name="type">
                <xsl:value-of select="class-scheme-item-type"/>
            </xsl:element>
                <xsl:element name="preferredDefinition">
                    <xsl:value-of select="csi-description"/>
                </xsl:element>
            </xsl:element>
        </xsl:element>
    </xsl:template>

    <xsl:template match="trigger-actions">
        <xsl:element name="triggerAction">
            <xsl:apply-templates select="action-target/module"/>
            <xsl:apply-templates select="instruction"/>

            <xsl:element name="targetQuestionDisplayOrder">
                <xsl:value-of select="action-target/@display-order"/>
            </xsl:element>
            <xsl:element name="targetDataElementPublicID">
                <xsl:value-of select="action-target/data-element/CDEId"/>
            </xsl:element>
            <xsl:element name="targetDataElementVersion">
                <xsl:value-of select="action-target/data-element/version"/>
            </xsl:element>
            <xsl:apply-templates select="protocols"/>
        </xsl:element>
    </xsl:template>

    <xsl:template match="question-repititions">
        <xsl:element name="questionRepetitions">
            <xsl:element name="repeatSequenceNumber">
                <xsl:value-of select="./@repeat-sequence"/>
            </xsl:element>
            <xsl:element name="defaultValidValue">
                <xsl:value-of
                    select="normalize-space(./default-valid-value/form-value-meaning-text)"/>
            </xsl:element>
            <xsl:element name="isEditable">
                <xsl:choose>
                    <xsl:when test="@editable = 'true'">
                        <xsl:text>Yes</xsl:text>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:text>No</xsl:text>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:element>
        </xsl:element>
    </xsl:template>

    <xsl:template match="instruction">
        <xsl:element name="instruction">
            <xsl:element name="text">
                <xsl:value-of select="normalize-space(.)"/>
            </xsl:element>
        </xsl:element>
    </xsl:template>

    <xsl:template match="action-target/module">
        <xsl:element name="targetModuleDisplayOrder">
            <xsl:value-of select="./@display-order"/>
        </xsl:element>
        <xsl:element name="targetModuleName">
            <xsl:value-of select="normalize-space(./long-name)"/>
        </xsl:element>
    </xsl:template>

    <xsl:template match="protocols">
        <xsl:element name="protocol">
            <xsl:apply-templates select="lead-org"/>
            <xsl:apply-templates select="phase"/>
            <xsl:apply-templates select="type"/>
            <xsl:apply-templates select="protocol-id"/>
            <xsl:element name="longName">
                <xsl:value-of select="normalize-space(long-name)"/>
            </xsl:element>
            <xsl:apply-templates select="context"/>
            <xsl:element name="shortName">
                <xsl:value-of select="normalize-space(preferred-name)"/>
            </xsl:element>
            <xsl:element name="preferredDefinition">
                <xsl:value-of select="normalize-space(preferred-definition)"/>
            </xsl:element>
        </xsl:element>
    </xsl:template>

    <xsl:template match="lead-org">
        <xsl:element name="leadOrganization">
            <xsl:value-of select="."/>
        </xsl:element>
    </xsl:template>

    <xsl:template match="protocol-id">
        <xsl:element name="protocolID">
            <xsl:value-of select="."/>
        </xsl:element>
    </xsl:template>

    <xsl:template match="phase">
        <xsl:element name="phase">
            <xsl:value-of select="."/>
        </xsl:element>
    </xsl:template>

    <xsl:template match="type">
        <xsl:element name="type">
            <xsl:value-of select="."/>
        </xsl:element>
    </xsl:template>

    <xsl:template match="referece-docs">
        <xsl:element name="referenceDocument">
            <xsl:element name="name">
                <xsl:value-of select="doc-name"/>
            </xsl:element>
            <xsl:element name="type">
                <xsl:value-of select="doc-type"/>
            </xsl:element>
            <xsl:apply-templates select="context"/>
            <xsl:element name="doctext">
                <xsl:value-of select="doc-text"/>
            </xsl:element>
            <xsl:apply-templates select="url"/>
            <xsl:apply-templates select="attachments"/>
        </xsl:element>
    </xsl:template>

    <xsl:template match="url">
        <xsl:element name="URL">
            <xsl:value-of select="."/>
        </xsl:element>
    </xsl:template>

    <xsl:template match="attachments">
        <xsl:element name="attachments">
            <xsl:element name="name">
                <xsl:value-of select="./name"/>
            </xsl:element>
            <xsl:element name="mimeType">
                <xsl:value-of select="./mime-type"/>
            </xsl:element>
            <xsl:element name="size">
                <xsl:value-of select="./@doc-size"/>
            </xsl:element>
        </xsl:element>
    </xsl:template>
    <xsl:template match="registrationStatus">
        <xsl:value-of select="."/>
    </xsl:template>
    <xsl:template match="*"/>
    <xsl:template match="@*"/>

</xsl:stylesheet>
