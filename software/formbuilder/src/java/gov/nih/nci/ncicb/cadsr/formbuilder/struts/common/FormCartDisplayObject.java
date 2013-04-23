// bean for holding the data that we display for a form on the form cart page

package gov.nih.nci.ncicb.cadsr.formbuilder.struts.common;

import java.util.List;

public class FormCartDisplayObject {

	private int publicId;
	private String longName;
	private String contextName = null;
	private String formType = null;
	private String aslName;
	private Float version;
	private List protocols = null;	
	private String idseq;

	
	public int getPublicId() {
		return publicId;
	}

	public void setPublicId(int id) {
		publicId = id;
	}

	public String getLongName() {
		return longName;
	}

	public void setLongName(String pLongName) {
		longName = pLongName;
	}

	public void setContextName(String contextName) {
		this.contextName = contextName;
	}

	public String getContextName() {
		return contextName;
	}

	public String getFormType() {
		return formType;
	}

	public void setFormType(String newFormType) {
		formType = newFormType;
	}

	public String getAslName() {
		return aslName;
	}

	public void setAslName(String pAslName) {
		aslName = pAslName;
	}

	public Float getVersion() {
		return version;
	}

	public void setVersion(Float pVersion) {
		version = pVersion;
	}

	public List getProtocols() {
		return protocols;
	}

	public void setProtocols(List newProtocols) {
		this.protocols = newProtocols;
	}

	public String getIdseq() {
		return idseq;
	}

	public void setIdseq(String idseq) {
		this.idseq = idseq;
	}
	
}
