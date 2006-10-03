/**
 * <!-- LICENSE_TEXT_START -->
 * $Header: /share/content/gforge/formbuilder/hl7messagegenerator/config/caadapter/src/gov/nih/nci/hl7/ui/csv/CSVPanel.java,v 1.1 2006-10-03 17:38:42 marwahah Exp $
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


package gov.nih.nci.hl7.ui.csv;

import gov.nih.nci.hl7.csv.CSVMetaParserImpl;
import gov.nih.nci.hl7.csv.CSVMetaResult;
import gov.nih.nci.hl7.csv.meta.CSVMeta;
import gov.nih.nci.hl7.csv.meta.CSVSegmentMeta;
import gov.nih.nci.hl7.csv.meta.impl.CSVMetaImpl;
import gov.nih.nci.hl7.csv.meta.impl.CSVSegmentMetaImpl;
import gov.nih.nci.hl7.ui.MainContextManager;
import gov.nih.nci.hl7.ui.MainFrame;
import gov.nih.nci.hl7.ui.MenuConstants;
import gov.nih.nci.hl7.ui.actions.ActionConstants;
import gov.nih.nci.hl7.ui.context.DefaultContextManagerClientPanel;
import gov.nih.nci.hl7.ui.dragdrop.HL7SDKDropCompatibleComponent;
import gov.nih.nci.hl7.ui.nodeloader.SCMTreeNodeLoader;
import gov.nih.nci.hl7.ui.tree.AutoscrollableTree;
import gov.nih.nci.hl7.ui.tree.TreeDefaultDragTransferHandler;
import gov.nih.nci.hl7.ui.tree.actions.TreeCollapseAllAction;
import gov.nih.nci.hl7.ui.tree.actions.TreeExpandAllAction;
import gov.nih.nci.hl7.ui.util.DefaultSettings;
import gov.nih.nci.hl7.ui.validation.ValidationMessagePane;
import gov.nih.nci.hl7.util.Config;
import gov.nih.nci.hl7.validation.ValidatorResults;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.awt.dnd.DnDConstants;
import java.io.File;
import java.io.FileReader;
import java.util.Map;

/**
 * This class is the main entry point of CSV specification panel.
 *
 * @author OWNER: Scott Jiang
 * @author LAST UPDATE $Author: marwahah $
 * @version Since caAdapter v1.2
 *          revision    $Revision: 1.1 $
 *          date        $Date: 2006-10-03 17:38:42 $
 */
public class CSVPanel extends DefaultContextManagerClientPanel //JPanel implements ContextManagerClient
{
    private JSplitPane centerSplitPane;
    private JSplitPane rightSplitPane;
    private JPanel treePanel;
    private JPanel treeNorthPanel;
    private JToolBar treeToolBar;
    private TreeExpandAllAction treeExpandAllAction;
    private TreeCollapseAllAction treeCollapseAllAction;
    private JScrollPane treeScrollPane;
    // the display.
    private AutoscrollableTree tree = null;
    private CSVMetadataTreeModel treeModel = null;
//	private File sourceFileForNew = null;

    private CSVMetadataTreeNodePropertiesPane propertiesPane = null;
    private boolean propertiesPaneVisible = false;
    private CSVTreeChangeAdapter navigationController;

    private SCMTreeNodeLoader nodeLoader = new SCMTreeNodeLoader();
    private CSVMeta csvMeta = null;

    private HL7SDKDropCompatibleComponent dropTransferHandler = null;

    private ValidationMessagePane validationMessagePane;
    private boolean messagePaneVisible;

    private JPanel placeHolderForValidationMessageDisplay;
    private JPanel placeHolderForPropertiesDisplay;

    private JLabel dummyHolderForPropertiesDisplay = new JLabel("For Properties Display...");
    private JLabel dummyHolderForValidationMessageDisplay = new JLabel("For Validation Message Display...");


    //	//key: menu_name, value: a map of action items (key: menuitem name, value, action);
//	private Map menuMap;

    public CSVPanel()
    {
        initialize();
    }

    public CSVMetadataTreeNodePropertiesPane getPropertiesPane()
    {
        if (propertiesPane == null)
        {
            propertiesPane = new CSVMetadataTreeNodePropertiesPane(this);
        }
        return this.propertiesPane;
    }

    public ValidationMessagePane getMessagePane()
    {
        if (validationMessagePane == null)
        {
            validationMessagePane = new ValidationMessagePane();
        }
        validationMessagePane.setMinimumSize(new Dimension((int) (Config.FRAME_DEFAULT_WIDTH / 3), (int) (Config.FRAME_DEFAULT_HEIGHT / 4)));
        return validationMessagePane;
    }

    private void initialize()
    {
        this.setLayout(new BorderLayout());
        treeScrollPane = new JScrollPane();
        treeScrollPane.setPreferredSize(new Dimension(Config.FRAME_DEFAULT_WIDTH / 3, Config.FRAME_DEFAULT_HEIGHT / 2));
        initializeTree(getCSVMeta(true));

        treePanel = new JPanel(new BorderLayout());
        treeNorthPanel = new JPanel(new BorderLayout());
        treeExpandAllAction = new TreeExpandAllAction(tree);
        treeCollapseAllAction = new TreeCollapseAllAction(tree);
        treeToolBar = new JToolBar("Tree Navigation ToolBar");
        treeToolBar.setFloatable(false);
        treeToolBar.add(treeExpandAllAction);
        treeToolBar.add(treeCollapseAllAction);
        treeNorthPanel.add(treeToolBar, BorderLayout.WEST);
        treePanel.add(treeNorthPanel, BorderLayout.NORTH);
        treePanel.add(treeScrollPane, BorderLayout.CENTER);

        rightSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        DefaultSettings.setDefaultFeatureForJSplitPane(rightSplitPane);
        rightSplitPane.setBorder(BorderFactory.createEmptyBorder());
        rightSplitPane.setDividerLocation(0.4);

        //for place holding
        placeHolderForValidationMessageDisplay = new JPanel(new BorderLayout());
        dummyHolderForValidationMessageDisplay.setEnabled(false);
        placeHolderForValidationMessageDisplay.add(dummyHolderForValidationMessageDisplay, BorderLayout.NORTH);
        placeHolderForValidationMessageDisplay.setPreferredSize(new Dimension((int) (Config.FRAME_DEFAULT_WIDTH / 3), (int) (Config.FRAME_DEFAULT_HEIGHT / 4)));
        rightSplitPane.setTopComponent(placeHolderForValidationMessageDisplay);

        placeHolderForPropertiesDisplay = new JPanel(new BorderLayout());
        placeHolderForPropertiesDisplay.add(dummyHolderForPropertiesDisplay, BorderLayout.NORTH);
        dummyHolderForPropertiesDisplay.setEnabled(false);
        placeHolderForPropertiesDisplay.setPreferredSize(new Dimension(Config.FRAME_DEFAULT_WIDTH / 3, Config.FRAME_DEFAULT_HEIGHT / 3));
        rightSplitPane.setBottomComponent(placeHolderForPropertiesDisplay);
        //end of temporary place takers.

        centerSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        DefaultSettings.setDefaultFeatureForJSplitPane(centerSplitPane);
        centerSplitPane.setBorder(BorderFactory.createEmptyBorder());
        centerSplitPane.setDividerLocation(0.4);

        centerSplitPane.setLeftComponent(treePanel);
        centerSplitPane.setRightComponent(rightSplitPane);
        this.add(centerSplitPane, BorderLayout.CENTER);
    }

    public CSVTreeChangeAdapter getController()
    {
        if (this.navigationController == null)
        {
            this.navigationController = new CSVTreeChangeAdapter(this);
        }
        return this.navigationController;
    }

    private ValidatorResults initializeTree(File file)
    {
        ValidatorResults validatorResults = new ValidatorResults();
        CSVMetaParserImpl parser = new CSVMetaParserImpl();
        try
        {
            CSVMetaResult csvMetaResult = parser.parse(new FileReader(file));
            csvMeta = csvMetaResult.getCsvMeta();
            validatorResults.addValidatorResults(csvMetaResult.getValidatorResults());
            if (validatorResults.hasFatal())
            {//return immediately
                return validatorResults;
            }
            initializeTree(csvMeta);
        }
        catch (Exception e1)
        {
            DefaultSettings.reportThrowableToLogAndUI(this, e1, null, this, false, true);
            return null;
        }
        return validatorResults;
    }
    private JTree initializeTree(CSVMeta csvMeta)
    {
        TreeNode nodes = null;
        try
        {
            nodes = nodeLoader.loadData(csvMeta);
        }
        catch (Throwable e)
        {
            DefaultSettings.reportThrowableToLogAndUI(this, e, "Error occurred during tree initialitation", this, true, true);
        }
        if (nodes!= null)
        {
            treeModel = new CSVMetadataTreeModel(nodes);
        }
        else
        {
            treeModel = new CSVMetadataTreeModel();
        }

        tree = new AutoscrollableTree(treeModel);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
        navigationController = new CSVTreeChangeAdapter(this);
		//register the mouse listener to clean up drag-and-drop flag
		tree.addMouseListener(navigationController);

		tree.getSelectionModel().addTreeSelectionListener(navigationController);
        tree.getModel().addTreeModelListener(navigationController);
        new TreeDefaultDragTransferHandler(tree, DnDConstants.ACTION_COPY_OR_MOVE);
        this.dropTransferHandler = new CSVTreeDropTransferHandler(tree, DnDConstants.ACTION_COPY_OR_MOVE);
        tree.addMouseListener(new CSVMetadataTreeMouseAdapter(this));
        treeScrollPane.getViewport().setView(tree);

        if(treeExpandAllAction!=null)
        {//will skip the first initialization of just the tree.
            treeExpandAllAction.setTree(tree);
            treeCollapseAllAction.setTree(tree);
            tree.getInputMap().put(treeCollapseAllAction.getAcceleratorKey(), treeCollapseAllAction.getName());
            tree.getActionMap().put(treeCollapseAllAction.getName(), treeCollapseAllAction);
            tree.getInputMap().put(treeExpandAllAction.getAcceleratorKey(), treeExpandAllAction.getName());
            tree.getActionMap().put(treeExpandAllAction.getName(), treeExpandAllAction);
        }

        return tree;
    }

    public JTree getTree()
    {
        return tree;
    }

    public HL7SDKDropCompatibleComponent getDropTransferHandler()
    {
        return this.dropTransferHandler;
    }

    public CSVMeta getCSVMeta(boolean createIfNull)
    {
        if(this.csvMeta==null && createIfNull)
        {
            CSVSegmentMeta rootUserObject = new CSVSegmentMetaImpl("ROOT", null);
            csvMeta = new CSVMetaImpl(rootUserObject);
        }
        return csvMeta;
    }

    public void setCsvMeta(CSVMeta csvMeta)
    {
        this.csvMeta = csvMeta;
        initializeTree(csvMeta);
    }

//	public void setSourceFileForNew(File aFile)
//	{
//		if (!GeneralUtilities.areEqual(sourceFileForNew, aFile))
//		{
//			this.sourceFileForNew = aFile;
//		}
//	}

//	public File getSourceFileForNew()
//	{
//		return this.sourceFileForNew;
//	}

    public ValidatorResults setSaveFile(File saveFile, boolean refreshTree)
    {
        ValidatorResults validatorResults = new ValidatorResults();
        if (super.setSaveFile(saveFile))//!GeneralUtilities.areEqual(this.saveFile, saveFile))
        {
//			this.saveFile = saveFile;
            if (refreshTree)
            {
                validatorResults.addValidatorResults(initializeTree(this.saveFile));
            }
        }
        return validatorResults;
    }

    public boolean isPropertiesPaneVisible()
    {
        return propertiesPaneVisible;
    }

    public void setPropertiesPaneVisible(boolean newValue)
    {
        if (propertiesPaneVisible != newValue)
        {
            propertiesPaneVisible = newValue;
            if (propertiesPaneVisible)
            {
//				CSVMetadataTreeNodePropertiesPane propPane = getPropertiesPane();
                rightSplitPane.setBottomComponent(getPropertiesPane());
            }
            else
            {//set null implies removement
                rightSplitPane.setBottomComponent(null);
            }
        }
    }

    public DefaultMutableTreeNode getDefaultTreeNode(Object userObject, boolean allowsChildren)
    {
        return nodeLoader.constructTreeNode(userObject, allowsChildren);
    }

    SCMTreeNodeLoader getNodeLoader()
    {
        return nodeLoader;
    }

    CSVTreeChangeAdapter getDefaultNavigationAdapter()
    {
        return navigationController;
    }

	/**
	 * Return whether the mapping module is in drag-and-drop mode.
	 *
	 * @return whether the mapping module is in drag-and-drop mode.
	 */
	public boolean isInDragDropMode()
	{
		if (this.dropTransferHandler != null)
		{
			return dropTransferHandler.isInDragDropMode();
		}
		else
		{
			return false;
		}
	}

	/**
	 * Set a new value for the mode.
	 *
	 * @param newValue
	 */
	public void setInDragDropMode(boolean newValue)
	{
		if (this.dropTransferHandler != null)
		{
			dropTransferHandler.setInDragDropMode(newValue);
		}
	}

    public boolean isMessagePaneVisible()
    {
        return messagePaneVisible;
    }

    public void setMessagePaneVisible(boolean newValue)
    {
        if (this.messagePaneVisible != newValue)
        {
            this.messagePaneVisible = newValue;
            if (this.messagePaneVisible)
            {
                rightSplitPane.setTopComponent(getMessagePane());
            }
            else
            {
                rightSplitPane.setTopComponent(null);
            }
        }
    }

    /**
     * Indicate whether or not it is changed.
     */
    public boolean isChanged()
    {
        return this.navigationController.isDataChanged();
    }

    /**
     * Explicitly set the value.
     *
     * @param newValue
     */
    public void setChanged(boolean newValue)
    {
        this.navigationController.setDataChanged(newValue);
    }

    /**
     * Return a list menu items under the given menu to be updated.
     *
     * @param menu_name
     * @return the map contains the action information.
     */
    public Map getMenuItems(String menu_name)
    {
//		if (menuMap == null)
//		{
//			menuMap = Collections.synchronizedMap(new HashMap());
//		}

        Map actionMap = super.getMenuItems(menu_name);//(Map) menuMap.get(menu_name);
        Action action;
        if (MenuConstants.FILE_MENU_NAME.equals(menu_name))
        {
//			if (actionMap == null)
//			{//lazy initialization
//				actionMap = new HashMap();
//				menuMap.put(menu_name, actionMap);
//			}//end of if(actionMap==null)
            JRootPane rootPane = this.getRootPane();
            if (rootPane != null)
            {//rootpane is not null implies this panel is fully displayed;
                //on the flip side, if it is null, it implies it is under certain construction.
                MainFrame mf = (MainFrame) rootPane.getParent();
                MainContextManager contextManager = mf.getMainContextManager();
                contextManager.enableAction(ActionConstants.NEW_CSV_SPEC, false);
                contextManager.enableAction(ActionConstants.OPEN_CSV_SPEC, true);
//				action = (Action) actionMap.get(ActionConstants.OPEN_CSV_SPEC);
//				if (action == null)
//				{//create once and only once.
//					action = new OpenMapAction(mf);
//					actionMap.put(ActionConstants.OPEN_CSV_SPEC, action);
//				}
//				action.setEnabled(true);
//
//				action = (Action) actionMap.get(ActionConstants.NEW_CSV_SPEC);
//				if (action == null)
//				{
//					action = new NewMapAction(mf);
//					actionMap.put(ActionConstants.NEW_CSV_SPEC, action);
//				}
//				action.setEnabled(false);
            }
            action = (Action) actionMap.get(ActionConstants.SAVE);
            if (action == null || !(action instanceof gov.nih.nci.hl7.ui.csv.actions.SaveAction))
            {
                action = new gov.nih.nci.hl7.ui.csv.actions.SaveAction(this);
                actionMap.put(ActionConstants.SAVE, action);
            }
            action.setEnabled(true);

            action = (Action) actionMap.get(ActionConstants.SAVE_AS);
            if (action == null || !(action instanceof gov.nih.nci.hl7.ui.csv.actions.SaveAsAction))
            {
                action = new gov.nih.nci.hl7.ui.csv.actions.SaveAsAction(this);
                actionMap.put(ActionConstants.SAVE_AS, action);
            }
            action.setEnabled(true);

            action = (Action) actionMap.get(ActionConstants.VALIDATE);
            if (action == null || !(action instanceof gov.nih.nci.hl7.ui.csv.actions.ValidateCSVAction))
            {
                action = new gov.nih.nci.hl7.ui.csv.actions.ValidateCSVAction(this);
                actionMap.put(ActionConstants.VALIDATE, action);
            }
            action.setEnabled(true);

            action = (Action) actionMap.get(ActionConstants.CLOSE);
            if (action == null || !(action instanceof gov.nih.nci.hl7.ui.csv.actions.CloseAction))
            {
                action = new gov.nih.nci.hl7.ui.csv.actions.CloseAction(this);
                actionMap.put(ActionConstants.CLOSE, action);
            }
            action.setEnabled(true);
        }//end of if(menu_name == MenuConstants.FILE_MENU_NAME)
        else if(MenuConstants.REPORT_MENU_NAME.equals(menu_name))
        {
            action = (Action) actionMap.get(ActionConstants.GENERATE_REPORT);
            if (action == null || !(action instanceof gov.nih.nci.hl7.ui.csv.actions.GenerateReportAction))
            {
                action = new gov.nih.nci.hl7.ui.csv.actions.GenerateReportAction(this);
                actionMap.put(ActionConstants.GENERATE_REPORT, action);
            }
            action.setEnabled(true);
        }
        return actionMap;
    }

    /**
     * return the open action inherited with this client.
     */
    public Action getDefaultOpenAction()
    {
        MainContextManager contextManager = getMainContextManager();
        Action openAction = null;
         if (contextManager!= null)
        {//contextManager is not null implies this panel is fully displayed;
            //on the flip side, if it is null, it implies it is under certain construction.
            openAction = contextManager.getDefinedAction(ActionConstants.OPEN_CSV_SPEC);
        }
        return openAction;
    }

    /**
     * Explicitly reload information from the internal given file.
     * @throws Exception
     */
    public void reload() throws Exception
    {
        setSaveFile(getSaveFile(), true);
    }

//    /**
//     * return the action list(open, save, close) inherited with this client.
//     */
//    public java.util.List<Action> getToolbarActionList()
//    {
//        java.util.List<Action> actions = new ArrayList<Action>();
//        actions.add(getDefaultOpenAction());
//        actions.add(getDefaultSaveAction());
//        actions.add(getDefaultCloseAction());
//        return actions;
//    }
}

/**
 * HISTORY      : $Log: not supported by cvs2svn $
 * HISTORY      : Revision 1.37  2006/08/02 18:44:21  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.36  2006/01/26 22:40:30  jiangsc
 * HISTORY      : Fix drap and drop issue on CSV panel
 * HISTORY      :
 * HISTORY      : Revision 1.35  2006/01/03 19:16:52  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.34  2006/01/03 18:56:24  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.33  2005/12/29 23:06:15  jiangsc
 * HISTORY      : Changed to latest project name.
 * HISTORY      :
 * HISTORY      : Revision 1.32  2005/12/14 21:37:18  jiangsc
 * HISTORY      : Updated license information
 * HISTORY      :
 * HISTORY      : Revision 1.31  2005/11/29 16:23:55  jiangsc
 * HISTORY      : Updated License
 * HISTORY      :
 * HISTORY      : Revision 1.30  2005/11/18 20:28:14  jiangsc
 * HISTORY      : Enhanced context-sensitive menu navigation and constructions.
 * HISTORY      :
 * HISTORY      : Revision 1.29  2005/11/16 21:02:15  umkis
 * HISTORY      : defect# 195, getToolbarActionList() is added for tool bar menu.
 * HISTORY      :
 * HISTORY      : Revision 1.28  2005/11/14 19:55:51  jiangsc
 * HISTORY      : Implementing UI enhancement
 * HISTORY      :
 * HISTORY      : Revision 1.27  2005/10/25 17:13:27  jiangsc
 * HISTORY      : Added Validation implemenation.
 * HISTORY      :
 * HISTORY      : Revision 1.26  2005/10/24 18:49:04  jiangsc
 * HISTORY      : minor update
 * HISTORY      :
 * HISTORY      : Revision 1.25  2005/10/21 18:26:17  jiangsc
 * HISTORY      : Validation Class name changes.
 * HISTORY      :
 * HISTORY      : Revision 1.24  2005/10/18 17:01:03  jiangsc
 * HISTORY      : Changed one function signature in DragDrop component;
 * HISTORY      : Enhanced drag-drop status monitoring in MappingPanel;
 * HISTORY      :
 * HISTORY      : Revision 1.23  2005/10/13 17:37:41  jiangsc
 * HISTORY      : Enhanced UI reporting on exceptions.
 * HISTORY      :
 * HISTORY      : Revision 1.22  2005/10/12 21:42:46  jiangsc
 * HISTORY      : Added validation on invalid file type.
 * HISTORY      :
 * HISTORY      : Revision 1.21  2005/10/07 20:09:03  jiangsc
 * HISTORY      : Enhanced the Look and Feel of Validation and Properties.
 * HISTORY      :
 * HISTORY      : Revision 1.20  2005/10/04 20:49:14  jiangsc
 * HISTORY      : UI Enhancement to fix data inconsistency between tree and properties panel.
 * HISTORY      :
 * HISTORY      : Revision 1.19  2005/09/29 21:14:59  jiangsc
 * HISTORY      : Added Generate Report action support
 * HISTORY      :
 * HISTORY      : Revision 1.18  2005/09/15 16:01:54  giordanm
 * HISTORY      : trying to get result objects working for CSVMetaParser... (result objects contain the information a service returns as well as a list of validation errors/warnings)
 * HISTORY      :
 * HISTORY      : Revision 1.17  2005/08/26 21:43:52  jiangsc
 * HISTORY      : Added tree actions
 * HISTORY      :
 * HISTORY      : Revision 1.16  2005/08/24 22:28:35  jiangsc
 * HISTORY      : Enhanced JGraph implementation;
 * HISTORY      : Save point of CSV and HSM navigation update;
 * HISTORY      :
 * HISTORY      : Revision 1.15  2005/08/23 19:57:00  jiangsc
 * HISTORY      : Name change
 * HISTORY      :
 * HISTORY      : Revision 1.14  2005/08/23 19:54:47  jiangsc
 * HISTORY      : Name change
 * HISTORY      :
 * HISTORY      : Revision 1.13  2005/08/23 19:26:15  jiangsc
 * HISTORY      : File name update
 * HISTORY      :
 * HISTORY      : Revision 1.12  2005/08/19 20:38:21  jiangsc
 * HISTORY      : To implement Add Segment/Field
 * HISTORY      :
 * HISTORY      : Revision 1.11  2005/08/19 18:54:37  jiangsc
 * HISTORY      : Added reshuffle functionality.
 * HISTORY      :
 * HISTORY      : Revision 1.10  2005/08/18 15:30:16  jiangsc
 * HISTORY      : First implementation on Switch control.
 * HISTORY      :
 * HISTORY      : Revision 1.9  2005/08/11 22:10:29  jiangsc
 * HISTORY      : Open/Save File Dialog consolidation.
 * HISTORY      :
 * HISTORY      : Revision 1.8  2005/08/05 20:35:47  jiangsc
 * HISTORY      : 0)Implemented field sequencing on CSVPanel but needs further rework;
 * HISTORY      : 1)Removed (Yes/No) for questions;
 * HISTORY      : 2)Removed double-checking after Save-As;
 * HISTORY      :
 * HISTORY      : Revision 1.7  2005/08/04 18:54:07  jiangsc
 * HISTORY      : Consolidated tabPane management into MainFrame
 * HISTORY      :
 * HISTORY      : Revision 1.6  2005/08/02 22:28:52  jiangsc
 * HISTORY      : Newly enhanced context-sensitive menus and toolbar.
 * HISTORY      :
 * HISTORY      : Revision 1.5  2005/07/27 22:41:19  jiangsc
 * HISTORY      : Consolidated context sensitive menu implementation.
 * HISTORY      :
 * HISTORY      : Revision 1.4  2005/07/25 21:56:50  jiangsc
 * HISTORY      : 1) Added expand all and collapse all;
 * HISTORY      : 2) Added toolbar on the mapping panel;
 * HISTORY      : 3) Consolidated menus;
 * HISTORY      :
 * HISTORY      : Revision 1.3  2005/07/22 20:53:12  jiangsc
 * HISTORY      : Structure change and added License and history anchors.
 * HISTORY      :
 * HISTORY      : Revision 1.2  2005/06/29 21:21:39  jiangsc
 * HISTORY      : More functionality.
 * HISTORY      :
 * HISTORY      : Revision 1.1  2005/06/24 20:38:26  jiangsc
 * HISTORY      : Save Point
 * HISTORY      :
 * HISTORY      : Revision 1.1  2005/06/24 19:53:09  jiangsc
 * HISTORY      : Save Point
 * HISTORY      :
 * HISTORY      : Revision 1.19  2005/06/23 14:30:05  jiangsc
 * HISTORY      : Updated CSVPanel implementation to support basic drag and drop.
 * HISTORY      :
 * HISTORY      : Revision 1.18  2005/06/21 23:03:03  jiangsc
 * HISTORY      : Put in new CSVPanel Implementation.
 * HISTORY      :
 */
