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

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.hl7.meta.Association;
import org.hl7.meta.CloneClass;
import org.hl7.meta.ChoiceAssociation;
import org.hl7.meta.CloneClassAssociation;
import org.hl7.meta.Feature;
import org.hl7.meta.MessageType;
import org.hl7.meta.LoaderException;

/**
 * An implementation of metadata for an HL7 v3 message type.
 * 
 * @author Skirmantas Kligys (skirmis@hotmail.com)
 */
public class MessageTypeImpl implements MessageType
{
  //-------------------------------------------------------------------------
  /**
   * Message type ID, such as 'PRPA_MT101101'.
   */
  private final String id_;

  /**
   * Message type long name, such as 'A_Encounter_person_registry_active_detailed'.
   */
  private String name_;

  /**
   * Reference to the root clone class in the message type.
   */
  private CloneClassImpl rootClass_;

  /**
   * Map of clone class name to clone class instance, used for looking up
   * a clone class by its name.
   */
  private HashMap<String, CloneClassImpl> cloneClasses_ =
    new HashMap<String, CloneClassImpl>();
  
  //-------------------------------------------------------------------------
  /**
   * Constructor.
   * 
   * @param id  message type ID
   */
  public MessageTypeImpl(String id)
  {
    id_ = id;
  }
  
  //-------------------------------------------------------------------------
  /**
   * Returns the message type ID, such as 'PRPA_MT101101'.
   * 
   * @return the ID
   */
  public String getId()
  {
    return id_;
  }

  //-------------------------------------------------------------------------
  /**
   * Returns the message type long name, such as
   * 'A_Encounter_person_registry_active_detailed'.
   * 
   * @return the long name
   */
  public String getName()
  {
    return name_;
  }

  //-------------------------------------------------------------------------
  /**
   * Returns the reference to the root clone class in the message type.  The
   * remaining clone classes can be reached only by traversing associations
   * pointing at them.
   * 
   * @return the root clone class
   */
  public CloneClass getRootClass()
  {
    return rootClass_;
  }
  
  //-------------------------------------------------------------------------
  /**
   * Returns set of names of all clone classes.
   * 
   * @return  the set of names
   */
  public Set/*<String>*/ getAllCloneClassNames()
  {
    return Collections.unmodifiableSet(cloneClasses_.keySet());
  }
  
  //-------------------------------------------------------------------------
  /**
   * Looks up a clone class by its name.  Returns <code>null</code> if not
   * found.
   * 
   * @param name  clone class name
   * @return  reference to the clone class; <code>null</code> if not found
   */
  public CloneClass lookupCloneClass(String name)
  {
    return cloneClasses_.get(name);
  }

  //-------------------------------------------------------------------------
  /**
   * Sets the message type long name, such as
   * 'A_Encounter_person_registry_active_detailed'.
   * Should be called only from an implementation of
   * {@link org.hl7.meta.MessageTypeLoader MessageTypeLoader}.
   * 
   * @param name  the long name
   */
  /*default*/ public void setName(String name)
  {
    name_ = name;
  }

  //-------------------------------------------------------------------------
  /**
   * Sets the root clone class in the message type.  Can be called only
   * once, throws an <code>IllegalStateException</code> on the second and
   * subsequent calls.
   * 
   */
  void setRootClass(CloneClassImpl clone)
  {
    if (rootClass_ != null)
    {
      throw new IllegalStateException("Root clone class was already set");
    }
    
    rootClass_ = clone;
    addCloneClass(clone);
  }

  //-------------------------------------------------------------------------
  /**
   * Adds a new clone class.
   * Should be called only from an implementation of
   * {@link org.hl7.meta.MessageTypeLoader MessageTypeLoader}.
   * 
   * @param clone  a clone class to be added
   */
  void addCloneClass(CloneClassImpl clone)
  {
    cloneClasses_.put(clone.getName(), clone);
  }

  //-------------------------------------------------------------------------
  /**
   * Removes the clone class.
   * Should be called only from an implementation of
   * {@link org.hl7.meta.XmlIts XmlIts}.
   * 
   * @param clone  a clone class to be removed
   */
  void removeCloneClass(CloneClass clone)
  {
    cloneClasses_.remove(clone.getName());
  }

  //-------------------------------------------------------------------------
  /**
   * Returns a string representation of the message type metadata.
   * It lists all the clone classes found in the message type.
   *
   * @return  a string representation
   */
  public String toString()
  {
    StringBuffer sb = new StringBuffer();
    
    sb.append("Message type ");
    sb.append(id_);
    sb.append(", ");
    sb.append(name_);
    sb.append('\n');
    
    if (rootClass_ == null)
    {
      sb.append("<empty>");
    }
    else
    {
      sb.append('\n');
      Set/*<String>*/ classesVisited = new HashSet/*<String>*/();
      try
      {
        recursivelyOutputCloneClasses(sb, classesVisited, rootClass_);
      }
      catch (LoaderException ex)
      {
        sb.append("\n" + ex.getMessage() + '\n');
      }
    }
    
    return sb.toString();
  }

    /**
     * cloneClasses are only shallowed copied, but rootClass_ is deeped cloned
     * @return
     * @throws CloneNotSupportedException
     */
    public Object clone() throws CloneNotSupportedException
    {
        MessageTypeImpl messageType = (MessageTypeImpl)super.clone();
        messageType.cloneClasses_ = new HashMap<String, CloneClassImpl>();

        for (Iterator iterator = this.cloneClasses_.keySet().iterator(); iterator.hasNext();)
        {
            String name =  (String)iterator.next();
            messageType.cloneClasses_.put(name, ((CloneClassImpl)this.cloneClasses_.get(name)).clone(messageType, false));
        }

        messageType.rootClass_ = this.rootClass_.clone(messageType, true);

        return messageType;
    }


  //-------------------------------------------------------------------------
  /**
   * Iterates through all clone classes starting with <code>clone</code> and
   * prints them out.  Parameter <code>classesVisited</code> holds names
   * of classes already visited, so that duplication and infinite looping
   * can be avoided.
   * 
   * @param sb  string buffer for output
   * @param classesVisited  list of clone classes already visited,
   *                         to avoid infinite loops
   * @param clone  starting point for iteration
   */
  private void recursivelyOutputCloneClasses(StringBuffer sb,
    Set<String> classesVisited, CloneClass clone)
    throws LoaderException
  {
    if (classesVisited.contains(clone.getName())) return;
    classesVisited.add(clone.getName());
    
    sb.append(clone.toString());
    sb.append('\n');
    
    for (Iterator<Feature> it = clone.iterateChildren(); it.hasNext(); )
    {
      Feature f = (Feature)it.next();
      
      if (f instanceof CloneClassAssociation)
      {
        CloneClassAssociation cca = (CloneClassAssociation)f;
        
        recursivelyOutputCloneClasses(sb, classesVisited, cca.getTarget());
      }
      else if (f instanceof ChoiceAssociation)
      {
        ChoiceAssociation ca = (ChoiceAssociation)f;
        
        for (Iterator<Association> it2 = ca.iterateChoices(); it2.hasNext(); )
        {
          Association a = it2.next();
          
          if (a instanceof CloneClassAssociation)
          {
            CloneClassAssociation cca = (CloneClassAssociation)a;
            
            recursivelyOutputCloneClasses(sb, classesVisited, cca.getTarget());
          }
        }
      }
    }
  }
}
