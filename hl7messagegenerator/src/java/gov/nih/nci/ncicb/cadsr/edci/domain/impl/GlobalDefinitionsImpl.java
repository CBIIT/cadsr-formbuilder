package gov.nih.nci.ncicb.cadsr.edci.domain.impl;

import gov.nih.nci.ncicb.cadsr.edci.domain.DataElement;
import gov.nih.nci.ncicb.cadsr.edci.domain.DataElementConcept;
import gov.nih.nci.ncicb.cadsr.edci.domain.DataElementGroup;
import gov.nih.nci.ncicb.cadsr.edci.domain.GlobalDefinitions;
import gov.nih.nci.ncicb.cadsr.edci.domain.ValueDomain;

import java.util.Collection;


public class GlobalDefinitionsImpl extends gov.nih.nci.ncicb.cadsr.edci.domain.castor.GlobalDefinitions
implements GlobalDefinitions {
    public GlobalDefinitionsImpl() {
    }

    public void addValueDomain(ValueDomain valueDomain) {
        addValueDomain((gov.nih.nci.ncicb.cadsr.edci.domain.castor.ValueDomain)valueDomain);
    }
    
    public void setValueDomainCollection(Collection<ValueDomain> valueDomainCollection)
    {
       removeAllValueDomain();
       for(ValueDomain valueDomain:valueDomainCollection) {
           addValueDomain(valueDomain);
       }
    }

    public Collection<ValueDomain> getValueDomainCollection() {
        return null;
    }

    public void addDataElementConcept(DataElementConcept  dataElementConcept) {
        addDataElementConcept((gov.nih.nci.ncicb.cadsr.edci.domain.castor.DataElementConcept)dataElementConcept);
    }
    
    public void setDataElementConceptCollection(Collection<DataElementConcept> dataElementConceptCollection) {
        removeAllDataElementConcept();
        for(DataElementConcept dataElementConcept:dataElementConceptCollection) {
            addDataElementConcept(dataElementConcept);
        }       
    }

    public Collection<DataElementConcept> getDataElementConceptCollection() {
        return null;
    }
    
    public void addDataElement(DataElement  dataElement) {
        addDataElement((gov.nih.nci.ncicb.cadsr.edci.domain.castor.DataElement)dataElement);
    }
    

    public void setDataElementCollection(Collection<DataElement> dataElementCollection) {
        removeAllDataElement();
        for(DataElement dataElement:dataElementCollection) {
            addDataElement(dataElement);
        }  
    }

    public Collection<DataElement> getDataElementCollection() {
        return null;
    }
    
    public void addDataElementGroup(DataElementGroup  dataElementGroup) {
        addDataElementGroup((gov.nih.nci.ncicb.cadsr.edci.domain.castor.DataElementGroup)dataElementGroup);
    }
    

    public void setDataElementGroupCollection(Collection<DataElementGroup> dataElementGroupCollection) {
        removeAllDataElementGroup();
        for(DataElementGroup dataElementGroup:dataElementGroupCollection) {
            addDataElementGroup(dataElementGroup);
        }  
    }

    public Collection<DataElementGroup> getDataElementGroupCollection() {
        return null;
    }    
}
