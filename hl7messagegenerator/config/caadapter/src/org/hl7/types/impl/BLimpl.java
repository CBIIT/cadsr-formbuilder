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
import org.hl7.types.NullFlavor;

/**
 * BL
 */
public final class BLimpl extends ANYimpl implements BL
{
	public static final BLimpl TRUE = new BLimpl(true);
	public static final BLimpl FALSE = new BLimpl(false);

	public static BLimpl NI = new BLimpl(NullFlavorImpl.NI);
	public static BLimpl NA = new BLimpl(NullFlavorImpl.NA);
	public static BLimpl UNK = new BLimpl(NullFlavorImpl.UNK);
	public static BLimpl NASK = new BLimpl(NullFlavorImpl.NASK);
	public static BLimpl ASKU = new BLimpl(NullFlavorImpl.ASKU);
	public static BLimpl NAV = new BLimpl(NullFlavorImpl.NAV);
	public static BLimpl OTH = new BLimpl(NullFlavorImpl.OTH);

	private static final byte cFALSE = -1;
	private static final byte cNULL = 0;
	private static final byte cTRUE = 1;

	byte _value;

	private BLimpl(NullFlavor nf)
	{
		super(nf);
	}

	private BLimpl(boolean value)
	{
		super(null);
		if (value)
		{
			this._value = cTRUE;
		}
		else
		{
			this._value = cFALSE;
		}
	}

	public static BL valueOf(boolean x)
	{
		if (x)
		{
			return TRUE;
		}
		else
		{
			return FALSE;
		}
	}

	public static BL valueOf(String x)
	{
		if (x == null)
		{
			return NI;
		}
		String s = x.intern();
		if (s.trim().length()==0)
		{
			return NI;
		}
		if (s.toLowerCase().equals("true"))
		{
			return TRUE;
		}
		if (s.toLowerCase().equals("false"))
		{
			return FALSE;
		}

		/* These are NOT HL7 complient!
    if(s == "yes") return TRUE;
    if(s == "no") return FALSE;
    if(s == "1") return TRUE;
    if(s == "0") return FALSE;
    */

		throw new IllegalArgumentException("illegal symbol for BL value " + x);
	}

	public static BL nullValueOf(String nullFlavorString)
	{
		return valueOf(NullFlavorImpl.valueOf(nullFlavorString));
	}

	public static BL valueOf(NullFlavor x)
	{
		if (x.implies(NullFlavorImpl.NA).isTrue())
		{
			return BLimpl.NA;
		}
		if (x.implies(NullFlavorImpl.OTH).isTrue())
		{
			return BLimpl.OTH;
		}
		if (x.implies(NullFlavorImpl.NAV).isTrue())
		{
			return BLimpl.NAV;
		}
		if (x.implies(NullFlavorImpl.ASKU).isTrue())
		{
			return BLimpl.ASKU;
		}
		if (x.implies(NullFlavorImpl.NASK).isTrue())
		{
			return BLimpl.NASK;
		}
		if (x.implies(NullFlavorImpl.UNK).isTrue())
		{
			return BLimpl.UNK;
		}
		return BLimpl.NI;
	}

	public BL and(BL that)
	{
		switch (this._value)
		{
			case cTRUE:
				return that;
			case cFALSE:
				return FALSE;
			default:
			{
				if (that.nonNullJ())
				{
					return that;
				}
				else
				{
					//System.out.println("ARE WE HERE?? --> "+ that);
					//System.out.println("ARE WE HERE?? --> "+ this.nullFlavor().mostSpecificGeneralization(that.nullFlavor()));
					return valueOf(this.nullFlavor().mostSpecificGeneralization(that.nullFlavor()));
				}
			}
		}
	}

	public BL not()
	{
		switch (this._value)
		{
			case cTRUE:
				return FALSE;
			case cFALSE:
				return TRUE;
			default:
				return this;
		}
	}

	public BL or(BL that)
	{
		switch (this._value)
		{
			case cTRUE:
				return this;
			case cFALSE:
				return that;
			default:
			{
				if (that.nonNullJ())
				{
					return that;
				}
				else
				{
					return valueOf(this.nullFlavor().mostSpecificGeneralization(that.nullFlavor()));
				}
			}
		}
	}

	public BL eor(BL that)
	{
		return this.or(that).and(this.and(that).not());
	}

	public BL implies(BL that)
	{
		return this.not().or(that);
	}

	public BL equal(ANY that)
	{
		if (!(that instanceof BL))
		{
			return BLimpl.FALSE;
		}
		else
		{
			BL thatBL = (BL) that;

			// now I use the bit more costly method in order to promote
			// objects of this implementation to be used rather than
			// other implementations.

			if (thatBL.isTrue())
			{
				return this;
			}
			else if (thatBL.isFalse())
			{
				return this.not();
			}
			else if (this.isNullJ())
			{
				return valueOf(thatBL.nullFlavor().mostSpecificGeneralization(this.nullFlavor()));
			}
			else
			{
				return thatBL;
			}
		}
	}

	public boolean isTrue()
	{
		return this._value == cTRUE;
	}

	public boolean isFalse()
	{
		return this._value == cFALSE;
	}

	public String toString()
	{
		if (isTrue())
		{
			return "true";
		}
		else if (isFalse())
		{
			return "false";
		}
		else
		{
			return null;
		}
	}
}
