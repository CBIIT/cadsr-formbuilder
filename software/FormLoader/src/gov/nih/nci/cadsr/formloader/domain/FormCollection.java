package gov.nih.nci.cadsr.formloader.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

//@Entity
//@Table(name = "FORM_COLLECTIONS")
@XmlRootElement
public class FormCollection implements java.io.Serializable {
	
	private static final long serialVersionUID = 6315530260286190440L;
	
	//@Id
	//@GeneratedValue
	//@Column(name = "ID", unique = true, nullable = false)
	private String id;
	
	//@Column(name = "NAME", nullable = false)
	private String name;
	
	//@Column(name = "DESCRIPTION", nullable = false)
	private String description;
	
	//@Column(name = "XML_FILE_NAME", nullable = false)
	private String xmlFileName;
	
	//@Column(name = "XML_FILE_PATH")
	private String xmlPathOnServer;
	
	//@Column(name = "DATE_CREATED", nullable = false)
	private Date dateCreated;
	
	//@Column(name = "CREATED_BY", nullable = false)
	private String createdBy;
	
	//@Transient
	private boolean selected;
	
	//@OneToMany
	//@JoinTable
	//(
	//	name="FORM_COLLECTION_",
	//	joinColumns={ @JoinColumn(name="EMP_ID", referencedColumnName="EMP_ID") },
	//	inverseJoinColumns={ @JoinColumn(name="PHONE_ID", referencedColumnName="ID", unique=true) }
	//)
	//@Transient
	private List<FormHeader> forms;

	public FormCollection() {}
	
	public FormCollection(List<FormHeader> forms) {
		this.forms = forms;
	}
	
	public FormCollection(String id, String name, String description) {
		this.id = id;
		this.name = name;
		this.description = description;
	}
	
	public FormCollection(String id, String name, String description, 
			String xmlFileName, String xmlPathOnServer, Date dateCreated, String createdBy) {
		this.id = id;
		this.name = name;
		this.description = description;
		
		this.xmlFileName = xmlFileName;
		this.xmlPathOnServer = xmlPathOnServer;
		this.dateCreated = dateCreated;
		this.createdBy = createdBy;
	}
	
	
	public String getId() {
		return id;
	}
	
	@XmlElement
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	@XmlElement
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	@XmlElement
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getXmlFileName() {
		return xmlFileName;
	}
	@XmlElement
	public void setXmlFileName(String xmlFileName) {
		this.xmlFileName = xmlFileName;
	}
	
	public String getXmlPathOnServer() {
		return xmlPathOnServer;
	}
	@XmlElement
	public void setXmlPathOnServer(String xmlPathOnServer) {
		this.xmlPathOnServer = xmlPathOnServer;
	}
	
	
	public Date getDateCreated() {
		return dateCreated;
	}
	@XmlElement
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	
	public String getCreatedBy() {
		return createdBy;
	}
	@XmlElement
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	//@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "FORM_COLLECTIONS")
	
	public List<FormHeader> getForms() {
		return forms;
	}
	 @XmlElementWrapper(name ="forms")
	 @XmlElement(name ="form")
	public void setForms(List<FormHeader> forms) {
		this.forms = forms;
	}
	
	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

}
