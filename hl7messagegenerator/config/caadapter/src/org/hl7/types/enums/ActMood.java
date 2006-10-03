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

/** Codes distinguishing whether an Act is conceived of as a factual statement or in some other manner as a command, possibility,
goal, etc.<p>This table controls values for structural elements of the HL7 Reference Information Model. Therefore, it is part of the Normative
   Ballot for the RIM.
</p>
<p>This table controls values for structural elements of the HL7 Reference Information Model. Therefore, it is part of the Normative
   Ballot for the RIM.
</p> */
public enum ActMood implements CS {

  // ACTUAL DATA

  root(null, "ActMood", null, null),
  /** These are moods describing activities as they progress in the business cycle, from defined, through planned and ordered to
completed. */
  CompletionTrack(null, "ActMoodCompletionTrack", null, null),
  /** An intention or plan to perform a service. Historical note: in previous RIM versions, the intent mood was captured as a separate
class hierarchy, called Service_intent_or_order. */
  Intent(CompletionTrack, "ActMoodIntent", "INT", "intent"),
  /** A planned Act for a specific time and place. */
  Appointment(Intent, null, "APT", "appointment"),
  /** A request for the booking of an appointment. */
  AppointmentRequest(Intent, null, "ARQ", "appointment request"),
  /** An intent to perform a service that has the strength of a commitment, i.e., other parties may rely on the originator of such
promise that said originator will see to it that the promised act will be fulfilled. A promise can be either solicited or
unsolicited. */
  Promise(Intent, null, "PRMS", "promise"),
  /** A non-mandated intent to perform an act. Used to record intents that are explicitly not Orders. Professional responsibility
for the 'proposal' may or may not be present. */
  Proposal(Intent, null, "PRP", "proposal"),
  /** A request or order for a service is an intent directed from a placer (request author) to a fulfiller (service performer).<p>
   <emph>Rationale:</emph> The concepts of a "request" and an "order" are viewed as different, because there is an implication of a mandate associated
   with order. In practice, however, this distinction has no general functional value in the inter-operation of health care computing.
   "Orders" are commonly refused for a variety of clinical and business reasons, and the notion of a "request" obligates the
   recipient (the fulfiller) to respond to the sender (the author). Indeed, in many regions, including Australia and Europe,
   the common term used is "request."
</p>
<p>Thus, the concept embodies both notions, as there is no useful distinction to be made. If a mandate is to be associated with
   a request, this will be embodied in the "local" business rules applied to the transactions. Should HL7 desire to provide a
   distinction between these in the future, the individual concepts could be added as specializations of this concept.
</p>
<p>The critical distinction here, is the difference between this concept and an "intent", of which it is a specialization. An
   intent involves decisions by a single party, the author. A request, however, involves decisions by two parties, the author
   and the fulfiller, with an obligation on the part of the fulfiller to respond to the request indicating that the fulfiller
   will indeed fulfill the request.
</p> */
  Request(Intent, null, "RQO", "request"),
  /** Periods of time on a schedule for a resource. Appointments occupy sets of one or more booked slots. A slot that is open for
appointments is considered available and a slot that is held back for administrative purposes is considered blocked. A Resource
slot that is "tentatively" booked is referred to as reserved. */
  ResourceSlot(Intent, null, "SLOT", "resource slot"),
  /** A definition of a service (master).<p>Historical note: in previous RIM versions, the definition mood was captured as a separate class hierarchy, called Master_service.</p> */
  Definition(CompletionTrack, null, "DEF", "definition"),
  /** A service that actually happens, may be an ongoing service or a documentation of a past service.<p>Historical note: in previous RIM versions, the event mood was captured as a separate class hierarchy, called Patient_service_event,
   and later Service_event.
</p> */
  Event(CompletionTrack, null, "EVN", "event (occurrence)"),
  /** Any of the above service moods (e.g., event, intent, or goal) can be turned into a predicate used as a criterion to express
conditionals (or queries.) However, currently we allow only criteria on service events. */
  Predicate(null, "ActMoodPredicate", null, null),
  /** A criterion or condition over service events that must apply for an associated service to be considered. */
  EventCriterion(Predicate, null, "EVN.CRT", "event criterion"),
  /** Expectation to make a specific observation with a desired value at a predefined future time */
  Goal(Predicate, null, "GOL", "Goal"),
  /** An option is an alternative set of property-value bindings. Options specify alternative sets of values, typically used in
definitions or orders to describe alternatives. An option can only be used as a group, that is, all assigned values must be
used together.<p>Historical note: in HL7 v2.x option existed in the special case for alternative medication routes (RXR segment).</p> */
  Option(Predicate, null, "OPT", "option");

  // BOILER PLATE CODE IN WHICH ONLY THE ENUM NAME MUST BE INSERTED

  public static final UID CODE_SYSTEM = ValueFactory.getInstance().UIDvalueOfLiteral("2.16.840.1.113883.5.1001");
  public static final ST  CODE_SYSTEM_NAME = ValueFactory.getInstance().STvalueOfLiteral("ActMood");

  private final ActMood _parent;
  // duh! what a cheap way to make a set, but really we don't expect there to be too many more than 2 parents!
  private final ActMood _parent2;
  private EnumSet<ActMood> _impliedConcepts = null;

  private ActMood(ActMood parent, String domainName, String code, String displayName) {
    this(parent, null, domainName, code, displayName);
  }

  // duh! what a cheap way to make a set, but really we don't expect there to be too many more than 2 parents!
  private ActMood(ActMood parent, ActMood parent2, String domainName, String code, String displayName) {
    _parent = parent;
    _parent2 = parent2;
    _domainName = domainName;
    _code = ValueFactory.getInstance().STvalueOfLiteral(code);
    _displayName = ValueFactory.getInstance().STvalueOfLiteral(displayName);
  }

  private EnumSet<ActMood> getImpliedConcepts() {
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
    else if (that instanceof ActMood)
      return BLimpl.FALSE;
    else
      throw new UnsupportedOperationException("cannot handle argument of class " + that.getClass());
  }

  public BL implies(CD that) {
    if(this == that || this.equals(that))
      return BLimpl.TRUE;
    if(! (that instanceof ActMood))
      throw new UnsupportedOperationException("cannot handle argument of class " + that.getClass());
    final ActMood thatActMood = (ActMood)that;
    return BLimpl.valueOf(getImpliedConcepts().contains(thatActMood));
  }

  public ActMood mostSpecificGeneralization(CD that) {
    if(this == that || this.equals(that))
      return this;
    if(! (that instanceof ActMood))
      throw new UnsupportedOperationException("cannot handle argument of class " + that.getClass());
    final ActMood thatActMood = (ActMood)that;

    // First build the intersection of the implied concepts
    // the remainder is a single path of which we have to
    // find the most specific concept, i.e., the one who
    // has all others as parents, i.e., the one with the
    // largest set of implied concepts.
    EnumSet<ActMood> intersection = EnumSet.copyOf(getImpliedConcepts());
    intersection.removeAll(EnumSet.complementOf(thatActMood.getImpliedConcepts()));
    intersection.remove(root);

    // XXX: this iterative search is likely to be least optimal because
    // we probably have to search the path from the root here.
    // I don't know of any better way to do it right now though.

    ActMood mostSpecificKnownGeneralization = root;
    int maxKnownSpecificity = 1;

    for(ActMood candidate : intersection) {
      int specificity = candidate.getImpliedConcepts().size();
      if(specificity > maxKnownSpecificity) {
	maxKnownSpecificity = specificity;
	mostSpecificKnownGeneralization = candidate;
      }
    }

    return mostSpecificKnownGeneralization;
  }

  private static Map<ST,ActMood> _codeMap = null;

  public static ActMood valueOf(ST code) {
    // set up the _codeMap if needed for the first time
    if(_codeMap == null) {
      synchronized(ActMood.class) { // that one time might just as well keep it thread-safe
	if(_codeMap == null) {
	  _codeMap = new HashMap();
	  for(ActMood concept : EnumSet.allOf(ActMood.class)) {
	    ST conceptCode = concept.code();
	    if(conceptCode != null && conceptCode.nonNull().isTrue()) {
	      _codeMap.put(conceptCode,concept);
	    }
	  }
	}
      }
    }

    if(code.nonNull().isTrue()) {
      ActMood concept = _codeMap.get(code);
      if(concept == null)
        throw new IllegalArgumentException(("unknown code " + code + " at codeSystem = " +
          "2.16.840.1.113883.5.1001" + ", domain = " + "ActMood"));
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
