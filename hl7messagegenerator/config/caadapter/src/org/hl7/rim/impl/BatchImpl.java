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

import org.hl7.rim.Batch;
import org.hl7.rim.impl.TransmissionImpl;
import org.hl7.types.CS;
import org.hl7.types.II;
import org.hl7.types.SC;
import org.hl7.types.SET;
import org.hl7.types.ST;
import org.hl7.types.INT;
import org.hl7.types.INT;

import org.hl7.rim.Transmission;
import /*org.hl7.rim.AssociationSet*/java.util.List;

/** Implementation of org.hl7.rim.Batch as a simple data holder bean.
    @see org.hl7.rim.Batch
  */
public class BatchImpl extends TransmissionImpl implements Batch {

  private CS _versionCode;
  /** Gets the property versionCode.
      @see org.hl7.rim.Batch#getVersionCode
  */
  public CS getVersionCode() { return _versionCode; }
  /** Sets the property versionCode.
      @see org.hl7.rim.Batch#setVersionCode
  */
  public void setVersionCode(CS versionCode) {
    if(versionCode instanceof org.hl7.hibernate.ClonableCollection)
      versionCode = ((org.hl7.hibernate.ClonableCollection<CS>) versionCode).cloneHibernateCollectionIfNecessary();
    _versionCode = versionCode;
  }

  private II _interactionId;
  /** Gets the property interactionId.
      @see org.hl7.rim.Batch#getInteractionId
  */
  public II getInteractionId() { return _interactionId; }
  /** Sets the property interactionId.
      @see org.hl7.rim.Batch#setInteractionId
  */
  public void setInteractionId(II interactionId) {
    if(interactionId instanceof org.hl7.hibernate.ClonableCollection)
      interactionId = ((org.hl7.hibernate.ClonableCollection<II>) interactionId).cloneHibernateCollectionIfNecessary();
    _interactionId = interactionId;
  }

  private II _referenceControlId;
  /** Gets the property referenceControlId.
      @see org.hl7.rim.Batch#getReferenceControlId
  */
  public II getReferenceControlId() { return _referenceControlId; }
  /** Sets the property referenceControlId.
      @see org.hl7.rim.Batch#setReferenceControlId
  */
  public void setReferenceControlId(II referenceControlId) {
    if(referenceControlId instanceof org.hl7.hibernate.ClonableCollection)
      referenceControlId = ((org.hl7.hibernate.ClonableCollection<II>) referenceControlId).cloneHibernateCollectionIfNecessary();
    _referenceControlId = referenceControlId;
  }

  private SC _name;
  /** Gets the property name.
      @see org.hl7.rim.Batch#getName
  */
  public SC getName() { return _name; }
  /** Sets the property name.
      @see org.hl7.rim.Batch#setName
  */
  public void setName(SC name) {
    if(name instanceof org.hl7.hibernate.ClonableCollection)
      name = ((org.hl7.hibernate.ClonableCollection<SC>) name).cloneHibernateCollectionIfNecessary();
    _name = name;
  }

  private SET<ST> _batchComment;
  /** Gets the property batchComment.
      @see org.hl7.rim.Batch#getBatchComment
  */
  public SET<ST> getBatchComment() { return _batchComment; }
  /** Sets the property batchComment.
      @see org.hl7.rim.Batch#setBatchComment
  */
  public void setBatchComment(SET<ST> batchComment) {
    if(batchComment instanceof org.hl7.hibernate.ClonableCollection)
      batchComment = ((org.hl7.hibernate.ClonableCollection<SET<ST>>) batchComment).cloneHibernateCollectionIfNecessary();
    _batchComment = batchComment;
  }

  private INT _transmissionQuantity;
  /** Gets the property transmissionQuantity.
      @see org.hl7.rim.Batch#getTransmissionQuantity
  */
  public INT getTransmissionQuantity() { return _transmissionQuantity; }
  /** Sets the property transmissionQuantity.
      @see org.hl7.rim.Batch#setTransmissionQuantity
  */
  public void setTransmissionQuantity(INT transmissionQuantity) {
    if(transmissionQuantity instanceof org.hl7.hibernate.ClonableCollection)
      transmissionQuantity = ((org.hl7.hibernate.ClonableCollection<INT>) transmissionQuantity).cloneHibernateCollectionIfNecessary();
    _transmissionQuantity = transmissionQuantity;
  }

  private SET<INT> _batchTotalNumber;
  /** Gets the property batchTotalNumber.
      @see org.hl7.rim.Batch#getBatchTotalNumber
  */
  public SET<INT> getBatchTotalNumber() { return _batchTotalNumber; }
  /** Sets the property batchTotalNumber.
      @see org.hl7.rim.Batch#setBatchTotalNumber
  */
  public void setBatchTotalNumber(SET<INT> batchTotalNumber) {
    if(batchTotalNumber instanceof org.hl7.hibernate.ClonableCollection)
      batchTotalNumber = ((org.hl7.hibernate.ClonableCollection<SET<INT>>) batchTotalNumber).cloneHibernateCollectionIfNecessary();
    _batchTotalNumber = batchTotalNumber;
  }

  private /*AssociationSet*/List<Transmission> _transmission;
  /** Gets the property transmission.
      @see org.hl7.rim.Batch#getTransmission
  */
  public /*AssociationSet*/List<Transmission> getTransmission() {
    return _transmission;
  }
  /** Sets the property transmission.
      @see org.hl7.rim.Batch#setTransmission
  */
  public void setTransmission(/*AssociationSet*/List<Transmission> transmission) {
    _transmission = transmission;
  }
  /** Adds an association transmission.
      @see org.hl7.rim.Batch#setTransmission
  */
  public void addTransmission(Transmission transmission) {
        // create the association set if it doesn't exist already
    if(_transmission == null) _transmission = new AssociationSetImpl<Transmission>();
    // add the association to the association set
    getTransmission().add(transmission);
    // make the inverse link
    transmission.setBatch(this);
  }
  public Object clone() {
    BatchImpl that = (BatchImpl) super.clone();
    that._transmission= null;
    return that;
  }
}
