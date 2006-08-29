package gov.nih.nci.ncicb.cadsr.edci.domain.impl;

import gov.nih.nci.ncicb.cadsr.edci.domain.RangeCheck;

import gov.nih.nci.ncicb.cadsr.edci.domain.TriggeredAction;

import java.util.Collection;

public class TriggeredActionImpl implements TriggeredAction{
    private String action;
    private String criterionValue;
    private String enumeratedValueDomainSubsetId;
    private String forcedValue;
    private String targetId;
    private String triggerRelationship;
    private Collection<RangeCheck> rangeCheckCollection;
  
    public TriggeredActionImpl() {
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public void setCriterionValue(String criterionValue) {
        this.criterionValue = criterionValue;
    }

    public String getCriterionValue() {
        return criterionValue;
    }

    public void setEnumeratedValueDomainSubsetId(String enumeratedValueDomainSubsetId) {
        this.enumeratedValueDomainSubsetId = enumeratedValueDomainSubsetId;
    }

    public String getEnumeratedValueDomainSubsetId() {
        return enumeratedValueDomainSubsetId;
    }

    public void setForcedValue(String forcedValue) {
        this.forcedValue = forcedValue;
    }

    public String getForcedValue() {
        return forcedValue;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTriggerRelationship(String triggerRelationship) {
        this.triggerRelationship = triggerRelationship;
    }

    public String getTriggerRelationship() {
        return triggerRelationship;
    }

    public void setRangeCheckCollection(Collection<RangeCheck> rangeCheckCollection) {
        this.rangeCheckCollection = rangeCheckCollection;
    }

    public Collection<RangeCheck> getRangeCheckCollection() {
        return rangeCheckCollection;
    }
}
