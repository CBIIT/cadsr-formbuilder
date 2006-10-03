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

import java.util.Iterator;

import org.hl7.types.ANY;
import org.hl7.types.BIN;
import org.hl7.types.BL;
import org.hl7.types.CS;
import org.hl7.types.ED;
import org.hl7.types.INT;
import org.hl7.types.LIST;
import org.hl7.types.NullFlavor;
import org.hl7.types.ST;
import org.hl7.types.TEL;

/**
 * Minimal implementation of ST as a model we can use for all other
 * type. This one is only used to convey NULL values. Again, we have
 * static values of it, so we don't create so many objects in a
 * running program.
 */
public class STnull extends ANYimpl implements ST
{

	public static STnull NI = new STnull(NullFlavorImpl.NI);
	public static STnull NA = new STnull(NullFlavorImpl.NA);
	public static STnull UNK = new STnull(NullFlavorImpl.UNK);
	public static STnull NASK = new STnull(NullFlavorImpl.NASK);
	public static STnull ASKU = new STnull(NullFlavorImpl.ASKU);
	public static STnull NAV = new STnull(NullFlavorImpl.NAV);
	public static STnull OTH = new STnull(NullFlavorImpl.OTH);
	public static STnull PINF = new STnull(NullFlavorImpl.PINF);
	public static STnull NINF = new STnull(NullFlavorImpl.NINF);
	public static STnull MSK = new STnull(NullFlavorImpl.MSK);
	public static STnull NP = new STnull(NullFlavorImpl.NP);

	protected STnull(NullFlavor nf)
	{
		super(nf);
	}

	public static STnull valueOf(String nullFlavorString)
	{
		/** We first intern the argument because we'll compare it with
		 constants that are always interned.
		 */
		String nf = nullFlavorString.intern();
		if (NullFlavorImpl.sNI.equals(nf))
		{
			return NI;
		}
		else if (NullFlavorImpl.sNA.equals(nf))
		{
			return NA;
		}
		else if (NullFlavorImpl.sUNK.equals(nf))
		{
			return UNK;
		}
		else if (NullFlavorImpl.sNASK.equals(nf))
		{
			return NASK;
		}
		else if (NullFlavorImpl.sASKU.equals(nf))
		{
			return ASKU;
		}
		else if (NullFlavorImpl.sNAV.equals(nf))
		{
			return NAV;
		}
		else if (NullFlavorImpl.sOTH.equals(nf))
		{
			return OTH;
		}
		else if (NullFlavorImpl.sPINF.equals(nf))
		{
			return PINF;
		}
		else if (NullFlavorImpl.sNINF.equals(nf))
		{
			return NINF;
		}
		else if (NullFlavorImpl.sMSK.equals(nf))
		{
			return MSK;
		}
		else if (NullFlavorImpl.sNP.equals(nf))
		{
			return NP;
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
		if (that instanceof STnull)
		{
			STnull thatObj = (STnull) that;
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
		return BLimpl.NA;
	}

	public BL nonEmpty()
	{
		return BLimpl.NA;
	}

	public INT length()
	{
		return INTnull.NA;
	}

	// The ED interface

	public CS mediaType()
	{
		return CSnull.NA;
	}

	public CS charset()
	{
		return CSnull.NA;
	}

	public CS compression()
	{
		return CSnull.NA;
	}

	public CS language()
	{
		return CSnull.NA;
	}

	public TEL reference()
	{
		return TELnull.NA;
	}

	public BIN integrityCheck()
	{
		return BINnull.NA;
	}

	public CS integrityCheckAlgorithm()
	{
		return CSnull.NA;
	}

	public ED thumbnail()
	{
		return EDnull.NA;
	}
};
