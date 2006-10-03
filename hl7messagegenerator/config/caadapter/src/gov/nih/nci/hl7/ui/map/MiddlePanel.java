/**
 * <!-- LICENSE_TEXT_START -->
 * $Header: /share/content/gforge/formbuilder/hl7messagegenerator/config/caadapter/src/gov/nih/nci/hl7/ui/map/MiddlePanel.java,v 1.1 2006-10-03 17:38:43 marwahah Exp $
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

import gov.nih.nci.hl7.ui.jgraph.*;
import org.jgraph.graph.GraphModel;

import javax.swing.*;
import java.awt.*;
import java.awt.dnd.DnDConstants;

/**
 * The panel is used to render graphical respresentation of the mapping relations between
 * source and target tree panel.
 *
 * @author OWNER: Scott Jiang
 * @author LAST UPDATE $Author: marwahah $
 * @version Since caAdapter v1.2
 *          revision    $Revision: 1.1 $
 *          date        $Date: 2006-10-03 17:38:43 $
 */
public class MiddlePanel extends JPanel //implements ActionListener
{
	private MiddlePanelJGraph graph = null;
	private MiddlePanelJGraphController graphController = null;
	private MiddlePanelJGraphViewFactory graphViewFactory = null;
	private MiddlePanelJGraphDropTargetHandler middlePanelDropTransferHandler = null;
	private MappingPanel mappingPanel = null;
//    private JPanel functionPanel = null;

//	private String[] functionBoxArray = (String[]) FunctionalBoxManager.getInstance().getAllFunctionNames().toArray(new String[0]);//new String[]{"Merge", "Split", "Connect"};
//	private JComboBox functionComboBox = new JComboBox();//functionBoxArray);
//	private AddFunctionalBoxAction addFunctionAction = null;
//	private JButton addFunctionButton = null;

	private JScrollPane graphScrollPane = new JScrollPane();
	private MiddlePanelScrollAdjustmentCoordinator adjustmentCoordinator = null;
	private MiddlePanelJGraphScrollAdjustmentAdapter graphAdjustmentAdapter = null;
	//for test purpose
	private GraphModel model = null;
	private GraphModel oldModel = null;
	private java.util.List oldMappingPairCellMap = null;
//	private java.util.List mappings = new ArrayList();

	private boolean inTestMode = false;

    public MiddlePanel(MappingPanel mappingPanel)
	{
        this.mappingPanel = mappingPanel;
		setBorder(BorderFactory.createRaisedBevelBorder());
		setLayout(new BorderLayout());
		graphScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		graphScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		add(graphScrollPane, BorderLayout.CENTER);
//		functionPanel = new JPanel(new BorderLayout());
//		functionPanel.add(functionComboBox, BorderLayout.CENTER);
//		add(functionPanel, BorderLayout.NORTH);
		//need it there for place holding, but not visible to users.
//		functionPanel.setVisible(false);
		initGraph();
    }

	public void resetGraph()
	{
		if(graphController.isGraphChanged())
		{
			initGraph();
		}
		
	}

	public JScrollPane getGraphScrollPane()
	{
		return graphScrollPane;
	}

	private void initGraph()
	{
//		model = new DefaultGraphModel();
//		graph = new JGraph(model);
		boolean changed = false;
		model = new MiddlePanelGraphModel();
		if (graph == null)
		{
			graph = new MiddlePanelJGraph(model);
			graphController = null;
//			add(graph, BorderLayout.CENTER);
			graphScrollPane.getViewport().setView(graph);
			adjustmentCoordinator = new MiddlePanelScrollAdjustmentCoordinator(this, graphScrollPane);
//			mappingPanel.getSourceScrollPane().getVerticalScrollBar().addAdjustmentListener(adjustmentCoordinator);
//			mappingPanel.getTargetScrollPane().getVerticalScrollBar().addAdjustmentListener(adjustmentCoordinator);
			graphAdjustmentAdapter = new MiddlePanelJGraphScrollAdjustmentAdapter(this);
//			graphScrollPane.getVerticalScrollBar().addAdjustmentListener(graphAdjustmentAdapter);
			changed = true;
		}
		else
		{
			graph.setModel(model);
		}
		if (graphController == null)
		{
			graphController = new MiddlePanelJGraphController(graph, this, mappingPanel);
			changed = true;
		}
		else
		{
			graphController.setJGraph(graph);
		}
		if(changed)
		{
			graphViewFactory = new MiddlePanelJGraphViewFactory();
			graph.getGraphLayoutCache().setFactory(graphViewFactory);
			middlePanelDropTransferHandler = new MiddlePanelJGraphDropTargetHandler(graph, graphController, DnDConstants.ACTION_LINK);//DnDConstants.ACTION_LINK,
//			addFunctionAction = new AddFunctionalBoxAction(this, graphController);
//			addFunctionButton = new JButton(addFunctionAction);
//			functionPanel.remove(addFunctionButton);
//			functionPanel.add(addFunctionButton, BorderLayout.EAST);
//			addFunctionButton.setMnemonic('A');
		}
	}

	public MappingDataManager getMappingDataManager()
	{
		return this.graphController;
	}

	/**
	 * package only for test purpose
	 * should remove when test is done.
	 */
	boolean isInTestMode()
	{
		return inTestMode;
	}

//	void setInTestMode(boolean inTestMode)
//	{
//		boolean oldValue = this.inTestMode;
//		this.inTestMode = inTestMode;
//		if (oldValue != inTestMode)
//		{
//			if (oldModel == null)
//			{
//				oldModel = model;
//				model = new DefaultGraphModel();
//
//				oldMappingPairCellMap = graphController.getMappingViewList();
//				graphController.setMappingViewList(Collections.synchronizedList(new ArrayList()));
//			}
//			else
//			{//switch
//				GraphModel tempModel = oldModel;
//				oldModel = model;
//				model = tempModel;
//
//				java.util.List tempMap = oldMappingPairCellMap;
//				oldMappingPairCellMap = graphController.getMappingViewList();
//				graphController.setMappingViewList(tempMap);
//			}
//			graph.setModel(model);
//			if (this.inTestMode && (mappings == null) || (mappings.isEmpty()))
//			{
//				initMappingListForTest();
//			}
//			invalidate();
//		}
//	}

	public void paintComponent(Graphics g)
	{
//        System.out.println("test.gov.nih.nci.hl7.jgraph.MiddlePanel.paintComponent");
		super.paintComponent(g);
		if (mappingPanel == null)
		{
			return;// || mappingPanel.getMappings() == null) return;
		}

		//clear those roots before repaint
//		Object[] roots = graph.getRoots();
//		if(roots!=null)
//		{
//			model.remove(roots);
//		}

//		java.util.List mappingsList = null;
//		if (isInTestMode())
//		{
//			mappingsList = this.mappings;
//		}
//		else
//		{
//			mappingsList = mappingPanel.getMappings();
//		}
		graphController.renderInJGraph(g);
	}

//	/**
//	 * Used in initMappingListForTest()
//	 * @param tree
//	 * @return
//	 */
//	private java.util.List filterLeafNodes(JTree tree)
//	{
//		java.util.List leafNodeList = new ArrayList();
//		int rows = tree.getRowCount();
//		for (int i = 0; i < rows; i++)
//		{
//			DefaultMutableTreeNode sourceNode = (DefaultMutableTreeNode) tree.getPathForRow(i).getLastPathComponent();
//			if (sourceNode.isLeaf())
//			{
//				leafNodeList.add(sourceNode);
//			}
//		}
//		return leafNodeList;
//	}

//	private void initMappingListForTest()
//	{
//		if (mappingPanel == null) return;
//
//		BaseTree sourceTree = (BaseTree) mappingPanel.getSourceScrollPane().getViewport().getView();
//		if (sourceTree == null)
//		{
//			return;
//		}
//		//sourceTree.expandAll();
////		int sourceTreeRows = sourceTree.getRowCount();
//
//		BaseTree targetTree = (BaseTree) mappingPanel.getTargetScrollPane().getViewport().getView();
//		if (targetTree == null)
//		{
//			return;
//		}
//		//targetTree.expandAll();
////		int targetTreeRows = targetTree.getRowCount();
//
//		long startTime = 0;
//		long endTime = 0;
//
//		java.util.List sourceTreeLeafNodeList = filterLeafNodes(sourceTree);
//		java.util.List targetTreeLeafNodeList = filterLeafNodes(targetTree);
//		int sourceTreeRows = sourceTreeLeafNodeList.size();
//		int targetTreeRows = targetTreeLeafNodeList.size();
//		int minRows = Math.min(sourceTreeRows, targetTreeRows);
//		mappings = new ArrayList();
//
//		//map only those leaf nodes
//
//		startTime = System.currentTimeMillis();
//		for (int i = 0; i < minRows; i++)
//		{
////			DefaultMutableTreeNode sourceNode = (DefaultMutableTreeNode) sourceTree.getPathForRow(i).getLastPathComponent();
////			DefaultMutableTreeNode targetNode = (DefaultMutableTreeNode) targetTree.getPathForRow(i).getLastPathComponent();
//			DefaultMutableTreeNode sourceNode = (DefaultMutableTreeNode) sourceTreeLeafNodeList.get(i);
//			DefaultMutableTreeNode targetNode = (DefaultMutableTreeNode) targetTreeLeafNodeList.get(i);
//			TreeNodeMapping mappingNode = new TreeNodeMapping(sourceNode, targetNode);
//			mappings.add(mappingNode);
//		}
//
////		JTree nextSource=null, nextTarget=null;
////		if(sourceTreeRows > targetTreeRows)
////		{
////			nextSource = sourceTree;
////			nextTarget = targetTree;
////		}
////		else if(targetTreeRows > sourceTreeRows)
////		{
////			nextSource = targetTree;
////			nextTarget = sourceTree;
////		}
//
//		java.util.List nextSourceList = null, nextTargetList = null;
//		if (sourceTreeRows > targetTreeRows)
//		{
//			nextSourceList = sourceTreeLeafNodeList;
//			nextTargetList = targetTreeLeafNodeList;
//		}
//		else if (targetTreeRows > sourceTreeRows)
//		{
//			nextSourceList = targetTreeLeafNodeList;
//			nextTargetList = sourceTreeLeafNodeList;
//		}
//
//		if (nextSourceList != null)
//		{//i.e. source and target does not have the same number of nodes.
//			int maxRows = Math.max(sourceTreeRows, targetTreeRows);
//			for (int i = minRows; i < maxRows; i++)
//			{
////				DefaultMutableTreeNode sourceNode = (DefaultMutableTreeNode) nextSource.getPathForRow(i).getLastPathComponent();
////				//the smaller number of nodes, using i mod minRows, thus it will have duplicated mappings
////				DefaultMutableTreeNode targetNode = (DefaultMutableTreeNode) nextTarget.getPathForRow(i%minRows).getLastPathComponent();
//				DefaultMutableTreeNode sourceNode = (DefaultMutableTreeNode) nextSourceList.get(i);
//				DefaultMutableTreeNode targetNode = (DefaultMutableTreeNode) nextTargetList.get(i % minRows);
//				TreeNodeMapping mappingNode = new TreeNodeMapping(sourceNode, targetNode);
//				mappings.add(mappingNode);
//			}
//		}
//		endTime = System.currentTimeMillis();
//		int mappingSize = mappings.size();
//		UIHelper.timeMessage("Construction of Mapping of " + sourceTreeRows + " nodes in source and " + targetTreeRows + " nodes in target and mapping size '" + mappingSize + "'", endTime - startTime);
//	}

//	public void actionPerformed(ActionEvent e)
//	{
//		String actionCommand = e.getActionCommand();
//		if (ADD_FUNCTION_COMMAND.equals(actionCommand))
//		{
//			Object function = functionComboBox.getSelectedItem();
//			graphController.addFunction(function);
//		}
//		else
//		{
//			System.out.println(getClass().getName() + " don't understand command '" + actionCommand + "'.");
//		}
//	}

    public Object getFunctionBoxSelection()
	{
		return mappingPanel.getFunctionPane().getFunctionSelection();
	}

	public void setPreferredSize(Dimension preferredSize)
	{
//		System.out.println("MiddlePanel's setPreferredSize()");
		graph.setPreferredSize(new Dimension(preferredSize.width - 4, preferredSize.height - 25));
	}

	/**
	 * Return the middlePanelDropTransferHandler.
	 * @return the middlePanelDropTransferHandler.
	 */
	public MiddlePanelJGraphDropTargetHandler getMiddlePanelDropTransferHandler()
	{
		return middlePanelDropTransferHandler;
	}

	public MiddlePanelScrollAdjustmentCoordinator getAdjustmentCoordinator()
	{
		return adjustmentCoordinator;
	}

	public MiddlePanelJGraphScrollAdjustmentAdapter getGraphAdjustmentAdapter()
	{
		return graphAdjustmentAdapter;
	}

	public MappingPanel getMappingPanel()
	{
		return mappingPanel;
	}
}
/**
 * HISTORY      : $Log: not supported by cvs2svn $
 * HISTORY      : Revision 1.30  2006/08/02 18:44:23  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.29  2006/01/03 19:16:52  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.28  2006/01/03 18:56:25  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.27  2005/12/29 23:06:14  jiangsc
 * HISTORY      : Changed to latest project name.
 * HISTORY      :
 * HISTORY      : Revision 1.26  2005/12/14 21:37:17  jiangsc
 * HISTORY      : Updated license information
 * HISTORY      :
 * HISTORY      : Revision 1.25  2005/11/29 16:23:54  jiangsc
 * HISTORY      : Updated License
 * HISTORY      :
 * HISTORY      : Revision 1.24  2005/11/23 19:48:52  jiangsc
 * HISTORY      : Enhancement on mapping validations.
 * HISTORY      :
 * HISTORY      : Revision 1.23  2005/11/09 23:05:51  jiangsc
 * HISTORY      : Back to previous version.
 * HISTORY      :
 * HISTORY      : Revision 1.21  2005/10/25 22:00:42  jiangsc
 * HISTORY      : Re-arranged system output strings within UI packages.
 * HISTORY      :
 * HISTORY      : Revision 1.20  2005/10/21 15:11:55  jiangsc
 * HISTORY      : Resolve scrolling issue.
 * HISTORY      :
 * HISTORY      : Revision 1.19  2005/10/20 22:29:29  jiangsc
 * HISTORY      : Resolve scrolling issue.
 * HISTORY      :
 * HISTORY      : Revision 1.18  2005/10/20 20:31:50  jiangsc
 * HISTORY      : to Scroll consistently for source, target, and map panel on the MappingPanel.
 * HISTORY      :
 * HISTORY      : Revision 1.17  2005/10/18 17:01:03  jiangsc
 * HISTORY      : Changed one function signature in DragDrop component;
 * HISTORY      : Enhanced drag-drop status monitoring in MappingPanel;
 * HISTORY      :
 * HISTORY      : Revision 1.16  2005/10/18 15:46:19  jiangsc
 * HISTORY      : Added scroll pane to allow more vertical visibility.
 * HISTORY      :
 * HISTORY      : Revision 1.15  2005/08/04 22:22:17  jiangsc
 * HISTORY      : Updated license and class header information.
 * HISTORY      :
 */
