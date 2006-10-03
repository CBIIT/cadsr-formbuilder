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

import org.hl7.types.ANY;
import org.hl7.types.BL;
import org.hl7.types.CS;
import org.hl7.types.INT;
import org.hl7.types.ORD;
import org.hl7.types.PQ;
import org.hl7.types.PQR;
import org.hl7.types.QTY;
import org.hl7.types.REAL;
import org.hl7.types.SET;
import org.hl7.types.ValueFactory;
import org.regenstrief.ucum.Dimension;
import org.regenstrief.ucum.DimensionedQuantity;
import org.regenstrief.ucum.Unit;
import org.regenstrief.ucum.UnitSystem;

/** Implementation of PQ. */
public final class PQimpl extends ORDimpl implements PQ {
	
	/*
	 Internally delegates most operations to the canonical form but
	 keeps the original unit mostly for external representation and 
	 to respond to value and unit properties with the original value
	 and unit.
	 
	 NOTE: Most anything that operates on _canonicalForm.magnitude()
	 is most certainly wrong.
	 */
	
	private DimensionedQuantity _canonicalForm;
	private Unit _unit;
	
	static {
		UnitSystem.getInstance();
	}
	
	public String toString() {
		return value().toString() + " " + _unit.code();
	}
	
	private PQimpl(REAL magnitude, Unit unit) {
		super(null);
		if(magnitude==null)
			throw new IllegalArgumentException("magnitude must be non-null");
		if(unit==null)
			throw new IllegalArgumentException("unit must be non-null");
		if(!magnitude.nonNull().isTrue())
			throw new IllegalArgumentException("magnitude must be non-NULL");
		
		_unit = unit;
		// Special units don't allow this: this._canonicalForm = unit.times(magnitude);
		_canonicalForm = _unit.quantityOfMagnitude(magnitude);
	} 
	
	private PQimpl(DimensionedQuantity canonicalForm, Unit unit) {
		super(null);
		
		if(canonicalForm==null)
			throw new IllegalArgumentException("canonicalForm must be non-null");
		if(unit==null)
			throw new IllegalArgumentException("unit must be non-null");
		
		this._canonicalForm = canonicalForm;
		this._unit = unit;
	} 
	
	/** Constructs a units that is its own canonical form */
	private PQimpl(DimensionedQuantity canonicalForm) {
		this(canonicalForm, Unit.valueOf(canonicalForm.dimension().toUnitString()));
		this._canonicalPQ = this;
	} 
	
	/** Static factory method. */
	public static PQ valueOf(REAL magnitude, Unit unit) {
		return new PQimpl(magnitude, unit);
	}
	
	private static PQ valueOf(DimensionedQuantity canonicalForm, Unit unit) {
		return new PQimpl(canonicalForm, unit);
	}
	
	private static PQ valueOf(DimensionedQuantity canonicalForm) {
		return new PQimpl(canonicalForm);
	}
	
	/** Static factory method. */
	public static PQ valueOf(REAL magnitude, String unitString) {
		return new PQimpl(magnitude, Unit.valueOf(unitString));
	}

	/** Static factory method. */
	public static PQ valueOf(String magnitudeString, Unit unit) {
		return new PQimpl(ValueFactory.getInstance().REALvalueOfLiteral(magnitudeString), unit);
	}
	
	/** Static factory method. */
	public static PQ valueOf(String magnitudeString, String unitString) {
		if (unitString.equals("")) unitString="1";
		return new PQimpl(ValueFactory.getInstance().REALvalueOfLiteral(magnitudeString), Unit.valueOf(unitString));
	} 
	
	/* FIXME: need a valueOfLiteral too. */
	
	public BL equal(ANY that) {
		if(that instanceof PQ) {
			
			if(that instanceof PQimpl) {
				PQimpl thatPQimpl = (PQimpl)that;	 
				//System.out.println("###EQ: "+this+":"+_canonicalForm+" == "+that+":"+thatPQimpl._canonicalForm+ " --> " +this._canonicalForm.equals(thatPQimpl._canonicalForm));
				return BLimpl.valueOf(this._canonicalForm.equals(thatPQimpl._canonicalForm));

			} else if (that.isNull().isTrue())
				return BLimpl.FALSE; 
			else {
				throw new UnsupportedOperationException("argument is not a PQimpl: " + that);
			}
		} else if(that instanceof REAL) {
			return BLimpl.valueOf(this._canonicalForm.dimension().equals(Dimension.ZERO))
			.and(this._canonicalForm.magnitude().equal((REAL)that));
		} else return BLimpl.FALSE;    
	}
	
	public BL lessOrEqual(ORD that) {
		if(that instanceof PQ || that instanceof REAL) {
			if(that.isNullJ()) {
				if(that.nullFlavor().implies(NullFlavorImpl.PINF).isTrue())
					return BLimpl.TRUE;
				if(that.nullFlavor().implies(NullFlavorImpl.NINF).isTrue())
					return BLimpl.TRUE;
				return BLimpl.NA;	  
			} else if(that instanceof PQ) {
				if(that instanceof PQimpl) {
					PQimpl thatPQimpl = (PQimpl)that;	 
					return BLimpl.valueOf(this._canonicalForm.lessOrEqual(thatPQimpl._canonicalForm));
				} else {
					throw new UnsupportedOperationException("argument is not a PQimpl: " + that);
				}
			} else if(that instanceof REAL) {
				return 
				BLimpl.valueOf(this._canonicalForm.dimension().equals(Dimension.ZERO))
					.and(this._canonicalForm.magnitude().lessOrEqual((REAL)that));
			}
		}
		
		return BLimpl.FALSE;    
	}
	
	public BL compares(ORD that) { 
		if(that instanceof PQ) {
			if(that instanceof PQimpl) {
				PQimpl thatPQimpl = (PQimpl)that;	 
				return BLimpl.valueOf(this._canonicalForm.dimension()
						.equals(thatPQimpl._canonicalForm.dimension()));
			} else {
				throw new UnsupportedOperationException("argument is not a PQimpl: " + that);
			}
		} else if(that instanceof REAL) {
			return BLimpl.valueOf(this._canonicalForm.dimension().equals(Dimension.ZERO));
		} else return BLimpl.FALSE;    
	}
	
	public QTY plus(QTY.diff that) { 
		if(that instanceof PQ) {
			return plus((PQ)that);
		} else if(that instanceof REAL) {
			throw new UnsupportedOperationException("argument is not a PQ: " + that);
		} else return PQnull.NA;    
	}
	
	public PQ plus(PQ that) { 
		if(that instanceof PQ)
			if (that instanceof PQimpl) {
				PQimpl thatPQimpl = (PQimpl)that;
				if(this._canonicalForm.dimension()
						.equals(thatPQimpl._canonicalForm.dimension()))
					return valueOf(this._canonicalForm.plus(thatPQimpl._canonicalForm), this._unit);
				else
					return PQnull.NA; // FIXME: or exception??
			} 
			else if (that.isNull().isTrue())
				return that;
		
		// else
		throw new UnsupportedOperationException("argument is not a PQimpl: " + that);
	}
	
	public PQ minus(QTY that) { 
		if(that instanceof PQ) {
			return minus((PQ)that);
		} else if(that instanceof REAL) {
			throw new UnsupportedOperationException("argument is not a PQ: " + that);
		} else return PQnull.NA;    
	} 
	
	public PQ minus(QTY.diff that) { 
		if(that instanceof PQ) {
			return minus((PQ)that);
		} else if(that instanceof REAL) {
			throw new UnsupportedOperationException("argument is not a PQ: " + that);
		} else return PQnull.NA;    
	} 
	
	public PQ minus(PQ that) { 
		if(that instanceof PQ) {
			if (that.isNull().isFalse()) {
				PQimpl thatPQimpl = (PQimpl)that;
				if(that.isNull().isFalse() && this._canonicalForm.dimension().equals(thatPQimpl._canonicalForm.dimension())) 
					return valueOf(this._canonicalForm.minus(thatPQimpl._canonicalForm), this._unit);
			}
			return PQnull.NA; // FIXME: or exception??
		} else {
			throw new UnsupportedOperationException("argument is not a PQ: " + that);
		}
	} 
	
	public BL isZero() {
		return this._canonicalForm.magnitude().isZero();
	}
	public BL nonZero() {
		return this._canonicalForm.magnitude().nonZero();
	}
	
	public REAL value() {
		//System.out.println("#####" + _unit + "###" + _canonicalForm.magnitude() + "###" + _canonicalForm.magnitude().precision());
		//return _canonicalForm.magnitude().dividedBy(this._unit.magnitude());
		return _unit.magnitudeOf(_canonicalForm);
	}
	
	public REAL toREAL() {
		return _canonicalForm.toREAL();
	}
	
	public CS unit() {
		return this._unit;
	}
	
	private PQ _canonicalPQ = null;
	
	public PQ canonical() {
		if(this._canonicalPQ == null)
			this._canonicalPQ = valueOf(this._canonicalForm);
		return this._canonicalPQ;
	}
	
	public PQ negated() {
		return valueOf(this._canonicalForm.negated(), this._unit);
	}
	
	public PQ times(REAL that) {
		return valueOf(_canonicalForm.times(that), _unit);
	}
	
	public PQ times(PQ that) {
		if(that instanceof PQimpl) {
			PQimpl thatPQimpl = (PQimpl)that;
			return valueOf(this._canonicalForm.times(thatPQimpl._canonicalForm)); 
		} else {
			throw new UnsupportedOperationException("argument is not a PQimpl");
		}
	}
	public PQ inverted() {
		return valueOf(this._canonicalForm.inverted());
	}
	
	public PQ power(INT that) {
		return valueOf(this._canonicalForm.power(that));
	}
	
	public PQ dividedBy(PQ that) {
		return this.times(that.inverted());
	}
	
	public PQ dividedBy(REAL that) {
		return this.times(that.inverted());
	}
	
	public PQ expressedIn(PQ that) {
		if (_unit.equal(that.unit()).isTrue())
			return this;
		else if (this.compares(that).isTrue())
			if (that instanceof PQimpl) {
				PQimpl thatPQimpl = (PQimpl)that;
				return PQimpl.valueOf(thatPQimpl._unit.magnitudeOf(_canonicalForm), thatPQimpl._unit);

			} else
				throw new UnsupportedOperationException("argument is not a PQimpl: " + that);
		else 
			throw new IllegalArgumentException("argument is not of the same dimension: " + this + " : " + that);
	}
	
	public static void main(String args[])
	throws Exception
	{
		PQ pq = PQimpl.valueOf(ValueFactory.getInstance()
				.REALvalueOfLiteral(args[0]),args[1]);
		System.out.println(pq.toString()
				+ " " + pq.value()
				+ " " + pq.unit()
				+ " " + pq.unit().codeSystem()
				+ " " + pq.unit().codeSystemName()
				+ " " + pq.unit().codeSystemVersion()
				+ " " + ((Unit)pq.unit()).magnitude()
				+ " " + ((Unit)pq.unit()).dimension()
				+ " " + pq.canonical().value()
				+ " " + pq.canonical().unit());
	}
	
	public SET<PQR> translation() {
		return this._translation;
	}
	private SET<PQR> _translation = null;
	
	// FIXME: this should not be here! we are immutable!
	/** @deprecate: */ 
	public void addTranslation(PQR p){
		if(_translation==null)_translation=SETjuSetAdapter.valueOf(new ArrayList());
		ArrayList temp = new ArrayList();
		temp.add(p);
		_translation = _translation.union(SETjuSetAdapter.valueOf(temp));
	}

	public PQ epsilon() {
		return valueOf(REALdoubleAdapter.ONE, _unit);
	}
}
