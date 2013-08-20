package gov.nih.nci.cadsr.formloader.domain;

import gov.nih.nci.cadsr.formloader.service.common.XmlValidationError;

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
	public static final int STATUS_LOAD_FAILED = -3;
	
	public static final int STATUS_INITIALIZED = 0;
	public static final int STATUS_XML_VALIDATED = 1; 
	public static final int STATUS_DB_VALIDATED = 3;
	public static final int STATUS_LOADED = 4;
	public static final int STATUS_UNLOADED = 5;
	public static final int STATUS_SKIPPED_LOADING = 6;
	public static final int STATUS_SKIPPED_UNLOADING = 7;
	
	String formSeqId;
	String publicId;
	String version;
	
	String longName;
	String context;
	String type;
	String protocolName;
	String workflowStatusName;
	String modifiedBy;
	
	//pass 2
	String createdBy;
	String changeNote;
	String preferredDefinition;
	String registrationStatus;
	String headerInstruction;
	String footerInstruction;
	String categoryName;
	//pass2
	
	List<ModuleDescriptor> modules = new ArrayList<ModuleDescriptor>();
	
	//Any error (xml, content validation, etc) will be here
	List<XmlValidationError> xmlValidationErrors = new ArrayList<XmlValidationError>();
	List<String> messages = new ArrayList<String>();
	
	//Load services only: new form, new version or update
	String loadType;
	int loadStatus;
	
	protected transient boolean selected;
	protected transient int xml_line_begin;
	protected transient int xml_line_end;
	
	//This is from db by name
	protected String contextSeqid; 
	
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
	public FormStatus getStructuredStatus() {
		FormStatus formStatus = new FormStatus(this.getFormIdString() + "|" + this.formSeqId);
		formStatus.setLoadType(this.loadType);
		formStatus.setLoadStatus(getLoadStatusString(this.loadStatus));
		
		List<String> msgs = formStatus.getMessages();
		if (this.xmlValidationErrors != null && this.xmlValidationErrors.size() > 0) {
			for (XmlValidationError error : this.xmlValidationErrors)
				msgs.add(error.toString());
		}
		
		if (this.messages.size() > 0) {
			msgs.addAll(this.messages);
		}
		
		if (this.modules.size() > 0) {
			for (int i = 0; i < this.modules.size(); i++) {
				ModuleStatus moduleStatus = new ModuleStatus("" + (i+1));
				moduleStatus.setQuestionStatuses(modules.get(i).getQuestionStatuses());
				formStatus.getModuleStatuses().add(moduleStatus);
			}
		}
		
		return formStatus;
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
		return protocolName;
	}
	public void setProtocolName(String protocolName) {
		if (this.protocolName != null && protocolName.length() > 0)
			this.protocolName += "," + protocolName;
		else
			this.protocolName = protocolName;
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
	
	public List<XmlValidationError> getErrors() {
		return xmlValidationErrors;
	}
	public void setErrors(List<XmlValidationError> errors) {
		this.xmlValidationErrors = errors;
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

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getPreferredDefinition() {
		return preferredDefinition;
	}

	public void setPreferredDefinition(String preferredDefinition) {
		this.preferredDefinition = preferredDefinition;
	}

	public String getRegistrationStatus() {
		return registrationStatus;
	}

	public void setRegistrationStatus(String registrationStatus) {
		this.registrationStatus = registrationStatus;
	}

	public String getHeaderInstruction() {
		return headerInstruction;
	}

	public void setHeaderInstruction(String headerInstruction) {
		this.headerInstruction = headerInstruction;
	}

	public String getFooterInstruction() {
		return footerInstruction;
	}

	public void setFooterInstruction(String footerInstruction) {
		this.footerInstruction = footerInstruction;
	}

	public String getChangeNote() {
		return changeNote;
	}

	public void setChangeNote(String changeNote) {
		this.changeNote = changeNote;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	
	public String getContextSeqid() {
		return contextSeqid;
	}

	public void setContextSeqid(String contextSeqid) {
		this.contextSeqid = contextSeqid;
	}

	protected String getLoadStatusString(int statusCode) {
		switch (statusCode) {
		case STATUS_INITIALIZED:
			return "Initialized";
		case STATUS_XML_VALIDATED:
			return "XML Validated";
		case STATUS_DB_VALIDATED:
			return "Content Validated";
		case STATUS_LOADED:
			return "Loaded";
		case STATUS_UNLOADED:
			return "Unloaded";
		case STATUS_SKIPPED_LOADING:
			return "Skipped Loading";
		case STATUS_SKIPPED_UNLOADING:
			return "Stipped Unloading";
		case STATUS_XML_VALIDATION_FAILED:
			return "Xml validation failed";
		case STATUS_LOAD_FAILED:
			return "Load failed";
		case STATUS_ERROR:
			return "Error";
			
		default: 
			return "Status Unknown";
			
		}
	}
	
	/**
	 * Returns form's id in this format: <publicid>|<version> 
	 * * @return
	 */
	public String getFormIdString() {
		return this.publicId + "|" + this.version;
	}
}
