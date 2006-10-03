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

/** A decorator for Iterators that we can use to add special behavior
    to java.util.Iterators. If not otherwise extended it passes
    through values from a parent iterator. When extended, it can be
    used to build chains or pipelines of iterators, which can filter
    or generate values based on the output of the parent iterator.

      @author Gunther Schadow
      @version $Id: IteratorDecorator.java,v 1.1 2006-10-03 17:38:45 marwahah Exp $
*/
/*package protected*/ class IteratorDecorator implements Iterator {
  protected Iterator _parentIterator;

  public Iterator parentIterator() {
    return _parentIterator;
  }

	public boolean hasOnlySingleValue() {
		return (_parentIterator instanceof HPathIterator) && ((HPathIterator)_parentIterator).hasOnlySingleValue();
	}

  public IteratorDecorator(Iterator parentIterator) {
    _parentIterator = parentIterator;
  }

  public IteratorDecorator(HPathIterator parentIterator) {
    _parentIterator = parentIterator;
  }

  public boolean hasNext() {
    return _parentIterator.hasNext();
  }

  public Object next() {
    return _parentIterator.next();
  }
  
  public void remove() { throw new UnsupportedOperationException(); } 
}
