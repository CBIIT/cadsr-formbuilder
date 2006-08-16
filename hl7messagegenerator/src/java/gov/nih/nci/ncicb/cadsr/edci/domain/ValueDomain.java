package gov.nih.nci.ncicb.cadsr.edci.domain;

public abstract class ValueDomain {
    private String datatype;
    private String decimalPlaces;
    private String description;
    private String GUID;
    private String maximumLength;
    private String name;
    private String namespace;
    private String sasFormatName;
    
    public ValueDomain() {
    }

    public void setDatatype(String datatype) {
        this.datatype = datatype;
    }

    public String getDatatype() {
        return datatype;
    }

    public void setDecimalPlaces(String decimalPlaces) {
        this.decimalPlaces = decimalPlaces;
    }

    public String getDecimalPlaces() {
        return decimalPlaces;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setGUID(String gUID) {
        this.GUID = gUID;
    }

    public String getGUID() {
        return GUID;
    }

    public void setMaximumLength(String maximumLength) {
        this.maximumLength = maximumLength;
    }

    public String getMaximumLength() {
        return maximumLength;
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

    public void setSasFormatName(String sasFormatName) {
        this.sasFormatName = sasFormatName;
    }

    public String getSasFormatName() {
        return sasFormatName;
    }
}
