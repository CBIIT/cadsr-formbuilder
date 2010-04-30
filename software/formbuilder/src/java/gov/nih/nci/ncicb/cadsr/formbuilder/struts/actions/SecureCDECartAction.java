package gov.nih.nci.ncicb.cadsr.formbuilder.struts.actions;

import gov.nih.nci.ncicb.cadsr.common.CaDSRConstants;
import gov.nih.nci.ncicb.cadsr.common.dto.QuestionTransferObject;
import gov.nih.nci.ncicb.cadsr.common.resource.DataElement;
import gov.nih.nci.ncicb.cadsr.common.resource.Form;
import gov.nih.nci.ncicb.cadsr.common.resource.Module;
import gov.nih.nci.ncicb.cadsr.common.resource.NCIUser;
import gov.nih.nci.ncicb.cadsr.common.resource.Question;
import gov.nih.nci.ncicb.cadsr.common.struts.formbeans.CDECartFormBean;
import gov.nih.nci.ncicb.cadsr.common.util.CDEBrowserParams;
import gov.nih.nci.ncicb.cadsr.common.util.DTOTransformer;
import gov.nih.nci.ncicb.cadsr.formbuilder.common.FormBuilderException;
import gov.nih.nci.ncicb.cadsr.formbuilder.service.FormBuilderServiceDelegate;
import gov.nih.nci.ncicb.cadsr.formbuilder.struts.common.FormActionUtil;
import gov.nih.nci.ncicb.cadsr.objectCart.CDECart;
import gov.nih.nci.ncicb.cadsr.objectCart.CDECartItem;
import gov.nih.nci.ncicb.cadsr.objectCart.impl.CDECartOCImpl;
import gov.nih.nci.objectCart.client.ObjectCartClient;
import gov.nih.nci.objectCart.client.ObjectCartException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;


public class SecureCDECartAction extends FormBuilderSecureBaseDispatchAction {

  public ActionForward gotoChangeDEAssociation (
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {

    DynaActionForm dynaForm = (DynaActionForm) form;
    int questionIndex =
      Integer.parseInt((String) dynaForm.get("questionIndex"));
    List questions =
      ((Module) getSessionObject(request, MODULE)).getQuestions();
    Question q = (Question) questions.get(questionIndex);

    if(q.getDataElement() == null) 
      request.setAttribute("removeButton", "no");

    return displayCDECart(mapping, form, request, response);
  }

  public ActionForward addQuestion(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {
    DynaActionForm dynaForm = (DynaActionForm) form;
    String[] selectedItems = (String[]) dynaForm.get(SELECTED_ITEMS);

    CDECart sessionCart =
      (CDECart) this.getSessionObject(request, CaDSRConstants.CDE_CART);

    Form crf = (Form) getSessionObject(request, CRF);
    Module module = (Module) getSessionObject(request, MODULE);
    List questions = module.getQuestions();   
    List newQuestions = new ArrayList(selectedItems.length);

    //int display = Integer.parseInt((String) dynaForm.get(QUESTION_INDEX));    
    Integer qindex = (Integer) this.getSessionObject(request, QUESTION_INDEX);
    int displayOrder = 	qindex.intValue();	//Integer.parseInt(qindex);

    Collection col = sessionCart.getDataElements();
    ArrayList al = new ArrayList(col);

    //for getting reference docs
    FormBuilderServiceDelegate service = getFormBuilderService();
    List refDocs = null;
    
    int length = selectedItems.length;
    List deIdList = new ArrayList(length);
    List vdIdList = new ArrayList(length);
    DataElement de = null;   
    for (int i = 0; i < selectedItems.length; i++) {
        de =
            (DataElement) ((CDECartItem) al.get(Integer.parseInt(selectedItems[i]))).getItem();
        deIdList.add(de.getDeIdseq());
        if (de.getValueDomain()!=null && de.getValueDomain().getVdIdseq()!=null){
            vdIdList.add(de.getValueDomain().getVdIdseq());            
        }    
    }
    
    try {
    Map vvMap = service.getValidValues(vdIdList);
    for (int i = 0; i < selectedItems.length; i++) {      
      de = (DataElement) ((CDECartItem) al.get(Integer.parseInt(selectedItems[i]))).getItem();      
      String vdIdSeq = de.getValueDomain().getVdIdseq();
      //may refactor the following code for better performance 
            refDocs = service.getRreferenceDocuments(de.getDeIdseq());
            de.setReferenceDocs(refDocs);
      String questionLongName = de.getLongCDEName();
      if (!isValidCDE(de)){
         saveMessage("cadsr.formbuilder.form.question.add.badCDE", request, de.getLongName());
         questionLongName = "Data Element " + de.getLongName() + " does not have Preferred Question Text";         
          //user can still continue
         //return mapping.findForward(FAILURE);
      }
    
      Question q = new QuestionTransferObject();
      module.setForm(crf);
      q.setModule(module);

      //set valid value
      List values = (List)vvMap.get(vdIdSeq);
      de.getValueDomain().setValidValues(values);
      List newValidValues = DTOTransformer.toFormValidValueList(values, q);
      q.setQuesIdseq(new Date().getTime() + "" + i);
      q.setValidValues(newValidValues);
      q.setDataElement(de);
      q.setLongName(questionLongName);

      q.setVersion(crf.getVersion());
      q.setAslName(crf.getAslName());
      q.setPreferredDefinition(de.getPreferredDefinition());
      q.setContext(crf.getContext());

      q.setDisplayOrder(displayOrder);

      newQuestions.add(q);        
    }//end of for
    }catch (FormBuilderException exp){
            if (log.isErrorEnabled()) {
              log.error("Exception on getting reference documents or Permissible values for the Data Element de Idseq=" + de.getIdseq() , exp);
            }
            saveMessage(exp.getErrorCode(), request);
            return mapping.findForward(FAILURE);
    }       
        
    
    //only when all CDE are valid to be added to a form then add new questions to form.module.questions
    if (displayOrder < questions.size()) {
        questions.addAll(displayOrder, newQuestions);
    }else{
        questions.addAll(newQuestions);
    }
    FormActionUtil.setInitDisplayOrders(questions); //This is done to set display order in a sequential order 
                                      // in case  they are  incorrect in database
                                            
    // Jump to the update location on the screen
    request.setAttribute(CaDSRConstants.ANCHOR,"Q"+displayOrder);    
        
    saveMessage("cadsr.formbuilder.question.add.success",request);
    return mapping.findForward("success");
  }

  public ActionForward changeAssociation(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {

    DynaActionForm dynaForm = (DynaActionForm) form;
    String selectedText = (String) dynaForm.get("selectedText");

    DataElement de = null;
    List newValidValues = null;

 //   String qindex = (String) this.getSessionObject(request, QUESTION_INDEX);
 //   int questionIndex = Integer.parseInt(qindex);
    Integer qindex = (Integer) this.getSessionObject(request, QUESTION_INDEX);
    int questionIndex = qindex.intValue();	

    List questions =
      ((Module) getSessionObject(request, MODULE)).getQuestions();
    CDECart sessionCart =
      (CDECart) this.getSessionObject(request, CaDSRConstants.CDE_CART);
    Question q = (Question) questions.get(questionIndex);

    if ((selectedText == null) | (selectedText.indexOf(",") == -1)) {
      // this is the remove association case
      saveMessage("cadsr.formbuilder.question.changeAssociation.remove",request);
    }
    else {
      int ind = selectedText.indexOf(',');
      int deIndex = Integer.parseInt(selectedText.substring(0, ind).trim());
      String newLongName = "";

      if (selectedText.length() > ind) {
        newLongName = selectedText.substring(ind + 1).trim();
      }

      Collection col = sessionCart.getDataElements();
      ArrayList al = new ArrayList(col);

      de = (DataElement) ((CDECartItem) al.get(deIndex)).getItem();
      if (newLongName==null || "null".equals(newLongName) || newLongName.length()==0){
          //newLongName = "";
           newLongName = "Data Element " + de.getLongName() + " does not have Preferred Question Text";
      }

      //get reference docs
      FormBuilderServiceDelegate service = getFormBuilderService();
      List refDocs = null;
      
      try {
          refDocs = service.getRreferenceDocuments(de.getDeIdseq());          
          de.setReferenceDocs(refDocs);
       }catch (FormBuilderException exp){
           if (log.isErrorEnabled()) {
             log.error("Exception on getting reference documents for the Data Element " , exp);
           }
           saveMessage(exp.getErrorCode(), request);
           return mapping.findForward(FAILURE);
       }       
        
      if (!isValidCDE(de)){
          saveMessage("cadsr.formbuilder.form.question.add.badCDE", request, de.getLongName());
          //return mapping.findForward(FAILURE);
          //user can still continue
      }
      
      //set valid values with value meaning
      List vdIdList = new ArrayList();
      try{
          if (de.getValueDomain()!=null && de.getValueDomain().getVdIdseq()!=null ){
              String vdIdSeq = de.getValueDomain().getVdIdseq();
              vdIdList.add(vdIdSeq);
              Map vvMap = service.getValidValues(vdIdList);
              List vvList = (List)vvMap.get(vdIdSeq);
              de.getValueDomain().setValidValues(vvList);
              newValidValues = DTOTransformer.toFormValidValueList(vvList, q);
          }    
          
      }catch (FormBuilderException fbe){
          log.error("Exception on getting valid values for the Data Element Value Doamin , vdIdSeq=" 
                                +  de.getValueDomain().getVdIdseq(), fbe);
          saveMessage("cadsr.formbuilder.question.changeAssociation.newAssociation.fail", request);
          return mapping.findForward(FAILURE);
      }
      
      q.setLongName(newLongName);
      
      q.setValidValues(newValidValues);
      saveMessage("cadsr.formbuilder.question.changeAssociation.newAssociation",request);
    }

    q.setDataElement(de);
    //clear out the default value
    q.setDefaultValidValue(null);
    q.setDefaultValue("");

    // Jump to the update location on the screen
    request.setAttribute(CaDSRConstants.ANCHOR,"Q"+questionIndex);     

    return mapping.findForward("success");
  }

  /**
   * Displays CDE Cart.
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
  public ActionForward displayCDECart(
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
	      
	      ArrayList<CDECart> sessionCarts = CDECartOCImpl.getAllCarts(cartClient, user.getUsername());
	      sessionCarts = this.nameSort(sessionCarts);
	      this.setSessionObject(request, CaDSRConstants.CDE_CART, sessionCarts);
	    }
	    catch (Exception exp) {
	      if (log.isErrorEnabled()) {
	        log.error("Exception on displayCDECart", exp);
	      }
	      saveMessage(exp.getMessage(), request);
	      return FAILURE;
	    }
	    return SUCCESS;
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
  public ActionForward addItems(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		try {
			String userName = getLoggedInUsername(request);
			ArrayList<CDECart> sessionCarts = (ArrayList<CDECart>) this.getSessionObject(request, CaDSRConstants.CDE_CART);
			
			CDECart sessionCart = sessionCarts.get(0);
			CDECartFormBean myForm = (CDECartFormBean) form;
			String[] selectedSaveItems = myForm.getSelectedSaveItems();			
			myForm.setSelectedSaveItems(null);
			
			Collection<CDECartItem> items = new ArrayList<CDECartItem>();

			CDEBrowserParams params = CDEBrowserParams.getInstance();
			String ocURL = params.getObjectCartUrl();			
			ObjectCartClient ocClient = null;
			CDECart userCart = null;  
			if (!ocURL.equals(""))
				ocClient = new ObjectCartClient(ocURL);
			else
				ocClient = new ObjectCartClient();

			userCart = new CDECartOCImpl(ocClient,userName,CaDSRConstants.CDE_CART);			

			for (int i = 0; i < selectedSaveItems.length; i++) {
				CDECartItem cartItem = sessionCart.findDataElement(selectedSaveItems[i]);

				cartItem.setPersistedInd(true);
				items.add(cartItem);
			}      
			//sessionCart.mergeDataElements(items);      
			userCart.mergeDataElements(items);

			saveMessage("cadsr.cdecart.save.success",request);
		}catch (ObjectCartException oce) {
			if (log.isErrorEnabled()) {
				log.error("Exception on addItems " , oce);
			}
			//saveMessage(exp.getErrorCode(), request);      
		}

		return mapping.findForward("addDeleteSuccess");
	}

  /**
   * Delete items from the CDE Cart.
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
			String userName = getLoggedInUsername(request);

			CDECartFormBean myForm = (CDECartFormBean) form;
			String[] selectedDeleteItems = myForm.getSelectedDeleteItems();      
			Collection items = new ArrayList();

			//Get the cart in the session
			ArrayList<CDECart> sessionCarts = (ArrayList<CDECart>) this.getSessionObject(request, CaDSRConstants.CDE_CART);
			
			CDECartItem item = null;

			CDEBrowserParams params = CDEBrowserParams.getInstance();
			String ocURL = params.getObjectCartUrl();			
			ObjectCartClient ocClient = null;
			CDECart userCart = null;  
			if (!ocURL.equals(""))
				ocClient = new ObjectCartClient(ocURL);
			else
				ocClient = new ObjectCartClient();

			userCart = new CDECartOCImpl(ocClient,userName,CaDSRConstants.CDE_CART);
			sessionCarts = transferDataElements(sessionCarts, selectedDeleteItems, false, userCart);
			this.setSessionObject(request, CaDSRConstants.CDE_CART, sessionCarts);
			
			saveMessage("cadsr.cdecart.delete.success",request);
		}
		catch (ObjectCartException oce) {
			if (log.isErrorEnabled()) {
				log.error("Exception on removeItems " , oce);
			}
			//saveMessage(exp.getErrorCode(), request);      
		}    
		return mapping.findForward("addDeleteSuccess");
	} 
 
 
  public ActionForward subsetQuestionValidValues(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {
    DynaActionForm dynaForm = (DynaActionForm) form;
    String[] selectedItems = (String[]) dynaForm.get(SELECTED_ITEMS);

    CDECart sessionCart =
      (CDECart) this.getSessionObject(request, CaDSRConstants.CDE_CART);

    Form crf = (Form) getSessionObject(request, CRF);
    Module module = (Module) getSessionObject(request, MODULE);
    List selectedDataElements = new ArrayList();

    Collection col = sessionCart.getDataElements();
    ArrayList al = new ArrayList(col);

    int displayOrder = Integer.parseInt((String) dynaForm.get(QUESTION_INDEX));

    for (int i = 0; i < selectedItems.length; i++) {
      DataElement de =
        (DataElement) ((CDECartItem) al.get(Integer.parseInt(selectedItems[i]))).getItem();
      selectedDataElements.add(de);
    }
    
    this.setSessionObject(request,SELECTED_DATAELEMENTS,selectedDataElements,true);
    return mapping.findForward(SUBSET_VALIDVALUES);
  }
 
  public ActionForward addSubsettedValidValuesQuestion(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {
    DynaActionForm dynaForm = (DynaActionForm) form;
    
    saveMessage("cadsr.formbuilder.question.add.success",request);
    return mapping.findForward("success");
  } 
  
  
  public ActionForward cancelAddSubsettedValidValuesQuestion(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {
    DynaActionForm dynaForm = (DynaActionForm) form;
    
    removeSessionObject(request,SELECTED_DATAELEMENTS);
    return mapping.findForward(CANCEL);
  }
  
  public ActionForward retrieveAssociateCDEs(
		    ActionMapping mapping,
		    ActionForm form,
		    HttpServletRequest request,
		    HttpServletResponse response) throws IOException, ServletException {
		    
	    	DynaActionForm dynaForm = (DynaActionForm) form;
		    String action = retrieveItems(mapping, form, request, response);
		    return mapping.findForward("retrieveCDEs");
		  }
		  
  public ActionForward retrieveQuestionCDEs(
		    ActionMapping mapping,
		    ActionForm form,
		    HttpServletRequest request,
		    HttpServletResponse response) throws IOException, ServletException {
		    
	    	String action = retrieveItems(mapping, form, request, response);
	    	return mapping.findForward("retrieveCDEs");
  }
		  
  private boolean  isValidCDE(DataElement de){
    if (de.getLongCDEName()==null || de.getLongCDEName().length()==0){
         if (log.isDebugEnabled()) {
           log.debug("User is trying to add a CDE without Preferred Question Text. The Data Element deIdseq=" + de.getIdseq());
         }
        return false;
    }
    return true;
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
			sessionCarts = transferDataElements(sessionCarts, selectedSaveItems, true, userCart);
			    
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
		boolean fCart = false;
		for (CDECart cart: carts) {
			temp.put(cart.getCartName(), cart);
			if (cart.getCartName().equals(CaDSRConstants.CDE_CART))
				basicCart = true;
			else if (cart.getCartName().equals(CaDSRConstants.FORMS_CART))
				fCart = true;
			else
				names.add(cart.getCartName());
			i++;
		}

		java.util.Collections.sort(names);
		
		//Making sure that the basic cart is always on top of the list.
		if (basicCart) {
			names.add(0, CaDSRConstants.CDE_CART);		
			if (fCart)
				names.add(1, CaDSRConstants.FORMS_CART);
		} else if (fCart) 
			names.add(0, CaDSRConstants.FORMS_CART);
		
		for (String name: names) {
			ret.add(temp.get(name));
		}
		
		return ret;
	}

	private ArrayList<CDECart> transferDataElements(ArrayList<CDECart> carts, String[] saveItems, boolean persist, CDECart targetCart) {
		
		HashMap <String, CDECart> cartMap = new HashMap<String, CDECart>();
		ArrayList<CDECartItem> ret = new ArrayList<CDECartItem>();
		for (CDECart cart: carts) {
			cartMap.put(cart.getCartId(), cart);
		}
		
		for (int i = 0; i < saveItems.length; i++) {
			String id = saveItems[i];
			//CartID:DEID
			String[] split = id.split(":");
			
			CDECart cart = cartMap.get(split[0]);
			CDECartItem ci = cart.findDataElement(split[1]);
			ci.setPersistedInd(persist);
			ret.add(ci);
			cart.removeDataElement(split[1]);
		}  
		targetCart.mergeDataElements(ret);
		
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
