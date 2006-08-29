package gov.nih.nci.ncicb.cadsr.edci.domain.impl;

import gov.nih.nci.ncicb.cadsr.edci.domain.GroupRefHelp;

public class GroupRefHelpImpl implements GroupRefHelp {
   
    private String helpText;
    private String language;

    public GroupRefHelpImpl() {
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
}
