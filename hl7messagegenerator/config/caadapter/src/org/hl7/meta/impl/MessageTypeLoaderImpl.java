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
import java.util.logging.Logger;

import org.hl7.meta.Association;
import org.hl7.meta.ChoiceAssociation;
import org.hl7.meta.CloneClass;
import org.hl7.meta.CloneClassAssociation;
import org.hl7.meta.Feature;
import org.hl7.meta.JavaIts;
import org.hl7.meta.LoaderException;
import org.hl7.meta.MessageType;
import org.hl7.meta.MessageTypeLoader;
import org.hl7.meta.XmlItsException;

import org.hl7.util.FactoryException;

/**
 * Common part of implementation of <code>MessageTypeLoader</code>.
 *
 * @author Skirmantas Kligys (skirmis@hotmail.com)
 */
public abstract class MessageTypeLoaderImpl implements MessageTypeLoader
{
  //-------------------------------------------------------------------------
  protected static final Logger LOGGER = Logger.getLogger("org.hl7.meta");

  //-------------------------------------------------------------------------
  protected final JavaIts javaIts_ = new JavaItsImpl();

  //-------------------------------------------------------------------------
  /* (non-Javadoc)
   * @see org.hl7.meta.MessageTypeLoader#loadMessageType(java.lang.String)
   */
  public abstract MessageType loadMessageType(String id) throws LoaderException;

  //-------------------------------------------------------------------------
  /* (non-Javadoc)
   * @see org.hl7.meta.MessageTypeLoader#loadCmet(java.lang.String)
   */
  public abstract MessageType loadCmet(String id) throws LoaderException;

  //-------------------------------------------------------------------------
  /**
   * Does nothing, should be overridden for database loaders to close
   * database connections.
   */
  public void close()
  {
  }

  //-------------------------------------------------------------------------
  /**
   * Postprocesses a loaded message type.  Currently performs two steps:
   * folds generalization associations, and adjusts base RIM class of a clone
   * class to be the most derived RIM class mentioned by any attributes or
   * associations.
   */
  protected void postProcess(MessageType mt) throws LoaderException
  {
    try
    {
      LOGGER.finest("----------------------------------------\n" +
        "Before folding:\n" + mt.toString());
  
      // Should be able to use multiple ITSs.
      new XmlItsBallot3Impl().foldGeneralizations(mt);
  
      LOGGER.finest("----------------------------------------\n" +
        "After folding:\n" + mt.toString());
      
      adjustRimClasses(mt);
  
      LOGGER.finest("----------------------------------------\n" +
        "After adjusting RIM classes:\n" + mt.toString());
    }
    catch (FactoryException ex)
    {
      throw new LoaderException(ex);
    }
    catch (XmlItsException ex)
    {
        ex.printStackTrace();
        throw new LoaderException(ex);
    }
  }

  //-------------------------------------------------------------------------
  /**
   * Adjusts base RIM class of a clone class to be the most derived RIM
   * class mentioned by any attributes or associations.
   * 
   * @param mt  message type metadata to process
   */
  private void adjustRimClasses(MessageType mt) throws LoaderException
  {
    MessageTypeImpl messageTypeImpl = (MessageTypeImpl)mt;
    CloneClass clone = messageTypeImpl.getRootClass();
    Set/*<String>*/ clonesProcessed = new HashSet/*<String>*/();
    adjustRimClassesRecursively(clonesProcessed, clone);
  }

  //-------------------------------------------------------------------------
  private void adjustRimClassesRecursively(Set clonesProcessed,
    CloneClass cloneClass) throws LoaderException
  {
    if (clonesProcessed.contains(cloneClass.getName())) return;

    clonesProcessed.add(cloneClass.getName());
    adjustRimClass(cloneClass);

    for (Iterator/*<Feature>*/ it = cloneClass.iterateChildren();
      it.hasNext(); )
    {
      Feature feature = (Feature)it.next();

      if (feature instanceof CloneClassAssociation)
      {
        CloneClassAssociation cloneAssoc = (CloneClassAssociation)feature;
        CloneClass target = cloneAssoc.getTarget();

        adjustRimClassesRecursively(clonesProcessed, target);
      }
      else if (feature instanceof ChoiceAssociation)
      {
        ChoiceAssociation choiceAssoc = (ChoiceAssociation)feature;
        for (Iterator/*<Association>*/ it2 = choiceAssoc.iterateChoices();
          it2.hasNext(); )
        {
          Association choice = (Association)it2.next();

          if (choice instanceof CloneClassAssociation)
          {
            CloneClassAssociation cloneAssoc = (CloneClassAssociation)choice;
            CloneClass target = cloneAssoc.getTarget();

            adjustRimClassesRecursively(clonesProcessed, target);
          }
        }
      }
    }
  }

  //-------------------------------------------------------------------------
  private void adjustRimClass(CloneClass cloneClass)
    throws LoaderException
  {
    CloneClassImpl cci = (CloneClassImpl)cloneClass;
    for (Iterator/*<Feature>*/ it = cci.iterateChildren(); it.hasNext(); )
    {
      Feature feature = (Feature)it.next();
      
      String sourceRimClass = feature.getRimClass();
      String currentRimClass = cci.getRimClass();
      if (sourceRimClass.equals(currentRimClass)) continue;
      
      if (isChildClass(currentRimClass, sourceRimClass))
      {
        cci.setRimClass(sourceRimClass);
      }
    }
  }

  //-------------------------------------------------------------------------
  /**
   * Checks if RIM class child extends RIM class parent.
   * 
   * @param parent
   * @param child
   * @return  true if it does extend
   */
  private boolean isChildClass(String parent, String child)
    throws LoaderException
  {
    try
    {
      Class parentClass = javaIts_.getRimInterface(parent);
      Class childClass = javaIts_.getRimInterface(child);
      return parentClass.isAssignableFrom(childClass);
    }
    catch (ClassNotFoundException ex)
    {
      throw new LoaderException(ex);
    }
  }
}
