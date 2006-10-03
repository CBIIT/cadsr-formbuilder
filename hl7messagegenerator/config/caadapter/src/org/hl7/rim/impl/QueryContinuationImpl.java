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

import org.hl7.rim.QueryContinuation;
import org.hl7.rim.impl.QueryEventImpl;
import org.hl7.types.INT;

import /*org.hl7.rim.AssociationSet*/java.util.List;

/** Implementation of org.hl7.rim.QueryContinuation as a simple data holder bean.
    @see org.hl7.rim.QueryContinuation
  */
public class QueryContinuationImpl extends QueryEventImpl implements QueryContinuation {

  private INT _startResultNumber;
  /** Gets the property startResultNumber.
      @see org.hl7.rim.QueryContinuation#getStartResultNumber
  */
  public INT getStartResultNumber() { return _startResultNumber; }
  /** Sets the property startResultNumber.
      @see org.hl7.rim.QueryContinuation#setStartResultNumber
  */
  public void setStartResultNumber(INT startResultNumber) {
    if(startResultNumber instanceof org.hl7.hibernate.ClonableCollection)
      startResultNumber = ((org.hl7.hibernate.ClonableCollection<INT>) startResultNumber).cloneHibernateCollectionIfNecessary();
    _startResultNumber = startResultNumber;
  }

  private INT _continuationQuantity;
  /** Gets the property continuationQuantity.
      @see org.hl7.rim.QueryContinuation#getContinuationQuantity
  */
  public INT getContinuationQuantity() { return _continuationQuantity; }
  /** Sets the property continuationQuantity.
      @see org.hl7.rim.QueryContinuation#setContinuationQuantity
  */
  public void setContinuationQuantity(INT continuationQuantity) {
    if(continuationQuantity instanceof org.hl7.hibernate.ClonableCollection)
      continuationQuantity = ((org.hl7.hibernate.ClonableCollection<INT>) continuationQuantity).cloneHibernateCollectionIfNecessary();
    _continuationQuantity = continuationQuantity;
  }
  public Object clone() {
    QueryContinuationImpl that = (QueryContinuationImpl) super.clone();
    return that;
  }
}
