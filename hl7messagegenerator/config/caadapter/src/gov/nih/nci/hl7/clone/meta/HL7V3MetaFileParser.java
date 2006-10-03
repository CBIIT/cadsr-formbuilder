/**
 * <!-- LICENSE_TEXT_START -->
 * $Header: /share/content/gforge/formbuilder/hl7messagegenerator/config/caadapter/src/gov/nih/nci/hl7/clone/meta/HL7V3MetaFileParser.java,v 1.1 2006-10-03 17:38:25 marwahah Exp $
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

import gov.nih.nci.hl7.clone.meta.impl.*;
import gov.nih.nci.hl7.common.MetaException;
import gov.nih.nci.hl7.common.MetaParser;
import gov.nih.nci.hl7.util.GeneralUtilities;
import gov.nih.nci.hl7.util.Log;
import gov.nih.nci.hl7.util.MessageResources;
import gov.nih.nci.hl7.validation.Message;
import gov.nih.nci.hl7.validation.ValidatorResult;
import gov.nih.nci.hl7.validation.ValidatorResults;
import org.hl7.meta.Cardinality;
import org.hl7.meta.CodingStrength;
import org.hl7.meta.Conformance;

import java.io.FileReader;

/**
 * Parse caAdapter V3 Meta File into HL7 V3 meta object
 *
 * @author OWNER: Eric Chen  Date: Jun 3, 2005
 * @author LAST UPDATE: $Author: marwahah $
 * @version Since caAdapter v1.2
 *          Revision $Revision: 1.1 $
 *          Date     $$Date: 2006-10-03 17:38:25 $
 */

public class HL7V3MetaFileParser implements MetaParser {

    private static HL7V3MetaFileParser metaParser = null;

    public static HL7V3MetaFileParser instance()
    {
        if (metaParser == null)
        {
            metaParser = new HL7V3MetaFileParser();
        }
        return metaParser;
    }

    private HL7V3MetaFileParser()
    {
    }


    public HL7V3MetaResult parse(FileReader metafile) throws MetaException {
        C_hl7v3meta c_hl7V3Meta = null;
        HL7V3MetaResult hl7V3MetaResult = new HL7V3MetaResult();
        try {
            c_hl7V3Meta = (C_hl7v3meta) C_hl7v3meta.unmarshalC_hl7v3meta(metafile);
            hl7V3MetaResult.setHl7V3Meta(processRoot(c_hl7V3Meta));
        } catch (Exception e) {
            Log.logException(this, e);
            Message msg = MessageResources.getMessage("GEN0", new Object[]{"Could not parse HL7 meta file."});
            ValidatorResults validatorResults = new ValidatorResults();
            validatorResults.addValidatorResult(new ValidatorResult(ValidatorResult.Level.FATAL, msg));
            hl7V3MetaResult.setValidatorResults(validatorResults);
        }
        return hl7V3MetaResult;
    }

    private HL7V3Meta processRoot(C_hl7v3meta c_hl7V3Meta) {
        HL7V3Meta hl7V3Meta = new HL7V3MetaImpl();
        hl7V3Meta.setMessageID(c_hl7V3Meta.getMessageId());
        hl7V3Meta.setVersion(c_hl7V3Meta.getVersion());
        hl7V3Meta.setRootCloneMeta(processClone(null, c_hl7V3Meta.getC_clone()));
        return hl7V3Meta;
    }

    private CloneMeta processClone(CloneMeta parentCloneMeta , C_clone c_clone) {

        CloneMeta cloneMeta = new CloneMetaImpl();
        final Cardinality cardinality = Cardinality.create(c_clone.getCardinality());

        if ((cardinality != null) && (cardinality.getMax() > 1))
        {
            cloneMeta = new CloneMultipleMetaImpl();
            final int multipleSequenceNumber = c_clone.getMultipleSequenceNumber();
            if (multipleSequenceNumber > 0)
            {
                ((CloneMultipleMeta)cloneMeta).setMultipleSequenceNumber(multipleSequenceNumber);
            }
        }

        String choiceGroupName = c_clone.getChoiceGroupName();
        if (! GeneralUtilities.isBlank(choiceGroupName))
        {
            cloneMeta = new CloneChoiceMetaImpl();
            ((CloneChoiceMeta)cloneMeta).setChoiceGroupName(choiceGroupName);
        }

        cloneMeta.setName(c_clone.getClonename());
        cloneMeta.setUUID(c_clone.getUuid());

        final int sortKey = c_clone.getSortKey();
        if (sortKey > 0)
        {
            cloneMeta.setSortKey(sortKey);
        }

        cloneMeta.setCardinality(Cardinality.create(c_clone.getCardinality()));
        cloneMeta.setRimSource(c_clone.getRimSource());
        cloneMeta.setReferenceCloneUUID(c_clone.getReferenceUuid());
        cloneMeta.setReferenceCloneName(c_clone.getReferenceName());
        cloneMeta.setReference(c_clone.getIsReference());
        cloneMeta.setCmetID(c_clone.getCmetID());
        cloneMeta.setParentMeta(parentCloneMeta);

        C_cloneItem[] cloneItems = c_clone.getC_cloneItem();
        for (int i = 0; i < cloneItems.length; i++) {
            C_cloneItem c_cloneItem = cloneItems[i];
            if (c_cloneItem.getC_attribute() != null) {
                cloneMeta.addAttribute(processAttribute(c_cloneItem.getC_attribute()));
            }
            if (c_cloneItem.getC_clone() != null) {
                cloneMeta.addClone(processClone(cloneMeta, c_cloneItem.getC_clone()));
            }
        }

        return cloneMeta;
    }

    private CloneAttributeMeta processAttribute(C_attribute c_attribute) {
        final Cardinality cardinality = Cardinality.create(c_attribute.getCardinality());
        CloneAttributeMeta cloneAttribute = new CloneAttributeMetaImpl();
        if ((cardinality != null) && (cardinality.getMax() > 1))
        {
            cloneAttribute = new CloneAttributeMultipleMetaImpl();
            final int multipleSequenceNumber = c_attribute.getMultipleSequenceNumber();
            if (multipleSequenceNumber > 0)
            {
                ((CloneAttributeMultipleMeta)cloneAttribute).setMultipleSequenceNumber(multipleSequenceNumber);
            }
        }

        cloneAttribute.setUUID(c_attribute.getUuid());
        cloneAttribute.setName(c_attribute.getName());
        final int sortKey = c_attribute.getSortKey();
        if (sortKey > 0)
        {
            cloneAttribute.setSortKey(sortKey);
        }

        cloneAttribute.setCardinality(cardinality);
        cloneAttribute.setDatatype(c_attribute.getDatatype());
        cloneAttribute.setMandatory(c_attribute.getIsMandatory());
        cloneAttribute.setConformance(Conformance.create(c_attribute.getConformance()));
        cloneAttribute.setCodingStrength(CodingStrength.create(c_attribute.getCodingStrength()));
        cloneAttribute.setDomainName(c_attribute.getDomainName());
        cloneAttribute.setRimSource(c_attribute.getRimSource());
        cloneAttribute.setHL7DefaultValue(c_attribute.getHl7Default());
        cloneAttribute.setSubClass(c_attribute.getSubClass());

        C_attributeItem[] c_attributeItems = c_attribute.getC_attributeItem();
        for (int i = 0; i < c_attributeItems.length; i++) {
            C_attributeItem c_attributeItem = c_attributeItems[i];
            if (c_attributeItem.getC_datatypeField() != null) {
                cloneAttribute.addDatatyepField(processDatatypeField(c_attributeItem.getC_datatypeField()));
            }
            if (c_attributeItem.getC_attribute() != null) {
                cloneAttribute.addAttribute(processAttribute(c_attributeItem.getC_attribute()));
            }
        }
        return cloneAttribute;
    }

    private CloneDatatypeFieldMeta processDatatypeField(C_datatypeField c_datatypeField) {
        CloneDatatypeFieldMeta cloneDatatypeField = new CloneDatatypeFieldMetaImpl();
        cloneDatatypeField.setName(c_datatypeField.getName());
        cloneDatatypeField.setUUID(c_datatypeField.getUuid());
        cloneDatatypeField.setCardinality(Cardinality.create(c_datatypeField.getCardinality()));
        cloneDatatypeField.setUserDefaultValue(c_datatypeField.getUserDefault());
        cloneDatatypeField.setHL7DefaultValue(c_datatypeField.getHl7Default());
        return cloneDatatypeField;
    }
}

/**
 * HISTORY      : $Log: not supported by cvs2svn $
 * HISTORY      : Revision 1.39  2006/08/02 18:44:20  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.38  2006/05/04 15:06:53  chene
 * HISTORY      : Change h3s specification from "isRecursive" to "isReference"
 * HISTORY      :
 * HISTORY      : Revision 1.37  2006/01/03 19:16:51  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.36  2006/01/03 18:27:13  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.35  2005/12/29 23:06:15  jiangsc
 * HISTORY      : Changed to latest project name.
 * HISTORY      :
 * HISTORY      : Revision 1.34  2005/12/29 15:39:04  chene
 * HISTORY      : Optimize imports
 * HISTORY      :
 * HISTORY      : Revision 1.33  2005/12/07 23:55:01  chene
 * HISTORY      : Saved point for Clone Multiple Implementation
 * HISTORY      :
 * HISTORY      : Revision 1.32  2005/11/15 23:02:04  giordanm
 * HISTORY      : no message
 * HISTORY      :
 * HISTORY      : Revision 1.31  2005/11/07 21:23:06  chene
 * HISTORY      : Add datatype cardinality support
 * HISTORY      :
 * HISTORY      : Revision 1.30  2005/11/07 15:39:05  chene
 * HISTORY      : Add Log.logException when we catched the exception
 * HISTORY      :
 * HISTORY      : Revision 1.29  2005/10/06 20:32:54  chene
 * HISTORY      : Add Fatal Error Support
 * HISTORY      :
 * HISTORY      : Revision 1.28  2005/10/03 18:55:42  chene
 * HISTORY      : Refactor CloneMeta, add CloneChoiceMeta
 * HISTORY      :
 * HISTORY      : Revision 1.27  2005/10/03 18:07:53  chene
 * HISTORY      : Remove isSelect attribute of clonemeta
 * HISTORY      :
 * HISTORY      : Revision 1.26  2005/09/30 19:56:28  chene
 * HISTORY      : User Defined Structure Supported
 * HISTORY      :
 * HISTORY      : Revision 1.25  2005/09/29 20:35:13  chene
 * HISTORY      : Support RIM Source for CloneAttributeMeta
 * HISTORY      :
 * HISTORY      : Revision 1.24  2005/09/28 21:14:25  chene
 * HISTORY      : Sortkey supported at HSM file
 * HISTORY      :
 * HISTORY      : Revision 1.23  2005/09/28 20:47:18  chene
 * HISTORY      : Add getSortKey at Attribute Meta Interface, clean up visitor pattern
 * HISTORY      :
 * HISTORY      : Revision 1.22  2005/09/26 19:29:45  chene
 * HISTORY      : Add Clone Attribute Multiple Class in order to support Multiple Attribute
 * HISTORY      :
 * HISTORY      : Revision 1.21  2005/09/26 15:36:56  chene
 * HISTORY      : Add Clone Attribute Multiple Class in order to support Multiple Attribute
 * HISTORY      :
 * HISTORY      : Revision 1.20  2005/09/16 16:20:15  giordanm
 * HISTORY      : HL7V3 parser is not returning a result object not a just a meta object.
 * HISTORY      :
 * HISTORY      : Revision 1.19  2005/09/15 17:09:42  chene
 * HISTORY      : SelectChoice GUI/Backend Support
 * HISTORY      :
 * HISTORY      : Revision 1.18  2005/09/15 16:19:32  giordanm
 * HISTORY      : rename CSVMetaParserResult to CSVMetaResult
 * HISTORY      :
 * HISTORY      : Revision 1.17  2005/09/04 21:41:12  chene
 * HISTORY      : Add Meta Object Parser Mimimum
 * HISTORY      :
 * HISTORY      : Revision 1.16  2005/09/04 17:32:23  chene
 * HISTORY      : Add conformance, cardinality, etc support at HSM subsystem
 * HISTORY      :
 * HISTORY      : Revision 1.15  2005/08/31 16:52:29  chene
 * HISTORY      : Saved point
 * HISTORY      :
 * HISTORY      : Revision 1.14  2005/08/17 20:01:36  chene
 * HISTORY      : Refactor HL7V3MetaFileParser to a singleton
 * HISTORY      :
 * HISTORY      : Revision 1.13  2005/08/03 16:59:05  chene
 * HISTORY      : Add datatype feature at CloneMetaAttribute
 * HISTORY      :
 * HISTORY      : Revision 1.12  2005/08/02 15:18:29  chene
 * HISTORY      : Add HL7 default value and user default value
 * HISTORY      :
 * HISTORY      : Revision 1.11  2005/07/30 15:18:37  chene
 * HISTORY      : No more cache. Always create new clone class for cmet
 * HISTORY      :
 * HISTORY      : Revision 1.10  2005/07/29 17:51:05  chene
 * HISTORY      : Upgrade CloneMeta toString function
 * HISTORY      :
 * HISTORY      : Revision 1.9  2005/07/28 18:22:24  chene
 * HISTORY      : Choice is supported
 * HISTORY      :
 * HISTORY      : Revision 1.8  2005/07/27 21:49:49  chene
 * HISTORY      : Add the abstract data type support
 * HISTORY      :
 * HISTORY      : Revision 1.7  2005/07/27 19:44:54  jiangsc
 * HISTORY      : Corrected addAttribute signature because it was previously spelled incorrectly (a typo).
 * HISTORY      :
 * HISTORY      : Revision 1.6  2005/07/25 21:54:25  chene
 * HISTORY      : Add CMET feature
 * HISTORY      :
 * HISTORY      : Revision 1.5  2005/07/12 20:09:02  jiangsc
 * HISTORY      : Added severity support.
 * HISTORY      :
 * HISTORY      : Revision 1.4  2005/07/11 22:43:00  chene
 * HISTORY      : Add Exception Stuff
 * HISTORY      :
 * HISTORY      : Revision 1.3  2005/07/02 03:32:19  chene
 * HISTORY      : Add the recursive at CloneMeta
 * HISTORY      :
 * HISTORY      : Revision 1.2  2005/06/06 15:17:43  jiangsc
 * HISTORY      : Renamed addAttribut to addAttribute to be consistent.
 * HISTORY      :
 * HISTORY      : Revision 1.1  2005/06/05 04:22:41  chene
 * HISTORY      : Firstcut of Meta Object Parser
 * HISTORY      :
 * HISTORY      : Revision 1.5  2005/06/05 01:36:23  chene
 * HISTORY      : Add HL7V3 Meta Builder
 * HISTORY      :
 * HISTORY      : Revision 1.4  2005/06/03 22:43:02  chene
 * HISTORY      : Add HL7V3 Meta Builder
 * HISTORY      :
 * HISTORY      : Revision 1.3  2005/06/03 19:56:44  chene
 * HISTORY      : Add HL7V3 Meta Parser
 * HISTORY      :
 * HISTORY      : Revision 1.2  2005/06/03 19:41:56  chene
 * HISTORY      : Add HL7V3 Meta Parser
 * HISTORY      :
 * HISTORY      : Revision 1.1  2005/06/03 19:24:13  chene
 * HISTORY      : Added Meta Parser Stuff
 * HISTORY      :
 */
