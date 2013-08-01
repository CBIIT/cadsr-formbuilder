package gov.nih.nci.cadsr.formloader.client;

import gov.nih.nci.cadsr.formloader.domain.FormCollection;
import gov.nih.nci.cadsr.formloader.domain.FormDescriptor;
import gov.nih.nci.cadsr.formloader.domain.ModuleDescriptor;
import gov.nih.nci.cadsr.formloader.domain.QuestionDescriptor;
import gov.nih.nci.cadsr.formloader.service.ContentValidationService;
import gov.nih.nci.cadsr.formloader.service.LoadingService;
import gov.nih.nci.cadsr.formloader.service.XmlValidationService;
import gov.nih.nci.cadsr.formloader.service.common.FormLoaderServiceException;
import gov.nih.nci.cadsr.formloader.service.impl.ContentValidationServiceImpl;
import gov.nih.nci.cadsr.formloader.service.impl.XmlValidationServiceImpl;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class FormLoaderServiceClient {
	
	public static void main(String[] args) {

		@SuppressWarnings("resource")
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"/applicationContext-service-test.xml");

		XmlValidationService xmlValidator = 
				(XmlValidationServiceImpl)applicationContext.getBean("xmlValidationService");
		ContentValidationService contentValidator = 
				(ContentValidationServiceImpl)applicationContext.getBean("contentValidationService");
		LoadingService loadService =  (LoadingService)applicationContext.getBean("loadService");
		
		//Input xml
		
		//good file
		String testFile = ".\\test\\data\\3193449_has_valid_values.xml";
		
		//bad file
		//String testFile = ".\\test\\data\\forms-malformed.xml";
		
		
		try {
			//Step 1: call xml validation service
			List<FormDescriptor> forms = xmlValidator.validateXml(testFile);
			
			FormCollection aColl = new FormCollection();
			aColl.setName("TestCollection");
			aColl.setForms(forms);

			//Step 2: call content validation service
			aColl= contentValidator.validateXmlContent(aColl, testFile);
			
			//Step 3: call load service
			aColl = loadService.loadForms(aColl);
			
			//Step 4: check status
			
			// Coming soon...
			/* 
			 * FormCollectionStatus status = aColl.getStatus();
			 * 
			 * 
			 */
			
			
			//This is just to show what messages get generated during validation and load process
			//Client app will NOT need to do this
			forms = aColl.getForms();
			for (FormDescriptor form : forms) {
				List<String> msgs = form.getMessages();
				for (String msg : msgs) 
					System.out.println("=====" + msg);
				
				List<ModuleDescriptor> modules = form.getModules();
				for (ModuleDescriptor module : modules) {
					List<QuestionDescriptor> questions = module.getQuestions();
					for (QuestionDescriptor question : questions) {
						List<String> qmsgs = question.getMessages();
						for (String m : qmsgs)
							System.out.println("=====" + m);
					}
				}
			}
			

		} catch (FormLoaderServiceException fle) {
			System.out.println("=====" + fle.getMessage());
		}
		
		
	}

}
