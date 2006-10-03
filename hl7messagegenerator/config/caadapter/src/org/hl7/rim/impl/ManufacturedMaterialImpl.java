/*
THIS FILE IS GENERATED AUTOMATICALLY - DO NOT MODIFY.

The contents of this file are subject to the Health Level-7 Public
License Version 1.0 (the "License"); you may not use this file except
in compliance with the License. You may obtain a copy of the License
at http://www.hl7.org/HPL/

Software distributed under the License is distributed on an "AS IS"
basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See
the License for the specific language governing rights and limitations
under the License.

The Original Code is all this file.

The Initial Developer of the Original Code is automatically generated
from HL7 copyrighted standards specification which may be subject to
different license. Portions created by Initial Developer are Copyright
(C) 2002-2004 Health Level Seven, Inc. All Rights Reserved.

THIS FILE IS GENERATED AUTOMATICALLY - DO NOT MODIFY.
*/
package org.hl7.rim.impl;

import org.hibernate.Criteria;
import org.hl7.hibernate.Persistence;

import org.hl7.rim.ManufacturedMaterial;
import org.hl7.rim.impl.MaterialImpl;
import org.hl7.types.ST;
import org.hl7.types.IVL;
import org.hl7.types.TS;
import org.hl7.types.TS;

import /*org.hl7.rim.AssociationSet*/java.util.List;

/** Implementation of org.hl7.rim.ManufacturedMaterial as a simple data holder bean.
    @see org.hl7.rim.ManufacturedMaterial
  */
public class ManufacturedMaterialImpl extends MaterialImpl implements ManufacturedMaterial {

  private ST _lotNumberText;
  /** Gets the property lotNumberText.
      @see org.hl7.rim.ManufacturedMaterial#getLotNumberText
  */
  public ST getLotNumberText() { return _lotNumberText; }
  /** Sets the property lotNumberText.
      @see org.hl7.rim.ManufacturedMaterial#setLotNumberText
  */
  public void setLotNumberText(ST lotNumberText) {
    if(lotNumberText instanceof org.hl7.hibernate.ClonableCollection)
      lotNumberText = ((org.hl7.hibernate.ClonableCollection<ST>) lotNumberText).cloneHibernateCollectionIfNecessary();
    _lotNumberText = lotNumberText;
  }

  private IVL<TS> _expirationTime;
  /** Gets the property expirationTime.
      @see org.hl7.rim.ManufacturedMaterial#getExpirationTime
  */
  public IVL<TS> getExpirationTime() { return _expirationTime; }
  /** Sets the property expirationTime.
      @see org.hl7.rim.ManufacturedMaterial#setExpirationTime
  */
  public void setExpirationTime(IVL<TS> expirationTime) {
    if(expirationTime instanceof org.hl7.hibernate.ClonableCollection)
      expirationTime = ((org.hl7.hibernate.ClonableCollection<IVL<TS>>) expirationTime).cloneHibernateCollectionIfNecessary();
    _expirationTime = expirationTime;
  }

  private IVL<TS> _stabilityTime;
  /** Gets the property stabilityTime.
      @see org.hl7.rim.ManufacturedMaterial#getStabilityTime
  */
  public IVL<TS> getStabilityTime() { return _stabilityTime; }
  /** Sets the property stabilityTime.
      @see org.hl7.rim.ManufacturedMaterial#setStabilityTime
  */
  public void setStabilityTime(IVL<TS> stabilityTime) {
    if(stabilityTime instanceof org.hl7.hibernate.ClonableCollection)
      stabilityTime = ((org.hl7.hibernate.ClonableCollection<IVL<TS>>) stabilityTime).cloneHibernateCollectionIfNecessary();
    _stabilityTime = stabilityTime;
  }
  public Object clone() {
    ManufacturedMaterialImpl that = (ManufacturedMaterialImpl) super.clone();
    return that;
  }
}
