package gov.nih.nci.cadsr.formloader.domain;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import gov.nih.nci.cadsr.formloader.service.common.FormLoaderServiceError;
import gov.nih.nci.ncicb.cadsr.common.dto.FormTransferObject;

//@XmlRootElement(namespace ="gov.nih.nci.cadsr.formloader.domain.FormCollection")
public class FormHeader extends FormTransferObject {
	
	public static final String LOAD_TYPE_NEW = "New Form";
	public static final String LOAD_TYPE_NEW_VERSION = "New Version";
	public static final String LOAD_TYPE_UPDATE_FORM = "Update Form";
	public static final String LOAD_TYPE_DUPLICATE_FORM = "Duplicate Form";

	//Any error (xml, content validation, etc) will be here
	private List<FormLoaderServiceError> errors = new ArrayList<FormLoaderServiceError>();
	
	int numOfModules = 0;
	
	//Load services only: new form, new version or update
	private String loadType;
	private FormStatus status;
	
	private transient boolean selected;
	private transient int xml_line_begin;
	private transient int xml_line_end;
	
	
	
	public FormHeader() {}
	
	public FormHeader(String id, int publicId, float version) {
		this.idseq = id;
		this.publicId = publicId;
		this.version = version;
	}
	
	public List<FormLoaderServiceError> getErrors() {
		return errors;
	}

	public void setErrors(List<FormLoaderServiceError> errors) {
		this.errors = errors;
	}

	
	public int getNumOfModules() {
		return numOfModules;
	}

	public void setNumOfModules(int numOfModules) {
		this.numOfModules = numOfModules;
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

	public FormStatus getStatus() {
		return status;
	}

	public void setStatus(FormStatus status) {
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
	
	//Overload method in super
	public void setVersion(String version) {
		if (version == null || version.length() == 0) return;
		
		setVersion(Float.parseFloat(version));
	}
	
	//Overload method in AdminComponentTransferObject
	public void setPublicId(String publicID) {
		if (publicID == null || publicID.length() == 0) return;
		
		setPublicId(Integer.parseInt(publicID));
	}

	public String toString() {
		String formString = "Public Id: " + this.publicId + "; version: " + this.version;
		
		formString += "; long name: ";
		if (this.longName != null)
			formString += this.longName;
		
		formString += "; protocol name: ";
		if (this.protocolLongName != null)
			formString += this.protocolLongName;
		
		formString += "; type: ";
		if (this.formType != null)
			formString += this.formType;
		
		formString += "; workflow status: ";
		if (this.aslName != null)
			formString += this.aslName;
		
		formString += "; num of modules: " + this.numOfModules + "(line positions: " + this.xml_line_begin + "-" + this.xml_line_end + ")";
		
		return formString;
		
		
		
		//return "Public Id: " + this.publicId + "; version: " + this.version + "; long name: " + this.longName +
		//		"; protocol name: " + this.protocolLongName + "; type: " + this.formType + "; workflow status: " + this.aslName +
		//		"; num of modules: " + this.modules.size() +  "(line positions: " + this.xml_line_begin + "-" + this.xml_line_end + ")"; 
	}
}
