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

import org.hl7.types.ENXP;
import org.hl7.types.ANY;
import org.hl7.types.CS;
import org.hl7.types.DSET;
import org.hl7.types.impl.ENXPimpl;
import org.hl7.types.impl.ENXPnull;
import org.hl7.types.impl.CSimpl;
import org.hl7.types.impl.CSnull;
import org.hl7.meta.Datatype;

import org.hl7.types.enums.EntityNamePartType;
import org.hl7.types.enums.EntityNamePartQualifier;

public class ENXPPresenter extends STPresenter {
  public static final String ATTR_PART_TYPE = "partType";
  public static final String ATTR_QUALIFIER = "qualifier";
  
  /** Maps local element names into part types. */
  public static final Map<String, EntityNamePartType> TAGS_TO_PART_TYPES = new HashMap<String, EntityNamePartType>();
  
  static {
    TAGS_TO_PART_TYPES.put("delimiter", EntityNamePartType.Delimiter);
    TAGS_TO_PART_TYPES.put("family", EntityNamePartType.Family);
    TAGS_TO_PART_TYPES.put("given", EntityNamePartType.Given);
    TAGS_TO_PART_TYPES.put("prefix", EntityNamePartType.Prefix);
    TAGS_TO_PART_TYPES.put("suffix", EntityNamePartType.Suffix);
  }
  
  /** Maps part types into local element names. */
  public static final Map<EntityNamePartType, String> PART_TYPES_TO_TAGS = new HashMap<EntityNamePartType, String>();
  
  static {
    PART_TYPES_TO_TAGS.put(EntityNamePartType.Delimiter, "delimiter");
    PART_TYPES_TO_TAGS.put(EntityNamePartType.Family, "family");
    PART_TYPES_TO_TAGS.put(EntityNamePartType.Given, "given");
    PART_TYPES_TO_TAGS.put(EntityNamePartType.Prefix, "prefix");
    PART_TYPES_TO_TAGS.put(EntityNamePartType.Suffix, "suffix");
  }
  
  private static final ENXPPresenter INSTANCE = new ENXPPresenter();
  
  //-------------------------------------------------------------------------
  protected static class ENXPContentHandler extends TreeContentHandler
  {
    //.......................................................................
    public ENXPContentHandler(String namespaceURI, String localName,
			      String qName, Attributes atts)
    {
      super(namespaceURI, localName, qName, atts);
    }

    //.......................................................................
    protected void returnResult(Object intermediate) throws SAXException
    {
      TreeContentHandler.Element element =
        (TreeContentHandler.Element) intermediate;
      ENXP result = valueOf(element);
      super.returnResult(result);
    }
    
    //.......................................................................
    protected static ENXP valueOf(TreeContentHandler.Node node)
    {
      if (node instanceof TreeContentHandler.Element)
      {
        TreeContentHandler.Element element = (TreeContentHandler.Element) node;
        String nullFlavorString = element.getAttributeValue(DatatypePresenterBase.ATTR_NULL_FLAVOR);
        if (nullFlavorString != null)
        {
          return ENXPnull.valueOf(nullFlavorString);
        }

        String language = element.getAttributeValue(ATTR_LANGUAGE);

        CS type = TAGS_TO_PART_TYPES.get(element.getLocalName());
	if(type == null)
	  type = CSnull.NI;
	
        DSET<CS> qualifiers = CSPresenter.parseList(element.getAttributeValue(ATTR_QUALIFIER), EntityNamePartQualifier.root.codeSystem().toString());
            
        return ENXPimpl.valueOf(element.getText(), type, qualifiers, language);
      }
      else if (node instanceof TreeContentHandler.Text)
      {
        TreeContentHandler.Text text = (TreeContentHandler.Text) node;
        return ENXPimpl.valueOf(text.getValue());
      }
      else return null;
    }
  }

  //-----------------------------------------------------------------
  protected static class ENXPBuilder extends DatatypeBuilderImpl
  {
    public void build(RimGraphXMLSpeaker.ContentBuilder builder,
      ANY value, String localName) throws BuilderException
    {
      ENXP enxp = (ENXP)value;
      
      try
      {
        if (builder.nullValueHandled(value, localName)) return;

        builder.addAttribute(ATTR_PART_TYPE, enxp.type().code());
        if (enxp.qualifier()!=null && !enxp.qualifier().isNullJ())
            builder.addAttribute(ATTR_QUALIFIER, ((CS)enxp.qualifier().iterator().next()).code());
        builder.addAttribute(ATTR_LANGUAGE, enxp.language());
        builder.addAttribute(ATTR_REPRESENTATION, "TXT");
        builder.addAttribute(ATTR_MEDIA_TYPE, enxp.mediaType());

        builder.startElement(localName);
        
        String s = enxp.toString();
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
      ENXP enxp = (ENXP)value;
      
      try
      {
        String tag = (String) PART_TYPES_TO_TAGS.get(enxp.type());
        if (tag != null)
        {
          // Emit typed part.
          build(builder, enxp, tag);
        }
        else
        {
          // Emit untyped part.
          String s = enxp.toString();
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
      throw new BuilderException("ENXP cannot be a structural attribute");
    }
  }
    
  //-------------------------------------------------------------------------
  protected ENXPPresenter() {}
  public static ENXPPresenter instance() { return INSTANCE; }

  //-------------------------------------------------------------------------
  public DynamicContentHandler getContentHandler(String namespaceURI,
    String localName, String qName, Attributes atts, Datatype datatype)
  {
    // do nothing with the datatype
    return new ENXPContentHandler(namespaceURI, localName, qName, atts);
  }

  //-------------------------------------------------------------------------
  public DatatypeBuilder getBuilder() { return new ENXPBuilder(); }
}
