package gov.nih.nci.ncicb.cadsr.edci.domain;

import gov.nih.nci.ncicb.cadsr.edci.domain.impl.DataElementImpl;

import java.io.Serializable;

import java.util.Collection;

public interface DataElementGroup extends Serializable {

    public void setGUID(String gUID);

    public String getGUID();

    public void setDescription(String description);

    public String getDescription();

    public void setName(String name);

    public String getName();

    public void setNamespace(String namespace);

    public String getNamespace();
    
    public void addDataElement(DataElement dataElement);

    public void setDataElementCollection(Collection<DataElement> dataElementCollection);

    public Collection<DataElement> getDataElementCollection();
    
}
