package gov.nih.nci.ncicb.cadsr.edci.domain;

public class ValueDomain {
    private String datatype;
    private Integer decimalPlaces;
    private String description;
    private String GUID;
    private Integer maximumLength;
    private String name;
    private String namespace;
    private String sasFormatName;
    private String isEnumeratedFlag;
    
    public ValueDomain() {
    }

    public void setDatatype(String datatype) {
        this.datatype = datatype;
    }

    public String getDatatype() {
        return datatype;
    }

    public void setDecimalPlaces(Integer decimalPlaces) {
        this.decimalPlaces = decimalPlaces;
    }

    public Integer getDecimalPlaces() {
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

    public void setMaximumLength(Integer maximumLength) {
        this.maximumLength = maximumLength;
    }

    public Integer getMaximumLength() {
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
    
    public void setIsEnumeratedFlag(String isEnumeratedFlag) {
        this.isEnumeratedFlag = isEnumeratedFlag;
    }

    public String getIsEnumeratedFlag() {
        return isEnumeratedFlag;
    }
}
