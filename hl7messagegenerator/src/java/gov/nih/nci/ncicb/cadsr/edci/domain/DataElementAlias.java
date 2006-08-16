package gov.nih.nci.ncicb.cadsr.edci.domain;

public class DataElementAlias {

    private String name;
    private String system;
    private String language;
    
    public DataElementAlias() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getSystem() {
        return system;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLanguage() {
        return language;
    }
}
