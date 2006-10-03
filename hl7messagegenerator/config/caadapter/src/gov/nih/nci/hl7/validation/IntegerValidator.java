/**
 * <!-- LICENSE_TEXT_START -->
 * $Header: /share/content/gforge/formbuilder/hl7messagegenerator/config/caadapter/src/gov/nih/nci/hl7/validation/IntegerValidator.java,v 1.1 2006-10-03 17:38:44 marwahah Exp $
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

import gov.nih.nci.hl7.util.MessageResources;

/**
 * This class defines interger validation
 *
 * @author OWNER: Scott Jiang
 * @author LAST UPDATE $Author: marwahah $
 * @version Since caAdapter v1.2
 *          revision    $Revision: 1.1 $
 *          date        $Date: 2006-10-03 17:38:44 $
 */
public class IntegerValidator extends Validator
{
	/**
	 * Logging constant used to identify source of log entry, that could be later used to create
	 * logging mechanism to uniquely identify the logged class.
	 */
	private static final String LOGID = "$RCSfile: IntegerValidator.java,v $";

	/**
	 * String that identifies the class version and solves the serial version UID problem.
	 * This String is for informational purposes only and MUST not be made final.
	 *
	 * @see <a href="http://www.visi.com/~gyles19/cgi-bin/fom.cgi?file=63">JBuilder vice javac serial version UID</a>
	 */
	public static String RCSID = "$Header: /share/content/gforge/formbuilder/hl7messagegenerator/config/caadapter/src/gov/nih/nci/hl7/validation/IntegerValidator.java,v 1.1 2006-10-03 17:38:44 marwahah Exp $";


	public IntegerValidator(Object valueToValidate)
	{
		super(valueToValidate);
	}

	public ValidatorResults validate()
	{
		ValidatorResults result = new ValidatorResults();
		if(!(toBeValidatedObject instanceof Integer))
		{
			if(toBeValidatedObject==null)
			{
				Message msg = MessageResources.getMessage("GEN1", new Object[]{"Parameter is null."});
				ValidatorResult one = new ValidatorResult(ValidatorResult.Level.ERROR, msg);
				result.addValidatorResult(one);
			}
			else
			{
				try
				{
					Integer.valueOf(toBeValidatedObject.toString());
				}
				catch(Throwable t)
				{
					Message msg = MessageResources.getMessage("GEN2", new Object[]{toBeValidatedObject.toString()});
					ValidatorResult one = new ValidatorResult(ValidatorResult.Level.ERROR, msg);
					result.addValidatorResult(one);
//                    Log.logException(this, t);
				}
			}
		}
		return result;
	}
}
/**
 * HISTORY      : $Log: not supported by cvs2svn $
 * HISTORY      : Revision 1.12  2006/08/02 18:44:25  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.11  2006/01/03 19:16:53  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.10  2006/01/03 18:56:26  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.9  2005/12/30 22:23:31  chene
 * HISTORY      : Update JavaDoc
 * HISTORY      :
 * HISTORY      : Revision 1.8  2005/12/29 23:06:13  jiangsc
 * HISTORY      : Changed to latest project name.
 * HISTORY      :
 * HISTORY      : Revision 1.7  2005/12/29 15:39:06  chene
 * HISTORY      : Optimize imports
 * HISTORY      :
 * HISTORY      : Revision 1.6  2005/11/07 15:59:19  chene
 * HISTORY      : Add Log.logException when we catch the exception besides adding the validation result.
 * HISTORY      :
 * HISTORY      : Revision 1.5  2005/10/21 15:23:10  chene
 * HISTORY      : Clean up ValidateXML.
 * HISTORY      :
 * HISTORY      : Revision 1.4  2005/10/12 19:05:31  jiangsc
 * HISTORY      : minor change.
 * HISTORY      :
 * HISTORY      : Revision 1.3  2005/08/23 19:18:11  chene
 * HISTORY      : First Cut of Validation
 * HISTORY      :
 * HISTORY      : Revision 1.2  2005/08/23 16:07:58  chene
 * HISTORY      : Updated addValidarResults method
 * HISTORY      :
 * HISTORY      : Revision 1.1  2005/08/22 20:16:16  jiangsc
 * HISTORY      : Integer validator
 * HISTORY      :
 */
