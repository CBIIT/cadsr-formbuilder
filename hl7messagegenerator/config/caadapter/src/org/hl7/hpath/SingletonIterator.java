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
import java.util.NoSuchElementException;

/** A pseudo-iterator for a single object. */
/*package protected*/ public final class SingletonIterator implements HPathIterator {
  /** The simple value wrapped in this Iterator */
  private Object _source;

  /** Used for the replaceSource method, without this, we cannot replace. */
  private Object _originOfSource;

  /** Used for the replaceSource method, without this, we cannot replace. */
  private Property _propertyOfOriginWhoseValueWasSource;

	public boolean hasOnlySingleValue() {
		return true;
	}

  public SingletonIterator(Object source) { 
    this(source, null, null);
  }

  public SingletonIterator(Object source, Object originOfSource, Property propertyOfOriginWhoseValueWasSource) { 
    if(source == null)
      throw new NullPointerException();
    else 
      _source = source; 

    _originOfSource = originOfSource;
    _propertyOfOriginWhoseValueWasSource = propertyOfOriginWhoseValueWasSource;
  }
  
  public boolean hasNext() { return _source != null; }
  
  public Object next() { 
    if(_source != null) {
      Object result = _source;
      _source = null;
      return result;
    } else
      throw new NoSuchElementException();
  }
  
  public void remove() { throw new UnsupportedOperationException(); } 

  public Object source() { return _source; }
}
