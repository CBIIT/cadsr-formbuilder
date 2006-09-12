package gov.nih.nci.ncicb.cadsr.serviceimpl;

import gov.nih.nci.ncicb.cadsr.service.CaAdapterService;

import java.io.File;


/**
 * Implements the CaAdapterService interface.
 */
public class CaAdapterServiceImpl implements CaAdapterService {

    private String csvEDCIGL7MappingFile;
    
    public CaAdapterServiceImpl() {
    }

    public String generateeDCIHL7Message(File csvFile) {
        return null;
    }

    public void setCsvEDCIGL7MappingFile(String csvEDCIGL7MappingFile) {
        this.csvEDCIGL7MappingFile = csvEDCIGL7MappingFile;
    }

    public String getCsvEDCIGL7MappingFile() {
        return csvEDCIGL7MappingFile;
    }
}
