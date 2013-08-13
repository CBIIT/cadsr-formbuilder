/*L
 * Copyright Oracle Inc, ScenPro Inc, SAIC-F
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-formbuilder/LICENSE.txt for details.
 */

package gov.nih.nci.cadsr.formloader.service.common;

import gov.nih.nci.cadsr.formloader.domain.FormCollection;
import gov.nih.nci.cadsr.formloader.domain.FormDescriptor;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
 
public class JAXBMockGen {
	public static String namePath = ".\\test\\data\\mockdata-forms.xml";
	
	public static void main(String[] args) {
 
	  FormCollection aColl = new FormCollection();
	  
	  aColl.setCreatedBy("Denise");
	  aColl.setDateCreated(new Date());
	  aColl.setDescription("This is the collection from nci");
	  aColl.setId("1234567");
	  aColl.setName("Denise's Collection");
	  aColl.setXmlFileName("denise-coll.xml");
	  aColl.setXmlPathOnServer("/local/content/formloader/20130703");
	  
	  List<FormDescriptor> forms = new ArrayList<FormDescriptor>();
	  FormDescriptor form = new FormDescriptor("443355", "1234345", "1.0");
	  form.setContext("CRF");
	  form.setLoadType(FormDescriptor.LOAD_TYPE_NEW);
	  forms.add(form);
	  
	  form = new FormDescriptor("553355", "1234355", "4.0");
	  form.setContext("CRF");
	  form.setLoadType(FormDescriptor.LOAD_TYPE_NEW_VERSION);
	  forms.add(form);
	  
	  form = new FormDescriptor("663355", "1234366", "3.0");
	  form.setContext("NCIP");
	  form.setLoadType(FormDescriptor.LOAD_TYPE_UPDATE_FORM);
	  forms.add(form);
	  
	  form = new FormDescriptor("773355", "1234377", "2.0");
	  form.setWorkflowStatusName("RELEASED");
	  form.setContext("NCIP");
	  form.setLoadType(FormDescriptor.LOAD_TYPE_UPDATE_FORM);
	  forms.add(form);
	  
	  form = new FormDescriptor("883355", "1234388", "1.0");
	  forms.add(new FormDescriptor("883355", "1234388", "1.0"));
	  
	  aColl.setForms(forms);
 
	  try {
 
		File file = new File(JAXBMockGen.namePath);
		JAXBContext jaxbContext = JAXBContext.newInstance(FormCollection.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
 
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
 
		jaxbMarshaller.marshal(aColl, file);
		jaxbMarshaller.marshal(aColl, System.out);
 
	      } catch (JAXBException e) {
		e.printStackTrace();
	      }
 
	}
	
	public static FormCollection xmlToObjects(String fullPathname) {
		 
		 try {
	
			File file = new File(fullPathname);
			JAXBContext jaxbContext = JAXBContext.newInstance(FormCollection.class);
	 
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			FormCollection coll = (FormCollection) jaxbUnmarshaller.unmarshal(file);
			System.out.println(coll);
			return coll;
	 
		  } catch (JAXBException e) {
			e.printStackTrace();
		  }
		 
		 return null;
	 
		}
}