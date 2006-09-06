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
import gov.nih.nci.ncicb.cadsr.edci.domain.GlobalDefinitions;
import gov.nih.nci.ncicb.cadsr.edci.domain.ValueDomain;
//import gov.nih.nci.ncicb.cadsr.edci.domain.impl.GlobalDefinitionsImpl;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

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
}
