package gov.nih.nci.ncicb.cadsr.edci.domain;

import java.util.Collection;

public class DataElementGroup {
    private String GUID;
    private String description;
    private String name;
    private String namespace;
    private Collection<DataElement> dataElementCollection;
    public DataElementGroup() {
    }

    public void setGUID(String gUID) {
        this.GUID = gUID;
    }

    public String getGUID() {
        return GUID;
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

    public void setDataElementCollection(Collection<DataElement> dataElementCollection) {
        this.dataElementCollection = dataElementCollection;
    }

    public Collection<DataElement> getDataElementCollection() {
        return dataElementCollection;
    }
}
