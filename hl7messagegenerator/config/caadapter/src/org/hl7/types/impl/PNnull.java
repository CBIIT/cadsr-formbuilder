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

import org.hl7.types.NullFlavor;
import org.hl7.types.PN;

public class PNnull extends ENnull implements PN {
  public static PNnull NI   = new PNnull(NullFlavorImpl.NI);
  public static PNnull NA   = new PNnull(NullFlavorImpl.NA);
  public static PNnull UNK  = new PNnull(NullFlavorImpl.UNK);
  public static PNnull NASK = new PNnull(NullFlavorImpl.NASK);
  public static PNnull ASKU = new PNnull(NullFlavorImpl.ASKU);
  public static PNnull NAV  = new PNnull(NullFlavorImpl.NAV);
  public static PNnull OTH  = new PNnull(NullFlavorImpl.OTH);
  public static PNnull PINF = new PNnull(NullFlavorImpl.PINF);
  public static PNnull NINF = new PNnull(NullFlavorImpl.NINF);

  private PNnull(NullFlavor nf) { super(nf); } 

  /** Get the a null value according to the null flavor code. 
   */
  public static PNnull valueOf(String nullFlavorString) {
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
}
