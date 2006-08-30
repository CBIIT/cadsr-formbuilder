package gov.nih.nci.ncicb.cadsr.edci.domain;

import java.io.Serializable;

import java.util.Collection;

public interface ValueDomain extends Serializable {

    public void setDatatype(String datatype);

    public String getDatatype();

    public void setDecimalPlaces(int decimalPlaces);
    
    public int getDecimalPlaces();

    public void setDescription(String description);

    public String getDescription();

    public void setGUID(String gUID) ;

    public String getGUID();

    public void setMaximumLength(int maximumLength);

    public int getMaximumLength();
    
    public void setName(String name);
    
    public String getName();

    public void setNamespace(String namespace);
    
    public String getNamespace();

    public void setSasFormatName(String sasFormatName);

    public String getSasFormatName();
    
    public void setIsEnumeratedFlag(boolean isEnumeratedFlag);
    
    public boolean getIsEnumeratedFlag();
    
    public void addEVDElement(EVDElement evdElement);
    
    public void setEVDElementCollection(Collection<EVDElement> evdElements);
    
    public Collection<EVDElement> getEVDElementCollection();
    
    public void addEVDSubset(EVDSubset evdSubset);
    
    public void setEVDSubsetCollection(Collection<EVDSubset> evdSubsets);
    
    public Collection<EVDSubset> getEVDSubsetCollection();
}
