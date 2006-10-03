/**
 * <!-- LICENSE_TEXT_START -->
 * $Header: /share/content/gforge/formbuilder/hl7messagegenerator/config/caadapter/src/gov/nih/nci/hl7/clone/meta/Merge.java,v 1.1 2006-10-03 17:38:25 marwahah Exp $
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
 * Abstract Class for Merge Utility. It preserves user default value from Old CloneDatatypeFieldMeta.
 *
 * @author OWNER: Eric Chen  Date: Aug 17, 2004
 * @author LAST UPDATE: $Author: marwahah $
 * @version $Revision: 1.1 $
 * @date $$Date: 2006-10-03 17:38:25 $
 * @since caAdapter v1.2
 */


abstract public class Merge
{
    protected MetaObject newMetaObject;
    protected MetaObject oldMetaObject;

    protected Merge(MetaObject newMetaObject, MetaObject oldMetaObject)
    {
        this.newMetaObject = newMetaObject;
        this.oldMetaObject = oldMetaObject;
    }

    public void setNewMetaObject(MetaObject newMetaObject)
    {
        this.newMetaObject = newMetaObject;
    }

    public void setOldMetaObject(MetaObject oldMetaObject)
    {
        this.oldMetaObject = oldMetaObject;
    }

    /**
     * User default value is automatically merged from Old Meta Object to new Meta Object
     */
    public void process()
    {
        
        if ((newMetaObject instanceof CloneDatatypeFieldMeta)
            && (oldMetaObject instanceof CloneDatatypeFieldMeta))
        {
            CloneDatatypeFieldMeta newDatatypeField = (CloneDatatypeFieldMeta) newMetaObject;
            CloneDatatypeFieldMeta oldDatatypeField = (CloneDatatypeFieldMeta) oldMetaObject;
            newDatatypeField.setUserDefaultValue(oldDatatypeField.getUserDefaultValue());
        }
    }

}

/**
 * HISTORY      : $Log: not supported by cvs2svn $
 * HISTORY      : Revision 1.9  2006/08/02 18:44:20  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.8  2006/01/03 19:16:51  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.7  2006/01/03 18:27:13  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.6  2005/12/29 23:06:15  jiangsc
 * HISTORY      : Changed to latest project name.
 * HISTORY      :
 * HISTORY      : Revision 1.5  2005/09/29 21:36:52  chene
 * HISTORY      : Support RIM Source for CloneAttributeMeta
 * HISTORY      :
 * HISTORY      : Revision 1.4  2005/08/31 16:52:30  chene
 * HISTORY      : Saved point
 * HISTORY      :
 * HISTORY      : Revision 1.3  2005/08/26 14:53:22  chene
 * HISTORY      : Add isValidated method into ValidatorResults
 * HISTORY      :
 * HISTORY      : Revision 1.2  2005/08/25 15:59:16  chene
 * HISTORY      : Update MergeUUID function
 * HISTORY      :
 * HISTORY      : Revision 1.1  2005/08/17 21:27:49  chene
 * HISTORY      : Refactor MetaBuilder to be singleton
 * HISTORY      :
 */
