package gov.nih.nci.ncicb.cadsr.edci.domain.impl;

import gov.nih.nci.ncicb.cadsr.edci.domain.RangeCheckText;

public class RangeCheckTextImpl implements RangeCheckText {
    public RangeCheckTextImpl() {
    }

        private String errorMessage;
        private String language;


        public void setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public String getLanguage() {
            return language;
        }
   
}
