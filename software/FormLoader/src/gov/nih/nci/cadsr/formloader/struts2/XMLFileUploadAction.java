package gov.nih.nci.cadsr.formloader.struts2;


import gov.nih.nci.cadsr.formloader.domain.FormCollection;
import gov.nih.nci.cadsr.formloader.domain.FormDescriptor;
import gov.nih.nci.cadsr.formloader.service.common.FormLoaderHelper;
import gov.nih.nci.cadsr.formloader.service.common.FormLoaderServiceException;
import gov.nih.nci.cadsr.formloader.service.impl.CollectionRetrievalServiceImpl;
import gov.nih.nci.cadsr.formloader.service.impl.XmlValidationServiceImpl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ValidationAware;
 
public class XMLFileUploadAction extends ActionSupport implements
ServletRequestAware, ValidationAware{
	
	private static Logger logger = Logger.getLogger(XMLFileUploadAction.class.getName());
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ApplicationContext applicationContext = null;
	
	private File file;
	private String fileName;
	private String contentType;
	
    private String collectionName;
	private String description;
	 
    private HttpServletRequest servletRequest;
    
    private List<FormDescriptor> parsedFormsList = null;
    private List<FormDescriptor> parsedFormsNoErrorList = null;
    private List<FormDescriptor> parsedFormsWithErrorList = null;
    //private Properties configProp;
    private File uploadedfile = null;
    private XmlValidationServiceImpl xmlValidator = null;
    private boolean clear=false;
   
    
    public File getFile() {
		return file;
	}


	//public String getCollectionName() {
	//	return collectionName;
	//}


	public String getContentType() {
		return contentType;
	}

    
    public List<FormDescriptor> getParsedFormsList() {
		return parsedFormsList;
	}


	public void setParsedFormsList(List<FormDescriptor> parsedFormsList) {
		this.parsedFormsList = parsedFormsList;
	}
    
    public String execute() {
    	servletRequest = ServletActionContext.getRequest();
    	
    	if (this.file == null) {
    		addActionError("Uploaded file is null");
    		return ERROR;
    	}
	   
    	if (saveUploadedFile(this.file) > 0) {
			return validateXML();
	    }
       
    	return ERROR;
    }
    
    private int saveUploadedFile(File xmlFile)
    {	
    	InputStream inStream = null;
    	OutputStream outStream = null;
 
    	String filePath = servletRequest.getSession().getServletContext().getRealPath("/");
    	String uploadFilePath = FormLoaderHelper.getProperty(filePath, "upload.file.path");
    	//loadProps();
    	 	
    	try
	    	{
    		
    		uploadedfile = new File(uploadFilePath +"\\" + this.fileName);
	 
	    	    inStream = new FileInputStream(xmlFile);
	    	    outStream = new FileOutputStream(uploadedfile);
	 
	    	    byte[] buffer = new byte[1024];
	 
	    	    int length;
	    	    int total = 0;
	    	    //copy the file content in bytes 
	    	    while ((length = inStream.read(buffer)) > 0) {
	    	    	outStream.write(buffer, 0, length);
	    	    	total += length;
	    	    }
	 
	    	    inStream.close();
	    	    outStream.close();
	 
	    	    System.out.println("File is copied successful!");
	    	    return total;
	 
	    	}
    	catch(IOException e)
	    	{
	    		logger.error("Got IOException while saving upload file to destination: "  + e.getMessage());
	    		return 0;
	    	}
	    	
    }

	private String validateXML()
	{
		applicationContext =
				WebApplicationContextUtils.getRequiredWebApplicationContext(
	                                    ServletActionContext.getServletContext()
	                        );
		XmlValidationServiceImpl xmlValidator = (XmlValidationServiceImpl)this.applicationContext.getBean("xmlValidationService");
		
		String userName = (String)servletRequest.getSession().getAttribute("username");
		
		FormCollection aColl = new FormCollection();
		aColl.setXmlPathOnServer(FormLoaderHelper.getProperty("", "upload.file.path") +"\\");
		servletRequest.getSession().setAttribute("upload.file.path", FormLoaderHelper.getProperty("", "upload.file.path") +"\\");
		aColl.setXmlFileName(this.fileName);
		//aColl.setDescription(description);
		//aColl.setName(collectionName);
		aColl.setCreatedBy(userName.toUpperCase());
		servletRequest.getSession().setAttribute("filename", this.fileName);
		try {
			aColl = xmlValidator.validateXml(aColl);
			parsedFormsList = aColl.getForms();
			sortForms();
			
			servletRequest.getSession().setAttribute("formCollection", aColl);
			//servletRequest.getSession().setAttribute("collectionName",  aColl.getName());
			this.description = aColl.getDescription();
			this.collectionName = aColl.getNameWithRepeatIndicator();
        	//servletRequest.getSession().setAttribute("parsedFormsList", parsedFormsList);
        	System.out.println(parsedFormsList.size()+" Parsed Forms ");
        	
        	return SUCCESS;
		} catch (FormLoaderServiceException e) {
			handleFormLoaderServiceException(e);
			return ERROR;
		}
	}
	
	protected void handleFormLoaderServiceException(FormLoaderServiceException e) {
		String msg;
		
		int code = e.getErrorCode();
		
		if (code == FormLoaderServiceException.ERROR_MALFORMED_XML) {
			addActionError("Your form collection xml file is malformed. Please correct it and try again.");
		}
		else if (code == FormLoaderServiceException.ERROR_COLLECTION_NAME_MISSING)
			addActionError("Your form collection xml file is missing a required element. Please correct it and try again."); 
	
		addActionError("Error: " + e.getMessage());
	}
	
	private void sortForms()
		{
		    parsedFormsNoErrorList = new ArrayList<FormDescriptor>();
		    parsedFormsWithErrorList = new ArrayList<FormDescriptor>();
		    for (FormDescriptor each : parsedFormsList)
		    {
		    	if (each.getXmlValidationErrorString().isEmpty())
			    	{
		    			parsedFormsNoErrorList.add(each);
			    	}
		    	else
			    	{
		    			parsedFormsWithErrorList.add(each);
			    	}
		    }
	    	servletRequest.getSession().setAttribute("parsedFormsNoErrorList", parsedFormsNoErrorList);
	    	servletRequest.getSession().setAttribute("parsedFormsWithErrorList", parsedFormsWithErrorList);
		}
	
	public void setUpload(File xmlFile) 
		{
			this.file = xmlFile;
		}
	
	public void setUploadFileName(String xmlFileName) {
		this.fileName = xmlFileName;
	}

	public void setUploadContentType(String xmlFileContentType) {
		this.contentType = xmlFileContentType;
	}

	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		this.servletRequest = arg0;
	}
	
	public String clear()
	{
		return SUCCESS;
	}
	
	public void validateClear(){
		clear = true;
	}
	
	public void validate(){
		if (!clear)
		{
		if((file == null)){
			addFieldError("upload", getText("file is required"));
		}
		//if(collectionName == null || collectionName.isEmpty()){
		//	addFieldError("collectionName", getText("collectionName is required"));
		//}
		}
	}


	public String getCollectionName() {
		return collectionName;
	}

	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<FormDescriptor> getParsedFormsNoErrorList() {
		return parsedFormsNoErrorList;
	}

	public void setParsedFormsNoErrorList(
			List<FormDescriptor> parsedFormsNoErrorList) {
		this.parsedFormsNoErrorList = parsedFormsNoErrorList;
	}

	public List<FormDescriptor> getParsedFormsWithErrorList() {
		return parsedFormsWithErrorList;
	}

	public void setParsedFormsWithErrorList(
			List<FormDescriptor> parsedFormsWithErrorList) {
		this.parsedFormsWithErrorList = parsedFormsWithErrorList;
	}
}