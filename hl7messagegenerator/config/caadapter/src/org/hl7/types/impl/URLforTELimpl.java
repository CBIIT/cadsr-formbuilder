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
import org.hl7.types.BL;
import org.hl7.types.CS;
import org.hl7.types.ST;

/** Adapter for java.net.URL to the org.hl7.types.URL interface.

  FIXME: There are a couple of quirks:

  - the j.n.URL has no way of getting at an opaque part (i.e.
    everything after the scheme, and I'm not sure we can 
    reassemble that from the URL accessors. 

  - given that it's so hard to get at the opaque part, the
    equals implementation may not be ideal.
*/

public class URLforTELimpl extends ANYimpl implements org.hl7.types.URL
{
  String _data;

  protected URLforTELimpl(String data) 
    throws IllegalArgumentException
  {
    super(null);
    if(data == null)
      throw new IllegalArgumentException();
    else 
      this._data=data;
  }

  public static org.hl7.types.URL valueOf(String data) {
    if(data == null)
      return URLnull.NI;
    else
      return new URLforTELimpl(data);
  }

  public final BL equal(ANY that) {
    if(that instanceof org.hl7.types.URL) {
      if(that instanceof URLforTELimpl){ 
	return BLimpl.valueOf(this._data.equals(((URLforTELimpl)that)._data));
      }
    }        
      return BLimpl.FALSE;    
  }

  public CS scheme() {
      return CSnull.NA;
  }

  public ST address() {
    if(this.isNullJ() && _data == null)
      return STnull.NA;
    else {
     return STjlStringAdapter.valueOf(_data);
    }
  }

    public String toString() {
	return _data;
    }
}
