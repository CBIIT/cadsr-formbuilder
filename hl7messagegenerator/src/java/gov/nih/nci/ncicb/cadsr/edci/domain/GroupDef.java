package gov.nih.nci.ncicb.cadsr.edci.domain;

import java.io.Serializable;

import java.util.Collection;

public interface GroupDef extends Serializable{

    public void setDataElementGroupGUID(String dataElementGroupGUID);

    public String getDataElementGroupGUID();

    public void setDescription(String description);

    public String getDescription();

    public void setId(String id);

    public String getId();

    public void setRepeatingGroupFlag(String repeatingGroupFlag);
    
    public String getRepeatingGroupFlag();

    public void setGroupAliasCollection(Collection groupAliasCollection);

    public Collection getGroupAliasCollection();

    public void setItemDefCollection(Collection itemDefCollection);

    public Collection getItemDefCollection();
}
