package gov.nih.nci.ncicb.cadsr.edci.domain;

import java.io.Serializable;

public interface PersistentInformation extends Serializable {
    public void setAssociatedElement(String associatedElement);

    public String getAssociatedElement();
}
