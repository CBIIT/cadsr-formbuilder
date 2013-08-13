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
import gov.nih.nci.ncicb.cadsr.common.resource.FormV2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class FormLoaderRepositoryMockup implements FormLoaderRepository {
	
	String mockRepoPath = ".\\test\\data\\serializedDtos\\";
	
	@Override
	public List<FormV2> getFormsForPublicIDs(List<String> pubicIDList) {
		final String fileName =  "FormV2List.ser";
		
		String filePathName = mockRepoPath + fileName;
		File objs = new File(filePathName);
		
		List<FormV2> formDtos = null;
		try {
			if (!objs.exists()) 
				return null;


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
		final String fileName =  "Question-" + publicId + ".ser";

		String filePathName = mockRepoPath + fileName;
		File objs = new File(filePathName);
		
		List<QuestionTransferObject> questions = null;
		
		try {
			if (!objs.exists()) 
				return null;

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
		final String fileName =  "CDE-" + cdePublicId + ".ser";

		String filePathName = mockRepoPath + fileName;
		File objs = new File(filePathName);
		
		List<DataElementTransferObject> cdes = null;
		
		try {
			if (!objs.exists()) 
				return null;
			
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
	public List<ReferenceDocumentTransferObject> getReferenceDocsForQuestionCde(
			String cdePublicId, String cdeVersion) {
		final String fileName =  "refdocss- " + cdePublicId + "-" + cdeVersion + ".ser";

		String filePathName = mockRepoPath + fileName;
		File objs = new File(filePathName);
		
		List<ReferenceDocumentTransferObject> deRefDocs = null;
		
		try {
			if (!objs.exists()) 
				return null;

				deRefDocs = new ArrayList<ReferenceDocumentTransferObject>();
				FileInputStream fis = new FileInputStream(filePathName);
				//Create new ObjectInputStream object to read object from file
				ObjectInputStream obj = new ObjectInputStream(fis);

				while (fis.available() != -1) {
					//Read object from file
					ReferenceDocumentTransferObject acc = (ReferenceDocumentTransferObject) obj.readObject();
					deRefDocs.add(acc);
				}
				fis.close();
		
			
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
	public List<PermissibleValueV2TransferObject> getValueDomainPermissibleValuesByVdId(
			String vdSeqId) {
		final String fileName =  "permisVals- " + vdSeqId + ".ser";

		String filePathName = mockRepoPath + fileName;
		File objs = new File(filePathName);
		
		List<PermissibleValueV2TransferObject> pvs = null;
		
		try {
			if (!objs.exists()) 
				return null;
			
				pvs = new ArrayList<PermissibleValueV2TransferObject>();
				FileInputStream fis = new FileInputStream(filePathName);
				//Create new ObjectInputStream object to read object from file
				ObjectInputStream obj = new ObjectInputStream(fis);

				while (fis.available() != -1) {
					//Read object from file
					PermissibleValueV2TransferObject acc = (PermissibleValueV2TransferObject) obj.readObject();
					pvs.add(acc);
				}
				fis.close();
			
			}catch (ClassNotFoundException fne) {
				System.out.println(fne);
			}catch (FileNotFoundException fne) {
				System.out.println(fne);
			} catch (IOException ioe) {
				System.out.println(ioe);
			}


		
		return pvs;
	}
	
	
	
	@Override
	public String getContextSeqId(String contextName) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<QuestionTransferObject> getQuestionsByPublicIds(List<String> publicIds) {
		final String fileName =  "Question-" + publicIds.get(0) + ".ser";

		String filePathName = mockRepoPath + fileName;
		File objs = new File(filePathName);
		
		List<QuestionTransferObject> questions = new ArrayList<QuestionTransferObject>();
		
		try {
			if (!objs.exists()) 
				return null;

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
			
			}catch (ClassNotFoundException fne) {
				System.out.println(fne);
			}catch (FileNotFoundException fne) {
			System.out.println(fne);
		} catch (IOException ioe) {
			System.out.println(ioe);
		}

		return questions;
	}
	
	public List<DataElementTransferObject> getCDEsByPublicIds(List<String> cdePublicIds) {
		final String fileName =  "CDE-" + cdePublicIds.get(0) + ".ser";

		String filePathName = mockRepoPath + fileName;
		File objs = new File(filePathName);
		
		List<DataElementTransferObject> cdes = new ArrayList<DataElementTransferObject>();
		
		try {
			if (!objs.exists()) 
				return null;
			
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
			
			
			}catch (ClassNotFoundException fne) {
				System.out.println(fne);
			}catch (FileNotFoundException fne) {
				System.out.println(fne);
			} catch (IOException ioe) {
				System.out.println(ioe);
			}


		
		return cdes;
	}
	
	public HashMap<String, List<ReferenceDocumentTransferObject>> 
	getReferenceDocsByCdePublicIds(List<String> cdePublicIds) {
		final String fileName =  "refdocss-" + cdePublicIds.get(0) + ".ser";

		String filePathName = mockRepoPath + fileName;
		File objs = new File(filePathName);
		
		HashMap<String, List<ReferenceDocumentTransferObject>> deRefDocs = null;
		
		try {
			if (!objs.exists()) 
				return null;

				FileInputStream fis = new FileInputStream(filePathName);
				//Create new ObjectInputStream object to read object from file
				ObjectInputStream obj = new ObjectInputStream(fis);

				while (fis.available() != -1) {
					//Read object from file
					deRefDocs = (HashMap<String, List<ReferenceDocumentTransferObject>>) obj.readObject();

				}
				fis.close();
		
			
			}catch (ClassNotFoundException fne) {
				System.out.println(fne);
			}catch (FileNotFoundException fne) {
				System.out.println(fne);
			} catch (IOException ioe) {
				System.out.println(ioe);
			}


		
		return deRefDocs;
	}
	
	public HashMap<String, List<PermissibleValueV2TransferObject>> 
	getPermissibleValuesByVdIds(List<String> vdSeqIds) {
		final String fileName =  "permisVals-" + vdSeqIds.get(0) + ".ser";

		String filePathName = mockRepoPath + fileName;
		File objs = new File(filePathName);
		
		HashMap<String, List<PermissibleValueV2TransferObject>> pvs = null;
		
		try {
			if (!objs.exists()) 
				return null;
				
				FileInputStream fis = new FileInputStream(filePathName);
				//Create new ObjectInputStream object to read object from file
				ObjectInputStream obj = new ObjectInputStream(fis);
				pvs = (HashMap<String, List<PermissibleValueV2TransferObject>>) obj.readObject();
				
				
				fis.close();

		}catch (ClassNotFoundException fne) {
			System.out.println(fne);
		}
		catch (FileNotFoundException fne) {
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

	@Override
	public String createNewForm(FormDescriptor form, String userName, String xmlPathName, int formIdx) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}