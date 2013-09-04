package gov.nih.nci.ncicb.cadsr.formbuilder.struts.actions;


import gov.nih.nci.ncicb.cadsr.formbuilder.struts.common.FormConverterUtil;
import gov.nih.nci.ncicb.cadsr.common.CaDSRConstants;
import gov.nih.nci.ncicb.cadsr.common.cdebrowser.DataElementSearchBean;
import gov.nih.nci.ncicb.cadsr.common.dto.FormTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.FormV2TransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.jdbc.JDBCFormTransferObject;
import gov.nih.nci.ncicb.cadsr.common.formbuilder.struts.common.FormConstants;
import gov.nih.nci.ncicb.cadsr.common.jsp.bean.PaginationBean;
import gov.nih.nci.ncicb.cadsr.common.resource.Context;
import gov.nih.nci.ncicb.cadsr.common.resource.Form;
import gov.nih.nci.ncicb.cadsr.common.resource.FormV2;
import gov.nih.nci.ncicb.cadsr.common.resource.NCIUser;
import gov.nih.nci.ncicb.cadsr.common.struts.formbeans.CDECartFormBean;
import gov.nih.nci.ncicb.cadsr.common.struts.formbeans.GenericDynaFormBean;
import gov.nih.nci.ncicb.cadsr.common.util.CDEBrowserParams;
import gov.nih.nci.ncicb.cadsr.common.util.StringPropertyComparator;
import gov.nih.nci.ncicb.cadsr.common.util.StringUtils;
import gov.nih.nci.ncicb.cadsr.formbuilder.common.FormBuilderException;
import gov.nih.nci.ncicb.cadsr.formbuilder.service.FormBuilderServiceDelegate;
import gov.nih.nci.objectCart.client.ObjectCartClient;
import gov.nih.nci.objectCart.client.ObjectCartException;
import gov.nih.nci.objectCart.domain.Cart;
import gov.nih.nci.objectCart.domain.CartObject;
import gov.nih.nci.ncicb.cadsr.objectCart.CDECart;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import java.util.LinkedList;
import java.io.StringWriter;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.ValidationException;
import javax.xml.transform.*;
import javax.xml.transform.stream.*;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.StringReader;
import javax.servlet.ServletContext; 

import gov.nih.nci.ncicb.cadsr.formbuilder.common.FormCartOptionsUtil;
import gov.nih.nci.ncicb.cadsr.formbuilder.struts.actions.cadsrutil_ext.CDECartOCImplExtension;


public class FormAction extends FormBuilderSecureBaseDispatchActionWithCarts {

    /**
     * set a session object.
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @param name
     * @return
     * @throws Exception
     */
    protected ActionForward dispatchMethod(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response,
      String name) throws Exception {
          ActionForward forward = super.dispatchMethod(mapping, form, request, response, name);
          request.getSession().setAttribute("Initialized", "true");
      return forward;
      }


  /**
   * Returns all forms for the given criteria.
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
  public ActionForward getAllForms(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {
	//this.logSessionData("getAllForms ", form.toString(), request.getSession());
    //Set the lookup values in the session
    setInitLookupValues(request);

    FormBuilderServiceDelegate service = getFormBuilderService();
    DynaActionForm searchForm = (DynaActionForm) form;
    String formLongName = (String) searchForm.get(this.SEARCH_FORM_NAME);
    String protocolIdSeq = (String) searchForm.get(this.SEARCH_PROTO_IDSEQ);
    String proptocolName = (String)searchForm.get(this.PROTOCOLS_LOV_NAME_FIELD);
    String contextIdSeq = (String) searchForm.get(this.SEARCH_CONTEXT_IDSEQ);
    String workflow = (String) searchForm.get(this.SEARCH_WORKFLOW);
    String categoryName = (String) searchForm.get(this.SEARCH_CATEGORY_NAME);
    String type = (String) searchForm.get(this.SEARCH_FORM_TYPE);
    String csCsiIdSeq = (String) searchForm.get(this.CS_CSI_ID);
    String csIdseq = (String) searchForm.get(this.CS_ID);
    String publicId = (String)searchForm.get(this.SEARCH_FORM_PUBLICID);
    String version = (String)searchForm.get(this.LATEST_VERSION_INDICATOR);
    String moduleLongName = (String)searchForm.get(this.MODULE_LONG_NAME);
    String cdePublicId = (String)searchForm.get(this.CDE_PUBLIC_ID); 

   //Set the Context Name
   
   List contexts = (List)this.getSessionObject(request,this.ALL_CONTEXTS);
   Context currContext = getContextForId(contexts,contextIdSeq);
   if(currContext!=null)
    searchForm.set(this.SEARCH_CONTEXT_NAME,currContext.getName());
   
   Collection forms = null;
   String nodeType = request.getParameter("P_PARAM_TYPE");
    if(nodeType!=null&&nodeType.equals("CLASSIFICATION"))
    {
      forms = service.getAllFormsForClassification(csIdseq);
    }
    else if(nodeType!=null&&nodeType.equals("PUBLISHING_PROTOCOL"))
    {
      forms = service.getAllPublishedFormsForProtocol(protocolIdSeq);
    }
    else
    {
    	String exContexts = "";    	
    	DataElementSearchBean searchBean  = (DataElementSearchBean)getSessionObject(request,"desb");
    	if (searchBean != null)
    		exContexts = searchBean.getExcludeContextList();  //this.getExcludeList(searchBean);
    	
    forms =
      service.getAllForms(
        formLongName, protocolIdSeq, contextIdSeq, workflow, categoryName, type,
        csCsiIdSeq,
        publicId, version, moduleLongName,cdePublicId,
        (NCIUser)getSessionObject(request,this.USER_KEY), exContexts);
    }
    setSessionObject(request, this.FORM_SEARCH_RESULTS, forms,true);    
    //Initialize and add the PagenationBean to the Session
    PaginationBean pb = new PaginationBean();

    if (forms != null) {
      pb.setListSize(forms.size());
    }
    Form aForm = null;
    if(forms.size()>0)
    {
      Object[] formArr = forms.toArray();
      aForm=(Form)formArr[0];
      StringPropertyComparator comparator = new StringPropertyComparator(aForm.getClass());
      comparator.setPrimary("longName");
      comparator.setOrder(comparator.ASCENDING);
      Collections.sort((List)forms,comparator);
      setSessionObject(request,FORM_SEARCH_RESULT_COMPARATOR,comparator);      
    }
      
    setSessionObject(request, FORM_SEARCH_RESULTS_PAGINATION, pb,true);
    setSessionObject(request, ANCHOR, "results",true); 
    
	//this.logSessionData("getAllForms ", form.toString(), request.getSession());   
    return mapping.findForward(SUCCESS);
  }

  /**
   * Clear the cache for a new search.
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
  public ActionForward newSearch(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {
    //Set the lookup values in the session
    setInitLookupValues(request);
    DynaActionForm searchForm = (DynaActionForm) form;
    GenericDynaFormBean formBean  = (GenericDynaFormBean)form;
    formBean.clear();
    removeSessionObject(request, this.FORM_SEARCH_RESULTS);
    removeSessionObject(request, this.FORM_SEARCH_RESULTS_PAGINATION);
    return mapping.findForward(SUCCESS);
  }
  
    /**
   * Sorts search results by fieldName
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
  public ActionForward sortResult(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {
    //Set the lookup values in the session
    setInitLookupValues(request);
    DynaActionForm searchForm = (DynaActionForm) form;
    String sortField = (String) searchForm.get("sortField");
    Integer sortOrder = (Integer) searchForm.get("sortOrder");
    List forms = (List)getSessionObject(request,FORM_SEARCH_RESULTS);
    StringPropertyComparator comparator = (StringPropertyComparator)getSessionObject(request,FORM_SEARCH_RESULT_COMPARATOR);
    comparator.setRelativePrimary(sortField);
    comparator.setOrder(sortOrder.intValue());
    //Initialize and add the PagenationBean to the Session
    PaginationBean pb = new PaginationBean();
    if (forms != null) {
      pb.setListSize(forms.size());
    }
    Collections.sort(forms,comparator);
    setSessionObject(request, FORM_SEARCH_RESULTS_PAGINATION, pb,true);
    setSessionObject(request, ANCHOR, "results",true);  
    return mapping.findForward(SUCCESS);
  }
  
  /**
   * Sets the parameters into a map. This action method need to called before
   * forwarding framed jsps since each frame has its own request object.
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
  public ActionForward initMessageKeys(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {
    Enumeration names = request.getAttributeNames();
    Map params = new HashMap();
    ActionMessages messages =
      (ActionMessages) request.getAttribute(Globals.MESSAGE_KEY);

  //  ActionErrors errors =
  //    (ActionErrors) request.getAttribute(Globals.ERROR_KEY);

    if ((messages != null) && !messages.isEmpty()) {
      Iterator mesgIt = messages.get(ActionMessages.GLOBAL_MESSAGE);
      StringBuffer strBuff = new StringBuffer();

      while (mesgIt.hasNext()) {
        ActionMessage mesg = (ActionMessage) mesgIt.next();
        strBuff.append(mesg.getKey());

        if (mesgIt.hasNext()) {
          strBuff.append(",");
        }
      }      
     params.put(ActionMessages.GLOBAL_MESSAGE, strBuff.toString());
    }
 /*   if ((errors != null) && !errors.isEmpty()) {
      Iterator errorIt = errors.get(ActionErrors.GLOBAL_MESSAGE);
      StringBuffer strBuff = new StringBuffer();

      while (errorIt.hasNext()) {
        ActionMessage error = (ActionMessage)errorIt.next();
        strBuff.append(error.getKey());

        if (errorIt.hasNext()) {
          strBuff.append(",");
        }
      }      

      params.put(ActionErrors.GLOBAL_MESSAGE, strBuff.toString());
    }
    */
    request.setAttribute("requestMap", params);

    return mapping.findForward(SUCCESS);
  }

  /**
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
  public ActionForward setMessagesFormKeys(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {

    Map params = new HashMap();
    String stringMessages = request.getParameter(ActionMessages.GLOBAL_MESSAGE);
    if ((stringMessages != null) && !stringMessages.equals("")) {
      String[] mesgArr = StringUtils.tokenizeCSVList(stringMessages);
  
      for (int i = 0; i < mesgArr.length; i++) {
        this.saveMessage(mesgArr[i], request);
        log.debug(" message " + mesgArr[i]);
      }
    }
/*
    String stringErrors = request.getParameter(ActionErrors.GLOBAL_MESSAGE);
    if ((stringErrors != null) && !stringErrors.equals("")) {
      String[] errorArr = StringUtils.tokenizeCSVList(stringErrors);
  
      for (int i = 0; i < errorArr.length; i++) {
        this.saveMessage(errorArr[i], request);
        log.debug(" error " + errorArr[i]);
      }
    }
*/
    return mapping.findForward(SUCCESS);
  }

  /**
   * Returns Complete form given an Id.
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
  public ActionForward getFormDetails(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {
    setInitLookupValues(request);
        
    DynaActionForm hrefCRFForm = (DynaActionForm) form;
    Form crf2 = null;
    String showCached = (String)request.getAttribute("showCached");

	int formsInQueue = 0;
 	  String sFormIdSeq = (String)request.getParameter(FormConstants.FORM_ID_SEQ);       
	    if(showCached!=null&&showCached.equalsIgnoreCase(CaDSRConstants.YES))
	    {
	        crf2 = (Form) getSessionObject(request, CRF);
	        sFormIdSeq = crf2.getIdseq();
	    }
	    else if (hrefCRFForm != null)
	    {
	    	  sFormIdSeq = (String) hrefCRFForm.get(FORM_ID_SEQ);
	    }
	    
    try 
    {
       Object displayOrderToCopy = getSessionObject(request,MODULE_DISPLAY_ORDER_TO_COPY);
       
       if (displayOrderToCopy != null) {
          return mapping.findForward("setModuleCopyForm");
       }
      setFormForAction(form, request);
      
//// Begin added for monitor if the current form has been added in Form Cart for saving.  D.An, 20130830.  
      String userMame = (String) request.getSession().getAttribute("myUsername");
      if( userMame != null && userMame.equalsIgnoreCase("viewer") != true && sFormIdSeq.length() > 8 )
      {
System.out.println("userName -- " + userMame );	
		  ////request.getSession().setAttribute("myFormAdded", "n");
System.out.println(" " + sFormIdSeq + " not found " );	
  	
  	   CDECartOCImplExtension sessionCartV2 = (CDECartOCImplExtension) this
  				.getSessionObject(request, CaDSRConstants.FORMS_CART_V2);
  	   Collection itemsAdded = null;
  	   if( sessionCartV2 != null )
  		   if( sessionCartV2.getFormCartV2().size() >= 0 )
  			   itemsAdded = sessionCartV2.getFormCartV2().values();
  	   
  	   if ( itemsAdded != null )
  	   {
  		   for ( Object version2Form : itemsAdded ) 
  		   {
  			   if( ((FormV2TransferObject)version2Form).getFormIdseq().equals(sFormIdSeq) ) 
  			   {
  				   formsInQueue = 1;
System.out.println("sFormIdSeq found !!!!! -- " + sFormIdSeq );	
					break;
  			   }
  			}
  		}
  	   if( formsInQueue == 0 )
  			   request.getSession().setAttribute("myFormAdded", "n");
  	   else
			   request.getSession().setAttribute("myFormAdded", "Y");  		   
//// End. 	D.An, 20130830.	
      }
    }
    catch (FormBuilderException exp) {
      if (log.isErrorEnabled()) {
        log.error("Exception getting CRF", exp);
      }      
      saveMessage(ERROR_FORM_RETRIEVE, request);
      saveMessage(ERROR_FORM_DOES_NOT_EXIST, request);
      return mapping.findForward(FAILURE);
    }

    return mapping.findForward(SUCCESS);
  }

  /**
   * Returns Complete form given an Id for Copy.
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
  public ActionForward getPrinterVersion(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {
    try {
      Form crf = (Form) this.getSessionObject(request, CRF);

      if (form == null) {
        setFormForAction(form, request);
      }
    }
    catch (FormBuilderException exp) {
      saveMessage(ERROR_FORM_RETRIEVE, request);
      saveMessage(ERROR_FORM_DOES_NOT_EXIST, request);
      if (log.isErrorEnabled()) {
        log.error("Exception on getFormForEdit ", exp);
      }
    }

    return mapping.findForward(SUCCESS);
  }

  /**
   * Returns all forms for the search criteria specified by clicking on a tree
   * node.
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
  public ActionForward getAllFormsForTreeNode(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {
    String protocolIdSeq = "";
    String protocolLongName = "";
    String formIdSeq = "";
    String csCsiIdSeq = "";
    String csIdseq = "";    
    String nodeType = request.getParameter("P_PARAM_TYPE");
    String nodeIdSeq = request.getParameter("P_IDSEQ");
    String contextIdSeq = request.getParameter("P_CONTE_IDSEQ");
    if (contextIdSeq == null) contextIdSeq = "";
    String csiName = "";

    if ("PROTOCOL".equals(nodeType)||"PUBLISHING_PROTOCOL".equals(nodeType)) {
      protocolIdSeq = nodeIdSeq;
      protocolLongName = request.getParameter("protocolLongName");
    }
    else if ("CRF".equals(nodeType) || "TEMPLATE".equals(nodeType))
      formIdSeq = nodeIdSeq;
    else if ("CSI".equals(nodeType)) {
      csCsiIdSeq = nodeIdSeq;
      csiName = request.getParameter("csiName");
      //Publishing Change Order
      contextIdSeq = "";
    }
    //Publish Change Order
    else if("CLASSIFICATION".equals(nodeType))
    {
      csIdseq=nodeIdSeq;
      contextIdSeq = "";
    }

    GenericDynaFormBean searchForm = (GenericDynaFormBean) form;
    searchForm.clear();
    searchForm.set(this.SEARCH_PROTO_IDSEQ, protocolIdSeq);
    searchForm.set(this.PROTOCOLS_LOV_NAME_FIELD, protocolLongName);
    searchForm.set(this.SEARCH_CONTEXT_IDSEQ, contextIdSeq);
    searchForm.set(this.FORM_ID_SEQ,formIdSeq);
    searchForm.set("jspClassification",csCsiIdSeq);
    searchForm.set("txtClassSchemeItem",csiName);
    //Publish Change Order
    searchForm.set(this.CS_ID,nodeIdSeq);
    
    setSessionObject(request, ANCHOR, "results",true);  
    
    if ("CRF".equals(nodeType) || "TEMPLATE".equals(nodeType))
      return this.getFormDetails(mapping, form, request, response);
    else
      return this.getAllForms(mapping, form, request, response);
      
    
  }
  
  /**
   * Clear the screen for a new Search
   * Clears the struts form and also the resultSet of the previous search
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
  public ActionForward clearFormSearch(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {
    
    GenericDynaFormBean formBean  = (GenericDynaFormBean)form;
    formBean.clear();
    formBean.set(FormConstants.LATEST_VERSION_INDICATOR, FormConstants.LATEST_VERSION);  //default 
    removeSessionObject(request,FORM_SEARCH_RESULTS);
    return mapping.findForward(SUCCESS);
    }
  
	public ActionForward saveFormInQueue(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		   FormBuilderServiceDelegate service = getFormBuilderService();
		   CDECartOCImplExtension sessionCartV2 = (CDECartOCImplExtension) this
					.getSessionObject(request, CaDSRConstants.FORMS_CART_V2);
		   Collection selectedSaveItems = sessionCartV2.getFormCartV2().values();
		   int formsInQueue = 0;
		   
		    try {  	
				if (FormCartOptionsUtil.instance().writeInV1Format()) {
					Collection itemsToMerge = new ArrayList();
					CDECart sessionCart = (CDECart) this.getSessionObject(request,
							CaDSRConstants.FORMS_CART);
					
					if (selectedSaveItems != null) {
						for (Object version2Form : selectedSaveItems) {
							Form crf = service.getFormDetails(((FormV2TransferObject)version2Form).getFormIdseq());
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
					
					if (selectedSaveItems != null) {
						for (Object version2Form : selectedSaveItems) {
							itemsToRemove.add(((FormV2TransferObject)version2Form).getFormIdseq());
							FormV2 crf = service.getFormDetailsV2(((FormV2TransferObject)version2Form).getFormIdseq());
							itemsToAdd.add(translateCartObject((FormV2) crf));
						}
	
						// remove from cart before adding (so that we'll update), removing non-existent doesn't hurt
						// (wonder if that is really needed)
						log.debug("removing re-used objects");
						sessionCartV2.removeDataElements(itemsToRemove);
						// (removeDataElements works on native id and doesn't care
						// about element type)
						log.debug("adding new objects");
						sessionCartV2.addObjectCollection(itemsToAdd);
						log.debug("done");
						
						// Remove the saved forms from cart in memory
						formsInQueue = sessionCartV2.getFormCartV2().size();
						selectedSaveItems.clear();
						sessionCartV2.clearFormV2();
					}
					this.setSessionObject(request, CaDSRConstants.FORMS_CART_V2, sessionCartV2);
				}
			//// GF32932  D.An, 20130825.    
			      request.getSession().setAttribute("myFormCartInfo", new Integer(formsInQueue).toString());
System.out.println( "Forms Queued in Cart : " + request.getSession().getAttribute("myFormCartInfo") );
			      saveMessage("cadsr.common.formqueue.save.success",request, new Integer(formsInQueue).toString());
			      //saveMessage("cadsr.common.formqueue.save.success",request);

				}
		    catch (Exception exp) {
		        if (log.isErrorEnabled()) {
		          log.error("Exception on addItems " , exp);
		        }
		      }
		    


		return mapping.findForward("success");
	}
  
	public ActionForward addFormToCart(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		FormBuilderServiceDelegate service = getFormBuilderService();
		
	    DynaActionForm dynaBean2 = (DynaActionForm) form;
	    Form crf2 = null;
	    String showCached = (String)request.getAttribute("showCached");
		int formsInQueue = 0;
     	String[] sFormIdSeqA = (String[]) dynaBean2.get("checkedFormIds");
     	String sFormIdSeq = sFormIdSeqA[0];
     	
		try 
		{

			ensureSessionCarts(request);

			DynaActionForm dynaBean = (DynaActionForm) form;
			boolean clearCheckedFormIds = false;

			if (true)
			{ 
				log.debug("add-to-cart in memory - creating objects");
				Collection itemsToAdd = new ArrayList();

				CDECartOCImplExtension sessionCart = (CDECartOCImplExtension) this
						.getSessionObject(request, CaDSRConstants.FORMS_CART_V2);

				String[] formIds = (String[]) dynaBean.get("checkedFormIds");
								
				if (formIds != null) {
					for (String formId : formIds) {
						FormV2 crf = service.getFormDetailsV2(formId);
						itemsToAdd.add(crf);
					}
					sessionCart.addFormsV2(itemsToAdd);
					log.debug("done");
					
					clearCheckedFormIds = true;
				}
				this.setSessionObject(request, CaDSRConstants.FORMS_CART_V2, sessionCart);
				formsInQueue = sessionCart.getFormCartV2().size();
			}
//// GF32932  D.An, 20130825.  
		      request.getSession().setAttribute("myFormCartInfo", new Integer(formsInQueue).toString());
System.out.println( "Forms Queued in Cart : " + request.getSession().getAttribute("myFormCartInfo") );

		saveMessage("cadsr.common.formcart.add.success", request, new Integer(formsInQueue).toString());
		
		
		dynaBean.set("cartAddFormId", "");
		if (clearCheckedFormIds)
			dynaBean.set("checkedFormIds", new String[] {});
		} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
		
	    
	  		  
////Begin added for monitor if the current form has been added in Form Cart for saving.  -D.An, 20130830. 
		formsInQueue = 0;
	    String userMame = (String) request.getSession().getAttribute("myUsername");
	    if( userMame != null && userMame.equalsIgnoreCase("viewer") != true )
	    {
System.out.println("userName -- " + userMame );	
			  ////request.getSession().setAttribute("myFormAdded", "n");
System.out.println(" " + sFormIdSeq + " not found " );	
		
		   CDECartOCImplExtension sessionCartV2 = (CDECartOCImplExtension) this
					.getSessionObject(request, CaDSRConstants.FORMS_CART_V2);
		   Collection itemsAdded = null;
		   if( sessionCartV2 != null )
			   if( sessionCartV2.getFormCartV2().size() >= 0 )
				   itemsAdded = sessionCartV2.getFormCartV2().values();
		   
		   if ( itemsAdded != null )
		   {
			   for ( Object version2Form : itemsAdded ) 
			   {
				   if( ((FormV2TransferObject)version2Form).getFormIdseq().equals(sFormIdSeq) ) 
				   {
					   formsInQueue = 1;
					  //// request.getSession().setAttribute("myFormAdded", "Y");
System.out.println("sFormIdSeq found !!!!! -- " + sFormIdSeq );	
						break;
				   }
				}
			}
	    }
	   if( formsInQueue == 0 )
  			   request.getSession().setAttribute("myFormAdded", "n");
  	   else
			   request.getSession().setAttribute("myFormAdded", "Y");  		   
	    
//// End. -D.An, 20130830.
		
				return mapping.findForward("success");
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



  
  private Context getContextForId(List contexts,String contextIdSeq)
  {
    if(contexts==null||contextIdSeq==null)
      return null;
    ListIterator listIt = contexts.listIterator();
    while(listIt.hasNext())
    {
      Context currContext = (Context)listIt.next();
      if(currContext.getConteIdseq().equals(contextIdSeq))
      {
        return currContext;
      }
    }
    return null;
  }
}
  
