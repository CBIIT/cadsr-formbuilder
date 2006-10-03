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
 * The Initial Developer of the Original Code is Gunther Schadow.
 * Portions created by Initial Developer are Copyright (C) 2002-2005 
 * Health Level Seven, Inc. All Rights Reserved.
 *
 * Contributor(s): 
 */
package org.hl7.hpath;

import java.util.Date;
import java.util.List;

import org.hl7.types.ST;
import org.hl7.types.TS;
import org.hl7.types.ValueFactory;
import java.text.ParsePosition;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/*package*/ class TSasDateStringConversion extends Conversion<TS, String> {
	private static final String NAME = "TSasDateString";

  public static final Conversion.Definition DEFINITION = new Conversion.Definition() {
			public String name() { return NAME; }
			public Conversion instantiate(Expression previousExpression, List<Expression> arguments) {
				return new TSasDateStringConversion(previousExpression, arguments);
			}
		};

	private TSasDateStringConversion(Expression previousExpression, List<Expression> arguments) {
		super(previousExpression, arguments);
	}

	public String name() { return NAME; }

	private static final TS TSNULL = (TS)ValueFactory.getInstance().nullValueOf("TS", "NI");
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("d-MMM-yyyy");
	// XXX: should add date format argument

	public String convertForward(TS object, Object... args) {
		int argsLength = args.length;
		
		if(object == null || object.isNull().isTrue())
			return "";			
		else if (argsLength > 0) {
			ST format = (ST) args[0];
			return new SimpleDateFormat(format.toString()).format(object.toDate());
		} 
		else
			return DATE_FORMAT.format(object.toDate());	
	}
	
	public TS convertBackward(String object, Object... args) {
		if(object == null)
			return TSNULL;
		else {
			object = object.trim();
			if(object.length() == 0)
				return null;
			else
				return ValueFactory.getInstance().TSvalueOf(DATE_FORMAT.parse(object, new ParsePosition(0)));
		}
	}
}

