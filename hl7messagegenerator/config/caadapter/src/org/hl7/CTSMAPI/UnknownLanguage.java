package org.hl7.CTSMAPI;


/**
* org/hl7/CTSMAPI/UnknownLanguage.java .
* Generated by the IDL-to-Java compiler (portable), version "3.1"
* from idl/CTSMAPI.idl
* Monday, April 21, 2003 10:17:10 AM EDT
*/

public final class UnknownLanguage extends java.lang.Exception
{
  public org.hl7.types.ST language_code = null;

  public UnknownLanguage ()
  {
    // super(UnknownLanguageHelper.id());
  } // ctor

  public UnknownLanguage (org.hl7.types.ST _language_code)
  {
    // super(UnknownLanguageHelper.id());
    language_code = _language_code;
  } // ctor


  public UnknownLanguage (String $reason, org.hl7.types.ST _language_code)
  {
    // super(UnknownLanguageHelper.id() + "  " + $reason);
    language_code = _language_code;
  } // ctor

} // class UnknownLanguage
