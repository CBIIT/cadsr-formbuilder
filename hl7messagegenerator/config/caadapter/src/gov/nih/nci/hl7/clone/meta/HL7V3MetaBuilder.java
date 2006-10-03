/**
 * <!-- LICENSE_TEXT_START -->
 * $Header: /share/content/gforge/formbuilder/hl7messagegenerator/config/caadapter/src/gov/nih/nci/hl7/clone/meta/HL7V3MetaBuilder.java,v 1.1 2006-10-03 17:38:25 marwahah Exp $
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
import gov.nih.nci.hl7.common.MetaBuilderBase;
import gov.nih.nci.hl7.common.MetaException;
import gov.nih.nci.hl7.common.MetaObject;
import gov.nih.nci.hl7.util.GeneralUtilities;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.ValidationException;
import org.hl7.meta.Cardinality;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.List;

/**
 * Build a HL7 v3 meta XML file. It autogenerates sort key if not existed.
 *
 * @author OWNER: Eric Chen  Date: Jun 3, 2005
 * @author LAST UPDATE: $Author: marwahah $
 * @version $Revision: 1.1 $
 * @date $$Date: 2006-10-03 17:38:25 $
 * @since caAdapter v1.2
 */


public class HL7V3MetaBuilder extends MetaBuilderBase
{

    private static HL7V3MetaBuilder metaBuilder = null;

    private HL7V3MetaBuilder()
    {
    }

    public void build(OutputStream outputStream, MetaObject meta) throws MetaException
    {

        if (!(meta instanceof HL7V3Meta))
        {
            throw new MetaException("Parameter meta object should be an instance of CSVMeta", null);
        }
        else
        {

            try
            {

                HL7V3Meta hl7V3Meta = (HL7V3Meta) meta;

                C_hl7v3meta c_hl7V3Meta = new C_hl7v3meta();
                c_hl7V3Meta.setC_clone(processClone(hl7V3Meta.getRootCloneMeta()));
                c_hl7V3Meta.setMessageId(hl7V3Meta.getMessageID());
                c_hl7V3Meta.setVersion(hl7V3Meta.getVersion());

                // set up the castor objects
                StringWriter marshalString = new StringWriter();
                //c_hl7V3Meta.marshal(marshalString);
                Marshaller marshaller = new Marshaller(marshalString);
                marshaller.setSuppressXSIType(true);
                marshaller.marshal(c_hl7V3Meta);

                ByteArrayInputStream is = new ByteArrayInputStream(marshalString.toString().getBytes());

                // transform it.
                Transformer t = TransformerFactory.newInstance().newTransformer();
                t.setOutputProperty(OutputKeys.INDENT, "yes");
                t.transform(new StreamSource(is), new StreamResult(outputStream));

            }
            catch (IOException e)
            {
                throw new MetaException(e.getMessage(), e);
            }
            catch (MarshalException e)
            {
                throw new MetaException(e.getMessage(), e);
            }
            catch (ValidationException e)
            {
                throw new MetaException(e.getMessage(), e);
            }
            catch (TransformerException e)
            {
                throw new MetaException(e.getMessage(), e);
            }
        }
    }

    private C_clone processClone(CloneMeta cloneMeta)
    {
        C_clone c_clone = new C_clone();
        c_clone.setUuid(cloneMeta.getUUID());
        c_clone.setClonename(cloneMeta.getName());

        final int sortKey = cloneMeta.getSortKey();
        if (sortKey > 0)
        {
            c_clone.setSortKey(sortKey);
        }
        else  // create the sortKey if not existed
        {
            final CloneMeta parentMeta = cloneMeta.getParentMeta();
            if (parentMeta == null)
            {
                c_clone.setSortKey(1);
            }
            else
            {
                c_clone.setSortKey(parentMeta.getChildClones().lastIndexOf(cloneMeta) + 1);
            }
        }


        final Cardinality cardinality = cloneMeta.getCardinality();
        if (cardinality != null)
        {
            c_clone.setCardinality(cardinality.toString());
            if ((cardinality.getMax() > 1) && (cloneMeta instanceof CloneMultipleMeta))
            {
                c_clone.setMultipleSequenceNumber(((CloneMultipleMeta)cloneMeta).getMultipleSequenceNumber());
            }
        }
        if (!GeneralUtilities.isBlank(cloneMeta.getRimSource()))
        {
            c_clone.setRimSource(cloneMeta.getRimSource());
        }

        if (!GeneralUtilities.isBlank(cloneMeta.getReferenceCloneUUID()))
        {
            c_clone.setReferenceUuid(cloneMeta.getReferenceCloneUUID());
        }

        if (!GeneralUtilities.isBlank(cloneMeta.getReferenceCloneName()))
        {
            c_clone.setReferenceName(cloneMeta.getReferenceCloneName());
        }

        if (cloneMeta.isReference())
        {
            c_clone.setIsReference(cloneMeta.isReference());
        }

        if (!GeneralUtilities.isBlank(cloneMeta.getCmetID()))
        {
            c_clone.setCmetID(cloneMeta.getCmetID());
        }

        if (cloneMeta instanceof CloneChoiceMeta) {
            CloneChoiceMeta cloneChoiceMeta = (CloneChoiceMeta) cloneMeta;
            if (!GeneralUtilities.isBlank(cloneChoiceMeta.getChoiceGroupName()))
            {
                c_clone.setChoiceGroupName(cloneChoiceMeta.getChoiceGroupName());
            }

        }


        final List<CloneAttributeMeta> attributes = cloneMeta.getAttributes();
        for (int i = 0; i < attributes.size(); i++)
        {
            CloneAttributeMeta cloneAttributeMeta = attributes.get(i);
            C_cloneItem cloneItem = new C_cloneItem();
            cloneItem.setC_attribute(processAttribute(cloneAttributeMeta));
            c_clone.addC_cloneItem(cloneItem);
        }

        final List<CloneMeta> childClones = cloneMeta.getChildClones();
        for (int i = 0; i < childClones.size(); i++)
        {
            CloneMeta meta = childClones.get(i);
            C_cloneItem cloneItem = new C_cloneItem();
            cloneItem.setC_clone(processClone(meta));
            c_clone.addC_cloneItem(cloneItem);
        }

        return c_clone;
    }

    private C_attribute processAttribute(CloneAttributeMeta attributeMeta)
    {
        C_attribute c_attriibute = new C_attribute();
        c_attriibute.setUuid(attributeMeta.getUUID());
        c_attriibute.setName(attributeMeta.getName());

        final int sortKey = attributeMeta.getSortKey();
        if (sortKey > 0)
        {
            c_attriibute.setSortKey(sortKey);
        }
        else  // create the sortKey if not existed
        {
            final MetaObject parentMeta = attributeMeta.getParentMeta();
            if (parentMeta instanceof CloneMeta)
            {
                CloneMeta cloneMeta = (CloneMeta) parentMeta;
                c_attriibute.setSortKey(cloneMeta.getAttributes().lastIndexOf(attributeMeta) + 1);
            }
            if (parentMeta instanceof CloneAttributeMeta)
            {
                CloneAttributeMeta cloneAttributeMeta = (CloneAttributeMeta) parentMeta;
                c_attriibute.setSortKey(cloneAttributeMeta.getChildAttributes().lastIndexOf(attributeMeta) + 1);
            }
        }

        final Cardinality cardinality = attributeMeta.getCardinality();
        if (cardinality != null)
        {
            c_attriibute.setCardinality(cardinality.toString());
            if ((cardinality.getMax() > 1) && (attributeMeta instanceof CloneAttributeMultipleMeta))
            {
                c_attriibute.setMultipleSequenceNumber(((CloneAttributeMultipleMeta)attributeMeta).getMultipleSequenceNumber());
            }
        }

        if (attributeMeta.getConformance() != null)
        {
            c_attriibute.setConformance(attributeMeta.getConformance().toString());
        }

        if (attributeMeta.getCodingStrength() != null)
        {
            c_attriibute.setCodingStrength(attributeMeta.getCodingStrength().toString());
        }

        if (!GeneralUtilities.isBlank(attributeMeta.getDomainName()))
        {
            c_attriibute.setDomainName(attributeMeta.getDomainName());
        }

        if (!GeneralUtilities.isBlank(attributeMeta.getRimSource()))
        {
            c_attriibute.setRimSource(attributeMeta.getRimSource());
        }

        if (!GeneralUtilities.isBlank(attributeMeta.getHL7DefaultValue()))
        {
            c_attriibute.setHl7Default(attributeMeta.getHL7DefaultValue());
        }

        if (attributeMeta.isMandatory())
        {
            c_attriibute.setIsMandatory(attributeMeta.isMandatory());
        }

        if (!GeneralUtilities.isBlank(attributeMeta.getSubClass()))
        {
            c_attriibute.setSubClass(attributeMeta.getSubClass());
        }

        if (!GeneralUtilities.isBlank(attributeMeta.getDatatype()))
        {
            c_attriibute.setDatatype(attributeMeta.getDatatype());
        }

        final List<CloneDatatypeFieldMeta> datatypeFields = attributeMeta.getDatatypeFields();
        for (int i = 0; i < datatypeFields.size(); i++)
        {
            CloneDatatypeFieldMeta meta = datatypeFields.get(i);
            C_attributeItem attributeItem = new C_attributeItem();
            attributeItem.setC_datatypeField(processDatatypeField(meta));
            c_attriibute.addC_attributeItem(attributeItem);
        }

        final List<CloneAttributeMeta> childAttributeClones = attributeMeta.getChildAttributes();
        for (int i = 0; i < childAttributeClones.size(); i++)
        {
            CloneAttributeMeta meta = childAttributeClones.get(i);
            C_attributeItem attributeItem = new C_attributeItem();
            attributeItem.setC_attribute(processAttribute(meta));
            c_attriibute.addC_attributeItem(attributeItem);
        }

        return c_attriibute;

    }

    private C_datatypeField processDatatypeField(CloneDatatypeFieldMeta datatypeFieldMeta)
    {
        C_datatypeField c_DatatypeField = new C_datatypeField();
        c_DatatypeField.setName(datatypeFieldMeta.getName());
        c_DatatypeField.setUuid(datatypeFieldMeta.getUUID());
        final Cardinality cardinality = datatypeFieldMeta.getCardinality();
        if (cardinality != null)
        {
            c_DatatypeField.setCardinality(cardinality.toString());
        }

        if (!GeneralUtilities.isBlank(datatypeFieldMeta.getUserDefaultValue()))
        {
            c_DatatypeField.setUserDefault(datatypeFieldMeta.getUserDefaultValue());
        }
        if (!GeneralUtilities.isBlank(datatypeFieldMeta.getHL7DefaultValue()))
        {
            c_DatatypeField.setHl7Default(datatypeFieldMeta.getHL7DefaultValue());
        }
        return c_DatatypeField;
    }

    public static HL7V3MetaBuilder getInstance()
    {
        if (metaBuilder == null)
        {
            metaBuilder = new HL7V3MetaBuilder();
        }
        return metaBuilder;
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
 * HISTORY      : Revision 1.32  2005/11/07 21:23:06  chene
 * HISTORY      : Add datatype cardinality support
 * HISTORY      :
 * HISTORY      : Revision 1.31  2005/11/02 22:36:06  chene
 * HISTORY      : change "\\" to "/"
 * HISTORY      :
 * HISTORY      : Revision 1.30  2005/10/26 17:35:12  chene
 * HISTORY      : Edit History comment
 * HISTORY      :
 * HISTORY      : Revision 1.29  2005/10/26 16:32:37  chene
 * HISTORY      : .scm => .scs   (Segement CSV Specification)
 * HISTORY      : .hsm => .h3s   (HL7 v3 Specification)
 * HISTORY      : .dbm => .dms   (Database Specification)
 * HISTORY      : .ffm => .fls (Function Library Specification)
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
 * HISTORY      : Revision 1.24  2005/09/29 16:49:15  chene
 * HISTORY      : Move temporary generated HSM files to /test/mapping tool test files\h3s directory
 * HISTORY      :
 * HISTORY      : Revision 1.23  2005/09/29 16:40:16  chene
 * HISTORY      : Sortkey supported at HSM file
 * HISTORY      :
 * HISTORY      : Revision 1.22  2005/09/28 21:14:25  chene
 * HISTORY      : Sortkey supported at HSM file
 * HISTORY      :
 * HISTORY      : Revision 1.21  2005/09/28 20:47:18  chene
 * HISTORY      : Add getSortKey at Attribute Meta Interface, clean up visitor pattern
 * HISTORY      :
 * HISTORY      : Revision 1.20  2005/09/26 15:36:56  chene
 * HISTORY      : Add Clone Attribute Multiple Class in order to support Multiple Attribute
 * HISTORY      :
 * HISTORY      : Revision 1.19  2005/09/15 17:09:42  chene
 * HISTORY      : SelectChoice GUI/Backend Support
 * HISTORY      :
 * HISTORY      : Revision 1.18  2005/09/04 17:32:23  chene
 * HISTORY      : Add conformance, cardinality, etc support at HSM subsystem
 * HISTORY      :
 * HISTORY      : Revision 1.17  2005/08/31 16:52:28  chene
 * HISTORY      : Saved point
 * HISTORY      :
 * HISTORY      : Revision 1.16  2005/08/17 22:49:31  chene
 * HISTORY      : Refactor MetaBuilder to be singleton
 * HISTORY      :
 * HISTORY      : Revision 1.15  2005/08/17 22:48:53  chene
 * HISTORY      : Refactor MetaBuilder to be singleton
 * HISTORY      :
 * HISTORY      : Revision 1.14  2005/08/17 21:27:48  chene
 * HISTORY      : Refactor MetaBuilder to be singleton
 * HISTORY      :
 * HISTORY      : Revision 1.13  2005/08/04 21:41:13  chene
 * HISTORY      : Support temporaly file saving
 * HISTORY      :
 * HISTORY      : Revision 1.12  2005/08/03 19:18:59  giordanm
 * HISTORY      : update instances that were not valid according to their respective schemas before - builders are not NOT building out the xsi:type - regression is updated to validate all of these instances.
 * HISTORY      :
 * HISTORY      : Revision 1.11  2005/08/03 16:59:05  chene
 * HISTORY      : Add datatype feature at CloneMetaAttribute
 * HISTORY      :
 * HISTORY      : Revision 1.10  2005/08/03 15:20:05  chene
 * HISTORY      : Fixed Castor Schema Validation Error
 * HISTORY      :
 * HISTORY      : Revision 1.9  2005/08/02 15:18:29  chene
 * HISTORY      : Add HL7 default value and user default value
 * HISTORY      :
 * HISTORY      : Revision 1.8  2005/07/28 18:22:24  chene
 * HISTORY      : Choice is supported
 * HISTORY      :
 * HISTORY      : Revision 1.7  2005/07/27 21:49:49  chene
 * HISTORY      : Add the abstract data type support
 * HISTORY      :
 */
