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
import org.hl7.types.NullFlavor;

public class ANYnull extends ANYimpl implements ANY, Comparable<ANYnull> {

  public static ANYnull NI   = new ANYnull(NullFlavorImpl.NI);
  public static ANYnull NA   = new ANYnull(NullFlavorImpl.NA);
  public static ANYnull UNK  = new ANYnull(NullFlavorImpl.UNK);
  public static ANYnull NASK = new ANYnull(NullFlavorImpl.NASK);
  public static ANYnull ASKU = new ANYnull(NullFlavorImpl.ASKU);
  public static ANYnull NAV  = new ANYnull(NullFlavorImpl.NAV);
  public static ANYnull OTH  = new ANYnull(NullFlavorImpl.OTH);
  public static ANYnull PINF = new ANYnull(NullFlavorImpl.PINF);
  public static ANYnull NINF = new ANYnull(NullFlavorImpl.NINF);
  public static ANYnull MSK = new ANYnull(NullFlavorImpl.MSK);
  public static ANYnull NP = new ANYnull(NullFlavorImpl.NP);
  
  protected ANYnull(NullFlavor nf) {
    super(nf);
  }
  
  /** Get the a null value according to the null flavor code.
   */
  public static ANYnull valueOf(String nf) {
    /** We first intern the argument because we'll compare it with
	constants that are always interned.
    */
    if (NullFlavorImpl.sNI.equals(nf)) return NI;
    else if(NullFlavorImpl.sNA.equals(nf)) return NA;
    else if(NullFlavorImpl.sUNK.equals(nf)) return UNK;
    else if(NullFlavorImpl.sNASK.equals(nf)) return NASK;
    else if(NullFlavorImpl.sASKU.equals(nf)) return ASKU;
    else if(NullFlavorImpl.sNAV.equals(nf)) return NAV;
    else if(NullFlavorImpl.sOTH.equals(nf)) return OTH;
    else if(NullFlavorImpl.sPINF.equals(nf)) return PINF;
    else if(NullFlavorImpl.sNINF.equals(nf)) return NINF;
    else if(NullFlavorImpl.sMSK.equals(nf)) return MSK;
    else if(NullFlavorImpl.sNP.equals(nf)) return NP;
    else throw new IllegalArgumentException("null flavor " + nf);
  }
  
  public CS type() { return CSnull.NA; }
  
  /** FIXME: is NA correct or should it be derived from this and that?
   */
  public BL equal(ANY that) { return BLimpl.NA; }
  
  public String toString() {  return nullFlavor().code().toString();  }

  public int compareTo(ANYnull that) {
		return that.isNull().isTrue() ? 0 : -1;
	}
}
