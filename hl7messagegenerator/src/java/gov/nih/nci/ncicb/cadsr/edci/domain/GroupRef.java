package gov.nih.nci.ncicb.cadsr.edci.domain;

import gov.nih.nci.ncicb.cadsr.edci.domain.impl.ItemRefImpl;

import java.util.Collection;

public interface GroupRef  {

    public void setGroupDef(GroupDef groupDef);

    public GroupDef getGroupDef();

    public void setId(String id);

    public String getId();

    public void setMaximumItemRefRepeats(String maximumItemRefRepeats);
    
    public String getMaximumItemRefRepeats();

    public void setName(String name);

    public String getName();

    public void setNavigationSequenceNumber(String navigationSequenceNumber);

    public String getNavigationSequenceNumber();

    public void setItemRefCollection(Collection<ItemRef> itemRefCollection);

    public Collection<ItemRef> getItemRefCollection();

    public void setGroupRefHelpCollection(Collection<GroupRefHelp> groupRefHelpCollection);

    public Collection<GroupRefHelp> getGroupRefHelpCollection();
    
}
