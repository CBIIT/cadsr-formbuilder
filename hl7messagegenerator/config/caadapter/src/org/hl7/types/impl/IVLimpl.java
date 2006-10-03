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
 * Portions created by Initial Developer are Copyright (C) 2002-2004 
 * Health Level Seven, Inc. All Rights Reserved.
 *
 * Contributor(s): 
 */
package org.hl7.types.impl;

import java.util.Iterator;

import org.hl7.meta.Datatype;
import org.hl7.meta.impl.DatatypeMetadataFactoryDatatypes;
import org.hl7.types.ANY;
import org.hl7.types.BL;
import org.hl7.types.Criterion;
import org.hl7.types.INT;
import org.hl7.types.IVL;
import org.hl7.types.QSET;
import org.hl7.types.QTY;
import org.hl7.types.SET;
import org.hl7.types.ValueFactory;

public class IVLimpl<T extends QTY> extends ANYimpl implements IVL<T> {
	T _low = null;
	T _high = null;
	BL _lowClosed;
	BL _highClosed;
	T _center = null;
	QTY.diff _width = null;

	public String toString() {
		StringBuffer sb = new StringBuffer();
		if (_low != null && _low.nonNull().isTrue()) {
			if (_lowClosed.isTrue())
				sb.append("[");
			else
				sb.append("]");
			sb.append(_low.toString());
			sb.append(';');
			if (_high != null && _high.nonNull().isTrue())
				sb.append(_high.toString());
			if (_highClosed.isTrue())
				sb.append("]");
			else
				sb.append("[");

			return sb.toString();

		}
		else if (_center != null && _center.nonNull().isTrue()) {
			sb.append(_center.toString());
		}

		if (_lowClosed.isTrue())
			sb.append("[");
		else
			sb.append("]");

		if (_width != null && _width.nonNull().isTrue()) {
			sb.append(_width.toString());
		}

		if (_highClosed.isTrue())
			sb.append("[");
		else
			sb.append("]");

		return sb.toString();
	}

	/*
	 * NOTE: we have two forms low-high with boundaries and center-width. Either
	 * of these two forms must be fully specified, Java null references cannot be
	 * passed in. If specified in one form, the other fields are initially
	 * Java-null and filled in on demand.
	 * 
	 * The problem this solves is that we do not have to create typed NULL values
	 * from the parameter type T.
	 */

	/**
	 * Construct an interval in the low-high boundary form. No argument must
	 * contain Java null-references, although HL7 NULL values are allowed.
	 */

	// LOW_HIGH
	private IVLimpl(BL lowClosed, T low, T high, BL highClosed) {
		super(null);

		if (lowClosed == null || low == null || high == null || highClosed == null)
			throw new NullPointerException("none of the arguments may be null-pointers (but they can be NULL values)");
		if (low.isNull().or(high.isNull()).isTrue()) {
			if (low.nullFlavor().implies(NullFlavorImpl.PINF).isTrue())
				throw new IllegalArgumentException("low must not be +infinity");
			if (high.nullFlavor().implies(NullFlavorImpl.NINF).isTrue())
				throw new IllegalArgumentException("high must not be -infinity");
			// if(low.notApplicableJ())
			// throw new IllegalArgumentException("low must not be 'not applicable'");
			// if(high.notApplicableJ())
			// throw new IllegalArgumentException("high must not be 'not
			// applicable'");
		}
		else if (low.lessOrEqual(high).isFalse())
			throw new IllegalArgumentException("low must be less or equal to high");

		this._lowClosed = lowClosed;
		this._low = low;
		this._high = high;
		this._highClosed = highClosed;
	}

	// bl-qty-qty-bl

	/**
	 * Construct an interval in the center-width form. No argument must contain
	 * Java null-references, although HL7 NULL values are allowed. When a zero
	 * width is passed in and the boundaries are both open, that is an empty set.
	 */

	// qty-qty-bl-bl
	// CENTER_WIDTH
	private IVLimpl(T center, QTY.diff width, BL lowClosed, BL highClosed) {
		super(null);

		if (center == null || width == null || lowClosed == null || highClosed == null)
			throw new NullPointerException("none of the arguments may be null-pointers (but they can be NULL values)");
		
		try {
			center.plus(width);
		} catch(Exception ex) {
			throw new IllegalArgumentException(ex);
		}

		this._center = center;
		this._width = width;
		this._lowClosed = lowClosed;
		this._highClosed = highClosed;
	}

	// bl-bl-qty-qty

	// LOW_WIDTH
	private IVLimpl(BL lowClosed, BL highClosed, T low, QTY.diff width) {
		super(null);

		if (low == null || width == null || lowClosed == null || highClosed == null)
			throw new NullPointerException("none of the arguments may be null-pointers (but they can be NULL values)");

		try {
			low.plus(width);
		} catch(Exception ex) {
			throw new IllegalArgumentException(ex);
		}

		this._low = low;
		this._width = width;
		this._lowClosed = lowClosed;
		this._highClosed = highClosed;
	}

	// bl-qty-bl-qty

	// HIGH_WIDTH
	private IVLimpl(BL lowClosed, QTY.diff width, BL highClosed, T high) {
		super(null);

		if (high == null || width == null || lowClosed == null || highClosed == null)
			throw new NullPointerException("none of the arguments may be null-pointers (but they can be NULL values)");

		try {
			high.plus(width);
		} catch(Exception ex) {
			throw new IllegalArgumentException(ex);
		}

		this._high = high;
		this._width = width;
		this._lowClosed = lowClosed;
		this._highClosed = highClosed;
	}

	/**
	 * Must not pass in Java null refernces for any of the arguments, but can pass
	 * in HL7 NULL values.
	 */
	public static <T extends QTY> IVL<T> valueOf(BL lowClosed, T low, T high, BL highClosed) {
		return new IVLimpl<T>(lowClosed, low, high, highClosed);
	}

	/**
	 * Must not pass in Java null references for any of the arguments, but can
	 * pass in HL7 NULL values.
	 */
	public static <T extends QTY> IVL<T> valueOf(T center, QTY.diff width, BL lowClosed, BL highClosed) {
		return new IVLimpl<T>(center, width, lowClosed, highClosed);
	}

	/**
	 * Must not pass in Java null references for any of the arguments, but can
	 * pass in HL7 NULL values.
	 */
	public static <T extends QTY> IVL<T> valueOf(BL lowClosed, BL highClosed, T low, QTY.diff width) {
		return new IVLimpl<T>(lowClosed, highClosed, low, width);
	}

	/**
	 * Must not pass in Java null references for any of the arguments, but can
	 * pass in HL7 NULL values.
	 */
	public static <T extends QTY> IVL<T> valueOf(BL lowClosed, QTY.diff width, BL highClosed, T high) {
		return new IVLimpl<T>(lowClosed, width, highClosed, high);
	}

	public static <T extends QTY> IVL<T> valueOf(String literal, Datatype parameterType) {
		T low = null, high = null;
		BL lowClosed = null, highClosed = null;
		literal = literal.trim();
		char leftBracket = "[".charAt(0);
		char rightBracket = "]".charAt(0);
		int semiColonIndex = literal.indexOf(";");
		if (semiColonIndex < 0)
			throw new Error("invalid IVL literal: " + literal);
		char lowClosedChar = literal.charAt(0);
		char highClosedChar = literal.charAt(literal.length() - 1);
		String lowString = literal.substring(1, semiColonIndex);
		String highString = literal.substring(semiColonIndex + 1, literal.length() - 1);

		lowClosed = (leftBracket == lowClosedChar) ? BLimpl.TRUE : BLimpl.FALSE;
		highClosed = (rightBracket == highClosedChar) ? BLimpl.TRUE : BLimpl.FALSE;

		// need to do a bit more inspection on lowString/highString to see
		// what type of QTY is intended
		if (parameterType.equals(DatatypeMetadataFactoryDatatypes.instance().TSTYPE)) {
			low = (T) ValueFactory.getInstance().TSvalueOfLiteral(lowString);
			high = (T) ValueFactory.getInstance().TSvalueOfLiteral(highString);
		}
		else if (parameterType.equals(DatatypeMetadataFactoryDatatypes.instance().PQTYPE)) {
			low = (T) ValueFactory.getInstance().PQvalueOfLiteral(lowString);
			high = (T) ValueFactory.getInstance().PQvalueOfLiteral(highString);
		}
		else if (parameterType.equals(DatatypeMetadataFactoryDatatypes.instance().MOTYPE)) {
			low = (T) ValueFactory.getInstance().MOvalueOfLiteral(lowString);
			high = (T) ValueFactory.getInstance().MOvalueOfLiteral(highString);
		}
		else if (parameterType.equals(DatatypeMetadataFactoryDatatypes.instance().INTTYPE)) {
			low = (T) ValueFactory.getInstance().INTvalueOfLiteral(lowString);
			high = (T) ValueFactory.getInstance().INTvalueOfLiteral(highString);
		}
		else if (parameterType.equals(DatatypeMetadataFactoryDatatypes.instance().REALTYPE)) {
			low = (T) ValueFactory.getInstance().REALvalueOfLiteral(lowString);
			high = (T) ValueFactory.getInstance().REALvalueOfLiteral(highString);
		}
		else
			throw new Error("unknown parameterType: " + parameterType.getFullName());

		return new IVLimpl<T>(lowClosed, low, high, highClosed);
	}

	// this still fails in the high-width form

	public T low() {
		if (_low == null)
			if (_high != null)
				_low = (T) _high.minus(_width);
			else {
				_low = (T) _center.minus(_width.times(REALdoubleAdapter.HALF));
			}
		return this._low;
	}

	// this still fails in the low-width form

	public T high() {
		if (_high == null)
			if (_low != null)
				_high = (T) _low.plus(_width);
			else
				_high = (T) _center.plus(_width.times(REALdoubleAdapter.HALF));
		return this._high;
	}

	public BL lowClosed() {
		return this._lowClosed;
	}

	public BL highClosed() {
		return this._highClosed;
	}

	public QTY.diff width() {
		// Fixme: this implementation does not consider when IVL sub type is TS 
        if (this._width == null)
			// GS: Eric, this causes a problem, as width can return java null this way. Before none 
			// of these methods ever returned java null, and they should not. So, whatever this
			// was checking, you need to come up with some HL7 null flavor to return.
			//
			 if( (_high != null) && !_high.isNullJ() && (_low != null) && !_low.isNullJ()) {
				_width = _high.minus(_low);
             } else {
                _width =  QTYnull.NA;
             }
		return this._width;
	}

	// GS: Eric, notice this: according to the defined constructors, you have to have at least
	// two of the for parameters set with some value other than java null. It could 
	// be an HL7 null value, but has to be a value. So, I think this check up here and 
	// down there is a failing attempt at working around some other problem.

	public T center() {
		if (this._center == null)
			// GS: Eric, this causes a problem, as width can return java null this way. Before none 
			// of these methods ever returned java null, and they should not. So, whatever this
			// was checking, you need to come up with some HL7 null flavor to return.
			//
            // EC: According to abstract data type spec:
            // invariant(IVL<T> x)
            //  where x.low.isNull.or(x.high.isNull) {
            //  x.center.notApplicable;
            // }

             if ((_high != null) && !_high.isNullJ() && (_low != null) && !_low.isNullJ()) {

			    _center = (_low != null) ? (T) _low.plus(width().times(REALdoubleAdapter.HALF))
                                         : (T) _high.minus(width().times(REALdoubleAdapter.HALF));
             } else {
                _center = (T)QTYnull.NA;
             }

        return this._center;
	}

	public BL contains(T element) {
		return low().lessOrEqual(element).and(element.lessOrEqual(high())).and(low().equal(element).implies(lowClosed()))
				.and(high().equal(element).implies(highClosed()));
	}

	public BL isEmpty() {
		return width().isZero().and(lowClosed().and(highClosed()).not());
	}

	public BL nonEmpty() {
		return isEmpty().not();
	}

	public BL contains(SET<T> subset) {
		if (subset instanceof IVL) {
			IVL<T> thatIVL = (IVL<T>) subset;
			return contains(thatIVL.low()).and(contains(thatIVL.high()));
		}
		else if (subset instanceof QSET) {
			return contains(((QSET<T>) subset).hull());
		}
		else
			return BLimpl.FALSE;
	}

	/**
	 * If T is discrete we can take width to be the cardinality. But if T is
	 * continuous we can only distinguish a singleton 1 or PINF.
	 */
	public INT cardinality() {
		// the only discrete QTY is INT
		if (width() instanceof INT) {
			INT width = (INT) width();

			if (lowClosed().isNull().or(highClosed().isNull()).isTrue())
				return INTnull.UNK;
			else {
				int adjustment = -1;
				if (lowClosed().isTrue())
					adjustment++;
				if (highClosed().isTrue())
					adjustment++;

				return width.minus(INTlongAdapter.valueOf(adjustment));
			}
		}
		else {
			if (width().isZero().isTrue())
				if (lowClosed().and(highClosed()).isTrue())
					return INTlongAdapter.ONE;
				else
					return INTnull.UNK;
			else
				return INTnull.PINF;
		}
	}

	/*
	 * Combinations with other sets are tricky because their result may not be an
	 * interval. For the general case we need the QSET machinery in place where
	 * sets can be combined as algebraic terms built from intervals,
	 * singularities, periodic intervals, etc. The most general case will be quite
	 * difficult to support, so we will have to throw UnsupportedOperation every
	 * now and then.
	 */

	public QSET<T> union(SET<T> otherset) {
		if (otherset instanceof QSET)
			return union((QSET<T>) otherset);
		else
			throw new UnsupportedOperationException();
	}

	/**
	 * We can only handle the special case that the other set is an interval that
	 * overlaps or that the other set's convex hull is wholely contained in this
	 * interval.
	 */
	public QSET<T> union(QSET<T> otherset) {
		if (otherset instanceof IVL) {
			IVL<T> thatIVL = (IVL<T>) otherset;

			if (overlaps(thatIVL).isTrue()) { return hull(thatIVL); }

		}
		else if (contains(otherset.hull()).isTrue()) {
			return this;
		}
		else if (otherset.contains(this).isTrue()) { return otherset; }

		throw new UnsupportedOperationException();
	}

	/**
	 * We can only handle the case where element is not in the interval or the
	 * very special case of element being the closed boundary of the interval.
	 */
	public QSET<T> except(T element) {
		BL elementContained = contains(element);
		if (elementContained.isFalse())
			return this;
		else if (elementContained.isTrue()) {
			if (lowClosed().and(element.equal(low())).isTrue())
				return valueOf(BLimpl.FALSE, low(), high(), highClosed());
			else if (highClosed().and(element.equal(high())).isTrue())
				return valueOf(lowClosed(), low(), high(), BLimpl.FALSE);
		}
		throw new UnsupportedOperationException();
	}

	/**
	 * We can only handle the cases where (1) the two sets are disjunct, (2) the
	 * other set is an interval and the two sets overlap without being contained
	 * in each other; (3) the other set is a singleton in which case the except(T
	 * element) method takes over.
	 */
	public QSET<T> except(SET<T> otherset) {
		if (otherset instanceof QSET)
			return except((QSET<T>) otherset);
		else
			throw new UnsupportedOperationException();
	}

	public QSET<T> except(QSET<T> otherset) {
		if (otherset instanceof IVL) {
			IVL<T> thatIVL = (IVL<T>) otherset;

			BL overlaps = overlaps(thatIVL);

			if (overlaps.isFalse()) // disjunct, thank you
				return this;

			if (overlaps.isNullJ()) // unknown
				return QSETnull.UNK;

			T newLow = low();
			BL newLowClosed = lowClosed();

			if (thatIVL.contains(low()).isTrue()) {
				newLow = thatIVL.high();
				newLowClosed = thatIVL.highClosed().not();
			}

			T newHigh = high();
			BL newHighClosed = highClosed();

			if (thatIVL.contains(high()).isTrue()) {
				newHigh = thatIVL.low();
				newHighClosed = thatIVL.lowClosed().not();
			}

			if (newLow.lessOrEqual(newHigh).isTrue()) {
				return valueOf(newLowClosed, newLow, newHigh, newHighClosed);
			}
			else { // we are left with an empty set!
				return valueOf((T) center().plus(INTnull.NA), (QTY.diff) width().minus(width()), BLimpl.FALSE, BLimpl.FALSE);
			}
		}
		else {
			if (otherset.cardinality().isOne().isTrue())
				return except(otherset.hull().low());
		}

		throw new UnsupportedOperationException();
	}

	public QSET<T> intersection(SET<T> otherset) {
		throw new UnsupportedOperationException();
	}

	public QSET<T> intersection(QSET<T> otherset) {
		throw new UnsupportedOperationException();
	}

	public IVL<T> intersection(IVL<T> otherset) {
		T newLow = _low;
		BL newLowClosed = _lowClosed;
		T newHigh = _high;
		BL newHighClosed = _highClosed;

		if (this.equals(otherset))
			return this;
		// disjoint
		if (this.overlaps(otherset).isFalse())
			return IVLnull.NA;

		// there is some intersection
		if (this.contains(otherset.low()).isTrue()) {
			newLow = otherset.low();
			newLowClosed = otherset.lowClosed();
		}
		if (this.contains(otherset.high()).isTrue()) {
			newHigh = otherset.high();
			newHighClosed = otherset.highClosed();
		}
		return new IVLimpl(newLowClosed, newLow, newHigh, newHighClosed);
	}

	public IVL<T> hull(IVL<T> that) {
		T hullLow = _low;
		T hullHigh = _high;
		BL hullLowClosed = _lowClosed;
		BL hullHighClosed = _highClosed;

		if (that.low().lessOrEqual(this.low()).isTrue()) {
			hullLow = that.low();
			hullLowClosed = that.lowClosed();
		}
		if (that.high().greaterOrEqual(_high).isTrue()) {
			hullHigh = that.high();
			hullHighClosed = that.highClosed();
		}
		return new IVLimpl(hullLowClosed, hullLow, hullHigh, hullHighClosed);
	}

	/**
	 * Whether the two intervals overlap in any way, includes the cases that the
	 * case of subsets.
	 */
	public BL overlaps(IVL<T> that) {
		return contains(that.low()).or(contains(that.high())).or(that.contains(low())).or(that.contains(high()));
	}

	public IVL<T> hull() {
		return this;
	}

	public IVL<T> nextTo(T element) {
		// the only interval ever returned is this, and only if the
		// element point is in or before the interval, i.e., if
		// it is less than the high boundary (or equal if highClosed.)
		if (element.lessOrEqual(high()).isTrue())
			if (element.equal(high()).isTrue())
				if (highClosed().isFalse())
					return this;
				else
					return IVLnull.NA;
			else
				return this;
		else
			return IVLnull.NA;
	}

	/** By definition must return NULL/NA */
	public IVL<T> nextAfter(T element) {
		return IVLnull.NA;
	}

	public QSET<T> periodicHull(QSET<T> otherset) {
		return QSETnull.NA;
	}

	public BL interleaves(QSET<T> otherset) {
		return BLimpl.FALSE;
	}

	public BL equal(ANY that) {
		if (that.isNull().isTrue())
			return BLimpl.NI;
		if (that instanceof IVL) {
			IVL thatIVL = (IVL) that;
			return this.lowClosed().equal(thatIVL.lowClosed()).and(this.highClosed().equal(thatIVL.highClosed())).and(
					this.low().equal(thatIVL.low())).and(this.high().equal(thatIVL.high())).and(
					this.width().equal(thatIVL.width()));
		}
		else
			return BLimpl.FALSE;
	}

	public Iterator<ANY> iterator() {
		return new Iterator<ANY>() {
			private boolean _hasNext = true;

			public boolean hasNext() {
				return _hasNext;
			}

			public IVL<T> next() {
				if (_hasNext) {
					_hasNext = false;
					return IVLimpl.this;
				}
				else
					throw new java.util.NoSuchElementException();
			}

			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}

	public T any() {
		T nullResult = null;
		T result = null;

		if (_lowClosed.isTrue())
			result = _low;
		if (result != null) {
			if (result.nonNull().isTrue())
				return result;
			else
				nullResult = result;
		}

		if (_highClosed.isTrue())
			result = _high;
		if (result != null) {
			if (result.nonNull().isTrue())
				return result;
			else
				nullResult = result;
		}

		result = _center;
		if (result != null) {
			if (result.nonNull().isTrue())
				return result;
			else
				nullResult = result;
		}

		result = _low;
		if (result != null) {
			if (result.nonNull().isTrue())
				return (T) result.plus(result.epsilon());
			else
				nullResult = result;
		}

		result = _high;
		if (result != null) {
			if (result.nonNull().isTrue())
				return (T) result.minus(result.epsilon());
			else
				nullResult = result;
		}

		if (nullResult != null)
			return nullResult;
		else
			throw new java.util.NoSuchElementException();
	}

	public SET<T> select(Criterion c) {
		// this could be supported if we could analyze the criterion statically
		throw new UnsupportedOperationException();
	}
}

/**
 * HISTORY : $Log: not supported by cvs2svn $
 * HISTORY : Revision 1.53  2006/07/17 19:23:19  gschadow
 * HISTORY : import Cleanup
 * HISTORY :
 * HISTORY : Revision 1.52  2006/06/08 17:48:22  echen
 * HISTORY : Fix a couple of datatype mapping error
 * HISTORY :
 * HISTORY : Revision 1.51  2006/06/05 20:39:54  echen
 * HISTORY : Come up with HL7 null flavor QTYnull.NA for the width() and center
 * HISTORY :
 * HISTORY : Revision 1.50  2006/06/04 09:18:16  gschadow
 * HISTORY : I had to revert Eric's latest addition of an if... statement, in
 * HISTORY : width() and center(). These functions should never return java
 * HISTORY : null. I suspect that there is an upstream problem that causes
 * HISTORY : whatever Eric was trying to fix.
 * HISTORY :
 * HISTORY : Revision 1.49  2006/06/02 22:43:57  echen
 * HISTORY : Fix center method problem
 * HISTORY :
 * HISTORY : Revision 1.48  2006/05/15 22:29:23  gschadow
 * HISTORY : IVL checks if TS + width has a proper width (not kg)
 * HISTORY :
 * HISTORY : Revision 1.47  2006/04/28 22:46:02  gschadow
 * HISTORY : new epsilon()
 * HISTORY :
 * HISTORY : Revision 1.46  2006/04/24 22:10:48  gschadow
 * HISTORY : *** empty log message ***
 * HISTORY : HISTORY : Revision 1.45 2006/02/02 19:34:37
 * gschadow HISTORY : Added a case to the IVL.equal method. If 'that' IVL is
 * null then return BLimpl.NA HISTORY : HISTORY : Revision 1.44 2005/12/14
 * 22:02:59 gschadow HISTORY : Not sure what I did to these... HISTORY : HISTORY :
 * Revision 1.43 2005/12/08 18:03:52 gschadow HISTORY : more fixing of PIVLimpl
 * HISTORY : HISTORY : Revision 1.42 2005/12/06 12:16:05 gschadow HISTORY : ***
 * empty log message *** HISTORY : HISTORY : Revision 1.41 2005/12/05 20:41:05
 * gschadow HISTORY : *** empty log message *** HISTORY : HISTORY : Revision
 * 1.40 2005/12/01 05:15:12 gschadow HISTORY : introduce the DSET interface for
 * guaranteed discrete sets. This comes HISTORY : a little late, so it may cause
 * some ripples. Initial testing worked HISTORY : for me. HISTORY : HISTORY :
 * Revision 1.39 2005/11/15 16:02:39 echen HISTORY : add history comments
 * HISTORY : HISTORY : Revision 1.34 2005/11/14 20:36:54 gschadow HISTORY : ***
 * empty log message *** HISTORY :
 */
