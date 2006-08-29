package gov.nih.nci.ncicb.cadsr.edci.domain.impl;

import gov.nih.nci.ncicb.cadsr.edci.domain.EVDElement;

import gov.nih.nci.ncicb.cadsr.edci.domain.EVDElementText;

import java.util.Collection;

public class EVDElementImpl extends gov.nih.nci.ncicb.cadsr.edci.domain.castor.EVDElement 
implements EVDElement {
    private Collection<EVDElementText> eVDElementTextCollection;

    public EVDElementImpl() {
    }
    
    public void addEVDElementText(EVDElementText evdElementText){
        eVDElementTextCollection.add(evdElementText);
        addEVDElementText((gov.nih.nci.ncicb.cadsr.edci.domain.castor.EVDElementText)evdElementText);
    }

    public void setEVDElementTextCollection(Collection<EVDElementText> eVDElementTextCollection) {
        this.eVDElementTextCollection = eVDElementTextCollection;
        removeAllEVDElementText();
        for (EVDElementText evdElementText:eVDElementTextCollection){
            addEVDElementText((gov.nih.nci.ncicb.cadsr.edci.domain.castor.EVDElementText)evdElementText);
        }
    }

    public Collection<EVDElementText> getEVDElementTextCollection() {
        return eVDElementTextCollection;
    }

}
