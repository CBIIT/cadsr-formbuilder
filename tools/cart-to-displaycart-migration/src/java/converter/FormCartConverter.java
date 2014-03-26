/*L
 * Copyright Oracle Inc, ScenPro Inc, SAIC-F
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-formbuilder/LICENSE.txt for details.
 */

package converter;


import org.apache.log4j.Logger;

import gov.nih.nci.ncicb.cadsr.common.CaDSRConstants;
import gov.nih.nci.ncicb.cadsr.common.resource.Form;
import gov.nih.nci.ncicb.cadsr.common.resource.FormV2;
import gov.nih.nci.ncicb.cadsr.objectCart.CDECart;
import gov.nih.nci.ncicb.cadsr.objectCart.CDECartItem;
import gov.nih.nci.ncicb.cadsr.objectCart.impl.CDECartOCImpl;
import gov.nih.nci.objectCart.client.ObjectCartClient;
import gov.nih.nci.objectCart.client.ObjectCartException;
import gov.nih.nci.objectCart.domain.CartObject;
import gov.nih.nci.system.applicationservice.ApplicationException;
import gov.nih.nci.system.client.ApplicationServiceProvider;
import gov.nih.nci.objectCart.applicationService.ObjectCartService;
import gov.nih.nci.objectCart.domain.Cart;
import gov.nih.nci.ncicb.cadsr.formbuilder.common.FormBuilderException;

import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.ValidationException;

import javax.xml.transform.TransformerException;

import gov.nih.nci.ncicb.cadsr.formbuilder.ejb.FormBuilder;
import gov.nih.nci.ncicb.cadsr.formbuilder.ejb.FormBuilderHome;

import javax.naming.InitialContext;
import javax.naming.Context;

import java.util.Properties;
import java.rmi.RemoteException;

import javax.rmi.PortableRemoteObject;

import org.jnp.interfaces.NamingContextFactory;

import gov.nih.nci.ncicb.cadsr.formbuilder.struts.actions.FormDisplayCartTransferObject;
import gov.nih.nci.ncicb.cadsr.formbuilder.struts.common.FormConverterUtil;

import java.util.List;
import java.util.Collection;
import java.util.LinkedList;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

public class FormCartConverter {

	private static final Logger _logger = Logger.getLogger(FormCartConverter.class);
	
	private String objectCartURL;
	private ObjectCartService cartService;
	private ObjectCartClient cartClient;
	private FormBuilder formBuilderService;
	
	private int exceptionsWhileClearingExisting = 0;
	private int exceptionsWhileConverting = 0;
	private int cartsConverted = 0;
	
	// Use these flag to limit changes (and anything that creates a lot of logging)  
	private boolean limitToGuest; // limit changes to the guest cart (if changes are allowed)
	private boolean noChanges;

	public FormCartConverter(String formbuilderURL, String ocURL, boolean limit, boolean guestOnly) {
		try {
			objectCartURL = ocURL;
			noChanges = limit;
			limitToGuest = guestOnly;
			
			cartService = (ObjectCartService) ApplicationServiceProvider
			.getApplicationServiceFromUrl(objectCartURL, "objectCartServiceInfo");			
			_logger.debug("created ObjectCartService using URL and objectCartServiceInfo");

			cartClient = new ObjectCartClient(objectCartURL);
			_logger.debug("created ObjectCartClient using URL " + objectCartURL);
			
			String affectedCarts = "no carts";
			if (!noChanges)
				affectedCarts = limitToGuest ? "the guest cart" : "all carts";
			_logger.info("FormCartConverter created using URL " + objectCartURL + " operating on " + affectedCarts);
			
		} catch (Exception e) {
			throw new RuntimeException("FormCartConverterfailed to create ObjectCart service/client ", e);
		}
		
		try {
			Properties env = new Properties();
			env.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
			env.put(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");		
			env.put(Context.PROVIDER_URL, formbuilderURL);
			// Form Builder EJB doesn't seem to have security
			env.put(Context.SECURITY_PRINCIPAL, "dummy-username");
			env.put(Context.SECURITY_CREDENTIALS, "dummy-password");
			Context context = new InitialContext(env);
			Object remote = context.lookup("FormBuilder");
			FormBuilderHome home = (FormBuilderHome)PortableRemoteObject.narrow(remote,FormBuilderHome.class);	
			formBuilderService = home.create();
			String testConnection = formBuilderService.getIdseq((int)1, (float)0.1); // ("" instead of exception when no matching form)
			_logger.info("testConnection returned: " + testConnection);
			_logger.info("Form Builder service created using URL " + formbuilderURL);
		} catch (Exception e) {
			throw new RuntimeException("FormCartConverter failed to create FormBuilder service ", e);
		}
				
	}

	public static void main(String[] args_) {

		try
		{
			_logger.info("started main FormCartConverter");

			int argsCount = args_.length;
			if (argsCount < 2 || argsCount > 3) {
				_logger.info("Incorrect number of arguments.");				
				_logger.info("First argument is the Form Builder URL for EJB use.");				
				_logger.info("Second argument is the object cart url.");
				_logger.info("Optional third argument may be blank, 'guest', or 'all' to specify which carts to operate on (blank is none)");
				_logger.info("e.g.: java -Xmx512M -jar FormCartConverter.jar jnp://127.0.0.1:1099 https://objcart-dev.nci.nih.gov/objcart10/ all");
			} else {
			
				String formbuilderURL = args_[0];
				String ocURL = args_[1];
				
				boolean limit = true;
				boolean guestOnly = true;
				if (argsCount == 3 && args_[2].equalsIgnoreCase("guest")) {
					limit = false;
					guestOnly = true;
				}
				if (argsCount == 3 && args_[2].equalsIgnoreCase("all")) {
					limit = false;
					guestOnly = false;
				}
				
				FormCartConverter converter = new FormCartConverter(formbuilderURL, ocURL, limit, guestOnly);
				convertToDisplayCarts(converter, CaDSRConstants.FORMS_DISPLAY_CART);
				convertToDisplayCarts(converter, CaDSRConstants.FORMS_DISPLAY_CART2);
			}
			
		}
		catch (Exception ex)
		{
			_logger.fatal(ex.toString(), ex);
		}

	}
	
	private void convertToDisplayCarts(FormCartConverter converter, String cartName)
	{
		int clearingErrors = converter.clearExistingFormDisplayCarts(cartName);
		if (clearingErrors == 0) {
			int conversionErrors = converter.createFormDisplayCarts(cartName);
			if (conversionErrors == 0) {
				_logger.info("Conversion completed with no exceptions.");
				_logger.info(converter.getNumberOfCartsConverted() + " carts converted.");
			} else {
				_logger.info("Conversion completed with " + conversionErrors + " conversion exceptions.");
				_logger.info(converter.getNumberOfCartsConverted() + " carts converted.");
				_logger.info("Check logs.");
			}
			
		} else {
			_logger.info(clearingErrors + " exceptions occurred while clearing existing V2 form carts.");
			_logger.info("Processing stopped before running V1 -> V2 conversions.");				
			_logger.info("Check logs.");				
		}
	}


	private int clearExistingFormDisplayCarts(String cartName) {

		try {
			List<Cart> existingNewCarts = cartService.getCartsByName(cartName);					
			_logger.debug(existingNewCarts.size() + " existing formDisplay carts");


			for (Cart cart : existingNewCarts) {

				try {
					_logger.debug("cart " + cart.getId() + " " + cart.getUserId() + " " + cart.getName());

					// get the current formCartDisplay elements
					Collection<CartObject> cartElements = cart.getCartObjectCollection();
					_logger.debug("  " + cartElements.size() + " forms");

					// log the existing elements
					for (CartObject cartObject : cartElements) {
						if (!noChanges && (!limitToGuest || cart.getUserId().equalsIgnoreCase("guest")))
							dumpCart(cartObject);
					}

					// clear the existing elements
					if (cartElements.size() > 0) {
						_logger.debug("emptying cart");
						if (!noChanges && (!limitToGuest || cart.getUserId().equalsIgnoreCase("guest")))
							cartClient.removeObjectCollection(cart, cartElements);
						cartElements = cart.getCartObjectCollection();
						_logger.debug("checking size of cart " + cart.getUserId() + " after emptying..." + cartElements.size());
					}
				} catch (Exception e) {
					exceptionsWhileClearingExisting++;
					_logger.debug("  exception clearing cart: " + e.getMessage());
				}	

			}

		} catch (Exception e) {
			throw new RuntimeException("exception in clearExistingFormDisplayCarts ", e);
		}
		
		return exceptionsWhileClearingExisting;
	}


	private int createFormDisplayCarts(String cartName) {

		try {
			List<Cart> existingOldCarts = null;
			if (cartName == CaDSRConstants.FORMS_DISPLAY_CART)
				{
					existingOldCarts = cartService.getCartsByName(CaDSRConstants.FORMS_CART);
				}
			else if (cartName == CaDSRConstants.FORMS_DISPLAY_CART2)
				{
					existingOldCarts = cartService.getCartsByName(CaDSRConstants.FORMS_CART_V2);
				}
			_logger.debug(existingOldCarts.size() + " existing formCart carts");


			for (Cart cart : existingOldCarts) {

				try {
					_logger.debug("cart " + cart.getId() + " " + cart.getUserId() + " " + cart.getName());

					CDECart oldCart = null;
					if (cartName == CaDSRConstants.FORMS_DISPLAY_CART)
						{
							oldCart = new CDECartOCImpl(cartClient, cart.getUserId(), CaDSRConstants.FORMS_CART);
						}
					else if (cartName == CaDSRConstants.FORMS_DISPLAY_CART2)
						{
							oldCart = new CDECartOCImpl(cartClient, cart.getUserId(), CaDSRConstants.FORMS_CART_V2);
						}
					Collection formCollection = oldCart.getForms();
					_logger.debug(" contains " + formCollection.size() + " forms");

					if (!noChanges && (!limitToGuest || cart.getUserId().equalsIgnoreCase("guest"))) {
						addFormsToDisplayCart(cartName, formCollection, cart.getUserId());
						cartsConverted++;
					}

				} catch (Exception e) {
					exceptionsWhileConverting++;
					_logger.debug("  exception converting cart: msg: " + e.getMessage() + " toString: " + e.toString());			
				}	

			}

		} catch (Exception e) {
			throw new RuntimeException("exception in create displayCarts ", e);
		}
		
		return exceptionsWhileConverting;
	}


	private void dumpCart(CartObject cartObject){
		_logger.debug("   " + cartObject.getId());
		_logger.debug("   " + cartObject.getNativeId());
		_logger.debug("   " + cartObject.getRelatedId());
		_logger.debug("   " + cartObject.getType());
		_logger.debug("   " + cartObject.getDateAdded());
		_logger.debug("   " + cartObject.getDisplayText());
		_logger.debug("   " + cartObject.getData());
	}


	private Cart addFormsToDisplayCart(String cartName, Collection forms, String userId) throws ObjectCartException, FormBuilderException, RemoteException, MarshalException, ValidationException, TransformerException {
		// Loosely based on FormAction addFormToCart
		// We don't check for duplicates -- we assume the FORMS_DISPLAY_CART cart is empty and that added forms don't include duplicates 	

		_logger.debug(" adding forms to displayCart ..."); 

		Cart displayCart = cartClient.createCart(userId, cartName);

		Collection cartObjects = new ArrayList();
		for (Object f: forms)
			cartObjects.add(convertToDisplayItem((Form)f));

		displayCart = cartClient.mergeElements(cartObjects);
		
		_logger.debug("  ...done adding forms to displayCart cart for user " + userId); 		
		return displayCart;
	}

	public FormDisplayCartTransferObject convertToDisplayItem(Form crf)
	{
		FormDisplayCartTransferObject displayObject = new FormDisplayCartTransferObject();
		displayObject.setAslName(crf.getAslName());
		displayObject.setContextName(crf.getContext().getName());
		displayObject.setFormType(crf.getFormType());
		displayObject.setIdseq(crf.getIdseq());
		displayObject.setLongName(crf.getLongName());
		displayObject.setProtocols(crf.getProtocols());
		displayObject.setPublicId(crf.getPublicId());
		displayObject.setVersion(crf.getVersion());
		return displayObject;
	}

	public int getNumberOfCartsConverted() {
		return cartsConverted;
	}
}
