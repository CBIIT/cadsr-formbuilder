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
import org.hl7.types.BL;
import org.hl7.types.IVL;
import org.hl7.types.NullFlavor;
import org.hl7.types.QTY;

/** Minimal implementation of QSET for nulls. We don't want to
    instantiate new such objects every time we use them.
*/
public class IVLnull<T extends QTY> extends QSETnull<T> implements IVL<T> {
  public static IVLnull NI   = new IVLnull(NullFlavorImpl.NI);
  public static IVLnull NA   = new IVLnull(NullFlavorImpl.NA);
  public static IVLnull UNK  = new IVLnull(NullFlavorImpl.UNK);
  public static IVLnull NASK = new IVLnull(NullFlavorImpl.NASK);
  public static IVLnull ASKU = new IVLnull(NullFlavorImpl.ASKU);
  public static IVLnull NAV  = new IVLnull(NullFlavorImpl.NAV);
  public static IVLnull OTH  = new IVLnull(NullFlavorImpl.OTH);

  protected IVLnull(NullFlavor nf) {
    super(nf);
  }

  /** Get the a null value according to the null flavor code.
   */
  public static IVLnull valueOf(String nullFlavorString) {
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
  
  public T low() { // FIXME: should really return NI here, but how do I know which type null value I should throw?
    throw new UnsupportedOperationException();
  }
  public T high() {
    throw new UnsupportedOperationException();
  }
  public T center() {
    throw new UnsupportedOperationException();
  }
  public QTY.diff width() {
    throw new UnsupportedOperationException();
  }

  public BL lowClosed() {
    return BLimpl.NA;
  }
  public BL highClosed() {
    return BLimpl.NA;
  }
  public BL overlaps(IVL<T> x) {
    return BLimpl.NA;
  }
  public IVL<T> intersection(IVL<T> otherset) {
    return IVLnull.NA;
  }

  public IVL<T> hull(IVL<T> x) {
    return IVLnull.NA;
  }

  public Iterator<ANY> iterator() {
    throw new UnsupportedOperationException();
  }
}
