package gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc;

import gov.nih.nci.cadsr.formloader.service.impl.ContentValidationServiceImpl;
import gov.nih.nci.ncicb.cadsr.common.dto.ContextTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.FormV2TransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.ModuleTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.ProtocolTransferObject;
import gov.nih.nci.ncicb.cadsr.common.exception.DMLException;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.FormV2DAO;
import gov.nih.nci.ncicb.cadsr.common.resource.Context;
import gov.nih.nci.ncicb.cadsr.common.resource.Form;
import gov.nih.nci.ncicb.cadsr.common.resource.FormV2;
import gov.nih.nci.ncicb.cadsr.common.resource.Module;
import gov.nih.nci.ncicb.cadsr.common.resource.Protocol;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.object.MappingSqlQuery;

//Modification off JDBCFormV2Dao
public class JDBCFormDAOV2 extends JDBCAdminComponentDAOV2 implements FormV2DAO {
	
	private static Logger logger = Logger.getLogger(JDBCFormDAOV2.class.getName());
	
	public JDBCFormDAOV2(DataSource dataSource) {
		super(dataSource);
	}
    

  public FormV2 findFormV2ByPrimaryKey(String formId) throws DMLException {
    FormV2 myForm = null;
    FormV2ByPrimaryKey query = new FormV2ByPrimaryKey();
    query.setDataSource(this.dataSource);
    query.setSql();

    List result = (List) query.execute(formId);

    if (result == null || result.isEmpty()){
        DMLException dmlExp = new DMLException("No matching record found.");
              dmlExp.setErrorCode(NO_MATCH_FOUND);
          throw dmlExp;
    }

    myForm = (FormV2) (query.execute(formId).get(0));

    FormV2 myFormExtensions = null;
    FormV2ExtensionsByPrimaryKey queryExtensions = new FormV2ExtensionsByPrimaryKey();
    queryExtensions.setDataSource(this.dataSource);
    queryExtensions.setSql();
    List resultExtensions = (List) queryExtensions.execute(formId);

    if (resultExtensions == null || resultExtensions.isEmpty()){
        DMLException dmlExp = new DMLException("No matching record found.");
              dmlExp.setErrorCode(NO_MATCH_FOUND);
          throw dmlExp;
    }

    myFormExtensions = (FormV2) (queryExtensions.execute(formId).get(0));
    myForm.setModifiedBy(myFormExtensions.getModifiedBy());
    myForm.setRegistrationStatus(myFormExtensions.getRegistrationStatus());


    //get protocols
    myForm.setProtocols(getProtocols(myForm));
    return myForm;
  }


    private List getProtocols(Form form){
        FormProtocolByFormPrimaryKey query = new FormProtocolByFormPrimaryKey();
        query.setDataSource(this.dataSource);
        query.setSql();
        List protocols = query.execute(form.getFormIdseq());
        return protocols;
    }
    
  /**
   * Inner class that accesses database to get a form using the form idseq
   */
  class FormV2ByPrimaryKey extends MappingSqlQuery {
    FormV2ByPrimaryKey() {
      super();
    }

    public void setSql() {
      super.setSql("SELECT * FROM FB_FORMS_VIEW where QC_IDSEQ = ? ");
      declareParameter(new SqlParameter("QC_IDSEQ", Types.VARCHAR));
    }

    protected Object mapRow( ResultSet rs, int rownum) throws SQLException {
      FormV2 form = new FormV2TransferObject();
      form.setFormIdseq(rs.getString(1)); // QC_IDSEQ
      form.setIdseq(rs.getString(1));
      form.setLongName(rs.getString(9)); //LONG_NAME
      form.setPreferredName(rs.getString(7)); // PREFERRED_NAME

      //setContext(new ContextTransferObject(rs.getString("context_name")));
      ContextTransferObject contextTransferObject = new ContextTransferObject();
      contextTransferObject.setConteIdseq(rs.getString(4)); //CONTE_IDSEQ
      contextTransferObject.setName(rs.getString(12)); // CONTEXT_NAME
      form.setContext(contextTransferObject);
      form.setDateCreated(rs.getTimestamp(14));
      form.setDateModified(rs.getTimestamp(15));


      //multiple protocols will be set later

      form.setFormType(rs.getString(3)); // TYPE
      form.setAslName(rs.getString(6)); // WORKFLOW
      form.setVersion(new Float(rs.getString(2))); // VERSION
      form.setPreferredDefinition(rs.getString(8)); // PREFERRED_DEFINITION
      form.setCreatedBy(rs.getString(13)); // CREATED_BY
      form.setFormCategory(rs.getString(5));
      form.setPublicId(rs.getInt(17));
      form.setChangeNote(rs.getString(18));

      return form;
    }
  }

  /**
   * Inner class that accesses database to get a form using the form idseq
   */
  class FormV2ExtensionsByPrimaryKey extends MappingSqlQuery {
    FormV2ExtensionsByPrimaryKey() {
      super();
    }

    public void setSql() {
      super.setSql("SELECT * FROM CABIO31_FORMS_VIEW where QC_IDSEQ = ? ");
      declareParameter(new SqlParameter("QC_IDSEQ", Types.VARCHAR));
    }

    protected Object mapRow( ResultSet rs, int rownum) throws SQLException {
      FormV2 form = new FormV2TransferObject();
      form.setModifiedBy(rs.getString(22));
      form.setRegistrationStatus(rs.getString(23));

      return form;
    }
  }

    /**
     * Inner class that accesses database to get a form's protocols using the form idseq
     */
    class FormProtocolByFormPrimaryKey extends MappingSqlQuery {
      FormProtocolByFormPrimaryKey() {
        super();
      }

      public void setSql() {
        String sql = " SELECT p.proto_idseq, p.version, p.conte_idseq, p.preferred_name, p.preferred_definition, p.asl_name, p.long_name, p.LATEST_VERSION_IND, p.begin_date, p.END_DATE, p.PROTOCOL_ID, p.TYPE, p.PHASE, p.LEAD_ORG, p.origin, p.PROTO_ID, c.name contextname " +
                     " FROM protocol_qc_ext fp, sbrext.protocols_view_ext p , sbr.contexts_view c" +
                     " where QC_IDSEQ = ? and fp.PROTO_IDSEQ = p.PROTO_IDSEQ " +
                     " AND p.conte_idseq = c.conte_idseq";
        super.setSql(sql);
        declareParameter(new SqlParameter("QC_IDSEQ", Types.VARCHAR));
      }

      protected Object mapRow( ResultSet rs, int rownum) throws SQLException {
        Protocol protocol = new ProtocolTransferObject();
        protocol.setProtoIdseq(rs.getString(1));
        protocol.setVersion(rs.getFloat(2));
        protocol.setConteIdseq(rs.getString(3));
        protocol.setPreferredName(rs.getString(4));
        protocol.setPreferredDefinition(rs.getString(5));
        protocol.setAslName(rs.getString(6));
        protocol.setLongName(rs.getString(7));
        protocol.setLatestVersionInd(rs.getString(8));
        protocol.setBeginDate(rs.getDate(9));
        protocol.setEndDate(rs.getDate(10));
        protocol.setProtocolId(rs.getString(11));
        protocol.setType(rs.getString(12));
        protocol.setPhase(rs.getString(13));
        protocol.setLeadOrg(rs.getString(14));
        protocol.setOrigin(rs.getString(15));
        Float publicId = rs.getFloat(16);
        protocol.setPublicId(publicId.intValue());
        String contextName = rs.getString(17);
        Context context = new ContextTransferObject();
        context.setConteIdseq(rs.getString(3));
        context.setName(contextName);
        protocol.setContext(context);

        return protocol;
      }
    }//end of class FormProtocolByFormPrimaryKey
    
    /**
     * (Copy from JDBCFormDAO)
     * 
     * Gets all the modules that belong to the specified form
     *
     * @param formId corresponds to the form idseq
     *
     * @return modules that belong to the specified form
     */
    public List<ModuleTransferObject> getModulesInAForm(String formId) {
      ModulesInAFormQuery_STMT query = new ModulesInAFormQuery_STMT();
      query.setDataSource(this.dataSource);
      query._setSql(formId);

      return query.execute();
     
    }
    /**
     * Inner class that accesses database to get all the modules that belong to
     * the specified form
     */
    class ModulesInAFormQuery_STMT extends MappingSqlQuery {
      ModulesInAFormQuery_STMT() {
        super();
      }

      public void _setSql(String id) {
        super.setSql("SELECT a.*, b.PUBLIC_ID FROM FB_MODULES_VIEW a, AC_PUBLIC_ID_VIEW_EXT b where a.CRF_IDSEQ = '" + id + "' and a.MOD_IDSEQ = b.AC_IDSEQ");
//         declareParameter(new SqlParameter("CRF_IDSEQ", Types.VARCHAR));
      }

     /**
      * 3.0 Refactoring- Removed JDBCTransferObject
      */
      protected Object mapRow(
        ResultSet rs,
        int rownum) throws SQLException {
       Module module = new ModuleTransferObject();
       module.setModuleIdseq(rs.getString(1));  // MOD_IDSEQ
       module.setVersion(rs.getFloat(3)); //version
       module.setConteIdseq(rs.getString(4)); //context idseq
       module.setAslName(rs.getString(5));  //workflow status
       module.setPreferredName(rs.getString(6));
       module.setPreferredDefinition(rs.getString(7));
       module.setLongName(rs.getString(8));     // LONG_NAME
       module.setDisplayOrder(rs.getInt(13));   // DISPLAY_ORDER
       module.setNumberOfRepeats(rs.getInt(14));//repeat_no
       module.setPublicId(rs.getInt(15));
       return module;
      }
    }
    
    
    /**
     * Get versions for public ids. -- Form Loader
     * @param publicIds
     * @return a hashmap containing publicid - seqid + versions matching. Value has this format:
     * 	"form seqid | version1,version2,..." (Not pretty but we'll see...)
     */
    public List<FormV2> getVersionsByPublicIds(List<String> publicIds) {
       
        String sql = 
        	"SELECT FV.QC_IDSEQ, FV.PUBLIC_ID, FV.VERSION FROM FB_FORMS_VIEW fv WHERE FV.PUBLIC_ID in (:ids) " +
        			"ORDER BY FV.PUBLIC_ID, FV.VERSION";
      		//"SELECT FV.QC_IDSEQ, FV.QC_ID, FV.VERSION FROM CABIO31_FORMS_VIEW fv WHERE FV.QC_ID in (:ids) " + 
      		//		"ORDER BY FV.QC_ID, FV.VERSION";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("ids", publicIds);
        
        FormV2 form = new FormV2TransferObject();

        List<FormV2> forms = 
        this.namedParameterJdbcTemplate.query(sql, params, 
        		new RowMapper<FormV2>() {
        	public FormV2 mapRow(ResultSet rs, int rowNum) throws SQLException {
            	FormV2 form = new FormV2TransferObject();
            	form.setIdseq(rs.getString(1));
            	form.setPublicId(rs.getInt(2));
            	form.setVersion(rs.getFloat(3));
            	return form;
            }
        });
      
        
        return forms;
    }
    
    public static void main(String[] args) {
    	
    	//DataSource ds = DataSourceUtil.getDriverManagerDS(
    	//		"oracle.jdbc.OracleDriver", 
    	//		"jdbc:oracle:thin:@ncidb-dsr-d:1551:DSRDEV", 
    	//		"FORMBUILDER", "FORMBUILDER");
    	
    	DataSource ds = new DriverManagerDataSource("jdbc:oracle:thin:@ncidb-dsr-d:1551:DSRDEV", 
    			"FORMBUILDER", "FORMBUILDER");
    	
    	JDBCFormDAOV2 form2Dao = new JDBCFormDAOV2(ds);   	
    	FormV2 form = form2Dao.findFormV2ByPrimaryKey("BFC76B3A-AC92-45C7-E040-BB89AD430B2C");
    	String workflow = form.getAslName();
    	System.out.println("Got workflow: " + workflow);
    	//ds.getConnection().close();
    	
  
    }
    
   
}
