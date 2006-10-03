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

import java.util.List;

import org.hl7.types.AD;
import org.hl7.types.ADXP;
import org.hl7.types.ANY;
import org.hl7.types.BL;
import org.hl7.types.CS;
import org.hl7.types.QSET;
import org.hl7.types.SET;
import org.hl7.types.ST;
import org.hl7.types.TS;

public class ADimpl extends LISTjuListAdapter<ADXP> implements AD
{
	private SET<CS> _use;
	private QSET<TS> _validTime;
	private BL _isNotOrdered;

	// GS: these hacks make me question my sanity
	private static final QSET<TS> QSET_NI = (QSET<TS>) (Object) QSETnull.NI;

	private ADimpl(List<ADXP> list, SET<CS> use, QSET<TS> validTime, BL isNotOrdered)
	{
		super(list);

		this._use = ((use == null)) ? SETnull.NI : use;
		this._validTime = (validTime == null) ? QSET_NI : validTime;
		this._isNotOrdered = (isNotOrdered == null) ? BLimpl.NI : isNotOrdered;
	}

	/**
	 * Get the a null value according to the null flavor code.
	 */
	public static AD valueOf(List<ADXP> list, SET<CS> use, QSET<TS> validTime, BL isNotOrdered)
	{
		if (list == null)
		{
			return ADnull.NI;
		}
		else
		{
			return new ADimpl(list, use, validTime, isNotOrdered);
		}
	}

	/**
	 * Get the a null value according to the null flavor code.
	 */
	public static AD valueOf(List<ADXP> list, SET<CS> use, QSET<TS> validTime)
	{
		return valueOf(list, use, validTime, BLimpl.NI);
	}

	public static AD valueOf(List<ADXP> list, SET<CS> use)
	{
		return valueOf(list, use, QSET_NI);
	}

	public static AD valueOf(List<ADXP> list, QSET<TS> validTime)
	{
		return valueOf(list, new SETnull<CS>(NullFlavorImpl.NI), validTime);
	}

	public static AD valueOf(List<ADXP> list)
	{
		return valueOf(list, new SETnull<CS>(NullFlavorImpl.NI), QSET_NI);
	}

	public BL equal(ANY x)
	{
		ADimpl that = null;
		if (x instanceof ADimpl)
		{
			that = (ADimpl) x;
			return (super.equal(that).
					and(this._use.equal(that.use())).
					and(this._validTime.equal(that.validTime())));
		}
		else
		{
			throw new IllegalArgumentException();
		}
	}

	public SET<CS> use()
	{
		return _use;
	}

	public QSET<TS> validTime()
	{
		return _validTime;
	}

	public BL isNotOrdered()
	{
		return _isNotOrdered;
	}

	public ST formatted()
	{
		throw new UnsupportedOperationException();
	}

	public int hashCode()
	{
		int result = 0;
		result = 29 * result + (_use != null ? _use.hashCode() : 0);
		result = 29 * result + (_validTime != null ? _validTime.hashCode() : 0);
		result = 29 * result + (_isNotOrdered != null ? _isNotOrdered.hashCode() : 0);
		return result;
	}
}
