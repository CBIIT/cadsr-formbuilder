/**
 * <!-- LICENSE_TEXT_START -->
 * $Header: /share/content/gforge/formbuilder/hl7messagegenerator/config/caadapter/src/gov/nih/nci/hl7/ui/jgraph/MiddlePanelJGraphDropTargetHandler.java,v 1.1 2006-10-03 17:38:43 marwahah Exp $
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

import gov.nih.nci.hl7.function.FunctionMeta;
import gov.nih.nci.hl7.ui.dragdrop.HL7SDKDropCompatibleComponent;
import gov.nih.nci.hl7.ui.dragdrop.HL7SDKDropTargetAdapter;
import gov.nih.nci.hl7.ui.dragdrop.TransferableNode;
import gov.nih.nci.hl7.ui.jgraph.graph.FunctionBoxCell;
import gov.nih.nci.hl7.ui.jgraph.graph.FunctionBoxDefaultPort;
import gov.nih.nci.hl7.ui.map.MappingDataManager;
import gov.nih.nci.hl7.ui.tree.DefaultSourceTreeNode;
import gov.nih.nci.hl7.ui.tree.DefaultTargetTreeNode;
import gov.nih.nci.hl7.ui.tree.MappableNode;
import org.jgraph.JGraph;
import org.jgraph.graph.*;

import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.List;

/**
 * The class defines a customized drop hanlder for middle panel.
 *
 * @author OWNER: Scott Jiang
 * @author LAST UPDATE $Author: marwahah $
 * @version Since caAdapter v1.2
 *          revision    $Revision: 1.1 $
 *          date        $Date: 2006-10-03 17:38:43 $
 */
public class MiddlePanelJGraphDropTargetHandler implements HL7SDKDropCompatibleComponent
{
	//to indicate if current GUI status is in drag-and-drop
	//so as to let controller not update the table contents
	private boolean dragging = false;

	/**
	 * A Reference to the JGraph View it Supports
	 */
	protected JGraph mGraph;
	private MappingDataManager mappingDataMananger;

//	/**
//	 * drag variables:
//	 */
//	private DragSource dragSource;
//	private HL7SDKDragGestureAdapter dragGestureAdapter;
//	private HL7SDKDragSourceAdapter dragSourceAdapter;
//	//default setting
//	private int acceptableDragAction = DnDConstants.ACTION_MOVE; //DnDConstants.ACTION_COPY_OR_MOVE;

	private boolean drawFeedback;

	private DataFlavor[] acceptableDropFlavors = TransferableNode.transferDataFlavors;
	private DataFlavor[] preferredLocalFlavors = {TransferableNode.LOCAL_NODE_FLAVOR};

	// drop variables
	protected int acceptableDropAction = DnDConstants.ACTION_MOVE; //DnDConstants.ACTION_COPY_OR_MOVE;
	protected DropTarget dropTarget;
	protected HL7SDKDropTargetAdapter dropTargetAdapter;

	public MiddlePanelJGraphDropTargetHandler(JGraph mGraph, MappingDataManager mappingDataMananger)
	{
		this(mGraph, mappingDataMananger, DnDConstants.ACTION_MOVE);//DnDConstants.ACTION_MOVE,
	}

	public MiddlePanelJGraphDropTargetHandler(JGraph mGraph, MappingDataManager mappingDataMananger, int acceptableDropAction) //int acceptableDragAction,
	{
		this.mGraph = mGraph;
		this.mappingDataMananger = mappingDataMananger;
//		this.acceptableDragAction = acceptableDragAction;
		this.acceptableDropAction = acceptableDropAction;
		initDragAndDrop();
	}

	public JGraph getGraph()
	{
		return mGraph;
	}

	/**
	 * set up the drag and drop listeners. This must be called
	 * after the constructor.
	 */
	protected void initDragAndDrop()
	{
//		// set up drag stuff
//		this.dragSource = DragSource.getDefaultDragSource();
//		this.dragGestureAdapter = new HL7SDKDragGestureAdapter(this);
//		this.dragSourceAdapter = new HL7SDKDragSourceAdapter(this);
//		// component, action, listener
//		this.dragSource.createDefaultDragGestureRecognizer(this.getGraph(), this.acceptableDragAction, this.dragGestureAdapter);

		//set up drop stuff
		this.dropTargetAdapter = new HL7SDKDropTargetAdapter(this,
				acceptableDropAction,
				acceptableDropFlavors,
				preferredLocalFlavors);

		// component, ops, listener, accepting
		this.dropTarget = new DropTarget(this.getGraph(),
				acceptableDropAction,
				this.dropTargetAdapter,
				true);
		this.dropTarget.setActive(true);
	}

	/**
	 * Called by the DropTargetAdapter in dragEnter, dragOver and
	 * dragActionChanged
	 */
	public void dragUnderFeedback(boolean ok, DropTargetDragEvent e)
	{
		Point2D dropLocation = e.getLocation();
		// Find Cell in Model Coordinates
		Object object = getGraph().getFirstCellForLocation(dropLocation.getX(), dropLocation.getY());
		if (object != null)
		{
			if((object instanceof FunctionBoxDefaultPort) || (object instanceof FunctionBoxCell))
			{//does not highlight edge
				this.getGraph().setSelectionCell(object);
			}
		}
	}

	/**
	 * Called by the DropTargetAdapter in dragEnter, dragOver and
	 * dragActionChanged
	 */
	public boolean isDropOk(DropTargetDragEvent e)
	{
		/**
		 * Design Rationale:
		 * 1) If the about-to-be-dropped data is a function, return true;
		 * 2) If the aimed port has not been mapped, return true;
		 * Otherwise, return false;
		 */
		//check if the enclosed is a function
		Transferable trans = e.getTransferable();
		Object data = getTransferedData(trans);
		if(isDataContainsTargetClassObject(data, FunctionMeta.class) || isDataContainsTargetClassObject(data, DefaultGraphCell.class))
		{
			return true;
		}
        //if not, check other stuff.

		Point2D dropLocation = e.getLocation();
//				PortView result = graph.getPortViewAt(dropLocation.getX(), dropLocation.getY());
		// Find Cell in Model Coordinates
//		Object object = getGraph().getFirstCellForLocation(dropLocation.getX(), dropLocation.getY());
//		Log.logInfo(this, "isDropOK: the cell is of type: '" + (object==null? "null" : object.getClass().getName() + "-" + object.toString() + "'."));
		PortView portView = getGraph().getPortViewAt(dropLocation.getX(), dropLocation.getY());
//		Log.logInfo(this, "isDropOK: the portView is of type: '" + (portView == null ? "null" : portView.getClass().getName() + "-" + portView.toString() + "'."));
		if (portView != null)
		{
			Object portObj = portView.getCell();
			if (portObj instanceof FunctionBoxDefaultPort)
			{
				FunctionBoxDefaultPort port = (FunctionBoxDefaultPort) portObj;
				boolean result = !port.isMapped();
				if(result)
				{//further check if the input matches from source and output matches from target
					List selectionList = ((TransferableNode) data).getSelectionList();
					Object userSelection = null;
					if(selectionList!=null && selectionList.size()>0)
					{
						userSelection = selectionList.get(0);
					}
					result = (userSelection instanceof DefaultSourceTreeNode && port.isInput()) || (userSelection instanceof DefaultTargetTreeNode && !port.isInput());
				}
				return result;
			}
		}
		return false;
//		if (object instanceof FunctionBoxCell)
//		{//accept drop if it is on a functional box.
//			PortView portView = getGraph().getPortViewAt(dropLocation.getX(), dropLocation.getY());
//			if(portView!=null)
//			{
//				Object portObj = portView.getCell();
//				if(portObj instanceof FunctionBoxDefaultPort)
//				{
//					return true;
//				}
//			}
//			return false;
//		}
//		if (object instanceof FunctionBoxDefaultPort)
//		{//accept drop if it is a port of functional box.
//			Log.logInfo(this, "I am a port of type '" + object.getClass().getName() + "'");
//			if (((DefaultPort) object).getParent() instanceof FunctionBoxCell)
//			{
//				return true;
//			}
//			else
//			{
//				return false;
//			}
//		}
//		else
//		{
//			return false;
//		}
	}

	protected boolean isDataContainsTargetClassObject(Object data, Class targetClass)
	{
		if (data instanceof TransferableNode)
		{
			TransferableNode node = (TransferableNode) data;
			List selectionList = node.getSelectionList();
			if (selectionList != null && selectionList.size() > 0)
			{
				int size = selectionList.size();
				for(int i=0; i<size; i++)
				{
					Object selObj = selectionList.get(i);
					if (selObj!=null && targetClass.isAssignableFrom(selObj.getClass()))
					{
						return true;
					}
					if (selObj instanceof DefaultMutableTreeNode)
					{
						Object userObj = ((DefaultMutableTreeNode) selObj).getUserObject();
						if (userObj!=null && targetClass.isAssignableFrom(userObj.getClass()))
						{
							return true;
						}
					}
				}//end of for loop
			}
		}
		return false;
	}

    /**
	 * Obtain transfered data from the wrapping Transferable.
	 * @param trans
	 * @return the transfer data within the transferable
	 */
	protected Object getTransferedData(Transferable trans)
	{
		DataFlavor[] dataFlavors = trans.getTransferDataFlavors();
		Object data = null;
		for(int i=0; i<dataFlavors.length; i++)
		{
			try
			{
				data = trans.getTransferData(dataFlavors[i]);
			}
			catch(Throwable e)
			{//refresh and try next
				data = null;
			}
		}
		return data;
	}

	/**
	 * Called by the DropTargetAdapter in dragExit and drop
	 */
	public void undoDragUnderFeedback()
	{
		this.getGraph().clearSelection();
		this.drawFeedback = false;
	}

	/**
	 * Called by the DropTargetAdapter in drop
	 * return true if add action succeeded
	 * otherwise return false
	 */
	public boolean setDropData(Object transferredData, DropTargetDropEvent e, DataFlavor chosen)
	{
		boolean isSuccess = false;
		Point2D dropLocation = e.getLocation();
		TransferableNode dragSourceObjectSelection = (TransferableNode) transferredData;

		if(isDataContainsTargetClassObject(transferredData, DefaultGraphCell.class))
		{//assume this is just graph cell movement within this application, not support graph cells from other application.
			isSuccess = processGraphCellsMovement(dragSourceObjectSelection, dropLocation);
		}
		else if(isDataContainsTargetClassObject(transferredData, FunctionMeta.class))
		{
			java.util.List dragSourceObjectList = dragSourceObjectSelection.getSelectionList();
			int size = dragSourceObjectList.size();
			boolean errorFree = true;
			for (int i = 0; i < size; i++)
			{
				Object sourceNode = dragSourceObjectList.get(i);
				FunctionMeta userObject = null;
				if(sourceNode instanceof DefaultMutableTreeNode)
				{
					userObject = (FunctionMeta)((DefaultMutableTreeNode)sourceNode).getUserObject();
				}
				else if(sourceNode instanceof FunctionMeta)
				{
					userObject = (FunctionMeta)sourceNode;
				}
				if (errorFree)
				{
					errorFree = mappingDataMananger.addFunction(userObject, dropLocation);
				}
				else
				{
					break;
				}
			}
		}
		else
		{
			// Find Cell in Model Coordinates
//			DefaultGraphCell targetNode = (DefaultGraphCell) getGraph().getFirstCellForLocation(dropLocation.getX(), dropLocation.getY());
			DefaultPort targetNode = (DefaultPort) ((PortView)(getGraph().getPortViewAt(dropLocation.getX(), dropLocation.getY()))).getCell();
//			Log.logInfo(this, "TargetNode of type: '" + (targetNode==null ? "null" : targetNode.toString() + "'"));
//			TransferableNode dragSourceObjectSelection = (TransferableNode) transferredData;
			java.util.List dragSourceObjectList = dragSourceObjectSelection.getSelectionList();
			if (dragSourceObjectList == null || dragSourceObjectList.size() < 1)
			{
	//				DesktopController.showMessage("No Selected Object is found in this Drop action.");
				return false;
			}
			int size = dragSourceObjectList.size();
			boolean errorFree = true;
			for (int i = 0; i < size; i++)
			{
				Object sourceNode = dragSourceObjectList.get(i);
				if (errorFree)
				{
					if (targetNode instanceof MappableNode && sourceNode instanceof MappableNode)
					{
						errorFree = mappingDataMananger.createMapping((MappableNode) sourceNode, (MappableNode) targetNode);
					}
					else
					{
						errorFree = false;
					}
				}
				else
				{
					break;
				}
			}
			isSuccess = errorFree;
		}//end of outer else
		return isSuccess;
	}

	/**
	 * Because when a drag is over a component, a selection listener would be notified
	 * as if there were an item being selected.
	 * These two function will allow DropTargetAdapter to notify the selection listener(s)
	 * of the drop target component if the drag comes or the actual selection occurs
	 */
	public void setInDragDropMode(boolean flag)
	{
		this.dragging = flag;
	}

	public boolean isInDragDropMode()
	{
		return dragging;
	}

	/**
	 * Return the drop target adapter.
	 *
	 * @return the drop target adapter.
	 */
	public HL7SDKDropTargetAdapter getDropTargetAdapter()
	{
		return this.dropTargetAdapter;
	}

	/**
	 * Called by the setDropData() method to solely handle graph move around.
	 * @param transferredData
	 * @param dropLocation
	 * @return true if it handles the request successfully.
	 */
	private boolean processGraphCellsMovement(TransferableNode transferredData, Point2D dropLocation)
	{
		boolean isSuccess = false;
		java.util.List dragSourceObjectList = transferredData.getSelectionList();
		Object[] cells = dragSourceObjectList.toArray(new Object[0]);
		boolean allInModel = isAllCellsInGraphModel(cells);
		CellHandle cellHandle = mGraph.getUI().getHandle();
		if(allInModel &&  cellHandle != null)
		{
			Point p = mGraph.getUI().getInsertionLocation();
//			Log.logInfo(this, "point from insertion location: '" + p + "'");
//			Log.logInfo(this, "point from drop location: '" + dropLocation + "'");
//			cellHandle.mouseReleased(new MouseEvent(mGraph, 0, 0, 0, (int)dropLocation.getX(), (int)dropLocation.getY(), 1, false));
			cellHandle.mouseReleased(new MouseEvent(mGraph, 0, 0, 0, (int)p.getX(), (int)p.getY(), 1, false));
			isSuccess = true;
		}
		return isSuccess;
	}

	private boolean isAllCellsInGraphModel(Object[] cells)
	{
		boolean allInModel = true;
		GraphModel model = mGraph.getModel();
		for (int i = 0; i < cells.length && allInModel; i++)
		{
			allInModel = allInModel && model.contains(cells[i]);
		}
		return allInModel;
	}
}
/**
 * HISTORY      : $Log: not supported by cvs2svn $
 * HISTORY      : Revision 1.21  2006/08/02 18:44:22  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.20  2006/06/13 18:12:12  jiangsc
 * HISTORY      : Upgraded to catch Throwable instead of Exception.
 * HISTORY      :
 * HISTORY      : Revision 1.19  2006/01/26 22:53:15  jiangsc
 * HISTORY      : Fix drap and drop issue on mapping panel.
 * HISTORY      :
 * HISTORY      : Revision 1.18  2006/01/03 19:16:52  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.17  2006/01/03 18:56:25  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.16  2005/12/29 23:06:17  jiangsc
 * HISTORY      : Changed to latest project name.
 * HISTORY      :
 * HISTORY      : Revision 1.15  2005/12/23 16:37:59  jiangsc
 * HISTORY      : no message
 * HISTORY      :
 * HISTORY      : Revision 1.13  2005/12/14 21:37:19  jiangsc
 * HISTORY      : Updated license information
 * HISTORY      :
 * HISTORY      : Revision 1.12  2005/11/29 16:23:54  jiangsc
 * HISTORY      : Updated License
 * HISTORY      :
 * HISTORY      : Revision 1.11  2005/11/11 19:23:59  jiangsc
 * HISTORY      : Support Pseudo Root in Mapping Panel.
 * HISTORY      :
 * HISTORY      : Revision 1.10  2005/10/25 22:00:42  jiangsc
 * HISTORY      : Re-arranged system output strings within UI packages.
 * HISTORY      :
 * HISTORY      : Revision 1.9  2005/10/24 20:31:00  jiangsc
 * HISTORY      : Turned off auto-scroll feature to comprise mapping issue.
 * HISTORY      :
 * HISTORY      : Revision 1.8  2005/10/18 17:01:03  jiangsc
 * HISTORY      : Changed one function signature in DragDrop component;
 * HISTORY      : Enhanced drag-drop status monitoring in MappingPanel;
 * HISTORY      :
 * HISTORY      : Revision 1.7  2005/08/24 22:28:40  jiangsc
 * HISTORY      : Enhanced JGraph implementation;
 * HISTORY      : Save point of CSV and HSM navigation update;
 * HISTORY      :
 * HISTORY      : Revision 1.6  2005/08/04 22:22:11  jiangsc
 * HISTORY      : Updated license and class header information.
 * HISTORY      :
 * HISTORY      : Revision 1.5  2005/07/19 22:28:16  jiangsc
 * HISTORY      : 1) Renamed FunctionalBox to FunctionBox to be consistent;
 * HISTORY      : 2) Added SwingWorker to OpenMapAction;
 * HISTORY      : 3) Save Point for Function Change.
 * HISTORY      :
 * HISTORY      : Revision 1.4  2005/07/15 18:58:52  jiangsc
 * HISTORY      : 1) Reconstucted Menu bars;
 * HISTORY      : 2) Integrated FunctionPane to display property;
 * HISTORY      : 3) Enabled drag and drop functions to mapping panel.
 * HISTORY      :
 */