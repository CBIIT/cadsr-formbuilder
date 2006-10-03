/**
 * <!-- LICENSE_TEXT_START -->
 * $Header: /share/content/gforge/formbuilder/hl7messagegenerator/config/caadapter/src/gov/nih/nci/hl7/util/FileUtil.java,v 1.1 2006-10-03 17:38:44 marwahah Exp $
 *
 * ******************************************************************
 * COPYRIGHT NOTICE
 * ******************************************************************
 *
 * The caAdapter Software License, Version 1.3
 * Copyright Notice.
 * 
 * Copyright 2006 SAIC. This software was developed in conjunction with the National Cancer Institute. To the extent government employees are co-authors, any rights in such works are subject to Title 17 of the United States Code, section 105. 
 * 
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met: 
 * 
 * 1. Redistributions of source code must retain the Copyright Notice above, this list of conditions, and the disclaimer of Article 3, below. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution. 
 * 
 * 2. The end-user documentation included with the redistribution, if any, must include the following acknowledgment:
 * 
 * 
 * "This product includes software developed by the SAIC and the National Cancer Institute."
 * 
 * 
 * If no such end-user documentation is to be included, this acknowledgment shall appear in the software itself, wherever such third-party acknowledgments normally appear. 
 * 
 * 3. The names "The National Cancer Institute", "NCI" and "SAIC" must not be used to endorse or promote products derived from this software. 
 * 
 * 4. This license does not authorize the incorporation of this software into any third party proprietary programs. This license does not authorize the recipient to use any trademarks owned by either NCI or SAIC-Frederick. 
 * 
 * 5. THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES, (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO EVENT SHALL THE UNITED STATES GOVERNMENT, THE NATIONAL CANCER INSTITUTE, SAIC, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * <!-- LICENSE_TEXT_END -->
 */


package gov.nih.nci.hl7.util;


import org.hl7.util.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.FileHandler;

/**
 * File related utility class
 *
 * @author OWNER: Matthew Giordano
 * @author LAST UPDATE $Author: marwahah $
 * @version $Revision: 1.1 $
 */

public class FileUtil
{
	private static final String OUTPUT_DIR_NAME = "out";
	private static File OUTPUT_DIR = null;

	/**
	 * Create the output directory if it doesn't exist.
	 */
	private static void setupOutputDir()
	{
		OUTPUT_DIR = new File(OUTPUT_DIR_NAME);
		if (!OUTPUT_DIR.isDirectory())
		{
			OUTPUT_DIR.mkdir();
		}
	}

	/**
	 * Create the output directory if necessary and return a reference to it.
	 *
	 * @return The output directory
	 */
	public static File getOutputDir()
	{
		FileUtil.setupOutputDir();
		return OUTPUT_DIR;
	}

	public static String getWorkingDirPath()
	{
		File f = new File("");
		return f.getAbsolutePath();
	}

    public static String getExamplesDirPath()
	{
		File f = new File("./workingspace/examples");
		return f.getAbsolutePath();
	}

    public static String getSchemaFile(String messageId) throws FileNotFoundException
    {
        String fileLocation = StringUtils.searchMessageTypeFileName(Config.SCHEMA_LOCATION, messageId, "xsd");
        return fileLocation;
    }

	/**
	 * Return a convenient UI Working Directory, which may or may not be the same as the value from getWorkingDirPath().
	 * @return a convenient UI Working Directory, which may or may not be the same as the value from getWorkingDirPath().
	 */
	public static String getUIWorkingDirectoryPath()
	{
        File f = new File("./workingspace");
        return f.getAbsolutePath();
	}
	/**
	 * Delete a lck file from the output directory.  A lck file is a temporary file that is
	 * created by the logger.
	 *
	 * @param filename
	 */
	public static void deleteLckFile(String filename)
	{
		File lckFile = new File(FileUtil.getOutputDir().getAbsolutePath() + "//" +
				filename + ".lck");
		if (lckFile != null && lckFile.delete())
		{
			// do nothing
		}
		else
		{
			// lck file couldn't be deleted.
		}

	}

	public static String outputFile(String filename, String data) throws IOException
	{
		String fileName = FileUtil.getOutputDir().getAbsolutePath() + "/" + filename;
		FileWriter out = new FileWriter(fileName);
		out.write(data);
		out.flush();
		out.close();
		return fileName;
	}

	/**
	 * Create a filehandler to a log file that is located in the output directory.
	 *
	 * @param filename the log that you want to create
	 * @return the filehandler
	 * @throws IOException
	 */
	public static FileHandler getLogFileHandle(String filename) throws IOException
	{
		return new FileHandler(FileUtil.getOutputDir().getAbsolutePath() + "//" + filename);
	}

	/**
	 * Search for a file by searching the classpath
	 * (calling ClassLoader.getSystemResource()).
	 *
	 * @param fileName Name of the file you are looking for.
	 * @return the path to the file
	 * @throws FileNotFoundException
	 */
	public static String fileLocateOnClasspath(String fileName)
			throws FileNotFoundException
	{
		File f = new File(fileName);
		if (f.exists())
		{
			return f.getAbsolutePath();
		}

		URL u = null;
		u = ClassLoader.getSystemResource(fileName);
		if (u == null)
		{
			throw new FileNotFoundException(fileName + " - make sure the file is on the classpath.");
		}
		else
		{
			return u.getFile();
		}
	}

    public static File fileLocate(String directory, String fileName)throws FileNotFoundException{
        return new File(FileUtil.filenameLocate(directory,fileName));
    }


	/**
	 * Search for a file at the specified location and if it's not
	 * found there look on the classpath by calling filenameLocate(fileName).
	 *
	 * @param directory the directory to look first
	 * @param fileName  the name fo the file
	 * @return the path to the file
	 * @throws FileNotFoundException
	 */
	public static String filenameLocate(String directory, String fileName)
			throws FileNotFoundException
	{
		// check directory + filename
		File f = new File(directory + "/" + fileName);
		if (f.exists())
		{
			return f.getAbsolutePath();
		}
		// check just the filename
		f = new File(fileName);
		if (f.exists())
		{
			return f.getAbsolutePath();
		}

		String fileLocation = null;
		try
		{
			fileLocation = fileLocateOnClasspath(fileName);
		}
		catch (FileNotFoundException fnfe)
		{
			throw new FileNotFoundException(fileName + " - make sure " +
					"the location is correct OR the file is on the classpath");
		}
		return fileLocation;
	}

	public static String getLogFilename(String fullFilename)
	{
		String justFileName = new File(fullFilename).getName();
		return justFileName + ".log";
	}

	/**
	 * Return the extension part given file name.
	 * For example, if the name of the file is "foo.bar", ".bar" will be returned
	 * if includeDelimiter is true, or "bar" will be returned if includeDelimiter is false;
	 * otherwise, if no extension is specified in the file name, empty string is
	 * returned instead of null.
	 *
	 * @param file
	 * @param includeDelimiter
	 * @return the extension or an empty string if nothing is found
	 */
	public static final String getFileExtension(File file, boolean includeDelimiter)
	{
		String result = "";
		if (file != null)
		{
			String absoluteName = file.getAbsolutePath();
			if (absoluteName != null)
			{
				int delimIndex = absoluteName.lastIndexOf(".");
				if (delimIndex != -1)
				{//include the . delimiter
					if (!includeDelimiter)
					{//skip the . delimiter
						delimIndex++;
					}
					result = absoluteName.substring(delimIndex);
				}
			}
		}
		return result;
	}

	/**
	 * Construct a list of V3 Message file names and return.
	 * @param userSpecifiedFile
	 * @param numberOfMessages
	 * @param extension
	 * @param extensionIncludesDelimiter
	 * @return a list of V3 Message file names.
	 */
	public static final java.util.List<java.io.File> constructHL7V3MessageFileNames(File userSpecifiedFile, int numberOfMessages, String extension, boolean extensionIncludesDelimiter)
	{
		java.util.List<File> resultList = new ArrayList<File>();
		if(userSpecifiedFile==null)
		{
			Log.logWarning(FileUtil.class, "constructHL7V3MessageFileNames(): user specified file is null.");
			return resultList;
		}
		String extensionLocal = getFileExtension(userSpecifiedFile, extensionIncludesDelimiter);
		String absoluteFileName = userSpecifiedFile.getAbsolutePath();
		if(GeneralUtilities.areEqual(extensionLocal, extension))
		{//already contains the given extension, need to strip off so as to append
			absoluteFileName = getFileNameWithoutExtension(absoluteFileName);
		}
		for(int i=1; i<=numberOfMessages; i++)
		{
			String fileName = absoluteFileName + "_" + i;
			File file = new File(fileName);
			file = appendFileNameWithGivenExtension(file, extension, extensionIncludesDelimiter);
			resultList.add(file);
		}
		return resultList;
	}

	/**
	 * Return the absolute file name without the trailing file extension; return absoluteFileName itself if it does not contain any extension.
	 * @param absoluteFileName
	 * @return the absolute file name without the trailing file extension; return absoluteFileName itself if it does not contain any extension.
	 */
	private static final String getFileNameWithoutExtension(String absoluteFileName)
	{
		if(absoluteFileName==null)
		{
			return absoluteFileName;
		}
		int extIndex = absoluteFileName.lastIndexOf(".");
		if(extIndex!=-1)
		{
			absoluteFileName = absoluteFileName.substring(0, extIndex);
		}
		return absoluteFileName;
	}

	/**
	 * This function will return the file with the given extension. If it already contains, return immediately.
	 * @param file
	 * @param extension
	 * @param extensionIncludesDelimiter
	 * @return the File object contains the right file name with the given extension.
	 */
	public static final File appendFileNameWithGivenExtension(File file, String extension, boolean extensionIncludesDelimiter)
	{
		String extensionLocal = getFileExtension(file, extensionIncludesDelimiter);
		if(GeneralUtilities.areEqual(extensionLocal, extension))
		{//already contains the given extension, return
			return file;
		}
		else
		{
			String newFileName = file.getAbsolutePath();
			if(extensionIncludesDelimiter)
			{
				newFileName += extension;
			}
			else
			{
				newFileName += "." + extension;
			}
			File resultFile = new File(newFileName);
			return resultFile;
		}
	}
}

/**
 * $Log: not supported by cvs2svn $
 * Revision 1.37  2006/08/02 18:44:25  jiangsc
 * License Update
 *
 * Revision 1.36  2006/05/04 19:41:58  chene
 * Add 150003 test instance
 *
 * Revision 1.35  2006/01/03 19:16:53  jiangsc
 * License Update
 *
 * Revision 1.34  2006/01/03 18:56:26  jiangsc
 * License Update
 *
 * Revision 1.33  2005/12/30 22:23:30  chene
 * Update JavaDoc
 *
 * Revision 1.32  2005/12/29 23:06:16  jiangsc
 * Changed to latest project name.
 *
 * Revision 1.31  2005/12/29 15:39:06  chene
 * Optimize imports
 *
 * Revision 1.30  2005/12/14 21:29:40  giordanm
 * no message
 *
 * Revision 1.29  2005/11/02 22:36:06  chene
 * change "\\" to "/"
 *
 * Revision 1.28  2005/10/26 21:30:10  chene
 * Clean up e.printStackTrace()
 *
 * Revision 1.27  2005/10/26 17:33:13  giordanm
 * bug #129
 *
 * Revision 1.26  2005/10/25 20:20:25  chene
 * Support Schema Location
 *
 * Revision 1.25  2005/10/20 18:26:58  jiangsc
 * Updated to point to the UI default example directory.
 *
 * Revision 1.24  2005/10/19 21:49:21  chene
 * creat new directory workingspace, move example directory to there
 *
 * Revision 1.23  2005/08/22 17:32:45  giordanm
 * change the file attribute within BaseComponent from a String to a File,  this checkin also contains some refactor work to the FileUtil.
 *
 * Revision 1.22  2005/08/11 22:10:38  jiangsc
 * Open/Save File Dialog consolidation.
 *
 * Revision 1.21  2005/08/09 22:53:04  jiangsc
 * Save Point
 *
 * Revision 1.20  2005/08/08 18:05:50  giordanm
 * a bunch of checkins that changes hard coded paths to relative paths.
 *
 * Revision 1.19  2005/07/19 22:28:03  jiangsc
 * 1) Renamed FunctionalBox to FunctionBox to be consistent;
 * 2) Added SwingWorker to OpenMapAction;
 * 3) Save Point for Function Change.
 *
 * Revision 1.18  2005/06/24 20:58:08  jiangsc
 * Save Point
 *
 * Revision 1.17  2005/06/21 23:03:02  jiangsc
 * Put in new CSVPanel Implementation.
 *
 * Revision 1.16  2005/06/08 23:02:02  jiangsc
 * Implemented New UI.
 *
 * Revision 1.15  2005/06/02 22:12:02  chene
 * no message
 *
 * Revision 1.14  2005/05/17 20:07:16  chene
 * Updated CVS tag test
 *
 * Revision 1.13  2005/05/17 20:05:38  chene
 * Updated CVS tag test
 *
 * Revision 1.12  2005/05/17 17:33:07  giordanm
 * remove the <br> in the javadoc heading
 *
 * Revision 1.11  2005/05/17 17:15:45  giordanm
 * another minor change to the CVS javadoc comments.
 *
 * Revision 1.10  2005/05/17 17:01:20  giordanm
 * Playing around with CVS keywords / javadoc generation.
 *
 */
 
