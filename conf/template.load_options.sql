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
USING (SELECT 'FormBuilder' AS TOOL_NAME, 'URL' AS PROPERTY, 'https://formbuilder@TIER@.nci.nih.gov' AS VALUE, 'The URL for the Form Builder Tool connected this caDSR database.' AS DESCRIPTION, 'US' AS LOCALE FROM DUAL
UNION SELECT 'FormBuilder' AS TOOL_NAME, 'XML_DOWNLOAD_DIR' AS PROPERTY, '/local/content/formbuilder/output' AS VALUE, 'Download directory for the Form Builder Tool.' AS DESCRIPTION, 'US' AS LOCALE FROM DUAL
UNION SELECT 'FormBuilder' AS TOOL_NAME, 'XML_PAGINATION_FLAG' AS PROPERTY, 'no' AS VALUE, 'A flag for pagination for download' AS DESCRIPTION, 'US' AS LOCALE FROM DUAL
UNION SELECT 'FormBuilder' AS TOOL_NAME, 'XML_FILE_MAX_RECORDS' AS PROPERTY, '500' AS VALUE, 'Value for maximum file records' AS DESCRIPTION, 'US' AS LOCALE FROM DUAL
UNION SELECT 'FormBuilder' AS TOOL_NAME, 'HELP.ROOT' AS PROPERTY, 'https://formbuilder@TIER@.nci.nih.gov/help' AS VALUE, 'root url for formbulder tool help' AS DESCRIPTION, 'US' AS LOCALE FROM DUAL
) T
ON (S.TOOL_NAME = T.TOOL_NAME AND S.PROPERTY = T.PROPERTY)
WHEN MATCHED THEN UPDATE SET S.VALUE = T.VALUE, S.DESCRIPTION = T.DESCRIPTION, S.LOCALE = T.LOCALE
WHEN NOT MATCHED THEN INSERT (TOOL_NAME, PROPERTY, VALUE, DESCRIPTION, LOCALE) VALUES (T.TOOL_NAME, T.PROPERTY, T.VALUE, T.DESCRIPTION, T.LOCALE);

/*
   Commit Settings.
*/

commit;

exit
