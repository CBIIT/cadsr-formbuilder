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
import org.hl7.types.ORD;
import org.hl7.types.QTY;
import org.hl7.types.REAL;

public class QTYnull extends ANYimpl implements QTY.diff {
  public static QTYnull NI   = new QTYnull(NullFlavorImpl.NI);
  public static QTYnull NA   = new QTYnull(NullFlavorImpl.NA);
  public static QTYnull UNK  = new QTYnull(NullFlavorImpl.UNK);
  public static QTYnull NASK = new QTYnull(NullFlavorImpl.NASK);
  public static QTYnull ASKU = new QTYnull(NullFlavorImpl.ASKU);
  public static QTYnull NAV  = new QTYnull(NullFlavorImpl.NAV);
  public static QTYnull OTH  = new QTYnull(NullFlavorImpl.OTH);
  public static QTYnull PINF = new QTYnull(NullFlavorImpl.PINF);
  public static QTYnull NINF = new QTYnull(NullFlavorImpl.NINF);

  private QTYnull(NullFlavor nf) {
    super(nf);
  }

  /** Get the a null value according to the null flavor code.
   */
  public static QTYnull valueOf(String nullFlavorString) {
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
    
  public QTY.diff numerator() { return QTYnull.NA; }
  public QTY.diff denominator(){ return QTYnull.NA; }
  
  /** FIXME: is NA correct or should it be derived from this and that?

      FIXME: infinities may need to be treated differently!
   */
  public BL equal(ANY that) { return BLimpl.NA; }
  public BL isZero() { return BLimpl.NA; }
  public BL nonZero() { return BLimpl.NA; }
  public QTY.diff negated() { return QTYnull.NA; }
  public QTY.diff times(REAL x) { return QTYnull.NA; }
  public QTY plus(QTY.diff that) { return QTYnull.NA; }
  public QTY minus(QTY.diff that) { return QTYnull.NA; }
  public QTY.diff minus(QTY that) { return QTYnull.NA; }
  public BL lessOrEqual(ORD that) { return BLimpl.NA; }
  public BL lessThan(ORD that)  { return BLimpl.NA; }
  public BL greaterThan(ORD that)  { return BLimpl.NA; }
  public BL greaterOrEqual(ORD that)  { return BLimpl.NA; }
  public BL compares(ORD that)  { return BLimpl.NA; }

	public diff epsilon() {
		return NI;
	}
	
	public int compareTo(ORD that) {
		return that.isNull().isTrue() ? 0 : -1;
	}
}
