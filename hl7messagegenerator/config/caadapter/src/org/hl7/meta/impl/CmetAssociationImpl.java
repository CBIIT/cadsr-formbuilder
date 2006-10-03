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

import org.hl7.meta.CloneClass;
import org.hl7.meta.CmetAssociation;
import org.hl7.meta.LoaderException;

/**
 * An implementation of metadata for an HL7 v3 association between
 * a clone class and a CMET.  Implements on-demand cached loading of CMET
 * metadata through
 * {@link org.hl7.meta.CmetCache CmetCache}.
 * 
 * @author Skirmantas Kligys (skirmis@hotmail.com)
 */
public class CmetAssociationImpl
  extends AssociationImpl implements CmetAssociation
{
  //-------------------------------------------------------------------------
  /**
   * Saved reference to <code>CmetCache</code> to enable on-demand cached
   * loading of CMET metadata.
   */
  private final CmetCacheImpl cmetCache_;
  /**
   * Referenced CMET ID.
   */
  private String cmetId_;
  
  //-------------------------------------------------------------------------
  /**
   * Constructor.  Needs a reference to <code>CmetCache</code>
   * to enable on-demand cached loading.
   * 
   * @param cmetCache  reference to <code>CmetCache</code>.
   */
  public CmetAssociationImpl(CloneClass parent, CmetCacheImpl cmetCache)
  {
    super(parent);
    
    if (cmetCache == null) throw new NullPointerException();
    cmetCache_ = cmetCache;
  }

  //-------------------------------------------------------------------------
  /**
   * Returns the far end CMET ID; for example, COCT_MT010101.
   * 
   * @return the CMET ID
   */
  public String getCmetId()
  {
    return cmetId_;
  }

  //-------------------------------------------------------------------------
  /**
   * Sets the far end CMET ID.
   * Should be called only from an implementation of
   * {@link org.hl7.meta.MessageTypeLoader MessageTypeLoader}.
   * 
   * @param cmetId  the CMET ID
   */
  /*default*/ void setCmetId(String cmetId)
  {
    cmetId_ = cmetId;
  }

  //-------------------------------------------------------------------------
  /**
   * Gets the reference to the CMET cache.
   * Should be called only from an implementation of
   * {@link org.hl7.meta.XmlIts XmlIts}.
   * 
   * @return  the reference
   */
  /*default*/ CmetCacheImpl getCmetCache()
  {
    return cmetCache_;
  }
  
  //-------------------------------------------------------------------------
  /**
   * Returns the clone class that is targeted by this association.  It will
   * always be the root class of the CMET.
   * 
   * @return the target clone class
   * @throws LoaderException  if dynamic CMET loading fails
   */
  public CloneClass getTarget() throws LoaderException
  {
       return getTarget(true);
  }


    /**
   * Returns the clone class that is targeted by this association.  It will
   * always be the root class of the CMET.
   *
   * @return the target clone class
   * @throws LoaderException  if dynamic CMET loading fails
   */
  public CloneClass getTarget(boolean needClone) throws LoaderException
  {
      CloneClassImpl rootClass = (CloneClassImpl)cmetCache_.getCmet(cmetId_, needClone).getRootClass();
      rootClass.setParent(getParent());
      rootClass.setFullName(getParent().getFullName() + "." + getName());   // set full name on the fly
      return rootClass;
  }
  //-------------------------------------------------------------------------
  /**
   * Returns a string representation of the attribute metadata.
   * 
   * @return  a string representation
   */
  public String toString()
  {
    return getName() + ": " + getRimPropertyName() + ": " + cmetId_ +
      " // " + getCardinality();
  }

//    p CmetAssociationImpl clone() throws CloneNotSupportedException
//    {
//        CmetAssociationImpl association = (CmetAssociationImpl)super.clone();
//        return association;
//    }

}
