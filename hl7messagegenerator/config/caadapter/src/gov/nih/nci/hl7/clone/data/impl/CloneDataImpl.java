/**
 * <!-- LICENSE_TEXT_START -->
 * $Header: /share/content/gforge/formbuilder/hl7messagegenerator/config/caadapter/src/gov/nih/nci/hl7/clone/data/impl/CloneDataImpl.java,v 1.1 2006-10-03 17:38:25 marwahah Exp $
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


package gov.nih.nci.hl7.clone.data.impl;

import gov.nih.nci.hl7.clone.data.CloneAttributeData;
import gov.nih.nci.hl7.clone.data.CloneData;
import gov.nih.nci.hl7.clone.meta.CloneMeta;
import gov.nih.nci.hl7.common.DataObjectImpl;
import gov.nih.nci.hl7.util.GeneralUtilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
* Default implemetation for CloneData.
*
* @author OWNER: Eric Chen  Date: Jun 8, 2005
* @author LAST UPDATE: $Author: marwahah $

* @since caAdapter v1.2
* @version $Revision: 1.1 $
* @date     $$Date: 2006-10-03 17:38:25 $
*/


public class CloneDataImpl extends DataObjectImpl implements CloneData
{

    private List<CloneAttributeData> cloneAttributes = new ArrayList<CloneAttributeData>();
    private List<CloneData> childClones = new ArrayList<CloneData>();

    public CloneDataImpl(CloneMeta metaObject)
    {
        super(metaObject);
    }

    public List<Map<String, Object>> getValueMapList(String attributeName)
    {
        if (attributeName == null) return null;

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < cloneAttributes.size(); i++)
        {
            CloneAttributeData cloneAttributeData = cloneAttributes.get(i);
            if (attributeName.equalsIgnoreCase(cloneAttributeData.getName()))
            {
                final Map<String, Object> valueMap = cloneAttributeData.getValueMap();
                if ((valueMap != null) && valueMap.size() > 0)
                {
                    list.add(valueMap);
                }
            }
        }
        return list;
    }

    public List<CloneAttributeData> getAttributes(String attributeName)
    {
        List<CloneAttributeData> list = new ArrayList<CloneAttributeData>();

        if (attributeName == null) return list;

        for (int i = 0; i < cloneAttributes.size(); i++)
        {
            CloneAttributeData cloneAttributeData = cloneAttributes.get(i);
            if (attributeName.equalsIgnoreCase(cloneAttributeData.getName()))
            {
                list.add(cloneAttributeData);
            }
        }
        return list;
    }

    /**
     * @param childCloneName
     * @return
     */
    public List<CloneData> getChildClones(String childCloneName)
    {
        List<CloneData> list = new ArrayList<CloneData>();

        if (GeneralUtilities.isBlank(childCloneName)) return list;

        for (int i = 0; i < childClones.size(); i++)
        {
            CloneData cloneData = childClones.get(i);
            if (childCloneName.equalsIgnoreCase(cloneData.getName()))
            {
                list.add(cloneData);
            }
        }
        return list;

    }

    public String getSubClass(String attributeName)
    {
        if (attributeName == null) return null;

        for (int i = 0; i < cloneAttributes.size(); i++)
        {
            CloneAttributeData cloneAttributeData = cloneAttributes.get(i);
            if (attributeName.equalsIgnoreCase(cloneAttributeData.getName()))
            {
                return cloneAttributeData.getSubClass();
            }
        }
        return null;
    }

    public List<CloneAttributeData> getAttributes()
    {
        return cloneAttributes;
    }

    public void addAttribute(CloneAttributeData cloneAttribute)
    {
        cloneAttributes.add(cloneAttribute);
    }

    public void addAttributes(List<CloneAttributeData> cloneAttributeDatas)
    {
        cloneAttributes.addAll(cloneAttributeDatas);
    }

    public List<CloneData> getChildClones()
    {
        return childClones;
    }

    public void addClone(CloneData cloneData)
    {
        childClones.add(cloneData);
    }

    public void addClones(List<CloneData> cloneDatas)
    {
        childClones.addAll(cloneDatas);
    }

    public String toString()
    {
        return getName(); 
    }
}

/**
 * HISTORY      : $Log: not supported by cvs2svn $
 * HISTORY      : Revision 1.18  2006/08/02 18:44:19  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.17  2006/06/09 03:25:20  chene
 * HISTORY      : Saved Point
 * HISTORY      :
 * HISTORY      : Revision 1.16  2006/01/03 19:16:51  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.15  2006/01/03 18:27:13  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.14  2005/12/29 23:06:14  jiangsc
 * HISTORY      : Changed to latest project name.
 * HISTORY      :
 * HISTORY      : Revision 1.13  2005/12/29 15:39:04  chene
 * HISTORY      : Optimize imports
 * HISTORY      :
 * HISTORY      : Revision 1.12  2005/12/28 21:31:24  chene
 * HISTORY      : Updating JavaDOC
 * HISTORY      :
 * HISTORY      : Revision 1.11  2005/12/14 22:48:02  chene
 * HISTORY      : Promote backend org.hl7.xml.builder.AttributeCardinalityException: AuthorOrPerformer3.typeCode: HMD specifies cardinality 1..1, RIM object has 0 to validation message as well
 * HISTORY      :
 * HISTORY      : Revision 1.10  2005/12/13 22:31:24  chene
 * HISTORY      : Fix the classCode bad mapping error because of the improvement of CloneCode
 * HISTORY      :
 * HISTORY      : Revision 1.9  2005/08/18 18:18:01  chene
 * HISTORY      : Fixed one cardinality related to choice
 * HISTORY      :
 * HISTORY      : Revision 1.8  2005/07/20 16:47:59  giordanm
 * HISTORY      : minor changes associated with MapProcessor development.
 * HISTORY      :
 * HISTORY      : Revision 1.7  2005/07/19 20:23:45  giordanm
 * HISTORY      : More Map Processor work.
 * HISTORY      :
 * HISTORY      : Revision 1.6  2005/07/07 17:09:34  giordanm
 * HISTORY      : changes to the DataObject interface and DataObjectImpl class..
 * HISTORY      :
 * HISTORY      : Revision 1.5  2005/06/09 03:30:03  chene
 * HISTORY      : First Cut of RimGraphGen
 * HISTORY      :
 * HISTORY      : Revision 1.4  2005/06/09 02:16:58  chene
 * HISTORY      : First Cut of RimGraphGen
 * HISTORY      :
 */

