package gov.nih.nci.ncicb.cadsr.formbuilder.struts.actions;

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
	      
	      CDECart userCart = new CDECartOCImpl(cartClient, user.getUsername(),CaDSRConstants.FORMS_CART);
	      
	      this.setSessionObject(request, CaDSRConstants.FORMS_CART, userCart);
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
      saveMessage("cadsr.common.cdecart.save.success",request);
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
      saveMessage("cadsr.common.cdecart.delete.success",request);
    }
    catch (Exception exp) {
      if (log.isErrorEnabled()) {
        log.error("Exception on removeItems " , exp);
      }
    }

    return mapping.findForward("addDeleteSuccess");
  }  
}