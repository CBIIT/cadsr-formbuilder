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
package org.hl7.types.impl;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.hl7.meta.Datatype;
import org.hl7.meta.UnknownDatatypeException;
import org.hl7.meta.impl.DatatypeMetadataFactoryImpl;
import org.hl7.types.AD;
import org.hl7.types.ADXP;
import org.hl7.types.ANY;
import org.hl7.types.BAG;
import org.hl7.types.BIN;
import org.hl7.types.BL;
import org.hl7.types.CD;
import org.hl7.types.CE;
import org.hl7.types.CR;
import org.hl7.types.CS;
import org.hl7.types.CV;
import org.hl7.types.DSET;
import org.hl7.types.ED;
import org.hl7.types.EN;
import org.hl7.types.ENXP;
import org.hl7.types.II;
import org.hl7.types.INT;
import org.hl7.types.IVL;
import org.hl7.types.LIST;
import org.hl7.types.MO;
import org.hl7.types.OID;
import org.hl7.types.ON;
import org.hl7.types.ONXP;
import org.hl7.types.PIVL;
import org.hl7.types.PN;
import org.hl7.types.PNXP;
import org.hl7.types.PQ;
import org.hl7.types.PQR;
import org.hl7.types.QSET;
import org.hl7.types.QTY;
import org.hl7.types.REAL;
import org.hl7.types.RTO;
import org.hl7.types.SC;
import org.hl7.types.SET;
import org.hl7.types.ST;
import org.hl7.types.TEL;
import org.hl7.types.TN;
import org.hl7.types.TS;
import org.hl7.types.UID;
import org.hl7.types.URL;
import org.hl7.types.ValueFactory;
import org.hl7.types.ValueFactoryException;
import org.hl7.util.Base64;
import org.hl7.util.FactoryException;
import org.hl7.xml.ADPresenter;
import org.hl7.xml.ADXPPresenter;
import org.hl7.xml.BLPresenter;
import org.hl7.xml.CDPresenter;
import org.hl7.xml.CRPresenter;
import org.hl7.xml.CSPresenter;
import org.hl7.xml.CVPresenter;
import org.hl7.xml.DatatypePresenterBase;
import org.hl7.xml.DatatypePresenterFactory;
import org.hl7.xml.EDPresenter;
import org.hl7.xml.ENPresenter;
import org.hl7.xml.ENXPPresenter;
import org.hl7.xml.IIPresenter;
import org.hl7.xml.INTPresenter;
import org.hl7.xml.IVLPresenter;
import org.hl7.xml.OIDPresenter;
import org.hl7.xml.ONPresenter;
import org.hl7.xml.ONXPPresenter;
import org.hl7.xml.PNPresenter;
import org.hl7.xml.PNXPPresenter;
import org.hl7.xml.PQPresenter;
import org.hl7.xml.QTYPresenter;
import org.hl7.xml.RTOPresenter;
import org.hl7.xml.SCPresenter;
import org.hl7.xml.STPresenter;
import org.hl7.xml.TELPresenter;
import org.hl7.xml.TSPresenter;
import org.regenstrief.ucum.Unit;

/**
 * Abstact base class for factories that create data type values from all sorts
 * of sources including literal forms. <p/> A different implementation of data
 * types would subclass this factory to provide instances of their own type
 * implementations. <p/> A factory is made a singleton and a factory is obtained
 * with the static method getInstance(), this might in the future retrieve a
 * different class depending on preferences, properties, or classpath settings.
 */
public class ValueFactoryImpl extends ValueFactory {

	/**
	 * Create any data value that has a literal form given a type specification
	 * (typeSpec) and the literal string itself. <p/> FIXME: typeSpec should be a
	 * DataType value and literal should be an ST value.
	 */
	public ANY valueOfLiteral(String typeSpec, String literal) throws ValueFactoryException {
		try {
			// sorry, I am using varargs style because I keep getting warning in 1.5
			return (ANY) getClass().getMethod(typeSpec + "valueOfLiteral", String.class).invoke(this, literal);
		}
		catch (NoSuchMethodException ex) {
			throw new ValueFactoryException(ex);
		}
		catch (IllegalAccessException ex) {
			throw new ValueFactoryException(ex);
		}
		catch (InvocationTargetException ex) {
			throw new ValueFactoryException(ex);
		}
	}

	/** consider using DynaBean to replace Map from Apache Common **/
	public ANY valueOfMap(String typeSpec, Map map) throws ValueFactoryException 
	{
		try {
			return (ANY) getClass().getMethod(typeSpec + "valueOf", Map.class).invoke(this, map);
		}
		catch (NoSuchMethodException ex) {
			throw new ValueFactoryException(ex);
		}
		catch (IllegalAccessException ex) {
			throw new ValueFactoryException(ex);
		}
		catch (InvocationTargetException ex) {
			throw new ValueFactoryException(ex);
		}
	}

	/**
	 * Create a NULL value dynamically. <p/> FIXME: nullFlavorString should be a
	 * proper NullFlavor code.
	 */
	public ANY nullValueOf(String typeSpec, String nullFlavorString) throws ValueFactoryException {
		try {
			if (typeSpec.equals("BL"))
				return BLimpl.valueOf(NullFlavorImpl.valueOf(nullFlavorString));
			else
				return (ANY) Class.forName("org.hl7.types.impl." + typeSpec + "null").getMethod("valueOf",
						new Class[] { String.class }).invoke(null, new Object[] { nullFlavorString });
		}
		catch (NoSuchMethodException ex) {
			throw new ValueFactoryException(ex);
		}
		catch (ClassNotFoundException ex) {
			throw new ValueFactoryException(ex);
		}
		catch (IllegalAccessException ex) {
			throw new ValueFactoryException(ex);
		}
		catch (InvocationTargetException ex) {
			throw new ValueFactoryException(ex);
		}
	}

	/* here we have all data types with a literal form */

	public BL BLvalueOfLiteral(String that) {
		return BLimpl.valueOf(that);
	}

	public ENXP ENXPvalueOfLiteral(String that) {
		return ENXPimpl.valueOf(that);
	}

	public ST STvalueOfLiteral(String that) {
		return STjlStringAdapter.valueOf(that);
	}

	public OID OIDvalueOfLiteral(String that) {
		return OIDimpl.valueOf(that);
	}

	public UID UIDvalueOfLiteral(String that) {
		return UIDimpl.valueOf(that);
	}

	public ST STvalueOf(Map map) {
		return STjlStringAdapter.valueOf((String) map.get(DatatypePresenterBase.TEXT), (String) map
				.get(STPresenter.ATTR_LANGUAGE));
	}

	public SC SCvalueOf(Map map) {
		CE ce = CEvalueOf(map);
        return SCimpl.valueOf((String) map.get(DatatypePresenterBase.TEXT),
            (String) map.get(SCPresenter.ATTR_LANGUAGE), ce);
	}

	public OID OIDvalueOf(Map map) {
		return OIDimpl.valueOf((String) map.get(OIDPresenter.ATTR_VALUE));
	}

	public URL URLvalueOfLiteral(String that) {
		try {
			return URLjnURLAdapter.valueOf(new java.net.URL(that));
		}
		catch (java.net.MalformedURLException x) {
			return URLnull.OTH;
		}
	}

	public INT INTvalueOfLiteral(String that) {
		return INTlongAdapter.valueOf(that);
	}

	public REAL REALvalueOfLiteral(String that) {
		return REALdoubleAdapter.valueOf(that);
	}

	public PQ PQvalueOfLiteral(String that) {
		// the string better have two parts "part1 part2" else we throw an error
		if (that == null || that.equals(""))
			return PQnull.NA;
		that = that.trim();
		StringTokenizer st = new StringTokenizer(that, " ");
		String number = st.nextToken();
		String unit = "1";
		if(st.hasMoreTokens())
			unit = st.nextToken();
		return PQimpl.valueOf(number, unit);
	}

	public <T extends QTY> IVL<T> IVLvalueOf(BL lowClosed, T low, T high, BL highClosed) {
		return IVLimpl.valueOf(lowClosed, low, high, highClosed);
	}

	public <T extends QTY> IVL<T> IVLvalueOf(T center, QTY.diff width, BL lowClosed, BL highClosed) {
		return IVLimpl.valueOf(center, width, lowClosed, highClosed);
	}

	public <T extends QTY> IVL<T> IVLvalueOf(BL lowClosed, BL highClosed, T low, QTY.diff width) {
		return IVLimpl.valueOf(lowClosed, highClosed, low, width);
	}

	public <T extends QTY> IVL<T> IVLvalueOf(BL lowClosed, QTY.diff width, BL highClosed, T high) {
		return IVLimpl.valueOf(lowClosed, width, highClosed, high);
	}

	public <T extends QTY> PIVL<T> PIVLvalueOf(QTY low, QTY high, QTY.diff period, CS alignment, BL institutionSpecified) {
		return PIVLimpl.valueOf(low, high, period, alignment, institutionSpecified);
	}

	public <T extends QTY> PIVL<T> PIVLvalueOf(IVL ivl, QTY.diff period, CS alignment, BL institutionSpecified) {
		return PIVLimpl.valueOf(ivl, period, alignment, institutionSpecified);
	}

	public MO MOvalueOfLiteral(String that) {
		throw new UnsupportedOperationException();
	}

	public TS TSvalueOfLiteral(String that) {
		return TSjuDateAdapter.valueOf(that);
	}

	/* generic types and generic type extensions follow */
	// not yet ...
	/* special, non-literal factory methods */

	public BL BLvalueOf(boolean jvalue) {
		return BLimpl.valueOf(jvalue);
	}

	public ST STvalueOf(String jvalue) {
		return STjlStringAdapter.valueOf(jvalue);
	}

	public ST STvalueOf(String jvalue, String jlanguage) {
		return STjlStringAdapter.valueOf(jvalue, jlanguage);
	}

	public INT INTvalueOf(byte jvalue) {
		return INTlongAdapter.valueOf(jvalue);
	}

	public INT INTvalueOf(short jvalue) {
		return INTlongAdapter.valueOf(jvalue);
	}

	public INT INTvalueOf(int jvalue) {
		return INTlongAdapter.valueOf(jvalue);
	}

	public INT INTvalueOf(long jvalue) {
		return INTlongAdapter.valueOf(jvalue);
	}

	public INT INTvalueOf(BigInteger jvalue) {
		throw new UnsupportedOperationException();
	}

	public REAL REALvalueOf(byte jvalue) {
		return REALdoubleAdapter.valueOf(jvalue);
	}

	public REAL REALvalueOf(short jvalue) {
		return REALdoubleAdapter.valueOf(jvalue);
	}

	public REAL REALvalueOf(int jvalue) {
		return REALdoubleAdapter.valueOf(jvalue);
	}

	public REAL REALvalueOf(long jvalue) {
		return REALdoubleAdapter.valueOf(jvalue);
	}

	public REAL REALvalueOf(BigInteger jvalue) {
		throw new UnsupportedOperationException();
	}

	public REAL REALvalueOf(float jvalue) {
		return REALdoubleAdapter.valueOf(jvalue);
	}

	public REAL REALvalueOf(double jvalue) {
		return REALdoubleAdapter.valueOf(jvalue);
	}

	public REAL REALvalueOf(BigDecimal jvalue) {
		throw new UnsupportedOperationException();
	}

	public TS TSvalueOf(Date jvalue) {
		return TSjuDateAdapter.valueOf(jvalue, 0);
	}

	public TS TSvalueOf(Date jvalue, int precision) {
		return TSjuDateAdapter.valueOf(jvalue, precision);
	}

	public TN TNvalueOf(String jvalue) {
		return TNjlStringAdapter.valueOf(jvalue);
	}

	public RTO RTOvalueOf(QTY.diff numerator, QTY.diff denominator) {
		return RTOimpl.valueOf(numerator, denominator);
	}

	public II IIvalueOf(String root, String extension) {
		return IIimpl.valueOf(root, extension);
	}

	public II IIvalueOf(UID root, ST extension) {
		return IIimpl.valueOf(root, extension);
	}

	public II IIvalueOf(UID root, String extension) {
		return IIimpl.valueOf(root, STvalueOfLiteral(extension));
	}

	public PQ PQvalueOf(String magnitudeString, String unitString) {
		return PQimpl.valueOf(magnitudeString, unitString);
	}

	public PQ PQvalueOf(String magnitudeString, CS unit) {
		return PQimpl.valueOf(magnitudeString, (Unit) unit);
	}
	
	public PQ PQvalueOf(String magnitudeString, Unit unit) {
		return PQimpl.valueOf(magnitudeString, unit);
	}
	
	public PQ PQvalueOf(REAL magnitude, CS unit) {
		return PQimpl.valueOf(magnitude, (Unit) unit);
	}

	public PQ PQvalueOf(REAL magnitude, Unit unit) {
		return PQimpl.valueOf(magnitude, unit);
	}

	public Unit UnitvalueOf(ST s) {
		return Unit.valueOf(s);
	}

	public SC SCvalueOf(String data) {
		return SCimpl.valueOf(data);
	}

	public SC SCvalueOf(String data, CE code) {
		return SCimpl.valueOf(data, code);
	}

	/*
	 * Map-based factory methods by Eric Chen. The map objects are Strings and the
	 * names are the same as in the XML ITS.
	 */

	public II IIvalueOf(Map map) {
		String rootString = (String) map.get(IIPresenter.ATTR_ROOT);
		String extensionString = (String) map.get(IIPresenter.ATTR_EXTENSION);
		String assigningAuthorityNameString = (String) map.get(IIPresenter.ATTR_AAN);
		String displayableString = (String) map.get(IIPresenter.ATTR_DISPLAYABLE);
		return IIimpl.valueOf(rootString, extensionString, assigningAuthorityNameString, displayableString);
	}

	public BL BLvalueOf(Map map) {
		String valueString = (String) map.get(BLPresenter.ATTR_VALUE);
		return BLvalueOfLiteral(valueString);
	}

	public AD ADvalueOf(Map map) {
		String useValue = (String) map.get(ADPresenter.ATTR_USE);
		BL isNotOrdered = BLimpl.valueOf((String) map.get(ADPresenter.ATTR_IS_NOT_ORDERED));
		SET<CS> use = CSPresenter.parseList(useValue, "PostalAddressUse");
		QSET<TS> validTime = null;

		List<ADXP> addressParts = new ArrayList<ADXP>();
		String text = (String) map.get(DatatypePresenterBase.TEXT);
		if (text != null)
			addressParts.add(ADXPimpl.valueOf(text));
		Set<String> keySet = ADXPPresenter.TAGS_TO_PART_TYPES.keySet();
		for (String key : keySet) {
			List list = (List) map.get(key);
			if (list != null)
				for (int i = 0; i < list.size(); i++) {
					Map map1 = (Map) list.get(i);
					ADXP part = ADXPvalueOf(key, map1);
					if (part != null)
						addressParts.add(part);
				}
		}
		return ADimpl.valueOf(addressParts, use, validTime, isNotOrdered);
	}

	private ADXP ADXPvalueOf(String datatypeName, Map map) {
		String language = (String) map.get(ENXPPresenter.ATTR_LANGUAGE);
		CS type = ADXPPresenter.TAGS_TO_PART_TYPES.get(datatypeName);
		String text = (String) map.get(DatatypePresenterBase.TEXT);

		return ADXPimpl.valueOf(text, type, language);
	}

	public EN ENvalueOf(Map map) {
		String useValue = (String) map.get(ENPresenter.ATTR_USE);
		DSET<CS> use = CSPresenter.parseList(useValue, "EntityNameUse");

// validTime is not supported
//		List validTimeList = (List) map.get(ENPresenter.TAG_VALID_TIME);
		IVL<TS> validTime = null;

		List<ENXP> nameParts = new ArrayList<ENXP>();
		String text = (String) map.get(DatatypePresenterBase.TEXT);
		if (text != null)
			nameParts.add(ENXPimpl.valueOf(text));
		Set<String> keySet = ENXPPresenter.TAGS_TO_PART_TYPES.keySet();
		for (String key : keySet) {
			List list = (List) map.get(key);
			if (list != null)
				for (int i = 0; i < list.size(); i++) {
					Map map1 = (Map) list.get(i);
					ENXP part = ENXPvalueOf(key, map1);
					if (part != null)
						nameParts.add(part);
				}
		}
		return ENimpl.valueOf(nameParts, use, validTime);
	}

    public TN TNvalueOf(Map map) {
        return TNvalueOf((String) map.get(DatatypePresenterBase.TEXT));
    }

    private ENXP ENXPvalueOf(String datatypeName, Map map) {
		String language = (String) map.get(ENXPPresenter.ATTR_LANGUAGE);
		String qualifier = (String) map.get(ENXPPresenter.ATTR_QUALIFIER);
		DSET<CS> qualifiers = CSPresenter.parseList(qualifier, "EntityNamePartQualifier");
		String text = (String) map.get(DatatypePresenterBase.TEXT);
		CS type = ENXPPresenter.TAGS_TO_PART_TYPES.get(datatypeName);
		return ENXPimpl.valueOf(text, type, qualifiers, language);
	}

	public ON ONvalueOf(Map map) {
		String useValue = (String) map.get(ONPresenter.ATTR_USE);
		DSET<CS> use = CSPresenter.parseList(useValue, "EntityNameUse");
//		List validTimeList = (List) map.get(ENPresenter.TAG_VALID_TIME);
		IVL<TS> validTime = null;

		List<ENXP> nameParts = new ArrayList<ENXP>();
		String text = (String) map.get(DatatypePresenterBase.TEXT);
		if (text != null)
			nameParts.add(ONXPimpl.valueOf(text));
		Set<String> keySet = ONXPPresenter.TAGS_TO_PART_TYPES.keySet();
		for (String key : keySet) {
			List list = (List) map.get(key);
			if (list != null)
				for (int i = 0; i < list.size(); i++) {
					Map map1 = (Map) list.get(i);
					ONXP part = ONXPvalueOf(key, map1);
					if (part != null)
						nameParts.add(part);
				}
		}
		return ONimpl.valueOf(nameParts, use, validTime);
	}

	private ONXP ONXPvalueOf(String datatypeName, Map map) {
		String language = (String) map.get(ONXPPresenter.ATTR_LANGUAGE);
		String qualifier = (String) map.get(ONXPPresenter.ATTR_QUALIFIER);
		DSET<CS> qualifiers = CSPresenter.parseList(qualifier, "OrganizationNamePartQualifier");
		String text = (String) map.get(DatatypePresenterBase.TEXT);
		CS type = ENXPPresenter.TAGS_TO_PART_TYPES.get(datatypeName);
		return ONXPimpl.valueOf(text, type, qualifiers, language);
	}

	public PN PNvalueOf(Map map) {
		String useValue = (String) map.get(PNPresenter.ATTR_USE);
		DSET<CS> use = CSPresenter.parseList(useValue, "EntityNameUse");
//		List validTimeList = (List) map.get(ENPresenter.TAG_VALID_TIME);
		IVL<TS> validTime = null;

		List<ENXP> nameParts = new ArrayList<ENXP>();
		String text = (String) map.get(DatatypePresenterBase.TEXT);
		if (text != null)
			nameParts.add(PNXPimpl.valueOf(text));
		Set<String> keySet = PNXPPresenter.TAGS_TO_PART_TYPES.keySet();
		for (String key : keySet) {
			List list = (List) map.get(key);
			if (list != null)
				for (int i = 0; i < list.size(); i++) {
					Map map1 = (Map) list.get(i);
					PNXP part = PNXPvalueOf(key, map1);
					if (part != null)
						nameParts.add(part);
				}
		}
		return PNimpl.valueOf(nameParts, use, validTime);
	}

	private PNXP PNXPvalueOf(String datatypeName, Map map) {
		String language = (String) map.get(ONXPPresenter.ATTR_LANGUAGE);
		String qualifier = (String) map.get(ONXPPresenter.ATTR_QUALIFIER);
		DSET<CS> qualifiers = CSPresenter.parseList(qualifier, "OrganizationNamePartQualifier");
		String text = (String) map.get(DatatypePresenterBase.TEXT);
		CS type = ENXPPresenter.TAGS_TO_PART_TYPES.get(datatypeName);
		return PNXPimpl.valueOf(text, type, qualifiers, language);
	}

	public PQ PQvalueOf(Map map) {
		String valueString = (String) map.get(PQPresenter.ATTR_VALUE);
		String unitString = (String) map.get(PQPresenter.ATTR_UNIT);

		List translationList = null;
        // translationList = (List) map.get(PQPresenter.ATTR_TRANSLATION); // unsupported
		PQ pq = PQimpl.valueOf(valueString, unitString);

		if (translationList != null) {
			for (int i = 0; i < translationList.size(); i++) {
				Map map1 = (Map) translationList.get(i);
				((PQimpl) pq).addTranslation(PQRvalueOf(map1));
			}
		}

		return pq;
	}

	public CS CSvalueOf(Map map) {
		return CSimpl.valueOf(ValueFactory.getInstance().STvalueOfLiteral((String) map.get(CSPresenter.ATTR_CODE)),
				ValueFactory.getInstance().UIDvalueOfLiteral((String) map.get(CSPresenter.ATTR_CODE_SYSTEM)), ValueFactory
						.getInstance().STvalueOfLiteral((String) map.get(CSPresenter.ATTR_DISPLAY_NAME)), ValueFactory
						.getInstance().STvalueOfLiteral((String) map.get(CSPresenter.ATTR_CODE_SYSTEM_NAME)), ValueFactory
						.getInstance().STvalueOfLiteral((String) map.get(CSPresenter.ATTR_CODE_SYSTEM_VERSION)));
	}

	public CE CEvalueOf(Map map) {
		CV cv = CVvalueOf(map);
		List translationList = null;
//        translationList =  (List) map.get(CEPresenter.TAG_TRANSLATION); // translation is not supported

        return CEimpl.valueOf(cv, (SET<CV>) SETjuSetAdapter.valueOf(getTranslation(translationList)));
	}

	public CV CVvalueOf(Map map) {
		final String that = (String) map.get(CVPresenter.ATTR_CODE);
		ST code = ValueFactory.getInstance().STvalueOfLiteral(that);
		UID codeSystem = ValueFactory.getInstance().UIDvalueOfLiteral((String) map.get(CVPresenter.ATTR_CODE_SYSTEM));
		ST codeSystemName = ValueFactory.getInstance()
				.STvalueOfLiteral((String) map.get(CVPresenter.ATTR_CODE_SYSTEM_NAME));
		ST codeSystemVersion = ValueFactory.getInstance().STvalueOfLiteral(
				(String) map.get(CVPresenter.ATTR_CODE_SYSTEM_VERSION));
		ST displayName = ValueFactory.getInstance().STvalueOfLiteral((String) map.get(CVPresenter.ATTR_DISPLAY_NAME));
		ST originalText = ValueFactory.getInstance().STvalueOfLiteral((String) map.get(CVPresenter.ATTR_ORIGINAL_TEXT));
		return CVimpl.valueOf(code, codeSystem, originalText, displayName, codeSystemName, codeSystemVersion);
	}

	public CR CRvalueOf(Map map) {
		String invertedString = (String) map.get(CRPresenter.ATTR_INVERTED);
		List nameList = (List) map.get(CRPresenter.ATTR_NAME);
		List valueList = (List) map.get(CRPresenter.ATTR_VALUE);
		boolean invertedBool = (invertedString != null) ? Boolean.getBoolean(invertedString) : false;

		CV name = CVnull.NA;
		if (nameList != null) {
			for (int i = 0; i < nameList.size(); i++) {
				Map map1 = (Map) nameList.get(i);
				name = CVvalueOf(map1);
			}
		}

		CD value = CDnull.NA;
		for (int i = 0; i < valueList.size(); i++) {
			Map map1 = (Map) valueList.get(i);
			value = CDvalueOf(map1);
		}
		return (invertedString != null) ? CRimpl.valueOf(name, value, invertedBool) : CRimpl.valueOf(name, value);
	}

	public CD CDvalueOf(Map map) {
		String code = (String) map.get(CDPresenter.ATTR_CODE);
		String codeSystem = (String) map.get(CDPresenter.ATTR_CODE_SYSTEM);
		String codeSystemName = (String) map.get(CDPresenter.ATTR_CODE_SYSTEM_NAME);
		String codeSystemVersion = (String) map.get(CDPresenter.ATTR_CODE_SYSTEM_VERSION);
		String displayName = (String) map.get(CDPresenter.ATTR_DISPLAY_NAME);
		List originalTextList = null;
//            originalTextList = (List) map.get(CDPresenter.TAG_ORIGINAL_TEXT);
		List qualifierStringList = null;
//            qualifierStringList = (List) map.get(CDPresenter.TAG_QUALIFIER);
		List translationList = null;
//            translationList = (List) map.get(CDPresenter.TAG_TRANSLATION);

		ED originalText = null;
		originalText = getOriginalText(originalTextList);
		List<CR> qualifierList = null;
		if (qualifierStringList != null) {
			for (int i = 0; i < qualifierStringList.size(); i++) {
				Map map1 = (Map) qualifierStringList.get(i);
				qualifierList.add(CRvalueOf(map1));
			}
		}
		Set<CD> translationSet = null;
		if (translationList != null) {
			translationSet = new HashSet<CD>();
			for (int i = 0; i < translationList.size(); i++) {
				Map map1 = (Map) translationList.get(i);
				translationSet.add(CDvalueOf(map1));
			}
		}
		return CDimpl.valueOf(code, codeSystem, codeSystemName, codeSystemVersion, displayName, originalText,
				qualifierList, translationSet);
	}

	public PQR PQRvalueOf(Map map) {
		REAL value = ValueFactory.getInstance().REALvalueOfLiteral((String) map.get("value"));
		String code = (String) map.get(CDPresenter.ATTR_CODE);
		String codeSystem = (String) map.get(CDPresenter.ATTR_CODE_SYSTEM);
		String codeSystemName = (String) map.get(CDPresenter.ATTR_CODE_SYSTEM_NAME);
		String codeSystemVersion = (String) map.get(CDPresenter.ATTR_CODE_SYSTEM_VERSION);
		String displayName = (String) map.get(CDPresenter.ATTR_DISPLAY_NAME);
		List originalTextList = (List) map.get(CDPresenter.TAG_ORIGINAL_TEXT);
		List translationList = (List) map.get(CDPresenter.TAG_TRANSLATION);

		ED originalText = null;
		originalText = getOriginalText(originalTextList);
		Set<PQR> translationSet = null;
		if (translationList != null) {
			translationSet = new HashSet<PQR>();
			for (int i = 0; i < translationList.size(); i++) {
				Map map1 = (Map) translationList.get(i);
				translationSet.add(PQRvalueOf(map1));
			}
		}
		return PQRimpl.valueOf(value, code, codeSystem);
		// FIXME!!!: add this: translationSet);
	}

	public ED EDvalueOf(Map map) {
		String text = (String) map.get(DatatypePresenterBase.TEXT);
		String mediaTypeString = (String) map.get(EDPresenter.ATTR_MEDIA_TYPE);
		String languageString = (String) map.get(EDPresenter.ATTR_LANGUAGE);
		String compressionString = (String) map.get(EDPresenter.ATTR_COMPRESSION);
		String integrityCheckString = (String) map.get(EDPresenter.ATTR_INTEGRITY_CHECK);
		String integrityCheckAlgorithmString = (String) map.get(EDPresenter.ATTR_INTEGRITY_CHECK_ALGORITHM);
		String representationString = (String) map.get(EDPresenter.ATTR_REPRESENTATION);
		String encodingString = (String) map.get(EDPresenter.ATTR_REPRESENTATION_OLD); // the old "encoding" name for representation, for backwards compatibility
		String referenceString = (String) map.get(EDPresenter.ATTR_REFERENCE);
		List thumbnailList = null;
//        thumbnailList = (List) map.get(EDPresenter.ATTR_THUMBNAIL); // Thumbnail is not supported

		if (encodingString == null)
			encodingString = "TXT";
		boolean binary = encodingString.equals("B64");
		BIN integrityCheckBin = BINbyteArrayImpl.valueOf(integrityCheckString);
		TEL reference = TELnull.NI;

		ED thumbnail = null;
		if (thumbnailList != null) {
			for (int i = 0; i < thumbnailList.size(); i++) {
				Map map1 = (Map) thumbnailList.get(i);
				thumbnail = EDvalueOf(map1);
			}
		}

		String charSet = "UTF-8";
		if (binary) {
			byte[] bytes = Base64.decode(text);
			return EDbyteArrayImpl.valueOf(bytes, 0, bytes.length, mediaTypeString, charSet, languageString,
					compressionString, reference, integrityCheckBin, integrityCheckAlgorithmString, thumbnail);
		}
		else {
			return EDjlStringAdapter.valueOf(text, mediaTypeString, charSet, languageString, compressionString, reference,
					integrityCheckBin, integrityCheckAlgorithmString, thumbnail);
		}
	}

	public TEL TELvalueOf(Map map) {
		String value = (String) map.get(TELPresenter.ATTR_VALUE);
		String use = (String) map.get(TELPresenter.ATTR_USE);
		return TELimpl.valueOf(value, use, QSETnull.NI);
	}

	// abstract. Shoulbe never be called. If called, create TS or PQ data type
	// depends on QTYPresenter.ATTR_UNIT existed
	public QTY QTYvalueOf(Map map) {
		String unit = (String) map.get(QTYPresenter.ATTR_UNIT);
		if (unit == null)
			return TSvalueOf(map);
		return PQvalueOf(map);
	}

	public RTO RTOvalueOf(Map map) {
		List numeratorList = (List) map.get(RTOPresenter.TAG_NUMERATOR);
		List denominatorList = (List) map.get(RTOPresenter.TAG_DENOMINATOR);

		RTOPresenter rtoPresenter;
		try {
			rtoPresenter = (RTOPresenter) DatatypePresenterFactory.getInstance().createPresenter("RTO");
		}
		catch (FactoryException e) {
			System.err.println(e.getMessage());
		}

		Map numeratorMap = (Map) numeratorList.get(0);
		String numeratorSubType = (String) numeratorMap.get(DatatypePresenterBase.SUB_TYPE);
		QTY numerator = null;
		if (numeratorSubType != null)
			try {
				numerator = (QTY) valueOfMap(numeratorSubType, numeratorMap);
			}
			catch (ValueFactoryException e) {
				System.err.println(e.getMessage());
			}

		Map denominatorMap = (Map) denominatorList.get(0);
		String denominatorSubType = (String) denominatorMap.get(DatatypePresenterBase.SUB_TYPE);
		QTY denominator = null;
		if (denominatorSubType != null)
			try {
				denominator = (QTY) valueOfMap(denominatorSubType, denominatorMap);
			}
			catch (ValueFactoryException e) {
				System.err.println(e.getMessage());
			}
		if (numerator.isNullJ() || denominator.isNullJ()) return null;
        return RTOvalueOf((QTY.diff) numerator, (QTY.diff) denominator);
	}

	public TS TSvalueOf(Map map) {
		String valueString = (String) map.get(TSPresenter.ATTR_VALUE);
		return TSvalueOfLiteral(valueString);
	}

	public INT INTvalueOf(Map map) {
		String valueString = (String) map.get(INTPresenter.ATTR_VALUE);
        if (valueString == null || valueString == "")
            return INTnull.NA;
        return INTvalueOf(Integer.parseInt(valueString));
	}

	public LIST LISTvalueOf(Collection collection) {
		return LISTjuListAdapter.valueOf(collection);
	}

	public IVL IVLvalueOf(Map map) throws ValueFactoryException {
		String subType = "QTY";
		subType = (String) map.get(DatatypePresenterBase.SUB_TYPE);

		String valueString = (String) map.get(IVLPresenter.ATTR_VALUE);
		String unitString = (String) map.get(IVLPresenter.ATTR_UNIT);

		// String literalString = valueString + (unitString == null ? "" : " " +
		// unitString);

		BL lowClosed = null, highClosed = null;

		lowClosed = BLvalueOfLiteral((String) map.get(IVLPresenter.ATTR_LOWCLOSED));
		highClosed = BLvalueOfLiteral((String) map.get(IVLPresenter.ATTR_HIGHCLOSED));

		List highList = (List) map.get(IVLPresenter.TAG_HIGH);
		List lowList = (List) map.get(IVLPresenter.TAG_LOW);
		List centerList = (List) map.get(IVLPresenter.TAG_CENTER);
		List widthList = (List) map.get(IVLPresenter.TAG_WIDTH);

		QTY high = (highList == null) ? null : (QTY) valueOfMap(subType, (Map) highList.get(0));
		QTY low = (lowList == null) ? null : (QTY) valueOfMap(subType, (Map) lowList.get(0));
		QTY center = (centerList == null) ? null : (QTY) valueOfMap(subType, (Map) centerList.get(0));
		QTY.diff width = null;
        if (centerList != null) {
            QTY qty = (QTY)valueOfMap(subType, (Map) widthList.get(0)); // if IVL subtype is TS, width is not supported
            if (qty instanceof QTY.diff) {
                width = (QTY.diff)qty;
            }
        }

        IVLPresenter.Child child = IVLPresenter.Child.evalChild(low, high, center, width);

		if (child == IVLPresenter.Child.NULL_NI) {
			Datatype datatype = null;
			try {
				datatype = DatatypeMetadataFactoryImpl.instance().createByXsiType(subType);
			}
			catch (UnknownDatatypeException e) {
				throw new ValueFactoryException(e);
			}

            if (valueString == null) return null; // if value is IVLnull, evaluation of datatype is IVL
                                                 // but xml schema does not support IVL complex type
            return IVLPresenter.getIVLValue(valueString, unitString, datatype);
		}

		return IVLPresenter.getIVLValue(lowClosed, low, highClosed, high, width, center);
	}

	public IVL IVLvalueOf(Collection collection) {
		if (collection.size() != 1)
			throw new Error("Only one QTY is allowed in the constrained RMIM IVL");
		QTY boundary = (QTY) collection.toArray()[0];
		return IVLimpl.valueOf(BLimpl.TRUE, boundary, boundary, BLimpl.TRUE);
	}

	public SET SETvalueOf(Collection collection) {
		return SETjuSetAdapter.valueOf(collection);
	}

	public BAG BAGvalueOf(Collection collection) {
		return BAGjuListAdapter.valueOf(collection);
	}

	private Set<CV> getTranslation(List translationList) {
		Set<CV> translationSet = null;
		if (translationList != null) {
			translationSet = new HashSet<CV>();
			for (int i = 0; i < translationList.size(); i++) {
				Map map1 = (Map) translationList.get(i);
				translationSet.add(CVvalueOf(map1));
			}
		}
		return translationSet;
	}

	private ED getOriginalText(List originalTextList) {
		ED originalText = null;
		if (originalTextList == null)
			return originalText;
		for (int i = 0; i < originalTextList.size(); i++) {
			Map map1 = (Map) originalTextList.get(i);
			originalText = EDvalueOf(map1);
		}
		return originalText;
	}
}
