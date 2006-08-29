package gov.nih.nci.ncicb.cadsr.edci.domain.impl;

import gov.nih.nci.ncicb.cadsr.edci.domain.GraphicImage;
import gov.nih.nci.ncicb.cadsr.edci.domain.PersistentInformation;

public class GraphicImageImpl extends PersistentInformationImpl implements GraphicImage {

    private String source;
    
    public GraphicImageImpl() {
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSource() {
        return source;
    }
}
