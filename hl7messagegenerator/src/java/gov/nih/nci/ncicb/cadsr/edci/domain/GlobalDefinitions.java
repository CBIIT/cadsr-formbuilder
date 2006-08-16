package gov.nih.nci.ncicb.cadsr.edci.domain;

import java.util.Collection;

public class GlobalDefinitions {
    private Collection<ValueDomain> valueDomainCollection;
    private Collection<DataElementConcept> dataElementConceptCollection;
    private Collection<DataElement> dataElementCollection;
    
    public GlobalDefinitions() {
    }

    public void setValueDomainCollection(Collection<ValueDomain> valueDomainCollection) {
        this.valueDomainCollection = valueDomainCollection;
    }

    public Collection<ValueDomain> getValueDomainCollection() {
        return valueDomainCollection;
    }

    public void setDataElementConceptCollection(Collection<DataElementConcept> dataElementConceptCollection) {
        this.dataElementConceptCollection = dataElementConceptCollection;
    }

    public Collection<DataElementConcept> getDataElementConceptCollection() {
        return dataElementConceptCollection;
    }

    public void setDataElementCollection(Collection<DataElement> dataElementCollection) {
        this.dataElementCollection = dataElementCollection;
    }

    public Collection<DataElement> getDataElementCollection() {
        return dataElementCollection;
    }
}
