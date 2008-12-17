/**
 * <!-- LICENSE_TEXT_START -->
 * $Header: /share/content/gforge/formbuilder/hl7messagegenerator/config/caadapter/src/gov/nih/nci/hl7/ui/nodeloader/SCMMapSourceNodeLoader.java,v 1.1 2006-10-03 17:38:44 marwahah Exp $
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


package gov.nih.nci.hl7.ui.nodeloader;

import gov.nih.nci.hl7.csv.meta.CSVMeta;
import gov.nih.nci.hl7.ui.map.PseudoRootTreeNode;
import gov.nih.nci.hl7.ui.nodeloader.basic.SCMBasicNodeLoader;
import gov.nih.nci.hl7.ui.tree.DefaultSourceTreeNode;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

/**
 * This class helps convert a CSV meta object graph (SCM) into a graph of TreeNodes.
 *
 * @author OWNER: Scott Jiang
 * @author LAST UPDATE $Author: marwahah $
 * @version Since caAdapter v1.2
 *          revision    $Revision: 1.1 $
 *          date        $Date: 2006-10-03 17:38:44 $
 */
public class SCMMapSourceNodeLoader extends SCMBasicNodeLoader
{
	/**
	 * @param userObject
	 * @return a tree node that wraps the user object.
	 * @override the base implementation of this function.
	 * This is an overridable function to allow descendant class to provide
	 * different tree node implementations.
	 */
	protected DefaultMutableTreeNode constructTreeNode(Object userObject)
	{
		return constructTreeNode(userObject, true);
	}

	/**
	 * Overloaded version of the function above.
	 *
	 * @param userObject
	 * @param allowsChildren
	 * @return a tree node that wraps the user object.
	 */
	public DefaultMutableTreeNode constructTreeNode(Object userObject, boolean allowsChildren)
	{
		DefaultSourceTreeNode node = new DefaultSourceTreeNode(userObject, allowsChildren);
		return node;
	}

	/**
	 * Based on the given object type, this function will convert the meta-data tree to a TreeNode-based tree structure, whose root is the returned TreeNode.
	 *
	 * @param o the meta-data object
	 * @return the root node representing the TreeNode structure mapping the given meta-data tree.
	 * @throws gov.nih.nci.hl7.ui.nodeloader.NodeLoader.MetaDataloadException
	 *
	 */
	public TreeNode loadData(Object o) throws MetaDataloadException
	{
		TreeNode resultRoot = null;
		TreeNode realRoot = super.loadData(o);
		if (o instanceof CSVMeta)
		{//construct the pseudo root.
			PseudoRootTreeNode node = new PseudoRootTreeNode("Source Tree", true);
			node.add((MutableTreeNode) realRoot);
			resultRoot = node;
		}
		else
		{
			resultRoot = realRoot;
		}

		return resultRoot;
	}

	/**
	 * Given the node as the root of UI tree structure, this function will traverse the UI tree structure
	 * and construct a user object tree structure and return the root of the meta-data user object tree.
	 *
	 * @param treeNode  the root of the sub-tree to be processed.
	 * @param resetUUID if true, will tell loader to reset UUID field; otherwise, it will keep existing UUID;
	 *                  The reason to have the option is that the original data may come from another CSV metadata file and
	 *                  UUIDs of those data should be re-assigned before being persisted.
	 * @return the root of the meta-data user object tree.
	 * @throws gov.nih.nci.hl7.ui.nodeloader.NodeLoader.MetaDataloadException
	 *
	 */
	public CSVMeta unLoadData(DefaultMutableTreeNode treeNode, boolean resetUUID) throws MetaDataloadException
	{
		DefaultMutableTreeNode realRoot = treeNode;
		if (treeNode instanceof PseudoRootTreeNode)
		{
			realRoot = (DefaultMutableTreeNode) treeNode.getChildAt(0);
		}
		return super.unLoadData(realRoot, resetUUID);
	}
}
/**
 * HISTORY      : $Log: not supported by cvs2svn $
 * HISTORY      : Revision 1.9  2006/08/02 18:44:24  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.8  2006/01/03 19:16:52  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.7  2006/01/03 18:56:25  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.6  2005/12/29 23:06:15  jiangsc
 * HISTORY      : Changed to latest project name.
 * HISTORY      :
 * HISTORY      : Revision 1.5  2005/12/29 15:39:06  chene
 * HISTORY      : Optimize imports
 * HISTORY      :
 * HISTORY      : Revision 1.4  2005/12/14 21:37:18  jiangsc
 * HISTORY      : Updated license information
 * HISTORY      :
 * HISTORY      : Revision 1.3  2005/11/29 16:23:54  jiangsc
 * HISTORY      : Updated License
 * HISTORY      :
 * HISTORY      : Revision 1.2  2005/11/11 19:23:59  jiangsc
 * HISTORY      : Support Pseudo Root in Mapping Panel.
 * HISTORY      :
 * HISTORY      : Revision 1.1  2005/08/11 22:10:36  jiangsc
 * HISTORY      : Open/Save File Dialog consolidation.
 * HISTORY      :
 * HISTORY      : Revision 1.18  2005/08/04 22:22:27  jiangsc
 * HISTORY      : Updated license and class header information.
 * HISTORY      :
 */