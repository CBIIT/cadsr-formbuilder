/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.9</a>, using an XML
 * Schema.
 * $Id: StaticModelRepresentationKind.java,v 1.1 2006-10-03 19:42:17 marwahah Exp $
 */

package gov.nih.nci.ncicb.cadsr.edci.domain.impl.types;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * The allowed mechanisms by which a static can be serialized
 * 
 * @version $Revision: 1.1 $ $Date: 2006-10-03 19:42:17 $
 */
public class StaticModelRepresentationKind implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * The flat type
     */
    public static final int FLAT_TYPE = 0;

    /**
     * The instance of the flat type
     */
    public static final StaticModelRepresentationKind FLAT = new StaticModelRepresentationKind(FLAT_TYPE, "flat");

    /**
     * The serialized type
     */
    public static final int SERIALIZED_TYPE = 1;

    /**
     * The instance of the serialized type
     */
    public static final StaticModelRepresentationKind SERIALIZED = new StaticModelRepresentationKind(SERIALIZED_TYPE, "serialized");

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

    private StaticModelRepresentationKind(int type, java.lang.String value) 
     {
        super();
        this.type = type;
        this.stringValue = value;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.StaticModelRepresentationKind(int, java.lang.String)


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method enumerate
     * 
     * Returns an enumeration of all possible instances of
     * StaticModelRepresentationKind
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
     * Returns the type of this StaticModelRepresentationKind
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
        members.put("flat", FLAT);
        members.put("serialized", SERIALIZED);
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
     * StaticModelRepresentationKind
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
     * Returns a new StaticModelRepresentationKind based on the
     * given String value.
     * 
     * @param string
     * @return StaticModelRepresentationKind
     */
    public static gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.StaticModelRepresentationKind valueOf(java.lang.String string)
    {
        java.lang.Object obj = null;
        if (string != null) obj = _memberTable.get(string);
        if (obj == null) {
            String err = "'" + string + "' is not a valid StaticModelRepresentationKind";
            throw new IllegalArgumentException(err);
        }
        return (StaticModelRepresentationKind) obj;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.StaticModelRepresentationKind valueOf(java.lang.String) 

}
