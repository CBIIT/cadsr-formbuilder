package gov.nih.nci.ncicb.cadsr.edci.domain;

import gov.nih.nci.ncicb.cadsr.edci.domain.impl.EVDElementImpl;

import java.util.Collection;

public interface EVDSubset {

    public void setBaseSubsetFlag(boolean isBaseSubsetFlag);

    public boolean isBaseSubsetFlag();

    public void setRadioButtonLabel(String radioButtonLabel);

    public String getRadioButtonLabel();

    public void setSubsetId(String subsetId);

    public String getSubsetId();
    
    public void addElementInSubset(ElementInSubset elementsInSubset);

    public void setElementInSubsetCollection(Collection<ElementInSubset> elementsInSubsetCollection);

    public Collection<ElementInSubset> getElementInSubsetCollection();
    
}
