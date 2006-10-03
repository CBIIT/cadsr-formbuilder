/**
 * <!-- LICENSE_TEXT_START -->
 * $Header: /share/content/gforge/formbuilder/hl7messagegenerator/config/caadapter/src/gov/nih/nci/hl7/ui/map/MappingPanel.java,v 1.1 2006-10-03 17:38:43 marwahah Exp $
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


package gov.nih.nci.hl7.ui.map;

import gov.nih.nci.hl7.clone.meta.HL7V3MetaFileParser;
import gov.nih.nci.hl7.clone.meta.HL7V3MetaResult;
import gov.nih.nci.hl7.common.ApplicationException;
import gov.nih.nci.hl7.common.BaseResult;
import gov.nih.nci.hl7.common.MetaObject;
import gov.nih.nci.hl7.common.MetaParser;
import gov.nih.nci.hl7.csv.CSVMetaParserImpl;
import gov.nih.nci.hl7.csv.CSVMetaResult;
import gov.nih.nci.hl7.database.DatabaseMetaParserImpl;
import gov.nih.nci.hl7.database.DatabaseMetaResult;
import gov.nih.nci.hl7.map.MapParserImpl;
import gov.nih.nci.hl7.map.Mapping;
import gov.nih.nci.hl7.map.MappingResult;
import gov.nih.nci.hl7.map.components.BaseComponent;
import gov.nih.nci.hl7.ui.MainContextManager;
import gov.nih.nci.hl7.ui.MainFrame;
import gov.nih.nci.hl7.ui.MenuConstants;
import gov.nih.nci.hl7.ui.actions.ActionConstants;
import gov.nih.nci.hl7.ui.context.DefaultContextManagerClientPanel;
import gov.nih.nci.hl7.ui.jgraph.FunctionLibraryPane;
import gov.nih.nci.hl7.ui.map.actions.RefreshAction;
import gov.nih.nci.hl7.ui.nodeloader.DBMMapSourceNodeLoader;
import gov.nih.nci.hl7.ui.nodeloader.DBMMapTargetNodeLoader;
import gov.nih.nci.hl7.ui.nodeloader.HSMMapTargetNodeLoader;
import gov.nih.nci.hl7.ui.nodeloader.SCMMapSourceNodeLoader;
import gov.nih.nci.hl7.ui.properties.DefaultPropertiesPage;
import gov.nih.nci.hl7.ui.tree.TreeDefaultDragTransferHandler;
import gov.nih.nci.hl7.ui.tree.actions.TreeCollapseAllAction;
import gov.nih.nci.hl7.ui.tree.actions.TreeExpandAllAction;
import gov.nih.nci.hl7.ui.util.DefaultSettings;
import gov.nih.nci.hl7.util.Config;
import gov.nih.nci.hl7.util.FileUtil;
import gov.nih.nci.hl7.util.GeneralUtilities;
import gov.nih.nci.hl7.util.MessageResources;
import gov.nih.nci.hl7.validation.Message;
import gov.nih.nci.hl7.validation.ValidatorResult;
import gov.nih.nci.hl7.validation.ValidatorResults;

import javax.swing.*;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreeNode;
import java.awt.*;
import java.awt.dnd.DnDConstants;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.util.Iterator;
import java.util.Map;

/**
 * The class is the main panel to construct the UI and initialize the utilities to
 * facilitate mapping functions.
 *
 * @author OWNER: Scott Jiang
 * @author LAST UPDATE $Author: marwahah $
 * @version Since caAdapter v1.2
 *          revision    $Revision: 1.1 $
 *          date        $Date: 2006-10-03 17:38:43 $
 */
public class MappingPanel extends DefaultContextManagerClientPanel implements ActionListener//JPanel implements ActionListener, ContextManagerClient
{
	private static final String LOGID = "$RCSfile: MappingPanel.java,v $";
	public static String RCSID = "$Header: /share/content/gforge/formbuilder/hl7messagegenerator/config/caadapter/src/gov/nih/nci/hl7/ui/map/MappingPanel.java,v 1.1 2006-10-03 17:38:43 marwahah Exp $";

	private static final String SELECT_SOURCE = "Open Source...";
	private static final String SELECT_CSV_TIP = "Select a " + Config.CSV_MODULE_NAME;//CSV Specification";
	private static final String SELECT_TARGET = "Open Target...";
	private static final String SELECT_HMD_TIP = "Select an " + Config.HL7_V3_METADATA_MODULE_NAME;//HL7 v3 Specification";

	private File mappingSourceFile = null;
	private File mappingTargetFile = null;

//	private JLabel bottomCenterPlaceHolder;
	private FunctionLibraryPane functionPane;
	private DefaultPropertiesPage propertiesPane;

//	private ArrayList<TreeNodeMapping> mappings =
//			new ArrayList<TreeNodeMapping>();

	private JScrollPane sourceScrollPane = null;
	private JPanel sourceButtonPanel = null;
	private JPanel sourceLocationPanel = null;

	private JScrollPane targetScrollPane = null;
	private JPanel targetButtonPanel = null;
	private JPanel targetLocationPanel = null;
	private MiddlePanel middlePanel = null;
	private SourceTree sTree = null;
	private TargetTree tTree = null;
	private TreeDefaultDragTransferHandler sourceTreeDragTransferHandler = null;

	private TargetTreeDragTransferHandler targetTreeDragTransferHandler = null;
	private TargetTreeDropTransferHandler targetTreeDropTransferHandler = null;

//    private JPanel secondPanel = null;

	private JTextField sourceLocationArea = new JTextField();
	private JTextField targetLocationArea = new JTextField();
	private JButton openSourceButton = new JButton(SELECT_SOURCE);
	private TreeCollapseAllAction sourceTreeCollapseAllAction;
	private TreeExpandAllAction sourceTreeExpandAllAction;
	private JButton openTargetButton = new JButton(SELECT_TARGET);
	private TreeCollapseAllAction targetTreeCollapseAllAction;
	private TreeExpandAllAction targetTreeExpandAllAction;

	private MappingFileSynchronizer fileSynchronizer;

//	private JToolBar toolBar;
//	private OpenMapFileAction openMapAction;

//	//key: menu_name, value: a map of action items (key: menuitem name, value, action);
//	private Map menuMap;

	//for test purpose
	private static final String TO_TEST_LOAD_COMMAND = "Load for Test";
	private JButton testLoadButton = new JButton(TO_TEST_LOAD_COMMAND);

	public MappingPanel()
	{
		this.setBorder(BorderFactory.createEmptyBorder());
//		this.removeAll();
		this.setLayout(new BorderLayout());
//		this.add(getNorthPanel(), BorderLayout.NORTH);
		this.add(getCenterPanel(), BorderLayout.CENTER);
		fileSynchronizer = new MappingFileSynchronizer(this);
	}

//	/**
//	 * Return a toolbar so far.
//	 *
//	 * @return
//	 */
//	private JComponent getNorthPanel()
//	{
//		toolBar = new JToolBar("Mapping Panel Tool Bar");
//		Map actionMap = getMenuItems(MenuConstants.FILE_MENU_NAME);
//		Action saveAction = (Action) actionMap.get(ActionConstants.SAVE);
//		openMapAction = new OpenMapFileAction(this);
//        //add those actions
//		toolBar.add(openMapAction);
//		toolBar.add(saveAction);
//		return toolBar;
//	}

	 public void setSize(Dimension newDimension)
	{
		setSize((int) newDimension.getWidth(), (int) newDimension.getHeight());
	}

	public void setSize(int width, int height)
	{
//        System.out.println("MappingPanel's setSize() is called.");
		this.resize(width, height);
	}

	public void resize(int width, int height)
	{
//        System.out.println("MappingPanel's resize(int width, int height) is called.");
		double topCenterFactor = 1;
		sourceLocationArea.setSize(new Dimension((int) (width / 6), 25));
		sourceScrollPane.setSize(new Dimension((int) (width / 4.5), (int) (height * topCenterFactor)));
		targetLocationArea.setSize(new Dimension(width / 6, 25));
		targetScrollPane.setSize(new Dimension((int) (width / 4.5), (int) (height * topCenterFactor)));
		middlePanel.setSize(new Dimension((int) (width / 4), (int) (height * topCenterFactor)));

		topCenterFactor = 0.5;
		Dimension rightMostDim = new Dimension((int) (width / 5), (int) (height * topCenterFactor));
		propertiesPane.setSize(rightMostDim);
		functionPane.setSize(rightMostDim);

//		double bottomCenterFactor = 1 / 10;
//		bottomCenterPlaceHolder.setSize(width/8, (int) (height * bottomCenterFactor));
	}

	public Dimension getSize()
	{
//        System.out.println("MappingPanel's getSize() is called.");
		return super.getSize();
	}


	private JComponent getCenterPanel()
	{//construct the top level layout of mapping panel
		/**
		 * GUI Layout:
		 * JSplitPane - Horizontal:
		 *      left: JSplitPane - Horizontal:
		 *				left: source panel;
		 *				right: JSplitPane - Horizontal:
		 *							left: middle panel for graph;
		 *							right: target panel;
		 * 		right: JSplitPane - Vertical:
		 * 				top: functional pane;
		 *				bottom: properties panel;
		 */

		JSplitPane leftRightSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		DefaultSettings.setDefaultFeatureForJSplitPane(leftRightSplitPane);
		leftRightSplitPane.setDividerLocation(0.85);
		leftRightSplitPane.setLeftComponent(getTopLevelLeftPanel());
		leftRightSplitPane.setRightComponent(getTopLevelRightPanel());
		return leftRightSplitPane;
	}

	/**
	 * This constructs function and properties panels.
	 *
	 * @return the top level right pane.
	 */
	private JComponent getTopLevelRightPanel()
	{
		JSplitPane topBottomSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		DefaultSettings.setDefaultFeatureForJSplitPane(topBottomSplitPane);
		topBottomSplitPane.setDividerLocation(0.5);

		//always call getTopLevelLeftPanel() before getTopLevelRightPanel()
		//so that the PropertiesSwitchController in MiddlePanel's MappingDataManager will be instantiated before being used by the propertiesPane initiation.

		functionPane = new FunctionLibraryPane();
		functionPane.setBorder(BorderFactory.createTitledBorder("Functions"));
		topBottomSplitPane.setTopComponent(functionPane);
		propertiesPane = new DefaultPropertiesPage(this.getMappingDataManager().getPropertiesSwitchController());
		topBottomSplitPane.setBottomComponent(propertiesPane);

		double topCenterFactor = 0.3;
		Dimension rightMostDim = new Dimension((int) (Config.FRAME_DEFAULT_WIDTH / 11), (int) (Config.FRAME_DEFAULT_HEIGHT * topCenterFactor));
		propertiesPane.setPreferredSize(rightMostDim);
//		propertiesPane.setSize(rightMostDim);
		functionPane.setPreferredSize(rightMostDim);
//		functionPane.setSize(rightMostDim);
		//add the property switch listener
		functionPane.getFunctionTree().getSelectionModel().addTreeSelectionListener((TreeSelectionListener) (getMappingDataManager().getPropertiesSwitchController()));

		topCenterFactor = 1.5;
		rightMostDim = new Dimension((int) (Config.FRAME_DEFAULT_WIDTH / 10), (int) (Config.FRAME_DEFAULT_HEIGHT / topCenterFactor));
//		topBottomSplitPane.setPreferredSize(rightMostDim);
		topBottomSplitPane.setSize(rightMostDim);

		return topBottomSplitPane;
	}

	private JPanel getTopLevelLeftPanel()
	{
		JPanel topCenterPanel = new JPanel(new BorderLayout());
		topCenterPanel.setBorder(BorderFactory.createEmptyBorder());
		JSplitPane centerSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		DefaultSettings.setDefaultFeatureForJSplitPane(centerSplitPane);
//		centerSplitPane.setDividerLocation(0.3);
//		centerSplitPane.setDividerSize(Config.DEFAULT_DIVIDER_SIZE);
//        centerSplitPane.setBorder(BorderFactory.createEmptyBorder());

		//construct source panel
		sourceButtonPanel = new JPanel(new BorderLayout());
		sourceButtonPanel.setBorder(BorderFactory.createEmptyBorder());
		sourceLocationPanel = new JPanel(new BorderLayout(2, 0));
		sourceLocationPanel.setBorder(BorderFactory.createEmptyBorder());
//		JPanel sourceTreeActionPanel = new JPanel(new BorderLayout());//new FlowLayout(FlowLayout.LEADING));
		sourceTreeCollapseAllAction = new TreeCollapseAllAction(sTree);
//		JButton sourceTreeCollapseAllButton = new JButton(sourceTreeCollapseAllAction);
		sourceTreeExpandAllAction = new TreeExpandAllAction(sTree);
//		JButton sourceTreeExpandAllButton = new JButton(sourceTreeExpandAllAction);
//		sourceTreeExpandAllButton.setText("");
//		sourceTreeActionPanel.add(sourceTreeCollapseAllButton, BorderLayout.EAST);
//		sourceTreeActionPanel.add(sourceTreeExpandAllButton, BorderLayout.WEST);
		JToolBar sourceTreeToolBar = new JToolBar("Source Tree Tool Bar");
		sourceTreeToolBar.setFloatable(false);
		sourceTreeToolBar.add(sourceTreeExpandAllAction);
		sourceTreeToolBar.add(sourceTreeCollapseAllAction);
		sourceLocationPanel.add(sourceTreeToolBar, BorderLayout.WEST);
//		sourceLocationPanel.add(sourceTreeActionPanel, BorderLayout.WEST);
		sourceLocationArea.setEditable(false);
		sourceLocationArea.setPreferredSize(new Dimension((int) (Config.FRAME_DEFAULT_WIDTH / 10), 24));
		sourceLocationPanel.add(sourceLocationArea, BorderLayout.CENTER);
		sourceLocationPanel.add(openSourceButton, BorderLayout.EAST);
		openSourceButton.setMnemonic('S');
		openSourceButton.setToolTipText(SELECT_CSV_TIP);
		openSourceButton.addActionListener(this);
		sourceButtonPanel.add(sourceLocationPanel, BorderLayout.NORTH);
		sourceScrollPane = DefaultSettings.createScrollPaneWithDefaultFeatures();
//		sourceScrollPane.setPreferredSize(new Dimension((int)(Config.FRAME_DEFAULT_WIDTH / 4.5), (int)(Config.FRAME_DEFAULT_HEIGHT/1.5)));
		sourceScrollPane.setSize(new Dimension((int) (Config.FRAME_DEFAULT_WIDTH / 4), (int) (Config.FRAME_DEFAULT_HEIGHT / 1.5)));
		sourceButtonPanel.add(sourceScrollPane, BorderLayout.CENTER);
//		sourceButtonPanel.setPreferredSize(new Dimension((int) (Config.FRAME_DEFAULT_WIDTH / 4), (int) (Config.FRAME_DEFAULT_HEIGHT / 1.5)));
//		sourceButtonPanel.setSize(new Dimension((int) (Config.FRAME_DEFAULT_WIDTH / 4), (int) (Config.FRAME_DEFAULT_HEIGHT / 1.5)));

		//construct target panel
		targetButtonPanel = new JPanel(new BorderLayout());
		targetButtonPanel.setBorder(BorderFactory.createEmptyBorder());
		targetLocationPanel = new JPanel(new BorderLayout(2, 0));
		targetLocationPanel.setBorder(BorderFactory.createEmptyBorder());
		targetTreeCollapseAllAction = new TreeCollapseAllAction(tTree);
		targetTreeExpandAllAction = new TreeExpandAllAction(tTree);
		JToolBar targetTreeToolBar = new JToolBar("Target Tree Tool Bar");
		targetTreeToolBar.setFloatable(false);
		targetTreeToolBar.add(targetTreeExpandAllAction);
		targetTreeToolBar.add(targetTreeCollapseAllAction);
		targetLocationPanel.add(targetTreeToolBar, BorderLayout.WEST);
		targetLocationArea.setEditable(false);
		targetLocationArea.setPreferredSize(new Dimension((int) (Config.FRAME_DEFAULT_WIDTH / 10), 24));
		targetLocationPanel.add(targetLocationArea, BorderLayout.CENTER);
		targetLocationPanel.add(openTargetButton, BorderLayout.EAST);
		openTargetButton.setMnemonic('T');
		openTargetButton.setToolTipText(SELECT_HMD_TIP);
		openTargetButton.addActionListener(this);
		targetButtonPanel.add(targetLocationPanel, BorderLayout.NORTH);
		targetScrollPane = DefaultSettings.createScrollPaneWithDefaultFeatures();
//		targetScrollPane.setPreferredSize(new Dimension((int)(Config.FRAME_DEFAULT_WIDTH / 6), Config.FRAME_DEFAULT_HEIGHT / 2));
//		targetScrollPane.setSize(new Dimension((int)(Config.FRAME_DEFAULT_WIDTH / 6), Config.FRAME_DEFAULT_HEIGHT / 2));
		targetButtonPanel.add(targetScrollPane, BorderLayout.CENTER);
		targetButtonPanel.setPreferredSize(new Dimension((int) (Config.FRAME_DEFAULT_WIDTH / 5), (int) (Config.FRAME_DEFAULT_HEIGHT / 1.5)));
//		targetButtonPanel.setSize(new Dimension((int) (Config.FRAME_DEFAULT_WIDTH / 5), (int)(Config.FRAME_DEFAULT_HEIGHT / 1.5)));

		//construct middle panel
		JPanel middleContainerPanel = new JPanel(new BorderLayout());
		//to hold the place equates the source and target button panel so as to align the drawing the graphs.
		JLabel placeHolderLabel = new JLabel();
		placeHolderLabel.setPreferredSize(new Dimension((int) (Config.FRAME_DEFAULT_WIDTH / 3.5), 24));
		middlePanel = new MiddlePanel(this);
//		middlePanel.setPreferredSize(new Dimension((int) (Config.FRAME_DEFAULT_WIDTH / 3.5), (int)(Config.FRAME_DEFAULT_HEIGHT / 1.5)));
		middlePanel.setSize(new Dimension((int) (Config.FRAME_DEFAULT_WIDTH / 3), (int) (Config.FRAME_DEFAULT_HEIGHT / 1.5)));
		middleContainerPanel.add(placeHolderLabel, BorderLayout.NORTH);
		middleContainerPanel.add(middlePanel, BorderLayout.CENTER);
//		middleContainerPanel.setPreferredSize(new Dimension((int) (Config.FRAME_DEFAULT_WIDTH / 3), (int) (Config.FRAME_DEFAULT_HEIGHT / 1.5)));
//		middleContainerPanel.setSize(new Dimension((int) (Config.FRAME_DEFAULT_WIDTH / 3), (int) (Config.FRAME_DEFAULT_HEIGHT / 1.5)));

		JSplitPane rightSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		DefaultSettings.setDefaultFeatureForJSplitPane(rightSplitPane);
//        rightSplitPane.setBorder(BorderFactory.createEmptyBorder());
		rightSplitPane.setDividerLocation(0.5);
		rightSplitPane.setLeftComponent(middleContainerPanel);
		rightSplitPane.setRightComponent(targetButtonPanel);

		centerSplitPane.setLeftComponent(sourceButtonPanel);
		centerSplitPane.setRightComponent(rightSplitPane);

		topCenterPanel.add(centerSplitPane, BorderLayout.CENTER);
		topCenterPanel.setPreferredSize(new Dimension((int) (Config.FRAME_DEFAULT_WIDTH * 0.8), (int) (Config.FRAME_DEFAULT_HEIGHT / 1.5)));
//        topCenterPanel.setSize(new Dimension((int) (Config.FRAME_DEFAULT_WIDTH / 1.5), (int) (Config.FRAME_DEFAULT_HEIGHT / 1.5)));
		return topCenterPanel;
	}

//	public ArrayList<TreeNodeMapping> getMappings()
//	{
//		return mappings;
//	}

	public JScrollPane getSourceScrollPane()
	{
		return sourceScrollPane;
	}

	public JScrollPane getTargetScrollPane()
	{
		return targetScrollPane;
	}

	public MiddlePanel getMiddlePanel()
	{
		return middlePanel;
	}

	public void actionPerformed(ActionEvent e)
	{
		String command = e.getActionCommand();
		try
		{
			boolean everythingGood = true;
			if (SELECT_SOURCE.equals(command))
			{
				File file = DefaultSettings.getUserInputOfFileFromGUI(this, //FileUtil.getUIWorkingDirectoryPath(),
					Config.SOURCE_TREE_FILE_DEFAULT_EXTENTION, Config.OPEN_DIALOG_TITLE_FOR_DEFAULT_SOURCE_FILE, false, false);
				if (file != null)
				{
					everythingGood = processOpenSourceTree(file, true, true);
				}
//                else
//                {
//                    System.out.println("Open command cancelled by user.");
//                }
			}
			else if (SELECT_TARGET.equals(command))
			{
				File file = DefaultSettings.getUserInputOfFileFromGUI(this, //FileUtil.getUIWorkingDirectoryPath(),
					Config.TARGET_TREE_FILE_DEFAULT_EXTENTION, Config.OPEN_DIALOG_TITLE_FOR_DEFAULT_TARGET_FILE, false, false);
				if (file != null)
				{
					everythingGood = processOpenTargetTree(file, true, true);
				}
//                else
//                {
//                    System.out.println("Open command cancelled by user.");
//                }
			}
			if (!everythingGood)
			{
				Message msg = MessageResources.getMessage("GEN3", new Object[0]);
				JOptionPane.showMessageDialog(this, msg.toString(), "Error", JOptionPane.ERROR_MESSAGE);
			}
			//for test purpose
//            else if (TO_TEST_LOAD_COMMAND.equals(command))
//            {
//                File sourceFile = new File("C:/Projects/hl7sdk/map/examples/090102/090102.scm");
//                File targetFile = new File("C:/Projects/hl7sdk/map/examples/090102/090102.hsm");
//                processOpenSourceTree(sourceFile, false);
//                processOpenTargetTree(targetFile, true, false);
//            }
		}
		catch (Exception e1)
		{
			DefaultSettings.reportThrowableToLogAndUI(this, e1, "", this, false, false);
		}
	}

	private void resetMiddlePanel()
	{
		if (middlePanel != null)
		{
			middlePanel.resetGraph();
			middlePanel.repaint();
		}
	}

	protected void buildSourceTree(Object metaInfo, File absoluteFile, boolean isToResetGraph) throws Exception
	{

		// The following is changed by eric for the need of loading dbm file as the source, todo need refactory
		String fileExtension = FileUtil.getFileExtension(absoluteFile, true);

		TreeNode nodes;
		if (Config.CSV_METADATA_FILE_DEFAULT_EXTENTION.equals(fileExtension))
		{
			// generate GUI nodes from object graph.
			SCMMapSourceNodeLoader scmMapSourceNodeLoader = new SCMMapSourceNodeLoader();
			nodes = scmMapSourceNodeLoader.loadData(metaInfo);
		}
		else if (Config.DATABASE_META_FILE_DEFAULT_EXTENSION.equals(fileExtension))
		{
			// generate GUI nodes from object graph.
			DBMMapSourceNodeLoader dbmTreeNodeLoader = new DBMMapSourceNodeLoader();
			nodes = dbmTreeNodeLoader.loadData(metaInfo);
		}
		else
		{
			throw new ApplicationException("Unknow Source File Extension:" + absoluteFile,
				new IllegalArgumentException());
		}

		//Build the source tree
		sTree = new SourceTree(this, nodes);
		sTree.getSelectionModel().addTreeSelectionListener((TreeSelectionListener) (getMappingDataManager().getPropertiesSwitchController()));
//		sTree.setCellRenderer(new DefaultMappingTreeCellRender(this));
		sourceTreeDragTransferHandler = new TreeDefaultDragTransferHandler(sTree, DnDConstants.ACTION_LINK);

		sourceScrollPane.getViewport().add(sTree);
		sTree.expandAll();
		//register collapse all and expand all actions.
		sourceTreeCollapseAllAction.setTree(sTree);
		sourceTreeExpandAllAction.setTree(sTree);
		sTree.getInputMap().put(sourceTreeCollapseAllAction.getAcceleratorKey(), sourceTreeCollapseAllAction.getName());
		sTree.getActionMap().put(sourceTreeCollapseAllAction.getName(), sourceTreeCollapseAllAction);
		sTree.getInputMap().put(sourceTreeExpandAllAction.getAcceleratorKey(), sourceTreeExpandAllAction.getName());
		sTree.getActionMap().put(sourceTreeExpandAllAction.getName(), sourceTreeExpandAllAction);

		if (tTree != null && isToResetGraph)
		{
			resetMiddlePanel();
		}
		if (absoluteFile != null)
		{
			String absoluteFilePath = absoluteFile.getAbsolutePath();
			sourceLocationArea.setText(absoluteFilePath);
			sourceLocationArea.setToolTipText(absoluteFilePath);
			mappingSourceFile = absoluteFile;//new File(absoluteFilePath);
		}
		else
		{
			mappingSourceFile = null;
		}
		if (this.getRootPane() != null)
		{
			this.getRootPane().repaint();
		}

		getMappingFileSynchronizer().registerFile(MappingFileSynchronizer.FILE_TYPE.Source_File, absoluteFile);
	}

	// ??? Not Sure the behavior difference of build target tree and source tree besides we have SourceTree TargerTree -Eric
	protected void buildTargetTree(Object metaInfo, File absoluteFile, boolean isToResetGraph) throws Exception
	{
		// The following is changed by eric for the need of loading dbm file as the source, todo need refactory
		String fileExtension = FileUtil.getFileExtension(absoluteFile, true);

		TreeNode nodes;
		if (Config.HSM_META_DEFINITION_FILE_DEFAULT_EXTENSION.equals(fileExtension))
		{
			// generate GUI nodes from object graph.
			HSMMapTargetNodeLoader hl7MapTargetNodeLoader = new HSMMapTargetNodeLoader();
			nodes = hl7MapTargetNodeLoader.loadData(metaInfo);
		}
		else if (Config.DATABASE_META_FILE_DEFAULT_EXTENSION.equals(fileExtension))
		{
			// generate GUI nodes from object graph.
			DBMMapTargetNodeLoader dbmMapTargetNodeLoader = new DBMMapTargetNodeLoader();
			nodes = dbmMapTargetNodeLoader.loadData(metaInfo);
		}
		else
		{
			throw new ApplicationException("Unknow Source File Extension:" + absoluteFile,
				new IllegalArgumentException());
		}

		//Build the target tree
		tTree = new TargetTree(this, nodes);
		tTree.getSelectionModel().addTreeSelectionListener((TreeSelectionListener) (getMappingDataManager().getPropertiesSwitchController()));
//		tTree.setCellRenderer(new DefaultMappingTreeCellRender(this));
		//drag source for DnD to middle panel.
		targetTreeDragTransferHandler = new TargetTreeDragTransferHandler(tTree, DnDConstants.ACTION_LINK);
		//drop target for DnD from source tree.
		targetTreeDropTransferHandler = new TargetTreeDropTransferHandler(tTree, getMappingDataManager(), DnDConstants.ACTION_LINK);

		targetScrollPane.getViewport().add(tTree);
//		tTree.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
//		targetScrollPane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		tTree.expandAll();
		//register collapse all and expand all actions.
		targetTreeCollapseAllAction.setTree(tTree);
		targetTreeExpandAllAction.setTree(tTree);
		tTree.getInputMap().put(targetTreeCollapseAllAction.getAcceleratorKey(), targetTreeCollapseAllAction.getName());
		tTree.getActionMap().put(targetTreeCollapseAllAction.getName(), targetTreeCollapseAllAction);
		tTree.getInputMap().put(targetTreeExpandAllAction.getAcceleratorKey(), targetTreeExpandAllAction.getName());
		tTree.getActionMap().put(targetTreeExpandAllAction.getName(), targetTreeExpandAllAction);
		if (sTree != null && isToResetGraph)
		{
			resetMiddlePanel();
		}
		if (absoluteFile != null)
		{
			String absoluteFilePath = absoluteFile.getAbsolutePath();
			targetLocationArea.setText(absoluteFilePath);
			targetLocationArea.setToolTipText(absoluteFilePath);
			mappingTargetFile = absoluteFile;//new File(absoluteFilePath);
		}
		else
		{
			mappingTargetFile = null;
		}
		if (this.getRootPane() != null)
		{
			this.getRootPane().repaint();
		}
		getMappingFileSynchronizer().registerFile(MappingFileSynchronizer.FILE_TYPE.Target_File, absoluteFile);
	}

	/**
	 * Called by actionPerformed() and overridable by descendant classes.
	 *
	 * @param file
	 * @throws Exception
	 */
	protected boolean processOpenSourceTree(File file, boolean isToResetGraph, boolean supressReportIssuesToUI) throws Exception
	{
//        System.out.println("Opening: " + file.getName());
		String fileExtension = FileUtil.getFileExtension(file, true);

		MetaParser parser = null;
		MetaObject metaInfo = null;
		BaseResult returnResult = null;

		// The following is changed by eric for the need of loading dbm file as the source, todo need refactory

		// parse the file into a meta object graph.
		if (Config.DATABASE_META_FILE_DEFAULT_EXTENSION.equals(fileExtension))
		{
			parser = new DatabaseMetaParserImpl();
		}
		else
		{//default to Config.CSV_METADATA_FILE_DEFAULT_EXTENTION
//			if (Config.CSV_METADATA_FILE_DEFAULT_EXTENTION.equals(fileExtension))
			parser = new CSVMetaParserImpl();

//            throw new ApplicationException("Unkonw Source File Extension:" + file,
//                new IllegalArgumentException());
		}
		returnResult = parser.parse(new FileReader(file));
		ValidatorResults validatorResults = returnResult.getValidatorResults();
		if (validatorResults != null && validatorResults.hasFatal())
		{
			Message msg = validatorResults.getMessages(ValidatorResult.Level.FATAL).get(0);
			DefaultSettings.reportThrowableToLogAndUI(this, null, msg.toString(), this, true, supressReportIssuesToUI);
			return false;
		}

		if (Config.DATABASE_META_FILE_DEFAULT_EXTENSION.equals(fileExtension))
		{
			metaInfo = ((DatabaseMetaResult) returnResult).getDatabaseMeta();
		}
		else
		{//default to Config.HSM_META_DEFINITION_FILE_DEFAULT_EXTENSION
			metaInfo = ((CSVMetaResult) returnResult).getCsvMeta();
		}
		buildSourceTree(metaInfo, file, isToResetGraph);
		middlePanel.getMappingDataManager().registerSourceComponent(metaInfo, file);
		return true;
	}

	/**
	 * Called by actionPerformed() and overridable by descendant classes.
	 *
	 * @param file
	 * @throws Exception
	 */
	protected boolean processOpenTargetTree(File file, boolean isToResetGraph, boolean supressReportIssuesToUI) throws Exception
	{
//        System.out.println("Opening: " + file.getName());
		String fileExtension = FileUtil.getFileExtension(file, true);
		// parse the file into a meta object graph.
		MetaParser parser = null;
		MetaObject metaInfo = null;
		BaseResult returnResult = null;

		// The following is changed by eric for the need of loading dbm file as the source, todo need refactory

		// parse the file into a meta object graph.
		if (Config.DATABASE_META_FILE_DEFAULT_EXTENSION.equals(fileExtension))
		{
			parser = new DatabaseMetaParserImpl();
		}
		else
		{//default to Config.HSM_META_DEFINITION_FILE_DEFAULT_EXTENSION
//			if (Config.HSM_META_DEFINITION_FILE_DEFAULT_EXTENSION.equals(fileExtension))
			parser = HL7V3MetaFileParser.instance();

//			HL7V3MetaResult hl7V3MetaResult = (HL7V3MetaResult)parser.parse(new FileReader(file));
//            throw new ApplicationException("Unkonw Source File Extension:" + file,
//                new IllegalArgumentException());
		}
		returnResult = parser.parse(new FileReader(file));
		ValidatorResults validatorResults = returnResult.getValidatorResults();
		if (validatorResults != null && validatorResults.hasFatal())
		{
			Message msg = validatorResults.getMessages(ValidatorResult.Level.FATAL).get(0);
			DefaultSettings.reportThrowableToLogAndUI(this, null, msg.toString(), this, true, supressReportIssuesToUI);
			return false;
		}

		if (Config.DATABASE_META_FILE_DEFAULT_EXTENSION.equals(fileExtension))
		{
			metaInfo = ((DatabaseMetaResult) returnResult).getDatabaseMeta();
		}
		else
		{//default to Config.HSM_META_DEFINITION_FILE_DEFAULT_EXTENSION
			metaInfo = ((HL7V3MetaResult)returnResult).getHl7V3Meta();
		}
		buildTargetTree(metaInfo, file, isToResetGraph);
		middlePanel.getMappingDataManager().registerTargetComponent(metaInfo, file);
		return true;
	}

	/**
	 * Called by actionPerformed() and overridable by descendant classes.
	 *
	 * @param file
	 * @throws Exception changed from protected to pulic by sean
	 */
	public ValidatorResults processOpenMapFile(File file) throws Exception
	{
		// parse the file.
		MapParserImpl parser = new MapParserImpl();
		MappingResult returnResult = parser.parse(file.getParent(), new FileReader(file));
		ValidatorResults validatorResults = returnResult.getValidatorResults();
		if (validatorResults != null && validatorResults.hasFatal())
		{//immediately return if it has fatal errors.
			return validatorResults;
		}
		Mapping mapping = returnResult.getMapping();

		//build source tree
		BaseComponent sourceComp = mapping.getSourceComponent();
		Object sourceMetaInfo = sourceComp.getMeta();
		File sourceFile = sourceComp.getFile();
		buildSourceTree(sourceMetaInfo, sourceFile, false);
//        middlePanel.getMappingDataManager().registerSourceComponent(sourceComp);

		//build target tree
		BaseComponent targetComp = mapping.getTargetComponent();
		Object targetMetaInfo = targetComp.getMeta();
		File targetFile = targetComp.getFile();
		buildTargetTree(targetMetaInfo, targetFile, false);
//		middlePanel.getMappingDataManager().registerTargetComponent(targetComp);

		middlePanel.getMappingDataManager().setMappingData(mapping);

		//set both invisible since no use to allow user to change while mapping exists.
		if (mapping.getFunctionComponent().size() > 0 || mapping.getMaps().size() > 0)
		{
			openSourceButton.setEnabled(false);
			openTargetButton.setEnabled(false);
//			sourceLocationPanel.setEnabled(false);
//			targetLocationPanel.setEnabled(false);
		}
		setSaveFile(file);
		return validatorResults;
	}

	/**
	 * Return the mapping data manager.
	 *
	 * @return the mapping data manager.
	 */
	public MappingDataManager getMappingDataManager()
	{
		return middlePanel.getMappingDataManager();
	}

	public JTree getSourceTree()
	{
		return sTree;
	}

	public JTree getTargetTree()
	{
		return tTree;
	}

	public FunctionLibraryPane getFunctionPane()
	{
		return functionPane;
	}

	public DefaultPropertiesPage getPropertiesPane()
	{
		return propertiesPane;
	}

//	private void updateTitle(String newTitle)
//	{
//		JRootPane rootPane = getRootPane();
////		System.out.println("root pane type: " + rootPane.getClass().getName());
//		if (rootPane != null)
//		{
//			Container container = rootPane.getParent();
////		System.out.println("root pane parent is " + container.getClass().getName());
//			if (container instanceof MainFrame)
//			{
//				JTabbedPane tabbedPane = ((MainFrame) container).getTabbedPane();
//				int seleIndex = tabbedPane.getSelectedIndex();
//				if (seleIndex != -1)
//				{
//					tabbedPane.setTitleAt(seleIndex, newTitle);
//				}
//			}
//		}
//	}

//	public File getMappingFile()
//	{
//		return mappingFile;
//	}
//
//	public void setMappingFile(File mappingFile)
//	{
//		if (!GeneralUtilities.areEqual(this.mappingFile, mappingFile))
//		{
//			this.mappingFile = mappingFile;
//			updateTitle(this.mappingFile.getName());
//		}
//	}

	public void report()
	{

	}

	/**
	 * Indicate whether or not it is changed.
	 */
	public boolean isChanged()
	{
		return middlePanel.getMappingDataManager().isGraphChanged();
	}

	/**
	 * Explicitly set the value.
	 *
	 * @param newValue
	 */
	public void setChanged(boolean newValue)
	{
		middlePanel.getMappingDataManager().setGraphChanged(newValue);
	}

	public Map getMenuItems(String menu_name)
	{
//		if (menuMap == null)
//		{
//			menuMap = Collections.synchronizedMap(new HashMap());
//		}
//
//		Map actionMap = (Map) menuMap.get(menu_name);
//		if (actionMap == null)
//		{//lazy initialization
//			actionMap = new HashMap();
//			menuMap.put(menu_name, actionMap);
//		}//end of if(actionMap==null)

		Action action = null;
		Map actionMap = super.getMenuItems(menu_name);
		if (MenuConstants.FILE_MENU_NAME.equals(menu_name))
		{
//			System.out.println("File updated...");
			JRootPane rootPane = this.getRootPane();
			if (rootPane != null)
			{//rootpane is not null implies this panel is fully displayed;
				//on the flip side, if it is null, it implies it is under certain construction.
				MainFrame mf = (MainFrame) rootPane.getParent();
				MainContextManager contextManager = mf.getMainContextManager();
				contextManager.enableAction(ActionConstants.NEW_MAP_FILE, false);
				contextManager.enableAction(ActionConstants.OPEN_MAP_FILE, true);
//				action = (Action) actionMap.get(ActionConstants.OPEN_MAP_FILE);
//				if (action == null)
//				{//create once and only once.
//					action = new OpenMapAction(mf);
//					actionMap.put(ActionConstants.OPEN_MAP_FILE, action);
//				}
//				action.setEnabled(true);
//				action = (Action) actionMap.get(ActionConstants.NEW_MAP_FILE);
//				if (action == null)
//				{
//					action = new NewMapAction(mf);
//					actionMap.put(ActionConstants.NEW_MAP_FILE, action);
//				}
//				action.setEnabled(false);
			}
			action = (Action) actionMap.get(ActionConstants.SAVE);
			if (action == null || !(action instanceof gov.nih.nci.hl7.ui.map.actions.SaveAction))
			{
				action = new gov.nih.nci.hl7.ui.map.actions.SaveAction(this);
				actionMap.put(ActionConstants.SAVE, action);
			}
			action.setEnabled(true);

			action = (Action) actionMap.get(ActionConstants.SAVE_AS);
			if (action == null || !(action instanceof gov.nih.nci.hl7.ui.map.actions.SaveAsAction))
			{
				action = new gov.nih.nci.hl7.ui.map.actions.SaveAsAction(this);
				actionMap.put(ActionConstants.SAVE_AS, action);
			}
			action.setEnabled(true);

			action = (Action) actionMap.get(ActionConstants.VALIDATE);
			if (action == null || !(action instanceof gov.nih.nci.hl7.ui.map.actions.ValidateMapAction))
			{
				action = new gov.nih.nci.hl7.ui.map.actions.ValidateMapAction(this);
				actionMap.put(ActionConstants.VALIDATE, action);
			}
			action.setEnabled(true);

			action = (Action) actionMap.get(ActionConstants.CLOSE);
			if (action == null || !(action instanceof gov.nih.nci.hl7.ui.map.actions.CloseAction))
			{
				action = new gov.nih.nci.hl7.ui.map.actions.CloseAction(this);
				actionMap.put(ActionConstants.CLOSE, action);
			}
			action.setEnabled(true);
		}//end of if(menu_name == MenuConstants.FILE_MENU_NAME)
		else if (MenuConstants.REPORT_MENU_NAME.equals(menu_name))
		{
			action = (Action) actionMap.get(ActionConstants.GENERATE_REPORT);
			if (action == null || !(action instanceof gov.nih.nci.hl7.ui.map.actions.GenerateReportAction))
			{
				action = new gov.nih.nci.hl7.ui.map.actions.GenerateReportAction(this);
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
		if (contextManager != null)
		{//contextManager is not null implies this panel is fully displayed;
			//on the flip side, if it is null, it implies it is under certain construction.
			openAction = contextManager.getDefinedAction(ActionConstants.OPEN_MAP_FILE);
		}
		return openAction;
	}

	/**
	 * Provide the extended implementation of this method by adding additional files of source and target;
	 *
	 * @return a list of file objects that this context is associated with.
	 */
	public java.util.List<File> getAssociatedFileList()
	{
		java.util.List<File> resultList = super.getAssociatedFileList();
		if (mappingSourceFile != null)
		{
			resultList.add(mappingSourceFile);
		}
		if (mappingTargetFile != null)
		{
			resultList.add(mappingTargetFile);
		}
		return resultList;
	}

	/**
	 * Explicitly reload information from the internal given file.
	 *
	 * @throws Exception
	 */
	public void reload() throws Exception
	{
		processOpenMapFile(getSaveFile());
	}

	/**
	 * Return if the source tree has been populated.
	 * @return if the source tree has been populated.
	 */
	public boolean isSourceTreePopulated()
	{
		return sTree!=null;
	}

	/**
	 * Return if the target tree has been populated.
	 * @return if the target tree has been populated.
	 */
	public boolean isTargetTreePopulated()
	{
		return tTree!=null;
	}

	/**
	 * Return whether the mapping module is in drag-and-drop mode.
	 * @return whether the mapping module is in drag-and-drop mode.
	 */
	public boolean isInDragDropMode()
	{
		return sourceTreeDragTransferHandler.isInDragDropMode() ||
				targetTreeDropTransferHandler.isInDragDropMode() ||
				middlePanel.getMiddlePanelDropTransferHandler().isInDragDropMode();
	}

	/**
	 * Set a new value for the mode.
	 * @param newValue
	 */
	public void setInDragDropMode(boolean newValue)
	{
		sourceTreeDragTransferHandler.setInDragDropMode(newValue);
		targetTreeDropTransferHandler.setInDragDropMode(newValue);
		middlePanel.getMiddlePanelDropTransferHandler().setInDragDropMode(newValue);
	}

	/**
	 * return the action list(open, save, close, etc) inherited with this client.
	 */
	public java.util.List<Action> getToolbarActionList()
	{
		java.util.List<Action> actions = super.getToolbarActionList();
		RefreshAction refreshAction = new RefreshAction(this);
		actions.add(refreshAction);
		return actions;
	}

	public MappingFileSynchronizer getMappingFileSynchronizer()
	{
		return this.fileSynchronizer;
	}

	/**
	 * Set a new save file.
	 *
	 * @param saveFile
	 * @return true if the value is changed, false otherwise.
	 */
	public boolean setSaveFile(File saveFile)
	{
		boolean result = super.setSaveFile(saveFile);
		if(result)
		{
			getMappingFileSynchronizer().registerFile(MappingFileSynchronizer.FILE_TYPE.Mapping_File, saveFile);
		}
		return result;
	}

	/**
	 * Reload the file specified in the parameter.
	 * @param changedFileMap
	 */
	public void reload(Map<MappingFileSynchronizer.FILE_TYPE, File> changedFileMap)
	{
		/**
		 * Design rationale:
		 * 1) if the changedFileMap is null, simply return;
		 * 2) if the getSaveFile() method does not return null, it implies current panel associates with a mapping file,
		 * just reload the whole mapping file so as to refresh those mapping relationship;
		 * 3) if the getSaveFile() returns null, just reload source and/or target file within the changedFileMap,
		 * and ignore the checking of MappingFileSynchronizer.FILE_TYPE.Mapping_File item in the map;
		 */
		if(changedFileMap==null)
		{
			return;
		}
		File existMapFile = getSaveFile();
		try
		{
			if(existMapFile!=null)
			{
				if(existMapFile.exists())
				{
					processOpenMapFile(existMapFile);
				}
				else
				{//exist map file does not exist anymore
					JOptionPane.showMessageDialog(this, existMapFile.getAbsolutePath() + " does not exist or is not accessible anymore", "File Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
			else
			{//exist map file does not exist, simply reload source and/or target file
				Iterator it = changedFileMap.keySet().iterator();
				while(it.hasNext())
				{
					MappingFileSynchronizer.FILE_TYPE key = (MappingFileSynchronizer.FILE_TYPE) it.next();
					File file = changedFileMap.get(key);
					if(GeneralUtilities.areEqual(MappingFileSynchronizer.FILE_TYPE.Source_File, key))
					{
						processOpenSourceTree(file, true, true);
					}
					else if(GeneralUtilities.areEqual(MappingFileSynchronizer.FILE_TYPE.Target_File, key))
					{
						processOpenTargetTree(file, true, true);
					}
//					else
//					{//default to the map file reload
//						processOpenMapFile(file);
//					}
				}//end of while
			}//end of else
		}
		catch (Exception e)
		{
			DefaultSettings.reportThrowableToLogAndUI(this, e, "", this, false, false);
		}
	}
}

/**
 * HISTORY      : $Log: not supported by cvs2svn $
 * HISTORY      : Revision 1.80  2006/08/02 18:44:23  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.79  2006/07/13 19:51:49  jiangsc
 * HISTORY      : Save point.
 * HISTORY      :
 * HISTORY      : Revision 1.78  2006/01/03 19:16:52  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.77  2006/01/03 18:56:25  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.76  2005/12/29 23:06:14  jiangsc
 * HISTORY      : Changed to latest project name.
 * HISTORY      :
 * HISTORY      : Revision 1.75  2005/12/29 15:39:06  chene
 * HISTORY      : Optimize imports
 * HISTORY      :
 * HISTORY      : Revision 1.74  2005/12/22 19:06:33  jiangsc
 * HISTORY      : Feature enhancement.
 * HISTORY      :
 * HISTORY      : Revision 1.73  2005/12/08 23:22:43  jiangsc
 * HISTORY      : Upgrade the handleValidatorResults() function.
 * HISTORY      :
 * HISTORY      : Revision 1.72  2005/12/02 23:02:57  jiangsc
 * HISTORY      : Save point
 * HISTORY      :
 * HISTORY      : Revision 1.71  2005/11/29 16:23:54  jiangsc
 * HISTORY      : Updated License
 * HISTORY      :
 * HISTORY      : Revision 1.70  2005/11/23 19:48:52  jiangsc
 * HISTORY      : Enhancement on mapping validations.
 * HISTORY      :
 * HISTORY      : Revision 1.69  2005/11/18 20:28:14  jiangsc
 * HISTORY      : Enhanced context-sensitive menu navigation and constructions.
 * HISTORY      :
 * HISTORY      : Revision 1.68  2005/11/16 21:02:56  umkis
 * HISTORY      : defect# 195, getToolbarActionList() is added for tool bar menu.
 * HISTORY      :
 * HISTORY      : Revision 1.67  2005/11/14 19:55:51  jiangsc
 * HISTORY      : Implementing UI enhancement
 * HISTORY      :
 * HISTORY      : Revision 1.66  2005/11/09 23:05:51  jiangsc
 * HISTORY      : Back to previous version.
 * HISTORY      :
 * HISTORY      : Revision 1.64  2005/11/02 22:36:06  chene
 * HISTORY      : change "\\" to "/"
 * HISTORY      :
 * HISTORY      : Revision 1.63  2005/10/26 18:12:29  jiangsc
 * HISTORY      : replaced printStackTrace() to Log.logException
 * HISTORY      :
 * HISTORY      : Revision 1.62  2005/10/26 16:43:36  chene
 * HISTORY      : Fix the spelling error
 * HISTORY      :
 * HISTORY      : Revision 1.61  2005/10/25 22:00:42  jiangsc
 * HISTORY      : Re-arranged system output strings within UI packages.
 * HISTORY      :
 * HISTORY      : Revision 1.60  2005/10/24 20:31:00  jiangsc
 * HISTORY      : Turned off auto-scroll feature to comprise mapping issue.
 * HISTORY      :
 * HISTORY      : Revision 1.59  2005/10/18 17:01:03  jiangsc
 * HISTORY      : Changed one function signature in DragDrop component;
 * HISTORY      : Enhanced drag-drop status monitoring in MappingPanel;
 * HISTORY      :
 * HISTORY      : Revision 1.58  2005/10/17 22:32:00  umkis
 * HISTORY      : no message
 * HISTORY      :
 * HISTORY      : Revision 1.57  2005/10/13 18:53:44  jiangsc
 * HISTORY      : Added validation on invalid file type to map and HSM modules.
 * HISTORY      :
 * HISTORY      : Revision 1.56  2005/10/13 17:37:41  jiangsc
 * HISTORY      : Enhanced UI reporting on exceptions.
 * HISTORY      :
 * HISTORY      : Revision 1.55  2005/10/12 18:51:10  giordanm
 * HISTORY      : get MappingResult object working
 * HISTORY      :
 * HISTORY      : Revision 1.54  2005/10/10 20:49:01  jiangsc
 * HISTORY      : Enhanced dialog operation.
 * HISTORY      :
 * HISTORY      : Revision 1.53  2005/10/05 20:52:11  jiangsc
 * HISTORY      : GUI Enhancement.
 * HISTORY      :
 * HISTORY      : Revision 1.52  2005/10/05 20:50:30  jiangsc
 * HISTORY      : GUI Enhancement.
 * HISTORY      :
 * HISTORY      : Revision 1.51  2005/10/05 20:39:54  jiangsc
 * HISTORY      : GUI Enhancement.
 * HISTORY      :
 * HISTORY      : Revision 1.50  2005/09/16 23:18:55  chene
 * HISTORY      : Database prototype GUI support, but can not be loaded
 * HISTORY      :
 * HISTORY      : Revision 1.49  2005/09/16 16:20:17  giordanm
 * HISTORY      : HL7V3 parser is not returning a result object not a just a meta object.
 * HISTORY      :
 * HISTORY      : Revision 1.48  2005/09/15 16:01:49  giordanm
 * HISTORY      : trying to get result objects working for CSVMetaParser... (result objects contain the information a service returns as well as a list of validation errors/warnings)
 * HISTORY      :
 * HISTORY      : Revision 1.47  2005/08/26 15:57:45  jiangsc
 * HISTORY      : TreeExpandAll and TreeCollapseAll Class package move
 * HISTORY      :
 * HISTORY      : Revision 1.46  2005/08/22 21:35:29  jiangsc
 * HISTORY      : Changed BaseComponentFactory and other UI classes to use File instead of string name;
 * HISTORY      : Added first implementation of Function Constant;
 * HISTORY      :
 * HISTORY      : Revision 1.45  2005/08/22 17:32:46  giordanm
 * HISTORY      : change the file attribute within BaseComponent from a String to a File,  this checkin also contains some refactor work to the FileUtil.
 * HISTORY      :
 * HISTORY      : Revision 1.44  2005/08/19 21:09:38  jiangsc
 * HISTORY      : Name change
 * HISTORY      :
 * HISTORY      : Revision 1.43  2005/08/18 15:30:18  jiangsc
 * HISTORY      : First implementation on Switch control.
 * HISTORY      :
 * HISTORY      : Revision 1.42  2005/08/17 20:01:38  chene
 * HISTORY      : Refactor HL7V3MetaFileParser to a singleton
 * HISTORY      :
 * HISTORY      : Revision 1.41  2005/08/12 18:38:12  jiangsc
 * HISTORY      : Enable HL7 V3 Message to be saved in multiple XML file.
 * HISTORY      :
 * HISTORY      : Revision 1.40  2005/08/11 22:10:33  jiangsc
 * HISTORY      : Open/Save File Dialog consolidation.
 * HISTORY      :
 * HISTORY      : Revision 1.39  2005/08/08 18:05:53  giordanm
 * HISTORY      : a bunch of checkins that changes hard coded paths to relative paths.
 * HISTORY      :
 * HISTORY      : Revision 1.38  2005/08/05 20:35:54  jiangsc
 * HISTORY      : 0)Implemented field sequencing on CSVPanel but needs further rework;
 * HISTORY      : 1)Removed (Yes/No) for questions;
 * HISTORY      : 2)Removed double-checking after Save-As;
 * HISTORY      :
 * HISTORY      : Revision 1.37  2005/08/04 22:22:18  jiangsc
 * HISTORY      : Updated license and class header information.
 * HISTORY      :
 * HISTORY      : Revision 1.36  2005/08/02 22:28:56  jiangsc
 * HISTORY      : Newly enhanced context-sensitive menus and toolbar.
 * HISTORY      :
 * HISTORY      : Revision 1.35  2005/07/28 18:18:43  jiangsc
 * HISTORY      : Can Open HSM Panel
 * HISTORY      :
 * HISTORY      : Revision 1.34  2005/07/27 22:41:12  jiangsc
 * HISTORY      : Consolidated context sensitive menu implementation.
 * HISTORY      :
 * HISTORY      : Revision 1.33  2005/07/27 13:57:44  jiangsc
 * HISTORY      : Added the first round of HSMPanel.
 * HISTORY      :
 * HISTORY      : Revision 1.32  2005/07/25 22:32:16  jiangsc
 * HISTORY      : Added Open command.
 * HISTORY      :
 * HISTORY      : Revision 1.31  2005/07/25 21:56:44  jiangsc
 * HISTORY      : 1) Added expand all and collapse all;
 * HISTORY      : 2) Added toolbar on the mapping panel;
 * HISTORY      : 3) Consolidated menus;
 */

