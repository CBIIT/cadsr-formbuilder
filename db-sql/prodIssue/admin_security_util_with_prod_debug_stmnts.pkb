create or replace PACKAGE BODY  sbr.Admin_Security_Util IS

FUNCTION Security_Enabled
  RETURN VARCHAR2 IS
BEGIN
	RETURN g_security_enabled;
END Security_Enabled;



FUNCTION Is_DER_Admin ( p_ua_name user_accounts_view.ua_name%TYPE )
  RETURN VARCHAR2 IS
    v_user user_accounts_view.ua_name%TYPE := g_effective_user;
BEGIN
    IF g_security_enabled = 'No' THEN
	   RETURN 'Yes';
	END IF;

	IF g_delegated_auth = 'No' THEN
	    v_user := p_ua_name;
	END IF;

	IF v_user = g_effective_user THEN
	    RETURN g_der_admin;
	END IF;

    OPEN is_admin_cursor(v_user);
    FETCH is_admin_cursor INTO is_admin_rec;

    IF is_admin_cursor%NOTFOUND THEN
        is_admin_rec.DER_ADMIN_IND := 'No';
    END IF;

    CLOSE is_admin_cursor;

	RETURN is_admin_rec.DER_ADMIN_IND;
END Is_DER_Admin;

FUNCTION Is_Context_Admin (
  p_ua_name     user_accounts_view.ua_name%TYPE ,
  p_scl_name    sc_contexts_view.scl_name%TYPE)
  RETURN VARCHAR2 IS

    v_user user_accounts_view.ua_name%TYPE := g_effective_user;

    CURSOR is_ctx_admin_cursor IS
	    SELECT CONTEXT_ADMIN_IND
		  FROM SC_USER_ACCOUNTS_VIEW
		 WHERE UA_NAME = v_user
		   AND SCL_NAME = p_scl_name;

    is_ctx_admin_rec is_ctx_admin_cursor%ROWTYPE;

	v_is_admin VARCHAR2(3);
BEGIN
    IF g_security_enabled = 'No' OR g_der_admin = 'Yes' THEN
	   RETURN 'Yes';
	END IF;

	IF g_delegated_auth <> 'Yes' THEN
	    v_user := p_ua_name;
	END IF;

    OPEN is_ctx_admin_cursor;
	FETCH is_ctx_admin_cursor INTO is_ctx_admin_rec;

	IF is_ctx_admin_cursor%NOTFOUND THEN
        is_ctx_admin_rec.CONTEXT_ADMIN_IND := 'No';
	ELSE
	    IF is_ctx_admin_rec.CONTEXT_ADMIN_IND <> 'Yes' THEN
		    is_ctx_admin_rec.CONTEXT_ADMIN_IND := 'No';
	    END IF;
	END IF;

	CLOSE is_ctx_admin_cursor;
    RETURN is_ctx_admin_rec.CONTEXT_ADMIN_IND;
END Is_Context_Admin ;

FUNCTION Effective_User RETURN VARCHAR2 IS
  BEGIN
    RETURN g_effective_user;
  END;

Procedure SetEffectiveUser (
  p_ua_name     user_accounts_view.ua_name%TYPE) IS
BEGIN
    g_effective_user := p_ua_name;

    BEGIN
        OPEN is_admin_cursor(g_effective_user);
        FETCH is_admin_cursor INTO is_admin_rec;

        IF is_admin_cursor%NOTFOUND THEN
            g_der_admin := 'No';
        ELSE
            g_der_admin := is_admin_rec.DER_ADMIN_IND;
        END IF;

   	    CLOSE is_admin_cursor;
	EXCEPTION
	    WHEN OTHERS THEN
		    g_der_admin := 'No';
	END;
END;

FUNCTION has_Context_Admin (
  p_ua_name     user_accounts_view.ua_name%TYPE )
  RETURN VARCHAR2 IS

    v_user user_accounts_view.ua_name%TYPE := g_effective_user;

    CURSOR has_admin_cursor IS
	    SELECT CONTEXT_ADMIN_IND
		  FROM SC_USER_ACCOUNTS_VIEW
		 WHERE UA_NAME = v_user
		   AND CONTEXT_ADMIN_IND = 'Yes';

    has_admin_rec has_admin_cursor%ROWTYPE;

BEGIN
    IF g_security_enabled = 'No' OR g_der_admin = 'Yes' THEN
	   RETURN 'Yes';
	END IF;

	IF g_delegated_auth <> 'Yes' THEN
	    v_user := p_ua_name;
	END IF;

    OPEN has_admin_cursor;
	FETCH has_admin_cursor INTO has_admin_rec;

	IF has_admin_cursor%NOTFOUND THEN
	    has_admin_rec.context_admin_ind := 'No';
    END IF;

	CLOSE has_admin_cursor;
    RETURN has_admin_rec.context_admin_ind;
END Has_Context_Admin ;

FUNCTION has_context_read (
  p_ua_name     user_accounts_view.ua_name%TYPE ,
  p_conte_idseq CONTEXTS.conte_idseq%TYPE)
  RETURN VARCHAR2
IS
    v_user  user_accounts_view.ua_name%TYPE := g_effective_user;

    CURSOR security_context_cursor IS
	    SELECT SCL_NAME
		  FROM SC_CONTEXTS_VIEW scv
		 WHERE CONTE_IDSEQ = p_conte_idseq
		   AND EXISTS (SELECT 1
		                 FROM SC_USER_ACCOUNTS_VIEW suav
						WHERE SCL_NAME = scv.SCL_NAME
						    AND suav.UA_NAME = v_user);

    CURSOR group_security_context_cursor IS
	    SELECT SCL_NAME
		  FROM SC_CONTEXTS_VIEW scv
		 WHERE CONTE_IDSEQ = p_conte_idseq
		   AND EXISTS (SELECT 1
		                 FROM SC_GROUPS_VIEW sgv
						WHERE SCL_NAME = scv.SCL_NAME
						    AND sgv.GRP_NAME IN (SELECT grp_name
											 	  FROM USER_GROUPS
                                                  WHERE ua_name = v_user));

    security_context_rec security_context_cursor%ROWTYPE;
    group_security_context_rec group_security_context_cursor%ROWTYPE;
BEGIN
    IF g_security_enabled = 'No' OR g_der_admin = 'Yes' THEN
	   RETURN 'Yes';
	END IF;

	IF g_delegated_auth <> 'Yes' THEN
	    v_user := p_ua_name;
	END IF;

	OPEN security_context_cursor;
	FETCH security_context_cursor INTO security_context_rec;

	IF security_context_cursor%NOTFOUND THEN
         CLOSE security_context_cursor;

 	      OPEN group_security_context_cursor;
 	      FETCH group_security_context_cursor INTO group_security_context_rec;
		  IF group_security_context_cursor%NOTFOUND THEN
            CLOSE group_security_context_cursor;
            RETURN 'No';
		  ELSE
            RETURN 'Yes';
		  END IF;
   	  RETURN 'No';
	ELSE
	   CLOSE security_context_cursor;
       RETURN 'Yes';
	END IF;
END;



FUNCTION has_context_update (
  p_ua_name     user_accounts_view.ua_name%TYPE ,
  p_conte_idseq CONTEXTS.conte_idseq%TYPE)
  RETURN VARCHAR2
IS
    v_user  user_accounts_view.ua_name%TYPE := g_effective_user;

	is_context_admin number;
	has_read_privilege number;

	is_der_admin varchar2(3);

BEGIN
begin
select der_admin_ind into is_der_admin
from user_Accounts
where ua_name = p_ua_name;
exception when no_data_found then
return 'No';
end;


    IF g_security_enabled = 'No' OR is_der_admin = 'Yes' THEN
	   RETURN 'Yes';
	END IF;

	IF g_delegated_auth <> 'Yes' THEN
	    v_user := p_ua_name;
	END IF;


	select count(*) into is_context_admin
	from user_accounts u,
	sc_contexts c,
	sc_user_accounts sua
	where u.ua_name = sua.ua_name
	and sua.scl_name = c.scl_name
	and context_admin_ind = 'Yes'
	and u.ua_name = v_user
	and conte_idseq = p_conte_idseq;

	if is_context_admin >0 then
	 RETURN 'Yes';
	end if;


	select count(*) into has_read_privilege
	from user_accounts u,
	sc_contexts c,
	user_groups ug,
	sc_groups sua,
	grp_business_roles ubr,
	business_roles_lov br
	where u.ua_name = ug.ua_name
	and ug.grp_name = sua.grp_name
	and sua.scl_name = c.scl_name
	and sua.SCG_IDSEQ =ubr.SCG_IDSEQ
	and ubr.BRL_NAME =  br.brl_name
	and (br.CREATE_ALLOWED_IND = 'Yes' or br.UPDATE_ALLOWED_IND = 'Yes')
	and u.ua_name = v_user
	and conte_idseq = p_conte_idseq;

    if has_read_privilege >0 then
	 RETURN 'Yes';
	end if;

	select count(*) into has_read_privilege
	from user_accounts u,
	sc_contexts c,
	sc_user_accounts sua,
	ua_business_roles ubr,
	business_roles_lov br
	where u.ua_name = sua.ua_name
	and sua.scl_name = c.scl_name
	and sua.SCUA_IDSEQ =ubr.SCUA_IDSEQ
	and ubr.BRL_NAME =  br.brl_name
	and (br.CREATE_ALLOWED_IND = 'Yes' or br.UPDATE_ALLOWED_IND = 'Yes')
	and u.ua_name = v_user
	and conte_idseq = p_conte_idseq;

    if has_read_privilege >0 then
	 RETURN 'Yes';
	end if;

	 RETURN 'No';

END;

FUNCTION workflow_rule (
  p_ua_name        user_accounts_view.ua_name%TYPE,
  p_asl_name       ac_status_lov_view.asl_name%TYPE,
  p_from_asl_name  ac_status_lov_view.asl_name%TYPE,
  p_conte_idseq    CONTEXTS.conte_idseq%TYPE)
  RETURN VARCHAR2 IS

    CURSOR wf_enabled_cursor IS
      SELECT SCL_NAME
	    FROM AC_WF_RULES_VIEW awrv
       WHERE EXISTS (SELECT 1
	                   FROM SC_CONTEXTS_VIEW scv
		              WHERE scv.conte_idseq LIKE NVL(p_conte_idseq,'%')
					    AND scv.scl_name = awrv.scl_name);

    wf_enabled_rec wf_enabled_cursor%ROWTYPE;

    v_user  user_accounts_view.ua_name%TYPE := g_effective_user;

    CURSOR available_wf_cursor IS
    SELECT TO_ASL_NAME
      FROM AC_WF_RULES_VIEW awrv
     WHERE TO_ASL_NAME = p_asl_name AND FROM_ASL_NAME = NVL(p_from_asl_name, FROM_ASL_NAME)
	 AND scl_name = wf_enabled_rec.scl_name
	 AND EXISTS (SELECT 1
	               FROM AC_WF_BUSINESS_ROLES_VIEW awbrv
	 		  WHERE awrv.AWR_IDSEQ = awbrv.AWR_IDSEQ
			    AND EXISTS (SELECT 1
	 		                  FROM UA_BUSINESS_ROLES_VIEW ubrv
			                 WHERE ubrv.BRL_NAME = awbrv.BRL_NAME
					       AND EXISTS (SELECT 1
					                     FROM SC_USER_ACCOUNTS_VIEW suav
					                    WHERE suav.SCUA_IDSEQ = ubrv.SCUA_IDSEQ
								    AND suav.UA_NAME = v_user
								    AND EXISTS (SELECT 1
								                  FROM SC_CONTEXTS_VIEW scv
								                 WHERE scv.scl_name = suav.scl_name
										    AND scv.conte_idseq LIKE NVL(p_conte_idseq, '%')))))
UNION
    SELECT TO_ASL_NAME
    FROM AC_WF_RULES_VIEW awrv
   WHERE TO_ASL_NAME = p_asl_name
     AND FROM_ASL_NAME = NVL(p_from_asl_name, FROM_ASL_NAME)
	 AND scl_name = wf_enabled_rec.scl_name
     AND EXISTS (SELECT 1
                   FROM AC_WF_BUSINESS_ROLES_VIEW awbrv
                  WHERE awrv.AWR_IDSEQ = awbrv.AWR_IDSEQ
                    AND EXISTS (SELECT 1
                                  FROM GRP_BUSINESS_ROLES_VIEW gbrv
                                 WHERE gbrv.BRL_NAME = awbrv.BRL_NAME
                                   AND EXISTS (SELECT 1
                                                 FROM SC_GROUPS_VIEW sgv,USER_GROUPS_VIEW ugv
                                                WHERE sgv.SCG_IDSEQ = gbrv.SCG_IDSEQ
                                                  AND sgv.grp_name = ugv.grp_name
                                                  AND ugv.UA_NAME = v_user
                                                  AND EXISTS (SELECT 1
                                                                FROM SC_CONTEXTS_VIEW scv
                                                               WHERE scv.scl_name = sgv.scl_name
                                                                 AND scv.conte_idseq LIKE NVL(p_conte_idseq, '%')))));


	available_wf_rec available_wf_cursor%ROWTYPE;

BEGIN
    -- The first check is to verify that some form of workflow exists in the
    -- current context.  If no workflow transition rules exist, then WF is
    -- disabled.  When disabled, the user can select any value from the
    -- AC_STATUS_LOV and the transition will be valid.

    OPEN wf_enabled_cursor;
    FETCH wf_enabled_cursor INTO wf_enabled_rec;

    IF wf_enabled_cursor%NOTFOUND THEN
        CLOSE wf_enabled_cursor;
	    RETURN 'Yes';
    END IF;

    CLOSE wf_enabled_cursor;
dbms_output.put_line(v_user);
	dbms_output.put_line(wf_enabled_rec.scl_name);

    IF g_security_enabled = 'No' OR g_der_admin = 'Yes' THEN
	   RETURN 'Yes';
	END IF;

	IF g_delegated_auth <> 'Yes' THEN
	    v_user := p_ua_name;
	END IF;
dbms_output.put_line(v_user);
	-- Check the specific ASL.

	OPEN available_wf_cursor;
	FETCH available_wf_cursor INTO available_wf_rec;

	IF available_wf_cursor%NOTFOUND THEN
	    CLOSE available_wf_cursor;
        dbms_output.put_line('Nothing FOUND');
	    RETURN 'No';
	END IF;

    CLOSE available_wf_cursor;
	RETURN 'Yes';
END;

Procedure get_ac_by_idseq(p_ac_idseq    IN  VARCHAR2,
                          p_conte_idseq OUT VARCHAR2,
						  p_actl_name   OUT VARCHAR2,
						  p_asl_name    OUT VARCHAR2,
						  p_cmsl_name   OUT VARCHAR2)
IS
BEGIN
	-- Lookup the administered component and it's associated security context information.

   SELECT CONTE_IDSEQ, ACTL_NAME, ASL_NAME, CMSL_NAME
     INTO p_conte_idseq, p_actl_name, p_asl_name, p_cmsl_name
	 FROM ADMINISTERED_COMPONENTS
	WHERE AC_IDSEQ = p_ac_idseq;

EXCEPTION
  WHEN OTHERS THEN
      Cg$errors.push('Error accessing administratered component where AC_IDSEQ = ' || p_ac_idseq);
	  Cg$errors.raise_failure;
END;

FUNCTION has_privilege (
  p_ua_name      user_accounts_view.ua_name%TYPE,
  p_conte_idseq  ADMINISTERED_COMPONENTS.conte_idseq%TYPE,
  p_actl_name    ADMINISTERED_COMPONENTS.actl_name%TYPE,
  p_asl_name     ADMINISTERED_COMPONENTS.asl_name%TYPE,
  p_cmsl_name    ADMINISTERED_COMPONENTS.cmsl_name%TYPE,
  p_privilege    VARCHAR2)
  RETURN VARCHAR2
IS
    CURSOR security_context_cursor IS
	    SELECT SCL_NAME
		  FROM SC_CONTEXTS_VIEW
		 WHERE CONTE_IDSEQ = p_conte_idseq;

    security_context_rec security_context_cursor%ROWTYPE;

    v_user  user_accounts_view.ua_name%TYPE := g_effective_user;

	CURSOR ac_matrix_rights_cursor (
	  p_scl_name  VARCHAR2,
	  p_asl_name  VARCHAR2,
	  p_cmsl_name VARCHAR2,
	  p_ua_name   VARCHAR2,
	  p_actl_name VARCHAR2) IS
	   SELECT DECODE(p_privilege, 'READ',     read_allowed_ind,
	                              'UPDATE',   update_allowed_ind,
								  'DELETE',   delete_allowed_ind,
								  'VERSION',  version_allowed_ind,
								  'CHECKOUT', checkout_allowed_ind,
								  'No') has_right
	     FROM AC_ACTIONS_MATRIX_VIEW am
		WHERE am.SCL_NAME = p_scl_name
		  AND am.ASL_NAME = p_asl_name
		  AND am.CMSL_NAME = p_cmsl_name
		  AND EXISTS (SELECT 1
		                        FROM SC_USER_ACCOUNTS_VIEW sua, UA_BUSINESS_ROLES_VIEW ubr
							   WHERE sua.SCL_NAME = am.SCL_NAME
							     AND sua.UA_NAME = v_user
							     AND ubr.SCUA_IDSEQ = sua.SCUA_IDSEQ
								 AND ubr.ACTL_NAME = p_actl_name)
		ORDER BY 1 DESC; --Descending sort puts 'Yes' on top

	CURSOR group_ac_matrix_rights_cursor (
	  p_scl_name  VARCHAR2,
	  p_asl_name  VARCHAR2,
	  p_cmsl_name VARCHAR2,
	  p_ua_name   VARCHAR2,
	  p_actl_name VARCHAR2) IS
	   SELECT DECODE(p_privilege, 'READ',     read_allowed_ind,
	                              'UPDATE',   update_allowed_ind,
								  'DELETE',   delete_allowed_ind,
								  'VERSION',  version_allowed_ind,
								  'CHECKOUT', checkout_allowed_ind,
								  'No') has_right
	     FROM AC_ACTIONS_MATRIX_VIEW am
		WHERE am.SCL_NAME = p_scl_name
		  AND am.ASL_NAME = p_asl_name
		  AND am.CMSL_NAME = p_cmsl_name
		  AND EXISTS (SELECT 1
		                        FROM SC_GROUPS_VIEW sgv, GRP_BUSINESS_ROLES_VIEW gbr
							   WHERE sgv.SCL_NAME = am.SCL_NAME
							     AND sgv.GRP_NAME IN (SELECT GRP_NAME FROM USER_GROUPS_VIEW WHERE UA_NAME = v_user)
							     AND gbr.SCG_IDSEQ = sgv.SCG_IDSEQ
								 AND gbr.ACTL_NAME = p_actl_name)
		ORDER BY 1 DESC;

    CURSOR ac_user_rights_cursor  (
	  p_scl_name  VARCHAR2,
	  p_asl_name  VARCHAR2,
	  p_cmsl_name VARCHAR2,
	  p_ua_name   VARCHAR2,
	  p_actl_name VARCHAR2) IS
	   SELECT DECODE(p_privilege, 'READ',     read_allowed_ind,
	                              'UPDATE',   update_allowed_ind,
								  'DELETE',   delete_allowed_ind,
								  'VERSION',  version_allowed_ind,
								  'CHECKOUT', checkout_allowed_ind,
								  'No') has_right
	     FROM BUSINESS_ROLES_LOV brl
		WHERE EXISTS (SELECT 1
		                     FROM SC_USER_ACCOUNTS_VIEW sua, UA_BUSINESS_ROLES_VIEW ubr
							WHERE sua.SCL_NAME = p_scl_name
							  AND sua.UA_NAME = p_ua_name
							  AND ubr.SCUA_IDSEQ = sua.SCUA_IDSEQ
							  AND ubr.ACTL_NAME = p_actl_name
							  AND ubr.BRL_NAME = brl.BRL_NAME
							  AND NOT EXISTS (SELECT 1
							                    FROM AC_ACTIONS_MATRIX_VIEW am2
											   WHERE am2.SCL_NAME = p_scl_name
											     AND am2.SCL_NAME = sua.SCL_NAME
												 AND am2.ASL_NAME = p_asl_name
												 AND am2.CMSL_NAME = p_cmsl_name) )
	    ORDER BY 1 DESC;

    CURSOR ac_group_rights_cursor  (
	  p_scl_name  VARCHAR2,
	  p_asl_name  VARCHAR2,
	  p_cmsl_name VARCHAR2,
	  p_ua_name   VARCHAR2,
	  p_actl_name VARCHAR2) IS
	   SELECT DECODE(p_privilege, 'READ',     read_allowed_ind,
	                              'UPDATE',   update_allowed_ind,
								  'DELETE',   delete_allowed_ind,
								  'VERSION',  version_allowed_ind,
								  'CHECKOUT', checkout_allowed_ind,
								  'No') has_right
	     FROM BUSINESS_ROLES_LOV brl
		WHERE EXISTS (SELECT 1
		                     FROM SC_GROUPS_VIEW sg, GRP_BUSINESS_ROLES_VIEW gbr
							WHERE sg.SCL_NAME = p_scl_name
							  AND sg.GRP_NAME IN (SELECT GRP_NAME
							                        FROM USER_GROUPS_VIEW
												   WHERE UA_NAME = p_ua_name)
							  AND gbr.SCG_IDSEQ = sg.SCG_IDSEQ
							  AND gbr.ACTL_NAME = p_actl_name
							  AND gbr.BRL_NAME = brl.BRL_NAME
							  AND NOT EXISTS (SELECT 1
							                    FROM AC_ACTIONS_MATRIX_VIEW am2
											   WHERE am2.SCL_NAME = p_scl_name
											     AND am2.SCL_NAME = sg.SCL_NAME
												 AND am2.ASL_NAME = p_asl_name
												 AND am2.CMSL_NAME = p_cmsl_name  ) )
	    ORDER BY 1 DESC;

    ac_matrix_rights_rec ac_matrix_rights_cursor%ROWTYPE;
	group_ac_matrix_rights_rec group_ac_matrix_rights_cursor%ROWTYPE;
    ac_user_rights_rec   ac_user_rights_cursor%ROWTYPE;
    ac_group_rights_rec  ac_group_rights_cursor%ROWTYPE;
	-- ADD FOR ALL_BROWSER
	cursor all_browser_cursor is
	  select * from user_groups
	  where GRP_NAME='ALL_BROWSER'
	  AND UA_NAME = v_user;
  	all_browser_rec  all_browser_cursor%ROWTYPE;
	-- end add 011503
BEGIN
	--ADD FOR ALL_BROWSER
	IF p_privilege = 'READ' THEN
	   OPEN all_browser_cursor;
	   FETCH all_browser_cursor INTO all_browser_rec;
	   IF all_browser_cursor%FOUND THEN
	      CLOSE all_browser_cursor;
		  RETURN 'Yes';
       END IF;
	   CLOSE all_browser_cursor;
	END IF;
	--end 011503
    IF g_security_enabled = 'No' OR g_der_admin = 'Yes' THEN
	   RETURN 'Yes';
	END IF;

	IF NVL(g_delegated_auth,'No') <> 'Yes'  THEN
	    v_user := p_ua_name;
	END IF;

	IF INSTR(p_ua_name,'!')<>0 THEN
		v_user:=SUBSTR(p_ua_name,1,INSTR(p_ua_name,'-')-1);
    END IF;

	OPEN security_context_cursor;
	FETCH security_context_cursor INTO security_context_rec;

	IF security_context_cursor%NOTFOUND THEN
    	CLOSE security_context_cursor;
		RETURN 'No';
	END IF;

	CLOSE security_context_cursor;
	-- Based on the security context of the administered component, check to see if the current
	-- user is a context administrator for the AC's security context.

	IF Is_Context_Admin(p_ua_name, security_context_rec.scl_name) = 'Yes' THEN
	    RETURN 'Yes';
	END IF;


	-- Determine if an AC_ACTIONS_MATRIX record exists for the current
	-- state of the component.  If one does, this will be the user's
	-- rights for the component.

	OPEN ac_matrix_rights_cursor (security_context_rec.scl_name,
	                              p_asl_name,
	                              p_cmsl_name,
	    			          	  v_user,
				    		      p_actl_name);
 	FETCH ac_matrix_rights_cursor INTO ac_matrix_rights_rec;

    IF ac_matrix_rights_cursor%NOTFOUND THEN
        CLOSE ac_matrix_rights_cursor;
        OPEN group_ac_matrix_rights_cursor(security_context_rec.scl_name,
		                                   p_asl_name,
										   p_cmsl_name,
										   v_user,
										   p_actl_name);
		FETCH group_ac_matrix_rights_cursor INTO group_ac_matrix_rights_rec;

        -- If Group matrix not found, do nothing

		IF group_ac_matrix_rights_cursor%NOTFOUND THEN
		   CLOSE group_ac_matrix_rights_cursor;
        ELSE
	       CLOSE group_ac_matrix_rights_cursor;
    	   RETURN group_ac_matrix_rights_rec.has_right;
		END IF;
	ELSE
       CLOSE ac_matrix_rights_cursor;
	   RETURN ac_matrix_rights_rec.has_right;
   	END IF;

	-- There isn't a matrix record, look for a user right on the
	-- component.

	OPEN ac_user_rights_cursor (security_context_rec.scl_name,
                                p_asl_name,
	                            p_cmsl_name,
	    			          	v_user,
				    		    p_actl_name);
	FETCH ac_user_rights_cursor INTO ac_user_rights_rec;

    -- If User right not found, do nothing

	IF ac_user_rights_cursor%NOTFOUND THEN
	    CLOSE ac_user_rights_cursor;

	    OPEN ac_group_rights_cursor (security_context_rec.scl_name,
	                             p_asl_name,
	                             p_cmsl_name,
	    			          	 v_user,
				    		     p_actl_name);

	    FETCH ac_group_rights_cursor INTO ac_group_rights_rec;

        IF ac_group_rights_cursor%NOTFOUND THEN
		   CLOSE ac_group_rights_cursor;

		   RETURN 'No';
		END IF;

		CLOSE ac_group_rights_cursor;
		RETURN ac_group_rights_rec.has_right;
	END IF;

	RETURN ac_user_rights_rec.has_right;

	CLOSE ac_user_rights_cursor;
END has_privilege;

FUNCTION has_read_privilege (
  p_ua_name     user_accounts_view.ua_name%TYPE ,
  p_ac_idseq    ADMINISTERED_COMPONENTS.ac_idseq%TYPE,
  p_source  VARCHAR2)
  RETURN VARCHAR2
IS
    v_conte_idseq  ADMINISTERED_COMPONENTS.conte_idseq%TYPE;
    v_actl_name    ADMINISTERED_COMPONENTS.actl_name%TYPE;
    v_asl_name     ADMINISTERED_COMPONENTS.asl_name%TYPE;
    v_cmsl_name    ADMINISTERED_COMPONENTS.cmsl_name%TYPE;
BEGIN
    get_ac_by_idseq(p_ac_idseq, v_conte_idseq, v_actl_name, v_asl_name, v_cmsl_name);
    RETURN has_privilege(p_ua_name, v_conte_idseq, v_actl_name, v_asl_name, v_cmsl_name, 'READ');
END has_read_privilege;

FUNCTION has_read_privilege (
  p_ua_name     user_accounts_view.ua_name%TYPE ,
  p_ac_idseq    ADMINISTERED_COMPONENTS.ac_idseq%TYPE)
  RETURN VARCHAR2
IS
    v_conte_idseq  ADMINISTERED_COMPONENTS.conte_idseq%TYPE;
    v_actl_name    ADMINISTERED_COMPONENTS.actl_name%TYPE;
    v_asl_name     ADMINISTERED_COMPONENTS.asl_name%TYPE;
    v_cmsl_name    ADMINISTERED_COMPONENTS.cmsl_name%TYPE;
BEGIN
    get_ac_by_idseq(p_ac_idseq, v_conte_idseq, v_actl_name, v_asl_name, v_cmsl_name);
    RETURN has_privilege(p_ua_name, v_conte_idseq, v_actl_name, v_asl_name, v_cmsl_name, 'READ');
END has_read_privilege;

FUNCTION has_extended_read_privilege (
  p_ua_name     user_accounts_view.ua_name%TYPE ,
  p_ac_idseq    ADMINISTERED_COMPONENTS.ac_idseq%TYPE)
  RETURN VARCHAR2
IS
    v_conte_idseq  ADMINISTERED_COMPONENTS.conte_idseq%TYPE;
    v_actl_name    ADMINISTERED_COMPONENTS.actl_name%TYPE;
    v_asl_name     ADMINISTERED_COMPONENTS.asl_name%TYPE;
    v_cmsl_name    ADMINISTERED_COMPONENTS.cmsl_name%TYPE;

	CURSOR de_rights_cursor IS
	    SELECT 'Yes'
		  FROM data_elements_view dev
		 WHERE de_idseq = p_ac_idseq
		   AND EXISTS (SELECT 1
                         FROM value_domains_view vdv
						WHERE vdv.vd_idseq = dev.vd_idseq)
		   AND EXISTS (SELECT 1
		                 FROM data_element_concepts_view decv
						WHERE decv.dec_idseq = dev.dec_idseq);

    rights_rec de_rights_cursor%ROWTYPE;
BEGIN
    get_ac_by_idseq(p_ac_idseq, v_conte_idseq, v_actl_name, v_asl_name, v_cmsl_name);

    IF has_privilege(p_ua_name, v_conte_idseq, v_actl_name, v_asl_name, v_cmsl_name, 'READ') = 'No' THEN
	    RETURN 'No';
	END IF;

    -- Based on the component type, check for read on any of the
	-- foreign keys (by component type)

	IF v_actl_name = 'DATAELEMENT' THEN
	    OPEN de_rights_cursor;
		FETCH de_rights_cursor INTO rights_rec;

		IF de_rights_cursor%NOTFOUND THEN
		   CLOSE de_rights_cursor;
		   RETURN 'No';
		END IF;

		CLOSE de_rights_cursor;
    END IF;

	RETURN 'Yes';
END has_extended_read_privilege;

FUNCTION has_read_privilege (
  p_ua_name      user_accounts_view.ua_name%TYPE,
  p_conte_idseq  ADMINISTERED_COMPONENTS.conte_idseq%TYPE,
  p_actl_name    ADMINISTERED_COMPONENTS.actl_name%TYPE,
  p_asl_name     ADMINISTERED_COMPONENTS.asl_name%TYPE,
  p_cmsl_name    ADMINISTERED_COMPONENTS.cmsl_name%TYPE)
  RETURN VARCHAR2
IS
BEGIN
    RETURN has_privilege(p_ua_name, p_conte_idseq, p_actl_name, p_asl_name, p_cmsl_name, 'READ');
END has_read_privilege;


FUNCTION has_update_privilege (
  p_ua_name     user_accounts_view.ua_name%TYPE ,
  p_ac_idseq    ADMINISTERED_COMPONENTS.ac_idseq%TYPE)
  RETURN VARCHAR2
IS
    v_conte_idseq  ADMINISTERED_COMPONENTS.conte_idseq%TYPE;
    v_actl_name    ADMINISTERED_COMPONENTS.actl_name%TYPE;
    v_asl_name     ADMINISTERED_COMPONENTS.asl_name%TYPE;
    v_cmsl_name    ADMINISTERED_COMPONENTS.cmsl_name%TYPE;
BEGIN
	get_ac_by_idseq(p_ac_idseq, v_conte_idseq, v_actl_name, v_asl_name, v_cmsl_name);

    RETURN has_privilege(p_ua_name, v_conte_idseq, v_actl_name, v_asl_name, v_cmsl_name, 'UPDATE');
END has_update_privilege;

FUNCTION has_update_privilege (
  p_ua_name      user_accounts_view.ua_name%TYPE,
  p_conte_idseq  ADMINISTERED_COMPONENTS.conte_idseq%TYPE,
  p_actl_name    ADMINISTERED_COMPONENTS.actl_name%TYPE,
  p_asl_name     ADMINISTERED_COMPONENTS.asl_name%TYPE,
  p_cmsl_name    ADMINISTERED_COMPONENTS.cmsl_name%TYPE)
  RETURN VARCHAR2 IS
BEGIN
/*
    htp.p(p_ua_name     || '<BR>');
    htp.p(p_conte_idseq || '<BR>');
    htp.p(p_actl_name || '<BR>');
    htp.p(p_asl_name || '<BR>');
    htp.p(NVL(p_cmsl_name, 'NULL') || '<BR>');
	htp.p(has_privilege(p_ua_name, p_conte_idseq, p_actl_name, p_asl_name, p_cmsl_name, 'UPDATE'));
*/
    RETURN has_privilege(p_ua_name, p_conte_idseq, p_actl_name, p_asl_name, p_cmsl_name, 'UPDATE');
END has_update_privilege;

FUNCTION has_delete_privilege (
  p_ua_name     user_accounts_view.ua_name%TYPE ,
  p_ac_idseq    ADMINISTERED_COMPONENTS.ac_idseq%TYPE)
  RETURN VARCHAR2
IS
    v_conte_idseq  ADMINISTERED_COMPONENTS.conte_idseq%TYPE;
    v_actl_name    ADMINISTERED_COMPONENTS.actl_name%TYPE;
    v_asl_name     ADMINISTERED_COMPONENTS.asl_name%TYPE;
    v_cmsl_name    ADMINISTERED_COMPONENTS.cmsl_name%TYPE;
BEGIN
    get_ac_by_idseq(p_ac_idseq, v_conte_idseq, v_actl_name, v_asl_name, v_cmsl_name);

    RETURN has_privilege(p_ua_name, v_conte_idseq, v_actl_name, v_asl_name, v_cmsl_name, 'DELETE');
END has_delete_privilege;

FUNCTION has_delete_privilege (
  p_ua_name      user_accounts_view.ua_name%TYPE,
  p_conte_idseq  ADMINISTERED_COMPONENTS.conte_idseq%TYPE,
  p_actl_name    ADMINISTERED_COMPONENTS.actl_name%TYPE,
  p_asl_name     ADMINISTERED_COMPONENTS.asl_name%TYPE,
  p_cmsl_name    ADMINISTERED_COMPONENTS.cmsl_name%TYPE)
  RETURN VARCHAR2 IS
BEGIN
    RETURN has_privilege(p_ua_name, p_conte_idseq, p_actl_name, p_asl_name, p_cmsl_name, 'DELETE');
END has_delete_privilege;

FUNCTION has_version_privilege (
  p_ua_name     user_accounts_view.ua_name%TYPE ,
  p_ac_idseq    ADMINISTERED_COMPONENTS.ac_idseq%TYPE)
  RETURN VARCHAR2
IS
    v_conte_idseq  ADMINISTERED_COMPONENTS.conte_idseq%TYPE;
    v_actl_name    ADMINISTERED_COMPONENTS.actl_name%TYPE;
    v_asl_name     ADMINISTERED_COMPONENTS.asl_name%TYPE;
    v_cmsl_name    ADMINISTERED_COMPONENTS.cmsl_name%TYPE;
BEGIN
    get_ac_by_idseq(p_ac_idseq, v_conte_idseq, v_actl_name, v_asl_name, v_cmsl_name);

    RETURN has_privilege(p_ua_name, v_conte_idseq, v_actl_name, v_asl_name, v_cmsl_name, 'VERSION');
END has_version_privilege;

FUNCTION has_checkout_privilege (
  p_ua_name     user_accounts_view.ua_name%TYPE ,
  p_ac_idseq    ADMINISTERED_COMPONENTS.ac_idseq%TYPE)
  RETURN VARCHAR2
IS
    v_conte_idseq  ADMINISTERED_COMPONENTS.conte_idseq%TYPE;
    v_actl_name    ADMINISTERED_COMPONENTS.actl_name%TYPE;
    v_asl_name     ADMINISTERED_COMPONENTS.asl_name%TYPE;
    v_cmsl_name    ADMINISTERED_COMPONENTS.cmsl_name%TYPE;
BEGIN
    get_ac_by_idseq(p_ac_idseq, v_conte_idseq, v_actl_name, v_asl_name, v_cmsl_name);

    RETURN has_privilege(p_ua_name, v_conte_idseq, v_actl_name, v_asl_name, v_cmsl_name, 'CHECKOUT');
END has_checkout_privilege;

FUNCTION has_comp_create_priv (
  p_ua_name   user_accounts_view.ua_name%TYPE ,
  p_actl_name ac_types_lov_view.actl_name%TYPE )
  RETURN VARCHAR2
IS
    v_user user_accounts_view.ua_name%TYPE := g_effective_user;

    CURSOR has_create_right_cursor IS
	    SELECT CREATE_ALLOWED_IND
		  FROM BUSINESS_ROLES_LOV_VIEW brlv
		 WHERE CREATE_ALLOWED_IND = 'Yes'
		   AND EXISTS (SELECT 1
                           FROM UA_BUSINESS_ROLES_VIEW ubrv
                          WHERE ubrv.BRL_NAME = brlv.BRL_NAME
						    AND ubrv.ACTL_NAME = p_actl_name
                            AND EXISTS (SELECT 1
				                          FROM SC_USER_ACCOUNTS_VIEW suav
                                         WHERE suav.SCUA_IDSEQ = ubrv.SCUA_IDSEQ
                  						   AND suav.UA_NAME = v_user) );

    CURSOR has_group_create_right_cursor IS
	    SELECT CREATE_ALLOWED_IND
		  FROM BUSINESS_ROLES_LOV_VIEW brlv
		 WHERE CREATE_ALLOWED_IND = 'Yes'
		   AND EXISTS (SELECT 1 FROM GRP_BUSINESS_ROLES_VIEW gbrv
                        WHERE gbrv.ACTL_NAME = p_actl_name
                          AND EXISTS (SELECT 1 FROM SC_GROUPS_VIEW sgv
                                       WHERE sgv.SCG_IDSEQ = gbrv.SCG_IDSEQ
                                       AND EXISTS (SELECT 1 FROM USER_GROUPS_VIEW ug
                                                    WHERE ug.UA_NAME = v_user
                                                      AND ug.GRP_NAME = sgv.grp_name)) );

    has_create_right_rec has_create_right_cursor%ROWTYPE;
    has_group_create_right_rec has_group_create_right_cursor%ROWTYPE;

BEGIN
    IF g_security_enabled = 'No' OR g_der_admin = 'Yes' THEN
	   RETURN 'Yes';
	END IF;

	IF g_delegated_auth <> 'Yes' THEN
	    v_user := p_ua_name;
	END IF;

	-- If the user is a context administrator in any context, they have
	-- the right to create any component.

	IF has_context_admin(v_user) = 'Yes' THEN
	    RETURN 'Yes';
	END IF;

	-- Since security is enabled, and the user has no special privilege,
	-- check if they have create privilege from any user or group business role.

	OPEN has_create_right_cursor;
	FETCH has_create_right_cursor INTO has_create_right_rec;

	IF has_create_right_cursor%NOTFOUND THEN
	    CLOSE has_create_right_cursor;

        OPEN has_group_create_right_cursor;
        FETCH has_group_create_right_cursor INTO has_group_create_right_rec;

	    IF has_group_create_right_cursor%NOTFOUND THEN
          CLOSE has_group_create_right_cursor;
          RETURN 'No';
     	ELSE
 		  CLOSE has_group_create_right_cursor;
    	  RETURN has_group_create_right_rec.CREATE_ALLOWED_IND;
        END IF;
	ELSE
      CLOSE has_create_right_cursor;
      RETURN has_create_right_rec.CREATE_ALLOWED_IND;
	END IF;

END has_comp_create_priv;

FUNCTION has_role_priv (
  p_ua_name    user_accounts_view.ua_name%TYPE,
  b_role   ua_business_roles_view.brl_name%TYPE,
  p_actl_name ac_types_lov_view.actl_name%TYPE)
  RETURN VARCHAR2 IS

    v_user user_accounts_view.ua_name%TYPE := g_effective_user;

	CURSOR brl_name_cursor IS
	      SELECT brl_name
            FROM UA_BUSINESS_ROLES_VIEW ubrv
           WHERE ACTL_NAME = p_actl_name
		     AND brl_name = b_role
             AND EXISTS (SELECT 1
    		               FROM SC_USER_ACCOUNTS_VIEW suav
					      WHERE suav.UA_NAME = v_user
						    AND suav.SCUA_IDSEQ = ubrv.SCUA_IDSEQ );

	CURSOR group_brl_name_cursor IS
	      SELECT brl_name
            FROM GRP_BUSINESS_ROLES_VIEW ubrv
           WHERE ACTL_NAME = p_actl_name
		     AND brl_name = b_role
             AND EXISTS (SELECT 1 FROM SC_GROUPS_VIEW sgv
                         WHERE sgv.scg_idseq = ubrv.scg_idseq
                           AND EXISTS (SELECT 1 FROM USER_GROUPS_VIEW ug
                                        WHERE ug.UA_NAME = v_user
                                          AND ug.GRP_NAME = sgv.grp_name));

	brl_name_rec brl_name_cursor%ROWTYPE;
	group_brl_name_rec group_brl_name_cursor%ROWTYPE;

BEGIN
    IF g_security_enabled = 'No' OR g_der_admin = 'Yes' THEN
	   RETURN 'Yes';
	END IF;

	IF g_delegated_auth <> 'Yes' THEN
	    v_user := p_ua_name;
	END IF;

	OPEN brl_name_cursor;
	FETCH brl_name_cursor INTO brl_name_rec;

	IF brl_name_cursor%NOTFOUND THEN
	    CLOSE brl_name_cursor;

        OPEN group_brl_name_cursor;
        FETCH group_brl_name_cursor INTO group_brl_name_rec;
        IF group_brl_name_cursor%NOTFOUND THEN
		   CLOSE group_brl_name_cursor;
	       RETURN 'No';
	    ELSE
		   CLOSE group_brl_name_cursor;
		   RETURN 'Yes';
		END IF;
	ELSE
	   CLOSE brl_name_cursor;
	   RETURN 'Yes';
	END IF;

END has_role_priv;

FUNCTION has_business_role (
  account_name    user_accounts_view.ua_name%TYPE,
  b_role   ua_business_roles_view.brl_name%TYPE)
  RETURN VARCHAR2 IS
    unknown_user EXCEPTION;
    unknown_comp EXCEPTION;
    ret_val VARCHAR2(5) := 'FALSE';
  BEGIN
    ret_val := 'Yes';
    RETURN ret_val;
END has_business_role;

FUNCTION has_comp_business_role (
  account_name    user_accounts_view.ua_name%TYPE ,
  b_role   ua_business_roles_view.brl_name%TYPE ,
  comp_typ ua_business_roles_view.actl_name%TYPE ,
  ctx_id          VARCHAR2  )
  RETURN VARCHAR2 IS
    unknown_user EXCEPTION;
    unknown_comp EXCEPTION;
    ret_val VARCHAR2(5) := 'FALSE';
  BEGIN
    ret_val := 'Yes';
    RETURN ret_val;
END has_comp_business_role;

FUNCTION has_create_privilege (
  p_ua_name      user_accounts_view.ua_name%TYPE  ,
  p_actl_name    ac_types_lov_view.actl_name%TYPE ,
  p_conte_idseq  CONTEXTS.conte_idseq%TYPE        )
  RETURN VARCHAR2
IS
    v_message VARCHAR2(2000);
    
    CURSOR security_context_cursor IS
	    SELECT SCL_NAME
		  FROM SC_CONTEXTS_VIEW
		 WHERE CONTE_IDSEQ = p_conte_idseq;

    security_context_rec security_context_cursor%ROWTYPE;

    v_user user_accounts_view.ua_name%TYPE := g_effective_user;

    CURSOR has_create_cursor(p_scl_name VARCHAR2) IS
	    SELECT CREATE_ALLOWED_IND
		  FROM BUSINESS_ROLES_LOV_VIEW brlv
		 WHERE CREATE_ALLOWED_IND = 'Yes'
		   AND EXISTS (SELECT 1
		                 FROM UA_BUSINESS_ROLES_VIEW ubrv
						WHERE ubrv.BRL_NAME = brlv.BRL_NAME
						  AND ubrv.ACTL_NAME LIKE NVL(p_actl_name, '%')
						  AND EXISTS (SELECT 1
						                FROM SC_USER_ACCOUNTS_VIEW suav
									   WHERE suav.UA_NAME = v_user
									     AND suav.SCUA_IDSEQ = ubrv.SCUA_IDSEQ
									     AND suav.SCL_NAME = p_scl_name));

    CURSOR has_group_create_cursor(p_scl_name VARCHAR2) IS
	    SELECT CREATE_ALLOWED_IND
		  FROM BUSINESS_ROLES_LOV_VIEW brlv
		 WHERE CREATE_ALLOWED_IND = 'Yes'
		   AND EXISTS (SELECT 1
		                 FROM GRP_BUSINESS_ROLES_VIEW gbrv
						WHERE gbrv.BRL_NAME = brlv.BRL_NAME
						  AND gbrv.ACTL_NAME LIKE NVL(p_actl_name, '%')
						  AND EXISTS (SELECT 1
						                FROM SC_GROUPS_VIEW sgv
									   WHERE sgv.SCG_IDSEQ = gbrv.SCG_IDSEQ
                                         AND sgv.SCL_NAME = p_scl_name
									     AND EXISTS( SELECT 1 FROM USER_GROUPS_VIEW ugv
										              WHERE ugv.UA_NAME = v_user
									                    AND ugv.grp_name = sgv.grp_name)));


    has_create_rec has_create_cursor%ROWTYPE;
	has_group_create_rec has_group_create_cursor%ROWTYPE;

BEGIN
    v_message := '' || '1. p_ua_name = ' || p_ua_name || ', p_actl_name = ' || p_actl_name || ', p_conte_idseq = ' || p_conte_idseq;
    v_message := v_message || ' 2. g_security_enabled = ' || g_security_enabled || ', g_delegated_auth = ' || g_delegated_auth;
	   
    IF g_security_enabled = 'No' OR g_der_admin = 'Yes' THEN
     v_message := v_message || ' 3. g_security_enabled = ' || g_security_enabled || ', g_der_admin = ' || g_der_admin || ' - returning Yes.';
     insert into fb_privilege_log (date_ins, priv_log, username) values (sysdate, v_message, p_ua_name);
	   RETURN 'Yes';
    END IF;

	IF g_delegated_auth <> 'Yes' THEN
      v_message := v_message || ' 4. g_delegated_auth = Yes.';
	    v_user := p_ua_name;
	END IF;

	OPEN security_context_cursor;
	FETCH security_context_cursor INTO security_context_rec;

	IF security_context_cursor%NOTFOUND THEN
	    CLOSE security_context_cursor;
      v_message := v_message || '5. security context curosr has not records, so return value is No.';
	    insert into fb_privilege_log (date_ins, priv_log, username) values (sysdate, v_message, p_ua_name);
	    RETURN 'No';
    END IF;

	CLOSE security_context_cursor;

	IF Is_Context_Admin(p_ua_name, security_context_rec.SCL_NAME) = 'Yes' THEN
	    v_message := v_message || ' 6. context admin is Yes. so returning yes.';
	    insert into fb_privilege_log (date_ins, priv_log, username) values (sysdate, v_message, p_ua_name);
	    RETURN 'Yes';
    END IF;

    OPEN has_create_cursor(security_context_rec.SCL_NAME);
	FETCH has_create_cursor INTO has_create_rec;

	IF has_create_cursor%NOTFOUND THEN
	    CLOSE has_create_cursor;
        OPEN has_group_create_cursor(security_context_rec.SCL_NAME);
		FETCH has_group_create_cursor INTO has_group_create_rec;
		IF has_group_create_cursor%NOTFOUND THEN
		   CLOSE has_group_create_cursor;
       v_message := v_message || ' 7. group create cursor not found, so return value is No.';
		   insert into fb_privilege_log (date_ins, priv_log, username) values (sysdate, v_message, p_ua_name);
	     RETURN 'No';
		ELSE
		   CLOSE has_group_create_cursor;
       v_message := v_message || ' 8. group create cursor found, so return value is ' || has_group_create_rec.CREATE_ALLOWED_IND;
		   insert into fb_privilege_log (date_ins, priv_log, username) values (sysdate, v_message, p_ua_name);
	     RETURN has_group_create_rec.CREATE_ALLOWED_IND;
		END IF;
    ELSE
	   CLOSE has_create_cursor;
     v_message := v_message || ' 9. has create cursor found, so return value is ' || has_create_rec.CREATE_ALLOWED_IND;
	   insert into fb_privilege_log (date_ins, priv_log, username) values (sysdate, v_message, p_ua_name);
	   RETURN has_create_rec.CREATE_ALLOWED_IND;
	END IF;

	CLOSE has_create_cursor;

	IF has_create_rec.CREATE_ALLOWED_IND = 'Yes' THEN
	    v_message := v_message || ' 10. has_create_rec.CREATE_ALLOWED_IND = Yes, return = Yes';
	   insert into fb_privilege_log (date_ins, priv_log, username) values (sysdate, v_message, p_ua_name);
	   RETURN 'Yes';
	ELSE
	    v_message := v_message || ' 11. has_create_rec.CREATE_ALLOWED_IND = No, return = No';
	    insert into fb_privilege_log (date_ins, priv_log, username) values (sysdate, v_message, p_ua_name);
	    RETURN 'No';
	END IF;
END has_create_privilege;


FUNCTION has_wf_transition_priv (
  p_ua_name       user_accounts_view.ua_name%TYPE,
  p_conte_idseq   ADMINISTERED_COMPONENTS.conte_idseq%TYPE,
  p_actl_name     ADMINISTERED_COMPONENTS.actl_name%TYPE,
  p_from_asl_name AC_WF_RULES.from_asl_name%TYPE,
  p_to_asl_name   AC_WF_RULES.to_asl_name%TYPE)
  RETURN VARCHAR2
IS
  CURSOR wf_enabled_cursor IS
    SELECT SCL_NAME
	  FROM AC_WF_RULES_VIEW awrv
	 WHERE EXISTS (SELECT 1
	                 FROM SC_CONTEXTS_VIEW scv
					WHERE scv.conte_idseq = p_conte_idseq
					  AND scv.scl_name = awrv.scl_name);

  wf_enabled_rec wf_enabled_cursor%ROWTYPE;

  CURSOR transition_exists_cursor IS
    SELECT awrv.awr_idseq
	  FROM AC_WF_RULES_VIEW awrv
	 WHERE awrv.from_asl_name = p_from_asl_name
	   AND awrv.to_asl_name = to_asl_name
	   AND awrv.scl_name = wf_enabled_rec.scl_name;

  transition_exists_rec transition_exists_cursor%ROWTYPE;

  v_user user_accounts_view.ua_name%TYPE := g_effective_user;

  CURSOR has_right_cursor IS
    SELECT 'Yes'
      FROM AC_WF_BUSINESS_ROLES_VIEW awbrv
     WHERE awbrv.awr_idseq = transition_exists_rec.awr_idseq
       AND EXISTS (SELECT 1
	                 FROM BUSINESS_ROLES_LOV_VIEW brlv
			   	    WHERE brlv.brl_name = awbrv.brl_name
					  AND EXISTS (SELECT 1
								    FROM UA_BUSINESS_ROLES_VIEW ubrv
							       WHERE ubrv.BRL_NAME = brlv.BRL_NAME
									 AND ubrv.ACTL_NAME = p_actl_name
									 AND EXISTS (SELECT 1
 								                   FROM SC_USER_ACCOUNTS_VIEW suav
												  WHERE suav.scua_idseq = ubrv.scua_idseq
													AND suav.ua_name = p_ua_name
													AND suav.scl_name = wf_enabled_rec.scl_name
											    )
								 )
				  );

    CURSOR has_group_right_cursor IS
    SELECT 'Yes'
      FROM AC_WF_BUSINESS_ROLES_VIEW awbrv
     WHERE awbrv.awr_idseq = transition_exists_rec.awr_idseq
       AND EXISTS (SELECT 1
	                 FROM BUSINESS_ROLES_LOV_VIEW brlv
			   	    WHERE brlv.brl_name = awbrv.brl_name
					  AND EXISTS (SELECT 1
								    FROM GRP_BUSINESS_ROLES_VIEW gbrv
							       WHERE gbrv.BRL_NAME = brlv.BRL_NAME
									 AND gbrv.ACTL_NAME = p_actl_name
									 AND EXISTS (SELECT 1
 								                   FROM SC_GROUPS_VIEW suav
												  WHERE suav.scg_idseq = gbrv.scg_idseq
													AND suav.scl_name = wf_enabled_rec.scl_name
													AND EXISTS (SELECT 1 FROM USER_GROUPS_VIEW ugv
													             WHERE ugv.ua_name = v_user
																   AND ugv.grp_name = suav.grp_name)
											    )
								 )
				  );

  has_rights_rec has_right_cursor%ROWTYPE;
  has_group_rights_rec has_group_right_cursor%ROWTYPE;

BEGIN
  -- The first check is to verify that some form of workflow exists in the
  -- current context.  If not workflow transition rule exists, then WF is
  -- disabled.  When disabled, the user can select any value from the
  -- AC_STATUS_LOV and the transition will be valid.

  OPEN wf_enabled_cursor;
  FETCH wf_enabled_cursor INTO wf_enabled_rec;

  IF wf_enabled_cursor%NOTFOUND THEN
      CLOSE wf_enabled_cursor;

	  RETURN 'Yes';
  END IF;

  CLOSE wf_enabled_cursor;

  -- Since workflow is enabled, make sure the FROM/TO pair exists
  -- in the transistions for this security context.  No user
  -- (regardless of admin status) has the right to create an
  -- invalid transition.

  OPEN transition_exists_cursor;
  FETCH transition_exists_cursor INTO transition_exists_rec;

  IF transition_exists_cursor%NOTFOUND THEN
      CLOSE transition_exists_cursor;

      Cg$errors.push('Invalid workflow transition specified.');
	  Cg$errors.raise_failure;
  END IF;

  CLOSE transition_exists_cursor;

  -- Now that we know the transistion is ok, make sure the
  -- user has the right to make the transition.

  IF g_security_enabled = 'No' OR g_der_admin = 'Yes' THEN
    RETURN 'Yes';
  END IF;

  IF g_delegated_auth <> 'Yes' THEN
	v_user := p_ua_name;
  END IF;

  IF Is_Context_Admin(v_user, wf_enabled_rec.scl_name) = 'Yes' THEN
      RETURN 'Yes';
  END IF;

  -- The user is not an admisitrator, make sure the have the business
  -- role for the component type to make the transition.

  OPEN has_right_cursor;
  FETCH has_right_cursor INTO has_rights_rec;

  IF has_right_cursor%NOTFOUND THEN
      CLOSE has_right_cursor;
	  OPEN has_group_right_cursor;
	  FETCH has_group_right_cursor INTO has_group_rights_rec;
	  IF has_group_right_cursor%NOTFOUND THEN
	     CLOSE has_group_right_cursor;
		 RETURN 'No';
	  ELSE
	     CLOSE has_group_right_cursor;
		 RETURN 'Yes';
	  END IF;
  ELSE
     CLOSE has_right_cursor;
	 RETURN 'Yes';
  END IF;

END;

FUNCTION has_admin_privilege (  account_name user_accounts_view.ua_name%TYPE  ,
                                  comp_typ     ac_types_lov_view.actl_name%TYPE ,
                                  ctx_id       CONTEXTS.conte_idseq%TYPE   )
         RETURN VARCHAR2
IS
    ret_val VARCHAR2(5) := 'FALSE';
  BEGIN
    ret_val := 'Yes';
    RETURN ret_val;

END has_admin_privilege;

FUNCTION Delegated_Auth_Enabled RETURN VARCHAR2 IS
BEGIN
   RETURN g_delegated_auth;
END;

Procedure DERWhoAmI IS
BEGIN
   htp.htmlopen;
   htp.bodyopen;

   htp.tableopen;

   htp.tablerowopen;
   htp.tabledata(htf.bold('Delegated Authority Enabled:'));
   htp.tabledata(g_delegated_auth);
   htp.tablerowclose;

   htp.tablerowopen;
   htp.tabledata(htf.bold('Effective User:'));
   htp.tabledata(g_effective_user);
   htp.tablerowclose;

   htp.tablerowopen;
   htp.tabledata(htf.bold('Security Enabled:'));
   htp.tabledata(g_security_enabled);
   htp.tablerowclose;

   htp.tablerowopen;
   htp.tabledata(htf.bold('DER Administrator:'));
   htp.tabledata(g_der_admin);
   htp.tablerowclose;

   htp.tableclose;

   htp.bodyclose;
   htp.htmlclose;
END;

BEGIN
	-- Check to see if security is enabled in the system.  If
	-- security isn't enabled, every user can do any action.

	BEGIN
        OPEN security_enabled_cursor;
        FETCH security_enabled_cursor INTO security_enabled_rec;

        IF security_enabled_cursor%NOTFOUND THEN
	        g_security_enabled := 'Yes';
        ELSE
            g_security_enabled := security_enabled_rec.status_code;
    	END IF;

        CLOSE security_enabled_cursor;
	EXCEPTION
	  WHEN OTHERS THEN
	      g_security_enabled := 'Yes';
	END;

	IF g_security_enabled = 'Yes' THEN
        -- First determine if the system has been configured to
    	-- delegate authorization to the portal.

        OPEN delegated_auth_cursor;
    	FETCH delegated_auth_cursor INTO delegated_auth_rec;

	    IF delegated_auth_cursor%NOTFOUND THEN
	        g_delegated_auth := 'No';
    	ELSE
	        IF UPPER(delegated_auth_rec.status_code) IN ('ON', 'YES') THEN
		        g_delegated_auth := 'Yes';
    		ELSE
	    	    g_delegated_auth := 'No';
    		END IF;
    	END IF;

    	CLOSE delegated_auth_cursor;

	    -- If authorization has been delegated, then set the effective user
    	-- to the current portal user name.  If for any reason, the WWCTX_API
		-- package doesn't return a value, become the current user.

     	IF g_delegated_auth = 'Yes' THEN
           BEGIN
             v_dbms_cursorId := dbms_sql.open_cursor;

             dbms_sql.parse(v_dbms_cursorId,
			                'SELECT WWCTX_API.GET_USER FROM DUAL',
							dbms_sql.native);

             dbms_sql.define_column(v_dbms_cursorId, 1, g_effective_user, 30);

             v_dbms_exeId := dbms_sql.EXECUTE(v_dbms_cursorId);
             v_dbms_fetchCnt := dbms_sql.fetch_rows(v_dbms_cursorId);
		   EXCEPTION
               WHEN NO_SESSION_EXCEPTION THEN
			   v_dbms_fetchcnt := 0;
--			   g_effective_user := '*NO SESSION*';
               WHEN GENERAL_CONTEXT_EXCEPTION THEN
			   v_dbms_fetchcnt := 0;
--			   g_effective_user := '*GENERAL CONTEXT FAIL*';
			   WHEN OTHERS THEN
			   v_dbms_fetchcnt := 0;
--			   g_effective_user := 'FAIL';
		   END;

		   IF v_dbms_fetchCnt <> 0 THEN
             dbms_sql.column_value(v_dbms_cursorId, 1, g_effective_user);
		   ELSIF g_effective_user IS NULL THEN
			 g_effective_user := USER;
		   END IF;

		   g_effective_user := NVL(g_effective_user, USER);

		   IF dbms_sql.is_open(v_dbms_cursorId) THEN
		       dbms_sql.close_cursor(v_dbms_cursorId);
		   END IF;
        END IF;

    	IF g_security_enabled = 'Yes' THEN
	        OPEN is_admin_cursor(g_effective_user);
	        FETCH is_admin_cursor INTO is_admin_rec;

    	    IF is_admin_cursor%NOTFOUND THEN
                g_der_admin := 'No';
    	    ELSE
	            g_der_admin := is_admin_rec.DER_ADMIN_IND;
    	    END IF;

        	CLOSE is_admin_cursor;
	    ELSE
		    -- When there is no security, all users are DER administrators.

    	    g_der_admin := 'Yes';
        END IF;
	    END IF;



END Admin_Security_Util;