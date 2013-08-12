package gov.nih.nci.ncicb.cadsr.formbuilder.struts.actions;

import gov.nih.nci.ncicb.cadsr.formbuilder.service.FormBuilderServiceDelegate;
import gov.nih.nci.ncicb.cadsr.formbuilder.struts.actions.cadsrutil_ext.CDECartOCImplExtension;
import gov.nih.nci.ncicb.cadsr.formbuilder.struts.common.FormCartDisplayObject;
import gov.nih.nci.ncicb.cadsr.formbuilder.struts.common.FormCartDisplayObjectPersisted;
import gov.nih.nci.ncicb.cadsr.formbuilder.struts.common.FormConverterUtil;
import gov.nih.nci.ncicb.cadsr.common.CaDSRConstants;
import gov.nih.nci.ncicb.cadsr.common.resource.FormV2;
import gov.nih.nci.ncicb.cadsr.common.resource.Form;
import gov.nih.nci.ncicb.cadsr.common.resource.NCIUser;
import gov.nih.nci.ncicb.cadsr.common.struts.formbeans.CDECartFormBean;
import gov.nih.nci.ncicb.cadsr.common.util.CDEBrowserParams;
import gov.nih.nci.ncicb.cadsr.objectCart.CDECart;
import gov.nih.nci.ncicb.cadsr.objectCart.CDECartItem;
import gov.nih.nci.ncicb.cadsr.objectCart.impl.CDECartOCImpl;
import gov.nih.nci.objectCart.client.ObjectCartClient;
import gov.nih.nci.objectCart.domain.CartObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.sun.enterprise.log.Log;

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
	     
   FormBuilderServiceDelegate service = getFormBuilderService();
	
   log.info("add forms in memeory to the cart");
   CDECartFormBean myForm = (CDECartFormBean) form;
   String[] selectedSaveItems = myForm.getSelectedSaveItems();
   
    try {  	
	if (FormCartOptionsUtil.instance().writeInV1Format()) {
		Collection itemsToMerge = new ArrayList();
		CDECart sessionCart = (CDECart) this.getSessionObject(request,
				CaDSRConstants.FORMS_CART);
		
		if (selectedSaveItems != null) {
			for (Object version1FormId : selectedSaveItems) {
				Form crf = service.getFormDetails((String)version1FormId);
				log.debug("adding V1 form objects to cart (No Save)");
				itemsToMerge.add(crf);
			}
			log.debug("merging V1 form objects");
			sessionCart.mergeElements(itemsToMerge);
			log.debug("done merging V1 form objects");
		}
		this.setSessionObject(request, CaDSRConstants.FORMS_CART, sessionCart);
	}

	if (true) { // we always write the formCartV2 cart now
		log.debug("Starting new add-to-cart - creating objects");
		Collection itemsToRemove = new ArrayList();
		Collection itemsToAdd = new ArrayList();
		CDECartOCImplExtension sessionCart = (CDECartOCImplExtension) this
				.getSessionObject(request, CaDSRConstants.FORMS_CART_V2);
		if (selectedSaveItems != null) {
			for (Object version2FormId : selectedSaveItems) {
				itemsToRemove.add((String)version2FormId);
				FormV2 crf = service.getFormDetailsV2((String)version2FormId);
				itemsToAdd.add(translateCartObject((FormV2) crf));
			}

			// remove from cart before adding (so that we'll update), removing non-existent doesn't hurt
			// (wonder if that is really needed)
			log.debug("removing re-used objects");
			sessionCart.removeDataElements(itemsToRemove);
			// (removeDataElements works on native id and doesn't care
			// about element type)
			log.debug("adding new objects");
			sessionCart.addObjectCollection(itemsToAdd);
			log.debug("done");
			
			// Remove the saved forms from cart in memory
			for (Object version2FormId : selectedSaveItems) {
				sessionCart.removeFormV2((String)version2FormId);
			}
		}
		this.setSessionObject(request, CaDSRConstants.FORMS_CART_V2, sessionCart);
	}

      saveMessage("cadsr.common.formcart.save.success",request);
    }
    catch (Exception exp) {
      if (log.isErrorEnabled()) {
        log.error("Exception on addItems " , exp);
      }
    }

    return mapping.findForward("addDeleteSuccess");


  }
  
  private CartObject translateCartObject(FormV2 crf) throws Exception {
		CartObject ob = new CartObject();
		ob.setType(FormConverterUtil.instance().getCartObjectType());
		ob.setDisplayText(Integer.toString(crf.getPublicId()) + "v" + Float.toString(crf.getVersion()));
		ob.setNativeId(crf.getFormIdseq());
		
		String convertedForm = FormConverterUtil.instance().convertFormToV2(crf);		
		ob.setData(convertedForm);
		return ob;	  
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
