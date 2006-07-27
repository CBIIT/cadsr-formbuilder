package gov.nih.nci.ncicb.cadsr.dto;

import gov.nih.nci.cadsr.domain.Form;


/**
 * Holds metadata for all the relevant metadata for a Object required to
 * generate the CSV file for eDCI message generation
 */
public class FormMetaData {

    private Form form;
    
    public FormMetaData() {
    }


    public void setForm(Form form) {
        this.form = form;
    }

    public Form getForm() {
        return form;
    }
}
