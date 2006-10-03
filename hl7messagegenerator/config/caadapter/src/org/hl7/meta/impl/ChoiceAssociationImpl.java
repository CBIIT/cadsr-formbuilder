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
* The Initial Developer of the Original Code is .
* Portions created by Initial Developer are Copyright (C) 2002-2004 
* Health Level Seven, Inc. All Rights Reserved.
*
* Contributor(s): 
*/
package org.hl7.meta.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.hl7.meta.*;
import org.hl7.meta.util.NullIterator;

/**
 * An implementation of metadata for a choice between several associations.
 *
 * @author Jerry Joyce, Eric Chen
 */
public class ChoiceAssociationImpl extends AssociationImpl
    implements ChoiceAssociation
{


    /**
     * Clone class that is targeted by this association.
     */
    private CloneClass target_;


    /**
     * associations contained in this clone class.
     */
    private List<Association> choices_;

    //-------------------------------------------------------------------------
    /**
     * Constructor.
     */
    public ChoiceAssociationImpl(CloneClass parent)
    {
        super(parent);
    }

    public void setTarget(CloneClass target_)
    {
        this.target_ = target_;
    }

    public CloneClass getTarget() throws LoaderException
    {
        return target_;
    }

    //-------------------------------------------------------------------------
    /**
     * Returns the iterator of choices contained in choice association.
     *
     * @return the iterator of choices
     */
    public Iterator<Association> iterateChoices()
    {
        return (choices_ == null) ? new NullIterator<Association>() :
            Collections.unmodifiableList(choices_).iterator();
    }

    //-------------------------------------------------------------------------
    /**
     * Counts choices contained in this choice association.
     *
     * @return the number of children
     */
    public int countChoices()
    {
        return (choices_ == null) ? 0 : choices_.size();
    }

    //-------------------------------------------------------------------------
    /**
     * Indexed accessor to choices.
     *
     * @param index index of choice to return
     * @return the reference to the choice at the index
     */
    public Association getChoice(int index)
    {
        if (choices_ == null) throw new IndexOutOfBoundsException();
        return choices_.get(index);
    }

    //-------------------------------------------------------------------------
    /**
     * Adds an association at the end of the list of choices.
     * Should be called only from an implementation of
     * {@link org.hl7.meta.XmlIts XmlIts}.
     *
     * @param association an association to be added
     */
    /*default*/
    void addChoice(Association association)
    {
        if (choices_ == null)
        {
            choices_ = new ArrayList<Association>();
        }
        choices_.add(association);
    }

    //-------------------------------------------------------------------------
    /**
     * Returns a string representation of the clone association metadata.
     *
     * @return a string representation
     */
    public String toString()
    {
        StringBuffer sb = new StringBuffer();

        sb.append(getName());
        sb.append('[');
        sb.append(countChoices());
        sb.append("] // ");
        sb.append(getCardinality());
        sb.append('\n');

        for (Iterator<Association> it = iterateChoices(); it.hasNext();)
        {
            Association a = it.next();

            sb.append("-- ");
            sb.append(a.toString());
            sb.append('\n');
        }

        return sb.toString();
    }

    protected ChoiceAssociationImpl clone(CloneClass clonedParent) throws CloneNotSupportedException
    {
        ChoiceAssociationImpl choiceAssociation = (ChoiceAssociationImpl) super.clone(clonedParent);
        choiceAssociation.choices_ = new ArrayList<Association>();

        final MessageType messageType = ((CloneClassImpl) clonedParent).getMessageType();
        if ((getMetSource() != MetSource.RECURSIVE) && (getMetSource() != MetSource.REUSED))
        {
            choiceAssociation.target_ = ((CloneClassImpl) this.target_).clone((MessageTypeImpl) messageType, true);
        }
        else
        {
            try
            {
                choiceAssociation.target_ = messageType.lookupCloneClass(choiceAssociation.getTarget().getName());
            }
            catch (LoaderException e)
            {
                e.printStackTrace(); 
            }
        }

        for (int i = 0; i < this.choices_.size(); i++)
        {
            Association association = this.choices_.get(i);
//            choiceAssociation.choices_.set(i, (Association) ((AssociationImpl) association).clone());
            choiceAssociation.choices_.add((Association) ((AssociationImpl) association).clone(clonedParent));
        }
        return choiceAssociation;
    }

}
