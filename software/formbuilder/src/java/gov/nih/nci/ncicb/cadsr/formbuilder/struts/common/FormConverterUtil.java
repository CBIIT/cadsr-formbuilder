// This class provides utility functions for supporting the new form cart format

package gov.nih.nci.ncicb.cadsr.formbuilder.struts.common;

import gov.nih.nci.ncicb.cadsr.common.resource.FormV2;

import java.util.LinkedList;
import java.io.StringWriter;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.ValidationException;
import javax.xml.transform.*;
import javax.xml.transform.stream.*;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.StringReader;
import javax.servlet.ServletContext; 

import gov.nih.nci.ncicb.cadsr.common.util.logging.Log;
import gov.nih.nci.ncicb.cadsr.common.util.logging.LogFactory;

import net.sf.saxon.TransformerFactoryImpl;

import gov.nih.nci.ncicb.cadsr.formbuilder.common.FormCartOptionsUtil;


public class FormConverterUtil {
	private static Log log = LogFactory.getLog(FormConverterUtil.class.getName());
	
	static private FormConverterUtil _instance = null;
	public static final String V1ExtendedToV2XSL = "/transforms/ConvertFormCartV1ExtendedToV2.xsl";
	protected Transformer transformerV1ToV2 = null;
	

	public String getCartObjectType() {
		return FormCartOptionsUtil.instance().xsdLocation();
	}

	
	public String convertFormToV2(FormV2 crf) {

		// Start with our standard conversion to xml (in V1 format)
		StringWriter writer = new StringWriter();
		try {
			Marshaller.marshal(crf, writer);
		} catch (MarshalException ex) {
			// need exception handling
		} catch (ValidationException ex) {
			// need exception handling	
		}

		// Now use our transformer to create V2 format
		Source xmlInput = new StreamSource(new StringReader(writer.toString()));
//log.debug(writer.toString());		
		ByteArrayOutputStream xmlOutputStream = new ByteArrayOutputStream();  
		Result xmlOutput = new StreamResult(xmlOutputStream);
		try {
			transformerV1ToV2.transform(xmlInput, xmlOutput);
		} catch (TransformerException e) {
			// need exception handling.
		}	
				
		String V2XML = xmlOutputStream.toString();
		return V2XML;
	}
		
	protected FormConverterUtil() {
		
		StreamSource xslSource = null;
		try {
			InputStream xslStream = this.getClass().getResourceAsStream(V1ExtendedToV2XSL);  
			xslSource = new StreamSource(xslStream);
		}
		catch(Exception e) {
			System.out.println("FormConverterUtil error loading conversion xsl: " + V1ExtendedToV2XSL + " exc: "+ e);
		}
		
		try {
			log.debug("creating transformerV1ToV2");			
			transformerV1ToV2 = net.sf.saxon.TransformerFactoryImpl.newInstance().newTransformer(xslSource);
		} catch (TransformerException e) {
		// need exception handling.
			log.debug("transformerV1ToV2 exception: " + e.toString());
			log.debug("transformerV1ToV2 exception: " + e.getMessage());
		}	
	} 
	 
	 
	static public FormConverterUtil instance(){
		if (_instance == null) {
			_instance = new FormConverterUtil();
		}
		return _instance;
	}
  
}