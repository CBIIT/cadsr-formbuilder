package gov.nih.nci.cadsr.formloader.domain;

import java.util.List;

public class FormStatus {
	
	public static final String STATUS_XML_VALIDATED = "XML_VALIDATED"; 
	public static final String STATUS_DB_VALIDATED = "DB_VALIDATED";
	public static final String STATUS_LOADED = "LOADED";
	public static final String STATUS_UNLOADED = "UNLOADED";
	public static final String STATUS_ERROR = "ERROR";
	
	protected String type;
	protected List<String> messages;
	
	public FormStatus(String type) {
		this.type = type;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<String> getMessages() {
		return messages;
	}
	public void setMessages(List<String> messages) {
		this.messages = messages;
	}

}
