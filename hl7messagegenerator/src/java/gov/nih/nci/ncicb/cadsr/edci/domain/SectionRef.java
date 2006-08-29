package gov.nih.nci.ncicb.cadsr.edci.domain;

import gov.nih.nci.ncicb.cadsr.edci.domain.impl.SectionDefImpl;
import gov.nih.nci.ncicb.cadsr.edci.domain.impl.SectionRefHelpImpl;

import java.io.Serializable;

import java.util.Collection;

public interface SectionRef extends Serializable {

    public void setId(String id);

    public String getId();

    public void setName(String name);

    public String getName();

    public void setNavigationSequenceNumber(String navigationSequenceNumber);

    public String getNavigationSequenceNumber();

    public void setItemRefCollection(Collection itemRefCollection);

    public Collection getItemRefCollection();

    public void setGroupRefCollection(Collection groupRefCollection);

    public Collection getGroupRefCollection();

    public void setSectionDef(SectionDef sectionDef);

    public SectionDef getSectionDef();

    public void setSectionRefHelpCollection(Collection<SectionRefHelp> sectionRefHelpCollection);

    public Collection<SectionRefHelp> getSectionRefHelpCollection();
}
