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
import org.hl7.types.BAG;
import org.hl7.types.BL;
import org.hl7.types.INT;
import org.hl7.types.NullFlavor;

/** Minimal implementation of BAG for nulls. We don't want to
    instantiate new such objects every time we use them.
*/
public class BAGnull<T extends ANY> extends ANYimpl implements BAG<T> {

  public static BAGnull NI   = new BAGnull(NullFlavorImpl.NI);
  public static BAGnull NA   = new BAGnull(NullFlavorImpl.NA);
  public static BAGnull UNK  = new BAGnull(NullFlavorImpl.UNK);
  public static BAGnull NASK = new BAGnull(NullFlavorImpl.NASK);
  public static BAGnull ASKU = new BAGnull(NullFlavorImpl.ASKU);
  public static BAGnull NAV  = new BAGnull(NullFlavorImpl.NAV);
  public static BAGnull OTH  = new BAGnull(NullFlavorImpl.OTH);

  // the empty bag
  public static BAGnull NIL  = new BAGnull(null);

  protected BAGnull(NullFlavor nf) {
    super(nf);
  }

  /** Get the a null value according to the null flavor code.
   */
  public static BAGnull valueOf(String nullFlavorString) {
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

  public BL contains(T kind) {
    return BLimpl.FALSE;
  }

  public BAG<T>  plus(BAG<T> x) {
    throw new UnsupportedOperationException();
  }

  public BAG<T>  minus(BAG<T> x) {
    throw new UnsupportedOperationException();
  }

  public BL isEmpty() {
    return BLimpl.NA;
  }
  public BL nonEmpty() {
    return BLimpl.NA;
  }
  public INT size() {
    return INTnull.NA;
  }

  public Iterator<ANY> iterator() {
    return EmptySet.ITERATOR;
  }
}
