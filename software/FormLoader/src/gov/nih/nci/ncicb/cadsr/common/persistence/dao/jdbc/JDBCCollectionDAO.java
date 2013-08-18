package gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

public class JDBCCollectionDAO extends JDBCBaseDAOV2 {
	
	
	private static Logger logger = Logger.getLogger(JDBCFormDAOV2.class.getName());
	
	public JDBCCollectionDAO(DataSource dataSource) {
		super(dataSource);
	}
	
	public String createCollectionRecord(String name, String desc, String fileName, String filePath, String createdBy) {
		String sql = "INSERT into FORM_COLLECTIONS (form_collection_idseq, description, name, " +
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

}
