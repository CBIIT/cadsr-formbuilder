/* The contents of this file are subject to the Health Level-7 Public
* License Version 1.0 (the "License"); you may not use this file
* except in compliance with the License. You may obtain a copy of the
* License at http://www.hl7.org/HPL/
*
* Software distributed under the License is distributed on an "AS IS"
* basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See
* the License for the specific language governing rights and
* limitations under the License.
*
* The Original Code is all this file.
*
* The Initial Developer of the Original Code is .
* Portions created by Initial Developer are Copyright (C) 2002-2004 
* Health Level Seven, Inc. All Rights Reserved.
*
* Contributor(s): 
*/
package org.hl7.types.impl;

import org.hl7.types.ANY;
import org.hl7.types.BIN;
import org.hl7.types.BL;
import org.hl7.types.CS;
import org.hl7.types.ED;
import org.hl7.types.TEL;

public final class EDjlStringAdapter extends STjlStringAdapter implements ED {
  private final CS  _mediaType;
  private final CS  _charSet;
  private final CS  _language;
  private final CS  _compression;
  private final TEL  _reference;
  private final BIN  _integrityCheck;
  private final CS  _integrityCheckAlgorithm;
  private final ED  _thumbnail;

  private EDjlStringAdapter(String text, CS mediaType, CS charSet,
    CS language, CS compression, TEL reference,
    BIN integrityCheck, CS integrityCheckAlgorithm, ED thumbnail) {

    super(text);
    _mediaType = mediaType;
    _charSet = charSet;
    _language = language;
    _compression = compression;
    _reference = reference;
    _integrityCheck = integrityCheck;
    _integrityCheckAlgorithm = integrityCheckAlgorithm;
    _thumbnail = thumbnail;
  }

  public static ED valueOf(String text, String mediaTypeString,
    String charSetString, String languageString, String compressionString,
    TEL reference, BIN integrityCheck, String integrityCheckAlgorithmString,
    ED thumbnail) {

    if (text == null) {
      return EDnull.NI;
    }

    if (mediaTypeString == null) {
      mediaTypeString = "text/plain";
    }
    CS mediaType = CSimpl.valueOf(mediaTypeString, "MediaType");

    CS charSet = CSimpl.valueOf(charSetString, "Charset");
    CS language = CSimpl.valueOf(languageString, "HumanLanguage");
    CS compression = CSimpl.valueOf(compressionString, "CompressionAlgorithm");

    if (integrityCheckAlgorithmString == null) {
      integrityCheckAlgorithmString = "SHA-1";
    }
    CS integrityCheckAlgorithm = CSimpl.valueOf(integrityCheckAlgorithmString,
      "IntegrityCheckAlgorithm");
    
    return new EDjlStringAdapter(text, mediaType, charSet, language,
      compression, reference, integrityCheck, integrityCheckAlgorithm,
      thumbnail);
  }

  public static ED valueOf(String text, boolean fakeParameter) {
    
    return valueOf(text, null, null, null, null, TELnull.NI, BINnull.NI,
      null, EDnull.NI);
  }
  
  /** FIXME: is NA correct or should it be derived from this and that? */
  public BL equal(ANY that) {
    if(this == that)
      return BLimpl.TRUE;
    else
      if(that.isNullJ())
        return BLimpl.FALSE; // FIXME: false or NI or UNK?
      else
        if(that instanceof ED) {
          ED thatED = (ED)that;
          return super.equal(thatED)
            .and(this.mediaType().equal(thatED.mediaType()));
        } else
          return BLimpl.FALSE;
  }

  // The ED interface
  public CS mediaType() {
    return _mediaType;
  }

  public CS charset() {
    return _charSet;
  }

  public CS compression() {
    return _compression;
  }

  public CS language() {
    return _language;
  }

  public TEL reference() {
    return _reference;
  }

  public BIN integrityCheck() {
    return _integrityCheck;
  }

  public CS integrityCheckAlgorithm() {
    return _integrityCheckAlgorithm;
  }

  public ED thumbnail() {
    return _thumbnail;
  }
};
