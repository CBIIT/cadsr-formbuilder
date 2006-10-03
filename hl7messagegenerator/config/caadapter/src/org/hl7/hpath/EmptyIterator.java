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

/** A pseudo-iterator for no object at all, use this instead of dealing with Java nulls. */
/*package protected*/ public final class EmptyIterator implements HPathIterator {
	
	/*package*/public static final EmptyIterator INSTANCE = new EmptyIterator();

  /** Used for the replaceSource method, without this, we cannot replace. */
  private Object _originOfSource;

  /** Used for the replaceSource method, without this, we cannot replace. */
  private Property _propertyOfOriginWhoseValueWasSource;

	public boolean hasOnlySingleValue() {
		return true; // FIXME: method should be cardinality 0 1 or many
	}
	
  /*package*/ EmptyIterator() { 
    this(null, null);
  }
	
  /*package*/ EmptyIterator(Object originOfSource, Property propertyOfOriginWhoseValueWasSource) { 
    _originOfSource = originOfSource;
    _propertyOfOriginWhoseValueWasSource = propertyOfOriginWhoseValueWasSource;
  }
  
  public boolean hasNext() { return false; }
  
  public Object next() { throw new NoSuchElementException(); }
  
  public void remove() { throw new UnsupportedOperationException(); } 

  public Object source() { return null; }
}
