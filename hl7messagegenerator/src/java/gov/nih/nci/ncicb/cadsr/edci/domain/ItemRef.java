package gov.nih.nci.ncicb.cadsr.edci.domain;

import gov.nih.nci.ncicb.cadsr.edci.domain.impl.ExplicitItemRefRepetitionImpl;
import gov.nih.nci.ncicb.cadsr.edci.domain.impl.ItemDefImpl;
import gov.nih.nci.ncicb.cadsr.edci.domain.impl.ItemRefHelpImpl;
import gov.nih.nci.ncicb.cadsr.edci.domain.impl.ItemRefPromptImpl;
import gov.nih.nci.ncicb.cadsr.edci.domain.impl.TriggeredActionImpl;

import java.io.Serializable;

import java.util.Collection;

public interface ItemRef extends Serializable{

    public void setRangeCheckCollection(Collection<RangeCheck> rangeCheckCollection) ;

    public Collection<RangeCheck> getRangeCheckCollection();

    public void setDefaultValue(String defaultValue);

    public String getDefaultValue();

    public void setId(String id);

    public String getId();

    public void setIsCollectedFlag(String isCollectedFlag);

    public String getIsCollectedFlag();

    public void setIsMandatoryFlag(String isMandatoryFlag);
    
    public String getIsMandatoryFlag();

    public void setIsModifiableFlag(String isModifiableFlag);

    public String getIsModifiableFlag();

    public void setNavigationSequenceNumber(String navigationSequenceNumber) ;

    public String getNavigationSequenceNumber();

    public void setItemDef(ItemDef itemDef);
    
    public ItemDef getItemDef();

    public void setItemRefPromptCollection(Collection<ItemRefPrompt> itemRefPromptCollection) ;

    public Collection<ItemRefPrompt> getItemRefPromptCollection();

    public void setItemRefHelpCollection(Collection<ItemRefHelp> itemRefHelpCollection);

    public Collection<ItemRefHelp> getItemRefHelpCollection();

    public void setTriggeredActionCollection(Collection<TriggeredAction> triggeredActionCollection) ;

    public Collection<TriggeredAction> getTriggeredActionCollection();

    public void setExplicitItemRefRepetitionCollection(Collection<ExplicitItemRefRepetition> explicitItemRefRepetitionCollection) ;

    public Collection<ExplicitItemRefRepetition> getExplicitItemRefRepetitionCollection() ;
}
