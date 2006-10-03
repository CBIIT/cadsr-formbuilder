/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.9</a>, using an XML
 * Schema.
 * $Id: MapRelationshipKind.java,v 1.1 2006-10-03 19:42:17 marwahah Exp $
 */

package gov.nih.nci.ncicb.cadsr.edci.domain.impl.types;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * The possible relationships between two mapped elements
 * 
 * @version $Revision: 1.1 $ $Date: 2006-10-03 19:42:17 $
 */
public class MapRelationshipKind implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * The BT type
     */
    public static final int BT_TYPE = 0;

    /**
     * The instance of the BT type
     */
    public static final MapRelationshipKind BT = new MapRelationshipKind(BT_TYPE, "BT");

    /**
     * The E type
     */
    public static final int E_TYPE = 1;

    /**
     * The instance of the E type
     */
    public static final MapRelationshipKind E = new MapRelationshipKind(E_TYPE, "E");

    /**
     * The NT type
     */
    public static final int NT_TYPE = 2;

    /**
     * The instance of the NT type
     */
    public static final MapRelationshipKind NT = new MapRelationshipKind(NT_TYPE, "NT");

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

    private MapRelationshipKind(int type, java.lang.String value) 
     {
        super();
        this.type = type;
        this.stringValue = value;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.MapRelationshipKind(int, java.lang.String)


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method enumerate
     * 
     * Returns an enumeration of all possible instances of
     * MapRelationshipKind
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
     * Returns the type of this MapRelationshipKind
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
        members.put("BT", BT);
        members.put("E", E);
        members.put("NT", NT);
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
     * MapRelationshipKind
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
     * Returns a new MapRelationshipKind based on the given String
     * value.
     * 
     * @param string
     * @return MapRelationshipKind
     */
    public static gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.MapRelationshipKind valueOf(java.lang.String string)
    {
        java.lang.Object obj = null;
        if (string != null) obj = _memberTable.get(string);
        if (obj == null) {
            String err = "'" + string + "' is not a valid MapRelationshipKind";
            throw new IllegalArgumentException(err);
        }
        return (MapRelationshipKind) obj;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.MapRelationshipKind valueOf(java.lang.String) 

}
