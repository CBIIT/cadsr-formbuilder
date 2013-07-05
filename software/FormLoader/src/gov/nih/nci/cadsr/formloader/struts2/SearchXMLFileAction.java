package gov.nih.nci.cadsr.formloader.struts2;

import gov.nih.nci.cadsr.formloader.object.XMLFileObj;
import gov.nih.nci.cadsr.formloader.service.MiscMockupService;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class SearchXMLFileAction extends ActionSupport  {
	
	private String filename;
	private List<String> uploadedFilenames;
	private String description;
	private List<String> descriptionList;
	private String fromDate;
	private String toDate;
	
	//private XMLFileObj xmlFile = new XMLFileObj();
	private List<XMLFileObj> xmlFileList = new ArrayList<XMLFileObj>();
	
	
	public String execute() {
		System.out.println("in execute() ... ");
		if (this.uploadedFilenames == null || this.uploadedFilenames.isEmpty())
			this.uploadedFilenames = MiscMockupService.getUploadedXMLFilenames();
		if (this.descriptionList == null || this.descriptionList.isEmpty())
			this.descriptionList = MiscMockupService.getUploadedXMLFiledescriptions();
		
		return SUCCESS;
	}
	
	public String searchAction(){
		System.out.println("in searchAction ... "+this.getFilename());
		if (this.uploadedFilenames == null || this.uploadedFilenames.isEmpty())
			this.uploadedFilenames = MiscMockupService.getUploadedXMLFilenames();
		if (this.descriptionList == null || this.descriptionList.isEmpty())
			this.descriptionList = MiscMockupService.getUploadedXMLFiledescriptions();
		
		if ((this.filename != null && !this.filename.equals("")) ||
			(this.description !=null && !this.description.equals("")) ||
			(this.toDate !=null && !this.toDate.equals("")) 
		)
			xmlFileList = MiscMockupService.getSearchXMLFiles(this.filename, this.description, this.fromDate, this.toDate);
		
		return SUCCESS;
	}
	
	public String selectAction(){
		System.out.println("in selectAction() ... ");
		xmlFileList = MiscMockupService.getSearchXMLFiles(this.filename, this.description, this.fromDate, this.toDate);
		
		return SUCCESS;
	}
	
	public String getFilename() {
		System.out.println("in getFilename() ... ");
		return this.filename;
	}
	
	public void setFilename(String fileName) {
		System.out.println("in setFilename(String fileName) ... ");
		this.filename = fileName;
		
	}

	public List<String> getUploadedFilenames() {
		System.out.println("in getUploadedFilenames() ...");
		return this.uploadedFilenames;
	}
	
	public void setUploadedFilenames(List<String> filenames) {
		System.out.println("in setUploadedFilenames(List<String> uploadedFilenames) ...");
		this.uploadedFilenames = filenames;
	}
	

	public List<XMLFileObj> getXmlFileList() {
		System.out.println("in  getXmlFileList()  ...");
		return xmlFileList;
	}

	public void setXmlFileList(List<XMLFileObj> xmlFileList) {
		System.out.println("in  setXmlFileList(List<XMLFileObj> xmlFileList)   ...");
		this.xmlFileList = xmlFileList;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getDescriptionList() {
		return descriptionList;
	}

	public void setDescriptionList(List<String> descriptionList) {
		this.descriptionList = descriptionList;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

}
