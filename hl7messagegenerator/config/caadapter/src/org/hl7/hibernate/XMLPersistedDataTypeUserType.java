package org.hl7.hibernate;

import java.io.InputStream;
import java.io.StringWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.usertype.ParameterizedType;
import org.hibernate.usertype.UserType;
import org.hl7.meta.Datatype;
import org.hl7.meta.ParametrizedDatatype;
import org.hl7.meta.UnknownDatatypeException;
import org.hl7.meta.impl.DatatypeMetadataFactoryImpl;
import org.hl7.types.ANY;
import org.hl7.types.IVL;
import org.hl7.types.NullFlavor;
import org.hl7.types.PIVL;
import org.hl7.types.QSET;
import org.hl7.types.ValueFactory;
import org.hl7.types.ValueFactoryException;
import org.hl7.types.impl.NullFlavorImpl;
import org.hl7.xml.builder.RimGraphXMLSpeaker;
import org.hl7.xml.parser.StandaloneDataTypeContentHandler;
import org.xml.sax.SAXException;

import java.util.logging.Logger;

/** A Hibernate UserType that persists HL7 data type values through their XML serialization.
 This is a generic approach, where one mapper class does it all. It uses the ParameterizedType
 interface of hibernate
 
 <property name="effectiveTime">
 <type class="org.hl7.hibernate.XMLPersistedDataTypeUserType"> 
 <param name="type">QSET&lt;TS></param>
 </type>
 </property> 
 
 @author Gunther Schadow
 @version $Id: XMLPersistedDataTypeUserType.java,v 1.1 2006-10-03 17:38:44 marwahah Exp $
 */
public class XMLPersistedDataTypeUserType extends AbstractDataTypeUserType implements ParameterizedType, UserType {
	
	private Datatype _datatype = null;
	private Class _interface = null;
	private final String DEFAULT_TAG = "a";

	private static final Logger LOGGER = Logger.getLogger("org.hl7.hibernate");
	
	/** Gets called by Hibernate to pass the configured type parameters to the implementation. */
	public void setParameterValues(Properties parameters) {
		String typeSpec = (String)parameters.get("type");
		try {      
			_datatype = DatatypeMetadataFactoryImpl.instance().create(typeSpec);
			if (_datatype instanceof ParametrizedDatatype)
				_interface = Class.forName("org.hl7.types." + ((ParametrizedDatatype)_datatype).getType());
			else
				_interface = Class.forName("org.hl7.types." + _datatype.getFullName());
		} catch(ClassNotFoundException x) {
			throw new Error("data type interface not found", x);
		} catch(UnknownDatatypeException x) {
			throw new Error(x);
		}
	}
	
	/** The class returned by nullSafeGet(). */
	public Class returnedClass() {
		return _interface;
	}
	
	/** Return the SQL type codes for the columns mapped by this type. */
	public int[] sqlTypes() { return SQL_TYPES; }
	
	private static final int[] SQL_TYPES = new int[] { Hibernate.TEXT.sqlType() };
	
	/** Retrieve an instance of the mapped class from a JDBC resultset. */
	public Object nullSafeGet(ResultSet rs, String[] names, Object owner) throws HibernateException {
		try {
			InputStream is = rs.getAsciiStream(names[0]);
			if(!rs.wasNull()) {
				return StandaloneDataTypeContentHandler.parseValue(is, _datatype);
			}
			else {
				if (_datatype instanceof ParametrizedDatatype)
					return ValueFactory.getInstance().nullValueOf(((ParametrizedDatatype)_datatype).getType(),"NI");
				else
					return ValueFactory.getInstance().nullValueOf(_datatype.getFullName(),"NI");
			}
		} catch(SQLException ex) {
			throw new HibernateException(ex);
		} catch(SAXException ex) {
			String offendingText = null;
			try {
				offendingText = rs.getString(names[0]);
			} catch(SQLException why) { }
			throw new HibernateException(ex + ": " + offendingText);
		} catch(ValueFactoryException ex) {
			throw new HibernateException(ex);
		}
	}
	
	public static final TransformerFactory _transformerFactory = TransformerFactory.newInstance();
	
	/** Write an instance of the mapped class to a prepared statement. */
	public void nullSafeSet(PreparedStatement st, Object rawValue, int index) throws HibernateException {
		try {
			if(rawValue!=null) {
				final ANY value = (ANY)rawValue;
				
				final NullFlavor nf = value.nullFlavor();
				if(nf == null || nf.equal(NullFlavorImpl.NI).isFalse()) {
					Transformer transformer = _transformerFactory.newTransformer();
					transformer.setOutputProperty(OutputKeys.INDENT, "no");
					transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
					StringWriter sw = new StringWriter();
					transformer.transform(new SAXSource(new RimGraphXMLSpeaker(), new RimGraphXMLSpeaker.DataValueInputSource(value, DEFAULT_TAG, _datatype)), new StreamResult(sw));
					/*
					 * iff we have a QSET, we must wrap the QSET in an extra root
					 * element to keep the XML well formed.  The
					 * StandaloneDatatypeContentHandler knows this happens, and it looks
					 * for the QSET when it is called
					 */
					
					String string = sw.toString();

					if(string.length() > 0) {
						if (value.isNull().isFalse() && (value instanceof QSET) && !(value instanceof IVL) && !(value instanceof PIVL))
							Hibernate.TEXT.nullSafeSet(st,"<matt xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"+ string +"</matt>",index);
						else
							Hibernate.TEXT.nullSafeSet(st, string, index);						

						return;
					} // else 
					// LOGGER.warning("no output for: " + rawValue.getClass() + ": " + rawValue);
          // no need for this warning. Empty strings come about when collections are empty or null
				}
			} 
			
			// don't ever forget to set this to null explicitly or 
			// else the value from the previous insert might be used!
			Hibernate.TEXT.nullSafeSet(st, null, index);	
			
		} catch(TransformerConfigurationException ex) {
			throw new HibernateException(ex);
		} catch(TransformerException ex) {
			throw new HibernateException(ex);
		} catch(SQLException ex) {
			throw new HibernateException(ex);
		}
	}
}
