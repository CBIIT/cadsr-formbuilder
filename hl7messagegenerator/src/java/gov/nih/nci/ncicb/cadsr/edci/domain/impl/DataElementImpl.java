package gov.nih.nci.ncicb.cadsr.edci.domain.impl;

import gov.nih.nci.ncicb.cadsr.edci.domain.AlternateDesignation;

import gov.nih.nci.ncicb.cadsr.edci.domain.DataElementGroup;
import gov.nih.nci.ncicb.cadsr.edci.domain.DataElementText;

import java.util.ArrayList;
import java.util.Collection;

public class DataElementImpl extends gov.nih.nci.ncicb.cadsr.edci.domain.castor.DataElement implements gov.nih.nci.ncicb.cadsr.edci.domain.DataElement{

    public DataElementImpl() {
    }
    
    public void addAlternateDesignation(AlternateDesignation alternateDesignation) {
        addAlternateDesignation((gov.nih.nci.ncicb.cadsr.edci.domain.castor.AlternateDesignation)alternateDesignation);
    }


    public void setAlternateDesignationCollection(Collection<AlternateDesignation> alternateDesignationCollection) {
        for (AlternateDesignation alternateDesignation: alternateDesignationCollection)
        {
          addAlternateDesignation(alternateDesignation);
        }
    }

    public Collection<AlternateDesignation> getAlternateDesignationCollection() {
        return new ArrayList();
    }
    
    public void addDataElementText(DataElementText dataElementText) {
        addDataElementText((gov.nih.nci.ncicb.cadsr.edci.domain.castor.DataElementText)dataElementText);
    }
    

    public void setDataElementTextCollection(Collection<DataElementText> dataElementTextCollection) {
        for (DataElementText dataElementText: dataElementTextCollection)
        {
           addDataElementText(dataElementText);
        }
    }

    public Collection<DataElementText> getDataElementTextCollection() {
        return new ArrayList();
    }
    

}
