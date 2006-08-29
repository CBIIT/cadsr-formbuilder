package gov.nih.nci.ncicb.cadsr.edci.domain;


import java.util.Collection;

public interface ExplicitItemRefRepetition {

    public void setIsModifiableFlag(String isModifiableFlag);
    
    public String getIsModifiableFlag();

    public void setRepeatSequenceNumber(String repeatSequenceNumber);

    public String getRepeatSequenceNumber();

    public void setRepeatSpecificDefaultValue(String repeatSpecificDefaultValue);

    public String getRepeatSpecificDefaultValue();

    public void setTriggeredActionCollection(Collection<TriggeredAction> triggeredActionCollection);

    public Collection<TriggeredAction> getTriggeredActionCollection();
    
}
