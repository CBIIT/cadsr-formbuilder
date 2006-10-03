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
import org.hl7.types.DSET;
import org.hl7.types.NullFlavor;
import org.hl7.types.QSET;
import org.hl7.types.ST;
import org.hl7.types.TEL;
import org.hl7.types.TS;

/** Minimal implementation of TEL as a model we can use for all other
    type. This one is only used to convey NULL values. Again, we have
    static values of it, so we don't create so many objects in a
    running program.
*/
public final class TELnull extends ANYimpl implements TEL {

  public static TELnull NI   = new TELnull(NullFlavorImpl.NI);
  public static TELnull NA   = new TELnull(NullFlavorImpl.NA);
  public static TELnull UNK  = new TELnull(NullFlavorImpl.UNK);
  public static TELnull NASK = new TELnull(NullFlavorImpl.NASK);
  public static TELnull ASKU = new TELnull(NullFlavorImpl.ASKU);
  public static TELnull NAV  = new TELnull(NullFlavorImpl.NAV);
  public static TELnull OTH  = new TELnull(NullFlavorImpl.OTH);
  public static TELnull PINF = new TELnull(NullFlavorImpl.PINF);
  public static TELnull NINF = new TELnull(NullFlavorImpl.NINF);

  private TELnull(NullFlavor nf) {
    super(nf);
  }

  /** Get the a null value according to the null flavor code.
   */
  public static TELnull valueOf(String nullFlavorString) {
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
    else throw new IllegalArgumentException("null flavor " + nf);
  }
  
  /** FIXME: is NA correct or should it be derived from this and that? */
  public BL equal(ANY that) { return BLimpl.NA; }

  public CS scheme() { return CSnull.NA; }
  public ST address() { return STnull.NA; }
  public QSET<TS> validTime() {
    return QSETnull.NA;
  }
  public DSET<CS> use() {
    return DSETnull.NA;
  }
};
