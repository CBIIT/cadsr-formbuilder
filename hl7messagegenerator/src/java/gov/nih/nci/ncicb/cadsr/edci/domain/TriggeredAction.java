package gov.nih.nci.ncicb.cadsr.edci.domain;

import java.util.Collection;

public interface TriggeredAction {

    public void setAction(String action);

    public String getAction();
    
    public void setCriterionValue(String criterionValue);

    public String getCriterionValue();

    public void setEnumeratedValueDomainSubsetId(String enumeratedValueDomainSubsetId);

    public String getEnumeratedValueDomainSubsetId();

    public void setForcedValue(String forcedValue);

    public String getForcedValue();

    public void setTargetId(String targetId);

    public String getTargetId();

    public void setTriggerRelationship(String triggerRelationship);

    public String getTriggerRelationship();

    public void setRangeCheckCollection(Collection<RangeCheck> rangeCheckCollection);

    public Collection<RangeCheck> getRangeCheckCollection();
    
}
