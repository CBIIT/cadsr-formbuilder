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

import org.hl7.types.ADXP;
import org.hl7.types.ANY;
import org.hl7.types.BL;
import org.hl7.types.CS;
import org.hl7.types.enums.AddressPartType;

public class ADXPimpl extends STjlStringAdapter implements ADXP
{
	private static final CS DELIMITER_TYPE = AddressPartType.Delimiter;
	private CS _type;
	
	private ADXPimpl(String data, CS type, CS language) {
		super(data, language);
		this._type = (type == null) ? CSnull.NI : type;
	}
		
	/** Get the a null value according to the null flavor code.
	 */
	public static ADXP valueOf(String data, CS type, String language) {
		if (type.implies(DELIMITER_TYPE).isTrue())
		{
			return new ADXPimpl((data == null) ? "\n" : data, type, null);
		}
		else
		{
			return (data == null) ? (ADXP) ADXPnull.NI : (ADXP) new ADXPimpl(data.trim(), type, CSimpl.valueOf(language, "languageType"));
		}
	}
		
	/** Get the a null value according to the null flavor code.
	 */
	public static ADXP valueOf(String data, CS type) {
		if (type.implies(DELIMITER_TYPE).isTrue())
		{
			return new ADXPimpl((data == null) ? "\n" : data, type, null);
		}
		else
		{
			return (data == null) ? (ADXP) ADXPnull.NI : (ADXP) new ADXPimpl(data.trim(), type, null);
		}
	}
	
	/** Get the a null value according to the null flavor code. 
	 */
	public static ADXP valueOf(String data) {
		return (data == null) ? (ADXP)ADXPnull.NI :
			(ADXP)new ADXPimpl(data.trim(), null, null);
	}
	
	public CS type() { return _type; }
	
	public BL equal(ANY x) {
		if(!(x instanceof ADXPimpl)) {
			throw new IllegalArgumentException();
		}
		ADXPimpl that = (ADXPimpl)x;
		return ( super.equal(that).
				and(this._type.equal(that.type())) );
	}

	public int hashCode()
	{
		int result = super.hashCode();
		result = 29 * result + (_type != null ? _type.hashCode() : 0);
		return result;
	}
}
