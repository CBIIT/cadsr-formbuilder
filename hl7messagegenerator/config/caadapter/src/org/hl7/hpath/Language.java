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

import java.util.List;
import java.util.Arrays;

/** A singleton class to represent the HPath language itself. */
/*package protected*/ final class Language {
  private static final Language _INSTANCE = new Language();
  private Language() { } 

  public static Language instance() {
    return _INSTANCE;
  }

	private static Macros _macros = new Macros();
	public Macros macros() { return _macros; }
	
	static {
		_macros.define("given", "part[type.implies(CS:EntityNamePartType:Given)]");
		_macros.define("family", "part[type.implies(CS:EntityNamePartType:Family)]");
		_macros.define("streetAddressLine", "part[type.implies(CS:AddressPartType:StreetAddressLine)]");
		_macros.define("municipality", "part[type.implies(CS:AddressPartType:Municipality)]");
		_macros.define("stateOrProvince", "part[type.implies(CS:AddressPartType:StateOrProvince)]");
		_macros.define("postalCode", "part[type.implies(CS:AddressPartType:PostalCode)]");
		
		_macros.define("component", "outboundRelationship[typeCode.implies(CS:ActRelationshipType:ActRelationshipHasComponent)]");
		_macros.define("reason", "outboundRelationship[typeCode.implies(CS:ActRelationshipType:ActRelationshipReason)]");
		_macros.define("precondition", "outboundRelationship[typeCode.implies(CS:ActRelationshipType:HasPreCondition)]");
		_macros.define("continuingObjective", "outboundRelationship[typeCode.implies(CS:ActRelationshipType:HasContinuingObjective)]");
		_macros.define("finalObjective", "outboundRelationship[typeCode.implies(CS:ActRelationshipType:HasFinalObjective)]");
		_macros.define("problemSubject", "outboundRelationship[typeCode.implies(CS:ActRelationshipType:HasSubject)]");

		_macros.define("subjectParticipation", "participation[typeCode.implies(CS:ParticipationType:ParticipationTargetSubject)]");
		_macros.define("physicalPerformerParticipation", "participation[typeCode.implies(CS:ParticipationType:ParticipationPhysicalPerformer)]");
		_macros.define("recordTarget", "participation[typeCode.implies(CS:ParticipationType:RecordTarget)]");
		_macros.define("author", "participation[typeCode.implies(CS:ParticipationType:Author)]");
		_macros.define("consumable", "participation[typeCode.implies(CS:ParticipationType:ParticipationConsumable)]");

		_macros.define("ingredients", "scopedRole[classCode.implies(CS:RoleClass:IngredientEntity)]");

		_macros.define("workingList", "act[classCode.implies(CS:ActClass:WorkingList)]");
		_macros.define("observation", "act[classCode.implies(CS:ActClass:Observation)]");
		_macros.define("substanceAdministration", "act[classCode.implies(CS:ActClass:SubstanceAdministration)]");

		Conversion.define(TSasDateStringConversion.DEFINITION);
		Conversion.define(STasStringConversion.DEFINITION);
		Conversion.define(IIasExtensionStringConversion.DEFINITION);
		Conversion.define(CSasEnumStringConversion.DEFINITION);
		Conversion.define(ANYasStringConversion.DEFINITION);
	}

  public interface ExpressionForm {
    Expression parse(StaticContext staticContext) throws SyntaxError;
  }

  private static final List<ExpressionForm> _EXPRESSION_FORMS = Arrays.asList(new ExpressionForm[] {
    // Variable.FORM,
    // Parenthesis.FORM,
    Literal.FORM,
    Property.FORM,
    Filter.FORM
  });
  
  public List<ExpressionForm> expressionForm() {
    return _EXPRESSION_FORMS;
  }
}
