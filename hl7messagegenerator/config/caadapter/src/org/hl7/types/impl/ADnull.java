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

import org.hl7.types.AD;
import org.hl7.types.ADXP;
import org.hl7.types.ANY;
import org.hl7.types.BL;
import org.hl7.types.CS;
import org.hl7.types.NullFlavor;
import org.hl7.types.QSET;
import org.hl7.types.SET;
import org.hl7.types.ST;
import org.hl7.types.TS;

public class ADnull extends LISTnull<ADXP> implements AD
{
  public static ADnull NI   = new ADnull(NullFlavorImpl.NI);
  public static ADnull NA   = new ADnull(NullFlavorImpl.NA);
  public static ADnull UNK  = new ADnull(NullFlavorImpl.UNK);
  public static ADnull NASK = new ADnull(NullFlavorImpl.NASK);
  public static ADnull ASKU = new ADnull(NullFlavorImpl.ASKU);
  public static ADnull NAV  = new ADnull(NullFlavorImpl.NAV);
  public static ADnull OTH  = new ADnull(NullFlavorImpl.OTH);
  public static ADnull PINF = new ADnull(NullFlavorImpl.PINF);
  public static ADnull NINF = new ADnull(NullFlavorImpl.NINF);

  private ADnull(NullFlavor nf) {
    super(nf);
  }

  /** Get the a null value according to the null flavor code.
   */
  public static ADnull valueOf(String nullFlavorString) {
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
   */
  public BL equal(ANY that) { return BLimpl.NA; }
  
  public SET<CS> use() { return SETnull.NA; }
  public QSET<TS> validTime() { return QSETnull.NA; }
  public ST formatted() { return STnull.NA; }
  public BL isNotOrdered() { return BLimpl.NA;}
}
