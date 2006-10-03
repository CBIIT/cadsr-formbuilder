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
import org.hl7.types.CD;
import org.hl7.types.CR;
import org.hl7.types.CS;
import org.hl7.types.ED;
import org.hl7.types.LIST;
import org.hl7.types.NullFlavor;
import org.hl7.types.SET;
import org.hl7.types.ST;
import org.hl7.types.UID;

/** Minimal implementation of CS as a model we can use for all other
    type. This one is only used to convey NULL values. Again, we have
    static values of it, so we don't create so many objects in a
    running program.

    FIXME: this and all other C*null classes must have a code system
    for at least the OTH null value.
*/
public final class CSnull extends ANYimpl implements CS {

  public static CSnull NI   = new CSnull(NullFlavorImpl.NI);
  public static CSnull NA   = new CSnull(NullFlavorImpl.NA);
  public static CSnull UNK  = new CSnull(NullFlavorImpl.UNK);
  public static CSnull NASK = new CSnull(NullFlavorImpl.NASK);
  public static CSnull ASKU = new CSnull(NullFlavorImpl.ASKU);
  public static CSnull NAV  = new CSnull(NullFlavorImpl.NAV);
  public static CSnull OTH  = new CSnull(NullFlavorImpl.OTH);
  public static CSnull PINF = new CSnull(NullFlavorImpl.PINF);
  public static CSnull NINF = new CSnull(NullFlavorImpl.NINF);

  private CSnull(NullFlavor nf) {
    super(nf);
  }

  /** Get the a null value according to the null flavor code.
   */
  public static CSnull valueOf(String nullFlavorString) {
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

	/**
	 * the equal will tell whether the given ANY is equal to this object.
	 * @param that
	 * @return BLimpl.TRUE if that is equal to this object.
	 */
	public BL equal(ANY that)
	{
		/**
		 * Designed by S. Jiang:
		 * The contract of equal shall be carried no matter the semantics value of the class itself.
		 */
		if(that instanceof CSnull)
		{
			CSnull thatObj = (CSnull) that;
			return thatObj.nullFlavor().equal(this.nullFlavor());
		}
		else
		{
			return BLimpl.FALSE;
		}
	}

	//equals() method has been overriden in ANYimpl class

	/**
	 * override hashCode() method.
	 * @return hashCode
	 */
	public int hashCode()
	{
		return this.nullFlavor().hashCode();
	}

	/** CD interface */

  public ST code() { return STnull.NA; }
  public ST displayName() { return STnull.NA; }
  public UID codeSystem() { return UIDnull.NA; }
  public ST codeSystemName() { return STnull.NA; }
  public ST codeSystemVersion() { return STnull.NA; }
  public ED originalText() { return EDnull.NA; }
  public BL implies(CD x) { return BLimpl.NA; }
  public CD mostSpecificGeneralization(CD x) { return CDnull.NA; }

  public LIST<CR> qualifier() {
    throw new UnsupportedOperationException();
  }
  public SET<CD> translation() {
    throw new UnsupportedOperationException();
  }

  public String toString() {  return nullFlavor().code().toString(); }
};
