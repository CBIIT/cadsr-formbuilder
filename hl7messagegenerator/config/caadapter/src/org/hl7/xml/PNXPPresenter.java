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

import org.hl7.types.PNXP;
import org.hl7.types.ANY;
import org.hl7.types.CS;
import org.hl7.types.DSET;
import org.hl7.types.impl.PNXPimpl;
import org.hl7.types.impl.PNXPnull;
import org.hl7.types.impl.CSimpl;
import org.hl7.meta.Datatype;

public class PNXPPresenter extends ENXPPresenter
{
  //-------------------------------------------------------------------------
  private static final String ATTR_PART_TYPE = "partType";
  private static final String ATTR_QUALIFIER = "qualifier";

  
  //---------------------------------------------------------------------------
  /** Maps local element names into part types. */
  public static final Map<String, String> TAGS_TO_PART_TYPES =
    new HashMap<String, String>();
  static
  {
    TAGS_TO_PART_TYPES.put("delimiter", "DEL");
    TAGS_TO_PART_TYPES.put("family", "FAM");
    TAGS_TO_PART_TYPES.put("given", "GIV");
    TAGS_TO_PART_TYPES.put("prefix", "PFX");
    TAGS_TO_PART_TYPES.put("suffix", "SFX");
  }
  
  /** Maps part types into local element names. */
  protected static final Map<String, String> PART_TYPES_TO_TAGS =
    new HashMap<String, String>();
  static
  {
    PART_TYPES_TO_TAGS.put("DEL", "delimiter");
    PART_TYPES_TO_TAGS.put("FAM", "family");
    PART_TYPES_TO_TAGS.put("GIV", "given");
    PART_TYPES_TO_TAGS.put("PFX", "prefix");
    PART_TYPES_TO_TAGS.put("SFX", "suffix");
  }
  
  //-------------------------------------------------------------------------
  private static final PNXPPresenter INSTANCE = new PNXPPresenter();

  //-------------------------------------------------------------------------
  protected static class PNXPContentHandler extends TreeContentHandler
  {
    //.......................................................................
    public PNXPContentHandler(String namespaceURI, String localName,
      String qName, Attributes atts)
    {
      super(namespaceURI, localName, qName, atts);
    }

    //.......................................................................
    protected void returnResult(Object intermediate) throws SAXException
    {
      TreeContentHandler.Element element =
        (TreeContentHandler.Element) intermediate;
      PNXP result = valueOf(element);
      super.returnResult(result);
    }
    
    //.......................................................................
    protected static PNXP valueOf(TreeContentHandler.Node node)
    {
      if (node instanceof TreeContentHandler.Element)
      {
        TreeContentHandler.Element element = (TreeContentHandler.Element) node;
        String nullFlavorString = element.getAttributeValue(DatatypePresenterBase.ATTR_NULL_FLAVOR);
        if (nullFlavorString != null)
        {
          return PNXPnull.valueOf(nullFlavorString);
        }

        String language = element.getAttributeValue(ATTR_LANGUAGE);

        CS type = CSimpl.valueOf((String) TAGS_TO_PART_TYPES.get(
          element.getLocalName()), "PersonNamePartType");
        
        String qualifier = element.getAttributeValue(ATTR_QUALIFIER);
        DSET<CS> qualifiers = CSPresenter.parseList(qualifier,
          "PersonNamePartQualifier");
            
        return PNXPimpl.valueOf(element.getText(), type, qualifiers, language);
      }
      else if (node instanceof TreeContentHandler.Text)
      {
        TreeContentHandler.Text text = (TreeContentHandler.Text) node;
        return PNXPimpl.valueOf(text.getValue());
      }
      else return null;
    }
  }

  //-----------------------------------------------------------------
  protected static class PNXPBuilder extends DatatypeBuilderImpl
  {
    public void build(RimGraphXMLSpeaker.ContentBuilder builder,
      ANY value, String localName) throws BuilderException
    {
      //super.build(builder, value, localName);
      try
      {
        PNXP pnxp = (PNXP)value;
        if (builder.nullValueHandled(value, localName)) return;

        builder.addAttribute(ATTR_LANGUAGE, pnxp.language());
        builder.addAttribute(ATTR_REPRESENTATION, "TXT");
        builder.addAttribute(ATTR_MEDIA_TYPE, pnxp.mediaType());

        if (!pnxp.qualifier().isNullJ())
            builder.addAttribute(ATTR_QUALIFIER, (CS)pnxp.qualifier().iterator().next());
        builder.addAttribute(ATTR_PART_TYPE, pnxp.type());
        builder.startElement(localName);
        
        String s = pnxp.toString();
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
      PNXP pnxp = (PNXP)value;
      
      try
      {
        String tag = (String) PART_TYPES_TO_TAGS.get(pnxp.type().code().toString());
        if (tag != null)
        {
          // Emit typed part.
          build(builder, pnxp, tag);
        }
        else
        {
          // Emit untyped part.
          String s = pnxp.toString();
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
    protected String getLocalName(CS typeCode)
    {
      return (String) PART_TYPES_TO_TAGS.get(typeCode.code().toString());
    }

    //.......................................................................
    public void buildStructural(RimGraphXMLSpeaker.ContentBuilder builder,
      ANY value, String localName) throws BuilderException
    {
      throw new BuilderException("PNXP cannot be a structural attribute");
    }
  }
    
  //-------------------------------------------------------------------------
  private PNXPPresenter() {}
  public static PNXPPresenter instance() { return INSTANCE; }

  //-------------------------------------------------------------------------
  public DynamicContentHandler getContentHandler(String namespaceURI,
    String localName, String qName, Attributes atts, Datatype datatype)
  {
    // do nothing with the datatype
    return new PNXPContentHandler(namespaceURI, localName, qName, atts);
  }

  //-------------------------------------------------------------------------
  public DatatypeBuilder getBuilder() { return new PNXPBuilder(); }
}
