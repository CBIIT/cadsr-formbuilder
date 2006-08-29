package gov.nih.nci.ncicb.cadsr.edci.domain.impl;

import gov.nih.nci.ncicb.cadsr.edci.domain.CheckValue;

public class CheckValueImpl implements CheckValue{

       private String checkValue;

        public void setCheckValue(String checkValue) {
            this.checkValue = checkValue;
        }

        public String getCheckValue() {
            return checkValue;
        }
}
