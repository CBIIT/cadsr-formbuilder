/* THIS FILE IS GENERATED AUTOMATICALLY - DO NOT MODIFY.

The contents of this file are subject to the Health Level-7 Public
License Version 1.0 (the "License"); you may not use this file except
in compliance with the License. You may obtain a copy of the License
at http://www.hl7.org/HPL/

Software distributed under the License is distributed on an "AS IS"
basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See
the License for the specific language governing rights and limitations
under the License.

The Original Code is all this file.

The Initial Developer of the Original Code is automatically generated
from HL7 copyrighted standards specification which may be subject to
different license. Portions created by Initial Developer are Copyright
(C) 2002-2004 Health Level Seven, Inc. All Rights Reserved.

THIS FILE IS GENERATED AUTOMATICALLY - DO NOT MODIFY.
*/
package org.hl7.types.enums;

import org.hl7.types.ValueFactory;
import org.hl7.types.ANY;
import org.hl7.types.CS;
import org.hl7.types.ST;
import org.hl7.types.UID;
import org.hl7.types.BL;
import org.hl7.types.CD;
import org.hl7.types.SET;
import org.hl7.types.LIST;
import org.hl7.types.CR;
import org.hl7.types.NullFlavor;
import org.hl7.types.impl.NullFlavorImpl;
import org.hl7.types.impl.STnull;
import org.hl7.types.impl.BLimpl;
import org.hl7.types.impl.SETnull;
import org.hl7.types.impl.LISTnull;
import java.util.EnumSet;
import java.util.Map;
import java.util.HashMap;

/**  */
public enum TelecommunicationAddressUse implements CS {

  // ACTUAL DATA

  root(null, "TelecommunicationAddressUse", null, null),
  /**  */
  AddressUse(null, "AddressUse", null, null),
  /** A communication address at a home, attempted contacts for business purposes might intrude privacy and chances are one will
contact family or other household members instead of the person one wishes to call. Typically used with urgent cases, or if
no other contacts are available. */
  HomeAddressUse(AddressUse, "HomeAddressUse", "H", "home address"),
  /** The primary home, to reach a person after business hours. */
  PrimaryHome(HomeAddressUse, null, "HP", "primary home"),
  /** A vacation home, to reach a person while on vacation. */
  VacationHome(HomeAddressUse, null, "HV", "vacation home"),
  /**  */
  Birthplace(AddressUse, null, "BIR", "birthplace"),
  /** A temporary address, may be good for visit or mailing. Note that an address history can provide more detailed information. */
  TemporaryAddress(AddressUse, null, "TMP", "temporary address"),
  /** An office address. First choice for business related contacts during business hours. */
  WorkPlace(AddressUse, null, "WP", "work place"),
  /** An automated answering machine used for less urgent cases and if the main purpose of contact is to leave a message or access
an automated announcement. */
  AnsweringService(null, null, "AS", "answering service"),
  /** A contact specifically designated to be used for emergencies. This is the first choice in emergencies, independent of any
other use codes. */
  EmergencyContact(null, null, "EC", "emergency contact"),
  /** A telecommunication device that moves and stays with its owner. May have characteristics of all other use codes, suitable
for urgent matters, not the first choice for routine business. */
  MobileContact(null, null, "MC", "mobile contact"),
  /** A paging device suitable to solicit a callback or to leave a very short message. */
  Pager(null, null, "PG", "pager");

  // BOILER PLATE CODE IN WHICH ONLY THE ENUM NAME MUST BE INSERTED

  public static final UID CODE_SYSTEM = ValueFactory.getInstance().UIDvalueOfLiteral("2.16.840.1.113883.5.1011");
  public static final ST  CODE_SYSTEM_NAME = ValueFactory.getInstance().STvalueOfLiteral("TelecommunicationAddressUse");

  private final TelecommunicationAddressUse _parent;
  // duh! what a cheap way to make a set, but really we don't expect there to be too many more than 2 parents!
  private final TelecommunicationAddressUse _parent2;
  private EnumSet<TelecommunicationAddressUse> _impliedConcepts = null;

  private TelecommunicationAddressUse(TelecommunicationAddressUse parent, String domainName, String code, String displayName) {
    this(parent, null, domainName, code, displayName);
  }

  // duh! what a cheap way to make a set, but really we don't expect there to be too many more than 2 parents!
  private TelecommunicationAddressUse(TelecommunicationAddressUse parent, TelecommunicationAddressUse parent2, String domainName, String code, String displayName) {
    _parent = parent;
    _parent2 = parent2;
    _domainName = domainName;
    _code = ValueFactory.getInstance().STvalueOfLiteral(code);
    _displayName = ValueFactory.getInstance().STvalueOfLiteral(displayName);
  }

  private EnumSet<TelecommunicationAddressUse> getImpliedConcepts() {
    if(_impliedConcepts == null) {
      if(_parent == null) { // then _parent2 is also null
	_impliedConcepts = EnumSet.of(root);
	_impliedConcepts.add(this);
      } else {
	_impliedConcepts  = EnumSet.copyOf(_parent.getImpliedConcepts());
	_impliedConcepts.add(this);
	if(_parent2 != null)
	  _impliedConcepts.addAll(_parent2.getImpliedConcepts());
      }
    }
    return _impliedConcepts;
  }

  public final BL equal(ANY that) {
    if(this == that)
      return BLimpl.TRUE;
    if (!(that instanceof CD))
      return BLimpl.FALSE;
    else if (that instanceof TelecommunicationAddressUse)
      return BLimpl.FALSE;
    else
      throw new UnsupportedOperationException("cannot handle argument of class " + that.getClass());
  }

  public BL implies(CD that) {
    if(this == that || this.equals(that))
      return BLimpl.TRUE;
    if(! (that instanceof TelecommunicationAddressUse))
      throw new UnsupportedOperationException("cannot handle argument of class " + that.getClass());
    final TelecommunicationAddressUse thatTelecommunicationAddressUse = (TelecommunicationAddressUse)that;
    return BLimpl.valueOf(getImpliedConcepts().contains(thatTelecommunicationAddressUse));
  }

  public TelecommunicationAddressUse mostSpecificGeneralization(CD that) {
    if(this == that || this.equals(that))
      return this;
    if(! (that instanceof TelecommunicationAddressUse))
      throw new UnsupportedOperationException("cannot handle argument of class " + that.getClass());
    final TelecommunicationAddressUse thatTelecommunicationAddressUse = (TelecommunicationAddressUse)that;

    // First build the intersection of the implied concepts
    // the remainder is a single path of which we have to
    // find the most specific concept, i.e., the one who
    // has all others as parents, i.e., the one with the
    // largest set of implied concepts.
    EnumSet<TelecommunicationAddressUse> intersection = EnumSet.copyOf(getImpliedConcepts());
    intersection.removeAll(EnumSet.complementOf(thatTelecommunicationAddressUse.getImpliedConcepts()));
    intersection.remove(root);

    // XXX: this iterative search is likely to be least optimal because
    // we probably have to search the path from the root here.
    // I don't know of any better way to do it right now though.

    TelecommunicationAddressUse mostSpecificKnownGeneralization = root;
    int maxKnownSpecificity = 1;

    for(TelecommunicationAddressUse candidate : intersection) {
      int specificity = candidate.getImpliedConcepts().size();
      if(specificity > maxKnownSpecificity) {
	maxKnownSpecificity = specificity;
	mostSpecificKnownGeneralization = candidate;
      }
    }

    return mostSpecificKnownGeneralization;
  }

  private static Map<ST,TelecommunicationAddressUse> _codeMap = null;

  public static TelecommunicationAddressUse valueOf(ST code) {
    // set up the _codeMap if needed for the first time
    if(_codeMap == null) {
      synchronized(TelecommunicationAddressUse.class) { // that one time might just as well keep it thread-safe
	if(_codeMap == null) {
	  _codeMap = new HashMap();
	  for(TelecommunicationAddressUse concept : EnumSet.allOf(TelecommunicationAddressUse.class)) {
	    ST conceptCode = concept.code();
	    if(conceptCode != null && conceptCode.nonNull().isTrue()) {
	      _codeMap.put(conceptCode,concept);
	    }
	  }
	}
      }
    }

    if(code.nonNull().isTrue()) {
      TelecommunicationAddressUse concept = _codeMap.get(code);
      if(concept == null)
        throw new IllegalArgumentException(("unknown code " + code + " at codeSystem = " +
          "2.16.840.1.113883.5.1011" + ", domain = " + "TelecommunicationAddressUse"));
      else
        return concept;
    } else 
      return null;
  }

  // INVARIANT BOILER PLATE CODE

  public String toString() {
    return code().toString();
  }

  private final String _domainName;
  private final ST _code;
  private final ST _displayName;

  public String domainName() { return _domainName; }
  public ST code() { return _code; }
  public ST displayName() { return _displayName; }
  public UID codeSystem() { return CODE_SYSTEM; }
  public ST codeSystemName() { return CODE_SYSTEM_NAME; }
  public ST codeSystemVersion() { return STnull.NI; }
  public ST originalText() { return STnull.NI; }
  public SET<CD> translation() { return SETnull.NA; }
  public LIST<CR> qualifier() { return LISTnull.NA; }

  public NullFlavor nullFlavor() { return NullFlavorImpl.NOT_A_NULL_FLAVOR; }
  public boolean isNullJ() { return false; }
  public boolean nonNullJ() { return true; }
  public boolean notApplicableJ() { return false; }
  public boolean unknownJ() { return false; }
  public boolean otherJ() { return false; }
  public BL isNull() { return BLimpl.FALSE; }
  public BL nonNull() { return BLimpl.TRUE; }
  public BL notApplicable() { return BLimpl.FALSE; }
  public BL unknown() { return BLimpl.FALSE; }
  public BL other() { return BLimpl.FALSE; }
}