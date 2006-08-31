package gov.nih.nci.ncicb.cadsr.edci.domain.impl;

import gov.nih.nci.ncicb.cadsr.edci.domain.GroupDef;
import gov.nih.nci.ncicb.cadsr.edci.domain.GroupRef;
import gov.nih.nci.ncicb.cadsr.edci.domain.GroupRefHelp;

import gov.nih.nci.ncicb.cadsr.edci.domain.ItemRef;

import java.util.Collection;

public class GroupRefImpl implements GroupRef {
    private GroupDef groupDef;
    private String id;
    private String maximumItemRefRepeats;
    private String name;
    private String navigationSequenceNumber;
    private Collection<ItemRef> itemRefCollection;
    private Collection<GroupRefHelp> groupRefHelpCollection;
    
    public GroupRefImpl() {
    }

    public void setGroupDef(GroupDef groupDef) {
        this.groupDef = groupDef;
    }

    public GroupDef getGroupDef() {
        return groupDef;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setMaximumItemRefRepeats(String maximumItemRefRepeats) {
        this.maximumItemRefRepeats = maximumItemRefRepeats;
    }

    public String getMaximumItemRefRepeats() {
        return maximumItemRefRepeats;
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

    public void setItemRefCollection(Collection<ItemRef> itemRefCollection) {
        this.itemRefCollection = itemRefCollection;
    }

    public Collection<ItemRef> getItemRefCollection() {
        return itemRefCollection;
    }

    public void setGroupRefHelpCollection(Collection<GroupRefHelp> groupRefHelpCollection) {
        this.groupRefHelpCollection = groupRefHelpCollection;
    }

    public Collection<GroupRefHelp> getGroupRefHelpCollection() {
        return groupRefHelpCollection;
    }
}
