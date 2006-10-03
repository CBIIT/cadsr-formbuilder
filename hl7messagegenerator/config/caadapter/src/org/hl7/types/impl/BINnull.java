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
import org.hl7.types.INT;
import org.hl7.types.LIST;
import org.hl7.types.NullFlavor;

/** Minimal implementation of BIN as a model we can use for all other
    type. This one is only used to convey NULL values. Again, we have
    static values of it, so we don't create so many objects in a
    running program.
*/
public final class BINnull extends ANYimpl implements BIN {

  public static BINnull NI   = new BINnull(NullFlavorImpl.NI);
  public static BINnull NA   = new BINnull(NullFlavorImpl.NA);
  public static BINnull UNK  = new BINnull(NullFlavorImpl.UNK);
  public static BINnull NASK = new BINnull(NullFlavorImpl.NASK);
  public static BINnull ASKU = new BINnull(NullFlavorImpl.ASKU);
  public static BINnull NAV  = new BINnull(NullFlavorImpl.NAV);
  public static BINnull OTH  = new BINnull(NullFlavorImpl.OTH);
  public static BINnull PINF = new BINnull(NullFlavorImpl.PINF);
  public static BINnull NINF = new BINnull(NullFlavorImpl.NINF);

  private BINnull(NullFlavor nf) {
    super(nf);
  }

  /** Get the a null value according to the null flavor code.
   */
  public static BINnull valueOf(String nullFlavorString) {
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
  public BL equal(ANY that) { ;return BLimpl.NA; }

  public BL head() {
    throw new UnsupportedOperationException();
  }
  public LIST<BL> tail() {
    throw new UnsupportedOperationException();
  }
  public Iterator<BL> iterator() {
    throw new UnsupportedOperationException();
  }

  public BL isEmpty() {
    return BLimpl.NA;
  }
  public BL nonEmpty() {
    return BLimpl.NA;
  }
  public INT length() {
    return INTnull.NA;
  }
};
