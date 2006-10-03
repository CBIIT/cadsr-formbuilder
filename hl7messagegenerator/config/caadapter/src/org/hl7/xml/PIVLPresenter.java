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
import org.hl7.xml.parser.DynamicContentHandler;
import org.hl7.xml.parser.TreeContentHandler;

import org.hl7.types.*;
import org.hl7.types.impl.*;

import org.hl7.meta.Datatype;
import org.hl7.meta.ParametrizedDatatype;
import org.hl7.meta.impl.SimpleDatatypeImpl;
import org.hl7.meta.impl.ParametrizedDatatypeImpl;
import org.hl7.meta.util.MetaUtils;
import java.util.Iterator;
import java.util.logging.Logger;

public class PIVLPresenter extends DatatypePresenterBase {
  //-------------------------------------------------------------------------
  private static final String ATTR_ALIGNMENT = "alignment";
  private static final String ATTR_INST_SPEC = "institutionSpecified";
  private static final String ATTR_PHASE = "phase";
  private static final String ATTR_PERIOD = "period";
  private static final String ATTR_OPERATOR = "operator";
  private static final String ATTR_VALUE = "value"; // might not be needed
  protected static final Logger LOGGER = Logger.getLogger("org.hl7.xml");
  //-------------------------------------------------------------------------
  private static final PIVLPresenter INSTANCE = new PIVLPresenter();

  //-------------------------------------------------------------------------
  protected static class PIVLContentHandler extends DataTypeContentHandler
    implements DynamicContentHandler.ResultReceiver {
    
    BL _institutionSpecified=BLimpl.FALSE;
    CS _alignment=CSnull.NI;
    IVL _phase=IVLnull.NI;
    QTY _period=QTYnull.NI;
    QSET _result=null;
    String _operator=null;
    String _currentLocalName=null;
    Datatype _datatype=null;

    private PIVLContentHandler(Datatype datatype) {
      super();
      _datatype=datatype;
    }

    /***********************************************************************
     * startElement:
     *
     * Called when a PIVL is found.  The datatype should never be null when
     * startElement is called, so an error is thrown when this occurs.
     * The datatype is used to set the Datatype of IVLPresenter when the
     * phase is found..
     ***********************************************************************/
    public void startElement(String namespaceURI, String localName,
			     String qName, Attributes atts) {

      DynamicContentHandler newContext = null;
      _currentLocalName=localName;
   
      if (_datatype == null) {
	throw new UnsupportedOperationException("_datatype is null in PIVLPresenter");
      }
      if (_currentLocalName==ATTR_PHASE) {
	newContext=IVLPresenter.instance().getContentHandler(namespaceURI,localName,qName, atts, new ParametrizedDatatypeImpl("IVL",((ParametrizedDatatype)_datatype).getParameter(0)));
      } else if (_currentLocalName==ATTR_PERIOD) {
	// Assuming the period will never be anything but a PQ
	newContext=QTYPresenter.instance().getContentHandler(namespaceURI,localName, qName, atts, new SimpleDatatypeImpl("PQ"));
      } else {
	throw new UnsupportedOperationException("unknown localName "+_currentLocalName);
      }
      
      // Now we make the new context the content handler of the XML
      // parser.
   
      if (newContext != null) {
	this.suspendWith(newContext, atts);
      }
      else {
	// no special ContentHandler class available, so we will use
	// the TreeContentHandler and then later we will hopefully
	// find a factory method that can use the little DOM to build
	// a value of this kind.
	LOGGER.warning("no content handler for data type "
		       + " build tree " + localName);
	DynamicContentHandler tree =
	  new TreeContentHandler(namespaceURI, localName, qName, atts);
	this.suspendWith(tree, null);
      }
    }

    /*************************************************************************
     * endElement:
     *
     * This must get the PIVL value, handle the operator attribute,  and then
     * return the _result.
     ************************************************************************/
    public void endElement(String namespaceURI, String localName,
			   String qName) throws SAXException {
      PIVL pivlVal=PIVLimpl.valueOf(_phase, (QTY.diff)_period, _alignment, _institutionSpecified);
      _result=pivlVal;

      if (_operator!=null) {
	QSET accumValue = Accumulator.getInstance().getValue();
	if(accumValue == null) {
	  if(!_operator.equals(Operator.I))
	    throw new SAXException("operator "+_operator+" with null accumulator");
	} else {
	  if (_operator.equals(Operator.I)) {  // union
	    _result=QSETUnionImpl.valueOf(accumValue,pivlVal); 
	  }
	  else if (_operator.equals(Operator.E)) { // diff
	    _result=QSETDifferenceImpl.valueOf(accumValue,pivlVal);
	  }
	  else if (_operator.equals(Operator.A)) { // intersect
	    _result=QSETIntersectionImpl.valueOf(accumValue,pivlVal);
	  }
	  else if (_operator.equals(Operator.H)) { // hull
	    throw new UnsupportedOperationException("You cannot try to find the hull of a PIVL/QSET");
	  }
	  else if (_operator.equals(Operator.P)) { // periodic hull
	    _result=QSETPeriodicHullImpl.valueOf(accumValue,pivlVal);
	  }
	}
      }
      // Don't forget the accumulator!
      Accumulator.getInstance().setValue(_result);

      /*
       * Setting the accumulator is disabled when before parsing the phase
       * of a PIVL.  This is to avoid the phase of the pivl wiping out what
       * was in the accumulator when PIVLContentHandler was instantiated.
       * Now that we are finished with the PIVL, we must enable the
       * accumulator.
       */
      Accumulator.getInstance().setShouldSet(true);    

      returnResult(_result);
    }

    /**********************************************************************
     * notifyActivation
     *
     * Our chance to set the attributes.  Must set alignment, operator, 
     * and institutionSpecified attributes.
     **********************************************************************/
    protected void notifyActivation(Attributes atts) {
      _institutionSpecified = BLimpl.valueOf(atts.getValue(ATTR_INST_SPEC));
      _alignment = org.hl7.types.enums.CalendarCycle.valueOf(ValueFactory.getInstance().STvalueOfLiteral(atts.getValue(ATTR_ALIGNMENT)));
      _operator=atts.getValue(ATTR_OPERATOR);

      /*
       * Setting the accumulator is disabled when before parsing the phase
       * of a PIVL.  This is to avoid the phase of the pivl wiping out what
       * was in the accumulator when PIVLContentHandler was instantiated.
       * Now that we are finished with the PIVL, we must enable the
       * accumulator.
       */
      Accumulator.getInstance().setShouldSet(false);
    }
    
    public void notifyResult(Object result) {
      if (_currentLocalName==ATTR_PERIOD)
	_period=(QTY)result;
      else if (_currentLocalName==ATTR_PHASE)
	_phase=(IVL)result;
    }

    public Object getResult() throws SAXException { return _result; }
  }  
  
  //-------------------------------------------------------------------------
  private static class PIVLBuilder extends DatatypeBuilderImpl
  {
    public void build(RimGraphXMLSpeaker.ContentBuilder builder, ANY value,
		      String localName) throws BuilderException
    {

      super.build(builder, value, localName);
      try
	{
	  PIVL pivl = (PIVL)value;
	  if (builder.nullValueHandled(value, localName)) return;
	  //builder.addAttribute(DatatypePresenter.W3_XML_SCHEMA, "type", "xsi:type", "CDATA", "PIVL_TS");
	  builder.addAttribute(ATTR_ALIGNMENT, pivl.alignment());
	  builder.addAttribute(ATTR_INST_SPEC, pivl.institutionSpecified());
	  builder.startElement(DatatypePresenterBase.HL7_URI,localName,localName,builder.getAttributes());
	  //builder.startElement(localName);
	  builder.build(pivl.phase(), ATTR_PHASE);
	  builder.build(pivl.period(), ATTR_PERIOD);
	  builder.endElement(DatatypePresenterBase.HL7_URI,localName,localName);
	  //builder.endElement(localName);
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
      throw new BuilderException("PIVL<T> cannot be a structural attribute");
    }
  } // PIVLBuilder

    //-------------------------------------------------------------------------
  private PIVLPresenter() {}
  public static PIVLPresenter instance() { return INSTANCE; }
  
  //-------------------------------------------------------------------------
  public DynamicContentHandler getContentHandler(String namespaceURI, String localName, String qName, Attributes atts, Datatype datatype)
  {
    return new PIVLContentHandler(datatype);
  }
  
  //-------------------------------------------------------------------------
  public DatatypeBuilder getBuilder() { return new PIVLBuilder(); }
}
