package gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc;
        
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gov.nih.nci.cadsr.formloader.domain.FormCollection;
import gov.nih.nci.cadsr.formloader.domain.FormDescriptor;
import gov.nih.nci.ncicb.cadsr.common.dto.DataElementTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.FormV2TransferObject;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc.util.DataSourceUtil;
import gov.nih.nci.ncicb.cadsr.common.resource.FormV2;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
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
	
	public int createCollectionFormMappingRecord(String collectionseqid, String formseqid, 
			int formpublicid, float formversion, String loadType) {
		String sql = "INSERT into FORMS_IN_COLLECTION (FORM_COLLECTION_IDSEQ, FORM_IDSEQ, PUBLIC_ID, VERSION, LOAD_TYPE) " +
				" VALUES (:collectionseqid, :formseqid, :formpublicid, :formversion, :loadtype)";
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("collectionseqid", collectionseqid);
		params.addValue("formseqid", formseqid);
		params.addValue("formpublicid", formpublicid);
		params.addValue("formversion", formversion);
		params.addValue("loadtype", loadType);
		
		int res = this.namedParameterJdbcTemplate.update(sql, params);
		return res;
		
	}
	
	 @Override
	public List<FormCollection> getAllLoadedCollections() {
		String sql = 
		      "select * from form_collections order by date_created desc";

		List<FormCollection> colls = new ArrayList<FormCollection>();
		
		List rows = this.namedParameterJdbcTemplate.getJdbcOperations().queryForList(sql);  
		for (Object row : rows) {
			FormCollection aColl = new FormCollection();
			aColl.setId( (String) ((Map)row).get("FORM_COLLECTION_IDSEQ"));
			aColl.setName((String) ((Map)row).get("NAME"));
			aColl.setDescription( (String) ((Map)row).get("DESCRIPTION"));
			aColl.setCreatedBy( (String) ((Map)row).get("CREATED_BY"));
			aColl.setDateCreated((Date) ((Map)row).get("DATE_CREATED"));
			aColl.setXmlFileName((String) ((Map)row).get("XML_FILE_NAME"));
			aColl.setXmlPathOnServer((String) ((Map)row).get("XML_FILE_PATH"));
			
			colls.add(aColl);
		}
		        
		return colls;      
	}
	 
	 public List<String> getAllFormSeqidsForCollection(String collseqid) {
		 String sql = 
				 "select FORM_IDSEQ from forms_in_collection " +
						 " where form_collection_idseq=:collseqid";

		 MapSqlParameterSource params = new MapSqlParameterSource();
		 params.addValue("collseqid", collseqid);
		 
		 List<String> seqid = 
				 this.namedParameterJdbcTemplate.query(sql, params, 
						 new RowMapper<String>() {
					 public String mapRow(ResultSet rs, int rowNum) throws SQLException {
						 return rs.getString("FORM_IDSEQ");

					 }
				 });

		 return seqid;

	 }
	 
	 //public List<FormDescriptor> getForm

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
