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

/** A code specifying the meaning and purpose of every Participation instance. Each of its values implies specific constraints
on the Roles undertaking the participation.<p>This table controls values for structural elements of the HL7 Reference Information Model. Therefore, it is part of the Normative
   Ballot for the RIM.
</p>
<p>This table controls values for structural elements of the HL7 Reference Information Model. Therefore, it is part of the Normative
   Ballot for the RIM.
</p> */
public enum ParticipationType implements CS {

  // ACTUAL DATA

  root(null, "ParticipationType", null, null),
  /** Participations related, but not primary to an act. The Referring, Admitting, and Discharging practitioners must be the same
person as those authoring the ControlAct event for their respective trigger events. */
  ParticipationAncillary(null, "ParticipationAncillary", null, null),
  /** The practitioner who is responsible for admitting a patient to a hospital stay. */
  Admitter(ParticipationAncillary, null, "ADM", "admitter"),
  /** The attending practitioner that has responsibility for a patient's care during a hospital stay. */
  Attender(ParticipationAncillary, null, "ATND", "attender"),
  /** An advisor participating in the service by performing evaluations and making recommendations. */
  Consultant(ParticipationAncillary, null, "CON", "consultant"),
  /** The practitioner who is responsible for the discharge of a patient from a hospital stay. */
  Discharger(ParticipationAncillary, null, "DIS", "discharger"),
  /** Only with Transportation services. A person who escorts the patient. */
  Escort(ParticipationAncillary, null, "ESC", "escort"),
  /** A person having referred the subject of the service to the performer (referring physician). Typically, a referring physician
will receive a report. */
  Referrer(ParticipationAncillary, null, "REF", "referrer"),
  /** Target that is not substantially present in the act and which is not directly affected by the act, but which will be a focus
of the record or documentation of the act. */
  ParticipationIndirectTarget(null, "ParticipationIndirectTarget", "IND", "indirect target"),
  /** Target on behalf of whom the service happens, but that is not necessarily present in the service. Can occur together with
direct target to indicate that a target is both. Includes, a participant who derives benefits from an act, such as a covered
party */
  Beneficiary(ParticipationIndirectTarget, null, "BEN", "beneficiary"),
  /** The target participation for an individual in a health care coverage act in which the target role is either the policy holder
of the coverage, or a covered party under the coverage. */
  CoverageTarget(ParticipationIndirectTarget, null, "COV", "coverage target"),
  /** Participant who posses an instrument such as a financial contract (insurance policy) usually based on some agreement with
the author. */
  Holder(ParticipationIndirectTarget, null, "HLD", "holder"),
  /** The record target indicates whose medical record holds the documentation of this act. This is especially important when the
subject of a service is not the patient himself. */
  RecordTarget(ParticipationIndirectTarget, null, "RCT", "record target"),
  /** The person (or organization) who receives the product of an Act. */
  Receiver(ParticipationIndirectTarget, null, "RCV", "receiver"),
  /** Parties that may or should contribute or have contributed information to the Act. Such information includes information leading
to the decision to perform the Act and how to perform the Act (e.g., consultant), information that the Act itself seeks to
reveal (e.g., informant of clinical history), or information about what Act was performed (e.g., informant witness). */
  ParticipationInformationGenerator(null, "ParticipationInformationGenerator", null, null),
  /** A party that originates the Act and therefore has responsibility for the information given in the Act and ownership of this
Act. Example: the report writer, the person writing the act definition, the guideline author, the placer of an order, the
EKG cart (device) creating a report etc. Every Act should have an author. Authorship is regardless of mood always actual authorship.
The author (or authors) has ownership of the Acts that they originate. This means that a party other than this author (or
those authors) cannot cancel, abort, complete or modify the state or content of this Act in any other way. A party other than
the author may only amend, reverse, override, replace, or follow up in other ways on this Act, whereby the Act remains intact
and is linked to another Act authored by that other party. */
  Author(ParticipationInformationGenerator, null, "AUT", "author (originator)"),
  /** A person entering the data into the originating system. The data entry person is collected optionally for internal quality
control purposes. This includes the transcriptionist for dictated text. */
  DataEntryPerson(ParticipationInformationGenerator, null, "ENT", "data entry person"),
  /** A source of reported information (e.g., a next of kin who answers questions about the patient's history). For history questions,
the patient is logically an informant, yet the informant of history questions is implicitly the subject. */
  Informant(ParticipationInformationGenerator, null, "INF", "informant"),
  /** Only with service events. A person witnessing the action happening without doing anything. A witness is not necessarily aware,
much less approves of anything stated in the service event. Example for a witness is students watching an operation or an
advanced directive witness. */
  Witness(ParticipationInformationGenerator, null, "WIT", "witness"),
  /** A party, who may or should receive or who has recieved the Act or subsequent or derivative information of that Act. Information
recipient is inert, i.e., independent of mood." Rationale: this is a generalization of a too diverse family that the definition
can't be any more specific, and the concept is abstract so one of the specializations should be used. */
  ParticipationInformationRecipient(null, "ParticipationInformationRecipient", null, null),
  /** An information recipient to notify for urgent matters about this Act. (e.g., in a laboratory order, critical results are being
called by phone right away, this is the contact to call; or for an inpatient encounter, a next of kin to notify when the patient
becomes critically ill). */
  UgentNotificationContact(ParticipationInformationRecipient, null, "NOT", "ugent notification contact"),
  /** Information recipient to whom an act statement is primarily directed. E.g., a primary care provider receiving a discharge
letter from a hospitalist, a health department receiving information on a suspected case of infectious disease. Multiple of
these participations may exist on the same act without requiring that recipients be ranked as primary vs. secondary. */
  PrimaryInformationRecipient(ParticipationInformationRecipient, null, "PRCP", "primary information recipient"),
  /** A participant (e.g. provider) who has referred the subject of an act (e.g. patient).<p>Typically, a referred by participant will provide a report (e.g. referral).</p> */
  ReferredBy(ParticipationInformationRecipient, null, "REFB", "Referred By"),
  /** The person who receives the patient */
  ReferredTo(ParticipationInformationRecipient, null, "REFT", "Referred to"),
  /** A secondary information recipient, who receives copies (e.g., a primary care provider receiving copies of results as ordered
by specialist). */
  Tracker(ParticipationInformationRecipient, null, "TRC", "tracker"),
  /** A person who actually and principally carries out the action. Need not be the principal responsible actor, e.g. a surgery
resident operating under supervision of attending surgeon, and may be the patient in self-care, e.g. fingerstick blood sugar.
The traditional order filler is a performer. This information should accompany every service event. */
  ParticipationPhysicalPerformer(null, "ParticipationPhysicalPerformer", "PRF", "performer"),
  /** Distributes material used in or generated during the act. */
  Distributor(ParticipationPhysicalPerformer, null, "DIST", "distributor"),
  /** The principal or primary performer of the act. */
  PrimaryPerformer(ParticipationPhysicalPerformer, null, "PPRF", "primary performer"),
  /** A person assisting in an act through his substantial presence and involvement This includes: assistants, technicians, associates,
or whatever the job titles may be. */
  SecondaryPerformer(ParticipationPhysicalPerformer, null, "SPRF", "secondary performer"),
  /** Target that is substantially present in the service and which is directly affected by the service action (includes consumed
material, devices, etc.). */
  ParticipationTargetDirect(null, "ParticipationTargetDirect", "DIR", "direct target"),
  /** Target that is taken up, is diminished, and disappears in the service. */
  ParticipationConsumable(ParticipationTargetDirect, "ParticipationConsumable", "CSM", "consumable"),
  /** Something incorporated in the subject of a therapy service to achieve a physiologic effect (e.g., heal, relieve, provoke a
condition, etc.) on the subject. In an administration service the therapeutic agent is a consumable, in a preparation or dispense
service, it is a product. Thus, consumable or product must be specified in accordance with the kind of service. */
  TherapeuticAgent(ParticipationConsumable, null, "TPA", "therapeutic agent"),
  /** Something used in delivering the service without being substantially affected by the service (i.e. durable or inert with respect
to that particular service). Examples are: monitoring equipment, tools, but also access/drainage lines, prostheses, pace maker,
etc. */
  ParticipationTargetDevice(ParticipationTargetDirect, "ParticipationTargetDevice", "DEV", "device"),
  /** A device that changes ownership due to the service, e.g., a pacemaker, a prosthesis, an insulin injection equipment (pen),
etc. Such material may need to be restocked after he service. */
  NonReuseableDevice(ParticipationTargetDevice, null, "NRD", "non-reuseable device"),
  /** A device that does not change ownership due to the service, i.e., a surgical instrument or tool or an endoscope. The distinction
between reuseable and non-reuseable must be made in order to know whether material must be re-stocked. */
  ReusableDevice(ParticipationTargetDevice, null, "RDV", "reusable device"),
  /** The principle target that the service acts on. E.g. the patient in physical examination, a specimen in a lab observation.
May also be a patient's family member (teaching) or a device or room (cleaning, disinfecting, housekeeping). Note: not all
direct targets are subjects, consumables, and devices used as tools for a service are not subjects. However, a device may
be a subject of a maintenance service. */
  ParticipationTargetSubject(ParticipationTargetDirect, "ParticipationTargetSubject", "SBJ", "subject"),
  /** The subject of non-clinical (e.g. laboratory) observation services is a specimen. */
  Specimen(ParticipationTargetSubject, null, "SPC", "specimen"),
  /** In an obstetric service, the baby. */
  Baby(ParticipationTargetDirect, null, "BBY", "baby"),
  /** In some organ transplantation services and rarely in transfusion services a donor will be a target participant in the service.
However, in most cases transplantation is decomposed in three services: explantation, transport, and implantation. The identity
of the donor (recipient) is often irrelevant for the explantation (implantation) service. */
  Donor(ParticipationTargetDirect, null, "DON", "donor"),
  /** A material target that is brought forth (produced) in the service (e.g., specimen in a specimen collection, access or drainage
in a placement service, medication package in a dispense service). It doesn't matter whether the material produced had existence
prior to the service, or whether it is created in the service (e.g., in supply services the product is taken from a stock). */
  Product(ParticipationTargetDirect, null, "PRD", "product"),
  /** The facility where the service is done. May be a static building (or room therein) or a moving location (e.g., ambulance,
helicopter, aircraft, train, truck, ship, etc.) */
  ParticipationTargetLocation(null, "ParticipationTargetLocation", "LOC", "location"),
  /** The destination for services. May be a static building (or room therein) or a movable facility (e.g., ship). */
  Destination(ParticipationTargetLocation, null, "DST", "destination"),
  /** A location where data about an Act was entered. */
  EntryLocation(ParticipationTargetLocation, null, "ELOC", "entry location"),
  /** The location of origin for services. May be a static building (or room therein) or a movable facility (e.g., ship). */
  Origin(ParticipationTargetLocation, null, "ORG", "origin"),
  /** Some services take place at multiple concurrent locations (e.g., telemedicine, telephone consultation). The location where
the principal performing actor is located is taken as the primary location (LOC) while the other location(s) are considered
"remote." */
  Remote(ParticipationTargetLocation, null, "RML", "remote"),
  /** For services, an intermediate location that specifies a path between origin an destination. */
  Via(ParticipationTargetLocation, null, "VIA", "via"),
  /** A person who verifies the correctness and appropriateness of the service (plan, order, event, etc.) and hence takes on accountability. */
  ParticipationVerifier(null, "ParticipationVerifier", "VRF", "verifier"),
  /** A verifier who attests to the accuracy of an act, but who does not have privileges to legally authenticate the act. An example
would be a resident physician who sees a patient and dictates a note, then later signs it. Their signature constitutes an
authentication. */
  Authenticator(ParticipationVerifier, null, "AUTHEN", "authenticator"),
  /** A verifier who legally authenticates the accuracy of an act. An example would be a staff physician who sees a patient and
dictates a note, then later signs it. Their signature constitutes a legal authentication. */
  LegalAuthenticator(ParticipationVerifier, null, "LA", "legal authenticator"),
  /** A person (or organization) who is in charge of maintaining the information of this service object (e.g., who maintains the
report or the master service catalog item, etc.). */
  Custodian(null, null, "CST", "custodian"),
  /** The provider (person or organization) who has primary responsibility for the act. The responsible provider is not necessarily
present in an action, but is accountable for the action through the power to delegate, and the duty to review actions with
the performing actor after the fact (e.g. head of a biochemical laboratory). */
  ResponsibleParty(null, null, "RESP", "responsible party");

  // BOILER PLATE CODE IN WHICH ONLY THE ENUM NAME MUST BE INSERTED

  public static final UID CODE_SYSTEM = ValueFactory.getInstance().UIDvalueOfLiteral("2.16.840.1.113883.5.90");
  public static final ST  CODE_SYSTEM_NAME = ValueFactory.getInstance().STvalueOfLiteral("ParticipationType");

  private final ParticipationType _parent;
  // duh! what a cheap way to make a set, but really we don't expect there to be too many more than 2 parents!
  private final ParticipationType _parent2;
  private EnumSet<ParticipationType> _impliedConcepts = null;

  private ParticipationType(ParticipationType parent, String domainName, String code, String displayName) {
    this(parent, null, domainName, code, displayName);
  }

  // duh! what a cheap way to make a set, but really we don't expect there to be too many more than 2 parents!
  private ParticipationType(ParticipationType parent, ParticipationType parent2, String domainName, String code, String displayName) {
    _parent = parent;
    _parent2 = parent2;
    _domainName = domainName;
    _code = ValueFactory.getInstance().STvalueOfLiteral(code);
    _displayName = ValueFactory.getInstance().STvalueOfLiteral(displayName);
  }

  private EnumSet<ParticipationType> getImpliedConcepts() {
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
    else if (that instanceof ParticipationType)
      return BLimpl.FALSE;
    else
      throw new UnsupportedOperationException("cannot handle argument of class " + that.getClass());
  }

  public BL implies(CD that) {
    if(this == that || this.equals(that))
      return BLimpl.TRUE;
    if(! (that instanceof ParticipationType))
      throw new UnsupportedOperationException("cannot handle argument of class " + that.getClass());
    final ParticipationType thatParticipationType = (ParticipationType)that;
    return BLimpl.valueOf(getImpliedConcepts().contains(thatParticipationType));
  }

  public ParticipationType mostSpecificGeneralization(CD that) {
    if(this == that || this.equals(that))
      return this;
    if(! (that instanceof ParticipationType))
      throw new UnsupportedOperationException("cannot handle argument of class " + that.getClass());
    final ParticipationType thatParticipationType = (ParticipationType)that;

    // First build the intersection of the implied concepts
    // the remainder is a single path of which we have to
    // find the most specific concept, i.e., the one who
    // has all others as parents, i.e., the one with the
    // largest set of implied concepts.
    EnumSet<ParticipationType> intersection = EnumSet.copyOf(getImpliedConcepts());
    intersection.removeAll(EnumSet.complementOf(thatParticipationType.getImpliedConcepts()));
    intersection.remove(root);

    // XXX: this iterative search is likely to be least optimal because
    // we probably have to search the path from the root here.
    // I don't know of any better way to do it right now though.

    ParticipationType mostSpecificKnownGeneralization = root;
    int maxKnownSpecificity = 1;

    for(ParticipationType candidate : intersection) {
      int specificity = candidate.getImpliedConcepts().size();
      if(specificity > maxKnownSpecificity) {
	maxKnownSpecificity = specificity;
	mostSpecificKnownGeneralization = candidate;
      }
    }

    return mostSpecificKnownGeneralization;
  }

  private static Map<ST,ParticipationType> _codeMap = null;

  public static ParticipationType valueOf(ST code) {
    // set up the _codeMap if needed for the first time
    if(_codeMap == null) {
      synchronized(ParticipationType.class) { // that one time might just as well keep it thread-safe
	if(_codeMap == null) {
	  _codeMap = new HashMap();
	  for(ParticipationType concept : EnumSet.allOf(ParticipationType.class)) {
	    ST conceptCode = concept.code();
	    if(conceptCode != null && conceptCode.nonNull().isTrue()) {
	      _codeMap.put(conceptCode,concept);
	    }
	  }
	}
      }
    }

    if(code.nonNull().isTrue()) {
      ParticipationType concept = _codeMap.get(code);
      if(concept == null)
        throw new IllegalArgumentException(("unknown code " + code + " at codeSystem = " +
          "2.16.840.1.113883.5.90" + ", domain = " + "ParticipationType"));
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
