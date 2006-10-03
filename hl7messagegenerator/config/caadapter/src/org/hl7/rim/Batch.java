/*
THIS FILE IS GENERATED AUTOMATICALLY - DO NOT MODIFY.

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
package org.hl7.rim;

import org.hl7.rim.Transmission;
import org.hl7.types.CS;
import org.hl7.types.II;
import org.hl7.types.SC;
import org.hl7.types.SET;
import org.hl7.types.ST;
import org.hl7.types.INT;
import org.hl7.types.INT;

import org.hl7.rim.Transmission;
import /*org.hl7.rim.AssociationSet*/java.util.List;

/**<p>The Batch class is used to specify a message which is a collection of HL7 V3 messages.</p>
*/
public interface Batch extends Transmission {

  /**<p>This attribute is matched by the receiving system to its own version to be sure the message will be interpreted correctly.</p>
<p><i>Note:</i> This attribute is also present in the sibling class, Batch. This change was made rather than moving this attribute to their
   common ancestor class, Transmission. This decision was taken because we do not have all the methodology and backwards compatibility
   issues worked out. Once we have established our backwards compatibility, we should promote this attribute to the parent. The
   problem is the sequencing of attributes within the HDF and their impact on the ITSs.
</p>
  */
  CS getVersionCode();
  /** Sets the property versionCode.
      @see #getVersionCode
  */
  void setVersionCode(CS versionCode);

  /**<p>The interaction identifier is a reference to the unique information interchange derived from the V3 MDF for specifying a message.</p>
<p><i>Note:</i> This attribute is also present in the sibling class, Message. This change was made rather than moving this attribute to their
   common ancestor class, Transmission. This decision was taken because we do not have all the methodology and backwards compatibility
   issues worked out. Once we have established our backwards compatibility, we should promote this attribute to the parent. The
   problem is the sequencing of attributes within the HDF and their impact on the ITSs.
</p>
  */
  II getInteractionId();
  /** Sets the property interactionId.
      @see #getInteractionId
  */
  void setInteractionId(II interactionId);

  /**<p>This attribute indicates the control identifier of the batch when it was originally transmitted.</p>
  */
  II getReferenceControlId();
  /** Sets the property referenceControlId.
      @see #getReferenceControlId
  */
  void setReferenceControlId(II referenceControlId);

  /**<p>This attribute is used by the application processing the batch.</p>
  */
  SC getName();
  /** Sets the property name.
      @see #getName
  */
  void setName(SC name);

  /**<p>This attribute is available to capture comments related to the batch.</p>
  */
  SET<ST> getBatchComment();
  /** Sets the property batchComment.
      @see #getBatchComment
  */
  void setBatchComment(SET<ST> batchComment);

  /**<p>This attribute contains the count of individual transmissions contained within the batch.</p>
  */
  INT getTransmissionQuantity();
  /** Sets the property transmissionQuantity.
      @see #getTransmissionQuantity
  */
  void setTransmissionQuantity(INT transmissionQuantity);

  /**<p>The batch total. It is possible that more than a single batch total exists.</p>
  */
  SET<INT> getBatchTotalNumber();
  /** Sets the property batchTotalNumber.
      @see #getBatchTotalNumber
  */
  void setBatchTotalNumber(SET<INT> batchTotalNumber);

  /**
  */
  /*AssociationSet*/List<Transmission> getTransmission();
  /** Sets the property transmission.
      @see #getTransmission
  */
  void setTransmission(/*AssociationSet*/List<Transmission> transmission);
  /** Adds an association transmission.
      @see #addTransmission
  */
  void addTransmission(Transmission transmission);
}
