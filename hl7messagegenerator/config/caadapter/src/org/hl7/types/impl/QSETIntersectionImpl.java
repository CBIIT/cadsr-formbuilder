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
import java.util.Collections;
import java.util.List;

import org.hl7.types.ANY;
import org.hl7.types.BL;
import org.hl7.types.Criterion;
import org.hl7.types.INT;
import org.hl7.types.IVL;
import org.hl7.types.QSET;
import org.hl7.types.QTY;
import org.hl7.types.SET;
import org.hl7.types.TS;

/** Result of intersection with another set. Implemented as a Set
 of arguments combined as intersection.
 
 TODO: optimize contains operations by sorting the 'smallest'
 components first?
 */
public class QSETIntersectionImpl<T extends QTY> 
extends QSETTermBase<T> implements QSET<T> 
{
	private List<QSET<T>> _args;
	
	public List<QSET<T>> getArguments() { return _args; }
	
	public String toString() {
		StringBuffer sb = new StringBuffer("(");
		sb.append(_args.get(0));
		for(int i = 1; i < _args.size(); i++) {
			sb.append(" ");
			sb.append(_args.get(i));
		}
		sb.append(")");
		return sb.toString();
	}
	
	public static QSETIntersectionImpl valueOf(QSET one, QSET two) {
		return new QSETIntersectionImpl(one, two);
	}
	
	private QSETIntersectionImpl<T> cloneAndAppend(QSET<T> appendix) {
		return new QSETIntersectionImpl(this, appendix);
	}
	
	private QSETIntersectionImpl(QSET<T> one, QSET<T> two) {
		_args = new ArrayList<QSET<T>>(2);
		_args.add(one);
		_args.add(two);
	}
	
	private QSETIntersectionImpl(QSETIntersectionImpl<T> that, QSET<T> appendix) {
		_args = new ArrayList<QSET<T>>(that._args.size() + 1);
		Collections.copy(_args,that._args);
		_args.add(appendix);
	}
	
	public BL contains(T element) {
		// forall(QSET<T> s) where this.contains(s) {
		//   s.contains(element);
		// }
		
		for(int i = 0; i < _args.size(); i++)
			if(! _args.get(i).contains(element).isTrue())
				return BLimpl.FALSE;
		
		return BLimpl.TRUE;
		// TODO: what about null values from contains()?
	}
	
	public BL contains(SET<T> subset) {
		// forall(QSET<T> s) where this.contains(s) {
		//   s.contains(element);
		// }
		
		for(int i = 0; i < _args.size(); i++) {
			if(! _args.get(i).contains(subset).isTrue())
				return BLimpl.FALSE;
		}	
		return BLimpl.TRUE;
		// TODO: must make sure that overlapping and directly
		// connecting sets are fusioned together.
	}
	
	
	public QSET<T> intersection(QSET<T> otherset) {
		return cloneAndAppend(otherset);
	}
	
	public IVL<T> hull() {
		IVL<T> hull = _args.get(0).hull();
		for(int i = 1; i < _args.size(); i++)
			hull = hull.intersection(_args.get(i).hull());
		return hull;
	}
	
	public IVL<T> nextTo(T element) {
		
		IVL<T> next; IVL<T> newResult;
		IVL<T> result = _args.get(0).nextTo(element);
		
		loop:
			for(int i = 1; i < _args.size(); i++) {
				next=_args.get(i).nextTo(element);
				newResult=result.intersection(next);
				if (newResult.isNull().isTrue()) {
					if(next.high().lessOrEqual(result.high()).isTrue()) {
						next=_args.get(i).nextAfter(element);
						while(newResult.isNull().isTrue()) {
							result=_args.get(i-1).nextAfter(result.low());
							newResult=result.intersection(next);
							if (((TS)(result.low())).greaterOrEqual(((TS)(next.high()))).isTrue())
								return IVLnull.NA; // never intersecting
						}
					}
					else { // next high is after result high
						result=_args.get(i).nextAfter(element);
						while(newResult.isNull().isTrue()) {
							next=_args.get(i-1).nextAfter(next.low());
							newResult=result.intersection(next);
							if (((TS)(next.low())).greaterOrEqual(((TS)(result.high()))).isTrue())
								return IVLnull.NA; // never intersecting
						}
					}
					result=newResult;
					continue loop;
				}
				result=newResult;
			}
		return result;
	}
	
	public IVL<T> nextAfter(T element) {
		IVL<T> next; IVL<T> newResult;
		IVL<T> result = _args.get(0).nextAfter(element);
		loop:
			for(int i = 1; i < _args.size(); i++) {
				next=_args.get(i).nextAfter(element);
				newResult=result.intersection(next);
				if (newResult.isNull().isTrue()) {
					if(next.high().lessOrEqual(result.high()).isTrue()) {
						next=_args.get(i).nextAfter(element);
						while(newResult.isNull().isTrue()) {
							result=_args.get(i-1).nextAfter(result.low());
							newResult=result.intersection(next);
							if (((TS)(result.low())).greaterOrEqual(((TS)(next.high()))).isTrue())
								return IVLnull.NA; // never intersecting
						}
					}
					else { // next high is after result high
						result=_args.get(i).nextAfter(element);
						while(newResult.isNull().isTrue()) {
							next=_args.get(i-1).nextAfter(next.low());
							newResult=result.intersection(next);
							if (((TS)(next.low())).greaterOrEqual(((TS)(result.high()))).isTrue())
								return IVLnull.NA; // never intersecting
						}
					}
					result=newResult;
					continue loop;
				}
				result=newResult;
			}
		return result;
	}
	
	/*
	 // GS/MC: this is commented out because it is a particular challenging
	  // operation which has very little practical value besides being used
	   // in an assertion about the definition of the periodic hull.
	    
	    public BL interleaves(QSET<T> otherset) {
	    if (_args.size() <=0) return BLimpl.FALSE;
	    else if (_args.size() ==1) return _args.get(0).interleaves(otherset);
	    else {
	    for (int i=1;i<_args.size();i++) {
	    if (_args.get(i-1).intersection(_args.get(i)).isNull().isTrue())
	    return BLimpl.FALSE;
	    if (_args.get(i-1).intersection(_args.get(i)).interleaves(otherset).isFalse())
	    return BLimpl.FALSE;
	    }
	    return BLimpl.TRUE;
	    }
	    } */
	
	
	public QSET<T> getArgument(int i) {
		return _args.get(i);
	}
	
	public int size() {
		return _args.size();
	}
	
	/*    public QSET<T> getArgument(INT i) {
	 return _args.get(i.intValue());
	 }
	 
	 public INT size() {
	 return INTlongAdapter.valueOf(_args.size());
	 }
	 */
	
	public BL equal(ANY that) {
		// FIXME: there can be equivalence of two terms that look different!!
		if(that instanceof QSETIntersectionImpl) {
			QSETIntersectionImpl<T> thatIntersection = (QSETIntersectionImpl<T>)that;
			if (_args.size() != thatIntersection._args.size())
				return BLimpl.FALSE;
			BL result = BLimpl.TRUE;
			for(int i=0; i<_args.size(); i++) {
				result = result.and(_args.get(i).equal(thatIntersection._args.get(i)));
				if(result.isFalse())
					break;
			}
			return result;
		} else if (that instanceof QSET) {
			return BLimpl.FALSE;
		}	else 
			throw new UnsupportedOperationException("Need a QSET, got a " + that.getClass());
		// FIXME: there can be equivalence of two terms that look different!!
	}
	
	public INT cardinality() {
		throw new UnsupportedOperationException();
	}
	
	public BL isEmpty() {
		return this.nextTo((T)TSjuDateAdapter.valueOf("197001010000")).isNull();
		//throw new UnsupportedOperationException();
	}
	
	public T any() { 
		throw new UnsupportedOperationException();
	}
	
	public SET<T> select(Criterion c) {
		throw new UnsupportedOperationException();
	}
}
