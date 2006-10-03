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

import org.hl7.rim.Participation;
import org.hl7.rim.impl.InfrastructureRootImpl;
import org.hl7.types.CS;
import org.hl7.types.CD;
import org.hl7.types.INT;
import org.hl7.types.BL;
import org.hl7.types.ED;
import org.hl7.types.IVL;
import org.hl7.types.TS;
import org.hl7.types.CE;

import org.hl7.rim.Act;
import org.hl7.rim.Role;
import /*org.hl7.rim.AssociationSet*/java.util.List;

/** Implementation of org.hl7.rim.Participation as a simple data holder bean.
    @see org.hl7.rim.Participation
  */
public class ParticipationImpl extends InfrastructureRootImpl implements Participation {

  private CS _typeCode;
  /** Gets the property typeCode.
      @see org.hl7.rim.Participation#getTypeCode
  */
  public CS getTypeCode() { return _typeCode; }
  /** Sets the property typeCode.
      @see org.hl7.rim.Participation#setTypeCode
  */
  public void setTypeCode(CS typeCode) {
    if(typeCode instanceof org.hl7.hibernate.ClonableCollection)
      typeCode = ((org.hl7.hibernate.ClonableCollection<CS>) typeCode).cloneHibernateCollectionIfNecessary();
    _typeCode = typeCode;
  }

  private CD _functionCode;
  /** Gets the property functionCode.
      @see org.hl7.rim.Participation#getFunctionCode
  */
  public CD getFunctionCode() { return _functionCode; }
  /** Sets the property functionCode.
      @see org.hl7.rim.Participation#setFunctionCode
  */
  public void setFunctionCode(CD functionCode) {
    if(functionCode instanceof org.hl7.hibernate.ClonableCollection)
      functionCode = ((org.hl7.hibernate.ClonableCollection<CD>) functionCode).cloneHibernateCollectionIfNecessary();
    _functionCode = functionCode;
  }

  private CS _contextControlCode;
  /** Gets the property contextControlCode.
      @see org.hl7.rim.Participation#getContextControlCode
  */
  public CS getContextControlCode() { return _contextControlCode; }
  /** Sets the property contextControlCode.
      @see org.hl7.rim.Participation#setContextControlCode
  */
  public void setContextControlCode(CS contextControlCode) {
    if(contextControlCode instanceof org.hl7.hibernate.ClonableCollection)
      contextControlCode = ((org.hl7.hibernate.ClonableCollection<CS>) contextControlCode).cloneHibernateCollectionIfNecessary();
    _contextControlCode = contextControlCode;
  }

  private INT _sequenceNumber;
  /** Gets the property sequenceNumber.
      @see org.hl7.rim.Participation#getSequenceNumber
  */
  public INT getSequenceNumber() { return _sequenceNumber; }
  /** Sets the property sequenceNumber.
      @see org.hl7.rim.Participation#setSequenceNumber
  */
  public void setSequenceNumber(INT sequenceNumber) {
    if(sequenceNumber instanceof org.hl7.hibernate.ClonableCollection)
      sequenceNumber = ((org.hl7.hibernate.ClonableCollection<INT>) sequenceNumber).cloneHibernateCollectionIfNecessary();
    _sequenceNumber = sequenceNumber;
  }

  private BL _negationInd;
  /** Gets the property negationInd.
      @see org.hl7.rim.Participation#getNegationInd
  */
  public BL getNegationInd() { return _negationInd; }
  /** Sets the property negationInd.
      @see org.hl7.rim.Participation#setNegationInd
  */
  public void setNegationInd(BL negationInd) {
    if(negationInd instanceof org.hl7.hibernate.ClonableCollection)
      negationInd = ((org.hl7.hibernate.ClonableCollection<BL>) negationInd).cloneHibernateCollectionIfNecessary();
    _negationInd = negationInd;
  }

  private ED _noteText;
  /** Gets the property noteText.
      @see org.hl7.rim.Participation#getNoteText
  */
  public ED getNoteText() { return _noteText; }
  /** Sets the property noteText.
      @see org.hl7.rim.Participation#setNoteText
  */
  public void setNoteText(ED noteText) {
    if(noteText instanceof org.hl7.hibernate.ClonableCollection)
      noteText = ((org.hl7.hibernate.ClonableCollection<ED>) noteText).cloneHibernateCollectionIfNecessary();
    _noteText = noteText;
  }

  private IVL<TS> _time;
  /** Gets the property time.
      @see org.hl7.rim.Participation#getTime
  */
  public IVL<TS> getTime() { return _time; }
  /** Sets the property time.
      @see org.hl7.rim.Participation#setTime
  */
  public void setTime(IVL<TS> time) {
    if(time instanceof org.hl7.hibernate.ClonableCollection)
      time = ((org.hl7.hibernate.ClonableCollection<IVL<TS>>) time).cloneHibernateCollectionIfNecessary();
    _time = time;
  }

  private CE _modeCode;
  /** Gets the property modeCode.
      @see org.hl7.rim.Participation#getModeCode
  */
  public CE getModeCode() { return _modeCode; }
  /** Sets the property modeCode.
      @see org.hl7.rim.Participation#setModeCode
  */
  public void setModeCode(CE modeCode) {
    if(modeCode instanceof org.hl7.hibernate.ClonableCollection)
      modeCode = ((org.hl7.hibernate.ClonableCollection<CE>) modeCode).cloneHibernateCollectionIfNecessary();
    _modeCode = modeCode;
  }

  private CE _awarenessCode;
  /** Gets the property awarenessCode.
      @see org.hl7.rim.Participation#getAwarenessCode
  */
  public CE getAwarenessCode() { return _awarenessCode; }
  /** Sets the property awarenessCode.
      @see org.hl7.rim.Participation#setAwarenessCode
  */
  public void setAwarenessCode(CE awarenessCode) {
    if(awarenessCode instanceof org.hl7.hibernate.ClonableCollection)
      awarenessCode = ((org.hl7.hibernate.ClonableCollection<CE>) awarenessCode).cloneHibernateCollectionIfNecessary();
    _awarenessCode = awarenessCode;
  }

  private CE _signatureCode;
  /** Gets the property signatureCode.
      @see org.hl7.rim.Participation#getSignatureCode
  */
  public CE getSignatureCode() { return _signatureCode; }
  /** Sets the property signatureCode.
      @see org.hl7.rim.Participation#setSignatureCode
  */
  public void setSignatureCode(CE signatureCode) {
    if(signatureCode instanceof org.hl7.hibernate.ClonableCollection)
      signatureCode = ((org.hl7.hibernate.ClonableCollection<CE>) signatureCode).cloneHibernateCollectionIfNecessary();
    _signatureCode = signatureCode;
  }

  private ED _signatureText;
  /** Gets the property signatureText.
      @see org.hl7.rim.Participation#getSignatureText
  */
  public ED getSignatureText() { return _signatureText; }
  /** Sets the property signatureText.
      @see org.hl7.rim.Participation#setSignatureText
  */
  public void setSignatureText(ED signatureText) {
    if(signatureText instanceof org.hl7.hibernate.ClonableCollection)
      signatureText = ((org.hl7.hibernate.ClonableCollection<ED>) signatureText).cloneHibernateCollectionIfNecessary();
    _signatureText = signatureText;
  }

  private BL _performInd;
  /** Gets the property performInd.
      @see org.hl7.rim.Participation#getPerformInd
  */
  public BL getPerformInd() { return _performInd; }
  /** Sets the property performInd.
      @see org.hl7.rim.Participation#setPerformInd
  */
  public void setPerformInd(BL performInd) {
    if(performInd instanceof org.hl7.hibernate.ClonableCollection)
      performInd = ((org.hl7.hibernate.ClonableCollection<BL>) performInd).cloneHibernateCollectionIfNecessary();
    _performInd = performInd;
  }

  private CE _substitutionConditionCode;
  /** Gets the property substitutionConditionCode.
      @see org.hl7.rim.Participation#getSubstitutionConditionCode
  */
  public CE getSubstitutionConditionCode() { return _substitutionConditionCode; }
  /** Sets the property substitutionConditionCode.
      @see org.hl7.rim.Participation#setSubstitutionConditionCode
  */
  public void setSubstitutionConditionCode(CE substitutionConditionCode) {
    if(substitutionConditionCode instanceof org.hl7.hibernate.ClonableCollection)
      substitutionConditionCode = ((org.hl7.hibernate.ClonableCollection<CE>) substitutionConditionCode).cloneHibernateCollectionIfNecessary();
    _substitutionConditionCode = substitutionConditionCode;
  }

  private Act _act;
  /** Gets the property act.
      @see org.hl7.rim.Participation#getAct
  */
  public Act getAct() {
    return _act;
  }
  /** Sets the property act.
      @see org.hl7.rim.Participation#setAct
  */
  public void setAct(Act act) {
    _act = act;
  }

  private Role _role;
  /** Gets the property role.
      @see org.hl7.rim.Participation#getRole
  */
  public Role getRole() {
    return _role;
  }
  /** Sets the property role.
      @see org.hl7.rim.Participation#setRole
  */
  public void setRole(Role role) {
    _role = role;
  }
  public Object clone() {
    ParticipationImpl that = (ParticipationImpl) super.clone();
    return that;
  }
}
