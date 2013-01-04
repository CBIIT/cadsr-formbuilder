package converter;

// watch out for certs being installed and using correct (1.5) version of Java
// needs a bit of memory for intial list of carts  (tested with -Xmx512M)

import org.apache.log4j.Logger;
import gov.nih.nci.ncicb.cadsr.common.CaDSRConstants;
import gov.nih.nci.ncicb.cadsr.common.resource.Form;

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
	
	private int exceptionsWhileClearingExisting = 0;
	private int exceptionsWhileConverting = 0;
	private int cartsConverted = 0;
	
	// Use these flag to limit changes (and anything that creates a lot of logging)  
	private boolean limitToGuest; // limit changes to the guest cart (if changes are allowed)
	private boolean noChanges;

	public FormCartConverter(String ocURL, boolean limit, boolean guestOnly) {
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
	}

	public static void main(String[] args_) {

		try
		{
			_logger.info("started main FormCartConverter");

			int argsCount = args_.length;
			if (argsCount < 1 || argsCount > 2) {
				_logger.info("Incorrect number of arguments.");				
				_logger.info("First argument is the object cart url.");
				_logger.info("Optional second argument may be blank, 'guest', or 'all' to specify which carts to operate on (blank is none)");
				_logger.info("e.g.: java -Xmx512M -jar FormCartConverter.jar https://objcart-dev.nci.nih.gov/objcart10/ all");
			} else {
			
				String ocURL = args_[0];
				
				boolean limit = true;
				boolean guestOnly = true;
				if (argsCount == 2 && args_[1].equalsIgnoreCase("guest")) {
					limit = false;
					guestOnly = true;
				}
				if (argsCount == 2 && args_[1].equalsIgnoreCase("all")) {
					limit = false;
					guestOnly = false;
				}
				
				FormCartConverter converter = new FormCartConverter(ocURL, limit, guestOnly);
				
				int clearingErrors = converter.clearExistingV2FormCarts();
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
			}
			
		}
		catch (Exception ex)
		{
			_logger.fatal(ex.toString(), ex);
		}

	}


	private int clearExistingV2FormCarts() {

		try {
			List<Cart> existingNewCarts = cartService.getCartsByName(CaDSRConstants.FORMS_CART_V2);					
			_logger.debug(existingNewCarts.size() + " existing formCartV2 carts");


			for (Cart cart : existingNewCarts) {

				try {
					_logger.debug("cart " + cart.getId() + " " + cart.getUserId() + " " + cart.getName());

					// get the current formCartV2 elements
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

					if (!noChanges && (!limitToGuest || cart.getUserId().equalsIgnoreCase("guest"))) {
						addFormsToV2Cart(formCollection, cart.getUserId());
						cartsConverted++;
					}

				} catch (Exception e) {
					exceptionsWhileConverting++;
					_logger.debug("  exception converting cart: " + e.getMessage());
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


	private Cart addFormsToV2Cart(Collection forms, String userId) throws ObjectCartException {
		// Loosely based on FormAction addFormToCart
		// We don't check for duplicates -- we assume the v2 cart is empty and that added forms don't include duplicates 	

		_logger.debug(" adding forms to V2 cart..."); 

		Cart v2Cart = cartClient.createCart(userId, CaDSRConstants.FORMS_CART_V2);

		Collection<CartObject> cartObjects = new LinkedList<CartObject>();
		for (Object f: forms)
			cartObjects.add(translateCartObject((Form)f));

		v2Cart = cartClient.storeObjectCollection(v2Cart, cartObjects);
		
		_logger.debug("  ...done adding forms to V2 cart for user " + userId); 		
		return v2Cart;
	}

	
	private CartObject translateCartObject(Form crf) {
		// function copied from FormAction without modification
		CartObject ob = new CartObject();
		ob.setType(FormConverterUtil.instance().getCartObjectType());
		ob.setDisplayText(Integer.toString(crf.getPublicId()) + "v" + Float.toString(crf.getVersion()));
		ob.setNativeId(crf.getFormIdseq());

		String convertedForm = FormConverterUtil.instance().convertFormToV2(crf);		
		ob.setData(convertedForm);
		return ob;	  
	}	

	public int getNumberOfCartsConverted() {
		return cartsConverted;
	}
}
