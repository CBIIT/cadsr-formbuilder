package gov.nih.nci.ncicb.cadsr.edci.domain.impl;

import gov.nih.nci.ncicb.cadsr.edci.domain.ItemRefPrompt;

public class ItemRefPromptImpl implements ItemRefPrompt{

    private String prompt;
    private String language;
    
    public ItemRefPromptImpl() {
    }


    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLanguage() {
        return language;
    }
}
