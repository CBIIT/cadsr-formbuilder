package gov.nih.nci.ncicb.cadsr.formbuilder.common;

import java.io.*;
import java.util.Properties;

public class FormCartHandlingOptionsUtil
{
	static private FormCartHandlingOptionsUtil _instance = null;

	// note: these are not mutually exclusive
	private boolean writeV1Format = true;
	private boolean writeV2Format = false;

	public boolean writeInV1Format()
	{
		return writeV1Format;
	}

	public boolean writeInV2Format()
	{
		return writeV2Format;
	}

	protected FormCartHandlingOptionsUtil(){
		try{
			InputStream propertyFileStream = this.getClass().getResourceAsStream("/FormCartHandling.properties") ;
			Properties props = new Properties();
			props.load(propertyFileStream);
			String valueV1 = props.getProperty("writeV1Format");
			String valueV2 = props.getProperty("writeV2Format");
	
			writeV1Format = Boolean.valueOf(valueV1);
			writeV2Format = Boolean.valueOf(valueV2);
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

