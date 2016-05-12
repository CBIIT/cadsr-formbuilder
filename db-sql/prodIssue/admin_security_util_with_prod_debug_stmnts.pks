create or replace PACKAGE Admin_Security_Util IS

-- Portal Exceptions
GENERAL_CONTEXT_EXCEPTION   EXCEPTION;
NO_SESSION_EXCEPTION        EXCEPTION;

g_ua_name          VARCHAR2(100);
g_der_admin        user_accounts_view.der_admin_ind%TYPE := NULL;
g_security_enabled VARCHAR2(3) := NULL;

g_delegated_auth   VARCHAR2(3) := 'OFF';
g_effective_user   VARCHAR2(30) := USER;

v_dbms_cursorId    NUMBER;
v_dbms_exeId       NUMBER;
v_dbms_fetchCnt    NUMBER :=0;

CURSOR delegated_auth_cursor IS
 SELECT STATUS_CODE
   FROM meta_util_stat_view
  WHERE UTILITY_NAME = 'DELEGATE AUTH';

delegated_auth_rec delegated_auth_cursor%ROWTYPE;

CURSOR security_enabled_cursor IS
 SELECT DECODE(status_code, 'OFF', 'No', 'Yes') status_code
   FROM meta_util_stat_view
  WHERE utility_name = 'SECURITY';

security_enabled_rec security_enabled_cursor%ROWTYPE;

CURSOR is_admin_cursor(p_ua_name VARCHAR2) IS
 SELECT DER_ADMIN_IND
   FROM USER_ACCOUNTS_VIEW
  WHERE UA_NAME = p_ua_name;

is_admin_rec is_admin_cursor%ROWTYPE;

Procedure DERWhoAmI;

FUNCTION Delegated_Auth_Enabled
  RETURN VARCHAR2 DETERMINISTIC;

FUNCTION Security_Enabled
  RETURN VARCHAR2 DETERMINISTIC;

FUNCTION Is_DER_Admin (
  p_ua_name     user_accounts_view.ua_name%TYPE DEFAULT g_effective_user )
  RETURN VARCHAR2 DETERMINISTIC;

FUNCTION Is_Context_Admin (
  p_ua_name     user_accounts_view.ua_name%TYPE DEFAULT g_effective_user,
  p_scl_name    sc_contexts_view.scl_name%TYPE)
  RETURN VARCHAR2 DETERMINISTIC;

FUNCTION Effective_User
  RETURN VARCHAR2 DETERMINISTIC;

Procedure SetEffectiveUser(
  p_ua_name     user_accounts_view.ua_name%TYPE);

FUNCTION has_context_admin (
  p_ua_name     user_accounts_view.ua_name%TYPE DEFAULT g_effective_user )
  RETURN VARCHAR2 DETERMINISTIC;

FUNCTION has_context_read (
  p_ua_name     user_accounts_view.ua_name%TYPE DEFAULT g_effective_user,
  p_conte_idseq CONTEXTS.conte_idseq%TYPE)
  RETURN VARCHAR2  DETERMINISTIC;

FUNCTION has_context_update (
  p_ua_name     user_accounts_view.ua_name%TYPE DEFAULT g_effective_user,
  p_conte_idseq CONTEXTS.conte_idseq%TYPE)
  RETURN VARCHAR2  DETERMINISTIC;

FUNCTION workflow_rule (
  p_ua_name        user_accounts_view.ua_name%TYPE,
  p_asl_name       ac_status_lov_view.asl_name%TYPE,
  p_from_asl_name  ac_status_lov_view.asl_name%TYPE DEFAULT NULL,
  p_conte_idseq    CONTEXTS.conte_idseq%TYPE DEFAULT NULL)
  RETURN VARCHAR2 DETERMINISTIC;

FUNCTION has_privilege (
  p_ua_name      user_accounts_view.ua_name%TYPE,
  p_conte_idseq  ADMINISTERED_COMPONENTS.conte_idseq%TYPE,
  p_actl_name    ADMINISTERED_COMPONENTS.actl_name%TYPE,
  p_asl_name     ADMINISTERED_COMPONENTS.asl_name%TYPE,
  p_cmsl_name    ADMINISTERED_COMPONENTS.cmsl_name%TYPE,
  p_privilege    VARCHAR2)
  RETURN VARCHAR2 DETERMINISTIC;

FUNCTION has_read_privilege (
  p_ua_name     user_accounts_view.ua_name%TYPE DEFAULT g_effective_user,
  p_ac_idseq    ADMINISTERED_COMPONENTS.ac_idseq%TYPE)
  RETURN VARCHAR2 DETERMINISTIC;

FUNCTION has_read_privilege (
  p_ua_name     user_accounts_view.ua_name%TYPE DEFAULT USER,
  p_ac_idseq    ADMINISTERED_COMPONENTS.ac_idseq%TYPE,
  p_source  VARCHAR2)
  RETURN VARCHAR2;

FUNCTION has_read_privilege (
  p_ua_name      user_accounts_view.ua_name%TYPE,
  p_conte_idseq  ADMINISTERED_COMPONENTS.conte_idseq%TYPE,
  p_actl_name    ADMINISTERED_COMPONENTS.actl_name%TYPE,
  p_asl_name     ADMINISTERED_COMPONENTS.asl_name%TYPE,
  p_cmsl_name    ADMINISTERED_COMPONENTS.cmsl_name%TYPE)
  RETURN VARCHAR2 DETERMINISTIC;

FUNCTION has_extended_read_privilege (
  p_ua_name      user_accounts_view.ua_name%TYPE,
  p_ac_idseq     ADMINISTERED_COMPONENTS.ac_idseq%TYPE)
  RETURN VARCHAR2 DETERMINISTIC;

FUNCTION has_update_privilege (
  p_ua_name     user_accounts_view.ua_name%TYPE DEFAULT g_effective_user,
  p_ac_idseq    ADMINISTERED_COMPONENTS.ac_idseq%TYPE)
  RETURN VARCHAR2 DETERMINISTIC;

FUNCTION has_update_privilege (
  p_ua_name      user_accounts_view.ua_name%TYPE,
  p_conte_idseq  ADMINISTERED_COMPONENTS.conte_idseq%TYPE,
  p_actl_name    ADMINISTERED_COMPONENTS.actl_name%TYPE,
  p_asl_name     ADMINISTERED_COMPONENTS.asl_name%TYPE,
  p_cmsl_name    ADMINISTERED_COMPONENTS.cmsl_name%TYPE)
  RETURN VARCHAR2 DETERMINISTIC;

FUNCTION has_delete_privilege (
  p_ua_name     user_accounts_view.ua_name%TYPE DEFAULT USER,
  p_ac_idseq    ADMINISTERED_COMPONENTS.ac_idseq%TYPE)
  RETURN VARCHAR2 DETERMINISTIC;

FUNCTION has_delete_privilege (
  p_ua_name      user_accounts_view.ua_name%TYPE,
  p_conte_idseq  ADMINISTERED_COMPONENTS.conte_idseq%TYPE,
  p_actl_name    ADMINISTERED_COMPONENTS.actl_name%TYPE,
  p_asl_name     ADMINISTERED_COMPONENTS.asl_name%TYPE,
  p_cmsl_name    ADMINISTERED_COMPONENTS.cmsl_name%TYPE)
  RETURN VARCHAR2 DETERMINISTIC;

FUNCTION has_version_privilege (
  p_ua_name     user_accounts_view.ua_name%TYPE DEFAULT USER,
  p_ac_idseq    ADMINISTERED_COMPONENTS.ac_idseq%TYPE)
  RETURN VARCHAR2 DETERMINISTIC;

FUNCTION has_comp_create_priv (
  p_ua_name   user_accounts_view.ua_name%TYPE DEFAULT NULL,
  p_actl_name ac_types_lov_view.actl_name%TYPE DEFAULT NULL)
  RETURN VARCHAR2 deterministic;

FUNCTION has_wf_transition_priv (
  p_ua_name       user_accounts_view.ua_name%TYPE,
  p_conte_idseq   ADMINISTERED_COMPONENTS.conte_idseq%TYPE,
  p_actl_name     ADMINISTERED_COMPONENTS.actl_name%TYPE,
  p_from_asl_name AC_WF_RULES.from_asl_name%TYPE,
  p_to_asl_name   AC_WF_RULES.to_asl_name%TYPE)
  RETURN VARCHAR2 deterministic;

FUNCTION has_business_role ( account_name    user_accounts_view.ua_name%TYPE,
                             b_role   ua_business_roles_view.brl_name%TYPE )
         RETURN VARCHAR2 deterministic;

FUNCTION has_role_priv ( p_ua_name    user_accounts_view.ua_name%TYPE,
                         b_role   ua_business_roles_view.brl_name%TYPE DEFAULT NULL,
						 p_actl_name ac_types_lov_view.actl_name%TYPE DEFAULT 'DATAELEMENT')
RETURN VARCHAR2 deterministic;

FUNCTION has_comp_business_role ( account_name    user_accounts_view.ua_name%TYPE DEFAULT NULL,
                             b_role   ua_business_roles_view.brl_name%TYPE DEFAULT 'BROWSER',
                             comp_typ ua_business_roles_view.actl_name%TYPE DEFAULT NULL,
                             ctx_id          VARCHAR2 DEFAULT NULL )
         RETURN VARCHAR2 deterministic;

FUNCTION has_create_privilege (
  p_ua_name      user_accounts_view.ua_name%TYPE  DEFAULT NULL,
  p_actl_name    ac_types_lov_view.actl_name%TYPE DEFAULT NULL,
  p_conte_idseq  CONTEXTS.conte_idseq%TYPE   DEFAULT NULL )
  RETURN VARCHAR2 deterministic;

  FUNCTION has_admin_privilege (  account_name user_accounts_view.ua_name%TYPE  DEFAULT NULL,
                                  comp_typ     ac_types_lov_view.actl_name%TYPE DEFAULT NULL,
                                  ctx_id       CONTEXTS.conte_idseq%TYPE   DEFAULT NULL )
         RETURN VARCHAR2 deterministic;
END Admin_Security_Util;