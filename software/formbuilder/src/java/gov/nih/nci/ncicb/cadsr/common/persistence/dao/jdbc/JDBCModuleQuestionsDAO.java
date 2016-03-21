package gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.springframework.jdbc.object.MappingSqlQuery;

import gov.nih.nci.ncicb.cadsr.common.dto.DataElementTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.QuestionTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.ValueDomainV2TransferObject;
import gov.nih.nci.ncicb.cadsr.common.resource.Question;

public class JDBCModuleQuestionsDAO 
{
	public JDBCModuleQuestionsDAO() {
		super();
		// TODO Auto-generated constructor stub
	}

	class QuestionsInAModuleQuery extends MappingSqlQuery
	{

		public void _setSql(String idSeq)
		{
			super.setSql((new StringBuilder()).append("SELECT a.*, b.EDITABLE_IND, b.QC_ID, c.RULE, d.PREFERRED_NAME as DE_SHORT_NAME, d.PREFERRED_DEFINITION as DE_PREFERRED_DEFINITION, "
					+ "d.CREATED_BY as DE_CREATED_BY, b.DATE_CREATED as QUESTION_DATE_CREATED, b.DATE_MODIFIED as QUESTION_DATE_MODIFIED, e.NAME as DE_CONTEXT_NAME " 
					+ "FROM SBREXT.FB_QUESTIONS_VIEW a, CABIO31_QUESTIONS_VIEW b, COMPLEX_DATA_ELEMENTS_VIEW c, SBR.DATA_ELEMENTS_VIEW d, SBR.CONTEXTS_VIEW e  where a.MODULE_IDSEQ = '")
					.append(idSeq).append("' and a.ques_idseq=b.QC_IDSEQ and b.DE_IDSEQ = c.P_DE_IDSEQ(+) and b.de_idseq = d.de_idseq(+) and d.CONTE_IDSEQ = e.CONTE_IDSEQ(+)").toString());
		}

		protected Object mapRow(ResultSet rs, int rownum)
				throws SQLException
		{
			Question question = new QuestionTransferObject();
			question.setQuesIdseq(rs.getString("QUES_IDSEQ"));
			question.setLongName(rs.getString("LONG_NAME"));
			question.setDisplayOrder(rs.getInt("DISPLAY_ORDER"));
			question.setAslName(rs.getString("WORKFLOW"));
			question.setPreferredDefinition(rs.getString("DEFINITION"));
			question.setMandatory("Yes".equalsIgnoreCase(rs.getString("MANDATORY_IND")));
			question.setPublicId(rs.getInt("QC_ID"));
			question.setVersion(new Float(rs.getFloat("VERSION")));
			question.setDateCreated(rs.getTimestamp("QUESTION_DATE_CREATED"));
			question.setDateModified(rs.getTimestamp("QUESTION_DATE_MODIFIED"));
			String editableInd = rs.getString("EDITABLE_IND");
			boolean editable = editableInd == null || editableInd.trim().equals("") || editableInd.equalsIgnoreCase("Yes");
			question.setEditable(editable);
			String derivRule = rs.getString("RULE");
			if(derivRule != null && !derivRule.trim().equals(""))
				question.setDeDerived(true);
			else
				question.setDeDerived(false);
			String deIdSeq = rs.getString("DE_IDSEQ");
			if(deIdSeq != null)
			{
				DataElementTransferObject dataElementTransferObject = new DataElementTransferObject();
				dataElementTransferObject.setDeIdseq(deIdSeq);
				dataElementTransferObject.setLongCDEName(rs.getString(15));
				dataElementTransferObject.setVersion(new Float(rs.getFloat(16)));
				dataElementTransferObject.setLongName(rs.getString(17));
				dataElementTransferObject.setCDEId(Integer.toString(rs.getInt(18)));
				dataElementTransferObject.setAslName(rs.getString("DE_WORKFLOW"));
				dataElementTransferObject.setPreferredName(rs.getString("DE_SHORT_NAME"));
				dataElementTransferObject.setPreferredDefinition(rs.getString("DE_PREFERRED_DEFINITION"));
				dataElementTransferObject.setCreatedBy(rs.getString("DE_CREATED_BY"));
				dataElementTransferObject.setContextName(rs.getString("DE_CONTEXT_NAME"));
				question.setDataElement(dataElementTransferObject);
				ValueDomainV2TransferObject valueDomainV2TransferObject = new ValueDomainV2TransferObject();
				valueDomainV2TransferObject.setVdIdseq(rs.getString(19));
				dataElementTransferObject.setValueDomain(valueDomainV2TransferObject);
			}
			return question;
		}

		QuestionsInAModuleQuery()
		{
			super();
		}
	}
	
	public Collection getQuestionsInAModuleV2(String moduleId)
    {
        QuestionsInAModuleQuery query = new QuestionsInAModuleQuery();
        
        try {
			javax.naming.Context ic = new InitialContext();
			DataSource ds = (DataSource) ic.lookup("java:comp/env/jdbc/FormBuilderDS");
			query.setDataSource(ds);
		} catch (NamingException e) {
			System.out.println("Error in fetching DataSource: ");
			e.printStackTrace();
		}
        
        query._setSql(moduleId);
        return query.execute();
    }
}
