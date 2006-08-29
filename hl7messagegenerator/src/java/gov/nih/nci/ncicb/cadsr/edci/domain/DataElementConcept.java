package gov.nih.nci.ncicb.cadsr.edci.domain;

import java.io.Serializable;

public interface DataElementConcept extends Serializable{

    public void setDefinition(String definition);

    public String getDefinition();

    public void setDescription(String description);

    public String getDescription();

    public void setGUID(String guid);

    public String getGUID();

    public void setName(String name);

    public String getName();

}
