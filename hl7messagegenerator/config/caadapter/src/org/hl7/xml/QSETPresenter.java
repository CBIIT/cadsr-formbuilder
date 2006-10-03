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
 * Contributor(s): Matt Carlson
 */
package org.hl7.xml;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import org.hl7.xml.builder.RimGraphXMLSpeaker;
import org.hl7.xml.builder.impl.DatatypeBuilderImpl;
import org.hl7.xml.builder.BuilderException;
import org.hl7.xml.builder.DatatypeBuilder;
import org.hl7.xml.parser.TreeContentHandler;

import org.hl7.types.*;
import org.hl7.types.impl.*;
import org.hl7.meta.Datatype;

import java.util.*;

/*
  @ MC 2/14/05
  Class to handle presenting/building qsets.  The ContentHandler for
  a QSET simply returns null as of now.  This is because a qset will never
  be mentioned in the message files-- the message files will specify
  IVLs/PIVLs/etc along with operator attributes that will build the
  resulting QSET.  The QSET builder builds the appropriate QSET based on
  the passed value.

  This class does depend on the implementation of QSETs!  If that implementation
  were to change, it is possible that this class would also need to change!
*/
public class QSETPresenter extends DatatypePresenterBase
{
  //-------------------------------------------------------------------------
  private static final String ATTR_OPERATOR = "operator";
  private static final String ATTR_COMP = "comp";
  
  //-------------------------------------------------------------------------
  private static final QSETPresenter INSTANCE = new QSETPresenter();

  //-------------------------------------------------------------------------
  protected static class QSETContentHandler extends TreeContentHandler {
    
    Datatype _datatype=null;

    //.......................................................................
    public QSETContentHandler(String namespaceURI, String localName,
			      String qName, Attributes atts, Datatype datatype) {
      super(namespaceURI, localName, qName, atts);
      _datatype=datatype;
    }
    
    //.......................................................................
    protected void returnResult(Object intermediate) throws SAXException {
      TreeContentHandler.Element element =
        (TreeContentHandler.Element)intermediate;
      QSET result = valueOf(element);
      super.returnResult(result);
    }
    
    //-------------------------------------------------------------------------
    protected QSET valueOf(TreeContentHandler.Element element) {
      String nullFlavorString = element.getAttributeValue(DatatypePresenterBase.ATTR_NULL_FLAVOR);

      /*
       * We do nothing here.. this shouldn't be used for QSETs since the
       * QSETs are unknown to the messages.  The messages only know about
       * IVLs/PIVLs/SXPRs.  See IVL/PIVL/SXPR valueOf for more info
       */

      if (nullFlavorString != null) {
        return QSETnull.valueOf(nullFlavorString);
      }
      return QSETnull.NI;
    }
  }
  
  //-------------------------------------------------------------------------
  private static class QSETBuilder extends DatatypeBuilderImpl
  {
    public void build(RimGraphXMLSpeaker.ContentBuilder builder, ANY value,
		      String localName) throws BuilderException {

      /*
       * The idea is this: Determine what type of qset we have.
       * Look at the values of value and take appropriate action:
       * If it is a pivl, let the PIVLPresenter take care of it.
       * If it is an IVL, let the IVLPresenter take care of it.
       * If it is a QSET, wrap it in parentheses and recurr on it.
       */

      super.build(builder, value, localName);
      QSET qset = (QSET)value;
      
      if (isAQSETUnion(qset)) {
	QSETUnionImpl union=(QSETUnionImpl)qset;
	for (int i=0;i<union.size();i++) {
	  QSET arg = union.getArgument(i);
	  if (isAQSET(arg)) {
	    try {
	      if (i!=0) builder.addAttribute(ATTR_OPERATOR,Operator.I);
	      builder.addAttribute(DatatypePresenterBase.W3_XML_SCHEMA, "type", "xsi:type", "CDATA", "SXPR_TS");
	      builder.startElement(DatatypePresenterBase.HL7_URI,localName,localName,builder.getAttributes());
	      QSETPresenter.instance().getBuilder().build(builder,arg,ATTR_COMP);
	      builder.endElement(DatatypePresenterBase.HL7_URI,localName,localName);
	    } catch (SAXException ex) {
	      throw new BuilderException(ex);
	    }
	  }
	  else if (isAnIVL(arg)){
	    if (i!=0) builder.addAttribute(ATTR_OPERATOR,STjlStringAdapter.valueOf(Operator.I));
	    IVLPresenter.instance().getBuilder().build(builder,arg,localName);
	  }
	  else if (isAPIVL(arg)){
	    if (i!=0) builder.addAttribute(ATTR_OPERATOR,STjlStringAdapter.valueOf(Operator.I));
	    PIVLPresenter.instance().getBuilder().build(builder,arg,localName);
	  }
	}
      }
      
      else if (isAQSETIntersection(qset)) {
	QSETIntersectionImpl intersection=(QSETIntersectionImpl)qset;
	for (int i=0;i<intersection.size();i++) {
	  QSET arg=intersection.getArgument(i);
	  if (isAQSET(arg)) {
	    try {
	      if (i!=0) builder.addAttribute(ATTR_OPERATOR,Operator.A);
	      builder.addAttribute(DatatypePresenterBase.W3_XML_SCHEMA, "type", "xsi:type", "CDATA", "SXPR_TS");
	      builder.startElement(DatatypePresenterBase.HL7_URI,localName,localName,builder.getAttributes());
	      QSETPresenter.instance().getBuilder().build(builder,arg,ATTR_COMP);
	      builder.endElement(DatatypePresenterBase.HL7_URI,localName,localName);
	    } catch (SAXException ex) {
	      throw new BuilderException(ex);
	    }
	  }
	  else if(isAnIVL(arg)) {
	    if (i!=0) builder.addAttribute(ATTR_OPERATOR,STjlStringAdapter.valueOf(Operator.A));
	    IVLPresenter.instance().getBuilder().build(builder,arg,localName);
	  }
	  else if (isAPIVL(arg)){
	    if (i!=0) builder.addAttribute(ATTR_OPERATOR,STjlStringAdapter.valueOf(Operator.A));
	    PIVLPresenter.instance().getBuilder().build(builder,arg,localName);
	  }
	}
      }

      else if (isAQSETDifference(qset)) {
	QSETDifferenceImpl diff=(QSETDifferenceImpl)qset;
	QSET subtrahend=diff.getSubtrahend();
	QSET minuend=diff.getMinuend();
	// do minuend stuff
	if (isAQSET(minuend)) {
	  try {
	    builder.addAttribute(DatatypePresenterBase.W3_XML_SCHEMA, "type", "xsi:type", "CDATA", "SXPR_TS");
	    builder.startElement(DatatypePresenterBase.HL7_URI,localName,localName,builder.getAttributes());
	    QSETPresenter.instance().getBuilder().build(builder,minuend,ATTR_COMP);
	    builder.endElement(DatatypePresenterBase.HL7_URI,localName,localName);
	  } catch (SAXException ex) {
	    throw new BuilderException(ex);
	  }
	}
	else if(isAnIVL(minuend)) {
	  IVLPresenter.instance().getBuilder().build(builder,minuend,localName);
	}
	else if (isAPIVL(minuend)){
	  PIVLPresenter.instance().getBuilder().build(builder,minuend,localName);
	}	
	// now do subtrahend stuff
	if (isAQSET(subtrahend)) {
	  try {
	    builder.addAttribute(ATTR_OPERATOR,Operator.E);
	    builder.addAttribute(DatatypePresenterBase.W3_XML_SCHEMA, "type", "xsi:type", "CDATA", "SXPR_TS");
	    builder.startElement(DatatypePresenterBase.HL7_URI,localName,localName,builder.getAttributes());
	    QSETPresenter.instance().getBuilder().build(builder,subtrahend,ATTR_COMP);
	    builder.endElement(DatatypePresenterBase.HL7_URI,localName,localName);
	  } catch (SAXException ex) {
	    throw new BuilderException(ex);
	  }
	}
	else if(isAnIVL(subtrahend)) {
	  builder.addAttribute(ATTR_OPERATOR,STjlStringAdapter.valueOf(Operator.E));
	  IVLPresenter.instance().getBuilder().build(builder,subtrahend,localName);
	}
	else if (isAPIVL(subtrahend)){
	  builder.addAttribute(ATTR_OPERATOR,STjlStringAdapter.valueOf(Operator.E));
	  PIVLPresenter.instance().getBuilder().build(builder,subtrahend,localName);
	}
      }

      else if (isAQSETPeriodicHull(qset)) {
	QSETPeriodicHullImpl ph=(QSETPeriodicHullImpl)qset;
	QSET thisset=ph.getThisSet();
	QSET thatset=ph.getThatSet();
	// do thisset stuff
	if (isAQSET(thisset)) {
	  try {
	    builder.addAttribute(DatatypePresenterBase.W3_XML_SCHEMA, "type", "xsi:type", "CDATA", "SXPR_TS");
	    builder.startElement(DatatypePresenterBase.HL7_URI,localName,localName,builder.getAttributes());
	    QSETPresenter.instance().getBuilder().build(builder,thisset,ATTR_COMP);
	    builder.endElement(DatatypePresenterBase.HL7_URI,localName,localName);
	  } catch (SAXException ex) {
	    throw new BuilderException(ex);
	  }
	}
	else if(isAnIVL(thisset)) {
	  IVLPresenter.instance().getBuilder().build(builder,thisset,localName);
	}
	else if (isAPIVL(thisset)){
	  PIVLPresenter.instance().getBuilder().build(builder,thisset,localName);
	}
	
	// now do thatset stuff
	if (isAQSET(thatset)) {
	  try {
	    builder.addAttribute(ATTR_OPERATOR,Operator.P);
	    builder.addAttribute(DatatypePresenterBase.W3_XML_SCHEMA, "type", "xsi:type", "CDATA", "SXPR_TS");
	    builder.startElement(DatatypePresenterBase.HL7_URI,localName,localName,builder.getAttributes());
	    QSETPresenter.instance().getBuilder().build(builder,thatset,ATTR_COMP);
	    builder.endElement(DatatypePresenterBase.HL7_URI,localName,localName);
	  } catch (SAXException ex) {
	    throw new BuilderException(ex);
	  }
	}
	else if(isAnIVL(thatset)) {
	  builder.addAttribute(ATTR_OPERATOR,STjlStringAdapter.valueOf(Operator.P));
	  IVLPresenter.instance().getBuilder().build(builder,thatset,localName);
	}
	else if (isAPIVL(thatset)){
	  builder.addAttribute(ATTR_OPERATOR,STjlStringAdapter.valueOf(Operator.P));
	  PIVLPresenter.instance().getBuilder().build(builder,thatset,localName);
	}
      }
    }

    private boolean isAQSETUnion(ANY arg) {
      return (arg instanceof QSETUnionImpl); 
    }
    
    private boolean isAQSETIntersection(ANY arg) {
      return arg instanceof QSETIntersectionImpl; 
    }
    
    private boolean isAQSETDifference(ANY arg) {
      return (arg instanceof QSETDifferenceImpl); 
    }

    private boolean isAQSETPeriodicHull(ANY arg) {
      return (arg instanceof QSETPeriodicHullImpl); 
    }

    private boolean isAQSETSingularity(ANY arg) {
      return (arg instanceof QSETSingularityImpl); 
    }
    
    private boolean isAQSET(ANY arg) {
      return (isAQSETSingularity(arg) || isAQSETDifference(arg) ||
	      isAQSETUnion(arg) || isAQSETPeriodicHull(arg) ||
	      isAQSETIntersection(arg));
    }

    private boolean isAnIVL(ANY arg) {
      return (arg instanceof IVLimpl);
    }
    
    private boolean isAPIVL(ANY arg) {
      return (arg instanceof PIVLimpl);
    }
    
    //.......................................................................
    public void buildStructural(RimGraphXMLSpeaker.ContentBuilder builder,
				ANY value, String localName) throws BuilderException {
      throw new BuilderException("QSET<T> cannot be a structural attribute");
    }
  }

  //-------------------------------------------------------------------
  private QSETPresenter() {}
  public static QSETPresenter instance() { return INSTANCE; }
  
  //-------------------------------------------------------------------------
  public ContentHandler getContentHandler(String namespaceURI, String localName, String qName, Attributes atts, Datatype datatype) {
    return new QSETContentHandler(namespaceURI, localName, qName, atts, datatype);
  }
  
  //-------------------------------------------------------------------------
  public DatatypeBuilder getBuilder() { return new QSETBuilder(); }
}
