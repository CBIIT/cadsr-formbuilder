package gov.nih.nci.cadsr.formloader.struts2;


import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import gov.nih.nci.cadsr.formloader.domain.FormCollection;
import gov.nih.nci.cadsr.formloader.domain.FormDescriptor;
import gov.nih.nci.cadsr.formloader.object.FormObj;
import gov.nih.nci.cadsr.formloader.service.ValidationMockupService;
import gov.nih.nci.cadsr.formloader.service.common.FormLoaderServiceException;
import gov.nih.nci.cadsr.formloader.service.impl.XmlValidationServiceImpl;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.UserManagerDAO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import com.opensymphony.xwork2.ActionContext;
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
    private Properties configProp;
    private File uploadedfile = null;
    private XmlValidationServiceImpl xmlValidator = null;
	private List<FormDescriptor> forms;
    
    public void loadProps() {
    	InputStream in = null;
        configProp = new Properties();
    	
    	servletRequest = ServletActionContext.getRequest();
    	String filePath = servletRequest.getSession().getServletContext().getRealPath("/");
    	System.out.println("Server path:" + filePath);
    	
        try {
            File f = new File(filePath+"/WEB-INF/config.properties");
            in = new FileInputStream( f );
            configProp.load(in);
            System.out.println(configProp.getProperty("upload.file.path"));
        }
        catch ( Exception e ) { in = null; }
               
        try {
            if ( in == null ) {
                // Try loading from classpath
            	in = this.getClass().getResourceAsStream(filePath+"/WEB-INF/config.properties");
                // Try loading properties from the file (if found)
                configProp.load(in);
                System.out.println(configProp.getProperty("upload.file.path"));
            }
        }
        catch ( Exception e ) { }
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
    			uploadedfile = new File(configProp.getProperty("upload.file.path") +"\\" + this.fileName);
	 
	    	    inStream = new FileInputStream(xmlFile);
	    	    outStream = new FileOutputStream(uploadedfile);
	 
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
		this.file = xmlFile;
	}

	private void validateXML()
	{
		
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"/applicationContext-service-test-db.xml");
		XmlValidationServiceImpl xmlValidator = (XmlValidationServiceImpl)applicationContext.getBean("XmlValidationServiceImpl");
		
		FormCollection aColl = new FormCollection();
		aColl.setXmlPathOnServer(".\\test\\data");
		aColl.setXmlFileName("3193449_has_valid_values.xml");
		try {
			aColl = xmlValidator.validateXml(aColl);
			forms = aColl.getForms();
		} catch (FormLoaderServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void setUploadFileName(String xmlFileName) {
		this.fileName = xmlFileName;
		saveUploadedFile(this.file);
		validateXML();
	}

	public void setUploadContentType(String xmlFileContentType) {
		this.contentType = xmlFileContentType;
	}

	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		this.servletRequest = arg0;
		
	}
}