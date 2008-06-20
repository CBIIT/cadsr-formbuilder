/* Copyright ScenPro, Inc, 2005

   $Header: $
   $Name:  $

   Author: Sumana Hegde

   This script loads the Tool Options table with required and optional values
   for the Form Builder Tool.

   Each is described briefly below. A full description of each can be found in
   the Sentinel Tool Installation Guide (file:
   distrib/doc/Installation Guide.doc). These values must be reviewed and
   changed as needed per the local installation and database instance.  

*/
whenever sqlerror exit sql.sqlcode rollback;

/*
  ==============================================================================
  Required Settings (do not comment or remove)
  ==============================================================================
*/
/*
   Form builder url and download directory.
*/

MERGE INTO SBREXT.TOOL_OPTIONS_VIEW_EXT S
USING (SELECT 'FormBuilder' AS TOOL_NAME, 'URL' AS PROPERTY, 'https://formbuilder@TIER@.nci.nih.gov:6443' AS VALUE, 'The URL for the Form Builder Tool connected this caDSR database.' AS DESCRIPTION, 'US' AS LOCALE FROM DUAL
UNION SELECT 'FormBuilder' AS TOOL_NAME, 'XML_DOWNLOAD_DIR' AS PROPERTY, '/local/content/formbuilder/output' AS VALUE, 'Download directory for the Form Builder Tool.' AS DESCRIPTION, 'US' AS LOCALE FROM DUAL
) T
ON (S.TOOL_NAME = T.TOOL_NAME AND S.PROPERTY = T.PROPERTY)
WHEN MATCHED THEN UPDATE SET S.VALUE = S.VALUE, S.DESCRIPTION = T.DESCRIPTION, S.LOCALE = T.LOCALE
WHEN NOT MATCHED THEN INSERT (TOOL_NAME, PROPERTY, VALUE, DESCRIPTION, LOCALE) VALUES (T.TOOL_NAME, T.PROPERTY, T.VALUE, T.DESCRIPTION, T.LOCALE);

/*
   EVS browser url, evs api url and object cart api in case if it was not added before.  don't need to do this.


MERGE INTO SBREXT.TOOL_OPTIONS_VIEW_EXT S
USING (SELECT 'EVSBrowser' AS TOOL_NAME, 'URL' AS PROPERTY, 'http://bioportal.nci.nih.gov/ncbo/faces/pages/advanced_search.xhtml' AS VALUE, 'The URL for EVS Bioportal Browser.' AS DESCRIPTION, 'US' AS LOCALE FROM DUAL
UNION SELECT 'EVSAPI' AS TOOL_NAME, 'URL' AS PROPERTY, 'http://evsapi@TIER@.nci.nih.gov:19080/evsapi41' AS VALUE, 'The URL for EVS API access used in cadsr tools.' AS DESCRIPTION, 'US' AS LOCALE FROM DUAL
UNION SELECT 'ObjectCartAPI' AS TOOL_NAME, 'URL' AS PROPERTY, 'http://cbvapp-d1008.nci.nih.gov:19080/objectCart' AS VALUE, 'The URL for Object Cart API access used in cadsr tools.' AS DESCRIPTION, 'US' AS LOCALE FROM DUAL
) T
ON (S.TOOL_NAME = T.TOOL_NAME AND S.PROPERTY = T.PROPERTY)
WHEN MATCHED THEN UPDATE SET S.VALUE = S.VALUE, S.DESCRIPTION = T.DESCRIPTION, S.LOCALE = T.LOCALE
WHEN NOT MATCHED THEN INSERT (TOOL_NAME, PROPERTY, VALUE, DESCRIPTION, LOCALE) VALUES (T.TOOL_NAME, T.PROPERTY, T.VALUE, T.DESCRIPTION, T.LOCALE);

*/
/*
   Commit Settings.
*/

commit;

exit
