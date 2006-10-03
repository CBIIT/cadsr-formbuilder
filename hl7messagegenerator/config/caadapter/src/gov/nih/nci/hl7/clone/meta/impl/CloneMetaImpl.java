/**
 * <!-- LICENSE_TEXT_START -->
 * $Header: /share/content/gforge/formbuilder/hl7messagegenerator/config/caadapter/src/gov/nih/nci/hl7/clone/meta/impl/CloneMetaImpl.java,v 1.1 2006-10-03 17:38:25 marwahah Exp $
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
 * Default implementation of the clone meta class
 *
 * @author OWNER: Eric Chen  Date: Jun 2, 2005
 * @author LAST UPDATE: $Author: marwahah $
 * @version since caAdapter v1.2
 *          Revision $Revision: 1.1 $
 *          Date     $$Date: 2006-10-03 17:38:25 $
 */


public class CloneMetaImpl extends MetaObjectImpl implements CloneMeta
{

    /**
     * Logging constant used to identify source of log entry, that could be later used to create
     * logging mechanism to uniquely identify the logged class.
     */
    private static final String LOGID = "$RCSfile: CloneMetaImpl.java,v $";

    private int sortKey;

    private List<CloneAttributeMeta> cloneAttributes = new ArrayList<CloneAttributeMeta>();
    private List<CloneMeta> childClones = new ArrayList<CloneMeta>();

    private String referenceCloneUUID;
    private String referenceCloneName;
    private boolean isRecursive = false;
    private String cmetID;
    private CloneMeta parentMeta;

    private Cardinality cardinality;
    private String rimSource;


    public int getSortKey()
    {
        return sortKey;
    }

    public void setSortKey(int sortKey)
    {
        this.sortKey = sortKey;
    }

    public List<CloneAttributeMeta> getAttributes()
    {
        return cloneAttributes;
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
            if (parentMeta instanceof CloneMeta)
            {
                CloneMeta cloneMeta = (CloneMeta) parentMeta;
                cloneMeta.getAttributes().remove(cloneAttribute);
            }
        }

//		cloneAttributes.lastIndexOf(cloneAttribute);

        cloneAttributes.add(cloneAttribute);
        cloneAttribute.setParentMeta(this);
    }

    public Cardinality getCardinality()
    {
        return cardinality;
    }

    public void setCardinality(Cardinality cardinality)
    {
        this.cardinality = cardinality;
    }

    public String getRimSource()
    {
        return rimSource;
    }

    public void setRimSource(String rimSource)
    {
        this.rimSource = GeneralUtilities.forceNullToBlankString(rimSource);
    }

    public String getLinage()
    {
        String fullpath = getName();
        CloneMeta meta = this.getParentMeta();
        while (meta != null)
        {
            fullpath = meta.getName() + "." + fullpath;
            meta = meta.getParentMeta();
        }
        return fullpath;
    }

    /**
     * Return the type of this object.
     *
     * @return the type of this object.
     */
    public String getType()
    {
        return "Clone";
    }

    /**
     * Convenient method to find child attribute by name. If it's multiple attribute, return the first one
     *
     * @param attributeName
     * @return the CloneAttributeMeta.
     */
    public CloneAttributeMeta getAttributeByName(String attributeName)
    {
        if (GeneralUtilities.isBlank(attributeName))
            return null;

        for (int i = 0; i < getAttributes().size(); i++)
        {
            CloneAttributeMeta attributeMeta = getAttributes().get(i);
            if (attributeName.equals(attributeMeta.getName()))
            {
                return attributeMeta;
            }
        }

        return null;
    }

    public List<CloneMeta> getChildClones()
    {
        return childClones;
    }

    public void addClone(CloneMeta childClone)
    {
        if (childClone == null)
        {
            return;
        }

        if (childClone.getParentMeta() != null)
        {
            final CloneMeta parentMeta = childClone.getParentMeta();
            parentMeta.getChildClones().remove(childClone);
        }

        childClones.add(childClone);
        childClone.setParentMeta(this);
    }

    public CloneMeta getChildCloneByName(String cloneMetaName)
    {
        if (GeneralUtilities.isBlank(cloneMetaName))
            return null;

        for (int i = 0; i < getChildClones().size(); i++)
        {
            CloneMeta childCloneMeta = getChildClones().get(i);
            if (cloneMetaName.equals(childCloneMeta.getName()))
            {
                return childCloneMeta;
            }
        }

        return null;
    }

    public boolean isReference()
    {
        return isRecursive;
    }

    public void setReference(boolean recursive)
    {
        this.isRecursive = recursive;
    }

    public String getReferenceCloneUUID()
    {
        return referenceCloneUUID;
    }

    public void setReferenceCloneUUID(String referenceCloneUUID)
    {
        this.referenceCloneUUID = GeneralUtilities.forceNullToBlankString(referenceCloneUUID);
    }

    public String getReferenceCloneName()
    {
        return referenceCloneName;
    }

    public void setReferenceCloneName(String recursiveCloneName)
    {
        this.referenceCloneName = recursiveCloneName;
    }

    public String getCmetID()
    {
        return cmetID;
    }

    public void setCmetID(String cmetID)
    {
        this.cmetID = GeneralUtilities.forceNullToBlankString(cmetID);
    }

    public CloneMeta getParentMeta()
    {
        return parentMeta;
    }

    public void setParentMeta(CloneMeta parentMeta)
    {
        this.parentMeta = parentMeta;
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
        CloneMetaImpl cloneObj = (CloneMetaImpl) super.clone(copyUUID);
        ArrayList<CloneMeta> cloneChildClones = new ArrayList<CloneMeta>();
        ArrayList<CloneAttributeMeta> cloneChildAttributes = new ArrayList<CloneAttributeMeta>();
        if (withChildren)
        {//deep copy children list.
            int size = (this.cloneAttributes == null) ? 0 : this.cloneAttributes.size();
            for (int i = 0; i < size; i++)
            {
                CloneAttributeMetaImpl childAttributeCopy = (CloneAttributeMetaImpl)((CloneAttributeMetaImpl) this.cloneAttributes.get(i)).clone(withChildren, copyUUID);
                childAttributeCopy.setParentMeta(cloneObj);
                cloneChildAttributes.add((CloneAttributeMeta) childAttributeCopy);
            }
            size = (this.childClones == null) ? 0 : this.childClones.size();
            for (int i = 0; i < size; i++)
            {
                CloneMetaImpl childCloneCopy = (CloneMetaImpl)((CloneMetaImpl) this.childClones.get(i)).clone(withChildren, copyUUID);
                childCloneCopy.setParentMeta(cloneObj);
                cloneChildClones.add((CloneMeta) childCloneCopy);
            }
        }
        cloneObj.childClones = cloneChildClones;
        cloneObj.cloneAttributes = cloneChildAttributes;
        return cloneObj;
    }

    public String toString()
    {
        StringBuffer sb = new StringBuffer(super.toString());
        sb.append(GeneralUtilities.isBlank(getCmetID()) ? "" : "   (" + getCmetID() + ")");

        if (isReference())
        {
            sb.append("   [Reference - " + getReferenceCloneName() + "]");
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
        return "HL7 v3 Specification Clone Properties";
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
        PropertyDescriptor _isRecursive = new PropertyDescriptor("isReference", beanClass, "isReference", null);
        PropertyDescriptor _rimSource = new PropertyDescriptor("Rim Source", beanClass, "getRimSource", null);
        PropertyDescriptor _cardinality = new PropertyDescriptor("Cardinality", beanClass, "getCardinality", null);
        List<PropertyDescriptor> propList = new ArrayList<PropertyDescriptor>();
        propList.add(_parentName);
        propList.add(_name);
        propList.add(_class);
        propList.add(_isRecursive);
        propList.add(_rimSource);
        propList.add(_cardinality);
        PropertiesResult result = new PropertiesResult();
        result.addPropertyDescriptors(this, propList);
        return result;
    }

}

/**
 * HISTORY      : $Log: not supported by cvs2svn $
 * HISTORY      : Revision 1.54  2006/08/02 18:44:19  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.53  2006/05/04 15:07:50  chene
 * HISTORY      : Change h3s specification from "isRecursive" to "isReference"
 * HISTORY      :
 * HISTORY      : Revision 1.52  2006/05/04 15:06:53  chene
 * HISTORY      : Change h3s specification from "isRecursive" to "isReference"
 * HISTORY      :
 * HISTORY      : Revision 1.51  2006/01/03 19:16:51  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.50  2006/01/03 18:27:13  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.49  2005/12/29 23:06:15  jiangsc
 * HISTORY      : Changed to latest project name.
 * HISTORY      :
 * HISTORY      : Revision 1.48  2005/12/19 20:13:08  chene
 * HISTORY      : Fix for bug when we clone a child, we do not set the parent meta. So cloned child and child point to the same parent
 * HISTORY      :
 * HISTORY      : Revision 1.47  2005/12/15 19:50:43  chene
 * HISTORY      : Collect all schema validation error when parsing the xml
 * HISTORY      :
 * HISTORY      : Revision 1.46  2005/12/07 23:55:01  chene
 * HISTORY      : Saved point for Clone Multiple Implementation
 * HISTORY      :
 * HISTORY      : Revision 1.45  2005/12/01 18:50:32  jiangsc
 * HISTORY      : Fix to Defect #207.
 * HISTORY      :
 * HISTORY      : Revision 1.44  2005/11/11 23:43:38  chene
 * HISTORY      : Add cardinality etc properties
 * HISTORY      :
 * HISTORY      : Revision 1.43  2005/11/10 21:22:04  chene
 * HISTORY      : Correct the Mapping Panel Properties Title
 * HISTORY      :
 * HISTORY      : Revision 1.42  2005/10/18 17:02:49  umkis
 * HISTORY      : no message
 * HISTORY      :
 * HISTORY      : Revision 1.41  2005/10/11 15:42:44  jiangsc
 * HISTORY      : Changed the cosmetic displays to " - " instead of double hyphens.
 * HISTORY      :
 * HISTORY      : Revision 1.40  2005/10/06 19:25:18  chene
 * HISTORY      : Fixed isChoice properties bug
 * HISTORY      :
 * HISTORY      : Revision 1.39  2005/10/03 18:55:40  chene
 * HISTORY      : Refactor CloneMeta, add CloneChoiceMeta
 * HISTORY      :
 * HISTORY      : Revision 1.38  2005/10/03 18:07:52  chene
 * HISTORY      : Remove isSelect attribute of clonemeta
 * HISTORY      :
 * HISTORY      : Revision 1.37  2005/09/28 20:47:17  chene
 * HISTORY      : Add getSortKey at Attribute Meta Interface, clean up visitor pattern
 * HISTORY      :
 * HISTORY      : Revision 1.36  2005/09/26 22:16:36  chene
 * HISTORY      : Add CMET 999900 support
 * HISTORY      :
 * HISTORY      : Revision 1.35  2005/09/22 17:04:08  giordanm
 * HISTORY      : add getLinage()
 * HISTORY      :
 * HISTORY      : Revision 1.34  2005/09/15 17:09:41  chene
 * HISTORY      : SelectChoice GUI/Backend Support
 * HISTORY      :
 * HISTORY      : Revision 1.33  2005/09/14 03:04:12  chene
 * HISTORY      : Add/Remove Optional Clone support
 * HISTORY      :
 * HISTORY      : Revision 1.32  2005/09/12 22:14:30  chene
 * HISTORY      : Saved Point
 * HISTORY      :
 * HISTORY      : Revision 1.31  2005/09/04 17:32:22  chene
 * HISTORY      : Add conformance, cardinality, etc support at HSM subsystem
 * HISTORY      :
 * HISTORY      : Revision 1.30  2005/08/31 16:52:28  chene
 * HISTORY      : Saved point
 * HISTORY      :
 * HISTORY      : Revision 1.29  2005/08/30 19:12:35  jiangsc
 * HISTORY      : Implemented properties
 * HISTORY      :
 * HISTORY      : Revision 1.28  2005/08/26 14:53:22  chene
 * HISTORY      : Add isValidated method into ValidatorResults
 * HISTORY      :
 * HISTORY      : Revision 1.27  2005/08/25 15:46:28  chene
 * HISTORY      : Change not found recursive id null point exception to the warning message
 * HISTORY      :
 * HISTORY      : Revision 1.26  2005/08/23 22:32:37  chene
 * HISTORY      : Add/Remove recursive supported, need to be tested
 * HISTORY      :
 * HISTORY      : Revision 1.25  2005/08/18 18:18:02  chene
 * HISTORY      : Fixed one cardinality related to choice
 * HISTORY      :
 * HISTORY      : Revision 1.24  2005/08/17 20:57:29  jiangsc
 * HISTORY      : Build deep copy methods in clone().
 * HISTORY      :
 * HISTORY      : Revision 1.23  2005/08/04 18:05:06  jiangsc
 * HISTORY      : Refactorized clone() methods to have explicit clone(boolean copyUUID)
 * HISTORY      :
 * HISTORY      : Revision 1.22  2005/08/04 16:05:52  chene
 * HISTORY      : Add three extra space for toString
 * HISTORY      :
 * HISTORY      : Revision 1.21  2005/08/04 16:05:32  chene
 * HISTORY      : Add three extra space for toString
 * HISTORY      :
 * HISTORY      : Revision 1.20  2005/08/04 16:02:00  chene
 * HISTORY      : Add three extra space for toString
 * HISTORY      :
 * HISTORY      : Revision 1.19  2005/08/02 22:19:21  chene
 * HISTORY      : Alway force Clonemeta attribute value to "" if value is null
 * HISTORY      :
 * HISTORY      : Revision 1.18  2005/08/02 16:57:33  chene
 * HISTORY      : Update addAttrbute method
 * HISTORY      :
 * HISTORY      : Revision 1.17  2005/07/30 15:18:36  chene
 * HISTORY      : No more cache. Always create new clone class for cmet
 * HISTORY      :
 */
