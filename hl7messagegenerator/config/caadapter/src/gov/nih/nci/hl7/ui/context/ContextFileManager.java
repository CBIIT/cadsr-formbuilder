/**
 * <!-- LICENSE_TEXT_START -->
 * $Header: /share/content/gforge/formbuilder/hl7messagegenerator/config/caadapter/src/gov/nih/nci/hl7/ui/context/ContextFileManager.java,v 1.1 2006-10-03 17:38:42 marwahah Exp $
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


package gov.nih.nci.hl7.ui.context;

import gov.nih.nci.hl7.ui.MainContextManager;
import gov.nih.nci.hl7.ui.util.DefaultSettings;
import gov.nih.nci.hl7.util.GeneralUtilities;

import javax.swing.*;
import java.io.File;
import java.util.*;

/**
 * This class defines a list of functions to facilitate the management and synchronization
 * of files that may be simultaneously accessed across different context client, such as
 * an SCM file being used at SCM and Map panel.
 *
 * @author OWNER: Scott Jiang
 * @author LAST UPDATE $Author: marwahah $
 * @version Since caAdapter v1.2
 *          revision    $Revision: 1.1 $
 *          date        $Date: 2006-10-03 17:38:42 $
 */
public class ContextFileManager
{
	/**
	 * Logging constant used to identify source of log entry, that could be later used to create
	 * logging mechanism to uniquely identify the logged class.
	 */
	private static final String LOGID = "$RCSfile: ContextFileManager.java,v $";

	/**
	 * String that identifies the class version and solves the serial version UID problem.
	 * This String is for informational purposes only and MUST not be made final.
	 *
	 * @see <a href="http://www.visi.com/~gyles19/cgi-bin/fom.cgi?file=63">JBuilder vice javac serial version UID</a>
	 */
	public static String RCSID = "$Header: /share/content/gforge/formbuilder/hl7messagegenerator/config/caadapter/src/gov/nih/nci/hl7/ui/context/ContextFileManager.java,v 1.1 2006-10-03 17:38:42 marwahah Exp $";

	private Map <File, java.util.Set<ContextManagerClient>> fileUsageMap = null;

	private MainContextManager contextManager;

	public ContextFileManager(MainContextManager contextManager)
	{
		this.contextManager = contextManager;
		initilizeFileUsageMap();
	}

	private void initilizeFileUsageMap()
	{
		if(fileUsageMap!=null)
		{
			fileUsageMap.clear();
		}
		else
		{
			fileUsageMap = new HashMap<File, java.util.Set<ContextManagerClient>>();
		}
	}

	/**
	 * Register a given ContextManagerClient to associate with the given file.
	 * @param file
	 * @param usageClient
	 */
	public void registerFileUsageListener(File file, ContextManagerClient usageClient)
	{
		if(file==null || usageClient==null)
		{
			return;
		}
//		Log.logInfo(this, "To Add: File:'"+file.getName()+"',context='"+DefaultSettings.getClassNameWithoutPackage(usageClient.getClass())+"'.");
		Set<ContextManagerClient> usageList = fileUsageMap.get(file);
		if(usageList==null)
		{//nothing found
			usageList = new LinkedHashSet<ContextManagerClient>();
			fileUsageMap.put(file, usageList);
		}
		usageList.add(usageClient);
	}

	/**
	 * Register a given ContextManagerClient to associate with the given file.
	 * @param usageClient
	 */
	public void registerFileUsageListener(ContextManagerClient usageClient)
	{
		if(usageClient==null)
		{
			return;
		}
		List fileList = usageClient.getAssociatedFileList();
		int size = (fileList==null)? 0 : fileList.size();
		for(int i=0; i<size; i++)
		{
			File file = (File) fileList.get(i);
			registerFileUsageListener(file, usageClient);
		}
	}

	/**
	 * Remove the given ContextManagerClient from the association of the given file.
	 * @param file
	 * @param usageClient
	 */
	public void removeFileUsageListener(File file, ContextManagerClient usageClient)
	{
		if(file==null || usageClient==null)
		{
			return;
		}
//		Log.logInfo(this, "To Remove: File:'"+file.getName()+"',context='"+DefaultSettings.getClassNameWithoutPackage(usageClient.getClass())+"'.");
		Set<ContextManagerClient> usageList = fileUsageMap.get(file);
		if(usageList!=null)
		{//find the list
			usageList.remove(usageClient);
		}
	}

	/**
	 * Remove the given ContextManagerClient from the association of the files.
	 * @param usageClient
	 */
	public void removeFileUsageListener(ContextManagerClient usageClient)
	{
		if(usageClient==null)
		{
			return;
		}
		List fileList = usageClient.getAssociatedFileList();
		int size = (fileList==null)? 0 : fileList.size();
		for(int i=0; i<size; i++)
		{
			File file = (File) fileList.get(i);
			removeFileUsageListener(file, usageClient);
		}
	}

	public void clearFileCache()
	{
		initilizeFileUsageMap();
	}

	/**
	 * Execute the transition examination logic and determine the panel flow.
	 * @param contextManagerClient
	 * @return true if it is good to transition from the given contextManagerClient to another;
	 * false, intends to stay on current client, i.e. veto the transition;
	 */
	public boolean transitAwayFrom(ContextManagerClient contextManagerClient)
	{
		if(contextManagerClient==null)
		{
			return true;
		}

		boolean result = true;
		if(contextManagerClient.isChanged())
		{
			int userChoice = JOptionPane.showConfirmDialog(contextManager.getMainFrame(),
					"Content has been changed on this panel. Would you like to save it before switching away?", "Question", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
			if(userChoice!=JOptionPane.OK_OPTION)
			{
				return false;
			}
			Action action = contextManagerClient.getDefaultSaveAction();
			if(action!=null && action.isEnabled())
			{
				action.actionPerformed(null);
			}
			//notify will not be called, but by explict calling or SaveAsAction.
//			notifyAffectContextMangerClients(contextManagerClient);
			result = true;
		}

		return result;
	}

	/**
	 * To notify those affected context clients, if any, which have interest in the files that are associated with the given contextManagerClient.
	 * @param contextManagerClient
	 */
	public void notifyAffectContextMangerClients(ContextManagerClient contextManagerClient)
	{
		/**
		 * Design Rationale:
		 * 1) Given the contextManagerClient, for each associated file in the list, find the associated context client in the fileUsageMap;
		 * 2) If the item in the list from the map is not the same as the contextManagerClient, add it to the notification Map and its name in the list for user opinion;
		 * 3) Display the list of affected context;
		 * 4) If user would like to reload all of them, do so, otherwise, just simply ignore;
		 */
		if(contextManagerClient==null)
		{
			return;
		}
		//key: the name of the contextClient, value: the contextClient itself.
		Map<String,ContextManagerClient> associatedClientMap = new HashMap<String,ContextManagerClient>();
		List fileList = contextManagerClient.getAssociatedFileList();
		int size = fileList==null? 0: fileList.size();
		for(int i=0; i<size; i++)
		{
			File file = (File) fileList.get(i);
			Set<ContextManagerClient> contexClientList = this.fileUsageMap.get(file);
			Iterator it = contexClientList.iterator();
			while(it.hasNext())
			{
				ContextManagerClient localClient = (ContextManagerClient) it.next();
				if(!GeneralUtilities.areEqual(localClient, contextManagerClient))
				{
					String className = DefaultSettings.getClassNameWithoutPackage(localClient.getClass());
					associatedClientMap.put(className, localClient);
				}
			}
		}

		if(associatedClientMap.isEmpty())
		{//if no association, just return.
			return;
		}
		ContextManagerClientReloadDialog dialog = new ContextManagerClientReloadDialog(contextManager.getMainFrame(), associatedClientMap);
		DefaultSettings.centerWindow(dialog);
		dialog.setModal(true);
		dialog.setVisible(true);

		if(dialog.isReloadAllClicked())
		{
//			Log.logInfo(this, "Reload clicked. Let's do something");
		}
	}
}

/**
 * HISTORY      : $Log: not supported by cvs2svn $
 * HISTORY      : Revision 1.14  2006/08/02 18:44:21  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.13  2006/01/03 19:16:52  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.12  2006/01/03 18:56:24  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.11  2005/12/29 23:06:12  jiangsc
 * HISTORY      : Changed to latest project name.
 * HISTORY      :
 * HISTORY      : Revision 1.10  2005/12/29 15:39:05  chene
 * HISTORY      : Optimize imports
 * HISTORY      :
 * HISTORY      : Revision 1.9  2005/12/14 21:37:16  jiangsc
 * HISTORY      : Updated license information
 * HISTORY      :
 * HISTORY      : Revision 1.8  2005/11/29 16:23:56  jiangsc
 * HISTORY      : Updated License
 * HISTORY      :
 * HISTORY      : Revision 1.7  2005/10/25 22:00:43  jiangsc
 * HISTORY      : Re-arranged system output strings within UI packages.
 * HISTORY      :
 * HISTORY      : Revision 1.6  2005/10/17 23:03:24  umkis
 * HISTORY      : no message
 * HISTORY      :
 * HISTORY      : Revision 1.5  2005/10/03 17:49:34  jiangsc
 * HISTORY      : minor change
 * HISTORY      :
 * HISTORY      : Revision 1.4  2005/08/30 20:48:13  jiangsc
 * HISTORY      : minor update
 * HISTORY      :
 * HISTORY      : Revision 1.3  2005/08/18 21:58:45  jiangsc
 * HISTORY      : minor update
 * HISTORY      :
 * HISTORY      : Revision 1.2  2005/08/18 21:04:38  jiangsc
 * HISTORY      : Save point of the synchronization effort.
 * HISTORY      :
 * HISTORY      : Revision 1.1  2005/08/18 15:30:15  jiangsc
 * HISTORY      : First implementation on Switch control.
 * HISTORY      :
 */