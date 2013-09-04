package gov.nih.nci.cadsr.formloader.struts2;


import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;

import gov.nih.nci.cadsr.formloader.domain.FormCollection;
import gov.nih.nci.cadsr.formloader.object.FormObj;
import gov.nih.nci.cadsr.formloader.service.ValidationMockupService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import com.opensymphony.xwork2.ActionSupport;
 
public class XMLFileUploadAction extends ActionSupport implements
ServletRequestAware{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private File file;
    private String fileName;
	private String contentType;
	 
    private FormCollection formCollection;
    private HttpServletRequest servletRequest;
    
    private List<FormObj> parsedFormsList = new ArrayList<FormObj>();
    private Properties configProp = new Properties();
    
    public void loadProps() {
    	System.getProperty("user.dir");
    	System.getProperty("user.home");
    	
    	servletRequest = ServletActionContext.getRequest();
    	String filePath = servletRequest.getSession().getServletContext().getRealPath("/");
    	System.out.println("Server path:" + filePath);
    	
        InputStream in = this.getClass().getResourceAsStream(filePath+"/resources/config.properties");
        try {
            configProp.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public File getFile() {
		return file;
	}


	public String getFileName() {
		return fileName;
	}


	public String getContentType() {
		return contentType;
	}


	public FormCollection getFormCollection() {
		return formCollection;
	}


    
    public List<FormObj> getParsedFormsList() {
		return parsedFormsList;
	}


	public void setParsedFormsList(List<FormObj> parsedFormsList) {
		this.parsedFormsList = parsedFormsList;
	}
    
    public String execute() {
    	servletRequest = ServletActionContext.getRequest();
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
    
    private void saveUploadedFile(File xmlFile)
    {	
    	InputStream inStream = null;
    	OutputStream outStream = null;
 
    	loadProps();
    	
    	try
	    	{
	    	    File bfile = new File(configProp.getProperty("upload.file.path") + xmlFile.getName());
	 
	    	    inStream = new FileInputStream(xmlFile);
	    	    outStream = new FileOutputStream(bfile);
	 
	    	    byte[] buffer = new byte[1024];
	 
	    	    int length;
	    	    //copy the file content in bytes 
	    	    while ((length = inStream.read(buffer)) > 0){
	 
	    	    	outStream.write(buffer, 0, length);
	 
	    	    }
	 
	    	    inStream.close();
	    	    outStream.close();
	 
	    	    System.out.println("File is copied successful!");
	 
	    	}
    	catch(IOException e)
	    	{
	    		e.printStackTrace();
	    	}
    }

	public void setUpload(File xmlFile) {
		formCollection = new FormCollection();
		saveUploadedFile(xmlFile);
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
}