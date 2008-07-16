/**
 * <!-- LICENSE_TEXT_START -->
 * $Header: /share/content/gforge/formbuilder/hl7messagegenerator/config/caadapter/src/gov/nih/nci/hl7/ui/jgraph/functions/FunctionBoxMutableViewInterfaceImpl.java,v 1.1 2006-10-03 17:38:43 marwahah Exp $
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

import gov.nih.nci.hl7.common.MetaObjectImpl;
import gov.nih.nci.hl7.function.FunctionConstant;
import gov.nih.nci.hl7.function.FunctionMeta;
import gov.nih.nci.hl7.function.impl.FunctionMetaImpl;
import gov.nih.nci.hl7.map.components.FunctionComponent;
import gov.nih.nci.hl7.ui.jgraph.graph.FunctionBoxCell;
import gov.nih.nci.hl7.ui.tree.MappableNode;
import gov.nih.nci.hl7.util.Log;
import gov.nih.nci.hl7.util.PropertiesResult;

import javax.swing.*;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is the default implementation of FunctionBoxMutableViewInterface.
 * @author OWNER: Scott Jiang
 * @author LAST UPDATE $Author: marwahah $
 * @version Since caAdapter v1.2
 *          revision    $Revision: 1.1 $
 *          date        $Date: 2006-10-03 17:38:43 $
 */
public class FunctionBoxMutableViewInterfaceImpl extends MetaObjectImpl implements FunctionBoxMutableViewInterface, MappableNode, Cloneable
{
	private boolean mapped = false;

	private Icon icon = null;
//	private String name = null;
	private int totalNumberOfDefinedInputs = 0;
	private int totalNumberOfDefinedOutputs = 0;
	private List inputElementList = new ArrayList();
	private List outputElementList = new ArrayList();

	protected FunctionComponent functionComponent;
	protected FunctionMeta functionMeta;
	protected gov.nih.nci.hl7.map.View viewMeta;
	protected FunctionBoxCell functionBoxCell;
	protected FunctionConstant functionConstant;

	/**
	 * This constuctor is intended to be used when adding a new function on the mapping,
	 * since at that time, no function component is available but just functionMeta and/or viewMeta.
	 *
	 * @param functionMeta
	 * @param viewMeta
	 */
	public FunctionBoxMutableViewInterfaceImpl (FunctionMeta functionMeta, gov.nih.nci.hl7.map.View viewMeta)
	{
		this.functionComponent = null;
		this.functionMeta = functionMeta;
		this.viewMeta = viewMeta;
		resetMetas();
	}

	/**
	 * This constructor is intended to be used when adding functions from
	 * existing mapping file, and will remember the function component associated with the mapping
	 * so as to be referenced upon persistence.
	 * @param functionComponent
	 */
	public FunctionBoxMutableViewInterfaceImpl (FunctionComponent functionComponent)//FunctionMeta functionMeta, View viewMeta)
	{
		this.functionComponent = functionComponent;
		this.functionMeta = this.functionComponent.getMeta();
		this.viewMeta = this.functionComponent.getView();
		resetMetas();
	}

//	public FunctionBoxMutableViewInterfaceImpl (Icon icon, String name, int totalNumberOfDefinedInputs, int totalNumberOfDefinedOutputs)
//	{
//		this(icon, name, totalNumberOfDefinedInputs, totalNumberOfDefinedOutputs, null, null);
//	}
//
//	public FunctionBoxMutableViewInterfaceImpl (Icon icon, String name, int totalNumberOfDefinedInputs, int totalNumberOfDefinedOutputs, List inputList, List outputList)
//	{
//		this.icon = icon;
//		this.name = name;
//		this.totalNumberOfDefinedInputs = totalNumberOfDefinedInputs;
//		this.totalNumberOfDefinedOutputs = totalNumberOfDefinedOutputs;
//
//		if(inputList==null)
//		{
//			inputList = new ArrayList();
//		}
//		this.inputElementList = inputList;
//		if(outputList==null)
//		{
//			outputList = new ArrayList();
//		}
//		this.outputElementList = outputList;
//	}

	public Icon getIcon()
	{
		return icon;
	}

//	public String getName()
//	{
//		return name;
//	}

	public int getTotalNumberOfDefinedInputs()
	{
		return totalNumberOfDefinedInputs;
	}

	public int getTotalNumberOfDefinedOutputs()
	{
		return totalNumberOfDefinedOutputs;
	}

	public int getTotalNumberOfActualInputs()
	{
		return inputElementList.size();
	}

	public int getTotalNumberOfActualOutputs()
	{
		return outputElementList.size();
	}

	public List getInputElementList()
	{
		return inputElementList;
	}

//	public String toString()
//	{
//		return getName();
//	}

	public List getOutputElementList()
	{
		return outputElementList;
	}

	public FunctionMeta getFunctionMeta()
	{
		return this.functionMeta;
	}

	public void setFunctionMeta(FunctionMeta newFunctionMeta)
	{
		this.functionMeta = newFunctionMeta;
		resetMetas();
	}

	public gov.nih.nci.hl7.map.View getViewMeta()
	{
		return this.viewMeta;
	}

	public void setViewMeta(gov.nih.nci.hl7.map.View newViewMeta)
	{
		this.viewMeta = newViewMeta;
	}

	/**
	 * Return the embedded function component.
	 * @param create if true will create a new one given the current one is null.
	 * @return a FunctionComponent
	 */
	public FunctionComponent getFunctionComponent(boolean create)
	{
		if(create && functionComponent==null)
		{
			functionComponent = new FunctionComponent(functionMeta);
			functionComponent.setView(viewMeta);
			functionComponent.setFunctionConstant(getFunctionConstant());
		}
		else if(functionComponent!=null)
		{
			functionComponent.setMeta(this.functionMeta);
			functionComponent.setView(this.viewMeta);
			functionComponent.setFunctionConstant(getFunctionConstant());
		}
		return functionComponent;
	}

	/**
	 * Set a new function component.
	 *
	 * @param functionComponent
	 */
	public void setFunctionComponent(FunctionComponent functionComponent)
	{
		this.functionComponent = functionComponent;
		setFunctionMeta(functionComponent.getMeta());
		setViewMeta(functionComponent.getView());
		setFunctionConstant(functionComponent.getFunctionConstant());
	}

	private void resetMetas()
	{
		if (functionMeta != null)
		{
			this.name = functionMeta.getName();
			this.totalNumberOfDefinedInputs = functionMeta.getSizeOfDefinedInput();
			this.totalNumberOfDefinedOutputs = functionMeta.getSizeOfDefinedOutput();
		}
		this.inputElementList = new ArrayList();
		this.outputElementList = new ArrayList();
	}

	/**
	 * Return the associated view object in JGraph.
	 * Could return null if this view has not be put on view yet.
	 *
	 * @return a FunctionBoxCell
	 */
	public FunctionBoxCell getFunctionBoxCell()
	{
		return this.functionBoxCell;
	}

	/**
	 * Set the function box cell.
	 *
	 * @param newFunctionBoxCell
	 */
	public void setFunctionalBoxCell(FunctionBoxCell newFunctionBoxCell)
	{
		this.functionBoxCell = newFunctionBoxCell;
	}

	/**
	 * Return the function constant.
	 * @return a FunctionConstant
	 */
	public FunctionConstant getFunctionConstant()
	{
		return functionConstant;
	}

	/**
	 * Set a new functionConstant.
	 * @param functionConstant
	 */
	public void setFunctionConstant(FunctionConstant functionConstant)
	{
		this.functionConstant = functionConstant;
	}

	/**
	 * Set the map status to new value, which might trigger underline property change.
	 *
	 * @param newValue
	 */
	public void setMapStatus(boolean newValue)
	{
		this.mapped = newValue;
	}

	/**
	 * Answer if this given node is mapped.
	 *
	 * @return if this given node is mapped.
	 */
	public boolean isMapped()
	{
		return mapped;
	}

	/**
	 * @param copyUUID if true, the cloned object will share the same uuid value of this object; otherwise, it will have different UUID value.
	 */
	public Object clone(boolean copyUUID) throws CloneNotSupportedException
	{
		FunctionBoxMutableViewInterfaceImpl cloneObject = (FunctionBoxMutableViewInterfaceImpl) super.clone(copyUUID);
		cloneObject.name = this.name;
		cloneObject.icon = this.icon;

		cloneObject.totalNumberOfDefinedInputs = this.totalNumberOfDefinedInputs;
		cloneObject.totalNumberOfDefinedOutputs = this.totalNumberOfDefinedOutputs;

		cloneObject.inputElementList = new ArrayList();
		cloneObject.inputElementList.addAll(this.inputElementList);

		cloneObject.outputElementList = new ArrayList();
		cloneObject.outputElementList.addAll(this.outputElementList);
		cloneObject.mapped = this.mapped;
		return cloneObject;
	}

	/**
	 * Return the title of this provider that may be used to distinguish from others.
	 *
	 * @return the title value of this object for properties display
	 */
	public String getTitle()
	{
		String title = null;
		if(functionMeta instanceof FunctionMetaImpl)
		{
			title = ((FunctionMetaImpl)functionMeta).getTitle();
		}
		else
		{
			title = "Properties";
			if(functionConstant!=null)
			{
				title = "Constant " + title;
			}
			else
			{
				title = "Function " + title;
			}
		}
		return title;
	}

	/**
	 * This functions will return an array of PropertyDescriptor that would
	 * help reflection and/or introspection to figure out what information would be
	 * presented to the user.
	 * <p/>
	 * descendant classes are free to override to provide additional information.
	 */
	public PropertiesResult getPropertyDescriptors() throws Exception
	{
		PropertiesResult result = ((FunctionMetaImpl)functionMeta).getPropertyDescriptors();
		if(functionConstant!=null)
		{
			List<PropertyDescriptor> propList = new ArrayList<PropertyDescriptor>();
//			Class beanClass = this.getClass();
//			PropertyDescriptor prop = null;
//			Method readMethod = constructMethod(beanClass, "getFunctionConstantType");
//			if(readMethod !=null)
//			{
//				prop = new PropertyDescriptor("Constant Type", readMethod, null);
//				propList.add(prop);
//			}
//			readMethod = constructMethod(beanClass, "getFunctionConstantValue");
//			if(readMethod !=null)
//			{
//				prop = new PropertyDescriptor("Constant Value", readMethod, null);
//				propList.add(prop);
//			}
			Class beanClass = functionConstant.getClass();//this.getClass();
			PropertyDescriptor prop = null;
//			Method readMethod = constructMethod(beanClass, "getFunctionConstantType");
//			if(readMethod !=null)
//			{
//				prop = new PropertyDescriptor("Constant Type", readMethod, null);
				prop = new PropertyDescriptor("Constant Type", beanClass, "getType", null);
				propList.add(prop);
//			}
//			readMethod = constructMethod(beanClass, "getFunctionConstantValue");
//			if(readMethod !=null)
//			{
//				prop = new PropertyDescriptor("Constant Value", readMethod, null);
				prop = new PropertyDescriptor("Constant Value", beanClass, "getValue", null);
				propList.add(prop);
//			}

			if(propList.size()>0)
			{
				result.addPropertyDescriptors(functionConstant, propList);
//				result.addPropertyDescriptors(this, propList);
			}
		}
		return result;
	}

	protected Method constructMethod(Class aClass, String methodName)
	{
		Method method = null;
		try
		{
			method = aClass.getDeclaredMethod(methodName, new Class[0]);
		}
		catch (NoSuchMethodException e)
		{
			Log.logWarning(aClass, e);
		}
		return method;
	}

	protected Object getFunctionConstantType()
	{
		if(functionConstant!=null)
		{
			return functionConstant.getType();
		}
		else
		{
			return "";
		}
	}

	protected Object getFunctionConstantValue()
	{
		if(functionConstant!=null)
		{
			return functionConstant.getValue();
		}
		else
		{
			return "";
		}
	}

//	public FunctionBoxMutableViewInterfaceImpl(Icon icon, String name, int totalNumberOfDefinedInputs, int totalNumberOfDefinedOutputs)
//	{
//		super(icon, name, totalNumberOfDefinedInputs, totalNumberOfDefinedOutputs);
//	}
//
//	public FunctionBoxMutableViewInterfaceImpl(Icon icon, String name, int totalNumberOfDefinedInputs, int totalNumberOfDefinedOutputs, List inputList, List outputList)
//	{
//		super(icon, name, totalNumberOfDefinedInputs, totalNumberOfDefinedOutputs, inputList, outputList);
//	}

//	public boolean addElement(Object element, String inputOrOutput)
//	{
//		//todo: implement assert betweent the actual number of added vs the total number of defined so as to avoid overflow or underflow
//		boolean result = false;
//		if(element==null)
//		{
//			throw new IllegalArgumentException("Element should not be null!");
//		}
//		if(INPUT_PARAM.equals(inputOrOutput))
//		{
//            result = getInputElementList().add(element);
//		}
//		else if(OUTPUT_PARAM.equals(inputOrOutput))
//		{
//			result = getOutputElementList().add(element);
//		}
//		else
//		{
//			throw new IllegalArgumentException("The '" + inputOrOutput + "' is not valid input!");
//		}
//		return result;
//	}
//
//	public boolean removeElement(Object element, String inputOrOutput)
//	{
//		boolean result = false;
//		if (element == null)
//		{
//			throw new IllegalArgumentException("Element should not be null!");
//		}
//		if (INPUT_PARAM.equals(inputOrOutput))
//		{
//			result = getInputElementList().remove(element);
//		}
//		else if (OUTPUT_PARAM.equals(inputOrOutput))
//		{
//			result = getOutputElementList().remove(element);
//		}
//		else
//		{
//			throw new IllegalArgumentException("The '" + inputOrOutput + "' is not valid input!");
//		}
//		return result;
//	}
//
//	public boolean removeAll(List elementList, String inputOrOutput)
//	{
//		boolean result = false;
//		if (INPUT_PARAM.equals(inputOrOutput))
//		{
//			result = getInputElementList().removeAll(elementList);
//		}
//		else if (OUTPUT_PARAM.equals(inputOrOutput))
//		{
//			result = getOutputElementList().removeAll(elementList);
//		}
//		else
//		{
//			throw new IllegalArgumentException("The '" + inputOrOutput + "' is not valid input!");
//		}
//		return result;
//	}
//
//	public boolean addAll(List elementList, String inputOrOutput)
//	{
//		boolean result = false;
//		if (elementList == null)
//		{
//			throw new IllegalArgumentException("Element should not be null!");
//		}
//		if (INPUT_PARAM.equals(inputOrOutput))
//		{
//			result = getInputElementList().addAll(elementList);
//		}
//		else if (OUTPUT_PARAM.equals(inputOrOutput))
//		{
//			result = getOutputElementList().addAll(elementList);
//		}
//		else
//		{
//			throw new IllegalArgumentException("The '" + inputOrOutput + "' is not valid input!");
//		}
//		return result;
//	}

}
/**
 * HISTORY      : $Log: not supported by cvs2svn $
 * HISTORY      : Revision 1.13  2006/08/02 18:44:22  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.12  2006/01/03 19:16:52  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.11  2006/01/03 18:56:25  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.10  2005/12/29 23:06:16  jiangsc
 * HISTORY      : Changed to latest project name.
 * HISTORY      :
 * HISTORY      : Revision 1.9  2005/12/29 15:39:06  chene
 * HISTORY      : Optimize imports
 * HISTORY      :
 * HISTORY      : Revision 1.8  2005/12/14 21:37:18  jiangsc
 * HISTORY      : Updated license information
 * HISTORY      :
 * HISTORY      : Revision 1.7  2005/11/29 16:23:55  jiangsc
 * HISTORY      : Updated License
 * HISTORY      :
 * HISTORY      : Revision 1.6  2005/08/23 18:57:16  jiangsc
 * HISTORY      : Implemented the new Properties structure
 * HISTORY      :
 * HISTORY      : Revision 1.5  2005/08/22 21:35:27  jiangsc
 * HISTORY      : Changed BaseComponentFactory and other UI classes to use File instead of string name;
 * HISTORY      : Added first implementation of Function Constant;
 * HISTORY      :
 * HISTORY      : Revision 1.4  2005/08/04 18:05:04  jiangsc
 * HISTORY      : Refactorized clone() methods to have explicit clone(boolean copyUUID)
 * HISTORY      :
 * HISTORY      : Revision 1.3  2005/07/22 20:53:19  jiangsc
 * HISTORY      : Structure change and added License and history anchors.
 * HISTORY      :
 */