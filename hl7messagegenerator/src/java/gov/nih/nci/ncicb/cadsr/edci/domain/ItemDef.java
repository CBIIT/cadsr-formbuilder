package gov.nih.nci.ncicb.cadsr.edci.domain;

import java.io.Serializable;

public interface ItemDef extends Serializable {

    public void setDataElementGUID(String dataElementGUID);

    public String getDataElementGUID();

    public void setEnumeratedValueDomainSubsetId(String enumeratedValueDomainSubsetId);

    public String getEnumeratedValueDomainSubsetId();

    public void setId(String id);

    public String getId();

    public void setIsMandatoryFlag(String isMandatoryFlag);

    public String getIsMandatoryFlag();

    public void setIsProtocolEventItem(String isProtocolEventItem);

    public String getIsProtocolEventItem();

    public void setIsSectionDifferentiatorFlag(String isSectionDifferentiatorFlag);

    public String getIsSectionDifferentiatorFlag();

    public void setOccurenceSn(String occurenceSn);

    public String getOccurenceSn();
}
