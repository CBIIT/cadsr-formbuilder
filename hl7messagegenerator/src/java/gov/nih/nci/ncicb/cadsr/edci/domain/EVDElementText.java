package gov.nih.nci.ncicb.cadsr.edci.domain;

import java.io.Serializable;

public interface EVDElementText extends  Serializable {

    public void setHelpText(String helpText);

    public String getHelpText();

    public void setLanguage(String language);

    public String getLanguage();

    public void setValueMeaning(String valueMeaning);

    public String getValueMeaning();

    public void setValueMeaningDescription(String valueMeaningDescription);

    public String getValueMeaningDescription();
}
