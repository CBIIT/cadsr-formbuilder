package org.hl7.CTSMAPI;


/**
* org/hl7/CTSMAPI/CodeSystemRegistration.java .
* Generated by the IDL-to-Java compiler (portable), version "3.1"
* from idl/CTSMAPI.idl
* Monday, April 21, 2003 10:17:09 AM EDT
*/


/**

 *<PRE> * Code System Registration Entry
 *	sponsor		- person or organization that sponsors the system within HL7
 *	publisher	- person or organization the publishes the code system
 *	versionReportingMethod - method in which the code system is updated
 *	licensingInformation - description of necessary licensing and how it is obtained
 *	inUMLS		- True means that the code system is included in the Unified Medical Language System
 *	systemSpecificLocatorInfo - details that serve to further identify the code system within the context of
 *			the publisher. Includes things like table number, subsection, etc.
 *	codeSystemType_code	- One of (External, Internal, External but Internally maintained)
 </PRE>
 */
public final class CodeSystemRegistration implements org.omg.CORBA.portable.IDLEntity
{
  public org.hl7.types.ST sponsor = null;
  public org.hl7.types.ST publisher = null;
  public org.hl7.types.ST versionReportingMethod = null;
  public org.hl7.types.ST licensingInformation = null;
  public org.hl7.types.BL inUMLS = null;
  public org.hl7.types.ST systemSpecificLocatorInfo = null;
  public org.hl7.types.ST codeSystemType_code = null;

  public CodeSystemRegistration ()
  {
  } // ctor

  public CodeSystemRegistration (org.hl7.types.ST _sponsor, org.hl7.types.ST _publisher, org.hl7.types.ST _versionReportingMethod, org.hl7.types.ST _licensingInformation, org.hl7.types.BL _inUMLS, org.hl7.types.ST _systemSpecificLocatorInfo, org.hl7.types.ST _codeSystemType_code)
  {
    sponsor = _sponsor;
    publisher = _publisher;
    versionReportingMethod = _versionReportingMethod;
    licensingInformation = _licensingInformation;
    inUMLS = _inUMLS;
    systemSpecificLocatorInfo = _systemSpecificLocatorInfo;
    codeSystemType_code = _codeSystemType_code;
  } // ctor

} // class CodeSystemRegistration