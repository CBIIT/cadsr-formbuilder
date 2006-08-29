package gov.nih.nci.ncicb.cadsr.edci.domain.impl;

import gov.nih.nci.ncicb.cadsr.edci.domain.PersistentInformation;

public abstract class PersistentInformationImpl implements PersistentInformation{
    private String associatedElement;
    
    public PersistentInformationImpl() {
    }

    public void setAssociatedElement(String associatedElement) {
       this.associatedElement = associatedElement;
    }

    public String getAssociatedElement() {
        return associatedElement;
    }
}
