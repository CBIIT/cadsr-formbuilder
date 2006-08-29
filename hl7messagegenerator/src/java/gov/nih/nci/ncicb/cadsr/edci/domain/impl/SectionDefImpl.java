package gov.nih.nci.ncicb.cadsr.edci.domain.impl;

import gov.nih.nci.ncicb.cadsr.edci.domain.GroupDef;

import gov.nih.nci.ncicb.cadsr.edci.domain.ItemDef;

import gov.nih.nci.ncicb.cadsr.edci.domain.SectionDef;

import java.util.Collection;

public class SectionDefImpl implements SectionDef {
    private String description;
    private String differentiatorItem;
    private String GUID;
    private String id;
    private String name;
    private Collection<GroupDef> groupDefCollection;
    private Collection<ItemDef> itemDefCollection;
    
    public SectionDefImpl() {
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDifferentiatorItem(String differentiatorItem) {
        this.differentiatorItem = differentiatorItem;
    }

    public String getDifferentiatorItem() {
        return differentiatorItem;
    }

    public void setGUID(String gUID) {
        this.GUID = gUID;
    }

    public String getGUID() {
        return GUID;
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

    public void setGroupDefCollection(Collection groupDefCollection) {
        this.groupDefCollection = groupDefCollection;
    }

    public Collection getGroupDefCollection() {
        return groupDefCollection;
    }

    public void setItemDefCollection(Collection itemDefCollection) {
        this.itemDefCollection = itemDefCollection;
    }

    public Collection getItemDefCollection() {
        return itemDefCollection;
    }
}
