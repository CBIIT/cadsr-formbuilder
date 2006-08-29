package gov.nih.nci.ncicb.cadsr.edci.domain;

import gov.nih.nci.ncicb.cadsr.edci.domain.impl.EVDElementTextImpl;
import gov.nih.nci.ncicb.cadsr.edci.domain.impl.EVDSubsetImpl;

import java.io.Serializable;

import java.util.Collection;

public interface EVDElement extends Serializable {

    public void setValue(String value);

    public String getValue();
    
    public void addEVDElementText(EVDElementText evdElementText);
    
    public void setEVDElementTextCollection(Collection<EVDElementText> eVDElementTextCollection);

    public Collection<EVDElementText> getEVDElementTextCollection();

    
}
