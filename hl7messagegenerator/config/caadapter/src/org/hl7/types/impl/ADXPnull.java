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

import org.hl7.types.ADXP;
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

public class ADXPnull extends STnull implements ADXP {
  public static ADXPnull NI   = new ADXPnull(NullFlavorImpl.NI);
  public static ADXPnull NA   = new ADXPnull(NullFlavorImpl.NA);
  public static ADXPnull UNK  = new ADXPnull(NullFlavorImpl.UNK);
  public static ADXPnull NASK = new ADXPnull(NullFlavorImpl.NASK);
  public static ADXPnull ASKU = new ADXPnull(NullFlavorImpl.ASKU);
  public static ADXPnull NAV  = new ADXPnull(NullFlavorImpl.NAV);
  public static ADXPnull OTH  = new ADXPnull(NullFlavorImpl.OTH);
  public static ADXPnull PINF = new ADXPnull(NullFlavorImpl.PINF);
  public static ADXPnull NINF = new ADXPnull(NullFlavorImpl.NINF);

  private ADXPnull(NullFlavor nf) {
    super(nf);
  }

  /** Get the a null value according to the null flavor code.
   */
  public static ADXPnull valueOf(String nullFlavorString) {
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
  
  public CS type() { return CSnull.NA; }

  /** FIXME: is NA correct or should it be derived from this and that?
   */
  public BL equal(ANY that) { return BLimpl.NA; }

  public ST headST() {
    throw new UnsupportedOperationException();
  }
  public ST tailST() {
    throw new UnsupportedOperationException();
  }
  public Iterator<ST> listSTIterator() {
    throw new UnsupportedOperationException();
  }
  public String toString() {  return nullFlavor().code().toString();  }

  public BL head() {
    throw new UnsupportedOperationException();
  }
  public LIST<BL> tail() {
    throw new UnsupportedOperationException();
  }
  public Iterator<BL> listIterator() {
    throw new UnsupportedOperationException();
  }

  public BL isEmpty() { return BLimpl.NA; }
  public BL nonEmpty() { return BLimpl.NA; }
  public INT length() { return INTnull.NA; }

  // The ED interface
  public CS mediaType() { return CSnull.NA; }
  public CS charset() { return CSnull.NA; }
  public CS compression() { return CSnull.NA; }
  public CS language() { return CSnull.NA; }
  public TEL reference() { return TELnull.NA; }
  public BIN integrityCheck() { return BINnull.NA; }
  public CS integrityCheckAlgorithm() { return CSnull.NA; }
  public ED thumbnail() { return EDnull.NA; }
}
