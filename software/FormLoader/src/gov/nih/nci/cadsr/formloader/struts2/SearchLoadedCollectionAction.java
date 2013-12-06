package gov.nih.nci.cadsr.formloader.struts2;

import gov.nih.nci.cadsr.formloader.domain.FormCollection;
import gov.nih.nci.cadsr.formloader.domain.FormDescriptor;
import gov.nih.nci.cadsr.formloader.repository.FormLoaderRepositoryImpl;
import gov.nih.nci.cadsr.formloader.service.common.FormLoaderHelper;
import gov.nih.nci.cadsr.formloader.service.common.FormLoaderServiceError;
import gov.nih.nci.cadsr.formloader.service.impl.CollectionRetrievalServiceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.opensymphony.xwork2.ActionSupport;

public class SearchLoadedCollectionAction extends ActionSupport implements
		SessionAware {

	public static final String SORT_BY_NAME = "name";
	public static final String SORT_BY_NAME_REVERSE = "name-rev";
	public static final String SORT_BY_DATE = "date";
	public static final String SORT_BY_DATE_REVERSE = "date-rev";
	
	private static Logger logger = Logger.getLogger(SearchLoadedCollectionAction.class.getName());
	
	private HttpServletRequest servletRequest;
	private List<FormCollection> collectionList = null;
	ApplicationContext applicationContext = null;
	private String userName;
	
	private List<FormDescriptor> unloadableForms = null;
	private List<FormCollection> collection1Page = new ArrayList<FormCollection>();

	public String execute() {
		logger.debug("We are in XMLFileLoadedAction.execute()");
		servletRequest = ServletActionContext.getRequest();
		try {

			applicationContext = WebApplicationContextUtils
					.getRequiredWebApplicationContext(ServletActionContext
							.getServletContext());
			CollectionRetrievalServiceImpl collectionRetrieval =
					(CollectionRetrievalServiceImpl)this.applicationContext.getBean("collectionRetrievalService");
			
			userName = (String)servletRequest.getSession().getAttribute("username");
			if (userName == null || userName.length() == 0) {
				addActionError("User name is not available. Unable to continue");
				return ERROR;
			}
			
			userName = userName.toUpperCase();
			
			collectionList = collectionRetrieval.getAllCollectionsByUser(userName);

			if (collectionList == null) {
				logger.debug("Collection list is null.");
				addActionError("Unable to retrieve collection list from database for unknown reason");
				return ERROR;
			}

			logger.debug("User [" + userName + "] has previously loaded " + collectionList.size() + " collections.");
			servletRequest.getSession().setAttribute("collectionList", collectionList);
			
			unloadableForms = this.convertCollectionsToUnloadedFormList(collectionList);
			
//			if (unloadableForms == null) {
//				addActionError("Loaded form list is null.");
//				return ERROR;
//			}
			
			logger.debug("User [" + userName + "] has previously loaded and unloadable " + unloadableForms.size() + " forms.");
			
			return SUCCESS;

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			addActionError(e.getMessage());
		}
		
		return ERROR;
	}
	
	public String sortCollectionsByName() {
		servletRequest = ServletActionContext.getRequest();
		collectionList = (List<FormCollection>) servletRequest.getSession().getAttribute("collectionList");
		unloadableForms = this.convertCollectionsToUnloadedFormList(collectionList);
		
		String sorted = (String) servletRequest.getSession().getAttribute("sortField");
		if (sorted == null || sorted.length() == 0 || sorted.equals(SearchLoadedCollectionAction.SORT_BY_NAME_REVERSE)) {
			collectionList = FormLoaderHelper.sortCollectionsByName(collectionList);
			sorted = SearchLoadedCollectionAction.SORT_BY_NAME;
		} else {
			collectionList = FormLoaderHelper.reverseSortCollectionsByName(collectionList);
			sorted = SearchLoadedCollectionAction.SORT_BY_NAME_REVERSE;
		}
		
		servletRequest.getSession().setAttribute("collectionList", collectionList);
		servletRequest.getSession().setAttribute("sortField", sorted);
		return SUCCESS;
	}
	
	public String sortCollectionsByDate() {
		servletRequest = ServletActionContext.getRequest();
		collectionList = (List<FormCollection>) servletRequest.getSession().getAttribute("collectionList");
		unloadableForms = this.convertCollectionsToUnloadedFormList(collectionList);
		
		String sorted = (String) servletRequest.getSession().getAttribute("sortField");
		if (sorted == null || sorted.length() == 0 || sorted.equals(SearchLoadedCollectionAction.SORT_BY_DATE_REVERSE)) {
			collectionList = FormLoaderHelper.sortCollectionsByName(collectionList);
			sorted = SearchLoadedCollectionAction.SORT_BY_DATE;
		} else {
			collectionList = FormLoaderHelper.reverseSortCollectionsByName(collectionList);
			sorted = SearchLoadedCollectionAction.SORT_BY_DATE_REVERSE;
		}
		
		servletRequest.getSession().setAttribute("collectionList", collectionList);
		servletRequest.getSession().setAttribute("sortField", sorted);
		return SUCCESS;
	}

	public List<FormCollection> getCollectionList() {
		return collectionList;
	}

	public void setCollectionList(List<FormCollection> collectionList) {
		this.collectionList = collectionList;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public void setSession(Map<String, Object> arg0) {
		// TODO Auto-generated method stub

	}	
	
	public List<FormDescriptor> getUnloadableForms() {
		return unloadableForms;
	}

	public void setUnloadableForms(List<FormDescriptor> unloadableForms) {
		this.unloadableForms = unloadableForms;
	}

	public List<FormCollection> getCollection1Page() {
		return collection1Page;
	}

	public void setCollection1Page(List<FormCollection> collection1Page) {
		this.collection1Page = collection1Page;
	}

	protected List<FormDescriptor> convertCollectionsToUnloadedFormList(List<FormCollection> colls) {
		
		if (colls == null || colls.size() == 0) 
			return new ArrayList<FormDescriptor>();
		
		Map<String, FormDescriptor> formTracker = new HashMap<String, FormDescriptor>();
		
		String seq = "";
		for (FormCollection coll : colls) {
			List<FormDescriptor> forms = coll.getForms();
			for (FormDescriptor form : forms) {
				if (form.getLoadStatus() != FormDescriptor.STATUS_LOADED)
					continue;
				
				if (form.isUnloadable() == false)
					continue;
				
				String key = form.getFormSeqId();
				
				if (!formTracker.containsKey(key)) {
					form.getBelongToCollections().add(coll);
					formTracker.put(key, form);
				} else {
					formTracker.get(key).getBelongToCollections().add(coll);
				}
			}
		}
		
		return new ArrayList<FormDescriptor>(formTracker.values());
	}

}