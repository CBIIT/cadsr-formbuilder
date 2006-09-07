<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <!-- 
    * number-for-letter.xsl 
    * This stylesheet replaces each letter in the input string 
    * with a corresponding number, starting with 00 for A.
    * Case is ignored.
    
  -->
  <xsl:include href="search-and-replace.xsl"/>
  
  <xsl:template name="number-for-letter">
    <xsl:param name="input"/>
    <!-- Force all characters to uppercase-->
    <xsl:variable name="UC_input" select="translate($input,
    'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')"/>
    <!-- Walk through the alphabet, assigning numbers to letters -->
    <xsl:variable name="IDA">
      <xsl:call-template name="search-and-replace">
        <xsl:with-param name="input" select="$UC_input"/>
        <xsl:with-param name="search-string" select="'A'"/>
        <xsl:with-param name="replace-string" select="'00'"/>
      </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="IDB">
      <xsl:call-template name="search-and-replace">
        <xsl:with-param name="input" select="$IDA"/>
        <xsl:with-param name="search-string" select="'B'"/>
        <xsl:with-param name="replace-string" select="'01'"/>
      </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="IDC">
      <xsl:call-template name="search-and-replace">
        <xsl:with-param name="input" select="$IDB"/>
        <xsl:with-param name="search-string" select="'C'"/>
        <xsl:with-param name="replace-string" select="'02'"/>
      </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="IDD">
      <xsl:call-template name="search-and-replace">
        <xsl:with-param name="input" select="$IDC"/>
        <xsl:with-param name="search-string" select="'D'"/>
        <xsl:with-param name="replace-string" select="'03'"/>
      </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="IDE">
      <xsl:call-template name="search-and-replace">
        <xsl:with-param name="input" select="$IDD"/>
        <xsl:with-param name="search-string" select="'E'"/>
        <xsl:with-param name="replace-string" select="'04'"/>
      </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="IDF">
      <xsl:call-template name="search-and-replace">
        <xsl:with-param name="input" select="$IDE"/>
        <xsl:with-param name="search-string" select="'F'"/>
        <xsl:with-param name="replace-string" select="'05'"/>
      </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="IDG">
      <xsl:call-template name="search-and-replace">
        <xsl:with-param name="input" select="$IDF"/>
        <xsl:with-param name="search-string" select="'G'"/>
        <xsl:with-param name="replace-string" select="'06'"/>
      </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="IDH">
      <xsl:call-template name="search-and-replace">
        <xsl:with-param name="input" select="$IDG"/>
        <xsl:with-param name="search-string" select="'H'"/>
        <xsl:with-param name="replace-string" select="'07'"/>
      </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="IDI">
      <xsl:call-template name="search-and-replace">
        <xsl:with-param name="input" select="$IDH"/>
        <xsl:with-param name="search-string" select="'I'"/>
        <xsl:with-param name="replace-string" select="'08'"/>
      </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="IDJ">
      <xsl:call-template name="search-and-replace">
        <xsl:with-param name="input" select="$IDI"/>
        <xsl:with-param name="search-string" select="'J'"/>
        <xsl:with-param name="replace-string" select="'09'"/>
      </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="IDK">
      <xsl:call-template name="search-and-replace">
        <xsl:with-param name="input" select="$IDJ"/>
        <xsl:with-param name="search-string" select="'K'"/>
        <xsl:with-param name="replace-string" select="'10'"/>
      </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="IDL">
      <xsl:call-template name="search-and-replace">
        <xsl:with-param name="input" select="$IDK"/>
        <xsl:with-param name="search-string" select="'L'"/>
        <xsl:with-param name="replace-string" select="'11'"/>
      </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="IDM">
      <xsl:call-template name="search-and-replace">
        <xsl:with-param name="input" select="$IDL"/>
        <xsl:with-param name="search-string" select="'M'"/>
        <xsl:with-param name="replace-string" select="'12'"/>
      </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="IDN">
      <xsl:call-template name="search-and-replace">
        <xsl:with-param name="input" select="$IDM"/>
        <xsl:with-param name="search-string" select="'N'"/>
        <xsl:with-param name="replace-string" select="'13'"/>
      </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="IDO">
      <xsl:call-template name="search-and-replace">
        <xsl:with-param name="input" select="$IDN"/>
        <xsl:with-param name="search-string" select="'O'"/>
        <xsl:with-param name="replace-string" select="'14'"/>
      </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="IDP">
      <xsl:call-template name="search-and-replace">
        <xsl:with-param name="input" select="$IDO"/>
        <xsl:with-param name="search-string" select="'P'"/>
        <xsl:with-param name="replace-string" select="'15'"/>
      </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="IDQ">
      <xsl:call-template name="search-and-replace">
        <xsl:with-param name="input" select="$IDP"/>
        <xsl:with-param name="search-string" select="'Q'"/>
        <xsl:with-param name="replace-string" select="'16'"/>
      </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="IDR">
      <xsl:call-template name="search-and-replace">
        <xsl:with-param name="input" select="$IDQ"/>
        <xsl:with-param name="search-string" select="'R'"/>
        <xsl:with-param name="replace-string" select="'17'"/>
      </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="IDS">
      <xsl:call-template name="search-and-replace">
        <xsl:with-param name="input" select="$IDR"/>
        <xsl:with-param name="search-string" select="'S'"/>
        <xsl:with-param name="replace-string" select="'18'"/>
      </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="IDT">
      <xsl:call-template name="search-and-replace">
        <xsl:with-param name="input" select="$IDS"/>
        <xsl:with-param name="search-string" select="'T'"/>
        <xsl:with-param name="replace-string" select="'19'"/>
      </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="IDU">
      <xsl:call-template name="search-and-replace">
        <xsl:with-param name="input" select="$IDT"/>
        <xsl:with-param name="search-string" select="'U'"/>
        <xsl:with-param name="replace-string" select="'20'"/>
      </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="IDV">
      <xsl:call-template name="search-and-replace">
        <xsl:with-param name="input" select="$IDU"/>
        <xsl:with-param name="search-string" select="'V'"/>
        <xsl:with-param name="replace-string" select="'21'"/>
      </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="IDW">
      <xsl:call-template name="search-and-replace">
        <xsl:with-param name="input" select="$IDV"/>
        <xsl:with-param name="search-string" select="'W'"/>
        <xsl:with-param name="replace-string" select="'22'"/>
      </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="IDX">
      <xsl:call-template name="search-and-replace">
        <xsl:with-param name="input" select="$IDW"/>
        <xsl:with-param name="search-string" select="'X'"/>
        <xsl:with-param name="replace-string" select="'23'"/>
      </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="IDY">
      <xsl:call-template name="search-and-replace">
        <xsl:with-param name="input" select="$IDX"/>
        <xsl:with-param name="search-string" select="'Y'"/>
        <xsl:with-param name="replace-string" select="'24'"/>
      </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="IDZ">
      <xsl:call-template name="search-and-replace">
        <xsl:with-param name="input" select="$IDY"/>
        <xsl:with-param name="search-string" select="'Z'"/>
        <xsl:with-param name="replace-string" select="'25'"/>
      </xsl:call-template>
    </xsl:variable>
    <!-- Return -->
    <xsl:value-of select="$IDZ"/>
  </xsl:template>
</xsl:stylesheet>