package gov.nih.nci.ncicb.cadsr.edci.domain;

import java.util.Collection;

public class EVDElement {
    private String sequenceNumber;
    private String value;
    private Collection<EVDElementText> eVDElementTextCollection;
    private Collection<EVDSubset> eVDSubsetCollection;
    public EVDElement() {
    }

    public void setSequenceNumber(String sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public String getSequenceNumber() {
        return sequenceNumber;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setEVDElementTextCollection(Collection<EVDElementText> eVDElementTextCollection) {
        this.eVDElementTextCollection = eVDElementTextCollection;
    }

    public Collection<EVDElementText> getEVDElementTextCollection() {
        return eVDElementTextCollection;
    }

    public void setEVDSubsetCollection(Collection<EVDSubset> eVDSubsetCollection) {
        this.eVDSubsetCollection = eVDSubsetCollection;
    }

    public Collection<EVDSubset> getEVDSubsetCollection() {
        return eVDSubsetCollection;
    }
}
