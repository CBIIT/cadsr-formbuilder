package gov.nih.nci.cadsr.formloader.struts2;


	import gov.nih.nci.cadsr.formloader.domain.FormCollection;
	import gov.nih.nci.cadsr.formloader.domain.FormDescriptor;
	import gov.nih.nci.cadsr.formloader.service.common.FormLoaderServiceException;
	import gov.nih.nci.cadsr.formloader.service.impl.XmlValidationServiceImpl;

	import java.io.File;
	import java.io.FileInputStream;
	import java.io.FileOutputStream;
	import java.io.IOException;
	import java.io.InputStream;
	import java.io.OutputStream;
	import java.util.List;
	import java.util.Properties;

	import javax.servlet.http.HttpServletRequest;

	import org.apache.struts2.ServletActionContext;
	import org.apache.struts2.interceptor.ServletRequestAware;
	import org.springframework.context.ApplicationContext;
	import org.springframework.web.context.support.WebApplicationContextUtils;

	import com.opensymphony.xwork2.ActionSupport;
	import com.opensymphony.xwork2.ValidationAware;
	 
	public class XMLFileUploadInitialAction extends ActionSupport implements
	ServletRequestAware, ValidationAware{
	    /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		ApplicationContext applicationContext = null;
		
		private File file;
	    private String collectionName;
		private String contentType;
		 
	    private HttpServletRequest servletRequest;
	    
	    private List<FormDescriptor> parsedFormsList = null;
	    private Properties configProp;
	    private File uploadedfile = null;
	    private XmlValidationServiceImpl xmlValidator = null;
	    private boolean clear=false;
	    
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


		public String getCollectionName() {
			return collectionName;
		}


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
	    	if (this.file!=null)
		    	{
					saveUploadedFile(this.file);
					validateXML();
				    return SUCCESS;
		    	}
	        return ERROR;
	    }
	 
//	    private boolean isValidated() {
//	    	boolean isValidated = true;
//	    	for (int i=0; i<parsedFormsList.size();i++) {
//	    		FormObj one = parsedFormsList.get(i);
//	    		try {
	//   
//	    			one = ValidationMockupService.getInstance().validateForm(one);
//	    		} catch (Exception e) {
//	                e.printStackTrace();
//	               	one.setErrorMessage(e.getMessage());
//	                isValidated = false;
//	            }					
//			}
//	    	return isValidated;
//	    }
	    
	    private void saveUploadedFile(File xmlFile)
	    {	
	    	InputStream inStream = null;
	    	OutputStream outStream = null;
	 
	    	loadProps();
	    	
	    	try
		    	{
	    			uploadedfile = new File(configProp.getProperty("upload.file.path") +"\\" + this.collectionName);
		 
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
			this.file = xmlFile;
		}

		private void validateXML()
		{
			applicationContext =
					WebApplicationContextUtils.getRequiredWebApplicationContext(
		                                    ServletActionContext.getServletContext()
		                        );
			XmlValidationServiceImpl xmlValidator = (XmlValidationServiceImpl)this.applicationContext.getBean("xmlValidationService");
			
			FormCollection aColl = new FormCollection();
			aColl.setXmlPathOnServer(configProp.getProperty("upload.file.path") +"\\");
			servletRequest.getSession().setAttribute("upload.file.path", configProp.getProperty("upload.file.path") +"\\");
			aColl.setXmlFileName(this.collectionName);
			servletRequest.getSession().setAttribute("filename", this.collectionName);
			try {
				aColl = xmlValidator.validateXml(aColl);
				parsedFormsList = aColl.getForms();
	        	servletRequest.getSession().setAttribute("parsedFormsList", parsedFormsList);
	        	System.out.println(parsedFormsList.size()+" Parsed Forms ");
			} catch (FormLoaderServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		public void setUploadFileName(String xmlFileName) {
			this.collectionName = xmlFileName;
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
		
}
