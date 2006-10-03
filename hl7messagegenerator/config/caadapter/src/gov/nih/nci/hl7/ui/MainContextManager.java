/**
 * <!-- LICENSE_TEXT_START -->
 * $Header: /share/content/gforge/formbuilder/hl7messagegenerator/config/caadapter/src/gov/nih/nci/hl7/ui/MainContextManager.java,v 1.1 2006-10-03 17:38:42 marwahah Exp $
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

import gov.nih.nci.hl7.ui.actions.ActionConstants;
import gov.nih.nci.hl7.ui.context.ContextFileManager;
import gov.nih.nci.hl7.ui.context.ContextManagerClient;
import gov.nih.nci.hl7.ui.map.MappingPanel;
import gov.nih.nci.hl7.util.GeneralUtilities;
import gov.nih.nci.hl7.util.Log;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.Map;

/**
 * Manage the context change effect, including notify menus to update.
 *
 * @author OWNER: Scott Jiang
 * @author LAST UPDATE $Author: marwahah $
 * @version Since caAdapter v1.2
 *          revision    $Revision: 1.1 $
 *          date        $Date: 2006-10-03 17:38:42 $
 */
public class MainContextManager implements ChangeListener//, PropertyChangeListener
{
    /**
     * Logging constant used to identify source of log entry, that could be later used to create
     * logging mechanism to uniquely identify the logged class.
     */
    private static final String LOGID = "$RCSfile: MainContextManager.java,v $";

    /**
     * String that identifies the class version and solves the serial version UID problem.
     * This String is for informational purposes only and MUST not be made final.
     *
     * @see <a href="http://www.visi.com/~gyles19/cgi-bin/fom.cgi?file=63">JBuilder vice javac serial version UID</a>
     */
    public static String RCSID = "$Header: /share/content/gforge/formbuilder/hl7messagegenerator/config/caadapter/src/gov/nih/nci/hl7/ui/MainContextManager.java,v 1.1 2006-10-03 17:38:42 marwahah Exp $";

    private MainMenu menu = null;
    private MainToolBarHandler toolBarHandler = null;
    private MainFrame mainFrame = null;
    private JComponent currentPanel = null;
    private ContextFileManager contextFileManager = null;

    private boolean inClosingAllOrShutdownMode = false;

    public MainContextManager(MainFrame mainFrame)
    {
        this.mainFrame = mainFrame;
        getToolBarHandler().addAction(getMenu().helpTopicAction, true);
        contextFileManager = new ContextFileManager(this);
    }

    public MainMenu getMenu()
    {
        if (menu == null)
        {
            menu = new MainMenu(this);
        }
        return menu;
    }

    private MainToolBarHandler getToolBarHandler()
    {
        if (toolBarHandler == null)
        {
            toolBarHandler = new MainToolBarHandler(this);
        }
        return toolBarHandler;
    }

    public ContextFileManager getContextFileManager()
    {
        return contextFileManager;
    }

    public JToolBar getToolbar()
    {
        return getToolBarHandler().getToolbar(false);
    }

    public JPanel getToolbarPanel()
    {
        JPanel mainP = new JPanel(new BorderLayout());
        mainP.add(getToolbar(), BorderLayout.CENTER);
        mainP.add(new JPanel(), BorderLayout.EAST);
        return mainP;
    }

    public MainFrame getMainFrame()
    {
        return mainFrame;
    }

    /**
     * Return the currently active panel.
     *
     * @return the current panel.
     */
    public JComponent getCurrentPanel()
    {
        return currentPanel;
    }

    /**
     * Invoked when the target of the listener has changed its state.
     *
     * @param e a ChangeEvent object
     */
    public void stateChanged(ChangeEvent e)
    {
//		Log.logInfo(this, "State Changed...");
        if (isInClosingAllOrShutdownMode())
        {//ignore change if is about to closing all or shutting down.
            return;
        }
        int selectedIndex = mainFrame.getTabbedPane().getSelectedIndex();
        JComponent nowPanel = null;
        if (selectedIndex != -1)
        {
            nowPanel = (JComponent) mainFrame.getTabbedPane().getComponentAt(selectedIndex);
        }

        if (!GeneralUtilities.areEqual(nowPanel, currentPanel))
        {
            if(currentPanel instanceof ContextManagerClient)
            {
//				boolean toTransit = contextFileManager.transitAwayFrom((ContextManagerClient) currentPanel);
//				if(!toTransit)
//				{//stay where we are.
//					mainFrame.getTabbedPane().setSelectedComponent(currentPanel);
//					return;
//				}

//				int userChoice = JOptionPane.showConfirmDialog(mainFrame, "Switch Away?", "Question", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
//				if(userChoice!=JOptionPane.YES_OPTION)
//				{
//					mainFrame.getTabbedPane().setSelectedComponent(currentPanel);
//					return;
//				}
            }
            currentPanel = nowPanel;
            updateMenu();
        }

		/**
		 * Temporary solution
		 */
		if(nowPanel instanceof MappingPanel)
		{
			((MappingPanel)nowPanel).getMappingFileSynchronizer().doSynchronizationCheck(true);
		}
	}

    public void notifySaveActionPerformedOnContextClient(ContextManagerClient client)
    {
        contextFileManager.notifyAffectContextMangerClients(client);
    }

    /**
     * We take pro-active approach to update the menu after user change tab view but
     * before the actual menu is selected.
     */
    private synchronized void updateMenu()
    {
        try
        {
            if(currentPanel instanceof ContextManagerClient)
            {
                ContextManagerClient contextClient = (ContextManagerClient) currentPanel;
                getToolBarHandler().removeAllActions();
                java.util.List<Action> actions = contextClient.getToolbarActionList();
                for(int i=0;i<actions.size();i++)
				{//includes open, save, close, validate, etc.
					Action action = actions.get(i);
					if(action!=null)
					{
						getToolBarHandler().addAction(action, true);
					}
				}

                JButton closeButton = null;
                Action action = contextClient.getDefaultCloseAction();
                if(action!=null)
                {
                    closeButton = new JButton(action);
                    closeButton.setText("");
                }

                Map actionMap = contextClient.getMenuItems(MenuConstants.FILE_MENU_NAME);
                //update the toolbar
                getToolBarHandler().addAction(getMenu().helpTopicAction, true);
                mainFrame.updateToolBar(getToolbar(), closeButton);
                //				Log.logInfo(this, "update file menu...");

                //update the menus
                menu.resetMenus(true);
                updateMenu(actionMap, MenuConstants.FILE_MENU_NAME);
                actionMap = contextClient.getMenuItems(MenuConstants.REPORT_MENU_NAME);
                updateMenu(actionMap, MenuConstants.REPORT_MENU_NAME);
            }
            else
            {//we could possibly be here b/c user closed the last tab, roll back to menu's original state.
                getToolBarHandler().removeAllActions();
                getToolBarHandler().addAction(getMenu().helpTopicAction, true);
                mainFrame.updateToolBar(getToolbar());
                menu.resetMenus(false);
            }
            menu.repaint();
//			getToolBarHandler().repaintToolBar();
    //		menu.invalidate();
        }
        catch(Throwable t)
        {
            Log.logException(this, "Exception occurred in updateMenu()", t);
//			System.err.println("Exception occurred in updateMenu()");
//			t.printStackTrace();
        }
    }

    private void updateMenu(Map actionMap, String menu_name)
    {
        if (menu_name == MenuConstants.FILE_MENU_NAME)
        {
            updateMenuAction(actionMap, ActionConstants.NEW_MAP_FILE, menu.newMapFileItem);
            updateMenuAction(actionMap, ActionConstants.OPEN_MAP_FILE, menu.openMapFileItem);
            updateMenuAction(actionMap, ActionConstants.NEW_CSV_SPEC, menu.newCSVSpecificationItem);
            updateMenuAction(actionMap, ActionConstants.OPEN_CSV_SPEC, menu.openCSVSpecificationItem);
            updateMenuAction(actionMap, ActionConstants.NEW_HSM_FILE, menu.newHSMFileItem);
            updateMenuAction(actionMap, ActionConstants.OPEN_HSM_FILE, menu.openHSMFileItem);
            updateMenuAction(actionMap, ActionConstants.NEW_HL7_V3_MESSAGE, menu.newHL7V3MessageItem);
            updateMenuAction(actionMap, ActionConstants.OPEN_HL7_V3_MESSAGE, menu.openMapFileItem);

            updateMenuAction(actionMap, ActionConstants.SAVE, menu.saveMenuItem);
            updateMenuAction(actionMap, ActionConstants.SAVE_AS, menu.saveAsMenuItem);
            updateMenuAction(actionMap, ActionConstants.VALIDATE, menu.validateMenuItem);
            boolean updated = updateMenuAction(actionMap, ActionConstants.CLOSE, menu.closeMenuItem);
            if (updated)
            {
                menu.enableCloseAllAction(true);
            }
        }//end of if(menu_name==FILE_MENU_NAME)
        else if(menu_name == MenuConstants.REPORT_MENU_NAME)
        {
            if(actionMap==null || actionMap.isEmpty())
            {//remove menu and its items completely
                menu.reportMenu.setEnabled(false);
            }
            else
            {
                menu.reportMenu.setEnabled(true);
                updateMenuAction(actionMap, ActionConstants.GENERATE_REPORT, menu.generateReportMenuItem);
            }
        }

//		menu.invalidate();
//		menu.repaint();
    }

    private boolean updateMenuAction(Map actionMap, String actionConstant, JMenuItem menuItem)
    {
        Action act = (Action) actionMap.get(actionConstant);
        if (act != null)
        {
            menuItem.setAction(null);
            menuItem.setAction(act);
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean isItOKtoShutdown()
    {//todo: need real implementation
        return true;
    }

    /**
     * Answers whether the whole system is under closing all or shutting down period.
     *
     * @return true if in closing all or shut-down mode.
     */
    public boolean isInClosingAllOrShutdownMode()
    {
        return inClosingAllOrShutdownMode;
    }

    public void setInClosingAllOrShutdownMode(boolean inClosingAllOrShutdownMode, boolean runningSuccessful)
    {
        boolean oldValue = this.inClosingAllOrShutdownMode;
        this.inClosingAllOrShutdownMode = inClosingAllOrShutdownMode;
        if (oldValue && runningSuccessful)
        {//means it is previously under closingAll etc mode, thus, need to reset
            currentPanel = null;
            updateMenu();
        }
    }

    /**
     * Enable the given action with the given value.
     *
     * @param actionConstant the value defined in ActionConstants.
     * @param value          either true or false.
     */
    public void enableAction(String actionConstant, boolean value)
    {
        menu.enableAction(actionConstant, value);
    }

    /**
     * Find a defined action by the given action constant value.
     * Will return null if nothing is found.
     * @param actionConstant
     * @return the defined action.
     */
    public Action getDefinedAction(String actionConstant)
    {
        return menu.getDefinedAction(actionConstant);
    }
}

/**
 * HISTORY      : $Log: not supported by cvs2svn $
 * HISTORY      : Revision 1.41  2006/08/02 18:44:24  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.40  2006/01/03 19:16:52  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.39  2006/01/03 18:56:24  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.38  2005/12/29 23:06:17  jiangsc
 * HISTORY      : Changed to latest project name.
 * HISTORY      :
 * HISTORY      : Revision 1.37  2005/12/22 19:06:32  jiangsc
 * HISTORY      : Feature enhancement.
 * HISTORY      :
 * HISTORY      : Revision 1.36  2005/12/14 21:37:19  jiangsc
 * HISTORY      : Updated license information
 * HISTORY      :
 * HISTORY      : Revision 1.35  2005/12/02 23:02:57  jiangsc
 * HISTORY      : Save point
 * HISTORY      :
 * HISTORY      : Revision 1.34  2005/11/29 16:23:54  jiangsc
 * HISTORY      : Updated License
 * HISTORY      :
 * HISTORY      : Revision 1.33  2005/11/18 20:28:14  jiangsc
 * HISTORY      : Enhanced context-sensitive menu navigation and constructions.
 * HISTORY      :
 * HISTORY      : Revision 1.32  2005/11/18 07:24:46  umkis
 * HISTORY      : replace close button to right side of toolbar panel
 * HISTORY      :
 * HISTORY      : Revision 1.31  2005/11/18 05:59:29  umkis
 * HISTORY      : Change default tool bar menu from about to help
 * HISTORY      :
 * HISTORY      : Revision 1.30  2005/11/17 21:04:53  umkis
 * HISTORY      : Change default tool bar menu from about to help
 * HISTORY      :
 * HISTORY      : Revision 1.29  2005/11/16 21:07:33  umkis
 * HISTORY      : defect# 195, basic three actions(open, save, close) are received from getToolbarActionList() of context manager panel.
 * HISTORY      :
 * HISTORY      : Revision 1.28  2005/11/16 21:05:55  umkis
 * HISTORY      : defect# 195, basic three actions(open, save, close) are received from getToolbarActionList() of context manager panel.
 * HISTORY      :
 * HISTORY      : Revision 1.27  2005/10/26 18:12:29  jiangsc
 * HISTORY      : replaced printStackTrace() to Log.logException
 * HISTORY      :
 * HISTORY      : Revision 1.26  2005/10/25 22:00:43  jiangsc
 * HISTORY      : Re-arranged system output strings within UI packages.
 * HISTORY      :
 * HISTORY      : Revision 1.25  2005/10/04 20:51:32  jiangsc
 * HISTORY      : Validation enhancement.
 * HISTORY      :
 * HISTORY      : Revision 1.24  2005/09/29 21:19:37  jiangsc
 * HISTORY      : Added Generate Report action support
 * HISTORY      :
 * HISTORY      : Revision 1.23  2005/08/30 21:14:19  jiangsc
 * HISTORY      : minor update
 * HISTORY      :
 * HISTORY      : Revision 1.22  2005/08/30 20:48:18  jiangsc
 * HISTORY      : minor update
 * HISTORY      :
 * HISTORY      : Revision 1.21  2005/08/24 22:25:08  jiangsc
 * HISTORY      : Enhanced Toolbar navigation and creation so as to work around an AWT ArrayOutofBoundException.
 * HISTORY      :
 * HISTORY      : Revision 1.20  2005/08/19 18:54:04  jiangsc
 * HISTORY      : Enhanced exit on ask saving
 * HISTORY      :
 * HISTORY      : Revision 1.19  2005/08/18 21:04:39  jiangsc
 * HISTORY      : Save point of the synchronization effort.
 * HISTORY      :
 * HISTORY      : Revision 1.18  2005/08/18 15:30:18  jiangsc
 * HISTORY      : First implementation on Switch control.
 * HISTORY      :
 * HISTORY      : Revision 1.17  2005/08/12 18:38:18  jiangsc
 * HISTORY      : Enable HL7 V3 Message to be saved in multiple XML file.
 * HISTORY      :
 * HISTORY      : Revision 1.16  2005/08/04 18:54:05  jiangsc
 * HISTORY      : Consolidated tabPane management into MainFrame
 * HISTORY      :
 * HISTORY      : Revision 1.15  2005/08/03 16:56:18  jiangsc
 * HISTORY      : Further consolidation of context sensitive management.
 * HISTORY      :
 * HISTORY      : Revision 1.14  2005/08/02 22:28:57  jiangsc
 * HISTORY      : Newly enhanced context-sensitive menus and toolbar.
 * HISTORY      :
 * HISTORY      : Revision 1.13  2005/07/27 22:41:13  jiangsc
 * HISTORY      : Consolidated context sensitive menu implementation.
 * HISTORY      :
 * HISTORY      : Revision 1.12  2005/07/27 13:57:45  jiangsc
 * HISTORY      : Added the first round of HSMPanel.
 * HISTORY      :
 * HISTORY      : Revision 1.11  2005/07/25 22:12:28  jiangsc
 * HISTORY      : Fixed some menu transition.
 * HISTORY      :
 * HISTORY      : Revision 1.10  2005/07/25 21:56:47  jiangsc
 * HISTORY      : 1) Added expand all and collapse all;
 * HISTORY      : 2) Added toolbar on the mapping panel;
 * HISTORY      : 3) Consolidated menus;
 * HISTORY      :
 * HISTORY      : Revision 1.9  2005/07/22 17:39:26  jiangsc
 * HISTORY      : Persistence of Function involved mapping.
 * HISTORY      :
 * HISTORY      : Revision 1.8  2005/07/15 18:58:49  jiangsc
 * HISTORY      : 1) Reconstucted Menu bars;
 * HISTORY      : 2) Integrated FunctionPane to display property;
 * HISTORY      : 3) Enabled drag and drop functions to mapping panel.
 * HISTORY      :
 */
