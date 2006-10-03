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

/**
 * User: Eric Chen
 * Date: Jan 30, 2004
 * Time: 4:34:45 PM
 * To change this template use Options | File Templates.
 */

import java.util.Iterator;

import org.hl7.types.ANY;
import org.hl7.types.BIN;
import org.hl7.types.BL;
import org.hl7.types.CE;
import org.hl7.types.CS;
import org.hl7.types.ED;
import org.hl7.types.INT;
import org.hl7.types.LIST;
import org.hl7.types.NullFlavor;
import org.hl7.types.SC;
import org.hl7.types.ST;
import org.hl7.types.TEL;


public final class SCnull extends ANYimpl implements SC
{
	public static SCnull NI = new SCnull(NullFlavorImpl.NI);
	public static SCnull NA = new SCnull(NullFlavorImpl.NA);
	public static SCnull UNK = new SCnull(NullFlavorImpl.UNK);
	public static SCnull NASK = new SCnull(NullFlavorImpl.NASK);
	public static SCnull ASKU = new SCnull(NullFlavorImpl.ASKU);
	public static SCnull NAV = new SCnull(NullFlavorImpl.NAV);
	public static SCnull OTH = new SCnull(NullFlavorImpl.OTH);
	public static SCnull PINF = new SCnull(NullFlavorImpl.PINF);
	public static SCnull NINF = new SCnull(NullFlavorImpl.NINF);

	private SCnull(NullFlavor nf)
	{
		super(nf);
	}

	/**
	 * Get the a null value according to the null flavor code.
	 */
	public static SCnull valueOf(String nullFlavorString)
	{
		/** We first intern the argument because we'll compare it with
		 constants that are always interned.
		 */
		String nf = nullFlavorString.intern();
		if (nf == NullFlavorImpl.sNI)
		{
			return NI;
		}
		else if (nf == NullFlavorImpl.sNA)
		{
			return NA;
		}
		else if (nf == NullFlavorImpl.sUNK)
		{
			return UNK;
		}
		else if (nf == NullFlavorImpl.sNASK)
		{
			return NASK;
		}
		else if (nf == NullFlavorImpl.sASKU)
		{
			return ASKU;
		}
		else if (nf == NullFlavorImpl.sNAV)
		{
			return NAV;
		}
		else if (nf == NullFlavorImpl.sOTH)
		{
			return OTH;
		}
		else
		{
			throw new IllegalArgumentException("null flavor " + nf);
		}
	}

	/** FIXME: is NA correct or should it be derived from this and that? */
	//  public BL equal(ANY that) { return BLimpl.NA; }

	/**
	 * the equal will tell whether the given ANY is equal to this object.
	 *
	 * @param that
	 * @return BLimpl.TRUE if that is equal to this object.
	 */
	public BL equal(ANY that)
	{
		/**
		 * Designed by S. Jiang:
		 * The contract of equal shall be carried no matter the semantics value of the class itself.
		 */
		if (that instanceof SCnull)
		{
			SCnull thatObj = (SCnull) that;
			return thatObj.nullFlavor().equal(this.nullFlavor());
		}
		else
		{
			return BLimpl.FALSE;
		}
	}

	//equals() method has been overriden in ANYimpl class

	/**
	 * override hashCode() method.
	 *
	 * @return hashCode
	 */
	public int hashCode()
	{
		return this.nullFlavor().hashCode();
	}

	public ST headST()
	{
		throw new UnsupportedOperationException();
	}

	public ST tailST()
	{
		throw new UnsupportedOperationException();
	}

	public Iterator<ST> listSTIterator()
	{
		throw new UnsupportedOperationException();
	}


	public BL head()
	{
		throw new UnsupportedOperationException();
	}

	public LIST<BL> tail()
	{
		throw new UnsupportedOperationException();
	}

	public Iterator<BL> iterator()
	{
		throw new UnsupportedOperationException();
	}


	public BL isEmpty()
	{
		throw new UnsupportedOperationException();
		// return BLimpl.NA;
	}

	public BL nonEmpty()
	{
		throw new UnsupportedOperationException();
		// return BLimpl.NA;
	}

	public INT length()
	{
		throw new UnsupportedOperationException();
		// return BLimpl.NA;
	}

	// The ED interface

	public CS mediaType()
	{
		throw new UnsupportedOperationException();
		// return CSnull.NA;
	}

	public CS charset()
	{
		throw new UnsupportedOperationException();
		// return CSnull.NA;
	}

	public CS compression()
	{
		throw new UnsupportedOperationException();
		// return CSnull.NA;
	}

	public CS language()
	{
		throw new UnsupportedOperationException();
		// return CSnull.NA;
	}

	public TEL reference()
	{
		throw new UnsupportedOperationException();
		// return TELnull.NA;
	}

	public BIN integrityCheck()
	{
		throw new UnsupportedOperationException();
		// return BINnull.NA;
	}

	public CS integrityCheckAlgorithm()
	{
		throw new UnsupportedOperationException();
		// return CSnull.NA;
	}

	public ED thumbnail()
	{
		throw new UnsupportedOperationException();
		// return EDnull.NA;
	}

	public CE code()
	{
		return CEnull.NA;
	}
};
