package gov.nih.nci.ncicb.cadsr.edci.domain;

import java.io.Serializable;

public interface Instruction extends Serializable{

    public void setInstructionText(String instructionText);

    public String getInstructionText();

    public void setLanguage(String language);

    public String getLanguage();
}
