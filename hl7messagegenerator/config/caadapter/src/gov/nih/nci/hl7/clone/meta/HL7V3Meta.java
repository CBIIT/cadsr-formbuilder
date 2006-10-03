/**
 * <!-- LICENSE_TEXT_START -->
 * $Header: /share/content/gforge/formbuilder/hl7messagegenerator/config/caadapter/src/gov/nih/nci/hl7/clone/meta/HL7V3Meta.java,v 1.1 2006-10-03 17:38:25 marwahah Exp $
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

/**
 * The HL7 V3 Meta is the specialization of the HL7 v3 meta information that reflects an end-user’s
 * customization of the HL7 v3 meta information.
 *
 * @author OWNER: Eric Chen
 * LAST UPDATE: * $Author: marwahah $
 * @version $Revision: 1.1 $
 * @since caAdapter v1.2
 * $Date: 2006-10-03 17:38:25 $
 */
public interface HL7V3Meta extends MetaObject {

    /**
     * HL7 Message Type ID
     * @return String
     */
    public String getMessageID();

    /**
     *
     * @param messaageID
     */
    public void setMessageID(String messaageID);

    /**
     *
     * @return String
     */
    public String getVersion();

    /**
     *
     * @param version
     */
    public void setVersion(String version);

    /**
     * Entry Point of Clone Meta
     * @return
     */
    public CloneMeta getRootCloneMeta();

    /**
     *
     * @param cloneMeta
     */

    public void setRootCloneMeta(CloneMeta cloneMeta);
}

/**
 * HISTORY      : $Log: not supported by cvs2svn $
 * HISTORY      : Revision 1.12  2006/08/02 18:44:20  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.11  2006/01/03 19:16:51  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.10  2006/01/03 18:27:13  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.9  2005/12/29 23:06:15  jiangsc
 * HISTORY      : Changed to latest project name.
 * HISTORY      :
 * HISTORY      : Revision 1.8  2005/12/29 15:39:04  chene
 * HISTORY      : Optimize imports
 * HISTORY      :
 * HISTORY      : Revision 1.7  2005/07/29 17:51:05  chene
 * HISTORY      : Upgrade CloneMeta toString function
 * HISTORY      :
 * HISTORY      : Revision 1.6  2005/06/08 22:29:51  chene
 * HISTORY      : Update Clone Data implementation
 * HISTORY      :
 * HISTORY      : Revision 1.5  2005/06/05 01:36:22  chene
 * HISTORY      : Add HL7V3 Meta Builder
 * HISTORY      :
 * HISTORY      : Revision 1.4  2005/06/03 19:24:12  chene
 * HISTORY      : Added Meta Parser Stuff
 * HISTORY      :
 * HISTORY      : Revision 1.3  2005/06/02 22:09:17  chene
 * HISTORY      : Clone Meta Package
 * HISTORY      :
 * HISTORY      : Revision 1.2  2005/06/02 22:00:46  chene
 * HISTORY      : Clone Meta Package
 * HISTORY      :
 */
