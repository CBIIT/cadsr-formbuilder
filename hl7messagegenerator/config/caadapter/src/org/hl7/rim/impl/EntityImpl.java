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

import org.hl7.rim.Entity;
import org.hl7.rim.impl.InfrastructureRootImpl;
import org.hl7.types.CS;
import org.hl7.types.SET;
import org.hl7.types.II;
import org.hl7.types.CE;
import org.hl7.types.PQ;
import org.hl7.types.BAG;
import org.hl7.types.EN;
import org.hl7.types.ED;
import org.hl7.types.IVL;
import org.hl7.types.TS;
import org.hl7.types.TEL;

import org.hl7.rim.CommunicationFunction;
import org.hl7.rim.LanguageCommunication;
import org.hl7.rim.Role;
import /*org.hl7.rim.AssociationSet*/java.util.List;

/** Implementation of org.hl7.rim.Entity as a simple data holder bean.
    @see org.hl7.rim.Entity
  */
public class EntityImpl extends InfrastructureRootImpl implements Entity {

  private CS _classCode;
  /** Gets the property classCode.
      @see org.hl7.rim.Entity#getClassCode
  */
  public CS getClassCode() { return _classCode; }
  /** Sets the property classCode.
      @see org.hl7.rim.Entity#setClassCode
  */
  public void setClassCode(CS classCode) {
    if(classCode instanceof org.hl7.hibernate.ClonableCollection)
      classCode = ((org.hl7.hibernate.ClonableCollection<CS>) classCode).cloneHibernateCollectionIfNecessary();
    _classCode = classCode;
  }

  private CS _determinerCode;
  /** Gets the property determinerCode.
      @see org.hl7.rim.Entity#getDeterminerCode
  */
  public CS getDeterminerCode() { return _determinerCode; }
  /** Sets the property determinerCode.
      @see org.hl7.rim.Entity#setDeterminerCode
  */
  public void setDeterminerCode(CS determinerCode) {
    if(determinerCode instanceof org.hl7.hibernate.ClonableCollection)
      determinerCode = ((org.hl7.hibernate.ClonableCollection<CS>) determinerCode).cloneHibernateCollectionIfNecessary();
    _determinerCode = determinerCode;
  }

  private SET<II> _id;
  /** Gets the property id.
      @see org.hl7.rim.Entity#getId
  */
  public SET<II> getId() { return _id; }
  /** Sets the property id.
      @see org.hl7.rim.Entity#setId
  */
  public void setId(SET<II> id) {
    if(id instanceof org.hl7.hibernate.ClonableCollection)
      id = ((org.hl7.hibernate.ClonableCollection<SET<II>>) id).cloneHibernateCollectionIfNecessary();
    _id = id;
  }

  private CE _code;
  /** Gets the property code.
      @see org.hl7.rim.Entity#getCode
  */
  public CE getCode() { return _code; }
  /** Sets the property code.
      @see org.hl7.rim.Entity#setCode
  */
  public void setCode(CE code) {
    if(code instanceof org.hl7.hibernate.ClonableCollection)
      code = ((org.hl7.hibernate.ClonableCollection<CE>) code).cloneHibernateCollectionIfNecessary();
    _code = code;
  }

  private SET<PQ> _quantity;
  /** Gets the property quantity.
      @see org.hl7.rim.Entity#getQuantity
  */
  public SET<PQ> getQuantity() { return _quantity; }
  /** Sets the property quantity.
      @see org.hl7.rim.Entity#setQuantity
  */
  public void setQuantity(SET<PQ> quantity) {
    if(quantity instanceof org.hl7.hibernate.ClonableCollection)
      quantity = ((org.hl7.hibernate.ClonableCollection<SET<PQ>>) quantity).cloneHibernateCollectionIfNecessary();
    _quantity = quantity;
  }

  private BAG<EN> _name;
  /** Gets the property name.
      @see org.hl7.rim.Entity#getName
  */
  public BAG<EN> getName() { return _name; }
  /** Sets the property name.
      @see org.hl7.rim.Entity#setName
  */
  public void setName(BAG<EN> name) {
    if(name instanceof org.hl7.hibernate.ClonableCollection)
      name = ((org.hl7.hibernate.ClonableCollection<BAG<EN>>) name).cloneHibernateCollectionIfNecessary();
    _name = name;
  }

  private ED _desc;
  /** Gets the property desc.
      @see org.hl7.rim.Entity#getDesc
  */
  public ED getDesc() { return _desc; }
  /** Sets the property desc.
      @see org.hl7.rim.Entity#setDesc
  */
  public void setDesc(ED desc) {
    if(desc instanceof org.hl7.hibernate.ClonableCollection)
      desc = ((org.hl7.hibernate.ClonableCollection<ED>) desc).cloneHibernateCollectionIfNecessary();
    _desc = desc;
  }

  private CS _statusCode;
  /** Gets the property statusCode.
      @see org.hl7.rim.Entity#getStatusCode
  */
  public CS getStatusCode() { return _statusCode; }
  /** Sets the property statusCode.
      @see org.hl7.rim.Entity#setStatusCode
  */
  public void setStatusCode(CS statusCode) {
    if(statusCode instanceof org.hl7.hibernate.ClonableCollection)
      statusCode = ((org.hl7.hibernate.ClonableCollection<CS>) statusCode).cloneHibernateCollectionIfNecessary();
    _statusCode = statusCode;
  }

  private IVL<TS> _existenceTime;
  /** Gets the property existenceTime.
      @see org.hl7.rim.Entity#getExistenceTime
  */
  public IVL<TS> getExistenceTime() { return _existenceTime; }
  /** Sets the property existenceTime.
      @see org.hl7.rim.Entity#setExistenceTime
  */
  public void setExistenceTime(IVL<TS> existenceTime) {
    if(existenceTime instanceof org.hl7.hibernate.ClonableCollection)
      existenceTime = ((org.hl7.hibernate.ClonableCollection<IVL<TS>>) existenceTime).cloneHibernateCollectionIfNecessary();
    _existenceTime = existenceTime;
  }

  private BAG<TEL> _telecom;
  /** Gets the property telecom.
      @see org.hl7.rim.Entity#getTelecom
  */
  public BAG<TEL> getTelecom() { return _telecom; }
  /** Sets the property telecom.
      @see org.hl7.rim.Entity#setTelecom
  */
  public void setTelecom(BAG<TEL> telecom) {
    if(telecom instanceof org.hl7.hibernate.ClonableCollection)
      telecom = ((org.hl7.hibernate.ClonableCollection<BAG<TEL>>) telecom).cloneHibernateCollectionIfNecessary();
    _telecom = telecom;
  }

  private CE _riskCode;
  /** Gets the property riskCode.
      @see org.hl7.rim.Entity#getRiskCode
  */
  public CE getRiskCode() { return _riskCode; }
  /** Sets the property riskCode.
      @see org.hl7.rim.Entity#setRiskCode
  */
  public void setRiskCode(CE riskCode) {
    if(riskCode instanceof org.hl7.hibernate.ClonableCollection)
      riskCode = ((org.hl7.hibernate.ClonableCollection<CE>) riskCode).cloneHibernateCollectionIfNecessary();
    _riskCode = riskCode;
  }

  private CE _handlingCode;
  /** Gets the property handlingCode.
      @see org.hl7.rim.Entity#getHandlingCode
  */
  public CE getHandlingCode() { return _handlingCode; }
  /** Sets the property handlingCode.
      @see org.hl7.rim.Entity#setHandlingCode
  */
  public void setHandlingCode(CE handlingCode) {
    if(handlingCode instanceof org.hl7.hibernate.ClonableCollection)
      handlingCode = ((org.hl7.hibernate.ClonableCollection<CE>) handlingCode).cloneHibernateCollectionIfNecessary();
    _handlingCode = handlingCode;
  }

  private /*AssociationSet*/List<CommunicationFunction> _communicationFunction;
  /** Gets the property communicationFunction.
      @see org.hl7.rim.Entity#getCommunicationFunction
  */
  public /*AssociationSet*/List<CommunicationFunction> getCommunicationFunction() {
    return _communicationFunction;
  }
  /** Sets the property communicationFunction.
      @see org.hl7.rim.Entity#setCommunicationFunction
  */
  public void setCommunicationFunction(/*AssociationSet*/List<CommunicationFunction> communicationFunction) {
    _communicationFunction = communicationFunction;
  }
  /** Adds an association communicationFunction.
      @see org.hl7.rim.Entity#setCommunicationFunction
  */
  public void addCommunicationFunction(CommunicationFunction communicationFunction) {
        // create the association set if it doesn't exist already
    if(_communicationFunction == null) _communicationFunction = new AssociationSetImpl<CommunicationFunction>();
    // add the association to the association set
    getCommunicationFunction().add(communicationFunction);
  }

  private /*AssociationSet*/List<LanguageCommunication> _languageCommunication;
  /** Gets the property languageCommunication.
      @see org.hl7.rim.Entity#getLanguageCommunication
  */
  public /*AssociationSet*/List<LanguageCommunication> getLanguageCommunication() {
    return _languageCommunication;
  }
  /** Sets the property languageCommunication.
      @see org.hl7.rim.Entity#setLanguageCommunication
  */
  public void setLanguageCommunication(/*AssociationSet*/List<LanguageCommunication> languageCommunication) {
    _languageCommunication = languageCommunication;
  }
  /** Adds an association languageCommunication.
      @see org.hl7.rim.Entity#setLanguageCommunication
  */
  public void addLanguageCommunication(LanguageCommunication languageCommunication) {
        // create the association set if it doesn't exist already
    if(_languageCommunication == null) _languageCommunication = new AssociationSetImpl<LanguageCommunication>();
    // add the association to the association set
    getLanguageCommunication().add(languageCommunication);
    // make the inverse link
    languageCommunication.setEntity(this);
  }

  private /*AssociationSet*/List<Role> _playedRole;
  /** Gets the property playedRole.
      @see org.hl7.rim.Entity#getPlayedRole
  */
  public /*AssociationSet*/List<Role> getPlayedRole() {
    return _playedRole;
  }
  /** Sets the property playedRole.
      @see org.hl7.rim.Entity#setPlayedRole
  */
  public void setPlayedRole(/*AssociationSet*/List<Role> playedRole) {
    _playedRole = playedRole;
  }
  /** Adds an association playedRole.
      @see org.hl7.rim.Entity#setPlayedRole
  */
  public void addPlayedRole(Role playedRole) {
        // create the association set if it doesn't exist already
    if(_playedRole == null) _playedRole = new AssociationSetImpl<Role>();
    // add the association to the association set
    getPlayedRole().add(playedRole);
    // make the inverse link
    playedRole.setPlayer(this);
  }

  private /*AssociationSet*/List<Role> _scopedRole;
  /** Gets the property scopedRole.
      @see org.hl7.rim.Entity#getScopedRole
  */
  public /*AssociationSet*/List<Role> getScopedRole() {
    return _scopedRole;
  }
  /** Sets the property scopedRole.
      @see org.hl7.rim.Entity#setScopedRole
  */
  public void setScopedRole(/*AssociationSet*/List<Role> scopedRole) {
    _scopedRole = scopedRole;
  }
  /** Adds an association scopedRole.
      @see org.hl7.rim.Entity#setScopedRole
  */
  public void addScopedRole(Role scopedRole) {
        // create the association set if it doesn't exist already
    if(_scopedRole == null) _scopedRole = new AssociationSetImpl<Role>();
    // add the association to the association set
    getScopedRole().add(scopedRole);
    // make the inverse link
    scopedRole.setScoper(this);
  }
  public Object clone() {
    EntityImpl that = (EntityImpl) super.clone();
    that._communicationFunction= null;
    that._languageCommunication= null;
    that._playedRole= null;
    that._scopedRole= null;
    return that;
  }
}
