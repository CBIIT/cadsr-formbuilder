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
import org.hl7.types.ONXP;

public class ONXPnull extends ENXPnull implements ONXP {
  public static ONXPnull NI   = new ONXPnull(NullFlavorImpl.NI);
  public static ONXPnull NA   = new ONXPnull(NullFlavorImpl.NA);
  public static ONXPnull UNK  = new ONXPnull(NullFlavorImpl.UNK);
  public static ONXPnull NASK = new ONXPnull(NullFlavorImpl.NASK);
  public static ONXPnull ASKU = new ONXPnull(NullFlavorImpl.ASKU);
  public static ONXPnull NAV  = new ONXPnull(NullFlavorImpl.NAV);
  public static ONXPnull OTH  = new ONXPnull(NullFlavorImpl.OTH);
  public static ONXPnull PINF = new ONXPnull(NullFlavorImpl.PINF);
  public static ONXPnull NINF = new ONXPnull(NullFlavorImpl.NINF);

  private ONXPnull(NullFlavor nf) { super(nf); } 

  /** Get the a null value according to the null flavor code. 
   */
  public static ONXPnull valueOf(String nullFlavorString) {
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
