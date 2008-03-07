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

delete from sbrext.tool_options_view_ext where tool_name = 'FormBuilder';

/*
  ==============================================================================
  Required Settings (do not comment or remove)
  ==============================================================================
*/
/*
   By placing this value in the database, the URL may be dynamically changed as needed 
   without the need to build and deploy new WAR and JAR files.
*/

insert into sbrext.tool_options_view_ext (tool_name, property, value, description,locale)
values ('FormBuilder', 'URL', 'http://formbuilder@TIER@.nci.nih.gov',
'The URL for the Form Builder Tool connected this caDSR database.','US');


/*
   Commit Settings.
*/

commit;

exit
