package gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc;
        
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc.util.DataSourceUtil;
import gov.nih.nci.ncicb.cadsr.common.resource.FormV2;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Repository;

@Repository
public class JDBCCollectionDAO extends JDBCBaseDAOV2 implements CollectionDAO {
	
	
	private static Logger logger = Logger.getLogger(JDBCFormDAOV2.class.getName());
	
	public JDBCCollectionDAO(DataSource dataSource) {
		super(dataSource);
	}
	
	public String createCollectionRecord(String name, String desc, String fileName, String filePath, String createdBy) {
		String sql = "INSERT into sbrext.FORM_COLLECTIONS (form_collection_idseq, description, name, " +
				" xml_file_name, xml_file_path, created_by) " +
				" VALUES (:idseq, :description, :name, :xml_file_name, :xml_file_path,:created_by)";
		
		String idseq = generateGUID();
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("idseq", idseq);
		params.addValue("description", desc);
		params.addValue("name", name);
		params.addValue("xml_file_name", fileName);
		params.addValue("xml_file_path", filePath);
		params.addValue("created_by", createdBy);
		
		int res = this.namedParameterJdbcTemplate.update(sql, params);
		
		return idseq;
	}
	
	public int createCollectionFormMappingRecord(String collectionseqid, String formseqid, int formpublicid, float formversion) {
		String sql = "INSERT into FORMS_IN_COLLECTION (FORM_COLLECTION_IDSEQ, FORM_IDSEQ, PUBLIC_ID, VERSION) " +
				" VALUES (:collectionseqid, :formseqid, :formpublicid, :formversion)";
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("collectionseqid", collectionseqid);
		params.addValue("formseqid", formseqid);
		params.addValue("formpublicid", formpublicid);
		params.addValue("formversion", formversion);
		
		
		int res = this.namedParameterJdbcTemplate.update(sql, params);
		return res;
		
	}
	
	 public static void main(String[] args) {
	    	
	    	//DataSource ds = DataSourceUtil.getDriverManagerDS(
	    	//		"oracle.jdbc.OracleDriver", 
	    	//		"jdbc:oracle:thin:@ncidb-dsr-d:1551:DSRDEV", 
	    	//		"FORMBUILDER", "FORMBUILDER");
	    	
	    	DataSource ds = new DriverManagerDataSource("jdbc:oracle:thin:@ncidb-dsr-d:1551:DSRDEV", 
	    			"sbrext", "jjuser");
	    	
	    	JDBCCollectionDAO collDao = new JDBCCollectionDAO(ds);
	    	String idseq = collDao.createCollectionRecord("collection dao unit test", 
					"UnitTest", "SomeFile", "/opt/content/formloader", "shanyang");
	    	System.out.println("Add a coll record");
	    	
	  
	    }

}
