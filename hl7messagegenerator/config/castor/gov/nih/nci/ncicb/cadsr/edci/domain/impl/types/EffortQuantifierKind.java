/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.9</a>, using an XML
 * Schema.
 * $Id: EffortQuantifierKind.java,v 1.1 2006-10-03 19:42:17 marwahah Exp $
 */

package gov.nih.nci.ncicb.cadsr.edci.domain.impl.types;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Identifies language that constraints can be expressed in.
 * (Excludes OCL, which is the default)UML: Type used by a complex
 * stereotype
 * 
 * @version $Revision: 1.1 $ $Date: 2006-10-03 19:42:17 $
 */
public class EffortQuantifierKind implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * The PersonHour type
     */
    public static final int PERSONHOUR_TYPE = 0;

    /**
     * The instance of the PersonHour type
     */
    public static final EffortQuantifierKind PERSONHOUR = new EffortQuantifierKind(PERSONHOUR_TYPE, "PersonHour");

    /**
     * The PersonDay type
     */
    public static final int PERSONDAY_TYPE = 1;

    /**
     * The instance of the PersonDay type
     */
    public static final EffortQuantifierKind PERSONDAY = new EffortQuantifierKind(PERSONDAY_TYPE, "PersonDay");

    /**
     * The PersonYear type
     */
    public static final int PERSONYEAR_TYPE = 2;

    /**
     * The instance of the PersonYear type
     */
    public static final EffortQuantifierKind PERSONYEAR = new EffortQuantifierKind(PERSONYEAR_TYPE, "PersonYear");

    /**
     * The HourDuration type
     */
    public static final int HOURDURATION_TYPE = 3;

    /**
     * The instance of the HourDuration type
     */
    public static final EffortQuantifierKind HOURDURATION = new EffortQuantifierKind(HOURDURATION_TYPE, "HourDuration");

    /**
     * The DayDuration type
     */
    public static final int DAYDURATION_TYPE = 4;

    /**
     * The instance of the DayDuration type
     */
    public static final EffortQuantifierKind DAYDURATION = new EffortQuantifierKind(DAYDURATION_TYPE, "DayDuration");

    /**
     * The MonthDuration type
     */
    public static final int MONTHDURATION_TYPE = 5;

    /**
     * The instance of the MonthDuration type
     */
    public static final EffortQuantifierKind MONTHDURATION = new EffortQuantifierKind(MONTHDURATION_TYPE, "MonthDuration");

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

    private EffortQuantifierKind(int type, java.lang.String value) 
     {
        super();
        this.type = type;
        this.stringValue = value;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.EffortQuantifierKind(int, java.lang.String)


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method enumerate
     * 
     * Returns an enumeration of all possible instances of
     * EffortQuantifierKind
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
     * Returns the type of this EffortQuantifierKind
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
        members.put("PersonHour", PERSONHOUR);
        members.put("PersonDay", PERSONDAY);
        members.put("PersonYear", PERSONYEAR);
        members.put("HourDuration", HOURDURATION);
        members.put("DayDuration", DAYDURATION);
        members.put("MonthDuration", MONTHDURATION);
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
     * EffortQuantifierKind
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
     * Returns a new EffortQuantifierKind based on the given String
     * value.
     * 
     * @param string
     * @return EffortQuantifierKind
     */
    public static gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.EffortQuantifierKind valueOf(java.lang.String string)
    {
        java.lang.Object obj = null;
        if (string != null) obj = _memberTable.get(string);
        if (obj == null) {
            String err = "'" + string + "' is not a valid EffortQuantifierKind";
            throw new IllegalArgumentException(err);
        }
        return (EffortQuantifierKind) obj;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.EffortQuantifierKind valueOf(java.lang.String) 

}
