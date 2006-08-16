package gov.nih.nci.ncicb.cadsr.edci.domain;

import java.util.Collection;

public class EVDSubset {
    private boolean isBaseSubsetFlag;
    private String radioButtonLabel;
    private String subsetId;
    private Collection<EVDElement> eVDElementCollection;
    
    public EVDSubset() {
    }

    public void setIsBaseSubsetFlag(boolean isBaseSubsetFlag) {
        this.isBaseSubsetFlag = isBaseSubsetFlag;
    }

    public boolean isIsBaseSubsetFlag() {
        return isBaseSubsetFlag;
    }

    public void setRadioButtonLabel(String radioButtonLabel) {
        this.radioButtonLabel = radioButtonLabel;
    }

    public String getRadioButtonLabel() {
        return radioButtonLabel;
    }

    public void setSubsetId(String subsetId) {
        this.subsetId = subsetId;
    }

    public String getSubsetId() {
        return subsetId;
    }

    public void setEVDElementCollection(Collection<EVDElement> eVDElementCollection) {
        this.eVDElementCollection = eVDElementCollection;
    }

    public Collection<EVDElement> getEVDElementCollection() {
        return eVDElementCollection;
    }
}
