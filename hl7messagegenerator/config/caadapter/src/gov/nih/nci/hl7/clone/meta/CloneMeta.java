/**
 * <!-- LICENSE_TEXT_START -->
 * $Header: /share/content/gforge/formbuilder/hl7messagegenerator/config/caadapter/src/gov/nih/nci/hl7/clone/meta/CloneMeta.java,v 1.1 2006-10-03 17:38:25 marwahah Exp $
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


package gov.nih.nci.hl7.clone.meta;

import gov.nih.nci.hl7.common.MetaObject;
import org.hl7.meta.Cardinality;

import java.util.List;

/**
 * HL7 Clone Class meta information used for mapping purpose. CloneMeta name is HL7
 * association name.
 *
 * @author OWNER: Eric Chen
 * @author LAST UPDATE: $Author: marwahah $

 * @since caAdapter v1.2
 * @version $Revision: 1.1 $
 * @date     $$Date: 2006-10-03 17:38:25 $
 *
 */

public interface CloneMeta extends MetaObject{

    int getSortKey();

    void setSortKey(int sortKey);

    /**
     * Clone Class Attributes
     * @return List<CloneAttributeMeta>
     */
    public List<CloneAttributeMeta> getAttributes();

    /**
     *
     * @param cloneAttribute
     */
    public void addAttribute(CloneAttributeMeta cloneAttribute);

    /**
     * Child Clone Classes
     * @return List<CloneMeta>
     */
    public List<CloneMeta> getChildClones();

    /**
     * @param cloneMeta
     */
    public void addClone(CloneMeta cloneMeta);

    /**
     *
     * @return boolean
     */
    boolean isReference();

    /**
     *
     * @param isReference
     */
    void setReference(boolean isReference);

     /**
     * @return String
     */
    String getReferenceCloneUUID();

    /**
     * @param recursiveCloneUUID
     */
    void setReferenceCloneUUID(String recursiveCloneUUID);

   /**
     * The Reference Clone which includes Recursive and Re-Used
     * @return String
     */
    String getReferenceCloneName();

   /**
     * The Reference Clone which includes Recursive and Re-Used
     * @param referenceCloneName
     */
    void setReferenceCloneName(String referenceCloneName);

     /**
      * @return String
      */
     public String getCmetID();

     /**
      * @param cmetID
      */
     void setCmetID(String cmetID);

     /**
	  * Return the parent clone meta object. Return null if this is the root clone.
	  * @return
	  */
	 CloneMeta getParentMeta();

	 /**
	  * Set the parent clone meta object.
	  * @param parentMeta
	  */
	 void setParentMeta(CloneMeta parentMeta);

     /**
      * Convenient method to find child attribute by name
      * @param attributeName
      * @return
      */
     CloneAttributeMeta getAttributeByName(String attributeName);

    /**
     * Convenient method to find child clone by name
     * @param cloneMetaName
     * @return
     */
     CloneMeta getChildCloneByName(String cloneMetaName);

     Cardinality getCardinality();

     void setCardinality(Cardinality cardinality);

     String getRimSource();

     void setRimSource(String rimSource);

    String getLinage();

	/**
	 * Return the type of this object.
	 * @return the type of this object.
	 */
	String getType();
 }

 /**
 * HISTORY      : $Log: not supported by cvs2svn $
 * HISTORY      : Revision 1.36  2006/08/02 18:44:20  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.35  2006/05/04 15:06:53  chene
 * HISTORY      : Change h3s specification from "isRecursive" to "isReference"
 * HISTORY      :
 * HISTORY      : Revision 1.34  2006/05/03 21:58:11  chene
 * HISTORY      : Depreciated MetSource
 * HISTORY      :
 * HISTORY      : Revision 1.33  2006/01/03 19:16:51  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.32  2006/01/03 18:27:13  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.31  2005/12/29 23:06:15  jiangsc
 * HISTORY      : Changed to latest project name.
 * HISTORY      :
 * HISTORY      : Revision 1.30  2005/12/29 15:39:04  chene
 * HISTORY      : Optimize imports
 * HISTORY      :
 * HISTORY      : Revision 1.29  2005/12/07 23:55:01  chene
 * HISTORY      : Saved point for Clone Multiple Implementation
 * HISTORY      :
 * HISTORY      : Revision 1.28  2005/12/01 18:50:32  jiangsc
 * HISTORY      : Fix to Defect #207.
 * HISTORY      :
 * HISTORY      : Revision 1.27  2005/11/11 22:50:54  chene
 * HISTORY      : Add comments
 * HISTORY      :
 * HISTORY      : Revision 1.26  2005/10/03 18:55:41  chene
 * HISTORY      : Refactor CloneMeta, add CloneChoiceMeta
 * HISTORY      :
 * HISTORY      : Revision 1.25  2005/10/03 18:07:53  chene
 * HISTORY      : Remove isSelect attribute of clonemeta
 * HISTORY      :
 * HISTORY      : Revision 1.24  2005/09/26 22:16:37  chene
 * HISTORY      : Add CMET 999900 support
 * HISTORY      :
 * HISTORY      : Revision 1.23  2005/09/26 15:36:56  chene
 * HISTORY      : Add Clone Attribute Multiple Class in order to support Multiple Attribute
 * HISTORY      :
 * HISTORY      : Revision 1.22  2005/09/22 17:04:20  giordanm
 * HISTORY      : add getLinage()
 * HISTORY      :
 * HISTORY      : Revision 1.21  2005/09/15 17:09:42  chene
 * HISTORY      : SelectChoice GUI/Backend Support
 * HISTORY      :
 * HISTORY      : Revision 1.20  2005/09/14 03:04:13  chene
 * HISTORY      : Add/Remove Optional Clone support
 * HISTORY      :
 * HISTORY      : Revision 1.19  2005/09/08 19:37:00  chene
 * HISTORY      : Saved point
 * HISTORY      :
 * HISTORY      : Revision 1.18  2005/09/04 17:32:22  chene
 * HISTORY      : Add conformance, cardinality, etc support at HSM subsystem
 * HISTORY      :
 * HISTORY      : Revision 1.17  2005/08/31 16:52:28  chene
 * HISTORY      : Saved point
 * HISTORY      :
 * HISTORY      : Revision 1.16  2005/08/23 22:32:38  chene
 * HISTORY      : Add/Remove recursive supported, need to be tested
 * HISTORY      :
 * HISTORY      : Revision 1.15  2005/08/18 18:18:03  chene
 * HISTORY      : Fixed one cardinality related to choice
 * HISTORY      :
 * HISTORY      : Revision 1.14  2005/08/03 22:14:27  chene
 * HISTORY      : Add datatype feature at CloneMetaAttribute
 * HISTORY      :
 * HISTORY      : Revision 1.13  2005/07/28 18:22:24  chene
 * HISTORY      : Choice is supported
 * HISTORY      :
 * HISTORY      : Revision 1.12  2005/07/27 18:33:43  jiangsc
 * HISTORY      : Added setParentMeta and getParentMeta functions.
 * HISTORY      :
 */
