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
 * The Initial Developer of the Original Code is Gunther Schadow.
 * Portions created by Initial Developer are Copyright (C) 2002-2005 
 * Health Level Seven, Inc. All Rights Reserved.
 *
 * Contributor(s): 
 */
package org.hl7.hpath;

import java.util.Iterator;

/** An iterator that passes through values from an previous iterator but makes sure that if the collection elements are themselves collections, they too would be unwrapped. */
/* package protected */ class UnwrappingIterator extends IteratorDecorator implements HPathIterator {
  private Iterator _expressionIterator;
  private Object _nextElement;
  private Object _newValue = null;
  private Object _source;
  private Object _originOfSource;
  private Property _propertyOfOriginWhoseValueWasSource; // FIXME: not set properly
  
  public UnwrappingIterator(Iterator previousIterator, Object collection) {
    super(previousIterator);    
    seek();
  }

	public boolean hasOnlySingleValue() {
		return super.hasOnlySingleValue() 
			&& (_expressionIterator instanceof HPathIterator) 
			&& ((HPathIterator)_expressionIterator).hasOnlySingleValue();
	}
    
  private void seek() {
    if(_expressionIterator != null && _expressionIterator.hasNext()) {
      _nextElement = _expressionIterator.next();
      return;
    } else {
      while(super.hasNext()) {
				_expressionIterator = Expression.contextIterator(super.next());
				if(_expressionIterator != null && _expressionIterator.hasNext()) {
					_nextElement = _expressionIterator.next();
					return;
				}
      }
    }
    _nextElement = null;
  }
  
  public boolean hasNext() {
    return _nextElement != null;
  }
  
  public Object next() {
    Object result = _nextElement;
    seek();
    return result;  
  } 

  public Object source() { return _source; } 

  public void replaceSourceInOrigin(Object newValue) {
    throw new UnsupportedOperationException();
  }    
}
