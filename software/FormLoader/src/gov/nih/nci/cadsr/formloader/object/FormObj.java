package gov.nih.nci.cadsr.formloader.object;

import java.sql.Time;

public class FormObj {
	public int publicId;
	public String version;
	private String xmlFilename;
	private String longName;
	private String context;
	private String type;
	private String protocolName;
	private String workflowStatus;
	private int numModules;
	
	private String action;
	private String errorMessage;
	private String loadingSummary;
	
	public int getPublicId() {
		return publicId;
	}
	public void setPublicId(int publicId) {
		this.publicId = publicId;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getXmlFilename() {
		return xmlFilename;
	}
	public void setXmlFilename(String xmlFilename) {
		this.xmlFilename = xmlFilename;
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
		this.protocolName = protocolName;
	}
	public String getWorkflowStatus() {
		return workflowStatus;
	}
	public void setWorkflowStatus(String workflowStatus) {
		this.workflowStatus = workflowStatus;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getLoadingSummary() {
		return loadingSummary;
	}
	public void setLoadingSummary(String loadingSummary) {
		this.loadingSummary = loadingSummary;
	}
	public int getNumModules() {
		return numModules;
	}
	public void setNumModules(int numModules) {
		this.numModules = numModules;
	}
	
	
}
