package gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc;

import gov.nih.nci.ncicb.cadsr.common.dto.DataElementTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.ModuleTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.FormTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.ProtocolTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.QuestionTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.ValueDomainV2TransferObject;
import gov.nih.nci.ncicb.cadsr.common.exception.DMLException;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.ModuleDAOV2;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.ModuleV2DAO;
import gov.nih.nci.ncicb.cadsr.common.resource.Module;
import gov.nih.nci.ncicb.cadsr.common.resource.Question;
import gov.nih.nci.ncicb.cadsr.common.resource.Form;
import gov.nih.nci.ncicb.cadsr.common.resource.Protocol;
import gov.nih.nci.ncicb.cadsr.common.servicelocator.ServiceLocator;
import gov.nih.nci.ncicb.cadsr.common.servicelocator.SimpleServiceLocator;
import gov.nih.nci.ncicb.cadsr.common.util.StringUtils;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.jdbc.object.SqlUpdate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import javax.sql.DataSource;

//Modification off JDBCModuleV2DAO
public class JDBCModuleDAOV2 extends JDBCAdminComponentDAOV2 implements ModuleDAOV2 {
	
	public JDBCModuleDAOV2(DataSource dataSource) {
		super(dataSource);
	}


  // based on JDBCModuleDAO#getQuestionsInAModule / QuestionsInAModuleQuery_STMT
  
  public List<QuestionTransferObject> getQuestionsInAModuleV2(String moduleId) {
    QuestionsInAModuleQuery_STMT query = new QuestionsInAModuleQuery_STMT();

    query.setDataSource(dataSource);
    query._setSql(moduleId);

    return query.execute();
  }
 

  class QuestionsInAModuleQuery_STMT extends MappingSqlQuery {
    QuestionsInAModuleQuery_STMT() {
      super();
    }

    public void _setSql(String idSeq) {
      super.setSql(
        "SELECT a.*, b.EDITABLE_IND, b.QC_ID, c.RULE, d.PREFERRED_NAME as DE_SHORT_NAME, d.PREFERRED_DEFINITION as DE_PREFERRED_DEFINITION FROM SBREXT.FB_QUESTIONS_VIEW a, CABIO31_QUESTIONS_VIEW b, COMPLEX_DATA_ELEMENTS_VIEW c, SBR.DATA_ELEMENTS_VIEW d where a.MODULE_IDSEQ = '" + idSeq + "' and a.ques_idseq=b.QC_IDSEQ and b.DE_IDSEQ = c.P_DE_IDSEQ(+) and b.de_idseq = d.de_idseq");
//       declareParameter(new SqlParameter("MODULE_IDSEQ", Types.VARCHAR));
    }

    protected Object mapRow(
      ResultSet rs,
      int rownum) throws SQLException {
      Question question = new QuestionTransferObject();
      question.setQuesIdseq(rs.getString("QUES_IDSEQ"));  //QUES_IDSEQ
      question.setLongName(rs.getString("LONG_NAME"));   // LONG_NAME
      question.setDisplayOrder(rs.getInt("DISPLAY_ORDER")); // DISPLAY_ORDER
      question.setAslName(rs.getString("WORKFLOW"));//Workflow
      question.setPreferredDefinition(rs.getString("DEFINITION"));
      question.setMandatory("Yes".equalsIgnoreCase(rs.getString("MANDATORY_IND")));
      question.setPublicId(rs.getInt("QC_ID"));
      question.setVersion(new Float(rs.getFloat("VERSION")));
      
      String editableInd = rs.getString("EDITABLE_IND");
      boolean editable = (editableInd==null||editableInd.trim().equals("")||editableInd.equalsIgnoreCase("Yes"))?true:false;
      question.setEditable(editable);
      
      String derivRule = rs.getString("RULE");
      if (derivRule != null && !derivRule.trim().equals("")) {
    	  question.setDeDerived(true);
      }
      else {
    	  question.setDeDerived(false);
      }
      
      String deIdSeq = rs.getString("DE_IDSEQ");
      if(deIdSeq!=null)
       {
        DataElementTransferObject dataElementTransferObject =
          new DataElementTransferObject();       
        dataElementTransferObject.setDeIdseq(deIdSeq); // DE_IDSEQ
        dataElementTransferObject.setLongCDEName(rs.getString(15)); // DOC_TEXT 
        dataElementTransferObject.setVersion(new Float(rs.getFloat(16))); // VERSION
        dataElementTransferObject.setLongName(rs.getString(17)); // DE_LONG_NAME
        dataElementTransferObject.setCDEId(Integer.toString(rs.getInt(18)));
        dataElementTransferObject.setAslName(rs.getString("DE_WORKFLOW"));
        dataElementTransferObject.setPreferredName(rs.getString("DE_SHORT_NAME"));
        dataElementTransferObject.setPreferredDefinition(rs.getString("DE_PREFERRED_DEFINITION"));
        question.setDataElement(dataElementTransferObject); 
      
      
        ValueDomainV2TransferObject valueDomainV2TransferObject = 
                                         new ValueDomainV2TransferObject();
        valueDomainV2TransferObject.setVdIdseq(rs.getString(19)); // VD_IDSEQ
        dataElementTransferObject.setValueDomain(valueDomainV2TransferObject);
    }
    return question;
   }
  }
  
}

