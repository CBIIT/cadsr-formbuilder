package gov.nih.nci.ncicb.cadsr.formbuilder.struts.actions.cadsrutil_ext;

// This class extends the previous objectCart code from cadsrutil and provides additional functionality needed for V2 forms
// Since getForms was only used for display, an alternate mechanism providing display objects for V2 forms is provided 
// Caching is provided for the display objects so repeated calls are more efficient
// Note: There is no automatic mechanism for ensuring the cache is in sync with the forms.
// setFormDisplayObjects should be explicitly called before displaying the cart.

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

import gov.nih.nci.ncicb.cadsr.formbuilder.struts.common.FormCartDisplayObject;

public class CDECartOCImplExtension extends gov.nih.nci.ncicb.cadsr.objectCart.impl.CDECartOCImpl implements CDECart, Serializable  {

	private static Log log = LogFactory.getLog(CDECartOCImplExtension.class.getName());

	public static final String transformToConvertCartToDisplayObject = "/transforms/ConvertFormCartV2ToDisplayObject.xsl";
	public static final String formNotInDatabaseLongNamePrefix = "NOT IN DATABASE: "; 
	
	protected FormBuilderServiceDelegate formBuilderService;
	protected Collection formDisplayObjects;
	
	public CDECartOCImplExtension(ObjectCartClient client, String uid, String cName, FormBuilderServiceDelegate formBuilderServiceDelegate) {
		super(client, uid, cName);
		formBuilderService = formBuilderServiceDelegate;
	}

	public void setFormDisplayObjects() {
		log.debug("setFormDisplayObjects");
		log.debug("cartClient " + cartClient + " oCart " + oCart);
		log.debug("cart id " + oCart.getId());

		try {
			Collection<CartObject> newFormCartElements = cartClient.getObjectsByType(oCart, FormConverterUtil.instance().getCartObjectType());
			log.debug("newFormCartElements has " + newFormCartElements.size() + " elements");

			List itemList = new ArrayList();

			// create the transformer 
			InputStream xslStream = this.getClass().getResourceAsStream(transformToConvertCartToDisplayObject);
						
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
	
					// convert the serialized FormCartDisplayObject into an actual FormCartDisplayObject
					Object pOb = new Object();
					StringReader reader = new StringReader(xmlOutputStream.toString());
					pOb = Unmarshaller.unmarshal(FormCartDisplayObject.class, reader);		
	
					log.debug("Trying to convert object pointer to FormCartDisplayObject...");		
					FormCartDisplayObject FCDO = (FormCartDisplayObject)pOb;
					if (FormCartOptionsUtil.instance().dumpXMLDuringDebug())
						log.debug("FormCartDisplayObject: " + FCDO.toString());
	
					// new form cart data doesn't include idseq, get it from the cart native id
					String idseq = f.getNativeId();
					FCDO.setIdseq(idseq);
					
					// check whether the form exists in database and show a warning prefix on the name if it doesn't 
					String databaseidseq = formBuilderService.getIdseq(FCDO.getPublicId(), FCDO.getVersion());

					if (databaseidseq.length() == 0) {
			    		log.info("Form " + FCDO.getPublicId() + " " + FCDO.getVersion() + " in cart not found in database");
			    		FCDO.setLongName(formNotInDatabaseLongNamePrefix + FCDO.getLongName());   		
					}
	
					itemList.add(FCDO);
					log.debug("Loaded " + FCDO.getIdseq());
					
				} catch (TransformerException e) {
					log.error("TransformerException loading forms", e);
				} catch (MarshalException e) {
					log.error("MarshalException loading forms", e);	
				} catch (ValidationException e) {
					log.error("ValidationException loading forms", e);	
				}
				
			}

			formDisplayObjects = itemList;		

		} catch (ObjectCartException oce) {
			oce.printStackTrace();
			throw new RuntimeException("getForms: Error loading forms", oce);
		}
	}
	
	public Collection getFormDisplayObjects() {
		log.debug("getFormDisplayObjects " + formDisplayObjects.size() + " objects");		
		return formDisplayObjects;
	}
	
	// need access to some generic (non-typed, non-POJO) cart functions 
	public void addObjectCollection(Collection<CartObject> cartObjects) throws gov.nih.nci.objectCart.client.ObjectCartException {
		oCart = cartClient.storeObjectCollection(oCart, cartObjects);		
	}
	
}
