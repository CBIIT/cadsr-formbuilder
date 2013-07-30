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
import gov.nih.nci.ncicb.cadsr.common.dto.QuestionTransferObject;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc.JDBCFormDAOV2;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc.JDBCModuleDAOV2;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc.JDBCQuestionDAOV2;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc.JDBCValueDomainDAOV2;
import gov.nih.nci.ncicb.cadsr.common.resource.FormV2;
import gov.nih.nci.ncicb.cadsr.common.resource.PermissibleValueV2;
import gov.nih.nci.ncicb.cadsr.common.resource.ReferenceDocument;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class FormLoaderRepositoryDataGen implements FormLoaderRepository {
	
	String mockRepoPath = ".\\test\\data\\serializedDtos\\";
	
	JDBCFormDAOV2 formV2Dao;
	JDBCModuleDAOV2 moduleV2Dao;
	JDBCQuestionDAOV2 questionV2Dao;
	JDBCValueDomainDAOV2 valueDomainV2Dao;

	
	@Override
	public List<FormV2> getFormsForPublicIDs(List<String> pubicIDList) {
		final String fileName =  "FormV2List.ser";
		
		String filePathName = mockRepoPath + fileName;
		File objs = new File(filePathName);
		
		List<FormV2> formDtos = null;
		try {
			if (!objs.exists()) {
				formDtos = formV2Dao.getVersionsByPublicIds(pubicIDList);
				
				// Serialize data object to a file
				ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePathName));
				for (FormV2 form : formDtos) {
					out.writeObject(form);
				}
				out.close();

			} else {

				formDtos = new ArrayList<FormV2>();
				FileInputStream fis = new FileInputStream(filePathName);
				//Create new ObjectInputStream object to read object from file
				ObjectInputStream obj = new ObjectInputStream(fis);

				while (fis.available() != -1) {
					//Read object from file
					FormV2TransferObject acc = (FormV2TransferObject) obj.readObject();
					formDtos.add(acc);
				}
				fis.close();
			}
			
			}catch (ClassNotFoundException fne) {
				System.out.println(fne);
			}catch (FileNotFoundException fne) {
				System.out.println(fne);
			} catch (IOException ioe) {
				System.out.println(ioe);
			}


		
		return formDtos;
	}

	@Override
	public List<QuestionTransferObject> getQuestionsByPublicId(String publicId) {
		final String fileName =  "Question- " + publicId + ".ser";

		String filePathName = mockRepoPath + fileName;
		File objs = new File(filePathName);
		
		List<QuestionTransferObject> questions = null;
		
		try {
			if (!objs.exists()) {
				int pubId = Integer.parseInt(publicId);
				questions = questionV2Dao.getQuestionsByPublicId(pubId);
				
				// Serialize data object to a file
				ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePathName));
				for (QuestionTransferObject question : questions) {
					out.writeObject(question);
				}
				out.close();

			} else {

				questions = new ArrayList<QuestionTransferObject>();
				FileInputStream fis = new FileInputStream(filePathName);
				//Create new ObjectInputStream object to read object from file
				ObjectInputStream obj = new ObjectInputStream(fis);

				while (fis.available() != -1) {
					//Read object from file
					QuestionTransferObject acc = (QuestionTransferObject) obj.readObject();
					questions.add(acc);
				}
				fis.close();
			}
			
			}catch (ClassNotFoundException fne) {
				System.out.println(fne);
			}catch (FileNotFoundException fne) {
				System.out.println(fne);
			} catch (IOException ioe) {
				System.out.println(ioe);
			}


		
		return questions;
	}

	@Override
	public List<DataElementTransferObject> getCDEByPublicId(String cdePublicId) {
		final String fileName =  "CDE- " + cdePublicId + ".ser";

		String filePathName = mockRepoPath + fileName;
		File objs = new File(filePathName);
		
		List<DataElementTransferObject> cdes = null;
		
		try {
			if (!objs.exists()) {
				cdes = questionV2Dao.getCdesByPublicId(cdePublicId);
				
				// Serialize data object to a file
				ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePathName));
				for (DataElementTransferObject cde : cdes) {
					out.writeObject(cde);
				}
				out.close();

			} else {

				cdes = new ArrayList<DataElementTransferObject>();
				FileInputStream fis = new FileInputStream(filePathName);
				//Create new ObjectInputStream object to read object from file
				ObjectInputStream obj = new ObjectInputStream(fis);

				while (fis.available() != -1) {
					//Read object from file
					DataElementTransferObject acc = (DataElementTransferObject) obj.readObject();
					cdes.add(acc);
				}
				fis.close();
			}
			
			}catch (ClassNotFoundException fne) {
				System.out.println(fne);
			}catch (FileNotFoundException fne) {
				System.out.println(fne);
			} catch (IOException ioe) {
				System.out.println(ioe);
			}


		
		return cdes;
	}

	@Override
	public List<ReferenceDocument> getReferenceDocsForQuestionCde(
			String cdePublicId, String cdeVersion) {
		final String fileName =  "refdocss- " + cdePublicId + "-" + cdeVersion + ".ser";

		String filePathName = mockRepoPath + fileName;
		File objs = new File(filePathName);
		
		List<ReferenceDocument> deRefDocs = null;
		
		try {
			if (!objs.exists()) {
				deRefDocs = questionV2Dao.getAllReferenceDocuments(
						Integer.parseInt(cdePublicId), Float.parseFloat(cdeVersion));
				
				// Serialize data object to a file
				ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePathName));
				for (ReferenceDocument refdoc : deRefDocs) {
					out.writeObject(refdoc);
				}
				out.close();

			} else {

				deRefDocs = new ArrayList<ReferenceDocument>();
				FileInputStream fis = new FileInputStream(filePathName);
				//Create new ObjectInputStream object to read object from file
				ObjectInputStream obj = new ObjectInputStream(fis);

				while (fis.available() != -1) {
					//Read object from file
					ReferenceDocument acc = (ReferenceDocument) obj.readObject();
					deRefDocs.add(acc);
				}
				fis.close();
			}
			
			}catch (ClassNotFoundException fne) {
				System.out.println(fne);
			}catch (FileNotFoundException fne) {
				System.out.println(fne);
			} catch (IOException ioe) {
				System.out.println(ioe);
			}


		
		return deRefDocs;
	}

	@Override
	public List<PermissibleValueV2> getValueDomainPermissibleValuesByVdId(
			String vdSeqId) {
		final String fileName =  "permisVals- " + vdSeqId + ".ser";

		String filePathName = mockRepoPath + fileName;
		File objs = new File(filePathName);
		
		List<PermissibleValueV2> pvs = null;
		
		try {
			if (!objs.exists()) {
				pvs = valueDomainV2Dao.getPermissibleValuesByVdId(vdSeqId);
				
				// Serialize data object to a file
				ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePathName));
				for (PermissibleValueV2 cde : pvs) {
					out.writeObject(cde);
				}
				out.close();

			} else {

				pvs = new ArrayList<PermissibleValueV2>();
				FileInputStream fis = new FileInputStream(filePathName);
				//Create new ObjectInputStream object to read object from file
				ObjectInputStream obj = new ObjectInputStream(fis);

				while (fis.available() != -1) {
					//Read object from file
					PermissibleValueV2 acc = (PermissibleValueV2) obj.readObject();
					pvs.add(acc);
				}
				fis.close();
			}
			
			}catch (ClassNotFoundException fne) {
				System.out.println(fne);
			}catch (FileNotFoundException fne) {
				System.out.println(fne);
			} catch (IOException ioe) {
				System.out.println(ioe);
			}


		
		return pvs;
	}

	
	public static void main(String[] args) {
		@SuppressWarnings("resource")
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"/applicationContext-db-connectionless-test.xml");
		//		"/applicationContext-mock-repository-test.xml");

		XmlValidationService xmlValidator = 
				(XmlValidationServiceImpl)applicationContext.getBean("xmlValidationService");
		ContentValidationService contentValidator = 
				(ContentValidationServiceImpl)applicationContext.getBean("contentValidationService");
		
		
		String testFile = ".\\test\\data\\3193449_has_valid_values.xml";
		
		try {
		List<FormDescriptor> forms = xmlValidator.validateXml(testFile);
		FormCollection aColl = new FormCollection();
		aColl.setName("TestCollection");
		aColl.setForms(forms);
		
		
		contentValidator.validateXmlContent(aColl, testFile);
		
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

	
}
