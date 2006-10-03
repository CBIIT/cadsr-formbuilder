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
import org.hl7.types.QSET;
import org.hl7.types.QTY;
import org.hl7.types.SET;

/** Minimal implementation of QSET for nulls. We don't want to
    instantiate new such objects every time we use them.
*/
public class QSETnull<T extends QTY> extends SETnull<T> implements QSET<T> {
  public static QSETnull NI   = new QSETnull(NullFlavorImpl.NI);
  public static QSETnull NA   = new QSETnull(NullFlavorImpl.NA);
  public static QSETnull UNK  = new QSETnull(NullFlavorImpl.UNK);
  public static QSETnull NASK = new QSETnull(NullFlavorImpl.NASK);
  public static QSETnull ASKU = new QSETnull(NullFlavorImpl.ASKU);
  public static QSETnull NAV  = new QSETnull(NullFlavorImpl.NAV);
  public static QSETnull OTH  = new QSETnull(NullFlavorImpl.OTH);

  // the empty set
  public static QSETnull NIL  = new QSETnull(null);

  protected QSETnull(NullFlavor nf) {
    super(nf);
  }

  /** Get the a null value according to the null flavor code.
   */
  public static QSETnull valueOf(String nullFlavorString) {
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
  
  public QSET<T> union(SET<T> subset) {
    return QSETnull.NA;
  }
  public QSET<T> union(QSET<T> subset) {
    return QSETnull.NA;
  }
  public QSET<T> intersection(SET<T> otherset) {
    return QSETnull.NA;
  }
  public QSET<T> intersection(QSET<T> otherset) {
    return QSETnull.NA;
  }
  public IVL<T> intersection(IVL<T> otherset) {
    return IVLnull.NA;
  }
  public QSET<T> except(T element) {
    return QSETnull.NA;
  }
  public QSET<T> except(SET<T> element) {
    return QSETnull.NA;
  }
  public QSET<T> except(QSET<T> element) {
    return QSETnull.NA;
  }
  public IVL<T> hull() {
    return IVLnull.NA;
  }
  public IVL<T> nextTo(T t) {
    return IVLnull.NA;
  }
  public IVL<T> nextAfter(T t) {
    return IVLnull.NA;
  }
  public QSET<T> periodicHull(QSET<T> otherset) {
    return QSETnull.NA;
  }
  public BL interleaves(QSET<T> otherset) {
    return BLimpl.NA;
  }
  public Iterator<ANY> iterator() {
    throw new UnsupportedOperationException();
  }
}
