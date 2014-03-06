package gov.nih.nci.cadsr.formloader.service.common;


import gov.nih.nci.cadsr.formloader.domain.FormCollection;
import gov.nih.nci.cadsr.formloader.domain.FormDescriptor;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

public class FormLoaderHelper {
	
	private static Logger logger = Logger.getLogger(FormLoaderHelper.class.getName());
	
	private static Properties properties = null;
	
	public static String getProperty(String key) {
		
		if (properties == null)
			properties = FormLoaderHelper.loadPropertiesFromClassPath();
		
		return (properties == null) ? "" : properties.getProperty(key);	    
	}
	
	public static Properties loadPropertiesFromClassPath() {
		Properties props = new Properties();
               
        try { 
        	InputStream	in = FormLoaderHelper.class.getClassLoader().getResourceAsStream("formloader.properties");
            props.load(in);
        } catch ( Exception e ) {
        	logger.error("Error!! Unable to open property file from classpath");
        }
        
        return props;
	}
	
	public static String checkInputFile(String xmlPath, String xmlName) 
			throws FormLoaderServiceException
	{
		if (xmlPath == null || xmlPath.length() == 0)
			throw new FormLoaderServiceException(FormLoaderServiceException.ERROR_FILE_INVALID,
					"Input file path on server is null or empty. Unable to validate form content.");

		if (xmlName == null || xmlName.length() == 0)
			throw new FormLoaderServiceException(FormLoaderServiceException.ERROR_FILE_INVALID,
					"Input file name is null or empty. Unable to validate form content.");
		
		String xmlPathName = xmlPath.endsWith("/") ? xmlPath + xmlName : xmlPath + "/" + xmlName;
		
		//xmlPath = FormLoaderHelper.resolveWindowsPathIfNecessary(xmlPath);
		
		File input = new File(xmlPathName);
		if (input == null || !input.exists() || !input.canRead())
			throw new FormLoaderServiceException(FormLoaderServiceException.ERROR_FILE_INVALID,
					"Input file [" + xmlPathName + "] is invalid. Does it exist? Unable to proceed to load form collection.");


		return xmlPathName;
	}
	
	public static String resolveWindowsPathIfNecessary(String pathName) {
		
		if (pathName == null) 
			return pathName;
		
		String osName = System.getProperty("os.name");;
		
		if (osName == null || !osName.startsWith("Windows"))
			return pathName;
		
		if (pathName.contains(":")) 
			return pathName;
		
		String workingDir = System.getProperty("user.dir");
	
		if (!workingDir.endsWith("/"))
			workingDir += "/";
		
		if (pathName.startsWith("."))
			pathName = pathName.substring(1);
		
		if (pathName.startsWith("/") || pathName.startsWith("\\"))
			pathName = pathName.substring(1);
		
		return workingDir + pathName;
	}

	public static XmlValidationError filePahtNameContainsError(String filePathName) {
		if (filePathName == null)
			return new XmlValidationError(XmlValidationError.XML_FILE_INVALID, "File path name is null", 0);
		else {
			File xml = new File(filePathName);
			if (!xml.exists() || !xml.canRead()) {
				return new XmlValidationError(XmlValidationError.XML_FILE_NOT_FOUND, 
						"File doesn't exist or can't be read at " + filePathName, 0);
			}
		}
		
		//if we're here, we're good
		return null;
	}
	
	public static String checkFilePathName(String filePathName) {
		if (filePathName == null)
			return "File path name is null";
		else {
			File xml = new File(filePathName);
			if (!xml.exists() || !xml.canRead()) {
				return "File doesn't exist or can't be read at " + filePathName;
			}
		}
		
		//if we're here, we're good
		return "";
	}
	
	public static XmlValidationError inputFilePathNamesContainsError(String xmlPathName, String xsdPathName) {
		
		XmlValidationError error = filePahtNameContainsError(xmlPathName);
		if (error == null) {
			error = filePahtNameContainsError(xsdPathName);
		}
		
		return error;
	}
	
	/**
	 * This mimicks the xpath function normalize-space(): whitespace normalized by stripping leading and 
	 * trailing whitespace and replacing sequences of whitespace characters by a single space.
	 * 
	 * @param input
	 * @return
	 */
	public static String normalizeSpace(String input) {
		if (input == null || input.length() == 0)
			return input;
		
		String in = input.trim();
		int len = in.length();
		
		char[] outArr = new char[len+1];
		int outIdx = 0;
		int spaceCnt = 0;
		for (int i = 0; i < len; i++) {
			char c = in.charAt(i);
			
			if (c != 32) {
				outArr[outIdx++] = c;
				spaceCnt = 0; 
				continue;
			}
			
			spaceCnt++;
			if (spaceCnt == 1)
				outArr[outIdx++] = c;
		}
		
		return new String(outArr).trim();
		
	}
	
	
	public static void saveCollectionListToFile (List<FormCollection> colls) {
		final String fileName = "collectionList.ser";

		String filePathName = fileName;
		File objs = new File(filePathName);

		try {
			if (!objs.exists()) {
				
				// Serialize data object to a file
				ObjectOutputStream out = new ObjectOutputStream(
						new FileOutputStream(filePathName));
				out.writeObject(colls);
				/*
				 * Iterator ite = pvs.keySet().iterator(); while (ite.hasNext())
				 * { out.writeObject(pvs.get(ite.next())); }
				 */
				out.close();

			}
		} catch (FileNotFoundException fne) {
			System.out.println(fne);
		} catch (IOException ioe) {
			System.out.println(ioe);
		}

		
	}
	
	public static void saveFormListToFile (List<FormDescriptor> forms) {
		final String fileName = "formList.ser";

		String filePathName = fileName;
		File objs = new File(filePathName);

		try {
			if (!objs.exists()) {
				
				// Serialize data object to a file
				ObjectOutputStream out = new ObjectOutputStream(
						new FileOutputStream(filePathName));
				out.writeObject(forms);
				/*
				 * Iterator ite = pvs.keySet().iterator(); while (ite.hasNext())
				 * { out.writeObject(pvs.get(ite.next())); }
				 */
				out.close();

			}
		} catch (FileNotFoundException fne) {
			System.out.println(fne);
		} catch (IOException ioe) {
			System.out.println(ioe);
		}

		
	}

	public static List<FormDescriptor> readFormListFromFile () {
		final String fileName = "formList.ser";

		String filePathName = fileName;
		File objs = new File(filePathName);
		List<FormDescriptor> forms = null;

		try {
			InputStream file = new FileInputStream(filePathName);
			InputStream buffer = new BufferedInputStream(file);
			ObjectInput input = new ObjectInputStream (buffer);

			//deserialize the List
			forms = (List<FormDescriptor>)input.readObject();

		}
		catch(ClassNotFoundException ex){

		}
		catch(IOException ex){

		}


		return forms;

	}
	
	//For testing
	public static List<FormCollection> readCollectionListFromFile () {
		final String fileName = "collectionList.ser";

		String filePathName = fileName;
		File objs = new File(filePathName);
		List<FormCollection> colls = null;

		try {
			InputStream file = new FileInputStream(filePathName);
			InputStream buffer = new BufferedInputStream(file);
			ObjectInput input = new ObjectInputStream (buffer);

			//deserialize the List
			colls = (List<FormCollection>)input.readObject();

		}
		catch(ClassNotFoundException ex){

		}
		catch(IOException ex){

		}


		return colls;

	}
	
	public static List<FormCollection> sortCollectionsByDate(List<FormCollection> collections) {
		
		Collections.sort(collections, new Comparator<FormCollection>(){
			public int compare(FormCollection o1, FormCollection o2) {
		        return o1.getDateCreated().compareTo(o2.getDateCreated());
		    }
		});
		
		return collections;
	}
	
	public static List<FormCollection> reversSortCollectionsByDate(List<FormCollection> collections) {
		
		List<FormCollection> colls = FormLoaderHelper.sortCollectionsByDate(collections);
		Collections.reverse(colls);
		
		return colls;
	}
	
	public static List<FormCollection> sortCollectionsByName(List<FormCollection> collections) {

		Collections.sort(collections, new Comparator<FormCollection>(){
			public int compare(FormCollection o1, FormCollection o2) {
				return o1.getNameWithRepeatIndicator().compareTo(o2.getNameWithRepeatIndicator());
			}
		});

		return collections;
	}
	
	public static List<FormCollection> reverseSortCollectionsByName(List<FormCollection> collections) {

		List<FormCollection> colls = FormLoaderHelper.sortCollectionsByName(collections);
		Collections.reverse(colls);

		return collections;
	}
	
	/**
	 * Format version number into #0.0 form
	 * @param versionNumber
	 * @return
	 */
	public static String formatVersion(float versionNumber) {
		NumberFormat formatter = new DecimalFormat("#0.0");
		return formatter.format(versionNumber);
	}
	
	public static boolean samePublicIdVersions(String publicId, String version, int publicIdInt, float versionInFloat) {
		if (publicId == null && publicIdInt > 0)
			return false;
		
		if (version == null && versionInFloat > 0)
			return false;
		
		try {
			if (Integer.parseInt(publicId) != publicIdInt)
				return false;
			
			if (Float.parseFloat(version) != versionInFloat)
				return false;
		} catch (NumberFormatException ne) {
			return false;
		}
		
		return true;
	}
	
}
