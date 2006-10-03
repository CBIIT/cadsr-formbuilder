/**
 * <!-- LICENSE_TEXT_START -->
 * $Header: /share/content/gforge/formbuilder/hl7messagegenerator/config/caadapter/src/gov/nih/nci/hl7/ui/MainFrame.java,v 1.1 2006-10-03 17:38:42 marwahah Exp $
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

import gov.nih.nci.hl7.ui.actions.CloseAllAction;
import gov.nih.nci.hl7.ui.context.ContextManagerClient;
import gov.nih.nci.hl7.ui.csv.CSVPanel;
import gov.nih.nci.hl7.ui.help.InitialSplashThread;
import gov.nih.nci.hl7.ui.help.HelpContentViewer;
import gov.nih.nci.hl7.ui.hl7.HL7Panel;
import gov.nih.nci.hl7.ui.hsm.HSMPanel;
import gov.nih.nci.hl7.ui.map.MappingPanel;
import gov.nih.nci.hl7.ui.util.DefaultSettings;
import gov.nih.nci.hl7.util.Config;
import gov.nih.nci.hl7.util.FileUtil;
import gov.nih.nci.hl7.util.Log;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.HashMap;

/**
 * This class is the main entry of this sdk application.
 *
 * @author OWNER: Scott Jiang
 * @author LAST UPDATE $Author: marwahah $
 * @version Since caAdapter v1.2
 *          revision    $Revision: 1.1 $
 *          date        $Date: 2006-10-03 17:38:42 $
 */
public class MainFrame extends JFrame //implements WindowListener
{
    private MainContextManager contextManager;
    private JTabbedPane tabbedPane = new JTabbedPane();
    private Image icon = null;
    private Image bannerImage = null;
    private int tabcount = 0;
    private JPanel statusBar = new JPanel();
    private JPanel toolBarPanel;
    private JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.LEADING)); // inserted by Kisung for handling the default screen and the tabbedPane on 09/20/05.
    //private JToolBar currentToolBar;
    private JPanel currentToolBarPanel;

    private java.util.Map<Class, JComponent> tabMap;

    private JLabel baseScreenJLabel;       // inserted by Kisung 09/13/05   The default screen component object
    private HelpContentViewer helpContentViewer;

    public MainFrame() throws HeadlessException
    {
    }

    public void launch()
    {
        InitialSplashThread splashThread = null; // inserted by Kisung Um (09/12/05) : creation Thread Object For Splash Window
        Thread ist = new Thread(splashThread = new InitialSplashThread()); //inserted by Kisung Um (09/12/05) : installing Thread
        ist.start();  //inserted by Kisung Um (09/12/05) : Running Thread and calling Splash window (InitialSplashWindow)

        tabMap = new HashMap<Class, JComponent>();
        contextManager = new MainContextManager(this);
        this.setTitle(Config.PRODUCT_NAME + " Mapping Tool");
        Container contentPane = this.getContentPane();
        contentPane.setLayout(new BorderLayout());
        //set the icon.
        icon = DefaultSettings.getMainframeImage();//Toolkit.getDefaultToolkit().getImage("./images/icon.gif");
        setIconImage(icon);

        // set the menu bar.
        setJMenuBar(contextManager.getMenu());

        //set size before constructing each of those panels since some of them
        //may depend on the size to align components.
        this.setSize(Config.FRAME_DEFAULT_WIDTH, Config.FRAME_DEFAULT_HEIGHT);
//         setup the tabs.
//        addNewTab(new CSVPanel());
//        addNewTab(new MappingPanel());

//        final JPanel jPanel = new JPanel();
//        jPanel.setLayout(new GridLayout(1, 1));
//        jPanel.add(new JScrollPane(output));
//        output.setBackground(Color.WHITE);
        contentPane.add(constructNorthPanel(), BorderLayout.NORTH);
        contentPane.add(constructCenterPanel(), BorderLayout.CENTER);  // inserted by Kisung on 09/20/05

        //--------------------------------------
        //contentPane.add(tabbedPane, BorderLayout.CENTER);        // Deactivated by Kisung

        contentPane.add(statusBar, BorderLayout.SOUTH);
        tabbedPane.addChangeListener(contextManager);
//        tabbedPane.addPropertyChangeListener(contextManager);
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        enableEvents(AWTEvent.WINDOW_EVENT_MASK);

//		this.addWindowListener(this);
        this.setVisible(true);

         helpContentViewer = new HelpContentViewer(this);
        //while(true)
        //{
        //    if (helpContentViewer.getCompleteSig())
        //    {
        //        splashThread.setSignal("CLOSE");
        //        break;
        //    }
        //}
        //DefaultSettings.centerWindow(helpContentViewer);
        //helpContentViewer.setVisible(true);
        //isth.tagFileWrite("%%%Loading Complete!\r\ntrue\r\n");  //inserted by Kisung Um (09/12/05) : send to the Thread object a loading complete signal

        splashThread.setSignal(false); //inserted by Kisung Um (09/28/05) : send to the Thread object a loading complete signal

     }


    private JPanel constructNorthPanel()
    {
        bannerImage = Toolkit.getDefaultToolkit().getImage("./images/NCICBBanner.jpg");
        ImageIcon imageIcon = new ImageIcon(bannerImage);
        toolBarPanel = new JPanel(new BorderLayout());
        JPanel northUpperPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        JLabel label = new JLabel(imageIcon);
        northUpperPanel.add(label);
        toolBarPanel.add(northUpperPanel, BorderLayout.NORTH);
        currentToolBarPanel = getMainContextManager().getToolbarPanel();
        //updateToolBar(getMainContextManager().getToolbar());
        toolBarPanel.add(currentToolBarPanel, BorderLayout.SOUTH);

        return toolBarPanel;
    }

    //This method was inserted by Kisung 09/20/05 For initial displaying of the default screen object before the JTabbedPane object is activated.
    private JPanel constructCenterPanel()
    {
        ImageIcon ii1 = new ImageIcon(FileUtil.getWorkingDirPath() + File.separator + "images"
                      + File.separator + Config.DEFAULT_SCREEN_IMAGE_FILENAME );
        baseScreenJLabel = new JLabel(ii1);
        ii1.setImageObserver(baseScreenJLabel);

        centerPanel.add(baseScreenJLabel);
        centerPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        return centerPanel;
    }

    void updateToolBar(JToolBar newToolBar)
    {
        updateToolBar(newToolBar, null);
    }
    void updateToolBar(JToolBar newToolBar, JButton rightSideButton)
    {
        //remove first in case it already contains
        JToolBar rightSideToolbar = new JToolBar();
        JPanel rightSidePanel = new JPanel(new BorderLayout());
        if (rightSideButton != null)
        {
            rightSideToolbar.add(rightSideButton);
            rightSidePanel.add(rightSideToolbar, BorderLayout.CENTER);
        }
        toolBarPanel.remove(currentToolBarPanel);
        currentToolBarPanel.removeAll();
        currentToolBarPanel.add(newToolBar, BorderLayout.CENTER);
        currentToolBarPanel.add(rightSidePanel, BorderLayout.EAST);
        toolBarPanel.add(currentToolBarPanel, BorderLayout.SOUTH);
        //currentToolBar = newToolBar;
    }

    public void addNewTab(JPanel panel)
    {
        //Follwing 5 lines are inserted by Kisung 09/20/05 For remove default screen object before the JTabbedPane object is activated.
        if (tabbedPane.getTabCount() == 0)
          {
            centerPanel.removeAll();
            centerPanel.setLayout(new BorderLayout());
            centerPanel.add(tabbedPane, BorderLayout.CENTER);
          }
        //-----------------------------------------------------------------------------------------
        String title = null;
        if (isContainedInTabPane(panel))
        {//do nothing if tabbed pane already contains the given panel.
            return;
        }
        if (panel instanceof CSVPanel)
        {
            title = "Untitled_" + (++tabcount) + Config.CSV_METADATA_FILE_DEFAULT_EXTENTION;
        }
        else if (panel instanceof MappingPanel)
        {
            title = "Untitled_" + (++tabcount) + Config.MAP_FILE_DEFAULT_EXTENTION;
        }
        else if (panel instanceof HSMPanel)
        {
            title = "Untitled_" + (++tabcount) + Config.HSM_META_DEFINITION_FILE_DEFAULT_EXTENSION;
        }
        else if (panel instanceof HL7Panel)
        {
            title = "HL7 v3 Message";
        }
        tabbedPane.addTab(title, panel);
        tabbedPane.setSelectedComponent(panel);
//		Log.logInfo(this, "Panel Class: '" + (panel==null?"null":panel.getClass().getName()) + "'.");
        tabMap.put(panel.getClass(), panel);
    }

    private boolean isContainedInTabPane(JPanel j)
    {
        boolean result = false;
        result = tabMap.containsValue(j);
//		int count = tabbedPane.getComponentCount();
//		for(int i=0; i<count; i++)
//		{
//			Component comp = tabbedPane.getComponentAt(i);
//			if(comp == j)
//			{
//				result = true;
//				break;
//			}
//		}
        return result;
    }

    public void closeTab()
    {
        Component comp = tabbedPane.getSelectedComponent();
        tabbedPane.remove(comp);
        if (tabbedPane.getTabCount() == 0)
        {//reset if not tab at all.
            tabcount = 0;

            //Follwing 4 lines are inserted by Kisung 09/20/05 For remove empty JTabbedPane object and display default screen.
            centerPanel.removeAll();
            centerPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
            centerPanel.add(baseScreenJLabel);
            centerPanel.update(centerPanel.getGraphics());
            //-------------------------------------------------------------------------------------------------
        }
        tabMap.remove(comp.getClass());
        if(comp instanceof ContextManagerClient)
        {
            getMainContextManager().getContextFileManager().removeFileUsageListener((ContextManagerClient)comp);
        }
    }

    /**
     * Only accessible by the same package so as to
     * avoid abuse of using the tabbedPane directly.
     * @return the tab pane.
     */
    JTabbedPane getTabbedPane()
    {
        return tabbedPane;
    }

    public MainContextManager getMainContextManager()
    {
        return contextManager;
    }

    /**
     * Will return the component of the given classValue. If nothing is found, return null.
     *
     * @param classValue
     * @param bringToFront if exists, will bring it to front to be the selected.
     * @return the component of the given classValue. If nothing is found, return null.
     */
    public JComponent hasComponentOfGivenClass(Class classValue, boolean bringToFront)
    {
        JComponent component = tabMap.get(classValue);
        if (component != null)
        {
            if (bringToFront)
            {
                try
                {
                    tabbedPane.setSelectedComponent(component);
                }
                catch(Throwable e)
                {
                    Log.logInfo(this, "What kind of Component is this: '" + component.getClass().getName() + "'.");
                    Log.logException(this, e);
                }
            }
            return component;
        }
        else
        {
            return null;
        }
    }

    /**
     * Return all tabs in the frame, if nothing exists, will return an empty list.
     * @return all tabs in list.
     */
    public java.util.List<Component> getAllTabs()
    {
        java.util.List<Component> resultList = new java.util.ArrayList<Component>();
        int count = tabbedPane.getComponentCount();
        for(int i=0; i<count; i++)
        {
            Component comp = tabbedPane.getComponentAt(i);
            resultList.add(comp);
        }
        return resultList;
    }

    /**
     * Set the title value of currently selected inner panel.
     * @param newTitle
     * @return true if the title is successfully updated.
     */
    public boolean setCurrentPanelTitle(String newTitle)
    {
        int seleIndex = tabbedPane.getSelectedIndex();
        if (seleIndex != -1)
        {
            tabbedPane.setTitleAt(seleIndex, newTitle);
            return true;
        }
        else
        {
            return false;
        }
    }

    public static void main(String[] args)
    {
        try
        {
            DefaultSettings.installAll();
            MainFrame mainFrame = new MainFrame();
            mainFrame.launch();
            DefaultSettings.centerWindow(mainFrame);
        }
        catch (Throwable t)
        {
            Log.logException(new Object(), t);
        }
    }


    public void showHelpContentViewer()  // inserted by umkis on 01/16/2006, for reduce uploading time for help content.
    {
        DefaultSettings.centerWindow(helpContentViewer);
        helpContentViewer.setVisible(true);
    }
    public void showHelpContentWithNodeID(String id)  // inserted by umkis on 01/16/2006, for reduce uploading time for help content.
    {
        DefaultSettings.centerWindow(helpContentViewer);
        helpContentViewer.linkNodeID(id);
        helpContentViewer.setVisible(true);
    }

    public void showHelpContentWithNodeID(String id, Dialog dispose)  // inserted by umkis on 01/16/2006, for reduce uploading time for help content.
    {
        if (dispose != null) dispose.dispose();
        showHelpContentWithNodeID(id);
    }

    public void resetCenterPanel()    // inserted by umkis on 01/18/2006, defaect# 252
    {
        centerPanel.updateUI();
    }

//	/**
//	 * Invoked when the target of the listener has changed its state.
//	 * @param e a ChangeEvent object
//	 */
//	public void stateChanged(ChangeEvent e)
//	{//listen to tab change event
//	}
//
//	public void windowOpened(WindowEvent e)
//	{
//		//To change body of implemented methods use File | Settings | File Templates.
//	}
//
//	public void windowClosing(WindowEvent event)
//	{
//		exit();
//	}
//
//	public void windowClosed(WindowEvent e)
//	{
//		//To change body of implemented methods use File | Settings | File Templates.
//	}
//
//	public void windowIconified(WindowEvent e)
//	{
//		//To change body of implemented methods use File | Settings | File Templates.
//	}
//
//	public void windowDeiconified(WindowEvent e)
//	{
//		//To change body of implemented methods use File | Settings | File Templates.
//	}
//
//	public void windowActivated(WindowEvent e)
//	{
//		//To change body of implemented methods use File | Settings | File Templates.
//	}
//
//	public void windowDeactivated(WindowEvent e)
//	{
//		//To change body of implemented methods use File | Settings | File Templates.
//	}

    /**
     * Overridden so we can exit when window is closed
     */
    public void processWindowEvent(WindowEvent e)
    {
//		Log.logInfo(this, "processWindowEvent() invoked with '" + e + "'.");
        if (e.getID() == WindowEvent.WINDOW_CLOSING)
        {
            CloseAllAction closeAllAction = getMainContextManager().getMenu().closeAllAction;
            if(closeAllAction!=null && closeAllAction.isEnabled())
            {
                closeAllAction.actionPerformed(null);
                if(closeAllAction.isSuccessfullyPerformed())
                {
                    super.processWindowEvent(e);
                }
                else
                {//back to normal process.
                    return;
                }
            }
            else
            {
                super.processWindowEvent(e);
            }
            exit();
        }
        else
        {
            super.processWindowEvent(e);
        }
    }

    public void exit()
    {
        if (contextManager.isItOKtoShutdown())
        {
            this.exit(0);
        }

    }

    protected void exit(int errorLevel)
    {
        this.setVisible(false);
        this.dispose();
        Log.logInfo(this, "\r\n\r\nShutting down logging with exit code = " + errorLevel + "\r\n\r\n" +
                "===============================================================\r\n" +
                "===============================================================\r\n");
        System.exit(errorLevel);
    }

}
/**
 * HISTORY      : $Log: not supported by cvs2svn $
 * HISTORY      : Revision 1.64  2006/08/02 18:44:24  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.63  2006/06/13 18:12:12  jiangsc
 * HISTORY      : Upgraded to catch Throwable instead of Exception.
 * HISTORY      :
 * HISTORY      : Revision 1.62  2006/01/23 21:32:41  umkis
 * HISTORY      : no message
 * HISTORY      :
 * HISTORY      : Revision 1.61  2006/01/20 21:36:38  umkis
 * HISTORY      : add Extending caAdapter to include new HL7 message types (6.1.3)
 * HISTORY      :
 * HISTORY      : Revision 1.60  2006/01/19 00:45:46  umkis
 * HISTORY      : no message
 * HISTORY      :
 * HISTORY      : Revision 1.59  2006/01/18 19:49:52  umkis
 * HISTORY      : defaect# 252, resetCenterPanel function is inserted
 * HISTORY      :
 * HISTORY      : Revision 1.58  2006/01/17 19:37:48  umkis
 * HISTORY      : HelpContentViewer is Locate in mainFrame
 * HISTORY      :
 * HISTORY      : Revision 1.57  2006/01/03 19:16:52  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.56  2006/01/03 18:56:24  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.55  2005/12/29 23:06:17  jiangsc
 * HISTORY      : Changed to latest project name.
 * HISTORY      :
 * HISTORY      : Revision 1.54  2005/12/29 15:39:06  chene
 * HISTORY      : Optimize imports
 * HISTORY      :
 * HISTORY      : Revision 1.53  2005/12/14 21:37:19  jiangsc
 * HISTORY      : Updated license information
 * HISTORY      :
 * HISTORY      : Revision 1.52  2005/11/29 16:23:54  jiangsc
 * HISTORY      : Updated License
 * HISTORY      :
 * HISTORY      : Revision 1.51  2005/11/18 07:24:36  umkis
 * HISTORY      : replace close button to right side of toolbar panel
 * HISTORY      :
 * HISTORY      : Revision 1.50  2005/11/15 19:39:47  jiangsc
 * HISTORY      : Changed HL7 to caAdapter
 * HISTORY      :
 * HISTORY      : Revision 1.49  2005/11/04 19:54:26  chene
 * HISTORY      : change function library file extention from ffs to fls
 * HISTORY      :
 * HISTORY      : Revision 1.48  2005/11/02 00:12:52  umkis
 * HISTORY      : The default screen image filename is refered to Config class
 * HISTORY      :
 * HISTORY      : Revision 1.47  2005/10/26 18:12:29  jiangsc
 * HISTORY      : replaced printStackTrace() to Log.logException
 * HISTORY      :
 * HISTORY      : Revision 1.46  2005/10/25 22:00:43  jiangsc
 * HISTORY      : Re-arranged system output strings within UI packages.
 * HISTORY      :
 * HISTORY      : Revision 1.45  2005/10/17 22:39:02  umkis
 * HISTORY      : no message
 * HISTORY      :
 * HISTORY      : Revision 1.44  2005/09/28 20:55:08  umkis
 * HISTORY      : no message
 * HISTORY      :
 * HISTORY      : Revision 1.43  2005/09/21 01:10:11  umkis
 * HISTORY      : no message
 * HISTORY      :
 * HISTORY      : Revision 1.42  2005/09/15 16:01:39  umkis
 * HISTORY      : no message
 * HISTORY      :
 * HISTORY      : Revision 1.41  2005/09/14 19:56:27  umkis
 * HISTORY      : no message
 * HISTORY      :
 * HISTORY      : Revision 1.40  2005/09/14 15:21:46  umkis
 * HISTORY      : no message
 * HISTORY      :
 * HISTORY      : Revision 1.39  2005/09/13 22:24:00  umkis
 * HISTORY      : no message
 * HISTORY      :
 * HISTORY      : Revision 1.38  2005/09/13 14:37:15  umkis
 * HISTORY      : no message
 * HISTORY      :
 * HISTORY      : Revision 1.37  2005/09/13 00:52:35  umkis
 * HISTORY      : no message
 * HISTORY      :
 * HISTORY      : Revision 1.36  2005/09/12 22:00:22  umkis
 * HISTORY      : no message
 * HISTORY      :
 * HISTORY      : Revision 1.35  2005/09/12 16:15:56  umkis
 * HISTORY      : no message
 * HISTORY      :
 * HISTORY      : Revision 1.34  2005/09/09 13:18:37  umkis
 * HISTORY      : Help Content Viewer
 * HISTORY      :
 * HISTORY      : Revision 1.33  2005/08/24 22:25:08  jiangsc
 * HISTORY      : Enhanced Toolbar navigation and creation so as to work around an AWT ArrayOutofBoundException.
 * HISTORY      :
 * HISTORY      : Revision 1.32  2005/08/19 18:54:05  jiangsc
 * HISTORY      : Enhanced exit on ask saving
 * HISTORY      :
 * HISTORY      : Revision 1.31  2005/08/18 21:04:39  jiangsc
 * HISTORY      : Save point of the synchronization effort.
 * HISTORY      :
 * HISTORY      : Revision 1.30  2005/08/18 15:30:19  jiangsc
 * HISTORY      : First implementation on Switch control.
 * HISTORY      :
 * HISTORY      : Revision 1.29  2005/08/04 22:22:28  jiangsc
 * HISTORY      : Updated license and class header information.
 * HISTORY      :
 */
