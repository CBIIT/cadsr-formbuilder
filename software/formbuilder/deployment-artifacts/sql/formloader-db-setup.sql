-- Request new login user "FORMLOADER":
--	1). Needs same rights as "FORMBUILDER"
--  2). Needs read/write access to tables sbrext.FORM_COLLECTIONS
--											sbrext.FORMS_IN_COLLECTION
--											sbrext.qc_recs_ext
-- 	3). Needs read right to these tables: sbr.DOCUMENT_TYPES_LOV
--											sbrext.DEFINITION_TYPES_LOV_EXT 
--	 										sbr.contexts_view 
--	4). Use the Admin Tool and create a new user account - FORMLOADER. The associated user groups should 
--		be setup similar to FORMBUILDER account

--
-- CRM DDL
--

-- basic table definitions
CREATE TABLE SBREXT.FORM_COLLECTIONS (
  FORM_COLLECTION_IDSEQ CHAR(36) PRIMARY KEY,
  DESCRIPTION VARCHAR2(2000),
  NAME VARCHAR2(255) NOT NULL,
  XML_FILE_NAME VARCHAR2(255),
  XML_FILE_PATH VARCHAR2(255),
  DATE_CREATED DATE default sysdate,
  CREATED_BY VARCHAR2(30),
  NAME_REPEAT_NUM NUMBER
);


CREATE TABLE SBREXT.FORMS_IN_COLLECTION (
  FORM_COLLECTION_IDSEQ CHAR(36) NOT NULL,
  FORM_IDSEQ CHAR(36),
  PUBLIC_ID NUMBER, 
  VERSION NUMBER(4,2),
  LONG_NAME VARCHAR2(4000) NOT NULL,
  LOAD_TYPE CHAR(36),
  LOAD_STATUS NUMBER,
  LOAD_UNLOAD_DATE DATE default sysdate,
  PREVIOUS_LATEST_VERSION NUMBER(4,2)
);

alter table SBREXT.FORMS_IN_COLLECTION 
add foreign key (FORM_COLLECTION_IDSEQ) 
references SBREXT.FORM_COLLECTIONS(FORM_COLLECTION_IDSEQ);
commit;

-- Insert new designation type for Form Loader
insert into sbrext.DESIGNATION_TYPES_LOV_VIEW (DETL_NAME) values ('Form Loader');

-- Insert new definition type for Form Loader
insert into sbrext.DEFINITION_TYPES_LOV_EXT (DEFL_NAME) values ('Form Loader');

-- Insert the new workflow status value "RETIRED UNLOADED" for Form Loader
INSERT INTO SBR.AC_STATUS_LOV (ASL_NAME, CREATED_BY, DATE_CREATED )
values ('RETIRED UNLOADED', 'SBREXT', SYSDATE );

insert into sbrext.ASL_ACTL_EXT (ASL_NAME, ACTL_NAME, CREATED_BY) 
values ('RETIRED UNLOADED', 'QUEST_CONTENT', 'SBREXT');

grant select, insert, update, delete on FORM_COLLECTIONS to formbuilder;
 
grant select, insert, update, delete on FORMS_IN_COLLECTION to formbuilder;

grant select, insert, update, delete on FORMS_IN_COLLECTION to FORMLOADER;

grant select, insert, update, delete on FORM_COLLECTIONS to FORMLOADER;

commit;
