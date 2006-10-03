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
import org.hl7.types.DSET;
import org.hl7.types.ENXP;
import org.hl7.types.enums.AddressPartType;

public class ENXPimpl extends STjlStringAdapter implements ENXP
{
	private static final CS DELIMITER_TYPE = AddressPartType.Delimiter;
	private CS _type;
	private DSET<CS> _qualifier;
	
	protected ENXPimpl(String data, CS type,  CS language) {
		super(data, language);
		
		this._type = (type == null) ? CSnull.NI : type;
	} 
	
	protected ENXPimpl(String data, CS type, DSET<CS> qualifier, CS language) {
		super(data, language);
		
		this._type = (type == null) ? CSnull.NI : type;
		
		if(qualifier == null)
			this._qualifier = DSETnull.NI;
		else
			this._qualifier = qualifier;
	} 
	
	public static ENXP valueOf(String data) {
		return (data == null) ? (ENXP)ENXPnull.NI :
			(ENXP)new ENXPimpl(data.trim(), null, null);
	}
	
	/** Get the a null value according to the null flavor code. 
	 */
	public static ENXP valueOf(String data, CS type) {
		return (data == null) ? (ENXP)ENXPnull.NI :
			(ENXP)new ENXPimpl(data.trim(), type, null);
	}
	
	public static ENXP valueOf(String data, CS type, DSET<CS> qualifier) {
		return (data == null) ? (ENXP)ENXPnull.NI :
			(ENXP)new ENXPimpl(data.trim(), type, qualifier, null);
	}
	
	public static ENXP valueOf(String data, CS type, DSET<CS> qualifier, String language) {
		return (data == null) ? (ENXP)ENXPnull.NI :
			(ENXP)new ENXPimpl(data.trim(), type, qualifier, CSimpl.valueOf(language, "languageType"));
	}
	
	public BL equal(ANY x) {
		if(!(x instanceof ENXPimpl)) {
			throw new IllegalArgumentException();
		}
		ENXPimpl that = (ENXPimpl)x;
		
		return ( super.equal(that).
				and(this._type.equal(that.type())) );
	}
	
	public CS type() { return _type; }
	public DSET<CS> qualifier() { return _qualifier; }
}
