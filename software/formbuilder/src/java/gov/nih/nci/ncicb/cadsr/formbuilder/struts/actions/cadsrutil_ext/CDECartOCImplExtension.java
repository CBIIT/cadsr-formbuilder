package gov.nih.nci.ncicb.cadsr.formbuilder.struts.actions.cadsrutil_ext;

import gov.nih.nci.ncicb.cadsr.objectCart.impl.CDECartOCImpl;

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
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;


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


public class CDECartOCImplExtension extends gov.nih.nci.ncicb.cadsr.objectCart.impl.CDECartOCImpl implements CDECart, Serializable  {

	private static Log log = LogFactory.getLog(CDECartOCImplExtension.class.getName());
	
	public CDECartOCImplExtension(ObjectCartClient client, String uid, String cName) {
		super(client, uid, cName);
	}
	
	public Collection getForms() {
		log.debug("CDECartOCImplExtension getForms this = " + this);		
		return getElementsV2(FormTransferObject.class);
	}


	private Collection getElementsV2(Class type) {
		try {
			List itemList = null;
///can't use FormBuilder code here	
//			if (type != FormTransferObject.class || FormCartHandlingOptionsUtil.instance().readInV1Format()) {
			if (type != FormTransferObject.class) {						

				Collection cartElements = cartClient.getObjectsByType(oCart, type);
				if (cartElements != null){
					Collection items = ObjectCartClient.getPOJOCollection(type, cartElements);
					itemList = new ArrayList(items);
					Collections.sort(itemList,getComparator(type));
				return itemList;
				} else {
					itemList = new ArrayList();
					return itemList;
				}
			}
			
// hacking away to test things out...			
// now lets add in our new forms
			
if (log.isDebugEnabled()) {log.debug("Trying new objects, passed in class is " + FormTransferObject.class + " in CDECartOCImpl " + this);}
///can't use FormBuilder code here	
//if (type == FormTransferObject.class && FormCartHandlingOptionsUtil.instance().readInV2Format())
if (type == FormTransferObject.class ) {
	log.debug("cartClient " + cartClient + " oCart " + oCart);
	log.debug("cart id " + oCart.getId());	
	Collection<CartObject> newFormCartElements = cartClient.getObjectsByType(oCart, ":Test:my new type");
	if (log.isDebugEnabled()) {log.debug("newFormCartElements has " + newFormCartElements.size() + " elements");}

	itemList = new ArrayList();
	
	InputStream xslStream = this.getClass().getResourceAsStream("/transforms/ConvertFormCartV2ToDTO.xsl");  // we need to change to a non-versioned name
	StreamSource xslSource = new StreamSource(xslStream);
		Transformer transformer = null;
		try {
		    transformer = TransformerFactory.newInstance().newTransformer(xslSource);
		} catch (TransformerException e) {
// Handle.
if (log.isDebugEnabled()) {log.debug("Tranformer exception");}
		}	
if (log.isDebugEnabled()) {log.debug("created transformer");}
		
	for (CartObject f: newFormCartElements) {
		
		
		Source xmlInput = new StreamSource(new StringReader(f.getData()));	
		ByteArrayOutputStream xmlOutputStream = new ByteArrayOutputStream();  
		Result xmlOutput = new StreamResult(xmlOutputStream);

if (log.isDebugEnabled()) {log.debug("xml from cart: " + f.getData());}		
		try {
		    transformer.transform(xmlInput, xmlOutput);
		} catch (TransformerException e) {
// Handle.
		}	

if (log.isDebugEnabled()) {log.debug("transformed xml from cart: " + xmlOutputStream.toString());}		
		
		Object pOb = new Object();
		// need exception handling		
		try {		
			StringReader reader = new StringReader(xmlOutputStream.toString());
			pOb = Unmarshaller.unmarshal(FormTransferObject.class, reader);		
			
		} catch (MarshalException ex) {
if (log.isDebugEnabled()) log.debug("MarshalException: " + ex.getMessage());			
		} catch (ValidationException ex) {
if (log.isDebugEnabled()) log.debug("ValidationException");			
		}
if (log.isDebugEnabled()) {log.debug("Trying to convert object pointer to FormTransferObject...");}		
FormTransferObject FTO2 = (FormTransferObject)pOb;
if (log.isDebugEnabled()) {log.debug("FormTransferObject " + FTO2.toString());}

// set idseq here

		itemList.add(pOb);

	}
	return itemList;	
}
return itemList;		

		} catch (ObjectCartException oce) {
			oce.printStackTrace();
			throw new RuntimeException("getElements: Error restoring the POJO Collection", oce);
		}
	}	
	
}
