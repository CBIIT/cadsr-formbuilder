/**
 * <!-- LICENSE_TEXT_START -->
 * $Header: /share/content/gforge/formbuilder/hl7messagegenerator/config/caadapter/src/gov/nih/nci/hl7/ui/map/DefaultMappingTreeCellRender.java,v 1.1 2006-10-03 17:38:43 marwahah Exp $
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

import gov.nih.nci.hl7.ui.util.DefaultSettings;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

/**
 * The class defines the default tree cell renderer for mapping panel.
 *
 * @author OWNER: Scott Jiang
 * @author LAST UPDATE $Author: marwahah $
 * @version Since caAdapter v1.2
 *          revision    $Revision: 1.1 $
 *          date        $Date: 2006-10-03 17:38:43 $
 */
public class DefaultMappingTreeCellRender extends DefaultTreeCellRenderer //extends JPanel implements TreeCellRenderer
{
	private static Font basicFont = new Font(null, Font.PLAIN, 12);
	private static Font boldFont = new Font(null, Font.BOLD, 12);
	private static Font italicFont = new Font(null, Font.ITALIC, 12);

	private static final Color ALTERNATE_COLOR = new Color(220, 220, 220);

//	private JLabel iconPanel = new JLabel();
//	private JLabel textLabel = new JLabel();
//	private DefaultTreeCellRenderer defaultTreeCell = new DefaultTreeCellRenderer();

	private MappingPanel mappingPanel;
	private static ImageIcon pseudoRootIcon = new ImageIcon(DefaultSettings.getImage("pseudo_root.gif"));

	private boolean inChange;

	public DefaultMappingTreeCellRender(MappingPanel mappingPanel)
	{
		super();
		this.mappingPanel = mappingPanel;
//		initialize();
//		setBackground(Color.LIGHT_GRAY);
	}

//	private void initialize()
//	{
//		this.setLayout(new BorderLayout());
//		textLabel = new JLabel();
//		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEADING));
//		panel.add(textLabel);
//		this.add(panel, BorderLayout.NORTH);
//	}

	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus)
	{
//		DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
		if (!selected)
		{
//			TreePath treePath = new TreePath(node.getPath());
//			int rowNumber = tree.getRowForPath(treePath);
//			System.out.println("isRow==RowNumber?:" + (rowNumber==row));
			if (row % 2 == 0)
			{//even
				//			setBackgroundNonSelectionColor(Color.LIGHT_GRAY);
				setBackgroundNonSelectionColor(ALTERNATE_COLOR);
			}
			else
			{//odd
//				setBackgroundNonSelectionColor(tree.getBackground());
				setBackgroundNonSelectionColor(UIManager.getColor("Tree.textBackground"));
			}
		}
		Component returnValue = super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
		if (value instanceof PseudoRootTreeNode)
		{
			setIcon(pseudoRootIcon);
		}
		return returnValue;
	}

	/**
	 System.out.println("Renderer current Dimension='" + getPreferredSize() + "'.");

	 if(tree!=null && tree.isVisible())
	 {
	 int treeWidth = tree.getWidth();
	 if(treeWidth>0)
	 {
	 System.out.println("Tree width in renderer=" + treeWidth);
	 System.out.println("my size " + getSize());
	 System.out.println("my preferred size " + getPreferredSize());
	 System.out.println("my min size " + getMinimumSize());
	 System.out.println("my max size " + getMaximumSize());
	 Dimension oldDim = this.getPreferredSize();
	 if (oldDim != null && oldDim.width < (treeWidth - 5))
	 {
	 Dimension newDim = new Dimension(treeWidth - 5, oldDim.height);
	 System.out.println("new Dimension=" + newDim);
	 this.setSize(newDim);
	 this.setPreferredSize(newDim);
	 this.repaint();
	 //					this.setMinimumSize(newDim);
	 }
	 }
	 }
	 */

//	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus)
//	{
////		if(inChange)
////		{//don't do duplicate things
////			return this;
////		}
////		super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
//
//		DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
//
////		Container parent = tree.getParent();
////		if(parent==null)
////		{
////			parent = mappingPanel;
////		}
//
////		Dimension parentPanelSize = mappingPanel.getPreferredSize();
////		Dimension mySize = this.getPreferredSize();
////		int height = 20;
////		//in case tree width is not accessible
////		int width = (int) (parentPanelSize.width / 2.5);
////		if(tree!=null && tree.isVisible())
////		{
////			mySize = tree.getPreferredSize();
//////			width = tree.getWidth();
////		}
////		if(mySize!=null)
////		{
////			height = mySize.height;
////			width = mySize.width;//Math.max(mySize.width, width);
////		}
////		Dimension newSize = new Dimension(width, height);
////		setPreferredSize(newSize);
//
////		Rectangle treeRect = tree==null? null : tree.getBounds();
////        Rectangle rect = this.getBounds();
////		if(treeRect!=null && treeRect.width!=0 && treeRect.height!=0)
////		{
////			synchronized(this)
////			{
////				inChange = true;
////				System.out.println("tree bound is not null and rect is '" + treeRect.toString() + "'");
////				Rectangle newRect = new Rectangle(rect.x, rect.y, treeRect.width, rect.height);
//////				this.setBounds(newRect);
////				this.setPreferredSize(new Dimension(treeRect.width, rect.height));
////				inChange = false;
////			}
////		}
//
//		if(!selected)
//		{
//			TreePath treePath = new TreePath(node.getPath());
//			int rowNumber = tree.getRowForPath(treePath);
//			if(rowNumber%2==0)
//			{//even
//	//			setBackgroundNonSelectionColor(Color.LIGHT_GRAY);
//				setBackgroundNonSelectionColor(ALTERNATE_COLOR);
//			}
//			else
//			{//odd
//				setBackgroundNonSelectionColor(tree.getBackground());
//			}
//		}
////		boolean isMapped = false;
////		if(node instanceof MappableNode)
////		{
////			isMapped = ((MappableNode) node).isMapped();
////		}
////
////		if(isMapped)
////		{
////			setFont(boldFont);
////			setForeground(Color.BLACK);
//////			setTextSelectionColor(Color.BLACK);
////		}
////		else
////		{
////			setFont(basicFont);
////			setForeground(Color.BLACK);
//////			setTextSelectionColor(Color.BLACK);
////		}
//		return super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
////		return this;
//	}
}

/**
 //		Object userObject = node==null? "null" : node.getUserObject();
 //		textPanel.setText((userObject==null)? "null" : userObject.toString());

 //		if(node.getAllowsChildren())
 //		{
 //			if(selected)
 //			{
 //				iconPanel.setIcon(defaultTreeCell.getDefaultOpenIcon());
 //			}
 //			else
 //			{
 //				iconPanel.setIcon(defaultTreeCell.getDefaultClosedIcon());
 //			}
 //		}
 //		else
 //		{
 //			iconPanel.setIcon(defaultTreeCell.getDefaultLeafIcon());
 //		}
 *
*/
