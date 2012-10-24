package gov.nih.nci.ncicb.cadsr.formbuilder.common;

import java.io.*;
import java.util.Properties;

public class FormCartHandlingOptionsUtil
{
	static private FormCartHandlingOptionsUtil _instance = null;

	// note: write formats are not mutually exclusive
	private boolean writeV1Format = false;
	private boolean writeV2Format = true;

	// note: may only be one read format, meant for development/transition use than long-term use
	private boolean readV2Format = true;


	public boolean writeInV1Format()
	{
		return writeV1Format;
	}

	public boolean writeInV2Format()
	{
		return writeV2Format;
	}

	public boolean readInV1Format()
	{
		// read format is controlled by readV2Format
		return !readV2Format;
	}

	public boolean readInV2Format()
	{
		return readV2Format;
	}

	protected FormCartHandlingOptionsUtil(){
		try{
			InputStream propertyFileStream = this.getClass().getResourceAsStream("/FormCartHandling.properties") ;
			Properties props = new Properties();
			props.load(propertyFileStream);
			String value1 = props.getProperty("writeV1Format");
			String value2 = props.getProperty("writeV2Format");
			String value3 = props.getProperty("readV2Format");

			writeV1Format = Boolean.valueOf(value1);
			writeV2Format = Boolean.valueOf(value2);
			readV2Format = Boolean.valueOf(value3);
		} 
		catch(Exception e){
			System.out.println("FormCartHandlingOptionsUtil error loading properties: " + e);
		}	 
	}
	 
	static public FormCartHandlingOptionsUtil instance(){
		if (_instance == null) {
			_instance = new FormCartHandlingOptionsUtil();
		}

		return _instance;
	}
}

