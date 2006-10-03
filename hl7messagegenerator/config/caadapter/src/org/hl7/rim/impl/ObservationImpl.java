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

import org.hl7.rim.Observation;
import org.hl7.rim.impl.ActImpl;
import org.hl7.types.ANY;
import org.hl7.types.SET;
import org.hl7.types.CE;
import org.hl7.types.CE;
import org.hl7.types.CD;

import /*org.hl7.rim.AssociationSet*/java.util.List;

/** Implementation of org.hl7.rim.Observation as a simple data holder bean.
    @see org.hl7.rim.Observation
  */
public class ObservationImpl extends ActImpl implements Observation {

  private ANY _value;
  /** Gets the property value.
      @see org.hl7.rim.Observation#getValue
  */
  public ANY getValue() { return _value; }
  /** Sets the property value.
      @see org.hl7.rim.Observation#setValue
  */
  public void setValue(ANY value) {
    if(value instanceof org.hl7.hibernate.ClonableCollection)
      value = ((org.hl7.hibernate.ClonableCollection<ANY>) value).cloneHibernateCollectionIfNecessary();
    _value = value;
  }

  private SET<CE> _interpretationCode;
  /** Gets the property interpretationCode.
      @see org.hl7.rim.Observation#getInterpretationCode
  */
  public SET<CE> getInterpretationCode() { return _interpretationCode; }
  /** Sets the property interpretationCode.
      @see org.hl7.rim.Observation#setInterpretationCode
  */
  public void setInterpretationCode(SET<CE> interpretationCode) {
    if(interpretationCode instanceof org.hl7.hibernate.ClonableCollection)
      interpretationCode = ((org.hl7.hibernate.ClonableCollection<SET<CE>>) interpretationCode).cloneHibernateCollectionIfNecessary();
    _interpretationCode = interpretationCode;
  }

  private SET<CE> _methodCode;
  /** Gets the property methodCode.
      @see org.hl7.rim.Observation#getMethodCode
  */
  public SET<CE> getMethodCode() { return _methodCode; }
  /** Sets the property methodCode.
      @see org.hl7.rim.Observation#setMethodCode
  */
  public void setMethodCode(SET<CE> methodCode) {
    if(methodCode instanceof org.hl7.hibernate.ClonableCollection)
      methodCode = ((org.hl7.hibernate.ClonableCollection<SET<CE>>) methodCode).cloneHibernateCollectionIfNecessary();
    _methodCode = methodCode;
  }

  private SET<CD> _targetSiteCode;
  /** Gets the property targetSiteCode.
      @see org.hl7.rim.Observation#getTargetSiteCode
  */
  public SET<CD> getTargetSiteCode() { return _targetSiteCode; }
  /** Sets the property targetSiteCode.
      @see org.hl7.rim.Observation#setTargetSiteCode
  */
  public void setTargetSiteCode(SET<CD> targetSiteCode) {
    if(targetSiteCode instanceof org.hl7.hibernate.ClonableCollection)
      targetSiteCode = ((org.hl7.hibernate.ClonableCollection<SET<CD>>) targetSiteCode).cloneHibernateCollectionIfNecessary();
    _targetSiteCode = targetSiteCode;
  }
  public Object clone() {
    ObservationImpl that = (ObservationImpl) super.clone();
    return that;
  }
}
