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
 * The Initial Developer of the Original Code is Skirmantas Kligys.
 * Portions created by Initial Developer are Copyright (C) 2002-2004 
 * Health Level Seven, Inc. All Rights Reserved.
 *
 * Contributor(s): @Matt Carlson 2/17/05 -- revised 3/29/05
 */
package org.hl7.xml;

import java.util.logging.Logger;

import org.hl7.meta.Datatype;
import org.hl7.meta.impl.ParametrizedDatatypeImpl;
import org.hl7.meta.impl.SimpleDatatypeImpl;
import org.hl7.types.ANY;
import org.hl7.types.QSET;
import org.hl7.types.impl.QSETDifferenceImpl;
import org.hl7.types.impl.QSETIntersectionImpl;
import org.hl7.types.impl.QSETPeriodicHullImpl;
import org.hl7.types.impl.QSETUnionImpl;
import org.hl7.types.impl.QSETnull;
import org.hl7.xml.builder.BuilderException;
import org.hl7.xml.builder.DatatypeBuilder;
import org.hl7.xml.builder.RimGraphXMLSpeaker;
import org.hl7.xml.builder.impl.DatatypeBuilderImpl;
import org.hl7.xml.parser.DataTypeContentHandler;
import org.hl7.xml.parser.DynamicContentHandler;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class SXPRPresenter extends DatatypePresenterBase {
	
	private static final String ATTR_ALIGNMENT = "alignment";
	private static final String ATTR_INST_SPEC = "institutionSpecified";
	private static final String ATTR_PHASE = "phase";
	private static final String ATTR_PERIOD = "period";
	private static final String ATTR_LOW = "low";
	private static final String ATTR_HIGH = "high";
	private static final String COMP = "comp";
	private static final String ATTR_OPERATOR = "operator";
	protected static final Logger LOGGER = Logger.getLogger("org.hl7.xml");
	
	//-------------------------------------------------------------------------
	private static final SXPRPresenter INSTANCE = new SXPRPresenter();
	
	//-------------------------------------------------------------------------
	protected static class SXPRContentHandler extends DataTypeContentHandler
	implements DynamicContentHandler.ResultReceiver {
		
		QSET _result=null;
		String _operator=null;
		String _currentLocalName=null;
		String _xsi=null;
		boolean foundIVL=false, foundPIVL=false, foundComp=false;
		QSET _origAccum=null; 
		
		public SXPRContentHandler(String namespaceURI, String localName,
				String qName, Attributes atts) {
			super();
		}
		
		/*************************************************************************
		 * startElement:
		 *
		 * Called when a comp element is found.  This uses the xsi:type attribute
		 * to determine which Presenter to switch contexts with: IVL, PIVL, SXPR.
		 * As of now, the xsi:type is mandatory.  In the future, this will not be
		 * mandatory and SXPR will implement Datatypable.  Then, if xsi:type is
		 * not specified, the Datatype can be used to delegate.
		 *************************************************************************/
		public void startElement(String namespaceURI, String localName,
				String qName, Attributes atts) {
			
			DynamicContentHandler newContext = null;
			
			_currentLocalName=localName;
			_xsi = atts.getValue(DatatypePresenterBase.W3_XML_SCHEMA, "type");
			
			if (_xsi.contains("SXPR")) {
				newContext=new SXPRPresenter.SXPRContentHandler(namespaceURI,localName, qName, atts);
			} else if (_xsi.contains("PIVL")) {
				newContext=PIVLPresenter.instance().getContentHandler(namespaceURI, localName, qName, atts, new ParametrizedDatatypeImpl("PIVL",new SimpleDatatypeImpl("TS")));
			} else if (_xsi.contains("IVL") && !_xsi.contains("PIVL")) {
				newContext=IVLPresenter.instance().getContentHandler(namespaceURI, localName, qName, atts, new ParametrizedDatatypeImpl("IVL",new SimpleDatatypeImpl("TS")));
			} 
			
			// Now we make the new context the content handler of the XML parser.
			if (newContext != null) {
				this.suspendWith(newContext, atts);
			}
		}
		
		/*************************************************************************
		 * endElement:
		 *
		 * This must handle the operator attribute and then return the _result.
		 ************************************************************************/
		public void endElement(String namespaceURI, String localName,
				String qName) throws SAXException {
			if(_operator!=null) {
				if(_origAccum == null) {
					if(!_operator.equals(Operator.I))
						throw new SAXException("operator "+_operator+" with null accumulator");
				} else {
					if (_operator.equals(Operator.I)){ // union
						_result=QSETUnionImpl.valueOf(_origAccum,_result);
					}
					else if (_operator.equals(Operator.E)) { // diff
						_result=QSETDifferenceImpl.valueOf(_origAccum,_result);
					}
					else if (_operator.equals(Operator.A)) { // intersect
						_result=QSETIntersectionImpl.valueOf(_origAccum,_result);
					}
					else if (_operator.equals(Operator.H)) { // hull
						_result=_result.hull();
					}
					else if (_operator.equals(Operator.P)) { // periodic hull
						_result=QSETPeriodicHullImpl.valueOf(_origAccum,_result);
					}
				}
			}
			// be sure to set the accumulator!
			Accumulator.getInstance().setValue(_result);
			if (_result == null)
				returnResult(QSETnull.NI);
			else
				returnResult(_result);
		}
		
		public void notifyResult(Object result) {
			_result=(QSET)result;
		}
		
		public Object getResult() throws SAXException {
			return _result;
		}
		
		/**********************************************************************
		 * notifyActivation
		 *
		 * Our chance to set the attributes.  Operator is the only valid
		 * attribute for SXPR.
		 **********************************************************************/
		protected void notifyActivation(Attributes atts) {
			_origAccum = Accumulator.getInstance().getValue();
			Accumulator.getInstance().clear();
			_operator=atts.getValue(ATTR_OPERATOR);
		}
	}
	
	//-------------------------------------------------------------------------
	private static class SXPRBuilder extends DatatypeBuilderImpl {
		public void build(RimGraphXMLSpeaker.ContentBuilder builder, ANY value,
				String localName) throws BuilderException {
			
			/*
			 * We never actually build an SXPR explicitly.
			 * See QSETPresenter.QSETBuilder to see how SXPRs are built
			 */
			
			super.build(builder, value, localName);
		}
		
		//.......................................................................
		public void buildStructural(RimGraphXMLSpeaker.ContentBuilder builder,
				ANY value, String localName) throws BuilderException {
			throw new BuilderException("SXPR<T> cannot be a structural attribute");
		}
	}
	
	//-------------------------------------------------------------------
	private SXPRPresenter() {}
	
	public static SXPRPresenter instance() {
		return INSTANCE;
	}
	
	//-------------------------------------------------------------------------
	public DynamicContentHandler getContentHandler(String namespaceURI, String localName, String qName, Attributes atts, Datatype datatype) {
		// do nothing with the datatype
		return new SXPRContentHandler(namespaceURI, localName, qName, atts);
	}
	
	//-------------------------------------------------------------------------
	public DatatypeBuilder getBuilder() {
		return new SXPRBuilder();
	}
}

