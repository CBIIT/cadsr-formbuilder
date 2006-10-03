/* The contents of this file are subject to the Health Level-7 Public
 * License Version 1.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the
 * License at http://www.hl7.org/HPL/
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See
 * the License for the specific language governing rights and
 * limitations under the License.
 *
 * The Original Code is all this file.
 *
 * The Initial Developer of the Original Code is Gunther Schadow.
 * Portions created by Initial Developer are Copyright (C) 2002-2004
 * Health Level Seven, Inc. All Rights Reserved.
 *
 * Contributor(s): Skirmantas Kligys, Eric Chen
 */

package org.hl7.xml.parser;

import org.hl7.meta.*;
import org.hl7.meta.impl.DatatypeMetadataFactoryDatatypes;
import org.hl7.meta.impl.DatatypeMetadataFactoryImpl;
import org.hl7.meta.impl.JavaItsImpl;
import org.hl7.meta.util.MetaUtils;
import org.hl7.util.StringUtils;
import org.hl7.rim.RimObject;
import org.hl7.rim.Act;
import org.hl7.rim.Role;
import org.hl7.rim.Entity;
import org.hl7.types.*;
import org.hl7.types.impl.BLimpl;
import org.hl7.types.impl.CSimpl;
import org.hl7.types.impl.OIDimpl;
import org.hl7.types.impl.SETjuSetAdapter;
import org.hl7.types.enums.ActClass;
import org.hl7.types.enums.ActMood;
import org.hl7.types.enums.EntityDeterminer;
import org.hl7.util.FactoryException;
import org.hl7.util.RimUtil;
import org.hl7.xml.Accumulator;
import org.hl7.xml.XmlItsUtil;
import org.hl7.xml.DatatypePresenterBase;
import org.hl7.xml.CSPresenter;
import org.hl7.xml.builder.FeatureException;
import org.hl7.xml.validator.FeatureCardinalityException;
import org.hl7.xml.validator.CardinalityValidator;
import org.hl7.hibernate.Persistence;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.hibernate.Query;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Logger;

/**
 * An XML SAX content handler for parsing generic HL7 message
 * elements that stem from RIM class clones.
 * <p/>
 * If you need an introduction into SAX parser design, see the
 * appendix after the Java code in this file.
 * <p/>
 * Also note the DynamicContentHandler interface which adds
 * significant magic to the functioning of this code.
 *
 * @author Gunther Schadow
 * @version $Id: MessageElementContentHandler.java,v 1.1 2006-10-03 17:39:04 marwahah Exp $
 */
public class MessageElementContentHandler extends DynamicContentHandlerBase
    implements DynamicContentHandler, DynamicContentHandler.ResultReceiver
{
    protected static final Logger LOGGER = Logger.getLogger("org.hl7.xml.parser");
    protected static final Logger HIB_LOGGER = Logger.getLogger("org.hl7.hibernate");

    //-------------------------------------------------------------------------
    private static final JavaIts javaIts = new JavaItsImpl();

    /**
     * The clone class we are parsing.
     */
    CloneClass _cloneClass;
    /**
     * The currently parsed feature in that clone class.
     */
    Feature _currentFeature;

    /**
     * The result object that we are about to construct.
     */
    RimObject _result;


    /**
     * Get the result of this parse.
     */
    public RimObject getResult()
    {
        return this._result;
    }

    private static final String JSIG_NAMESPACE_URI = "urn:hl7-jsig";
    private static final String JSIG_ATT_CLASS = "class";

    private static final boolean CONF_USE_HIBERNATE = Boolean.parseBoolean(System.getProperty("org.hl7.xml.parser.MessageElementContentHandler.useHibernate", "false"));

    private static final boolean CONF_COMMIT_EVERY_CONTROL_ACT = Boolean.parseBoolean(System.getProperty("org.hl7.xml.parser.MessageElementContentHandler.commitEveryControlAct", "false"));

    private static final boolean CONF_MERGE_OBJECTS_WITH_SAME_ID = Boolean.parseBoolean(System.getProperty("org.hl7.xml.parser.MessageElementContentHandler.mergeObjectsWithSameId", "false"));
    private static final boolean CONF_MERGE_ENTITY_KINDS_WITH_SAME_CODE = Boolean.parseBoolean(System.getProperty("org.hl7.xml.parser.MessageElementContentHandler.mergeEntityKindsWithSameCode", "false"));
    private static final boolean CONF_MERGE_ACT_DEFINITIONS_WITH_SAME_CODE = Boolean.parseBoolean(System.getProperty("org.hl7.xml.parser.MessageElementContentHandler.mergeActDefinitionsWithSameCode", "false"));
    private static final boolean CONF_MERGE_ENTITY_KINDS_WITH_SAME_NAME = Boolean.parseBoolean(System.getProperty("org.hl7.xml.parser.MessageElementContentHandler.mergeEntityKindsWithSameName", "false"));

    /**
     * TODO: this is not yet implemented and it raises serious questions about what we have so far:
     * <p/>
     * If mergeObjectsWithSameId, when updating, merge the roles, participations, act-relationships and roleLinks, if they have
     * (a) the same source and target
     * (b) matching typeCode (or classCode and code)
     * (c) same sequence number (if applicable)
     * (d) the role that we have parsed must not have an id of itself
     * This is implemented in notifyResults when the result is such an associative object.
     * We then query for another such association with same target, and then pick the first one that has:
     * typeCode.nonNull.and(candidate.typeCode.nonNull).implies(typeCode.equals(candidate.typeCode))
     * .and(sequenceNumber.nonNull.and(candidate.sequenceNumber.nonNull).implies(sequenceNumber.equals(candidate.sequenceNumber)))
     * all of which we can actually do in the query itself by inner joining on all these keys.
     */
    private static final boolean CONF_MERGE_ASSOCIATIVE_OBJECT_WITH_SAME_SOURCE_AND_TARGET = Boolean.parseBoolean(System.getProperty("org.hl7.xml.parser.MessageElementContentHandler.mergeAssociativeObjectWithSameSourceAndTarget", "false"));

    /**
     * This is an experimental feature, where the source message can contain an attribute j:class with a Java FQCN of the class to instantiate.
     * This is enabled only if the jclass option is set on.
     * This is strictly speaking not very safe. Instead, the annotated MIF file should have such information.
     * But even if and when it can be done with annotated MIF files, there may be the need for such info at runtime.
     */
    private static final boolean CONF_OBEY_JCLASS_ATTRIBUTE = Boolean.parseBoolean(System.getProperty("org.hl7.xml.parser.MessageElementContentHandler.obeyJClassAttribute", "false"));
    private static final boolean CONF_USE_GLOBAL_CACHE = Boolean.parseBoolean(System.getProperty("org.hl7.xml.parser.MessageElementContentHandler.useGlobalCache", "false"));

    private static final Map<Object, RimObject> _CACHE = CONF_USE_GLOBAL_CACHE ? new org.regenstrief.util.Cache<Object, RimObject>() : new java.util.HashMap<Object, RimObject>();

    private static Query _queryForActById = null;

    private static final Query getQueryForActById()
    {
        if (_queryForActById == null)
        {
            _queryForActById = Persistence.createNamedQuery("actById");
        }
        return _queryForActById;
    }

    private static Query _queryForEntityById = null;

    private static final Query getQueryForEntityById()
    {
        if (_queryForEntityById == null)
        {
            _queryForEntityById = Persistence.createNamedQuery("entityById");
        }
        return _queryForEntityById;
    }

    private static Query _queryForEntityKindByCode = null;

    private static final Query getQueryForEntityKindByCode()
    {
        if (_queryForEntityKindByCode == null)
        {
            _queryForEntityKindByCode = Persistence.createNamedQuery("entityKindByCode");
        }
        return _queryForEntityKindByCode;
    }

    private static Query _queryForActDefinitionByCode = null;

    private static final Query getQueryForActDefinitionByCode()
    {
        if (_queryForActDefinitionByCode == null)
        {
            _queryForActDefinitionByCode = Persistence.createNamedQuery("actDefinitionByCode");
        }
        return _queryForActDefinitionByCode;
    }

    private static Query _queryForEntityKindByName = null;

    private static final Query getQueryForEntityKindByName()
    {
        if (_queryForEntityKindByName == null)
        {
            _queryForEntityKindByName = Persistence.createNamedQuery("entityKindByName");
        }
        return _queryForEntityKindByName;
    }

    private static Query _queryForRoleById = null;

    private static final Query getQueryForRoleById()
    {
        if (_queryForRoleById == null)
        {
            _queryForRoleById = Persistence.createNamedQuery("roleById");
        }
        return _queryForRoleById;
    }

    private static void clearQueries()
    {
        _queryForActById = null;
        _queryForEntityById = null;
        _queryForRoleById = null;
    }

    private final String cacheKey(II id)
    {
        return ((_result instanceof Act) ? "A:"
            : (_result instanceof Role) ? "R:"
            : (_result instanceof Entity) ? "E:"
            : "M") + id.toString();
    }

    /**
     * This is called from a child content handler return back the
     * result. Here, we set the current feature to the value returned.
     */
    public void notifyResult(Object value)
    {
        try
        {
            if (value != null)
            {
                if (this._currentFeature instanceof Association && value instanceof RimObject)
                {
                    Association association = (Association) this._currentFeature;
                    MessageElementContentHandler.LOGGER.finer(addLoc("add association: " + association.getName() +
                        " RIM property name: " + association.getRimPropertyName() +
                        " assoc: " + association));
                    RimUtil.add(this._result, association, (RimObject) value);

                    if (CONF_USE_HIBERNATE && value instanceof Act)
                    {
                        Act act = (Act) value;
                        if ((CONF_COMMIT_EVERY_CONTROL_ACT && act.getClassCode().implies(ActClass.ControlAct).isTrue()))
                        {
                            HIB_LOGGER.info("SAVING:" + act + "[" + act.getTitle() + "] ...");
                            Persistence.save(act);
                            Persistence.commit();
                            Persistence.close();
                            clearQueries();
                            HIB_LOGGER.info("... as " + ((org.hl7.rim.impl.ActImpl) act).getInternalId());
                            if (!CONF_USE_GLOBAL_CACHE)
                                _CACHE.clear();
                        }
                    }
                }
                else if (this._currentFeature instanceof Attribute && value instanceof ANY)
                {
                    add((Attribute) this._currentFeature, (ANY) value);

                    if (((ANY) value).nonNull().isTrue())
                    {

                        if (CONF_MERGE_OBJECTS_WITH_SAME_ID
                            && (_result instanceof Act || _result instanceof Role || _result instanceof Entity)
                            && _currentFeature.getName().equals("id"))
                        {

                            II id = (II) value;

                            HIB_LOGGER.finest("LOOKING FOR ID IN CACHE: " + cacheKey(id));
                            RimObject cachedObject = _CACHE.get(cacheKey(id));
                            if (cachedObject != null)
                            {
                                HIB_LOGGER.finest("OBJECT IN CACHE: " + cacheKey(id) + " --> " + ((org.hl7.rim.impl.RimObjectImpl) cachedObject).getInternalId());

                                if ((_result instanceof Act && cachedObject instanceof Act)
                                    || (_result instanceof Entity && cachedObject instanceof Entity)
                                    || (_result instanceof Role && cachedObject instanceof Role))
                                    _result = cachedObject;
                                else
                                    HIB_LOGGER.finest(addLoc("objects with same id whave different classes: " + id + ": " + _result + " != " + cachedObject));
                            }
                            else
                            {

                                if (CONF_USE_HIBERNATE)
                                {

                                    // query for all other id's to see if this one already exists
                                    if (_result instanceof Act)
                                    {
                                        Iterator<Act> acts = getQueryForActById()
                                            .setParameter("root", id.root().toString())
                                            .setParameter("extension", id.extension().isNull().isTrue() ? null : id.extension().toString())
                                            .list().iterator();
                                        if (acts.hasNext())
                                        { // an existing role with this ID has been found
                                            _result = acts.next();
                                            HIB_LOGGER.finest("FOUND ACT BY ID: " + id + " --> " + ((org.hl7.rim.impl.RimObjectImpl) _result).getInternalId());
                                        }
                                    }
                                    else if (_result instanceof Role)
                                    {
                                        Iterator<Role> roles = getQueryForRoleById()
                                            .setParameter("root", id.root().toString())
                                            .setParameter("extension", id.extension().isNull().isTrue() ? null : id.extension().toString())
                                            .list().iterator();
                                        if (roles.hasNext())
                                        { // an existing role with this ID has been found
                                            _result = roles.next();
                                            HIB_LOGGER.finest("FOUND ROLE BY ID: " + id + " --> " + ((org.hl7.rim.impl.RimObjectImpl) _result).getInternalId());
                                        }
                                    }
                                    else if (_result instanceof Entity)
                                    {
                                        Iterator<Entity> entities = getQueryForEntityById()
                                            .setParameter("root", id.root().toString())
                                            .setParameter("extension", id.extension().isNull().isTrue() ? null : id.extension().toString())
                                            .list().iterator();
                                        if (entities.hasNext())
                                        { // an existing entity with this ID has been found
                                            _result = entities.next();
                                            HIB_LOGGER.finest("FOUND ENTITY BY ID: " + id + " --> " + ((org.hl7.rim.impl.RimObjectImpl) _result).getInternalId());
                                        }
                                    }
                                }

                                HIB_LOGGER.finest("CACHING: " + cacheKey(id) + " --> " + ((org.hl7.rim.impl.RimObjectImpl) _result).getInternalId());
                                _CACHE.put(cacheKey(id), _result);
                            }
                        }

                        if (CONF_MERGE_ACT_DEFINITIONS_WITH_SAME_CODE && (_result instanceof Act) && _currentFeature.getName().equals("code"))
                        {
                            Act resultAct = (Act) _result;
                            SET<II> resultId = resultAct.getId();

                            if ((resultId == null || resultId.isNull().isTrue() || resultId.isEmpty().isTrue())
                                && resultAct.getMoodCode().implies(ActMood.Definition).isTrue())
                            {
                                CD code = (CD) value;
                                org.hl7.rim.impl.ActImpl cachedAct = (org.hl7.rim.impl.ActImpl) _CACHE.get(code);
                                if (cachedAct != null)
                                {
                                    HIB_LOGGER.finest("ACT CODE IN CACHE: " + code.toString() + " --> " + cachedAct.getInternalId());
                                    // FIXME: should MERGE properties loaded so far!
                                    _result = cachedAct;
                                }
                                else
                                {
                                    if (CONF_USE_HIBERNATE && !Persistence.isPersistent(_result))
                                    {
                                        Iterator<Act> acts = getQueryForActDefinitionByCode().setParameter("code", code.code().toString()).setParameter("codeSystem", code.codeSystem().toString()).list().iterator();
                                        if (acts.hasNext())
                                        { // an existing act with this code has been found
                                            _result = acts.next();
                                            HIB_LOGGER.finest("FOUND ACT BY CODE: " + code.code() + "@" + code.codeSystem() + " --> " + ((org.hl7.rim.impl.RimObjectImpl) _result).getInternalId());
                                        }
                                    }

                                    _CACHE.put(code, _result);
                                }
                            }
                        }

                        if (CONF_MERGE_ENTITY_KINDS_WITH_SAME_NAME && (_result instanceof Entity) && _currentFeature.getName().equals("code"))
                        {
                            Entity resultEntity = (Entity) _result;
                            SET<II> resultId = resultEntity.getId();

                            if ((resultId == null || resultId.isNull().isTrue() || resultId.isEmpty().isTrue())
                                && resultEntity.getDeterminerCode().implies(EntityDeterminer.Determined).isTrue())
                            {
                                CD code = (CD) value;
                                org.hl7.rim.impl.EntityImpl cachedEntity = (org.hl7.rim.impl.EntityImpl) _CACHE.get(code);
                                if (cachedEntity != null)
                                {
                                    HIB_LOGGER.finest("ENTITY CODE IN CACHE: " + code.toString() + " --> " + cachedEntity.getInternalId());
                                    // FIXME: should MERGE properties loaded so far!
                                    _result = cachedEntity;
                                }
                                else
                                {
                                    if (CONF_USE_HIBERNATE && !Persistence.isPersistent(_result))
                                    {
                                        Iterator<Entity> entities = getQueryForEntityKindByCode().setParameter("code", code.code().toString()).setParameter("codeSystem", code.codeSystem().toString()).list().iterator();
                                        if (entities.hasNext())
                                        { // an existing entity with this code has been found
                                            _result = entities.next();
                                            HIB_LOGGER.finest("FOUND ENTITY BY CODE: " + code.code() + "@" + code.codeSystem() + " --> " + ((org.hl7.rim.impl.RimObjectImpl) _result).getInternalId());
                                        }
                                    }

                                    _CACHE.put(code, _result);
                                }
                            }
                        }

                        if (CONF_MERGE_ENTITY_KINDS_WITH_SAME_CODE && (_result instanceof Entity) && _currentFeature.getName().equals("name"))
                        {
                            Entity resultEntity = (Entity) _result;
                            SET<II> resultId = resultEntity.getId();

                            if ((resultId == null || resultId.isNull().isTrue() || resultId.isEmpty().isTrue())
                                && resultEntity.getDeterminerCode()/*FIXME MUST BE: .implies(EntityDeterminer.Determined).isTrue(), NOT:*/.code().toString().equals("KIND"))
                            {
                                EN name = (EN) value;
                                if (name.length().isOne().isTrue())
                                {
                                    ENXP onlypart = name.iterator().next();
                                    if (onlypart.qualifier() != null && onlypart.qualifier().nonEmpty().isFalse())
                                    {
                                        String nameString = onlypart.toString();

                                        org.hl7.rim.impl.EntityImpl cachedEntity = (org.hl7.rim.impl.EntityImpl) _CACHE.get(nameString);
                                        if (cachedEntity != null)
                                        {
                                            HIB_LOGGER.finest("ENTITY NAME IN CACHE: " + nameString + " --> " + cachedEntity.getInternalId());
                                            // FIXME: should MERGE properties loaded so far!
                                            _result = cachedEntity;
                                        }
                                        else
                                        {
                                            if (CONF_USE_HIBERNATE && !Persistence.isPersistent(_result))
                                            {
                                                Iterator<Entity> entities = getQueryForEntityKindByName().setParameter("name", nameString).list().iterator();
                                                if (entities.hasNext())
                                                { // an existing entity with this name has been found
                                                    _result = entities.next();
                                                    HIB_LOGGER.finest("####FOUND ENTITY BY NAME: " + onlypart.toString() + " --> " + ((org.hl7.rim.impl.RimObjectImpl) _result).getInternalId());
                                                }
                                            }

                                            _CACHE.put(nameString, _result);
                                        }
                                    }
                                }
                            }
                        }
                    }

                }
                else if (value instanceof TreeContentHandler.Node
                    && this._result instanceof Extensible)
                {
                    ((Extensible) this._result)
                        .addExtensionNode((TreeContentHandler.Node) value);

                }
                else
                {
                    /* Oops, we should never get here! */
                    throw new Error(addLoc(" unhandled feature " + this._currentFeature.getClass()));
                }
            }
            else
            {
                LOGGER.warning(addLoc("oops, was notified of a value == null?"));
            }
        }
        catch (FeatureCardinalityException e)
        {
            LOGGER.warning(addLoc(e.getMessage()));
        }
    }


    /**
     * If this is not null, those are the collection elements that we
     * are just in the middle of parsing.
     */
    private Collection _currentCollection = null;

    /**
     * If this is not null, this is the type of the current collection
     * that we are just in the middle of collecting all its elements.
     */
    private Class _currentCollectionType = null;

    /**
     * If this is not null, this is the current collection attribute
     * that we are just in the middle of collecting all its elements.
     */
    private Attribute _currentAttribute = null;

    /**
     * Assign the currentCollection to the currentAttribute.
     * <p/>
     * XXX: this only works well if all elements that are members of this
     * collection do indeed appear together. This is true for strict
     * XML schema data, but need not be true for a relaxed syntax.
     */
    public void flushCurrentCollection()
    {
        try
        {
            String attributeName = this._currentAttribute.getRimPropertyName();

            LOGGER.finer(addLoc("flush collection: "
                + this._currentAttribute.getName() +
                ", RIM name: " + attributeName +
                ", attr: " + this._currentAttribute +
                ", type: " + _currentCollectionType));

            ANY value = null;

            Class type = null;

            //TODO: Need to be cleaned up with RimGraphGenerator
            if (LIST.class.isAssignableFrom(_currentCollectionType))
            {
                value = ValueFactory.getInstance().LISTvalueOf(this._currentCollection);
                type = LIST.class;
            }
            else if (IVL.class.isAssignableFrom(_currentCollectionType))
            {
                value = ValueFactory.getInstance().IVLvalueOf(this._currentCollection);
                type = IVL.class;
            }
            else if (SET.class.isAssignableFrom(_currentCollectionType))
            {
                value = ValueFactory.getInstance().SETvalueOf(this._currentCollection);
                type = SET.class;
            }
            else if (BAG.class.isAssignableFrom(_currentCollectionType))
            {
                value = ValueFactory.getInstance().BAGvalueOf(this._currentCollection);
                type = BAG.class;
            }

            CardinalityValidator.checkAttributeCardinality(_currentCollection, _currentAttribute);

            this._result.getClass()
                .getMethod("set" + StringUtils.forceInitialCap(attributeName),
                    new Class[]{type})
                .invoke(this._result, new Object[]{value});


        }
        catch (NoSuchMethodException ex)
        {
            throw new Error(addLoc(""), ex);
        }
        catch (IllegalAccessException ex)
        {
            throw new Error(addLoc(""), ex);
        }
        catch (InvocationTargetException ex)
        {
            throw new Error(addLoc(""), ex);
        }
        catch (FeatureCardinalityException e)
        {
            LOGGER.warning(addLoc(e.getMessage()));
        }
        finally
        {
            this._currentCollection = null;
            this._currentAttribute = null;
            this._currentCollectionType = null;
        }
    }

    public void add(Attribute attribute, ANY value)
    {
        try
        {
            String rimPropertyName = attribute.getRimPropertyName();
            Datatype dataType = attribute.getDatatype();
            String methodNameStem = StringUtils.forceInitialCap(rimPropertyName);
            Class type = javaIts.getRIMDataType(_result, methodNameStem);

            /* @MC
                        We must do this check before the maxOneCardinality call or else
                        maxCardinality call will throw an exception and QSETS are
                        skipped.
                   */
            if (value instanceof QSET || value instanceof SC)
            {
                this._result.getClass()
                    .getMethod("set" + methodNameStem, new Class[]{type})
                    .invoke(this._result, new Object[]{value});
                return;
            }

            if (type.isAssignableFrom(value.getClass())
                && (type.equals(ANY.class) || attribute.getCardinality().getMax() == 1))
            {
                // Maxim cardinality is 1,
                // Do not care about SimpleDatatype or ParametrizedDatatype
                LOGGER.finest(addLoc("add simple attribute: " + attribute.getName() +
                    " RIM name: " + rimPropertyName +
                    " attr: " + attribute +
                    " RIM type: " + type +
                    " type: " + dataType.getFullName()));

                if (type != null)
                {
                    // XXX, GS sez don't do this:
                    // CardinalityValidator.checkMaxOneCardinality(this._result, methodNameStem, attribute);
                    // 1) this is not a specific criterion to check this message, as the data
                    //    object can have been resolved to the database
                    // 2) it is too expensive of a check to do this reflection dance done inside this
                    //    procedure
                    // 3) instead cardinality checks should be done exclusively in the message parser
                    //    if need be by checking off a list of features already seen
                    // 4) I don't want to spend cycles for these checks anyway
                    this._result.getClass()
                        .getMethod("set" + methodNameStem, new Class[]{type})
                        .invoke(this._result, new Object[]{value});
                    return;
                }
            }

            if ((SET.class.isAssignableFrom(type)
                && !QSET.class.isAssignableFrom(type))
                || BAG.class.isAssignableFrom(type)
                || (LIST.class.isAssignableFrom(type) && !SC.class.isAssignableFrom(type))
                || IVL.class.isAssignableFrom(type))
            {
                // In RMIM, RIM class's IVL<TS> could constrain to TS
                LOGGER.finest(addLoc("add collection element: " + attribute.getName() +
                    " RIM name: " + rimPropertyName +
                    " attr: " + attribute +
                    " RIM type: " + type +
                    " type: " + dataType.getFullName()));

                // if we have an HL7 collection we will save the elements
                // on the currentCollection
                if (this._currentCollection == null)
                {
                    this._currentCollection = new ArrayList();
                    this._currentAttribute = attribute;
                }
                this._currentCollectionType = type;
                this._currentCollection.add(value);

                return;
            }

            /* FIXME: this is a hack to get the following to work:
                    *  <value value="13" unit="mm" xsi:type="PQ"/>
                    *
                    * XXX: GS sez, this looks like a hack, how can that be right?
                    if (value instanceof QTY) {
                    this._result.getClass()
                    .getMethod("set" + methodNameStem, new Class[]{type})
                    .invoke(this._result, new Object[]{value});
                    return;
                    }
                   */

            // XXX: GS sez: why do people comment out exceptions??? This is a
            // case of a program deficiency, we need these exception to help
            // us complete our work properly. If we comment these out, it only
            // looks like it's working when in fact it isn't!
            throw new Error(addLoc("unhandled case add of value '" + value + "' to attribute " + attribute + " : " + type));

        }
        catch (NoSuchMethodException ex)
        {
            throw new Error(addLoc(ex.getMessage()), ex);
        }
        catch (IllegalAccessException ex)
        {
            throw new Error(addLoc(ex.getMessage()), ex);
        }
        catch (InvocationTargetException ex)
        {
            throw new Error(addLoc(ex.getMessage()), ex);
        }
    }

    public void startElement(String namespaceURI,
                             String localName,
                             String qName,
                             Attributes atts) throws SAXException
    {

        try
        {
            /* 1. check with metadata if this is a good element. We can
                    do this in various modes: we can insist on the correct
                    ordering, or we may not. We can throw up errors on
                    unknown elements or we may not. In reality, it's better
                    to be more tolerant than paranoid.
               */
            LOGGER.finer(addLoc("parse " + this._cloneClass.getName() + " " + localName));

            Feature thisFeature = XmlItsUtil.lookupMetadataByTag(this._cloneClass, localName);

            /* check if there is a current collection pending that needs to be
                    flushed
               */
            if (_currentFeature != null && _currentFeature != thisFeature)
            {
                if (_currentCollection != null)
                    flushCurrentCollection();
                Accumulator.getInstance().clear();
            }

            _currentFeature = thisFeature;

            if (_currentFeature == null)
            {
                if (this._result instanceof Extensible)
                {
                    LOGGER.warning(addLoc("warning: unknown element " + localName + ", preserved as tree"));
                    parseTree(namespaceURI, localName, qName, atts);
                }
                else
                {
                    LOGGER.warning(addLoc("warning: unknown element " + localName + ", ignored"));
                    ignoreFeature(namespaceURI, localName, qName);
                }
            }
            else
            {
                /* Now, depending on whether the current feature is an Attribute
                         or an Association we do our thing.

                         I never like it when I have to manually specify the cases for
                         what really is parameter-polymorphism. This used to work easier
                         in C++, didn't it?
                    */
                if (this._currentFeature instanceof Attribute)
                    parseFeature((Attribute) this._currentFeature,
                        namespaceURI, localName, qName, atts);
                else if (this._currentFeature instanceof Association)
                    parseFeature((Association) this._currentFeature,
                        namespaceURI, localName, qName, atts);
                else /* Oops, we should never get here! */
                    throw new Error(addLoc("unhandled type of feature " + this._currentFeature.getClass()));
            }

            /* and that's all we have to do here. See endElement for how
                    the story continues.
               */
            return;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            throw new SAXParseException(addLoc(ex.getMessage()), getDocumentLocator(), ex);
        }
        catch (Throwable ex)
        {
            throw new SAXParseException(addLoc(ex.getMessage()), getDocumentLocator(), new Exception(ex));
        }
    }

    public void endElement(String namespaceURI, String localName, String qName) throws SAXException
    {
        try
        {
            /* We only see an endElement for the things that we parse ourselves.
                    if the data type value parser was called on an Attribute, it will
                    handle that endElement for us before it returns.

                    We will now do three things:

                    1) conclude the parsing of the current object,
                    2) notify the parent context about our result
                    3) release ourselves from the XMLReader
               */

            /* 1) To conclude parsing of this object may include filling in
                    defaults or perhaps doing some validation.

                    NOTE: while doing this validation, the object cannot look into
                    properties of it's parent yet!
               */

            LOGGER.finest(addLoc("add default attributes to " + _cloneClass.getName()));

            Iterator<Attribute> structuralAttributes
                = _cloneClass.iterateStructuralAttributes();
            while (structuralAttributes.hasNext())
            {
                Attribute attribute = (Attribute) structuralAttributes.next();

                String defaultString = attribute.getDefaultValue();
                if (defaultString == null)
                {
                    String constraints[] = attribute.getFixedValues();
                    if (constraints != null && constraints.length > 0)
                    {
                        defaultString = constraints[0];
                    }
                } // defaultString == null

                LOGGER.finest(addLoc("add default attribute " + attribute.getName()
                    + " = \"" + defaultString + "\""));

                if (defaultString != null)
                {
                    try
                    {
                        String attributeName = attribute.getRimPropertyName();
                        Datatype dataType = attribute.getDatatype();

                        String methodNameStem = attributeName.substring(0, 1).toUpperCase() +
                            attributeName.substring(1);

                        Class type = javaIts.getRIMDataType(_result, methodNameStem);

                        // XXX: I only handle structural *codes* here for now
                        if (type.isAssignableFrom(CS.class))
                        {
                            ANY value = (ANY) this._result.getClass().getMethod("get" + methodNameStem, (Class[]) null)
                                .invoke(this._result, (Object[]) null);

                            if (CardinalityValidator.getDatatypeCardinality(value) == 0)
                            {
                                LOGGER.finest(addLoc("add default attribute: " + attribute.getName() +
                                    " RIM name: " + attributeName +
                                    " attr: " + attribute +
                                    " RIM type: " + type +
                                    " type: " + dataType.getFullName()));
                                CS cs = null;
                                try
                                {
                                    cs = (CS) MetaUtils.getAttributeDefaultValue(value, attribute);
                                }
                                catch (FeatureException e)
                                {
                                    LOGGER.warning(addLoc(e.getFeature().getName() + ":" + e.getMessage()));
                                }

                                this._result.getClass()
                                    .getMethod("set" + methodNameStem, new Class[]{type})
                                    .invoke(this._result,
                                        new Object[]{cs});
                            } // end if CardinalityValidator.getDatatypeCardinality(value) == 0
                        }
                    }
                    catch (NoSuchMethodException ex)
                    {
                        throw new Error(addLoc(ex.getMessage()), ex);
                    }
                    catch (IllegalAccessException ex)
                    {
                        throw new Error(addLoc(ex.getMessage()), ex);
                    }
                    catch (InvocationTargetException ex)
                    {
                        throw new Error(addLoc(ex.getMessage()), ex);
                    }
                }
            } // end of while (structuralAttributes.hasNext())

            /* - check if there is a current collection pending that needs to be
                    flushed.
               */
            if (this._currentAttribute != null)
            {
                flushCurrentCollection();
            }

            /* - compact extension trees */
            if (this._result instanceof Extensible)
            {
                ((Extensible) this._result).compactExtensionNodes();
            }

            /* 2) Return the results via the results notification and
                    release ourselves as a content handler. */

            returnResult(this._result);
        }
        catch (Exception ex)
        {
            throw new SAXParseException(addLoc(ex.getMessage()), getDocumentLocator(), ex);
        }
        catch (Throwable ex)
        {
            throw new RuntimeException(addLoc(ex.getMessage()), ex);
        }
    }

    // FIXME: This may not have dealt with collections or choices at all.
    // But otherwise we are done with this.

    private void parseFeature(Attribute attribute,
                              String namespaceURI,
                              String localName,
                              String qName,
                              Attributes atts)
    {

        // Also, need to pass in the current attribute metadata,
        // because things like domain is here as well!

        Datatype dataType = attribute.getDatatype();

        /*
        * If we have ANY, we must do a little extra work to make sure the
        * appropriate handler is called.  We know we have an xsi type, so we
        * can use that to determine what the datatype actually is
        */
        if (dataType.equals(DatatypeMetadataFactoryDatatypes.instance().ANYTYPE))
        {
            try
            {
                String xsi = atts.getValue(DatatypePresenterBase.W3_XML_SCHEMA, "type");
                dataType = DatatypeMetadataFactoryImpl.instance().createByXsiType(xsi);
            }
            catch (UnknownDatatypeException ex)
            {
                LOGGER.warning(addLoc(ex.getMessage()));
            }
        }

        // If we have CS datatype as a regular xml element, such as 'statusCode', we need to find out the code system name
        // from the damain name sicne CS xml schema data type does not allow to have code system name
        if (dataType.equals(DatatypeMetadataFactoryDatatypes.instance().CSTYPE))
        {
            UID codeSystem = MetaUtils.getCodeSystemId(attribute.getDomains());
            if (codeSystem != null)
            {
                CS cs = CSimpl.valueOf(atts.getValue("code"), codeSystem.toString());
                this.suspendWith(new SimpleTypeContentHandler(cs), null);
                return;
            }
        }

        DynamicContentHandler newContext = null;

        try
        {
            newContext = dataType.getHandler(namespaceURI, localName, qName, atts);
        }
        catch (FactoryException ex)
        {
            newContext = null;
        }
        catch (Throwable th)
        {
            newContext = null;
        }

        // Now we make the new context the content handler of the XML
        // parser.

        if (newContext != null)
            this.suspendWith(newContext, atts);
        else
        {
            // no special ContentHandler class available, so we will use
            // the TreeContentHandler and then later we will hopefully
            // find a factory method that can use the little DOM to build
            // a value of this kind.
            LOGGER.warning(addLoc("No content handler for data type '"
                + dataType.getFullName()
                + "', element name " + localName));

            parseTree(namespaceURI, localName, qName, atts);
        }
    }

    private void ignoreFeature(String namespaceURI,
                               String localName,
                               String qName)
    {
        /* FIXME: this is not good. We produce tons of garbage here.
           And there is hardly any two ignoreContentHandlers used
           at the same time by the same object... So, could have
           at least my own private ignore handler instance to call
           up.
        */
        this.suspendWith(new IgnoreContentHandler(), null);
    }

    private void parseFeature(Association association,
                              String namespaceURI,
                              String localName,
                              String qName,
                              Attributes atts)
    {
        try
        {
            /* FIXME: not as bad as the other two, but still a cache of
                       reusable objects might be useful if a lot of parsing is
                       done.
            */
            MessageElementContentHandler newContext
                = new MessageElementContentHandler(association.getTarget(), atts);

            /* Now we make the new context the content handler of the XML
                       parser.
            */
            this.suspendWith(newContext, atts);

        }
        catch (LoaderException ex)
        {
            throw new Error(addLoc(ex.getMessage()), ex);
        }
    }

    private void parseTree(String namespaceURI,
                           String localName,
                           String qName,
                           Attributes atts)
    {
        /* FIXME: this is not good. We produce tons of garbage here.
           And there is hardly any two TreeContentHandlers used
           at the same time by the same object... So, could have
           at least my own private handler instance to call up.
        */
        DynamicContentHandler tree =
            new TreeContentHandler(namespaceURI, localName, qName, atts);
        this.suspendWith(tree, null);
    }

    /**
     * Called when this DynamicContentHandler is activated. This
     * gives us a chance to inspect and handle attributes.
     * Process the structure attributes
     */
    protected void notifyActivation(Attributes atts)
    {

        if (atts != null)
        {
            int max = atts.getLength();
            for (int i = 0; i < max; i++)
            {
                /* This process is similar as startElement()

                    Check with metadata if this is a good element. We can
                    do this in various modes: we can insist on the correct
                    ordering, or we may not. We can throw up errors on
                    unknown elements or we may not. In reality, it's better
                    to be more tolerant than paranoid.

                    The current feature must be an Attribute.
                    */
                Attribute attribute = null;

                attribute = (Attribute) XmlItsUtil.lookupMetadataByTag(this._cloneClass, atts.getLocalName(i));
                if (attribute == null)
                {
                    if (atts.getURI(i) == null || atts.getURI(i).length() == 0)
                    { // ignore if there is some namespace
                        if (this._result instanceof Extensible)
                        {
                            LOGGER.warning(addLoc("unhandled attribute: "
                                + atts.getLocalName(i) +
                                " add as extension node"));
                            ((Extensible) this._result).addExtensionNode(new TreeContentHandler.Attribute(atts.getURI(i),
                                atts.getLocalName(i),
                                atts.getQName(i),
                                atts.getValue(i)));

                        }
                        else
                        {
                            LOGGER.warning(addLoc("ignore unhandled attribute: "
                                + atts.getLocalName(i)));
                        }
                    }
                    continue;
                }

                Datatype attributeDatatype = attribute.getDatatype();

                try
                {

                    String attributeName = attribute.getRimPropertyName();

                    LOGGER.finest(addLoc("add structural code: "
                        + attribute.getName() +
                        " RIM name: " + attributeName +
                        " attr: " + attribute));

                    String methodNameStem = StringUtils.forceInitialCap(attributeName);

                    Class type = javaIts.getRIMDataType(_result, methodNameStem);
                    String setterName = "set" + methodNameStem;
                    ANY value = null;
                    if (!type.isAssignableFrom(SET.class))
                    {

                        Method setter = this._result.getClass()
                            .getMethod(setterName, new Class[]{
                                Class.forName("org.hl7.types." + attributeDatatype.getFullName())
                            });

                        if (attributeDatatype.equals(DatatypeMetadataFactoryDatatypes.instance().CSTYPE))
                        {
                            /* FIXME: this could be something other than CS or BL, but turns
                                        out that right now it is only CS or BL.*/
                            UID codeSystem = MetaUtils.getCodeSystemId(attribute.getDomains());
                            if (codeSystem != null)
                                value = CSimpl.valueOf(atts.getValue(i), codeSystem.toString());
                            else
                            {
                                LOGGER.warning(addLoc("unknown code system for " + Arrays.asList(attribute.getDomains())));
                            }
                        }
                        else if (attributeDatatype.equals(DatatypeMetadataFactoryDatatypes.instance().BLTYPE))
                        {
                            value = BLimpl.valueOf(atts.getValue(i));
                        }
                        else /* Oops, we should never get here! */
                            throw new Error(addLoc("assertion failed, data type is " + attributeDatatype.getFullName()));

                        setter.invoke(this._result, new Object[]{value});
                    }
                    else
                    {
                        /* FIXME: this could be something other than SET<CS>, SET<OID> */
                        Datatype parameterType =
                            ((ParametrizedDatatype) attribute.getDatatype()).getParameter(0);
                        if (parameterType.equals(DatatypeMetadataFactoryDatatypes.instance().CSTYPE))
                        {
                            /* FIXME: this could be something other than CS or BL, but turns
                                        out that right now it is only CS or BL.*/
                            value = CSimpl.valueOf(atts.getValue(i), "CS-ID");
                        }
                        else if (parameterType.equals(DatatypeMetadataFactoryDatatypes.instance().OIDTYPE))
                        {
                            value = OIDimpl.valueOf(atts.getValue(i));
                        }
                        else /* Oops, we should never get here! */
                            throw new Error(addLoc("assertion failed, data type is " + attributeDatatype.getFullName()));

                        value = SETjuSetAdapter.valueOf(Arrays.asList(new ANY[]{value}));
                        this._result.getClass().getMethod(setterName, new Class[]{type})
                            .invoke(this._result, new Object[]{value});

                    }

                }
                catch (ClassNotFoundException ex)
                {
                    throw new Error(addLoc(ex.getMessage()), ex);
                }
                catch (NoSuchMethodException ex)
                {
                    throw new Error(addLoc(ex.getMessage()), ex);
                }
                catch (IllegalAccessException ex)
                {
                    throw new Error(addLoc(ex.getMessage()), ex);
                }
                catch (InvocationTargetException ex)
                {
                    throw new Error(addLoc(ex.getMessage()), ex);
                }
            }
        }
    }

    MessageElementContentHandler(CloneClass cloneClass, Attributes atts)
    {
        super();
        this._cloneClass = cloneClass;

        // Now we instantiate a RIM class

        this._result = null;

        if (CONF_OBEY_JCLASS_ATTRIBUTE)
        {
            // This is a new experimental feature, where the source message can contain
            // an attribute j:class with a Java FQCN of the class to instantiate. This
            // is enabled only if the jclass option is set on.

            String jclass = atts.getValue(JSIG_NAMESPACE_URI, JSIG_ATT_CLASS);
            if (jclass != null)
            {
                try
                {
                    _result = (RimObject) Class.forName(jclass).newInstance();
                }
                catch (Throwable ex)
                {
                    LOGGER.warning(addLoc("could not instantiate j:class " + jclass));
                }
            }
        }

        if (_result == null)
        {
            try
            {
                this._result = cloneClass.getInstance();
            }
            catch (FactoryException ex)
            {
                throw new Error(addLoc(ex.getMessage()), ex);
            }
        }
    }
}

/* APPENDIX: About SAX Parser Writing

We first must understand that a SAX parser is an XML parser, usually
a black box product that deals with most of the pointy-bracket
parsing. The sax parser implements the XMLReader interface.
Our parser is simply a ContentHandler to the XML parser
gizmo that we use. So, our parser receives callback calls from the
main parser. In order to receive such calls, it needs to be registered
as the parser's ContentHandler.

parser.setContentHandler(this);

This is a very important method, because we will have several
content handlers, who are specialists in parsing things like
RIM objects, and data type values. The RIM object parser will
probably be a single ContentHandler that interprets the HMD
metadata to work. But data types will use a different content
handler per each different data type.

The content handler methods are (in the order of practical
importance):

void startElement(String namespaceURI, String localName,
String qName, Attributes atts)
Receive notification of the beginning of an element.

-> this is the only start-event that really matters for the
bulk of the RIM object parsing. On a start element event,
the RIM parser will have to query the HMD metadata thing
to see
- if this new one is a valid element allowed in the
current element
- if it should create any other default elements that
should have occurred in the message (default handling)
- which other handler (if any) to use to proceed with
parsing this element.

If our RIM object parser does not call another specialist
parser, it will now put its current context on a stack
and set up a new context for the current element, then it
will listen for additional events.

NOTE: we have identified the parser itself with the
stack frame (or "context"), and use the DynamicContentHandler
framework to implement the stack.

void endElement(String namespaceURI, String localName, String qName)
Receive notification of the end of an element.

-> this is an indication that the parser is done with
something. Typically corresponds with completing the creation
and setup of the newly instantiated RIM object filling in all
remaining defaults, then popping the old context off the stack
and proceeding with parsing the parent element.

NOTE: with the DynamicContentHandler, the popping off the
stack is a little different, so transparent it's almost
like magic :-) (clue: watch for releaseReader() call.)

void characters(char[] ch, int start, int length)
Receive notification of character data.
void ignorableWhitespace(char[] ch, int start, int length)
Receive notification of ignorable whitespace in element content.

-> these are probably only used by the data type value parsers!


void startDocument()
Receive notification of the beginning of a document.
void endDocument()
Receive notification of the end of a document.

-> these are trivial, probably handled by some top-level
setter-upper gizmo.

The rest is special things that I won't even list here.
*/

/**
 * HISTORY      : $Log: not supported by cvs2svn $
 * HISTORY      : Revision 1.112  2006/06/21 02:36:22  echen
 * HISTORY      : Add PORR_MT040005 support
 * HISTORY      :
 * HISTORY      : Revision 1.111  2006/05/23 21:44:42  echen
 * HISTORY      : Support PORT_MT030001
 * HISTORY      :
 * HISTORY      : Revision 1.110  2006/05/19 15:20:04  echen
 * HISTORY      : Fix sortKey problem to replace "_" with space
 * HISTORY      :
 * HISTORY      : Revision 1.109  2006/05/15 16:16:51  echen
 * HISTORY      : Support CS data type as a RIM object attribute but not as a structure attribute
 * HISTORY      :
 * HISTORY      : Revision 1.108  2006/05/11 22:46:54  echen
 * HISTORY      : Saved Point
 * HISTORY      :
 * HISTORY      : Revision 1.107  2006/05/11 20:01:24  echen
 * HISTORY      : The feature of Class has an attribute 'sortKey', which is defined by MIF schema file as
 * HISTORY      :       'A name used in determining the sort order of the model element within its siblings.'
 * HISTORY      :  However, the MIF file does not display the class associations element sequentially as the sortKey indicates, for example at COCT_MT090100 message type, PrincipalChoiceList display the association "asRoleOther" first, which has the sortKey value 3:
 * HISTORY      :  And it's related COCT_MT090100  schema file follows the displaying sequence of MIF, but not the sortKey value
 * HISTORY      :
 * HISTORY      : Revision 1.106  2006/04/13 20:47:52  gschadow
 * HISTORY      : added mergeActDefinitionsWithSameCode
 * HISTORY      :
 * HISTORY      : Revision 1.105  2006/01/24 15:09:01  gschadow
 * HISTORY      : *** empty log message ***
 * HISTORY      :
 * HISTORY      : Revision 1.104  2005/12/21 19:53:23  gschadow
 * HISTORY      : just a minor fix this time
 * HISTORY      :
 * HISTORY      : Revision 1.103  2005/12/17 04:29:09  gschadow
 * HISTORY      : the equal op on II was wrong, it didn't work when extension was NULL,
 * HISTORY      : but that is explicitly allowed.
 * HISTORY      :
 * HISTORY      : Revision 1.102  2005/12/15 22:36:14  gschadow
 * HISTORY      : *** empty log message ***
 * HISTORY      :
 * HISTORY      : Revision 1.101  2005/12/15 03:35:56  gschadow
 * HISTORY      : build file has some new tricks to start the HSQLDB server automatically
 * HISTORY      : the content handlers now track a document Locator to give information
 * HISTORY      : on source XML location of any errors and warnings
 * HISTORY      :
 * HISTORY      : Revision 1.100  2005/12/13 16:58:50  gschadow
 * HISTORY      : replaced the OidLoader thing with something that knows all the sub-
 * HISTORY      : domains that are referenced in the message definitions. The DomainMapImpl
 * HISTORY      : is simply a Map<String, UID> that reads its data from the domain-oid-map.xml
 * HISTORY      : file, which exists in etc as a seed (see SQL query in there) and also
 * HISTORY      : is expanded from the vocab.xml file like the enums. TODO: handle Domains
 * HISTORY      : as real objects and move the vocab.xml into the version specific data
 * HISTORY      : directory. It is the same as Vocabulary.xml in the release.
 * HISTORY      :
 * HISTORY      : Revision 1.99  2005/12/12 17:20:16  gschadow
 * HISTORY      : *** empty log message ***
 * HISTORY      :
 * HISTORY      : Revision 1.98  2005/12/09 16:54:18  gschadow
 * HISTORY      : The Cache now implements most of the Map interface. The Cache in the
 * HISTORY      : message parser is also not so very useful, as the map grows we seem to
 * HISTORY      : be wasting cycles in its maintenance for little benefit. So, now we
 * HISTORY      : have a simple Map caching which is effective locally within one
 * HISTORY      : ControlAct transaction and cleared afterwards. Also, there was a bug
 * HISTORY      : in the last revisions where the cache was always cleared.
 * HISTORY      :
 * HISTORY      : Revision 1.97  2005/12/07 23:10:36  gschadow
 * HISTORY      : more fix for dropped BLnull, allTests succeed now
 * HISTORY      :
 * HISTORY      : Revision 1.96  2005/12/01 05:15:12  gschadow
 * HISTORY      : introduce the DSET interface for guaranteed discrete sets. This comes
 * HISTORY      : a little late, so it may cause some ripples. Initial testing worked
 * HISTORY      : for me.
 * HISTORY      :
 * HISTORY      : Revision 1.95  2005/11/23 22:19:08  gschadow
 * HISTORY      : small but effective bug fix on INT.lessOrEqual(PINF)
 * HISTORY      : in the message element content handler I am adding handling of an
 * HISTORY      : extension attribute j:class, which allows the inbound message to
 * HISTORY      : set the implementation class for the RIM class to create. Like
 * HISTORY      : all my advanced features, this is protected and has to be turned
 * HISTORY      : on explicitly to use, otherwise that attribute is ignored.
 * HISTORY      :
 * HISTORY      : Revision 1.94  2005/11/21 21:03:47  echen
 * HISTORY      : Add Test Case for Parse and Build
 * HISTORY      :
 * HISTORY      : Revision 1.93  2005/11/08 15:26:50  echen
 * HISTORY      : Add history section at the end of the file
 * HISTORY      :
 * HISTORY      :
 */
