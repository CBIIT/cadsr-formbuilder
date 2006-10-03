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

import org.hl7.rim.InfrastructureRoot;
import org.hl7.types.II;
import org.hl7.types.TS;
import org.hl7.types.ST;
import org.hl7.types.CS;

import org.hl7.rim.Acknowledgement;
import org.hl7.rim.Attachment;
import org.hl7.rim.AttentionLine;
import org.hl7.rim.Batch;
import org.hl7.rim.CommunicationFunction;
import /*org.hl7.rim.AssociationSet*/java.util.List;

/**<p>Represents information about a specific transmission of information from one application to another.</p>
*/
public interface Transmission extends InfrastructureRoot {

  /**<p>Unique identifier of the transmission.</p>
  */
  II getId();
  /** Sets the property id.
      @see #getId
  */
  void setId(II id);

  /**<p>The date/time that the sending system created the transmission. If the time zone is specified, it will be used throughout
   the transmission as the default time zone.
</p>
  */
  TS getCreationTime();
  /** Sets the property creationTime.
      @see #getCreationTime
  */
  void setCreationTime(TS creationTime);

  /**<p>This attribute is specified for applications to implement security features for a transmission. Its use is not further specified
   at this time.
</p>
  */
  ST getSecurityText();
  /** Sets the property securityText.
      @see #getSecurityText
  */
  void setSecurityText(ST securityText);

  /**<p>This attribute defines the transmission mode with which a receiver should communicate its receiver responsibilities, i.e.
   the expected dynamic behavior of the receiving application with regard to application response transmissions.
</p>
  */
  CS getResponseModeCode();
  /** Sets the property responseModeCode.
      @see #getResponseModeCode
  */
  void setResponseModeCode(CS responseModeCode);

  /**
  */
  /*AssociationSet*/List<Acknowledgement> getAcknowledgedBy();
  /** Sets the property acknowledgedBy.
      @see #getAcknowledgedBy
  */
  void setAcknowledgedBy(/*AssociationSet*/List<Acknowledgement> acknowledgedBy);
  /** Adds an association acknowledgedBy.
      @see #addAcknowledgedBy
  */
  void addAcknowledgedBy(Acknowledgement acknowledgedBy);

  /**
  */
  /*AssociationSet*/List<Acknowledgement> getConveyedAcknowledgement();
  /** Sets the property conveyedAcknowledgement.
      @see #getConveyedAcknowledgement
  */
  void setConveyedAcknowledgement(/*AssociationSet*/List<Acknowledgement> conveyedAcknowledgement);
  /** Adds an association conveyedAcknowledgement.
      @see #addConveyedAcknowledgement
  */
  void addConveyedAcknowledgement(Acknowledgement conveyedAcknowledgement);

  /**
  */
  /*AssociationSet*/List<Attachment> getAttachment();
  /** Sets the property attachment.
      @see #getAttachment
  */
  void setAttachment(/*AssociationSet*/List<Attachment> attachment);
  /** Adds an association attachment.
      @see #addAttachment
  */
  void addAttachment(Attachment attachment);

  /**
  */
  /*AssociationSet*/List<AttentionLine> getAttentionLine();
  /** Sets the property attentionLine.
      @see #getAttentionLine
  */
  void setAttentionLine(/*AssociationSet*/List<AttentionLine> attentionLine);
  /** Adds an association attentionLine.
      @see #addAttentionLine
  */
  void addAttentionLine(AttentionLine attentionLine);

  /**
  */
  Batch getBatch();
  /** Sets the property batch.
      @see #getBatch
  */
  void setBatch(Batch batch);

  /**
  */
  /*AssociationSet*/List<CommunicationFunction> getCommunicationFunction();
  /** Sets the property communicationFunction.
      @see #getCommunicationFunction
  */
  void setCommunicationFunction(/*AssociationSet*/List<CommunicationFunction> communicationFunction);
  /** Adds an association communicationFunction.
      @see #addCommunicationFunction
  */
  void addCommunicationFunction(CommunicationFunction communicationFunction);
}
