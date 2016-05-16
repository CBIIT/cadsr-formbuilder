update SBREXT.TOOL_OPTIONS_VIEW_EXT set value='http://objcart-stage.nci.nih.gov/objcart103' where TOOL_NAME = 'ObjectCartAPI' and property = 'URL';

update SBREXT.TOOL_OPTIONS_VIEW_EXT set value='https://formbuilder-stage.nci.nih.gov' where TOOL_NAME = 'FormBuilder' and property = 'URL';

update SBREXT.TOOL_OPTIONS_VIEW_EXT set value='https://wiki.nci.nih.gov/display/caDSR/caDSR+Form+Builder+4.1.2+Release+Notes' where TOOL_NAME = 'FormBuilder' and property = 'WHATS_NEW_URL';

commit;

GRANT SELECT ON sbr.document_types_lov TO DER_USER;