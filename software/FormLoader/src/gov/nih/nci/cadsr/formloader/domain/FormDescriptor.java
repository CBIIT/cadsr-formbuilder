package gov.nih.nci.cadsr.formloader.domain;

import gov.nih.nci.cadsr.formloader.service.common.FormLoaderServiceError;

import java.util.ArrayList;
import java.util.List;

public class FormDescriptor {
	
	public static final String LOAD_TYPE_NEW = "New Form";
	public static final String LOAD_TYPE_NEW_VERSION = "New Version";
	public static final String LOAD_TYPE_UPDATE_FORM = "Update Form";
	public static final String LOAD_TYPE_DUPLICATE_FORM = "Duplicate Form"; 
	public static final String LOAD_TYPE_UNKNOWN = "Unknown";
	
	public static final int STATUS_ERROR = -1;
	public static final int STATUS_XML_VALIDATION_FAILED = -2;
	
	public static final int STATUS_INITIALIZED = 0;
	public static final int STATUS_XML_VALIDATED = 1; 
	public static final int STATUS_DB_VALIDATED = 3;
	public static final int STATUS_LOADED = 4;
	public static final int STATUS_UNLOADED = 5;
	
	String formSeqId;
	String publicId;
	String version;
	
	String longName;
	String context;
	String type;
	String ProtocolName;
	String workflowStatusName;
	
	List<ModuleDescriptor> modules = new ArrayList<ModuleDescriptor>();
	
	//Any error (xml, content validation, etc) will be here
	List<FormLoaderServiceError> errors = new ArrayList<FormLoaderServiceError>();
	List<String> messages = new ArrayList<String>();
	
	//Load services only: new form, new version or update
	String loadType;
	int loadStatus;
	//FormStatus status;

	protected transient boolean selected;
	protected transient int xml_line_begin;
	protected transient int xml_line_end;
	
	public FormDescriptor() {
		loadStatus = STATUS_INITIALIZED;
		loadType = LOAD_TYPE_UNKNOWN;
	}
	
	/**
	 * This intends to be called by front end or web service to gather all errors/info with the form and its
	 * questions.
	 * 
	 * @return
	 */
	public String getStructuredMessages() {
		return "Not yet implemented";
	}
	
	public FormDescriptor(String id, String publicId, String version) {
		this.formSeqId = id;
		this.publicId = publicId;
		this.version = version;
		loadStatus = STATUS_INITIALIZED;
		loadType = LOAD_TYPE_UNKNOWN;
	}
	
	public String getFormSeqId() {
		return formSeqId;
	}
	public void setFormSeqId(String formSeqId) {
		this.formSeqId = formSeqId;
	}
	
	public String getPublicId() {
		return publicId;
	}
	public void setPublicId(String publicId) {
		this.publicId = publicId;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getLongName() {
		return longName;
	}
	public void setLongName(String longName) {
		this.longName = longName;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getProtocolName() {
		return ProtocolName;
	}
	public void setProtocolName(String protocolName) {
		ProtocolName = protocolName;
	}
	public String getWorkflowStatusName() {
		return workflowStatusName;
	}
	public void setWorkflowStatusName(String workflowStatus) {
		this.workflowStatusName = workflowStatus;
	}
	public List<ModuleDescriptor> getModules() {
		return modules;
	}
	public void setModules(List<ModuleDescriptor> modules) {
		this.modules = modules;
	}
	public String getLoadType() {
		return loadType;
	}
	public void setLoadType(String loadType) {
		this.loadType = loadType;
	}
	
	public int getLoadStatus() {
		return loadStatus;
	}

	public void setLoadStatus(int loadStatus) {
		this.loadStatus = loadStatus;
	}

	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	public int getXml_line_begin() {
		return xml_line_begin;
	}
	public void setXml_line_begin(int xml_line_begin) {
		this.xml_line_begin = xml_line_begin;
	}
	public int getXml_line_end() {
		return xml_line_end;
	}
	public void setXml_line_end(int xml_line_end) {
		this.xml_line_end = xml_line_end;
	}
	
	public List<FormLoaderServiceError> getErrors() {
		return errors;
	}
	public void setErrors(List<FormLoaderServiceError> errors) {
		this.errors = errors;
	}
	public int getNumberOfModules() {
		return this.modules.size();
		
	}
	
	public void addMessage(String msg) {
		this.messages.add(msg);
	}

	public List<String> getMessages() {
		return messages;
	}

	public void setMessages(List<String> messages) {
		this.messages = messages;
	}
	
	

}
