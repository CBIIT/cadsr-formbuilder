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

import org.hl7.types.CS;
import org.hl7.types.DSET;
import org.hl7.types.EN;
import org.hl7.types.ENXP;
import org.hl7.types.IVL;
import org.hl7.types.NullFlavor;
import org.hl7.types.ST;
import org.hl7.types.TS;

public class ENnull extends LISTnull<ENXP> implements EN
{
  public static ENnull NI   = new ENnull(NullFlavorImpl.NI);
  public static ENnull NA   = new ENnull(NullFlavorImpl.NA);
  public static ENnull UNK  = new ENnull(NullFlavorImpl.UNK);
  public static ENnull NASK = new ENnull(NullFlavorImpl.NASK);
  public static ENnull ASKU = new ENnull(NullFlavorImpl.ASKU);
  public static ENnull NAV  = new ENnull(NullFlavorImpl.NAV);
  public static ENnull OTH  = new ENnull(NullFlavorImpl.OTH);
  public static ENnull PINF = new ENnull(NullFlavorImpl.PINF);
  public static ENnull NINF = new ENnull(NullFlavorImpl.NINF);

  protected ENnull(NullFlavor nf) { super(nf); }

  /** Get the a null value according to the null flavor code.
   */
  public static ENnull valueOf(String nullFlavorString) {
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
//  public BL equal(ANY that) { return BLimpl.NA; }

	/**
	 * equal() method is defined in super class, no need to override.
	 *
	 */

  public DSET<CS> use() { return DSETnull.NA; }
  public IVL<TS> validTime() { return IVLnull.NA; }
  public ST formatted() { return STnull.NA; }
}
