/**
 * <!-- LICENSE_TEXT_START -->
 * $Header: /share/content/gforge/formbuilder/hl7messagegenerator/config/caadapter/src/gov/nih/nci/hl7/ui/MainMenu.java,v 1.1 2006-10-03 17:38:42 marwahah Exp $
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


package gov.nih.nci.hl7.ui;

import gov.nih.nci.hl7.ui.actions.*;
import gov.nih.nci.hl7.util.Log;

import javax.swing.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * This class manages the definitions and instantiations of menu items.
 * It will coordinate MainContextManager class to deal with context sensitive menu switches.
 *
 * @author OWNER: Scott Jiang
 * @author LAST UPDATE $Author: marwahah $
 * @version Since caAdapter v1.2
 *          revision    $Revision: 1.1 $
 *          date        $Date: 2006-10-03 17:38:42 $
 */
public class MainMenu extends JMenuBar //implements ActionListener
{
	MainContextManager contextManager = null;
	MainFrame mainFrame = null;

	//--menu and menu item list.
	// definition of menu items, refer to constructXXXMenu() functions for construction.
    JMenu fileMenu = null;

    JMenu newMenu = null;
    JMenuItem newMapFileItem = null;
    JMenuItem newCSVSpecificationItem = null;
    JMenuItem newHSMFileItem = null;
    JMenuItem newHL7V3MessageItem = null;

    JMenu openMenu = null;
    JMenuItem openMapFileItem = null;
    JMenuItem openCSVSpecificationItem = null;
	JMenuItem openHSMFileItem = null;
    JMenuItem openHL7V3MessageItem = null;

	JMenuItem saveMenuItem = null;
    JMenuItem saveAsMenuItem = null;
	JMenuItem validateMenuItem = null;
	JMenuItem closeMenuItem = null;
	JMenuItem closeAllMenuItem = null;
	JMenuItem exitMenuItem = null;

	JMenu reportMenu = null;
	JMenuItem generateReportMenuItem = null;

	JMenu helpMenu = null;

    JMenuItem helpTopicMenuItem = null;
    //JMenuItem helpTopicMenuItem2 = null;
    //JMenuItem helpManageMenuItem = null;       // Kisung add
    JMenuItem aboutMenuItem = null;
	//--end of menu and menu item list.

	//--action list
	DefaultSaveAction defaultSaveAction = null;
	DefaultSaveAsAction defaultSaveAsAction = null;
	DefaultValidateAction defaultValidateAction = null;
	DefaultCloseAction defaultCloseAction = null;
	CloseAllAction closeAllAction = null;
	ExitAction exitAction = null;
	AboutAction aboutAction = null;
	HelpAction helpTopicAction = null;
    //HelpAction2 helpTopicAction2 = null;
    //HelpContentManageAction helpManageAction = null;	  // Kisung add

	NewMapAction newMapAction = null;
	OpenMapAction openMapAction = null;
	NewCSVSpecificationAction newCSVSpecificationAction = null;
	OpenCSVSpecificationAction openCSVSpecificationAction = null;
	NewHSMAction newHSMAction = null;
	OpenHSMAction openHSMAction = null;
	NewHL7V3MessageAction newHL7V3MessageAction = null;
	OpenHL7V3MessageAction openHL7V3MessageAction = null;
	//--end of action list.


	//key: ActionConstants, value: soft reference of the action.
    private Map actionMap;

    public MainMenu(MainContextManager contextManager)
	{
		this.contextManager = contextManager;
		this.mainFrame = contextManager.getMainFrame();
		initialize();
	}

	private void initialize()
	{
		actionMap = Collections.synchronizedMap(new HashMap());
		add(constructFileMenu());
		add(constructReportMenu());
		add(constructHelpMenu());
		constructActionMap();
	}

	/**
	 * Enable the given action with the given value.
	 * @param actionConstant the value defined in ActionConstants.
	 * @param value either true or false.
	 */
	public void enableAction(String actionConstant, boolean value)
	{
		Action action = getDefinedAction(actionConstant);//(Action)actionMap.get(actionConstant);
		if(action!=null)
		{
			action.setEnabled(value);
		}
		else
		{
			String msg = "Action could not be found for '" + actionConstant + "'.";
			System.err.println(msg);
			Log.logWarning(this.getClass(), msg);
		}
	}

	Action getDefinedAction(String actionConstant)
	{
		return (Action) actionMap.get(actionConstant);
	}

	private JMenu constructFileMenu()
	{
		defaultSaveAction = new DefaultSaveAction(mainFrame);
		saveMenuItem = new JMenuItem(defaultSaveAction);
		defaultSaveAsAction = new DefaultSaveAsAction(mainFrame);
		saveAsMenuItem = new JMenuItem(defaultSaveAsAction);

		defaultValidateAction = new DefaultValidateAction(mainFrame);
		validateMenuItem = new JMenuItem(defaultValidateAction);

		defaultCloseAction = new DefaultCloseAction(mainFrame);
		closeMenuItem = new JMenuItem(defaultCloseAction);

		closeAllAction = new CloseAllAction(mainFrame);
		closeAllMenuItem = new JMenuItem(closeAllAction);
		exitAction = new ExitAction(mainFrame);
		exitMenuItem = new JMenuItem(exitAction);

		//link them together
		fileMenu = new JMenu(MenuConstants.FILE_MENU_NAME);
		fileMenu.setMnemonic('F');
        fileMenu.add(constructNewMenu());
		fileMenu.addSeparator();
        fileMenu.add(constructOpenMenu());
		fileMenu.addSeparator();
		fileMenu.add(saveMenuItem);
		fileMenu.add(saveAsMenuItem);
		fileMenu.addSeparator();
		fileMenu.add(validateMenuItem);
		fileMenu.addSeparator();
        fileMenu.add(closeMenuItem);
        fileMenu.add(closeAllMenuItem);
		fileMenu.addSeparator();
		fileMenu.add(exitMenuItem);

        defaultSaveAction.setEnabled(false);
		defaultSaveAsAction.setEnabled(false);
		defaultValidateAction.setEnabled(false);
		defaultCloseAction.setEnabled(false);
		closeAllAction.setEnabled(false);

		return fileMenu;
    }

	private JMenu constructReportMenu()
	{
		reportMenu = new JMenu(MenuConstants.REPORT_MENU_NAME);
		reportMenu.setMnemonic('R');
		reportMenu.setEnabled(false);
		//first is just place holder.
		generateReportMenuItem = new JMenuItem((Action)null);
		reportMenu.add(generateReportMenuItem);

		return reportMenu;
	}

	private JMenu constructHelpMenu()
	{
		aboutAction = new AboutAction(mainFrame);
		aboutMenuItem = new JMenuItem(aboutAction);

        helpTopicAction = new HelpAction(mainFrame);
        helpTopicMenuItem = new JMenuItem(helpTopicAction);

        //helpTopicAction2 = new HelpAction2(mainFrame);
        //helpTopicMenuItem2 = new JMenuItem(helpTopicAction2);


        //helpManageAction = new HelpContentManageAction(mainFrame);    // Kisung add
        //helpManageMenuItem = new JMenuItem(helpManageAction);  // Kisung add


        helpMenu = new JMenu(MenuConstants.HELP_MENU_NAME);
		helpMenu.setMnemonic('H');

		helpMenu.add(helpTopicMenuItem); // eric addied
        helpMenu.add(aboutMenuItem);
        //helpMenu.add(helpTopicMenuItem2);
        //helpMenu.add(helpManageMenuItem);  // Kisung add
        return helpMenu;
	}

	private JMenu constructNewMenu()
	{
		//construct actions and menu items.
		newMapAction = new NewMapAction(mainFrame);
		newMapFileItem = new JMenuItem(newMapAction);
		newCSVSpecificationAction = new NewCSVSpecificationAction(mainFrame);
		newCSVSpecificationItem = new JMenuItem(newCSVSpecificationAction);
		newHSMAction = new NewHSMAction(mainFrame);
		newHSMFileItem = new JMenuItem(newHSMAction);
		newHL7V3MessageAction = new NewHL7V3MessageAction(mainFrame);
		newHL7V3MessageItem = new JMenuItem(newHL7V3MessageAction);

		// link them together.
		newMenu = new JMenu(MenuConstants.NEW_MENU_NAME);
		newMenu.setMnemonic('N');
		newMenu.add(newMapFileItem);
		newMenu.add(newCSVSpecificationItem);
		newMenu.add(newHSMFileItem);
		newMenu.add(newHL7V3MessageItem);
		return newMenu;
	}

	private JMenu constructOpenMenu()
	{
        //construct actions and menu items.
		openMapAction = new OpenMapAction(mainFrame);
		openMapFileItem = new JMenuItem(openMapAction);
		openCSVSpecificationAction = new OpenCSVSpecificationAction(mainFrame);
		openCSVSpecificationItem = new JMenuItem(openCSVSpecificationAction);
		openHSMAction = new OpenHSMAction(mainFrame);
		openHSMFileItem = new JMenuItem(openHSMAction);
		openHL7V3MessageAction = new OpenHL7V3MessageAction(mainFrame);
        openHL7V3MessageItem = new JMenuItem(openHL7V3MessageAction);

		//link them together
		openMenu = new JMenu(MenuConstants.OPEN_MENU_NAME);
		openMenu.setMnemonic('O');
		openMenu.add(openMapFileItem);
		openMenu.add(openCSVSpecificationItem);
		openMenu.add(openHSMFileItem);
//		openMenu.add(openHL7V3MessageItem);
		return openMenu;
	}
	public void resetMenus(boolean hasActiveDocument)
	{//provide structure for more menus to be reset
		resetFileMenu(hasActiveDocument);
		resetReportMenu(hasActiveDocument);
	}

	private void resetFileMenu(boolean hasActiveDocument)
	{
		resetNewSubMenu(hasActiveDocument);
		resetOpenSubMenu(hasActiveDocument);
		saveMenuItem.setAction(null);
		saveMenuItem.setAction(defaultSaveAction);
		actionMap.put(ActionConstants.SAVE, defaultSaveAction);

		saveAsMenuItem.setAction(null);
		saveAsMenuItem.setAction(defaultSaveAsAction);
        actionMap.put(ActionConstants.SAVE_AS, defaultSaveAsAction);

		validateMenuItem.setAction(null);
		validateMenuItem.setAction(defaultValidateAction);
        actionMap.put(ActionConstants.VALIDATE, defaultValidateAction);

		closeMenuItem.setAction(null);
		closeMenuItem.setAction(defaultCloseAction);
        actionMap.put(ActionConstants.CLOSE, defaultCloseAction);

		saveMenuItem.getAction().setEnabled(false);
		saveAsMenuItem.getAction().setEnabled(false);
		validateMenuItem.getAction().setEnabled(false);
		closeMenuItem.getAction().setEnabled(false);
		closeAllMenuItem.getAction().setEnabled(hasActiveDocument);
	}

	private void resetNewSubMenu(boolean hasActiveDocument)
	{
		if(!hasActiveDocument)
		{
			newMapFileItem.getAction().setEnabled(true);
			newCSVSpecificationItem.getAction().setEnabled(true);
			newHSMFileItem.getAction().setEnabled(true);
			newHL7V3MessageItem.getAction().setEnabled(true);
		}
	}

	private void resetOpenSubMenu(boolean hasActiveDocument)
	{
		if (!hasActiveDocument)
		{
			openMapFileItem.getAction().setEnabled(true);
			openCSVSpecificationItem.getAction().setEnabled(true);
			openHSMFileItem.getAction().setEnabled(true);
			openHL7V3MessageItem.getAction().setEnabled(true);
		}
	}

	private void resetReportMenu(boolean hasActiveDocument)
	{
		if (!hasActiveDocument)
		{
			Action a = generateReportMenuItem.getAction();
			if(a!=null)
			{
				a.setEnabled(false);
			}
		}
	}

	protected void enableCloseAllAction(boolean newValue)
	{
		if(closeAllAction!=null)
		{
			closeAllAction.setEnabled(newValue);
			closeAllMenuItem.setAction(null);
			closeAllMenuItem.setAction(closeAllAction);
//			closeAllMenuItem.invalidate();
		}
	}

	private void constructActionMap()
	{
		actionMap.put(ActionConstants.SAVE, defaultSaveAction);
		actionMap.put(ActionConstants.SAVE_AS, defaultSaveAsAction);
		actionMap.put(ActionConstants.VALIDATE, defaultValidateAction);
		actionMap.put(ActionConstants.CLOSE, defaultCloseAction);
		actionMap.put(ActionConstants.CLOSE_ALL, closeAllAction);
		actionMap.put(ActionConstants.EXIT, exitAction);
		actionMap.put(ActionConstants.ABOUT, aboutAction);

		actionMap.put(ActionConstants.NEW_MAP_FILE, newMapAction);
		actionMap.put(ActionConstants.OPEN_MAP_FILE, openMapAction);
		actionMap.put(ActionConstants.NEW_CSV_SPEC, newCSVSpecificationAction);
		actionMap.put(ActionConstants.OPEN_CSV_SPEC, openCSVSpecificationAction);
		actionMap.put(ActionConstants.NEW_HSM_FILE, newHSMAction);
		actionMap.put(ActionConstants.OPEN_HSM_FILE, openHSMAction);
		actionMap.put(ActionConstants.NEW_HL7_V3_MESSAGE, newHL7V3MessageAction);
		actionMap.put(ActionConstants.OPEN_HL7_V3_MESSAGE, openHL7V3MessageAction);

//		actionMap.put(ActionConstants.GENERATE_REPORT, generateReportMenuItem);
	}

}
/**
 * HISTORY      : $Log: not supported by cvs2svn $
 * HISTORY      : Revision 1.39  2006/08/02 18:44:25  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.38  2006/01/03 19:16:52  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.37  2006/01/03 18:56:24  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.36  2005/12/29 23:06:17  jiangsc
 * HISTORY      : Changed to latest project name.
 * HISTORY      :
 * HISTORY      : Revision 1.35  2005/12/29 15:39:06  chene
 * HISTORY      : Optimize imports
 * HISTORY      :
 * HISTORY      : Revision 1.34  2005/12/14 21:37:19  jiangsc
 * HISTORY      : Updated license information
 * HISTORY      :
 * HISTORY      : Revision 1.33  2005/11/29 16:23:54  jiangsc
 * HISTORY      : Updated License
 * HISTORY      :
 * HISTORY      : Revision 1.32  2005/11/17 21:04:08  umkis
 * HISTORY      : menu position exchange between help and about
 * HISTORY      :
 * HISTORY      : Revision 1.31  2005/10/26 17:21:09  umkis
 * HISTORY      : #156
 * HISTORY      : a)Help manager Menu is droped down
 * HISTORY      : b) Change the "About HL7SDK..." option to "About caAdapter..."
 * HISTORY      : c) Change the "Help..." option to "Contents and Index..."
 * HISTORY      :
 * HISTORY      : Revision 1.30  2005/10/20 15:37:04  umkis
 * HISTORY      : no message
 * HISTORY      :
 * HISTORY      : Revision 1.29  2005/10/04 20:51:32  jiangsc
 * HISTORY      : Validation enhancement.
 * HISTORY      :
 * HISTORY      : Revision 1.28  2005/09/29 21:19:52  jiangsc
 * HISTORY      : Added Generate Report action support
 * HISTORY      :
 * HISTORY      : Revision 1.27  2005/09/12 21:45:19  umkis
 * HISTORY      : no message
 * HISTORY      :
 * HISTORY      : Revision 1.26  2005/09/07 22:26:50  umkis
 * HISTORY      : no message
 * HISTORY      :
 * HISTORY      : Revision 1.25  2005/08/12 18:38:18  jiangsc
 * HISTORY      : Enable HL7 V3 Message to be saved in multiple XML file.
 * HISTORY      :
 * HISTORY      : Revision 1.24  2005/08/11 22:10:38  jiangsc
 * HISTORY      : Open/Save File Dialog consolidation.
 * HISTORY      :
 * HISTORY      : Revision 1.23  2005/08/02 22:28:57  jiangsc
 * HISTORY      : Newly enhanced context-sensitive menus and toolbar.
 * HISTORY      :
 * HISTORY      : Revision 1.22  2005/07/27 22:41:13  jiangsc
 * HISTORY      : Consolidated context sensitive menu implementation.
 * HISTORY      :
 * HISTORY      : Revision 1.21  2005/07/27 13:57:44  jiangsc
 * HISTORY      : Added the first round of HSMPanel.
 * HISTORY      :
 * HISTORY      : Revision 1.20  2005/07/25 22:12:28  jiangsc
 * HISTORY      : Fixed some menu transition.
 * HISTORY      :
 * HISTORY      : Revision 1.19  2005/07/25 21:56:47  jiangsc
 * HISTORY      : 1) Added expand all and collapse all;
 * HISTORY      : 2) Added toolbar on the mapping panel;
 * HISTORY      : 3) Consolidated menus;
 * HISTORY      :
 */
