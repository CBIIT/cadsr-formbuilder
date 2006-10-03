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
package org.hl7.rim.impl;

import org.hibernate.Criteria;
import org.hl7.hibernate.Persistence;

import org.hl7.rim.Transmission;
import org.hl7.rim.impl.InfrastructureRootImpl;
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

/** Implementation of org.hl7.rim.Transmission as a simple data holder bean.
    @see org.hl7.rim.Transmission
  */
public class TransmissionImpl extends InfrastructureRootImpl implements Transmission {

  private II _id;
  /** Gets the property id.
      @see org.hl7.rim.Transmission#getId
  */
  public II getId() { return _id; }
  /** Sets the property id.
      @see org.hl7.rim.Transmission#setId
  */
  public void setId(II id) {
    if(id instanceof org.hl7.hibernate.ClonableCollection)
      id = ((org.hl7.hibernate.ClonableCollection<II>) id).cloneHibernateCollectionIfNecessary();
    _id = id;
  }

  private TS _creationTime;
  /** Gets the property creationTime.
      @see org.hl7.rim.Transmission#getCreationTime
  */
  public TS getCreationTime() { return _creationTime; }
  /** Sets the property creationTime.
      @see org.hl7.rim.Transmission#setCreationTime
  */
  public void setCreationTime(TS creationTime) {
    if(creationTime instanceof org.hl7.hibernate.ClonableCollection)
      creationTime = ((org.hl7.hibernate.ClonableCollection<TS>) creationTime).cloneHibernateCollectionIfNecessary();
    _creationTime = creationTime;
  }

  private ST _securityText;
  /** Gets the property securityText.
      @see org.hl7.rim.Transmission#getSecurityText
  */
  public ST getSecurityText() { return _securityText; }
  /** Sets the property securityText.
      @see org.hl7.rim.Transmission#setSecurityText
  */
  public void setSecurityText(ST securityText) {
    if(securityText instanceof org.hl7.hibernate.ClonableCollection)
      securityText = ((org.hl7.hibernate.ClonableCollection<ST>) securityText).cloneHibernateCollectionIfNecessary();
    _securityText = securityText;
  }

  private CS _responseModeCode;
  /** Gets the property responseModeCode.
      @see org.hl7.rim.Transmission#getResponseModeCode
  */
  public CS getResponseModeCode() { return _responseModeCode; }
  /** Sets the property responseModeCode.
      @see org.hl7.rim.Transmission#setResponseModeCode
  */
  public void setResponseModeCode(CS responseModeCode) {
    if(responseModeCode instanceof org.hl7.hibernate.ClonableCollection)
      responseModeCode = ((org.hl7.hibernate.ClonableCollection<CS>) responseModeCode).cloneHibernateCollectionIfNecessary();
    _responseModeCode = responseModeCode;
  }

  private /*AssociationSet*/List<Acknowledgement> _acknowledgedBy;
  /** Gets the property acknowledgedBy.
      @see org.hl7.rim.Transmission#getAcknowledgedBy
  */
  public /*AssociationSet*/List<Acknowledgement> getAcknowledgedBy() {
    return _acknowledgedBy;
  }
  /** Sets the property acknowledgedBy.
      @see org.hl7.rim.Transmission#setAcknowledgedBy
  */
  public void setAcknowledgedBy(/*AssociationSet*/List<Acknowledgement> acknowledgedBy) {
    _acknowledgedBy = acknowledgedBy;
  }
  /** Adds an association acknowledgedBy.
      @see org.hl7.rim.Transmission#setAcknowledgedBy
  */
  public void addAcknowledgedBy(Acknowledgement acknowledgedBy) {
        // create the association set if it doesn't exist already
    if(_acknowledgedBy == null) _acknowledgedBy = new AssociationSetImpl<Acknowledgement>();
    // add the association to the association set
    getAcknowledgedBy().add(acknowledgedBy);
    // make the inverse link
    acknowledgedBy.setAcknowledges(this);
  }

  private /*AssociationSet*/List<Acknowledgement> _conveyedAcknowledgement;
  /** Gets the property conveyedAcknowledgement.
      @see org.hl7.rim.Transmission#getConveyedAcknowledgement
  */
  public /*AssociationSet*/List<Acknowledgement> getConveyedAcknowledgement() {
    return _conveyedAcknowledgement;
  }
  /** Sets the property conveyedAcknowledgement.
      @see org.hl7.rim.Transmission#setConveyedAcknowledgement
  */
  public void setConveyedAcknowledgement(/*AssociationSet*/List<Acknowledgement> conveyedAcknowledgement) {
    _conveyedAcknowledgement = conveyedAcknowledgement;
  }
  /** Adds an association conveyedAcknowledgement.
      @see org.hl7.rim.Transmission#setConveyedAcknowledgement
  */
  public void addConveyedAcknowledgement(Acknowledgement conveyedAcknowledgement) {
        // create the association set if it doesn't exist already
    if(_conveyedAcknowledgement == null) _conveyedAcknowledgement = new AssociationSetImpl<Acknowledgement>();
    // add the association to the association set
    getConveyedAcknowledgement().add(conveyedAcknowledgement);
    // make the inverse link
    conveyedAcknowledgement.setConveyingTransmission(this);
  }

  private /*AssociationSet*/List<Attachment> _attachment;
  /** Gets the property attachment.
      @see org.hl7.rim.Transmission#getAttachment
  */
  public /*AssociationSet*/List<Attachment> getAttachment() {
    return _attachment;
  }
  /** Sets the property attachment.
      @see org.hl7.rim.Transmission#setAttachment
  */
  public void setAttachment(/*AssociationSet*/List<Attachment> attachment) {
    _attachment = attachment;
  }
  /** Adds an association attachment.
      @see org.hl7.rim.Transmission#setAttachment
  */
  public void addAttachment(Attachment attachment) {
        // create the association set if it doesn't exist already
    if(_attachment == null) _attachment = new AssociationSetImpl<Attachment>();
    // add the association to the association set
    getAttachment().add(attachment);
    // make the inverse link
    attachment.setTransmission(this);
  }

  private /*AssociationSet*/List<AttentionLine> _attentionLine;
  /** Gets the property attentionLine.
      @see org.hl7.rim.Transmission#getAttentionLine
  */
  public /*AssociationSet*/List<AttentionLine> getAttentionLine() {
    return _attentionLine;
  }
  /** Sets the property attentionLine.
      @see org.hl7.rim.Transmission#setAttentionLine
  */
  public void setAttentionLine(/*AssociationSet*/List<AttentionLine> attentionLine) {
    _attentionLine = attentionLine;
  }
  /** Adds an association attentionLine.
      @see org.hl7.rim.Transmission#setAttentionLine
  */
  public void addAttentionLine(AttentionLine attentionLine) {
        // create the association set if it doesn't exist already
    if(_attentionLine == null) _attentionLine = new AssociationSetImpl<AttentionLine>();
    // add the association to the association set
    getAttentionLine().add(attentionLine);
    // make the inverse link
    attentionLine.setTransmission(this);
  }

  private Batch _batch;
  /** Gets the property batch.
      @see org.hl7.rim.Transmission#getBatch
  */
  public Batch getBatch() {
    return _batch;
  }
  /** Sets the property batch.
      @see org.hl7.rim.Transmission#setBatch
  */
  public void setBatch(Batch batch) {
    _batch = batch;
  }

  private /*AssociationSet*/List<CommunicationFunction> _communicationFunction;
  /** Gets the property communicationFunction.
      @see org.hl7.rim.Transmission#getCommunicationFunction
  */
  public /*AssociationSet*/List<CommunicationFunction> getCommunicationFunction() {
    return _communicationFunction;
  }
  /** Sets the property communicationFunction.
      @see org.hl7.rim.Transmission#setCommunicationFunction
  */
  public void setCommunicationFunction(/*AssociationSet*/List<CommunicationFunction> communicationFunction) {
    _communicationFunction = communicationFunction;
  }
  /** Adds an association communicationFunction.
      @see org.hl7.rim.Transmission#setCommunicationFunction
  */
  public void addCommunicationFunction(CommunicationFunction communicationFunction) {
        // create the association set if it doesn't exist already
    if(_communicationFunction == null) _communicationFunction = new AssociationSetImpl<CommunicationFunction>();
    // add the association to the association set
    getCommunicationFunction().add(communicationFunction);
  }
  public Object clone() {
    TransmissionImpl that = (TransmissionImpl) super.clone();
    that._acknowledgedBy= null;
    that._conveyedAcknowledgement= null;
    that._attachment= null;
    that._attentionLine= null;
    that._communicationFunction= null;
    return that;
  }
}
