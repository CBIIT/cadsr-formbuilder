package gov.nih.nci.ncicb.cadsr.dao;

import gov.nih.nci.ncicb.cadsr.edci.domain.AlternateDesignation;
import gov.nih.nci.ncicb.cadsr.edci.domain.DataElement;
import gov.nih.nci.ncicb.cadsr.edci.domain.DataElementConcept;
import gov.nih.nci.ncicb.cadsr.edci.domain.DataElementGroup;
import gov.nih.nci.ncicb.cadsr.edci.domain.DataElementText;
import gov.nih.nci.ncicb.cadsr.edci.domain.EVDElement;
import gov.nih.nci.ncicb.cadsr.edci.domain.EVDElementText;
import gov.nih.nci.ncicb.cadsr.edci.domain.EVDSubset;
import gov.nih.nci.ncicb.cadsr.edci.domain.ElementInSubset;
import gov.nih.nci.ncicb.cadsr.edci.domain.ExplicitItemRefRepetition;
import gov.nih.nci.ncicb.cadsr.edci.domain.GlobalDefinitions;
import gov.nih.nci.ncicb.cadsr.edci.domain.GroupAlias;
import gov.nih.nci.ncicb.cadsr.edci.domain.GroupDef;
import gov.nih.nci.ncicb.cadsr.edci.domain.GroupRef;
import gov.nih.nci.ncicb.cadsr.edci.domain.Instruction;
import gov.nih.nci.ncicb.cadsr.edci.domain.Instrument;
import gov.nih.nci.ncicb.cadsr.edci.domain.ItemDef;
import gov.nih.nci.ncicb.cadsr.edci.domain.ItemRef;
import gov.nih.nci.ncicb.cadsr.edci.domain.ItemRefPrompt;
import gov.nih.nci.ncicb.cadsr.edci.domain.SectionDef;
import gov.nih.nci.ncicb.cadsr.edci.domain.SectionRef;
import gov.nih.nci.ncicb.cadsr.edci.domain.TriggeredAction;
import gov.nih.nci.ncicb.cadsr.edci.domain.ValueDomain;
//import gov.nih.nci.ncicb.cadsr.edci.domain.impl.GlobalDefinitionsImpl;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
/**
 * Generate new instances of eDCI domain objects.
 */
public class DomainObjectFactory {
    BeanFactory beanFactory = null;
        
    public DomainObjectFactory() {
        ClassPathResource resource = new ClassPathResource("beans.xml");
        beanFactory = new XmlBeanFactory(resource);    
    }
    
    public GlobalDefinitions getGlobalDefinitions()  {
          GlobalDefinitions globalDefinitions = (GlobalDefinitions)beanFactory.getBean("globalDefinitions");
          return globalDefinitions;
    }
    
    public AlternateDesignation getAlternateDesignation()  {
          AlternateDesignation alternateDesignation = (AlternateDesignation)beanFactory.getBean("alternateDesignation");
          return alternateDesignation;
    }
    public DataElement getDataElement()  {
          DataElement dataElement = (DataElement)beanFactory.getBean("dataElement");
          return dataElement;
    }
    
    public DataElementText getDataElementText()  {
          DataElementText dataElementText = (DataElementText)beanFactory.getBean("dataElementText");
          return dataElementText;
    }    
    
    public DataElementGroup getDataElementGroup()  {
          DataElementGroup dataElementGroup = (DataElementGroup)beanFactory.getBean("dataElementGroup");
          return dataElementGroup;
    }
    
    public DataElementConcept getDataElementConcept()  {
          DataElementConcept dataElementConcept = (DataElementConcept)beanFactory.getBean("dataElementConcept");
          return dataElementConcept;
    }
    
    public ValueDomain getValueDomain()  {
          ValueDomain valueDomain = (ValueDomain)beanFactory.getBean("valueDomain");
          return valueDomain;
    }    
    
    public EVDSubset getEVDSubset()  {
          EVDSubset eVDSubset = (EVDSubset)beanFactory.getBean("eVDSubset");
          return eVDSubset;
    }        
    
    public EVDElement getEVDElement()  {
          EVDElement eVDElement = (EVDElement)beanFactory.getBean("eVDElement");
          return eVDElement;
    }    
    
    public EVDElementText getEVDElementText()  {
          EVDElementText eVDElementText = (EVDElementText)beanFactory.getBean("eVDElementText");
          return eVDElementText;
    }     
    
    public ElementInSubset getElementInSubset()  {
          ElementInSubset elementInSubset = (ElementInSubset)beanFactory.getBean("elementInSubset");
          return elementInSubset;
    }     
    
    public ItemDef getItemDef() {
        ItemDef itemDef = (ItemDef)beanFactory.getBean("itemDef");
        return itemDef;
    }
    
    public Instrument getInstrument() {
        Instrument instrument = (Instrument)beanFactory.getBean("instrument");
        return instrument;
    }
    
    public GroupDef getGroupDef() {
        GroupDef groupDef = (GroupDef)beanFactory.getBean("groupDef");
        return groupDef;
    }
    
    public SectionDef getSectionDef() {
        SectionDef sectionDef = (SectionDef)beanFactory.getBean("sectionDef");
        return sectionDef;
    }
    
    public SectionRef getSectionRef() {
        SectionRef sectionRef = (SectionRef)beanFactory.getBean("sectionRef");
        return sectionRef;
    }
    
    public GroupRef getGroupRef() {
        GroupRef groupRef = (GroupRef)beanFactory.getBean("groupRef");
        return groupRef;
    }
    
    public ItemRef getItemRef() {
        ItemRef itemRef = (ItemRef)beanFactory.getBean("itemRef");
        return itemRef;
    }
    
    public GroupAlias getGroupAlias(){
        GroupAlias groupAlias = (GroupAlias)beanFactory.getBean("groupAlias");
        return groupAlias;
    }
    
    public ExplicitItemRefRepetition getExplicitItemRefRepetition(){
        ExplicitItemRefRepetition explicitItemRefRepetition = (ExplicitItemRefRepetition)beanFactory.getBean("explicitItemRefRepetition");
        return explicitItemRefRepetition;
    }    
    
    public TriggeredAction getTriggeredAction() {
        TriggeredAction triggeredAction = (TriggeredAction)beanFactory.getBean("triggeredAction");
        return triggeredAction;
    }
    
    public Instruction getInstruction() {
        Instruction instruction = (Instruction)beanFactory.getBean("instruction");
        return instruction;
    }
    
    public ItemRefPrompt getItemRefPrompt() {
        ItemRefPrompt itemRefPrompt = (ItemRefPrompt)beanFactory.getBean("itemRefPrompt");
        return itemRefPrompt;
    }    
}
