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
* The Initial Developer of the Original Code is .
* Portions created by Initial Developer are Copyright (C) 2002-2004 
* Health Level Seven, Inc. All Rights Reserved.
*
* Contributor(s): 
*/
package org.hl7.types.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.hibernate.collection.PersistentCollection;
import org.hl7.hibernate.ClonableCollection;
import org.hl7.types.ANY;
import org.hl7.types.BL;
import org.hl7.types.INT;
import org.hl7.types.LIST;
import org.hl7.types.NullFlavor;

public class LISTjuListAdapter<T extends ANY>  extends ANYimpl implements LIST<T>, ClonableCollection<LIST<T>>
{
  List<T> _list = null;

  private LISTjuListAdapter() {
    super(NullFlavorImpl.NI);
  }

  private LISTjuListAdapter(NullFlavor nf) {
    super(nf);
  }

  public LISTjuListAdapter(Collection<T> data) {
    super(null);
    this._list= new ArrayList<T>(data);
  }

  protected LISTjuListAdapter(List<T> data, boolean makeCopy) throws IllegalArgumentException{
    super(null);

    if(data == null)
      throw new IllegalArgumentException();
    else if(makeCopy)
      this._list = new ArrayList<T>(data);
    else
      this._list = data;
  }

  public static LIST valueOf(Collection data) {
    if(data == null)
      return LISTnull.NI;
    else
      return new LISTjuListAdapter(data);
  }
  
  public T head() {
    return this._list.get(0);
  }

  /** The tail function is dangerous as it wastes objects if used for
      tail recursion. We need an interator instead. Iterators are
      defined for DISCRETE LISTS.
  */
  public LIST<T> tail() {
    return new LISTjuListAdapter<T>(this._list.subList(1,this._list.size()));
  }

  public BL isEmpty() {
    return BLimpl.valueOf(this._list.isEmpty());
  }

  public BL nonEmpty() {
    return BLimpl.valueOf(!this._list.isEmpty());
  }

  public INT length() {
    return INTlongAdapter.valueOf(this._list.size());
  }

  public List<T> toList() {
    return Collections.unmodifiableList(this._list);
  }

  public Iterator<T> iterator() {
    return this._list.listIterator();
  }

  public BL equal(ANY that) {
    if(this instanceof LIST) {
      Iterator<T> thissi = this.iterator();
      Iterator<T> thatti = ((LIST<T>)that).iterator();
      // unchecked assignment ignore this warning, nothing we can do about it

      while(thissi.hasNext() && thatti.hasNext()) {
        if(!thissi.next().equal(thatti.next()).isTrue()) {
          return BLimpl.FALSE;
        }
      }

      if(thissi.hasNext() || thatti.hasNext())
        return BLimpl.FALSE;
      else
        return BLimpl.TRUE;
    } else
      return BLimpl.FALSE;
  }

  /** For Hibernate, use with caution!
   *
   * @return a LIST wrapping the supplied List without checking or cloning
   * @deprecated noone but Hibernate should use this!
   */
  public static LIST _hibernateWrap(List list) {
    return new LISTjuListAdapter(list, false);
  }
  
  /** For Hibernate, use with caution!
   * 
   * @return the internal list without cloning.
   * @deprecated noone but Hibernate should use this!
   */
  public List<T> _hibernateUnwrap(){
    return _list;
  }

	public LIST<T> cloneHibernateCollectionIfNecessary() {
		if (this._list instanceof PersistentCollection)
			return new LISTjuListAdapter(this._list);
		else
			return this;
	}
}
