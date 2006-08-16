package gov.nih.nci.ncicb.cadsr.edci.domain;

public class EVDElementText {
    private String helpText;
    private String language;
    private String valueMeaning;
    private String valueMeaningDescription;
    public EVDElementText() {
    }

    public void setHelpText(String helpText) {
        this.helpText = helpText;
    }

    public String getHelpText() {
        return helpText;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLanguage() {
        return language;
    }

    public void setValueMeaning(String valueMeaning) {
        this.valueMeaning = valueMeaning;
    }

    public String getValueMeaning() {
        return valueMeaning;
    }

    public void setValueMeaningDescription(String valueMeaningDescription) {
        this.valueMeaningDescription = valueMeaningDescription;
    }

    public String getValueMeaningDescription() {
        return valueMeaningDescription;
    }
}
