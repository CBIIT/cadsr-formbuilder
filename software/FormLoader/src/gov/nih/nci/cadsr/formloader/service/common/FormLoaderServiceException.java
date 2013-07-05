package gov.nih.nci.cadsr.formloader.service.common;


public class FormLoaderServiceException extends Exception {
	
	protected int errorCode;
	
	FormLoaderServiceError error; 
	
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
