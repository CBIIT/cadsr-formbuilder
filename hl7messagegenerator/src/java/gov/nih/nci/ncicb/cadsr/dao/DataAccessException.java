package gov.nih.nci.ncicb.cadsr.dao;

public class DataAccessException extends Exception {
    public DataAccessException() {
    }
    
    public DataAccessException(String msg){
        super(msg);
    }
    
    public DataAccessException(Throwable throwable){
        super(throwable);
    }
    
    public DataAccessException(String msg,Throwable throwable){
        super(msg,throwable);
    }    
}
