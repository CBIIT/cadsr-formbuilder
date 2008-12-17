/**
 * <!-- LICENSE_TEXT_START -->
 * $Header: /share/content/gforge/formbuilder/hl7messagegenerator/config/caadapter/src/gov/nih/nci/hl7/ui/jgraph/MiddlePanelJGraphScrollAdjustmentAdapter.java,v 1.1 2006-10-03 17:38:43 marwahah Exp $
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


package gov.nih.nci.hl7.ui.jgraph;

import gov.nih.nci.hl7.ui.map.MiddlePanel;
import gov.nih.nci.hl7.util.Log;

import javax.swing.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

/**
 * This class defines ...
 *
 * @author OWNER: Scott Jiang
 * @author LAST UPDATE $Author: marwahah $
 * @version Since caAdapter v1.2
 *          revision    $Revision: 1.1 $
 *          date        $Date: 2006-10-03 17:38:43 $
 */
public class MiddlePanelJGraphScrollAdjustmentAdapter implements AdjustmentListener
{
	/**
	 * Logging constant used to identify source of log entry, that could be later used to create
	 * logging mechanism to uniquely identify the logged class.
	 */
	private static final String LOGID = "$RCSfile: MiddlePanelJGraphScrollAdjustmentAdapter.java,v $";

	/**
	 * String that identifies the class version and solves the serial version UID problem.
	 * This String is for informational purposes only and MUST not be made final.
	 *
	 * @see <a href="http://www.visi.com/~gyles19/cgi-bin/fom.cgi?file=63">JBuilder vice javac serial version UID</a>
	 */
	public static String RCSID = "$Header: /share/content/gforge/formbuilder/hl7messagegenerator/config/caadapter/src/gov/nih/nci/hl7/ui/jgraph/MiddlePanelJGraphScrollAdjustmentAdapter.java,v 1.1 2006-10-03 17:38:43 marwahah Exp $";

	private MiddlePanel middlePanel;

	private boolean inScrollingMode = false;

	public boolean isInScrollingMode()
	{
		return inScrollingMode;
	}

	public MiddlePanelJGraphScrollAdjustmentAdapter(MiddlePanel middlePanel)
	{
		this.middlePanel = middlePanel;
	}

	/**
	 * Invoked when the value of the adjustable has changed.
	 */
	public void adjustmentValueChanged(AdjustmentEvent e)
	{
//		if(middlePanel!=null)
//		{
//			middlePanel.repaint();
//		}

		if(middlePanel.getAdjustmentCoordinator().isInScrollingMode())
		{//ignore if the other one is scrolling mode.
			return;
		}
		//start scrolling
		inScrollingMode = true;

		JScrollBar localScrollBar = (JScrollBar) e.getSource();
		int localMin = localScrollBar.getMinimum();
		int localMax = localScrollBar.getMaximum();
		int scrollValue = e.getValue();
		double ratio = ((double) scrollValue) / ((double) (localMax - localMin));
		scrollAffectedScrollPane(middlePanel.getMappingPanel().getSourceScrollPane(), ratio);
		scrollAffectedScrollPane(middlePanel.getMappingPanel().getTargetScrollPane(), ratio);

		//end scrolling
		inScrollingMode = false;
	}

	private void scrollAffectedScrollPane(JScrollPane affectedScrollPane, double ratio)
	{
		if (affectedScrollPane == null)
		{
			return;
		}
		JScrollBar verticalBar = affectedScrollPane.getVerticalScrollBar();
		if (verticalBar == null || !verticalBar.isVisible())
		{
			return;
		}
		int localMin = verticalBar.getMinimum();
		int localMax = verticalBar.getMaximum();

		int scrollValue = (int) (((double) (localMax - localMin)) * ratio);
		Log.logInfo(this, "MiddlePanelJGraphScrollAdjustmentAdapter: affected scroll min: '" + localMin + "',max='" + localMax + "',scrollValue='" + scrollValue + "'.");
		verticalBar.setValue(scrollValue);
	}

}
/**
 * HISTORY      : $Log: not supported by cvs2svn $
 * HISTORY      : Revision 1.11  2006/08/02 18:44:23  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.10  2006/01/03 19:16:52  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.9  2006/01/03 18:56:25  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.8  2005/12/29 23:06:17  jiangsc
 * HISTORY      : Changed to latest project name.
 * HISTORY      :
 * HISTORY      : Revision 1.7  2005/12/29 15:39:06  chene
 * HISTORY      : Optimize imports
 * HISTORY      :
 * HISTORY      : Revision 1.6  2005/12/14 21:37:19  jiangsc
 * HISTORY      : Updated license information
 * HISTORY      :
 * HISTORY      : Revision 1.5  2005/11/29 16:23:54  jiangsc
 * HISTORY      : Updated License
 * HISTORY      :
 * HISTORY      : Revision 1.4  2005/11/09 23:05:51  jiangsc
 * HISTORY      : Back to previous version.
 * HISTORY      :
 * HISTORY      : Revision 1.2  2005/10/25 22:00:42  jiangsc
 * HISTORY      : Re-arranged system output strings within UI packages.
 * HISTORY      :
 * HISTORY      : Revision 1.1  2005/10/20 20:31:49  jiangsc
 * HISTORY      : to Scroll consistently for source, target, and map panel on the MappingPanel.
 * HISTORY      :
 */