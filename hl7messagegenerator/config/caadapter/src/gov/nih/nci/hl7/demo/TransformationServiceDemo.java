/**
 * $Header: /share/content/gforge/formbuilder/hl7messagegenerator/config/caadapter/src/gov/nih/nci/hl7/demo/TransformationServiceDemo.java,v 1.1 2006-10-03 17:38:26 marwahah Exp $
 *
 * ******************************************************************
 * COPYRIGHT NOTICE
 * ******************************************************************
 *
 *	The caAdapter Software License, Version 1.0
 *
 *	Copyright 2001 SAIC. This software was developed in conjunction with the National Cancer
 *	Institute, and so to the extent government employees are co-authors, any rights in such works
 *	shall be subject to Title 17 of the United States Code, section 105.
 *
 *	Redistribution and use in source and binary forms, with or without modification, are permitted
 *	provided that the following conditions are met:
 *
 *	1. Redistributions of source code must retain the above copyright notice, this list of conditions
 *	and the disclaimer of Article 3, below.  Redistributions in binary form must reproduce the above
 *	copyright notice, this list of conditions and the following disclaimer in the documentation and/or
 *	other materials provided with the distribution.
 *
 *	2.  The end-user documentation included with the redistribution, if any, must include the
 *	following acknowledgment:
 *
 *	"This product includes software developed by the SAIC and the National Cancer
 *	Institute."
 *
 *	If no such end-user documentation is to be included, this acknowledgment shall appear in the
 *	software itself, wherever such third-party acknowledgments normally appear.
 *
 *	3. The names "The National Cancer Institute", "NCI" and "SAIC" must not be used to endorse or
 *	promote products derived from this software.
 *
 *	4. This license does not authorize the incorporation of this software into any proprietary
 *	programs.  This license does not authorize the recipient to use any trademarks owned by either
 *	NCI or SAIC-Frederick.
 *
 *
 *	5. THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED
 *	WARRANTIES, (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 *	MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE) ARE
 *	DISCLAIMED.  IN NO EVENT SHALL THE NATIONAL CANCER INSTITUTE, SAIC, OR
 *	THEIR AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 *	EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 *	PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 *	PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY
 *	OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *	NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *	SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  MODULENAME   : $Workfile: $
 *
 * ********************************************************************
 */
package gov.nih.nci.hl7.demo;

import gov.nih.nci.hl7.map.TransformationResult;
import gov.nih.nci.hl7.map.TransformationService;
import gov.nih.nci.hl7.util.FileUtil;
import gov.nih.nci.hl7.validation.ValidatorResults;

import java.util.List;

/**
 * A tiny driver which demo the usage of TransformationService.
 *
 * @author OWNER: Eric Chen
 * @author LAST UPDATE $Author: marwahah $
 * @version $Revision: 1.1 $
 * @since caAdapter v1.2
 */
public class TransformationServiceDemo {
    public static String RCSID = "$Header: /share/content/gforge/formbuilder/hl7messagegenerator/config/caadapter/src/gov/nih/nci/hl7/demo/TransformationServiceDemo.java,v 1.1 2006-10-03 17:38:26 marwahah Exp $";

    public static void main(String[] args) {
        String mapFile040001 =  FileUtil.getExamplesDirPath() + "/040001/040001.map";
        String csvSource040001 = FileUtil.getExamplesDirPath() + "/040001/040001.csv";
        TransformationService transformationService = new TransformationService(mapFile040001, csvSource040001);
        List<TransformationResult> mapGenerateResults = transformationService.process();
        for (int i = 0; i < mapGenerateResults.size(); i++)
        {
            TransformationResult transformationResult = mapGenerateResults.get(i);
            String hl7V3Message = transformationResult.getHl7V3MessageText();
            System.out.println("Generated HL7 Message:\n" + hl7V3Message);
            ValidatorResults validatorResults = transformationResult.getValidatorResults();
            System.out.println("Validation Results:\n" + validatorResults);
        }


    }
}
