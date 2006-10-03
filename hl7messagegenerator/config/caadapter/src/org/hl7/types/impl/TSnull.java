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

import java.util.Date;

import org.hl7.types.ANY;
import org.hl7.types.BL;
import org.hl7.types.CS;
import org.hl7.types.INT;
import org.hl7.types.NullFlavor;
import org.hl7.types.ORD;
import org.hl7.types.PQ;
import org.hl7.types.QTY;
import org.hl7.types.TS;

/** Minimal implementation of TS as a model we can use for all other
    type. This one is only used to convey NULL values. Again, we have
    static values of it, so we don't create so many objects in a
    running program.
*/
public final class TSnull extends ORDimpl implements TS {
  public static TSnull NI   = new TSnull(NullFlavorImpl.NI);
  public static TSnull NA   = new TSnull(NullFlavorImpl.NA);
  public static TSnull UNK  = new TSnull(NullFlavorImpl.UNK);
  public static TSnull NASK = new TSnull(NullFlavorImpl.NASK);
  public static TSnull ASKU = new TSnull(NullFlavorImpl.ASKU);
  public static TSnull NAV  = new TSnull(NullFlavorImpl.NAV);
  public static TSnull OTH  = new TSnull(NullFlavorImpl.OTH);
  public static TSnull PINF = new TSnull(NullFlavorImpl.PINF);
  public static TSnull NINF = new TSnull(NullFlavorImpl.NINF);

  private TSnull(NullFlavor nf) {
    super(nf);
  }

  /** Get the a null value according to the null flavor code.
   */
  public static TSnull valueOf(String nullFlavorString) {
    /** We first intern the argument because we'll compare it with
	constants that are always interned.
    */
    String nf = nullFlavorString.intern();
    if(nf == NullFlavorImpl.sNI) return NI;
    else if(nf == NullFlavorImpl.sNA) return NA;
    else if(nf == NullFlavorImpl.sUNK) return UNK;
    else if(nf == NullFlavorImpl.sNASK) return NASK;
    else if(nf == NullFlavorImpl.sASKU) return ASKU;
    else if(nf == NullFlavorImpl.sNAV) return NAV;
    else if(nf == NullFlavorImpl.sOTH) return OTH;
    else if(nf == NullFlavorImpl.sPINF) return PINF;
    else if(nf == NullFlavorImpl.sNINF) return NINF;
    else throw new IllegalArgumentException("null flavor " + nf);
  }
  
  public Date toDate() { return null; }

  /** FIXME: is NA correct or should it be derived from this and that?

      FIXME: infinities may need to be treated differently!
   */
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
		if (that instanceof TSnull)
		{
			TSnull thatObj = (TSnull) that;
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


	// FIXME: we're returning NAs here. Shouldn't we return a null
  // that is this flavor or a flavor that's a result of the
  // combination of this and that?

  // FIXME: infinities can be compared!

  public BL lessOrEqual(ORD x) { return BLimpl.NA; }
  public BL lessOrEqual(int x) { return BLimpl.NA; }
  public BL lessOrEqual(long x) { return BLimpl.NA; }
  public BL lessOrEqual(float x) { return BLimpl.NA; }
  public BL lessOrEqual(double x) { return BLimpl.NA; }

  public BL compares(ORD x) { return BLimpl.NA; }
  public BL compares(long x) { return BLimpl.TRUE; }

  public QTY plus(QTY.diff x) { return TSnull.NA; }
  public TS plus(PQ x) { return TSnull.NA; }
  public PQ minus(QTY x) { return PQnull.NA; } // throw new UnsupportedOperationException();
  public TS minus(QTY.diff x) { return TSnull.NA; }
  public PQ minus(TS x) { return PQnull.NA; }  // throw new UnsupportedOperationException();

  public PQ offset() { return PQnull.NA; } // throw new UnsupportedOperationException();
  public CS calendar() {
    return CSnull.NA;
  }
  public INT precision() {
    return INTnull.NA;
  }
  public PQ timezone() {
    throw new UnsupportedOperationException();
  }

	public PQ epsilon() {
		return TSjuDateAdapter.MILLISECOND;
	}
  
}
