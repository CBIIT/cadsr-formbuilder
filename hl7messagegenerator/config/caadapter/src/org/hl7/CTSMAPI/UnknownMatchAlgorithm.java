package org.hl7.CTSMAPI;


/**
* org/hl7/CTSMAPI/UnknownMatchAlgorithm.java .
* Generated by the IDL-to-Java compiler (portable), version "3.1"
* from idl/CTSMAPI.idl
* Monday, April 21, 2003 10:17:10 AM EDT
*/

public final class UnknownMatchAlgorithm extends java.lang.Exception
{
  public org.hl7.types.ST matchAlgorithm_code = null;

  public UnknownMatchAlgorithm ()
  {
    // super(UnknownMatchAlgorithmHelper.id());
  } // ctor

  public UnknownMatchAlgorithm (org.hl7.types.ST _matchAlgorithm_code)
  {
    // super(UnknownMatchAlgorithmHelper.id());
    matchAlgorithm_code = _matchAlgorithm_code;
  } // ctor


  public UnknownMatchAlgorithm (String $reason, org.hl7.types.ST _matchAlgorithm_code)
  {
    // super(UnknownMatchAlgorithmHelper.id() + "  " + $reason);
    matchAlgorithm_code = _matchAlgorithm_code;
  } // ctor

} // class UnknownMatchAlgorithm