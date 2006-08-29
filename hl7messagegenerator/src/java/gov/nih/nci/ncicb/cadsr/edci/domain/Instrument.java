package gov.nih.nci.ncicb.cadsr.edci.domain;

import java.io.Serializable;

import java.util.Collection;
import java.util.Date;

public interface Instrument extends Serializable{

    public void setClinicalArea(String clinicalArea);

    public String getClinicalArea();

    public void setClinicalStudy(String clinicalStudy);

    public String getClinicalStudy();

    public void setDescription(String description);

    public String getDescription();

    public void setGUID(String gUID);

    public String getGUID();

    public void setIsTemplateDciFlag(Boolean isTemplateDciFlag);

    public Boolean getIsTemplateDciFlag();

    public void setName(String name);

    public String getName();

    public void setNamespace(String namespace);

    public String getNamespace();

    public void setSectionDefCollection(Collection sectionDefCollection);

    public Collection getSectionDefCollection();

    public void setSectionRefCollection(Collection sectionRefCollection);

    public Collection getSectionRefCollection();

    public void setPersistentInformationCollection(Collection persistentInformationCollection);

    public Collection getPersistentInformationCollection();

    public void setCreationTimestamp(Date creationTimestamp);

    public Date getCreationTimestamp();
}
