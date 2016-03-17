-- Run this script as formbuilder
-- GF32671_fb_whats_new_link.sql

set echo on
SET SERVEROUTPUT ON
SET LINESIZE 200
set heading off
set define off
set scan off

select * from tool_options_view_ext where tool_name = 'FormBuilder' and property = 'WHATS_NEW_URL';

MERGE INTO SBREXT.TOOL_OPTIONS_VIEW_EXT S
USING (SELECT 'FormBuilder' AS TOOL_NAME, 'WHATS_NEW_URL' AS PROPERTY, 'https://wiki.nci.nih.gov/x/P4CrBQ' AS VALUE, 'The URL for the Whats New link' AS DESCRIPTION, 'US' AS LOCALE FROM DUAL) T
ON (S.TOOL_NAME = T.TOOL_NAME AND S.PROPERTY = T.PROPERTY)
WHEN MATCHED THEN UPDATE SET S.VALUE = T.VALUE, S.DESCRIPTION = T.DESCRIPTION, S.LOCALE = T.LOCALE
WHEN NOT MATCHED THEN INSERT (TOOL_NAME, PROPERTY, VALUE, DESCRIPTION, LOCALE) VALUES (T.TOOL_NAME, T.PROPERTY, T.VALUE, T.DESCRIPTION, T.LOCALE);

commit;
--rollback;

select * from tool_options_view_ext where tool_name = 'FormBuilder' and property = 'WHATS_NEW_URL';

spool off
set echo off
exit


