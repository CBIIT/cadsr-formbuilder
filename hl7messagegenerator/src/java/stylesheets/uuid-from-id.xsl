<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <!-- 
    * uuid-from-id.xsl 
    * This stylesheet creates a UUID (a DCE universal unique identifier)
    * from an arbitrary string created by xslt function generate-id().
    * If input is
    * <a>
    * <b/>
    * <c/>
    * <c/>
    * </a>
    * then output will be 
    *  idz: 830372112
    *  a: IDADH2LC::{12345678-1234-1234-1234-000830372112}
    *  idz: 830562112
    *  b: IDA5G2LC::{12345678-1234-1234-1234-000830562112}
    *  idz: 8301862112
    *  c: IDASG2LC::{12345678-1234-1234-1234-008301862112}
    *  idz: 8301362112
    *  c: IDANG2LC::{12345678-1234-1234-1234-008301362112}
    *
    * Example call:
    * 
    *     <xsl:call-template name="uuid-from_id">
    *        <xsl:with-param name="id">
    *          <xsl:value-of select="generate-id()"/>
    *        </xsl:with-param>
    *     </xsl:call-template>

  -->
  <xsl:include href="number-for-letter.xsl"/>
  <!--
  
  -->
  <xsl:template name="uuid-from-id">
    <xsl:param name="id"/>
    <xsl:variable name="id_numbers">
      <xsl:call-template name="number-for-letter">
        <xsl:with-param name="input" select="$id"/>
      </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="ID12">
      <xsl:call-template name="make12">
        <xsl:with-param name="input" select="$id_numbers"/>
      </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="UUID"
    select="concat('{12345678-1234-1234-1234-', $ID12, '}')"/>
    <!-- Return -->
    <xsl:value-of select="$UUID"/>
  </xsl:template>
  <!-- subroutines -->
  <xsl:template name="make12">
    <xsl:param name="input"/>
    <xsl:variable name="A" select="1000000000000 + $input"/>
    <xsl:variable name="B" select="substring(string($A),2,12)"/>
    <xsl:value-of select="$B"/>
  </xsl:template>
</xsl:stylesheet>