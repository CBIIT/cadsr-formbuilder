package gov.nih.nci.ncicb.cadsr.formbuilder.struts.actions;

import gov.nih.nci.ncicb.cadsr.common.CaDSRConstants;
import gov.nih.nci.ncicb.cadsr.common.dto.FormTransferObject;
import gov.nih.nci.ncicb.cadsr.common.resource.NCIUser;
import gov.nih.nci.ncicb.cadsr.common.struts.formbeans.CDECartFormBean;
import gov.nih.nci.ncicb.cadsr.common.util.CDEBrowserParams;
import gov.nih.nci.ncicb.cadsr.objectCart.CDECart;
import gov.nih.nci.ncicb.cadsr.objectCart.CDECartItem;
import gov.nih.nci.ncicb.cadsr.objectCart.impl.CDECartOCImpl;
import gov.nih.nci.objectCart.client.ObjectCartClient;
import gov.nih.nci.objectCart.client.ObjectCartException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class SecureFormsCartAction extends FormBuilderSecureBaseDispatchAction {

  /**
   * Displays Form Cart.
   *
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   *
   * @return
   *
   * @throws IOException
   * @throws ServletException
   */
  public ActionForward displayFormsCart(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {
     
    return mapping.findForward(retrieveItems(mapping, form, request, response));
  }

  public String retrieveItems(
	    ActionMapping mapping,
	    ActionForm form,
	    HttpServletRequest request,
	    HttpServletResponse response) throws IOException, ServletException {
	     
	    try {
	      NCIUser user =
	        (NCIUser) this.getSessionObject(request, CaDSRConstants.USER_KEY);
	      CDEBrowserParams params = CDEBrowserParams.getInstance();
	      String ocURL = params.getObjectCartUrl();
	      //Get the cart in the session
	      ObjectCartClient cartClient = null;
	      
		  if (!ocURL.equals(""))
			  cartClient = new ObjectCartClient(ocURL);
		  else
	    	  cartClient = new ObjectCartClient();
	      
	      CDECart userCarts = new CDECartOCImpl(cartClient, user.getUsername(),CaDSRConstants.FORMS_CART);
//	      userCarts = nameSort(userCarts);
	      
	      this.setSessionObject(request, CaDSRConstants.FORMS_CART, userCarts);     
	      
	    }
	    catch (Exception exp) {
	      if (log.isErrorEnabled()) {
	        log.error("Exception on displayFormCart", exp);
	      }
	      saveMessage(exp.getMessage(), request);
	      return FAILURE;
	    }
	    return SUCCESS;
  	}
  
  /**
   * Adds items to Form Cart.
   *
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   *
   * @return
   *
   * @throws IOException
   * @throws ServletException
   */
  public ActionForward addItems(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {
    try {
      CDECart cart =
        (CDECart) this.getSessionObject(request, CaDSRConstants.FORMS_CART);
      saveMessage("cadsr.common.formcart.save.success",request);
    }
    catch (Exception exp) {
      if (log.isErrorEnabled()) {
        log.error("Exception on addItems " , exp);
      }
    }

    return mapping.findForward("addDeleteSuccess");
  }

  /**
   * Delete items from the Form Cart.
   *
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   *
   * @return
   *
   * @throws IOException
   * @throws ServletException
   */
  public ActionForward removeItems(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {
    try {
      CDECartFormBean myForm = (CDECartFormBean) form;
      String[] selectedDeleteItems = myForm.getSelectedDeleteItems();
      Collection savedItems = new ArrayList();

      //Collection unsavedItems = new ArrayList();
      Collection items = new ArrayList();

      //Get the cart in the session
      CDECart sessionCart =
        (CDECart) this.getSessionObject(request, CaDSRConstants.FORMS_CART);
      CDECartItem item = null;

      for (int i = 0; i < selectedDeleteItems.length; i++) {
        items.add(selectedDeleteItems[i]);
      }
      
      sessionCart.removeDataElements(items);
      saveMessage("cadsr.common.formcart.delete.success",request);
    }
    catch (Exception exp) {
      if (log.isErrorEnabled()) {
        log.error("Exception on removeItems " , exp);
      }
    }

    return mapping.findForward("addDeleteSuccess");
  }  
  
	/**
	 * Adds items to CDE Cart.
	 *
	 * @param mapping The ActionMapping used to select this instance.
	 * @param form The optional ActionForm bean for this request.
	 * @param request The HTTP Request we are processing.
	 * @param response The HTTP Response we are processing.
	 *
	 * @return
	 *
	 * @throws IOException
	 * @throws ServletException
	 */
	public ActionForward addNewCart(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		try {
			String userName = getLoggedInUsername(request);
			ArrayList<CDECart> sessionCarts = (ArrayList<CDECart>) this.getSessionObject(request, CaDSRConstants.CDE_CART);

			CDECartFormBean myForm = (CDECartFormBean) form;
			String[] selectedSaveItems = myForm.getSelectedSaveItems();	
			String cartName = myForm.getNewCartName();
			
			myForm.setSelectedSaveItems(null);
			
			Collection<CDECartItem> items = new ArrayList<CDECartItem> ();

			CDEBrowserParams params = CDEBrowserParams.getInstance();
			String ocURL = params.getObjectCartUrl();			
			ObjectCartClient ocClient = null;
			CDECart userCart = null;  
			if (!ocURL.equals(""))
				ocClient = new ObjectCartClient(ocURL);
			else
				ocClient = new ObjectCartClient();

			userCart = new CDECartOCImpl(ocClient,userName,cartName);			
			sessionCarts = transferElements(sessionCarts, selectedSaveItems, true, userCart);
			    
			//sessionCart.mergeDataElements(items);      
			this.setSessionObject(request, CaDSRConstants.CDE_CART, sessionCarts);
			
			//saveMessage("cadsr.cdecart.save.success",request);
		}catch (ObjectCartException oce) {
			if (log.isErrorEnabled()) {
				log.error("Exception on addItems " , oce);
			}
			//saveMessage(exp.getErrorCode(), request);      
		}

		return mapping.findForward("addDeleteSuccess");
	}
	
  
	/**
	 * Delete CDE Cart.
	 *
	 * @param mapping The ActionMapping used to select this instance.
	 * @param form The optional ActionForm bean for this request.
	 * @param request The HTTP Request we are processing.
	 * @param response The HTTP Response we are processing.
	 *
	 * @return
	 *
	 * @throws IOException
	 * @throws ServletException
	 */
	public ActionForward deleteCart(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		try {
			String userName = getLoggedInUsername(request);
			ArrayList<CDECart> sessionCarts = (ArrayList<CDECart>) this.getSessionObject(request, CaDSRConstants.CDE_CART);

			CDECartFormBean myForm = (CDECartFormBean) form;
			String[] selectedSaveItems = myForm.getSelectedSaveItems();	
			String cartName = myForm.getDeleteCartName();
			
			myForm.setSelectedSaveItems(null);
			
			Collection<CDECartItem> items = new ArrayList<CDECartItem> ();

			CDEBrowserParams params = CDEBrowserParams.getInstance();
			String ocURL = params.getObjectCartUrl();			
			ObjectCartClient ocClient = null;
			CDECart userCart = null;  
			if (!ocURL.equals(""))
				ocClient = new ObjectCartClient(ocURL);
			else
				ocClient = new ObjectCartClient();

			userCart = new CDECartOCImpl(ocClient,userName,cartName);	
			//Set expiration for Now.
			userCart.expireCart(new java.util.Date(System.currentTimeMillis()));
			
			removeCart(sessionCarts, userCart);
			          
			this.setSessionObject(request, CaDSRConstants.CDE_CART, sessionCarts);
			
		}catch (ObjectCartException oce) {
			if (log.isErrorEnabled()) {
				log.error("Exception on addItems " , oce);
			}    
		}

		return mapping.findForward("addDeleteSuccess");
	}
  
  
  public static ArrayList<CDECart> nameSort(ArrayList<CDECart> carts) {
		ArrayList<CDECart> ret = new ArrayList<CDECart>();
		HashMap<String, CDECart> temp = new HashMap<String, CDECart>();
		ArrayList<String> names = new ArrayList<String>();
		int i = 0;
		boolean basicCart = false;
		for (CDECart cart: carts) {
			temp.put(cart.getCartName(), cart);
			if (cart.getCartName().equals(CaDSRConstants.CDE_CART))
				basicCart = true;
			else
				names.add(cart.getCartName());
			i++;
		}

		java.util.Collections.sort(names);
		
		//Making sure that the basic cart is always on top of the list.
		if (basicCart)
			names.add(0, CaDSRConstants.CDE_CART);			
		
		for (String name: names) {
			ret.add(temp.get(name));
		}
		
		return ret;
	}
  
	private ArrayList<CDECart> transferElements(ArrayList<CDECart> carts, String[] saveItems, boolean persist, CDECart targetCart) {
		
		HashMap <String, CDECart> cartMap = new HashMap<String, CDECart>();
		ArrayList<FormTransferObject> ret = new ArrayList<FormTransferObject>();
		for (CDECart cart: carts) {
			cartMap.put(cart.getCartId(), cart);
		}
		
		for (int i = 0; i < saveItems.length; i++) {
			String id = saveItems[i];
			//CartID:DEID
			String[] split = id.split(":");
			
			CDECart cart = cartMap.get(split[0]);
			FormTransferObject ci = (FormTransferObject)cart.findElement(split[1], FormTransferObject.class);
			ret.add(ci);
			cart.removeDataElement(split[1]);
		}  
		targetCart.mergeElements(ret);
		
		for (int i = 0; i < carts.size(); i++) {
			CDECart cart = carts.get(i);
			if (cart.getCartId().equals(targetCart.getCartId()))
				carts.set(i, targetCart);
		}
		
		return carts;
	}
	
	private HashMap<String, CDECart> makeCartMap(ArrayList<CDECart> carts) {
		HashMap<String, CDECart> cartMap = new HashMap<String, CDECart>();
		if (carts != null) {
			for (CDECart c: carts) 
				cartMap.put(c.getCartName(), c);
		}
		return cartMap;
	}
	
	private void removeCart(ArrayList<CDECart> carts, CDECart targetCart) {
		
		for (int i = 0; i < carts.size(); i++) {
			CDECart cart = carts.get(i);
			if (cart.getCartId().equals(targetCart.getCartId()))
				carts.remove(i);
		}
	}
	
	
}
