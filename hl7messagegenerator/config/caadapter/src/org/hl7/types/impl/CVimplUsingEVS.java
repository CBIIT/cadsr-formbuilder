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


//import gov.nih.nci.EVS.bean.DescLogicConcept;

/**
 * @deprecated caBIO EVS is using implementation
 */

public class CVimplUsingEVS
//    extends CVimpl implements CV
{
//
//  protected CVimplUsingEVS(ST code, UID codeSystem, String cui) {
//    super(code, codeSystem);
//    _cui = cui;
//  }
//
//  private final String _cui;
//
//  // FIXME: it is a mystery to me why there is a concept object when all
//  // the logical methods use string arguments and return strings as if
//  // they'd be all just static methods, but they aren't. Very weird!!!
//
//  private static ThreadLocal<DescLogicConcept> _DLC = null;
//
//  private static DescLogicConcept DLC() {
//    DescLogicConcept thing = _DLC.get();
//    if(thing == null) {
//      thing = new DescLogicConcept();
//      _DLC.set(thing);
//    }
//    return thing;
//  }
//
//  private static final String VOCABNAME = "HL7_V3";
//  private static final String DELIMITER = "#";
//
//  public static CV valueOf(ST code,
//			   UID codeSystem,
//			   ST originalText,
//			   ST displayName,
//			   ST codeSystemName,
//			   ST codeSystemVersion) {
//    try {
//      // FIXME: why, oh why do we instantiate a nothing-concepthere?
//      String propertyValue = codeSystem.toString()+DELIMITER+code.toString();
//      String cui = null;
//      String cuis[] = DLC().findConceptWithPropertyMatching(VOCABNAME,"OID",propertyValue,100);
//
//      switch(cuis.length) {
//      case 0: throw new IllegalArgumentException(new CodeValueFactory.Exception("EVS returned no concept for " + propertyValue));
//      case 1: cui = cuis[0]; break;
//      default: throw new IllegalArgumentException(new CodeValueFactory.Exception("EVS returned " + cuis.length + " concepts for " + propertyValue));
//      }
//
//      CVimplUsingEVS value = new CVimplUsingEVS(code, codeSystem, cui);
//
//      value._originalText = originalText;
//      value._displayName = displayName;
//      value._codeSystemName = codeSystemName;
//      value._codeSystemVersion = codeSystemVersion;
//
//      return value;
//
//    } catch(Exception x) {
//      throw new RuntimeException(x);
//    }
//  }
//
//  public BL implies(CD that) {
//    BL equality = this.equal(that);
//    if(equality.isTrue())
//      return equality;
//    else if(! (that instanceof CVimplUsingEVS))
//      throw new UnsupportedOperationException();
//
//    CVimplUsingEVS evsThat = (CVimplUsingEVS)that;
//
//    try {
//      return BLimpl.valueOf(DLC().isSubConcept(VOCABNAME, _cui, evsThat._cui));
//    } catch(Exception x) {
//      throw new RuntimeException(x);
//    }
//  }
//
//  public CD mostSpecificGeneralization(CD x) {
//    throw new UnsupportedOperationException();
//  }
};
