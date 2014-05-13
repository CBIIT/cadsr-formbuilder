/*L
 * Copyright Oracle Inc, ScenPro Inc, SAIC-F
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-formbuilder/LICENSE.txt for details.
 */

package converter;

// watch out for certs being installed and using correct (1.5) version of Java
// needs a bit of memory for intial list of carts  (tested with -Xmx512M)

import org.apache.log4j.Logger;

import gov.nih.nci.ncicb.cadsr.common.CaDSRConstants;
import gov.nih.nci.ncicb.cadsr.common.dto.FormTransferObject;
import gov.nih.nci.ncicb.cadsr.common.resource.Form;
import gov.nih.nci.ncicb.cadsr.common.resource.FormV2;
import gov.nih.nci.ncicb.cadsr.objectCart.CDECart;
import gov.nih.nci.ncicb.cadsr.objectCart.CDECartItem;
import gov.nih.nci.ncicb.cadsr.objectCart.FormDisplayCartTransferObject;
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

import gov.nih.nci.ncicb.cadsr.formbuilder.struts.common.FormConverterUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
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
	private boolean perCart;
	private String cartName;

	public FormCartConverter(String formbuilderURL, String ocURL, boolean limit, boolean guestOnly, boolean percart, String cartId) {
		try {
			objectCartURL = ocURL;
			noChanges = limit;
			limitToGuest = guestOnly;
			cartName = cartId;
			perCart = percart;
			
			cartService = (ObjectCartService) ApplicationServiceProvider
			.getApplicationServiceFromUrl(objectCartURL, "objectCartServiceInfo");			
			_logger.debug("created ObjectCartService using URL and objectCartServiceInfo");

			cartClient = new ObjectCartClient(objectCartURL);
			_logger.debug("created ObjectCartClient using URL " + objectCartURL);
			
			String affectedCarts = "no carts";
			if ((cartName.contentEquals("all")) || (cartName.contentEquals("guest")))
				{
					if (!noChanges)
						affectedCarts = limitToGuest ? "the guest cart" : "all carts";
				}
			else 
				affectedCarts = cartName;
			
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
				boolean onlycart = false;
				String cartId = null;
				if (argsCount == 3 && args_[2].equalsIgnoreCase("guest")) {
					limit = false;
					guestOnly = true;
				}
				else if (argsCount == 3 && args_[2].equalsIgnoreCase("all")) {
					limit = false;
					guestOnly = false;
				}
				else if (argsCount == 3) {
					limit = true;
					guestOnly = false;
					cartId = args_[2];
					onlycart = true;
				}
				
				FormCartConverter converter = new FormCartConverter(formbuilderURL, ocURL, limit, guestOnly, onlycart, cartId);
				
				int clearingErrors = converter.clearExistingCarts(CaDSRConstants.FORMS_CART_V2);
				if (clearingErrors == 0) {
					int conversionErrors = converter.createV2FormCarts();
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
				

				clearingErrors = converter.clearExistingCarts(CaDSRConstants.FORMS_DISPLAY_CART);
				if (clearingErrors == 0) {
					int conversionErrors = converter.createDisplayCarts(CaDSRConstants.FORMS_DISPLAY_CART);
					if (conversionErrors == 0) {
						_logger.info("Conversion completed with no exceptions.");
						_logger.info(converter.getNumberOfCartsConverted() + " carts converted.");
					} else {
						_logger.info("Conversion completed with " + conversionErrors + " conversion exceptions.");
						_logger.info(converter.getNumberOfCartsConverted() + " carts converted.");
						_logger.info("Check logs.");
					}
					
				} else {
					_logger.info(clearingErrors + " exceptions occurred while clearing existing Display form carts.");
					_logger.info("Processing stopped before running V1 Display cart conversions.");				
					_logger.info("Check logs.");				
				}
				
				clearingErrors = converter.clearExistingCarts(CaDSRConstants.FORMS_DISPLAY_CART2);
				if (clearingErrors == 0) {
					int conversionErrors = converter.createDisplayCarts(CaDSRConstants.FORMS_DISPLAY_CART2);
					if (conversionErrors == 0) {
						_logger.info("Conversion completed with no exceptions.");
						_logger.info(converter.getNumberOfCartsConverted() + " carts converted.");
					} else {
						_logger.info("Conversion completed with " + conversionErrors + " conversion exceptions.");
						_logger.info(converter.getNumberOfCartsConverted() + " carts converted.");
						_logger.info("Check logs.");
					}
					
				} else {
					_logger.info(clearingErrors + " exceptions occurred while clearing existing Display form carts.");
					_logger.info("Processing stopped before running V2 Display cart conversions.");				
					_logger.info("Check logs.");				
				}
			}
			
		}
		catch (Exception ex)
		{
			_logger.fatal(ex.toString(), ex);
		}

	}


	private int clearExistingCarts(String cartname) {

		try {
			List<Cart> existingNewCarts = cartService.getCartsByName(cartname);					
			_logger.debug(existingNewCarts.size() + " existing " + cartname + " carts");


			for (Cart cart : existingNewCarts) {

				try {
					_logger.debug("cart " + cart.getId() + " " + cart.getUserId() + " " + cart.getName());

					// get the current formCartV2 elements
					Collection<CartObject> cartElements = cart.getCartObjectCollection();
					_logger.debug("  " + cartElements.size() + " forms");

					// log the existing elements
					for (CartObject cartObject : cartElements) {
						if ((perCart && cart.getUserId().equalsIgnoreCase(cartName.toString())) ||
						   (!noChanges && (!limitToGuest || cart.getUserId().equalsIgnoreCase("guest"))))
							dumpCart(cartObject);
					}

					// clear the existing elements
					if (cartElements.size() > 0) {
						_logger.debug("emptying cart");
						if ((perCart && cart.getUserId().equalsIgnoreCase(cartName.toString())) ||
							(!noChanges && (!limitToGuest || cart.getUserId().equalsIgnoreCase("guest"))))
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
			throw new RuntimeException("exception in clearExistingV2FormCarts ", e);
		}
		
		return exceptionsWhileClearingExisting;
	}
	

	private int createV2FormCarts() {

		try {
			List<Cart> existingOldCarts = cartService.getCartsByName(CaDSRConstants.FORMS_CART);					
			_logger.debug(existingOldCarts.size() + " existing formCart carts");


			for (Cart cart : existingOldCarts) {

				try {
					_logger.debug("cart " + cart.getId() + " " + cart.getUserId() + " " + cart.getName());

					CDECart oldCart = new CDECartOCImpl(cartClient, cart.getUserId(), CaDSRConstants.FORMS_CART);
					Collection formCollection = oldCart.getForms();
					_logger.debug(" contains " + formCollection.size() + " forms");

					if ((perCart && cart.getUserId().equalsIgnoreCase(cartName.toString())) ||
						(!noChanges && (!limitToGuest || cart.getUserId().equalsIgnoreCase("guest")))) {
						addFormsToCart(formCollection, cart.getUserId(), CaDSRConstants.FORMS_CART_V2);
						cartsConverted++;
					}

				} catch (Exception e) {
					exceptionsWhileConverting++;
					_logger.debug("  exception converting cart: msg: " + e.getMessage() + " toString: " + e.toString());			
				}	

			}

		} catch (Exception e) {
			throw new RuntimeException("exception in createV2FormCarts ", e);
		}
		
		return exceptionsWhileConverting;
	}
	
	private int createDisplayCarts(String displayCartName) {

		try {
			List<Cart> existingCarts = cartService.getCartsByName(CaDSRConstants.FORMS_CART);					
			_logger.debug(existingCarts.size() + " existing " + CaDSRConstants.FORMS_CART + " carts");


			for (Cart cart : existingCarts) {

				try {
					_logger.debug("cart " + cart.getId() + " " + cart.getUserId() + " " + cart.getName());

					CDECart v2Cart = new CDECartOCImpl(cartClient, cart.getUserId(), CaDSRConstants.FORMS_CART);
					Collection formCollection = v2Cart.getForms();
					_logger.debug(" contains " + formCollection.size() + " forms");

					if ((perCart && cart.getUserId().equalsIgnoreCase(cartName.toString())) ||
						(!noChanges && (!limitToGuest || cart.getUserId().equalsIgnoreCase("guest")))) {
						addFormsToCart(formCollection, cart.getUserId(), displayCartName);
						cartsConverted++;
					}

				} catch (Exception e) {
					exceptionsWhileConverting++;
					_logger.debug("  exception converting cart: msg: " + e.getMessage() + " toString: " + e.toString());			
				}	

			}

		} catch (Exception e) {
			throw new RuntimeException("exception in createV2FormCarts ", e);
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


	private Cart addFormsToCart(Collection forms, String userId, String cartName) throws ObjectCartException, FormBuilderException, RemoteException, MarshalException, ValidationException, TransformerException 
		{
			_logger.debug(" adding  to cart..."); 
	
			Cart cart = cartClient.createCart(userId, cartName);
			if (cartName.equals(CaDSRConstants.FORMS_CART_V2))
				{
					Collection<CartObject> cartObjects = new LinkedList<CartObject>();
					for (Object f: forms)
						{
							cartObjects.add(translateCartObject((Form)f));
						}
					cart = cartClient.storeObjectCollection(cart, cartObjects);
				}
			else
				{
					Map<String, String> objectDisplayNames = new HashMap<String, String> ();
					Map<String, Object>  objects = new HashMap<String, Object>();
					for (Object f: forms)
						{
							FormDisplayCartTransferObject item = translateToDisplayObject((Form)f);
							objectDisplayNames.put(item.getIdseq(), item.getLongName());
							objects.put(item.getIdseq(), item);
						}
					cart = cartClient.storePOJOCollection(cart, FormDisplayCartTransferObject.class, objectDisplayNames, objects);
				}
	
			
			_logger.debug("  ...done adding objects to cart for user " + userId); 		
			return cart;
		}

	
	private CartObject translateCartObject(Form crf) throws FormBuilderException, RemoteException, MarshalException, ValidationException, TransformerException 
		{
			// function copied from FormAction and then modified for formV2
			CartObject ob = new CartObject();
			ob.setType(FormConverterUtil.instance().getCartObjectType());
			ob.setDisplayText(Integer.toString(crf.getPublicId()) + "v" + Float.toString(crf.getVersion()));
			ob.setNativeId(crf.getFormIdseq());		
			FormV2 formV2 = formBuilderService.getFormDetailsV2(crf.getFormIdseq());	// fetching V2 form data from the database
			String convertedForm = FormConverterUtil.instance().convertFormToV2(formV2);
			ob.setData(convertedForm);
			return ob;
		}	
	
	public FormDisplayCartTransferObject translateToDisplayObject(Form form)
		{
			FormDisplayCartTransferObject displayObject = new FormDisplayCartTransferObject();
			displayObject.setAslName(form.getAslName());
			displayObject.setContextName(form.getContext().getName());
			displayObject.setFormType(form.getFormType());
			displayObject.setIdseq(form.getIdseq());
			displayObject.setLongName(form.getLongName());
			displayObject.setProtocols(form.getProtocols());
			displayObject.setPublicId(form.getPublicId());
			displayObject.setVersion(form.getVersion());
			return displayObject;
		}
	

	public int getNumberOfCartsConverted() {
		return cartsConverted;
	}
}
