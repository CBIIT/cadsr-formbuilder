/**
 * 
 */
package gov.nih.nci.cadsr.formloader.service.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import gov.nih.nci.cadsr.formloader.domain.FormCollection;
import gov.nih.nci.cadsr.formloader.domain.FormHeader;
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
		  
		  List<FormHeader> forms = new ArrayList<FormHeader>();
		  FormHeader form = new FormHeader("443355", 1234345, (float)1.0);
		  form.setContextName("CTRP");
		  form.setLoadType(FormHeader.LOAD_TYPE_NEW);
		  FormStatus status = new FormStatus(FormStatus.STATUS_DB_VALIDATED);
		  List<String> msgs = new ArrayList<String>();
		  msgs.add("Question 1 has no default text");
		  msgs.add("Question 2 need work");
		  status.setMessages(msgs);
		  form.setStatus(status);
		  forms.add(form);
		  
		  form = new FormHeader("553355", 1234355, (float)4.0);
		  form.setContextName("NCIP");
		  form.setLoadType(FormHeader.LOAD_TYPE_NEW_VERSION);
		  status = new FormStatus(FormStatus.STATUS_DB_VALIDATED);
		  List<String> msgs2 = new ArrayList<String>();
		  msgs.add("No error / success");
		  status.setMessages(msgs);
		  form.setStatus(status);
		  forms.add(form);
		  
		  form = new FormHeader("663355", 1234366, (float)3.0);
		  form.setContextName("NCIP");
		  form.setLoadType(FormHeader.LOAD_TYPE_UPDATE_FORM);
		  status = new FormStatus(FormStatus.STATUS_DB_VALIDATED);
		  List<String> msgs3 = new ArrayList<String>();
		  msgs.add("Question 1 has no default text");
		  msgs.add("Question 2 need work");
		  status.setMessages(msgs);
		  form.setStatus(status);
		  forms.add(form);
		  
		  form = new FormHeader("773355", 1234377, (float)2.0);
		  form.setAslName("RELEASED");
		  form.setContextName("NCIP");
		  form.setLoadType(FormHeader.LOAD_TYPE_UPDATE_FORM);
		  status = new FormStatus(FormStatus.STATUS_DB_VALIDATED);
		  List<String> msgs4 = new ArrayList<String>();
		  msgs.add("Question 3 has no default text");
		  msgs.add("Question 4 need work");
		  status.setMessages(msgs);
		  form.setStatus(status);
		  forms.add(form);
		  
		  form = new FormHeader("883355", 1234388, (float)1.0);
		  forms.add(new FormHeader("883355", 1234388, (float)1.0));
		  
		  aColl.setForms(forms);
		  
		  return aColl;
	}
	
	public static FormCollection generateBaseData() {
		return null;
		
	}

}
