package gov.nih.nci.ncicb.cadsr.formbuilder.struts.actions;

import gov.nih.nci.ncicb.cadsr.formbuilder.struts.actions.cadsrutil_ext.CDECartOCImplExtension;
import gov.nih.nci.ncicb.cadsr.common.CaDSRConstants;
import gov.nih.nci.ncicb.cadsr.common.resource.NCIUser;
import gov.nih.nci.ncicb.cadsr.common.struts.formbeans.CDECartFormBean;
import gov.nih.nci.ncicb.cadsr.common.util.CDEBrowserParams;
import gov.nih.nci.ncicb.cadsr.objectCart.CDECart;
import gov.nih.nci.ncicb.cadsr.objectCart.CDECartItem;
import gov.nih.nci.ncicb.cadsr.objectCart.impl.CDECartOCImpl;
import gov.nih.nci.objectCart.client.ObjectCartClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import gov.nih.nci.ncicb.cadsr.formbuilder.common.FormCartOptionsUtil;


public class SecureFormsCartAction extends FormBuilderSecureBaseDispatchActionWithCarts {

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
	    	ensureSessionCarts(request);
	    	CDECartOCImplExtension extendedCart = (CDECartOCImplExtension)this.getSessionObject(request, CaDSRConstants.FORMS_CART_V2);
	    	extendedCart.setFormDisplayObjects();	    		      
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
	  
    // Don't think this is used (or really did anything). No longer supported.
    if (log.isErrorEnabled())
    	log.error("addItems not supported");
	  
//    try {
//      CDECart cart =
//        (CDECart) this.getSessionObject(request, CaDSRConstants.FORMS_CART);
//      saveMessage("cadsr.common.formcart.save.success",request);
//    }
//    catch (Exception exp) {
//      if (log.isErrorEnabled()) {
//        log.error("Exception on addItems " , exp);
//      }
//    }

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
 
      
      if (FormCartOptionsUtil.instance().writeInV1Format()){
    	  try {
    		  Collection items = new ArrayList();

    		  //Get the cart in the session
    		  CDECart sessionCart =
    			  (CDECart) this.getSessionObject(request, CaDSRConstants.FORMS_CART);
    		  CDECartItem item = null;

    		  for (int i = 0; i < selectedDeleteItems.length; i++) {
    			  items.add(selectedDeleteItems[i]);
    		  }
    		  sessionCart.removeDataElements(items);  // (removeDataElements works on native id and doesn't care about element type)
    	  }
    	  catch (Exception exp) {
    		  log.error("Exception on removeItems from " + CaDSRConstants.FORMS_CART + " ", exp);
    	  }
      }      
      
      if (true){ // we always write the formCartV2 cart now
    	  try {
    		  Collection items = new ArrayList();

    		  //Get the cart in the session
    		  CDECart sessionCart =
    			  (CDECart) this.getSessionObject(request, CaDSRConstants.FORMS_CART_V2);
    		  CDECartItem item = null;

    		  for (int i = 0; i < selectedDeleteItems.length; i++) {
    			  items.add(selectedDeleteItems[i]);
    		  }
    		  sessionCart.removeDataElements(items);  // (removeDataElements works on native id and doesn't care about element type)
    	  }
    	  catch (Exception exp) {
    		  log.error("Exception on removeItems from " + CaDSRConstants.FORMS_CART_V2 + " ", exp);
    	  }
    		  
      }      

      saveMessage("cadsr.common.formcart.delete.success",request);
    }
    catch (Exception exp) {
      if (log.isErrorEnabled()) {
        log.error("Exception on removeItems " , exp);
      }
    }

    return mapping.findForward("addDeleteSuccess");
  }  
}
