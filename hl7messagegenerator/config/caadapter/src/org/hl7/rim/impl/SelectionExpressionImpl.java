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

import org.hl7.rim.SelectionExpression;
import org.hl7.rim.impl.InfrastructureRootImpl;

import org.hl7.rim.LogicalExpression;
import org.hl7.rim.QueryBySelection;
import /*org.hl7.rim.AssociationSet*/java.util.List;

/** Implementation of org.hl7.rim.SelectionExpression as a simple data holder bean.
    @see org.hl7.rim.SelectionExpression
  */
public abstract class SelectionExpressionImpl extends InfrastructureRootImpl implements SelectionExpression {

  private LogicalExpression _leftSide;
  /** Gets the property leftSide.
      @see org.hl7.rim.SelectionExpression#getLeftSide
  */
  public LogicalExpression getLeftSide() {
    return _leftSide;
  }
  /** Sets the property leftSide.
      @see org.hl7.rim.SelectionExpression#setLeftSide
  */
  public void setLeftSide(LogicalExpression leftSide) {
    _leftSide = leftSide;
  }

  private QueryBySelection _queryBySelection;
  /** Gets the property queryBySelection.
      @see org.hl7.rim.SelectionExpression#getQueryBySelection
  */
  public QueryBySelection getQueryBySelection() {
    return _queryBySelection;
  }
  /** Sets the property queryBySelection.
      @see org.hl7.rim.SelectionExpression#setQueryBySelection
  */
  public void setQueryBySelection(QueryBySelection queryBySelection) {
    _queryBySelection = queryBySelection;
  }

  private LogicalExpression _rightSide;
  /** Gets the property rightSide.
      @see org.hl7.rim.SelectionExpression#getRightSide
  */
  public LogicalExpression getRightSide() {
    return _rightSide;
  }
  /** Sets the property rightSide.
      @see org.hl7.rim.SelectionExpression#setRightSide
  */
  public void setRightSide(LogicalExpression rightSide) {
    _rightSide = rightSide;
  }
  public Object clone() {
    SelectionExpressionImpl that = (SelectionExpressionImpl) super.clone();
    return that;
  }
}
