package gov.nih.nci.ncicb.cadsr.edci.domain;

import java.io.Serializable;

public interface RangeCheckText extends Serializable{

        public void setErrorMessage(String errorMessage);

        public String getErrorMessage();

        public void setLanguage(String language);

        public String getLanguage();

}
