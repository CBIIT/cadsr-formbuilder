package gov.nih.nci.ncicb.cadsr.edci.domain;

import java.io.Serializable;

import java.util.Collection;

public interface GlobalDefinitions extends Serializable{

    public void addValueDomain(ValueDomain valueDomain);
    
    public void setValueDomainCollection(Collection<ValueDomain> valueDomainCollection);
    
    public Collection<ValueDomain> getValueDomainCollection();
    
    public void addDataElementConcept(DataElementConcept dataElementConcept);

    public void setDataElementConceptCollection(Collection<DataElementConcept> dataElementConceptCollection);

    public Collection<DataElementConcept> getDataElementConceptCollection();
    
    public void addDataElement(DataElement dataElement);

    public void setDataElementCollection(Collection<DataElement> dataElementCollection);

    public Collection<DataElement> getDataElementCollection();
    
    public void addDataElementGroup(DataElementGroup  dataElementGroup);  

    public void setDataElementGroupCollection(Collection<DataElementGroup> dataElementGroupCollection);

    public Collection<DataElementGroup> getDataElementGroupCollection();
    
}
