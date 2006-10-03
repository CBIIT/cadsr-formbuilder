/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.9</a>, using an XML
 * Schema.
 * $Id: VisibilityKind.java,v 1.1 2006-10-03 19:42:17 marwahah Exp $
 */

package gov.nih.nci.ncicb.cadsr.edci.domain.impl.types;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * The allowed levels of exposure for an elementUML: Restriction on
 * UML's VisibilityKind (excludes 'package' visibility)
 * 
 * @version $Revision: 1.1 $ $Date: 2006-10-03 19:42:17 $
 */
public class VisibilityKind implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * The private type
     */
    public static final int PRIVATE_TYPE = 0;

    /**
     * The instance of the private type
     */
    public static final VisibilityKind PRIVATE = new VisibilityKind(PRIVATE_TYPE, "private");

    /**
     * The protected type
     */
    public static final int PROTECTED_TYPE = 1;

    /**
     * The instance of the protected type
     */
    public static final VisibilityKind PROTECTED = new VisibilityKind(PROTECTED_TYPE, "protected");

    /**
     * The public type
     */
    public static final int PUBLIC_TYPE = 2;

    /**
     * The instance of the public type
     */
    public static final VisibilityKind PUBLIC = new VisibilityKind(PUBLIC_TYPE, "public");

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

    private VisibilityKind(int type, java.lang.String value) 
     {
        super();
        this.type = type;
        this.stringValue = value;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.VisibilityKind(int, java.lang.String)


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method enumerate
     * 
     * Returns an enumeration of all possible instances of
     * VisibilityKind
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
     * Returns the type of this VisibilityKind
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
        members.put("private", PRIVATE);
        members.put("protected", PROTECTED);
        members.put("public", PUBLIC);
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
     * Returns the String representation of this VisibilityKind
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
     * Returns a new VisibilityKind based on the given String
     * value.
     * 
     * @param string
     * @return VisibilityKind
     */
    public static gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.VisibilityKind valueOf(java.lang.String string)
    {
        java.lang.Object obj = null;
        if (string != null) obj = _memberTable.get(string);
        if (obj == null) {
            String err = "'" + string + "' is not a valid VisibilityKind";
            throw new IllegalArgumentException(err);
        }
        return (VisibilityKind) obj;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.VisibilityKind valueOf(java.lang.String) 

}
