package gov.nih.nci.cadsr.formloader.repository;

import gov.nih.nci.cadsr.formloader.domain.FormCollection;
import gov.nih.nci.cadsr.formloader.domain.FormDescriptor;
import gov.nih.nci.cadsr.formloader.service.ContentValidationService;
import gov.nih.nci.cadsr.formloader.service.XmlValidationService;
import gov.nih.nci.cadsr.formloader.service.common.FormLoaderServiceException;
import gov.nih.nci.cadsr.formloader.service.impl.ContentValidationServiceImpl;
import gov.nih.nci.cadsr.formloader.service.impl.XmlValidationServiceImpl;
import gov.nih.nci.ncicb.cadsr.common.dto.DataElementTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.FormV2TransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.PermissibleValueV2TransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.QuestionTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.ReferenceDocumentTransferObject;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc.JDBCFormDAOV2;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc.JDBCModuleDAOV2;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc.JDBCQuestionDAOV2;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc.JDBCValueDomainDAOV2;
import gov.nih.nci.ncicb.cadsr.common.resource.FormV2;
import gov.nih.nci.ncicb.cadsr.common.resource.PermissibleValueV2;
import gov.nih.nci.ncicb.cadsr.common.resource.ValueDomainV2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class FormLoaderRepositoryDataGen implements FormLoaderRepository {

	@Override
	public boolean isContactCommunicationTypeValid(String contactCommType) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean validClassificationScheme(String publicId, String version) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean validClassificationSchemeItem(String publicId, String version) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean definitionTypeValid(String definitionType) {
		// TODO Auto-generated method stub
		return false;
	}

	String mockRepoPath = ".\\test\\data\\serializedDtos\\";

	JDBCFormDAOV2 formV2Dao;
	JDBCModuleDAOV2 moduleV2Dao;
	JDBCQuestionDAOV2 questionV2Dao;
	JDBCValueDomainDAOV2 valueDomainV2Dao;

	@Override
	public List<FormV2> getFormsForPublicIDs(List<String> pubicIDList) {
		final String fileName = "FormV2List.ser";

		String filePathName = mockRepoPath + fileName;
		File objs = new File(filePathName);

		List<FormV2> formDtos = null;
		try {
			if (!objs.exists()) {
				formDtos = formV2Dao.getExistingVersionsForPublicIds(pubicIDList);

				// Serialize data object to a file
				ObjectOutputStream out = new ObjectOutputStream(
						new FileOutputStream(filePathName));
				for (FormV2 form : formDtos) {
					out.writeObject(form);
				}
				out.close();

			}

		} catch (FileNotFoundException fne) {
			System.out.println(fne);
		} catch (IOException ioe) {
			System.out.println(ioe);
		}

		return formDtos;
	}

	@Override
	public List<QuestionTransferObject> getQuestionsByPublicId(String publicId) {
		final String fileName = "Question- " + publicId + ".ser";

		String filePathName = mockRepoPath + fileName;
		File objs = new File(filePathName);

		List<QuestionTransferObject> questions = null;

		try {
			if (!objs.exists()) {
				int pubId = Integer.parseInt(publicId);
				questions = questionV2Dao.getQuestionsByPublicId(pubId);

				// Serialize data object to a file
				ObjectOutputStream out = new ObjectOutputStream(
						new FileOutputStream(filePathName));
				for (QuestionTransferObject question : questions) {
					out.writeObject(question);
				}
				out.close();

			}
		} catch (FileNotFoundException fne) {
			System.out.println(fne);
		} catch (IOException ioe) {
			System.out.println(ioe);
		}

		return questions;
	}

	@Override
	public List<DataElementTransferObject> getCDEByPublicId(String cdePublicId) {
		final String fileName = "CDE- " + cdePublicId + ".ser";

		String filePathName = mockRepoPath + fileName;
		File objs = new File(filePathName);

		List<DataElementTransferObject> cdes = null;

		try {
			if (!objs.exists()) {
				cdes = questionV2Dao.getCdesByPublicId(cdePublicId);

				// Serialize data object to a file
				ObjectOutputStream out = new ObjectOutputStream(
						new FileOutputStream(filePathName));
				for (DataElementTransferObject cde : cdes) {
					out.writeObject(cde);
				}
				out.close();

			}

		} catch (FileNotFoundException fne) {
			System.out.println(fne);
		} catch (IOException ioe) {
			System.out.println(ioe);
		}

		return cdes;
	}

	@Override
	public List<ReferenceDocumentTransferObject> getReferenceDocsForQuestionCde(
			String cdePublicId, String cdeVersion) {
		final String fileName = "refdocss- " + cdePublicId + "-" + cdeVersion
				+ ".ser";

		String filePathName = mockRepoPath + fileName;
		File objs = new File(filePathName);

		List<ReferenceDocumentTransferObject> deRefDocs = null;

		try {
			if (!objs.exists()) {
				deRefDocs = questionV2Dao.getAllReferenceDocumentsForDE(
						Integer.parseInt(cdePublicId),
						Float.parseFloat(cdeVersion));

				// Serialize data object to a file
				ObjectOutputStream out = new ObjectOutputStream(
						new FileOutputStream(filePathName));
				for (ReferenceDocumentTransferObject refdoc : deRefDocs) {
					out.writeObject(refdoc);
				}
				out.close();

			}
		} catch (FileNotFoundException fne) {
			System.out.println(fne);
		} catch (IOException ioe) {
			System.out.println(ioe);
		}

		return deRefDocs;
	}

	public List<QuestionTransferObject> getQuestionsByPublicIds(
			List<String> publicIds) {
		final String fileName = "Question-" + publicIds.get(0) + ".ser";

		String filePathName = mockRepoPath + fileName;
		File objs = new File(filePathName);

		List<QuestionTransferObject> questions = null;

		try {
			questions = questionV2Dao.getQuestionsByPublicIds(publicIds);

			// Serialize data object to a file
			ObjectOutputStream out = new ObjectOutputStream(
					new FileOutputStream(filePathName));
			for (QuestionTransferObject question : questions) {
				out.writeObject(question);
			}
			out.close();

		} catch (FileNotFoundException fne) {
			System.out.println(fne);
		} catch (IOException ioe) {
			System.out.println(ioe);
		}

		return questions;
	}

	public List<DataElementTransferObject> getCDEsByPublicIds(
			List<String> cdePublicIds) {
		final String fileName = "CDE-" + cdePublicIds.get(0) + ".ser";

		String filePathName = mockRepoPath + fileName;
		File objs = new File(filePathName);

		List<DataElementTransferObject> cdes = null;

		try {
			if (!objs.exists()) {
				cdes = questionV2Dao.getCdesByPublicIds(cdePublicIds);

				// Serialize data object to a file
				ObjectOutputStream out = new ObjectOutputStream(
						new FileOutputStream(filePathName));
				for (DataElementTransferObject cde : cdes) {
					out.writeObject(cde);
				}
				out.close();

			}

		} catch (FileNotFoundException fne) {
			System.out.println(fne);
		} catch (IOException ioe) {
			System.out.println(ioe);
		}

		return cdes;
	}

	public HashMap<String, List<ReferenceDocumentTransferObject>> getReferenceDocsByCdePublicIds(
			List<String> cdePublicIds) {
		final String fileName = "refdocss-" + cdePublicIds.get(0) + ".ser";

		String filePathName = mockRepoPath + fileName;
		File objs = new File(filePathName);

		HashMap<String, List<ReferenceDocumentTransferObject>> deRefDocs = null;

		try {
			if (!objs.exists()) {
				deRefDocs = questionV2Dao
						.getReferenceDocumentsByCdePublicIds(cdePublicIds);

				// Serialize data object to a file
				ObjectOutputStream out = new ObjectOutputStream(
						new FileOutputStream(filePathName));
				out.writeObject(deRefDocs);

				out.close();

			}
		} catch (FileNotFoundException fne) {
			System.out.println(fne);
		} catch (IOException ioe) {
			System.out.println(ioe);
		}

		return deRefDocs;
	}

	public HashMap<String, List<PermissibleValueV2TransferObject>> getPermissibleValuesByVdIds(
			List<String> vdSeqIds) {
		final String fileName = "permisVals-" + vdSeqIds.get(0) + ".ser";

		String filePathName = mockRepoPath + fileName;
		File objs = new File(filePathName);

		HashMap<String, List<PermissibleValueV2TransferObject>> pvs = null;

		try {
			if (!objs.exists()) {
				pvs = valueDomainV2Dao.getPermissibleValuesByVdIds(vdSeqIds);

				// Serialize data object to a file
				ObjectOutputStream out = new ObjectOutputStream(
						new FileOutputStream(filePathName));
				out.writeObject(pvs);
				/*
				 * Iterator ite = pvs.keySet().iterator(); while (ite.hasNext())
				 * { out.writeObject(pvs.get(ite.next())); }
				 */
				out.close();

			}
		} catch (FileNotFoundException fne) {
			System.out.println(fne);
		} catch (IOException ioe) {
			System.out.println(ioe);
		}

		return pvs;
	}

	public static void main(String[] args) {
		@SuppressWarnings("resource")
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"/applicationContext-mock-repository-test.xml");
		// "/applicationContext-db-connectionless-test.xml");

		XmlValidationService xmlValidator = (XmlValidationServiceImpl) applicationContext
				.getBean("xmlValidationService");
		ContentValidationService contentValidator = (ContentValidationServiceImpl) applicationContext
				.getBean("contentValidationService");

		String testFile = ".\\test\\data\\3193449_has_valid_values.xml";

		try {
			FormCollection aColl = new FormCollection();
			aColl.setXmlPathOnServer(".\\test\\data");
			aColl.setXmlFileName("3193449_has_valid_values.xml");
			aColl.setName("TestCollection");
			aColl = xmlValidator.validateXml(aColl);
			
			List<FormDescriptor> forms = aColl.getForms();

			contentValidator.validateXmlContent(aColl);

		} catch (FormLoaderServiceException fle) {
			System.out.println(fle);
		}
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

	@Override
	public String createForm(FormDescriptor form, String xmlPathName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String updateForm(FormDescriptor form, String userName,
			String xmlPathName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPublicIdVersionBySeqids(List<FormDescriptor> forms) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String createFormCollectionRecords(FormCollection coll) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasLoadFormRight(FormDescriptor form, String userName, String contextName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getContextSeqIdByName(String contextName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FormCollection> getAllLoadedCollectionsByUser(String user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void checkWorkflowStatusName(FormDescriptor form) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unloadForm(FormDescriptor form) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String createFormNewVersion(FormDescriptor form,
			String loggedinUser, String xmlPathName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getMaxNameRepeatForCollection(String collName) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getLatestVersionForForm(String publicId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void updateFormInCollectionRecord(FormCollection coll,
			FormDescriptor form) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public HashMap<String, Date> getModifiedDateForForms(List<String> formSeqids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getDefinitionTextsByVmIds(String vmSeqid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getDesignationNamesByVmIds(String vmSeqid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ValueDomainV2 getValueDomainBySeqid(String vdseqid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FormDescriptor> getAllFormsWithCollectionId(
			String collectionSeqid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FormV2TransferObject> getFormsInCadsrBySeqids(
			List<String> formSeqids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean designationTypeExists(String designName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getOrganizationSeqidByName(String orgName) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
