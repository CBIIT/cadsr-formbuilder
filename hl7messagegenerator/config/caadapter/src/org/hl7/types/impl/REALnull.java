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
import org.hl7.types.ORD;
import org.hl7.types.QTY;
import org.hl7.types.REAL;

/** Minimal implementation of REAL as a model we can use for all other
    type. This one is only used to convey NULL values. Again, we have
    static values of it, so we don't create so many objects in a
    running program.
*/
public class REALnull extends ORDimpl implements REAL {
  public static REALnull NI   = new REALnull(NullFlavorImpl.NI);
  public static REALnull NA   = new REALnull(NullFlavorImpl.NA);
  public static REALnull UNK  = new REALnull(NullFlavorImpl.UNK);
  public static REALnull NASK = new REALnull(NullFlavorImpl.NASK);
  public static REALnull ASKU = new REALnull(NullFlavorImpl.ASKU);
  public static REALnull NAV  = new REALnull(NullFlavorImpl.NAV);
  public static REALnull OTH  = new REALnull(NullFlavorImpl.OTH);
  public static REALnull PINF = REALpinf.VALUE;
  public static REALnull NINF = REALninf.VALUE;

  protected REALnull(NullFlavor nf) {
    super(nf);
  }

  /** Get the a null value according to the null flavor code.
   */
  public static REALnull valueOf(String nullFlavorString) {
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
  
  /** FIXME: is NA correct or should it be derived from this and that?

      FIXME: infinities may need to be treated differently!
   */
  public BL equal(ANY that) { return BLimpl.NA; }
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

  public QTY plus(QTY.diff x) { return REALnull.NA; }
  public REAL plus(REAL x) { return REALnull.NA; }
  public REAL plus(int x) { return REALnull.NA; }
  public REAL plus(long x) { return REALnull.NA; }
  public REAL plus(float x) { return REALnull.NA; }
  public REAL plus(double x) { return REALnull.NA; }
  public REAL plus(int x, INT precision) { return REALnull.NA; }
  public REAL plus(long x, INT precision) { return REALnull.NA; }
  public REAL plus(float x, INT precision) { return REALnull.NA; }
  public REAL plus(double x, INT precision) { return REALnull.NA; }
  public REAL minus(QTY x) { return REALnull.NA; }
  public REAL minus(QTY.diff x) { return REALnull.NA; }
  public REAL minus(REAL x) { return REALnull.NA; }
  public REAL minusReverse(int x) { return REALnull.NA; }
  public REAL minusReverse(long x) { return REALnull.NA; }
  public REAL minusReverse(float x) { return REALnull.NA; }
  public REAL minusReverse(double x) { return REALnull.NA; }
  public REAL minusReverse(int x, INT precision) { return REALnull.NA; }
  public REAL minusReverse(long x, INT precision) { return REALnull.NA; }
  public REAL minusReverse(float x, INT precision) { return REALnull.NA; }
  public REAL minusReverse(double x, INT precision) { return REALnull.NA; }

  public BL isZero() { return BLimpl.NA; }
  public BL nonZero() { return BLimpl.NA; }

  public BL isOne() { return BLimpl.NA; }

  public REAL times(REAL x) { return REALnull.NA; }
  public REAL times(int x) { return REALnull.NA; }
  public REAL times(long x) { return REALnull.NA; }
  public REAL times(float x) { return REALnull.NA; }
  public REAL times(double x) { return REALnull.NA; }
  public REAL times(int x, INT precision) { return REALnull.NA; }
  public REAL times(long x, INT precision) { return REALnull.NA; }
  public REAL times(float x, INT precision) { return REALnull.NA; }
  public REAL times(double x, INT precision) { return REALnull.NA; }
  public REAL dividedBy(REAL that) { return REALnull.NA; }
  public REAL dividedByReverse(int that) { return REALnull.NA; }
  public REAL dividedByReverse(long that) { return REALnull.NA; }
  public REAL dividedByReverse(float that) { return REALnull.NA; }
  public REAL dividedByReverse(double that) { return REALnull.NA; }
  public REAL dividedByReverse(int that, INT precision) { return REALnull.NA; }
  public REAL dividedByReverse(long that, INT precision) { return REALnull.NA; }
  public REAL dividedByReverse(float that, INT precision) { return REALnull.NA; }
  public REAL dividedByReverse(double that, INT precision) { return REALnull.NA; }

  public REAL negated() { return REALnull.NA; }
  public REAL inverted() { return REALnull.NA; }
  public REAL power(REAL x) { return REALnull.NA; }
  public REAL powerReverse(int x) { return REALnull.NA; }
  public REAL powerReverse(long x) { return REALnull.NA; }
  public REAL powerReverse(float x) { return REALnull.NA; }
  public REAL powerReverse(double x) { return REALnull.NA; }
  public REAL powerReverse(int x, INT precision) { return REALnull.NA; }
  public REAL powerReverse(long x, INT precision) { return REALnull.NA; }
  public REAL powerReverse(float x, INT precision) { return REALnull.NA; }
  public REAL powerReverse(double x, INT precision) { return REALnull.NA; }

  public REAL log() { return REALnull.NA; }
  public REAL exp() { return REALnull.NA; }
  public REAL floor() { return REALnull.NA; }

  public int intValue() { throw new NullPointerException(); }
  public long longValue() { throw new NullPointerException(); }
  public float floatValue() { throw new NullPointerException(); }
  public double doubleValue() { throw new NullPointerException(); }
  
  public INT precision() { throw new NullPointerException(); }

	public diff epsilon() {
		return REALdoubleAdapter.ONE;
	}
};
