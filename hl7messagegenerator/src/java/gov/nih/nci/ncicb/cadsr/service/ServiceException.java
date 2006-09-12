package gov.nih.nci.ncicb.cadsr.service;
/**
 * Exceptions occuring at the Service layer.
 */
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
