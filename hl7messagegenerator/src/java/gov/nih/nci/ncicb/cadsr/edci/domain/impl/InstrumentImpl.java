package gov.nih.nci.ncicb.cadsr.edci.domain.impl;

import gov.nih.nci.ncicb.cadsr.edci.domain.Instrument;

import gov.nih.nci.ncicb.cadsr.edci.domain.SectionDef;

import gov.nih.nci.ncicb.cadsr.edci.domain.SectionRef;

import java.util.Collection;
import java.util.Date;

public class InstrumentImpl implements Instrument{
   private String clinicalArea;
   private String clinicalStudy;
   private Date creationTimestamp;
   private String description;
   private String GUID;
   private Boolean isTemplateDciFlag;
   private String name;
   private String namespace;
   private Collection<SectionDef> sectionDefCollection;
   private Collection<SectionRef> sectionRefCollection;
   private Collection persistentInformationCollection;
   
   

    public InstrumentImpl() {
    }


    public void setClinicalArea(String clinicalArea) {
        this.clinicalArea = clinicalArea;
    }

    public String getClinicalArea() {
        return clinicalArea;
    }

    public void setClinicalStudy(String clinicalStudy) {
        this.clinicalStudy = clinicalStudy;
    }

    public String getClinicalStudy() {
        return clinicalStudy;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setGUID(String gUID) {
        this.GUID = gUID;
    }

    public String getGUID() {
        return GUID;
    }

    public void setIsTemplateDciFlag(Boolean isTemplateDciFlag) {
        this.isTemplateDciFlag = isTemplateDciFlag;
    }

    public Boolean getIsTemplateDciFlag() {
        return isTemplateDciFlag;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setSectionDefCollection(Collection sectionDefCollection) {
        this.sectionDefCollection = sectionDefCollection;
    }

    public Collection<SectionDef> getSectionDefCollection() {
        return sectionDefCollection;
    }

    public void setSectionRefCollection(Collection sectionRefCollection)
     {
        this.sectionRefCollection = sectionRefCollection;
    }

    public Collection<SectionRef> getSectionRefCollection() {
        return sectionRefCollection;
    }

    public void setPersistentInformationCollection(Collection persistentInformationCollection) {
        this.persistentInformationCollection = persistentInformationCollection;
    }

    public Collection getPersistentInformationCollection() {
        return persistentInformationCollection;
    }

    public void setCreationTimestamp(Date creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    public Date getCreationTimestamp() {
        return creationTimestamp;
    }
}
