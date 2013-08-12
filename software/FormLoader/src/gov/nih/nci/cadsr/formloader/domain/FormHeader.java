/*L
 * Copyright Oracle Inc, ScenPro Inc, SAIC-F
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-formbuilder/LICENSE.txt for details.
 */

package gov.nih.nci.cadsr.formloader.domain;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import gov.nih.nci.cadsr.formloader.service.common.FormLoaderServiceError;

@XmlRootElement(namespace ="gov.nih.nci.cadsr.formloader.domain.FormCollection")
public class FormHeader {
	
	public static final String LOAD_TYPE_NEW = "New Form";
	public static final String LOAD_TYPE_NEW_VERSION = "New Version";
	public static final String LOAD_TYPE_UPDATE_FORM = "Update Form";
	public static final String LOAD_TYPE_DUPLICATE_FORM = "Duplicate Form";
	
	private String id;
	private String publicID;
	private String longName;
	private String version;
	private String context;
	private String type;
	private String protocolName;
	private int numberOfModules;
	private String workflowStatusName;
	
	//Any error (xml, content validation, etc) will be here
	private List<FormLoaderServiceError> errors = new ArrayList<FormLoaderServiceError>();
	
	private transient boolean selected;
	
	//Load services only: new form, new version or update
	private String loadType;
	private Status status;
	
	private transient int xml_line_begin;
	private transient int xml_line_end;
	
	
	
	public FormHeader() {}
	
	public FormHeader(String id, String publicId, String version) {
		this.id = id;
		this.publicID = publicId;
		this.version = version;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPublicID() {
		return publicID;
	}
	public void setPublicID(String publicID) {
		this.publicID = publicID;
	}
	public String getLongName() {
		return longName;
	}
	
	@XmlElement
	public void setLongName(String longName) {
		this.longName = longName;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
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
	public String getWorkflowStatusName() {
		return workflowStatusName;
	}
	public void setWorkflowStatusName(String workflowStatusName) {
		this.workflowStatusName = workflowStatusName;
	}
	public int getNumberOfModules() {
		return numberOfModules;
	}
	public void setNumberOfModules(int numberOfModules) {
		this.numberOfModules = numberOfModules;
	}	
	
	public List<FormLoaderServiceError> getErrors() {
		return errors;
	}

	public void setErrors(List<FormLoaderServiceError> errors) {
		this.errors = errors;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	public String getLoadType() {
		return loadType;
	}

	public void setLoadType(String loadType) {
		this.loadType = loadType;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
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

	public String toString() {
		return "Public Id: " + this.publicID + "; version: " + this.version + "; long name: " + this.longName +
				"; protocol name: " + this.protocolName + "; type: " + this.type + "; workflow status: " + this.workflowStatusName +
				"; num of modules: " + this.numberOfModules +  "(line positions: " + this.xml_line_begin + "-" + this.xml_line_end + ")"; 
	}
}
