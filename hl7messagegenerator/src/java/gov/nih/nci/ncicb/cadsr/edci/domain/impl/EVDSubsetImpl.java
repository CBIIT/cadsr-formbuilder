package gov.nih.nci.ncicb.cadsr.edci.domain.impl;

import gov.nih.nci.ncicb.cadsr.edci.domain.EVDSubset;

import gov.nih.nci.ncicb.cadsr.edci.domain.ElementInSubset;

import java.util.Collection;

public class EVDSubsetImpl extends gov.nih.nci.ncicb.cadsr.edci.domain.castor.EVDSubset
implements EVDSubset{

    private boolean baseSubsetFlag;
    public EVDSubsetImpl() {
    }

    public void setBaseSubsetFlag(boolean baseSubsetFlag) {
      this.baseSubsetFlag = baseSubsetFlag;
    }

    public boolean isBaseSubsetFlag() {
        return baseSubsetFlag;
    }
    
    public void addElementInSubset(ElementInSubset elementsInSubset) {
       addElementInSubset((gov.nih.nci.ncicb.cadsr.edci.domain.castor.ElementInSubset)elementsInSubset);
    }

    public void setElementInSubsetCollection(Collection<ElementInSubset> elementsInSubsetCollection) 
    {
       removeAllElementInSubset();
       for (ElementInSubset elementInSubset:elementsInSubsetCollection){
           addElementInSubset(elementInSubset);
       }
    }

    public Collection<ElementInSubset> getElementInSubsetCollection() {
        return null;
    }
}
