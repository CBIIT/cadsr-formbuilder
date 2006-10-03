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

import org.hl7.types.ANY;
import org.hl7.types.BL;
import org.hl7.types.CS;
import org.hl7.types.INT;
import org.hl7.types.MO;
import org.hl7.types.ORD;
import org.hl7.types.QTY;
import org.hl7.types.REAL;

/**
 * Minimal implementation of MO as a model we can use for all other
 * type. This one is only used to convey NULL values. Again, we have
 * static values of it, so we don't create so many objects in a
 * running program.
 */
public final class MOimpl extends ORDimpl implements MO
{

	private REAL _value;
	private CS _currency;
	private MO _canonical;

	private MOimpl(REAL value, CS currency)
	{
		//NOTE: why does every instance of MOimpl has to be NullFlavorImpl.OTH?
		//Currently, commented it out and replace it with super(null);
		//for complex handling of null flavor, consider overriding the isNull() methods from ANYimpl class.
		//super(NullFlavorImpl.OTH);

		super(null);
		this._value = value;
		// Do we allow currencys to be null???
		if (currency == null)
		{
			this._currency = CSnull.NI;
		}
		else
		{
			this._currency = currency;
		}
	}

	/**
	 * Get the a null value according to the null flavor code.
	 */
	public static MO valueOf(REAL value, CS currency)
	{
		if (value == null)
		{
			return MOnull.NI;
		}
		else
		{
			return new MOimpl(value, currency);
		}
	}

	public BL equal(ANY x)
	{
		MOimpl that = null;
		if (x instanceof MOimpl)
		{
			that = (MOimpl) x;
			return (this._value.equal(that.value().doubleValue()).and(this._currency.equal(that.currency())));
		}
		else
		{
			throw new IllegalArgumentException();
		}
	}

	public int hashCode()
	{
		int result;
		result = (_value != null ? _value.hashCode() : 0);
		result = 29 * result + (_currency != null ? _currency.hashCode() : 0);
		result = 29 * result + (_canonical != null ? _canonical.hashCode() : 0);
		return result;
	}

	public BL equal(int x)
	{
		return BLimpl.valueOf(this._value.intValue() == x);
	}

	public BL equal(long x)
	{
		return BLimpl.valueOf((long) this._value.longValue() == x);
	}

	public BL equal(float x)
	{
		return BLimpl.valueOf((float) this._value.floatValue() == x);
	}

	public BL equal(double x)
	{
		return BLimpl.valueOf((double) this._value.doubleValue() == x);
	}

	public BL lessOrEqual(ORD x)
	{
		MOimpl that = null;
		if (x instanceof MOimpl)
		{
			that = (MOimpl) x;
			return _value.lessOrEqual(that.value());
		}
		else
		{
			throw new IllegalArgumentException();
		}
	}

	public BL compares(ORD x)
	{
		return _value.compares(x);
	}

	public QTY plus(QTY.diff x)
	{
		return _value.plus(x);
	}

	public MO plus(MO x)
	{
		MOimpl that = null;
		if (x instanceof MOimpl)
		{
			that = (MOimpl) x;
		}
		else
		{
			throw new IllegalArgumentException();
		}
		if (_currency.equal(x.currency()).isFalse())
		{
			throw new IllegalArgumentException();
		}
		return MOimpl.valueOf(_value.plus(that.value()), _currency);
	}

	public QTY.diff minus(QTY x)
	{
		MOimpl xx = null;
		if (x instanceof MOimpl)
		{
			xx = (MOimpl) x;
			return minus(xx);
		}
		else
		{
			throw new IllegalArgumentException();
		}
	}

	public QTY minus(QTY.diff x)
	{
		return minus((QTY) x);
	}

	public MO minus(MO x)
	{
		return MOimpl.valueOf(_value.minus(x.value()), _currency);
	}

	public BL isZero()
	{
		return _value.isZero();
	}

	public BL nonZero()
	{
		return isZero().not();
	}

	public REAL value()
	{
		return _value;
	}

	public CS currency()
	{
		return _currency;
	}

	public MO negated()
	{
		return MOimpl.valueOf(_value.negated(), _currency);
	}

	public MO times(REAL that)
	{
		return MOimpl.valueOf(_value.times(that), _currency);
	}

	public MO times(MO that)
	{
		return times(that.value());
	}

	public MO power(INT that)
	{
		return MOimpl.valueOf(_value.powerReverse(that.intValue(), INTnull.PINF), _currency);
	}

	public MO epsilon()
	{
		return valueOf(REALdoubleAdapter.ONE, this._currency);
	}
}
