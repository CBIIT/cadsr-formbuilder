package gov.nih.nci.ncicb.cadsr.dto;

import gov.nih.nci.cadsr.domain.ReferenceDocument;

import java.util.Date;

public class ReferenceDocumentAttachment {
    private String name;
    private ReferenceDocument referenceDocument;
    private String mimeType;
    private Long docSize;
    private String dadCharset;
    private String contentType;
    private String createdBy;
    private Date createdDate;
    private String modifiedBy;
    private Date modifiedDate;
    private Date lastUpdated;
    private String attachment;
    
    public ReferenceDocumentAttachment() 
    {
       
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setReferenceDocument(ReferenceDocument referenceDocument) {
        this.referenceDocument = referenceDocument;
    }

    public ReferenceDocument getReferenceDocument() {
        return referenceDocument;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setDocSize(Long docSize) {
        this.docSize = docSize;
    }

    public Long getDocSize() {
        return docSize;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentType() {
        return contentType;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setDadCharset(String dadCharset) {
        this.dadCharset = dadCharset;
    }

    public String getDadCharset() {
        return dadCharset;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getAttachment() {
        return attachment;
    }
    
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("name -> "+name+"\n");
        sb.append("mimeType -> "+mimeType+"\n");
        sb.append("attachment -> "+attachment+"\n");
        return sb.toString();
    }
}
