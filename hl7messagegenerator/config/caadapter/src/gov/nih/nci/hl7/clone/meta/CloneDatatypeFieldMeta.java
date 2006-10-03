/**
 * <!-- LICENSE_TEXT_START -->
 * $Header: /share/content/gforge/formbuilder/hl7messagegenerator/config/caadapter/src/gov/nih/nci/hl7/clone/meta/CloneDatatypeFieldMeta.java,v 1.1 2006-10-03 17:38:25 marwahah Exp $
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

 /**
 * Description of the class
 *
 * @author OWNER: Eric Chen  Date: Jun 2, 2005
 * @author LAST UPDATE: $Author: marwahah $

 * @version  Since caAdapter v1.2
 * Revision $Revision: 1.1 $
 * Date     $$Date: 2006-10-03 17:38:25 $
 */

public interface CloneDatatypeFieldMeta extends MetaObject {

    /**
     *
     * @return String
     */
    public String getHL7DefaultValue();

    /**
     *
     * @param hl7DefaultValue
     */
    public void setHL7DefaultValue(String hl7DefaultValue);

     /**
      *
      * @return String
      */
     public String getUserDefaultValue();

     /**
      *
      * @param userDefaultValue
      */
     public void setUserDefaultValue(String userDefaultValue);


	/**
	 * Return the parent meta object, could be either a CloneMeta or an AttributeMeta.
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
      * @return
      */
     Cardinality getCardinality();

     /**
      * @param cardinality
      */
     void setCardinality(Cardinality cardinality);

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
 * HISTORY      : Revision 1.15  2006/08/02 18:44:20  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.14  2006/01/18 17:46:44  giordanm
 * HISTORY      : no message
 * HISTORY      :
 * HISTORY      : Revision 1.13  2006/01/03 19:16:51  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.12  2006/01/03 18:27:13  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.11  2005/12/29 23:06:15  jiangsc
 * HISTORY      : Changed to latest project name.
 * HISTORY      :
 * HISTORY      : Revision 1.10  2005/12/01 18:50:32  jiangsc
 * HISTORY      : Fix to Defect #207.
 * HISTORY      :
 * HISTORY      : Revision 1.9  2005/11/07 21:23:06  chene
 * HISTORY      : Add datatype cardinality support
 * HISTORY      :
 * HISTORY      : Revision 1.8  2005/08/02 15:18:28  chene
 * HISTORY      : Add HL7 default value and user default value
 * HISTORY      :
 * HISTORY      : Revision 1.7  2005/07/27 18:33:43  jiangsc
 * HISTORY      : Added setParentMeta and getParentMeta functions.
 * HISTORY      :
 * HISTORY      : Revision 1.6  2005/07/02 03:32:19  chene
 * HISTORY      : Add the recursive at CloneMeta
 * HISTORY      :
 * HISTORY      : Revision 1.5  2005/06/03 22:43:02  chene
 * HISTORY      : Add HL7V3 Meta Builder
 * HISTORY      :
 * HISTORY      : Revision 1.4  2005/06/03 17:01:39  chene
 * HISTORY      : Added Clone Meta Stuff
 * HISTORY      :
 * HISTORY      : Revision 1.3  2005/06/02 22:09:01  chene
 * HISTORY      : Clone Meta Package
 * HISTORY      :
 * HISTORY      : Revision 1.2  2005/06/02 22:00:46  chene
 * HISTORY      : Clone Meta Package
 * HISTORY      :
 * HISTORY      : Revision 1.1  2005/06/02 21:34:18  chene
 * HISTORY      : Add CloneDatatypeFieldMeta
 * HISTORY      :
 */
