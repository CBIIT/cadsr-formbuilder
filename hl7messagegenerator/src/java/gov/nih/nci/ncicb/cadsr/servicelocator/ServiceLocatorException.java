package gov.nih.nci.ncicb.cadsr.servicelocator;
/**
 * An exception occurred finding an external resource.
 */
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
