package gov.nih.nci.ncicb.cadsr.edci.domain;

import java.io.Serializable;

public interface DataElementText extends Serializable{

    public void setLanguage(String language);

    public String getLanguage();

    public void setPrompt(String prompt);
    
    public String getPrompt();
    
}
