/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.9</a>, using an XML
 * Schema.
 * $Id: ApplicationRoleRelationshipKind.java,v 1.1 2006-10-03 19:42:06 marwahah Exp $
 */

package gov.nih.nci.ncicb.cadsr.edci.domain.impl.types;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Possible relationships between application roles
 * 
 * @version $Revision: 1.1 $ $Date: 2006-10-03 19:42:06 $
 */
public class ApplicationRoleRelationshipKind implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * The AtLeastOne type
     */
    public static final int ATLEASTONE_TYPE = 0;

    /**
     * The instance of the AtLeastOne type
     */
    public static final ApplicationRoleRelationshipKind ATLEASTONE = new ApplicationRoleRelationshipKind(ATLEASTONE_TYPE, "AtLeastOne");

    /**
     * The Includes type
     */
    public static final int INCLUDES_TYPE = 1;

    /**
     * The instance of the Includes type
     */
    public static final ApplicationRoleRelationshipKind INCLUDES = new ApplicationRoleRelationshipKind(INCLUDES_TYPE, "Includes");

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

    private ApplicationRoleRelationshipKind(int type, java.lang.String value) 
     {
        super();
        this.type = type;
        this.stringValue = value;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.ApplicationRoleRelationshipKind(int, java.lang.String)


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method enumerate
     * 
     * Returns an enumeration of all possible instances of
     * ApplicationRoleRelationshipKind
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
     * Returns the type of this ApplicationRoleRelationshipKind
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
        members.put("AtLeastOne", ATLEASTONE);
        members.put("Includes", INCLUDES);
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
     * ApplicationRoleRelationshipKind
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
     * Returns a new ApplicationRoleRelationshipKind based on the
     * given String value.
     * 
     * @param string
     * @return ApplicationRoleRelationshipKind
     */
    public static gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.ApplicationRoleRelationshipKind valueOf(java.lang.String string)
    {
        java.lang.Object obj = null;
        if (string != null) obj = _memberTable.get(string);
        if (obj == null) {
            String err = "'" + string + "' is not a valid ApplicationRoleRelationshipKind";
            throw new IllegalArgumentException(err);
        }
        return (ApplicationRoleRelationshipKind) obj;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.ApplicationRoleRelationshipKind valueOf(java.lang.String) 

}
