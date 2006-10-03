/**
 * <!-- LICENSE_TEXT_START -->
 * $Header: /share/content/gforge/formbuilder/hl7messagegenerator/config/caadapter/src/gov/nih/nci/hl7/ui/jgraph/functions/FunctionBoxViewUsageManager.java,v 1.1 2006-10-03 17:38:43 marwahah Exp $
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


package gov.nih.nci.hl7.ui.jgraph.functions;

import gov.nih.nci.hl7.function.FunctionConstant;
import gov.nih.nci.hl7.function.FunctionMeta;
import gov.nih.nci.hl7.map.components.FunctionComponent;
import gov.nih.nci.hl7.ui.map.FunctionConstantDefinitionDialog;
import gov.nih.nci.hl7.ui.util.DefaultSettings;
import gov.nih.nci.hl7.util.GeneralUtilities;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * This class may look similar to FunctionalBoxManager, but it is solely designated
 * to support MiddlePanelJGraphController to manage the list of function box usages.
 *
 * Therefore, the implementation of FunctionalBoxManager is singleton while this class is
 * instance oriented, as each instance targets to one instance of mapping.
 *
 * @author OWNER: Scott Jiang
 * @author LAST UPDATE $Author: marwahah $
 * @version Since caAdapter v1.2
 *          revision    $Revision: 1.1 $
 *          date        $Date: 2006-10-03 17:38:43 $
 */
public class FunctionBoxViewUsageManager
{
	/**
	 * Logging constant used to identify source of log entry, that could be later used to create
	 * logging mechanism to uniquely identify the logged class.
	 */
	private static final String LOGID = "$RCSfile: FunctionBoxViewUsageManager.java,v $";

	/**
	 * String that identifies the class version and solves the serial version UID problem.
	 * This String is for informational purposes only and MUST not be made final.
	 *
	 * @see <a href="http://www.visi.com/~gyles19/cgi-bin/fom.cgi?file=63">JBuilder vice javac serial version UID</a>
	 */
	public static String RCSID = "$Header: /share/content/gforge/formbuilder/hl7messagegenerator/config/caadapter/src/gov/nih/nci/hl7/ui/jgraph/functions/FunctionBoxViewUsageManager.java,v 1.1 2006-10-03 17:38:43 marwahah Exp $";

	//key: uuid of function instance, function instance.
	private Map functionInstanceMap;

	public FunctionBoxViewUsageManager()
	{
		clear();
	}

	public void clear()
	{
		this.functionInstanceMap = Collections.synchronizedMap(new HashMap());
	}

	/**
	 * This function is expected to be called by UI when it is loading a map file to display and tries to convert a functionComponent to an instance of GUI component.
	 * This function will handle constant function as well.
	 * @param functionComponent the uuid of the FunctionMeta, or of type FunctionMeta,
	 *                 or of type of FunctionBoxMutableViewInterface, or of type FunctionComponent
	 * @return null if the given functionComponent is null or could not find specified function within the functionComponent.
	 */
	public FunctionBoxMutableViewInterface createOneFunctionalBoxMutableViewInstance(FunctionComponent functionComponent)
	{
		if(functionComponent==null)
		{
			return null;
		}

		FunctionMeta functionMetaFromComponent = functionComponent.getMeta();
		FunctionMeta functionDefinition = FunctionBoxViewManager.getInstance().getOneFunctionalBoxSpecification(functionMetaFromComponent);
		FunctionBoxMutableViewInterface newFunctionBox = null;
		if (functionDefinition != null)
		{//just to confirm the system has it.

			newFunctionBox = new FunctionBoxMutableViewInterfaceImpl(functionComponent);
			if(functionMetaFromComponent.isConstantFunction() || functionComponent.getFunctionConstant()!=null)
			{
				newFunctionBox.setFunctionConstant(functionComponent.getFunctionConstant());
			}
			//register the newly created item in the map.
			functionInstanceMap.put(newFunctionBox.getUUID(), newFunctionBox);
		}
		return newFunctionBox;
	}

	/**
	 * This function is expected to be called by UI's action to manually add/create a function box, which will expect to ask constant definition if it is a constant function.
	 * @param function the uuid of the FunctionMeta, or of type FunctionMeta,
	 *                 or of type of FunctionBoxMutableViewInterface, or of type FunctionComponent
	 * @param viewInfo
	 * @param parentContainer
	 * @return a FunctionBoxMutableViewInterface
	 */
	public FunctionBoxMutableViewInterface createOneFunctionalBoxMutableViewInstance(Object function, gov.nih.nci.hl7.map.View viewInfo, Container parentContainer)
	{
		FunctionMeta functionMeta = FunctionBoxViewManager.getInstance().getOneFunctionalBoxSpecification(function);

		FunctionConstant functionConstant = null;
		if(functionMeta.isConstantFunction())
		{//a constant function, need to ask for input values
			//following does not work. It only works for one input type only.
//			Object returnValue = JOptionPane.showInputDialog(getMiddlePanel(), new Object[]{"Type", "Value"},"Define a Constant", JOptionPane.INFORMATION_MESSAGE, null, null, new Object[]{"", ""});
//			Log.logInfo(this, "return value: '" + returnValue + "'.");
			FunctionConstantDefinitionDialog dialog = null;
			if (parentContainer instanceof Frame)
			{
				dialog = new FunctionConstantDefinitionDialog((Frame) parentContainer);
			}
			else if (parentContainer instanceof Dialog)
			{
				dialog = new FunctionConstantDefinitionDialog((Dialog) parentContainer);
			}
			if (dialog != null)
			{
				DefaultSettings.centerWindow(dialog);
				dialog.setVisible(true);
				if (dialog.isOkButtonClicked())
				{
					String typeValue = DefaultSettings.getClassNameWithoutPackage(dialog.getConstantTypeClass());
					functionConstant = new FunctionConstant(typeValue, dialog.getConstantValue());
				}
				else
				{//adding constant was cancelled.
					return null;
				}
			}
			else
			{//adding constant was cancelled.
				return null;
			}
		}
		FunctionBoxMutableViewInterface newFunctionBoxInstance = null;
		if (functionMeta != null)
		{
			newFunctionBoxInstance = new FunctionBoxMutableViewInterfaceImpl(functionMeta, viewInfo);
			if(functionConstant!=null)
			{//implies the function is a constant function
				newFunctionBoxInstance.setFunctionConstant(functionConstant);
			}
			//register the newly created item in the map.
			functionInstanceMap.put(newFunctionBoxInstance.getUUID(), newFunctionBoxInstance);
		}
		return newFunctionBoxInstance;
	}

	/**
	 * Return the function usage instance based on the given instance's UUID.
	 * @param functionInstanceUUID
	 * @return a FunctionBoxMutableViewInterface
	 */
	public FunctionBoxMutableViewInterface findFunctionUsageInstance(String functionInstanceUUID)
	{
		return ((FunctionBoxMutableViewInterface) functionInstanceMap.get(functionInstanceUUID));
	}

	/**
	 * Find the function usage instance based on the given functionComponent's UUID.
	 * @param functionComponent
	 * @return return null if either functionComponent is null or no one is found.
	 */
	public FunctionBoxMutableViewInterface findFunctionUsageInstanceByComponentUUID(FunctionComponent functionComponent)
	{
		if(functionComponent==null)
		{
			return null;
		}
		FunctionBoxMutableViewInterface result = null;
		Iterator it = functionInstanceMap.keySet().iterator();
		while(it.hasNext())
		{
			Object key = it.next();
			FunctionBoxMutableViewInterface value = (FunctionBoxMutableViewInterface) functionInstanceMap.get(key);
			FunctionComponent localComp = value.getFunctionComponent(false);
			if(localComp!=null && GeneralUtilities.areEqual(localComp.getUUID(), functionComponent.getUUID()))
			{
				result = value;
				break;
			}
		}
		return result;
	}

	public List<FunctionBoxMutableViewInterface> findFunctionUsageInstanceByName(String functionName)
	{
		ArrayList<FunctionBoxMutableViewInterface> result = new ArrayList<FunctionBoxMutableViewInterface>();
		Iterator it = functionInstanceMap.keySet().iterator();
		while(it.hasNext())
		{
			Object key = it.next();
			FunctionBoxMutableViewInterface element = (FunctionBoxMutableViewInterface) functionInstanceMap.get(key);
			if(GeneralUtilities.areEqual(element.getName(), functionName))
			{
				result.add(element);
			}
		}
		return result;
	}

	/**
	 * Return all usage functions.
	 * @return a list of FunctionBoxMutableViewInterface
	 */
	public List<FunctionBoxMutableViewInterface> getAllFunctionUsageList()
	{
		ArrayList<FunctionBoxMutableViewInterface> result = new ArrayList<FunctionBoxMutableViewInterface>();
		Iterator it = functionInstanceMap.keySet().iterator();
		while (it.hasNext())
		{
			Object key = it.next();
			FunctionBoxMutableViewInterface element = (FunctionBoxMutableViewInterface) functionInstanceMap.get(key);
			result.add(element);
		}
		return result;
	}

	public Object removeFunctionUsage(FunctionBoxMutableViewInterface functionUsage)
	{
		if(functionUsage!=null)
		{
			return functionInstanceMap.remove(functionUsage.getUUID());
		}
		else
		{
			return null;
		}
	}
}

/**
 * HISTORY      : $Log: not supported by cvs2svn $
 * HISTORY      : Revision 1.14  2006/08/02 18:44:22  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.13  2006/01/03 19:16:52  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.12  2006/01/03 18:56:25  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.11  2005/12/29 23:06:16  jiangsc
 * HISTORY      : Changed to latest project name.
 * HISTORY      :
 * HISTORY      : Revision 1.10  2005/12/14 21:37:18  jiangsc
 * HISTORY      : Updated license information
 * HISTORY      :
 * HISTORY      : Revision 1.9  2005/11/29 16:23:55  jiangsc
 * HISTORY      : Updated License
 * HISTORY      :
 * HISTORY      : Revision 1.8  2005/10/25 22:00:43  jiangsc
 * HISTORY      : Re-arranged system output strings within UI packages.
 * HISTORY      :
 * HISTORY      : Revision 1.7  2005/10/12 20:47:54  jiangsc
 * HISTORY      : GUI Enhancement
 * HISTORY      :
 * HISTORY      : Revision 1.6  2005/08/23 18:57:16  jiangsc
 * HISTORY      : Implemented the new Properties structure
 * HISTORY      :
 * HISTORY      : Revision 1.5  2005/08/22 21:35:27  jiangsc
 * HISTORY      : Changed BaseComponentFactory and other UI classes to use File instead of string name;
 * HISTORY      : Added first implementation of Function Constant;
 * HISTORY      :
 * HISTORY      : Revision 1.4  2005/07/22 17:39:24  jiangsc
 * HISTORY      : Persistence of Function involved mapping.
 * HISTORY      :
 * HISTORY      : Revision 1.3  2005/07/21 17:07:48  jiangsc
 * HISTORY      : First round to implement Functions in mapping persistence.
 * HISTORY      :
 * HISTORY      : Revision 1.2  2005/07/20 22:00:54  jiangsc
 * HISTORY      : Save point.
 * HISTORY      :
 * HISTORY      : Revision 1.1  2005/07/19 22:28:14  jiangsc
 * HISTORY      : 1) Renamed FunctionalBox to FunctionBox to be consistent;
 * HISTORY      : 2) Added SwingWorker to OpenMapAction;
 * HISTORY      : 3) Save Point for Function Change.
 * HISTORY      :
 * HISTORY      : Revision 1.1  2005/07/14 22:24:33  jiangsc
 * HISTORY      : Save point
 * HISTORY      :
 */
