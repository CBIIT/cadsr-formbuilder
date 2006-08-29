package gov.nih.nci.ncicb.cadsr.edci.domain;

import java.io.Serializable;

public interface AlternateDesignation extends Serializable {

    public void setName(String name);

    public String getName();

    public void setType(String type);

    public String getType();

    public void setLanguage(String language);

    public String getLanguage();
}
