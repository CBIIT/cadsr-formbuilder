package gov.nih.nci.ncicb.cadsr.edci.domain;

public class DataElementConcept {
    private String definition;
    private String description;
    private String guid;
    private String name;
    private String intent;
    
    public DataElementConcept() {
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

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getGuid() {
        return guid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setIntent(String intent) {
        this.intent = intent;
    }

    public String getIntent() {
        return intent;
    }
}
