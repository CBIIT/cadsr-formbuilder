package org.hl7.CTSMAPI;


/**
* org/hl7/CTSMAPI/UnknownCodeSystem.java .
* Generated by the IDL-to-Java compiler (portable), version "3.1"
* from idl/CTSMAPI.idl
* Monday, April 21, 2003 10:17:10 AM EDT
*/

public final class UnknownCodeSystem extends java.lang.Exception
{
  public org.hl7.types.UID codeSystem_id = null;

  public UnknownCodeSystem ()
  {
    // super(UnknownCodeSystemHelper.id());
  } // ctor

  public UnknownCodeSystem (org.hl7.types.UID _codeSystem_id)
  {
    // super(UnknownCodeSystemHelper.id());
    codeSystem_id = _codeSystem_id;
  } // ctor


  public UnknownCodeSystem (String $reason, org.hl7.types.UID _codeSystem_id)
  {
    // super(UnknownCodeSystemHelper.id() + "  " + $reason);
    codeSystem_id = _codeSystem_id;
  } // ctor

} // class UnknownCodeSystem
