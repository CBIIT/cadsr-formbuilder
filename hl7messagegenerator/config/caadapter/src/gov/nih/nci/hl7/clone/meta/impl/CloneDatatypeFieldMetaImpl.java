/**
 * <!-- LICENSE_TEXT_START -->
 * $Header: /share/content/gforge/formbuilder/hl7messagegenerator/config/caadapter/src/gov/nih/nci/hl7/clone/meta/impl/CloneDatatypeFieldMetaImpl.java,v 1.1 2006-10-03 17:38:25 marwahah Exp $
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


package gov.nih.nci.hl7.clone.meta.impl;

import gov.nih.nci.hl7.clone.meta.CloneDatatypeFieldMeta;
import gov.nih.nci.hl7.clone.meta.CloneAttributeMeta;
import gov.nih.nci.hl7.clone.meta.CloneMeta;
import gov.nih.nci.hl7.common.MetaObject;
import gov.nih.nci.hl7.common.MetaObjectImpl;
import gov.nih.nci.hl7.util.GeneralUtilities;
import gov.nih.nci.hl7.util.PropertiesResult;
import org.hl7.meta.Cardinality;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

/**
 * Default implementation of CloneDatatypeFieldMeta
 *
 * @author OWNER: Eric Chen  Date: Jun 2, 2005
 * @author LAST UPDATE: $Author: marwahah $
 * @version since caAdapter v1.2
 *          Revision $Revision: 1.1 $
 *          Date     $$Date: 2006-10-03 17:38:25 $
 */
public class CloneDatatypeFieldMetaImpl extends MetaObjectImpl implements CloneDatatypeFieldMeta
{
	private String hl7DefaultValue;
	private String userDefaultValue;
	private Cardinality cardinality;
    private MetaObject parentMeta;

	public String getHL7DefaultValue()
	{
		return hl7DefaultValue;
	}

	public void setHL7DefaultValue(String hl7DefaultValue)
	{
		this.hl7DefaultValue = GeneralUtilities.forceNullToBlankString(hl7DefaultValue);
	}

	public String getUserDefaultValue()
	{
		return userDefaultValue;
	}

	public void setUserDefaultValue(String userDefaultValue)
	{
		this.userDefaultValue = GeneralUtilities.forceNullToBlankString(userDefaultValue);
	}

    public Cardinality getCardinality()
    {
        return cardinality;
    }

    public void setCardinality(Cardinality cardinality)
    {
        this.cardinality = cardinality;
    }


	/**
	 * Return the parent meta object, could be either a CloneMeta or an AttributeMeta.
	 *
	 * @return parentMeta
	 */
	public MetaObject getParentMeta()
	{
		return this.parentMeta;
	}

	/**
	 * Set the parent meta object, could be either a CloneMeta or an AttributeMeta.
	 *
	 * @param parentMeta
	 */
	public void setParentMeta(MetaObject parentMeta)
	{
		this.parentMeta = parentMeta;
	}
	/**
	 * Return a clone of this object with or without children list.
	 * @param copyUUID if true, the cloned object will share the same uuid value of this object; otherwise, it will have different UUID value.
	 * @return
	 * @throws CloneNotSupportedException
	 */
	public Object clone(boolean copyUUID) throws CloneNotSupportedException
	{
		CloneDatatypeFieldMetaImpl cloneObj = (CloneDatatypeFieldMetaImpl) super.clone(copyUUID);
		cloneObj.setHL7DefaultValue(this.hl7DefaultValue);
		cloneObj.setParentMeta(this.parentMeta);
		return cloneObj;
	}

	/**
	 * Return the title of this provider that may be used to distinguish from others.
	 *
	 * @return the title value of this object for properties display
	 */
	public String getTitle()
	{
		return "HL7 v3 Specification Data Type Field Properties";
	}

	/**
	 * Return the type of this object.
	 *
	 * @return the type of this object.
	 */
	public String getType()
	{
		return "Data Type Field";
	}

    public String getLinage() {
        if(getParentMeta() instanceof CloneAttributeMeta){
            return ((CloneAttributeMeta)getParentMeta()).getLinage() +"."+ getName();
        }else{
            return getName();
        }
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
		Class beanClass = this.getClass();

		PropertyDescriptor _parentName = new PropertyDescriptor("Parent Meta", beanClass, "getParentMeta", null);
		PropertyDescriptor _name = new PropertyDescriptor("Name", beanClass, "getName", null);
		PropertyDescriptor _class = new PropertyDescriptor("Type", beanClass, "getType", null);
        PropertyDescriptor _cardinality = new PropertyDescriptor("Cardinality", beanClass, "getCardinality", null);
		PropertyDescriptor _hl7DefaultValue = new PropertyDescriptor("HL7 Default Value", beanClass, "getHL7DefaultValue", null);
		PropertyDescriptor _userDefaultValue = new PropertyDescriptor("User Default Value", beanClass, "getUserDefaultValue", null);
		List<PropertyDescriptor> propList = new ArrayList<PropertyDescriptor>();
		propList.add(_parentName);
		propList.add(_name);
		propList.add(_class);
		propList.add(_cardinality);
		propList.add(_hl7DefaultValue);
		propList.add(_userDefaultValue);
		PropertiesResult result = new PropertiesResult();
		result.addPropertyDescriptors(this, propList);
		return result;
	}

}

/**
 * HISTORY      : $Log: not supported by cvs2svn $
 * HISTORY      : Revision 1.19  2006/08/02 18:44:19  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.18  2006/01/18 17:46:44  giordanm
 * HISTORY      : no message
 * HISTORY      :
 * HISTORY      : Revision 1.17  2006/01/03 19:16:51  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.16  2006/01/03 18:27:13  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.15  2005/12/29 23:06:15  jiangsc
 * HISTORY      : Changed to latest project name.
 * HISTORY      :
 * HISTORY      : Revision 1.14  2005/12/01 18:50:32  jiangsc
 * HISTORY      : Fix to Defect #207.
 * HISTORY      :
 * HISTORY      : Revision 1.13  2005/11/11 23:43:38  chene
 * HISTORY      : Add cardinality etc properties
 * HISTORY      :
 * HISTORY      : Revision 1.12  2005/11/07 21:23:06  chene
 * HISTORY      : Add datatype cardinality support
 * HISTORY      :
 * HISTORY      : Revision 1.11  2005/10/18 17:02:49  umkis
 * HISTORY      : no message
 * HISTORY      :
 * HISTORY      : Revision 1.10  2005/08/30 19:12:34  jiangsc
 * HISTORY      : Implemented properties
 * HISTORY      :
 * HISTORY      : Revision 1.9  2005/08/04 18:05:06  jiangsc
 * HISTORY      : Refactorized clone() methods to have explicit clone(boolean copyUUID)
 * HISTORY      :
 * HISTORY      : Revision 1.8  2005/08/03 15:20:05  chene
 * HISTORY      : Fixed Castor Schema Validation Error
 * HISTORY      :
 * HISTORY      : Revision 1.7  2005/08/02 22:29:01  chene
 * HISTORY      : Alway force Clonemeta attribute value to "" if value is null
 * HISTORY      :
 * HISTORY      : Revision 1.6  2005/08/02 15:18:28  chene
 * HISTORY      : Add HL7 default value and user default value
 * HISTORY      :
 * HISTORY      : Revision 1.5  2005/07/27 19:37:55  jiangsc
 * HISTORY      : Implemented and/or added clone() methods.
 * HISTORY      :
 * HISTORY      : Revision 1.4  2005/07/27 18:33:44  jiangsc
 * HISTORY      : Added setParentMeta and getParentMeta functions.
 * HISTORY      :
 * HISTORY      : Revision 1.3  2005/06/03 22:43:01  chene
 * HISTORY      : Add HL7V3 Meta Builder
 * HISTORY      :
 * HISTORY      : Revision 1.2  2005/06/03 19:24:11  chene
 * HISTORY      : Added Meta Parser Stuff
 * HISTORY      :
 * HISTORY      : Revision 1.1  2005/06/03 17:01:38  chene
 * HISTORY      : Added Clone Meta Stuff
 * HISTORY      :
 */
