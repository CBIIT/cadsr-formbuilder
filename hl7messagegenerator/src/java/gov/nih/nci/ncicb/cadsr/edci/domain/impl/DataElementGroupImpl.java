package gov.nih.nci.ncicb.cadsr.edci.domain.impl;

import gov.nih.nci.ncicb.cadsr.edci.domain.DataElement;

import gov.nih.nci.ncicb.cadsr.edci.domain.DataElementGroup;
import gov.nih.nci.ncicb.cadsr.edci.domain.castor.Member;

import java.util.Collection;

public class DataElementGroupImpl extends gov.nih.nci.ncicb.cadsr.edci.domain.castor.DataElementGroup 
implements DataElementGroup{

    private Collection<DataElement> dataElementCollection;
    
    public DataElementGroupImpl() {
    }

    public void addDataElement(DataElement dataElement){
        dataElementCollection.add(dataElement);
        Member member = new Member();
        member.setDataElementGUID(dataElement.getGUID());
        addMember(member);
    }
    public void setDataElementCollection(Collection<DataElement> dataElementCollection) {
        this.dataElementCollection = dataElementCollection;
        for (DataElement dataElement:dataElementCollection) {
            Member member = new Member();
            member.setDataElementGUID(dataElement.getGUID());
            addMember(member);
        }
    }

    public Collection<DataElement> getDataElementCollection() {
        return dataElementCollection;
    }
}
