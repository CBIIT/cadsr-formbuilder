/**
 * 
 */
package gov.nih.nci.cadsr.formloader.service.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import gov.nih.nci.cadsr.formloader.domain.FormCollection;
import gov.nih.nci.cadsr.formloader.domain.FormDescriptor;
import gov.nih.nci.cadsr.formloader.domain.FormStatus;

/**
 * @author yangs8
 *
 */
public class MockDataGenerator {
	
	public static FormCollection generateContentValidationData() {
		FormCollection aColl = new FormCollection();
		List<FormLoaderServiceError> errors = new ArrayList<FormLoaderServiceError>();
		  
		  aColl.setCreatedBy("Denise");
		  aColl.setDateCreated(new Date());
		  aColl.setDescription("This is the collection from nci");
		  aColl.setId("1234567");
		  aColl.setName("Denise's Collection");
		  aColl.setXmlFileName("denise-coll.xml");
		  aColl.setXmlPathOnServer("/local/content/formloader/20130703");
		  
		  //1
		  List<FormDescriptor> forms = new ArrayList<FormDescriptor>();
		  FormDescriptor form = new FormDescriptor("443355", "1234345", "1.0");
		  form.setContext("CTRP");
		  FormStatus status = new FormStatus(FormStatus.STATUS_DB_VALIDATED);
		  List<String> msgs = new ArrayList<String>();
		  msgs.add("Question 1 has no default text");
		  msgs.add("Question 2 need work");
		  status.setMessages(msgs);
		  form.setStatus(status);
		  forms.add(form);
		  
		  //2
		  form = new FormDescriptor("553355", "1234346", "3.0");
		  form.setContext("NCIP");
		  status = new FormStatus(FormStatus.STATUS_DB_VALIDATED);
		  List<String> msgs2 = new ArrayList<String>();
		  msgs.add("No error / success");
		  status.setMessages(msgs);
		  form.setStatus(status);
		  forms.add(form);
		  
		  //3
		  form = new FormDescriptor("663355", "1234347", "4.0");
		  form.setContext("NCIP");
		  form.setLoadType(FormDescriptor.LOAD_TYPE_UPDATE_FORM);
		  status = new FormStatus(FormStatus.STATUS_DB_VALIDATED);
		  List<String> msgs3 = new ArrayList<String>();
		  msgs.add("Question 1 has no default text");
		  msgs.add("Question 2 need work");
		  status.setMessages(msgs);
		  form.setStatus(status);
		  forms.add(form);
		  
		  //4
		  form = new FormDescriptor("773355", "1234348", "2.0");
		  form.setWorkflowStatusName("RELEASED");
		  form.setContext("NCIP");
		  form.setLoadType(FormDescriptor.LOAD_TYPE_UPDATE_FORM);
		  status = new FormStatus(FormStatus.STATUS_DB_VALIDATED);
		  List<String> msgs4 = new ArrayList<String>();
		  msgs.add("Question 3 has no default text");
		  msgs.add("Question 4 need work");
		  status.setMessages(msgs);
		  form.setStatus(status);
		  forms.add(form);
		  
		  //5
		 //public id = 0
		  form = new FormDescriptor("883355", "0", "1.0");
		  forms.add(form);
		  //6
		  forms.add(new FormDescriptor("883355", "1234349", "1.0"));
		  
		  aColl.setForms(forms);
		  
		  return aColl;
	}
	
	public static FormCollection generateBaseData() {
		return null;
		
	}

}
