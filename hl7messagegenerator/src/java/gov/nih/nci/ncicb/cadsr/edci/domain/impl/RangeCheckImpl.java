package gov.nih.nci.ncicb.cadsr.edci.domain.impl;

import gov.nih.nci.ncicb.cadsr.edci.domain.CheckValue;
import gov.nih.nci.ncicb.cadsr.edci.domain.RangeCheck;

import gov.nih.nci.ncicb.cadsr.edci.domain.RangeCheckText;

import java.util.Collection;

public class RangeCheckImpl implements RangeCheck{
    public RangeCheckImpl() {
    }

        private String comparator;
        private String softHard;
        private String unitOfMeasure;
        private Collection<CheckValue> checkValueCollection;
        private Collection<RangeCheckText> rangeCheckTextCollection;
        

        public void setComparator(String comparator) {
            this.comparator = comparator;
        }

        public String getComparator() {
            return comparator;
        }

        public void setSoftHard(String softHard) {
            this.softHard = softHard;
        }

        public String getSoftHard() {
            return softHard;
        }

        public void setUnitOfMeasure(String unitOfMeasure) {
            this.unitOfMeasure = unitOfMeasure;
        }

        public String getUnitOfMeasure() {
            return unitOfMeasure;
        }

        public void setCheckValueCollection(Collection checkValueCollection) {
            this.checkValueCollection = checkValueCollection;
        }

        public Collection getCheckValueCollection() {
            return checkValueCollection;
        }

        public void setRangeCheckTextCollection(Collection rangeCheckTextCollection) {
            this.rangeCheckTextCollection = rangeCheckTextCollection;
        }

        public Collection getRangeCheckTextCollection() {
            return rangeCheckTextCollection;
        }
    
}
