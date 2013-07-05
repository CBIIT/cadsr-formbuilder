package gov.nih.nci.cadsr.formloader.domain;

import java.util.List;

public class Status {
	
	public static final String STATUS_XML_VALIDATED = "XML_VALIDATED"; 
	public static final String STATUS_DB_VALIDATED = "DB_VALIDATED";
	public static final String STATUS_LOADED = "LOADED";
	public static final String STATUS_UNLOADED = "UNLOADED";
	public static final String STATUS_ERROR = "ERROR";
	
	private String type;
	private List<String> messages;

}
