package gov.nih.nci.ncicb.cadsr.edci.domain;

import java.io.Serializable;

public interface GroupAlias extends Serializable {

    public void setName(String name);

    public String getName();

    public void setSystem(String system);
    
    public String getSystem();

}
