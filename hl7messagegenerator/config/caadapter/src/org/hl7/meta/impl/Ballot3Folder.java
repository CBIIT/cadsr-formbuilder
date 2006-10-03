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
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.hl7.meta.Association;
import org.hl7.meta.Attribute;
import org.hl7.meta.ChoiceAssociation;
import org.hl7.meta.CloneClass;
import org.hl7.meta.CloneClassAssociation;
import org.hl7.meta.CmetAssociation;
import org.hl7.meta.Feature;
import org.hl7.meta.LoaderException;
import org.hl7.meta.MetSource;
import org.hl7.meta.XmlItsException;

import org.hl7.util.FactoryException;

/**
 * Helper class for XmlItsBallot3Impl.  Implements generalization folding
 * algorithms.  Folds one message type only.
 *
 * @author Jerry Joyce
 */
public class Ballot3Folder
{
    //-------------------------------------------------------------------------
    protected static final Logger LOGGER = Logger.getLogger("org.hl7.meta");

    //-------------------------------------------------------------------------
    /**
     * Reference to the message type being folded. Folding functionality comes
     * via side effects to this message type.
     */
    private final MessageTypeImpl messageType_;

    //-------------------------------------------------------------------------
    /**
     * Constructs a new instance for the specified message type.
     *
     * @param messageType the message type
     */
    public Ballot3Folder(MessageTypeImpl messageType) throws FactoryException
    {
        messageType_ = messageType;
    }

    //-------------------------------------------------------------------------
    /**
     * If the current class contains any associations with non-null choiceSet,
     * traverses all associations of RIM type 'generalizes' and folds them
     * into a single class.  Only folds the current class, not classes linked
     * to it.
     *
     * @param cloneClass clone class to start from
     * @throws LoaderException if loading CMETs on demand fails
     * @throws XmlItsException if consistency checks fail
     */
    public void foldGeneralizations(CloneClassImpl cloneClass)
        throws LoaderException, XmlItsException
    {

        int childrenCount = cloneClass.countChildren();
        for (int i = 0; i < childrenCount; i++)
        {
            Feature feature = cloneClass.getChild(i);

            // There are no associations to abstract CMETs.
            if (feature instanceof CloneClassAssociation)
            {
                CloneClassAssociation cloneAssoc = (CloneClassAssociation) feature;
                CloneClass target = cloneAssoc.getTarget();

                if (target.isAbstract())
                {
                    Feature foldedAssoc = foldChoiceSet(cloneAssoc);
                    ((CloneClassImpl) cloneClass).replaceAssociation(i, foldedAssoc);
                }
            }
        }
    }

    //-------------------------------------------------------------------------
    /**
     * Folds the current class and all classes connected to it.
     *
     * @param clonesProcessed set of clone class names already processed;
     *                        needed to avoid infinite recursion
     * @param cloneClass      clone class to start from
     * @throws LoaderException if loading CMETs on demand fails
     * @throws XmlItsException if consistency checks fail
     */
    public void foldRecursively(Set/*<String>*/ clonesProcessed,
                                CloneClassImpl cloneClass) throws LoaderException, XmlItsException
    {
        if (clonesProcessed.contains(cloneClass.getName()))
        {
            return;
        }

        LOGGER.finest("Folding clone class:\n" + cloneClass.toString());

        clonesProcessed.add(cloneClass.getName());
        foldGeneralizations(cloneClass);

        for (Iterator/*<Feature>*/ it = cloneClass.iterateChildren();
             it.hasNext();)
        {
            Feature feature = (Feature) it.next();

            if (feature instanceof CloneClassAssociation)
            {

                CloneClassAssociation cloneAssoc = (CloneClassAssociation) feature;
                CloneClassImpl target = (CloneClassImpl) cloneAssoc.getTarget();

                foldRecursively(clonesProcessed, target);
            }
            else if (feature instanceof ChoiceAssociation)
            {
                ChoiceAssociation choiceAssociation = (ChoiceAssociation) feature;
                Iterator<Association> branchIter = choiceAssociation.iterateChoices();
                while (branchIter.hasNext())
                {
                    Association assoc = branchIter.next();
                    if (assoc instanceof CloneClassAssociation)
                    {
                        CloneClassImpl target = (CloneClassImpl) assoc.getTarget();
                        foldRecursively(clonesProcessed, target);
                    }
                }
            }
        }
    }

    //-------------------------------------------------------------------------
    /**
     * Folds a single choice set into an <code>Association</code> or
     * into <code>ChoiceAssociation</code>, depending on branching index.
     * More specifically, folds a non-branching sequence of 'generalizes'
     * associations into an <code>CloneClassAssociation</code> or
     * <code>CmetAssociation</code> depending on its last link; and folds
     * a branching tree into a <code>ChoiceAssociation</code>.
     *
     * @param cloneAssoc association to be folded
     * @return the folded equivalent
     * @throws LoaderException if dynamic CMET loading fails
     * @throws XmlItsException if consistency checks fail
     */
    protected Feature foldChoiceSet(CloneClassAssociation cloneAssoc)
        throws LoaderException, XmlItsException
    {
        LOGGER.finest("Folding choice set: " + cloneAssoc.toString());

        GeneralizesInfo generalizesInfo = foldsIntoChoice(cloneAssoc);

        LOGGER.finest("foldsIntoChoice() returned: " + generalizesInfo.toString());

        if (generalizesInfo.getFoldsIntoChoice())
        {
            return foldIntoChoiceAssociation(cloneAssoc,
                generalizesInfo.getGeneralizesIndices());
        }
        else
        {
            return foldIntoAssociation(cloneAssoc,
                generalizesInfo.getGeneralizesIndices()[0], true);
        }
    }

    //-------------------------------------------------------------------------
    /**
     * A simple data structure solely for holding the return value of method
     * <code>foldsIntoChoice()</code>.
     */
    protected static class GeneralizesInfo
    {
        /**
         * a flag
         */
        private final boolean foldsIntoChoice_;
        /**
         * list of indices
         */
        private final int[] generalizesIndices_;

        /**
         * Creates the data streucture.
         *
         * @param foldsIntoChoice    intial value
         * @param generalizesIndices intial value
         */
        protected GeneralizesInfo(boolean foldsIntoChoice,
                                  int[] generalizesIndices)
        {
            foldsIntoChoice_ = foldsIntoChoice;
            generalizesIndices_ = generalizesIndices;
        }

        /**
         * Returns the attribute <code>foldsIntoChoice_</code>.
         *
         * @return the attribute value
         */
        protected boolean getFoldsIntoChoice()
        {
            return foldsIntoChoice_;
        }

        /**
         * Returns the attribute <code>generalizesIndices__</code>.
         *
         * @return the attribute value
         */
        protected int[] getGeneralizesIndices()
        {
            return generalizesIndices_;
        }

        /**
         * Returns a string represebtation of the object.
         *
         * @return the string representation
         */
        public String toString()
        {
            StringBuffer sb = new StringBuffer();
            sb.append("GeneralizesInfo(foldsIntoChoice=");
            sb.append(foldsIntoChoice_);
            sb.append(", indices=");
            if (generalizesIndices_ == null)
            {
                sb.append("null");
            }
            else
            {
                sb.append("[");
                for (int i = 0; i < generalizesIndices_.length; ++i)
                {
                    if (i > 0) sb.append(",");
                    sb.append(generalizesIndices_[i]);
                }
                sb.append("]");
            }
            sb.append(")");
            return sb.toString();
        }
    }

    //-------------------------------------------------------------------------
    /**
     * Looks into the attached CloneClass to find whether the given
     * association folds into a simple association, or into a choice.
     *
     * @param cloneAssoc association to be folded
     * @return a <code>GeneralizesInfo</code> object
     * @throws LoaderException if dynamic CMET loading fails
     * @throws XmlItsException if consistency checks fail, e.g. no choices
     *                         were detected
     */
    protected GeneralizesInfo foldsIntoChoice(CloneClassAssociation cloneAssoc)
        throws LoaderException, XmlItsException
    {
        CloneClass target = cloneAssoc.getTarget();
        if (target == null)
        {
            throw new XmlItsException("Target clone class missing");
        }

        int[] generalizesIndices = findGeneralizesAssociations(target);
        if (generalizesIndices == null || generalizesIndices.length == 0)
        {
            throw new XmlItsException("No choices found");
        }
        return new GeneralizesInfo(generalizesIndices.length > 1,
            generalizesIndices);
    }

    //-------------------------------------------------------------------------
    /**
     * Finds all 'generalizes' associations originating in a clone class.
     * Returns an array of indices where those associations are located.
     * Returns <code>null</code> if no 'generalizes' associations were found.
     *
     * @param cloneClass the clone class to process
     * @return indices of 'generalizes' associations
     */
    protected int[] findGeneralizesAssociations(CloneClass cloneClass)
    {
        List/*<Integer>*/ indices = new ArrayList/*<Integer>*/();

        int i = 0;
        for (Iterator/*<Feature>*/ it = cloneClass.iterateChildren();
             it.hasNext(); ++i)
        {
            Feature feature = (Feature) it.next();

            if (feature instanceof Association)
            {
                String rimName = feature.getRimPropertyName();
                if (rimName != null && rimName.equals("generalizes"))
                {
                    indices.add(new Integer(i));
                }
            }
        }

        if (indices.isEmpty())
            return null;
        else
        {
            int[] result = new int[indices.size()];
            i = 0;
            for (Iterator/*<Integer>*/ it = indices.iterator(); it.hasNext(); ++i)
            {
                Integer index = (Integer) it.next();
                result[i] = index.intValue();
            }
            return result;
        }
    }

    //-------------------------------------------------------------------------
    /**
     * Finds and returns the next unique 'generalizes' association in the chain.
     *
     * @param association a 'generalizes' association starting the chain
     * @return next level 'generalizes' association
     * @throws LoaderException if dynamic CMET loading fails
     * @throws XmlItsException if more than one 'generalizes' association
     *                         was found on the next level
     */
    protected Association findGeneralizesAssociation(Association association)
        throws LoaderException, XmlItsException
    {
        CloneClass target = association.getTarget();
        if (target == null)
        {
            throw new XmlItsException("Target clone class missing");
        }

        Association result = null;
        for (Iterator/*<Feature>*/ it = target.iterateChildren();
             it.hasNext();)
        {
            Feature feature = (Feature) it.next();

            if (feature instanceof Association)
            {
                String rimName = feature.getRimPropertyName();
                if (rimName != null && rimName.equals("generalizes"))
                {
                    if (result == null)
                        result = (Association) feature;
                    else
                    {
                        throw new XmlItsException("Illegal generalization branching in: " +
                            messageType_.getId() + '.' + target.getName());
                    }
                }
            }
        }
        return result;
    }

    //-------------------------------------------------------------------------
    /**
     * Folds the given association into a clone or CMET association,
     * depending on the lat link in the 'generalizes' chain.
     *
     * @param cloneAssoc       association to be folded
     * @param generalizesIndex index where the only 'generalizes' association
     *                         is located
     * @param complexName      a flag indicating if the result's name should combine
     *                         the choice prefix and distal class name
     * @return the folded equivalent association
     * @throws LoaderException if dynamic CMET loading fails
     * @throws XmlItsException if consistency checks failed
     */
    protected Association foldIntoAssociation(CloneClassAssociation cloneAssoc,
                                              int generalizesIndex, boolean complexName)
        throws LoaderException, XmlItsException
    {
        CloneClassImpl target = (CloneClassImpl) cloneAssoc.getTarget();
        if (target == null)
        {
            throw new XmlItsException("Target clone class missing");
        }

        Feature feature = (Feature) target.getChild(generalizesIndex);
        if (!(feature instanceof Association))
        {
            throw new XmlItsException("Not an association: " +
                messageType_.getId() + '.' + target.getName() + '.' +
                feature.getName());
        }
        Association generalizesAssociation = (Association) feature;

        LOGGER.finest("foldIntoAssociation(): target=" + target.getName() +
            ", assocation=" + generalizesAssociation);

        LeafInfo leafInfo = getLeafInfo(generalizesAssociation);
        if (leafInfo.isCmet())
        {
            LOGGER.finest("folding into CMET association");

            return foldIntoCmetAssociation(cloneAssoc, generalizesAssociation,
                leafInfo.getCmetId(), leafInfo.getCmetCache(), complexName);
        }
        else
        {
            LOGGER.finest("folding into clone association");

            return foldIntoCloneAssociation(cloneAssoc, generalizesAssociation,
                leafInfo.getLeafName(), leafInfo.getLeafRimClass(), complexName);
        }
    }

    //-------------------------------------------------------------------------
    /**
     * A simple data structure solely for holding the return value of method
     * <code>getLeafInfo()</code>.
     */
    protected static class LeafInfo
    {
        /**
         * Name of the last clone class in 'generalizes' chain
         */
        private final String leafName_;
        /**
         * RIM class of the last clone class in 'generalizes' chain
         */
        private final String leafRimClass_;
        /**
         * CMET id if the last link is CmetAssociation
         */
        private final String cmetId_;
        /**
         * list of indices
         */
        private final CmetCacheImpl cmetCache_;

        /**
         * Creates the data streucture.
         *
         * @param leafName     intial value
         * @param leafRimClass intial value
         * @param cmetId       intial value
         * @param cmetCache    intial value
         */
        protected LeafInfo(String leafName, String leafRimClass, String cmetId,
                           CmetCacheImpl cmetCache)
        {
            leafName_ = leafName;
            leafRimClass_ = leafRimClass;
            cmetId_ = cmetId;
            cmetCache_ = cmetCache;
        }

        /**
         * Returns the attribute <code>leafName_</code>.
         *
         * @return the attribute value
         */
        protected String getLeafName()
        {
            return leafName_;
        }

        /**
         * Returns the attribute <code>leafRimClass_</code>.
         *
         * @return the attribute value
         */
        protected String getLeafRimClass()
        {
            return leafRimClass_;
        }

        /**
         * Returns the attribute <code>cmetId__</code>.
         *
         * @return the attribute value
         */
        protected String getCmetId()
        {
            return cmetId_;
        }

        /**
         * Returns true if the last association in the chain points to a CMET.
         *
         * @return the attribute value
         */
        protected boolean isCmet()
        {
            return cmetId_ != null;
        }

        /**
         * Returns the attribute <code>cmetCache_</code>.
         *
         * @return the attribute value
         */
        protected CmetCacheImpl getCmetCache()
        {
            return cmetCache_;
        }
    }

    //-------------------------------------------------------------------------
    /**
     * Traverses the 'generalizes' association chain to determine if it should
     * fold into a <code>CmetAssociation</code> or
     * <code>CloneClassAssociation</code>.  The criterion is the type of the
     * last 'generalizes' association in the chain.
     * <br/>
     * Also validates that 'generalizes' chain has no branches within it
     * (since branching is allowed only at the first level of 'generalizes'.
     *
     * @param association association to be traversed
     * @return a <code>LeafInfo</code> structure with information about
     *         the last node in 'generalizes' chain
     * @throws LoaderException if dynamic CMET loading fails
     * @throws XmlItsException if consistency checks fail, e.g. no choices
     *                         were detected
     */
    protected LeafInfo getLeafInfo(Association association)
        throws LoaderException, XmlItsException
    {
        if (association instanceof CmetAssociation)
        {
            CmetAssociationImpl cmetAssociation = (CmetAssociationImpl) association;
            return new LeafInfo(null, null, cmetAssociation.getCmetId(),
                cmetAssociation.getCmetCache());
        }

        while (true)
        {
            Association childAssociation = findGeneralizesAssociation(association);
            if (childAssociation == null)
            {
                String leafName = association.getTarget().getName();
                String leafRimClass = association.getTarget().getRimClass();
                return new LeafInfo(leafName, leafRimClass, null, null);
            }
            else if (childAssociation instanceof CmetAssociation)
            {
                CmetAssociationImpl cmetAssociation =
                    (CmetAssociationImpl) childAssociation;
                return new LeafInfo(null, null, cmetAssociation.getCmetId(),
                    cmetAssociation.getCmetCache());
            }
            else
            {
                association = childAssociation;
            }
        }
    }

    //-------------------------------------------------------------------------
    /**
     * Folds the given association into a CMET association.
     *
     * @param cloneAssoc             association to be folded
     * @param generalizesAssociation 'generalizes' association to be used
     * @param cmetId                 CMET ID for the newly created association
     * @param cmetCache              CMET cache for the newly created association
     * @param complexName            a flag indicating if the result's name should combine
     *                               the association name and distal class name
     * @return the folded equivalent association
     * @throws LoaderException if dynamic CMET loading fails
     * @throws XmlItsException if consistency checks fail
     */
    protected CmetAssociation foldIntoCmetAssociation(CloneClassAssociation cloneAssoc, Association generalizesAssociation,
                                                      String cmetId, CmetCacheImpl cmetCache, boolean complexName)
        throws LoaderException, XmlItsException
    {
        CmetAssociationImpl result = new CmetAssociationImpl(cloneAssoc.getParent(), cmetCache);

        String name = cloneAssoc.getName();
        if (name != null)
        {
            int idxUnderscore = name.indexOf("_");
            if (idxUnderscore != -1) name = name.substring(0, idxUnderscore);
        }

        result.setCardinality(cloneAssoc.getCardinality());
        result.setRimClass(cloneAssoc.getRimClass());
        result.setRimPropertyName(cloneAssoc.getRimPropertyName());
        result.setMandatory(cloneAssoc.isMandatory());
        result.setConformance(cloneAssoc.getConformance());
        result.setConstraint(cloneAssoc.getConstraint());
        result.setMetSource(MetSource.CMET);
        result.setCmetId(cmetId);

        //    if (complexName) name += cmetId;
        if (complexName)
        {
            String cloneName = result.getTarget().getName();
            String prefix = cloneAssoc.getPrefix();
            name = prefix + cloneName;
        }
        result.setName(name);

        foldClass(cloneAssoc.getTarget(), generalizesAssociation);

        return result;
    }

    //-------------------------------------------------------------------------
    /**
     * Validates that there are only 'generalizes' associations in
     * <code>clone</code>, then removes <code>clone</code> from the
     * message type.
     *
     * @param clone clone class that is source under folding
     * @throws LoaderException if dynamic CMET loading fails
     * @throws XmlItsException if consistency checks fail
     */
    protected void foldClass(CloneClassImpl clone)
        throws LoaderException, XmlItsException
    {
//        int childrenCount = clone.countChildren();
        Feature feature = clone.getChild(0);

//        if (childrenCount != 1)
//        {
            Iterator<Feature> iterator = clone.iterateChildren();
            int childrenAssociationAccount = 0;
            while (iterator.hasNext())
            {
                Feature f = iterator.next();

                if (f instanceof Association)
                {
                    childrenAssociationAccount++;
                    feature = f;
                }
            }
            if (childrenAssociationAccount != 1)
            {
                throw new XmlItsException("Clone class must have only a 'generalizes' " +
                    "association: " + messageType_.getId() + '.' + clone.getName());
            }

//        }


        if (feature instanceof CmetAssociation)
        {
            String rimName = feature.getRimPropertyName();
            if (rimName == null || !rimName.equals("generalizes"))
            {
                throw new XmlItsException("Clone class must have only a " +
                    "'generalizes' association: " + messageType_.getId() + '.' +
                    clone.getName());
            }

            messageType_.removeCloneClass(clone);
            return;
        }
        else if (feature instanceof CloneClassAssociation)
        {
            String rimName = feature.getRimPropertyName();
            if (rimName == null || !rimName.equals("generalizes"))
            {
                throw new XmlItsException("Clone class must have only a " +
                    "'generalizes' association: " + messageType_.getId() + '.' +
                    clone.getName());
            }

            messageType_.removeCloneClass(clone);

            CloneClassAssociation association = (CloneClassAssociation) feature;
            CloneClassImpl target = (CloneClassImpl) association.getTarget();
            if (target == null)
            {
                throw new XmlItsException("Target clone class missing");
            }

            // Recurse.
            foldClass(target);
        }
        else
        {
            throw new XmlItsException("Clone class must have only a 'generalizes' " +
                "association: " + messageType_.getId() + '.' + clone.getName());
        }
    }

    //-------------------------------------------------------------------------
    /**
     * Validates that there are only 'generalizes' associations in
     * <code>clone</code>, then removes <code>clone</code> from the
     * message type.
     *
     * @param clone                  clone class that is source under folding
     * @param generalizesAssociation 'generalizes' association to be used
     * @throws LoaderException if dynamic CMET loading fails
     * @throws XmlItsException if consistency checks fail
     */
    protected void foldClass(CloneClass clone,
                             Association generalizesAssociation)
        throws LoaderException, XmlItsException
    {
        for (Iterator/*<Feature>*/ it = clone.iterateChildren();
             it.hasNext();)
        {
            Feature feature = (Feature) it.next();
            if (feature instanceof Attribute)
            {
                Attribute attribute = (Attribute) feature;
                if (!"InfrastructureRoot".equals(attribute.getRimClass())
                    && !"RimObject".equals(attribute.getRimClass())
                )   // TODO: A work around because of CloneClassImpl Constructure kludge
                    throw new XmlItsException("Choice class must have no attributes: " +
                        messageType_.getId() + '.' + clone.getName());
            }
            else if (feature instanceof Association)
            {
                String rimName = feature.getRimPropertyName();
                if (rimName == null || !rimName.equals("generalizes"))
                {
                    throw new XmlItsException("Choice class must have only generalizes" +
                        "associations: " + messageType_.getId() + '.' + clone.getName());
                }
            }
            else
            {
                throw new XmlItsException("Unknown feature type: " +
                    feature.getClass().getName());
            }
        }

        if (generalizesAssociation instanceof CmetAssociation)
        {
            String rimName = generalizesAssociation.getRimPropertyName();
            if (rimName == null || !rimName.equals("generalizes"))
            {
                throw new XmlItsException("Clone class must have only a " +
                    "'generalizes' association: " + messageType_.getId() + '.' +
                    clone.getName());
            }

            messageType_.removeCloneClass(clone);
            return;
        }
        else if (generalizesAssociation instanceof CloneClassAssociation)
        {
            String rimName = generalizesAssociation.getRimPropertyName();
            if (rimName == null && !rimName.equals("generalizes"))
            {
                throw new XmlItsException("Clone class must have only a " +
                    "'generalizes' association: " + messageType_.getId() + '.' +
                    clone.getName());
            }

            messageType_.removeCloneClass(clone);

            CloneClassAssociation association =
                (CloneClassAssociation) generalizesAssociation;
            CloneClassImpl target = (CloneClassImpl) association.getTarget();
            if (target == null)
            {
                throw new XmlItsException("Target clone class missing");
            }

            // Recurse.
            foldClass(target);
        }
        else
        {
            throw new XmlItsException("Clone class must have only a 'generalizes' " +
                "association: " + messageType_.getId() + '.' + clone.getName());
        }
    }

    //-------------------------------------------------------------------------
    /**
     * Folds the given association into a clone class association.
     *
     * @param cloneAssoc             association to be folded
     * @param generalizesAssociation 'generalizes' association to be used
     * @param cloneName              name for the newly created clone class
     * @param rimClass               RIM class for the newly created clone class
     * @param complexName            a flag indicating if the result's name should combine
     *                               the association name and distal class name
     * @return the folded equivalent association
     * @throws LoaderException if dynamic CMET loading fails
     * @throws XmlItsException if consistency checks fail
     */
    protected CloneClassAssociation foldIntoCloneAssociation(CloneClassAssociation cloneAssoc, Association generalizesAssociation,
                                                             String cloneName, String rimClass,
                                                             boolean complexName)
        throws LoaderException, XmlItsException
    {
        CloneClassAssociationImpl result = new CloneClassAssociationImpl(cloneAssoc.getParent());

        String name = cloneAssoc.getName();
        if (name != null)
        {
            int idxUnderscore = name.indexOf("_");
            if (idxUnderscore != -1) name = name.substring(0, idxUnderscore);
        }
//    if (complexName) name += cloneName;
        if (complexName)
        {
            String prefix = cloneAssoc.getPrefix();
            if ("".equals(prefix))   // TODO: name kludge if prefix is "", then first charcter should be lower case
                cloneName = Character.toLowerCase(cloneName.charAt(0)) + cloneName.substring(1);
            name = prefix + cloneName;
        }

        result.setName(name);
        result.setCardinality(cloneAssoc.getCardinality());
        result.setRimClass(cloneAssoc.getRimClass());
        result.setRimPropertyName(cloneAssoc.getRimPropertyName());
        result.setMandatory(cloneAssoc.isMandatory());
        result.setConformance(cloneAssoc.getConformance());
        result.setConstraint(cloneAssoc.getConstraint());
        result.setMetSource(cloneAssoc.getMetSource());

        CloneClassImpl foldedClone = new CloneClassImpl(messageType_);
        foldedClone.setName(cloneName);
        foldedClone.setFullName(cloneAssoc.getParent().getFullName() + "." + name);
        foldedClone.setRimClass(rimClass);
        result.setTarget(foldedClone);
        messageType_.addCloneClass(foldedClone);

        CloneClass clone = cloneAssoc.getTarget();
        if (clone == null)
        {
            throw new XmlItsException("Target clone class missing");
        }

        foldClass(foldedClone, clone, generalizesAssociation);

        return result;
    }

    //-------------------------------------------------------------------------
    /**
     * Copies all attributes and associations from <code>clone</code> to
     * <code>foldedClone</code>, then removes <code>clone</code> from the
     * message type.
     *
     * @param foldedClone clone class that is target under folding
     * @param clone       clone class that is source under folding
     * @throws LoaderException if dynamic CMET loading fails
     * @throws XmlItsException if consistency checks fail
     */
    protected void foldClass(CloneClassImpl foldedClone, CloneClass clone)
        throws LoaderException, XmlItsException
    {
        Association generalizes = null;
        for (Iterator/*<Feature>*/ it = clone.iterateChildren();
             it.hasNext();)
        {
            Feature feature = (Feature) it.next();

            if (feature instanceof Attribute)
            {
                Attribute attribute = (Attribute) feature;
                if (!"InfrastructureRoot".equals(attribute.getRimClass())
                    && !"RimObject".equals(attribute.getRimClass())
                )                  // "templateId", "realmCode", "typeID", "type" is initiated in CloneImpl Constructor

                    foldedClone.addAttribute((Attribute) feature);
            }
            else if (feature instanceof Association)
            {
                String rimName = feature.getRimPropertyName();

                if (rimName != null && rimName.equals("generalizes"))
                {
                    generalizes = (Association) feature;
                }
                else
                {
                    foldedClone.addAssociation((Association) feature);
                }
            }
            else
            {
                throw new XmlItsException("Unknown feature type: " +
                    feature.getClass().getName());
            }
        }

        messageType_.removeCloneClass(clone);
        if (generalizes == null) return;

        CloneClass target = generalizes.getTarget();
        if (target == null)
        {
            throw new XmlItsException("Target clone class missing");
        }

        // Recurse.
        foldClass(foldedClone, target);
    }

    //-------------------------------------------------------------------------
    /**
     * Copies all attributes and associations from <code>clone</code> to
     * <code>foldedClone</code>, then removes <code>clone</code> from the
     * message type.
     *
     * @param foldedClone            clone class that is target under folding
     * @param clone                  clone class that is source under folding
     * @param generalizesAssociation 'generalizes' association to be used
     * @throws LoaderException if dynamic CMET loading fails
     * @throws XmlItsException if consistency checks fail
     */
    protected void foldClass(CloneClassImpl foldedClone, CloneClass clone,
                             Association generalizesAssociation)
        throws LoaderException, XmlItsException
    {
        for (Iterator/*<Feature>*/ it = clone.iterateChildren(); it.hasNext();)
        {
            Feature feature = (Feature) it.next();

            if (feature instanceof Attribute)
            {
                Attribute attribute = (Attribute) feature;
                if (!"InfrastructureRoot".equals(attribute.getRimClass())
                    && !"RimObject".equals(attribute.getRimClass())
                )                  // "templateId", "realmCode", "typeID", "type" is initiated in CloneImpl Constructor
                    foldedClone.addAttribute(attribute);
            }
            else if (feature instanceof Association)
            {
                String rimName = feature.getRimPropertyName();
                if (rimName == null || !rimName.equals("generalizes"))
                {
                    foldedClone.addAssociation((Association) feature);
                }
            }
            else
            {
                throw new XmlItsException("Unknown feature type: " +
                    feature.getClass().getName());
            }
        }

        messageType_.removeCloneClass(clone);
        if (generalizesAssociation == null) return;

        CloneClass target = generalizesAssociation.getTarget();
        if (target == null)
        {
            throw new XmlItsException("Target clone class missing");
        }

        // Recurse.
        foldClass(foldedClone, target);
    }

    //-------------------------------------------------------------------------
    /**
     * Folds the given association into a choice association.
     *
     * @param cloneAssoc         association to be folded
     * @param generalizesIndices list of indices where the 'generalizes'
     *                           associations are located
     * @return the folded equivalent association
     * @throws LoaderException if dynamic CMET loading fails
     * @throws XmlItsException if consistency checks fail
     */
    protected ChoiceAssociation foldIntoChoiceAssociation(CloneClassAssociation cloneAssoc, int[] generalizesIndices)
        throws LoaderException, XmlItsException
    {
        ChoiceAssociationImpl result = new ChoiceAssociationImpl(cloneAssoc.getParent());

        String name = cloneAssoc.getName();
        if (name != null)
        {
            int idxUnderscore = name.indexOf("_");
            if (idxUnderscore != -1) name = name.substring(0, idxUnderscore);
        }

        LOGGER.finest("choice association name: " + name.toString());

        result.setName(name);
        result.setCardinality(cloneAssoc.getCardinality());
        result.setRimClass(cloneAssoc.getRimClass());
        result.setRimPropertyName(cloneAssoc.getRimPropertyName());
        result.setMandatory(cloneAssoc.isMandatory());
        result.setConformance(cloneAssoc.getConformance());
        result.setConstraint(cloneAssoc.getConstraint());
        result.setTarget(cloneAssoc.getTarget());
        result.setMetSource(cloneAssoc.getMetSource());

        for (int i = 0; i < generalizesIndices.length; ++i)
        {
            Association foldedAssociation = foldIntoAssociation(cloneAssoc,
                generalizesIndices[i], true);

            result.addChoice(foldedAssociation);
        }

        return result;
    }
}
