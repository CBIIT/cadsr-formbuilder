<?xml version="1.0" encoding="windows-1252" ?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <!-- This template wraps the ',' character with "" in the input string parameter -->
  <xsl:template name="wrapCommas">
      <xsl:param name="pString" />
      <xsl:if test="contains($pString,',')">
         <xsl:value-of select="substring-before($pString,',')" />
         <xsl:text>","</xsl:text>
         <xsl:call-template name="wrapCommas" >
            <xsl:with-param name="pString" select="substring-after($pString,',')" />
         </xsl:call-template>
      </xsl:if>
      <xsl:if test="contains($pString,',') = false">
         <xsl:value-of select="$pString"/>
      </xsl:if>
  </xsl:template>
  <!-- override the builtin rule for matching text -->
  <xsl:template match="text()|@*">
  </xsl:template>  
  <!-- This template formats an input date parameter as YYYYMMDDhhmmss -->
  <xsl:template name="formatDate">
     <xsl:param name="pDate" />
     <xsl:value-of select="substring($pDate,1,4)" />
     <xsl:value-of select="substring($pDate,6,2)" />
     <xsl:value-of select="substring($pDate,9,2)" />
     <xsl:value-of select="substring($pDate,12,2)" />
     <xsl:value-of select="substring($pDate,15,2)" />
     <xsl:value-of select="substring($pDate,18,2)" />     
  </xsl:template>
  
  <xsl:template name="newline">
<xsl:text>
</xsl:text>
  </xsl:template>

  <xsl:output method="text" />
  <xsl:template match="/">
    <xsl:apply-templates />
  </xsl:template>
  <!-- This template produces the INSTRUMENT segment of the csv file -->
  <xsl:template name="instrument" match="instrument">
    <xsl:text>INSTRUMENT</xsl:text>
    <xsl:text>,</xsl:text> <!-- id_root -->
    <xsl:value-of  select="GUID"/> 
    <xsl:text>,</xsl:text> <!-- id_extension -->
    <xsl:text>,</xsl:text> <!-- title -->
    <xsl:call-template name="wrapCommas"> 
      <xsl:with-param name="pString" select="name" />
    </xsl:call-template>
    <xsl:text>,</xsl:text> <!-- status code -->
    <xsl:text>,</xsl:text> <!-- creation_datetime -->
    <xsl:call-template name="formatDate">
      <xsl:with-param name="pDate" select="creation-timestamp" />
    </xsl:call-template>    
    <xsl:text>,</xsl:text> <!-- description_value -->
    <xsl:call-template name="wrapCommas"> 
      <xsl:with-param name="pString" select="description" />
    </xsl:call-template>    
    <xsl:text>,</xsl:text> <!-- clinical_study_title -->    
    <xsl:call-template name="wrapCommas"> 
      <xsl:with-param name="pString" select="clinical-study" />
    </xsl:call-template>        
    <xsl:text>,</xsl:text> <!-- clinical_area_title -->    
    <xsl:call-template name="wrapCommas"> 
      <xsl:with-param name="pString" select="clinical-area" />
    </xsl:call-template>
    <xsl:call-template name="newline"/>
    <xsl:apply-templates />
  </xsl:template>
  <!-- Template to produce the AUTHOR segment -->
  <xsl:template name="author" match="instrument/author" >
    <xsl:text>AUTHOR</xsl:text>
    <xsl:text>,NOT_MAPPED</xsl:text> <!-- time -->
    <xsl:text>,NOT_MAPPED</xsl:text> <!-- id_root -->
    <xsl:text>,</xsl:text> <!-- id_extension -->
    <xsl:text>,</xsl:text> <!-- name -->
    <xsl:text>,</xsl:text> <!-- address_street_address -->
    <xsl:text>,</xsl:text> <!-- address_city -->
    <xsl:text>,</xsl:text> <!-- address_state -->
    <xsl:text>,</xsl:text> <!-- address_zipcode -->
    <xsl:text>,</xsl:text> <!-- telecom -->
    <xsl:text>,</xsl:text> <!-- organization_id_root -->
    <xsl:text>,</xsl:text> <!-- organization_id_extension -->
    <xsl:text>,</xsl:text> <!-- organization_name -->
    <xsl:text>,</xsl:text> <!-- organization_telecom -->
    <xsl:text>,</xsl:text> <!-- organization_std_industry_class_code -->
    <xsl:call-template name="newline"/>
  </xsl:template>

  <!-- Template to produce the PARTICIPANT segment -->
  <xsl:template name="participant" match="instrument/participant" >
    <xsl:text>PARTICIPANT</xsl:text>
    <xsl:text>,</xsl:text> <!-- time -->
    <xsl:value-of select="time" />
    <xsl:text>,</xsl:text> <!-- id_root -->
    <xsl:value-of select="GUID" />
    <xsl:text>,</xsl:text> <!-- id_extension -->
    <xsl:text>,</xsl:text> <!-- name -->
    <xsl:text>,</xsl:text> <!-- address_street_address -->
    <xsl:text>,</xsl:text> <!-- address_city -->
    <xsl:text>,</xsl:text> <!-- address_state -->
    <xsl:text>,</xsl:text> <!-- address_zipcode -->
    <xsl:text>,</xsl:text> <!-- telecom -->
    <xsl:text>,</xsl:text> <!-- organization_id_root -->
    <xsl:text>,</xsl:text> <!-- organization_id_extension -->
    <xsl:text>,</xsl:text> <!-- organization_name -->
    <xsl:text>,</xsl:text> <!-- organization_telecom -->
    <xsl:text>,</xsl:text> <!-- organization_std_industry_class_code -->
    <xsl:call-template name="newline"/>
</xsl:template>
  
  <!-- Template to produce the INSTRUMENT-SETTING segment -->
  <xsl:template name="instrument-setting" match="instrument/instrument-setting" >
    <xsl:text>INSTRUMENT_SETTING</xsl:text>
    <xsl:text>,</xsl:text> <!-- setting_code  Mandatory -->
    <xsl:text>,</xsl:text> <!-- setting_code_system  Mandatory -->
    <xsl:text>,</xsl:text> <!-- setting_code_system_version -->
    <xsl:text>,</xsl:text> <!-- setting_code_system_name -->
    <xsl:text>,</xsl:text> <!-- setting_display_name -->
    <xsl:text>,</xsl:text> <!-- setting flag Mandatory -->
    <xsl:call-template name="newline"/>
  </xsl:template>
  
  <xsl:template name="instrument-instruction" match="instrument/persistent-information-collection">
     <xsl:text>INSTRUMENT_INSTRUCTION</xsl:text>
     <xsl:text>,</xsl:text> <!-- instruction text Mandatory -->
     <xsl:call-template name="wrapCommas"> 
      <xsl:with-param name="pString" select="instruction-text" />
     </xsl:call-template>
     <xsl:text>,</xsl:text> <!-- instruction language code  -->
     <xsl:value-of select="language" />
     <xsl:text>,</xsl:text> <!-- instruction language code system -->
     <xsl:text>,</xsl:text> <!-- instruction language code system  version-->
     <xsl:text>,</xsl:text> <!-- instruction language code system  name-->
     <xsl:text>,</xsl:text> <!-- instruction language display name -->
     <xsl:call-template name="newline"/>
  </xsl:template>
  
  <xsl:template name="sectiondef" match="instrument/section-def-collection">
     <xsl:text>SECTIONDEF</xsl:text>
     <xsl:text>,</xsl:text> <!-- id_root -->
     <xsl:value-of select="GUID" />
     <xsl:text>,</xsl:text> <!-- id_extension -->
     <xsl:text>,</xsl:text> <!-- title -->
     <xsl:call-template name="wrapCommas"> 
       <xsl:with-param name="pString" select="name" />
     </xsl:call-template>     
     <xsl:text>,</xsl:text> <!-- description_value -->
     <xsl:call-template name="wrapCommas"> 
       <xsl:with-param name="pString" select="description" />
     </xsl:call-template> 
     <xsl:call-template name="newline"/>
     <xsl:apply-templates />
  </xsl:template>
  
  <xsl:template name="groupdef" match="instrument/section-def-collection/group-def-collection">
     <xsl:text>GROUPDEF</xsl:text>
     <xsl:text>,</xsl:text> <!-- id_root -->
     <xsl:value-of select="id" />
     <xsl:text>,</xsl:text> <!-- id_extension -->
     <xsl:text>,</xsl:text> <!-- title -->
     <xsl:call-template name="wrapCommas"> 
       <xsl:with-param name="pString" select="name" />
     </xsl:call-template>     
     <xsl:text>,</xsl:text> <!-- description_value -->
     <xsl:call-template name="wrapCommas"> 
       <xsl:with-param name="pString" select="description" />
     </xsl:call-template>
     <xsl:text>,</xsl:text> <!-- degrpref_id_root -->
     <xsl:value-of select="data-element-group-gUID" />
     <xsl:text>,</xsl:text> <!-- degrpref_id_extension -->
     <xsl:call-template name="newline"/>
     <xsl:apply-templates />
  </xsl:template>
  
  <xsl:template name="groupdef-alias" match="instrument/section-def-collection/group-def-collection/group-alias-collection">
      <xsl:text>GROUPDEF_ALIAS</xsl:text>
      <xsl:text>,</xsl:text> <!-- name -->
      <xsl:call-template name="wrapCommas"> 
        <xsl:with-param name="pString" select="name" />
      </xsl:call-template>       
      <xsl:text>,NOT_MAPPED</xsl:text> <!-- alias user code Mandatory -->
      <xsl:text>,NOT_MAPPED</xsl:text> <!-- alias user code system Mandatory -->    
      <xsl:text>,</xsl:text> <!-- alias user code system version -->      
      <xsl:text>,</xsl:text> <!-- alias user code system name -->      
      <xsl:text>,</xsl:text> <!-- alias user display name -->      
      <xsl:call-template name="newline"/>
  </xsl:template>

  <xsl:template name="itemdef" match="instrument/section-def-collection/group-def-collection/item-def-collection">
     <xsl:text>ITEMDEF</xsl:text>
     <xsl:text>,</xsl:text> <!-- sequence_number -->
     <xsl:value-of select="navigation-sequence-number" />
     <xsl:text>,</xsl:text> <!-- id_root -->
     <xsl:value-of select="id" />
     <xsl:text>,</xsl:text> <!-- id_extension -->
     <xsl:text>,</xsl:text> <!-- derefid_root -->
     <xsl:value-of select="data-element-gUID" />
     <xsl:text>,</xsl:text> <!-- derefid_extension -->     
     <xsl:text>,</xsl:text> <!-- evdsubsetrefid_root -->
     <xsl:value-of select="enumerated-value-domain-subset-id" />
     <xsl:text>,</xsl:text> <!-- evdsubsetrefid_extension -->    
     <xsl:text>,NOT_MAPPED</xsl:text> <!-- rangecheck1_comparator -->
     <xsl:text>,</xsl:text> <!-- rangecheck1_setting_code -->
     <xsl:text>,</xsl:text> <!-- rangecheck1_setting_code_system -->
     <xsl:text>,</xsl:text> <!-- rangecheck1_setting_code_system_version -->
     <xsl:text>,</xsl:text> <!-- rangecheck1_setting_code_system_name -->
     <xsl:text>,</xsl:text> <!-- rangecheck1_setting_display_name -->
     <xsl:text>,</xsl:text> <!-- rangecheck1_setting_flag -->
     <xsl:text>,NOT_MAPPED</xsl:text> <!-- rangecheck2_comparator -->
     <xsl:text>,</xsl:text> <!-- rangecheck2_setting_code -->
     <xsl:text>,</xsl:text> <!-- rangecheck2_setting_code_system -->
     <xsl:text>,</xsl:text> <!-- rangecheck2_setting_code_system_version -->
     <xsl:text>,</xsl:text> <!-- rangecheck2_setting_code_system_name -->
     <xsl:text>,</xsl:text> <!-- rangecheck2_setting_display_name -->
     <xsl:text>,</xsl:text> <!-- rangecheck2_setting_flag -->
     <xsl:call-template name="newline"/>
  </xsl:template>
  
  <xsl:template name="sectionref" match="instrument/section-ref-collection">
     <xsl:text>SECTIONREF</xsl:text>
     <xsl:text>,</xsl:text> <!-- sequence_number -->
     <xsl:value-of select="navigation-sequence-number" />
     <xsl:text>,</xsl:text> <!-- id_root -->
     <xsl:value-of select="id" />
     <xsl:text>,</xsl:text> <!-- id_extension -->
     <xsl:text>,</xsl:text> <!-- sectiondefref_id_root -->
     <xsl:value-of select="section-def/GUID" />
     <xsl:text>,</xsl:text> <!-- sectiondefref_id_extension -->
     <xsl:call-template name="newline"/>
     <xsl:apply-templates />     
  </xsl:template>  
 
  <xsl:template name="groupref" match="instrument/section-ref-collection/group-ref-collection">
     <xsl:text>GROUPREF</xsl:text>
     <xsl:text>,</xsl:text> <!-- sequence_number -->
     <xsl:value-of  select="navigation-sequence-number" />
     <xsl:text>,</xsl:text> <!-- id_root -->
     <xsl:value-of select="id" />
     <xsl:text>,</xsl:text> <!-- id_extension -->
     <xsl:text>,</xsl:text> <!-- title -->
     <xsl:call-template name="wrapCommas"> 
       <xsl:with-param name="pString" select="name" />
     </xsl:call-template>   
     <xsl:text>,</xsl:text> <!-- max_itemref_repeats -->     
     <xsl:value-of select="maximum-item-ref-repeats" />
     <xsl:text>,</xsl:text> <!--groupdefref_id_root-->
     <xsl:value-of select="group-def/id" />
     <xsl:text>,</xsl:text> <!-- groupdefref_id_extension -->
     <xsl:call-template name="newline"/>
     <xsl:apply-templates />
  </xsl:template> 
  
  <xsl:template name="itemref" match="instrument/section-ref-collection/group-ref-collection/item-ref-collection">
     <xsl:text>ITEMREF</xsl:text>
     <xsl:text>,</xsl:text> <!-- sequence_number -->
     <xsl:value-of select="navigation-sequence-number" />
     <xsl:text>,</xsl:text> <!-- id_root -->
     <xsl:value-of select="id" />
     <xsl:text>,</xsl:text> <!-- id_extension -->
     <xsl:text>,</xsl:text> <!-- default_value -->
     <xsl:call-template name="wrapCommas">      
        <xsl:with-param name="pString" select="default-value" />
     </xsl:call-template>
     <xsl:text>,NOT_MAPPED</xsl:text> <!-- rngchk1_comparator -->     
     <xsl:text>,</xsl:text> <!-- rngchk1_setting_code -->
     <xsl:text>,</xsl:text> <!-- rngchk1_setting_code_system -->    
     <xsl:text>,</xsl:text> <!-- rngchk1_setting_code_system_version -->
     <xsl:text>,</xsl:text> <!-- rngchk1_setting_code_system_name -->
     <xsl:text>,</xsl:text> <!-- rngchk1_setting_display_name -->
     <xsl:text>,</xsl:text> <!-- rangecheck1_setting_flag -->
     <xsl:text>,NOT_MAPPED</xsl:text> <!-- rangecheck2_comparator -->
     <xsl:text>,</xsl:text> <!-- rangecheck2_setting_code -->
     <xsl:text>,</xsl:text> <!-- rangecheck2_setting_code_system -->
     <xsl:text>,</xsl:text> <!-- rangecheck2_setting_code_system_version -->
     <xsl:text>,</xsl:text> <!-- rangecheck2_setting_code_system_name -->
     <xsl:text>,</xsl:text> <!-- rangecheck2_setting_display_name -->
     <xsl:text>,</xsl:text> <!-- rangecheck2_setting_flag -->
     <xsl:call-template name="newline"/>
     <xsl:apply-templates />
  </xsl:template>
  
  <xsl:template name="itemref-prompt" match="instrument/section-ref-collection/group-ref-collection/item-ref-collection/item-ref-prompt-collection">
     <xsl:text>ITEMREF_PROMPT</xsl:text>
     <xsl:text>,</xsl:text> <!-- prompt_text -->
     <xsl:call-template name="wrapCommas"> 
        <xsl:with-param name="pString" select="prompt" />
     </xsl:call-template>
     <xsl:text>,</xsl:text> <!-- prompt_language_code -->
     <xsl:value-of select="language" />
     <xsl:text>,</xsl:text> <!-- prompt_language_code_system -->
     <xsl:text>,</xsl:text> <!-- prompt_language_code_system_version -->
     <xsl:text>,</xsl:text> <!-- prompt_language_code_system_name -->     
     <xsl:text>,</xsl:text> <!-- prompt_language_display_name -->
     <xsl:call-template name="newline"/>
     <xsl:apply-templates />
  </xsl:template>    
  
  <xsl:template name="itemref-instruction" match="instrument/section-ref-collection/group-ref-collection/item-ref-collection/persistent-information-collection">
     <xsl:text>ITEMREF_INSTRUCTION</xsl:text>
     <xsl:text>,</xsl:text> <!-- instruction text Mandatory -->
     <xsl:call-template name="wrapCommas"> 
      <xsl:with-param name="pString" select="instruction-text" />
     </xsl:call-template>
     <xsl:text>,</xsl:text> <!-- instruction language code  -->
     <xsl:value-of select="language" />
     <xsl:text>,</xsl:text> <!-- instruction language code system -->
     <xsl:text>,</xsl:text> <!-- instruction language code system  version-->
     <xsl:text>,</xsl:text> <!-- instruction language code system  name-->
     <xsl:text>,</xsl:text> <!-- instruction language display name -->
     <xsl:call-template name="newline"/>
  </xsl:template>  
 
</xsl:stylesheet>
