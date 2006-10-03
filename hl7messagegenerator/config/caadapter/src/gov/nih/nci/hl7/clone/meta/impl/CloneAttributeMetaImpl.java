/**
 * <!-- LICENSE_TEXT_START -->
 * $Header: /share/content/gforge/formbuilder/hl7messagegenerator/config/caadapter/src/gov/nih/nci/hl7/clone/meta/impl/CloneAttributeMetaImpl.java,v 1.1 2006-10-03 17:38:25 marwahah Exp $
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

import gov.nih.nci.hl7.clone.meta.CloneAttributeMeta;
import gov.nih.nci.hl7.clone.meta.CloneDatatypeFieldMeta;
import gov.nih.nci.hl7.clone.meta.CloneMeta;
import gov.nih.nci.hl7.common.MetaObject;
import gov.nih.nci.hl7.common.MetaObjectImpl;
import gov.nih.nci.hl7.util.GeneralUtilities;
import gov.nih.nci.hl7.util.PropertiesResult;
import org.hl7.meta.*;
import org.hl7.meta.impl.DatatypeMetadataFactoryDatatypes;
import org.hl7.meta.impl.DatatypeMetadataFactoryImpl;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

/**
 * This class provides the default implementation of CloneAttributeMeta interface.
 *
 * @author OWNER: Eric Chen  Date: Jun 2, 2005
 * @author LAST UPDATE: $Author: marwahah $
 * @version since caAdapter v1.2
 *          Revision $Revision: 1.1 $
 *          Date     $$Date: 2006-10-03 17:38:25 $
 */


public class CloneAttributeMetaImpl extends MetaObjectImpl implements CloneAttributeMeta
{
    private int sortKey;
	private String datatype;
	private String subClass;
	private String hl7DefaultValue;
	private Cardinality cardinality;
    private CodingStrength codingStrength;
    private Conformance conformance;
    private boolean isMandatory;
    private String domainName;
    private String rimSource;

    private List<CloneDatatypeFieldMeta> datatypeFields = new ArrayList<CloneDatatypeFieldMeta>();
	private List<CloneAttributeMeta> childAttributes = new ArrayList<CloneAttributeMeta>();

	private MetaObject parentMeta;

    public int getSortKey()
    {
        return sortKey;
    }

    public void setSortKey(int sortKey)
    {
        this.sortKey = sortKey;
    }

    public boolean isAbstract()
	{
		if (GeneralUtilities.isBlank(getDatatype()))
			return false;

		try
		{
			Datatype subDataType = DatatypeMetadataFactoryImpl.instance().create(getDatatype());
			return DatatypeMetadataFactoryDatatypes.instance().ABSTRACT_DATATYPES_MAP.keySet().contains(subDataType);
		}
		catch (UnknownDatatypeException e)
		{
			return false;
		}
	}

    public String getHL7DefaultValue()
    {
        return hl7DefaultValue;
    }

    public void setHL7DefaultValue(String hl7DefaultValue)
    {
        this.hl7DefaultValue = GeneralUtilities.forceNullToBlankString(hl7DefaultValue);
    }


	public void setDatatype(String abstractClass)
	{
		this.datatype = GeneralUtilities.forceNullToBlankString(abstractClass);
	}

	public String getDatatype()
	{
		return datatype;
	}

	public String getSubClass()
	{
		return subClass;
	}

	public void setSubClass(String subClass)
	{
		this.subClass = GeneralUtilities.forceNullToBlankString(subClass);
	}

    public Cardinality getCardinality()
    {
        return cardinality;
    }

    public void setCardinality(Cardinality cardinality)
    {
        this.cardinality = cardinality;
    }

    public CodingStrength getCodingStrength()
    {
        return codingStrength;
    }

    public void setCodingStrength(CodingStrength codingStrength)
    {
        this.codingStrength = codingStrength;
    }

    public Conformance getConformance()
    {
        return conformance;
    }

    public void setConformance(Conformance conformance)
    {
        this.conformance = conformance;
    }

    public boolean isMandatory()
    {
        return isMandatory;
    }

    public void setMandatory(boolean mandatory)
    {
        isMandatory = mandatory;
    }

	public List<CloneDatatypeFieldMeta> getDatatypeFields()
	{
		return datatypeFields;
	}

	public void addDatatyepField(CloneDatatypeFieldMeta datatypeFieldMeta)
	{
		if (datatypeFieldMeta == null)
		{
			return;
		}

		if (datatypeFieldMeta.getParentMeta() != null)
		{
			final MetaObject parentMeta = datatypeFieldMeta.getParentMeta();
			if (parentMeta instanceof CloneAttributeMeta)
			{
				CloneAttributeMeta pCloneAttribute = (CloneAttributeMeta) parentMeta;
				pCloneAttribute.getDatatypeFields().remove(datatypeFieldMeta);
			}
		}

		datatypeFields.add(datatypeFieldMeta);
		datatypeFieldMeta.setParentMeta(this);
	}

	public CloneDatatypeFieldMeta getDatatypeFieldByName(String datatypeFieldName)
	{
		if (GeneralUtilities.isBlank(datatypeFieldName))
			return null;

		for (int i = 0; i < getDatatypeFields().size(); i++)
		{
			CloneDatatypeFieldMeta datatypeFieldMeta =  getDatatypeFields().get(i);
			if (datatypeFieldName.equals(datatypeFieldMeta.getName()))
			{
				return datatypeFieldMeta;
			}
		}

		return null;

	}

	public List<CloneAttributeMeta> getChildAttributes()
	{
		return childAttributes;
	}

	public void addAttribute(CloneAttributeMeta cloneAttribute)
	{
		if (cloneAttribute == null)
		{
			return;
		}

		if (cloneAttribute.getParentMeta() != null)
		{
			final MetaObject parentMeta = cloneAttribute.getParentMeta();
			if (parentMeta instanceof CloneAttributeMeta)
			{
				CloneAttributeMeta pCloneAttribute = (CloneAttributeMeta) parentMeta;
				pCloneAttribute.getChildAttributes().remove(cloneAttribute);
			}
		}

		childAttributes.add(cloneAttribute);
		cloneAttribute.setParentMeta(this);
	}

	public CloneAttributeMeta getChildAttributeByName(String attributeName)
	{
		if (GeneralUtilities.isBlank(attributeName))
			return null;

		for (int i = 0; i < getChildAttributes().size(); i++)
		{
			CloneAttributeMeta attributeMeta =  getChildAttributes().get(i);
			if (attributeName.equals(attributeMeta.getName()))
			{
				return attributeMeta;
			}
		}

		return null;

	}

	/**
	 * Return the parent meta object, could be either a CloneMeta or an AttributeMeta.
	 *
	 * @return parent Meta object.
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
     * Check if the attribute allowed to create multiple instance to facilate many to many mapping
     * @return if the attribute allowed to create multiple instance to facilate many to many mapping
     */
    public boolean isMultiple()
    {
        return (cardinality != null) &&  (cardinality.getMax() > 1);
    }

    public String getDomainName()
    {
        return domainName;
    }

    public void setDomainName(String domainName)
    {
        this.domainName = GeneralUtilities.forceNullToBlankString(domainName);
    }

    public String getRimSource()
    {
        return rimSource;
    }

    public void setRimSource(String rimSource)
    {
        this.rimSource = GeneralUtilities.forceNullToBlankString(rimSource);
    }

    public boolean isMappable() {
        if(getChildAttributes().isEmpty() && getDatatypeFields().isEmpty()){
            return false;
        }else{
            return true;
        }
    }


    /**
	 * Return a clone of this object with or without children list.
	 *
	 * @param withChildren
	 * @param copyUUID
	 * @return
	 * @throws CloneNotSupportedException
	 */
	public Object clone(boolean withChildren, boolean copyUUID) throws CloneNotSupportedException
	{
		CloneAttributeMetaImpl cloneObj = (CloneAttributeMetaImpl) super.clone(copyUUID);
		ArrayList<CloneDatatypeFieldMeta> cloneDataTypeFields = new ArrayList<CloneDatatypeFieldMeta>();
		ArrayList<CloneAttributeMeta> cloneChildAttributes = new ArrayList<CloneAttributeMeta>();
		if (withChildren)
		{//deep copy
//deep copy children list.
			int size = (this.childAttributes==null)? 0 : this.childAttributes.size();
			for(int i=0; i<size; i++)
			{
                CloneAttributeMetaImpl cloneAttributeCopy = (CloneAttributeMetaImpl)((CloneAttributeMetaImpl)this.childAttributes.get(i)).clone(withChildren, copyUUID);
                cloneAttributeCopy.setParentMeta(cloneObj);
                cloneChildAttributes.add((CloneAttributeMeta) cloneAttributeCopy);
			}
			size = (this.datatypeFields==null)? 0 : this.datatypeFields.size();
			for(int i=0; i<size; i++)
			{
                CloneDatatypeFieldMetaImpl datatypeFieldCopy = (CloneDatatypeFieldMetaImpl)((CloneDatatypeFieldMetaImpl)this.datatypeFields.get(i)).clone(copyUUID);
                datatypeFieldCopy.setParentMeta(cloneObj);
                cloneDataTypeFields.add((CloneDatatypeFieldMeta) datatypeFieldCopy);
			}
		}
		cloneObj.datatypeFields = cloneDataTypeFields;
		cloneObj.childAttributes = cloneChildAttributes;
		return cloneObj;
	}

	public String toString()
	{
		StringBuffer sb = new StringBuffer(super.toString());

		if (isAbstract())
        {
            sb.append("   [Abstract - " + getDatatype());

            if (!GeneralUtilities.isBlank(getSubClass()))
            {
                sb.append(" implemented as " + getSubClass());
            }

            sb.append("]");

        }




        return sb.toString();
	}

	/**
	 * Return the title of this provider that may be used to distinguish from others.
	 *
	 * @return the title value of this object for properties display
	 */
	public String getTitle()
	{
		return "HL7 v3 Specification Attribute Properties";
	}

	/**
	 * Return the type of this object.
	 *
	 * @return the type of this object.
	 */
	public String getType()
	{
		return "Attribute";
	}

    public String getLinage() {
        if(getParentMeta() instanceof CloneAttributeMeta){
            return ((CloneAttributeMeta)getParentMeta()).getLinage() +"."+ getName();
        }else if(getParentMeta() instanceof CloneMeta){
            return ((CloneMeta)getParentMeta()).getLinage() +"."+ getName();
        }else{
            return getName();
        }
    }

    /**
     *
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
        PropertyDescriptor _mandatory = new PropertyDescriptor("Mandatory", beanClass, "isMandatory", null);
		PropertyDescriptor _conformance = new PropertyDescriptor("Conformance", beanClass, "getConformance", null);
        PropertyDescriptor _rimSource = new PropertyDescriptor("Rim Source", beanClass, "getRimSource", null);
		PropertyDescriptor _isMultiple = new PropertyDescriptor("isMultiple", beanClass, "isMultiple", null);
		PropertyDescriptor _isAbstract = new PropertyDescriptor("isAbstract", beanClass, "isAbstract", null);
		PropertyDescriptor _dataType = new PropertyDescriptor("Data Type", beanClass, "getDatatype", null);
		PropertyDescriptor _hl7DefaultValue = new PropertyDescriptor("HL7 Default Value", beanClass, "getHL7DefaultValue", null);
		PropertyDescriptor _hl7Domain = new PropertyDescriptor("HL7 Domain", beanClass, "getDomainName", null);
		PropertyDescriptor _codingStrength = new PropertyDescriptor("Coding Strength", beanClass, "getCodingStrength", null);
		List<PropertyDescriptor> propList = new ArrayList<PropertyDescriptor>();
		propList.add(_parentName);
		propList.add(_name);
		propList.add(_class);
		propList.add(_cardinality);
		propList.add(_isMultiple);
		propList.add(_mandatory);
		propList.add(_conformance);
		propList.add(_rimSource);
		propList.add(_isAbstract);
		propList.add(_dataType);
		propList.add(_hl7DefaultValue);
		propList.add(_hl7Domain);
		propList.add(_codingStrength);

		PropertiesResult result = new PropertiesResult();
		result.addPropertyDescriptors(this, propList);
		return result;
	}
}

/**
 * HISTORY      : $Log: not supported by cvs2svn $
 * HISTORY      : Revision 1.39  2006/08/02 18:44:19  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.38  2006/06/02 20:51:12  chene
 * HISTORY      : layout improvement per Wendy
 * HISTORY      :
 * HISTORY      : Revision 1.37  2006/06/02 16:08:09  chene
 * HISTORY      : Display sub class if selected
 * HISTORY      :
 * HISTORY      : Revision 1.36  2006/01/18 17:46:44  giordanm
 * HISTORY      : no message
 * HISTORY      :
 * HISTORY      : Revision 1.35  2006/01/03 19:16:51  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.34  2006/01/03 18:27:13  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.33  2005/12/29 23:06:15  jiangsc
 * HISTORY      : Changed to latest project name.
 * HISTORY      :
 * HISTORY      : Revision 1.32  2005/12/19 20:13:08  chene
 * HISTORY      : Fix for bug when we clone a child, we do not set the parent meta. So cloned child and child point to the same parent
 * HISTORY      :
 * HISTORY      : Revision 1.31  2005/12/01 18:50:32  jiangsc
 * HISTORY      : Fix to Defect #207.
 * HISTORY      :
 * HISTORY      : Revision 1.30  2005/11/14 23:32:12  giordanm
 * HISTORY      : BUG: 192
 * HISTORY      :
 * HISTORY      : Revision 1.29  2005/11/11 23:43:38  chene
 * HISTORY      : Add cardinality etc properties
 * HISTORY      :
 * HISTORY      : Revision 1.28  2005/11/10 21:22:04  chene
 * HISTORY      : Correct the Mapping Panel Properties Title
 * HISTORY      :
 * HISTORY      : Revision 1.27  2005/11/07 20:13:57  chene
 * HISTORY      : Rename sub_datatypes_map to abstract_datatypes_map
 * HISTORY      :
 * HISTORY      : Revision 1.26  2005/10/11 15:42:44  jiangsc
 * HISTORY      : Changed the cosmetic displays to " - " instead of double hyphens.
 * HISTORY      :
 * HISTORY      : Revision 1.25  2005/09/29 20:35:11  chene
 * HISTORY      : Support RIM Source for CloneAttributeMeta
 * HISTORY      :
 * HISTORY      : Revision 1.24  2005/09/28 20:47:17  chene
 * HISTORY      : Add getSortKey at Attribute Meta Interface, clean up visitor pattern
 * HISTORY      :
 * HISTORY      : Revision 1.23  2005/09/26 19:29:44  chene
 * HISTORY      : Add Clone Attribute Multiple Class in order to support Multiple Attribute
 * HISTORY      :
 * HISTORY      : Revision 1.22  2005/09/22 20:07:55  chene
 * HISTORY      : Fix the multiple disply problem
 * HISTORY      :
 * HISTORY      : Revision 1.21  2005/09/04 17:32:21  chene
 * HISTORY      : Add conformance, cardinality, etc support at HSM subsystem
 * HISTORY      :
 * HISTORY      : Revision 1.20  2005/08/30 19:12:35  jiangsc
 * HISTORY      : Implemented properties
 * HISTORY      :
 * HISTORY      : Revision 1.19  2005/08/18 18:18:02  chene
 * HISTORY      : Fixed one cardinality related to choice
 * HISTORY      :
 * HISTORY      : Revision 1.18  2005/08/17 20:57:28  jiangsc
 * HISTORY      : Build deep copy methods in clone().
 * HISTORY      :
 * HISTORY      : Revision 1.17  2005/08/04 18:05:06  jiangsc
 * HISTORY      : Refactorized clone() methods to have explicit clone(boolean copyUUID)
 * HISTORY      :
 * HISTORY      : Revision 1.16  2005/08/04 16:06:32  chene
 * HISTORY      : Add three extra space for toString
 * HISTORY      :
 * HISTORY      : Revision 1.15  2005/08/04 16:02:33  chene
 * HISTORY      : Add three extra space for toString
 * HISTORY      :
 */
