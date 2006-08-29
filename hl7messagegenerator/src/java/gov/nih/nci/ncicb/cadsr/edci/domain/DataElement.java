package gov.nih.nci.ncicb.cadsr.edci.domain;

import java.io.Serializable;

import java.util.Collection;

public interface DataElement extends Serializable{

    public void setEVDSubsetId(String eVDSubsetId) ;

    public String getEVDSubsetId() ;


    public void setAlternateDesignationCollection(Collection<AlternateDesignation> alternateDesignationCollection) ;

    public Collection<AlternateDesignation> getAlternateDesignationCollection() ;
    
    public void addAlternateDesignation(AlternateDesignation alternateDesignation);
    
    public void setDataElementTextCollection(Collection<DataElementText> dataElementTextCollection) ;
    
    public Collection<DataElementText> getDataElementTextCollection() ;
    
    public void addDataElementText(DataElementText dataElementText);

    public void setDataElementConceptGUID(String dataElementConceptGUID);

    public String getDataElementConceptGUID() ;

    public void setDefinition(String definition);

    public String getDefinition() ;

    public void setDescription(String description) ;

    public String getDescription() ;

    public void setName(String name) ;

    public String getName() ;

    public void setNamespace(String namespace) ;

    public String getNamespace() ;

    public void setValueDomainGUID(String valueDomainGUID) ;

    public String getValueDomainGUID() ;
    
    public void setGUID(String gUID) ;

    public String getGUID() ;
}
