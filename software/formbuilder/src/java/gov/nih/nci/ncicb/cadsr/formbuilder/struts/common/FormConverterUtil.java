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
	public static final String stripEmptyNodesXSL = "/transforms/remove-empty-nodes.xsl";
	protected Transformer transformerV1ToV2 = null;
	protected Transformer transformerStripEmpty = null;
	

	public String getCartObjectType() {
		return FormCartOptionsUtil.instance().xsdLocation();
	}

	private String convertToV2(FormV2 crf) throws MarshalException, ValidationException, TransformerException
		{
			// Start with our standard conversion to xml (in V1 format)
			StringWriter writer = new StringWriter();
			try {
				Marshaller.marshal(crf, writer);
			} catch (MarshalException ex) {
				log.debug("FormV2 " + crf);
				throw ex;
			} catch (ValidationException ex) {
				// need exception handling	
				log.debug("FormV2 " + crf);
				throw ex;
			}
			
			// Now use our transformer to create V2 format
			Source xmlInput = new StreamSource(new StringReader(writer.toString()));
			ByteArrayOutputStream xmlOutputStream = new ByteArrayOutputStream();  
			Result xmlOutput = new StreamResult(xmlOutputStream);
			try {
				transformerV1ToV2.transform(xmlInput, xmlOutput);
			} catch (TransformerException e) {
				log.debug(writer.toString());
				throw e;
			}	
			
			String V2XML = xmlOutputStream.toString();
			return V2XML;
		}
	
	public String convertFormToV2 (FormV2 crf) throws MarshalException, ValidationException, TransformerException 
		{
			Source xmlInputV2Forms = new StreamSource(new StringReader(convertToV2(crf)));
			ByteArrayOutputStream xmlOutputStreamStripEmpty = new ByteArrayOutputStream();  
			Result xmlOutputStripEmpty = new StreamResult(xmlOutputStreamStripEmpty);
			
			try {
				// Strip empty nodes from the transformed v2 form xml file
				transformerStripEmpty.transform(xmlInputV2Forms, xmlOutputStripEmpty);
			} catch (TransformerException e) {
				log.debug(xmlInputV2Forms.toString());
				throw e;
			}	
					
			String V2XML = xmlOutputStreamStripEmpty.toString();
			return V2XML;
		}
		
	protected FormConverterUtil() {
		
		StreamSource xslSource = null;
		StreamSource xslSourceStripEmpty = null;
		try {
			InputStream xslStream = this.getClass().getResourceAsStream(V1ExtendedToV2XSL);
			xslSource = new StreamSource(xslStream);
			InputStream xslStreamRemoveEmptyNodes = this.getClass().getResourceAsStream(stripEmptyNodesXSL); 
			xslSourceStripEmpty = new StreamSource(xslStreamRemoveEmptyNodes);
		}
		catch(Exception e) {
			System.out.println("FormConverterUtil error loading conversion xsl: " + V1ExtendedToV2XSL + " OR " + stripEmptyNodesXSL + " exc: "+ e);
		}
		
		try {
			log.debug("creating transformerV1ToV2");			
			transformerV1ToV2 = net.sf.saxon.TransformerFactoryImpl.newInstance().newTransformer(xslSource);
			log.debug("creating transformerStripEmpty");
			transformerStripEmpty  = net.sf.saxon.TransformerFactoryImpl.newInstance().newTransformer(xslSourceStripEmpty);
		} catch (TransformerException e) {
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