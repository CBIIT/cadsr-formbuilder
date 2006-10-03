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

/** Calendar cycle identifiers */
public enum CalendarCycle implements CS {

  // ACTUAL DATA

  root(null, "CalendarCycle", null, null),
  /**  */
  OneLetter(null, "CalendarCycleOneLetter", null, null),
  /**  */
  TwoLetter(null, "CalendarCycleTwoLetter", null, null),
  /**  */
  GregorianCalendarCycle(TwoLetter, "GregorianCalendarCycle", null, null),
  /**  */
  Day(TwoLetter, null, "CD", "day (continuous)"),
  /**  */
  Hour(TwoLetter, null, "CH", "hour (continuous)"),
  /**  */
  Month(TwoLetter, null, "CM", "month (continuous)"),
  /**  */
  Minute(TwoLetter, null, "CN", "minute (continuous)"),
  /**  */
  Second(TwoLetter, null, "CS", "second (continuous)"),
  /**  */
  Week(OneLetter, TwoLetter, null, "CW", "week (continuous)"),
  /**  */
  Year(OneLetter, TwoLetter, null, "CY", "year"),
  /**  */
  DayOfTheMonth(OneLetter, TwoLetter, null, "DM", "day of the month"),
  /**  */
  DayOfTheWeek(OneLetter, TwoLetter, null, "DW", "day of the week (begins with Monday)"),
  /**  */
  DayOfTheYear(TwoLetter, null, "DY", "day of the year"),
  /**  */
  HourOfTheDay(OneLetter, TwoLetter, null, "HD", "hour of the day"),
  /**  */
  MonthOfTheYear(OneLetter, TwoLetter, null, "MY", "month of the year"),
  /**  */
  MinuteOfTheHour(OneLetter, TwoLetter, null, "NH", "minute of the hour"),
  /**  */
  SecondOfTheMinute(OneLetter, TwoLetter, null, "SN", "second of the minute"),
  /**  */
  WeekOfTheYear(TwoLetter, null, "WY", "week of the year");

  // BOILER PLATE CODE IN WHICH ONLY THE ENUM NAME MUST BE INSERTED

  public static final UID CODE_SYSTEM = ValueFactory.getInstance().UIDvalueOfLiteral("2.16.840.1.113883.5.9");
  public static final ST  CODE_SYSTEM_NAME = ValueFactory.getInstance().STvalueOfLiteral("CalendarCycle");

  private final CalendarCycle _parent;
  // duh! what a cheap way to make a set, but really we don't expect there to be too many more than 2 parents!
  private final CalendarCycle _parent2;
  private EnumSet<CalendarCycle> _impliedConcepts = null;

  private CalendarCycle(CalendarCycle parent, String domainName, String code, String displayName) {
    this(parent, null, domainName, code, displayName);
  }

  // duh! what a cheap way to make a set, but really we don't expect there to be too many more than 2 parents!
  private CalendarCycle(CalendarCycle parent, CalendarCycle parent2, String domainName, String code, String displayName) {
    _parent = parent;
    _parent2 = parent2;
    _domainName = domainName;
    _code = ValueFactory.getInstance().STvalueOfLiteral(code);
    _displayName = ValueFactory.getInstance().STvalueOfLiteral(displayName);
  }

  private EnumSet<CalendarCycle> getImpliedConcepts() {
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
    else if (that instanceof CalendarCycle)
      return BLimpl.FALSE;
    else
      throw new UnsupportedOperationException("cannot handle argument of class " + that.getClass());
  }

  public BL implies(CD that) {
    if(this == that || this.equals(that))
      return BLimpl.TRUE;
    if(! (that instanceof CalendarCycle))
      throw new UnsupportedOperationException("cannot handle argument of class " + that.getClass());
    final CalendarCycle thatCalendarCycle = (CalendarCycle)that;
    return BLimpl.valueOf(getImpliedConcepts().contains(thatCalendarCycle));
  }

  public CalendarCycle mostSpecificGeneralization(CD that) {
    if(this == that || this.equals(that))
      return this;
    if(! (that instanceof CalendarCycle))
      throw new UnsupportedOperationException("cannot handle argument of class " + that.getClass());
    final CalendarCycle thatCalendarCycle = (CalendarCycle)that;

    // First build the intersection of the implied concepts
    // the remainder is a single path of which we have to
    // find the most specific concept, i.e., the one who
    // has all others as parents, i.e., the one with the
    // largest set of implied concepts.
    EnumSet<CalendarCycle> intersection = EnumSet.copyOf(getImpliedConcepts());
    intersection.removeAll(EnumSet.complementOf(thatCalendarCycle.getImpliedConcepts()));
    intersection.remove(root);

    // XXX: this iterative search is likely to be least optimal because
    // we probably have to search the path from the root here.
    // I don't know of any better way to do it right now though.

    CalendarCycle mostSpecificKnownGeneralization = root;
    int maxKnownSpecificity = 1;

    for(CalendarCycle candidate : intersection) {
      int specificity = candidate.getImpliedConcepts().size();
      if(specificity > maxKnownSpecificity) {
	maxKnownSpecificity = specificity;
	mostSpecificKnownGeneralization = candidate;
      }
    }

    return mostSpecificKnownGeneralization;
  }

  private static Map<ST,CalendarCycle> _codeMap = null;

  public static CalendarCycle valueOf(ST code) {
    // set up the _codeMap if needed for the first time
    if(_codeMap == null) {
      synchronized(CalendarCycle.class) { // that one time might just as well keep it thread-safe
	if(_codeMap == null) {
	  _codeMap = new HashMap();
	  for(CalendarCycle concept : EnumSet.allOf(CalendarCycle.class)) {
	    ST conceptCode = concept.code();
	    if(conceptCode != null && conceptCode.nonNull().isTrue()) {
	      _codeMap.put(conceptCode,concept);
	    }
	  }
	}
      }
    }

    if(code.nonNull().isTrue()) {
      CalendarCycle concept = _codeMap.get(code);
      if(concept == null)
        throw new IllegalArgumentException(("unknown code " + code + " at codeSystem = " +
          "2.16.840.1.113883.5.9" + ", domain = " + "CalendarCycle"));
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
