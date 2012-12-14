package gov.nih.nci.ncicb.cadsr.formbuilder.struts.actions.cadsrutil_ext;

// This class extends the previous objectCart code from cadsrutil and allows it to retrieve forms that are in the new forCartV2 format 

import gov.nih.nci.ncicb.cadsr.objectCart.impl.CDECartOCImpl;

import gov.nih.nci.ncicb.cadsr.formbuilder.service.FormBuilderServiceDelegate;
import gov.nih.nci.ncicb.cadsr.formbuilder.service.ServiceDelegateFactory;
import gov.nih.nci.ncicb.cadsr.formbuilder.service.ServiceStartupException;
import gov.nih.nci.ncicb.cadsr.common.formbuilder.common.FormBuilderConstants;

import gov.nih.nci.cadsr.domain.Form;
import gov.nih.nci.ncicb.cadsr.common.dto.FormTransferObject;
import gov.nih.nci.ncicb.cadsr.objectCart.CDECart;
import gov.nih.nci.ncicb.cadsr.objectCart.CDECartItem;
import gov.nih.nci.ncicb.cadsr.objectCart.CDECartItemComparator;
import gov.nih.nci.ncicb.cadsr.objectCart.CDECartItemTransferObject;
import gov.nih.nci.objectCart.client.ObjectCartClient;
import gov.nih.nci.objectCart.client.ObjectCartException;
import gov.nih.nci.objectCart.domain.Cart;
import gov.nih.nci.objectCart.domain.CartObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import gov.nih.nci.ncicb.cadsr.common.util.logging.Log;
import gov.nih.nci.ncicb.cadsr.common.util.logging.LogFactory;
import gov.nih.nci.ncicb.cadsr.common.dto.ContextTransferObject;

import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.ValidationException;
import javax.xml.transform.*;
import javax.xml.transform.stream.*;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.StringReader;

import net.sf.saxon.TransformerFactoryImpl;

import gov.nih.nci.ncicb.cadsr.formbuilder.struts.common.FormConverterUtil;
import gov.nih.nci.ncicb.cadsr.formbuilder.common.FormCartOptionsUtil;
import gov.nih.nci.ncicb.cadsr.common.exception.DMLException;



public class CDECartOCImplExtension extends gov.nih.nci.ncicb.cadsr.objectCart.impl.CDECartOCImpl implements CDECart, Serializable  {

	private static Log log = LogFactory.getLog(CDECartOCImplExtension.class.getName());

	public static final String transformToConvertCartToDTO = "/transforms/ConvertFormCartV2ToDTO.xsl";  // consider changing to a non-versioned name
	public static final String formNotInDatabaseLongNamePrefix = "NOT IN DATABASE: "; 
	
	protected FormBuilderServiceDelegate formBuilderService;

	public CDECartOCImplExtension(ObjectCartClient client, String uid, String cName, FormBuilderServiceDelegate formBuilderServiceDelegate) {
		super(client, uid, cName);
		formBuilderService = formBuilderServiceDelegate;
	}

	public Collection getForms() {

		log.debug("cartClient " + cartClient + " oCart " + oCart);
		log.debug("cart id " + oCart.getId());

		try {
			Collection<CartObject> newFormCartElements = cartClient.getObjectsByType(oCart, FormConverterUtil.instance().getCartObjectType());
			log.debug("newFormCartElements has " + newFormCartElements.size() + " elements");

			List itemList = new ArrayList();

			// create the transformer 
			InputStream xslStream = this.getClass().getResourceAsStream(transformToConvertCartToDTO);
			StreamSource xslSource = new StreamSource(xslStream);
			Transformer transformer = null;
			try {
				transformer = net.sf.saxon.TransformerFactoryImpl.newInstance().newTransformer(xslSource);
			} catch (TransformerException e) {
				log.error("TransformerException creating transformer", e);
			}	
			log.debug("created transformer");

			for (CartObject f: newFormCartElements) {

				// exception handling is arranged so loading will continue for other objects if there are bad objects
				try {
					// read the new format XML from the cart object and convert to (partial) old cart XML (i.e. serialized FormTransferObject)
					if (FormCartOptionsUtil.instance().dumpXMLDuringDebug())
						log.debug("XML from cart: " + f.getData());
					Source xmlInput = new StreamSource(new StringReader(f.getData()));	
					ByteArrayOutputStream xmlOutputStream = new ByteArrayOutputStream();  
					Result xmlOutput = new StreamResult(xmlOutputStream);
					transformer.transform(xmlInput, xmlOutput);
					if (FormCartOptionsUtil.instance().dumpXMLDuringDebug())
						log.debug("Converted XML: " + xmlOutputStream.toString());
	
					// convert the serialized FormTransferObject back into an actual FormTransferObject
					Object pOb = new Object();
					StringReader reader = new StringReader(xmlOutputStream.toString());
					pOb = Unmarshaller.unmarshal(FormTransferObject.class, reader);		
	
					log.debug("Trying to convert object pointer to FormTransferObject...");		
					FormTransferObject FTO = (FormTransferObject)pOb;
					if (FormCartOptionsUtil.instance().dumpXMLDuringDebug())
						log.debug("FormTransferObject: " + FTO.toString());
	
					String idseq = formBuilderService.getIdseq(FTO.getPublicId(), FTO.getVersion());
					FTO.setIdseq(idseq);
					// We work off idseq for form operations, so we can't do much if the idseq wasn't found.
					// We'll show the form with a prefix to let the user know something is wrong.
					if (idseq.length() == 0) {
			    		log.info("Form " + FTO.getPublicId() + " " + FTO.getVersion() + " in cart not found in database");
			    		FTO.setLongName(formNotInDatabaseLongNamePrefix + FTO.getLongName());
					}
	
					itemList.add(FTO);
					log.debug("Loaded " + FTO.getIdseq());
					
				} catch (TransformerException e) {
					log.error("TransformerException loading forms", e);
				} catch (MarshalException e) {
					log.error("MarshalException loading forms", e);	
				} catch (ValidationException e) {
					log.error("ValidationException loading forms", e);	
				}
				
			}

			return itemList;		

		} catch (ObjectCartException oce) {
			oce.printStackTrace();
			throw new RuntimeException("getForms: Error loading forms", oce);
		}
	}	

}
