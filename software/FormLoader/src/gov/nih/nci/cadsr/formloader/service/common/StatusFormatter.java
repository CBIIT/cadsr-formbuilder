package gov.nih.nci.cadsr.formloader.service.common;

import gov.nih.nci.cadsr.formloader.domain.FormCollection;
import gov.nih.nci.cadsr.formloader.domain.FormCollectionStatus;
import gov.nih.nci.cadsr.formloader.domain.FormDescriptor;
import gov.nih.nci.cadsr.formloader.domain.FormStatus;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class StatusFormatter {
	
	public static String getStatusInXml(FormDescriptor form) {
		FormStatus formStatus = form.getStructuredStatus();
		
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(FormStatus.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			final StringWriter stringWriter = new StringWriter();
			
			jaxbMarshaller.marshal(formStatus, stringWriter);
			return stringWriter.toString();
			
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		
		return "Form status xml generation failed";
	}
	
	public static String getStatusInXml(FormCollection coll) {
		FormCollectionStatus collStatus= coll.getStructuredStatus();
		
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(FormCollectionStatus.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			final StringWriter stringWriter = new StringWriter();
			
			jaxbMarshaller.marshal(collStatus, stringWriter);
			return stringWriter.toString();
			
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		
		return "Collection status xml generation failed";
	}
	
	public static void writeStatusToXml(String content, String fileNamePath) {
		try {
			 
			File file = new File(fileNamePath);
 
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
 
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();
 
			System.out.println("Done");
 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
