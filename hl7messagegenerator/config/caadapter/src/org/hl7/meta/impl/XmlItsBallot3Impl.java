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

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.hl7.util.FactoryException;
import org.hl7.meta.*;

/**
 * Provides XML ITS specific functionality; as opposed to generic metadata.
 * Corresponds to v3 ballot 3.
 *
 * @author  Jerry Joyce
 */
public class XmlItsBallot3Impl implements XmlIts
{
  //-------------------------------------------------------------------------
  /**
   * Looks up metadata for a linked clone class or nested datatype, based
   * on XML tag name.  Really belongs in XML ITS class.
   * 
   * @param cloneClass  parent clone class to start from
   * @param tag  XML tag to look up
   * @return  metadata found, or null if not found
   */
  public Feature lookupMetadataByTag(CloneClass cloneClass,
    String tag)
  {
    if (tag == null) return null;

    String assocPrefix = tag + '_';

    for (Iterator it = cloneClass.iterateChildren(); it.hasNext(); )
    {
      Feature feature = (Feature)it.next();
      
      if (feature instanceof Attribute)
      {
        if (tag.equals(feature.getName()))
        {
          return (Attribute)feature;
        }
      }
      else if (feature instanceof Association)
      {
          if (feature instanceof ChoiceAssociation)
         {
           ChoiceAssociation choiceAssociation = (ChoiceAssociation)feature;
            for (Iterator/*<Association>*/ it2 = choiceAssociation.iterateChoices();
              it2.hasNext(); )
            {
              Association association = (Association)it2.next();
              if (association.getName().equals(tag))
              {
                return association;
              }
            }
         }
        if (feature instanceof CmetAssociation) {
            CmetAssociation choiceAssociation = (CmetAssociation)feature;

            // This is VERY dangerous.  It is looking for meta information
            // based on elementname=cmetid OR if the elemementname is
            // *contained in* the choiceAssociationName.
            if (feature.getName().indexOf(tag)!=-1
//              ||
            /*feature.getName().equals(tag) ||
            feature.getName().startsWith(assocPrefix) ||*/
            )     
            {
              return (Association)feature;
            }
        }
        else {
          if (feature.getName().equals(tag) ||
	      feature.getName().startsWith(assocPrefix))
          {
            return (Association)feature;
          }
        }
      }
    }

    // Feature not found.
    return null;
  }

  //-------------------------------------------------------------------------
  /**
   * If the current class contains any associations with non-null choiceSet,
   * traverses all associations of RIM type 'generalizes' and folds them
   * into a single class.  Only folds the current class, not classes linked
   * to it.
   * 
   * @param messageType  message type containing folded clone classes
   * @param cloneClass  clone class to start from
   * 
   * @throws LoaderException  if loading CMETs on demand fails
   * @throws FactoryException  if creating a folded class fails
   * @throws XmlItsException  if consistency checks fail
   */
  public void foldGeneralizations(MessageType messageType,
    CloneClass cloneClass)
    throws LoaderException, FactoryException, XmlItsException
  {
    MessageTypeImpl messageTypeImpl = (MessageTypeImpl)messageType;
    Ballot3Folder folder = new Ballot3Folder(messageTypeImpl);
    folder.foldGeneralizations((CloneClassImpl)cloneClass);
  }

  //-------------------------------------------------------------------------
  /**
   * Folds all classes in a message type.
   * 
   * @param messageType  message type containing folded clone classes
   * 
   * @throws LoaderException  if loading CMETs on demand fails
   * @throws FactoryException  if creating a folded class fails
   * @throws XmlItsException  if consistency checks fail
   */
  public void foldGeneralizations(MessageType messageType)
    throws LoaderException, FactoryException, XmlItsException
  {
    MessageTypeImpl messageTypeImpl = (MessageTypeImpl)messageType;
    Ballot3Folder folder = new Ballot3Folder(messageTypeImpl);
    
    CloneClassImpl clone = (CloneClassImpl)messageTypeImpl.getRootClass();
    Set/*<String>*/ clonesProcessed = new HashSet/*<String>*/();
    folder.foldRecursively(clonesProcessed, clone);
  }
}
