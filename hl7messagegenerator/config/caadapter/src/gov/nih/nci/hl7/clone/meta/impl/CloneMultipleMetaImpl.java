/**
 * <!-- LICENSE_TEXT_START -->
 * $Header: /share/content/gforge/formbuilder/hl7messagegenerator/config/caadapter/src/gov/nih/nci/hl7/clone/meta/impl/CloneMultipleMetaImpl.java,v 1.1 2006-10-03 17:38:25 marwahah Exp $
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

import gov.nih.nci.hl7.clone.meta.CloneMeta;
import gov.nih.nci.hl7.clone.meta.CloneMultipleMeta;
import gov.nih.nci.hl7.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * This class provides the default implementation of CloneMultipleMeta interface.
 *
 * @author OWNER: Eric Chen  Date: Sep 22, 2005
 * @author LAST UPDATE: $Author: marwahah $
 * @version $Revision: 1.1 $
 * @date $$Date: 2006-10-03 17:38:25 $
 * @since caAdapter v1.2
 */


public class CloneMultipleMetaImpl extends CloneMetaImpl
    implements CloneMultipleMeta
{
    private int sequenceNumber = 1;

    public int getMultipleSequenceNumber()
    {
        return sequenceNumber;
    }

    public void setMultipleSequenceNumber(int number)
    {
        sequenceNumber = number;
    }

    public List<CloneMultipleMeta> getCloneMultipleMetaByName()
    {
        List<CloneMultipleMeta> cloneMultipleMetas = new ArrayList<CloneMultipleMeta>();

        final CloneMeta parentMeta = getParentMeta();

        List<CloneMeta> cloneMetas = parentMeta.getChildClones();

        for (int i = 0; i < cloneMetas.size(); i++)
        {
            CloneMeta cm = cloneMetas.get(i);
            if ((cm instanceof CloneMultipleMeta)
                && getName().equals(cm.getName()))
            {
                cloneMultipleMetas.add((CloneMultipleMeta)cm);
            }
        }

        return cloneMultipleMetas;
    }

    public String toString()
    {
        StringBuffer sb = new StringBuffer(super.toString());
        final List<CloneMultipleMeta> list = getCloneMultipleMetaByName();
        if (list.size() == 0)
        {
            Log.logWarning(this, "Can not find the multiple clone " + getName());
        }
        else if (list.size() == 1)
        {
            sb.append("   [Multiple]");
        }
        else
        {
            sb.append("   [" + getMultipleSequenceNumber() + "]");
        }
        return sb.toString();
    }
}

/**
 * HISTORY      : $Log: not supported by cvs2svn $
 * HISTORY      : Revision 1.7  2006/08/02 18:44:19  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.6  2006/01/03 19:16:51  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.5  2006/01/03 18:27:13  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.4  2005/12/29 15:39:04  chene
 * HISTORY      : Optimize imports
 * HISTORY      :
 * HISTORY      : Revision 1.3  2005/12/08 23:49:20  chene
 * HISTORY      : no message
 * HISTORY      :
 * HISTORY      : Revision 1.2  2005/12/07 23:55:01  chene
 * HISTORY      : Saved point for Clone Multiple Implementation
 * HISTORY      :
 * HISTORY      : Revision 1.1  2005/12/05 22:07:46  chene
 * HISTORY      : Integrate GeneralTask, improve the statics
 * HISTORY      :
 * HISTORY      : Revision 1.3  2005/09/26 22:16:36  chene
 * HISTORY      : Add CMET 999900 support
 * HISTORY      :
 */
