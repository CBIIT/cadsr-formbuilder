/**
 * <!-- LICENSE_TEXT_START -->
 * $Header: /share/content/gforge/formbuilder/hl7messagegenerator/config/caadapter/src/gov/nih/nci/hl7/map/RimGraphGen.java,v 1.1 2006-10-03 17:38:42 marwahah Exp $
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


package gov.nih.nci.hl7.map;

import gov.nih.nci.hl7.clone.data.CloneAttributeData;
import gov.nih.nci.hl7.clone.data.CloneData;
import gov.nih.nci.hl7.clone.data.HL7V3Data;
import gov.nih.nci.hl7.clone.meta.CloneMeta;
import gov.nih.nci.hl7.common.SystemException;
import gov.nih.nci.hl7.util.GeneralUtilities;
import gov.nih.nci.hl7.util.HL7V3Util;
import gov.nih.nci.hl7.util.Log;
import gov.nih.nci.hl7.util.MessageResources;
import gov.nih.nci.hl7.validation.Message;
import gov.nih.nci.hl7.validation.ValidatorResult;
import gov.nih.nci.hl7.validation.ValidatorResults;
import org.hl7.meta.*;
import org.hl7.meta.impl.JavaItsImpl;
import org.hl7.meta.impl.SimpleDatatypeImpl;
import org.hl7.meta.impl.DatatypeMetadataFactoryImpl;
import org.hl7.meta.impl.DatatypeMetadataFactoryDatatypes;
import org.hl7.meta.util.MetaUtils;
import org.hl7.meta.util.StringUtils;
import org.hl7.rim.RimObject;
import org.hl7.types.*;
import org.hl7.util.FactoryException;
import org.hl7.util.RimUtil;
import org.hl7.xml.CSPresenter;
import org.hl7.xml.DatatypePresenterBase;
import org.hl7.xml.builder.FeatureException;
import org.hl7.xml.validator.AssociationCardinalityException;
import org.hl7.xml.validator.CardinalityValidator;
import org.hl7.xml.validator.FeatureCardinalityException;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.Map;

/**
 * Generate Rim Objects from HL7 V3(HSM) Data Objects.
 *
 * @author OWNER: Eric Chen  Date: Jun 8, 2005
 * @author LAST UPDATE: $Author: marwahah $
 * @version $Revision: 1.1 $
 * @date $$Date: 2006-10-03 17:38:42 $
 * @since caAdapter v1.2
 */


public class RimGraphGen
{
    private static final String LOGID = "$RCSfile: RimGraphGen.java,v $";
    public static String RCSID = "$Header: /share/content/gforge/formbuilder/hl7messagegenerator/config/caadapter/src/gov/nih/nci/hl7/map/RimGraphGen.java,v 1.1 2006-10-03 17:38:42 marwahah Exp $";


    private ValidatorResults validatorResults = new ValidatorResults();

    private static final JavaIts javaIts = new JavaItsImpl();

    /**
     * HL7 String Based HL7 Object Graph (HSM)
     */
    private HL7V3Data hl7V3Data;

    public RimGraphGen(HL7V3Data hl7V3Data) throws MappingException
    {
        this.hl7V3Data = hl7V3Data;
    }


    /**
     * Generate Object Graph Based on Clone Class and HL7
     * V3 Object Data
     *
     * @return Rim Object Graph
     * @throws MappingException
     */
    public RimGraphResult generate() throws MappingException
    {

        RimObject result;
        CloneClass cloneClass;

        MessageType messageType;

        try
        {
            messageType = HL7V3Util.getMessageType(hl7V3Data.getMessageID());
        }
        catch (FileNotFoundException e)
        {
            Log.logException(this, e);
            Message msg = MessageResources.getMessage("RIM1",
                new Object[]{hl7V3Data.getMessageID()});
            validatorResults.addValidatorResult(new ValidatorResult(ValidatorResult.Level.FATAL, msg));
            throw new MappingException(msg.toString(), e);
        }
        catch (LoaderException e)
        {
            Log.logException(this, e);
            Message msg = MessageResources.getMessage("RIM2", new Object[]{hl7V3Data.getMessageID()});
            validatorResults.addValidatorResult(new ValidatorResult(ValidatorResult.Level.FATAL, msg));
            throw new MappingException(msg.toString(), e);
        }


//        if (!messageType.getId().equalsIgnoreCase(hl7V3Data.getMessageID()))
//            throw new MappingException("HL7 V3 Meta Message ID: " + messageType.getId() + " is not equals to"
//                + "HL7 V3 HL7 Object Graph Message ID: " + hl7V3Data.getMessageID(), new IllegalArgumentException());


        try
        {
            cloneClass = messageType.getRootClass();
            result = cloneClass.getInstance();

            processFeatures(result, cloneClass, hl7V3Data.getRootCloneData());

        }
        catch (FactoryException ex)
        {
            throw new SystemException(ex);
        }

        return new RimGraphResult(result, validatorResults);
    }

    private void processFeatures(RimObject rimObject, CloneClass cloneClass, CloneData cloneData) throws MappingException
    {
        for (Iterator it = cloneClass.iterateChildren(); it.hasNext();)
        {
            Feature feature = (Feature) it.next();

            if (feature instanceof Attribute)
            {
                processAttribute(rimObject, (Attribute) feature, cloneData);
            }
            else if (feature instanceof Association)
            {
                if (feature instanceof ChoiceAssociation)
                {
                    processChoiceAssociation(rimObject, (ChoiceAssociation) feature, cloneData);
                }
                else if (feature instanceof CmetAssociation)
                {
                    processAssociation(rimObject, (Association) feature, cloneData);
                }
                else
                {
                    processAssociation(rimObject, (Association) feature, cloneData);
                }
            }
        }
    }

    private void processAttribute(RimObject rimObject, Attribute attribute, CloneData cloneData) throws MappingException
    {
        if (cloneData == null)
        {

            if (attribute.getCardinality().getMin() >= 1 )
//                && !Arrays.asList(HL7V3Util.getHL7DefinedValueStructureAttributes()).contains(attribute.getName()))
            {
                Message msg = MessageResources.getMessage("RIM4",
                    new Object[]{attribute.getParent().getName() + "." + attribute.getName(),
                                 attribute.getCardinality().toString(),
                                 ((CloneMeta) cloneData.getMetaObject()).getLinage(),
                                 0
                    });
                validatorResults.addValidatorResult(new ValidatorResult(ValidatorResult.Level.ERROR, msg));
            }
            return;
        }

        try
        {
            Datatype datatype = attribute.getDatatype();
            String rimPropertyName = attribute.getRimPropertyName();

            List valueMapList = cloneData.getValueMapList(rimPropertyName);

            if (valueMapList == null || valueMapList.size() == 0)
            {
                List<CloneAttributeData> attributeDatas = cloneData.getAttributes(rimPropertyName);
                if (attribute.getCardinality().getMin() >= 1
                    && (attributeDatas.size() == 0 || GeneralUtilities.isBlank(attributeDatas.get(0).getHL7DefaultValue())))
                { // does not care about that HL7 Structure Attributes has Default Value
                    Message msg = MessageResources.getMessage("RIM4",
                        new Object[]{attribute.getParent().getName() + "." + attribute.getName(),
                                     attribute.getCardinality().toString(),
                                     ((CloneMeta) cloneData.getMetaObject()).getLinage(),
                                     0
                        });
                    validatorResults.addValidatorResult(new ValidatorResult(ValidatorResult.Level.ERROR, msg));
                }
                return;
            }

            int attributeCount = valueMapList.size();
            String subClassname = cloneData.getSubClass(rimPropertyName);

            if (subClassname != null)
            {
                // set up the sub class for SimpleDatatype
                if (datatype instanceof SimpleDatatypeImpl)
                {
                    SimpleDatatypeImpl simpleDatatype = (SimpleDatatypeImpl) datatype;
                    simpleDatatype.setXsiTypeString(subClassname);
                }
            }

            String methodNameStem = StringUtils.forceInitialCap(rimPropertyName);
            Class rimDataType = javaIts.getRIMDataType(rimObject, methodNameStem);
            Collection collection = new ArrayList();

            if (datatype instanceof SimpleDatatype)
            {
                if (attributeCount >= 2)
                {
                    Message msg = MessageResources.getMessage("RIM4",
                        new Object[]{attribute.getParent().getName() + "." + attribute.getName(),
                                     attribute.getCardinality().toString(),
                                     ((CloneMeta) cloneData.getMetaObject()).getLinage(),
                                     attributeCount
                        });
                    validatorResults.addValidatorResult(new ValidatorResult(ValidatorResult.Level.ERROR, msg));
                }

                ANY value = null;

                java.util.Map<String, String> m = new HashMap();
                try
                {
                    m = (Map) valueMapList.get(0);

//                    if (attribute.isStructural())  // does not care about structure or not
//                    {
                        if (datatype.equals(DatatypeMetadataFactoryDatatypes.instance().CSTYPE))
                        {
                            m.put(CSPresenter.ATTR_CODE_SYSTEM, MetaUtils.getCodeSystemId(attribute.getDomains()).toString());
                        }
//                    }

                    if (m.size() == 0 && !attribute.isStructural())
                    {
                        if (attribute.getCardinality().getMin() >= 1)
                        { // does not care about HL7 Defined Value Structure Attributes
                            Message msg = MessageResources.getMessage("RIM4",
                                new Object[]{attribute.getParent().getName() + "." + attribute.getName(),
                                             attribute.getCardinality().toString(),
                                             ((CloneMeta) cloneData.getMetaObject()).getLinage(),
                                             0
                                });
                            validatorResults.addValidatorResult(new ValidatorResult(ValidatorResult.Level.ERROR, msg));
                        }
                        return;
                    }

                    value = getSimpleDatatypeValue((SimpleDatatype) datatype, m);

                    if ((CardinalityValidator.getDatatypeCardinality(value) == 0)
                        && attribute.isStructural())
                    {
                        value = MetaUtils.getAttributeDefaultValue(value, attribute);
                    }
                }
                catch (FeatureException e)
                {
                    Log.logWarning(this, e.getFeature().getName() + ":" + e.getMessage());
                }
                catch (ValueFactoryException e)
                {
                    Message msg = MessageResources.getMessage("RIM6",
                        new Object[]{((CloneMeta) cloneData.getMetaObject()).getLinage() + "." + attribute.getName(),
                                     datatype.getFullName(),
                                     m
                        });
                    validatorResults.addValidatorResult(new ValidatorResult(ValidatorResult.Level.ERROR, msg));
                    Log.logException(this, msg.toString(), e);
                }

                if (value == null) return;

                if (rimDataType.isAssignableFrom(value.getClass()))
                {  // Maxim cardinality is 1
                    Log.logDebug(this, "add simple attribute: " + attribute.getName() + " RIM name: " + rimPropertyName +
                        " attr: " + attribute + " RIM rimDataType: " + rimDataType + " rimDataType: " + datatype.getFullName());

                    if (rimDataType != null)
                    {
                        Method setter = rimObject.getClass().getMethod("set" + StringUtils.forceInitialCap(rimPropertyName),
                            new Class[]{rimDataType});
                        setter.invoke(rimObject, new Object[]{value});
                        return;
                    }
                } // end of rimDataType isAssignableFrom

                // else dealing with collection

                // possible RMIM's Simpledatatype such as is constrained from Collection, such as TS is constrained
                // from IVL<TS>, II is constrained from SET<II>
                collection.add(value);
                try
                {
                    CardinalityValidator.checkAttributeCardinality(collection, attribute);
                }
                catch (FeatureCardinalityException e)
                {
                    Log.logException(this, e);
                }
                ANY collectionValue = getCollectionValue(rimDataType, collection);

                rimObject.getClass().getMethod("set" + StringUtils.forceInitialCap(rimPropertyName),
                    new Class[]{rimDataType}).
                    invoke(rimObject, new Object[]{collectionValue});
                return;
            } // end of the datatype instanceof SimpleDatatype
            else if (datatype instanceof ParametrizedDatatype)
            {
                // for example, SET<II>, BAG<INT>, LIST<ST>, IVL<TS>, IVL<PQ>, RTO<QTY, QTY>

                ParametrizedDatatype parametrizedDatatype = (ParametrizedDatatype) datatype;
                if (!parametrizedDatatype.isPure())
                {
                    // for instance IVL<TS>, RTO<QTY, QTY>
                    // meta data thinks it's parametrized Data rimDataType because HMD data rimDataType contains with <PQ>

                    ANY value = null;

                    Map m = new HashMap();
                    try
                    {
                        m = (Map) valueMapList.get(0);
                        if (m.size() == 0)
                        {
                            if (attribute.getCardinality().getMin() >= 1)
                            {
                                Message msg = MessageResources.getMessage("RIM4",
                                    new Object[]{attribute.getParent().getName() + "." + attribute.getName(),
                                                 attribute.getCardinality().toString(),
                                                 ((CloneMeta) cloneData.getMetaObject()).getLinage(),
                                                 0
                                    });
                                validatorResults.addValidatorResult(new ValidatorResult(ValidatorResult.Level.ERROR, msg));
                            }
                            return;
                        }

                        if ("IVL".equalsIgnoreCase(parametrizedDatatype.getType()))
                        {   // auto setup IVL sub type for ValueFactory use
                            m.put(DatatypePresenterBase.SUB_TYPE, parametrizedDatatype.getParameter(0).getFullName());
                        }
                        value = getDatatypeValue(parametrizedDatatype, m);
                    }
                    catch (ValueFactoryException e)
                    {
                        Message msg = MessageResources.getMessage("RIM6",
                            new Object[]{((CloneMeta) cloneData.getMetaObject()).getLinage() + "." + attribute.getName(),
                                         parametrizedDatatype.getFullName(),
                                         m
                            });
                        validatorResults.addValidatorResult(new ValidatorResult(ValidatorResult.Level.ERROR, msg));
                        Log.logException(this, msg.toString(), e);

                    }

                    Log.logDebug(this, "add simple attribute: " + attribute.getName() + " RIM name: " + rimPropertyName +
                        " attr: " + attribute + " RIM rimDataType: " + rimDataType + " rimDataType: " + datatype.getFullName());

                    if (rimDataType != null)
                    {
                        Method setter = rimObject.getClass().getMethod("set" + StringUtils.forceInitialCap(rimPropertyName),
                            new Class[]{rimDataType});
                        setter.invoke(rimObject, new Object[]{value});
                        return;
                    }
                } // end of parametrizedDatatype is not pure
                else
                {
                    // handle the SET, BAG and LIST datatype, such as SET<II>, BAG<INT>, LIST<ST>, SET< RTO<QTY, QTY> >
                    Datatype dt = (Datatype) parametrizedDatatype.getParameter().get(0);
                    for (int i = 0; i < valueMapList.size(); i++)
                    {
                        Map<String, String> map = (Map) valueMapList.get(i);
                        try
                        {
                            if (attribute.isStructural())
                            {
                                if (dt.getFullName().equals("CS"))
                                {
                                    map.put(CSPresenter.ATTR_CODE_SYSTEM, MetaUtils.getCodeSystemId(attribute.getDomains()).toString());
                                }
                            }
                            ANY any = getDatatypeValue(dt, map);
                            collection.add(any);
                        }
                        catch (ValueFactoryException e)
                        {
                            Message msg = MessageResources.getMessage("RIM6",
                                new Object[]{((CloneMeta) cloneData.getMetaObject()).getLinage() + "." + attribute.getName(),
                                             dt.getFullName(),
                                             map
                                });
                            validatorResults.addValidatorResult(new ValidatorResult(ValidatorResult.Level.ERROR, msg));
                            Log.logException(this, msg.toString(), e);
                        }
                    } //the end of valueMapList loop

                    ANY collectionValue = getCollectionValue(rimDataType, collection);
                    try
                    {
                        CardinalityValidator.checkAttributeCardinality(collection, attribute);
                    }
                    catch (FeatureCardinalityException e)
                    {
                        Message msg = MessageResources.getMessage("RIM4",
                            new Object[]{attribute.getParent().getName() + "." + attribute.getName(),
                                         attribute.getCardinality().toString(),
                                         ((CloneMeta) cloneData.getMetaObject()).getLinage(),
                                         collection.size()
                            });
                        validatorResults.addValidatorResult(new ValidatorResult(ValidatorResult.Level.ERROR, msg));
                        Log.logWarning(this, e);
                    }

                    rimObject.getClass().getMethod("set" + StringUtils.forceInitialCap(rimPropertyName),
                        new Class[]{rimDataType}).
                        invoke(rimObject, new Object[]{collectionValue});
                    return;
                }
            }
            else
            {
                /* Oops, we should never get here! */
                throw new SystemException("unhandled feature " + attribute.getClass());
            }
        }
        catch (NoSuchMethodException e)
        {
            throw new SystemException(e);
        }
        catch (IllegalAccessException e)
        {
            throw new SystemException(e);
        }
        catch (InvocationTargetException e)
        {
//            throw new SystemException(e);
            throw new MappingException(e.getMessage(), e);
        }
    }

    private void processAssociation(RimObject rimObject, Association association, CloneData cloneData)
        throws MappingException
    {

        try
        {
            CloneClass distalClone = association.getTarget();
            final String name = association.getName();
            List<CloneData> childCloneList = cloneData.getChildClones(name);
            final Cardinality cardinality = association.getCardinality();

            if ((childCloneList == null || childCloneList.size() == 0) && cardinality.getMin() < 1)
                return;

            Log.logDebug(this, "add Association: " + association.getName() + ", RIM property name: " + association.getRimPropertyName()
                + " , Assoc: " + association);
            if (childCloneList != null)
            {
                try
                {
                    CardinalityValidator.checkAssociationCardinality(childCloneList, association);
                }
                catch (AssociationCardinalityException e)
                {
                    Message msg = MessageResources.getMessage("RIM5",
                        new Object[]{association.getParent().getName() + "." + association.getName(),
                                     association.getCardinality().toString(),
                                     ((CloneMeta) cloneData.getMetaObject()).getLinage(),
                                     childCloneList.size()
                        });
                    validatorResults.addValidatorResult(new ValidatorResult(ValidatorResult.Level.ERROR, msg));

                    Log.logWarning(this, ((CloneMeta) cloneData.getMetaObject()).getLinage());
                }

                for (int i = 0; i < childCloneList.size(); i++)
                {
                    RimObject value = distalClone.getInstance();
                    RimUtil.add(rimObject, association, (RimObject) value);

                    CloneData cd = childCloneList.get(i);
                    processFeatures(value, association.getTarget(), cd);
                }
            }
        }
        catch (LoaderException e)
        {
            Message msg = MessageResources.getMessage("GEN0", new Object[]{e.getMessage()});
            ValidatorResult validatorResult = new ValidatorResult(ValidatorResult.Level.FATAL, msg);
            validatorResults.addValidatorResult(validatorResult);
            Log.logException(this, e);
        }
        catch (FactoryException e)
        {
            Message msg = MessageResources.getMessage("GEN0", new Object[]{e.getMessage()});
            ValidatorResult validatorResult = new ValidatorResult(ValidatorResult.Level.FATAL, msg);
            validatorResults.addValidatorResult(validatorResult);
            Log.logException(this, e);
        }
        catch (FeatureCardinalityException e)
        {
            Log.logWarning(this, e);
        }
    }

    private void processChoiceAssociation(RimObject rimObject, ChoiceAssociation choiceAssociation,
                                          CloneData cloneData) throws MappingException
    {
        try
        {
            List<CloneData> matchedChildCloneList = null;
            Association matchedAssociation = null;

            Iterator iterator = choiceAssociation.iterateChoices();

            while (iterator.hasNext())
            {
                Association association = (Association) iterator.next();

                List<CloneData> childCloneList = cloneData.getChildClones(association.getName());

                Cardinality cardinality = association.getCardinality();
                if ((childCloneList == null || childCloneList.size() == 0) && cardinality.getMin() < 1) continue;

                if (childCloneList != null && !childCloneList.isEmpty())
                {
                    if (matchedChildCloneList == null)
                    {
                        matchedChildCloneList = childCloneList;
                        matchedAssociation = association;
                        try
                        {
                            CardinalityValidator.checkAssociationCardinality(matchedChildCloneList, association);
                        }
                        catch (AssociationCardinalityException e)
                        {
                            Message msg = MessageResources.getMessage("RIM5",
                                new Object[]{association.getParent().getName() + "." + association.getName(),
                                             cardinality.toString(),
                                             ((CloneMeta) cloneData.getMetaObject()).getLinage(),
                                             matchedChildCloneList.size()
                                });
                            validatorResults.addValidatorResult(new ValidatorResult(ValidatorResult.Level.ERROR, msg));
                            Log.logWarning(this, ((CloneMeta) cloneData.getMetaObject()).getLinage());
                        }
                    }
                    else
                    {   // Two or more choices are selected under the choice group
                        Message msg = MessageResources.getMessage("RIM7",
                            new Object[]{choiceAssociation.getName()});
                        validatorResults.addValidatorResult(new ValidatorResult(ValidatorResult.Level.WARNING, msg));
                    }
                }

            }

            if (matchedChildCloneList == null)
            { // Nothing is found. Most likely choice is not selected or choice clone is optional.
              return;
            }

            for (int i = 0; i < matchedChildCloneList.size(); i++)
            {
                CloneClass matchedDistalClone = matchedAssociation.getTarget();
                RimObject value = matchedDistalClone.getInstance();
                RimUtil.add(rimObject, matchedAssociation, (RimObject) value);
                CloneData cd = matchedChildCloneList.get(i);
                processFeatures(value, matchedDistalClone, cd);
            }

        }
        catch (LoaderException e)
        {
            Message msg = MessageResources.getMessage("GEN0", new Object[]{e.getMessage()});
            ValidatorResult validatorResult = new ValidatorResult(ValidatorResult.Level.FATAL, msg);
            validatorResults.addValidatorResult(validatorResult);
            Log.logException(this, e);
        }
        catch (FactoryException e)
        {
            Message msg = MessageResources.getMessage("GEN0", new Object[]{e.getMessage()});
            ValidatorResult validatorResult = new ValidatorResult(ValidatorResult.Level.FATAL, msg);
            validatorResults.addValidatorResult(validatorResult);
            Log.logException(this, e);
        }
        catch (FeatureCardinalityException e)
        {
            Log.logWarning(this, e);
        }


    }


    private ANY getDatatypeValue(Datatype datatype, java.util.Map map) throws ValueFactoryException
    {
        //todo: Three sets of getDatatypeValue method will not refactor to Datatype class until HMD, MIF CVS branch issue resolved

        if (datatype instanceof SimpleDatatype)
        {
            return getSimpleDatatypeValue((SimpleDatatype) datatype, map);
        }
        else if (datatype instanceof ParametrizedDatatype)
        {
            return getParametrizedDatatypeValue((ParametrizedDatatype) datatype, map);
        }
        else
        {
            return null;
        }
    }


    private ANY getSimpleDatatypeValue(SimpleDatatype simpleDatatype, java.util.Map map) throws ValueFactoryException
    {
        final String xsiTypeString = ((SimpleDatatypeImpl) simpleDatatype).getXsiTypeString();

        if (! GeneralUtilities.isBlank(xsiTypeString))
        try
        {
            Datatype subDatatype = DatatypeMetadataFactoryImpl.instance().create(xsiTypeString);
            if (subDatatype instanceof ParametrizedDatatype) // if ParametrizedDatatype
            {
                return getParametrizedDatatypeValue((ParametrizedDatatype)subDatatype, map);
            }
        }
        catch (UnknownDatatypeException e)
        {
            // ignored
        }

        String methodStem = GeneralUtilities.isBlank(xsiTypeString) ?
            simpleDatatype.getFullName() : xsiTypeString;
        ANY value = null;
        value = (ANY) ValueFactory.getInstance().valueOfMap(methodStem, map);
        return value;
    }

    // Actually for IVL<QTY>, RTO<QTY, QTY> only for now
    private ANY getParametrizedDatatypeValue(ParametrizedDatatype parametrizedDatatype, java.util.Map map)
        throws ValueFactoryException
    {
        ANY value = null;
        value = (ANY) ValueFactory.getInstance().valueOfMap(parametrizedDatatype.getType(), map);
        return value;
    }

    private ANY getCollectionValue(Class type, Collection collection)
    {

        if ((SET.class.isAssignableFrom(type) && !QSET.class.isAssignableFrom(type)))
        {
            return ValueFactory.getInstance().SETvalueOf(collection);
        }
        else if (BAG.class.isAssignableFrom(type))
        {
            return ValueFactory.getInstance().BAGvalueOf(collection);
        }
        else if (LIST.class.isAssignableFrom(type))
        {
            return ValueFactory.getInstance().LISTvalueOf(collection);
        }
        else if (IVL.class.isAssignableFrom(type))
        {
            // In RMIM, RIM class's IVL<TS> could constrain to TS
            return ValueFactory.getInstance().IVLvalueOf(collection);
        }
        return null;
    }

}

/**
 * HISTORY      : $Log: not supported by cvs2svn $
 * HISTORY      : Revision 1.46  2006/08/02 18:44:20  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.45  2006/06/11 21:58:09  chene
 * HISTORY      : Fix print out QSETnull error
 * HISTORY      :
 * HISTORY      : Revision 1.44  2006/06/11 03:07:11  chene
 * HISTORY      : Performer input text missing one attribute
 * HISTORY      :
 * HISTORY      : Revision 1.43  2006/06/09 21:18:01  chene
 * HISTORY      : Initial Draft
 * HISTORY      :
 * HISTORY      : Revision 1.42  2006/06/09 03:25:20  chene
 * HISTORY      : Saved Point
 * HISTORY      :
 * HISTORY      : Revision 1.41  2006/06/08 22:02:15  chene
 * HISTORY      : Saved Point
 * HISTORY      :
 * HISTORY      : Revision 1.40  2006/06/06 22:07:11  chene
 * HISTORY      : RTO support
 * HISTORY      :
 * HISTORY      : Revision 1.39  2006/06/06 18:26:12  chene
 * HISTORY      : Support if Parametrized Datatype is the subClass of Simple Abstract Datatype
 * HISTORY      :
 * HISTORY      : Revision 1.38  2006/05/19 18:55:27  chene
 * HISTORY      : Deprecated isExtra method at Feature interface, provide the XmlItsUtil isAddtionAtribute method
 * HISTORY      :
 * HISTORY      : Revision 1.37  2006/01/03 19:16:52  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.36  2006/01/03 18:56:24  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.35  2005/12/29 23:06:14  jiangsc
 * HISTORY      : Changed to latest project name.
 * HISTORY      :
 * HISTORY      : Revision 1.34  2005/12/29 15:39:05  chene
 * HISTORY      : Optimize imports
 * HISTORY      :
 * HISTORY      : Revision 1.33  2005/12/21 22:22:56  chene
 * HISTORY      : Raise the cardinality validation level from 'warning' to 'error'
 * HISTORY      :
 * HISTORY      : Revision 1.32  2005/12/14 22:48:02  chene
 * HISTORY      : Promote backend org.hl7.xml.builder.AttributeCardinalityException: AuthorOrPerformer3.typeCode: HMD specifies cardinality 1..1, RIM object has 0 to validation message as well
 * HISTORY      :
 * HISTORY      : Revision 1.31  2005/12/14 17:02:24  chene
 * HISTORY      : Fix 040002 one validation error
 * HISTORY      :
 * HISTORY      : Revision 1.30  2005/11/30 22:24:51  chene
 * HISTORY      : Fix Null point exception bug because choice is not selected.
 * HISTORY      :
 * HISTORY      : Revision 1.29  2005/11/29 19:16:16  giordanm
 * HISTORY      : no message
 * HISTORY      :
 * HISTORY      : Revision 1.28  2005/11/21 21:00:13  chene
 * HISTORY      : Add Test Case for Parse and Build
 * HISTORY      :
 * HISTORY      : Revision 1.27  2005/11/07 20:13:57  chene
 * HISTORY      : Rename sub_datatypes_map to abstract_datatypes_map
 * HISTORY      :
 * HISTORY      : Revision 1.26  2005/11/07 15:59:19  chene
 * HISTORY      : Add Log.logException when we catch the exception besides adding the validation result.
 * HISTORY      :
 * HISTORY      : Revision 1.25  2005/11/02 15:18:19  chene
 * HISTORY      : Fix the bug: Message Validation: Choice validation returning erroneous results
 * HISTORY      :
 * HISTORY      : Revision 1.24  2005/10/26 17:46:57  chene
 * HISTORY      : Change RIM4,RIM5 message from Error to Warning
 * HISTORY      :
 * HISTORY      : Revision 1.23  2005/10/06 21:26:21  chene
 * HISTORY      : Support Message Validation
 * HISTORY      :
 * HISTORY      : Revision 1.22  2005/10/06 19:25:21  chene
 * HISTORY      : Fixed isChoice properties bug
 * HISTORY      :
 * HISTORY      : Revision 1.21  2005/10/06 03:34:11  chene
 * HISTORY      : Update with the minor change
 * HISTORY      :
 * HISTORY      : Revision 1.20  2005/10/05 22:34:50  chene
 * HISTORY      : Saving point
 * HISTORY      :
 * HISTORY      : Revision 1.19  2005/10/05 19:28:53  chene
 * HISTORY      : Upate messages file
 * HISTORY      :
 * HISTORY      : Revision 1.18  2005/09/22 17:08:14  giordanm
 * HISTORY      : add getLinage() log message
 * HISTORY      :
 * HISTORY      : Revision 1.17  2005/09/08 19:37:01  chene
 * HISTORY      : Saved point
 * HISTORY      :
 * HISTORY      : Revision 1.16  2005/08/25 17:07:09  chene
 * HISTORY      : Fixed RimGen Bug cardinality bug
 * HISTORY      :
 * HISTORY      : Revision 1.15  2005/08/18 18:18:04  chene
 * HISTORY      : Fixed one cardinality related to choice
 * HISTORY      :
 * HISTORY      : Revision 1.14  2005/08/18 17:49:04  chene
 * HISTORY      : Change clonename reference to association name
 * HISTORY      :
 * HISTORY      : Revision 1.13  2005/08/08 02:23:37  chene
 * HISTORY      : Finally fixed the abstract datatype, but GUI needs to add this functionality
 * HISTORY      :
 * HISTORY      : Revision 1.12  2005/08/08 02:06:22  chene
 * HISTORY      : Fixed most of Schema non-validated bugs, second round
 * HISTORY      :
 * HISTORY      : Revision 1.11  2005/08/04 22:03:28  chene
 * HISTORY      : Fix the no such method exception
 * HISTORY      :
 * HISTORY      : Revision 1.10  2005/08/02 15:18:29  chene
 * HISTORY      : Add HL7 default value and user default value
 * HISTORY      :
 * HISTORY      : Revision 1.9  2005/07/25 20:51:54  chene
 * HISTORY      : Fix 'expirationTime should have the fields of value and inclusive' bug
 * HISTORY      :
 * HISTORY      : Revision 1.8  2005/07/25 16:09:10  chene
 * HISTORY      : Change IVL Presenter xml sub element name from attribute to tag
 * HISTORY      :
 * HISTORY      : Revision 1.7  2005/07/21 21:35:23  chene
 * HISTORY      : replace log name to object log sender
 * HISTORY      :
 * HISTORY      : Revision 1.6  2005/07/14 15:52:08  giordanm
 * HISTORY      : purely aesthetic stuff - license headers, class headers, javdoc comments, etc.
 * HISTORY      :
 * HISTORY      : Revision 1.5  2005/07/12 20:25:30  jiangsc
 * HISTORY      : Added severity support.
 * HISTORY      :
 * HISTORY      : Revision 1.4  2005/06/09 23:02:24  chene
 * HISTORY      : Add another Constructor
 * HISTORY      :
 * HISTORY      : Revision 1.3  2005/06/09 03:30:04  chene
 * HISTORY      : First Cut of RimGraphGen
 * HISTORY      :
 * HISTORY      : Revision 1.2  2005/06/09 02:16:59  chene
 * HISTORY      : First Cut of RimGraphGen
 * HISTORY      :
 * HISTORY      : Revision 1.1  2005/06/08 22:29:52  chene
 * HISTORY      : Update Clone Data implementation
 * HISTORY      :
 */
