/**
 * <!-- LICENSE_TEXT_START -->
 * $Header: /share/content/gforge/formbuilder/hl7messagegenerator/config/caadapter/src/gov/nih/nci/hl7/clone/meta/HL7V3MetaObjectParser.java,v 1.1 2006-10-03 17:38:25 marwahah Exp $
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
import gov.nih.nci.hl7.common.MetaObject;
import gov.nih.nci.hl7.common.SystemException;
import gov.nih.nci.hl7.util.Config;
import gov.nih.nci.hl7.util.GeneralUtilities;
import gov.nih.nci.hl7.util.HL7V3Util;
import gov.nih.nci.hl7.util.UUIDGenerator;
import org.hl7.meta.*;
import org.hl7.meta.impl.DatatypeMetadataFactoryDatatypes;
import org.hl7.meta.impl.JdomMessageTypeLoader;
import org.hl7.meta.util.MetaUtils;
import org.hl7.util.FactoryException;
import org.hl7.xml.DatatypePresenter;
import org.hl7.xml.DatatypePresenterBase;
import org.hl7.xml.DatatypePresenterFactory;
import org.hl7.xml.RTOPresenter;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Parse HL7 v3 Meta Object into caAdapter HL7 v3 specification object. There are two modes to create the HL7 v3
 * specification object: maximum and minimum mode. The H3S file should be created using minimum mode.
 * Default is minumum mode.
 *
 * @author OWNER: Eric Chen  Date: Jun 3, 2005
 * @author LAST UPDATE: $Author: marwahah $
 * @version $Revision: 1.1 $
 * @date $$Date: 2006-10-03 17:38:25 $
 * @since caAdapter v1.2
 */

public class HL7V3MetaObjectParser
{
//    protected Map<CloneClass, CloneMeta> classCloneMap = new HashMap<CloneClass, CloneMeta>();

    public static enum Mode
    {
        MAXIMUM,
        MINIMUM
    }

    private Mode mode = Mode.MINIMUM;


    public HL7V3MetaObjectParser()
    {
        this.mode = Mode.MINIMUM;
    }

    /**
     * Parse HL7 Meta Object using maximum or minumum mode
     *
     * @param mode
     */
    public HL7V3MetaObjectParser(Mode mode)
    {
        this.mode = mode;
    }

    /**
     * @return Meta Object Parsing Mode
     */
    public Mode getMode()
    {
        return mode;
    }

    /**
     * @param mode
     */
    public void setMode(Mode mode)
    {
        this.mode = mode;
    }

    /**
     * Generate HL7 v3 Specification meta object.
     * @param messageType
     * @return HL7 V3 Meta Object
     * @throws MetaException
     */
    public MetaObject parse(MessageType messageType) throws MetaException
    {
        HL7V3Meta hl7V3Meta = new HL7V3MetaImpl();
        hl7V3Meta.setMessageID(messageType.getId());
        hl7V3Meta.setVersion(Config.CAADAPTER_VERSION);

        final CloneClass rootClass = messageType.getRootClass();
        CloneMeta cloneMeta = new CloneMetaImpl();
        cloneMeta.setName(rootClass.getName());
        cloneMeta.setCardinality(Cardinality.create("1..1"));

        cloneMeta.setUUID(UUIDGenerator.getUniqueString());
        hl7V3Meta.setRootCloneMeta(cloneMeta);

//        classCloneMap.put(rootClass, cloneMeta);

        processFeatures(rootClass, cloneMeta, null);
        return hl7V3Meta;
    }

    /**
     * Ignore generalized association
     *
     * @param cloneClass
     * @param cloneMeta
     * @param choiceContext
     * @throws MetaException
     */
    protected void processFeatures(CloneClass cloneClass, CloneMeta cloneMeta, ChoiceAssociation choiceContext) throws MetaException
    {

        for (Iterator it = cloneClass.iterateChildren(); it.hasNext();)
        {
            Feature feature = (Feature) it.next();
            if (JdomMessageTypeLoader.GENERALIZES.equals(feature.getRimPropertyName()))
            {
                continue;  // ignore generalized feature if any
            }

            if (feature instanceof Attribute)
            {
                final CloneAttributeMeta cloneAttribute = processAttribute((Attribute) feature);
                cloneMeta.addAttribute(cloneAttribute);
            }
            else if (feature instanceof Association)
            {
                final CloneMeta childCloneMeta = internalProcessAssociation((Association) feature, cloneMeta, choiceContext);
                if (childCloneMeta != null)
                {
                    childCloneMeta.setParentMeta(cloneMeta);
                    cloneMeta.addClone(childCloneMeta);
                }
            }
        }
    }

    /**
     * For Internal Use Only
     *
     * @param association
     * @param cloneMeta
     * @param choiceContext
     * @return
     * @throws MetaException
     */
    private CloneMeta internalProcessAssociation(Association association, CloneMeta cloneMeta, ChoiceAssociation choiceContext)
        throws MetaException
    {
        // check the mode
        if (getMode() == Mode.MINIMUM)
        {
            final Cardinality cardinality = association.getCardinality();
            // check if required
            if ((cardinality == null) || (cardinality.getMin() < 1))
                return null;
        }

//            final MetSource metSource = association.getMetSource();
            final boolean isReference = association.isReference();

            if (isReference)
            {
                if (!(association instanceof CmetAssociation))
                {
                    cloneMeta.setReferenceCloneName(association.getName());
                    cloneMeta.setReference(true);
                    return null;
                }
            }

            return processAssociation(association, choiceContext);

    }

    /**
     * Create CloneMeta related to the association. If it's choice association, only create one clone meta.
     *
     * @param association
     * @return CloneMeta
     * @throws MetaException
     */
    public CloneMeta processAssociation(Association association, ChoiceAssociation choiceContext)
        throws MetaException
    {
        CloneMeta childCloneMeta = null;
        if (association instanceof ChoiceAssociation)
        {
            ChoiceAssociation choiceAssociation = (ChoiceAssociation) association;
            childCloneMeta = processChoiceAssociation(choiceAssociation);
        }
        else
        {
            childCloneMeta = processCloneAndCmetAssociation(association, choiceContext);
        }
        // added to class clone cache
//            classCloneMap.put(targetClone, childCloneMeta);

//            processFeatures(targetClone, childCloneMeta, choiceContext);
        return childCloneMeta;
    }

    /**
     * Create the choice clone class for the choice association.
     *
     * @param choiceAssociation
     */
    private CloneMeta processChoiceAssociation(ChoiceAssociation choiceAssociation)
    {

        CloneChoiceMeta cloneChoiceMeta = new CloneChoiceMetaImpl();
        cloneChoiceMeta.setName(choiceAssociation.getName());
        cloneChoiceMeta.setUUID(UUIDGenerator.getUniqueString());
        cloneChoiceMeta.setCardinality(choiceAssociation.getCardinality());
        cloneChoiceMeta.setChoiceGroupName(choiceAssociation.getName());
        return cloneChoiceMeta;

//            for (Iterator<Association> itChoices = choiceAssociation.iterateChoices();
//                 itChoices.hasNext();)
//            {
//                Association association = itChoices.next();
//                // Create ONLY ONE clone class randomly for choice association.
//                return processCloneAndCmetAssociation(association, choiceAssociation);
//            }
//
//            return null;
    }

    /**
     * Genereate a child clone meta based on association, which will be added to the clone meta
     *
     * @param association   //     * @param cloneMeta     the clone meta
     * @param choiceContext the container of the current association
     * @throws MetaException
     */

    private CloneMeta processCloneAndCmetAssociation(Association association, ChoiceAssociation choiceContext)
        throws MetaException
    {
//        System.out.println("association = " + association);

        try
        {
            final Cardinality cardinality = association.getCardinality();

            CloneMeta childCloneMeta = new CloneMetaImpl();

            if ((cardinality != null) && (cardinality.getMax() > 1))
            {
                childCloneMeta = new CloneMultipleMetaImpl();
                ((CloneMultipleMeta) childCloneMeta).setMultipleSequenceNumber(1);
            }

            if (choiceContext != null) // this association is part of choice association group
            {
                childCloneMeta = new CloneChoiceMetaImpl();
                ((CloneChoiceMeta) childCloneMeta).setChoiceGroupName(choiceContext.getName());
            }

            childCloneMeta.setName(association.getName());
            childCloneMeta.setUUID(UUIDGenerator.getUniqueString());
            childCloneMeta.setCardinality(association.getCardinality());
            childCloneMeta.setRimSource(association.getRimClass());

//        childCloneMeta.setParentMeta(cloneMeta);

//        classCloneMap.put(targetClone, childCloneMeta);


            if (association instanceof CmetAssociation)
            {
                final String cmetId = ((CmetAssociation) association).getCmetId();
//                System.out.println(cmetId);
                childCloneMeta.setCmetID(cmetId);
            }

//            processFeatures(association.getTarget(), childCloneMeta, choiceContext);
            processFeatures(association.getTarget(), childCloneMeta, null);
            return childCloneMeta;
        }
        catch (LoaderException e)
        {
            throw new MetaException(e.getMessage(), e);
        }
    }

    protected CloneAttributeMeta processAttribute(Attribute attribute) throws MetaException
    {
        final Cardinality cardinality = attribute.getCardinality();

        CloneAttributeMeta cloneAttribute = new CloneAttributeMetaImpl();
        if ((cardinality != null) && (cardinality.getMax() > 1))
        {
            cloneAttribute = new CloneAttributeMultipleMetaImpl();
            ((CloneAttributeMultipleMeta) cloneAttribute).setMultipleSequenceNumber(1);
        }

        cloneAttribute.setUUID(UUIDGenerator.getUniqueString());
        cloneAttribute.setName(attribute.getName());
        cloneAttribute.setRimSource(attribute.getRimClass());

        // FIXME: Kludge -- sortKey is not supported at RIM 202, but at RIM 207.
        final int sortKey = attribute.getSortKey();
        if (sortKey > 0)
        {
            cloneAttribute.setSortKey(sortKey);
        }
//        else
//        {
//            cloneAttribute.setSortKey(((CloneMeta)cloneAttribute.getParentMeta()).getAttributes().size() + 1);
//        }
        cloneAttribute.setCardinality(cardinality);
        cloneAttribute.setMandatory(attribute.isMandatory());
        cloneAttribute.setConformance(attribute.getConformance());
        cloneAttribute.setCodingStrength(attribute.getCodingStrength());
        final String[] domains = attribute.getDomains();

        if ((domains != null) && (domains.length > 0))
        {
            cloneAttribute.setDomainName(domains[0]);
        }

        if (attribute.isStructural())
        {
            final String defaultValue = MetaUtils.getDefaultValue(attribute);

            if (!GeneralUtilities.isBlank(defaultValue))
            { //if the default value is blank, we are going to create the datatype fields
                cloneAttribute.setHL7DefaultValue(defaultValue);
                if (Arrays.asList(HL7V3Util.getHL7DefinedValueStructureAttributes()).contains(attribute.getName()))
                {
                    return cloneAttribute;
                }
            }
        }

        Datatype datatype = attribute.getDatatype();
        if (datatype instanceof SimpleDatatype)
        {
            loadSimpleDatatype(cloneAttribute, (SimpleDatatype) datatype);
        }
        else if (datatype instanceof ParametrizedDatatype)
        {
            // SET<II>, BAG<INT>, LIST<ST>, IVL<TS>, IVL<PQ>, RTO<QTY, QTY>
            ParametrizedDatatype parametrizedDatatype = (ParametrizedDatatype) datatype;
            if (!parametrizedDatatype.isPure())
            {
                // for instance IVL<TS>, RTO<QTY, QTY>
                // meta data thinks it's parametrized Data type because HMD data type contains with <PQ>
                loadParametrizedDatatype(cloneAttribute, parametrizedDatatype);
            }
            else
            {
                // SET<II>, BAG<INT>, LIST<ST>, SET< RTO<QTY, QTY> >
                Datatype dt = (Datatype) parametrizedDatatype.getParameter().get(0);
                loadDatatype(cloneAttribute, dt);
            }
        }
        else
        {
            /* Oops, we should never get here! */
            throw new SystemException("unhandled feature " + attribute.getClass());
        }

        return cloneAttribute;
    }

    /**
     * Special case for Address Part and Name Part
     *
     * @param partName
     * @return Clone Attribute Meta
     */
    private static CloneAttributeMeta processAttribute(String partName)
    {
        CloneAttributeMultipleMeta cloneAttribute = new CloneAttributeMultipleMetaImpl();
        cloneAttribute.setUUID(UUIDGenerator.getUniqueString());
        cloneAttribute.setName(partName);
//        cloneAttribute.setSortKey(1);
        cloneAttribute.setCardinality(Cardinality.create("0..*"));
        cloneAttribute.setMultipleSequenceNumber(1);

        loadSimpleDatatype(cloneAttribute, DatatypePresenterBase.TEXT);

        return cloneAttribute;
    }

    public static void loadDatatype(CloneAttributeMeta cloneAttribute, Datatype datatype) throws MetaException
    {
        if (datatype instanceof SimpleDatatype)
        {
            loadSimpleDatatype(cloneAttribute, (SimpleDatatype) datatype);
        }
        else if (datatype instanceof ParametrizedDatatype)
        {
            loadParametrizedDatatype(cloneAttribute, (ParametrizedDatatype) datatype);
        }
    }


    // Only support IVL<QTY>, RTO<QTY, QTY>
    private static void loadParametrizedDatatype(CloneAttributeMeta cloneAttribute, ParametrizedDatatype parametrizedDatatype)
        throws MetaException
    {
        try
        {
            final String type = parametrizedDatatype.getType();
            DatatypePresenter presenter = DatatypePresenterFactory.getInstance().createPresenter(type);
            Class aClass = presenter.getClass();
            Field[] fields = aClass.getFields();
            for (Field field : fields)
            {
                String fieldName = field.getName();
                if ((fieldName.startsWith("ATTR_")
                    || fieldName.startsWith("TEXT"))
                    && !"ATTR_NULL_FLAVOR".equalsIgnoreCase(fieldName))
                {
                    CloneDatatypeFieldMeta cloneDatatypeField = new CloneDatatypeFieldMetaImpl();
                    cloneDatatypeField.setUUID(UUIDGenerator.getUniqueString());
                    final String datatypeFieldName = (String) field.get(null);
                    cloneDatatypeField.setCardinality(DatatypeFieldCardinality.getCardinality(type, datatypeFieldName));
                    cloneDatatypeField.setName(datatypeFieldName);
                    cloneAttribute.addDatatyepField(cloneDatatypeField);
                }

                if (fieldName.startsWith("TAG_"))
                {
                    CloneAttributeMeta cam = new CloneAttributeMetaImpl();
                    cam.setName((String) field.get(null));
                    cam.setUUID(UUIDGenerator.getUniqueString());
                    cam.setCardinality(Cardinality.create("0..*"));
                    cloneAttribute.addAttribute(cam);

                    if ("IVL".equalsIgnoreCase(type))
                    {
                        loadDatatype(cam, parametrizedDatatype.getParameter(0));
                    }
                    else if ("RTO".equalsIgnoreCase(type))
                    {
                        final String datatypeFieldName = (String) field.get(null);
                        if (RTOPresenter.TAG_NUMERATOR.equalsIgnoreCase(datatypeFieldName))
                        {
                            loadDatatype(cam, parametrizedDatatype.getParameter(0));
                        }
                        else if (RTOPresenter.TAG_DENOMINATOR.equalsIgnoreCase(datatypeFieldName))
                        {
                            loadDatatype(cam, parametrizedDatatype.getParameter(1));
                        }
                    }

                }
            }
        }
        catch (FactoryException e)
        {
            throw new MetaException(e.getMessage(), e);
        }
        catch (IllegalAccessException e)
        {
            throw new MetaException(e.getMessage(), e);
        }

    }

    private static void loadSimpleDatatype(CloneAttributeMeta cloneAttribute, SimpleDatatype simpleDatatype) throws MetaException
    {
        final String fullName = simpleDatatype.getFullName();
        cloneAttribute.setDatatype(simpleDatatype.getFullName());

        // If QTY or ANY abstract
        if (DatatypeMetadataFactoryDatatypes.instance().ABSTRACT_DATATYPES_MAP.keySet().contains(simpleDatatype))
        {
            return;
        }

        try
        {

            DatatypePresenter presenter = DatatypePresenterFactory.getInstance().createPresenter(fullName);
            Class aClass = presenter.getClass();
            Field[] fields = aClass.getFields();
            for (Field field : fields)
            {
                String fieldName = field.getName();
                if ((fieldName.startsWith("ATTR_")
                    || fieldName.startsWith("TAG_")      //todo: Take TAG_ as the sub-element
                    || fieldName.startsWith("TEXT"))
                    && !"ATTR_NULL_FLAVOR".equalsIgnoreCase(fieldName))
                {
                    CloneDatatypeFieldMeta cloneDatatypeField = new CloneDatatypeFieldMetaImpl();
                    cloneDatatypeField.setUUID(UUIDGenerator.getUniqueString());
                    String daattypeFieldname = (String) field.get(null);
                    cloneDatatypeField.setName(daattypeFieldname);
                    cloneDatatypeField.setCardinality(DatatypeFieldCardinality.getCardinality(fullName, daattypeFieldname));
                    cloneAttribute.addDatatyepField(cloneDatatypeField);
                }
            }


            if (DatatypeMetadataFactoryDatatypes.instance().NAME_PART_DATATYPES.contains(simpleDatatype))
            {
                DatatypePresenter xpPresenter = DatatypePresenterFactory.getInstance().createPresenter(fullName + "XP");
                final Field field = xpPresenter.getClass().getField("TAGS_TO_PART_TYPES");
                final Map map = (Map) field.get(null);
                Set keySet = map.keySet();
                for (Iterator iterator = keySet.iterator(); iterator.hasNext();)
                {
                    final CloneAttributeMeta childAttribute = processAttribute((String) iterator.next());
                    cloneAttribute.addAttribute(childAttribute);
                }
            }

        }
        catch (FactoryException e)
        {
            throw new MetaException(e.getMessage(), e);
        }
        catch (IllegalAccessException e)
        {
            throw new MetaException(e.getMessage(), e);
        }
        catch (NoSuchFieldException e)
        {
            throw new SystemException(e);
        }
    }

    /**
     * Only support inlineText
     * @param cloneAttribute
     * @param datatypeFieldName
     */
    private static void loadSimpleDatatype(CloneAttributeMeta cloneAttribute, String datatypeFieldName)
    {
        CloneDatatypeFieldMeta cloneDatatypeField = new CloneDatatypeFieldMetaImpl();
        cloneDatatypeField.setUUID(UUIDGenerator.getUniqueString());
        cloneDatatypeField.setName(datatypeFieldName);
        cloneDatatypeField.setCardinality(Cardinality.create("0..1"));
        cloneAttribute.addDatatyepField(cloneDatatypeField);
    }


}

/**
 * HISTORY      : $Log: not supported by cvs2svn $
 * HISTORY      : Revision 1.68  2006/08/02 18:44:20  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.67  2006/06/02 22:18:50  chene
 * HISTORY      : 040002 improvement
 * HISTORY      :
 * HISTORY      : Revision 1.66  2006/05/19 18:55:27  chene
 * HISTORY      : Deprecated isExtra method at Feature interface, provide the XmlItsUtil isAddtionAtribute method
 * HISTORY      :
 * HISTORY      : Revision 1.65  2006/05/15 20:43:57  chene
 * HISTORY      : Fix RTO H3s missing data type spec error
 * HISTORY      :
 * HISTORY      : Revision 1.64  2006/05/04 15:06:53  chene
 * HISTORY      : Change h3s specification from "isRecursive" to "isReference"
 * HISTORY      :
 * HISTORY      : Revision 1.63  2006/05/03 21:58:11  chene
 * HISTORY      : Depreciated MetSource
 * HISTORY      :
 * HISTORY      : Revision 1.62  2006/05/03 21:26:43  chene
 * HISTORY      : Saved Point
 * HISTORY      :
 * HISTORY      : Revision 1.61  2006/05/02 18:22:56  chene
 * HISTORY      : Add isReference interface
 * HISTORY      :
 * HISTORY      : Revision 1.60  2006/03/13 22:06:13  chene
 * HISTORY      : Minor comment change
 * HISTORY      :
 * HISTORY      : Revision 1.59  2006/01/11 15:35:19  chene
 * HISTORY      : no message
 * HISTORY      :
 */
