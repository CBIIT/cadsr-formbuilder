/**
 * <!-- LICENSE_TEXT_START -->
 * $Header: /share/content/gforge/formbuilder/hl7messagegenerator/config/caadapter/src/gov/nih/nci/hl7/clone/meta/CloneAttributeMeta.java,v 1.1 2006-10-03 17:38:25 marwahah Exp $
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
import org.hl7.meta.CodingStrength;
import org.hl7.meta.Conformance;

import java.util.List;

/**
 * HL7 v3 Specification(H3S) Attribute Meta Class
 *
 * @author OWNER: Eric Chen  Date: Jun 2, 2005
 * @author LAST UPDATE: $Author: marwahah $
 * @version Since caAdapter v1.2
 *          revision $Revision: 1.1 $
 *          date     $$Date: 2006-10-03 17:38:25 $
 */
public interface CloneAttributeMeta extends MetaObject
{
    /**
     * Sort key derived
     * @return
     */
    int getSortKey();

    /**
     *
     * @param sortKey
     */
    void setSortKey(int sortKey);

    /**
     * Check if it is abstract data type(ANY, QTY)
     * @return
     */
    boolean isAbstract();

    /**
     * Datatype Name, such as II, ANY, QTY
     *
     * @param abstractClass
     */
    void setDatatype(String abstractClass);

    /**
     * Datatype Name, such as II, ANY, QTY
     * @return
     */
    String getDatatype();

    /**
     *
     * @return
     */
    public boolean isMandatory();

    /**
     *
     * @param mandatory
     */
    public void setMandatory(boolean mandatory);

    /**
     * The specialization for ANY and QTY
     * @return String
     */
    String getSubClass();

    /**
     * @param subClass
     */
    void setSubClass(String subClass);


    /**
     * @return String
     */
    public String getHL7DefaultValue();

    /**
     * @param hl7DefaultValue
     */
    public void setHL7DefaultValue(String hl7DefaultValue);


    /**
     * @return
     */
    Cardinality getCardinality();

    /**
     * @param cardinality
     */
    void setCardinality(Cardinality cardinality);


    /**
     * @return List<CloneDatatypeFieldMeta>
     */
    List<CloneDatatypeFieldMeta> getDatatypeFields();

    /**
     * @param datatypeFieldMeta
     */
    void addDatatyepField(CloneDatatypeFieldMeta datatypeFieldMeta);

    /**
     * @return List<CloneAttributeMeta>
     */
    List<CloneAttributeMeta> getChildAttributes();

    /**
     * @param cloneAttribute
     */
    void addAttribute(CloneAttributeMeta cloneAttribute);

    /**
     * Return the parent meta object, could be either a CloneMeta or a CloneAttributeMeta.
     *
     * @return
     */
    MetaObject getParentMeta();

    /**
     * Set the parent meta object, could be either a CloneMeta or an AttributeMeta.
     *
     * @param parentMeta
     */
    void setParentMeta(MetaObject parentMeta);

    /**
     * Convenient method to find child attribute by name.
     *
     * @param name
     * @return
     */
    public CloneAttributeMeta getChildAttributeByName(String name);

    /**
     * @param datatypeFieldName
     * @return
     */
    CloneDatatypeFieldMeta getDatatypeFieldByName(String datatypeFieldName);

    /**
     * HL7 coding strength
     *
     * @return
     */
    CodingStrength getCodingStrength();

    /**
     * HL7 coding strength
     *
     * @param codingStrength
     */
    void setCodingStrength(CodingStrength codingStrength);

    /**
     * HL7 conformance
     *
     * @return
     */
    Conformance getConformance();

    /**
     * HL7 conformance
     *
     * @param conformance
     */
    void setConformance(Conformance conformance);

    /**
     * Check if the attribute allowed to create multiple instance to facilate many to many mapping
     *
     * @return
     */
    public boolean isMultiple();

    String getDomainName();

    void setDomainName(String domainName);

    String getRimSource();

    void setRimSource(String rimSource);

    boolean isMappable();

	/**
	 * Return the type of this object.
	 *
	 * @return the type of this object.
	 */
	String getType();
    String getLinage();
}

/**
 * HISTORY      : $Log: not supported by cvs2svn $
 * HISTORY      : Revision 1.27  2006/08/02 18:44:19  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.26  2006/01/18 17:46:44  giordanm
 * HISTORY      : no message
 * HISTORY      :
 * HISTORY      : Revision 1.25  2006/01/03 19:16:51  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.24  2006/01/03 18:27:13  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.23  2005/12/29 23:06:14  jiangsc
 * HISTORY      : Changed to latest project name.
 * HISTORY      :
 * HISTORY      : Revision 1.22  2005/12/29 15:39:04  chene
 * HISTORY      : Optimize imports
 * HISTORY      :
 * HISTORY      : Revision 1.21  2005/12/14 22:48:02  chene
 * HISTORY      : Promote backend org.hl7.xml.builder.AttributeCardinalityException: AuthorOrPerformer3.typeCode: HMD specifies cardinality 1..1, RIM object has 0 to validation message as well
 * HISTORY      :
 * HISTORY      : Revision 1.20  2005/12/01 18:50:32  jiangsc
 * HISTORY      : Fix to Defect #207.
 * HISTORY      :
 * HISTORY      : Revision 1.19  2005/11/14 23:32:12  giordanm
 * HISTORY      : BUG: 192
 * HISTORY      :
 * HISTORY      : Revision 1.18  2005/10/03 18:07:52  chene
 * HISTORY      : Remove isSelect attribute of clonemeta
 * HISTORY      :
 * HISTORY      : Revision 1.17  2005/09/29 20:35:12  chene
 * HISTORY      : Support RIM Source for CloneAttributeMeta
 * HISTORY      :
 * HISTORY      : Revision 1.16  2005/09/28 20:47:17  chene
 * HISTORY      : Add getSortKey at Attribute Meta Interface, clean up visitor pattern
 * HISTORY      :
 * HISTORY      : Revision 1.15  2005/09/26 15:36:55  chene
 * HISTORY      : Add Clone Attribute Multiple Class in order to support Multiple Attribute
 * HISTORY      :
 * HISTORY      : Revision 1.14  2005/09/04 17:32:22  chene
 * HISTORY      : Add conformance, cardinality, etc support at HSM subsystem
 * HISTORY      :
 * HISTORY      : Revision 1.13  2005/08/18 18:18:03  chene
 * HISTORY      : Fixed one cardinality related to choice
 * HISTORY      :
 * HISTORY      : Revision 1.12  2005/08/08 02:06:21  chene
 * HISTORY      : Fixed most of Schema non-validated bugs, second round
 * HISTORY      :
 * HISTORY      : Revision 1.11  2005/08/03 16:59:05  chene
 * HISTORY      : Add datatype feature at CloneMetaAttribute
 * HISTORY      :
 * HISTORY      : Revision 1.10  2005/07/27 22:04:27  chene
 * HISTORY      : Add the abstract data type support
 * HISTORY      :
 * HISTORY      : Revision 1.9  2005/07/27 21:52:02  chene
 * HISTORY      : Add the abstract data type support
 * HISTORY      :
 * HISTORY      : Revision 1.8  2005/07/27 21:49:48  chene
 * HISTORY      : Add the abstract data type support
 * HISTORY      :
 * HISTORY      : Revision 1.7  2005/07/27 19:44:54  jiangsc
 * HISTORY      : Corrected addAttribute signature because it was previously spelled incorrectly (a typo).
 * HISTORY      :
 * HISTORY      : Revision 1.6  2005/07/27 18:33:43  jiangsc
 * HISTORY      : Added setParentMeta and getParentMeta functions.
 * HISTORY      :
 * HISTORY      : Revision 1.5  2005/07/27 18:13:31  chene
 * HISTORY      : QTY and ANY subtypes
 * HISTORY      :
 * HISTORY      : Revision 1.4  2005/06/03 22:43:02  chene
 * HISTORY      : Add HL7V3 Meta Builder
 * HISTORY      :
 * HISTORY      : Revision 1.3  2005/06/03 17:01:39  chene
 * HISTORY      : Added Clone Meta Stuff
 * HISTORY      :
 */
