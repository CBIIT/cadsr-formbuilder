/**
 * <!-- LICENSE_TEXT_START -->
 * $Header: /share/content/gforge/formbuilder/hl7messagegenerator/config/caadapter/src/gov/nih/nci/hl7/vocabulary/CaBioRuntime.java,v 1.1 2006-10-03 17:38:44 marwahah Exp $
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


package gov.nih.nci.hl7.vocabulary;

import gov.nih.nci.evs.query.EVSQuery;
import gov.nih.nci.evs.query.EVSQueryImpl;
import gov.nih.nci.hl7.util.Config;
import gov.nih.nci.hl7.util.Log;
import gov.nih.nci.system.applicationservice.ApplicationService;
import org.hl7.CTSMAPI.*;
import org.hl7.types.INT;
import org.hl7.types.BL;
import org.hl7.types.impl.BLimpl;
import org.hl7.types.impl.INTlongAdapter;
import org.hl7.types.impl.STjlStringAdapter;

import java.util.List;

/**
 * A CTS implementation that utilizes NCICB's EVS.
 *
 * @author OWNER: Matthew Giordano
 * @author LAST UPDATE $Author: marwahah $
 * @version $Revision: 1.1 $
 * @date $Date: 2006-10-03 17:38:44 $
 * @since caAdapter v1.2
 */

public class CaBioRuntime implements org.hl7.CTSMAPI.RuntimeOperations
{
	private static final String LOGID = "$RCSfile: CaBioRuntime.java,v $";
	public static String RCSID = "$Header: /share/content/gforge/formbuilder/hl7messagegenerator/config/caadapter/src/gov/nih/nci/hl7/vocabulary/CaBioRuntime.java,v 1.1 2006-10-03 17:38:44 marwahah Exp $";

	public static final String VOCABNAME = "HL7";
//	public static final String VOCABNAME = "HL7_V3";
	public static final String OID_VALUE = "OID";
	public static final String DELIMETER = "#";

	// IdentificationOperations methods.
	public org.hl7.types.ST getServiceName() throws org.hl7.CTSMAPI.UnexpectedError
	{
		throw new UnexpectedError();
	}

	public org.hl7.types.ST getServiceVersion() throws org.hl7.CTSMAPI.UnexpectedError
	{
		throw new UnexpectedError();
	}

	public org.hl7.types.ST getServiceDescription() throws org.hl7.CTSMAPI.UnexpectedError
	{
		throw new UnexpectedError();
	}

	public org.hl7.types.ST getHL7ReleaseVersion() throws org.hl7.CTSMAPI.UnexpectedError
	{
		throw new UnexpectedError();
	}

	public org.hl7.CTSMAPI.CTSVersionId getCTSVersion() throws org.hl7.CTSMAPI.UnexpectedError
	{
		throw new UnexpectedError();
	}

	// MessageRuntimeOperations methods.
	public org.hl7.types.ST[] getSupportedMatchAlgorithms() throws org.hl7.CTSMAPI.UnexpectedError
	{
		throw new UnexpectedError();
	}

	public org.hl7.types.ST[] getSupportedVocabularyDomains(org.hl7.types.ST matchText, org.hl7.types.ST matchAlgorithm_code, int timeout, int sizeLimit) throws org.hl7.CTSMAPI.BadlyFormedMatchText, org.hl7.CTSMAPI.UnknownMatchAlgorithm, org.hl7.CTSMAPI.TimeoutError, org.hl7.CTSMAPI.UnexpectedError
	{
		throw new UnexpectedError();
	}

	public org.hl7.CTSMAPI.ValidateCodeReturn validateCode(org.hl7.types.ST vocabularyDomain_name, org.hl7.types.CD codeToValidate, org.hl7.types.ST applicationContext_code, org.hl7.types.BL activeConceptsOnly, org.hl7.types.BL errorCheckOnly) throws org.hl7.CTSMAPI.UnknownVocabularyDomain, org.hl7.CTSMAPI.UnexpectedError, org.hl7.CTSMAPI.UnknownApplicationContextCode, org.hl7.CTSMAPI.NoApplicableValueSet
	{
		INT numErrors = INTlongAdapter.valueOf(0);
		INT numWarnings = INTlongAdapter.valueOf(0);
		ValidationDetail vd = null;

		//        // TODO : remove this!!!
		//        // temporarily redirect System.out becuase there are debugging lines in caBIO.
		//        PrintStream p = System.out;
		//        System.setOut(new PrintStream(new ByteArrayOutputStream()));
		//        System.setOut(p);

//		DescLogicConcept dlc = new DescLogicConcept();

		//We are searching for concepts based on an EVS property.
		//That property has the name "OID" and its value is in the format of codesystemid/mnumonic.
		//For example "2.16.840.1.113883.5.6/ACCM"
		String propertyValue = codeToValidate.codeSystem().toString() + DELIMETER + codeToValidate.code().toString();

		// Find the EVS concepts with these specified properties.
//		String[] concepts = null;
		List concepts = null;
		try
		{
			ApplicationService appService = getApplicationService();
			EVSQuery evsQuery = new EVSQueryImpl();
			evsQuery.getConceptWithPropertyMatching(VOCABNAME, OID_VALUE, propertyValue, 100);
			concepts = appService.evsSearch(evsQuery);
					//			concepts = dlc.findConceptWithPropertyMatching(VOCABNAME, "OID", propertyValue, 100);
		}
		catch (Exception e)
		{
			Log.logException(this, e);
		}

		//If you cannot find the code, return false
		if (concepts == null || concepts.size() == 0)
		{
			numErrors = INTlongAdapter.valueOf(1);
			vd = new ValidationDetail(codeToValidate, null, null, STjlStringAdapter.valueOf(propertyValue + " could not be found in EVS"));
		}

		return new ValidateCodeReturn(numErrors, numWarnings, new ValidationDetail[]{vd});
	}

	public org.hl7.CTSMAPI.ValidateCodeReturn validateTranslation(org.hl7.types.ST vocabularyDomain_name, org.hl7.types.CD codeToValidate, org.hl7.types.ST applicationContext_code, org.hl7.types.BL activeConceptsOnly, org.hl7.types.BL errorCheckOnly) throws org.hl7.CTSMAPI.UnknownVocabularyDomain, org.hl7.CTSMAPI.UnknownApplicationContextCode, org.hl7.CTSMAPI.UnexpectedError
	{
		throw new UnexpectedError();
	}

	public org.hl7.types.CD translateCode(org.hl7.types.ST vocabularyDomain_name, org.hl7.types.CD fromCode, org.hl7.types.UID toCodeSystemId, org.hl7.types.ST toApplicationContext_code) throws org.hl7.CTSMAPI.UnknownVocabularyDomain, org.hl7.CTSMAPI.UnknownCodeSystem, org.hl7.CTSMAPI.UnknownApplicationContextCode, org.hl7.CTSMAPI.UnableToTranslate, org.hl7.CTSMAPI.UnexpectedError
	{
		throw new UnexpectedError();
	}

	public org.hl7.types.CD fillInDetails(org.hl7.types.CD codeToFillIn, org.hl7.types.ST displayLanguage_code) throws org.hl7.CTSMAPI.UnknownCodeSystem, org.hl7.CTSMAPI.UnknownConceptCode, org.hl7.CTSMAPI.UnknownLanguage, org.hl7.CTSMAPI.UnexpectedError, org.hl7.CTSMAPI.NoApplicableDesignationFound
	{
		throw new UnexpectedError();
	}

	public org.hl7.types.BL subsumes(org.hl7.types.CD parentCode, org.hl7.types.CD childCode) throws org.hl7.CTSMAPI.UnknownCodeSystem, org.hl7.CTSMAPI.UnknownConceptCode, org.hl7.CTSMAPI.SubsumptionNotSupported, org.hl7.CTSMAPI.UnrecognizedQualifier, org.hl7.CTSMAPI.QualifiersNotSupported, org.hl7.CTSMAPI.UnexpectedError
	{
		//        // TODO : remove this!!!
		//        // temporarily redirect System.out becuase there are debugging lines in caBIO.
		//        PrintStream p = System.out;
		//        System.setOut(new PrintStream(new ByteArrayOutputStream()));
		//        System.setOut(p);

		ApplicationService appService = getApplicationService();

		//We are searching for concepts based on an EVS property.
		//That property has the name "OID" and its value is in the format of codesystemid/mnumonic.
		//For example "2.16.840.1.113883.5.6/ACCM"
		String parentPropertyValue = parentCode.codeSystem().toString() + DELIMETER + parentCode.code().toString();
		String childPropertyValue = childCode.codeSystem().toString() + DELIMETER + childCode.code().toString();

		// Find the EVS concepts with these specified properties.
		List parentConcepts = null;
		List childConcepts = null;
		try
		{
			EVSQuery evsQuery = new EVSQueryImpl();
			evsQuery.getConceptWithPropertyMatching(VOCABNAME, OID_VALUE, parentPropertyValue, 100);
			parentConcepts = appService.evsSearch(evsQuery);

			evsQuery = new EVSQueryImpl();
			evsQuery.getConceptWithPropertyMatching(VOCABNAME, OID_VALUE, childPropertyValue, 100);
			childConcepts = appService.evsSearch(evsQuery);
			//			parentConcepts = dlc.findConceptWithPropertyMatching(VOCABNAME, "OID", parentPropertyValue, 100);
//			childConcepts = dlc.findConceptWithPropertyMatching(VOCABNAME, "OID", childPropertyValue, 100);
		}
		catch (Exception e)
		{
			Log.logException(this, e);
		}

		//If you cannot find the child or parent, return false
		if (parentConcepts == null || parentConcepts.size() == 0)
		{
			throw new UnknownConceptCode(new ConceptId(parentCode.codeSystem(), parentCode.code()));
		}
		if (childConcepts == null || childConcepts.size() == 0)
		{
			throw new UnknownConceptCode(new ConceptId(childCode.codeSystem(), childCode.code()));
		}

//		// There should only be one concept w/ that property in EVS, so take the first one.
//		String parentConcept = (String) parentConcepts.get(0);
//		String childConcept = (String) childConcepts.get(0);

		//handle multiple return results for both parent and child concepts.
		int parentConceptsSize = parentConcepts.size();
		int childConceptResultsSize = childConcepts.size();
		boolean isSubConcept = false;
		for (int i = 0; i < parentConceptsSize; i++)
		{
			String parentConcept = (String) parentConcepts.get(i);
			for (int j = 0; j < childConceptResultsSize; j++)
			{
				if (isSubConcept == false)
				{//only proceed the verification if isSubConcept is false, so as to save processing time.
					String childConcept = (String) childConcepts.get(j);
					try
					{
						isSubConcept = answersWhetherSubConcept(appService, parentConcept, childConcept);
					}
					catch (Exception e)
					{
						Log.logException(this, e);
					}
				}
			}//end of for j
		}//end of for i

		BL isSubConceptBL = BLimpl.valueOf(isSubConcept);
		return isSubConceptBL;
	}

	public org.hl7.types.BL areEquivalent(org.hl7.types.CD code1, org.hl7.types.CD code2) throws org.hl7.CTSMAPI.UnknownCodeSystem, org.hl7.CTSMAPI.UnknownConceptCode, org.hl7.CTSMAPI.SubsumptionNotSupported, org.hl7.CTSMAPI.UnrecognizedQualifier, org.hl7.CTSMAPI.QualifiersNotSupported, org.hl7.CTSMAPI.UnexpectedError
	{
		throw new UnexpectedError();
	}

	public org.hl7.CTSMAPI.ValueSetExpansion[] lookupValueSetExpansion(org.hl7.types.ST vocabularyDomain_name, org.hl7.types.ST applicationContext_code, org.hl7.types.ST language_code, org.hl7.types.BL expandAll, int timeout, int sizeLimit) throws org.hl7.CTSMAPI.UnknownVocabularyDomain, org.hl7.CTSMAPI.UnknownApplicationContextCode, org.hl7.CTSMAPI.UnknownLanguage, org.hl7.CTSMAPI.NoApplicableValueSet, org.hl7.CTSMAPI.TimeoutError, org.hl7.CTSMAPI.UnexpectedError
	{
		throw new UnexpectedError();
	}

	public org.hl7.CTSMAPI.ValueSetExpansion[] expandValueSetExpansionContext(byte[] expansionContext) throws org.hl7.CTSMAPI.InvalidExpansionContext, org.hl7.CTSMAPI.TimeoutError, org.hl7.CTSMAPI.UnexpectedError
	{
		throw new UnexpectedError();
	}

	private ApplicationService getApplicationService()
	{
		ApplicationService appService = ApplicationService.getRemoteInstance(Config.EVS_DEFAULT_CONNECTION);
		return appService;
	}

	private boolean answersWhetherSubConcept(ApplicationService appService, String parentConcept, String childConcept) throws Exception
	{
		if (parentConcept.equals(childConcept))
		{
			return true;
		}
		EVSQuery evsQuery = new EVSQueryImpl();
		evsQuery.isSubConcept(VOCABNAME, childConcept, parentConcept);
		List subconceptResults = appService.evsSearch(evsQuery);
		Boolean resultBoolean = (subconceptResults.size() != 0) ? (Boolean) subconceptResults.get(0) : Boolean.FALSE;
		boolean result = resultBoolean.booleanValue();
		return result;
	}
}
