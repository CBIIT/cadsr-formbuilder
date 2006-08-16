package gov.nih.nci.ncicb.cadsr.edci.domain;

import java.util.Collection;

public class DataElement {
    private String eVDSubsetId;
    private String GUID;
    private String dataElementConceptGUID;
    private String definition;
    private String description;
    private String name;
    private String namespace;
    private String valueDomainGUID;
    private Collection<DataElementGroup> dataElementGroupCollection;
    private Collection<DataElementAlias> dataElementAliasCollection;
    private Collection<DataElementText> dataElementTextCollection;

    public DataElement() {
    }

    public void setEVDSubsetId(String eVDSubsetId) {
        this.eVDSubsetId = eVDSubsetId;
    }

    public String getEVDSubsetId() {
        return eVDSubsetId;
    }


    public void setDataElementAliasCollection(Collection<DataElementAlias> dataElementAliasCollection) {
        this.dataElementAliasCollection = dataElementAliasCollection;
    }

    public Collection<DataElementAlias> getDataElementAliasCollection() {
        return dataElementAliasCollection;
    }

    public void setDataElementTextCollection(Collection<DataElementText> dataElementTextCollection) {
        this.dataElementTextCollection = dataElementTextCollection;
    }

    public Collection<DataElementText> getDataElementTextCollection() {
        return dataElementTextCollection;
    }

    public void setDataElementConceptGUID(String dataElementConceptGUID) {
        this.dataElementConceptGUID = dataElementConceptGUID;
    }

    public String getDataElementConceptGUID() {
        return dataElementConceptGUID;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setValueDomainGUID(String valueDomainGUID) {
        this.valueDomainGUID = valueDomainGUID;
    }

    public String getValueDomainGUID() {
        return valueDomainGUID;
    }

    public void setDataElementGroupCollection(Collection<DataElementGroup> dataElementGroupCollection) {
        this.dataElementGroupCollection = dataElementGroupCollection;
    }

    public Collection<DataElementGroup> getDataElementGroupCollection() {
        return dataElementGroupCollection;
    }

    public void setGUID(String gUID) {
        this.GUID = gUID;
    }

    public String getGUID() {
        return GUID;
    }
}
