package gov.nih.nci.cadsr.formloader.struts2;

import gov.nih.nci.cadsr.formloader.domain.FormCollection;
import gov.nih.nci.cadsr.formloader.domain.FormDescriptor;
import gov.nih.nci.cadsr.formloader.service.common.FormLoaderServiceError;
import gov.nih.nci.cadsr.formloader.service.impl.CollectionRetrievalServiceImpl;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.opensymphony.xwork2.ActionSupport;

public class SearchLoadedCollectionAction extends ActionSupport implements SessionAware{
	
    private Map<Integer, String> checkboxes;
    private HttpServletRequest servletRequest;
    private List<FormCollection> selectedCollectionList = new ArrayList<FormCollection>();
    ApplicationContext applicationContext = null;
    HttpServletRequest req;
    		
	 public String execute() {
		 System.out.println("We are in XMLFileLoadedAction.execute()");
		 servletRequest = ServletActionContext.getRequest();
		 try {
			 
			  applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(ServletActionContext.getServletContext());
			//  CollectionRetrievalServiceImpl collectionRetrieval = (CollectionRetrievalServiceImpl)this.applicationContext.getBean("collectionRetrievalService");
			 // List<FormCollection> collectionList = collectionRetrieval.getAllCollections();
			  List<FormCollection> collectionList = this.getMockCollectionList();
			  
			  if (collectionList == null) {
				  //log or handler
				  return null;
			  }
			  
			  for (FormCollection aColl : collectionList) {
				  
				  
				  aColl.getName();
				  aColl.getDescription();
				  aColl.getDateCreated();
				  aColl.getCreatedBy();
				  
				  req.setAttribute("name", aColl.getName());
				  req.setAttribute("description", aColl.getDescription());
				  req.setAttribute("createdBy", aColl.getCreatedBy());
				  req.setAttribute("dateCreated", aColl.getDateCreated());
				//  req.setAttribute("IncludeViewPage", "ValueMeaningDetail.jsp") ;

				  
				  
				  System.out.print(aColl);
				  
				  				
				  List<FormDescriptor> forms = aColl.getForms();
				  for (FormDescriptor form : forms) {
					  form.getPublicId();
					  form.getVersion();
					  form.getLongName();
					  form.getContext();
					  form.getType();
					  form.getProtocolName();
					  form.getWorkflowStatusName();
					  form.getModifiedBy();
					  form.getFormIdString();
					
				  }
				  System.out.println(collectionList.size()+" is collectionList size");
				  servletRequest.getSession().setAttribute("collectionList", collectionList);
			        //	System.out.println(selectedCollectionList.size()+" Forms selected for validation");	 
				return SUCCESS;
				  
			  }
			  
			  
			
		/*	  
			  if (checkboxes != null && checkboxes.size()>0){
					
					for (int i=0; i<checkboxes.size();i++) {
						Integer key = new Integer(i);
						if (checkboxes.containsKey(key)) {
							String checkboxValue = checkboxes.get(key).toString();
							if (checkboxValue.equals("true")) {
								FormCollection selectedCollection = collectionList.get(i);
								selectedCollection.setSelected(true);
								selectedCollectionList.add(selectedCollection);
							}
						}
					}					
			   } */
	            
		
	 }catch (Exception e) {
         e.printStackTrace();
         System.err.println(e.getMessage());
         addActionError(e.getMessage());
      
     }
     return SUCCESS;
 }	 
			
	 public FormCollection generateContentValidationData(int idx) {
         FormCollection aColl = new FormCollection();
         List<FormLoaderServiceError> errors = new ArrayList<FormLoaderServiceError>();
           
           aColl.setCreatedBy("Denise");
           aColl.setDateCreated(new Date());
           aColl.setDescription("This is the collection from nci");
           aColl.setId("1234567");
           aColl.setName("Denise's Collection " + idx);
           aColl.setXmlFileName("denise-coll.xml");
           aColl.setXmlPathOnServer("/local/content/formloader/20130703");
           
           //1
           List<FormDescriptor> forms = new ArrayList<FormDescriptor>();
           FormDescriptor form = new FormDescriptor("443355", "1234345", "1.0");
           form.setContext("CTRP");
           //FormStatus status = new FormStatus(FormStatus.STATUS_DB_VALIDATED);
         // List<String> msgs = new ArrayList<String>();
           form.addMessage("Question 1 has no default text");
           form.addMessage("Question 2 need work");
           forms.add(form);
           
           //2
           form = new FormDescriptor("553355", "1234346", "3.0");
           form.setContext("NCIP");
         form.addMessage("No error / success");
           forms.add(form);
           
           //3
           form = new FormDescriptor("663355", "1234347", "4.0");
           form.setContext("NCIP");
           form.setLoadType(FormDescriptor.LOAD_TYPE_UPDATE_FORM);
           List<String> msgs3 = new ArrayList<String>();
           form.addMessage("Question 1 has no default text");
           form.addMessage("Question 2 need work");
           forms.add(form);
           
           //4
           form = new FormDescriptor("773355", "1234348", "2.0");
           form.setWorkflowStatusName("RELEASED");
           form.setContext("NCIP");
           form.setLoadType(FormDescriptor.LOAD_TYPE_UPDATE_FORM);
           form.addMessage("Question 3 has no default text");
           form.addMessage("Question 4 need work");
           forms.add(form);
           
           //5
         //public id = 0
           form = new FormDescriptor("883355", "0", "1.0");
           forms.add(form);
           //6
           forms.add(new FormDescriptor("883355", "1234349", "1.0"));
           
           aColl.setForms(forms);
           
           return aColl;
  }
  
  public List<FormCollection> getMockCollectionList() {
         List<FormCollection> colls = new ArrayList<FormCollection>();
         for (int i = 0; i < 10; i++) {
                colls.add(generateContentValidationData(i));
                System.out.println(colls);    
         }
         
         return colls;
  }
	  
	    public List<FormCollection> getSelectedCollectionList() {
			return selectedCollectionList;
		}

		public void setSelectedCollectionList(List<FormCollection> collectionList) {
			this.selectedCollectionList = collectionList;
		}

	    public Map<Integer, String> getCheckboxes() {
			return checkboxes;
		}
		public void setCheckboxes(Map<Integer, String> checkboxes) {
			this.checkboxes = checkboxes;
		}

		public void setSession(Map<String, Object> arg0) {
			// TODO Auto-generated method stub
			
		}


}