/*L
  Copyright Oracle Inc, ScenPro Inc, SAIC-F

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/cadsr-formbuilder/LICENSE.txt for details.
L*/

-- Run this script as formbuilder
-- GF32430_fb_help_link.sql

set echo on
SET SERVEROUTPUT ON
SET LINESIZE 200
set heading off
set define off
set scan off

spool ./GF32430_fb_help_link.log

select * from tool_options_view_ext where tool_name = 'FormBuilder' and property = 'HELP.ROOT';

update tool_options_view_ext set value = 'https://wiki.nci.nih.gov/x/TgvRB' where tool_name = 'FormBuilder' and property = 'HELP.ROOT';

commit;
--rollback;

select * from tool_options_view_ext where tool_name = 'FormBuilder' and property = 'HELP.ROOT';

spool off
set echo off
exit


