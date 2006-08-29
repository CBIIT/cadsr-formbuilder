package gov.nih.nci.ncicb.cadsr.edci.domain.impl;

import gov.nih.nci.ncicb.cadsr.edci.domain.Instruction;
import gov.nih.nci.ncicb.cadsr.edci.domain.PersistentInformation;

public class InstructionImpl extends PersistentInformationImpl implements Instruction{

    private String instructionText;
    private String language;
    
    public InstructionImpl() {
    }

    public void setInstructionText(String instructionText) {
        this.instructionText = instructionText;
    }

    public String getInstructionText() {
        return instructionText;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLanguage() {
        return language;
    }
}
