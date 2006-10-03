/**
 * <!-- LICENSE_TEXT_START -->
 * $Header: /share/content/gforge/formbuilder/hl7messagegenerator/config/caadapter/src/gov/nih/nci/hl7/ui/map/actions/CloseAction.java,v 1.1 2006-10-03 17:38:44 marwahah Exp $
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


package gov.nih.nci.hl7.ui.map.actions;

import gov.nih.nci.hl7.ui.MainContextManager;
import gov.nih.nci.hl7.ui.actions.ActionConstants;
import gov.nih.nci.hl7.ui.context.DefaultContextCloseAction;
import gov.nih.nci.hl7.ui.map.MappingPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * This class defines the close action of Mapping panel.
 *
 * @author OWNER: Scott Jiang
 * @author LAST UPDATE $Author: marwahah $
 * @version Since caAdapter v1.2
 *          revision    $Revision: 1.1 $
 *          date        $Date: 2006-10-03 17:38:44 $
 */
public class CloseAction extends DefaultContextCloseAction //implements ContextManagerClient
{
//	private static final String COMMAND_NAME = ActionConstants.CLOSE;
//	private static final Character COMMAND_MNEMONIC = new Character('C');
//	private static final KeyStroke ACCELERATOR_KEY_STROKE = KeyStroke.getKeyStroke(KeyEvent.VK_F4, Event.CTRL_MASK, false);

//	private transient MappingPanel mappingPanel;
//    private transient Map menuMap;

	public CloseAction(MappingPanel mappingPanel)
	{
		this(COMMAND_NAME, mappingPanel);
	}

	public CloseAction(String name, MappingPanel mappingPanel)
	{
		this(name, null, mappingPanel);
	}

	public CloseAction(String name, Icon icon, MappingPanel mappingPanel)
	{
		super(name, icon, mappingPanel);
//		this.mappingPanel = mappingPanel;
	}

//	protected void setAdditionalAttributes()
//	{//override super class's one to plug in its own attributes.
//		setMnemonic(COMMAND_MNEMONIC);
//		setAcceleratorKey(ACCELERATOR_KEY_STROKE);
//		setActionCommandType(DOCUMENT_ACTION_TYPE);
//	}

	/**
	 * Descendant class could override this method to provide actions to be executed after the
	 * given action is performed, such as update menu status, etc.
	 *
	 * @param e
	 * @return if the action has been executed successfully.
	 */
	protected boolean postActionPerformed(ActionEvent e)
	{
		if(mainFrame!=null)
		{
			MainContextManager cm = mainFrame.getMainContextManager();
			cm.enableAction(ActionConstants.OPEN_MAP_FILE, true);
			cm.enableAction(ActionConstants.NEW_MAP_FILE, true);
		}
		return true;
	}
//	/**
//	 * Indicate whether or not it is changed.
//	 */
//	public boolean isChanged()
//	{
//		return false;  //To change body of implemented methods use File | Settings | File Templates.
//	}
//
//	public Map getMenuItems(String menu_name)
//	{
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
//		}
//		Action act;
//		if (MenuConstants.FILE_MENU_NAME.equals(menu_name))
//		{
//			act = new OpenMapAction(mainFrame);
//			actionMap.put(ActionConstants.OPEN_MAP_FILE, act);
//
//			act = new NewMapAction(mainFrame);
//			actionMap.put(ActionConstants.NEW_MAP_FILE,  act);
//		}
//        return actionMap;
//    }
//
//	/**
//	 * return the close action inherited with this client.
//	 */
//	public Action getDefaultCloseAction()
//	{
//		return this;  //To change body of implemented methods use File | Settings | File Templates.
//	}
}
/**
 * HISTORY      : $Log: not supported by cvs2svn $
 * HISTORY      : Revision 1.16  2006/08/02 18:44:23  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.15  2006/01/03 19:16:52  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.14  2006/01/03 18:56:25  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.13  2005/12/29 23:06:15  jiangsc
 * HISTORY      : Changed to latest project name.
 * HISTORY      :
 * HISTORY      : Revision 1.12  2005/12/14 21:37:18  jiangsc
 * HISTORY      : Updated license information
 * HISTORY      :
 * HISTORY      : Revision 1.11  2005/11/29 16:23:54  jiangsc
 * HISTORY      : Updated License
 * HISTORY      :
 * HISTORY      : Revision 1.10  2005/10/19 18:51:24  jiangsc
 * HISTORY      : Re-engineered Action calling sequence.
 * HISTORY      :
 * HISTORY      : Revision 1.9  2005/07/27 22:41:12  jiangsc
 * HISTORY      : Consolidated context sensitive menu implementation.
 * HISTORY      :
 * HISTORY      : Revision 1.4  2005/07/22 20:53:08  jiangsc
 * HISTORY      : Structure change and added License and history anchors.
 * HISTORY      :
 */
