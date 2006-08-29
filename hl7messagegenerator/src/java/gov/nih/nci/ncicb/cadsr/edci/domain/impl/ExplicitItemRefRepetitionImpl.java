package gov.nih.nci.ncicb.cadsr.edci.domain.impl;

import gov.nih.nci.ncicb.cadsr.edci.domain.ExplicitItemRefRepetition;

import gov.nih.nci.ncicb.cadsr.edci.domain.TriggeredAction;

import java.util.Collection;

public class ExplicitItemRefRepetitionImpl implements ExplicitItemRefRepetition{
    private String isModifiableFlag;
    private String repeatSequenceNumber;
    private String repeatSpecificDefaultValue;
    private Collection<TriggeredAction> triggeredActionCollection;
    
    public ExplicitItemRefRepetitionImpl() {
    }

    public void setIsModifiableFlag(String isModifiableFlag) {
        this.isModifiableFlag = isModifiableFlag;
    }

    public String getIsModifiableFlag() {
        return isModifiableFlag;
    }

    public void setRepeatSequenceNumber(String repeatSequenceNumber) {
        this.repeatSequenceNumber = repeatSequenceNumber;
    }

    public String getRepeatSequenceNumber() {
        return repeatSequenceNumber;
    }

    public void setRepeatSpecificDefaultValue(String repeatSpecificDefaultValue) {
        this.repeatSpecificDefaultValue = repeatSpecificDefaultValue;
    }

    public String getRepeatSpecificDefaultValue() {
        return repeatSpecificDefaultValue;
    }

    public void setTriggeredActionCollection(Collection<TriggeredAction> triggeredActionCollection) {
        this.triggeredActionCollection = triggeredActionCollection;
    }

    public Collection<TriggeredAction> getTriggeredActionCollection() {
        return triggeredActionCollection;
    }
}
