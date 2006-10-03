/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.9</a>, using an XML
 * Schema.
 * $Id: DurationUnitsKind.java,v 1.1 2006-10-03 19:42:17 marwahah Exp $
 */

package gov.nih.nci.ncicb.cadsr.edci.domain.impl.types;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Allowed units for measuring duration
 * 
 * @version $Revision: 1.1 $ $Date: 2006-10-03 19:42:17 $
 */
public class DurationUnitsKind implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * The ms type
     */
    public static final int MS_TYPE = 0;

    /**
     * The instance of the ms type
     */
    public static final DurationUnitsKind MS = new DurationUnitsKind(MS_TYPE, "ms");

    /**
     * The s type
     */
    public static final int S_TYPE = 1;

    /**
     * The instance of the s type
     */
    public static final DurationUnitsKind S = new DurationUnitsKind(S_TYPE, "s");

    /**
     * The min type
     */
    public static final int MIN_TYPE = 2;

    /**
     * The instance of the min type
     */
    public static final DurationUnitsKind MIN = new DurationUnitsKind(MIN_TYPE, "min");

    /**
     * The h type
     */
    public static final int H_TYPE = 3;

    /**
     * The instance of the h type
     */
    public static final DurationUnitsKind H = new DurationUnitsKind(H_TYPE, "h");

    /**
     * The d type
     */
    public static final int D_TYPE = 4;

    /**
     * The instance of the d type
     */
    public static final DurationUnitsKind D = new DurationUnitsKind(D_TYPE, "d");

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

    private DurationUnitsKind(int type, java.lang.String value) 
     {
        super();
        this.type = type;
        this.stringValue = value;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.DurationUnitsKind(int, java.lang.String)


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method enumerate
     * 
     * Returns an enumeration of all possible instances of
     * DurationUnitsKind
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
     * Returns the type of this DurationUnitsKind
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
        members.put("ms", MS);
        members.put("s", S);
        members.put("min", MIN);
        members.put("h", H);
        members.put("d", D);
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
     * Returns the String representation of this DurationUnitsKind
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
     * Returns a new DurationUnitsKind based on the given String
     * value.
     * 
     * @param string
     * @return DurationUnitsKind
     */
    public static gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.DurationUnitsKind valueOf(java.lang.String string)
    {
        java.lang.Object obj = null;
        if (string != null) obj = _memberTable.get(string);
        if (obj == null) {
            String err = "'" + string + "' is not a valid DurationUnitsKind";
            throw new IllegalArgumentException(err);
        }
        return (DurationUnitsKind) obj;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.DurationUnitsKind valueOf(java.lang.String) 

}
