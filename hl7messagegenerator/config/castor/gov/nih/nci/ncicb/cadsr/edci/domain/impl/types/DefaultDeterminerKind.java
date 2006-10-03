/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.9</a>, using an XML
 * Schema.
 * $Id: DefaultDeterminerKind.java,v 1.1 2006-10-03 19:42:07 marwahah Exp $
 */

package gov.nih.nci.ncicb.cadsr.edci.domain.impl.types;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Indicates places defaults can be drawn from
 * 
 * @version $Revision: 1.1 $ $Date: 2006-10-03 19:42:07 $
 */
public class DefaultDeterminerKind implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * The ITS type
     */
    public static final int ITS_TYPE = 0;

    /**
     * The instance of the ITS type
     */
    public static final DefaultDeterminerKind ITS = new DefaultDeterminerKind(ITS_TYPE, "ITS");

    /**
     * The ReferencingAttribute type
     */
    public static final int REFERENCINGATTRIBUTE_TYPE = 1;

    /**
     * The instance of the ReferencingAttribute type
     */
    public static final DefaultDeterminerKind REFERENCINGATTRIBUTE = new DefaultDeterminerKind(REFERENCINGATTRIBUTE_TYPE, "ReferencingAttribute");

    /**
     * The Realm type
     */
    public static final int REALM_TYPE = 2;

    /**
     * The instance of the Realm type
     */
    public static final DefaultDeterminerKind REALM = new DefaultDeterminerKind(REALM_TYPE, "Realm");

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

    private DefaultDeterminerKind(int type, java.lang.String value) 
     {
        super();
        this.type = type;
        this.stringValue = value;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.DefaultDeterminerKind(int, java.lang.String)


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method enumerate
     * 
     * Returns an enumeration of all possible instances of
     * DefaultDeterminerKind
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
     * Returns the type of this DefaultDeterminerKind
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
        members.put("ITS", ITS);
        members.put("ReferencingAttribute", REFERENCINGATTRIBUTE);
        members.put("Realm", REALM);
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
     * DefaultDeterminerKind
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
     * Returns a new DefaultDeterminerKind based on the given
     * String value.
     * 
     * @param string
     * @return DefaultDeterminerKind
     */
    public static gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.DefaultDeterminerKind valueOf(java.lang.String string)
    {
        java.lang.Object obj = null;
        if (string != null) obj = _memberTable.get(string);
        if (obj == null) {
            String err = "'" + string + "' is not a valid DefaultDeterminerKind";
            throw new IllegalArgumentException(err);
        }
        return (DefaultDeterminerKind) obj;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.DefaultDeterminerKind valueOf(java.lang.String) 

}
