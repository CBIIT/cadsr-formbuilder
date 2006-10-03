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
import org.hl7.types.CodeValueFactory;
import org.hl7.types.ST;
import org.hl7.types.UID;

/** FIXME:
 
 This should be an abstract class, because we still need to know the 
 codeSystem used.
 
 Use like this:
 
 new CSimpl("SYMBOL") {
 public UID codeSystem() { return THAT_CODE_SYSTEM; }
 }
 */
public final class CSimpl extends CVimpl implements CS {
	
	private CSimpl(ST code, UID codeSystem) throws IllegalArgumentException {
		super(code, codeSystem);
	}
	
	public static CS valueOf(ST code, UID codeSystem) {
		return valueOf(code, codeSystem, STnull.NI, STnull.NI, STnull.NI);
	}
	
	public static CS valueOf(String code, String codeSystem) {
		return valueOf(STjlStringAdapter.valueOf(code), UIDimpl.valueOf(codeSystem), STnull.NI, STnull.NI, STnull.NI);
	}
	
	public static CS valueOf(ST code, UID codeSystem, ST originalText, ST displayName, ST codeSystemName, ST codeSystemVersion) {
		throw new Error("this should not have been called"); // CR: Why have this then??
	}
	
	public static CS valueOf(ST code, UID codeSystem, ST displayName, ST codeSystemName, ST codeSystemVersion) {
		if(code.isNullJ() || codeSystem.isNullJ()) {
			return CSnull.NI;  // enforce that we need a root!
			// FIXME: should this sort of convenience be one level up?
			// I mean, we could just throw an IllegalArgumentException?
		} else {
			try { // try to create a specialized code value instance

				CS result = (CS)CodeValueFactory.getInstance().valueOf(code, codeSystem, codeSystemVersion, displayName, STnull.NI);
				//System.err.println("#CSF#: " + result.getClass());
				return result;
				
			} catch(CodeValueFactory.Exception x) {
			} catch(ClassCastException x) {
			}
			
			// creating a specialized code has failed for whatever reason, for now just 
			// create an unvalidated CS.
			
			// FIXME: this is stupid. The Exception should at least distinguish the case
			// of a known invalid code so we can handle that properly!
			CSimpl theCS = new CSimpl(code, codeSystem);
			
			// FIXME: should any of these ever be java null? I don't think so!
			theCS._displayName = displayName;
			theCS._codeSystemName = codeSystemName;
			theCS._codeSystemVersion = codeSystemVersion;
			
			return theCS;
		}
	}
	
	public BL equal(ANY that) 
	{
        if (that == null)
		    return BLimpl.FALSE;

        if(this == that)
			return BLimpl.TRUE;
		else
            if(that.isNullJ()){
				return BLimpl.FALSE; // FIXME: false or NI or UNK?
			}
			else
				if(that instanceof CS) {
					CS thatCS = (CS)that;
					/*
					 return this.codeSystem().equal(thatCS.codeSystem())
					 .and(this.code().equal(thatCS.code()));
					 */
					return this.code().equal(thatCS.code());
				} else
					return BLimpl.FALSE;
	}
	
	public int hashCode()
	{
		return code().hashCode() + 15 * codeSystem().hashCode();
	}
	
	public String toString()
	{
		return code().toString();
	}
};
