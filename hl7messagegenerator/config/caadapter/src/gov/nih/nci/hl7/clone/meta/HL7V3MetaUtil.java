/**
 * <!-- LICENSE_TEXT_START -->
 * $Header: /share/content/gforge/formbuilder/hl7messagegenerator/config/caadapter/src/gov/nih/nci/hl7/clone/meta/HL7V3MetaUtil.java,v 1.1 2006-10-03 17:38:25 marwahah Exp $
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

import gov.nih.nci.hl7.clone.meta.impl.CloneAttributeMultipleMetaImpl;
import gov.nih.nci.hl7.clone.meta.impl.CloneMultipleMetaImpl;
import gov.nih.nci.hl7.common.MetaException;
import gov.nih.nci.hl7.common.MetaObject;
import gov.nih.nci.hl7.util.GeneralUtilities;
import gov.nih.nci.hl7.util.Log;
import org.hl7.meta.*;
import org.hl7.meta.impl.DatatypeMetadataFactoryImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * HL7 v3 Meta related utility to CRUD HL7 v3 specification objects
 *
 * @author OWNER: Eric Chen  Date: Aug 17, 2005
 * @author LAST UPDATE: $Author: marwahah $
 * @version $Revision: 1.1 $
 * @since caAdapter v1.2
 */


public class HL7V3MetaUtil
{
    /**
     * Add child clone metas to the current clone meta based on the list of association. Associatin's
     * parent CloneClass is the same level as CloneMeta
     *
     * @param associations
     * @param cloneMeta
     * @throws MetaException
     */
    public static void addClone(List<Association> associations, CloneMeta cloneMeta) throws MetaException
    {
        if (cloneMeta == null)
        {
            throw new MetaException("CloneMeta Object is null", new IllegalArgumentException("CloneMeta Object is null"));
        }

        HL7V3MetaObjectParser objectParser = new HL7V3MetaObjectParser();
        if (associations == null)
        {
            throw new MetaException("Association list is null",
                new IllegalArgumentException("Association list is null"));
        }

        for (int i = 0; i < associations.size(); i++)
        {
            Association association = associations.get(i);
            final CloneMeta childCloneMeta = objectParser.processAssociation(association, null);
            cloneMeta.addClone(childCloneMeta);
        }
    }

    /**
     * Remove child Clone Metas related to the associations
     *
     * @param associations
     * @param cloneMeta
     * @throws MetaException
     */
    public static void removeClone(List<Association> associations, CloneMeta cloneMeta) throws MetaException
    {
        if (cloneMeta == null)
        {
            throw new MetaException("CloneMeta Object is null", new IllegalArgumentException("CloneMeta Object is null"));
        }

        if (associations == null)
        {
            throw new MetaException("Association list is null",
                new IllegalArgumentException("Association list is null"));
        }

        for (int i = 0; i < associations.size(); i++)
        {
            final List<CloneMeta> childClones = cloneMeta.getChildClones();
            List<CloneMeta> needToRemovedClones = new ArrayList<CloneMeta>();

            for (int j = 0; j < childClones.size(); j++)
            {

                CloneMeta meta = childClones.get(j);
                if (meta.getName().equals(associations.get(i).getName()))
                {
                    needToRemovedClones.add(meta);
                }
            }
            childClones.removeAll(needToRemovedClones);
        }
    }

    /**
     * Add child clone meta related to the specialized association to the current clone meta's parent and
     * remove the current cloneMeta.
     *
     * @param association user selected from ChoiceClone's choices
     * @param cloneMeta   is a choice clone meta.
     * @throws MetaException
     */
    public static void selectChoiceClone(Association association, CloneChoiceMeta cloneMeta) throws MetaException
    {
        if (cloneMeta == null || GeneralUtilities.isBlank(cloneMeta.getChoiceGroupName()))
        {
            throw new MetaException("CloneMeta Object is null or is not a choice clone",
                new IllegalArgumentException("CloneMeta Object is null or is not a choice clone"));
        }


        if (association == null)
        {
            throw new MetaException("Association is null",
                new IllegalArgumentException("Association is null"));
        }

        HL7V3MetaObjectParser objectParser = new HL7V3MetaObjectParser();
        final CloneMeta parentMeta = cloneMeta.getParentMeta();

        ChoiceAssociation choiceAssociation = null;
        choiceAssociation = findChoiceAssociation(association.getParent(), cloneMeta);

        final CloneMeta childCloneMeta = objectParser.processAssociation(association, choiceAssociation);

        parentMeta.addClone(childCloneMeta);
        parentMeta.getChildClones().remove(cloneMeta);
    }

    /**
     * Add multiple clone
     * @param multipleMeta
     * @throws MetaException
     */
    public static void addMultipleClone(CloneMultipleMeta multipleMeta) throws MetaException
    {
        CloneMeta parentMeta = multipleMeta.getParentMeta();
        List<CloneMultipleMeta> cloneMultipleMetas = multipleMeta.getCloneMultipleMetaByName();
        try
        {
            CloneMultipleMeta newCloneMultipleMeta = (CloneMultipleMeta)((CloneMultipleMetaImpl) multipleMeta).clone(true, false);
            newCloneMultipleMeta.setMultipleSequenceNumber(cloneMultipleMetas.size() + 1);
            parentMeta.addClone(newCloneMultipleMeta);
            Collections.sort(parentMeta.getChildClones(), new CloneMetaComparator());
        }
        catch (CloneNotSupportedException e)
        {
            throw new MetaException("CloneMultipleMeta "+ multipleMeta.getName() + " cannot be cloned", e);
        }
    }

    /**
     * Remove multiple clone
     * @param multipleMeta
     */
    public static void removeMultipleClone(CloneMultipleMeta multipleMeta)
    {
        CloneMeta parentMeta = multipleMeta.getParentMeta();
        List<CloneMultipleMeta> cloneMultipleMetas = multipleMeta.getCloneMultipleMetaByName();
        if (cloneMultipleMetas.size() <= 1)
        {
            return;
        }
        else
        {
            parentMeta.getChildClones().remove(cloneMultipleMetas.get(cloneMultipleMetas.size() - 1));
        }
    }

    /**
     * Add Multiple Attribute
     * @param multipleMeta
     * @throws MetaException
     */
    public static void addMultipleAttribute(CloneAttributeMultipleMeta multipleMeta) throws MetaException
    {
        final MetaObject parentMeta = multipleMeta.getParentMeta();
        final List<CloneAttributeMultipleMeta> multipleMetas = multipleMeta.getAttributeMultipleMetaByName();
        try
        {
            CloneAttributeMultipleMeta newMultipleMeta = (CloneAttributeMultipleMeta)((CloneAttributeMultipleMetaImpl)multipleMeta).clone(true, false);
            newMultipleMeta.setMultipleSequenceNumber(multipleMetas.size() + 1);
            if (parentMeta instanceof CloneMeta)
            {
                CloneMeta cloneMeta = (CloneMeta) parentMeta;
                cloneMeta.addAttribute(newMultipleMeta);
                Collections.sort(cloneMeta.getAttributes(), new CloneAttributeMetaComparator());
            }
            else if (parentMeta instanceof CloneAttributeMeta)
            {
                CloneAttributeMeta cloneAttributeMeta = (CloneAttributeMeta) parentMeta;
                cloneAttributeMeta.addAttribute(newMultipleMeta);
                Collections.sort(cloneAttributeMeta.getChildAttributes(), new CloneAttributeMetaComparator());
            }
        }
        catch (CloneNotSupportedException e)
        {
            throw new MetaException("CloneAttributeMultipleMeta "+ multipleMeta.getName() + " cannot be cloned", e);
        }
    }

    /**
     * Remove the multiple data type
     * @param multipleMeta
     * @throws MetaException
     */
    public static void removeMultipleAttribute(CloneAttributeMultipleMeta multipleMeta) throws MetaException
    {
        final MetaObject parentMeta = multipleMeta.getParentMeta();
        final List<CloneAttributeMultipleMeta> multipleMetas = multipleMeta.getAttributeMultipleMetaByName();
        if (multipleMetas.size() <= 1)
        {
            return;
        }
        else
        {
            if (parentMeta instanceof CloneMeta)
            {
                CloneMeta cloneMeta = (CloneMeta) parentMeta;
                cloneMeta.getAttributes().remove(multipleMetas.get(multipleMetas.size() - 1));
            }
            else if (parentMeta instanceof CloneAttributeMeta)
            {
                CloneAttributeMeta cloneAttributeMeta = (CloneAttributeMeta) parentMeta;
                cloneAttributeMeta.getChildAttributes().remove(multipleMetas.get(multipleMetas.size() - 1));
            }
            else
            {
                throw new MetaException("How can I get here? There are two types of CloneAttributeMeta: CloneMeta and CloneAttributeMeta",
                    new IllegalArgumentException());
            }
        }
    }


    /**
     * Depending on the subtype, repopulate CloneAttributeMeta for Abstract Datatype ANY, QTY
     *
     * @param cloneAttributeMeta
     */
    public static void loadAbstractDatatype(CloneAttributeMeta cloneAttributeMeta) throws MetaException
    {
        if (cloneAttributeMeta == null)
        {
            throw new MetaException("CloneAttributeMeta Object is null", new IllegalArgumentException("CloneAttributeMeta Object is null"));
        }

        if (!cloneAttributeMeta.isAbstract())
        {//if not abstract type, do nothing.
            return;
        }

        final String subClass = cloneAttributeMeta.getSubClass();
        if (GeneralUtilities.isBlank(subClass))
        {
            throw new MetaException("Sub Class(type) is blank", new IllegalArgumentException(cloneAttributeMeta.toString()));
        }

        try
        {
            final Datatype datatype = DatatypeMetadataFactoryImpl.instance().create(subClass);
            cloneAttributeMeta.getChildAttributes().clear();
            cloneAttributeMeta.getDatatypeFields().clear();
            // preserve the old datatype
            final String old_datatype = cloneAttributeMeta.getDatatype();
            HL7V3MetaObjectParser.loadDatatype(cloneAttributeMeta, datatype);
            cloneAttributeMeta.setDatatype(old_datatype);
        }
        catch (UnknownDatatypeException e)
        {
            throw new MetaException("Connot create the HL7 v3 Data type, unknow Data type:" + subClass, e);
        }

    }

    /**
     * Get the specialization child associations by giving the clone meta. The CloneMeta is Choice Clone Meta
     *
     * @param messageType
     * @param choiceCloneMeta
     * @return
     */
    public static List<Association> getSpecializationChildAssociations(MessageType messageType, CloneChoiceMeta choiceCloneMeta)
        throws MetaException
    {

        List<Association> associations = new ArrayList<Association>();

        final ChoiceAssociation choiceAssociation = findChoiceAssociation(choiceCloneMeta, messageType);

        if (choiceAssociation == null)
        {
            return associations;
        }

        for (Iterator<Association> itChoices = choiceAssociation.iterateChoices();
             itChoices.hasNext();)
        {
            associations.add(itChoices.next());
        }

        return associations;
    }

    /**
     * Find the choice association by giving the clone meta in the context of the particular message type.
     *
     * @param choiceCloneMeta   is a choice Clone Meta which contains choice group name
     * @param messageType
     * @return
     * @throws MetaException
     */
    public static ChoiceAssociation findChoiceAssociation(CloneChoiceMeta choiceCloneMeta, MessageType messageType)
        throws MetaException
    {

        if (choiceCloneMeta == null)
        {
            throw new MetaException("Clone Meta " + choiceCloneMeta + " is not choice meta",
                new IllegalArgumentException("Clone Meta is not choice meta"));
        }

        final CloneMeta parentMeta = choiceCloneMeta.getParentMeta();
        final CloneClass parentCloneClass = HL7V3MetaUtil.findCloneClass(messageType, parentMeta);
        return findChoiceAssociation(parentCloneClass, choiceCloneMeta);
    }

    /**
     * Find the choice association by giving parent clone class.
     *
     * @param parentCloneClass is the parent clone class of the choice clone meta represented clone class
     * @param choiceCloneMeta  is a choice clone meta
     * @return
     */
    private static ChoiceAssociation findChoiceAssociation(CloneClass parentCloneClass, CloneChoiceMeta choiceCloneMeta)
    {
        if (parentCloneClass == null)
        {
            Log.logWarning(choiceCloneMeta, "Parent Clone Class can not be founded for the Clone Meta" + choiceCloneMeta);
            return null;
        }
        else
        {
            for (Iterator it = parentCloneClass.iterateChildren(); it.hasNext();)
            {
                Feature feature = (Feature) it.next();
                if (feature instanceof ChoiceAssociation)
                {
                    ChoiceAssociation choiceAssociation = (ChoiceAssociation) feature;
                    if (choiceAssociation.getName().equals(choiceCloneMeta.getChoiceGroupName()))
                        return choiceAssociation;
                }
            }
        }
        return null;
    }

    /**
     * Get the clone class removable associations by giving the clone meta. If the association is choice type,
     * return the choice association, not the specialized child ssociation of the choice association
     *
     * @param cloneClass
     * @param cloneMeta
     * @return
     */
    public static List<Association> getRemovableAssociations(CloneClass cloneClass, CloneMeta cloneMeta)
    {

        List<Association> associations = new ArrayList<Association>();
        for (Iterator it = cloneClass.iterateChildren(); it.hasNext();)
        {
            Feature feature = (Feature) it.next();

            if ((feature instanceof Association) && (feature.getCardinality().getMin() < 1))
            {

                Association association = (Association) feature;
                if (association instanceof ChoiceAssociation)
                {
                    ChoiceAssociation choiceAssociation = (ChoiceAssociation) association;

                    // check if clone meta has child clone meta related to the choice group name(init state)
                    // or any association under this group
                    boolean flag = false;
                    if (cloneMeta.getChildCloneByName(choiceAssociation.getName()) == null)
                    {
                        for (Iterator<Association> itChoices = choiceAssociation.iterateChoices();
                             itChoices.hasNext();)
                        {
                            Association ass = itChoices.next();
                            if (cloneMeta.getChildCloneByName(ass.getName()) != null)
                            {
                                flag = true;
                                break;
                            }
                        }
                    }
                    else
                    {
                        flag = true;
                    }

                    if (flag)
                        associations.add(association);
                }
                else
                {
                    if (cloneMeta.getChildCloneByName(association.getName()) != null)
                    {
                        associations.add(association);
                    }
                }
            }
        }
        return associations;
    }

    /**
     * Get the clone class addable associations by giving the clone meta. If the association is choice type,
     * return the choice association, not the specialized child ssociation of the choice association
     *
     * @param cloneClass
     * @param cloneMeta
     * @return
     */
    public static List<Association> getAddableAssociations(CloneClass cloneClass, CloneMeta cloneMeta)
    {

        List<Association> associations = new ArrayList<Association>();
        for (Iterator it = cloneClass.iterateChildren(); it.hasNext();)
        {
            Feature feature = (Feature) it.next();

            if (feature instanceof Association)
            {

                Association association = (Association) feature;
                if (association instanceof ChoiceAssociation)
                {
                    ChoiceAssociation choiceAssociation = (ChoiceAssociation) association;

                    // check if clone meta has child clone meta related to the choice group name(init state)
                    // or any association under this group
                    boolean flag = true;
                    if (cloneMeta.getChildCloneByName(choiceAssociation.getName()) == null)
                    {
                        for (Iterator<Association> itChoices = choiceAssociation.iterateChoices();
                             itChoices.hasNext();)
                        {
                            Association ass = itChoices.next();
                            if (cloneMeta.getChildCloneByName(ass.getName()) != null)
                            {
                                flag = false;
                                break;
                            }
                        }
                    }
                    else
                    {
                        flag = false;
                    }

                    if (flag)
                        associations.add(choiceAssociation );
                }
                else
                {
                    if (cloneMeta.getChildCloneByName(association.getName()) == null)
                    {
                        associations.add(association);
                    }
                }
            }
        }
        return associations;
    }


    /**
     * Search the clone class equivalent to clone meta using path algorithm method
     *
     * @param messageType
     * @param cloneMeta
     * @return a CloneClass equivalent to CloneMeta at the Messagetype CloneClass tree
     * @throws MetaException
     */
    public static CloneClass findCloneClass(MessageType messageType, CloneMeta cloneMeta)
        throws MetaException
    {
        CloneClass rootClone = messageType.getRootClass();

        List<String> clonePath = new ArrayList<String>();
        clonePath.add(cloneMeta.getName());

        CloneMeta childMeta = cloneMeta;
        CloneMeta parentMeta = childMeta.getParentMeta();

        while (parentMeta != null)
        {
            clonePath.add(parentMeta.getName());
            childMeta = parentMeta;
            parentMeta = childMeta.getParentMeta();
        }

        Collections.reverse(clonePath);

        CloneClass cc = rootClone;

        if (clonePath.size() > 1)
        {
            cc = findCloneByPath(rootClone, clonePath, 1);
        }
        return cc;
    }

    /**
     * Match the clone class by path
     * @param cloneClass
     * @param path
     * @param index
     * @return
     * @throws MetaException
     */
    private static CloneClass findCloneByPath(CloneClass cloneClass, List<String> path, int index) throws MetaException
    {
        if (index >= path.size())
            return cloneClass;

        try
        {
            for (Iterator it = cloneClass.iterateChildren(); it.hasNext();)
            {
                Feature feature = (Feature) it.next();

                if (feature instanceof Association)
                {
                    Association association = (Association) feature;

                    if (association instanceof ChoiceAssociation)
                    {
                        ChoiceAssociation choiceAssociation = (ChoiceAssociation) association;

                        if (choiceAssociation.getName().equalsIgnoreCase(path.get(index)))
                        {
                            return choiceAssociation.getTarget();
                        }
                        else
                        {
                            for (Iterator<Association> itChoices = choiceAssociation.iterateChoices();
                                 itChoices.hasNext();)
                            {
                                Association ass = itChoices.next();
                                if (ass.getName().equalsIgnoreCase(path.get(index)))
                                {
                                    return findCloneByPath(ass.getTarget(), path, ++index);
                                }
                            }
                        }
                    }
                    else if (association.getName().equalsIgnoreCase(path.get(index)))
                    {
                        return findCloneByPath(((Association) feature).getTarget(), path, ++index);
                    }
                }
            }
        }
        catch (LoaderException e)
        {
            throw new MetaException(e.getMessage(), e);
        }

        throw new MetaException("No Clone Class is matched for Clone Meta:" + path, new IllegalArgumentException());
    }

    /**
     * @param cloneMeta
     * @throws MetaException
     * @deprecated replaced by removeOptionClone
     */
    public static void removeRecursiveClone(CloneMeta cloneMeta) throws MetaException
    {
        if (cloneMeta == null)
        {
            throw new MetaException("CloneMeta Object is null", new IllegalArgumentException("CloneMeta Object is null"));
        }

        if (!cloneMeta.isReference())
        {
            return;
        }

        final List<CloneMeta> childClones = cloneMeta.getChildClones();
        for (int i = 0; i < childClones.size(); i++)
        {
            CloneMeta meta = childClones.get(i);
            childClones.remove(meta);
            return;
        }
    }

}

/**
 * HISTORY      : $Log: not supported by cvs2svn $
 * HISTORY      : Revision 1.32  2006/08/02 18:44:20  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.31  2006/05/04 15:06:53  chene
 * HISTORY      : Change h3s specification from "isRecursive" to "isReference"
 * HISTORY      :
 * HISTORY      : Revision 1.30  2006/01/03 19:16:51  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.29  2006/01/03 18:27:13  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.28  2005/12/29 23:06:15  jiangsc
 * HISTORY      : Changed to latest project name.
 * HISTORY      :
 * HISTORY      : Revision 1.27  2005/12/29 15:39:04  chene
 * HISTORY      : Optimize imports
 * HISTORY      :
 * HISTORY      : Revision 1.26  2005/12/08 23:23:27  chene
 * HISTORY      : no message
 * HISTORY      :
 * HISTORY      : Revision 1.25  2005/12/08 22:46:27  chene
 * HISTORY      : Add /Remove Multiple Clone Support
 * HISTORY      :
 * HISTORY      : Revision 1.24  2005/12/07 23:55:01  chene
 * HISTORY      : Saved point for Clone Multiple Implementation
 * HISTORY      :
 * HISTORY      : Revision 1.23  2005/11/30 23:47:48  chene
 * HISTORY      : Saved point
 * HISTORY      :
 * HISTORY      : Revision 1.22  2005/11/11 22:47:51  chene
 * HISTORY      : Delete AddRecursiveClone method
 * HISTORY      :
 * HISTORY      : Revision 1.21  2005/11/07 20:13:57  chene
 * HISTORY      : Rename sub_datatypes_map to abstract_datatypes_map
 * HISTORY      :
 * HISTORY      : Revision 1.20  2005/11/04 23:36:00  chene
 * HISTORY      : Support structure is mappable if we can not find the default value from HMD
 * HISTORY      :
 * HISTORY      : Revision 1.19  2005/10/17 22:39:01  umkis
 * HISTORY      : no message
 * HISTORY      :
 * HISTORY      : Revision 1.18  2005/10/03 18:55:43  chene
 * HISTORY      : Refactor CloneMeta, add CloneChoiceMeta
 * HISTORY      :
 * HISTORY      : Revision 1.17  2005/09/29 16:14:31  chene
 * HISTORY      : Sortkey supported at HSM file
 * HISTORY      :
 * HISTORY      : Revision 1.16  2005/09/26 22:16:37  chene
 * HISTORY      : Add CMET 999900 support
 * HISTORY      :
 * HISTORY      : Revision 1.15  2005/09/16 23:18:51  chene
 * HISTORY      : Database prototype GUI support, but can not be loaded
 * HISTORY      :
 * HISTORY      : Revision 1.14  2005/09/16 15:51:14  chene
 * HISTORY      : Database prototype
 * HISTORY      :
 * HISTORY      : Revision 1.13  2005/09/15 17:09:42  chene
 * HISTORY      : SelectChoice GUI/Backend Support
 * HISTORY      :
 * HISTORY      : Revision 1.12  2005/09/15 16:01:34  chene
 * HISTORY      : SelectChoice GUI/Backend Support
 * HISTORY      :
 * HISTORY      : Revision 1.11  2005/09/14 03:04:13  chene
 * HISTORY      : Add/Remove Optional Clone support
 * HISTORY      :
 * HISTORY      : Revision 1.10  2005/09/12 21:57:16  chene
 * HISTORY      : Saved Point
 * HISTORY      :
 * HISTORY      : Revision 1.9  2005/09/09 22:41:48  chene
 * HISTORY      : Saved Point
 * HISTORY      :
 * HISTORY      : Revision 1.8  2005/09/08 19:37:00  chene
 * HISTORY      : Saved point
 * HISTORY      :
 * HISTORY      : Revision 1.7  2005/09/04 21:41:15  chene
 * HISTORY      : Add Meta Object Parser Mimimum
 * HISTORY      :
 */
