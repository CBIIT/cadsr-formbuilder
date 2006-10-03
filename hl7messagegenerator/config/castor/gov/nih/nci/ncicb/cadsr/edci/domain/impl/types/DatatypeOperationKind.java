/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.9</a>, using an XML
 * Schema.
 * $Id: DatatypeOperationKind.java,v 1.1 2006-10-03 19:42:07 marwahah Exp $
 */

package gov.nih.nci.ncicb.cadsr.edci.domain.impl.types;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * The list of allowed datatype property kinds
 * 
 * @version $Revision: 1.1 $ $Date: 2006-10-03 19:42:07 $
 */
public class DatatypeOperationKind implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * The conversion type
     */
    public static final int CONVERSION_TYPE = 0;

    /**
     * The instance of the conversion type
     */
    public static final DatatypeOperationKind CONVERSION = new DatatypeOperationKind(CONVERSION_TYPE, "conversion");

    /**
     * The fixedProperty type
     */
    public static final int FIXEDPROPERTY_TYPE = 1;

    /**
     * The instance of the fixedProperty type
     */
    public static final DatatypeOperationKind FIXEDPROPERTY = new DatatypeOperationKind(FIXEDPROPERTY_TYPE, "fixedProperty");

    /**
     * The variableProperty type
     */
    public static final int VARIABLEPROPERTY_TYPE = 2;

    /**
     * The instance of the variableProperty type
     */
    public static final DatatypeOperationKind VARIABLEPROPERTY = new DatatypeOperationKind(VARIABLEPROPERTY_TYPE, "variableProperty");

    /**
     * Field _memberTable
     */
    private static java.util.Hashtable _memberTable = init();

    /**
     * Field type
     */
    private int type = -1;

    /**
     * Field stringValue
     */
    private java.lang.String stringValue = null;


      //----------------/
     //- Constructors -/
    //----------------/

    private DatatypeOperationKind(int type, java.lang.String value) 
     {
        super();
        this.type = type;
        this.stringValue = value;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.DatatypeOperationKind(int, java.lang.String)


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method enumerate
     * 
     * Returns an enumeration of all possible instances of
     * DatatypeOperationKind
     * 
     * @return Enumeration
     */
    public static java.util.Enumeration enumerate()
    {
        return _memberTable.elements();
    } //-- java.util.Enumeration enumerate() 

    /**
     * Method getType
     * 
     * Returns the type of this DatatypeOperationKind
     * 
     * @return int
     */
    public int getType()
    {
        return this.type;
    } //-- int getType() 

    /**
     * Method init
     * 
     * 
     * 
     * @return Hashtable
     */
    private static java.util.Hashtable init()
    {
        Hashtable members = new Hashtable();
        members.put("conversion", CONVERSION);
        members.put("fixedProperty", FIXEDPROPERTY);
        members.put("variableProperty", VARIABLEPROPERTY);
        return members;
    } //-- java.util.Hashtable init() 

    /**
     * Method readResolve
     * 
     *  will be called during deserialization to replace the
     * deserialized object with the correct constant instance.
     * <br/>
     * 
     * @return Object
     */
    private java.lang.Object readResolve()
    {
        return valueOf(this.stringValue);
    } //-- java.lang.Object readResolve() 

    /**
     * Method toString
     * 
     * Returns the String representation of this
     * DatatypeOperationKind
     * 
     * @return String
     */
    public java.lang.String toString()
    {
        return this.stringValue;
    } //-- java.lang.String toString() 

    /**
     * Method valueOf
     * 
     * Returns a new DatatypeOperationKind based on the given
     * String value.
     * 
     * @param string
     * @return DatatypeOperationKind
     */
    public static gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.DatatypeOperationKind valueOf(java.lang.String string)
    {
        java.lang.Object obj = null;
        if (string != null) obj = _memberTable.get(string);
        if (obj == null) {
            String err = "'" + string + "' is not a valid DatatypeOperationKind";
            throw new IllegalArgumentException(err);
        }
        return (DatatypeOperationKind) obj;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.DatatypeOperationKind valueOf(java.lang.String) 

}
