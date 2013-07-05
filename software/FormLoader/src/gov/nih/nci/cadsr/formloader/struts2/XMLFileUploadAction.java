package gov.nih.nci.cadsr.formloader.struts2;


import gov.nih.nci.cadsr.formloader.object.FormObj;
import gov.nih.nci.cadsr.formloader.service.ValidationMockupService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;
 
public class XMLFileUploadAction extends ActionSupport implements ServletRequestAware{
    private File xmlFile;
    private String xmlFileFileName;
    private String xmlFileContentType;
 
    private List<FormObj> parsedFormsList = new ArrayList<FormObj>();
    private HttpServletRequest servletRequest;
    
    public List<FormObj> getParsedFormsList() {
		return parsedFormsList;
	}


	public void setParsedFormsList(List<FormObj> parsedFormsList) {
		this.parsedFormsList = parsedFormsList;
	}
    
    public String execute() {
    	System.out.println("in execute() - filename:"+xmlFileFileName);
        try {
      
        	parsedFormsList = ValidationMockupService.getInstance().parseXMLFile(xmlFile, xmlFileFileName);
        	
        
        	servletRequest.getSession().putValue("parsedFormsList", parsedFormsList);
			
        	System.out.println(parsedFormsList.size()+" Forms parsed from file:"+xmlFileFileName);
        	
        } catch (Exception e) {
            e.printStackTrace();
            addActionError(e.getMessage());
 
            return INPUT;
        }
        
        if (!this.isValidated())
        	return ERROR;
        
        return SUCCESS;
    }
 
    private boolean isValidated() {
    	boolean isValidated = true;
    	for (int i=0; i<parsedFormsList.size();i++) {
    		FormObj one = parsedFormsList.get(i);
    		try {
   
    			one = ValidationMockupService.getInstance().validateForm(one);
    		} catch (Exception e) {
                e.printStackTrace();
               	one.setErrorMessage(e.getMessage());
                isValidated = false;
            }					
		}
    	return isValidated;
    }

	public File getXmlFile() {
		return xmlFile;
	}


	public void setXmlFile(File xmlFile) {
		this.xmlFile = xmlFile;
	}


	public String getXmlFileFileName() {
		return xmlFileFileName;
	}


	public void setXmlFileFileName(String xmlFileName) {
		this.xmlFileFileName = xmlFileName;
	}


	public String getXmlFileContentType() {
		return xmlFileContentType;
	}


	public void setXmlFileContentType(String xmlFileContentType) {
		this.xmlFileContentType = xmlFileContentType;
	}
	

    public void setServletRequest(HttpServletRequest servletRequest) {
        this.servletRequest = servletRequest;
 
    }
}