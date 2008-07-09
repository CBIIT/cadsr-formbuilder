/**
 * <!-- LICENSE_TEXT_START -->
 * $Header: /share/content/gforge/formbuilder/hl7messagegenerator/config/caadapter/src/gov/nih/nci/hl7/ui/csv/actions/EditTreeNodeAction.java,v 1.1 2006-10-03 17:38:42 marwahah Exp $
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


package gov.nih.nci.hl7.ui.csv.actions;

import gov.nih.nci.hl7.common.MetaObject;
import gov.nih.nci.hl7.csv.meta.CSVFieldMeta;
import gov.nih.nci.hl7.csv.meta.CSVMeta;
import gov.nih.nci.hl7.csv.meta.CSVSegmentMeta;
import gov.nih.nci.hl7.csv.meta.impl.CSVMetaImpl;
import gov.nih.nci.hl7.ui.csv.CSVPanel;
import gov.nih.nci.hl7.ui.util.DefaultSettings;
import gov.nih.nci.hl7.util.GeneralUtilities;
import gov.nih.nci.hl7.validation.CSVMetaValidator;
import gov.nih.nci.hl7.validation.ValidatorResults;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 * This class defines the edit tree node action.
 * @author OWNER: Scott Jiang
 * @author LAST UPDATE $Author: marwahah $
 * @version Since caAdapter v1.2
 *          revision    $Revision: 1.1 $
 *          date        $Date: 2006-10-03 17:38:42 $
 */
public class EditTreeNodeAction extends AbstractCSVContextCRUDAction
{
	private static final String COMMAND_NAME = "Edit";
	private static final Character COMMAND_MNEMONIC = new Character('E');
	private static final KeyStroke ACCELERATOR_KEY_STROKE = KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0, false);

	private transient JTree tree;

	/**
	 * Defines an <code>Action</code> object with a default
	 * description string and default icon.
	 */
	public EditTreeNodeAction(CSVPanel parentPanel)
	{
		this(COMMAND_NAME, parentPanel);
	}

	/**
	 * Defines an <code>Action</code> object with the specified
	 * description string and a default icon.
	 */
	public EditTreeNodeAction(String name, CSVPanel parentPanel)
	{
		this(name, null, parentPanel);
	}

	/**
	 * Defines an <code>Action</code> object with the specified
	 * description string and a the specified icon.
	 */
	public EditTreeNodeAction(String name, Icon icon, CSVPanel parentPanel)
	{
		super(name, icon, parentPanel);
		setMnemonic(COMMAND_MNEMONIC);
		setAcceleratorKey(ACCELERATOR_KEY_STROKE);
		setActionCommandType(DOCUMENT_ACTION_TYPE);
	}

	private JTree getTree()
	{
		if(this.tree==null)
		{
			this.tree = parentPanel.getTree();
		}
		return this.tree;
	}

	/**
	 * Invoked when an action occurs.
	 */
	protected boolean doAction(ActionEvent e)
	{
//		Log.logInfo(this, "EditTreeNodeAction is called.");
		super.doAction(e);
		if(!isSuccessfullyPerformed())
		{
			return false;
		}
		//only support single select.
		TreePath treePath = getTree().getSelectionPath();
		if(treePath==null)
		{
			JOptionPane.showMessageDialog(tree.getRootPane().getParent(), "Tree has no selection",
					"No Selection", JOptionPane.WARNING_MESSAGE);
			setSuccessfullyPerformed(false);
			return false;
		}
		DefaultMutableTreeNode targetNode = (DefaultMutableTreeNode) treePath.getLastPathComponent();
		MetaObject userObject = (MetaObject) targetNode.getUserObject();
		String currentValue = userObject.getName();
		String inputValue = getValidatedUserInput(userObject);
		// inputValue is null if the user hits cancel.
		if (inputValue != null && !GeneralUtilities.areEqual(inputValue, currentValue))
		{
			userObject.setName(inputValue);
			TreeModel treeModel = tree.getModel();
			if(treeModel instanceof DefaultTreeModel)
			{
				((DefaultTreeModel)treeModel).nodeChanged(targetNode);
			}
		}
		setSuccessfullyPerformed(true);
		return isSuccessfullyPerformed();
	}

	/**
	 * Return a valid user input after validated, or null or empty string if user cancelled the action.
	 * @param segmentOrFieldMeta
	 * @return a valid user input after validated, or null or empty string if user cancelled the action.
	 */
	private String getValidatedUserInput(MetaObject segmentOrFieldMeta)
	{
		String newInputValue = null;
		CSVMeta rootMeta = parentPanel.getCSVMeta(false);
		String currentValue = segmentOrFieldMeta.getName();
		String cosmeticName = (DefaultSettings.getClassNameWithoutPackage(segmentOrFieldMeta.getClass()).toLowerCase().indexOf("segment") != -1) ? "Segment" : "Field";
		do
		{
			newInputValue = (String) JOptionPane.showInputDialog(parentPanel,
					"Edit a " + cosmeticName + " name", COMMAND_NAME, JOptionPane.INFORMATION_MESSAGE, null, null, currentValue);
			if (GeneralUtilities.isBlank(newInputValue))
			{
//				Log.logInfo(this, "user may cancelled the input");
				break;
			}
			else
			{
				if (rootMeta == null)
				{//hope this section of code never is called.
					System.err.println("WARNING: CSV Root Meta is null!");
					rootMeta = new CSVMetaImpl();
//					rootMeta.setRootSegment(segmentOrFieldMeta);
					parentPanel.setCsvMeta(rootMeta);
				}
				//change only for validation purpose
				segmentOrFieldMeta.setName(newInputValue);

//				CSVMetaValidator validator = new CSVMetaValidator(rootMeta);
				ValidatorResults validatorResults = new ValidatorResults();
				if(segmentOrFieldMeta instanceof CSVSegmentMeta)
				{
					//per meeting discussion on defect #164, will only validate the name not the whole CSV tree.
					validatorResults.addValidatorResults(CSVMetaValidator.validateSegmentMetaName((CSVSegmentMeta) segmentOrFieldMeta));

//					//Check if 2 or more segments with same name in SCM.
//					validatorResults.addValidatorResults(validator.ScmRule1());
//					//Check if it is ALLCAPS.
//					validatorResults.addValidatorResults(validator.ScmRule4());
				}
				else if(segmentOrFieldMeta instanceof CSVFieldMeta)
				{
					//per meeting discussion on defect #164, will only validate the name not the whole CSV tree.
					validatorResults.addValidatorResults(CSVMetaValidator.validateFieldMetaName((CSVFieldMeta)segmentOrFieldMeta));

//					//Check if 2 or more fields with same name in same segment in SCM (case-insensitive).
//					validatorResults.addValidatorResults(validator.ScmRule2());
//					//Check if field with default field name in SCM.
//					validatorResults.addValidatorResults(validator.ScmRule7());
//					//Field name valiation
//					validatorResults.addValidatorResults(validator.ScmRule8());
				}

				//clean up after validation purpose
				segmentOrFieldMeta.setName(currentValue);

				if (validatorResults.getAllMessages().size() == 0)
				{
					break;
				}
				displayValidationResults(validatorResults);
			}
		}
		while (true);
		return newInputValue;
	}

}
/**
 * HISTORY      : $Log: not supported by cvs2svn $
 * HISTORY      : Revision 1.19  2006/08/02 18:44:21  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.18  2006/01/03 19:16:52  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.17  2006/01/03 18:56:24  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.16  2005/12/29 23:06:12  jiangsc
 * HISTORY      : Changed to latest project name.
 * HISTORY      :
 * HISTORY      : Revision 1.15  2005/12/29 15:39:05  chene
 * HISTORY      : Optimize imports
 * HISTORY      :
 * HISTORY      : Revision 1.14  2005/12/14 21:37:17  jiangsc
 * HISTORY      : Updated license information
 * HISTORY      :
 * HISTORY      : Revision 1.13  2005/11/29 16:23:56  jiangsc
 * HISTORY      : Updated License
 * HISTORY      :
 * HISTORY      : Revision 1.12  2005/11/01 23:09:31  jiangsc
 * HISTORY      : UI Enhancement
 * HISTORY      :
 * HISTORY      : Revision 1.11  2005/10/31 21:31:52  jiangsc
 * HISTORY      : Fix to Defect 164 and 162.
 * HISTORY      :
 * HISTORY      : Revision 1.10  2005/10/25 22:00:42  jiangsc
 * HISTORY      : Re-arranged system output strings within UI packages.
 * HISTORY      :
 * HISTORY      : Revision 1.9  2005/10/24 19:09:40  jiangsc
 * HISTORY      : Implement some validation upon CRUD.
 * HISTORY      :
 * HISTORY      : Revision 1.8  2005/10/19 18:51:24  jiangsc
 * HISTORY      : Re-engineered Action calling sequence.
 * HISTORY      :
 * HISTORY      : Revision 1.7  2005/10/18 13:35:26  umkis
 * HISTORY      : no message
 * HISTORY      :
 * HISTORY      : Revision 1.6  2005/08/23 19:11:29  jiangsc
 * HISTORY      : action status update
 * HISTORY      :
 * HISTORY      : Revision 1.5  2005/08/22 17:16:03  jiangsc
 * HISTORY      : Enhanced the display on edit dialog
 * HISTORY      :
 * HISTORY      : Revision 1.4  2005/08/22 16:02:39  jiangsc
 * HISTORY      : Work on Add Field/Segment
 * HISTORY      :
 * HISTORY      : Revision 1.3  2005/07/22 20:53:09  jiangsc
 * HISTORY      : Structure change and added License and history anchors.
 * HISTORY      :
 */