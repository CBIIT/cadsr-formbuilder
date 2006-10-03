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
 * The Initial Developer of the Original Code is Gunther Schadow.
 * Portions created by Initial Developer are Copyright (C) 2002-2004 
 * Health Level Seven, Inc. All Rights Reserved.
 *
 * Contributor(s): 
 */

package org.hl7.xml.parser;

import org.hl7.meta.Datatype;
import org.hl7.meta.ParametrizedDatatype;
import org.hl7.meta.SimpleDatatype;
import org.hl7.meta.UnknownDatatypeException;
import org.hl7.meta.impl.DatatypeMetadataFactoryDatatypes;
import org.hl7.meta.impl.DatatypeMetadataFactoryImpl;
import org.hl7.types.*;
import org.hl7.util.FactoryException;
import org.hl7.xml.DatatypePresenter;
import org.hl7.xml.SXPRPresenter;
import org.hl7.xml.DatatypePresenterBase;
import org.xml.sax.*;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

/**
 A simple thing that bootstraps the XML parsing process for standalone
 data type value. This is used when serializing just a data type value without
 any RIM classes (as in some object/relational persistence mechanisms)
 
 I do this as an extension of DataTypeContentHandler
 */
public class StandaloneDataTypeContentHandler extends DataTypeContentHandler
implements DynamicContentHandler, DynamicContentHandler.ResultReceiver
{
	protected static final Logger LOGGER = Logger.getLogger("org.hl7.xml.parser");
	
	Collection _collection = null; 
	Datatype _datatype = null;
	Object _result = null;
	
	private StandaloneDataTypeContentHandler(Datatype datatype) { _datatype = datatype; }
	
	public void notifyResult(Object result) {
		assert _result == null;
		_result = result;
		
		if (this._collection == null)
			this._collection = new ArrayList();
		
		if (_result == null)
			new Error("WARNING: Result is null!").printStackTrace();
		
		this._collection.add(_result);
	}
	
	public Object getResult() throws SAXException {
		if (_collection != null) flushCollection();
		return _result;
	}
	
	public void startElement(String namespaceURI,
			String localName,
			String qName,
			Attributes atts) throws SAXException {
		if (_collection != null) flushCollection();
		
		
		
		
		DynamicContentHandler newContext = null;
		/*
		 * check for QSET first!! ie look for matt element that is added in
		 * XMLPersistedDataTypeUserType
		 */
		
		if (localName.equals("matt"))
			newContext=SXPRPresenter.instance().getContentHandler(namespaceURI,localName,qName,atts, _datatype);
		
		else {
			
			/* 
			 * If we have ANY, we must do a little extra work to make sure the
			 * appropriate handler is called.  We know we have an xsi type, so we
			 * can use that to determine what the datatype actually is
			 */
			
			if (_datatype.equals(DatatypeMetadataFactoryDatatypes.instance().ANYTYPE)) {
				try {
					String xsi=atts.getValue(DatatypePresenterBase.W3_XML_SCHEMA,"type");
					_datatype=DatatypeMetadataFactoryImpl.instance().createByXsiType(xsi);
				} catch (UnknownDatatypeException ex) {
					new UnknownDatatypeException(ex.toString());
				}
			}
			
			if (_datatype instanceof SimpleDatatype) {
				try {
					newContext = _datatype.getHandler(namespaceURI, localName, qName, atts);
				} catch (FactoryException ex) {
					newContext = null;
				}
			} else if (_datatype instanceof ParametrizedDatatype) {
				// the ParametrizedDatatype.getHandler() for SET, LIST, and
				// BAG returns the content handler for the parameter type,
				// so that's very nice for us here!
				try {
					newContext = _datatype.getHandler(namespaceURI, localName, qName,atts);
				} catch (FactoryException ex) {
					newContext = null;
				}
			}
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
					+ _datatype.getFullName()
					+ " build tree " + localName);
			DynamicContentHandler tree =
				new TreeContentHandler(namespaceURI, localName, qName, atts);
			this.suspendWith(tree, null);
		}
	}
	
	
	public void flushCollection()throws SAXException {
		if (_datatype.getFullName().contains("LIST") && !(_result instanceof LIST)) {
			_result = ValueFactory.getInstance().LISTvalueOf(_collection);
		} else if (_datatype.getFullName().contains("IVL")  && !(_result instanceof IVL)) {
			_result = ValueFactory.getInstance().IVLvalueOf(_collection);
		} else if (_datatype.getFullName().contains("SET")  && !(_result instanceof SET)) {
			_result = ValueFactory.getInstance().SETvalueOf(_collection);
		} else if (_datatype.getFullName().contains("BAG")  && !(_result instanceof BAG)) {
			_result = ValueFactory.getInstance().BAGvalueOf(_collection);
		}
		
		_collection=null;
	}
	
	
	public static Object parseValue(InputStream is, Datatype datatype) throws SAXException {
		try {
			StandaloneDataTypeContentHandler parser = new StandaloneDataTypeContentHandler(datatype);
			XMLReader reader = XMLReaderFactory.createXMLReader();
			parser.setReader(reader, null);
			reader.parse(new InputSource(is));
			
			// and return the thing!
			return parser.getResult();
			
		} catch(IOException ex) {
			throw new Error(ex);
		}
	}
	
	public void endElement(String namespaceURI,
			String localName,
			String qName) throws SAXException {
		// Be a good citizen and return results and release the reader.
		if (_collection != null)
			flushCollection();
		
		returnResult(_result);
	}
}
