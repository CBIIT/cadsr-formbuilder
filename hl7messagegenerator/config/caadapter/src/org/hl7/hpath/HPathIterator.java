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

/** A common interface extending the standard java.util.Interator interface as a uniform result of the evaluation of HPath Expressions.
    Most uses of HPath can treat the result of HPath Expression evaluation as normal java Iterator. However, there are needs to access the collection that is at the basis of the Iterator, including accessing the properties of an underlying collection as a whole at any time when its items are being enumerated. This requirement exists by the HPath language itself. 

    <p>Note that by "collection" we do not only mean instances of java.util.Collection, but generally any class that has a property iterator() returning a java.util.Iterator. We call this property "source" because in the case that single result values are wrapped in an Iterator, that source is the same as the single next() value returned from the Iterator.

    <p>Other usages of this HPathIterator are in support of updating needs. The source, if the source is a "collection" can be used to insert new elements or remove others, if that is appropriate for the given kind of collection. This interface will also provides a way to replace the source "collection" or single value in the originating property to an alltogether new value. While the HPath language itself has no explicit features to impose side effects (outside of the unknown effects of method invocations), this implementation of HPath understands that legitimate usage of a navigation and query expression is to make changes.

    @author Gunther Schadow
    @version $Id: HPathIterator.java,v 1.1 2006-10-03 17:38:44 marwahah Exp $
*/
public interface HPathIterator extends Iterator {	
	/** Returns true if this iterator holds only a single value, based on this a caller may decide to unwrap the single result and not use the iterator any further.*/
	boolean hasOnlySingleValue();

  /** The source object underlying this Iterator. This may be a "collection" or a single object. By "collection" we mean not only an instance of java.util.Collection, but more generally, may be any class that has a property iterator() returning a java.util.Iterator. The source may also be a single object, in which case this iterator is probably one that simply wraps that same single object as the only element ever returned.

  <p>Note that if the source is a "collection" this Iterator may not iterate over the entire collection but may only enumerate a subset of it. 
  */
  public Object source();
}
