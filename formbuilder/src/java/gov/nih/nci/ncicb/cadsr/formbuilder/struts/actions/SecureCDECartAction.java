package gov.nih.nci.ncicb.cadsr.formbuilder.struts.actions;

import gov.nih.nci.ncicb.cadsr.common.CaDSRConstants;
import gov.nih.nci.ncicb.cadsr.common.downloads.GetExcelDownload;
import gov.nih.nci.ncicb.cadsr.common.downloads.GetXMLDownload;
import gov.nih.nci.ncicb.cadsr.common.downloads.impl.GetExcelDownloadImpl;
import gov.nih.nci.ncicb.cadsr.common.downloads.impl.GetXMLDownloadImpl;
import gov.nih.nci.ncicb.cadsr.common.dto.QuestionTransferObject;
import gov.nih.nci.ncicb.cadsr.common.resource.DataElement;
import gov.nih.nci.ncicb.cadsr.common.resource.Form;
import gov.nih.nci.ncicb.cadsr.common.resource.Module;
import gov.nih.nci.ncicb.cadsr.common.resource.NCIUser;
import gov.nih.nci.ncicb.cadsr.common.resource.Question;
import gov.nih.nci.ncicb.cadsr.common.struts.formbeans.CDECartFormBean;
import gov.nih.nci.ncicb.cadsr.common.util.CDEBrowserParams;
import gov.nih.nci.ncicb.cadsr.common.util.ContentTypeHelper;
import gov.nih.nci.ncicb.cadsr.common.util.DTOTransformer;
import gov.nih.nci.ncicb.cadsr.formbuilder.common.FormBuilderException;
import gov.nih.nci.ncicb.cadsr.formbuilder.service.FormBuilderServiceDelegate;
import gov.nih.nci.ncicb.cadsr.formbuilder.struts.common.FormActionUtil;
import gov.nih.nci.ncicb.cadsr.objectCart.CDECart;
import gov.nih.nci.ncicb.cadsr.objectCart.CDECartItem;
import gov.nih.nci.ncicb.cadsr.objectCart.impl.CDECartOCImpl;
import gov.nih.nci.objectCart.client.ObjectCartClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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

    Collection col = sessionCart.getDataElements();
    ArrayList al = new ArrayList(col);

    int displayOrder = Integer.parseInt((String) dynaForm.get(QUESTION_INDEX));

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

    int questionIndex =
      Integer.parseInt((String) dynaForm.get("questionIndex"));

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
      
      CDECart userCart = new CDECartOCImpl(cartClient, user.getUsername(),CaDSRConstants.CDE_CART);
      
      this.setSessionObject(request, CaDSRConstants.CDE_CART, userCart);
    }
    catch (Exception exp) {
      if (log.isErrorEnabled()) {
        log.error("Exception on displayCDECart", exp);
      }
      saveMessage(exp.getMessage(), request);
    }

    return mapping.findForward(SUCCESS);
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
/*      String userName = getLoggedInUsername(request);
     // FormBuilderServiceDelegate service = getFormBuilderService();
      CDECartFormBean myForm = (CDECartFormBean) form;
      String[] selectedSaveItems = myForm.getSelectedSaveItems();
      Collection items = new ArrayList();

      for (int i = 0; i < selectedSaveItems.length; i++) {
        CDECartItem cartItem = new CDECartItemTransferObject();
        cartItem.setId(selectedSaveItems[i]);
        cartItem.setType("DATAELEMENT");
        items.add(cartItem);
      }

    //  service.addToCDECart(items,userName);

*/      CDECart cart =
        (CDECart) this.getSessionObject(request, CaDSRConstants.CDE_CART);
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
      //String userName = getLoggedInUsername(request);
      //FormBuilderServiceDelegate service = getFormBuilderService();
      CDECartFormBean myForm = (CDECartFormBean) form;
      String[] selectedDeleteItems = myForm.getSelectedDeleteItems();
      Collection savedItems = new ArrayList();

      //Collection unsavedItems = new ArrayList();
      Collection items = new ArrayList();

      //Get the cart in the session
      CDECart sessionCart =
        (CDECart) this.getSessionObject(request, CaDSRConstants.CDE_CART);
      CDECartItem item = null;

      for (int i = 0; i < selectedDeleteItems.length; i++) {
//        item = sessionCart.findDataElement(selectedDeleteItems[i]);

//        if (item.getPersistedInd()) {
//          savedItems.add(selectedDeleteItems[i]);
//        }

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
  
  public ActionForward downloadExcel(
		    ActionMapping mapping,
		    ActionForm form,
		    HttpServletRequest request,
		    HttpServletResponse response) throws IOException, ServletException 
  {
	try
	{
	//  DynaActionForm dynaForm = (DynaActionForm) form;
      CDECart sessionCart =
          (CDECart) this.getSessionObject(request, CaDSRConstants.CDE_CART);
      GetExcelDownload excelDown = new GetExcelDownloadImpl();
      excelDown.generateExcelForCDECart(sessionCart, "cdeCart", null);
      String fileName = excelDown.getFileName();
      this.displayExcel(request, response, fileName);
	}
	catch (Exception ex) {
		log.error("Error generating excel file", ex);
		return mapping.findForward(SUCCESS);
	}
	finally {
	}
	return mapping.findForward(SUCCESS);
  }	
  
  public ActionForward downloadXML(
		    ActionMapping mapping,
		    ActionForm form,
		    HttpServletRequest request,
		    HttpServletResponse response) throws IOException, ServletException 
  {
	try
	{
	    CDECart sessionCart =
	        (CDECart) this.getSessionObject(request, CaDSRConstants.CDE_CART);
	    GetXMLDownload xmlDown = new GetXMLDownloadImpl();
	    xmlDown.generateXMLForCDECart(sessionCart, "cdeCart", null);
	    String fileName = xmlDown.getFileName("");
	    this.displayXML(request, response, fileName);
	}
	catch (Exception ex) {
		log.error("Error generating excel file", ex);
		return mapping.findForward(SUCCESS);
	}
	finally {
	}
	return mapping.findForward(SUCCESS);
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
  
  private void displayExcel(
			HttpServletRequest request,
		    HttpServletResponse response,
		    String excelFilename) throws Exception
	{
		  File f = new File(excelFilename);
		  String ctype = ContentTypeHelper.getContentType(f.getName());

		  response.setContentType(ctype);
		  response.setContentLength((int)f.length());
		  response.addHeader("Content-Disposition", "attachment; filename=" + f.getName());
		  response.addHeader("Pragma", "No-cache");
		  response.addHeader("Cache-Control", "no-cache");
		  response.addHeader("Expires", "0");

		  try {
			   // create buffer			
			   byte [] buffer = new byte[1024];
	
			   int r = 0;
			   // write out file			
			   FileInputStream fin = new FileInputStream(f);
			   OutputStream out = response.getOutputStream();
	
			   while ((r = fin.read(buffer, 0, buffer.length)) != -1) {
			    out.write(buffer, 0, r);
			   }
	
			   try {
				    fin.close();		
				    out.flush();
				    out.close();
			   } catch (Exception e) { }
	
			   buffer = null;
		  } catch (Exception ex) {
			   String msg = ex.getMessage();
	
			   response.setContentType("text/html");
			   response.setContentLength(msg.length());
			   PrintWriter out = response.getWriter();
			   out.println("Unexpected error");
			   out.flush();
			   out.close();
		  }
		
	}
 
  private void displayXML(
			HttpServletRequest request,
		    HttpServletResponse response,
		    String fileName) throws Exception
	{
		  File f = new File(fileName);
		  String ctype = ContentTypeHelper.getContentType(f.getName());

		  response.setContentType(ctype);
		  response.setContentLength((int)f.length());
		  response.addHeader("Content-Disposition", "attachment; filename=" + f.getName());
		  response.addHeader("Pragma", "No-cache");
		  response.addHeader("Cache-Control", "no-cache");
		  response.addHeader("Expires", "0");

		  try {
			   // create buffer			
			   byte [] buffer = new byte[1024];
	
			   int r = 0;
			   // write out file			
			   FileInputStream fin = new FileInputStream(f);
			   OutputStream out = response.getOutputStream();
	
			   while ((r = fin.read(buffer, 0, buffer.length)) != -1) {
			    out.write(buffer, 0, r);
			   }
	
			   try {
				    //fin.close();		
				    out.flush();
				    out.close();
			   } catch (Exception e) { }
	
			   buffer = null;
		  } catch (Exception ex) {
			   String msg = ex.getMessage();
	
			   response.setContentType("text/html");
			   response.setContentLength(msg.length());
			   PrintWriter out = response.getWriter();
			   out.println("Unexpected error");
			   out.flush();
			   out.close();
		  }
		
	}

}
