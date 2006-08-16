package gov.nih.nci.ncicb.cadsr.edci.domain;

public class DataElementText {
    private String language;
    private String prompt;
    
    public DataElementText() {
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLanguage() {
        return language;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public String getPrompt() {
        return prompt;
    }
}
