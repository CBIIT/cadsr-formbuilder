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
import org.hl7.types.CS;
import org.hl7.types.INT;
import org.hl7.types.IVL;
import org.hl7.types.NullFlavor;
import org.hl7.types.PIVL;
import org.hl7.types.QSET;
import org.hl7.types.QTY;
import org.hl7.types.SET;
import org.hl7.types.TS;

/**
 * @author Gunther Schadow, Geoffry Roberts
 */
public class PIVLnull<T extends QTY> extends QSETnull<T> implements PIVL<T> {

  public static PIVLnull NI   = new PIVLnull(NullFlavorImpl.NI);
  public static PIVLnull NA   = new PIVLnull(NullFlavorImpl.NA);
  public static PIVLnull UNK  = new PIVLnull(NullFlavorImpl.UNK);
  public static PIVLnull NASK = new PIVLnull(NullFlavorImpl.NASK);
  public static PIVLnull ASKU = new PIVLnull(NullFlavorImpl.ASKU);
  public static PIVLnull NAV  = new PIVLnull(NullFlavorImpl.NAV);
  public static PIVLnull OTH  = new PIVLnull(NullFlavorImpl.OTH);

  // the empty set
  public static PIVLnull NIL  = new PIVLnull(null);

  private PIVLnull(NullFlavor nf) {
    super(nf);
  }
  
  public IVL<T> phase() {
    return IVLnull.NA;
  }

  public QTY.diff period() {
    return PQnull.NA;
  }

  public CS alignment() {
    return CSnull.NA;
  }

	/**
	 * Utilize the equal() method from super.
	 */
	
//  public BL equal(ANY that) { return BLimpl.NA; }

  public QSET<T> intersection(SET<T> otherset) {
    return QSETnull.NA;
  }
  public QSET<T> intersection(QSET<T> otherset) {
    return QSETnull.NA;
  }

  public BL institutionSpecified() {
    return BLimpl.NA;
  }

  public BL contains(TS element) {
    return BLimpl.NA;
  }

  public BL isEmpty() {
    return BLimpl.NA;
  }

  public BL nonEmpty() {
    return BLimpl.NA;
  }

  public BL contains(SET<T> otherset) {
    return BLimpl.NA;
  }

  public INT cardinality() {
    return INTnull.NA;
  }

  public QSET<T> union(SET<T> otherset) {
    return QSETnull.NAV;
  }
  public QSET<T> union(QSET<T> otherset) {
    return QSETnull.NAV;
  }

  public QSET<T> except(TS element) {
    return QSETnull.NAV;
  }

  public QSET<T> except(SET<T> otherset) {
    return QSETnull.NAV;
  }
  public QSET<T> except(QSET<T> otherset) {
    return QSETnull.NAV;
  }

  public Iterator<ANY> iterator() {
    throw new UnsupportedOperationException();
  }
}
