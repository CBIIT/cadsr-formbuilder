package gov.nih.nci.ncicb.cadsr.edci.domain;

import java.io.Serializable;

import java.util.Collection;

public interface RangeCheck extends Serializable{

        public void setComparator(String comparator) ;

        public String getComparator() ;

        public void setSoftHard(String softHard) ;

        public String getSoftHard() ;

        public void setUnitOfMeasure(String unitOfMeasure) ;

        public String getUnitOfMeasure() ;

        public void setCheckValueCollection(Collection checkValueCollection);

        public Collection getCheckValueCollection() ;

        public void setRangeCheckTextCollection(Collection rangeCheckTextCollection) ;

        public Collection getRangeCheckTextCollection() ;

}
