package gov.nih.nci.ncicb.cadsr.edci.domain.impl;

import gov.nih.nci.ncicb.cadsr.edci.domain.ExplicitItemRefRepetition;
import gov.nih.nci.ncicb.cadsr.edci.domain.ItemDef;
import gov.nih.nci.ncicb.cadsr.edci.domain.ItemRef;
import gov.nih.nci.ncicb.cadsr.edci.domain.ItemRefHelp;
import gov.nih.nci.ncicb.cadsr.edci.domain.ItemRefPrompt;
import gov.nih.nci.ncicb.cadsr.edci.domain.RangeCheck;

import gov.nih.nci.ncicb.cadsr.edci.domain.TriggeredAction;

import java.util.Collection;

public class ItemRefImpl implements ItemRef {

    private String defaultValue;
    private String id;
    private String isCollectedFlag;
    private String isMandatoryFlag;
    private String isModifiableFlag;
    private String navigationSequenceNumber;
    private ItemDef itemDef;
    private Collection<ItemRefPrompt> itemRefPromptCollection;
    private Collection<ItemRefHelp> itemRefHelpCollection;
    private Collection<TriggeredAction> triggeredActionCollection;
    private Collection<ExplicitItemRefRepetition> explicitItemRefRepetitionCollection;
    private Collection<RangeCheck> rangeCheckCollection;
    
    public ItemRefImpl() {
    }

    public void setRangeCheckCollection(Collection<RangeCheck> rangeCheckCollection) {
        this.rangeCheckCollection = rangeCheckCollection;
    }

    public Collection<RangeCheck> getRangeCheckCollection() {
        return rangeCheckCollection;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setIsCollectedFlag(String isCollectedFlag) {
        this.isCollectedFlag = isCollectedFlag;
    }

    public String getIsCollectedFlag() {
        return isCollectedFlag;
    }

    public void setIsMandatoryFlag(String isMandatoryFlag) {
        this.isMandatoryFlag = isMandatoryFlag;
    }

    public String getIsMandatoryFlag() {
        return isMandatoryFlag;
    }

    public void setIsModifiableFlag(String isModifiableFlag) {
        this.isModifiableFlag = isModifiableFlag;
    }

    public String getIsModifiableFlag() {
        return isModifiableFlag;
    }

    public void setNavigationSequenceNumber(String navigationSequenceNumber) {
        this.navigationSequenceNumber = navigationSequenceNumber;
    }

    public String getNavigationSequenceNumber() {
        return navigationSequenceNumber;
    }

    public void setItemDef(ItemDef itemDef) {
        this.itemDef = itemDef;
    }

    public ItemDef getItemDef() {
        return itemDef;
    }

    public void setItemRefPromptCollection(Collection<ItemRefPrompt> itemRefPromptCollection) {
        this.itemRefPromptCollection = itemRefPromptCollection;
    }

    public Collection<ItemRefPrompt> getItemRefPromptCollection() {
        return itemRefPromptCollection;
    }

    public void setItemRefHelpCollection(Collection<ItemRefHelp> itemRefHelpCollection) {
        this.itemRefHelpCollection = itemRefHelpCollection;
    }

    public Collection<ItemRefHelp> getItemRefHelpCollection() {
        return itemRefHelpCollection;
    }

    public void setTriggeredActionCollection(Collection<TriggeredAction> triggeredActionCollection) {
        this.triggeredActionCollection = triggeredActionCollection;
    }

    public Collection<TriggeredAction> getTriggeredActionCollection() {
        return triggeredActionCollection;
    }

    public void setExplicitItemRefRepetitionCollection(Collection<ExplicitItemRefRepetition> explicitItemRefRepetitionCollection) {
        this.explicitItemRefRepetitionCollection = explicitItemRefRepetitionCollection;
    }

    public Collection<ExplicitItemRefRepetition> getExplicitItemRefRepetitionCollection() {
        return explicitItemRefRepetitionCollection;
    }
}
