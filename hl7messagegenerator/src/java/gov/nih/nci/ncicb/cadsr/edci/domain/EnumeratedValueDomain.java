package gov.nih.nci.ncicb.cadsr.edci.domain;

import java.util.Collection;

public class EnumeratedValueDomain extends ValueDomain{
    private String codingSystem;
    private String codingSystemVersion;
    private String domainId;
    private Collection<EVDElement> eVDElementCollection;
    
    public EnumeratedValueDomain() {
    }
}
