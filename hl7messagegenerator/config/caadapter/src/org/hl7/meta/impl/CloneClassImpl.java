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
* The Initial Developer of the Original Code is Skirmantas Kligys.
* Portions created by Initial Developer are Copyright (C) 2002-2004
* Health Level Seven, Inc. All Rights Reserved.
*
* Contributor(s): Eric Chen
*/
package org.hl7.meta.impl;

import org.hl7.meta.*;
import org.hl7.meta.util.NullIterator;
import org.hl7.rim.RimObject;
import org.hl7.rim.RimObjectFactory;
import org.hl7.util.FactoryException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

/**
 * An implementation of metadata for an HL7 v3 clone class.
 *
 * @author Skirmantas Kligys (skirmis@hotmail.com)
 */
public class CloneClassImpl implements CloneClass
{

    protected static final Logger LOGGER = Logger.getLogger("org.hl7.meta");
    //-------------------------------------------------------------------------
    private MessageType messageType_;

    /**
     * Clone class name, unique in a message type.
     */
    private String name_;

    /**
     * Clone class name, unique in a message type.
     */
    private String fullname_;


    /**
     * Name of the RIM class name from which this clone class was derived.
     */
    private String rimClass_;

    /**
     * List of 'choices'.  Should be redone as a choice between metadata classes.
     */
    private String[] choices_;

    /**
     * Attributes and associations contained in this clone class.
     */
    private List<Feature> children_;

    /**
     * Subset of children_: structural attributes only.
     */
    private List<Attribute> structuralAttributes_;

    /**
     * Subset of children_: extra attributes needs to spit out as an attribute for the clone class.
     */
    private List<Attribute> extraAttributes_;


    /**
     * Parent Clone Class
     */
    private CloneClass parent_;

    //-------------------------------------------------------------------------
    /**
     * Default constructor.
     */
    public CloneClassImpl(MessageTypeImpl messageType)
    {
        messageType_ = messageType;
        initInfrastructureRootAttributes();
        // TODO: a kludge to fix the ITS 'type' attribute
        addStructureAttribute("type", "CS", "RimObject");
    }

    //-------------------------------------------------------------------------
    public MessageType getMessageType()
    {
        return messageType_;
    }

    //-------------------------------------------------------------------------
    /**
     * Returns the clone class name, unique in a message type.
     *
     * @return the clone class name, , known as clone code
     */
    public String getName()
    {
        return name_;
    }


    //-------------------------------------------------------------------------
    /**
     * Returns the clone class full path name, unique in a message type,
     * for example: "AdverseEventInvestigation.authorOrPerformer.AssignedPerson"
     *
     * @return the clone class full path name
     */

    public String getFullName()
    {
        return fullname_;
    }
    //-------------------------------------------------------------------------
    /**
     * Returns the name of the RIM class name from which this clone class was
     * derived.
     *
     * @return the RIM class name
     */
    public String getRimClass()
    {
        return rimClass_;
    }

    //-------------------------------------------------------------------------
    /**
     * Returns an instance of the RIM class name from which this clone class was
     * derived.
     *
     * @return the RIM object
     * @throws FactoryException if creating a RIM object fails
     */
    public RimObject getInstance() throws FactoryException
    {
        return RimObjectFactory.getInstance().createRimObject(rimClass_);
    }

    //-------------------------------------------------------------------------
    /**
     * Returns the list of 'choices'.  Should be redone as a choice between
     * metadata classes.
     *
     * @return the list of choices
     */
    public String[] getChoices()
    {
        return choices_;
    }

    //-------------------------------------------------------------------------
    /**
     * Returns the flag indicating if the clone class is abstract.
     *
     * @return abstract indicator
     */
    public boolean isAbstract()
    {
        return choices_ != null;
    }

    public List getCommomAssociations()
    {
        List<Feature> commonAssociations = new ArrayList<Feature>();
        if (!isAbstract())
        {
            return commonAssociations;
        }
        for (int i = 0; i < children_.size(); i++)
        {
            Feature feature = children_.get(i);
            if (feature instanceof Association)
            {
                if (!JdomMessageTypeLoader.GENERALIZES.equals(feature.getRimPropertyName()))
                {
                    commonAssociations.add(feature);
                }
            }
        }
        return commonAssociations;
    }

    //-------------------------------------------------------------------------
    /**
     * Returns the iterator of attributes and associations contained in this
     * clone class.
     *
     * @return the iterator of children
     */
    public Iterator<Feature> iterateChildren()
    {
        return (children_ == null) ? new NullIterator<Feature>() :
            Collections.unmodifiableList(children_).iterator();
    }

    //-------------------------------------------------------------------------
    /**
     * Counts attributes and associations contained in this clone class.
     *
     * @return the number of children
     */
    public int countChildren()
    {
        return (children_ == null) ? 0 : children_.size();
    }

    //-------------------------------------------------------------------------
    /**
     * Indexed accessor to attributes and associations.
     *
     * @param index index of child to return
     * @return the reference to the child at the index
     */
    public Feature getChild(int index)
    {
        if (children_ == null) throw new IndexOutOfBoundsException();
        return children_.get(index);
    }

    //-------------------------------------------------------------------------
    /**
     * Returns the iterator of structural attributes contained in this
     * clone class.
     *
     * @return the iterator of structural attributes
     */
    public Iterator<Attribute> iterateStructuralAttributes()
    {
        return (structuralAttributes_ == null) ? new NullIterator<Attribute>() :
            Collections.unmodifiableList(structuralAttributes_).iterator();
    }

    /**
     * Counts structural attributes contained in this clone class.
     *
     * @return the number of structural attributes
     */
    public int countStructuralAttributes()
    {
        return (structuralAttributes_ == null) ? 0 : structuralAttributes_.size();
    }

    /**
     * Indexed accessor to structural attributes.
     *
     * @param index index of structural attribute to return
     * @return the reference to the structural attribute at the index
     */
    public Attribute getStructuralAttribute(int index)
    {
        if (structuralAttributes_ == null) throw new IndexOutOfBoundsException();
        return (Attribute) structuralAttributes_.get(index);
    }

    /**
     * Counts extra attributes contained in this clone class.
     *
     * @return the iterator of extra attributes
     */

    public Iterator<Attribute> iterateExtraAttributes()
    {
        return (extraAttributes_ == null) ? new NullIterator<Attribute>() :
            Collections.unmodifiableList(extraAttributes_).iterator();

    }

    //-------------------------------------------------------------------------
    /**
     * Sets the clone class name, unique in a message type.
     * Should be called only from an implementation of
     * {@link org.hl7.meta.MessageTypeLoader MessageTypeLoader}.
     *
     * @param name the clone class name
     */
    /*default*/
    public void setName(String name)
    {
        name_ = name;
    }

    //-------------------------------------------------------------------------
    /**
     * Sets the clone class full path name.
     * Should be called only from an implementation of
     * {@link org.hl7.meta.MessageTypeLoader MessageTypeLoader}.
     *
     * @param fullname full class path name
     */
    /*default*/
    public void setFullName(String fullname)
    {
        fullname_ = fullname;
    }


    //-------------------------------------------------------------------------
    /**
     * Sets the name of the RIM class name from which this clone class was
     * derived.
     * Should be called only from an implementation of
     * {@link org.hl7.meta.MessageTypeLoader MessageTypeLoader}.
     *
     * @param rimClass the RIM class name
     */
    /*default*/
    void setRimClass(String rimClass)
    {
        if (rimClass.endsWith("Heir"))
        {
            rimClass_ = rimClass.substring(0, rimClass.length() - 4);
        }
        else
        {
            rimClass_ = rimClass;
        }
    }

    //-------------------------------------------------------------------------
    /**
     * Sets the list of 'choices'.  Should be redone as a choice between
     * metadata classes.
     *
     * @param choices the list of choices
     */
    /*default*/
    void setChoices(String[] choices)
    {
        choices_ = choices;
    }

    //-------------------------------------------------------------------------
    /**
     * Adds an attribute at the end of the list of features of the clone class.
     * Should be called only from an implementation of
     * {@link org.hl7.meta.MessageTypeLoader MessageTypeLoader}.
     *
     * @param attribute an attribute to be added
     */
    /*default*/
    void addAttribute(Attribute attribute)
    {
        if (children_ == null)
        {
            children_ = new ArrayList<Feature>();
        }
        children_.add(attribute);

        if (attribute.isStructural())
        {
            if (structuralAttributes_ == null)
            {
                structuralAttributes_ = new ArrayList<Attribute>();
            }
            if (!structuralAttributes_.contains(attribute))
                structuralAttributes_.add(attribute);
        }

// IsExtra method is deprecated
//        if (attribute.isExtra())
//        {
//            if (extraAttributes_ == null)
//            {
//                extraAttributes_ = new ArrayList<Attribute>();
//            }
//            extraAttributes_.add(attribute);
//        }

    }

    //-------------------------------------------------------------------------
    /**
     * Adds an association at the end of the list of features of the clone class.
     * Should be called only from an implementation of
     * {@link org.hl7.meta.MessageTypeLoader MessageTypeLoader}.
     *
     * @param assoc an association to be added
     */
    /*default*/
    void addAssociation(Association assoc)
    {
        if (children_ == null)
        {
            children_ = new ArrayList<Feature>();
        }
        children_.add(assoc);
    }

    //-------------------------------------------------------------------------
    /**
     * Replaces an association at the given index.
     * Should be called only from an implementation of
     * {@link org.hl7.meta.XmlIts XmlIts}.
     *
     * @param i     index
     * @param assoc an association to be added
     */
    /*default*/
    void replaceAssociation(int i, Feature assoc)
    {
        if (children_ == null) throw new IndexOutOfBoundsException();
        children_.set(i, assoc);
    }

    //-------------------------------------------------------------------------
    /**
     * Init infrastructure:
     * templateId  :: SET<OID>
     * realmCode :: SET<CS>
     * typeId :: SET<OID>
     * nullFlavor :: CS
     * <p/>
     * Should be called only from contructor
     */

    private void initInfrastructureRootAttributes()
    {
        return;
// we will not consider to support those for now. -Eric 08-02-2005      
//      addStructureAttribute("templateId", "SET<OID>", "InfrastructureRoot");
//      addStructureAttribute("realmCode", "SET<CS>", "InfrastructureRoot");
//      addStructureAttribute("typeID", "SET<OID>", "InfrastructureRoot");

    }

    private Attribute addStructureAttribute(String name, String dataType, String rimClassName)
    {
        AttributeImpl ai = new AttributeImpl(this);
        ai.setName(name);
        DatatypeMetadataFactory dmFactory_ = DatatypeMetadataFactoryImpl.instance();
        try
        {
            ai.setDatatype(dmFactory_.create(dataType));
        }
        catch (UnknownDatatypeException ex)
        {
            LOGGER.warning("Cannot load the Infrastructure Root Attribute." + name
                + ". Unknown datatype: " + dataType);
        }
        ai.setRimPropertyName(name);
        ai.setRimClass(rimClassName);
        ai.setCardinality(Cardinality.create("0..*"));
        ai.setDomains(new String[]{"RoleClass"});
        addAttribute(ai);
        return ai;
    }

    //-------------------------------------------------------------------------
    /**
     * Returns a string representation of the clone class metadata.
     * It looks similar to the following:
     * <pre>
     * Clone class &lt;name&gt; clones &lt;rim class&gt;: [&lt;choices&gt;]
     * ^^ &lt;attribute&gt;: &lt;datatype&gt; // 0..1
     * -- &lt;clone association&gt;: &lt;target clone class&gt; // 1..*
     * -- &lt;CMET association&gt;: &lt;target CMET ID&gt; // 0..2
     * </pre>
     *
     * @return a string representation
     */
    public String toString()
    {
        StringBuffer sb = new StringBuffer();

        sb.append("Clone class ");
        sb.append(name_);
        sb.append(" clones ");
        sb.append(rimClass_);
        if (choices_ == null)
        {
            sb.append('\n');
        }
        else
        {
            sb.append(": [");
            for (int i = 0; i < choices_.length; ++i)
            {
                if (i > 0) sb.append(" | ");
                sb.append(choices_[i]);
            }
            sb.append("]\n");
        }

        for (Iterator<Feature> it = iterateChildren(); it.hasNext();)
        {
            Feature f = it.next();

            if (f instanceof Attribute)
            {
                Attribute a = (Attribute) f;

                sb.append("^^ ");
                sb.append(a.toString());
                sb.append('\n');
            }
            else if (f instanceof Association)
            {
                Association a = (Association) f;

                sb.append("-- ");
                sb.append(a.toString());
                sb.append('\n');
            }
            else if (f instanceof ChoiceAssociation)
            {
                ChoiceAssociation ca = (ChoiceAssociation) f;

                sb.append("== ");
                sb.append(ca.toString());
                sb.append('\n');
            }
        }

        return sb.toString();
    }

    public CloneClass getParent()
    {
        return parent_;
    }

    public void setParent(CloneClass parent)
    {
        parent_ = parent;
    }

    /**
     * Deep copy Clone, shallow copy structure attributes and extra attributes
     *
     * @return
     * @throws CloneNotSupportedException
     */
    protected CloneClassImpl clone() throws CloneNotSupportedException
    {
        CloneClassImpl cloneClass = (CloneClassImpl) super.clone();
        cloneClass.children_ = new ArrayList<Feature>();
        return cloneClass;
    }

    /**
     * Always be called from MessageTypeImpl
     * @param messageType
     * @param cloneChild clone Child or not
     * @return
     * @throws CloneNotSupportedException
     */
    protected CloneClassImpl clone(MessageTypeImpl messageType, boolean cloneChild) throws CloneNotSupportedException
    {
        CloneClassImpl cloneClass = null;
        cloneClass = (CloneClassImpl) messageType.lookupCloneClass(this.name_);
        if (cloneClass == null)
        {
            cloneClass = (CloneClassImpl) clone();
            cloneClass.messageType_ = messageType;
        }

        if (cloneChild)
        {
//            System.out.println(cloneClass.getName());
            for (int i = 0; i < this.children_.size(); i++)
            {
                Feature feature = this.children_.get(i);
//                System.out.println("Feature:" + feature.getName());
                cloneClass.children_.add(((FeatureImpl) feature).clone(cloneClass));

            }
        }

        return cloneClass;
    }


}
