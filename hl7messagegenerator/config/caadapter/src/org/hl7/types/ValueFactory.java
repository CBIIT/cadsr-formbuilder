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

package org.hl7.types;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import org.regenstrief.ucum.Unit;

/** Abstact base class for factories that create data type values 
    from all sorts of sources including literal forms.

    A different implementation of data types would subclass 
    this factory to provide instances of their own type
    implementations.

    A factory is made a singleton and a factory is obtained with 
    the static method getInstance(), this might in the future 
    retrieve a different class depending on preferences, properties,
    or classpath settings. 
 */
public abstract class ValueFactory {

  /** The field holding the singleton instance. */
  private static ValueFactory _valueFactory 
    = new org.hl7.types.impl.ValueFactoryImpl();

  /** Returns a value factory instance. At this time this is simply a
      singleton instance of this class, in the future this may return
      a subclass depending on some property or preference of classpath
      setting.
  */
  public static ValueFactory getInstance() {
    return _valueFactory;
  }

  /** This class wants to manage its instances and therefore does not
      permit rogue news. */
  protected ValueFactory() {}

  /* interface ValueFactory */
	
  /** Create any data value that has a literal form given a
     type specification (typeSpec) and the literal
     string itself.

     FIXME: typeSpec should be a DataType value and
     literal should be an ST value.
  */
  public abstract ANY valueOfLiteral(String typeSpec, String literal) throws ValueFactoryException;

  public abstract ANY valueOfMap(String typeSpec, Map map) throws ValueFactoryException;  

  /** Create a NULL value dynamically.

      FIXME: nullFlavorString should be a proper NullFlavor code.
   */
  public abstract ANY nullValueOf(String typeSpec, String nullFlavorString) throws ValueFactoryException;
  
  /* here we have all data types with a literal form */

  public abstract BL   BLvalueOfLiteral(String that);
  public abstract ST   STvalueOfLiteral(String that);
  public abstract UID  UIDvalueOfLiteral(String that);
  public abstract URL  URLvalueOfLiteral(String that);
  public abstract INT  INTvalueOfLiteral(String that);
  public abstract REAL REALvalueOfLiteral(String that);
  public abstract PQ   PQvalueOfLiteral(String that);
  public abstract MO   MOvalueOfLiteral(String that);
  public abstract TS   TSvalueOfLiteral(String that);
  public abstract OID  OIDvalueOfLiteral(String that);
  public abstract ENXP ENXPvalueOfLiteral(String that);

  /* generic types and generic type extensions follow */
  // not yet ...

  /* special, non-literal factory methods */

  public abstract BL  BLvalueOf(boolean jvalue);
  public abstract ST  STvalueOf(String jvalue);
  public abstract ST  STvalueOf(String jvalue, String jlanguage);
  public abstract INT INTvalueOf(byte jvalue);
  public abstract INT INTvalueOf(short jvalue);
  public abstract INT INTvalueOf(int jvalue);
  public abstract INT INTvalueOf(long jvalue);
  public abstract INT INTvalueOf(BigInteger jvalue);
  public abstract REAL REALvalueOf(byte jvalue);
  public abstract REAL REALvalueOf(short jvalue);
  public abstract REAL REALvalueOf(int jvalue);
  public abstract REAL REALvalueOf(long jvalue);
  public abstract REAL REALvalueOf(BigInteger jvalue);
  public abstract REAL REALvalueOf(float jvalue);
  public abstract REAL REALvalueOf(double jvalue);
  public abstract REAL REALvalueOf(BigDecimal jvalue);
  public abstract TS   TSvalueOf(Date jvalue);
  public abstract TS   TSvalueOf(Date jvalue, int precision);
  public abstract TN   TNvalueOf(String jvalue);
  public abstract RTO  RTOvalueOf(QTY.diff numerator, QTY.diff denominator);
  public abstract II   IIvalueOf(String root, String extension);
  public abstract II   IIvalueOf(UID root, ST extension);
  public abstract II   IIvalueOf(UID root, String extension);
  public abstract PQ   PQvalueOf(String magnitudeString, String unitString);
  public abstract PQ   PQvalueOf(REAL magnitude, CS unit);
  public abstract PQ   PQvalueOf(REAL magnitude, Unit unit);
  public abstract PQ   PQvalueOf(String magnitude, CS unit);
  public abstract PQ   PQvalueOf(String magnitude, Unit unit);
  public abstract SC   SCvalueOf(String data);
  public abstract SC   SCvalueOf(String data, CE code);
  public abstract Unit UnitvalueOf(ST s);

  public abstract <T extends QTY> IVL<T> IVLvalueOf(BL lowClosed, T low, T high, BL highClosed);
  public abstract <T extends QTY> IVL<T> IVLvalueOf(T center, QTY.diff width, BL lowClosed, BL highClosed);
  public abstract <T extends QTY> IVL<T> IVLvalueOf(BL lowClosed, BL highClosed, T low, QTY.diff width);
  public abstract <T extends QTY> IVL<T> IVLvalueOf(BL lowClosed, QTY.diff width, BL highClosed, T high);

  public abstract <T extends QTY> PIVL<T> PIVLvalueOf(QTY low, QTY high, QTY.diff period, CS alignment, BL institutionSpecified);
  public abstract <T extends QTY> PIVL<T> PIVLvalueOf(IVL ivl, QTY.diff period, CS alignment, BL institutionSpecified);

  /* map-based factory methods by Eric Chen. The map objects are Strings and the
     names are the same as in the XML ITS.
  */
  public abstract II IIvalueOf(Map map);
  public abstract BL BLvalueOf(Map map);
  public abstract AD ADvalueOf(Map map);
  public abstract EN ENvalueOf(Map map);
  public abstract ON ONvalueOf(Map map);
  public abstract PN PNvalueOf(Map map);
  public abstract PQ PQvalueOf(Map map);
  public abstract CS CSvalueOf(Map map);
  public abstract CE CEvalueOf(Map map);
  public abstract CV CVvalueOf(Map map);
  public abstract CR CRvalueOf(Map map);
  public abstract CD CDvalueOf(Map map);
  public abstract ED EDvalueOf(Map map);
  public abstract TS TSvalueOf(Map map);
  public abstract TEL TELvalueOf(Map map);
  public abstract INT INTvalueOf(Map map);
  public abstract RTO RTOvalueOf(Map map);  
  public abstract ST STvalueOf(Map map);
  public abstract SC SCvalueOf(Map map);
  public abstract OID OIDvalueOf(Map map);
  public abstract IVL IVLvalueOf(Map map) throws ValueFactoryException;
  public abstract LIST LISTvalueOf(Collection collection);
  public abstract IVL IVLvalueOf(Collection collection);
  public abstract SET SETvalueOf(Collection collection);
  public abstract BAG BAGvalueOf(Collection collection);
}
