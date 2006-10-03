/**
 * <!-- LICENSE_TEXT_START -->
 * $Header: /share/content/gforge/formbuilder/hl7messagegenerator/config/caadapter/src/gov/nih/nci/hl7/ui/map/BaseTree.java,v 1.1 2006-10-03 17:38:43 marwahah Exp $
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

import gov.nih.nci.hl7.ui.tree.AutoscrollableTree;

import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.tree.TreePath;
import java.awt.*;

/**
 * This class provides some abstract implementations of commonly used tree features in this application.
 *
 * @author OWNER: Scott Jiang
 * @author LAST UPDATE $Author: marwahah $
 * @version Since caAdapter v1.2
 *          revision    $Revision: 1.1 $
 *          date        $Date: 2006-10-03 17:38:43 $
 */
public abstract class BaseTree extends AutoscrollableTree implements TreeExpansionListener
{
//	private DefaultMappingTreeCellRender c = null;
	private MappingPanel mappingPanel = null;

	public BaseTree(MappingPanel m)
	{
		this.mappingPanel = m;
		this.setCellRenderer(new DefaultMappingTreeCellRender(this.mappingPanel));
	}

	public MappingPanel getMappingPanel()
	{
		return mappingPanel;
	}

	public void setMappingPanel(MappingPanel mappingPanel)
	{
		this.mappingPanel = mappingPanel;
	}

	public void treeExpanded(TreeExpansionEvent event)
	{
//		System.out.println("BaseTree.TreeExpansionListener.treeExpanded");
//		reCalculateCellWidthUponTreeStructureChange();
		mappingPanel.getMiddlePanel().repaint();
	}

	public void treeCollapsed(TreeExpansionEvent event)
	{
//		System.out.println("BaseTree.TreeExpansionListener.treeCollapsed");
//		reCalculateCellWidthUponTreeStructureChange();
		mappingPanel.getMiddlePanel().repaint();
	}

//	private void reCalculateCellWidthUponTreeStructureChange()
//	{
//		TreeCellRenderer cellRenderer = this.getCellRenderer();
//		System.out.println("cellRenderer of type '" + cellRenderer.getClass().getName() + "'");
//		if (cellRenderer instanceof DefaultMappingTreeCellRender)
//		{
//			int treeWidth = this.getWidth();
//			if (treeWidth > 0)
//			{
//				System.out.println("Tree width=" + treeWidth);
//				DefaultTreeCellRenderer defaultCellRenderer = (DefaultTreeCellRenderer) cellRenderer;
//				Dimension oldDim = defaultCellRenderer.getPreferredSize();
//				if (oldDim != null && oldDim.width < (treeWidth - 5))
//				{
//					Dimension newDim = new Dimension(treeWidth - 5, oldDim.height);
//					System.out.println("new Dimension=" + newDim);
//					defaultCellRenderer.setSize(newDim);
//					defaultCellRenderer.setPreferredSize(newDim);
//				}
//			}
//		}
//	}

	public void redraw()
	{
		mappingPanel.getMiddlePanel().repaint();
	}

	public void expandAll()
	{
		int size = getRowCount();
//		for (int i = 0; i < getRowCount(); i++)
		/**
		 * Insted of using getRowCount() in the for loop, this method will just expand once;
		 * Since expandRow method will change the row count on the fly, using getRowCount() will return different value each time.
		 * To optimize CPU efficiency, just implement expandAll at one level.
		 * See: gov.nih.nci.hl7.ui.tree.actions.TreeExpandAllAction
		 */
		for (int i = 0; i < size; i++)
		{
			expandRow(i);
		}
	}

//	public boolean isTargetMapped(DefaultMutableTreeNode d)
//	{
//		for (int i = 0; i < mappingPanel.getMappings().size(); i++)
//		{
//			if (d == mappingPanel.getMappings().get(i).getTargetNode())
//				return true;
//		}
//		return false;
//	}
//
//	public void removeMap(DefaultMutableTreeNode d)
//	{
//		for (int i = 0; i < mappingPanel.getMappings().size(); i++)
//		{
//			if (d == mappingPanel.getMappings().get(i).getTargetNode())
//				mappingPanel.getMappings().remove(i);
//		}
//	}

	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
//		reCalculateCellWidthUponTreeStructureChange();
//		super.paintComponent(g);
//		System.out.println("BaseTree.paintComponent");
		mappingPanel.getMiddlePanel().repaint();
	}

	protected abstract void loadData();

	/**
	 * If any of the ancestor nodes are collapsed,
	 * returns path to the first visible ancestor.
	 *
	 * @param path
	 * @return path to the first visible ancestor
	 */
	public TreePath getFirstVisibleAncestor(TreePath path)
	{
		/*if(null == path) {
		System.out.println("null == path");
		return null;
		}*/
		while (!isVisible(path))
			path = path.getParentPath();
		return path;
	}


}

/**
 * HISTORY      : $Log: not supported by cvs2svn $
 * HISTORY      : Revision 1.17  2006/08/02 18:44:23  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.16  2006/01/03 19:16:52  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.15  2006/01/03 18:56:25  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.14  2005/12/29 23:06:14  jiangsc
 * HISTORY      : Changed to latest project name.
 * HISTORY      :
 * HISTORY      : Revision 1.13  2005/12/14 21:37:17  jiangsc
 * HISTORY      : Updated license information
 * HISTORY      :
 * HISTORY      : Revision 1.12  2005/11/29 16:23:54  jiangsc
 * HISTORY      : Updated License
 * HISTORY      :
 * HISTORY      : Revision 1.11  2005/11/09 23:05:51  jiangsc
 * HISTORY      : Back to previous version.
 * HISTORY      :
 * HISTORY      : Revision 1.8  2005/08/28 19:09:26  jiangsc
 * HISTORY      : Improved GUI Performance on large data loading.
 * HISTORY      :
 * HISTORY      : Revision 1.7  2005/08/17 20:35:43  jiangsc
 * HISTORY      : Removed some comments
 * HISTORY      :
 * HISTORY      : Revision 1.6  2005/08/04 22:22:20  jiangsc
 * HISTORY      : Updated license and class header information.
 * HISTORY      :
 */
