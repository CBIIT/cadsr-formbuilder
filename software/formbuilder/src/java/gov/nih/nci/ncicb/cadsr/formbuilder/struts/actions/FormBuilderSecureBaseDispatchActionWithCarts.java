package gov.nih.nci.ncicb.cadsr.formbuilder.struts.actions;

import gov.nih.nci.ncicb.cadsr.formbuilder.struts.actions.cadsrutil_ext.CDECartOCImplExtension;
import gov.nih.nci.ncicb.cadsr.common.CaDSRConstants;
import gov.nih.nci.ncicb.cadsr.common.CaDSRUtil;
import gov.nih.nci.ncicb.cadsr.common.resource.NCIUser;
import gov.nih.nci.ncicb.cadsr.common.util.CDEBrowserParams;
import gov.nih.nci.ncicb.cadsr.objectCart.CDECart;
import gov.nih.nci.ncicb.cadsr.objectCart.impl.CDECartOCImpl;
import gov.nih.nci.cadsrapi.client.*;
import gov.nih.nci.ncicb.cadsr.formbuilder.struts.actions.cadsrutil_ext.FormDisplayCartOCIImpl;

import javax.servlet.http.HttpServletRequest;

import gov.nih.nci.ncicb.cadsr.formbuilder.common.FormCartOptionsUtil;
import gov.nih.nci.ncicb.cadsr.common.util.logging.Log;
import gov.nih.nci.ncicb.cadsr.common.util.logging.LogFactory;

public class FormBuilderSecureBaseDispatchActionWithCarts extends
		FormBuilderSecureBaseDispatchAction {

	public void ensureSessionCarts(HttpServletRequest request) {

		try {
			// Only checking for required V2 cart. If it's there, we can assume
			// V1 cart was created (or not) properly.
			Object sessionCart = this.getSessionObject(request,	CaDSRConstants.FORMS_CART_V2);
			if (sessionCart == null) {

				NCIUser user = (NCIUser) this.getSessionObject(request,
						CaDSRConstants.USER_KEY);
				CDEBrowserParams params = CDEBrowserParams.getInstance();
				String ocURL = "http://cadsrapi-dev.nci.nih.gov/cadsrapi41";//params.getObjectCartUrl();
				// Get the cart in the session
				ObjectCartClient cartClient = null;

				 String username = CaDSRUtil.getFormBuilderUserNameNoCache();
			        String password = CaDSRUtil.getFormBuilderPasswordNoCache();

				if (!ocURL.equals(""))
					cartClient = new ObjectCartClient(username, password);
			//	if (!ocURL.equals(""))
				//	cartClient = new ObjectCartClient(ocURL);
				else
					cartClient = new ObjectCartClient();

				if (FormCartOptionsUtil.instance().writeInV1Format()) {
					CDECart userCart = new CDECartOCImpl(cartClient, user
							.getUsername(), CaDSRConstants.FORMS_CART);
					this.setSessionObject(request, CaDSRConstants.FORMS_CART,
							userCart);
					log.debug("setSessionObject " + CaDSRConstants.FORMS_CART
							+ " " + userCart);
				}

				if (true) { // we always write the formCartV2 cart now
					CDECart userCartV2 = new CDECartOCImplExtension(cartClient,
							user.getUsername(), CaDSRConstants.FORMS_CART_V2,
							getFormBuilderService());
					this.setSessionObject(request,
							CaDSRConstants.FORMS_CART_V2, userCartV2);
					log.debug("setSessionObject "
							+ CaDSRConstants.FORMS_CART_V2 + " " + userCartV2);
				}
				
				if (this.getSessionObject(request,	CaDSRConstants.FORMS_DISPLAY_CART) == null) { 
					FormDisplayCartOCIImpl userFormDisplayCart = new FormDisplayCartOCIImpl(cartClient,
							user.getUsername(), CaDSRConstants.FORMS_DISPLAY_CART,
							getFormBuilderService());
					this.setSessionObject(request,
							CaDSRConstants.FORMS_DISPLAY_CART, userFormDisplayCart);
					log.debug("setSessionObject "
							+ CaDSRConstants.FORMS_DISPLAY_CART + " " + userFormDisplayCart);
				}
				if (this.getSessionObject(request,	CaDSRConstants.FORMS_DISPLAY_CART2) == null) { 
					FormDisplayCartOCIImpl userFormDisplayCart = new FormDisplayCartOCIImpl(cartClient,
							user.getUsername(), CaDSRConstants.FORMS_DISPLAY_CART2,
							getFormBuilderService());
					this.setSessionObject(request,
							CaDSRConstants.FORMS_DISPLAY_CART2, userFormDisplayCart);
					log.debug("setSessionObject "
							+ CaDSRConstants.FORMS_DISPLAY_CART2 + " " + userFormDisplayCart);
				}
			}
		} catch (Exception exp) {
			if (log.isErrorEnabled()) {
				log.error("Exception creating session form carts", exp);
			}
		}
	}

}