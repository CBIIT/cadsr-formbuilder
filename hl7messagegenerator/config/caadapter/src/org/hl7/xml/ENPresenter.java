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
import org.hl7.meta.Datatype;
import org.hl7.meta.impl.DatatypeMetadataFactoryImpl;

import org.hl7.types.*;
import org.hl7.types.impl.*;
import org.hl7.xml.builder.BuilderException;
import org.hl7.xml.builder.DatatypeBuilder;
import org.hl7.xml.builder.RimGraphXMLSpeaker;
import org.hl7.xml.builder.impl.DatatypeBuilderImpl;
import org.hl7.xml.parser.*;
import org.hl7.xml.parser.DataTypeContentHandler;
import org.hl7.xml.parser.DynamicContentHandler;

import org.hl7.util.FactoryException;

public class ENPresenter extends DatatypePresenterBase
{
  public static final String ATTR_USE = "use";
  public static final String TAG_VALID_TIME = "validTime";
  
  private static final ENPresenter INSTANCE = new ENPresenter();

  protected static class ENContentHandler extends DataTypeContentHandler implements DynamicContentHandler, DynamicContentHandler.ResultReceiver {

    private EN _result = null;
    private DSET<CS> _use = null;
    private IVL<TS> _validTime = null;
    private List<ENXP> _nameParts = new ArrayList<ENXP>();
   
    protected void notifyActivation(Attributes atts) {
      String nullFlavorString = atts.getValue(DatatypePresenterBase.ATTR_NULL_FLAVOR);
      if (nullFlavorString != null) {
        _result = ENnull.valueOf(nullFlavorString);
      } else {
        String useString = atts.getValue(ATTR_USE);
				if(useString != null)
					_use = CSPresenter.parseList(useString, "2.16.840.1.113883.5.45"); // FIXME use enums
      }
    }

    public void notifyResult(Object result) {
      if(_currentLocalName == TAG_VALID_TIME)
				_validTime=(IVL<TS>)result;
      else if(result instanceof ENXP) {
				_nameParts.add((ENXP)result);
      }
    }
		
    private static final Datatype PART_DATATYPE;
    private static final Datatype VALID_TIME_DATATYPE;
		
    static {
      try {
				VALID_TIME_DATATYPE = DatatypeMetadataFactoryImpl.instance().create("IVL<TS>");
        PART_DATATYPE = DatatypeMetadataFactoryImpl.instance().create("ENXP");
      } catch(Exception x) {
				throw new Error(x);
      }
    }
		
    private String _currentLocalName;
    
    public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
      if(_stringBuffer != null) 
	flushText();

      _currentLocalName = localName.intern();
      DynamicContentHandler newContext = null;

      if(_currentLocalName == TAG_VALID_TIME) {
	try {
	  newContext = VALID_TIME_DATATYPE.getHandler(namespaceURI, localName, qName, atts);
	} catch(FactoryException x) { }
      } else {
	newContext = ENXPPresenter.instance().getContentHandler(namespaceURI, localName, qName, atts, PART_DATATYPE);
      }

      if(newContext == null)
	newContext = new IgnoreContentHandler();

      this.suspendWith(newContext, atts);      
    }

    public void endElement(String namespaceURI,
			   String localName,
			   String qName) throws SAXException {
      if(_stringBuffer != null) 
	flushText();

      returnResult(getResult());      
    }
    
    protected Object getResult() {
      if(_result == null)
				_result = ENimpl.valueOf(_nameParts, _use, _validTime);
      return _result;
    }

    private StringBuffer _stringBuffer = null;

    /** This only gathers characters if a _result is not already
     * set. I.e., if this came with a nullFlavor attribute, we
     * silently ignore the characters. */
    public void characters(char[] ch, int start, int length) {
      if (_result == null) {
        if(_stringBuffer == null)
	  _stringBuffer = new StringBuffer(length);
	_stringBuffer.append(ch,start,length);
      }
    }

    private void flushText() {
      if(_stringBuffer != null && _stringBuffer.length() > 0) {
	_nameParts.add(ENXPimpl.valueOf(_stringBuffer.toString()));
	_stringBuffer = null;
      }
    }
  }
  
  //-------------------------------------------------------------------------
  private static class ENBuilder extends DatatypeBuilderImpl
  {
    private final CSPresenter.CSBuilder csBuilder_ =
      (CSPresenter.CSBuilder)CSPresenter.instance().getBuilder();
    private final ENXPPresenter.ENXPBuilder enxpBuilder_ =
      (ENXPPresenter.ENXPBuilder)ENXPPresenter.instance().getBuilder();
    
    //.......................................................................
    public void build(RimGraphXMLSpeaker.ContentBuilder builder, ANY value,
      String localName) throws BuilderException
    {
      super.build(builder, value, localName);
      EN en = (EN)value;
      try
      {
        if (builder.nullValueHandled(value, localName)) return;
        csBuilder_.buildAttribute(builder, en.use(), ATTR_USE);

        builder.startElement(localName);
        
        for (Iterator<ENXP> it = en.iterator(); it.hasNext(); )
        {
          ENXP enxp = (ENXP) it.next();
          enxpBuilder_.build(builder, enxp);
        }
        
        // Emit validTime if present.
        if (!en.validTime().isNullJ())
          builder.build(en.validTime(), TAG_VALID_TIME);
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
      throw new BuilderException("EN cannot be a structural attribute");
    }
  }
  
  //----------------------------------------------------------------------------
  protected ENPresenter() {}
  public static ENPresenter instance() { return INSTANCE; }
  
  //-------------------------------------------------------------------------
  public ContentHandler getContentHandler(String namespaceURI, String localName, String qName, Attributes atts, Datatype datatype)
  {
    // do nothing with the datatype
    return new ENContentHandler();
  }
  
  //-------------------------------------------------------------------------
  public DatatypeBuilder getBuilder() { return new ENBuilder(); }
}
