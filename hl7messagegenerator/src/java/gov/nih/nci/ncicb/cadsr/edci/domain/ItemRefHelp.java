package gov.nih.nci.ncicb.cadsr.edci.domain;

import java.io.Serializable;

public interface ItemRefHelp extends Serializable {

    public void setHelpText(String helpText);

    public String getHelpText();

    public void setLanguage(String language);

    public String getLanguage();
    
}
