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

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;
import java.util.HashSet;
import java.util.Set;

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

public class URLjnURLAdapter extends ANYimpl implements org.hl7.types.URL
{
  java.net.URL _data;
	

  private static final Set<String> KNOWN_PROTOCOLS = new HashSet<String>();
  static
  {
    KNOWN_PROTOCOLS.add("tel");
    KNOWN_PROTOCOLS.add("fax");
    KNOWN_PROTOCOLS.add("modem");
    KNOWN_PROTOCOLS.add("mllp");
    KNOWN_PROTOCOLS.add("nfs");
    KNOWN_PROTOCOLS.add("telnet");
  }

  private static final URLStreamHandler FAKE_STREAM_HANDLER =
    new URLStreamHandler()
    {
      protected URLConnection openConnection(URL url) throws IOException
      {
        throw new UnsupportedOperationException("Method not implemented.");
      }
    };

  private static final URLStreamHandlerFactory FAKE_STREAM_HANDLER_FACTORY =
    new URLStreamHandlerFactory()
    {
      public URLStreamHandler createURLStreamHandler(String protocol)
      {
        if (protocol == null) return null;
        else if (KNOWN_PROTOCOLS.contains(protocol))
        {
          return FAKE_STREAM_HANDLER;
        }
        else return null;
      }
  };

  static
  {
    URL.setURLStreamHandlerFactory(FAKE_STREAM_HANDLER_FACTORY);
  }

  protected URLjnURLAdapter(java.net.URL data)
    throws IllegalArgumentException
  {
    super(null);
    if(data == null)
      throw new IllegalArgumentException();
    else
      this._data=data;
  }

  protected URLjnURLAdapter(String data) {
    super(null);
	  
    try {
      if(data == null)
        throw new IllegalArgumentException();
      else
        this._data=new java.net.URL(data);
		  
    } catch(java.net.MalformedURLException x) {
      throw new IllegalArgumentException(x.getMessage());
    }
	
  }

  public static org.hl7.types.URL valueOf(java.net.URL data) {
    if(data == null)
      return URLnull.NI;
    else
      return new URLjnURLAdapter(data);
  }

  java.net.URL tojnURL() {
    return _data;
  }

  public final BL equal(ANY that) {
    if(that instanceof org.hl7.types.URL) {
      if(that instanceof URLjnURLAdapter) {
        return BLimpl.valueOf(this._data
                              .equals(((URLjnURLAdapter)that)._data));
      } else {
        org.hl7.types.URL thatURL = (org.hl7.types.URL)that;

        return this.scheme().equal(thatURL.scheme())
          .and(this.address().equal(thatURL.address()));
        }
      }
    else
      return BLimpl.FALSE;
  }

  public CS scheme() {
    if(this.isNullJ() && _data == null)
      return CSnull.NA;
    else {
      return CSimpl.valueOf(_data.getProtocol(),"HL7");
		
      // FIXME: code system!!!
    }
  }

  public ST address() {
    if(this.isNullJ() && _data == null)
      return STnull.NA;
    else {
      String urlstring = _data.toString();
      String scheme = _data.getProtocol();
      return STjlStringAdapter
        .valueOf(urlstring.substring(scheme.length()+1));
    }
  }

    public String toString() {
	return _data.toString();
    }
}
