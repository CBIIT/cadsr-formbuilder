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
package org.hl7.xml;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import org.hl7.xml.builder.BuilderException;
import org.hl7.xml.builder.DatatypeBuilder;
import org.hl7.xml.builder.RimGraphXMLSpeaker;
import org.hl7.xml.builder.impl.DatatypeBuilderImpl;
import org.hl7.xml.parser.DataTypeContentHandler;

import org.hl7.types.ANY;
import org.hl7.types.PIVL;
import org.hl7.types.GTS;
import org.hl7.meta.Datatype;

//
// not implemented
//
// NOTE: The GSTPresenter will have to work with the new QSET
// classes.
//

public class GTSPresenter extends DatatypePresenterBase {
  //-------------------------------------------------------------------------
  private static final String ATTR_ALIGNMENT = "alignment";
  private static final String ATTR_INST_SPEC = "institutionSpecified";
  private static final String ATTR_PHASE = "phase";
  private static final String ATTR_PERIOD = "period";
  
  //-------------------------------------------------------------------------
  private static final GTSPresenter INSTANCE = new GTSPresenter();
  private final ContentHandler contentHandler_ = new GTSContentHandler();

  //-------------------------------------------------------------------------
  private static class GTSContentHandler extends DataTypeContentHandler {
    //.......................................................................
    public void startElement(String namespaceURI, String localName,
      String qName, Attributes atts) {
        throw new UnsupportedOperationException();
    }
    
    //.......................................................................
    public void endElement(String namespaceURI, String localName,
      String qName) throws SAXException {
        throw new UnsupportedOperationException();
    }
  } // GTSContentHandler
  
  
  //-------------------------------------------------------------------------
  private static class GTSBuilder extends DatatypeBuilderImpl
  {
    public void build(RimGraphXMLSpeaker.ContentBuilder builder, ANY value,
      String localName) throws BuilderException
    {
      super.build(builder, value, localName);
      // not supported yet
      try
      {
        GTS pivl = (GTS)value;
        if (builder.nullValueHandled(value, localName)) return;
        builder.startElement(localName);

        builder.endElement(localName);
      }
      catch (SAXException ex)
      {
        throw new BuilderException(ex);
      }
    }

    //.......................................................................
    public void buildStructural(RimGraphXMLSpeaker.ContentBuilder builder,
      ANY value, String localName) throws BuilderException
    {
      throw new BuilderException("GTS cannot be a structural attribute");
    }
  } // GTSBuilder

  //-------------------------------------------------------------------------
  private GTSPresenter() {}
  public static GTSPresenter instance() { return INSTANCE; }
  
  //-------------------------------------------------------------------------
  public ContentHandler getContentHandler(String namespaceURI,
    String localName, String qName, Attributes atts, Datatype datatype)
  {
    // do nothing with the datatype
    return contentHandler_;
  }
  
  //-------------------------------------------------------------------------
  public DatatypeBuilder getBuilder() { return new GTSBuilder(); }
}
