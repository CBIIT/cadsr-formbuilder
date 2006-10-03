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

import org.hl7.rim.Container;
import org.hl7.rim.impl.ManufacturedMaterialImpl;
import org.hl7.types.PQ;
import org.hl7.types.CE;

import /*org.hl7.rim.AssociationSet*/java.util.List;

/** Implementation of org.hl7.rim.Container as a simple data holder bean.
    @see org.hl7.rim.Container
  */
public class ContainerImpl extends ManufacturedMaterialImpl implements Container {

  private PQ _capacityQuantity;
  /** Gets the property capacityQuantity.
      @see org.hl7.rim.Container#getCapacityQuantity
  */
  public PQ getCapacityQuantity() { return _capacityQuantity; }
  /** Sets the property capacityQuantity.
      @see org.hl7.rim.Container#setCapacityQuantity
  */
  public void setCapacityQuantity(PQ capacityQuantity) {
    if(capacityQuantity instanceof org.hl7.hibernate.ClonableCollection)
      capacityQuantity = ((org.hl7.hibernate.ClonableCollection<PQ>) capacityQuantity).cloneHibernateCollectionIfNecessary();
    _capacityQuantity = capacityQuantity;
  }

  private PQ _heightQuantity;
  /** Gets the property heightQuantity.
      @see org.hl7.rim.Container#getHeightQuantity
  */
  public PQ getHeightQuantity() { return _heightQuantity; }
  /** Sets the property heightQuantity.
      @see org.hl7.rim.Container#setHeightQuantity
  */
  public void setHeightQuantity(PQ heightQuantity) {
    if(heightQuantity instanceof org.hl7.hibernate.ClonableCollection)
      heightQuantity = ((org.hl7.hibernate.ClonableCollection<PQ>) heightQuantity).cloneHibernateCollectionIfNecessary();
    _heightQuantity = heightQuantity;
  }

  private PQ _diameterQuantity;
  /** Gets the property diameterQuantity.
      @see org.hl7.rim.Container#getDiameterQuantity
  */
  public PQ getDiameterQuantity() { return _diameterQuantity; }
  /** Sets the property diameterQuantity.
      @see org.hl7.rim.Container#setDiameterQuantity
  */
  public void setDiameterQuantity(PQ diameterQuantity) {
    if(diameterQuantity instanceof org.hl7.hibernate.ClonableCollection)
      diameterQuantity = ((org.hl7.hibernate.ClonableCollection<PQ>) diameterQuantity).cloneHibernateCollectionIfNecessary();
    _diameterQuantity = diameterQuantity;
  }

  private CE _capTypeCode;
  /** Gets the property capTypeCode.
      @see org.hl7.rim.Container#getCapTypeCode
  */
  public CE getCapTypeCode() { return _capTypeCode; }
  /** Sets the property capTypeCode.
      @see org.hl7.rim.Container#setCapTypeCode
  */
  public void setCapTypeCode(CE capTypeCode) {
    if(capTypeCode instanceof org.hl7.hibernate.ClonableCollection)
      capTypeCode = ((org.hl7.hibernate.ClonableCollection<CE>) capTypeCode).cloneHibernateCollectionIfNecessary();
    _capTypeCode = capTypeCode;
  }

  private CE _separatorTypeCode;
  /** Gets the property separatorTypeCode.
      @see org.hl7.rim.Container#getSeparatorTypeCode
  */
  public CE getSeparatorTypeCode() { return _separatorTypeCode; }
  /** Sets the property separatorTypeCode.
      @see org.hl7.rim.Container#setSeparatorTypeCode
  */
  public void setSeparatorTypeCode(CE separatorTypeCode) {
    if(separatorTypeCode instanceof org.hl7.hibernate.ClonableCollection)
      separatorTypeCode = ((org.hl7.hibernate.ClonableCollection<CE>) separatorTypeCode).cloneHibernateCollectionIfNecessary();
    _separatorTypeCode = separatorTypeCode;
  }

  private PQ _barrierDeltaQuantity;
  /** Gets the property barrierDeltaQuantity.
      @see org.hl7.rim.Container#getBarrierDeltaQuantity
  */
  public PQ getBarrierDeltaQuantity() { return _barrierDeltaQuantity; }
  /** Sets the property barrierDeltaQuantity.
      @see org.hl7.rim.Container#setBarrierDeltaQuantity
  */
  public void setBarrierDeltaQuantity(PQ barrierDeltaQuantity) {
    if(barrierDeltaQuantity instanceof org.hl7.hibernate.ClonableCollection)
      barrierDeltaQuantity = ((org.hl7.hibernate.ClonableCollection<PQ>) barrierDeltaQuantity).cloneHibernateCollectionIfNecessary();
    _barrierDeltaQuantity = barrierDeltaQuantity;
  }

  private PQ _bottomDeltaQuantity;
  /** Gets the property bottomDeltaQuantity.
      @see org.hl7.rim.Container#getBottomDeltaQuantity
  */
  public PQ getBottomDeltaQuantity() { return _bottomDeltaQuantity; }
  /** Sets the property bottomDeltaQuantity.
      @see org.hl7.rim.Container#setBottomDeltaQuantity
  */
  public void setBottomDeltaQuantity(PQ bottomDeltaQuantity) {
    if(bottomDeltaQuantity instanceof org.hl7.hibernate.ClonableCollection)
      bottomDeltaQuantity = ((org.hl7.hibernate.ClonableCollection<PQ>) bottomDeltaQuantity).cloneHibernateCollectionIfNecessary();
    _bottomDeltaQuantity = bottomDeltaQuantity;
  }
  public Object clone() {
    ContainerImpl that = (ContainerImpl) super.clone();
    return that;
  }
}
