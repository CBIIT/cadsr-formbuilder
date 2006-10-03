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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import org.hl7.types.*;
import org.hl7.types.impl.*;
import org.hl7.xml.builder.BuilderException;
import org.hl7.xml.builder.DatatypeBuilder;
import org.hl7.xml.builder.RimGraphXMLSpeaker;
import org.hl7.xml.builder.impl.DatatypeBuilderImpl;
import org.hl7.xml.parser.*;
import org.hl7.meta.Datatype;

public class ONPresenter extends ENPresenter {
  //-------------------------------------------------------------------------
  private static final ONPresenter INSTANCE = new ONPresenter();

  //-------------------------------------------------------------------------
  protected static class ONContentHandler extends TreeContentHandler {
    //.......................................................................
    public ONContentHandler(String namespaceURI, String localName,
      String qName, Attributes atts) {
        super(namespaceURI, localName, qName, atts);
    }
    
    //.......................................................................
    protected void returnResult(Object intermediate) throws SAXException {
      Element element = (Element)intermediate;
      ON result = valueOf(element);
      super.returnResult(result);
    }
    
    //.......................................................................
    protected ON valueOf(Element element) {
      String nullFlavorString = element.getAttributeValue(DatatypePresenterBase.ATTR_NULL_FLAVOR);
      if (nullFlavorString != null) {
        return ONnull.valueOf(nullFlavorString);
      }
      
      String useValue = element.getAttributeValue(ATTR_USE);
      DSET<CS> use = CSPresenter.parseList(useValue, "EntityNameUse");
      
      Iterator<Element> it = element.findChildren(TAG_VALID_TIME);
      // NYI.
//    IVL<TS> validTime = it.hasNext() ? IVLPresenter.valueOf(it.next()) : null;
      IVL<TS> validTime = null;
      
      List<ENXP> nameParts = new ArrayList<ENXP>();
      for (Iterator<Node> it2 = element.getChildren().iterator(); it2.hasNext(); )
      {
        Node node = (TreeContentHandler.Node) it2.next();
        ONXP part = ONXPPresenter.ONXPContentHandler.valueOf(node);
        if (part != null) nameParts.add(part);
      }
      
      return ONimpl.valueOf(nameParts, use, validTime);
    }
  }
  
  //-------------------------------------------------------------------------
  private static class ONBuilder extends DatatypeBuilderImpl
  {
    //.......................................................................
    private final CSPresenter.CSBuilder csBuilder_ =
      (CSPresenter.CSBuilder)CSPresenter.instance().getBuilder();
    private final ONXPPresenter.ONXPBuilder onxpBuilder_ =
      (ONXPPresenter.ONXPBuilder)ONXPPresenter.instance().getBuilder();

    public void build(RimGraphXMLSpeaker.ContentBuilder builder, ANY value,
      String localName) throws BuilderException
    {
      super.build(builder, value, localName);
      try
      {
        ON on = (ON)value;
        if (builder.nullValueHandled(value, localName)) return;
        csBuilder_.buildAttribute(builder, on.use(), ATTR_USE);
        
        builder.startElement(localName);
        
        for (Iterator<ENXP> it = on.iterator(); it.hasNext(); )
        {
          ONXP onxp = (ONXP)it.next();
          onxpBuilder_.build(builder, onxp);
        }
        
        // Emit validTime if present.
//        builder.build(on.validTime(), ATTR_VALID_TIME);
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
      throw new BuilderException("ON cannot be a structural attribute");
    }
  }
  
  //---------------------------------------------------------------------------
  private ONPresenter() {}
  public static ONPresenter instance() { return INSTANCE; }
  
  //-------------------------------------------------------------------------
  public ContentHandler getContentHandler(String namespaceURI,
    String localName, String qName, Attributes atts, Datatype datatype) {
    // do nothing with the datatype
    return new ONContentHandler(namespaceURI, localName, qName, atts);
  }
  
  //-------------------------------------------------------------------------
  public DatatypeBuilder getBuilder() { return new ONBuilder(); }
}
