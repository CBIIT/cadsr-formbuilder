package gov.nih.nci.ncicb.cadsr.edci.domain;

import java.io.Serializable;

public interface ItemRefPrompt extends Serializable{

    public void setPrompt(String prompt);

    public String getPrompt();

    public void setLanguage(String language);

    public String getLanguage();
}
