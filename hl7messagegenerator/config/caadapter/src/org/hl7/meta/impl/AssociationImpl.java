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

import org.hl7.meta.Association;
import org.hl7.meta.CloneClass;
import org.hl7.meta.LoaderException;
import org.hl7.meta.MetSource;

/**
 * An implementation of metadata for an abstract HL7 v3 association
 * between two clone classes, or between a clone class and a CMET.
 * 
 * @author Skirmantas Kligys (skirmis@hotmail.com)
 */
public abstract class AssociationImpl
  extends FeatureImpl implements Association
{
  //-------------------------------------------------------------------------
  /**
   * MET source enumeration for this association.
   */
  private MetSource metSource_;
  
  //-------------------------------------------------------------------------
  /**
   * Constructor.
   */
  public AssociationImpl(CloneClass parent)
  {
    super(parent);
  }

  //-------------------------------------------------------------------------
  /**
   * Returns the MET source enumeration for this association.
   * 
   * @return the MET source
   */
  public MetSource getMetSource()
  {
    return metSource_;
  }

  /**
   * Sets the MET source enumeration for this association.
   * Should be called only from an implementation of
   * {@link org.hl7.meta.MessageTypeLoader MessageTypeLoader}.
   * 
   * @param metSource  the MET source
   */
  /*default*/ void setMetSource(MetSource metSource)
  {
    metSource_ = metSource;
  }

    public boolean isReference()
    {
        return (metSource_ == MetSource.RECURSIVE  || metSource_ == MetSource.REUSED);
    }

    /**
   * Returns the clone class that is targeted by this association.
   * 
   * @return the target clone class
   * @throws LoaderException  if on-demand loading fails for
   *   {@link org.hl7.meta.CmetAssociation CmetAssociation}
   */
  public abstract CloneClass getTarget() throws LoaderException;

}
