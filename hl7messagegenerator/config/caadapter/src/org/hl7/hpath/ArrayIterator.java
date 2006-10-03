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
import java.lang.reflect.Array;

/** A pseudo-iterator for an array, so we don't have to convert an array to a list first, which is actually pretty difficult. */
/*package protected*/ final class ArrayIterator implements HPathIterator {
  private final Object _array;
  private int _index;
  private final int _max;
  
  /** Used for the replaceSource method, without this, we cannot replace. */
  private Object _originOfArray;

  /** Used for the replaceSource method, without this, we cannot replace. */
  private Property _propertyOfOriginWhoseValueWasArray;

  public ArrayIterator(Object array) { 
    this(array, null, null);
  }

  public ArrayIterator(Object array, Object originOfArray, Property propertyOfOriginWhoseValueWasArray) { 
    if(array == null)
      throw new NullPointerException();
    else if(!array.getClass().isArray())
      throw new IllegalArgumentException("array expected");
    else {
      _array = array; 
      _max = Array.getLength(array);
    }

    _originOfArray = originOfArray;
    _propertyOfOriginWhoseValueWasArray = propertyOfOriginWhoseValueWasArray;
  }
  
	public boolean hasOnlySingleValue() {
		return false; // XXX: see we give a static answer.
	}

  public boolean hasNext() { return _index < _max; }
  
  public Object next() { 
    if(_index < _max) {
      return Array.get(_array,_index++);
    } else
      throw new NoSuchElementException();
  }
  
  public void remove() { throw new UnsupportedOperationException(); } 

  public Object source() { return _array; } 
}
