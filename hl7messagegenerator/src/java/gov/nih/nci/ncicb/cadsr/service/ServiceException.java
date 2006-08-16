package gov.nih.nci.ncicb.cadsr.service;

public class ServiceException extends Exception {
    public ServiceException() {
    }
    
    public ServiceException(String msg) {
       super(msg);
    }    
    
    public ServiceException(Throwable throwable){
        super(throwable);
    }
    
    public ServiceException(String msg,Throwable throwable){
        super(msg,throwable);
    }    
}
