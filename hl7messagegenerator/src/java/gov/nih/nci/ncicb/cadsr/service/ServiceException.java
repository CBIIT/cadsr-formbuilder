package gov.nih.nci.ncicb.cadsr.service;

public class ServiceException extends Exception {
    public ServiceException() {
    }
    
    public ServiceException(String msg) {
       super(msg);
    }    
}
