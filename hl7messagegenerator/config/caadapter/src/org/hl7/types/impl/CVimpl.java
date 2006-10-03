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

import java.util.logging.Logger;

import org.hl7.types.ANY;
import org.hl7.types.BL;
import org.hl7.types.CD;
import org.hl7.types.CR;
import org.hl7.types.CV;
import org.hl7.types.CodeValueFactory;
import org.hl7.types.ED;
import org.hl7.types.LIST;
import org.hl7.types.SET;
import org.hl7.types.ST;
import org.hl7.types.UID;

public class CVimpl extends ANYimpl implements CV {
	private ST _code;
	private UID _codeSystem;
	protected ST _codeSystemName = STnull.NI; // don't need to store that
	protected ST _codeSystemVersion = STnull.NI;
	protected ST _displayName = STnull.NI; // don't need to store that either
	protected ST _originalText = STnull.NI;
	
	private static final Logger LOGGER = Logger.getLogger(CV.class.getName());
	
	protected CVimpl(ST code, UID codeSystem) throws IllegalArgumentException
	{
		super(null);
		if (code == null || code.isNullJ()
				|| codeSystem == null || codeSystem.isNullJ())
			throw new IllegalArgumentException();
		else
		{
			this._code = code;
			this._codeSystem = codeSystem;
		}
	}
	
	public static CV valueOf(String code, String codeSystem)
	{
		return valueOf(STjlStringAdapter.valueOf(code), UIDimpl.valueOf(codeSystem), STnull.NI, STnull.NI, STnull.NI, STnull.NI);
	}
	
	public static CV valueOf(ST code, UID codeSystem, ST originalText, ST displayName, ST codeSystemName, ST codeSystemVersion)
	{
		if (code.isNullJ() || codeSystem.isNullJ())
		{
			return CVnull.NI;  // enforce that we need a root!
			// FIXME: should this sort of convenience be one level up?
			// I mean, we could just throw an IllegalArgumentException?
		}
		else
		{
			try
			{ // try to create a specialized code value instance
				CV result = CodeValueFactory.getInstance().valueOf(code, codeSystem, codeSystemVersion, displayName, originalText);
				//System.err.println("#CVF#: " + result.getClass());
                LOGGER.fine("#CVF#: " + result.getClass());
				return result;
			}
			catch (CodeValueFactory.InvalidCodeException x)
			{
				throw new IllegalArgumentException("codeSystem " + codeSystem.toString(), x);
			}
			catch (CodeValueFactory.UnknownCodeSystemException x)
			{
				// if code system is not known, we create an unchecked code value
				CVimpl theCV = new CVimpl(code, codeSystem);
				
				theCV._displayName = (displayName != null ? displayName : STnull.NI);
				theCV._codeSystemName = (codeSystemName != null ? codeSystemName : STnull.NI);
				theCV._codeSystemVersion = (codeSystemVersion != null ? codeSystemVersion : STnull.NI);
				theCV._originalText = (originalText != null ? originalText : STnull.NI);
				
				return theCV;
				
			}
			catch (CodeValueFactory.DelegateException x)
			{
				LOGGER.warning(x.getCause().getMessage() + ":" + x.getMessage());
				return CVnull.NI;
//				throw new RuntimeException(x.getCause());
			}
			catch (CodeValueFactory.Exception x)
			{
				throw new RuntimeException(x);
			}
		}
	}
	
	public BL equal(ANY that) {
		if (this == that)
			return BLimpl.TRUE;
		else if (that.isNullJ())
			return BLimpl.FALSE; // FIXME: false or NI or UNK?
		else if (that instanceof CV)
		{
			CV thatCV = (CV) that;
			return this.codeSystem().equal(thatCV.codeSystem())
			.and(this.code().equal(thatCV.code()));
		}
		else
			return BLimpl.FALSE;
	}
	
	public ST code()
	{
		return this._code;
	}
	
	public ST displayName()
	{
		return this._displayName;
	}
	
	public UID codeSystem()
	{
		return this._codeSystem;
	}
	
	public ST codeSystemName()
	{
		return this._codeSystemName;
	}
	
	public ST codeSystemVersion()
	{
		return this._codeSystemVersion;
	}
	
	public ED originalText()
	{
		return this._originalText;
	}
	
	public BL implies(CD x)
	{
		BL equality = this.equal(x);
		if (equality.isTrue())
			return equality;
		else
			throw new UnsupportedOperationException("cannot imply anything of " + this);
	}
	
	public CD mostSpecificGeneralization(CD x)
	{
		throw new UnsupportedOperationException();
	}
	
	public LIST<CR> qualifier()
	{
		return LISTnull.NA;
	}
	
	public SET<CD> translation()
	{
		return SETnull.NA;
	}
	
	
	/** HashCode to allow using CVs in hash maps and sets. */
	public int hashCode() {
		// notice how this relates with the definition of equal
		return (codeSystem().hashCode()<<16) + code().hashCode();
	}

	public String toString() {
		return code().toString() + "@" + codeSystem().toString();
	}
}

