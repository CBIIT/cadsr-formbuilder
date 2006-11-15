package gov.nih.nci.ncicb.cadsr.edci.domain.impl;

import gov.nih.nci.ncicb.cadsr.edci.domain.GroupAlias;

import gov.nih.nci.ncicb.cadsr.edci.domain.GroupDef;

import gov.nih.nci.ncicb.cadsr.edci.domain.ItemDef;

import java.util.Collection;

public class GroupDefImpl implements GroupDef {
    private String dataElementGroupGUID;
    private String description;
    private String id;
    private String name;
    private String repeatingGroupFlag;
    private Collection<GroupAlias> groupAliasCollection;
    private Collection<ItemDef> itemDefCollection;
    
    public GroupDefImpl() {
    }

    public void setDataElementGroupGUID(String dataElementGroupGUID) {
        this.dataElementGroupGUID = dataElementGroupGUID;
    }

    public String getDataElementGroupGUID() {
        return dataElementGroupGUID;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setRepeatingGroupFlag(String repeatingGroupFlag) {
        this.repeatingGroupFlag = repeatingGroupFlag;
    }

    public String getRepeatingGroupFlag() {
        return repeatingGroupFlag;
    }

    public void setGroupAliasCollection(Collection groupAliasCollection) {
        this.groupAliasCollection = groupAliasCollection;
    }

    public Collection getGroupAliasCollection() {
        return groupAliasCollection;
    }

    public void setItemDefCollection(Collection itemDefCollection) {
        this.itemDefCollection = itemDefCollection;
    }

    public Collection getItemDefCollection() {
        return itemDefCollection;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }    
}
