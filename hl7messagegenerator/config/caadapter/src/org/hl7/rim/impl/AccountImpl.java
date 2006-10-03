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

import org.hl7.rim.Account;
import org.hl7.rim.impl.ActImpl;
import org.hl7.types.MO;
import org.hl7.types.CE;
import org.hl7.types.RTO;
import org.hl7.types.MO;
import org.hl7.types.PQ;
import org.hl7.types.IVL;
import org.hl7.types.MO;

import /*org.hl7.rim.AssociationSet*/java.util.List;

/** Implementation of org.hl7.rim.Account as a simple data holder bean.
    @see org.hl7.rim.Account
  */
public class AccountImpl extends ActImpl implements Account {

  private MO _balanceAmt;
  /** Gets the property balanceAmt.
      @see org.hl7.rim.Account#getBalanceAmt
  */
  public MO getBalanceAmt() { return _balanceAmt; }
  /** Sets the property balanceAmt.
      @see org.hl7.rim.Account#setBalanceAmt
  */
  public void setBalanceAmt(MO balanceAmt) {
    if(balanceAmt instanceof org.hl7.hibernate.ClonableCollection)
      balanceAmt = ((org.hl7.hibernate.ClonableCollection<MO>) balanceAmt).cloneHibernateCollectionIfNecessary();
    _balanceAmt = balanceAmt;
  }

  private CE _currencyCode;
  /** Gets the property currencyCode.
      @see org.hl7.rim.Account#getCurrencyCode
  */
  public CE getCurrencyCode() { return _currencyCode; }
  /** Sets the property currencyCode.
      @see org.hl7.rim.Account#setCurrencyCode
  */
  public void setCurrencyCode(CE currencyCode) {
    if(currencyCode instanceof org.hl7.hibernate.ClonableCollection)
      currencyCode = ((org.hl7.hibernate.ClonableCollection<CE>) currencyCode).cloneHibernateCollectionIfNecessary();
    _currencyCode = currencyCode;
  }

  private RTO _interestRateQuantity;
  /** Gets the property interestRateQuantity.
      @see org.hl7.rim.Account#getInterestRateQuantity
  */
  public RTO getInterestRateQuantity() { return _interestRateQuantity; }
  /** Sets the property interestRateQuantity.
      @see org.hl7.rim.Account#setInterestRateQuantity
  */
  public void setInterestRateQuantity(RTO interestRateQuantity) {
    if(interestRateQuantity instanceof org.hl7.hibernate.ClonableCollection)
      interestRateQuantity = ((org.hl7.hibernate.ClonableCollection<RTO>) interestRateQuantity).cloneHibernateCollectionIfNecessary();
    _interestRateQuantity = interestRateQuantity;
  }

  private IVL<MO> _allowedBalanceQuantity;
  /** Gets the property allowedBalanceQuantity.
      @see org.hl7.rim.Account#getAllowedBalanceQuantity
  */
  public IVL<MO> getAllowedBalanceQuantity() { return _allowedBalanceQuantity; }
  /** Sets the property allowedBalanceQuantity.
      @see org.hl7.rim.Account#setAllowedBalanceQuantity
  */
  public void setAllowedBalanceQuantity(IVL<MO> allowedBalanceQuantity) {
    if(allowedBalanceQuantity instanceof org.hl7.hibernate.ClonableCollection)
      allowedBalanceQuantity = ((org.hl7.hibernate.ClonableCollection<IVL<MO>>) allowedBalanceQuantity).cloneHibernateCollectionIfNecessary();
    _allowedBalanceQuantity = allowedBalanceQuantity;
  }
  public Object clone() {
    AccountImpl that = (AccountImpl) super.clone();
    return that;
  }
}
