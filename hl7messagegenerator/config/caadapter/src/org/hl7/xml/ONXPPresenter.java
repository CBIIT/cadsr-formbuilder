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

import java.util.HashMap;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import org.hl7.xml.builder.BuilderException;
import org.hl7.xml.builder.DatatypeBuilder;
import org.hl7.xml.builder.RimGraphXMLSpeaker;
import org.hl7.xml.builder.impl.DatatypeBuilderImpl;
import org.hl7.xml.parser.TreeContentHandler;
import org.hl7.xml.parser.DynamicContentHandler;

import org.hl7.types.ONXP;
import org.hl7.types.ANY;
import org.hl7.types.CS;
import org.hl7.types.DSET;
import org.hl7.types.impl.ONXPimpl;
import org.hl7.types.impl.ONXPnull;
import org.hl7.types.impl.CSimpl;
import org.hl7.meta.Datatype;

public class ONXPPresenter extends ENXPPresenter
{
  //-------------------------------------------------------------------------
  /** Maps local element names into part types. */
  public static final Map<String, String> TAGS_TO_PART_TYPES =
    new HashMap<String, String>();
  static
  {
    TAGS_TO_PART_TYPES.put("delimiter", "DEL");
    TAGS_TO_PART_TYPES.put("prefix", "PFX");
    TAGS_TO_PART_TYPES.put("suffix", "SFX");
  }
  
  /** Maps part types into local element names. */
  private static final Map<String, String> PART_TYPES_TO_TAGS =
    new HashMap<String, String>();
  static
  {
    PART_TYPES_TO_TAGS.put("DEL", "delimiter");
    PART_TYPES_TO_TAGS.put("PFX", "prefix");
    PART_TYPES_TO_TAGS.put("SFX", "suffix");
  }

  //-------------------------------------------------------------------------
  private static final ONXPPresenter INSTANCE = new ONXPPresenter();

  //-------------------------------------------------------------------------
  protected static class ONXPContentHandler extends TreeContentHandler
  {
    //.......................................................................
    public ONXPContentHandler(String namespaceURI, String localName,
      String qName, Attributes atts)
    {
      super(namespaceURI, localName, qName, atts);
    }

    //.......................................................................
    protected void returnResult(Object intermediate) throws SAXException
    {
      TreeContentHandler.Element element =
        (TreeContentHandler.Element) intermediate;
      ONXP result = valueOf(element);
      super.returnResult(result);
    }
    
    //.......................................................................
    protected static ONXP valueOf(TreeContentHandler.Node node)
    {
      if (node instanceof TreeContentHandler.Element)
      {
        TreeContentHandler.Element element = (TreeContentHandler.Element) node;
        String nullFlavorString = element.getAttributeValue(DatatypePresenterBase.ATTR_NULL_FLAVOR);
        if (nullFlavorString != null)
        {
          return ONXPnull.valueOf(nullFlavorString);
        }

        String language = element.getAttributeValue(ATTR_LANGUAGE);
        CS type = CSimpl.valueOf((String)TAGS_TO_PART_TYPES.get(
          element.getLocalName()), "OrganizationNamePartType");
        
        String qualifier = element.getAttributeValue(ATTR_QUALIFIER);
        DSET<CS> qualifiers = CSPresenter.parseList(qualifier,
          "OrganizationNamePartQualifier");
            
        return ONXPimpl.valueOf(element.getText(), type, qualifiers, language);
      }
      else if (node instanceof TreeContentHandler.Text)
      {
        TreeContentHandler.Text text = (TreeContentHandler.Text) node;
        return ONXPimpl.valueOf(text.getValue());
      }
      else return null;
    }
  }

  //-----------------------------------------------------------------
  protected static class ONXPBuilder extends DatatypeBuilderImpl
  {
    public void build(RimGraphXMLSpeaker.ContentBuilder builder,
      ANY value, String localName) throws BuilderException
    {

      //super.build(builder, value, localName);
      try
      {

        ONXP onxp = (ONXP)value;
        if (builder.nullValueHandled(value, localName)) return;

          builder.addAttribute(ATTR_LANGUAGE, onxp.language());
          builder.addAttribute(ATTR_REPRESENTATION, "TXT");
          builder.addAttribute(ATTR_MEDIA_TYPE, onxp.mediaType());

        if (!onxp.qualifier().isNullJ())
          builder.addAttribute(ATTR_QUALIFIER, (CS)onxp.qualifier().iterator().next());
        builder.addAttribute(ATTR_PART_TYPE, onxp.type());

        builder.startElement(localName);
        
        String s = onxp.toString();
        char[] ac = s.toCharArray();
        builder.characters(ac, 0, ac.length);
        
        builder.endElement(localName);
      }
      catch (SAXException ex)
      {
        throw new BuilderException(ex);
      }
    }

    //.......................................................................
    public void build(RimGraphXMLSpeaker.ContentBuilder builder,
      ANY value) throws BuilderException
    {
      ONXP onxp = (ONXP)value;
      
      try
      {
        String tag = (String) PART_TYPES_TO_TAGS.get(onxp.type().code().toString());
        if (tag != null)
        {
          // Emit typed part.
          build(builder, onxp, tag);
        }
        else
        {
          // Emit untyped part.
          String s = onxp.toString();
          char[] ac = s.toCharArray();
          builder.characters(ac, 0, ac.length);
        }
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
      throw new BuilderException("ONXP cannot be a structural attribute");
    }
  }
    
  //-------------------------------------------------------------------------
  private ONXPPresenter() {}
  public static ONXPPresenter instance() { return INSTANCE; }

  //-------------------------------------------------------------------------
  public DynamicContentHandler getContentHandler(String namespaceURI,
    String localName, String qName, Attributes atts, Datatype datatype)
  {
    // do nothing with the datatype
    return new ONXPContentHandler(namespaceURI, localName, qName, atts);
  }

  //-------------------------------------------------------------------------
  public DatatypeBuilder getBuilder() { return new ONXPBuilder(); }
}
