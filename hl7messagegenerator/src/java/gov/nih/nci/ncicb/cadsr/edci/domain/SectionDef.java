package gov.nih.nci.ncicb.cadsr.edci.domain;

import java.io.Serializable;

import java.util.Collection;

public interface SectionDef extends Serializable{

    public void setDescription(String description);

    public String getDescription();

    public void setDifferentiatorItem(String differentiatorItem);

    public String getDifferentiatorItem();

    public void setGUID(String gUID);

    public String getGUID();

    public void setId(String id);

    public String getId();

    public void setName(String name);

    public String getName();

    public void setGroupDefCollection(Collection groupDefCollection);

    public Collection getGroupDefCollection() ;

    public void setItemDefCollection(Collection itemDefCollection);

    public Collection getItemDefCollection();
    
}
