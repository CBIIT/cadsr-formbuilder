package gov.nih.nci.ncicb.cadsr.edci.domain.impl;

import gov.nih.nci.ncicb.cadsr.edci.domain.GroupRef;
import gov.nih.nci.ncicb.cadsr.edci.domain.ItemRef;
import gov.nih.nci.ncicb.cadsr.edci.domain.SectionDef;

import gov.nih.nci.ncicb.cadsr.edci.domain.SectionRef;
import gov.nih.nci.ncicb.cadsr.edci.domain.SectionRefHelp;

import java.util.Collection;

public class SectionRefImpl implements SectionRef {

    private String id;
    private String name;
    private String navigationSequenceNumber;
    private SectionDef sectionDef;
    private Collection<ItemRef> itemRefCollection;
    private Collection<GroupRef> groupRefCollection;
    private Collection<SectionRefHelp> sectionRefHelpCollection;
    
    public SectionRefImpl() {
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setNavigationSequenceNumber(String navigationSequenceNumber) {
        this.navigationSequenceNumber = navigationSequenceNumber;
    }

    public String getNavigationSequenceNumber() {
        return navigationSequenceNumber;
    }

    public void setItemRefCollection(Collection itemRefCollection) {
        this.itemRefCollection = itemRefCollection;
    }

    public Collection getItemRefCollection() {
        return itemRefCollection;
    }

    public void setGroupRefCollection(Collection groupRefCollection) {
        this.groupRefCollection = groupRefCollection;
    }

    public Collection getGroupRefCollection() {
        return groupRefCollection;
    }

    public void setSectionDef(SectionDef sectionDef) {
        this.sectionDef = sectionDef;
    }

    public SectionDef getSectionDef() {
        return sectionDef;
    }

    public void setSectionRefHelpCollection(Collection<SectionRefHelp> sectionRefHelpCollection) {
        this.sectionRefHelpCollection = sectionRefHelpCollection;
    }

    public Collection<SectionRefHelp> getSectionRefHelpCollection() {
        return sectionRefHelpCollection;
    }
}
