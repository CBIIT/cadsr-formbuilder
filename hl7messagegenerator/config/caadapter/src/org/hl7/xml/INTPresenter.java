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
import org.hl7.xml.parser.SimpleTypeContentHandler;

import org.hl7.types.ANY;
import org.hl7.types.INT;
import org.hl7.types.ValueFactory;
import org.hl7.types.impl.INTnull;
import org.hl7.meta.Datatype;

/** This little guy knows how to build integers
 *
 * FIXME: defaults are not configurable.
 */
public class INTPresenter extends DatatypePresenterBase {
  //-------------------------------------------------------------------------
  public static final String ATTR_VALUE = "value";
  
  //-------------------------------------------------------------------------
  private static final INTPresenter INSTANCE = new INTPresenter();
  private final ContentHandler contentHandler_ = new INTContentHandler();

  //-------------------------------------------------------------------------
  private static class INTContentHandler extends SimpleTypeContentHandler {
    //.......................................................................
    INT _result = null;

    //.......................................................................
    protected void notifyActivation(Attributes atts) {
      String nullFlavorString = atts.getValue(DatatypePresenterBase.ATTR_NULL_FLAVOR);
      if(nullFlavorString != null) {
        _result = INTnull.valueOf(nullFlavorString);
      } else {
        String valueString = atts.getValue(ATTR_VALUE);
        if(valueString != null) {
          _result = ValueFactory.getInstance().INTvalueOfLiteral(valueString);
        }
      }
    }
    
    //.......................................................................
    protected Object getResult() {
      return (_result == null) ? INTnull.NI : _result;
    }
  }

  //------------------------------------------------------------------------
  private static class INTBuilder extends DatatypeBuilderImpl
  {
    public void build(RimGraphXMLSpeaker.ContentBuilder builder, ANY value,
      String localName) throws BuilderException
    {
      super.build(builder, value, localName);
      try
      {
        INT integer = (INT)value;
        if (builder.nullValueHandled(value, localName)) return;        
        builder.addAttribute(ATTR_VALUE, integer);
        
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
      throw new BuilderException("INT cannot be a structural attribute");
    }
  }

  //-------------------------------------------------------------------------
  private INTPresenter() {}
  public static INTPresenter instance() { return INSTANCE; }

  public static INT getValue(Object value) {
    if (value == null) return INTnull.NI;      
    return ValueFactory.getInstance().INTvalueOfLiteral(value.toString());

  }
  //-------------------------------------------------------------------------
  public ContentHandler getContentHandler(String namespaceURI,
    String localName, String qName, Attributes atts, Datatype datatype)
  {
    // do nothing with the datatype
    return contentHandler_;
  }
  
  //-------------------------------------------------------------------------
  public DatatypeBuilder getBuilder() { return new INTBuilder(); }
}
