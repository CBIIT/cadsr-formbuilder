/**
 * <!-- LICENSE_TEXT_START -->
 * $Header: /share/content/gforge/formbuilder/hl7messagegenerator/config/caadapter/src/gov/nih/nci/hl7/ui/hl7/actions/RegenerateAction.java,v 1.1 2006-10-03 17:38:43 marwahah Exp $
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


package gov.nih.nci.hl7.ui.hl7.actions;

import gov.nih.nci.hl7.ui.MainFrame;
import gov.nih.nci.hl7.ui.context.AbstractContextAction;
import gov.nih.nci.hl7.ui.hl7.HL7Panel;
import gov.nih.nci.hl7.ui.util.DefaultSettings;
import gov.nih.nci.hl7.ui.util.SwingWorker;
import gov.nih.nci.hl7.util.GeneralUtilities;
import gov.nih.nci.hl7.util.MessageResources;
import gov.nih.nci.hl7.validation.Message;
import gov.nih.nci.hl7.validation.ValidatorResults;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

/**
 * This class defines a action to trigger generation of HL7 v3 message again.
 *
 * @author OWNER: Scott Jiang
 * @author LAST UPDATE $Author: marwahah $
 * @version Since caAdapter v1.2
 *          revision    $Revision: 1.1 $
 *          date        $Date: 2006-10-03 17:38:43 $
 */
public class RegenerateAction extends AbstractContextAction
{
	/**
	 * Logging constant used to identify source of log entry, that could be later used to create
	 * logging mechanism to uniquely identify the logged class.
	 */
	private static final String LOGID = "$RCSfile: RegenerateAction.java,v $";

	/**
	 * String that identifies the class version and solves the serial version UID problem.
	 * This String is for informational purposes only and MUST not be made final.
	 *
	 * @see <a href="http://www.visi.com/~gyles19/cgi-bin/fom.cgi?file=63">JBuilder vice javac serial version UID</a>
	 */
	public static String RCSID = "$Header: /share/content/gforge/formbuilder/hl7messagegenerator/config/caadapter/src/gov/nih/nci/hl7/ui/hl7/actions/RegenerateAction.java,v 1.1 2006-10-03 17:38:43 marwahah Exp $";

	public static final String COMMAND_NAME = "Regenerate";
	public static final Character COMMAND_MNEMONIC = new Character('R');
	//	public static final KeyStroke ACCELERATOR_KEY_STROKE = KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0, false);
	private static final String TOOL_TIP_DESCRIPTION = COMMAND_NAME;

	private HL7Panel hl7Panel;

	/**
	 * Defines an <code>Action</code> object with a default
	 * description string and default icon.
	 */
	public RegenerateAction(HL7Panel hl7Panel)
	{
		this(COMMAND_NAME, hl7Panel);
	}

	/**
	 * Defines an <code>Action</code> object with the specified
	 * description string and a default icon.
	 */
	public RegenerateAction(String name, HL7Panel hl7Panel)
	{
		this(name, null, hl7Panel);
	}

	/**
	 * Defines an <code>Action</code> object with the specified
	 * description string and a the specified icon.
	 */
	public RegenerateAction(String name, Icon icon, HL7Panel hl7Panel)
	{
		super(name, icon);
		this.hl7Panel = hl7Panel;
		setAdditionalAttributes();
	}

	protected void setAdditionalAttributes()
	{//override super class's one to plug in its own attributes.
		setMnemonic(COMMAND_MNEMONIC);
		setActionCommandType(DOCUMENT_ACTION_TYPE);
		setShortDescription(TOOL_TIP_DESCRIPTION);
	}

	/**
	 * The abstract function that descendant classes must be overridden to provide customsized handling.
	 *
	 * @param e
	 * @return true if the action is finished successfully; otherwise, return false.
	 */
	protected boolean doAction(ActionEvent e) throws Exception
	{
		SwingWorker worker = new SwingWorker()
		{
			private File dataFile;
			private File mapFile;
			public Object construct()
			{
				try
				{
					GeneralUtilities.setCursorWaiting(hl7Panel);
					final String dataFilePath = hl7Panel.getDataFileNameFieldValue();
					final String mapFilePath = hl7Panel.getMapFileNameFieldValue();
					dataFile = new File(dataFilePath);
					mapFile = new File(mapFilePath);

					hl7Panel.clearDataFromUI();

					setSuccessfullyPerformed(true);
				}
				catch (Throwable e1)
				{
					DefaultSettings.reportThrowableToLogAndUI(this, e1, "", hl7Panel, false, true);
					Message msg = MessageResources.getMessage("GEN0", new Object[]{e1.getMessage()});
					//report the nice to have message
					DefaultSettings.reportThrowableToLogAndUI(this, null, msg.toString(), hl7Panel, true, false);
					setSuccessfullyPerformed(false);
				}
				finally
				{
					//back to normal, in case exception occurred.
					GeneralUtilities.setCursorDefault(hl7Panel);
					afterMathHandling();
				}
				return null;
			}

			public void finished()
			{
				if (!isSuccessfullyPerformed())
				{//no need to proceed further
					return;
				}

				boolean everythingGood = true;
				try
				{
					GeneralUtilities.setCursorWaiting(hl7Panel);
					ValidatorResults validatorResults = null;
					if (dataFile!=null && mapFile!=null && dataFile.exists() && mapFile.exists())
					{
						validatorResults = hl7Panel.generateMappingMessages(dataFile, mapFile);
						everythingGood = handleValidatorResults(validatorResults);
					}
					else
					{
						StringBuffer buf = new StringBuffer();
						if(dataFile==null)
						{
							buf.append("Data file is null!\n");
						}
						if(mapFile==null)
						{
							buf.append("Map file is null!\n");
						}
						if(dataFile!=null && !dataFile.exists())
						{
							buf.append("Data file does not exist!\n");
						}
						if(mapFile!=null && !mapFile.exists())
						{
							buf.append("Map file does not exist!\n");
						}
						everythingGood = false;

						JOptionPane.showMessageDialog(hl7Panel, buf.toString(), "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
				catch(Throwable e1)
				{
					DefaultSettings.reportThrowableToLogAndUI(this, e1, "", hl7Panel, false, true);
					Message msg = MessageResources.getMessage("GEN0", new Object[]{e1.getMessage()});
					//report the nice to have message
					DefaultSettings.reportThrowableToLogAndUI(this, null, msg.toString(), hl7Panel, true, false);
					everythingGood = false;
				}
				finally
				{
					//back to normal.
					GeneralUtilities.setCursorDefault(hl7Panel);
					setSuccessfullyPerformed(everythingGood);
					afterMathHandling();
				}
			}

			private void afterMathHandling()
			{
				if (!isSuccessfullyPerformed())
				{//do the clean up.
					Container container = hl7Panel.getRootContainer();
					if (container instanceof MainFrame)
					{
						MainFrame mainFrame = (MainFrame) container;
						if (hl7Panel != null && mainFrame.hasComponentOfGivenClass(hl7Panel.getClass(), false) != null)
						{
							//						mainFrame.getTabbedPane().remove(mappingPanel);
							//use close action instead of removing it from tabbed directly so as to allow main frame to clean up maps.
							gov.nih.nci.hl7.ui.hl7.actions.CloseAction closeAction = new gov.nih.nci.hl7.ui.hl7.actions.CloseAction(hl7Panel);
							closeAction.actionPerformed(null);
						}
					}//end of instanceof MainFrame
				}
			}
		};
		worker.start();
		return false;
	}

	/**
	 * Return the associated UI component.
	 *
	 * @return the associated UI component.
	 */
	protected Component getAssociatedUIComponent()
	{
		return hl7Panel;
	}
}
/**
 * HISTORY      : $Log: not supported by cvs2svn $
 * HISTORY      : Revision 1.10  2006/08/02 18:44:21  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.9  2006/01/03 19:16:52  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.8  2006/01/03 18:56:24  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.7  2005/12/29 23:06:15  jiangsc
 * HISTORY      : Changed to latest project name.
 * HISTORY      :
 * HISTORY      : Revision 1.6  2005/12/29 15:39:06  chene
 * HISTORY      : Optimize imports
 * HISTORY      :
 * HISTORY      : Revision 1.5  2005/12/14 22:11:56  jiangsc
 * HISTORY      : Updated functionality.
 * HISTORY      :
 * HISTORY      : Revision 1.4  2005/12/12 22:56:25  jiangsc
 * HISTORY      : With enhanced functions.
 * HISTORY      :
 * HISTORY      : Revision 1.3  2005/12/08 23:52:17  jiangsc
 * HISTORY      : minor update
 * HISTORY      :
 * HISTORY      : Revision 1.2  2005/11/29 16:23:55  jiangsc
 * HISTORY      : Updated License
 * HISTORY      :
 * HISTORY      : Revision 1.1  2005/11/14 19:55:51  jiangsc
 * HISTORY      : Implementing UI enhancement
 * HISTORY      :
 */
