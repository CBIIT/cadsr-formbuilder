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
import org.hl7.types.II;
import org.hl7.types.NullFlavor;
import org.hl7.types.ST;
import org.hl7.types.UID;

/** Minimal implementation of II as a model we can use for all other
    type. This one is only used to convey NULL values. Again, we have
    static values of it, so we don't create so many objects in a
    running program.
*/
public final class IInull extends ANYimpl implements II {

  public static IInull NI   = new IInull(NullFlavorImpl.NI);
  public static IInull NA   = new IInull(NullFlavorImpl.NA);
  public static IInull UNK  = new IInull(NullFlavorImpl.UNK);
  public static IInull NASK = new IInull(NullFlavorImpl.NASK);
  public static IInull ASKU = new IInull(NullFlavorImpl.ASKU);
  public static IInull NAV  = new IInull(NullFlavorImpl.NAV);
  public static IInull OTH  = new IInull(NullFlavorImpl.OTH);
  public static IInull PINF = new IInull(NullFlavorImpl.PINF);
  public static IInull NINF = new IInull(NullFlavorImpl.NINF);

  private IInull(NullFlavor nf) {
    super(nf);
  }

  /** Get the a null value according to the null flavor code.
   */
  public static IInull valueOf(String nullFlavorString) {
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
//  public BL equal(ANY that) { return BLimpl.NA; }

	/**
	 * the equal will tell whether the given ANY is equal to this object.
	 *
	 * @param that
	 * @return BLimpl.TRUE if that is equal to this object.
	 */
	public BL equal(ANY that)
	{
		/**
		 * Designed by S. Jiang:
		 * The contract of equal shall be carried no matter the semantics value of the class itself.
		 */
		if (that instanceof IInull)
		{
			IInull thatObj = (IInull) that;
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
	 *
	 * @return hashCode
	 */
	public int hashCode()
	{
		return this.nullFlavor().hashCode();
	}


	/** II interface */

  public ST extension() { return STnull.NA; }
  public UID root() { return UIDnull.NA; }
  public ST assigningAuthorityName() { return STnull.NA; }
  public BL displayable() {return BLimpl.NA;}
};
