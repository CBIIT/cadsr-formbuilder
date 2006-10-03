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
import org.hl7.types.BAG;
import org.hl7.types.BL;
import org.hl7.types.INT;
import org.hl7.types.NullFlavor;

public class BAGjuListAdapter<T extends ANY> extends ANYimpl implements BAG<T>, ClonableCollection<BAG<T>> {
	List<T> _list = null;
	
	private BAGjuListAdapter() {
		super(NullFlavorImpl.NI);
	}
	
	private BAGjuListAdapter(NullFlavor nf) {
		super(nf);
	}
	
	public BAGjuListAdapter(Collection<T> data) {
		super(null);
		this._list= new ArrayList<T>(data);
	}
	
	public static BAG valueOf(Collection data) {
		if(data == null)
			return BAGnull.NI;
		else
			return new BAGjuListAdapter(data);
	}
	
	protected BAGjuListAdapter(List<T> data, boolean makeCopy) throws IllegalArgumentException {
		super(null);
		
		if(data == null)
			throw new IllegalArgumentException();
		else if(makeCopy)
			this._list = new ArrayList<T>(data);
		else
			this._list = data;
	}
	
	public BL isEmpty() {
		return BLimpl.valueOf(this._list.isEmpty());
	}
	
	public BL nonEmpty() {
		return BLimpl.valueOf(!this._list.isEmpty());
	}
	
	public INT size() {
		return INTlongAdapter.valueOf(this._list.size());
	}
	
	public BL contains(T kind) {
		return BLimpl.valueOf(_list.contains(kind));
	}
	
	public BAG<T>  plus(BAG<T> x) {
		throw new UnsupportedOperationException();
	}
	
	public BAG<T>  minus(BAG<T> x) {
		throw new UnsupportedOperationException();
	}
	
	public List<T> toList() {
		return Collections.unmodifiableList(this._list);
	}
	
	public Iterator<ANY> iterator() {
		return (Iterator<ANY>)this._list.listIterator();
	}
	
	public BL equal(ANY that) {
		throw new UnsupportedOperationException();
	}
	/** For Hibernate, use with caution!
	 *
	 * @return a BAG wrapping the supplied bag without checking or cloning
	 * @deprecated noone but Hibernate should use this!
	 */
	public static BAG _hibernateWrap(List list) 
	{
		return new BAGjuListAdapter(list,false);
	}
	
	/** For Hibernate, use with caution!
	 * 
	 * @return the internal list without cloning.
	 * @deprecated noone but Hibernate should use this!
	 */
	public List<T> _hibernateUnwrap()
	{
		return _list;
	}

	public BAG<T> cloneHibernateCollectionIfNecessary() {
		if (this._list instanceof PersistentCollection)
			return new BAGjuListAdapter(this._list);
		else
			return this;
	}
}
