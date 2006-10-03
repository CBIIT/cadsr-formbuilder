/**
 * <!-- LICENSE_TEXT_START -->
 * $Header: /share/content/gforge/formbuilder/hl7messagegenerator/config/caadapter/src/gov/nih/nci/hl7/validation/CloneMetaValidator.java,v 1.1 2006-10-03 17:38:44 marwahah Exp $
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


package gov.nih.nci.hl7.validation;

import gov.nih.nci.hl7.clone.meta.CloneChoiceMeta;
import gov.nih.nci.hl7.clone.meta.CloneMeta;
import gov.nih.nci.hl7.util.Log;
import gov.nih.nci.hl7.util.MessageResources;

import java.util.List;

/**
 * Validation for H3S Component. By given any CloneMeta as a root CloneMeta,
 * validate the current CloneMeta as well as its child clones. Support abstract data type validation,
 * choice validation
 *
 * @author OWNER: Eric Chen  Date: Aug 23, 2005
 * @author LAST UPDATE: $Author: marwahah $
 * @version $Revision: 1.1 $
 * @date $$Date: 2006-10-03 17:38:44 $
 * @since caAdapter v1.2
 */


public class CloneMetaValidator extends Validator
{
    public CloneMetaValidator(CloneMeta cloneMeta)
    {
        super(cloneMeta);
    }

    public ValidatorResults validate()
    {
        ValidatorResults validatorResults = new ValidatorResults();
        if (toBeValidatedObject == null)
        {
            Message msg = MessageResources.getMessage("GEN1", new Object[0]);
            validatorResults.addValidatorResult(new ValidatorResult(ValidatorResult.Level.ERROR, msg));
            Log.logError(this, msg.toString());
        }
        else
        {
            CloneMeta cloneMeta = (CloneMeta) toBeValidatedObject;
            validatorResults.addValidatorResults(processClone(cloneMeta));

            // information only by the end of validation
            for (int i = 4; i < 7; i++)
            {
                Message message = MessageResources.getMessage("HSM" + i,
                    new Object[]{});
                ValidatorResult one = new ValidatorResult(ValidatorResult.Level.INFO, message);
                validatorResults.addValidatorResult(one);
            }
        }

        return validatorResults;
    }

    private ValidatorResults processClone(CloneMeta cloneMeta)
    {
        ValidatorResults validatorResults = new ValidatorResults();
        validatorResults.addValidatorResults(new AbstractDatatypeValidator(cloneMeta).validate());
        if (cloneMeta instanceof CloneChoiceMeta)
        {
            CloneChoiceMeta cloneChoiceMeta = (CloneChoiceMeta) cloneMeta;
            validatorResults.addValidatorResults(new ChoiceCloneValidator(cloneChoiceMeta).validate());
        }

        final List<CloneMeta> childClones = cloneMeta.getChildClones();
        for (int i = 0; i < childClones.size(); i++)
        {
            CloneMeta childClone = childClones.get(i);
            validatorResults.addValidatorResults(processClone(childClone));
        }

        return validatorResults;
    }
}

/**
 * HISTORY      : $Log: not supported by cvs2svn $
 * HISTORY      : Revision 1.11  2006/08/02 18:44:25  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.10  2006/01/03 19:16:53  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.9  2006/01/03 18:56:26  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.8  2005/12/30 22:23:30  chene
 * HISTORY      : Update JavaDoc
 * HISTORY      :
 * HISTORY      : Revision 1.7  2005/12/29 23:06:13  jiangsc
 * HISTORY      : Changed to latest project name.
 * HISTORY      :
 * HISTORY      : Revision 1.6  2005/12/29 15:39:06  chene
 * HISTORY      : Optimize imports
 * HISTORY      :
 * HISTORY      : Revision 1.5  2005/11/07 15:59:19  chene
 * HISTORY      : Add Log.logException when we catch the exception besides adding the validation result.
 * HISTORY      :
 * HISTORY      : Revision 1.4  2005/10/04 18:23:53  chene
 * HISTORY      : Add three more HSM info validation.
 * HISTORY      :
 * HISTORY      : Revision 1.3  2005/10/03 19:28:37  chene
 * HISTORY      : Choice Validation Support
 * HISTORY      :
 * HISTORY      : Revision 1.2  2005/10/03 18:07:55  chene
 * HISTORY      : Remove isSelect attribute of clonemeta
 * HISTORY      :
 */
