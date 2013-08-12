package gov.nih.nci.cadsr.formloader.domain;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FormStatus {
	
	protected String identifier;
	protected String loadType;
	protected String loadStatus;
	protected List<String> messages = new ArrayList<String>();
	
	protected List<ModuleStatus> moduleStatuses = new ArrayList<ModuleStatus>();
	
	public FormStatus() {}
	
	public FormStatus(String id) { 
		identifier = id;
	}
	
	public String getIdentifier() {
		return identifier;
	}
	
	@XmlElement
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	public String getLoadType() {
		return loadType;
	}
	
	@XmlElement
	public void setLoadType(String loadType) {
		this.loadType = loadType;
	}
	public String getLoadStatus() {
		return loadStatus;
	}
	
	@XmlElement
	public void setLoadStatus(String loadStatus) {
		this.loadStatus = loadStatus;
	}
	public List<String> getMessages() {
		return messages;
	}
	
	 @XmlElementWrapper(name ="messages")
	 @XmlElement(name ="message")
	public void setMessages(List<String> messages) {
		this.messages = messages;
	}

	
	public List<ModuleStatus> getModuleStatuses() {
		return moduleStatuses;
	}

	 @XmlElementWrapper(name ="modules")
	 @XmlElement(name ="module")
	public void setModuleStatuses(List<ModuleStatus> moduleStatuses) {
		this.moduleStatuses = moduleStatuses;
	}
	
	
}
