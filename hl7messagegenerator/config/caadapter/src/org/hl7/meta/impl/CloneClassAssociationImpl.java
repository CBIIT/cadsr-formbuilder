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

import org.hl7.meta.*;

/**
 * An implementation of metadata for an HL7 v3 association between
 * two clone classes.
 * 
 * @author Skirmantas Kligys (skirmis@hotmail.com)
 */
public class CloneClassAssociationImpl
  extends AssociationImpl implements CloneClassAssociation
{
  //-------------------------------------------------------------------------
  /**
   * Clone class that is targeted by this association.
   */
  private CloneClass target_;
  
  //-------------------------------------------------------------------------
  /**
   * Constructor.
   */
  public CloneClassAssociationImpl(CloneClass parent)
  {
    super(parent);
  }
  
  //-------------------------------------------------------------------------
  /**
   * Returns the clone class that is targeted by this association.
   * 
   * @return the target clone class
   */
  public CloneClass getTarget()
  {
      return target_;
  }

  //-------------------------------------------------------------------------
  /**
   * Sets the clone class that is targeted by this association.
   * Should be called only from an implementation of
   * {@link org.hl7.meta.MessageTypeLoader MessageTypeLoader}.
   * 
   * @param target  the target clone class
   */
  /*default*/ void setTarget(CloneClass target)
  {
    target_ = target;
  }

  //-------------------------------------------------------------------------
  /**
   * Returns a string representation of the clone association metadata.
   * 
   * @return  a string representation
   */
  public String toString()
  {
    return getName() + ": " + getRimPropertyName() + ": " + target_.getName() +
      " // " + getCardinality();
  }

  private String prefix_="";

    public String getPrefix() {
          return prefix_;
      }

      public void setPrefix_(String prefix_) {
          this.prefix_ = prefix_;
      }

    protected CloneClassAssociationImpl clone(CloneClass parent) throws CloneNotSupportedException
    {
//        System.out.println("clone association name: " + this.getName());
        CloneClassAssociationImpl association = (CloneClassAssociationImpl)super.clone(parent);
        if ((getMetSource() != MetSource.RECURSIVE) && (getMetSource() != MetSource.REUSED))
        {
            association.target_ = ((CloneClassImpl)this.target_).clone((MessageTypeImpl)((CloneClassImpl)association.getParent()).getMessageType(), true);
        }
        else
        {
            final CloneClassImpl cloneClass = (CloneClassImpl)association.getParent();
            association.target_ = cloneClass.getMessageType().lookupCloneClass(association.getTarget().getName());
        }
        return association;
    }


}
