/*L
 * Copyright Oracle Inc, ScenPro Inc, SAIC-F
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-formbuilder/LICENSE.txt for details.
 */

package gov.nih.nci.cadsr.formloader.service.common;


import org.apache.log4j.Logger;

public class XmlValidationError extends FormLoaderServiceError {
	
	private static Logger logger = Logger.getLogger(XmlValidationError.class.getName());
	
	public static final String XML_NO_ERROR = "NoError";
	public static final String XML_WARNING = "Warning";
	public static final String XML_ERROR = "Error";
	public static final String XML_FATAL_ERROR = "FatalError";
	public static final String XML_FILE_NOT_FOUND = "FileNotFound";
	public static final String XML_FILE_INVALID = "InvalidFile";
	
	private int lineNumber;
	private String message;
	private String type;
	
	public XmlValidationError() {
		this.type = XML_NO_ERROR;
	}
	
	public XmlValidationError(String type, String message, int lineNumber) {
		this.type = type;
		this.message = message;
		this.lineNumber = lineNumber;
	}
	
	public int getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String toString() {
		return this.type + " at " + this.lineNumber + ": " + this.message;
	}

}
