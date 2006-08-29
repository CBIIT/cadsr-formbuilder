package gov.nih.nci.ncicb.cadsr.edci.domain.impl;

import gov.nih.nci.ncicb.cadsr.edci.domain.RangeCheck;

import java.util.Collection;

public class ItemDefImpl {

    private String dataElementGUID;
    private String enumeratedValueDomainSubsetId;
    private String id;
    private String isMandatoryFlag;
    private String isProtocolEventItem;
    private String isSectionDifferentiatorFlag;
    private String occurenceSn;
    private Collection<RangeCheck> rangeCheckCollection;

    
    public ItemDefImpl() {
    }

    public void setDataElementGUID(String dataElementGUID) {
        this.dataElementGUID = dataElementGUID;
    }

    public String getDataElementGUID() {
        return dataElementGUID;
    }

    public void setEnumeratedValueDomainSubsetId(String enumeratedValueDomainSubsetId) {
        this.enumeratedValueDomainSubsetId = enumeratedValueDomainSubsetId;
    }

    public String getEnumeratedValueDomainSubsetId() {
        return enumeratedValueDomainSubsetId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setIsMandatoryFlag(String isMandatoryFlag) {
        this.isMandatoryFlag = isMandatoryFlag;
    }

    public String getIsMandatoryFlag() {
        return isMandatoryFlag;
    }

    public void setIsProtocolEventItem(String isProtocolEventItem) {
        this.isProtocolEventItem = isProtocolEventItem;
    }

    public String getIsProtocolEventItem() {
        return isProtocolEventItem;
    }

    public void setIsSectionDifferentiatorFlag(String isSectionDifferentiatorFlag) {
        this.isSectionDifferentiatorFlag = isSectionDifferentiatorFlag;
    }

    public String getIsSectionDifferentiatorFlag() {
        return isSectionDifferentiatorFlag;
    }

    public void setOccurenceSn(String occurenceSn) {
        this.occurenceSn = occurenceSn;
    }

    public String getOccurenceSn() {
        return occurenceSn;
    }

}
