package gov.nih.nci.cadsr.formloader.repository;

import gov.nih.nci.cadsr.formloader.domain.FormDescriptor;
import gov.nih.nci.cadsr.formloader.service.common.StaXParser;
import gov.nih.nci.ncicb.cadsr.common.dto.ContextTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.DataElementTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.DesignationTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.FormTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.ModuleTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.PermissibleValueV2TransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.QuestionTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.ReferenceDocumentTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.InstructionTransferObject;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc.JDBCFormDAOV2;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc.JDBCFormInstructionDAOV2;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc.JDBCModuleDAOV2;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc.JDBCQuestionDAOV2;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc.JDBCValueDomainDAOV2;
import gov.nih.nci.ncicb.cadsr.common.resource.Context;
import gov.nih.nci.ncicb.cadsr.common.resource.FormV2;
import gov.nih.nci.ncicb.cadsr.common.resource.Instruction;
import gov.nih.nci.ncicb.cadsr.common.resource.PermissibleValueV2;
import gov.nih.nci.ncicb.cadsr.common.resource.ValueDomainV2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class FormLoaderRepositoryImpl implements FormLoaderRepository {
	private static Logger logger = Logger.getLogger(FormLoaderRepositoryImpl.class.getName());
	
	JDBCFormDAOV2 formV2Dao;
	JDBCModuleDAOV2 moduleV2Dao;
	JDBCQuestionDAOV2 questionV2Dao;
	JDBCValueDomainDAOV2 valueDomainV2Dao;
	JDBCFormInstructionDAOV2 formInstructionV2Dao;
	
	HashMap<String, String> conteNameSeqIdMap;

	/**
	 * Gets Seq id, public id and version for forms with the given public ids
	 * @param pubicIDList
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<FormV2> getFormsForPublicIDs(List<String> pubicIDList) {
		if (pubicIDList == null || pubicIDList.size() ==0) {
			logger.debug("getFormsForPublicIDs(): public id list is null or empty. Do nothing.");
			return null;
		}
		
		//Seq id, public id and version are set in the returned forms
		List<FormV2> formDtos = formV2Dao.getExistingVersionsForPublicIds(pubicIDList);
		logger.debug("getFormsForPublicIDs() returns " + formDtos.size() + " forms with " + pubicIDList.size() 
				+ " public ids");
		
		return formDtos;
	}
	
	@Transactional(readOnly=true)
	public List<QuestionTransferObject> getQuestionsByPublicId(String publicId) {
		if (publicId == null || publicId.length() == 0) {
			logger.debug("getQuestionsByPublicId(): Question public id is null or empty. Unable to querry db.");
			return null;
		}
		
		int pubId = Integer.parseInt(publicId);
		
		List<QuestionTransferObject> questions = questionV2Dao.getQuestionsByPublicId(pubId);
		logger.debug("getQuestionsByPublicId(): Dao returns " + questions.size() + " questions.");
		return questions;
	}
	
	@Transactional(readOnly=true)
	public List<QuestionTransferObject> getQuestionsByPublicIds(List<String> publicIds) {
		if (publicIds == null || publicIds.size() == 0) {
			logger.debug("getQuestionsByPublicIds(): Question public id list is null or empty. Unable to querry db.");
			return null;
		}
			
		List<QuestionTransferObject> questions = questionV2Dao.getQuestionsByPublicIds(publicIds);
		logger.debug("getQuestionsByPublicId(): Dao returns " + questions.size() + " questions.");
		return questions;
	}
	
	@Transactional(readOnly=true)
	public List<DataElementTransferObject> getCDEByPublicId(String cdePublicId) {
		if (cdePublicId == null || cdePublicId.length() == 0) {
			logger.debug("getCDEByPublicId(): Question's CDE public id is null or empty. Unable to querry db.");
			return null;
		}
		
		List<DataElementTransferObject> des = questionV2Dao.getCdesByPublicId(cdePublicId);
		logger.debug("getCDEByPublicId(): Dao returns " + des.size() + " CDEs");
		
		return des;
	}
	
	@Transactional(readOnly=true)
	public List<DataElementTransferObject> getCDEsByPublicIds(List<String> cdePublicIds) {
		if (cdePublicIds == null || cdePublicIds.size() == 0) {
			logger.debug("getCDEsByPublicIds(): cde public id list is null or empty. Unable to querry db.");
			return null;
		}
		List<DataElementTransferObject> des = questionV2Dao.getCdesByPublicIds(cdePublicIds);
		logger.debug("getCDEsByPublicIds(): Dao returns " + des.size() + " CDEs");
		
		return des;
	}
	
	@Transactional(readOnly=true)
	public List<ReferenceDocumentTransferObject> getReferenceDocsForQuestionCde(String cdePublicId, String cdeVersion) {
		if (cdePublicId == null || cdePublicId.length() == 0) {
			logger.debug("getReferenceDocsForQuestionCde(): Question's CDE public id is null or empty. Unable to querry db.");
			return null;
		}
		
		if (cdeVersion == null || cdeVersion.length() == 0) {
			logger.debug("getReferenceDocsForQuestionCde(): Question CDE version is null or empty. Unable to querry db.");
			return null;
		}
		
		List<ReferenceDocumentTransferObject> deRefDocs = 
				questionV2Dao.getAllReferenceDocuments(
						Integer.parseInt(cdePublicId), Float.parseFloat(cdeVersion));
		logger.debug("getReferenceDocsForQuestionCde(): Dao returns " + deRefDocs.size() + " CDE reference docs.");
		
		return deRefDocs;
	}
	
	@Transactional(readOnly=true)
	public HashMap<String, List<ReferenceDocumentTransferObject>> getReferenceDocsByCdePublicIds(List<String> cdePublicIds) {
		if (cdePublicIds == null || cdePublicIds.size() == 0) {
			logger.debug("getReferenceDocsByCdePublicIds(): cde public id list is null or empty. Unable to querry db.");
			return null;
		}
		
		HashMap<String, List<ReferenceDocumentTransferObject>> deRefDocs = 
				questionV2Dao.getReferenceDocumentsByCdePublicIds(cdePublicIds);
				
				
		logger.debug("getReferenceDocsByCdePublicIds(): Dao returns " + deRefDocs.size() + " CDE reference docs.");
		
		return deRefDocs;
	}
	
	@Transactional(readOnly=true)
	public List<PermissibleValueV2TransferObject> getValueDomainPermissibleValuesByVdId(String vdSeqId) {
		if (vdSeqId == null || vdSeqId.length() == 0) {
			logger.debug("getValueDomainBySeqId(): Value domain seq id is null or empty. Unable to querry db.");
			return null;
		}
		
		List<PermissibleValueV2TransferObject> pValues = valueDomainV2Dao.getPermissibleValuesByVdId(vdSeqId);
		String msg = "getValueDomainPermissibleValuesByVdId(): Dao returns ";
		if (pValues == null || pValues.size() == 0)
			msg += "a value domain obj with 0 permissible value.";
		else
			msg += "a value domain obj with " + pValues.size() + " permissible values";
		
		logger.debug("msg");
		return pValues;
	}
	
	@Transactional(readOnly=true)
	public HashMap<String, List<PermissibleValueV2TransferObject>> getPermissibleValuesByVdIds(List<String> vdSeqIds) {
		if (vdSeqIds == null || vdSeqIds.size() == 0) {
			logger.debug("getPermissibleValuesByVdIds(): vdSeqIds list is null or empty. Unable to querry db.");
			return null;
		}
		
		HashMap<String, List<PermissibleValueV2TransferObject>> pValues = 
				valueDomainV2Dao.getPermissibleValuesByVdIds(vdSeqIds);
		
		String msg = "getPermissibleValuesByVdIds(): Dao returns ";
		if (pValues == null || pValues.size() == 0)
			msg += "a value domain obj with 0 permissible value.";
		else
			msg += "a value domain obj with " + pValues.size() + " permissible values";
		
		logger.debug("msg");
		return pValues;
	}

	public JDBCFormDAOV2 getFormV2Dao() {
		return formV2Dao;
	}

	public void setFormV2Dao(JDBCFormDAOV2 formV2Dao) {
		this.formV2Dao = formV2Dao;
	}

	public JDBCModuleDAOV2 getModuleV2Dao() {
		return moduleV2Dao;
	}

	public void setModuleV2Dao(JDBCModuleDAOV2 moduleV2Dao) {
		this.moduleV2Dao = moduleV2Dao;
	}

	public JDBCQuestionDAOV2 getQuestionV2Dao() {
		return questionV2Dao;
	}

	public void setQuestionV2Dao(JDBCQuestionDAOV2 questionV2Dao) {
		this.questionV2Dao = questionV2Dao;
	}

	public JDBCValueDomainDAOV2 getValueDomainV2Dao() {
		return valueDomainV2Dao;
	}

	public void setValueDomainV2Dao(JDBCValueDomainDAOV2 valueDomainV2Dao) {
		this.valueDomainV2Dao = valueDomainV2Dao;
	}

	@Transactional(readOnly=true)
	public String getContextSeqId(String contextName) {
		if (conteNameSeqIdMap == null)
			conteNameSeqIdMap = this.formV2Dao.getAllContextSeqIds();
		
		return conteNameSeqIdMap.get(contextName);
	}
	
	@Transactional
	public String createNewForm(FormDescriptor form, String formloaderUser, String xmlPathName, int formIdx) {
		FormTransferObject formdto = translateIntoFormDTO(form);
		
		formdto.setVersion(Float.valueOf("1.0"));
		formdto.setAslName("DRAFT NEW");
		formdto.setCreatedBy(form.getCreatedBy());  //assuming we use same user name as the person logged in to load collection
		String contextName = form.getContext();
		if (contextName != null && contextName.length() > 0) {
			Context context = new ContextTransferObject();
		    context.setConteIdseq(this.getContextSeqId(contextName));
		    formdto.setContext(context);
			formdto.setConteIdseq(this.getContextSeqId(contextName));
		}
		
		formdto.setLongName(form.getLongName());
		formdto.setFormType(form.getType());
		formdto.setFormCategory(form.getCategoryName());
		
		//TODO: when loading, first check user from xml form. If that's invalid,
		//check with the form loader user name. If neither works, inform user
		// - confirmed with Denise on 8/7/2013
		String formSeqid = formV2Dao.createFormComponent(formdto);
		
		formdto.setFormIdseq(formSeqid);
		form.setFormSeqId(formSeqid);
		
		//instructions
		createFormInstructions(form, formdto);
		
		//designations
		List<String> cdeSeqIds = getCdeSeqIdsFromForm(form);
		
		processFormdetails(form, xmlPathName, formIdx);
		
		return formSeqid;
	}
	
	protected FormTransferObject translateIntoFormDTO(FormDescriptor form) {
		FormTransferObject formDto = new FormTransferObject();
		
		
		
		return formDto;
	}
	
	protected void createFormInstructions(FormDescriptor form, FormTransferObject formdto) {
		String instructString = form.getHeaderInstruction();
		
		InstructionTransferObject formInstruction = createInstruction(form, formdto, instructString);
		if (formInstruction != null)
			formInstructionV2Dao.createInstruction(formInstruction, formdto.getFormIdseq());
		
		instructString = form.getFooterInstruction();
		formInstruction = createInstruction(form, formdto, instructString);
		if (formInstruction != null)
			formInstructionV2Dao.createFooterInstruction(formInstruction, formdto.getFormIdseq());
	}
	
	protected InstructionTransferObject createInstruction(FormDescriptor form, 
			FormTransferObject formdto, String instructionString) {
			
		InstructionTransferObject formInstruction = null;
		if (instructionString != null && instructionString.length() > 0) {
			formInstruction = new InstructionTransferObject();
			formInstruction.setLongName(form.getLongName());
			formInstruction.setPreferredDefinition(instructionString);
			formInstruction.setContext(formdto.getContext());
			formInstruction.setAslName("DRAFT NEW");
			formInstruction.setVersion(new Float(1.0));
			formInstruction.setCreatedBy(form.getCreatedBy());
			formInstruction.setDisplayOrder(1);
		}
		
		return formInstruction;
	}
	
	protected List<String> getCdeSeqIdsFromForm(FormDescriptor form) {
		return null;
	}
	
	protected void processFormdetails(FormDescriptor form, String xmlPathName, int currFormIdx) {
		StaXParser parser = new StaXParser();
		parser.parseFormDetails(xmlPathName, form, currFormIdx);
		
		String formSeqid = form.getFormSeqId();
		
		List<String> protoIds = parser.getProtocolIds();
		processProtocols(form, protoIds);
		
		List<DesignationTransferObject> designations = parser.getDesignations();
		processDesignations(form, designations);
		
		//TODO
		//processRefdocs();
		//processContactCommunications()
		//processDefinitions()
	}
	
	protected void processDesignations(FormDescriptor form, List<DesignationTransferObject> designations) {
		String formSeqid = form.getFormSeqId();
		String contextSeqId = this.getContextSeqId(form.getContext());
		
		for (DesignationTransferObject desig : designations) {
			if (form.getLoadType().equals(FormDescriptor.LOAD_TYPE_UPDATE_FORM)) {
				List<DesignationTransferObject> existing = formV2Dao.getDesignationsForForm(
						formSeqid, desig.getName(), desig.getType(), desig.getLanguage());
				if (existing == null || existing.size() == 0) {
					designateForm(formSeqid, contextSeqId, form.getCreatedBy(), desig);
				}
			} else {
				designateForm(formSeqid, contextSeqId, form.getCreatedBy(), desig);
			}
		}
	}
	
	protected int designateForm(String formSeqid, String contextSeqid, String createdby,
			DesignationTransferObject desig) {
		List<String> ac_seqids = new ArrayList<String>();
		ac_seqids.add(formSeqid);
		return formV2Dao.designate(contextSeqid, ac_seqids, createdby);
	}
	
	protected void processProtocols(FormDescriptor form, List<String> protoIds) {
		String formSeqid = form.getFormSeqId();
		if (protoIds != null && protoIds.size() > 0) {
			HashMap<String, String> protoIdMap = formV2Dao.getProtocolSeqidsByIds(protoIds);
			List<String> protoSeqIds = markNoMatchProtoIds(form, protoIds, protoIdMap);

			for (String protoSeqid : protoSeqIds) {
				if (FormDescriptor.LOAD_TYPE_UPDATE_FORM.equals(form.getLoadType())) {
					if (!formV2Dao.formProtocolExists(formSeqid, protoSeqid));
						formV2Dao.addFormProtocol(formSeqid, protoSeqid, form.getCreatedBy());

				} else {
					formV2Dao.addFormProtocol(formSeqid, protoSeqid, form.getCreatedBy());
				}
			}
		}
	}
	
	protected List<String> markNoMatchProtoIds(FormDescriptor form, List<String> protoIds, HashMap<String, String> protoIdMap) {
		List<String> protoSeqIds = new ArrayList<String>();
		String nomatchids = "";
		for (String protoId : protoIds) {
			String seqid = protoIdMap.get(protoId);
			if (seqid == null)
				nomatchids = (nomatchids.length() > 0) ? "," + protoId : protoId;
			else
				protoSeqIds.add(seqid);
			
		}
		
		if (nomatchids.length() > 0)
			form.addMessage("Protocol ids [" + nomatchids + "] with the form has no match in database.");
		
		return protoSeqIds;
	}
}
