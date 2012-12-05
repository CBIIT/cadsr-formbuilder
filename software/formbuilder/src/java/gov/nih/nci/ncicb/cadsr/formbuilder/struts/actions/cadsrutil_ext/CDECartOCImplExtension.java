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

public class CDECartOCImplExtension extends gov.nih.nci.ncicb.cadsr.objectCart.impl.CDECartOCImpl implements CDECart, Serializable  {

	private static Log log = LogFactory.getLog(CDECartOCImplExtension.class.getName());

	public static final String transformToConvertCartToDTO = "/transforms/ConvertFormCartV2ToDTO.xsl";  // consider changing to a non-versioned name

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

				// read the new format XML from the cart object and convert to (partial) old cart XML (i.e. serialized FormTransferObject)
				if (FormCartOptionsUtil.instance().dumpXMLDuringDebug())
					log.debug("XML from cart: " + f.getData());
				Source xmlInput = new StreamSource(new StringReader(f.getData()));	
				ByteArrayOutputStream xmlOutputStream = new ByteArrayOutputStream();  
				Result xmlOutput = new StreamResult(xmlOutputStream);
				try {
					transformer.transform(xmlInput, xmlOutput);
				} catch (TransformerException e) {
					log.error("TransformerException loading forms", e);
				}	
				if (FormCartOptionsUtil.instance().dumpXMLDuringDebug())
					log.debug("Converted XML: " + xmlOutputStream.toString());


				// convert the serialized FormTransferObject back into an actual FormTransferObject
				Object pOb = new Object();
				try {		
					StringReader reader = new StringReader(xmlOutputStream.toString());
					pOb = Unmarshaller.unmarshal(FormTransferObject.class, reader);		

				} catch (MarshalException ex) {
					log.error("MarshalException loading forms", ex);	
				} catch (ValidationException ex) {
					log.error("ValidationException loading forms", ex);	
				}
				log.debug("Trying to convert object pointer to FormTransferObject...");		
				FormTransferObject FTO = (FormTransferObject)pOb;
				if (FormCartOptionsUtil.instance().dumpXMLDuringDebug())
					log.debug("FormTransferObject: " + FTO.toString());


				// new format doesn't include idseq so it's empty, we need it for the UI so set it here
				String idseq = formBuilderService.getIdseq(FTO.getPublicId(), FTO.getVersion());
				FTO.setIdseq(idseq);

				itemList.add(FTO);
				log.debug("Loaded " + FTO.getIdseq());			
			}

			return itemList;		

		} catch (ObjectCartException oce) {
			oce.printStackTrace();
			throw new RuntimeException("getForms: Error loading forms", oce);
		}
	}	

}
