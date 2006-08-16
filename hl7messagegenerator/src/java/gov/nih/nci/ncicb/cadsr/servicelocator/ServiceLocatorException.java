package gov.nih.nci.ncicb.cadsr.servicelocator;

public class ServiceLocatorException extends Exception {
    public ServiceLocatorException() {
    }
    
    public ServiceLocatorException(String msg){
        super(msg);
    }

    public ServiceLocatorException(Throwable throwable){
        super(throwable);
    }

    
    public ServiceLocatorException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}
