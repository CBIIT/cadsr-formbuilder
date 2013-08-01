package gov.nih.nci.cadsr.formloader.service.common;


public class FormLoaderServiceException extends Exception {
	
	public static final int ERROR_NO_ERROR = 0;
	public static final int ERROR_MALFORMED_XML = 1;
	public static final int ERROR_EMPTY_FORM_LIST = 2;
	public static final int ERROR_FILE_INVALID = 3;
	
	protected int errorCode;
	
	FormLoaderServiceError error; 
	
	public  FormLoaderServiceException(int errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}
	
	public FormLoaderServiceException(int errorCode, String message, FormLoaderServiceError error)  {
		super(message);
		this.errorCode = errorCode;
		this.error = error;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public FormLoaderServiceError getError() {
		return error;
	}

	public void setError(FormLoaderServiceError error) {
		this.error = error;
	}
	

}
