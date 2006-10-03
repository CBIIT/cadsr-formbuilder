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
import org.hl7.types.INT;
import org.hl7.types.NullFlavor;
import org.hl7.types.Numeric;
import org.hl7.types.ORD;
import org.hl7.types.QTY;
import org.hl7.types.REAL;

/** Minimal implementation of INT as a model we can use for all other
    type. This one is only used to convey NULL values. Again, we have
    static values of it, so we don't create so many objects in a
    running program.
*/
public class INTnull extends ORDimpl implements INT, Numeric  {
  public static INTnull NI   = new INTnull(NullFlavorImpl.NI);
  public static INTnull NA   = new INTnull(NullFlavorImpl.NA);
  public static INTnull UNK  = new INTnull(NullFlavorImpl.UNK);
  public static INTnull NASK = new INTnull(NullFlavorImpl.NASK);
  public static INTnull ASKU = new INTnull(NullFlavorImpl.ASKU);
  public static INTnull NAV  = new INTnull(NullFlavorImpl.NAV);
  public static INTnull OTH  = new INTnull(NullFlavorImpl.OTH);
  public static INTnull PINF = INTpinf.VALUE;
  public static INTnull NINF = INTninf.VALUE;

  protected INTnull(NullFlavor nf) {
    super(nf);
  }

  /** Get the a null value according to the null flavor code.
   */
  public static INTnull valueOf(String nullFlavorString) {
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
    else throw new IllegalArgumentException("undefined null flavor " + nf);
  }
  
  /** FIXME: is NA correct or should it be derived from this and that?

      FIXME: infinities may need to be treated differently!
   */
	//public BL equal(ANY that) { return BLimpl.NA; }

	/**
	 * the equal will tell whether the given ANY is equal to this object.
	 *
	 * At least this function will return BLimpl.TRUE on INTnull.PINF.equal(INTnull.PINF) and
	 * BLimpl.FALSE on INTnull.PINF.equal(INTnull.NI)!
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
		if (that instanceof INTnull)
		{
			INTnull thatObj = (INTnull) that;
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


	public BL equal(int x) { return BLimpl.NA; }
  public BL equal(long x) { return BLimpl.NA; }
  public BL equal(float x) { return BLimpl.NA; }
  public BL equal(double x) { return BLimpl.NA; }

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

  public QTY plus(QTY.diff x) { return INTnull.NA; }
  public INT plus(INT x) { return INTnull.NA; }
  public INT plus(int x) { return INTnull.NA; }
  public INT plus(long x) { return INTnull.NA; }
  public INT plus(float x) { return INTnull.NA; }
  public INT plus(double x) { return INTnull.NA; }
  public INT minus(QTY x) { return INTnull.NA; }
  public INT minus(QTY.diff x) { return INTnull.NA; }
  public INT minus(INT x) { return INTnull.NA; }
  public INT minusReverse(int x) { return INTnull.NA; }
  public INT minusReverse(long x) { return INTnull.NA; }
  public INT minusReverse(float x) { return INTnull.NA; }
  public INT minusReverse(double x) { return INTnull.NA; }

  public BL isZero() { return BLimpl.NA; }
  public BL nonZero() { return BLimpl.NA; }

  public BL isOne() { return BLimpl.NA; }
  public INT successor() { return INTnull.NA; }
  public INT predecessor() { return INTnull.NA; }

  public INT times(REAL x) { return INTnull.NA; }
  public INT times(INT x) { return INTnull.NA; }
  public INT times(int x) { return INTnull.NA; }
  public INT times(long x) { return INTnull.NA; }
  public INT dividedBy(INT that) { return INTnull.NA; }
  public INT dividedByReverse(int that) { return INTnull.NA; }
  public INT dividedByReverse(long that) { return INTnull.NA; }

  public INT negated() { return INTnull.NA; }
  public BL isNegative() { return BLimpl.NA; }
  public BL nonNegative() { return BLimpl.NA; }

  public int intValue() { throw new NullPointerException(); }
  public long longValue() { throw new NullPointerException(); }
  public float floatValue() { throw new NullPointerException(); }
  public double doubleValue() { throw new NullPointerException(); }
  public REAL toREAL() { return REALnull.valueOf(nullFlavor().toString()); }

	public INT epsilon() {
		return INTlongAdapter.ONE;
	}
};
